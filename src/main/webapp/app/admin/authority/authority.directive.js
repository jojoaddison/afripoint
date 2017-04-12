(function() {
    'use strict';

    var authority = {
        templateUrl: "app/admin/authority/authorities.html",
        controller: 'AuthorityController',
        controllerAs: 'vm',
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
                $translatePartialLoader.addPart('authority');
                $translatePartialLoader.addPart('global');
                return $translate.refresh();
            }]
        }
    };

    angular
        .module('afripointApp')
        .component('authority', authority);

})();
