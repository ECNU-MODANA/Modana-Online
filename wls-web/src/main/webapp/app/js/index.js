var app = angular.module('app', []).controller('index',function($http, $location, $scope) {
	$scope.load = function(){
		$http.get( "/i/user/findUser").success(function(data,status,config,headers){
			if(data.id!=undefined){
				$scope.user = data;	
			}
	    });
   };
   $scope.load();
   $scope.logout = function() {
	   $http.get( "/i/user/logout").success(function(data){
		   window.location.reload();
	   });
  };
});
