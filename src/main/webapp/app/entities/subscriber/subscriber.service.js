(function() {
    'use strict';
    angular
        .module('afripointApp')
        .factory('Subscriber', Subscriber);

    Subscriber.$inject = ['$resource', 'DateUtils'];

    function Subscriber ($resource, DateUtils) {
        var resourceUrl =  'api/subscribers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
