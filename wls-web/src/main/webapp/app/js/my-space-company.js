var app = angular.module('app', []).controller('my-space-company',function($http, $location, $scope) {
    $scope.load = function(){
			 $.ajax({type: "GET",cache: false,dataType: 'json',url: '/i/user/findUser'}).success(function(data,status,config,headers){
				if(data.success){
					$scope.user = data.entity;	
				}
				else{
					alert("请先登录");
					window.location.href="../../login.html";
				}
		    });
	   };
	$scope.load();
	$scope.saveInfo = function() {
	    	$http.get( "/i/user/updateUser",{
	    		params : { : $scope.telephone}
	    	}).success(function(data) {
				alert(data.message);
		   });
	   };
});
