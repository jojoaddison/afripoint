(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('LocationItemDeleteController',LocationItemDeleteController);

    LocationItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'LocationItem'];

    function LocationItemDeleteController($uibModalInstance, entity, LocationItem) {
        var vm = this;

        vm.locationOrder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LocationItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
