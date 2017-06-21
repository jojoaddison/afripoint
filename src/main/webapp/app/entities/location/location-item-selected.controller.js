(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('LocationItemSelectedController', LocationItemSelectedController);

    LocationItemSelectedController.$inject = ['$rootScope', 'ParseLinks', 'AlertService', '$uibModalInstance', 'ngCart'];

    function LocationItemSelectedController($rootScope, ParseLinks, AlertService, $uibModalInstance, ngCart) {

        var vm = this;
        vm.close = close;
        vm.itemsPerPage = 100;
        vm.ngCart = ngCart;
        vm.locationItemsSelected = ngCart.getItems();
        vm.showCart = ngCart.getCart().getItems().lenth > 0;

        function close(){
			$uibModalInstance.dismiss('cancel');
		}
    }
})();
