(function() {
    'use strict';

    angular
        .module('afripointApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {    	
        $stateProvider
        .state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'header@': {
                    templateUrl: 'app/home/header.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                },
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                },
                'footer@': {
                    templateUrl: 'app/home/footer.html',
                    controller: 'FooterController',
                    controllerAs: 'fvm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    $translatePartialLoader.addPart('periods');
                    $translatePartialLoader.addPart('afripointService');
                    return $translate.refresh();
                }]
            }
        })
        .state('about', {
            parent: 'app',
            url: '/#about',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                },
                'footer@': {
                    templateUrl: 'app/home/footer.html',
                    controller: 'HomeController',
                    controllerAs: 'fvm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    $translatePartialLoader.addPart('afripointService');
                    return $translate.refresh();
                }]
            }
        })        
        .state('services', {
            parent: 'home',
            url: 'services',
            data: {
                authorities: [],
                pageTitle: 'afripointApp.home.services'
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/services.html',
                    controller: 'ServicesController',
                    controllerAs: 'vm'
                },
                'footer@': {
                    templateUrl: 'app/home/footer.html',
                    controller: 'HomeController',
                    controllerAs: 'fvm'
                }
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
                    $translatePartialLoader.addPart('home');
                    $translatePartialLoader.addPart('periods');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('advertise', {
            parent: 'home',
            url: 'advertise',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/advertise.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                },
                'footer@': {
                    templateUrl: 'app/home/footer.html',
                    controller: 'HomeController',
                    controllerAs: 'fvm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    $translatePartialLoader.addPart('afripointService');
                    return $translate.refresh();
                }]
            }
        })
        .state('shop', {
            parent: 'home',
            url: 'shop',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/shop.html',
                    controller: 'ShoppingController',
                    controllerAs: 'vm'
                },
                'footer@': {
                    templateUrl: 'app/home/footer.html',
                    controller: 'HomeController',
                    controllerAs: 'fvm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    $translatePartialLoader.addPart('afripointService');
                    return $translate.refresh();
                }]
            }
        })
        .state('location', {
            parent: 'home',
            url: 'location',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/location.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                },
                'footer@': {
                    templateUrl: 'app/home/footer.html',
                    controller: 'HomeController',
                    controllerAs: 'fvm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    $translatePartialLoader.addPart('afripointService');
                    return $translate.refresh();
                }]
            }
        })
		.state('contact', {
            parent: 'app',
            url: '/#contact',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                },
                'footer@': {
                    templateUrl: 'app/home/footer.html',
                    controller: 'HomeController',
                    controllerAs: 'fvm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    $translatePartialLoader.addPart('afripointService');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
