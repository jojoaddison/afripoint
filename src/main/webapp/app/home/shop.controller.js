(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('ShoppingController', ShoppingController);

    ShoppingController.$inject = ['$rootScope', 'LocationItem', 'ParseLinks', 'AlertService', '$uibModal', '$uibModalInstance', 'ngCart'];

    function ShoppingController($rootScope, LocationItem, ParseLinks, AlertService, $uibModal, $uibModalInstance, ngCart) {

        var vm = this;
        vm.close = close;
        vm.itemsPerPage = 100;
        vm.basket = ngCart;
        vm.showItemsSelected = ngCart.getTotalItems() > 0;
        vm.checkLocationItemsSelected = checkLocationItemsSelected;
        

        loadAll();

        function loadAll () {
            LocationItem.query({
                page: 0,
                size: vm.itemsPerPage,
                sort: 'id,desc'
            }, onSuccess, onError);
            
            
            function onSuccess(data, headers) {
            	console.log(data);
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.locationItems = data;
                vm.page = 1;
            }
            
            function onError(error) {
                AlertService.error(error.data.message);
            }
            
        }
        
        $rootScope.$on("ngCart:itemAdded", function(event, item){
        	console.log(item);
        	angular.forEach(vm.locationItems, function(order, k){
        		if(order.id == item.id){
                	vm.orders.push(order);
        		}
        	});
            checkShowCart();
        });

        $rootScope.$on("ngCart:itemRemoved", function(event, item){
        	angular.forEach(vm.orders, function(order, k){
        		console.log(k);
        		if(order.id == item.id){
        			vm.orders.splice(k, 1);
        		}
        	});
        	
            checkShowCart();
        });
        
        function checkShowCart(){
        	vm.showItemsSelected = ngCart.getTotalItems() > 0;
        }

		function checkLocationItemsSelected() {	
			var orders = ngCart.getItems();
			var cart = [];
			for(var k = 0; k < orders.length; k++){
				var order = orders[k];
				console.log(order);
				for(var i = 0; i < vm.locationItems.length; i++){
					var item = vm.locationItems[i];
					if(item.id == order._id){
						console.log(item);
						cart.push(item);
					}
				}
			}
			$uibModal.open(
    				{
    					templateUrl : 'app/entities/location/location-item-selected.html',
    					controller : 'LocationItemSelectedController',
    					controllerAs : 'vm',
    					backdrop : 'static',
    					size : 'lg',
    					resolve : {    						
    						translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
    							$translatePartialLoader.addPart('locationItem');
    							$translatePartialLoader.addPart('home');
    							$translatePartialLoader.addPart('global');
    							return $translate.refresh();  	
    							}    						
    						],    						
    						orders: [ function() {
    							return cart;
    						}]
    					}
    				}
    			);

			close();
		}

        function close(){
			$uibModalInstance.dismiss('cancel');
		}
    }
})();
