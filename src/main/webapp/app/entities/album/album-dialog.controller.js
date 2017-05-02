(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('AlbumDialogController', AlbumDialogController);

    AlbumDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Album', 'Principal', 'Gallery'];

    function AlbumDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Album, Principal, Gallery) {
        var vm = this;
        vm.gallery = null;
        vm.album = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
            Principal.identity().then(function(account) {
				vm.account = account;
			});
        });
        
        getGallery();
        
        function getGallery(){
        	var id = $stateParams.id;
        	console.log("GalleryID: " + id);        	
        	Gallery.get({"id": id}, function(gallery){
        		vm.gallery = gallery;
        		console.log(gallery);
        	}, function(err){
        		console.log("error: " + err)
        	});
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.album.id !== null) {
            	vm.album.modifiedDate = new Date();
            	vm.album.modifiedBy = vm.account;            	
                Album.update(vm.album, onSaveSuccess, onSaveError);
            } else {
            	vm.album.createdDate = new Date();
            	vm.album.modifiedDate = new Date();
            	vm.album.createdBy = vm.account;
            	vm.album.modifiedBy = vm.account;
                Album.save(vm.album, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
        	if(vm.gallery.albums == null){
        		vm.gallery.albums = [];
        	}
        	vm.gallery.albums.push(result);
        	vm.album = result;
        	Gallery.update(vm.gallery, onGallerySaveSuccess, onSaveError);           
        }
        
        function onGallerySaveSuccess(result){
            $scope.$emit('afripointApp:albumUpdate', vm.album);
            $scope.$emit('afripointApp:galleryUpdate', result);
        	 $uibModalInstance.close(result);
             vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.modifiedDate = false;

        vm.setPhoto = function ($file, album) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        album.photo = base64Data;
                        album.photoContentType = $file.type;
                    });
                });
            }
        };

        vm.addMedia = function ($files, media) {
        	if(media == null) media = vm.album.media;
        	console.log(media);
        	for(var i= 0; i < $files.length; i++){
        		var file = $files[i];
        		if (file && file.$error === 'pattern') {
                    return;
                }
                if (file) {
                	var photo = {};
                	photo.id = getRandomInt(1111, 99999999) + file.lastModified;
                	photo.caption = file.name;
                	photo.name = file.name;
                	photo.createdDate = new Date();
                	photo.modifiedDate = new Date();                	
                    DataUtils.toBase64(file, function(base64Data) {
                        $scope.$apply(function() {
                            photo.bytes = base64Data;
                            photo.bytesContentType = file.type;
                            media.push(photo)
                        });
                    });
                }
        	}
            
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        function getRandomInt(min, max) {
        	  min = Math.ceil(min);
        	  max = Math.floor(max);
        	  return Math.floor(Math.random() * (max - min)) + min;
        	}
    }
})();
