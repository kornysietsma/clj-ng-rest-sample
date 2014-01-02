describe("Controllers", function() {
    "use strict";

    var $httpBackend, $rootScope, $scope, $controller;

    beforeEach(module('thingApp.controllers','thingApp.resources'));

    beforeEach(function() {
        module(function($provide) {
            // add services such as:
            // $provide.value('$window', { alert: jasmine.createSpy() });
            // see http://docs.angularjs.org/guide/dev_guide.services.testing_services
        });
        inject(function($injector) {
                $httpBackend = $injector.get('$httpBackend');
                $rootScope = $injector.get('$rootScope');
                $scope = $rootScope.$new();
                $controller = $injector.get('$controller');
            }
        );
    });

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it("should put the things in the scope, converted to text", function() {
        $httpBackend.when('GET','things').respond(200,[{"_id": "123", foo: "bar"}]);
        $controller('ThingController', {'$scope': $scope});
        $httpBackend.flush();
        expect($scope.things).toEqual([{"id": "123", text: '{"foo":"bar"}'}]);
    })
});