
function addTestPlanCtrl ($scope, $http) {
	$scope.addTestPlan = function(){
	  alert('Submitting data to server.. Check the log to see data sent');
	    	var result = {};
	    	console.log("add test plan",$scope.addTestPlanForm);
	    	var planName = $scope.addTestPlanForm.planName.$viewValue;
	    	var description=$scope.addTestPlanForm.description.$viewValue;
	    	result["planName"] = planName;
	    	result["description"] = description;
	    	alert('Result JSON:: '+result);
	    	console.log(result);
	    	$http({
	  			  url: "addTestPlan", 
	  			  method: "POST",
	  			  data: result,
	  			  headers: {'Content-Type': 'application/json,'}
	  			  })
	  			 .success(function(data) {
		                alert(data);
		 });
			    
}
	    
	
   }	
	