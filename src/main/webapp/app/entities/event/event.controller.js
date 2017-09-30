(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('EventController', EventController);

    EventController.$inject = ['$scope','$timeout', '$state', 'Event', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'PageUtils', 'StorageDB', 'StorageUtils'];

    function EventController($scope,$timeout, $state, Event, ParseLinks, AlertService, paginationConstants, pagingParams, PageUtils, StorageDB, StorageUtils) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.openEvent = PageUtils.openEvent;
        vm.mod = PageUtils.mod;
		vm.openPage = PageUtils.openPage;
		vm.active = 0;
		vm.page = 0;
		vm.size = 2;
     	var EVENTSDB = "eventsdb";
     	vm.refresh = refresh;
     	vm.showAll = showAll;
     	vm.mode = "CURRENT";
     	vm.showHistory = showHistory;
     	vm.showCurrent = showCurrent;
		vm.currentEvents = "data/event/docs/afripoint-events.pdf";
     	
     	
     	$scope.$on("afripointApp:eventUpdate", function(evt, event){
     		console.log("afripointApp:eventUpdate");
     		console.log(evt);
     		console.log(event);
     		console.log("afripointApp:eventUpdate");
     		refresh();
     	});

     	refresh();
     	
     	
     	function showHistory(){
     		vm.mode = "ALL";
 			EVENTSDB = "eventsdb";
     		vm.events = [];
     		Event.history({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
     	}

     	function refresh(){
     		if(vm.mode === "ALL"){
     			EVENTSDB = "eventsdb";
     			StorageUtils.remove('lastCurrentEventUpdated');
     			StorageDB.clear(EVENTSDB, function(){
     				showAll();
     			});
     		}
     		if(vm.mode === "CURRENT"){
     			EVENTSDB = "current_eventsdb";
     			StorageUtils.remove('lastCurrentEventUpdated');
     			StorageDB.clear(EVENTSDB, function(){
     				showCurrent();
     			});
     		}
     	}

     	function showAll(){
     		vm.mode = "ALL";
 			EVENTSDB = "eventsdb";
     		vm.events = [];
     		Event.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
     	}

     	function showCurrent(){
     		vm.mode = "CURRENT";
 			EVENTSDB = "current_eventsdb";
 			vm.events = [];
        	StorageDB.getAll(EVENTSDB, function(events){
        		console.log(events);
        		if(events && events.length < 1) events = undefined;
        		setEvent(events);        		
        	});  
     	}

        
        function setEvent(events){
        	if(!events){
        		Event.current({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
    		}else{
    			console.log();
    			vm.events = events;
    		}
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
