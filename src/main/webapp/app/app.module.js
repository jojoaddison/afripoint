(function() {
    'use strict';

    angular
        .module('afripointApp', [
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'ngAnimate',
            'ngTouch',
            'ui.tinymce',
            'infinite-scroll',
            '720kb.socialshare',
            'angular-loading-bar',
            'duScroll',
            'indexedDB',
            'ngCart'
            // jhipster-needle-angularjs-add-module JHipster will add new module here
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler', 'dataHandler'];

    function run(stateHandler, translationHandler, dataHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
        dataHandler.initialize();
    }
})();
