(function() {
    'use strict';

    angular
        .module('afripointApp')
        .config(localStorageConfig)        
        .config(indexedDBConfig);

    localStorageConfig.$inject = ['$localStorageProvider', '$sessionStorageProvider'];    
    indexedDBConfig.$inject = ['$indexedDBProvider'];
    
    function indexedDBConfig ($indexedDBProvider) {
    	//console.log($indexedDBProvider);
    	var index = 0;
        $indexedDBProvider.connection('AFRIPOINT')
        .upgradeDatabase(++index, function(event, db, tx){
    		console.log("--create events--");
      		console.log(index);
    		console.log(event);
    		db.createObjectStore('eventsdb', {keyPath: 'id'});
    		db.createObjectStore('current_eventsdb', {keyPath: 'id'});
    		console.log("--created--");
          }).upgradeDatabase(++index, function(event, db, tx){
      		console.log("--create galleries--");
      		console.log(index);
      		console.log(event);
      		db.createObjectStore('galleriesdb', {keyPath: 'id'});
      		console.log("--created--");
            });
    }
    
    function localStorageConfig($localStorageProvider, $sessionStorageProvider) {
        $localStorageProvider.setKeyPrefix('jhi-');
        $sessionStorageProvider.setKeyPrefix('jhi-');
    }
})();
