var app = angular.module('app', []).controller('login',function($http, $location, $scope) {
   $scope.login = function() {
	   if($scope.username!=""&&$scope.password!=""&&
			   $scope.username!=undefined&&$scope.password!=undefined){
		   $http.get("/i/user/login",{
 	    		params : {
 	    			userName : $scope.username,
 	    			password : $scope.password
 	    			}
 	    	}).success(function(data) {
 	    		alert(data.message);
 	    		if(data.success){
 	    			window.location.href="index.html";
 	    		}
 		   });
	   }
	   else{
		   alert("请输入用户名和密码");
	   }
  };
});
