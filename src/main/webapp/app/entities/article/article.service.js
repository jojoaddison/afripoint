(function() {
    'use strict';
    angular
        .module('afripointApp')
        .factory('Article', Article);

    Article.$inject = ['$resource'];

    function Article ($resource) {
        var resourceUrl =  'api/articles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }

    ArticleService.$inject = ['$http'];

    function ArticleService($http){
        return{
            getByPid: function(pid){
                var url = "api/articles/by-pid/" + pid;
                console.log(url);
                return $http.get(url);
            },
            getByType: function(type){
                var url = "api/articles/by-type/" + type;
                return $http.get(url);
            }
        }
    }
})();
