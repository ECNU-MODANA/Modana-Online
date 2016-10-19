var wls = angular.module('WLS', ['ui.bootstrap', 'ui.router', 'ui.checkbox','ngCookies', 'xeditable', 'isteven-multi-select', 'angucomplete', 'angular-table','ngFileUpload','remoteValidation', 'jkuri.gallery']);
if (typeof String.prototype.startsWith != 'function') {
    String.prototype.startsWith = function (prefix){  
     return this.slice(0, prefix.length) === prefix;  
    };  
 }
angular.element(document).ready(function ($ngCookies, $http, $rootScope) {
	    $.ajax({type: "GET",cache: false,dataType: 'json',url: '/i/user/findUser'}).success(function(data){
	    	user = data;
	    	angular.bootstrap(document, ['WLS']);
	    });
});
wls.run(function (editableOptions, naviService,userService) {
    editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
    userService.setUser(user);
});

wls.factory('userService',['$rootScope','$http', function($rootScope,$http){
	return {
		setUser: function(user){
	    	$rootScope.user = user;
	    	$rootScope.logout = function () {
	        	$.ajax({type: "GET",cache: false,dataType: 'json',url: '/i/user/logout'}).success(function(data){});
	        	 $rootScope.user =window.user=user=null;
	        	 window.location.href="#/index";
	        };
	    },
	};
}]);

JS.Engine.start('conn');
JS.Engine.on(
        { 
           msgData : function(msgData){
        	   //alert(msgData);
        	   $(".msgPush").fadeIn();
        	   $(".pushContent p").html(msgData)
        	   setTimeout(function(){$(".msgPush").fadeOut()}, 4000);
           },
       }
   );

wls.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/home");
    //index
    $stateProvider.state('login', {
        url: '/login',
        controller: 'login',
        templateUrl: 'app/template/login.html'
    }).state('home', {
        url: '/home',
        controller: 'home',
        templateUrl: 'app/template/home.html'
    }).state('search', {
        url: '/search',
        controller: 'search',
        templateUrl: 'app/template/search.html'
    }).state('info', {
        url: '/info/{id}',
        controller: 'info',
        templateUrl: 'app/template/info.html'
    }).state('multi-query', {
        url: '/multi-query/{key}',
        controller: 'multi-query',
        templateUrl: 'app/template/multi-query.html'
    }).state('goods-list', {
        url: '/goods-list/{key}',
        controller: 'goods-list',
        templateUrl: 'app/template/goods-list.html'
    }).state('coldStoragelist', {
        url: '/coldStoragelist',
        controller: 'coldStoragelist',
        templateUrl: 'app/template/coldStoragelist.html'
    }).state('coldStorageComment', {
        url: '/coldStorageComment/:rdcID',
        controller: 'coldStorageComment',
        templateUrl: 'app/template/coldStorageComment.html'
    }).state('coldShareComment', {  
        url: '/coldShareComment',
        controller: 'coldShareComment',
        params:{_cuttid:null,showData:null,codeCode:null},
        templateUrl: 'app/template/coldsharerdc.html'
    }).state('releaseItem', { 
        url: '/releaseItem',
        controller: 'releaseItem',
        params:{data:null,dataid:null,_cuttid:null},
        templateUrl: 'app/template/release_item.html'
    }).state('releaseItemList', { 
        url: '/releaseItemList',
        controller: 'releaseItemList',
        params:{data:null,dataid:null,_cuttid:null},
        templateUrl: 'app/template/release_item_list.html'
    }).state('releaseCarInfo', {  
		url: '/releaseCarInfo',
		controller: 'releaseCarInfo',
		params:{data:null,dataid:null,_cuttid:null},
		templateUrl: 'app/template/release_carinfo.html'
    }).state('shareriteminfo', {  
		url: '/shareriteminfo',
		controller: 'shareriteminfo',
		params:{dataid:null},
		templateUrl: 'app/template/release_item_info.html'
    }). state('review', {
        url: '/coldStorage/{rdcID}/review',
        controller: 'review',
        templateUrl: 'app/template/review.html'
    }).state('coldStorageAdd', {
        url: '/coldStorageAdd',
        controller: 'coldStorageAdd',
        templateUrl: 'app/template/coldStorageInfo.html'
    }).state('coldStorageEdit', {
        url: '/coldStorageEdit/:rdcID',
        controller: 'coldStorageEdit',
        templateUrl: 'app/template/coldStorageInfo.html'
    }).state('coldStorageMap', {
        url: '/coldStorageMap',
        controller: 'coldStorageMap',
        templateUrl: 'app/template/coldStorageMap.html'
    }).state('personalRdc', {
        url: '/personalRdc',
        controller: 'personalRdc',
        templateUrl: 'app/template/personalRdc.html'
    }).state('personalOrder', {
        url: '/personalOrder',
        controller: 'personalOrder',
        templateUrl: 'app/template/personalOrder.html'
    }).state('personalDetail', {
        url: '/personalDetail',
        controller: 'personalDetail',
        templateUrl: 'app/template/personalDetail.html'
    }).state('personalComment', {
        url: '/personalComment',
        controller: 'personalComment',
        templateUrl: 'app/template/personalComment.html'
    }).state('personalShare', {
        url: '/personalShare',
        controller: 'personalShare',
        templateUrl: 'app/template/personalShare.html'
    }).state('personalMessage', {
        url: '/personalMessage',
        controller: 'personalMessage',
        templateUrl: 'app/template/personalMessage.html'
    }).state('personalConnect', {
        url: '/personalConnect',
        controller: 'personalShare',
        templateUrl: 'app/template/personal_connect.html'
    }).state('orderGenerate', {
        url: '/orderGenerate',
        params:{data:null},
        controller: 'orderGenerate',
        templateUrl: 'app/template/order.html'
    }).state('coldStorageAuth', {
        url: '/coldStorageAuth/:rdcID',
        controller: 'coldStorageAuth',
        templateUrl: 'app/template/coldStorageAuth.html'
    });

});