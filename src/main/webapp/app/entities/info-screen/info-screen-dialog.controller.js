(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('InfoScreenDialogController', InfoScreenDialogController);

    InfoScreenDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InfoScreen'];

    function InfoScreenDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InfoScreen) {
        var vm = this;

        vm.infoScreen = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.infoScreen.id !== null) {
                InfoScreen.update(vm.infoScreen, onSaveSuccess, onSaveError);
            } else {
                InfoScreen.save(vm.infoScreen, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('afripointApp:infoScreenUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.modifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
