(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('AuthorityDialogController', AuthorityDialogController);

    AuthorityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Authority'];

    function AuthorityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Authority) {
        var vm = this;

        vm.authority = entity;
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
            if (vm.authority.id !== null) {
                Authority.update(vm.authority, onSaveSuccess, onSaveError);
            } else {
                Authority.save(vm.authority, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('afripointApp:authorityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
