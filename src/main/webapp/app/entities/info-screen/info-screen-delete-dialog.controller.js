(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('InfoScreenDeleteController',InfoScreenDeleteController);

    InfoScreenDeleteController.$inject = ['$uibModalInstance', 'entity', 'InfoScreen'];

    function InfoScreenDeleteController($uibModalInstance, entity, InfoScreen) {
        var vm = this;

        vm.infoScreen = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InfoScreen.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
