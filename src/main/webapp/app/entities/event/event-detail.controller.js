(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('EventDetailController', EventDetailController);

    EventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Event'];

    function EventDetailController($scope, $rootScope, $stateParams, previousState, entity, Event) {
        var evm = this;
        evm.event = entity;
        evm.previousState = previousState.name;
        var unsubscribe = $rootScope.$on('afripointApp:eventUpdate', function(event, result) {
            evm.event = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
