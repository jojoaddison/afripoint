(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('LocationItemListController', LocationItemListController);

    LocationItemListController.$inject = ['LocationItem', 'ParseLinks', 'AlertService', '$uibModalInstance'];

    function LocationItemListController(LocationItem, ParseLinks, AlertService, $uibModalInstance) {

        var vm = this;
        vm.close = close;
        vm.itemsPerPage = 20;

        loadAll();

        function loadAll () {
            LocationItem.query({
                page: 1,
                size: vm.itemsPerPage,
                sort: {
                    value: 'id,desc',
                    squash: true
                }
            }, onSuccess, onError);
            
            
            function onSuccess(data, headers) {
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


        function close(){
			$uibModalInstance.dismiss('cancel');
		}
    }
})();
