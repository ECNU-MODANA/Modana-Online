var app = angular.module('app', []).controller('geek',function($http, $location, $scope) {
    $scope.load = function(){
    	$http.get( "/i/user/findUser").success(function(data,status,config,headers){
				 $scope.user = data;
		    });
	};
	$scope.load();
	// 显示最大页数
    $scope.maxSize = 12;
    // 总条目数(默认每页十条)
    $scope.bigTotalItems = 12;
    // 当前页
    $scope.bigCurrentPage = 1;
	$scope.Allusers = [];
	$scope.optAudit = 8;
	 // 获取当前geek的列表
    $scope.getUsers = function() {
		$http({
			method : 'POST',
			url : '/i/user/findUserList',
			params : {
				pageNum : $scope.bigCurrentPage,
				pageSize : $scope.maxSize,
				audit : $scope.optAudit,
				keyword : encodeURI($scope.keyword,"UTF-8"),
			}
		}).success(function(data) {
			$scope.bigTotalItems = data.total;
			$scope.Allusers = data.list;
		});
	};

	$scope.pageChanged = function() {
		$scope.getUsers();
	};
	$scope.getUsers();
	// 获取当前冷库的列表
	$scope.auditChanged = function(optAudiet) {
		$scope.getUsers();
	};
    
	$scope.goSearch = function () {
		$scope.getUsers();
    };
    $scope.myspace = function() {
	      if($scope.user!=undefined&&$scope.user!=null&&$scope.user.id!=undefined){
	    	  if($scope.user.suproleid==1){
	    		  window.location.href="my-space.html";
	    	  }
	    	  else if($scope.user.suproleid==2){
	    		  window.location.href="my-space-company.html";
	    	  }
	      }
	      else{
	    	 alert("请先登录"); 
	    	 window.location.href="index.html";
	      }
	   };
	   $scope.logout = function() {
		   $http.get( "/i/user/logout").success(function(data){
			   window.location.reload();
		   });
	  };
});
