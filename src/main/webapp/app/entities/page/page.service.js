(function() {
    'use strict';
    angular
        .module('afripointApp')
        .factory('Page', Page);

    Page.$inject = ['$resource', 'DateUtils'];

    function Page ($resource, DateUtils) {
        var resourceUrl =  'api/pages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
