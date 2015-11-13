'use strict';

(function() {

	var team = function($http) {

		var getOptimal = function(week, formation) {
			var url = "/optimal/" + week;
			if (formation !== 'optimal') {
				url = url + "?formation=" + formation;
			}
			return $http.get(url);
		};

		return {
			getOptimal: getOptimal
		};
	};

	var module = angular.module('myApp');
	module.factory("team", team);

}());
