(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('ArticleDetailController', ArticleDetailController);

    ArticleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Article'];

    function ArticleDetailController($scope, $rootScope, $stateParams, entity, Article) {
        var vm = this;

        vm.article = entity;

        var unsubscribe = $rootScope.$on('afripointApp:articleUpdate', function(event, result) {
            vm.article = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
