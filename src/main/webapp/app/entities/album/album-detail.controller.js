(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('AlbumDetailController', AlbumDetailController);

    AlbumDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Album'];

    function AlbumDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Album) {
        var vm = this;

        vm.album = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('afripointApp:albumUpdate', function(event, result) {
            vm.album = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
