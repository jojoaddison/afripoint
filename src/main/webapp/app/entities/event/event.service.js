(function() {
    'use strict';
    angular
        .module('afripointApp')
        .factory('Event', Event);

    Event.$inject = ['$resource', 'DateUtils'];

    function Event ($resource, DateUtils) {
        var resourceUrl =  'api/events/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'current': { 
            			url: 'api/events/current',
            			method: 'GET', 
            			isArray: true
            		},
            'history': { 
		    			url: 'api/events/history',
		    			method: 'GET', 
		    			isArray: true
    				},
            'documents': { 
		    			url: 'api/events/documents',
		    			method: 'GET', 
		    			isArray: true
    				},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.modifiedDate = DateUtils.convertDateTimeFromServer(data.modifiedDate);
                        data.startTime = DateUtils.convertDateTimeFromServer(data.startTime);
                        data.endTime = DateUtils.convertDateTimeFromServer(data.endTime);
                    }
                    return data;
                }
            },
            'upload': {
                method: 'POST',
                url: 'api/events/upload',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    return angular.toJson(copy);
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    return angular.toJson(copy);
                }
            }
        });
    }

    
    function EventService(StorageDB, StorageUtils, Event){
    	var es = this;
    	es.CURR_EVENTSDB = "current_eventsdb";
    	es.EVENTSDB = "eventsdb";
		var service = {
				
		};
		
		return service;		
		
		
    }
    

})();
