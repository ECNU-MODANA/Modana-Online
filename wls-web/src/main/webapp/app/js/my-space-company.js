var app = angular.module('app', []).controller('my-space-company',function($http, $location, $scope) {
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
});
