(function() {
	'use strict';

	angular
		.module('afripointApp')
		.controller('BecomePartnerController', BecomePartnerController);

	BecomePartnerController.$inject = [ '$sce', '$rootScope', '$timeout', '$scope', '$stateParams', '$translate', '$uibModalInstance', 'entity', 'Partner', 'DataUtils', 'Principal', 'PartnerFactory' ];

	function BecomePartnerController($sce, $rootScope, $timeout, $scope, $stateParams, $translate, $uibModalInstance, entity, Partner, DataUtils, Principal, PartnerFactory) {
		var vm = this;
		vm.partner = entity;
		vm.clear = clear;
		vm.save = save;
		vm.showAddress = false;
		vm.toggleShowAddress = toggleShowAddress;
		vm.byteSize = DataUtils.byteSize;
		vm.setPhoto = setPhoto;
		vm.updatePhoto = updatePhoto;
		vm.showPage = 'infos';
		vm.switchTo = switchTo;
		vm.changeCatalog = changeCatalog;



		function changeCatalog() {
			var type = vm.partner.type;
			if (type.key) {
				type = type.key;
			}
			console.log(type);
			if (type) {
				vm.currentCatalog = PartnerFactory.getCatalog(type);
				console.log(vm.currentCatalog)
			}
		}



		loadLists();

		function loadLists() {
			vm.titles = PartnerFactory.getTitles();
			vm.types = PartnerFactory.getTypes();
			vm.states = PartnerFactory.getStates();
		}

		$timeout(function() {
			changeCatalog();
		}, 2000);

		function switchTo(page) {
			vm.showPage = page;
		}


		function setPhoto(photo) {
			vm.photoChanged = true;
			console.log("PhotoSet: " + vm.photoChanged);
			vm.photo = photo;
		//$scope.$apply();
		}

		function updatePhoto() {
			vm.photoChanged = true;
			console.log("PhotoUpdated: " + vm.photoChanged);
			//console.log(photo)
			var imageData = $().cropper('getImageData');
			vm.partner.image = imageData;
			loadPhoto();
		}


		function toggleShowAddress() {
			vm.showAddress = !vm.showAddress;
		}

		function loadPhoto() {
			if (vm.partner.image != null) {
				var photo = 'data:' + vm.partner.imageContentType + ';base64,' + vm.partner.image;
				//vm.photo =  vm.partner.photo;
				setPhoto(photo);
			}
		}

		$timeout(function() {
			getAccount();
			//console.log(vm.partner);
			loadPhoto();
			angular.element('.form-group:eq(1)>input').focus();
		});

		function getAccount() {
			Principal.identity().then(function(account) {
				vm.account = account;
				vm.isAuthenticated = Principal.isAuthenticated;
			});
		}
		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			if (vm.showPage == 'register') {
				vm.isSaving = true;
				console.log(vm.partner);
				vm.partner.type = vm.partner.type.key;
				if (vm.partner.id !== null) {
					console.log("updating...");
					vm.partner.modifiedBy = vm.account;
					vm.partner.modifiedDate = new Date();
					Partner.update(vm.partner, onSaveSuccess, onSaveError);
				} else {
					console.log("saving...");
					vm.partner.modifiedBy = vm.account;
					vm.partner.createdBy = vm.account;
					vm.partner.modifiedDate = new Date();
					vm.partner.createdDate = new Date();
					Partner.save(vm.partner, onSaveSuccess, onSaveError);
				}
			}
		}

		function onSaveSuccess(result) {
			console.log("success...");
			$scope.$emit('afripointApp:partnerUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
			console.log(result);
			PartnerFactory.partnerRegistered(result);
		}

		function onSaveError() {
			console.log("failure...")
			vm.isSaving = false;
		}

		vm.setBytes = function($file) {
			if ($file && $file.$error === 'pattern') {
				return;
			}
			if ($file) {
				DataUtils.toBase64($file, function(base64Data) {
					$scope.$apply(function() {
						vm.partner.image = base64Data;
						vm.partner.imageContentType = $file.type;
						loadPhoto();
					});
				});
			}
		};

	}
})();