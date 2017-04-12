(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('AuthorityDetailController', AuthorityDetailController);

    AuthorityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Authority'];

    function AuthorityDetailController($scope, $rootScope, $stateParams, previousState, entity, Authority) {
        var vm = this;

        vm.authority = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('afripointApp:authorityUpdate', function(event, result) {
            vm.authority = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
