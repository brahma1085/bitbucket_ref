'use strict';

/* App Module */



var tfwApp =angular.module('tfwApp', [
                                        'ngRoute','addTestPlanCtrl'              
                         ]);

                         tfwApp.config(['routeProvider',
                            function($routeProvider) {
                                $routeProvider.
                                when('/addTestPlan', {
                                    templateUrl: 'partials/TestPlan.html',
                                     controller: 'addTestPlanCtrl'
                                });
                         }]);
