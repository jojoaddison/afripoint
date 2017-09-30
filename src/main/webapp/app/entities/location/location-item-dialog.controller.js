(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('LocationItemDialogController', LocationItemDialogController);

<<<<<<< HEAD
    LocationItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LocationItem', 'DataUtils'];

    function LocationItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LocationItem, DataUtils) {
=======
    LocationItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LocationItem'];

    function LocationItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LocationItem) {
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
        var vm = this;

        vm.locationItem = entity;
        vm.clear = clear;
        vm.save = save;
<<<<<<< HEAD
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866

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

<<<<<<< HEAD
        vm.setBytes = function ($file, locationItem) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        locationItem.image = base64Data;
                        locationItem.contentType = $file.type;
                    });
                });
            }
        };
=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866

    }
})();
