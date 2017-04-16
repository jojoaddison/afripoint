(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('PageDetailController', PageDetailController);

    PageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Page'];

    function PageDetailController($scope, $rootScope, $stateParams, previousState, entity, Page) {
        var vm = this;

        vm.page = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('afripointApp:pageUpdate', function(event, result) {
            vm.page = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
