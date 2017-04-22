(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('EventDetailController', EventDetailController);

    EventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Event'];

    function EventDetailController($scope, $rootScope, $stateParams, previousState, entity, Event) {
        var vm = this;
        vm.event = entity;
        vm.previousState = previousState.name;
        var evm = vm;

        var unsubscribe = $rootScope.$on('afripointApp:eventUpdate', function(event, result) {
            vm.event = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
