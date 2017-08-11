(function() {
	'use strict';

	angular
		.module('afripointApp')
		.controller('HomeController', HomeController);

	HomeController.$inject = [ '$scope', 'Socialshare', 'Principal', 'LoginService', '$state', '$timeout', 'LocationItem', 'Gallery', 'Event', 'AfripointService', 'PageUtils', 'StorageUtils', 'StorageDB', 'PartnerFactory', '$sce' ];

	function HomeController($scope, Socialshare, Principal, LoginService, $state, $timeout, LocationItem, Gallery, Event, AfripointService, PageUtils, StorageUtils, StorageDB, PartnerFactory, $sce) {
		var vm = this;

		vm.account = null;
		vm.isAuthenticated = null;
		vm.login = LoginService.open;
		vm.register = register;
		vm.slideInterval = 10000;
		vm.videoInterval = 300000;
		vm.noWrapSlides = false;
		vm.active = 0;
		vm.openEvent = PageUtils.openEvent;
		vm.openLearn = PageUtils.openLearn;
		vm.openPartner = PartnerFactory.openPartner;
		vm.openPage = PageUtils.openPage;
		vm.openLocation = PageUtils.openLocation;
		vm.mod = PageUtils.mod;
		vm.openService = PageUtils.openService;
		vm.page = 0;
		vm.size = 10;
		var EVENTSDB = "current_eventsdb";
		vm.service = null;
		vm.setService = setService;
		vm.cookiesAccepted = true;
		vm.acceptCookies = acceptCookies; 
		
        var GALLERIESDB = "galleriesdb";
        vm.predicate = 'id';
        var lastUpdatedGallery = "lastGalleryUpdated";
        
        var servicesGallery = null;
        
        vm.currentEvents = "data/event/docs/afripoint-events.pdf";
        
        vm.currentBrochure = "content/docs/afripoint_presentation.pdf";

		$scope.$on('authenticationSuccess', function() {
			getAccount();
		});
	

		getAccount();
		loadServices();
		
		$timeout(function(){
		loadEvents();
		loadGalleries();
		checkCookies();
		});
		
		$timeout(function() {
			reloadEvents();
		}, 5000);

		
		function setService(service){
			vm.service = service;
		}

		
		function loadEvents() {
			setCurrentEvents(undefined);
			/*
			StorageDB.getAll(EVENTSDB, 
					function(data){
				if(data && data.length < 1) data = undefined;
				  setCurrentEvents(data);
				}
			);		
			*/
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
        
        function checkCookies(){
        	var cookiesAccepted = StorageUtils.get('cookiesAccepted', false);
        	if(cookiesAccepted){
        		vm.cookiesAccepted = true;
        	}else{
        		vm.cookiesAccepted = false;
        	}
        }
        
        function acceptCookies(){
        	StorageUtils.set('cookiesAccepted', true);
        	vm.cookiesAccepted = true;
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
                if(gallery.name == 'services'){
                	servicesGallery = gallery;
                }
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

        function setServiceGallery(){
        	if(servicesGallery){
        		var album = servicesGallery; //TODO: get the first album and the first media
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
				vm.service =  vm.services[0];
				vm.screens = [
		        	                   {
		        	                	   "id": 1,
		        	                	   "src": $sce.trustAs($sce.RESOURCE_URL, "https://www.youtube.com/embed/pRjqZTmxdxo?ecver=2&autoplay=1&rel=0&controls=0")
		        	                   },
		        	                   {
		        	                	   "id": 2,
		        	                	   "src": $sce.trustAs($sce.RESOURCE_URL, "https://www.youtube.com/embed/-h1h1koEDp0?ecver=2&autoplay=1&rel=0&controls=0")
		        	                   },
		        	                   {
		        	                	   "id": 3,
		        	                	   "src": $sce.trustAs($sce.RESOURCE_URL, "https://www.youtube.com/embed/UGBcYnxRKPs?ecver=2&autoplay=1&rel=0&controls=0")
		        	                   },
		        	                   {
		        	                	   "id": 4,
		        	                	   "src": $sce.trustAs($sce.RESOURCE_URL, "https://www.youtube.com/embed/6jOvHRnZBD4?ecver=2&autoplay=1&rel=0&controls=0")
		        	                   },
		        	                   {
		        	                	   "id": 5,
		        	                	   "src": $sce.trustAs($sce.RESOURCE_URL, "https://www.youtube.com/embed/fSPy4vVv33Q?ecver=2&autoplay=1&rel=0&controls=0")
		        	                   },
		        	                   {
		        	                	   "id": 6,
		        	                	   "src": $sce.trustAs($sce.RESOURCE_URL, "https://www.youtube.com/embed/OAarK0qHVdw?ecver=2&autoplay=1&rel=0&controls=0")
		        	                   }
		        	];
		        	vm.offers = [
		          	                   {
		          	                	   "id": 1,
		          	                	   "src": "data/afripoint/offers/1_offers.jpg"
		          	                   },
		          	                   {
		          	                	   "id": 2,
		          	                	   "src": "data/afripoint/offers/2_offers.jpg"
		          	                   },
		          	                   {
		          	                	   "id": 3,
		          	                	   "src": "data/afripoint/offers/3_offers.jpg"
		          	                   },
		          	                   {
		          	                	   "id": 4,
		          	                	   "src": "data/afripoint/offers/4_offers.jpg"
		          	                   },
		          	                   {
		          	                	   "id": 5,
		          	                	   "src": "data/afripoint/offers/5_offers.jpg"
		          	                   },
		          	                   {
		          	                	   "id": 6,
		          	                	   "src": "data/afripoint/offers/6_offers.jpg"
		          	                   },
		          	                   {
		          	                	   "id": 7,
		          	                	   "src": "data/afripoint/offers/7_offers.jpg"
		          	                   }
		          	];
		        	
		        	LocationItem.query({}, onSuccess, onError); 
		            function onSuccess(data) {
		            	console.log(data);
		                vm.serviceOffers = data;
		            }            
		            function onError(error) {
		                AlertService.error(error.data.message);
		            }
		        	
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
