(function() {
	'use strict';

	angular
		.module('afripointApp')
		.config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider

		.state('album-view', {
			parent : 'gallery-view',
			url : '/album/{albumId}',
			data : {
				authorities : [],
				pageTitle : 'afripointApp.gallery.detail.title'
			},
			views : {
				'content@' : {
					templateUrl : 'app/entities/album/album-view.html',
					controller : 'GalleryAlbumViewController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('gallery');
					$translatePartialLoader.addPart('album');
					return $translate.refresh();
				} ],
				entity : function (){return null;},
				previousState : [ "$state", function($state) {
					var currentStateData = {
						name : $state.current.name || 'gallery-view',
						params : $state.params,
						url : $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				} ]
			}
		})
		.state('gallery-album-view', {
			parent : 'gallery-detail',
			url : '/view/{albumId}',
			data : {
				authorities : [ 'ROLE_USER' ]
			},
			onEnter : [ '$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
				$uibModal.open({
					templateUrl : 'app/entities/album/album-detail.html',
					controller : 'GalleryAlbumController',
					controllerAs : 'vm',
					backdrop : 'static',
					size : 'lg',
					resolve : {
						entity : null,
						translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
							$translatePartialLoader.addPart('gallery');
							$translatePartialLoader.addPart('album');
							return $translate.refresh();
						} ]
					}
				}).result.then(function() {
					$state.go('gallery-detail', null, {
						reload : 'gallery-detail'
					});
				}, function() {
					$state.go('^');
				});
			} ]
		})
			.state('gallery-album-delete', {
				parent : 'gallery-detail',
				url : '/delete/{albumId}',
				data : {
					authorities : [ 'ROLE_USER' ]
				},
				onEnter : [ '$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl : 'app/entities/album/album-delete-dialog.html',
						controller : 'AlbumDeleteController',
						controllerAs : 'vm',
						size : 'md',
						resolve : {
							entity : [ 'Album', function(Album) {
								return Album.get({
									id : $stateParams.albumId
								}).$promise;
							} ]
						}
					}).result.then(function() {
						$state.go('gallery-detail', null, {
							reload : 'gallery-detail'
						});
					}, function() {
						$state.go('^');
					});
				} ]
			})
			.state('gallery-album-edit', {
				parent : 'gallery-detail',
				url : '/edit-album/{albumId}',
				data : {
					authorities : [ 'ROLE_USER' ]
				},
				onEnter : [ '$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl : 'app/entities/album/album-dialog.html',
						controller : 'GalleryAlbumController',
						controllerAs : 'vm',
						backdrop : 'static',
						size : 'lg',
						resolve : {
							entity : null,
							translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
								$translatePartialLoader.addPart('gallery');
								$translatePartialLoader.addPart('album');
								return $translate.refresh();
							} ]
						}
					}).result.then(function() {
						$state.go('gallery-detail', null, {
							reload : true
						});
					}, function() {
						$state.go('^');
					});
				} ]
			})
			.state('gallery-album-new', {
				parent : 'gallery-detail',
				url : '/album/new',
				data : {
					authorities : [ 'ROLE_USER' ]
				},
				onEnter : [ '$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl : 'app/entities/album/album-dialog.html',
						controller : 'GalleryAlbumController',
						controllerAs : 'vm',
						backdrop : 'static',
						size : 'lg',
						resolve : {
							entity : function() {
								return {
									name : null,
									description : null,
									createdBy : null,
									modifiedBy : null,
									createdDate : null,
									modifiedDate : null,
									media : [],
									photo : null,
									photoContentType : null,
									id : null
								};
							},
							gallery : [ '$stateParams', 'Gallery', function($stateParams, Gallery) {
								return Gallery.get({
									id : $stateParams.id
								}).$promise;
							} ],
							translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
								$translatePartialLoader.addPart('gallery');
								$translatePartialLoader.addPart('album');
								return $translate.refresh();
							} ]
						}
					}).result.then(function() {
						$state.go('^', null, {
							reload : true
						});
					}, function() {
						$state.go('^');
					});
				} ]
			})			
			.state('gallery-view', {
				parent : 'gallery',
				url : '/view/{id}',
				data : {
					authorities : [],
					pageTitle : 'afripointApp.gallery.detail.title'
				},
				views : {
					'content@' : {
						templateUrl : 'app/entities/gallery/gallery-view.html',
						controller : 'GalleryDetailController',
						controllerAs : 'vm'
					}
				},
				resolve : {
					translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('gallery');
						$translatePartialLoader.addPart('album');
						return $translate.refresh();
					} ],
					entity : [ '$stateParams', 'Gallery', function($stateParams, Gallery) {
						return Gallery.get({
							id : $stateParams.id
						}).$promise;
					} ],
					previousState : [ "$state", function($state) {
						var currentStateData = {
							name : $state.current.name || 'galleries',
							params : $state.params,
							url : $state.href($state.current.name, $state.params)
						};
						return currentStateData;
					} ]
				}
			})
			.state('gallery-detail', {
				parent : 'gallery',
				url : '/{id}',
				data : {
					authorities : [ 'ROLE_USER' ],
					pageTitle : 'afripointApp.gallery.detail.title'
				},
				views : {
					'content@' : {
						templateUrl : 'app/entities/gallery/gallery-detail.html',
						controller : 'GalleryDetailController',
						controllerAs : 'vm'
					}
				},
				resolve : {
					translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('gallery');
						$translatePartialLoader.addPart('album');
						return $translate.refresh();
					} ],
					entity : [ '$stateParams', 'Gallery', function($stateParams, Gallery) {
						return Gallery.get({
							id : $stateParams.id
						}).$promise;
					} ],
					previousState : [ "$state", function($state) {
						var currentStateData = {
							name : $state.current.name || 'gallery',
							params : $state.params,
							url : $state.href($state.current.name, $state.params)
						};
						return currentStateData;
					} ]
				}
			})
			.state('gallery-detail.edit', {
				parent : 'gallery-detail',
				url : '/edit/{id}',
				data : {
					authorities : [ 'ROLE_USER' ]
				},
				onEnter : [ '$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl : 'app/entities/gallery/gallery-dialog.html',
						controller : 'GalleryDialogController',
						controllerAs : 'vm',
						backdrop : 'static',
						size : 'md',
						resolve : {
							entity : [ 'Gallery', function(Gallery) {
								return Gallery.get({
									id : $stateParams.id
								}).$promise;
							} ]
						}
					}).result.then(function() {
						$state.go('^', {}, {
							reload : false
						});
					}, function() {
						$state.go('^');
					});
				} ]
			})
			.state('gallery.new', {
				parent : 'gallery',
				url : '/new',
				data : {
					authorities : [ 'ROLE_USER' ]
				},
				onEnter : [ '$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl : 'app/entities/gallery/gallery-dialog.html',
						controller : 'GalleryDialogController',
						controllerAs : 'vm',
						backdrop : 'static',
						size : 'md',
						resolve : {
							entity : function() {
								return {
									name : null,
									picture : null,
									pictureContentType : null,
									album : null,
									createdDate : null,
									modifiedDate : null,
									createdBy : null,
									modifiedBy : null,
									id : null
								};
							}
						}
					}).result.then(function() {
						$state.go('gallery', null, {
							reload : 'gallery'
						});
					}, function() {
						$state.go('gallery');
					});
				} ]
			})
			.state('gallery.edit', {
				parent : 'gallery',
				url : '/edit/{id}',
				data : {
					authorities : [ 'ROLE_USER' ]
				},
				onEnter : [ '$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl : 'app/entities/gallery/gallery-dialog.html',
						controller : 'GalleryDialogController',
						controllerAs : 'vm',
						backdrop : 'static',
						size : 'md',
						resolve : {
							entity : [ 'Gallery', function(Gallery) {
								return Gallery.get({
									id : $stateParams.id
								}).$promise;
							} ]
						}
					}).result.then(function() {
						$state.go('gallery', null, {
							reload : 'gallery'
						});
					}, function() {
						$state.go('^');
					});
				} ]
			})
			.state('gallery.delete', {
				parent : 'gallery',
				url : '/{id}/delete',
				data : {
					authorities : [ 'ROLE_USER' ]
				},
				onEnter : [ '$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl : 'app/entities/gallery/gallery-delete-dialog.html',
						controller : 'GalleryDeleteController',
						controllerAs : 'vm',
						size : 'md',
						resolve : {
							entity : [ 'Gallery', function(Gallery) {
								return Gallery.get({
									id : $stateParams.id
								}).$promise;
							} ]
						}
					}).result.then(function() {
						$state.go('gallery', null, {
							reload : 'gallery'
						});
					}, function() {
						$state.go('^');
					});
				} ]
			})
			.state('galleries', {
				parent : 'app',
				url : '/galleries',
				data : {
					authorities : [],
					pageTitle : 'afripointApp.gallery.home.title'
				},
				views : {
					'content@' : {
						templateUrl : 'app/entities/gallery/home.html',
						controller : 'GalleryController',
						controllerAs : 'vm'
					}
				},

				resolve : {
					translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('gallery');
						$translatePartialLoader.addPart('global');
						return $translate.refresh();
					} ]
				}
			})
			.state('gallery', {
				parent : 'entity',
				url : '/gallery',
				data : {
					authorities : [ 'ROLE_USER' ],
					pageTitle : 'afripointApp.gallery.home.title'
				},
				views : {
					'content@' : {
						templateUrl : 'app/entities/gallery/galleries.html',
						controller : 'GalleryController',
						controllerAs : 'vm'
					}
				},
				resolve : {
					translatePartialLoader : [ '$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('gallery');
						$translatePartialLoader.addPart('global');
						return $translate.refresh();
					} ]
				}
			});
	}

})();