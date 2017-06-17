(function() {
    'use strict';
    
    angular
        .module('afripointApp')
        .factory('StorageDB', StorageDB);
    
    StorageDB.$inject = ['$indexedDB'];
    
    function StorageDB ($indexedDB) {
        var service = {
            get: get,
            set: set,
            update: update,
            remove: remove,
            clear: clear,
            getAll: getAll,
            count: count
        };

        return service;
        
        function count(collectionName, cb){
        	var result = 0;
        	$indexedDB.openStore(collectionName, function(store){
        		console.log(store);
        		store.count().then(cb(value));
        	});
        }
        
        function get(collectionName, key, cb){        	
        	$indexedDB.openStore(collectionName, function(store){
        		console.log(store);
        		store.find(key).then(cb(value));
        	});
        }
        
        function set(collectionName, data, cb) {
        	$indexedDB.openStore(collectionName, function(store){
        		store.insert(data).then( function(value) {
        			cb(value);
        		} );
        	});
        }

        function update(collectionName, data, cb) {
        	$indexedDB.openStore(collectionName, function(store){
        		store.upsert(data).then( function(value) {
        			cb(value);
        		} );
        	});
        }
        
        function remove(collectionName, key, cb){
        	$indexedDB.openStore(collectionName, function(store){
        		if(key)store.delete(key).then(cb());
        	});
        }
        
        function clear(collectionName, cb){
        	$indexedDB.openStore(collectionName, function(store){
        		store.clear().then(cb());
        	});
        }
        
        function getAll(collectionName, cb){
        	console.log("get-all + " + collectionName);
        	$indexedDB.openStore(collectionName, function(store){
        		if(store.hasOwnProperty){
            		store.getAll().then( function(value){
            			cb(value);
            		} );
        		}
        	});
        }
    }
})();