(function() {
  'use strict';

  angular
  .module('afripointApp')
  .controller('PartnerDialogController', PartnerDialogController);

  PartnerDialogController.$inject = ['$rootScope', '$timeout', '$scope', '$stateParams', '$translate', '$uibModalInstance', 'entity', 'Partner', 'DataUtils', 'Principal'];

  function PartnerDialogController ($rootScope, $timeout, $scope, $stateParams, $translate, $uibModalInstance, entity, Partner, DataUtils, Principal) {
    var vm = this;

    vm.partner = entity;
    vm.clear = clear;
    vm.save = save;
    vm.showAddress = false;
    vm.toggleShowAddress = toggleShowAddress;
    vm.titles = [];
    vm.byteSize = DataUtils.byteSize;
    vm.setPhoto = setPhoto;
    vm.updatePhoto = updatePhoto;

    var titles =["mr","mrs","miss","dr","ms","ing","prof"];

    $scope.translateTitle = translateTitle;
    $timeout(function () {
      $('#image').cropper({
        aspectRatio: 16 / 9,
        crop: function(e) {
          // Output the result data for cropping image.
          /**
          console.log(e.x);
          console.log(e.y);
          console.log(e.width);
          console.log(e.height);
          console.log(e.rotate);
          console.log(e.scaleX);
          console.log(e.scaleY);
          **/
          console.log(e);
        }
      });
    }, 1000);



    function setPhoto(photo){
      vm.photoChanged = true;
      console.log("PhotoSet: " + vm.photoChanged);
      vm.photo = photo;
      //$scope.$apply();
    }

    function updatePhoto(){
      vm.photoChanged = true;
      console.log("PhotoUpdated: " + vm.photoChanged);
      //console.log(photo)
      var imageData = $().cropper('getImageData');
      vm.partner.image = imageData;
      loadPhoto();
    }

    function translateTitle(title){
      var key = "afripointApp.partner.titles."+title;
      $translate(key).then(function(value){
        return value;
      });
    }

    function toggleShowAddress(){
      vm.showAddress = !vm.showAddress;
    }

    function loadPhoto(){
      if(vm.partner.image != null){
        var photo =  'data:'+ vm.partner.imageContentType + ';base64,' + vm.partner.image;
        //vm.photo =  vm.partner.photo;
        setPhoto(photo);
      }
    }

    $timeout(function (){
      getAccount();
      //console.log(vm.partner);
      loadPhoto();
      angular.forEach(titles, function(title){
        var key = "afripointApp.partner.titles."+title;
        $translate(key).then(function(value){
          vm.titles.push(value);
        });
      });
      angular.element('.form-group:eq(1)>input').focus();
    });

    function getAccount() {
      Principal.identity().then(function(account) {
        vm.account = account;
        vm.isAuthenticated = Principal.isAuthenticated;
      });
    }
    function clear () {
      $uibModalInstance.dismiss('cancel');
    }

    function save () {
      vm.isSaving = true;
      console.log(vm.partner);
      if (vm.partner.id !== null) {
        vm.partner.modifiedBy = vm.account;
        vm.partner.modifiedDate = new Date();
        Partner.update(vm.partner, onSaveSuccess, onSaveError);
      } else {
        vm.partner.modifiedBy = vm.account;
        vm.partner.createdBy = vm.account;
        vm.partner.modifiedDate = new Date();
        vm.partner.createdDate = new Date();
        Partner.save(vm.partner, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess (result) {
      $scope.$emit('afripointApp:partnerUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError () {
      vm.isSaving = false;
    }

    vm.setBytes = function ($file) {
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
