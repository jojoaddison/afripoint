(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('ProductController', ProductController);

    ProductController.$inject = ['Product'];

    function ProductController(Product) {

        var vm = this;

        vm.products = [];

        loadAll();

        function loadAll() {
            Product.query(function(result) {
                vm.products = result;
                vm.searchQuery = null;
            });
        }
    }
})();
