"use strict";

angular.module('thingApp', [
        'ngRoute',
        'thingApp.controllers',
        'thingApp.resources'
    ]).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/things', {templateUrl: 'partials/things.html', controller: 'ThingController'});
        $routeProvider.otherwise({redirectTo: '/things'});
    }]);
