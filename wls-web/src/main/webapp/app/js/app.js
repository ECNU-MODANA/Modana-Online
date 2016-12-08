var wlsWeb = angular.module('WlsWeb', ['ui.bootstrap', 'ui.router', 'ui.checkbox',
    'ngCookies', 'xeditable', 'isteven-multi-select', 'angucomplete', 'angular-table','ngFileUpload','remoteValidation']);
angular.element(document).ready(function ($ngCookies, $http, $rootScope) {
	angular.bootstrap(document, ['WlsWeb']);
});
wlsWeb.run(function (editableOptions, userService, $location) {
    editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
    $.ajax({type: "GET",cache: false,dataType: 'json',url: '/i/user/findUser'}).success(function(data){
      	user = data;
  		userService.setUser(user);
      });
});

wlsWeb.factory('userService',['$rootScope','$http', function($rootScope,$http){
	return {
		setUser: function(user){
	    	$rootScope.user = user;
	    	$rootScope.logout = function () {
	        	$http.get('/i/user/logout').success(function(data){
	        		$rootScope.user = null;
	            });
	        	window.location.href="#/home";
	        };
	    },
	};
}]);


wlsWeb.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/home");
    //index
    $stateProvider.state('home', {
        url: '/home',
        controller: 'home',
        templateUrl: '../../home.html'
    }).state('geek', {
        url: '/geek',
        controller: 'geek',
        templateUrl: 'app/template/geek.html'
    }).state('my-space', {
        url: '/my-space',
        controller: 'my-space',
        templateUrl: 'app/template/my-space.html'
    }).state('my-space-company', {
        url: '/my-space-company',
        controller: 'my-space-company',
        templateUrl: 'app/template/my-space-company.html'
    }).state('register', {
        url: '/register',
        controller: 'register',
        templateUrl: '../../register.html'
    });
});