var app = angular.module('app', []).controller('index',function($http, $location, $scope) {
	$scope.load = function(){
		 $.ajax({type: "GET",cache: false,dataType: 'json',url: '/i/user/findUser'}).success(function(data,status,config,headers){
			$scope.user = data;
	    });
   };
   $scope.load();
   $scope.myspace = function() {
	      if($scope.user.id!=undefined){
	    	  if($scope.user.suproleid==1){
	    		  window.location.href="app/template/my-space.html";
	    	  }
	    	  else if($scope.user.suproleid==2){
	    		  window.location.href="app/template/my-space-company.html";
	    	  }
	      }
	      else{
	    	 alert("请先登录"); 
	    	 window.location.href="index.html";
	      }
	   };
});
