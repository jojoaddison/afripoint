(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('AfripointServiceDeleteController',AfripointServiceDeleteController);

    AfripointServiceDeleteController.$inject = ['$uibModalInstance', 'entity', 'AfripointService'];

    function AfripointServiceDeleteController($uibModalInstance, entity, AfripointService) {
        var vm = this;

        vm.afripointService = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AfripointService.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
