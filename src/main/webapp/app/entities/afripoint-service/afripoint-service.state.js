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
                authorities: ['ROLE_USER'],
                pageTitle: 'afripointApp.afripointService.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/afripoint-service/afripoint-services.html',
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
        .state('afripoint-service-detail', {
            parent: 'afripoint-service',
            url: '/afripoint-service/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'afripointApp.afripointService.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/afripoint-service/afripoint-service-detail.html',
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
                        name: $state.current.name || 'afripoint-service',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('afripoint-service-detail.edit', {
            parent: 'afripoint-service-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/afripoint-service/afripoint-service-dialog.html',
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
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/afripoint-service/afripoint-service-dialog.html',
                    controller: 'AfripointServiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                photo: null,
                                photoContentType: null,
                                contact: null,
                                createdDate: null,
                                modifiedDate: null,
                                createdBy: null,
                                modifiedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('afripoint-service', null, { reload: 'afripoint-service' });
                }, function() {
                    $state.go('afripoint-service');
                });
            }]
        })
        .state('afripoint-service.edit', {
            parent: 'afripoint-service',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/afripoint-service/afripoint-service-dialog.html',
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
                    $state.go('afripoint-service', null, { reload: 'afripoint-service' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('afripoint-service.delete', {
            parent: 'afripoint-service',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/afripoint-service/afripoint-service-delete-dialog.html',
                    controller: 'AfripointServiceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AfripointService', function(AfripointService) {
                            return AfripointService.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('afripoint-service', null, { reload: 'afripoint-service' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
