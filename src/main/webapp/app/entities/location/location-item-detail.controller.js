(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('LocationItemDetailController', LocationItemDetailController);

    LocationItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LocationItem'];

    function LocationItemDetailController($scope, $rootScope, $stateParams, previousState, entity, LocationItem) {
        var vm = this;

        vm.locationOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('afripointApp:locationOrderUpdate', function(event, result) {
            vm.locationOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
