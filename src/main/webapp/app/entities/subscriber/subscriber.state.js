(function() {
    'use strict';

    angular
        .module('afripointApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subscriber', {
            parent: 'entity',
            url: '/subscriber?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'afripointApp.subscriber.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subscriber/subscribers.html',
                    controller: 'SubscriberController',
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
                    $translatePartialLoader.addPart('subscriber');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('subscriber-detail', {
            parent: 'subscriber',
            url: '/subscriber/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'afripointApp.subscriber.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subscriber/subscriber-detail.html',
                    controller: 'SubscriberDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subscriber');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Subscriber', function($stateParams, Subscriber) {
                    return Subscriber.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'subscriber',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('subscriber-detail.edit', {
            parent: 'subscriber-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber/subscriber-dialog.html',
                    controller: 'SubscriberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subscriber', function(Subscriber) {
                            return Subscriber.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subscriber.new', {
            parent: 'subscriber',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber/subscriber-dialog.html',
                    controller: 'SubscriberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                email: null,
                                createdDate: null,
                                active: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('subscriber', null, { reload: 'subscriber' });
                }, function() {
                    $state.go('subscriber');
                });
            }]
        })
        .state('subscriber.edit', {
            parent: 'subscriber',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber/subscriber-dialog.html',
                    controller: 'SubscriberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subscriber', function(Subscriber) {
                            return Subscriber.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subscriber', null, { reload: 'subscriber' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subscriber.delete', {
            parent: 'subscriber',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber/subscriber-delete-dialog.html',
                    controller: 'SubscriberDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Subscriber', function(Subscriber) {
                            return Subscriber.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subscriber', null, { reload: 'subscriber' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
