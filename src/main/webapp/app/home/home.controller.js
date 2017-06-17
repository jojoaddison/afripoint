(function() {
	'use strict';

	angular
		.module('afripointApp')
		.controller('HomeController', HomeController);

	HomeController.$inject = [ '$scope', 'Socialshare', 'Principal', 'LoginService', '$state', '$timeout', 'Gallery', 'Event', 'AfripointService', 'PageUtils', 'StorageUtils', 'StorageDB' ];

	function HomeController($scope, Socialshare, Principal, LoginService, $state, $timeout, Gallery, Event, AfripointService, PageUtils, StorageUtils, StorageDB) {
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
		vm.openPage = PageUtils.openPage;
		vm.mod = PageUtils.mod;
		vm.page = 0;
		vm.size = 10;
		var EVENTSDB = "current_eventsdb";
		
        var GALLERIESDB = "galleriesdb";
        vm.predicate = 'id';
        var lastUpdatedGallery = "lastGalleryUpdated";
        
        vm.currentEvents = "content/docs/afripoint-events.pdf";
        
        vm.currentBrochure = "content/docs/afripoint_presentation.pdf";

		$scope.$on('authenticationSuccess', function() {
			getAccount();
		});

		getAccount();
		loadServices();
		
		$timeout(function(){
		loadEvents();
		loadGalleries();
		});
		
		$timeout(function() {
			reloadEvents();
		}, 5000);


		function loadEvents() {
			StorageDB.getAll(EVENTSDB, 
					function(data){
				console.log(EVENTSDB);
				console.log(data);
				if(data && data.length < 1) data = undefined;
				  setCurrentEvents(data);
				}
			);		
		}
		
		function setCurrentEvents(events){
			if(!events){
				Event.current({
					page: vm.page,
					size: vm.size,
					sort: ['startTime, asc']
				},
				function(data) {
				 console.log(data);		
				vm.events = data;
				if(data && data.length > 0){
					StorageDB.set(EVENTSDB, data, function(res){
						console.log(res);
					});
                	var now = new Date().getTime();
                    StorageUtils.set('lastCurrentEventUpdated', now);
				}
				});
			}else{						
				vm.events = events;
			}
		}
		
        function reloadEvents(){
        	var now = new Date().getTime();
        	console.log(now);
        	var lastUpdated = StorageUtils.get('lastCurrentEventUpdated', false);
        	if(!lastUpdated){
        		setCurrentEvents(undefined);
        	}else{
        		var diff = now - lastUpdated;
        		if(!diff){
        			setCurrentEvents(undefined);
        		}else{
        			diff = Math.floor(diff / (60*60*1000)); // milliseconds to minutes
            		console.log("diff-in-mins: " + diff);
        			if(diff > 30){
        				setCurrentEvents(undefined);
        			}
        		}
        	} 
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

    	function loadGalleries(){
    		StorageDB.getAll(GALLERIESDB, function(galleries){    			
            	if(!galleries || galleries.length == 0){
            		console.log("reloading galleries");
            		Gallery.query({
                        page: vm.page,
                        size: vm.itemsPerPage,
        				sort: ['modifiedDate, desc']
                    }, onGalleriesSuccess, onGalleriesError);
            	}
        	});
    		
    	}

        function onGalleriesSuccess(data) {
    		vm.galleries = [];
            for (var i = 0; i < data.length; i++) {
            	var gallery = data[i];
            	gallery.albumSize = gallery.albums !== null? gallery.albums.length : 0;
            	gallery.albumLabel = gallery.albumSize > 1? "albums" : "album";
            	if(gallery.albumSize == 0) gallery.albumLabel="noAlbum";
                vm.galleries.push(gallery);
            }
            StorageDB.set(GALLERIESDB, vm.galleries, function(res){
            	console.log(res);
            });
        	var now = new Date().getTime();
            StorageUtils.set(lastUpdatedGallery, now);
        }

        function onGalleriesError(error) {
        	if(error && error.data && error.data.message){
                AlertService.error(error.data.message);
        	}
        }

        function loadServices(){
				vm.services = [
					{
						"id": 1,
						"name": "location",
						"icon": "location-arrow"
					},
					{
						"id": 2,
						"name": "shop",
						"icon": "shopping-bag"
					},
					{
						"id": 3,
						"name": "advertise",
						"icon": "newspaper-o"
					},
					{
						"id": 4,
						"name": "events",
						"icon": "heart"						
					}
				];
				/*
		        AfripointService.getAll({}, function(data){
		            vm.services = data;
		        });
				*/
        }
        /**
        Socialshare.share(
        		{
        			'provider': 'facebook',
        			'attrs': {
        				'socialshareUrl': 'http://www.afripoint.at'
        			}
        		}
        		);
        **/
		$scope.$watch("images", function(newValue, oldValue) {
			$timeout(function() {
				$('.popup-gallery').each(function() {
					//console.log($(this));
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
