 'use strict';

angular.module('myApp.view1', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl'
  });
}])

.controller('View1Ctrl', function($scope, team) {

        $scope.week = 11;
        $scope.formation = "optimal";

        $scope.isCaptain = function(i) {
            if (i.captain != null && i.captain !== undefined && i.captain === true) {
                return true;
            } else {
                return false;
            }
        }
        $scope.getSurname = function(i) {
            var surname = i.surname;
            return surname;
        }

        $scope.formatFormation = function () {
            if ($scope.team != null && $scope.team !== undefined) {
                if ($scope.team.formation != null && $scope.team.formation !== undefined) {
                    var ff = $scope.team.formation.substring(0, 1) + '-';
                    ff = ff + $scope.team.formation.substring(1, 2) + '-';
                    ff = ff + $scope.team.formation.substring(2, 3);
                    return ff;
                }
            }
            return '';
        }

        $scope.getCSS = function(position) {
            var css;

            if (position == '1') {
                return 'keeper';
            } else if (position == '2') { // def
                css = $scope.team.formation.substring(0, 1);
            } else if (position == '3') { // mid
                css = $scope.team.formation.substring(1, 2);
            } else if (position == '4') { // att
                css = $scope.team.formation.substring(2, 3);
            }

            var finalCss = 'pos' + css;

            return finalCss;
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