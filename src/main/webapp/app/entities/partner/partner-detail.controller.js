(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('PartnerDetailController', PartnerDetailController);

    PartnerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Partner'];

    function PartnerDetailController($scope, $rootScope, $stateParams, previousState, entity, Partner) {
        var vm = this;

        vm.partner = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('afripointApp:partnerUpdate', function(event, result) {
            vm.partner = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
