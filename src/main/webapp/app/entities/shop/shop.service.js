(function() {
    'use strict';
    angular
        .module('afripointApp')
        .factory('Shop', Shop);

    Shop.$inject = ['$resource', 'DateUtils'];

    function Shop ($resource, DateUtils) {
        var resourceUrl =  'api/shops/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.modifiedDate = DateUtils.convertDateTimeFromServer(data.modifiedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
