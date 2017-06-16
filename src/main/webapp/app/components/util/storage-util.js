(function() {
    'use strict';

    angular
        .module('afripointApp')
        .factory('StorageUtils', StorageUtils);

    StorageUtils.$inject = ['$window'];
    function StorageUtils ($window) {

        var service = {
            get: get,
            set: set,
            getObject: getObject,
            setObject: setObject,
            remove: remove,
            clear: clear
        };

        return service;
        
        function get(key, defaultValue){
        	return $window.localStorage[key] || defaultValue || false;
        }
        
        function set(key, value) {
            $window.localStorage[key] = value;
        }
        
        function setObject(key, value) {
            $window.localStorage[key] = JSON.stringify(value);
        }
        
        function getObject(key, defaultValue) {
            if($window.localStorage[key] != undefined){
                return JSON.parse($window.localStorage[key]);
            }else{
              return defaultValue || false;
            }
          }
        
        function remove(key){
            $window.localStorage.removeItem(key);
        }
        
        function clear(){
            $window.localStorage.clear();
        }
    }
})();