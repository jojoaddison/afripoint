(function() {
	'use strict';

	angular
		.module('afripointApp')
		.controller('HomeController', HomeController);

	HomeController.$inject = [ '$scope', 'Principal', 'LoginService', '$state', '$timeout', 'Event', 'AfripointService','PageUtils' ];

	function HomeController($scope, Principal, LoginService, $state, $timeout, Event, AfripointService, PageUtils) {
		var vm = this;

		vm.account = null;
		vm.isAuthenticated = null;
		vm.login = LoginService.open;
		vm.register = register;
		vm.slideInterval = 10000;
		vm.noWrapSlides = false;
		vm.active = 0;
		vm.openEvent = PageUtils.openEvent;
		vm.openLearn = PageUtils.openLearn;
		vm.openPartner = PageUtils.openPartner;
		vm.mod = PageUtils.mod;
		vm.page = 0;
		vm.size = 4;

		$scope.$on('authenticationSuccess', function() {
			getAccount();
		});


		$timeout(function() {
			loadServices();
			getAccount();
			loadEvents();
		});

    function loadServices(){
        AfripointService.getAll({}, function(data){
            vm.services = data;
        });
    }

		function loadEvents() {
			Event.current({
				page: vm.page,
				size: vm.size,
				sort: ['startTime, asc']
			},
						function(data) {
						vm.events = data;
			});
		}

		function getAccount() {
			Principal.identity().then(function(account) {
				vm.account = account;
				vm.isAuthenticated = Principal.isAuthenticated;
			});
		}
		function register() {
			$state.go('register');
		}

		$scope.$watch("images", function(newValue, oldValue) {
			$timeout(function() {
				$('.popup-gallery').each(function() {
					console.log($(this));
					$(this).magnificPopup({
						delegate : '.portfolio-box',
						type : 'image',
						//type: $(this).el.attr('type'),
						gallery : {
							enabled : true
						},
						titleSrc : function(item) {
							return item.el.attr('title');
						}
					});
				});
			});
		});
	}
})();
