(function() {
    'use strict';
    angular
        .module('afripointApp')
        .factory('Media', Media);

    Media.$inject = ['$resource', 'DateUtils'];

    function Media ($resource, DateUtils) {
        var resourceUrl =  'api/media/:id';

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
