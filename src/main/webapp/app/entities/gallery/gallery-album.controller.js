(function() {
	'use strict';

	angular
		.module('afripointApp')
		.controller('GalleryAlbumController', GalleryAlbumController)
		.controller('GalleryAlbumViewController', GalleryAlbumViewController);

	GalleryAlbumController.$inject = [ '$timeout', '$scope', '$stateParams', 'DataUtils', 'entity', '$uibModalInstance', 'Album', 'Principal', 'Gallery', 'Media' ];
	GalleryAlbumViewController.$inject = [ '$timeout', '$scope', '$stateParams', 'DataUtils', 'entity', 'previousState', 'Album', 'Principal', 'Gallery' ];
	
	function GalleryAlbumController($timeout, $scope, $stateParams, DataUtils, entity, $uibModalInstance, Album, Principal, Gallery, Media) {
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
        vm.addMedia = addMedia;
        vm.removeMedia = removeMedia;		

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
					var albumId = $stateParams.albumId;
					vm.album = getAlbum(albumId);					
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
			
			if (vm.album.id !== null) {
				vm.album.modifiedDate = new Date();
				vm.album.modifiedBy = vm.account;
				console.log("saving album...");
				Album.update(vm.album, onAlbumSaveSuccess, onSaveError);
			} else {				
				vm.album.createdDate = new Date();
				vm.album.modifiedDate = new Date();
				vm.album.createdBy = vm.account;
				vm.album.modifiedBy = vm.account;
				Album.save(vm.album, onAlbumSaveSuccess, onSaveError);
			}
		}
		
		function onAlbumSaveSuccess(result){
			console.log("album saved.");
			console.log(result);

			if (vm.gallery.albums == null) {
				vm.gallery.albums = [];				
			}

			removeAlbum(result.id, vm.gallery.albums);			
			vm.gallery.albums.push(result);
			
			console.log("updating gallery.");
			console.log(vm.gallery);
			Gallery.update(vm.gallery, onGallerySaveSuccess, onSaveError);
		}

		function onGallerySaveSuccess(result) {
			//$scope.$emit('afripointApp:galleryUpdate', result);
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

		function addMedia ($files, media) {
			if (media == null)
				media = vm.album.media;
			for (var i = 0; i < $files.length; i++) {
				var file = $files[i];				
				
				if (file && file.$error === 'pattern') {
					return;
				}
				
				if (file) {
					DataUtils.base64File(file, function(file64) {						
						$scope.$apply(function() {
							var photo = getPhoto(file64);	
							console.log("----- <media> --------");
							console.log(photo);
							console.log("----- </media> --------");
							media.push(photo);
							console.log(media);
						});
					});
					
				}
			}

		}
		
		function removeMedia (id, media){
			var delMedia = null;
			for(var i =0; i< media.length; i++){
				var albumMedia = media[i];
				if(albumMedia && id == albumMedia.id){
					delMedia = copyMedia(albumMedia);
					media.splice(i,1);
				}
			}
			if(delMedia != null && delMedia.imageUrl){
				vm.state="removeMedia";
				console.log(delMedia);
				Album.deleteMedia(delMedia);
			}
		}
		
		function copyMedia(media){
			return {
				id: media.id,
				imageUrl: media.imageUrl
			};
		}
		
		function getPhoto(file){
			//console.log(file);
			var photo = {};
			photo.image = file.base64Data;
			photo.imageContentType = file.type;
			photo.caption = file.name;
			photo.fileName = file.name;
			photo.createdDate = new Date();
			photo.modifiedDate = new Date();
			photo.id = DataUtils.uuid();
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
		
		function removeAlbum(id, albums){
			for(var i=0; i<albums.length; i++){
				var album = albums[i];
				if(album && album.id == id){
					albums.splice(i, 1);
				}
			}
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