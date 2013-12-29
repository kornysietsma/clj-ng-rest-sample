'use strict';

/* Controllers */

angular.module('thingApp.resources', ['ngResource']).factory('Thing',['$resource',
    function($resource){
        return $resource('things/:id',
            {id: '@id'});
    }]);