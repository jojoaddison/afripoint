(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('AfripointServiceDetailController', AfripointServiceDetailController);

    AfripointServiceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'AfripointService'];

    function AfripointServiceDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, AfripointService) {
        var vm = this;

        vm.afripointService = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('afripointApp:afripointServiceUpdate', function(event, result) {
            vm.afripointService = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
