var coldWeb = angular.module('ColdWeb', ['ui.bootstrap', 'ui.router', 'ui.checkbox',
    'ngCookies', 'xeditable', 'isteven-multi-select', 'angucomplete', 'angular-table','ngFileUpload']);

angular.element(document).ready(function ($ngCookies, $http, $rootScope) {
	$.ajax({
	      url: '/i/user/findUser',
	      type: "GET",
	      dataType: 'json'
	    }).success(function(data){
	    	user = data;
	    	angular.bootstrap(document, ['ColdWeb']);
	    })
});
coldWeb.run(function (editableOptions, naviService,userService) {
    editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
    naviService.setNAVI();
    userService.setUser(user);
    
});

coldWeb.config(function ($httpProvider) {
    $httpProvider.interceptors.push(function ($q, $injector) {
        return {
            'response': function (response) {
                return response;
            },
            'responseError': function (rejection) {
                var modal = $injector.get('$Modal');
                modal.open({
                    animation: true,
                    templateUrl: 'app/template/error.html',
                    controller: 'error',
                    backdrop: true,
                    resolve: {
                        rejection: function () {
                            return rejection;
                        }
                    }
                });

                return $q.reject(rejection);
            }
        };
    });
});

coldWeb.factory('userService',['$rootScope','$http', function($rootScope,$http){
	return {
		setUser: function(user){
	    	$rootScope.user = user;
	    	$rootScope.logout = function () {
	        	$http.get('/i/user/logout');
	        	$rootScope.user = null;
	        };
	        $rootScope.gotoSmartCold = function(){
	        	cookies = document.cookie.split(";")
	        	url = "http://www.smartcold.net";
	        	angular.forEach(cookies,function(item){
	        		item = item.trim();
	        		if(item.startsWith("token=")){	        			
	        			url = url + "/#/" + item.split("=")[1];
	        		}
	        	})
	        	window.open(url);
	        }
	    },
	}
}])

coldWeb.factory('naviService', ['$rootScope', '$state', function ($rootScope, $state) {
    return {
        setNAVI: function () {
            $rootScope.toColdStorageList = function () {
                $state.go('coldStoragelist', {});
            };
            $rootScope.toColdStorageComment = function (storageId) {
                $state.go('coldStorageComment', {'storageId': storageId});
            };
        },
    };
}]);

coldWeb.filter('objectCount', function () {
    return function (input) {
        var size = 0, key;
        for (key in input) {
            if (input.hasOwnProperty(key)) size++;
        }
        return size;
    }
});

coldWeb.filter('toArray', function () {
    'use strict';

    return function (obj) {
        if (!(obj instanceof Object)) {
            return obj;
        }

        return Object.keys(obj).filter(function (key) {
            if (key.charAt(0) !== "$") {
                return key;
            }
        }).map(function (key) {
            if (!(obj[key] instanceof Object)) {
                obj[key] = {value: obj[key]};
            }

            return Object.defineProperty(obj[key], '$key', {__proto__: null, value: key});
        });
    };
});


coldWeb.directive('snippet', function () {
    return {
        restrict: 'E',
        template: '<pre><div class="hidden code" ng-transclude></div><code></code></pre>',
        replace: true,
        transclude: true,
        link: function (scope, elm, attrs) {
            scope.$watch(function () {
                return elm.find('.code').text();
            }, function (newValue, oldValue) {
                if (newValue != oldValue) {
                    elm.find('code').html(hljs.highlightAuto(newValue).value);
                }
            });
        }
    };
});


coldWeb.directive('star', function () {
  return {
    template: '<ul class="rating" ng-mouseleave="leave(order)">' +
        '<li ng-repeat="star in stars" ng-class="star" ng-click="click(order,$index + 1)" ng-mouseover="over(order,$index + 1)">' +
        '\u2605' +
        '</li>' +
        '</ul>{{overVal?overVal:ratingVal}} 分',
    scope: {
      ratingValue: '=',
      max: '=',
      order: '=',
      readonly: '@',
      onClick: '='
    },
    controller: function($scope){
      $scope.ratingValue = $scope.ratingValue || 0;
      $scope.max = $scope.max || 5;
	  $scope.ratingVal = 0;
	  $scope.overVal = null;
	  $scope.stars = [];
	  for(var i=0; i< $scope.max;i++){
		  $scope.stars.push({
              filled: i < $scope.ratingVal
            });
	  }
	  var updateStars = function (val) {
          for (var i = 0; i < $scope.max; i++) {
            $scope.stars[i].filled = i < val;
          }
        };
      $scope.click = function(i,val){
        $scope.ratingVal = val;
    	updateStars(val);
        $scope.onClick(i,val);
      };
      $scope.over = function(i,val){
    	$scope.overVal = val;
    	updateStars(val);
      };
      $scope.leave = function(i){
    	  $scope.overVal = null;
    	updateStars($scope.ratingVal);
      }
      updateStars(0);
    },
    link: function (scope, elem, attrs) {
      elem.css("text-align", "center");
 
    }
  };
});

coldWeb.directive('activeLink', ['$location', '$filter', function (location, filter) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs, controller) {
            var clazz = attrs.activeLink;
            var path = element.children().attr('href') + "";
            path = filter('limitTo')(path, path.length - 1, 1);
            scope.location = location;
            scope.$watch('location.path()', function (newPath) {
                if (newPath.indexOf(path) > -1) {
                    element.addClass(clazz);
                } else {
                    element.removeClass(clazz);
                }
            });
        }
    };
}]);

coldWeb.filter('sizeformat', function () {
    return function (size) {
        if (size / (1024 * 1024 * 1024) > 1)
            return (size / (1024 * 1024 * 1024)).toFixed(2) + 'G';
        else if (size / (1024 * 1024) > 1)
            return (size / (1024 * 1024)).toFixed(2) + 'M';
        else if (size / 1024 > 1)
            return (size / 1024).toFixed(2) + 'K';
        else
            return size + 'B'
    }
});


coldWeb.config(function ($stateProvider, $urlRouterProvider) {
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
    }).state('review', {
        url: '/coldStorage/{rdcID}/review',
        controller: 'review',
        templateUrl: 'app/template/review.html'
    }).state('coldStorageAdd', {
        url: '/coldStorageAdd',
        controller: 'coldStorageAdd',
        templateUrl: 'app/template/coldStorageAdd.html'
    });

});