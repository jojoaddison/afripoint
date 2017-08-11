(function() {
    'use strict';

    angular
        .module('afripointApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('info-screen', {
            parent: 'entity',
            url: '/info-screen?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'afripointApp.infoScreen.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/info-screen/info-screens.html',
                    controller: 'InfoScreenController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('infoScreen');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('info-screen-detail', {
            parent: 'info-screen',
            url: '/info-screen/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'afripointApp.infoScreen.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/info-screen/info-screen-detail.html',
                    controller: 'InfoScreenDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('infoScreen');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'InfoScreen', function($stateParams, InfoScreen) {
                    return InfoScreen.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'info-screen',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('info-screen-detail.edit', {
            parent: 'info-screen-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/info-screen/info-screen-dialog.html',
                    controller: 'InfoScreenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InfoScreen', function(InfoScreen) {
                            return InfoScreen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('info-screen.new', {
            parent: 'info-screen',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/info-screen/info-screen-dialog.html',
                    controller: 'InfoScreenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                screenUrl: null,
                                caption: null,
                                createdDate: null,
                                modifiedDate: null,
                                lastModifiedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('info-screen', null, { reload: 'info-screen' });
                }, function() {
                    $state.go('info-screen');
                });
            }]
        })
        .state('info-screen.edit', {
            parent: 'info-screen',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/info-screen/info-screen-dialog.html',
                    controller: 'InfoScreenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InfoScreen', function(InfoScreen) {
                            return InfoScreen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('info-screen', null, { reload: 'info-screen' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('info-screen.delete', {
            parent: 'info-screen',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/info-screen/info-screen-delete-dialog.html',
                    controller: 'InfoScreenDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InfoScreen', function(InfoScreen) {
                            return InfoScreen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('info-screen', null, { reload: 'info-screen' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
