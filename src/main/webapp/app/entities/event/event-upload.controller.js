(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('EventUploadController', EventUploadController);

    EventUploadController.$inject = ['$rootScope', '$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Event', 'Partner', 'Principal', 'DataUtils'];

    function EventUploadController ($rootScope, $timeout, $scope, $stateParams, $uibModalInstance, entity, Event, Partner, Principal, DataUtils) {
        var vm = this;

        vm.event = entity;
        vm.clear = clear;
        vm.save = save;
        $scope.timeFormat="EEEE, dd MMM, yyyy HH:mm";
        

        $timeout(function (){
            Principal.identity().then(function(account) {
                vm.account = account;
            });
            Event.documents({}, function(data){
            	vm.events = data;
            	console.log(data);
            }, function(err){});
            angular.element('.form-group:eq(1)>input').focus();
        });
        

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.event.id !== null) {
            	vm.event.modifiedBy = vm.account;
                Event.upload(vm.event, onSaveSuccess, onSaveError);
            } else {
            	vm.event.modifiedBy = vm.account;
            	vm.event.createdBy = vm.account;
                Event.upload(vm.event, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('afripointApp:eventUpdate', result);
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
                    	vm.event.document = base64Data;
                    	vm.event.contentType = $file.type;
                    	vm.event.name = $file.name;
                    });
                });
            }
        };
    }

})();
