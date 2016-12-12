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
 	    		if(data.success){
 	    			alert("登录成功");
 	    			window.location.href="#/home";
 	    		}
 	    		else{
 	    			
 	    		}
 		   });
	   }
	   else{
		   alert("请输入用户名和密码");
	   }
  };
});
