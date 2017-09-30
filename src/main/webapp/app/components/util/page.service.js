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
	            openService: openService,
	            openPage: openPage,
	            openLocation: openLocation,
<<<<<<< HEAD
	            checkBasket: checkBasket,
=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
	            mod: mod
    		};

    		return service;
    		
    		function checkBasket(){
    			console.log("Doing checkBasket...");
    		}

    		function mod(x, y){
    			return (x%y);
    		}
<<<<<<< HEAD
    		
    		function openLocation() {
=======

    		
    		function openLocation() {
    			$uibModal.open(
        				{
        					templateUrl : "app/home/location.html",
        					controller : 'LocationItemListController',
        					controllerAs : 'vm',
        					backdrop : 'static',
        					size : 'lg',
        		            params: {
        		                page: {
        		                    value: '1',
        		                    squash: true
        		                },
        		                sort: {
        		                    value: 'id,desc',
        		                    squash: true
        		                },
        		                search: null
        		            },
        					resolve : {
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
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
    			$uibModal.open(
        				{
        					templateUrl : 'app/home/shopservice.html',
        					controller : 'LocationItemListController',
        					controllerAs : 'vm',
        					backdrop : 'static',
        					size : 'lg',
        		            params: {
        		                page: {
        		                    value: '1',
        		                    squash: true
        		                },
        		                sort: {
        		                    value: 'id,desc',
        		                    squash: true
        		                },
        		                search: null
        		            },
        					resolve : {
        						translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
        							$translatePartialLoader.addPart('periods');
        							$translatePartialLoader.addPart('locationItem');
        							$translatePartialLoader.addPart('home');
        							$translatePartialLoader.addPart('global');
        							return $translate.refresh();
        						} ]
        					}
        				}
        			);
    		}
<<<<<<< HEAD
    		
=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866

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
                        templateUrl : "app/entities/afripoint/afripoint-view.html",
                        controller : [ '$uibModalInstance', 'service',
                            function($uibModalInstance, service) {
                                var evm =  this;
                                evm.service = service;
                                evm.service.header = "afripointApp.afripointService." + service.name + ".name";
                                evm.service.description = "afripointApp.afripointService." + service.name + ".description";
                                evm.service.url = "data/afripoint/" + service.name + ".pdf";
                                evm.close = function(){
                                    $uibModalInstance.dismiss('cancel');
                                }
                                evm.clear = function(){
                                    $uibModalInstance.dismiss('cancel');
                                }
                            }
                        ],
                        controllerAs : 'vm',
                        backdrop : 'static',
                        size : 'lg',
                        resolve : {
                            translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('afripointService');
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            } ],
                            service : [ function() {
                                return service;
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
<<<<<<< HEAD
    
            s
    	}
=======
    }
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866

})();
