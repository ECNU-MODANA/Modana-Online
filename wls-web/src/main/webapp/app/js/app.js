var wls = angular.module('app', []);
angular.element(document).ready(function ($http, $rootScope) {
    $http.get( "/i/user/findUser").success(function(data){
    	user = data;
    });
});
wls.run(function (userService) {
    userService.setUser(user);
});
wls.factory('userService',['$rootScope','$http', function($rootScope,$http){
	return {
		setUser: function(user){
	    	$rootScope.user = user;
	    	$rootScope.logout = function () {
	    		$http.get( "/i/user/logout").success(function(data){});
	        	 $rootScope.user =window.user=user=null;//清除系统user;
	        	 window.location.href="#/home";
	        };
	    },
	};
}]);

wls.config(function ($stateProvider, $urlRouterProvider) {
	    $urlRouterProvider.otherwise("/home");
	    $stateProvider.state('home', {
	        url: '/home',
	        controller: 'home',
	        templateUrl: '../../home.html'
	    });
});
