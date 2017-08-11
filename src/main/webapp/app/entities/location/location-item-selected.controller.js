(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('LocationItemSelectedController', LocationItemSelectedController);

    LocationItemSelectedController.$inject = ['$rootScope', 'JhiLanguageService', 'ParseLinks', 'AlertService', '$uibModalInstance', 'ngCart', '$translate', '$sce', 'orders'];

    function LocationItemSelectedController($rootScope, JhiLanguageService, ParseLinks, AlertService, $uibModalInstance, ngCart, $translate, $sce, orders) {

        var vm = this;
        vm.close = close;
        vm.itemsPerPage = 100;
        vm.basket = ngCart;
        vm.locationItemsSelected = orders;
        vm.showItemsSelected = ngCart.getTotalItems() > 0;
       JhiLanguageService.getCurrent().then(function(lang){
    	   vm.lang = lang;
       });
        
        console.log(vm.lang);        
        console.log(orders);
        console.log(ngCart.getItems());

        $translate("ngcart.checkoutButton").then(function(value){
        	vm.paymentButton = $sce.trustAsHtml(value);
        });
        
        function close(){
			$uibModalInstance.dismiss('cancel');
		}
        

        $rootScope.$on("ngCart:itemAdded", function(){
            checkShowCart();
        });

        $rootScope.$on("ngCart:itemRemoved", function(){
            checkShowCart();
        });
        
        function checkShowCart(){
        	vm.showItemsSelected = ngCart.getTotalItems() > 0;
        }
        
        
        
    }
})();
