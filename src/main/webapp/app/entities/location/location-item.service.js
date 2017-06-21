(function() {
    'use strict';
    angular
        .module('afripointApp')
        .factory('LocationItem', LocationItem);

    LocationItem.$inject = ['$resource'];

    function LocationItem ($resource) {
        var resourceUrl =  'api/location-items/:id';

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
