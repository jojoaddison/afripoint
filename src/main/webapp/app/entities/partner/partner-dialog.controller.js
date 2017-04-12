(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('PartnerDialogController', PartnerDialogController);

    PartnerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$translate', '$uibModalInstance', 'entity', 'Partner'];

    function PartnerDialogController ($timeout, $scope, $stateParams, $translate, $uibModalInstance, entity, Partner) {
        var vm = this;

        vm.partner = entity;
        vm.clear = clear;
        vm.save = save;
        vm.showAddress = false;
        vm.toggleShowAddress = toggleShowAddress;
        vm.titles = [];        
        var titles =["mr","mrs","miss","dr","ms","ing","prof"]; 
        
        $scope.translateTitle = translateTitle;
        
        function translateTitle(title){
        	var key = "afripointApp.partner.titles."+title;
        	$translate(key).then(function(value){
        		return value;
        	});
        }
        
        function toggleShowAddress(){
        	vm.showAddress = !vm.showAddress;
        }

        $timeout(function (){
        	angular.forEach(titles, function(title){
        		var key = "afripointApp.partner.titles."+title;
            	$translate(key).then(function(value){
            		vm.titles.push(value);
            	});
        	});
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            console.log(vm.partner);
            if (vm.partner.id !== null) {
                Partner.update(vm.partner, onSaveSuccess, onSaveError);
            } else {
                Partner.save(vm.partner, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('afripointApp:partnerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
