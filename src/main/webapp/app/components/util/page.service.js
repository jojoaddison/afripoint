(function() {
    'use strict';

    angular
        .module('afripointApp')
        .factory('PageUtils', PageUtils);

    	PageUtils.$inject = ['$window', '$sce', '$uibModal'];

    	function PageUtils($window, $sce, $uibModal){
    		
    		var service = {
				openEvent: openEvent,
				openLearn: openLearn,
				openPartner: openPartner,
	            openService: openService,
	            openPage: openPage,
	            openLocation: openLocation,
	            mod: mod
    		};

    		return service;

    		function mod(x, y){
    			return (x%y);
    		}

    		
    		function openLocation() {
    			$uibModal.open(
        				{
        					templateUrl : "app/home/location.html",
        					controller : ['$uibModalInstance', 'businesses',
    			    						function($uibModalInstance, businesses) {
    			    						var vm = this;
    				    						vm.services = businesses;
    			    							console.log(vm.services);
    				    						vm.close = function(){
    				    							$uibModalInstance.dismiss('cancel');
    				    						}
    			    						}
    		    						],
        					controllerAs : 'vm',
        					backdrop : 'static',
        					size : 'lg',
        					resolve : {
        						businesses: ['LocationItem', 
          						           function(LocationItem){
        											var result = LocationItem.query(
                  									 		{
                  	    					                page: 1,
                  	    					                size: 20,
                  	    					                sort: {
              	    	    					                	value: 'id,desc',
              	    	    					                    squash: true
                  	    					                	  }
                  		    					            }, function onSuccess(data) {
                  		    					                return data;
                  		    					            }, function onError(error) {
                  		    					                return error;
                  		    					            });
        											return result.$promise;
          									}    						           
          								],
        						translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
        							$translatePartialLoader.addPart('locationItem');
        							$translatePartialLoader.addPart('home');
        							$translatePartialLoader.addPart('global');
        							return $translate.refresh();
        						} ]
        					}
        				}
        			);
    		}
    		
    		function openPartner() {
    			$uibModal.open(
    				{
    					templateUrl : "app/home/partner.html",
    					controller : [ '$uibModalInstance',
    						function($uibModalInstance) {
    						var evm = this;
    						evm.close = function(){
    							$uibModalInstance.dismiss('cancel');
    						}
    					}
    					],
    					controllerAs : 'evm',
    					backdrop : 'static',
    					size : 'lg',
    					resolve : {
    						translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
    							$translatePartialLoader.addPart('partner');
    							$translatePartialLoader.addPart('home');
    							$translatePartialLoader.addPart('global');
    							return $translate.refresh();
    						} ]
    					}
    				}
    			);
    		}

    		function openLearn() {
    			$uibModal.open(
    				{
    					templateUrl : "app/home/learn.html",
    					controller : [ '$uibModalInstance',
    						function($uibModalInstance) {
    						var evm = this;
    						evm.close = function(){
    							$uibModalInstance.dismiss('cancel');
    						}
    					}
    					],
    					controllerAs : 'evm',
    					backdrop : 'static',
    					size : 'lg',
    					resolve : {
    						translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
    							$translatePartialLoader.addPart('home');
    							$translatePartialLoader.addPart('global');
    							return $translate.refresh();
    						} ]
    					}
    				}
    			);
    		}

    		function openEvent(event) {
    			$uibModal.open(
    				{
    					templateUrl : "app/entities/event/event-view.html",
    					controller : [ '$uibModalInstance', 'event',
    						function($uibModalInstance, event) {
    						var evm =  this;
    						evm.event = event;
    						evm.close = function(){
    							$uibModalInstance.dismiss('cancel');
    						}
    					}
    					],
    					controllerAs : 'evm',
    					backdrop : 'static',
    					size : 'lg',
    					resolve : {
    						translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
    							$translatePartialLoader.addPart('event');
    							$translatePartialLoader.addPart('global');
    							return $translate.refresh();
    						} ],
    						event : [ function() {
    							return event;
    						} ]
    					}
    				}
    			);
    		}

            function openService(service) {
                $uibModal.open(
                    {
                        templateUrl : "app/entities/afripoint-service/afripoint-service-view.html",
                        controller : [ '$uibModalInstance', 'service',
                            function($uibModalInstance, service) {
                                var evm =  this;
                                evm.service = service;
                                evm.close = function(){
                                    $uibModalInstance.dismiss('cancel');
                                }
                            }
                        ],
                        controllerAs : 'evm',
                        backdrop : 'static',
                        size : 'lg',
                        resolve : {
                            translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('afripointService');
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            } ],
                            event : [ function() {
                                return event;
                            } ]
                        }
                    }
                );
            }

            function openPage(page) {       	
                	$uibModal.open(
                		{
                        templateUrl : "app/home/document.html",
                        controller : [ '$uibModalInstance', 'page',
                            function($uibModalInstance, page) {
                                var dvm =  this;
                                dvm.src = page;
                                dvm.close = function(){
                                    $uibModalInstance.dismiss('cancel');
                                }
                            }
                        ],
                        controllerAs : 'dvm',
                        backdrop : 'static',
                        size : 'lg',
                        resolve : {
                            translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('home');
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            } ],
                            page : [ function() {
                                return page;
                            } ]
                        }
                    }
                );
            }    	
    }

})();
