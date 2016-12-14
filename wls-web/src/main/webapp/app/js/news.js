wlsWeb.controller('news',function($http, $location, $scope) {
	// 显示最大页数
    $scope.maxSize = 12;
    // 总条目数(默认每页十条)
    $scope.bigTotalItems = 12;
    // 当前页
    $scope.bigCurrentPage = 1;
	$scope.AllNews = [];
	$scope.optAudit = 8;
	 // 获取当前news的列表
    $scope.getNews = function() {
		$http({
			method : 'POST',
			url : '/i/information/findAllInformation',
			params : {
				pageNum : $scope.bigCurrentPage,
				pageSize : $scope.maxSize,
				audit : $scope.optAudit,
				keyword : encodeURI($scope.keyword,"UTF-8"),
			}
		}).success(function(data) {
			$scope.bigTotalItems = data.total;
			$scope.AllNews = data.list;
		});
	};
    
	$scope.pageChanged = function() {
		$scope.getNews();
	};
	$scope.getNews();

	$scope.auditChanged = function(optAudit) {
		$scope.optAudit = optAudit;
		$scope.getNews();
	};
    
	$scope.goSearch = function () {
		$scope.getNews();
    };
});
