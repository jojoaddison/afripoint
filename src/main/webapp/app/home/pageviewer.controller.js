(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('PageViewerController', PageViewerController);

    PageViewerController.$inject = ['$stateParams', '$uibModalInstance', 'AlertService', 'tmhDynamicLocale', '$sce', 'Article'];

    function PageViewerController($stateParams, $uibModalInstance, AlertService, tmhDynamicLocale, $sce, Article){
        var vm = this;
        vm.clear = clear;
        vm.page = vm.page | {};

        vm.pid = $stateParams.pid;

        loadPages();

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }


        function loadPages(){
            Article.query({}, onSuccess, onError);
        }

        function onSuccess(articles) {
            console.log("######### ARTICLES ######");
            console.log(articles);
            var lang = tmhDynamicLocale.get('NG_TRANSLATE_LANG_KEY');

            angular.forEach(articles, function(article){

                if(article.pid == vm.pid){
                    console.log("######### ARTICLE ######");
                    console.log(article);

                    angular.forEach(article.pages, function(page, index){
                        if(page !== null && page.lang == lang){
                            if(page.content != null){
                              page.content = $sce.trustAsHtml(page.content);
                            }
                            page.id = index;
                            vm.page = page;
                        }
                    });
                }

            });
            console.log("######### FINAL-PAGE ######");
            console.log(vm.page);
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }

    }

})();
