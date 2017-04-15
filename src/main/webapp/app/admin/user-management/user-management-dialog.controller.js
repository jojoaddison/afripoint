(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$rootScope', '$scope', '$timeout', '$stateParams', '$uibModalInstance', 'entity', 'User', 'JhiLanguageService', 'DataUtils', 'Authority', 'Principal'];

    function UserManagementDialogController ($rootScope, $scope, $timeout, $stateParams, $uibModalInstance, entity, User, JhiLanguageService, DataUtils, Authority, Principal) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;
        vm.setBytes = setBytes;
        vm.setAuthority = setAuthority;
        vm.unsetAuthority = unsetAuthority;
        
        $rootScope.$on('afripointApp:authorityUpdate', function(){
        	loadAuthorities();
        });

        $timeout(function(){
        	Principal.identity().then(function(account) {
                vm.account = account;
            });
        	loadAuthorities();
        	angular.element('.form-group:eq(1)>input').focus();
        });

        JhiLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });
        
        function loadAuthorities(){
        	Authority.query({}, function(data){
        		vm.authorities = data;
        		unsetAll();
        		setSelectedAuthorities();
        	});
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            vm.user.lastModifiedBy = vm.account.login;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
        
        function setAuthority(name){
        	vm.user.authorities.push(name);
        	for(var i=0; i < vm.authorities.length; i++){
        		if(vm.authorities[i].name == name){
        			vm.authorities[i].selected = true;
        		}
        	}
        }
        
        function unsetAuthority(name){
        	angular.forEach(vm.user.authorities, function(authority, index){
        		console.log("index: " + index + " - authority: " + authority);
        		if(authority == name){
        			vm.user.authorities.splice(index, 1);
        		}
        	});
        	for(var i=0; i < vm.authorities.length; i++){
        		if(vm.authorities[i].name == name){
        			vm.authorities[i].selected = false;
        		}
        	}
        }
        
        function authoritySelected(name){   
        	var selected = false;
        	for(var i=0; i < vm.authorities.length; i++){        		
        		if(vm.authorities[i].name == name){
        			if(vm.authorities[i].selected && vm.authorities[i].selected == true){
        				selected = true;
        			}
        		}
        	}
        	return selected;
        }
        
        function unsetAll(){
        	for(var i=0; i < vm.authorities.length; i++){
        		console.log(vm.authorities[i]);
        		vm.authorities[i].selected = false;
        	}
        }
        
        function setSelectedAuthorities(){
        	for(var k=0; k < vm.user.authorities.length; k++){
        		var name = vm.user.authorities[k];
        		for(var i=0; i < vm.authorities.length; i++){
            		if(vm.authorities[i].name == name){
            			vm.authorities[i].selected = true;
            		}
            	}
        	}
        }

        function setBytes ($file) {        	
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                    	vm.user.image = base64Data;
                    	vm.user.imageContentType = $file.type;
                    });
                });
            }
        }
    }
})();
