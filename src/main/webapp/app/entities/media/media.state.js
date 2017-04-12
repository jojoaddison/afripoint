(function() {
    'use strict';

    angular
        .module('afripointApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('media', {
            parent: 'entity',
            url: '/media',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'afripointApp.media.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/media/media.html',
                    controller: 'MediaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('media');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('media-detail', {
            parent: 'media',
            url: '/media/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'afripointApp.media.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/media/media-detail.html',
                    controller: 'MediaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('media');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Media', function($stateParams, Media) {
                    return Media.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'media',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('media-detail.edit', {
            parent: 'media-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-dialog.html',
                    controller: 'MediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Media', function(Media) {
                            return Media.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('media.new', {
            parent: 'media',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-dialog.html',
                    controller: 'MediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                caption: null,
                                url: null,
                                name: null,
                                bytes: null,
                                bytesContentType: null,
                                description: null,
                                createdDate: null,
                                modifiedDate: null,
                                type: null,
                                size: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('media', null, { reload: 'media' });
                }, function() {
                    $state.go('media');
                });
            }]
        })
        .state('media.edit', {
            parent: 'media',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-dialog.html',
                    controller: 'MediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Media', function(Media) {
                            return Media.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('media', null, { reload: 'media' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('media.delete', {
            parent: 'media',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-delete-dialog.html',
                    controller: 'MediaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Media', function(Media) {
                            return Media.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('media', null, { reload: 'media' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
