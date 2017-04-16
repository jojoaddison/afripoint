(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('AfripointServiceDialogController', AfripointServiceDialogController);

    AfripointServiceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'AfripointService', 'Principal', 'User'];

    function AfripointServiceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, AfripointService, Principal, User) {
        var vm = this;

        vm.afripointService = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.setIcon = setIcon;
        vm.icons = [
          "cutlery", "diamond", "star", "paper-plane", "shop", "newspaper-o", "newspaper-c", "gold", "heart"
        ];
        vm.contacts = [
          {"id": 1, "name": "Kojo Ampia"},
          {"id": 2, "name": " Alexis Neuberg"},
          {"id": 3, "name": "Anne Groepel"}
        ];
        vm.authority = "ROLE_WORKER";

        function setIcon(item){
          console.log(item);
          var iconField = angular.element("#icon_field");
          var currentIcon= "fa-"+vm.icon;
          var prevIcon = "fa-"+vm.afripointService.icon;
          vm.afripointService.icon = vm.icon;
          iconField.removeClass(prevIcon);
          iconField.addClass(currentIcon);
        }

        $timeout(function (){
            Principal.identity().then(function(account) {
                vm.account = account;
            });
            angular.element('.form-group:eq(1)>input').focus();
            User.byAuthority({
                authority: vm.authority
            }, function(data){
                if(data && data.length > 0){
                  vm.contacts = [];
                  angular.forEach(data, function(item, k){
                    var id = k +1;
                    var name = item.firstName + " " + item.lastName;
                    var contact = {
                      "id": id,
                      "name": name
                    };
                      vm.contacts.push(contact);
                  });
                }
            });
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            var account = "<" + vm.account.firstName + " " + vm.account.lastName + ">" + vm.account.email;
            vm.afripointService.modifiedBy = account;
            vm.afripointService.modifiedDate = new Date();
            if (vm.afripointService.id !== null) {
                AfripointService.update(vm.afripointService, onSaveSuccess, onSaveError);
            } else {
                vm.afripointService.createdDate = new Date();
                vm.afripointService.createdBy = account;
                AfripointService.save(vm.afripointService, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('afripointApp:afripointServiceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setPhoto = function ($file, afripointService) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        afripointService.photo = base64Data;
                        afripointService.photoContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.modifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
