(function() {
	'use strict';

	angular
		.module('afripointApp')
		.controller('AlbumDialogController', AlbumDialogController);

	AlbumDialogController.$inject = [ '$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Album', 'Principal', 'Gallery' ];
	
	function AlbumDialogController($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Album, Principal, Gallery) {
		var vm = this;
		vm.gallery = null;
		vm.album = entity;
		vm.clear = clear;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;
		vm.save = save;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
			Principal.identity().then(function(account) {
				vm.account = account;
			});
		});

		getGallery();

		function getGallery() {
			var id = $stateParams.id;
			console.log("GalleryID: " + id);
			Gallery.get({
				"id" : id
			}, function(gallery) {
				vm.gallery = gallery;
				if(entity==null){
					vm.album = getAlbum($stateParams.albumId);
				}
				console.log(gallery);
			}, function(err) {
				console.log("error: " + err)
			});
		}
		
		function getAlbum(id){
			var album;
			angular.forEach(vm.gallery.albums, function(alb){
				console.log(alb);
				if(alb.id == id){
					album = alb;
				}
			});
			return album;
		}

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			vm.isSaving = true;
			if (vm.album.id !== null) {
				vm.album.modifiedDate = new Date();
				vm.album.modifiedBy = vm.account;
				//Album.update(vm.album, onSaveSuccess, onSaveError);
			} else {
				vm.album.createdDate = new Date();
				vm.album.modifiedDate = new Date();
				vm.album.createdBy = vm.account;
				vm.album.modifiedBy = vm.account;
				//Album.save(vm.album, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess(result) {
			if (vm.gallery.albums == null) {
				vm.gallery.albums = [];
			}
			vm.gallery.albums.push(result);
			vm.album = result;
			Gallery.update(vm.gallery, onGallerySaveSuccess, onSaveError);
		}

		function onGallerySaveSuccess(result) {
			// $scope.$emit('afripointApp:albumUpdate', vm.album);
			// $scope.$emit('afripointApp:galleryUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		vm.datePickerOpenStatus.createdDate = false;
		vm.datePickerOpenStatus.modifiedDate = false;

		vm.setPhoto = function($file, album) {
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

		vm.addMedia = function($files, media) {
			if (media == null)
				media = vm.album.media;
			console.log(media);
			var num = 0;
			for (var i = 0; i < $files.length; i++) {
				var file = $files[i];
				
				if (file && file.$error === 'pattern') {
					return;
				}
				
				if (file) {
					DataUtils.base64File(file, function(file64) {
						var photo = getPhoto(file64);	
						$scope.$apply(function() {
							media.push(photo);
						});
					});
					
				}
			}

		};
		
		
		function getPhoto(file){
			var id =  DataUtils.uuid();
			var photo = {};
			photo.image = file.base64Data;
			photo.id = id;
			photo.imageContentType = file.type;
			photo.caption = file.name;
			photo.fileName = file.name;
			photo.createdDate = new Date();
			photo.modifiedDate = new Date();
			console.log(photo);
			return photo;
		}

		vm.removeMedia = function(id, media){
			for(var i =0; i< media.length; i++){
				var m = media[i];
				if(m && id == m.id){
					media.splice(i,1);
				}
			}
		}
		
		function exist(id, media){
			for(var i =0; i< media.length; i++){
				var m = media[i];
				if(m && id == m.id){
					return true;
				}
			}			
			return false;
		}
		
		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}

		function getRandomInt(min, max) {
			min = Math.ceil(min);
			max = Math.floor(max);
			return Math.floor(Math.random() * (max - min)) + min;
		}
	}
})();