(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('InfoScreenDetailController', InfoScreenDetailController);

    InfoScreenDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InfoScreen'];

    function InfoScreenDetailController($scope, $rootScope, $stateParams, previousState, entity, InfoScreen) {
        var vm = this;

        vm.infoScreen = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('afripointApp:infoScreenUpdate', function(event, result) {
            vm.infoScreen = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
