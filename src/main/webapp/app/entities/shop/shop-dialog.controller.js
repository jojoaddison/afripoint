(function() {
    'use strict';

    angular
        .module('afripointApp')
        .controller('ShopDialogController', ShopDialogController);

    ShopDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Shop'];

    function ShopDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Shop) {
        var vm = this;

        vm.shop = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.active = 'details';
        vm.switchPanel = switchPanel;
        vm.showCategory = showCategory;
        vm.addCategory = addCategory;
        vm.categoryShowing = false;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function showCategory(){
            vm.categoryShowing = !vm.categoryShowing;
        }

        function addCategory(){
            var id = vm.shop.categories.length + 1;
            var category = {
                "id": id,
                "name": vm.category
            }
            vm.shop.categories.push(category);
        }

        function switchPanel(panel){
            vm.active = panel;
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.shop.id !== null) {
                Shop.update(vm.shop, onSaveSuccess, onSaveError);
            } else {
                Shop.save(vm.shop, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('afripointApp:shopUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.modifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
