var app = angular.module('app', []).controller('register',function($http, $location, $scope) {
	$scope.suproleid = 1;
	$scope.company_gate = function() {
       $scope.suproleid = 2;
    };
    $scope.geek_gate = function() {
    	 $scope.suproleid = 1;  
    };
    $scope.getVerCode = function() {
      if($scope.telephone!=undefined){
    	$http.get( "/i/user/sendSignUpCode",{
    		params : {telephone : $scope.telephone}
    	}).success(function(data) {
			alert(data.message);
	   });
       }
    	else{
    		alert("请先输入手机号");
    	}
   };
   $scope.register = function() {
	   if( $scope.username==undefined&&$scope.telephone!=undefined){
		   $http.get( "/i/user/verifySignUpCode",{
 	    		params : {telephone : $scope.telephone}
 	    	}).success(function(data) {
 	    		if(data.success){
 	    			
 	    		}
 	    		alert(data.message);
 		   });
	   }
	   else if( $scope.username!=undefined&&$scope.email!=undefined&&$scope.password!=undefined){
  			$http.get( "/i/user/signup",{
  				params : {
  					username : $scope.username,
  					password :  $scope.password,
  					rpassword : $scope.rpassword,
  	    			telephone : $scope.telephone,
  	    			email : $scope.email,
  	    			suproleid : $scope.suproleid
  	    			}
  	    	}).success(function(data) {
  	    		alert(data.message);
  		   });
  	   }
  };
});
