(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('LocationItemListController', LocationItemListController);

    LocationItemListController.$inject = ['LocationItem', 'ParseLinks', 'AlertService', '$uibModal', '$uibModalInstance', 'ngCart'];

    function LocationItemListController(LocationItem, ParseLinks, AlertService, $uibModal, $uibModalInstance, ngCart) {

        var vm = this;
        vm.close = close;
        vm.itemsPerPage = 100;
        vm.ngCart = ngCart;
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

		function checkLocationItemsSelected() {
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
    						} ]
    					}
    				}
    			);
		}

        function close(){
			$uibModalInstance.dismiss('cancel');
		}
    }
})();
