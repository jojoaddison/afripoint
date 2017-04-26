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
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'duScroll'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }
})();
