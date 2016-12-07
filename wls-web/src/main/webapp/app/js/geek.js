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
	$scope.auditChanged = function(optAudit) {
		$scope.optAudit = optAudit;
		$scope.getUsers();
	};
    
	$scope.goSearch = function () {
		$scope.getUsers();
    };
});
