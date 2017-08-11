(function() {
    'use strict';

    angular
        .module('afripointApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('afripoint-service', {
            parent: 'entity',
            url: '/afripoint-service',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'afripointApp.afripointService.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/afripoint/afripoint.html',
                    controller: 'AfripointServiceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('afripointService');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('afripoint-detail', {
            parent: 'afripoint',
            url: '/afripoint-service/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'afripointApp.afripointService.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/afripoint/afripoint-detail.html',
                    controller: 'AfripointServiceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('afripointService');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AfripointService', function($stateParams, AfripointService) {
                    return AfripointService.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'afripoint',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('afripoint-detail.edit', {
            parent: 'afripoint-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/afripoint/afripoint-dialog.html',
                    controller: 'AfripointServiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AfripointService', function(AfripointService) {
                            return AfripointService.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('afripoint-service.new', {
            parent: 'afripoint-service',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/afripoint/afripoint-dialog.html',
                    controller: 'AfripointServiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                photo: null,
                                photoContentType: null,
                                contact: null,
                                icon: null,
                                createdDate: null,
                                modifiedDate: null,
                                createdBy: null,
                                modifiedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('afripoint', null, { reload: 'afripoint' });
                }, function() {
                    $state.go('afripoint');
                });
            }]
        })
        .state('afripoint-service.edit', {
            parent: 'afripoint-service',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/afripoint/afripoint-dialog.html',
                    controller: 'AfripointServiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AfripointService', function(AfripointService) {
                            return AfripointService.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('afripoint', null, { reload: 'afripoint' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('afripoint.delete', {
            parent: 'afripoint',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/afripoint/afripoint-delete-dialog.html',
                    controller: 'AfripointServiceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AfripointService', function(AfripointService) {
                            return AfripointService.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('afripoint', null, { reload: 'afripoint' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
