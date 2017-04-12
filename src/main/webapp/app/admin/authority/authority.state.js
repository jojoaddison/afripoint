(function() {
    'use strict';

    angular
        .module('afripointApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('authority', {
            parent: 'admin',
            url: '/authority?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'afripointApp.authority.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/authority/authorities.html',
                    controller: 'AuthorityController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'name,asc',
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
                    $translatePartialLoader.addPart('authority');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('authority-detail', {
            parent: 'authority',
            url: '/authority/{name}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'afripointApp.authority.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/authority/authority-detail.html',
                    controller: 'AuthorityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('authority');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Authority', function($stateParams, Authority) {
                    return Authority.get({name : $stateParams.name}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'authority',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('authority-detail.edit', {
            parent: 'authority-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/authority/authority-dialog.html',
                    controller: 'AuthorityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'sm',
                    resolve: {
                        entity: ['Authority', function(Authority) {
                            return Authority.get({name : $stateParams.name}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('authority.new', {
            parent: 'authority',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/authority/authority-dialog.html',
                    controller: 'AuthorityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'sm',
                    resolve: {
                        entity: function () {
                            return {
                                name: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('authority', null, { reload: 'authority' });
                }, function() {
                    $state.go('authority');
                });
            }]
        })
        .state('authority.edit', {
            parent: 'authority',
            url: '/{name}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/authority/authority-dialog.html',
                    controller: 'AuthorityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Authority', function(Authority) {
                            return Authority.get({name : $stateParams.name}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('authority', null, { reload: 'authority' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('authority.delete', {
            parent: 'authority',
            url: '/{name}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/authority/authority-delete-dialog.html',
                    controller: 'AuthorityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Authority', function(Authority) {
                            return Authority.get({name : $stateParams.name}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('authority', null, { reload: 'authority' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
