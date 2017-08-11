(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('ShopDetailController', ShopDetailController);

    ShopDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Shop'];

    function ShopDetailController($scope, $rootScope, $stateParams, previousState, entity, Shop) {
        var vm = this;

        vm.shop = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('afripointApp:shopUpdate', function(event, result) {
            vm.shop = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
