(function() {
	'use strict';

	angular
		.module('afripointApp')
		.controller('HomeController', HomeController);

	HomeController.$inject = [ '$scope', 'Principal', 'LoginService', '$state', '$timeout', 'Event', 'Media', 'AfripointService','PageUtils' ];

	function HomeController($scope, Principal, LoginService, $state, $timeout, Event, Media, AfripointService, PageUtils) {
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
		vm.mod = mod;
		vm.page = 0;
		vm.size = 4;



		function mod(x, y){
			var r=(x%y);
			//console.log("mod: "+r);
			return r;
		}


		$scope.$on('authenticationSuccess', function() {
			getAccount();
		});


		$timeout(function() {
			getAccount();
			loadEvents();
			loadMedia();
            loadServices();
		});

        function loadServices(){
            AfripointService.getAll({}, function(data){
                vm.services = data;
            });
        }

		function loadMedia() {
			Media.query({}, function(data) {
				vm.media = data;
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
				$('.gallery').each(function() {
					$(this).magnificPopup({
						delegate : '.slide',
						type : 'slide',
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
