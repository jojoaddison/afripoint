(function() {
    'use strict';

    angular
        .module('afripointApp')
        .factory('dataHandler', dataHandler);

    dataHandler.$inject = ['$localStorage', 'StorageDB'];

    function dataHandler( $localStorage, StorageDB) {
        return {
            initialize: initialize
        };

        function initialize() {
        	$localStorage['eventsdb'] = true;
        	$localStorage['galleriesdb'] = true;
        }

    }
})();
