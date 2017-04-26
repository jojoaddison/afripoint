(function() {
    'use strict';

    angular
        .module('afripointApp')
        .directive('imageCropper', ImageCropper);

        function ImageCropper () {
            var directive = {
                restrict: 'A',
                scope: {
                    predicate: '=',
                    ascending: '=',
                    callback: '&'
                },
                controller: ImageCropperController,
                controllerAs: 'vm',
                bindToController: true
            };

            return directive;
        }

    ImageCropperController.$inject = ['$scope', '$element'];

    function ImageCropperController ($scope, $element) {
        var vm = this;
    }


})();
