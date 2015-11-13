'use strict';

(function() {

	var team = function($http) {

		var getOptimal = function(week) {
			var url = "/optimal/" + week;
			return $http.get(url);
		};

		return {
			getOptimal: getOptimal
		};
	};

	var module = angular.module('myApp');
	module.factory("team", team);

}());
