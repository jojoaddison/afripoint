(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('EventController', EventController)
        .controller('EventComponent', EventComponent);

    EventController.$inject = ['$scope','$timeout', '$state', 'Event', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'PageUtils', 'StorageDB', 'StorageUtils'];

    EventComponent.$inject = ['$scope', '$timeout', '$state', 'Event', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'PageUtils', 'StorageDB', 'StorageUtils'];

    function EventComponent($scope, $timeout, $state, Event, ParseLinks, AlertService, paginationConstants, pagingParams, PageUtils, StorageDB, StorageUtils){
    	 var vm = this;
         vm.loadPage = loadPage;
         vm.predicate = pagingParams.predicate;
         vm.reverse = pagingParams.ascending;
         vm.transition = transition;
         vm.itemsPerPage = paginationConstants.itemsPerPage;
         vm.openEvent = PageUtils.openEvent;
         vm.mod = PageUtils.mod;
     		 vm.active = 0;
     		 vm.page = 0;
     		 vm.size = 2;
     	var EVENTSDB = "current_eventsdb";

     	$scope.$on("afripointApp:eventUpdate", function(evt, event){
     		console.log(event);
     		if(vm.events && vm.events.length > 0)vm.events.push(event);
     		else{
     			vm.events = [event];
     		}
     	});

         loadAll();

        function loadAll () {
        	StorageDB.getAll(EVENTSDB, function(events){
        		console.log(events);
        		if(events && events.length < 1) events = undefined;
        		setEvent(events);        		
        	});  

         	$timeout(function(){
         		reloadEvents();
         	}, 5000);

        }
        
        function reloadEvents(){
        	var now = new Date().getTime();
        	var lastUpdated = StorageUtils.get('lastCurrentEventUpdated', false);
        	console.log("lastUpdated: " + lastUpdated);
        	if(!lastUpdated){
        		setEvent(undefined);
        	}else{
            	console.log(now);
        		var diff = now - lastUpdated;
    			diff = Math.floor(diff / (60*60*1000)); // milliseconds to minutes
        		console.log("diff-in-mins: " + diff);
        		if(diff > 30){
        			console.log("loading at threshold crossed");
        			setEvent(undefined);
    			}
        	} 
        }
        
        function setEvent(events){
        	if(!events){
        		loadEvents();
    		}else{
    			vm.events = events;
    		}
        }
        
        function loadEvents(){
        	Event.current({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
        }

        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }
        function onSuccess(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.events = data;
            if(data & data.length > 0) {
                StorageDB.update(EVENTSDB, data, function(res){
                	console.log("events stored.");
                	console.log(res);
                	console.log("------");
                });
            	var now = new Date().getTime();
                StorageUtils.set('lastCurrentEventUpdated', now);
            }
        }
        function onError(error) {
            AlertService.error(error.data.message);
        }
        
        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }

    function EventController($scope,$timeout, $state, Event, ParseLinks, AlertService, paginationConstants, pagingParams, PageUtils, StorageDB, StorageUtils) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.openEvent = PageUtils.openEvent;
        vm.mod = PageUtils.mod;
		vm.active = 0;
		vm.page = 0;
		vm.size = 2;
     	var EVENTSDB = "eventsdb";
     	
     	$scope.$on("afripointApp:eventUpdate", function(evt, event){
     		console.log(event);
     		if(vm.events && vm.events.length > 0)vm.events.push(event);
     		else{
     			vm.events = [event];
     		}
     	});

        loadAll();

     	$timeout(function(){
     		reloadEvents();
     	}, 5000);

        function loadAll () {
        	StorageDB.getAll(EVENTSDB, function(events){
        		console.log(events);
        		if(events && events.length < 1) events = undefined;
        		setEvent(events);        		
        	});
        }
        

        function reloadEvents(){
        	var now = new Date().getTime();
        	console.log(now);
        	var lastUpdated = StorageUtils.get('lastAllEventsUpdated', false);
        	console.log("lastUpdated: " + lastUpdated);
        	if(!lastUpdated){
        		setEvent(undefined);
        	}else{
        		var diff = now - lastUpdated;
        		console.log("raw-diff: " + diff);
    			diff = Math.floor(diff / (60*60)); // milliseconds to minutes
        		console.log("diff-in-mins: " + diff);
        		if(!diff){
        			setEvent(undefined);
        		}
        		else if(diff > 30){
        			setEvent(undefined);
        		}
        	}
        }
        
        function setEvent(events){
        	if(!events){
        		Event.current({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
                function sort() {
                    var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                    if (vm.predicate !== 'id') {
                        result.push('id');
                    }
                    return result;
                }
                function onSuccess(data, headers) {
                    vm.links = ParseLinks.parse(headers('link'));
                    vm.totalItems = headers('X-Total-Count');
                    vm.queryCount = vm.totalItems;
                    vm.events = data;
                    vm.page = pagingParams.page;
                    StorageDB.update(EVENTSDB, data, function(res){
                    	console.log("events stored.");
                    	console.log(res);
                    	console.log("------");
                    });
                	var now = new Date().getTime();
                    StorageUtils.set('lastAllEventsUpdated', now);
                }
                function onError(error) {
                    AlertService.error(error.data.message);
                }
    		}else{
    			console.log();
    			vm.events = events;
    		}
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }

})();
