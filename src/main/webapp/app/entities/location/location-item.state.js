(function() {
    'use strict';

    angular
        .module('afripointApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('location-item', {
            parent: 'entity',
            url: '/location-item?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'afripointApp.locationItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/location/location-items.html',
                    controller: 'LocationItemController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,desc',
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
                    $translatePartialLoader.addPart('locationItem');
                    $translatePartialLoader.addPart('periods');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('location-item-detail', {
            parent: 'location-item',
            url: '/location/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'afripointApp.locationItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/location/location-item-detail.html',
                    controller: 'LocationItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('locationItem');
                    $translatePartialLoader.addPart('periods');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LocationItem', function($stateParams, LocationItem) {
                    return LocationItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'location-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('location-item-detail.edit', {
            parent: 'location-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-item-dialog.html',
                    controller: 'LocationItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocationItem', function(LocationItem) {
                            return LocationItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('location-item.new', {
            parent: 'location-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-item-dialog.html',
                    controller: 'LocationItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                            	image: null,
                            	contentType: null,
                                category: null,
                                service: null,
                                description: null,
                                period: null,
                                price: null,
                                memberPrice: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('location-item', null, { reload: 'location-item' });
                }, function() {
                    $state.go('location-item');
                });
            }]
        })
        .state('location-item.edit', {
            parent: 'location-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-item-dialog.html',
                    controller: 'LocationItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocationItem', function(LocationItem) {
                            return LocationItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('location-item', null, { reload: 'location-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('location-item.delete', {
            parent: 'location-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-item-delete-dialog.html',
                    controller: 'LocationItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LocationItem', function(LocationItem) {
                            return LocationItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('location-item', null, { reload: 'location-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
