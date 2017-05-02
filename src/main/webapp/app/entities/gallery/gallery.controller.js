(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('GalleryController', GalleryController)
        .controller('GalleryHomeController', GalleryHomeController);

    GalleryController.$inject = ['DataUtils', 'Gallery', 'ParseLinks', 'AlertService', 'paginationConstants'];
    GalleryHomeController.$inject = ['Gallery', 'ParseLinks', 'paginationConstants'];
    
    function GalleryHomeController(Gallery, ParseLinks, paginationConstants) {

        var vm = this;
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.galleries = [];

        loadAll();

        function loadAll () {
            Gallery.query({
                page: vm.page,
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
                for (var i = 0; i < data.length; i++) {
                	var gallery = data[i];
                	gallery.albumSize = gallery.albums !== null? gallery.albums.length : 0;
                	gallery.albumLabel = gallery.albumSize > 1? "albums" : "album";
                	if(gallery.albumSize == 0) gallery.albumLabel="noAlbum";
                    vm.galleries.push(gallery);
                }
            }

            function onError(error) {
            	if(error && error.data && error.data.message){
                    console.log(error.data.message);
            	}
            }
        }

        function reset () {
            vm.page = 0;
            vm.galleries = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }

    function GalleryController(DataUtils, Gallery, ParseLinks, AlertService, paginationConstants) {

        var vm = this;

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

        loadAll();

        function loadAll () {
            Gallery.query({
                page: vm.page,
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
                for (var i = 0; i < data.length; i++) {
                	var gallery = data[i];
                	gallery.albumSize = gallery.albums !== null? gallery.albums.length : 0;
                	gallery.albumLabel = gallery.albumSize > 1? "albums" : "album";
                	if(gallery.albumSize == 0) gallery.albumLabel="noAlbum";
                    vm.galleries.push(gallery);
                }
            }

            function onError(error) {
            	if(error && error.data && error.data.message){
                    AlertService.error(error.data.message);
            	}
            }
        }

        function reset () {
            vm.page = 0;
            vm.galleries = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }

})();
