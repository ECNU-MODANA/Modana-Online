var app = angular.module('app', []).controller('my-space',function($http, $location, $scope) {
	 $scope.load = function(){
	    	$http.get( "/i/user/findUser").success(function(data,status,config,headers){
					 if(data!=null&&data.id!=undefined){
					 $scope.user = data;
					 if($scope.user.sex==0){
							$scope.user.sex = "男";
						}
						else{
							$scope.user.sex = "女";
						}
						if($scope.user.score<100){
							$scope.level = 1;
						}
						else if($scope.user.score<500&&$scope.user.score>=100){
							$scope.level = 2;
						}
						else if($scope.user.score>=500){
							$scope.level = 3;
						}
						//$("#tab-1").addClass("in active");
					 }
					else{
						alert("请先登录");
						window.location.href="../../login.html";
					}
			    });
		   };
		$scope.load();
		$scope.logout = function() {
			   $http.get( "/i/user/logout").success(function(data){
				   window.location.reload();
			   });
	    };
		$scope.saveInfo = function() {
			if($scope.user.sex=="男"){
				$scope.sex = 0;
			}
			else{
				$scope.sex = 1;
			}
		    	$http.get( "/i/user/updateUser",{
		    		params : {
		    			realname : $scope.user.realname,
		    			telephone : $scope.user.telephone,
		    			position: $scope.user.position,
		    			company : $scope.user.company,
		    			address : $scope.user.address,
		    			phone : $scope.user.phone,
		    			email : $scope.user.email,
		    			signature : $scope.user.signature,
		    			sex : $scope.sex
		    		}
		    	}).success(function(data) {
		    		if(data.success){
		    			alert("信息修改成功");
		    			window.location.reload();
		    		}
		    		else{
		    			alert("信息修改失败");
		    		}
			   });
		   };
		   $scope.myspace = function() {
			      if($scope.user!=undefined&&$scope.user!=null&&$scope.user.id!=undefined){
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
