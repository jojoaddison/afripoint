(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('EventViewController', EventViewController);

    EventViewController.$inject = ['entity'];

    function EventViewController(entity) {
        var evm = this;
        evm.event = entity;
        evm.close = close;

        function close () {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();
