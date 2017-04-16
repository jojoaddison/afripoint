(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('PageController', PageController);

    PageController.$inject = ['Page'];

    function PageController(Page) {

        var vm = this;

        vm.pages = [];

        loadAll();

        function loadAll() {
            Page.query(function(result) {
                vm.pages = result;
                vm.searchQuery = null;
            });
        }
    }
})();
