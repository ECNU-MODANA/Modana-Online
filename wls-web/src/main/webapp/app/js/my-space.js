var app = angular.module('app', []).controller('my-space',function($http, $location, $scope) {
    $scope.load = function(){
			 $.ajax({type: "GET",cache: false,dataType: 'json',url: '/i/user/findUser'}).success(function(data,status,config,headers){
				$scope.user = data;
				if($scope.user.id==undefined){
					alert("请先登录");
					window.location.href="../../login.html";
				}
		    });
	   };
	$scope.load();
	
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
