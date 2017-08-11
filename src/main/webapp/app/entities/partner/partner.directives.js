(function() {
  'use strict';

  angular
  .module('afripointApp')
    .directive('becomePartner', function(){
        return {
            restrict : 'EA',
            controller : 'BecomePartnerController',
            controllerAs : 'evm',
            scope: {
            },
            transclude: true,
            templateUrl: 'app/entities/partner/become-a-partner.html',
            link: function($scope, element, attr){
            	
            },
			resolve : {
				entity: function () {
                    return {
                        firstname: null,
                        lastname: null,
                        title: null,
                        email: null,
                        type: null,
                        image: null,
                        mobileNumber: null,
                        telephoneNumber: null,
                        streetAddress: null,
                        zipcode: null,
                        city: "Vienna",
                        state: "Vienna",
                        country: "Austria",
                        region: "Western",
                        continent: "Europe",
                        notes: null,
                        id: null
                    };
                },
				translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('partner');
					$translatePartialLoader.addPart('home');
					$translatePartialLoader.addPart('global');
					return $translate.refresh();
				} ]
			}
        };
    })
    .directive('becomePartnerInfo', function(){
        return {
            restrict : 'EA',
            controller : '',
            scope: {
            },
            transclude: true,
            templateUrl: 'app/entities/partner/become-a-partner-information.html',
            link: function($scope, element, attr){
            }
        };
    });

})();