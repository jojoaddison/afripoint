(function() {
	'use strict';
	angular
		.module('afripointApp')
		.controller('ServicesController', ServicesController);
	ServicesController.$inject = ['$scope', 'LocationItem', 'AlertService'];
	function ServicesController($scope, LocationItem, AlertService){
        var vm = this;

        loadAll();

        function loadAll(){        	
            LocationItem.query({}, onSuccess, onError); 
            function onSuccess(data) {
            	console.log(data);
                vm.services = data;
            }            
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
	}
})();
