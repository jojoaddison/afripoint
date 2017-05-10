(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('SubscriberDetailController', SubscriberDetailController);

    SubscriberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Subscriber'];

    function SubscriberDetailController($scope, $rootScope, $stateParams, previousState, entity, Subscriber) {
        var vm = this;

        vm.subscriber = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('afripointApp:subscriberUpdate', function(event, result) {
            vm.subscriber = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
