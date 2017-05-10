(function() {
	'use strict';

	angular
		.module('afripointApp')
		.controller('GalleryAlbumController', GalleryAlbumController)
		.controller('GalleryAlbumViewController', GalleryAlbumViewController);

	GalleryAlbumController.$inject = [ '$timeout', '$scope', '$stateParams', 'DataUtils', 'entity', '$uibModalInstance', 'Album', 'Principal', 'Gallery' ];
	GalleryAlbumViewController.$inject = [ '$timeout', '$scope', '$stateParams', 'DataUtils', 'entity', 'previousState', 'Album', 'Principal', 'Gallery' ];
	
	function GalleryAlbumController($timeout, $scope, $stateParams, DataUtils, entity, $uibModalInstance, Album, Principal, Gallery) {
		var vm = this;
		vm.slideInterval = 10000;
		vm.noWrapSlides = false;
		vm.active = 0;
		vm.gallery = null;
		vm.album = entity;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;
		vm.save = save;
        vm.clear = clear;
		

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
					console.log("AlbumID: " + $stateParams.albumId);
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


		function save() {
			vm.isSaving = true;
			if (vm.gallery.albums == null) {
				vm.gallery.albums = [];
			}
			if (vm.album.id !== null) {
				vm.album.modifiedDate = new Date();
				vm.album.modifiedBy = vm.account;
				vm.gallery.albums.push(vm.album);
				Gallery.update(vm.gallery, onGallerySaveSuccess, onSaveError);
			} else {
				var id =  (getRandomInt(1111, 99999999)).toString();
				vm.album.id = id;
				vm.album.createdDate = new Date();
				vm.album.modifiedDate = new Date();
				vm.album.createdBy = vm.account;
				vm.album.modifiedBy = vm.account;
				vm.gallery.albums.push(vm.album);
				Gallery.update(vm.gallery, onGallerySaveSuccess, onSaveError);
			}
		}

		function onGallerySaveSuccess(result) {
			$scope.$emit('afripointApp:galleryUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

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
		vm.removeMedia = function(id, media){
			for(var i =0; i< media.length; i++){
				var m = media[i];
				if(m && id == m.id){
					media.splice(i,1);
				}
			}
		}
		
		function getPhoto(file){
			console.log(file);
			var id =  (getRandomInt(1111, 99999999)).toString();
			if(file.lastModified){
				id=file.lastModified;
			}		
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

		
		function exist(id, media){
			for(var i =0; i< media.length; i++){
				var m = media[i];
				if(m && id == m.id){
					return true;
				}
			}			
			return false;
		}
		

		function getRandomInt(min, max) {
			min = Math.ceil(min);
			max = Math.floor(max);
			return Math.floor(Math.random() * (max - min)) + min;
		}
		
		$scope.$watch("images", function(newValue, oldValue) {
			$timeout(function() {
				$('.popup-gallery').each(function() {
					console.log($(this));
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
	
	function GalleryAlbumViewController($timeout, $scope, $stateParams, DataUtils, entity, previousState, Album, Principal, Gallery) {
		var vm = this;
		vm.slideInterval = 10000;
		vm.noWrapSlides = false;
		vm.active = 0;
		vm.gallery = null;
		vm.album = entity;
        vm.previousState = previousState.name;

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
					console.log("AlbumID: " + $stateParams.albumId);
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
		
		$scope.$watch("images", function(newValue, oldValue) {
			$timeout(function() {
				$('.popup-gallery').each(function() {
					console.log($(this));
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