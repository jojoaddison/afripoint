(function() {
	'use strict';
	angular
		.module('afripointApp')
		.factory('Partner', Partner)
		.factory('Country', Country);

	Partner.$inject = [ '$resource' ];

	function Partner($resource) {
		var resourceUrl = 'api/partners/:id';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'get' : {
				method : 'GET',
				transformResponse : function(data) {
					if (data) {
						data = angular.fromJson(data);
					}
					return data;
				}
			},
			'update' : {
				method : 'PUT'
			},
			'getCountry' : {
				url : 'https://maps.googleapis.com/maps/api/geocode/json',
				method : 'GET'
			}
		});
	}

	Country.$inject = [ '$http' ];

	function Country($http) {
		var url = '//maps.googleapis.com/maps/api/geocode/json';
		return {
			'search' : function(val) {
				return $http.get(url, {
					params : {
						address : val,
						sensor : false
					}
				}).then(function(response) {
					return response.data.results.map(function(item) {
						return item.formatted_address;
					});
				});
			}
		}
	}
})();