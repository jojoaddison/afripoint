(function() {
    'use strict';

    angular
        .module('afripointApp')
        .factory('RoleResources', RoleResources);

    RoleResources.$inject = ['$resource'];

    function RoleResources($resource) {
        var resourceUrl =  'api/resources/';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
