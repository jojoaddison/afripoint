(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('LocationItemDialogController', LocationItemDialogController);

    LocationItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LocationItem'];

    function LocationItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LocationItem) {
        var vm = this;

        vm.locationItem = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.locationItem.id !== null) {
                LocationItem.update(vm.locationItem, onSaveSuccess, onSaveError);
            } else {
                LocationItem.save(vm.locationItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('afripointApp:locationItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
