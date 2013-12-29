'use strict';

/* Controllers */

angular.module('thingApp.controllers', []).
    controller('ThingController', ['$scope', 'Thing', function($scope, Thing) {
        $scope.things = Thing.query({}, function() {
            $scope.things = _.map($scope.things, function(thing) {
                return {
                    id: thing["_id"],
                    text: JSON.stringify(_.omit(thing,'_id'))
                };
            });
        });
    }]);