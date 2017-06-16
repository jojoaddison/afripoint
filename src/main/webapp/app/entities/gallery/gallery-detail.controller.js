(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('GalleryDetailController', GalleryDetailController);

    GalleryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Gallery'];

    function GalleryDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Gallery) {
        var vm = this;
        vm.gallery = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.deleteAlbum = deleteAlbum;
        
        setAlbum();
    	
    	function setAlbum(){
    		vm.gallery.albumSize = vm.gallery.albums !== null? vm.gallery.albums.length : 0;
        	vm.gallery.albumLabel = vm.gallery.albumSize > 1? "albums" : "album";
        	if(vm.gallery.albumSize == 0) vm.gallery.albumLabel="noAlbum";
        	
    		if(vm.gallery.albums)
        	for(var i= 0; i < vm.gallery.albums.length; i++){
        		if(vm.gallery.albums[i].media){
        			vm.gallery.albums[i].mediaSize = vm.gallery.albums[i].media.length;
        			vm.gallery.albums[i].mediaLabel = vm.gallery.albums[i].mediaSize > 1? "medien": "media";
        			if(vm.gallery.albums[i].media.length == 0){
        				vm.gallery.albums[i].mediaLabel = "noMedia";
        			}
        		}
        	}
    	}
        
        var unsubscribe = $rootScope.$on('afripointApp:galleryUpdate', function(event, result) {
            vm.gallery = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
        function deleteAlbum(id){
        	angular.forEach(vm.gallery.albums, function(album, k){
    			if(album.id == id){
    				vm.gallery.albums.splice(k,1);
    			}
        	}
        	);
        	Gallery.update(vm.gallery, onDeleteSuccess, onDeleteFailed);
        }
        
        function onDeleteSuccess(result){
        	vm.gallery = result;
        	setAlbum();
        }
        function onDeleteFailed(err){
        		console.log(err);
        }
    }
})();
