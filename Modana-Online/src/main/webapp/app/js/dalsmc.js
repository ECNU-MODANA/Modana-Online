coldWeb.controller('dalsmc', function( $scope, $rootScope,$http ,$timeout) {
	$scope.sampling = function() {
			   $http.get("/i/dalsmc/Sampling",{
	 	    		params : {
	 	    			learnTraceNum : 50,
	 	    			extractTraceNum : 2,
	 	    			extractTraceProbability : 0.5
	 	    			}
	 	    	}).success(function(data) {
	 		   });
		   };
	$scope.traceGen = function() {
				   $http.get("/i/dalsmc/ClientTrace",{
		 	    		params : {
		 	    			TraceNum : 100,
		 	    			extractTraceNum : 2,
		 	    			extractTraceProbability : 0.5
		 	    			}
		 	    	}).success(function(data) {
		 	    		$scope.traces = data;
		 		   });
			   };
});

