 'use strict';

angular.module('myApp.view1', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl'
  });
}])

.controller('View1Ctrl', function($scope, team) {

        $scope.week = 20;

        $scope.getOptimalTeam = function() {
            team.getOptimal($scope.week)
                    .success(function (response) {
                        $scope.team = response;
                    })
                    .error(function (error) {
                        alert('Unable to get: ' + error);
                    })
        };

        $scope.playingFilter = function (item) {
            return item.onBench === false;
        };

        $scope.getShirt = function(item) {
            var theclub = item.club.toLowerCase();
            theclub = theclub.replace(/ /g,"_");
            var shirt = 'shirts/' + theclub + '.png';
            return shirt;
        };

        $scope.getWord = function() {
            var words = ['lucky', 'brave', 'optimistic', 'crazy', 'desperate' ];
            return  words[Math.floor(Math.random() * words.length)];;
        }
  });