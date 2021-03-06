  (function() {
      'use strict';

      angular
          .module('afripointApp')
          .config(stateConfig);

      stateConfig.$inject = ['$stateProvider'];

      function stateConfig($stateProvider) {
          $stateProvider
              .state('event-partner', {
                  parent: 'event.new',
                  url: '/partner',
                  data: {
                      authorities: ['ROLE_USER']
                  },
                  onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                      $uibModal.open({
                          templateUrl: 'app/entities/partner/partner-dialog.html',
                          controller: 'PartnerDialogController',
                          controllerAs: 'vm',
                          backdrop: 'static',
                          size: 'lg',
                          resolve: {
                              translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                                  $translatePartialLoader.addPart('event');
                                  $translatePartialLoader.addPart('partner');
                                  $translatePartialLoader.addPart('global');
                                  return $translate.refresh();
                              }],
                              entity: function() {
                                  return {
                                      firstname: null,
                                      lastname: null,
                                      title: null,
                                      email: null,
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
                              }
                          }
                      }).result.then(function() {
                          $state.go('^');
                      }, function() {
                          $state.go('^');
                      });
                  }]
              })
              .state('events', {
                  parent: 'entity',
                  url: '/events?page&sort&search',
                  data: {
                      authorities: [],
                      pageTitle: 'afripointApp.event.home.title'
                  },
                  views: {
                      'header@': {
                          templateUrl: 'app/home/header.html',
                          controller: 'HomeController',
                          controllerAs: 'vm'
                      },
                      'content@': {
                          templateUrl: 'app/entities/event/event-page.html',
                          controller: 'EventPageController',
                          controllerAs: 'vm'
                      }
                  },
                  params: {
                      page: {
                          value: '1',
                          squash: true
                      },
                      sort: {
                          value: 'startTime,asc',
                          squash: true
                      },
                      search: null
                  },
                  resolve: {
                      pagingParams: ['$stateParams', 'PaginationUtil', function($stateParams, PaginationUtil) {
                          return {
                              page: PaginationUtil.parsePage($stateParams.page),
                              sort: $stateParams.sort,
                              predicate: PaginationUtil.parsePredicate($stateParams.sort),
                              ascending: PaginationUtil.parseAscending($stateParams.sort),
                              search: $stateParams.search
                          };
                      }],
                      translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                          $translatePartialLoader.addPart('event');
                          $translatePartialLoader.addPart('home');
                          $translatePartialLoader.addPart('global');
                          return $translate.refresh();
                      }]
                  }
              })
              .state('event-view', {
                  parent: 'events',
                  url: '/view/{id}',
                  data: {
                      authorities: [],
                      pageTitle: 'afripointApp.event.detail.title'
                  },
                  views: {
                      'content@': {
                          templateUrl: 'app/entities/event/event-view.html',
                          controller: 'EventViewController',
                          controllerAs: 'evm'
                      }
                  },
                  resolve: {
                      translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                          $translatePartialLoader.addPart('event');
                          $translatePartialLoader.addPart('global');
                          return $translate.refresh();
                      }],
                      entity: ['$stateParams', 'Event', function($stateParams, Event) {
                          return Event.get({
                              id: $stateParams.id
                          }).$promise;
                      }]
                  }
              })
              .state('events-management', {
                  parent: 'entity',
                  url: '/events-management?page&sort&search',
                  data: {
                      authorities: ['ROLE_USER'],
                      pageTitle: 'afripointApp.event.home.title'
                  },
                  views: {
                      'content@': {
                          templateUrl: 'app/entities/event/events.html',
                          controller: 'EventController',
                          controllerAs: 'vm'
                      }
                  },
                  params: {
                      page: {
                          value: '1',
                          squash: true
                      },
                      sort: {
                          value: 'endTime,asc',
                          squash: true
                      },
                      search: null
                  },
                  resolve: {
                      pagingParams: ['$stateParams', 'PaginationUtil', function($stateParams, PaginationUtil) {
                          return {
                              page: PaginationUtil.parsePage($stateParams.page),
                              sort: $stateParams.sort,
                              predicate: PaginationUtil.parsePredicate($stateParams.sort),
                              ascending: PaginationUtil.parseAscending($stateParams.sort),
                              search: $stateParams.search
                          };
                      }],
                      translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                          $translatePartialLoader.addPart('event');
                          $translatePartialLoader.addPart('home');
                          $translatePartialLoader.addPart('global');
                          return $translate.refresh();
                      }]
                  }
              })
              .state('events-management-detail', {
                  parent: 'events-management',
                  url: '/{id}',
                  data: {
                      authorities: ['ROLE_USER'],
                      pageTitle: 'afripointApp.event.detail.title'
                  },
                  views: {
                      'content@': {
                          templateUrl: 'app/entities/event/event-detail.html',
                          controller: 'EventDetailController',
                          controllerAs: 'vm'
                      }
                  },
                  resolve: {
                      translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                          $translatePartialLoader.addPart('event');
                          $translatePartialLoader.addPart('global');
                          return $translate.refresh();
                      }],
                      entity: ['$stateParams', 'Event', function($stateParams, Event) {
                          return Event.get({
                              id: $stateParams.id
                          }).$promise;
                      }],
                      previousState: ["$state", function($state) {
                          var currentStateData = {
                              name: $state.current.name || 'event',
                              params: $state.params,
                              url: $state.href($state.current.name, $state.params)
                          };
                          return currentStateData;
                      }]
                  }
              })
              .state('event-detail.edit', {
                  parent: 'event-detail',
                  url: '/detail/edit',
                  data: {
                      authorities: ['ROLE_USER']
                  },
                  onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                      $uibModal.open({
                          templateUrl: 'app/entities/event/event-dialog.html',
                          controller: 'EventDialogController',
                          controllerAs: 'vm',
                          backdrop: 'static',
                          size: 'lg',
                          resolve: {
                              entity: ['Event', function(Event) {
                                  return Event.get({
                                      id: $stateParams.id
                                  }).$promise;
                              }]
                          }
                      }).result.then(function() {
                          $state.go('^', {}, {
                              reload: false
                          });
                      }, function() {
                          $state.go('^');
                      });
                  }]
              })
              .state('events-management.upload', {
                  parent: 'events-management',
                  url: '/upload',
                  data: {
                      authorities: ['ROLE_USER']
                  },
                  onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                      $uibModal.open({
                          templateUrl: 'app/entities/event/event-upload.html',
                          controller: 'EventUploadController',
                          controllerAs: 'vm',
                          backdrop: 'static',
                          size: 'lg',
                          resolve: {
                              translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                                  $translatePartialLoader.addPart('media');
                                  return $translate.refresh();
                              }],
                              entity: function() {
                                  return {
                                      title: null,
                                      src: null,
                                      version: null,
                                      document: null,
                                      contentType: null,
                                      createdDate: null,
                                      modifiedDate: null,
                                      createdBy: null,
                                      modifiedBy: null,
                                      id: null
                                  };
                              }
                          }
                      }).result.then(function() {
                          $state.go('events-management', null, {
                              reload: 'events-management'
                          });
                      }, function() {
                          $state.go('events-management');
                      });
                  }]
              })
              .state('events-management.new', {
                  parent: 'events-management',
                  url: '/new',
                  data: {
                      authorities: ['ROLE_USER']
                  },
                  onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                      $uibModal.open({
                          templateUrl: 'app/entities/event/event-dialog.html',
                          controller: 'EventDialogController',
                          controllerAs: 'vm',
                          backdrop: 'static',
                          size: 'lg',
                          resolve: {
                              translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                                  $translatePartialLoader.addPart('media');
                                  return $translate.refresh();
                              }],
                              entity: function() {
                                  return {
                                      title: null,
                                      owner: null,
                                      description: null,
                                      photo: null,
                                      image: null,
                                      imageContentType: null,
                                      startTime: null,
                                      endTime: null,
                                      createdDate: null,
                                      modifiedDate: null,
                                      createdBy: null,
                                      modifiedBy: null,
                                      id: null
                                  };
                              }
                          }
                      }).result.then(function() {
                          $state.go('events-management', null, {
                              reload: 'events-management'
                          });
                      }, function() {
                          $state.go('events-management');
                      });
                  }]
              })
              .state('events-management.edit', {
                  parent: 'events-management',
                  url: '/{id}/edit',
                  data: {
                      authorities: ['ROLE_USER']
                  },
                  onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                      $uibModal.open({
                          templateUrl: 'app/entities/event/event-dialog.html',
                          controller: 'EventDialogController',
                          controllerAs: 'vm',
                          backdrop: 'static',
                          size: 'lg',
                          resolve: {
                              entity: ['Event', function(Event) {
                                  return Event.get({
                                      id: $stateParams.id
                                  }).$promise;
                              }]
                          }
                      }).result.then(function() {
                          $state.go('events-management', null, {
                              reload: 'events-management'
                          });
                      }, function() {
                          $state.go('^');
                      });
                  }]
              })
              .state('events-management.delete', {
                  parent: 'events-management',
                  url: '/{id}/delete',
                  data: {
                      authorities: ['ROLE_USER']
                  },
                  onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                      $uibModal.open({
                          templateUrl: 'app/entities/event/event-delete-dialog.html',
                          controller: 'EventDeleteController',
                          controllerAs: 'vm',
                          size: 'md',
                          resolve: {
                              entity: ['Event', function(Event) {
                                  return Event.get({
                                      id: $stateParams.id
                                  }).$promise;
                              }]
                          }
                      }).result.then(function() {
                          $state.go('events-management', null, {
                              reload: 'events-management'
                          });
                      }, function() {
                          $state.go('^');
                      });
                  }]
              });
      }

  })();
