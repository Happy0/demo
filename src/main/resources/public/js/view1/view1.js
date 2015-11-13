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
        $scope.formation = "optimal";

        $scope.getCSS = function(position) {
            var css = "pos";

            alert('getCSS: ' + position);

            if (position === 1) {
                return 'keeper';
            } else if (position === 2) { // def
                css = css + $scope.team.formation.substring(0, 1);
            } else if (position === 3) { // mid
                css = css + $scope.team.formation.substring(1, 1);
            } else if (position === 4) { // att
                css = css + $scope.team.formation.substring(2, 1);
            }

            return css;
        }

        $scope.getOptimalTeam = function() {
            team.getOptimal($scope.week, $scope.formation)
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