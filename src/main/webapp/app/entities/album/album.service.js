(function() {
    'use strict';
    angular
        .module('afripointApp')
        .factory('Album', Album);

    Album.$inject = ['$resource', 'DateUtils'];

    function Album ($resource, DateUtils) {
        var resourceUrl =  'api/albums/:id';

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
            'update': { method:'PUT' },
            'deleteMedia': {
            	url: '/api/albums/media',
            	method: 'POST'            	
            }
        });
    }
})();
