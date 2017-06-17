(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('EventDialogController', EventDialogController);

    EventDialogController.$inject = ['$rootScope', '$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Event', 'Partner', 'Principal', 'DataUtils'];

    function EventDialogController ($rootScope, $timeout, $scope, $stateParams, $uibModalInstance, entity, Event, Partner, Principal, DataUtils) {
        var vm = this;

        vm.event = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        $scope.timeFormat="EEEE, dd MMM, yyyy HH:mm";
        

        $timeout(function (){
            Principal.identity().then(function(account) {
                vm.account = account;
            });
            setDatepickerOptions();
            loadPartners();
            angular.element('.form-group:eq(1)>input').focus();
        });
        
        $rootScope.$on("afripointApp:partnerUpdate", function(){
        	loadPartners();
        });
        
        function loadPartners(){
            Partner.query({},function(partners){
            	vm.partners = partners;
            });
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.event.id !== null) {
            	vm.event.modifiedBy = vm.account;
                Event.update(vm.event, onSaveSuccess, onSaveError);
            } else {
            	vm.event.modifiedBy = vm.account;
            	vm.event.createdBy = vm.account;
                Event.save(vm.event, onSaveSuccess, onSaveError);
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

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.modifiedDate = false;
        vm.datePickerOpenStatus.startTime = false;
        vm.datePickerOpenStatus.endTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
            
        	
            if(date == 'endTime'){   
                var startTime = getNextDay(new Date());
            	if(vm.event.startTime) startTime = getNextDay(vm.event.startTime);         	
            	vm.event.endTime = startTime;
            	vm.endDatepickerOptions.initDate = vm.event.endTime !== null ? getDate(vm.event.endTime) : getToday();
            	vm.endDatepickerOptions.minDate = vm.event.startTime !== null ? getDate(vm.event.startTime) : getToday();
            }
            if(date == 'startTime'){                
            	vm.startDatepickerOptions.initDate = vm.event.startTime !== null ? getDate(vm.event.startTime) : null ;
            	vm.startDatepickerOptions.minDate = vm.event.startTime !== null ? getDate(vm.event.startTime) : getToday();
            	vm.startDatepickerOptions.maxDate = vm.event.endTime !== null ? getDate(vm.event.endTime) : null;
            }
        }

        function setDatepickerOptions(){
            var endTime = getDate(vm.event.endTime) ? null : new Date();
            var startTime = getDate(vm.event.startTime) ? null : new Date() ;
                    	
            vm.startDatepickerOptions = {
            		"maxDate": null,
            		"minDate": getToday()
            };
            vm.endDatepickerOptions = {
            		"minDate": getToday(),
            		"maxDate": null
            };
        }

        function getToday(){
        	var date = new Date();
        	return date;
        }
        function getNextDay(date){
        	var tom = new Date(date);
        	tom.setDate(tom.getDate()+1);
        	return tom;
        }
        function getDate(d){
        	return new Date(d);
        }

        vm.setBytes = function ($file) {        	
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                    	vm.event.image = base64Data;
                    	vm.event.imageContentType = $file.type;
                    });
                });
            }
        };
    }

})();
