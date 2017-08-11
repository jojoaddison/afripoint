(function() {
	'use strict';

	angular
		.module('afripointApp')
		.controller('FooterController', FooterController);
	
	FooterController.$inject = ['$scope', 'PageUtils', 'AlertService'];
	function FooterController($scope, PageUtils, AlertService){
        var vm = this;
		vm.openPage = PageUtils.openPage;
		vm.acceptCookies = acceptCookies;
		vm.checkCookies = checkCookies;
		vm.cookiesAccepted = true;

        function acceptCookies(){
        	StorageUtils.set('cookiesAccepted', true);
        	vm.cookiesAccepted = true;
        }

        function checkCookies(){
        	var cookiesAccepted = StorageUtils.get('cookiesAccepted', false);
        	if(cookiesAccepted){
        		vm.cookiesAccepted = true;
        	}else{
        		vm.cookiesAccepted = false;
        	}
        }
        
	}
	
})();
