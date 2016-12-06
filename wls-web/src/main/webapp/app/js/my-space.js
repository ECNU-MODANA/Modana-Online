var app = angular.module('app', []).controller('my-space',function($http, $location, $scope) {
	 $scope.load = function(){
	    	$http.get( "/i/user/findUser").success(function(data,status,config,headers){
					 if(data!=null&&data.id!=undefined){
					 $scope.user = data;
					 $scope.educates = {};
					 $scope.skills = {};
					 $scope.honors = {};
					 $scope.jobs = {};
					 $http.get('/i/user/findEducateByUserID', {
				            params: {
				                "userID": $scope.user.id
				            }
				        }).success(function (data) { 
				        	if(data.success){
				        		$scope.educates = data.data;
				        	}
				     });
					 $http.get('/i/user/findSkillByUserID', {
				            params: {
				                "userID": $scope.user.id
				            }
				        }).success(function (data) { 
				        	if(data.success){
				        		$scope.skills = data.data;
				        	}
				     });
					 $http.get('/i/user/findHonorByUserID', {
				            params: {
				                "userID": $scope.user.id
				            }
				        }).success(function (data) { 
				        	if(data.success){
				        		$scope.honors = data.data;
				        	}
				     });
					 $http.get('/i/user/findJobByUserID', {
				            params: {
				                "userID": $scope.user.id
				            }
				        }).success(function (data) { 
				        	if(data.success){
				        		$scope.jobs = data.data;
				        	}
				     });
					 $http.get('/i/city/findCitysByProvinceId', {
				            params: {
				                "provinceID": $scope.user.provinceid
				            }
				        }).success(function (data) {
				            $scope.citys = data;
				        });
					 $http.get('/i/city/findSchoolsByCityId', {
				            params: {
				                "cityID": $scope.user.cityid
				            }
				        }).success(function (data) {
				            $scope.schools = data;
				        });
					 if($scope.user.provinceid!=null){
					 $http.get('/i/city/findProvinceById', {
				            params: {
				                "provinceID": $scope.user.provinceid
				            }
				        }).success(function (data) {
				            $scope.userProvince = data.pr_province;
				     });
					 }
					 else{
						 $scope.userProvince = '';
					 }
					 if($scope.user.cityid!=null){
					 $http.get('/i/city/findCityById', {
				            params: {
				                "CityID": $scope.user.cityid
				            }
				        }).success(function (data) {
				            $scope.userCity = data.ci_city;
				     });
					 }
					 else{
						 $scope.userCity = '';
					 }
					 if($scope.user.schoolid!=null){
						 $http.get('/i/city/findSchoolById', {
					            params: {
					                "schoolID": $scope.user.schoolid
					            }
					        }).success(function (data) {
					            $scope.userSchool = data.sh_shool;
					     });
						 }
					else{
							 $scope.userSchool = '';
						 }
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
		 // 获取省列表
	    $http.get('/i/city/findProvinceList').success(function (data) {
	        $scope.provinces = data;
	        if($scope.user.provinceid==null)
	        	$scope.user.provinceid = data[0].pr_id;
	    });
	   
	    // 根据省ID查询城市列表
	    $scope.provinceSelected = function () {
	        $http.get('/i/city/findCitysByProvinceId', {
	            params: {
	                "provinceID": $scope.user.provinceid
	            }
	        }).success(function (data) {
	            $scope.citys = data;
	               $scope.user.cityid = data[0].ci_id;
	            if($scope.user.cityid==1||$scope.user.cityid==2||
	            		$scope.user.cityid==3||$scope.user.cityid==4){
	            	$scope.citySelected();
	            }
	        });
	    };
	    
	 // 根据城市ID查询学校列表
	    $scope.citySelected = function () {
	        $http.get('/i/city/findSchoolsByCityId', {
	            params: {
	                "cityID": $scope.user.cityid
	            }
	        }).success(function (data) {
	            $scope.schools = data;
	               $scope.user.schoolid = data[0].sh_id;
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
		    			signature : $scope.user.signature,
		    			sex : $scope.sex,
		    			
		    			pr_id : $scope.user.provinceid,
		    			ci_id : $scope.user.cityid,
		    			sh_id : $scope.user.schoolid,
		    			major : $scope.user.major,
		    			skill : $scope.user.skill,
		    			nickname : $scope.user.nickname,
		    			interest : $scope.user.interest,
		    			age : $scope.user.age
		    		}
		    	}).success(function(data) {
		    		if(data.success){
		    			alert("信息修改成功");
		    		}
		    		else{
		    			alert("信息修改失败");
		    		}
			   });
		   };
		   
	   function delcfm() {
		        if (!confirm("确认要删除？")) {
		            return false;
		        }
		        return true;
		}
		
	    $scope.deleteEducate = function (educateID) {
	    	if(delcfm()){
	    	$http.get('/i/user/deleteEducate', {
	            params: {
	                "educateID": educateID
	            }
	        }).success(function (data) {
	        	if(data.success){
	    			alert("删除成功");
	    			window.location.reload();
	    		}
	        });
	    	}
	    };
		   
		   $scope.saveEducate = function() {
			   $scope.educatefulltime=0;
			    	$http.get( "/i/user/addEducate",{
			    		params : {
			    			educatestarttime : $scope.educatestarttime,
			    			educateendtime : $scope.educateendtime,
			    			educateschool : $scope.educateschool,
			    			educatedegree : $scope.educatedegree,
			    			educatefulltime : $scope.educatefulltime,
			    			educateuserid : $scope.user.id,
			    			educatemajor : $scope.educatemajor,
			    			educatedescribe : $scope.educatedescribe,
			    		}
			    	}).success(function(data) {
			    		if(data.success){
			    			alert("教育经历添加成功");
			    			window.location.reload();
			    		}
			    		else{
			    			alert("教育经历添加失败");
			    		}
				   });
			   };
			   
			   
			   
			   $scope.deleteJob = function (jobID) {
			    	if(delcfm()){
			    	$http.get('/i/user/deleteJob', {
			            params: {
			                "jobID": jobID
			            }
			        }).success(function (data) {
			        	if(data.success){
			    			alert("删除成功");
			    			window.location.reload();
			    		}
			        });
			    	}
			    };
				   
				   $scope.saveJob = function() {
					    	$http.get( "/i/user/addJob",{
					    		params : {
					    			jobstarttime : $scope.jobstarttime,
					    			jobendtime : $scope.jobendtime,
					    			jobcompany : $scope.jobcompany,
					    			jobposition : $scope.jobposition,
					    			jobcompanysize : $scope.jobcompanysize,
					    			jobindustry : $scope.jobindustry,
					    			jobcompanynature : $scope.jobcompanynature,
					    			jobdescribe : $scope.jobdescribe,
					    			jobuserid : $scope.user.id
					    		}
					    	}).success(function(data) {
					    		if(data.success){
					    			alert("工作经历添加成功");
					    			window.location.reload();
					    		}
					    		else{
					    			alert("工作经历添加失败");
					    		}
						   });
					   };
				   
});
