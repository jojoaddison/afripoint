(function() {
    'use strict';
    angular
        .module('afripointApp')
        .factory('Authority', Authority);

    Authority.$inject = ['$resource'];

    function Authority ($resource) {
        var resourceUrl =  'api/authorities/:name';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
