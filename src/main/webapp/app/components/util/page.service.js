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
	            checkBasket: checkBasket,
	            mod: mod
    		};

    		return service;
    		
    		function checkBasket(){
    			console.log("Doing checkBasket...");
    		}

    		function mod(x, y){
    			return (x%y);
    		}

    		function openLocation() {
    			$uibModal.open(
        				{
        					templateUrl : "app/entities/location-items.html",
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
                                var vm =  this;
                                vm.service = service;
                                vm.service.header = "afripointApp.afripointService." + service.name + ".name";
                                vm.service.description = "afripointApp.afripointService." + service.name + ".description";
                                vm.service.url = "data/afripoint/" + service.name + ".pdf";
                                vm.close = function(){
                                    $uibModalInstance.dismiss('cancel');
                                };
                                vm.clear = function(){
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
    	}

})();
