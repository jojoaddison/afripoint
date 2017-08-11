(function() {
	'use strict';

	angular
		.module('afripointApp')
		.factory('PartnerFactory', PartnerFactory);

	PartnerFactory.$inject = [ '$window', '$sce', '$uibModal', '$translate' ];

	function PartnerFactory($window, $sce, $uibModal, $translate) {
		var service = {
			openPartner : openPartner,
			partnerRegistered : partnerRegistered,
			getTitles : getTitles,
			getTypes : getTypes,
			getCatalog : getCatalog,
			getPaymentOptions : getPaymentOptions,
			getStates : getStates
		};
		return service;

		function openPartner() {
			$uibModal.open(
				{
					templateUrl : "app/entities/partner/partner.html",
					controller : 'BecomePartnerController',
					controllerAs : 'evm',
					backdrop : 'static',
					size : 'lg',
					resolve : {
						entity : function() {
							return {
								firstname : null,
								lastname : null,
								title : null,
								email : null,
								type : {
									key : "classic",
									value : "classic"
								},
								image : null,
								mobileNumber : null,
								telephoneNumber : null,
								streetAddress : null,
								zipcode : null,
								city : "Wien",
								state : "Wien",
								country : "Österreich",
								region : "Western",
								continent : "Europe",
								notes : null,
								id : null
							};
						},
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
		
		function partnerRegistered(partner) {
			console.log(partner);
			$uibModal.open(
				{
					templateUrl : "app/entities/partner/thank-you.html",
					controller : [ '$uibModalInstance', 'partner',
						function($uibModalInstance, partner) {
							var vm = this;
							vm.partner = partner;
							vm.close = function() {
								$uibModalInstance.dismiss('cancel');
							}
						}
					],
					controllerAs : 'vm',
					backdrop : 'static',
					size : 'lg',
					resolve : {
						translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
							$translatePartialLoader.addPart('partner');
							$translatePartialLoader.addPart('global');
							return $translate.refresh();
						} ],
						partner : [ function() {
							return partner;
						} ]
					}
				}
			);
		}

		function getTitles() {
			var data = [];
			var titles = [ "mr", "mrs", "miss", "dr", "ms", "ing", "prof" ];
			angular.forEach(titles, function(title) {
				var key = "afripointApp.partner.titles." + title;
				$translate(key).then(function(value) {
					var start = key.lastIndexOf(".") + 1;
					var titleKey = key.substring(start);
					data.push({
						"key" : titleKey,
						"value" : value
					});
				});
			});
			return data;
		}

		function getTypes() {
			var data = [];
			var types = [ "classic", "gold", "platinum" ];
			angular.forEach(types, function(type){
				var key = "afripointApp.partner.types." + type;
				$translate(key).then(function(value) {
					var start = key.lastIndexOf(".") + 1;
					var typeKey = key.substring(start);
					console.log("key: " + key);
					console.log("start: " + start);
					console.log("typeKey: " + typeKey);
					data.push({
						"key" : typeKey,
						"value" : value
					});
				});
			});
			return data;
		}

		function getCatalog(type) {
			var catalogs = [
				{
					"type" : "classic",
					"price" : "100",
					"info" : $sce.trustAsHtml("<ul>\n " +
						"<li>Vierteljährliche Zusendung der Afrika Magazine</li>\n" +
						"<li>20 % Ermäßigung bei AfriPoint & Radio Afrika TV Veranstaltungen</li>\n" +
						"<li>20 % Ermäßigung auf AfriPoint & Radio Afrika TV Serviceleistungen (wie z.B. Saalmiete," +
						"Werbung auf Infoscreen, Dokumentationen/Reportagen, Veranstaltungen,Videodokumentation aller Events etc.)</li>\n" +
						"</ul>\n")
				},
				{
					"type" : "gold",
					"price" : "250",
					"info" : $sce.trustAsHtml("<ul>\n " +
						"<li>Vierteljährliche Zusendung der Afrika Magazine</li>\n" +
						"<li>20 % Ermäßigung bei AfriPoint & Radio Afrika TV Veranstaltungen</li>\n" +
						"<li>20 % Ermäßigung auf AfriPoint & Radio Afrika TV Serviceleistungen (wie z.B. Saalmiete," +
						"Werbung auf Infoscreen, Dokumentationen/Reportagen, Veranstaltungen,Videodokumentation aller Events etc.)</li>\n" +
						"<li><strong>Die zusätzliche Nutzung unserer Räumlichkeiten für einen Abend</strong></li>\n" +
						"<li><strong>Eine kostenfreie bis zu dreistündige Videodokumentation Ihrer Lieblingsveranstaltung</strong></li>\n" +
						"</ul>\n")
				},
				{
					"type" : "platinum",
					"price" : "1000",
					"info" : $sce.trustAsHtml("<ul>\n " +
						"<li>Vierteljährliche Zusendung der Afrika Magazine</li>\n" +
						"<li>20 % Ermäßigung bei AfriPoint & Radio Afrika TV Veranstaltungen</li>\n" +
						"<li>20 % Ermäßigung auf AfriPoint & Radio Afrika TV Serviceleistungen (wie z.B. Saalmiete," +
						"Werbung auf Infoscreen, Dokumentationen/Reportagen, Veranstaltungen,Videodokumentation aller Events etc.)</li>\n" +
						"<li>Die zusätzliche Nutzung unserer Räumlichkeiten für einen Abend</li>\n" +
						"<li>Eine kostenfreie bis zu dreistündige Videodokumentation Ihrer Lieblingsveranstaltung</li>\n" +
						"<li><strong>Kostenlosen Zugang zu all unseren Veranstaltungen im AfriPoint und Radio Afrika TV</strong></li>\n" +
						"</ul>\n")
				}
			];
			var catlog = {};
			for (var i = 0; i < catalogs.length; i++) {
				var cat = catalogs[i];
				if (cat.type == type) {
					catlog = cat;
				}
			}
			return catlog;
		}

		function getPaymentOptions(){
			var data = [];
			var options = [ "cash", "check", "bank", "paypal", "sofort" ]; //bar, erlagschein, bank-ueberweisung
			for(var option in options){
				var key = "ngcart.options." + options[option];
				$translate(key).then(function(value){
					data.push(value);
				});
			}
			return data;			
		}
		
		function getStates(){
			var data = [];
			var states = [ "Wien", "Niederösterreich", "Oberösterreich", "Burgenland", "Tirol", "Steiermark", "Vorarlberg", "Salzburg", "Kärnten" ];
			return states;
		}
	
	}

})();