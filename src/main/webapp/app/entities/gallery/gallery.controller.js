(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('GalleryController', GalleryController);

    GalleryController.$inject = ['$timeout','DataUtils', 'Gallery', 'ParseLinks', 'AlertService', 'paginationConstants', 'StorageDB', 'StorageUtils'];
       

    function GalleryController($timeout, DataUtils, Gallery, ParseLinks, AlertService, paginationConstants, StorageDB, StorageUtils) {

        var vm = this;
        vm.preloaded = false;
        vm.galleries = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        var GALLERIESDB = "galleriesdb";
        var lastUpdatedGallery = "lastGalleryUpdated";
        
        loadAll();
        
        $timeout(function(){
        	reloadGalleries();
        }, 15000);

        function loadAll () {
        	StorageDB.getAll(GALLERIESDB, function(galleries){
            	if(!galleries || galleries.length == 0)galleries = undefined;
            	setGalleries(galleries);
        	});
        }
        
        function setGalleries(galleries){
        	if(!galleries){
        		loadGalleries();
        	}else{
        		vm.galleries = galleries;
        	}
        }

        function reloadGalleries(){
        	var now = new Date().getTime();
        	var lastUpdated = StorageUtils.get(lastUpdatedGallery, false);
        	console.log("lastUpdated: " + lastUpdated);
        	if(!lastUpdated){
        		setGalleries(undefined);
        	}else{
        		var diff = now - lastUpdated;
    			diff = Math.floor(diff / (60*60*1000)); // milliseconds to minutes
        		if(diff > 30){ // diff older than 30mins
        			setGalleries(undefined);
    			}
        	} 
        }
        
    	function loadGalleries(){
    		console.log("reloading galleries");
    		Gallery.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onGalleriesSuccess, onGalleriesError);
    	}
        
        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }

        function onGalleriesSuccess(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
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
        
        function reset () {
            vm.page = 0;
            vm.galleries = [];
            StorageDB-clear(GALLERIESDB);
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }

})();
