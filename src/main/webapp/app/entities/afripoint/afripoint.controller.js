(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('AfripointServiceController', AfripointServiceController);

    AfripointServiceController.$inject = ['DataUtils', 'AfripointService'];

    function AfripointServiceController(DataUtils, AfripointService) {

        var vm = this;

        vm.afripointServices = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            AfripointService.query(function(result) {
                vm.afripointServices = result;
                vm.searchQuery = null;
            });
        }
    }
})();
