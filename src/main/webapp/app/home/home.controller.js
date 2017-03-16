(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$timeout'];

    function HomeController ($scope, Principal, LoginService, $state, $timeout) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        vm.images = [
            {src: "http://placekitten.com/g/200/300, title: 'Cute kitten'"},
        {src: "http://placekitten.com/g/300/300, title: 'Cuter kitten'"},
        {src: "http://placekitten.com/g/400/400, title: 'Cutest kitten'"}
            ];
        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        $scope.$watch("images", function (newValue, oldValue) {
            $timeout(function() {
                $('.gallery').each(function() {
                    $(this).magnificPopup({
                        delegate: '.slide',
                        type:'slide',
                        gallery: {
                            enabled: true
                        },
                        titleSrc: function(item){
                            return item.el.attr('title');
                        }
                    });
                });
            });
        });
    }
})();
