//Start : ***********Added By Firdos***********

//This is the Client Side Controller which receives the UI Form data values.
function addApplicationController($scope, $http, $window, $rootScope) {
		//alert('Landing Page');
	var queryString = location.search.substring(1);
	//alert(queryString);
	var keyValues = queryString.split('loggedInUser=');
	//alert(keyValues[1]);
	var guestName = keyValues[1];
		var dataBaseData ;
		var result = {};
		
		$http({
		url : "views/getAllApplications",
		method : "GET",
		//method : "POST",
		//data : guestName,
		headers : {
		'Content-Type' : 'application/json'}
		}).success(function(data) {
			dataBaseData = data;
			$rootScope["AllApplications"] = data;
		});
		
	// Changes from Jagadish Starts here	
		$scope.setAppId = function(appID) {			
			$window.appID=appID;
		//	alert("You selected: "+ $window.appID);
			result["appID"] = $window.appID;
			$http({
				url : "views/getTestExectionRunId",
				method : "GET",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["runnerList"] = data;
			});
			
			
			$http({
				url : "views/getTestExectionPlanId",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["planDetailsList"] = data.plan;
				$rootScope["dataDetailsList"] = data.data;
				
			});
			
			$http({
				url : "views/getTestExecutionRunSuites",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				//resultData = data;
				//console.log(data);
				$rootScope["executionSuiteList"] = data.suiteData;
			});
			
			$http({
				url : "views/getAgentData",
				method : "GET",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				//resultData = data;
				console.log(data);
				$rootScope["agentDataList"] = data;
			});
			
			$http({
				url : "views/getDataDefinition",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["dataDefList"] = data;
			});
			
			$http({
				url : "views/getTestParamGroupDetails",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ParamGroupList"] = data.paramGroup;
				//$rootScope["ParamList"] = data.param;
				
			});
			
			//start by Gaurav
			
			
			$http({
				url : "views/getTestScenariosBoxByAppId",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosBox"] = data;
			});
			
			
			$http({
				url : "views/getTestSuitesListFilterByAppId",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				
				$rootScope["AppSuitesPopUpName"] = data;
			});
			
			
			$http({
				url : "views/getTestScenariosListByAppId",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				
				$rootScope["AppScenarioPopUpsName"] = data;
			});
			
			
			$http({
				url : "views/getAppFunctionalNameByAppId",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				
				$rootScope["AppFuncPopUpsName"] = data;
			});
			
			
			$http({
				url : "views/getConditionGroupNamesByAppId",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				
				$rootScope["AppConditionPopUpsName"] = data;
			});
			
			
			$http({
				url : "views/getParamGroupNamesByAppId",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				
				$rootScope["AppParamPopUpsName"] = data;
			});
			
			$http({
				url : "views/getIdentifierTypeByAppID",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["IdentifierTypesBox"] = data;
			});
			
			$http({
				url : "views/getReplacementStringsByAppID",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ReplaceStringsTypesBox"] = data;
			});
			
			//End by Gaurav
			

			/*Changes from ramya start here*/
			$http({
				url : "views/getTestPlansForApplication",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				resultData = data;
				$rootScope["TestPlanList"] = data;
			});
			
			$http({
				url : "views/getFunctionsForApplication",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				dataBaseData = data;
				$rootScope["AppFunctionsList"] = data;
			});
			
			$http({
				url : "views/getScreensForApplication",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["AppScreens"] = data;
			});
			
			$http({
				url : "views/getObjGrpsForApplication",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ObjGrpList"] = data;
			});
			
			$http({
				url : "views/getIdentifiersForApplication",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["identifiersList"] = data;
			});
			

			$http({
				url : "views/getConditionGrp",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				resultData = data;
				$rootScope["AppConditionGrp"] = data;
			});
			
			$http({
				url : "views/getObjTypes",
				method : "GET",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				resultData = data;
				$rootScope["ObjTypeList"] = data;
			});
			
			$http({
				url : "views/getActions",
				method : "GET",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				//resultData = data;
				$rootScope["ActionsList"] = data;
			});
			
			$http({
				url : "views/getParamGrpsForApp",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["paramGrpsForApp"] = data;
			});
			/*Changes from ramya End here*/
			

			
			//Fetching reports data from schedulerRunDetails
			$http({
				url : "views/getSchedulerRunDetails/"+appID,
				method : "GET",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["jsons"]=data;
				//$scope.jsons = data;
			}).error(function(status) {
				$scope.jsons = status;
			});
			
			//Reports on overview Tab
			Sbi.sdk.services.setBaseUrl({
				protocol : 'http',
				host : 'localhost',
				port : '8100',
				contextPath : 'SpagoBI'
			});
			
			var html = Sbi.sdk.api.getDocumentHtml({
				documentLabel : 'tfw_summary_ckpt',
				userId : 'biadmin',
				executionRole : '/spagobi/admin',
				parameters : {
					year : new Date().getFullYear(),
					app_id : appID
				},
				displayToolbar : true,
				displaySliders : false,
				iframe : {
					height : '850px',
					width : '90%',
					style : 'border: 0px;'
				}
			});
			document.getElementById('targetDiv').innerHTML = html;
		};
// Changes from Jagadish Ends here	
		
	$scope.addApplication = function() {
		//alert('Add Application');
		
		var temp = {};
		
		var appName = $scope.addApplicationForm.appName.$viewValue;
		var description = $scope.addApplicationForm.description.$viewValue;
		
		temp["appName"] = appName;
		temp["description"] = description;
		
		$http({
			url : "views/addApplication",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$http({
				url : "views/getAllApplications",
				method : "GET",
				headers : {
				'Content-Type' : 'application/json'}
			}).success(function(data) {
				$rootScope["AllApplications"] = data;
			});
			alert("Successfully Persisted");
		});
	};
	
	$scope.editApplication = function(application) {
		//alert('Edit Application');
		
		var appID = application.appID;
		//alert("Application ID: " + appID);
		var temp = {};
		temp["appID"] = appID;
		
		$http({
			url : "views/editApplication",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//alert(data);
			$scope.appID = data.appID;
			//alert(data.appID);
			$scope.appName = data.appName;
			//alert(data.appName);
			$scope.description = data.description;
			//alert(data.description);
		});
	};
	
	$scope.updateApplication = function() {
//		alert('Update Application');
		
		var temp = {};
		var appID = $scope.appID;
		var appName = $scope.appName;
		var description = $scope.description;

//		alert('Application ID : ' + appID);
//		alert('Application Name : ' + appName);
//		alert('Application Description : ' + description);
		
		temp["appID"] = appID;
		temp["appName"] = appName;
		temp["description"] = description;
		
		//alert('Submitting Data for Update');
		
		$http({
			url : "views/updateApplication",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$http({
				url : "views/getAllApplications",
				method : "GET",
				headers : {
				'Content-Type' : 'application/json'}
			}).success(function(data) {
				$rootScope["AllApplications"] = data;
			});
			alert("Successfully Updated"+data);
		});
	};

}

function addFunctionController($scope, $http, $window, $rootScope) {
	$scope.setFunctionalId = function(thisFunction){
		$window.functionId = thisFunction.functionalID;
		var res={};
		res["functionalID"] = $window.functionId;
		 $http({
	            url : "views/getAllAppFeaturesByFunctionalId",
	            method : "POST",
	            data : res,
	            headers : {
	                    'Content-Type' : 'application/json'
	            }
	    }).success(function(data) {
	    	$rootScope["featureTableList"] = data;
	    });
	};
	
	
$scope.addFunction = function() {
	//alert('Add Function');
	var result = {};
	result["appID"] = $window.appID;
	var temp = {};
	var functionalName = $scope.addFunctionForm.functionalName.$viewValue;
	var description = $scope.addFunctionForm.description.$viewValue;
	
	temp["functionalName"] = functionalName;
	temp["description"] = description;
	temp["appID"] = $window.appID;
	$http({
		url : "views/addFunction",
		method : "POST",
		data : temp,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		$http({
			url : "views/getFunctionsForApplication",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			dataBaseData = data;
			$rootScope["AppFunctionsList"] = data;
		});
		alert("Successfully Persisted");
	});
};

$scope.editFunction = function(appfunctionality) {
	//alert('Edit Function');
	
	var functionalID = appfunctionality.functionalID;
	//alert("Function ID: " + functionalID);
	
	var temp = {};
	temp["functionalID"] = functionalID;
	
	$http({
		url : "views/editFunction",
		method : "POST",
		data : temp,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		$scope.functionalID = data.functionalID;
		$scope.functionalName = data.functionalName;
		$scope.description = data.description;
	});
};

$scope.updateFunction = function() {
//	alert('Update Function');
	var result = {};
	result["appID"] = $window.appID;
	var temp = {};
	var functionalID = $scope.functionalID;
	var functionalName = $scope.functionalName;
	var description = $scope.description;

//	alert('Function ID : ' + functionalID);
//	alert('Function Name : ' + functionalName);
//	alert('Function Description : ' + description);
	
	temp["functionalID"] = functionalID;
	temp["functionalName"] = functionalName;
	temp["description"] = description;
	
	$http({
		url : "views/updateFunction",
		method : "POST",
		data : temp,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		$http({
			url : "views/getFunctionsForApplication",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			dataBaseData = data;
			$rootScope["AppFunctionsList"] = data;
		});
		alert("Successfully Updated"+data);
	});
};
}
//$scope.getAllAppFeaturesByFunctionalId = function(appfunctionality) {
//
//	var functionalId = appfunctionality.functionalID;
//	alert("Function ID"+functionalId);
//	
//    $http({
//            url : "views/getAllAppFeaturesByFunctionalId",
//            method : "POST",
//            data : functionalId,
//            headers : {
//                    'Content-Type' : 'application/json'
//            }
//    }).success(function(data) {
////    		console.log(data);
//            $scope["featureTableList"] = data;
////            alert(data.featureName);
////            alert(data.description);
//    });
//};


//End : ***********Added By Firdos***********

//Start : Added By Gaurav

var app=angular.module('tfwUI', []);

function PaginationCtrl($scope,$rootScope) {
    $scope.currentPage = 0;
    $scope.pageSize = 12;
    $rootScope.TestScenariosBox = [];
    $scope.numberOfPages=function(){
        return Math.ceil($rootScope.TestScenariosBox.length/$scope.pageSize);                
    }
    for (var i=0; i<45; i++) {
    	$rootScope.TestScenariosBox.push("Item "+i);
    }
}

function schedulerPaginationCtrl($scope,$rootScope) {
    $scope.currentPage = 0;
    $scope.pageSize = 12;
    $rootScope.executionSuiteList = [];
    $scope.numberOfPages=function(){
        return Math.ceil($rootScope.executionSuiteList.length/$scope.pageSize);                
    }
    for (var i=0; i<45; i++) {
    	$rootScope.executionSuiteList.push("Item "+i);
    }
}

function applicationsTabPaginationCtrl($scope,$rootScope) {
    $scope.currentPage = 0;
    $scope.pageSize =12;
    $rootScope.AllApplications = [];
    $scope.numberOfPages=function(){
        return Math.ceil($rootScope.AllApplications.length/$scope.pageSize);                
    };
    for (var i=0; i< $rootScope.AllApplications.length; i++) {
    	$rootScope.AllApplications.push("Item "+i);
    }
}

function functionsTabPaginationCtrl($scope,$rootScope) {
    $scope.currentPage = 0;
    $scope.pageSize =12;
    $rootScope.AppFunctionsList = [];
    $scope.numberOfPages=function(){
        return Math.ceil($rootScope.AppFunctionsList.length/$scope.pageSize);                
    };
    for (var i=0; i< $rootScope.AppFunctionsList.length; i++) {
    	$rootScope.AppFunctionsList.push("Item "+i);
    }
}

function planAndSuitesPaginationCtrl($scope,$rootScope) {
    $scope.currentPage = 0;
    $scope.pageSize = 12;
    $rootScope.TestPlanList = [];
    $scope.numberOfPages=function(){
        return Math.ceil($rootScope.TestPlanList.length/$scope.pageSize);                
    };
    for (var i=0; i< $rootScope.TestPlanList.length; i++) {
    	$rootScope.TestPlanList.push("Item "+i);
    }
}

function conditionGrpPaginationCtrl($scope,$rootScope) {
    $scope.currentPage = 0;
    $scope.pageSize = 12;
    $rootScope.AppConditionGrp = [];
    $scope.numberOfPages=function(){
        return Math.ceil($rootScope.AppConditionGrp.length/$scope.pageSize);                
    };
    for (var i=0; i< $rootScope.AppConditionGrp.length; i++) {
    	$rootScope.AppConditionGrp.push("Item "+i);
    }
}

function paramGrpPaginationCtrl($scope,$rootScope) {
    $scope.currentPage = 0;
    $scope.pageSize = 12;
    $rootScope.paramGrpsForApp = [];
    $scope.numberOfPages=function(){
        return Math.ceil($rootScope.paramGrpsForApp.length/$scope.pageSize);                
    };
    for (var i=0; i<$rootScope.paramGrpsForApp.length; i++) {
    	$rootScope.paramGrpsForApp.push("Item "+i);
    }
}

function objectsTabPaginationCtrl($scope,$rootScope) {
    $scope.currentPage = 0;
    $scope.pageSize = 12;
    $rootScope.AppScreens = [];
    $scope.numberOfPages=function(){
        return Math.ceil($rootScope.AppScreens.length/$scope.pageSize);                
    };
    for (var i=0; i<$rootScope.AppScreens.length; i++) {
    	$rootScope.AppScreens.push("Item "+i);
    }
}


function IdentifierPaginationCtrl($scope,$rootScope) {
    $scope.currentPage = 0;
    $scope.pageSize = 12;
    $rootScope.IdentifierTypesBox = [];
    $scope.numberOfPages=function(){
        return Math.ceil($rootScope.IdentifierTypesBox.length/$scope.pageSize);                
    }
    for (var i=0; i<45; i++) {
    	$rootScope.IdentifierTypesBox.push("Item "+i);
    }
}


function ReplaceStringsPaginationCtrl($scope,$rootScope) {
    $scope.currentPage = 0;
    $scope.pageSize = 12;
    $rootScope.ReplaceStringsTypesBox = [];
    $scope.numberOfPages=function(){
        return Math.ceil($rootScope.ReplaceStringsTypesBox.length/$scope.pageSize);                
    }
    for (var i=0; i<45; i++) {
    	$rootScope.ReplaceStringsTypesBox.push("Item "+i);
    }
}

app.filter('startFrom', function() {
    return function(input, start) {
        start = +start;
        return input.slice(start);
    }
});


function addTestScenarioCtrl($scope, $http,$window,$rootScope) {
	
	$scope.closeTestScenario = function() {
		$scope.scenarioName='';
		$scope.description='';
		$scope.sortOrder='';
		$scope.testSuiteID='';
	};
	
	$scope.addTestScenario = function() {
		var result = {};
		var scenarioName = $scope.addTestScenarioForm.scenarioName.$viewValue;
		var description = $scope.addTestScenarioForm.description.$viewValue;
		var sortOrder = $scope.addTestScenarioForm.sortOrder.$modelValue;
		
		result["testScenarioName"] = scenarioName;
		result["description"] = description;
		result["sortOrder"] = sortOrder;
		result["appID"] = $window.appID;
		
		var testSuiteID = $scope.addTestScenarioForm.testSuiteID.$modelValue;
		result["testSuiteID"] = testSuiteID;
		
		$http({
			url : "views/addTestScenario",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//alert("Successfully Persisted data on testScenario and SuiteScenarioMapping table");
			$scope.scenarioName='';
			$scope.description='';
			$scope.sortOrder='';
			$scope.testSuiteID='';
			
			$http({
				url : "views/getTestScenariosBoxByAppId",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosBox"] = data;
				//alert("Successfully Landing on Scenario Page");
			});
			

		});

	};
	

}


function addTestCasesCtrl($scope, $http,$rootScope) {
	
	$http({
		url: "views/getRunnerNameList", 
		method: "GET",
		headers: {'Content-Type': 'application/json'}
		}).success(function(data) {
			
			$scope["runnerNameLists"] = data;
		});
	
	$scope.closeTestCases = function() {
		$scope.caseName='';
		$scope.description='';
		$scope.testScenarioID='';
		$scope.active='';
		$scope.classificationTag='';
		$scope.positive='';
		$scope.functionalID='';
		$scope.featureID='';
		$scope.sortOrder='';
		$scope.conditionGroupID='';
		$scope.runnerID='';
		
		$scope.stepName='';
		$scope.descriptions='';
		$scope.preConditionGroupID='';
		$scope.postConditionGroupID='';
		$scope.inputParamGroupID='';
		$scope.outputParamGroupID='';
		$scope.expectedResult='';
		$scope.testStepType='';
		$scope.active='';
		$scope.sortOrders='';
		$scope.actionID='';
	};
	
	$scope.addTestCases = function(testCaseID) {
		
		var result = {};
		
		var caseName = $scope.addTestCasesForm.caseName.$viewValue;
		var description = $scope.addTestCasesForm.description.$viewValue;
		var testScenarioID = $scope.addTestCasesForm.testScenarioID.$modelValue;
		var active = $scope.addTestCasesForm.active.$modelValue;
		var classificationTag = $scope.addTestCasesForm.classificationTag.$modelValue;
		var positive = $scope.addTestCasesForm.positive.$modelValue;
		var functionalID = $scope.addTestCasesForm.functionalID.$modelValue;
		var featureID = $scope.addTestCasesForm.featureID.$modelValue;
		var sortOrder = $scope.addTestCasesForm.sortOrder.$modelValue;
		var conditionGroupID = $scope.addTestCasesForm.conditionGroupID.$modelValue;
		var runnerID = $scope.addTestCasesForm.runnerID.$modelValue;
		
		result["caseName"] = caseName;
		result["description"] = description;
		result["testScenarioID"] = testScenarioID;
		result["active"] = active;
		result["classificationTag"] = classificationTag;
		result["positive"] = positive;
		result["functionalID"] = functionalID;
		result["featureID"] = featureID;
		result["sortOrder"] = sortOrder;
		result["conditionGroupID"] = conditionGroupID;
		result["runnerID"] = runnerID;
		//console.log(result);
		$http({
			url : "views/addTestCases",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
//			$scope.caseName='';
//			$scope.description='';
//			$scope.testScenarioID='';
//			$scope.active='';
//			$scope.classificationTag='';
//			$scope.positive='';
//			$scope.functionalID='';
//			$scope.featureID='';
//			$scope.sortOrder='';
//			$scope.conditionGroupID='';
//			$scope.runnerID='';
			
			//alert("Successfully Persisted");
			$rootScope.testCaseID = data;
			
			
		});

	};
	
	$scope.addTestStepApi = function() {
		var result = {};
		
		var stepName = $scope.addTestCasesForm.stepName.$viewValue;
		var description = $scope.addTestCasesForm.descriptions.$viewValue;
		var preConditionGroupID = $scope.addTestCasesForm.preConditionGroupID.$modelValue;
		var postConditionGroupID = $scope.addTestCasesForm.postConditionGroupID.$modelValue;
		var inputParamGroupID = $scope.addTestCasesForm.inputParamGroupID.$modelValue;
		var outputParamGroupID = $scope.addTestCasesForm.outputParamGroupID.$modelValue;
		var expectedResult = $scope.addTestCasesForm.expectedResult.$viewValue;
		var testStepType = $scope.addTestCasesForm.testStepType.$modelValue;
		var active = $scope.addTestCasesForm.active.$modelValue;
		var sortOrder = $scope.addTestCasesForm.sortOrders.$modelValue;
		var actionID = $scope.addTestCasesForm.actionID.$modelValue;
		result["testCaseID"] = $rootScope.testCaseID;
		result["stepName"] = stepName;
		result["description"] = description;
		result["preConditionGroupID"] = preConditionGroupID;
		result["postConditionGroupID"] = postConditionGroupID;
		result["inputParamGroupID"] = inputParamGroupID;
		result["outputParamGroupID"] = outputParamGroupID;
		result["expectedResult"] = expectedResult;
		result["testStepType"] = testStepType;
		result["active"] = active;
		result["sortOrder"] = sortOrder;
		result["actionID"] = actionID;
		
		//console.log(result);
		$http({
			url : "views/addTestStepsApi",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//alert("Successfully Persisted");
			$scope.stepName='';
			$scope.descriptions='';
			$scope.preConditionGroupID='';
			$scope.postConditionGroupID='';
			$scope.inputParamGroupID='';
			$scope.outputParamGroupID='';
			$scope.expectedResult='';
			$scope.testStepType='';
			$scope.active='';
			$scope.sortOrders='';
			$scope.actionID='';

			$scope.caseName='';
			$scope.description='';
			$scope.testScenarioID='';
			$scope.active='';
			$scope.classificationTag='';
			$scope.positive='';
			$scope.functionalID='';
			$scope.featureID='';
			$scope.sortOrder='';
			$scope.conditionGroupID='';
			$scope.runnerID='';
		});

	};
	
	$scope.getAppFeatureByFunctionalId = function() {
		
		var functionalID = $scope.addTestCasesForm.functionalID.$modelValue;
		
		$http({
			url : "views/getAllAppFeaturesListByFunctionalId",
			method : "POST",
			data : functionalID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope["FeatureNameList"] = data;
			
		});
	};
	
	
	
	
}


//function addTestStepApiCtrl($scope, $http,$rootScope) {
//	$scope.addTestStepApi = function() {
//		var result = {};
//		
//		var stepName = $scope.addTestStepApiForm.stepName.$viewValue;
//		var description = $scope.addTestStepApiForm.descriptions.$viewValue;
//		var preConditionGroupID = $scope.addTestStepApiForm.preConditionGroupID.$modelValue;
//		var postConditionGroupID = $scope.addTestStepApiForm.postConditionGroupID.$modelValue;
//		var inputParamGroupID = $scope.addTestStepApiForm.inputParamGroupID.$modelValue;
//		var outputParamGroupID = $scope.addTestStepApiForm.outputParamGroupID.$modelValue;
//		var expectedResult = $scope.addTestStepApiForm.expectedResult.$viewValue;
//		var testStepType = $scope.addTestStepApiForm.testStepType.$modelValue;
//		var active = $scope.addTestStepApiForm.active.$modelValue;
//		var sortOrder = $scope.addTestStepApiForm.sortOrders.$modelValue;
//		var actionID = $scope.addTestStepApiForm.actionID.$modelValue;
//		result["testCaseID"] = $rootScope.testCaseID;
//		result["stepName"] = stepName;
//		result["description"] = description;
//		result["preConditionGroupID"] = preConditionGroupID;
//		result["postConditionGroupID"] = postConditionGroupID;
//		result["inputParamGroupID"] = inputParamGroupID;
//		result["outputParamGroupID"] = outputParamGroupID;
//		result["expectedResult"] = expectedResult;
//		result["testStepType"] = testStepType;
//		result["active"] = active;
//		result["sortOrder"] = sortOrder;
//		result["actionID"] = actionID;
//		
//		//console.log(result);
//		$http({
//			url : "views/addTestStepsApi",
//			method : "POST",
//			data : result,
//			headers : {
//				'Content-Type' : 'application/json'
//			}
//		}).success(function(data) {
//			//alert("Successfully Persisted");
//			$scope.stepName='';
//			$scope.descriptions='';
//			$scope.preConditionGroupID='';
//			$scope.postConditionGroupID='';
//			$scope.inputParamGroupID='';
//			$scope.outputParamGroupID='';
//			$scope.expectedResult='';
//			$scope.testStepType='';
//			$scope.active='';
//			$scope.sortOrders='';
//			$scope.actionID='';
//
//			
//		});
//
//	};
//
//}

function getActionsListCtrl ($rootScope, $http) {
	var resultData;
	$http({
	url: "views/getActionList", 
	method: "GET",
	headers: {'Content-Type': 'application/json'}
	}).success(function(data) {
		resultData = data;
		$rootScope["actionLists"] = data;
	});
}

function getAllTestScenarios($scope, $http,$rootScope) {
	
	var resultData;
	$http({
		url : "views/getTestScenariosForApplication",
		method : "GET",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		
		resultData = data;
		$scope["AppTestScenarios"] = data;
		
	});

	
	$scope.editTestScenarios = function(testScenarios) {
		
		var result = {};
		var testScenarioID = testScenarios.testScenarioID;
		result["testScenarioID"] = testScenarioID;
		$http({
			url : "views/editTestScenarios",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.testScenarioID = data.testScenarioID;
			$scope.testScenarioName = data.testScenarioName;
			$scope.description = data.description;
			
			
		});
	};


	$scope.saveTestScenarios = function() {
		var temp = {};
		temp["testScenarioName"] = $scope.editTestScenariosForm.testScenarioName.$modelValue;
		temp["description"] = $scope.editTestScenariosForm.description.$modelValue;
		temp["testScenarioID"]= $scope.editTestScenariosForm.testScenarioID.$modelValue;
		$http({
			url : "views/updateTestScenarios",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//alert("Successfully Updated TestScenario");
			
			$http({
				url : "views/getTestScenariosBoxByAppId",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosBox"] = data;
				//alert("Successfully Landing on Scenario Page");
			});
			
		});
	};	
	
	$scope.getParameterForTestCaseTable = function(testScenarioID) {
		
		$http({
			url : "views/getParameterForTestCaseTable",
			method : "POST",
			data : testScenarioID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope["TestcaseList"] = data;
			
		});
	};
	
	
	$scope.editTestCases = function(testCases) {
		
		var result = {};
		var testCaseID = testCases.testCaseID;
		result["testCaseID"] = testCaseID;
		$http({
			url : "views/editTestCases",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.testCaseID = data.testCaseID;
			$scope.caseName = data.caseName;
			$scope.description = data.description;
			$scope.active = data.active;
			$scope.classificationTag = data.classificationTag;
			$scope.sortOrder = data.sortOrder;
			
			
		});
	};


	$scope.saveTestCases = function() {
		var temp = {};
		
		temp["caseName"] = $scope.editTestCasesForm.caseName.$modelValue;
		temp["description"] = $scope.editTestCasesForm.description.$modelValue;
		temp["testCaseID"]= $scope.editTestCasesForm.testCaseID.$modelValue;
		temp["active"]= $scope.editTestCasesForm.active.$modelValue;
		temp["classificationTag"]= $scope.editTestCasesForm.classificationTag.$modelValue;
		temp["sortOrder"]= $scope.editTestCasesForm.sortOrder.$modelValue;
		
		$http({
			url : "views/updateTestCases",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
		});
	};	
	
	
	$scope.getParameterForTestStepsTable = function(testCaseID) {
		
		$http({
			url : "views/getParameterForTestStepsTable",
			method : "POST",
			data : testCaseID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope["TestStepsList"] = data;
		});
	};
	
	
	
	$scope.editTestSteps = function(testSteps) {
		
		var result = {};
		var testStepID = testSteps.testStepID;
		result["testStepID"] = testStepID;
		$http({
			url : "views/editTestSteps",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.testStepID = data.testStepID;
			$scope.stepName = data.stepName;
			$scope.description = data.description;
			$scope.preConditionGroupID = data.preConditionGroupID;
			$scope.postConditionGroupID = data.postConditionGroupID;
			$scope.inputParamGroupID = data.inputParamGroupID;
			$scope.outputParamGroupID = data.outputParamGroupID;
			$scope.expectedResult = data.expectedResult;
			$scope.testStepType = data.testStepType;
			$scope.active = data.active;
			$scope.sortOrder = data.sortOrder;
			$scope.actionID = data.actionID;
			
			
		});
	};


	$scope.saveTestSteps = function() {
		var temp = {};
		temp["stepName"] = $scope.editTestStepsForm.stepName.$modelValue;
		temp["description"] = $scope.editTestStepsForm.description.$modelValue;
		temp["testStepID"]= $scope.editTestStepsForm.testStepID.$modelValue;
		temp["preConditionGroupID"]= $scope.editTestStepsForm.preConditionGroupID.$modelValue;
		temp["postConditionGroupID"]= $scope.editTestStepsForm.postConditionGroupID.$modelValue;
		temp["inputParamGroupID"]= $scope.editTestStepsForm.inputParamGroupID.$modelValue;
		temp["outputParamGroupID"]= $scope.editTestStepsForm.outputParamGroupID.$modelValue;
		temp["expectedResult"]= $scope.editTestStepsForm.expectedResult.$modelValue;
		temp["testStepType"]= $scope.editTestStepsForm.testStepType.$modelValue;
		temp["active"]= $scope.editTestStepsForm.active.$modelValue;
		temp["sortOrder"]= $scope.editTestStepsForm.sortOrder.$modelValue;
		temp["actionID"]= $scope.editTestStepsForm.actionID.$modelValue;
		
		$http({
			url : "views/updateTestSteps",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//alert("Successfully Updated TestCase");
			
		});
	};	
	
	
	$scope.getInputParamGroupNamesByParamId = function(inputParamGroupID) {
		
		$http({
			url : "views/getParamGroupNamesByParamId",
			method : "POST",
			data : inputParamGroupID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.inputParamGroupName = data.paramGroupName;
			
		});
	};
	
	$scope.getOutputParamGroupNamesByParamId = function(outputParamGroupID) {
		
		$http({
			url : "views/getParamGroupNamesByParamId",
			method : "POST",
			data : outputParamGroupID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.outputParamGroupName = data.paramGroupName;
			
		});
	};
	
	
	$scope.getConditionGroupByPreConditionId = function(preConditionGroupID) {
		
		$http({
			url : "views/getConditionGroupByConditionId",
			method : "POST",
			data : preConditionGroupID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.conditionPreGroupName = data.conditionGroupName;
			
		});
	};
	
	$scope.getConditionGroupByPostConditionId = function(postConditionGroupID) {
		
		$http({
			url : "views/getConditionGroupByConditionId",
			method : "POST",
			data : postConditionGroupID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.conditionPostGroupName = data.conditionGroupName;
			
		});
	};
	
	$scope.getActionsNameByActionId = function(actionsId) {

		$http({
			url : "views/getActionsNameById",
			method : "POST",
			data : actionsId,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.actionName = data.actionName;
			
		});
	};
	
	
}

function addIdentifierTypesCntrl($scope,$http, $window,$rootScope){
	
	$scope.closeIdentifierTypes = function() {
		$scope.identifierTypeName='';
		$scope.description='';
		
	};
	
	$scope.addIdentifierTypes = function(){
		var result={};
		result["identifierTypeName"] = $scope.addIdentifierTypesForm.identifierTypeName.$viewValue;
		result["description"] = $scope.addIdentifierTypesForm.description.$viewValue;
		result["appID"] = $window.appID;
		$http({
			url : "views/addIdentifierTypes",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.identifierTypeName='';
			$scope.description='';
			
			$http({
				url : "views/getIdentifierTypeByAppID",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["IdentifierTypesBox"] = data;
			});
		});
	};
}

function getAllIdentifierTypesListCntrl($scope,$http, $rootScope){
	$scope.editIdentifierType = function(IdentifierType){
		//alert("inside editIdentifierType");
		var id = IdentifierType.identifierTypeID;
		var temp = {};
		temp["identifierTypeID"] = id;
		$http({
			url : "views/editIdentifierType",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.identifierTypeID = data.identifierTypeID;
			$scope.identifierTypeName = data.identifierTypeName;
			$scope.description = data.description;
			
		});
	};
	
	$scope.updateIdentifierType = function(){
		var temp = {};
		temp["identifierTypeName"] = $scope.editIdentifierTypeForm.identifierTypeName.$modelValue;
		temp["description"] = $scope.editIdentifierTypeForm.description.$modelValue;
		temp["identifierTypeID"]= $scope.editIdentifierTypeForm.identifierTypeID.$modelValue;
		$http({
			url : "views/updateIdentifierType",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//alert("Successfully Updated TestScenario");
			$http({
				url : "views/getIdentifierTypeByAppID",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["IdentifierTypesBox"] = data;
			});
			
		});
		
	};
}



function addReplaceStringsCntrl($scope,$http, $window,$rootScope){
	
	$scope.closeReplaceStrings = function() {
		$scope.name='';
		$scope.value='';
		$scope.level='';
		$scope.foreign_ID='';
		
	};
	
	$scope.addReplaceStrings = function(){
		var result={};
		result["name"] = $scope.addReplaceStringsForm.name.$viewValue;
		result["value"] = $scope.addReplaceStringsForm.value.$viewValue;
		result["level"] = $scope.addReplaceStringsForm.level.$viewValue;
		result["foreign_ID"] = $scope.addReplaceStringsForm.foreign_ID.$viewValue;
		result["appID"] = $window.appID;
		console.log(result);
		$http({
			url : "views/addReplaceStringsData",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.name='';
			$scope.value='';
			$scope.level='';
			$scope.foreign_ID='';
			
			$http({
				url : "views/getReplacementStringsByAppID",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ReplaceStringsTypesBox"] = data;
			});
			
		});
	};
}


function getAllReplaceStringsListCntrl($scope,$http, $rootScope){
	$scope.editReplaceStrings = function(ReplacementStrings){
		
		var id = ReplacementStrings.id;
		var temp = {};
		temp["id"] = id;
		$http({
			url : "views/editReplaceStrings",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.id = data.id;
			$scope.name = data.name;
			$scope.value = data.value;
			$scope.level = data.level;
		});
	};
	
	$scope.updateReplaceStringsType = function(){
		var temp = {};
		temp["name"] = $scope.editReplaceStringsForm.name.$modelValue;
		temp["value"] = $scope.editReplaceStringsForm.value.$modelValue;
		temp["level"] = $scope.editReplaceStringsForm.level.$modelValue;
		temp["id"]= $scope.editReplaceStringsForm.id.$modelValue;
		$http({
			url : "views/updateReplaceStringsType",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//alert("Successfully Updated TestScenario");
			$http({
				url : "views/getReplacementStringsByAppID",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ReplaceStringsTypesBox"] = data;
			});
			
		});
		
	};
}




//End By Gaurav

// angular.Module('TFWUIApp', [])
function getFunctionalsCtrl($scope, $http) {
//	alert('getting Functionals from server..');
	var resultData;
	// var selectedFunctional = $scope.addTestSuiteForm.functions;
	// alert("Selected Function :: "+selectedFunctional);
	$http({
		url : "views/getFunctionals",
		method : "GET",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		resultData = data;
		$scope["functionals"] = data;
		//alert(data);

	});
}

//function getFeaturesCtrl($scope, $http) {
//	//alert('getting Features from server..');
//	var resultData;
//	// var selectedFunctional = $scope.addTestSuiteForm.features;
//	// alert("Selected Function :: "+selectedFunctional);
//	$http({
//		url : "views/getFeatures",
//		method : "GET",
//		headers : {
//			'Content-Type' : 'application/json'
//		}
//	}).success(function(data) {
//		resultData = data;
//		$scope["AppFeatures"] = data;
//		//alert(data);
//
//	});
//}




//Start: Added by Anand 

function addTestStepsCtrl($scope, $http, $filter, $rootScope, $window) {
 var path;
	$scope.setFiles = function(element) {
	    $scope.$apply(function($scope) {
	      console.log('files:', element.files);
	      // Turn the FileList object into an Array
	        $scope.files = [];
	        for (var i = 0; i < element.files.length; i++) {
	          $scope.files.push(element.files[i]);
	        }
	        
	     // scope.progressVisible = false;
	      });
	    };

	$scope.addTestSteps = function() {
	     if($("#projectPathId").val() == "Yes"){
			console.log("add java Runner Test step", $scope.addTestStepJRunnerForm);
			var svnUrl = $scope.addTestStepJRunnerForm.svnUrl.$viewValue;
			var userName = $scope.addTestStepJRunnerForm.userName.$viewValue;
			var password = $scope.addTestStepJRunnerForm.password.$viewValue;
			var targetDir = $scope.addTestStepJRunnerForm.targetPath.$viewValue;
			var testDataID=$scope.addTestStepJRunnerForm.testDataID.$modelValue;
			if(svnUrl==null || userName==null || password==null || targetDir==null){
	  			alert("Please enter all mandatory fields----1");
			
			}else{
	  		 
				alert('Submitting data to server.. Check the log to see data sent-Yes');
		        var result = {};
		        $.ajax({				    
				    success: function(data){				    	
						result["SVN"] = svnUrl;
						result["USERNAME"] = userName;
						result["PASSWORD"] = password;
						result["TARGETDIR"] = targetDir;
						result["testCaseID"] = $rootScope.testCaseID;
						result["AppParamPopUpsName"]= $window.appID;
						result["TestDataID"] = testDataID;
						alert('Result JSON:: ' + result+ $window.appID);
						console.log(result);
						
						$http({
							url : "views/addTestSteps",
							method : "POST",
							data : result,
							headers : {
								'Content-Type' : 'application/json'
							}
						}).success(function(data) {
							var temp=data.path;
							$('#"loader"').css({"display":"block"});
							//$scope.addTestScenarioForm.$setPristine();
							alert(data);
							var a= function() {
						        $scope.form.$setPristine();
						        $scope.model = '';
						    };

						}).beforeSend(function(){
							$('#"loader"').css({"display":"block"},{"text-align": "center"},{"position": "fixed"},
									{"top": "0px"},
									{"left": "0px"},
									{"height":"100%"},
									{"width":"100%"})
						}) ;
				    }
				  });
	        }
			
	 }else{
			
		var result = {};
		console.log("add java Runner Test step", $scope.addTestStepJRunnerForm);
		var targetDir = $scope.addTestStepJRunnerForm.targetPath.$viewValue;
		var testDataID=$scope.addTestStepJRunnerForm.testDataID.$modelValue;
		alert("testDataID"+testDataID);
		var projectPath=$scope.files;
		var fd = new FormData();
		if(projectPath==null || targetDir==null){
  			alert("Please enter all required fields---2");
  		  }else{
  			alert('Submitting data to server.. Check the log to see data sent-No');
	        for (var i in projectPath) {
	            fd.append("File", $scope.files[i]);
	       //     fd.append("svnUrl",svnUrl);
	        	var SrcPath = $scope.files[i];
	        	console.log(SrcPath);
	            alert('srcPathfiles'+$scope.files[i]+'SrcPath'+SrcPath);
	        }
	        $.ajax({
			    url: 'views/uploadfile',
			    data: fd,
			//    dataType: 'text',
			    processData: false,
			    contentType: false,
                forceSync: false,
                dataType: null,
			    type: 'POST',
			    success: function(data){
			    	result["SRCDIR"]=data;
					result["TARGETDIR"] = targetDir;
					result["testCaseID"] = $rootScope.testCaseID;
					result["AppParamPopUpsName"]=$window.appID;
					result["TestDataID"] = testDataID;
					alert('Result JSON:: ' + result+$window.appID);
					console.log(result);
					
					$http({
						url : "views/addTestSteps",
						method : "POST",
						data : result,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data) {
						var temp=data.path;
						$('#"loader"').css({"display":"block"});
						//$scope.addTestScenarioForm.$setPristine();
						alert(data);
						var a= function() {
					        $scope.form.$setPristine();
					        $scope.model = '';
					    };

					}).beforeSend(function(){
						$('#"loader"').css({"display":"block"},{"text-align": "center"},{"position": "fixed"},
								{"top": "0px"},
								{"left": "0px"},
								{"height":"100%"},
								{"width":"100%"})
					}) ;
			    }
			  });
		   }
		  $("#projectPathId").val("");
		}
	};

//Java API 

$scope.addJavaAPIdetails = function() {
	alert('Submitting data to server.. Check the log to see data sent');
	var result = {};
	console.log("add Test Case plan", $scope.addTestStepJRunnerForm);
	var classFileName = $scope.addTestStepJRunnerForm.classFileName.$viewValue;
	
	
  //testing :UI Data for java API: Start
	var fd = new FormData();
        for (var i in $scope.files) {
            fd.append("File", $scope.files[i]);
       //     fd.append("svnUrl",svnUrl);
        	var SrcPath = $scope.files[i];
            alert('srcPathfiles'+$scope.files[i]+'SrcPath'+SrcPath);
        }
        $.ajax({
		    url: 'views/uploadfile',
		    data: fd,
		//    dataType: 'text',
		    processData: false,
		    contentType: false,
            forceSync: false,
            dataType: null,
		    type: 'POST',
		    success: function(data){
		    	result["srcPath"]=data;
		    	path=data;
		    	alert("path"+path);
				result["classFileName"] = classFileName;
				
				
				alert('Result JSON:: ' + result);
				console.log(result);
				
				$http({
					url : "views/getClassDetails",
					method : "POST",
					data : result,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					alert(data);
					$scope["publicationData"]=data;
					

				}).beforeSend(function(){
					$('#"loader"').css({"display":"block"},{"text-align": "center"},{"position": "fixed"},
							{"top": "0px"},
							{"left": "0px"},
							{"height":"100%"},
							{"width":"100%"})
				}) ;
		    }
		  });

};

$scope.selectedMethods = function () {
    $scope.playList = $filter('filter')($scope.publicationData, {selected: true});
};

$scope.addJavaAPIvalues = function() {
	alert('Submitting data to server.. Check the log to see data sent');
	var classFileName = $scope.addTestStepJRunnerForm.classFileName.$viewValue;
	var testDataID=$scope.addTestStepJRunnerForm.testDataID.$modelValue;
	var resultdata = {};
	var result={};
	var index=1;
	//$scope.inputs = [];
	angular.forEach($scope.playList, function(value, key){
		
		var m;
		var mName;
		 // alert(key + ': ' + value);
		  angular.forEach(value, function(value, key){
		//  alert(key + ': ' + value);
		  if(value!=null){
		  if(key=="methodName"){
			   m=value;
			 //  alert(m);
		  }if(typeof(value )=="object"){
		  if(value.length>0){
			  if(key=="paramName"){
					var i=1;
					var keyName=key;        
				  angular.forEach(value, function(value, key){
			      //alert(key + ': ' + value);

				  //alert(m+'_'+i);
				  var inputVal=document.getElementById(m+'_'+i);
				  resultdata[keyName+'_'+m+'_'+i+'_'+index]=inputVal.value;
				 // alert(inputVal.value);
				  i++;});
			  }else if(key=="paramTypes"){
					var i=1;
					var keyName=key;        
				  angular.forEach(value, function(value, key){
			     // alert(key + ': ' + value);
				  resultdata[keyName+'_'+m+'_'+i+'_'+index]=value;
				  i++;});
			  }else{
				  var keyName1=key;
					var j=1;
				  angular.forEach(value, function(value, key){
					  if(typeof(value )=="object"){
						   mName=value.objMethodName;
						//  alert(mName);
							 angular.forEach(value, function(value, key){
								 if(typeof(value )=="object"){
								if(key=="objpName"){
									var i=1;
									var keyName=key;        
								  angular.forEach(value, function(value, key){
							     // alert(key + ': ' + value);

								 // alert(mName+'_'+i);
								  var inputVal=document.getElementById(mName);
								  resultdata[keyName+'_'+mName+'_'+i+'_'+index]=inputVal.value;
								 // alert(inputVal.value);
								  i++;});
							  }
							  else{
								  var keyName=key;
									var j=1;
								  angular.forEach(value, function(value, key){
								     // alert(keyName+'_'+j + ': ' + value);
								      resultdata[keyName+'_'+mName+'_'+j+'_'+index]=value;
										j++;
								  		});
							  }}
								 else{
									 if (key=="objpName" ){ 
											var k=1;  
											var keyName=key;      
									     // alert(key + ': ' + value);

										//  alert(mmName+'_'+k);
										  var inputVal=document.getElementById(mName);
										  resultdata[keyName+'_'+mName+'_'+k+'_'+index]=inputVal.value;
										 // alert(inputVal.value);
										  k++;
									  }else{
										  var keyName=key;
											var l=1;
										  resultdata[keyName+'_'+mName+'_'+l+'_'+index]=value;
											l++;
									  }
								 }
	});
						  }
					  else{
						 // var keyName=key;
							var j=1;
				     // alert(keyName1+'_'+j + ': ' + value);
				      resultdata[keyName1+'_'+m+'_'+j+'_'+index]=value;
						j++;
				  		
				  }
				  });
			  }
		  }}
		  else {
			  if (key=="paramName" ){ 
				var k=1;  
				var keyName=key;      
		     // alert(key + ': ' + value);

			 // alert(m+'_'+k);
			  var inputVal=document.getElementById(m+'_'+k);
			  resultdata[keyName+'_'+m+'_'+k+'_'+index]=inputVal.value;
			 // alert(inputVal.value);
			  k++;
		  }else{
			  var keyName=key;
				var l=1;
			  resultdata[keyName+'_'+m+'_'+l+'_'+index]=value;
				l++;
		  }
		  }
		  }
		});
		  index++;
		});
	alert(resultdata);
	result["METHODDATA"]=resultdata;
	result["testCaseID"] = $rootScope.testCaseID;
	result["AppParamPopUpsName"]=$window.appID;
	result["classFileName"]=classFileName;
	result["SRCDIR"]=path;
	result["TestDataID"]=testDataID;
	
	
	alert($scope.playList);
	$http({
		url : "views/addTestJavaApiSteps",
		method : "POST",
		data : result,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		alert(data);
	});
	

};

}


//End : Added by Anand

/** Import Excel **/

function importController($scope, $http, $window,$rootScope){
	$window.filesList = 0;
	$scope.setExcelFiles = function(element) {
	    $scope.$apply(function($scope) {
	        $scope.files = [];
	        for (var i = 0; i < element.files.length; i++) {
	          $scope.files.push(element.files[i]);
	       
	        }
	      //  alert($scope.files.length);
	      });
	 //   };	
	    $window.filesList  = $scope.files.length;
	// $scope.uploadExcel = function() {
	    var projectPath=$scope.files;
	  //  alert('projectPath :: '+projectPath);
	    var result = {};
	    var blnValid = false;
	    var fd = new FormData();
	    for (var i in projectPath) {
            fd.append("File", $scope.files[i]);
       //     fd.append("svnUrl",svnUrl);
        	var SrcPath = $scope.files[i];
        	var fileName =  $scope.files[i].name;
        	console.log(SrcPath);
        	if (fileName.length > 0) {
               
                    var sCurExtension =".xls";
                    if (fileName.substr(fileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                        blnValid = true;
                        break;
                    }
        	}
                    if (!blnValid) {
                        alert("Sorry, " + fileName + " is invalid, allowed extension is .xls");
                        return false;
                    }
                    
        //    alert('srcPathfiles'+$scope.files[i]+'SrcPath'+SrcPath);
        }
        $.ajax({
		    url:"views/uploadExcelUtil",
		    data: fd,
		//    dataType: 'text',
		    processData: false,
		    contentType: false,
            forceSync: false,
            dataType: null,
		    type: 'POST',
		    success: function(data){
		    	//result["SRCDIR"]=data;
				//result["TARGETDIR"] = targetDir;
			//	alert('Result JSON:: ' + result);
				
//				$http({
//					url : "views/addTestSteps",
//					method : "POST",
//					data : result,
//					headers : {
//						'Content-Type' : 'application/json'
//					}
//				}).success(function(data) {
//					var temp=data.path;
//					$('#"loader"').css({"display":"block"});
//					//$scope.addTestScenarioForm.$setPristine();
//					alert(data);
//					
//
//				}) ;
		    	//alert('Successfully uploaded');
		    }
		  });
		};
		
		$scope.importExcelSheet = function() {
			var temp = {};
			//alert('Start Validation');
			//alert($scope.files.length);
			var len = $window.filesList;
			if(len==0){
				alert("No File uploaded");
				return false;
			}
			var flag=0;
			var fName = $scope.importExcelForm.functionName.$viewValue;
			var planName = $scope.importExcelForm.testPlanName.$viewValue;
			for (var i=0;i<$rootScope.AppFuncPopUpsName.length; i++){
				if(fName==$rootScope.AppFuncPopUpsName[i].functionalName)
				flag=1;
			}
						
			for (var i=0;i<$rootScope.TestPlanList.length; i++){
				if(planName == $rootScope.TestPlanList[i].planName)
				flag=2;
			//	alert('Flag = '+flag);
			}
			if(flag==1){
				alert('Function Name already exists in The Application. Try with different Data');
				return false;
			}else if(flag==2){
				alert('TestPlan Name already exists in the Application. Try with different Data');
				return false;
			}
				
				//if(flag == 0){
				//alert('Flag = '+flag);
				temp["appID"] = $window.appID;
				temp["functionName"] = $scope.importExcelForm.functionName.$viewValue;
				temp["featureName"] = $scope.importExcelForm.featureName.$viewValue;
				temp["testPlanName"] = $scope.importExcelForm.testPlanName.$viewValue;
				temp["testSuiteName"] = $scope.importExcelForm.testSuiteName.$viewValue;
				temp["testScenarioName"] = $scope.importExcelForm.testScenarioName.$viewValue;
			//	temp["dataSetDescription"] = $scope.importExcelForm.dataSetDescription.$viewValue;
				$http({
					url : "views/importExcelSheetData",
					method : "POST",
					data : temp,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$http({
						url : "views/getTestExectionPlanId",
						method : "POST",
						data : $window.appID,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data) {
						$rootScope["planDetailsList"] = data.plan;
						$rootScope["dataDetailsList"] = data.data;
						
					});
					alert(data);
					
				});
			//}
			
		};
}

function importExcelController($scope, $http, $rootScope, $window){
	$window.excelFilesList = 0;
	$scope.setExcelFiles = function(element) {
	    $scope.$apply(function($scope) {
	    	$scope.files = [];
	        for (var i = 0; i < element.files.length; i++) {
	          $scope.files.push(element.files[i]);
	        }
	      //  alert($scope.files.length);
	      });
	   // };	
	    $window.excelFilesList  = $scope.files.length;
	// $scope.uploadExcel = function() {
	    var projectPath=$scope.files;
		  //  alert('projectPath :: '+projectPath);
		    var result = {};
		    var blnValid = false;
		    var fd = new FormData();
		    for (var i in projectPath) {
	            fd.append("File", $scope.files[i]);
	       //     fd.append("svnUrl",svnUrl);
	        	var SrcPath = $scope.files[i];
	        	var fileName =  $scope.files[i].name;
	        	console.log(SrcPath);
	        	if (fileName.length > 0) {
	               
	                    var sCurExtension =".xls";
	                    if (fileName.substr(fileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
	                        blnValid = true;
	                        break;
	                    }
	        	}
	                    if (!blnValid) {
	                        alert("Sorry, " + fileName + " is invalid, allowed extension is .xls");
	                        return false;
	                    }
	                    
	        //    alert('srcPathfiles'+$scope.files[i]+'SrcPath'+SrcPath);
	        }
        $.ajax({
		    url:"views/uploadExcelUtil",
		    data: fd,
		//    dataType: 'text',
		    processData: false,
		    contentType: false,
            forceSync: false,
            dataType: null,
		    type: 'POST',
		    success: function(data){
		    	//result["SRCDIR"]=data;
				//result["TARGETDIR"] = targetDir;
			//	alert('Result JSON:: ' + result);
				
//				$http({
//					url : "views/addTestSteps",
//					method : "POST",
//					data : result,
//					headers : {
//						'Content-Type' : 'application/json'
//					}
//				}).success(function(data) {
//					var temp=data.path;
//					$('#"loader"').css({"display":"block"});
//					//$scope.addTestScenarioForm.$setPristine();
//					alert(data);
//					
//
//				}) ;
		    	//alert('Successfully uploaded');
		    }
		  });
		};
		
	$scope.uploadExcelSheet = function() {
	var temp = {};
	var len = $window.excelFilesList;
	if(len==0){
		alert("No File uploaded");
		return false;
	}
	var appName = $scope.importExcelForm.applicationName.$viewValue;
	for (var i=0;i<$rootScope.AllApplications.length; i++){
		if(appName == $rootScope.AllApplications[i].appName){
			alert("Application Name already Exists.");
			return false;
		}
	}
	temp["applicationName"] = $scope.importExcelForm.applicationName.$viewValue;
	temp["functionName"] = $scope.importExcelForm.functionName.$viewValue;
	temp["featureName"] = $scope.importExcelForm.featureName.$viewValue;
	temp["testPlanName"] = $scope.importExcelForm.testPlanName.$viewValue;
	temp["testSuiteName"] = $scope.importExcelForm.testSuiteName.$viewValue;
	temp["testScenarioName"] = $scope.importExcelForm.testScenarioName.$viewValue;
	//temp["dataSetDescription"] = $scope.importExcelForm.dataSetDescription.$viewValue;
	$http({
		url : "views/uploadExcelSheetData",
		method : "POST",
		data : temp,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		alert(data);
		$http({
			url : "views/getAllApplications",
			method : "GET",
			headers : {
			'Content-Type' : 'application/json'}
			}).success(function(data) {
				dataBaseData = data;
				$rootScope["AllApplications"] = data;
			});
	
	});
};
}

// Changes from Ramya Start here
function addTestPlanCtrl($scope, $http, $window, $rootScope) {
	$scope.addTestPlan = function() {
		var result = {};
		var temp = {};
		temp["appID"] = $window.appID;
		var planName = $scope.addTestPlanForm.planName.$viewValue;
		var description = $scope.addTestPlanForm.description.$viewValue;
		result["planName"] = planName;
		result["description"] = description;
		result["appID"] = 	$window.appID;
		$http({
			url : "views/addTestPlan",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$http({
				url : "views/getTestPlansForApplication",
				method : "POST",
				data : temp,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				resultData = data;
				$rootScope["TestPlanList"] = data;
			});
			alert('Successfully Persisted');
			 $scope.form.$setPristine();
		        $scope.model = '';
		});

	};

}

function addTestSuiteCtrl($scope, $http, $window, $rootScope) {
	$scope.addTestSuite = function() {
		var result = {};
		var temp = {};
		temp["testPlanID"] = $window.testPlanId;
		var testSuiteName = $scope.addTestSuiteForm.testSuiteName.$viewValue;
		var testSuiteDescription = $scope.addTestSuiteForm.description.$viewValue;
		var sortOrder = $scope.addTestSuiteForm.sortOrder.$viewValue;
		result["suiteName"] = testSuiteName;
		result["description"] = testSuiteDescription;
		result["sortOrder"] = sortOrder;
		result["appID"] = 	$window.appID;
		result["testPlanID"] = $window.testPlanId;
		$http({
			url : "views/addTestSuite",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var appId = $window.appID;
			$http({
				url : "views/getTestSuitesForplan",
				method : "POST",
				data : temp,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$http({
					url : "views/getTestSuitesListFilterByAppId",
					method : "POST",
					data : appId,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["AppSuitesPopUpName"] = data;
				});
				$rootScope["planSuiteMappingList"] = data;
			});
			alert('Successfully Persisted');
		});
	};
}


function getAllTestPlans($scope, $http, $window, $rootScope) {
	
	$scope.setTestPlanId = function(testPlan){
		$window.testPlanId=testPlan.testPlanID;
			var result = {};
			result["testPlanID"] = $window.testPlanId;
			$http({
				url : "views/getTestSuitesForplan",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["planSuiteMappingList"] = data;
			});
	};
	
	$scope.editTestPlan = function(testPlan) {
		
		var planID = testPlan.testPlanID;
		var result = {};
		result["testPlanID"] = planID;
		$http({
			url : "views/editTestPlan",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.testPlanID = data.testPlanID;
			$scope.planName = data.planName;
			$scope.description = data.description;
		});
	};

	
	$scope.saveTestPlan = function() {
		var temp = {};
		var result = {};
		result["appID"] =  $window.appID;
		temp["appID"] = $window.appID;
		temp["planName"] = $scope.editTestPlanForm.planName.$viewValue;
		temp["description"] = $scope.editTestPlanForm.description.$viewValue;
		temp["testPlanID"]= $scope.editTestPlanForm.testPlanID.$viewValue;
		$http({
			url : "views/updateTestPlan",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$http({
				url : "views/getTestPlansForApplication",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				resultData = data;
				$rootScope["TestPlanList"] = data;
			});
			alert('Successfully Updated');
		});
	};	
	
}

function objectGrpCntrl($scope, $http, $window, $rootScope) {
	$scope.addObjectGrp = function() {
		var result = {};
		var screenID = $scope.addObjectGroupForm.screenID.$modelValue;
		var objGrpName = $scope.addObjectGroupForm.ObjGrpName.$viewValue;
		var objGrpDesc = $scope.addObjectGroupForm.ObjGrpDescription.$viewValue;
		result["screenID"]=screenID;
		result["objGrpName"]=objGrpName;
		result["objGrpDesc"]=objGrpDesc;
		result["appID"] = $window.appID;
		$http({
			url : "views/addObjGrp",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var temp = {};
			temp["screenID"] = $window.scrID;
			$http({
				url : "views/getObjGrpsForScreen",
				method : "POST",
				data : temp,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ObjGrpsForScreen"] = data;
			});
			alert("Successfully Persisted");
		});
	};
}

function objectsController($scope, $http, $window, $rootScope) {
	$scope.setScreenID = function(screen){
		$window.scrID = screen.screenID;
		var result = {};
		result["screenID"] = $window.scrID;
		$http({
			url : "views/getObjGrpsForScreen",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			resultData = data;
			$rootScope["ObjGrpsForScreen"] = data;
		});
	};
	$scope.editScreenDetails=function(screenID){
			var result = {};
			result["screenID"] = screenID;
			$http({
				url : "views/editScreenDetails",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$scope.screenID = data.screenID;
				$scope.screenName = data.screenName;
				$scope.description = data.description;
				$scope.prevFeatureName = data.prevFeatureName;
			});
	};
	
	$scope.saveScreenDetails=function(){
		var temp = {};
		temp["screenName"] = $scope.editScreenDetailsForm.screenName.$viewValue;
		temp["description"] = $scope.editScreenDetailsForm.description.$viewValue;
		//temp["featureID"]=$scope.editScreenDetailsForm.featureID.$modelValue;
		temp["screenID"]= $scope.editScreenDetailsForm.screenID.$viewValue;
		$http({
			url : "views/updateScreenDetails",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var temp = {};
			temp["appID"] = $window.appID;
			$http({
				url : "views/getScreensForApplication",
				method : "POST",
				data : temp,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["AppScreens"] = data;
			});
			alert("Successfully Updated");
		});
	};	
}

function testSuitesListCntrl($scope, $http, $window,$rootScope) {
	$scope.editTestSuite=function(tSuiteID){
		var result = {};
		result["testSuiteID"] = tSuiteID;
		$http({
			url : "views/editTestSuite",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.testSuiteID = data.testSuiteID;
			$scope.suiteName = data.suiteName;
			$scope.description = data.description;
			$scope.sortOrder = data.sortOrder;
		});
	};
	
	$scope.saveTestSuite=function(){
		var temp = {};
		temp["suiteName"] = $scope.editTestSuiteForm.suiteName.$viewValue;
		temp["description"] = $scope.editTestSuiteForm.description.$viewValue;
		temp["sortOrder"]=$scope.editTestSuiteForm.sortOrder.$viewValue;
		temp["testSuiteID"]= $scope.editTestSuiteForm.testSuiteID.$viewValue;
		$http({
			url : "views/updateTestSuite",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var result = {};
			result["testPlanID"] = $window.testPlanId;
			$http({
				url : "views/getTestSuitesForplan",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["planSuiteMappingList"] = data;
			});
			alert("Successfully Updated");
		});
	};	
	
}


function addNewScreenCntrl($scope, $http, $window,$rootScope){
	$scope.addNewScreen = function(){
		var result = {};
		result["featureID"] = $scope.addNewScreenForm.featureID.$modelValue;
		result["screenName"] = $scope.addNewScreenForm.screenName.$viewValue;
		result["description"] = $scope.addNewScreenForm.description.$viewValue;
		result["appID"] = $window.appID;
		$http({
			url : "views/addNewScreen",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var temp = {};
			temp["appID"] = $window.appID;
			$http({
				url : "views/getScreensForApplication",
				method : "POST",
				data : temp,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["AppScreens"] = data;
			});
			alert("Successfully Persisted");
		});
	};
}

function featuresListCntrl($scope, $http, $window,$rootScope){
	$scope.editAppFeature=function(feature){
		var result = {};
		//alert("editTestSuite "+tSuiteID);
		result["featureID"] = feature.featureID;
		$http({
			url : "views/editAppFeature",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.featureID = data.featureID;
			$scope.featureName = data.featureName;
			$scope.description = data.description;
			$scope.functionName = data.functionalID;
		});
	};
	
	$scope.updateAppFeature=function(){
		var temp = {};
		temp["featureName"] = $scope.editAppFeatureForm.featureName.$viewValue;
		temp["description"] = $scope.editAppFeatureForm.description.$viewValue;
		temp["functionalID"] = $scope.editAppFeatureForm.functionalID.$modelValue;
		temp["featureID"]= $scope.editAppFeatureForm.featureID.$viewValue;
		$http({
			url : "views/updateAppFeature",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var res={};
			res["functionalID"] = $window.functionId ;
			$http({
	            url : "views/getAllAppFeaturesByFunctionalId",
	            method : "POST",
	            data : res,
	            headers : {
	                    'Content-Type' : 'application/json'
	            }
		    }).success(function(data) {
		    	$rootScope["featureTableList"] = data;
		    });
			alert("Successfully Updated");
		});
	};	
}
//function actionCntrl($scope, $http) {
//	$scope.addAction = function() {
//		var result = {};
//		var actionName = $scope.addActionForm.actName.$viewValue;
//		var actionDesc = $scope.addActionForm.description.$viewValue;
//		result["actionName"]=actionName;
//		result["description"]=actionDesc;
//		$http({
//			url : "views/addAction",
//			method : "POST",
//			data : result,
//			headers : {
//				'Content-Type' : 'application/json'
//			}
//		}).success(function(data) {
//			alert("Successfully Persisted");
//		});
//	};
//}

function objectTypeCntrl($scope, $http) {
	//var resultData;
//	$http({
//		url : "views/getActions",
//		method : "GET",
//		headers : {
//			'Content-Type' : 'application/json'
//		}
//	}).success(function(data) {
//		//resultData = data;
//		$scope["ActionsList"] = data;
//	});
	
	$scope.addObjectType = function() {
		var result = {};
		var actionID = $scope.addObjectTypeForm.actionID.$modelValue;
		var objTypeName = $scope.addObjectTypeForm.ObjTypeName.$viewValue;
		var objTypeDescription = $scope.addObjectTypeForm.ObjTypeDescription.$viewValue;
		result["actionID"]=actionID;
		result["objectTypeName"]=objTypeName;
		result["description"]=objTypeDescription;
		$http({
			url : "views/addObjType",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$http({
				url : "views/getObjTypes",
				method : "GET",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				resultData = data;
				$rootScope["ObjTypeList"] = data;
			});
			alert("Successfully Persisted");
		});
	};
}

function getObjectGroupCtrl($scope, $http) {

}

//function getObjectTypeCtrl($scope, $http,$rootScope) {
//	$http({
//		url : "views/getObjTypes",
//		method : "GET",
//		headers : {
//			'Content-Type' : 'application/json'
//		}
//	}).success(function(data) {
//		resultData = data;
//		$rootScope["ObjTypeList"] = data;
//	});
//}

function getIdentifierTypeCtrl($scope, $http) {
//	$http({
//		url : "views/getIdentifierTypes",
//		method : "GET",
//		headers : {
//			'Content-Type' : 'application/json'
//		}
//	}).success(function(data) {
//		resultData = data;
//		$scope["IdentifiersList"] = data;
//	});
}

function addObjectDetailsCntrl($scope, $http, $window,$rootScope) {
	$scope.addObject = function() {
		var result={};
		var objGrpName = $scope.addObjectForm.objectGroupID.$modelValue;
		var objTypeName = $scope.addObjectForm.objectTypeID.$modelValue;
		var identifierTypeName = $scope.addObjectForm.identifierTypeID.$modelValue;
		var objName = $scope.addObjectForm.objectName.$viewValue;
		var objDesc = $scope.addObjectForm.description.$viewValue;
		var objIdentifier = $scope.addObjectForm.objIdentifier.$viewValue;
		result["objectName"]=objName;
		result["description"]=objDesc;
		result["objectGroupID"]=objGrpName;
		result["objectTypeID"]=objTypeName;
		result["identifierTypeID"]=identifierTypeName;
		result["identifier"] = objIdentifier;
		$http({
			url : "views/addObjectDetails",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var temp={};
			temp["objectGroupID"] = $window.objGrpId;
			$http({
				url : "views/getObjectsForObjectGrp",
				method : "POST",
				data : temp,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["objsForObjGrpID"] = data;
			});
			 alert("Successfully Persisted");
		});
		
	};
}

function  ObjGrpsListCntrl($scope, $http, $window,$rootScope) {
//	$http({
//		url : "views/getObjGrps",
//		method : "GET",
//		headers : {
//			'Content-Type' : 'application/json'
//		}
//	}).success(function(data) {
//		resultData = data;
//		$scope["ObjGrpList"] = data;
//	});
	
	$scope.getObjectsForObjGrp = function(objectGroupID){
		$window.objGrpId = objectGroupID;
		var result={};
		result["objectGroupID"] = $window.objGrpId;
		$http({
			url : "views/getObjectsForObjectGrp",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["objsForObjGrpID"] = data;
		});
	};
	
	
	$scope.editObjGrp=function(objectGroupID){
		var result = {};
		//	alert("editTestSuite "+tSuiteID);
			result["objectGroupID"] = objectGroupID;
			$http({
				url : "views/editObjGrp",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$scope.objectGroupID = data.objectGroupID;
				$scope.objectGroupName = data.objectGroupName;
				$scope.description = data.description;
				$scope.screenName = data.screenID;
			});
	};
	
	$scope.saveObjGrp=function(){
		var temp = {};
		temp["objectGroupName"] = $scope.editObjGrpForm.objectGroupName.$viewValue;
		temp["description"] = $scope.editObjGrpForm.description.$viewValue;
		temp["screenID"]=$scope.editObjGrpForm.screenID.$viewValue;
		temp["objectGroupID"]= $scope.editObjGrpForm.objectGroupID.$viewValue;
		$http({
			url : "views/updateObjGrp",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var res = {};
			res["screenID"] = $window.scrID;
			$http({
				url : "views/getObjGrpsForScreen",
				method : "POST",
				data : res,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ObjGrpsForScreen"] = data;
			});
			alert("Successfully Updated");
		});
	};	
	
}

function getScreensCntrl($scope, $http) {
//	$http({
//		url : "views/getScreens",
//		method : "GET",
//		headers : {
//			'Content-Type' : 'application/json'
//		}
//	}).success(function(data) {
//		$scope["AllScreens"] = data;
//	});
}
//
//function addParamGrpDetailsCntrl($scope, $http) {
//	$scope.addParamGrpDetails = function() {
//		var result={};
//		result["objectGroupID"]=$scope.addParamGrpDetailsForm.objectGroupID.$modelValue;
//		result["paramGroupName"]=$scope.addParamGrpDetailsForm.paramGroupName.$viewValue;
//		result["description"]=$scope.addParamGrpDetailsForm.description.$viewValue;
//		result["tag"]=$scope.addParamGrpDetailsForm.tag.$viewValue;
//		$http({
//			url : "views/addParamGrpDetails",
//			method : "POST",
//			data : result,
//			headers : {
//				'Content-Type' : 'application/json'
//			}
//		}).success(function(data) {
//			alert("Successfully Persisted");
//		});
//		
//	};
//}


function addFeatureCntrl($scope,$http){
	$scope.addFeature = function(){
		var result = {};
		result["functionalID"] = $scope.addFeatureForm.functionalID.$modelValue;
		result["featureName"] = $scope.addFeatureForm.featureName.$viewValue;
		result["description"] = $scope.addFeatureForm.description.$viewValue;
		$http({
			url : "views/addAppFeature",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			alert("Successfully Persisted");
		});
	};
}

function objectsListCntrl($scope,$http,$window,$rootScope){
	$scope.editObject=function(objectID){
		var result = {};
		result["objectID"] = objectID;
		$http({
			url : "views/editObject",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.objectID = data.objectID;
			$scope.objectName = data.objectName;
			$scope.description = data.description;
			$scope.objGrpName = data.objectGroupID;
			$scope.objTypeName = data.objectTypeID;
			$scope.identifierName = data.identifierTypeID;
		});
	};
	
	$scope.saveObjectDetails=function(){
		var temp = {};
		temp["objectName"] = $scope.editObjectDetailsForm.objectName.$viewValue;
		temp["description"] = $scope.editObjectDetailsForm.description.$viewValue;
		temp["identifierTypeID"]=$scope.editObjectDetailsForm.identifierTypeID;
		temp["objectTypeID"]= $scope.editObjectDetailsForm.objectTypeID;
		temp["objectGroupID"]= $scope.editObjectDetailsForm.objectGroupID;
		temp["objectID"]= $scope.editObjectDetailsForm.objectID.$viewValue;
		$http({
			url : "views/updateObject",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var result={};
			result["objectGroupID"] = $window.objGrpId;
			$http({
				url : "views/getObjectsForObjectGrp",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["objsForObjGrpID"] = data;
			});
			alert("Successfully Updated");
		});
	};	
}


function conditionGrpCntrl($scope,$http, $window, $rootScope){
	$scope.setConditionGrpID = function(conditionGrp){
		$window.conditionGrpId = conditionGrp.conditionGroupID;
		var temp = {};
		temp["conditionGroupID"] = $window.conditionGrpId;
		$http({
			url : "views/getConditionsForConditionGrp",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["conditionsListForGrp"] = data;
		});
	};
	
	$scope.editConditionGrpDetails = function(conditionGrp){
		var id = conditionGrp.conditionGroupID;
		var temp = {};
		temp["conditionGroupID"] = id;
		$http({
			url : "views/getConditionGroupById",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.conditionGroupName = data.conditionGroupName;
			$scope.description = data.description;
			$scope.conditionGroupID = data.conditionGroupID;
		});
	};
	
	$scope.updateConditionGroup = function(){
		var result= {};
		result["conditionGroupName"] = $scope.editConditionGrpForm.conditionGroupName.$viewValue;
		result["description"] = $scope.editConditionGrpForm.description.$viewValue;
		result["conditionGroupID"] = $scope.editConditionGrpForm.conditionGroupID.$viewValue;
		$http({
			url : "views/updateConditionGroupDetails",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var temp = {};
			temp["appID"] = $window.appID;
			$http({
				url : "views/getConditionGrp",
				method : "POST",
				data : temp,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				resultData = data;
				$rootScope["AppConditionGrp"] = data;
			});
			alert("Successfully Updated");
		});
	};
}


function addConditionGrpCntrl($scope,$http, $window, $rootScope){
	$scope.addConditionGroup = function(){
		var result={};
		result["conditionGroupName"] = $scope.addConditionGrpForm.conditionGroupName.$viewValue;
		result["description"] = $scope.addConditionGrpForm.description.$viewValue;
		result["appID"] = $window.appID;
		$http({
			url : "views/addConditionGroupDetails",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var temp = {};
			temp["appID"] = $window.appID;
			$http({
				url : "views/getConditionGrp",
				method : "POST",
				data : temp,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				resultData = data;
				$rootScope["AppConditionGrp"] = data;
			});
			
			alert("Successfully Persisted");
		});
	};
}

function conditionDataCntrl($scope,$http, $window, $rootScope){
	$scope.addConditionData = function(){
		var result={};
		result["conditionName"] = $scope.addNewConditionForm.conditionName.$viewValue;
		result["description"] = $scope.addNewConditionForm.description.$viewValue;
		result["expression"] = $scope.addNewConditionForm.expression.$viewValue;
		result["conditionGroupID"] = $scope.addNewConditionForm.conditionGroupID.$modelValue;
		$http({
			url : "views/addConditionData",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			alert("Successfully Persisted");
		});
	};
}

function conditionsListCntrl($scope,$http, $window, $rootScope){
	$scope.editCondition = function(condtn){
		var id = condtn.conditionID;
		var temp = {};
		temp["conditionID"] = id;
		$http({
			url : "views/getConditionDetailsById",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.conditionID = data.conditionID;
			$scope.conditionName = data.conditionName;
			$scope.description = data.description;
			$scope.conGrpName = data.conditionGroupID;
			$scope.expression =  data.expression;
		});
	};
	
	$scope.updateConditionData = function(){
		var result= {};
		var temp = {};
		temp["conditionGroupID"] = $window.conditionGrpId;
		result["conditionID"] = $scope.editConditionDetailsPopForm.conditionID.$viewValue;
		result["conditionName"] = $scope.editConditionDetailsPopForm.conditionName.$viewValue;
		result["description"] = $scope.editConditionDetailsPopForm.description.$viewValue;
		result["expression"] = $scope.editConditionDetailsPopForm.expression.$viewValue;
		result["conditionGrpID"] = $scope.editConditionDetailsPopForm.conditionGroupID.$modelValue;
		$http({
			url : "views/updateConditionDataDetails",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$http({
				url : "views/getConditionsForConditionGrp",
				method : "POST",
				data : temp,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["conditionsListForGrp"] = data;
			});
		});
		
//		success(function(data) {
//			$http({
//			url : "views/getDataDefinition",
//			method : "POST",
//			data : appID,
//			headers : {
//			'Content-Type' : 'application/json'
//			}
//			}).success(function(data) {
//			$rootScope["dataDefList"] = data;
//			alert("Data Updated Succesfully");
//			});
//
//			});
	};
}
function paramGrpCntrl($scope,$http, $window, $rootScope){
	$scope.setParamGrpID = function(paramGrp){
		$window.paramGrpID = paramGrp.paramGroupID;
		var temp = {};
		temp["paramGroupID"] = $window.paramGrpID;
		$http({
			url : "views/getParamDataForGrp",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["paramData"] = data;
		});

	};
	
	$scope.setObjectGroupID = function(paramGrp){
		$window.objectGrpId = paramGrp.objectGroupID;
		var temp = {};
		temp["objectGroupID"] = $window.objectGrpId;
		$http({
			url : "views/getObjectsForObjectGrp",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["objsForGrp"] = data;
		});

	};
	
	$scope.editParamGrp =function(paramGrp){
		var res = {};
		res["paramGroupID"] = paramGrp.paramGroupID;
		$http({
			url : "views/getParamGrpDetailsForID",
			method : "POST",
			data : res,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope["paramGroupName"] = data.paramGroupName;
			$scope["description"] = data.description;
			$scope["tag"] = data.tag;
			$scope["paramGroupID"] = data.paramGroupID;
			$scope["objGrpName"] = data.objectGroupID;
			
		});
	};
	
	$scope.updateParamGroup =function(){
		var result = {};
		result["paramGroupName"] = $scope.editParamGrpForm.paramGroupName.$viewValue;
		result["description"] = $scope.editParamGrpForm.description.$viewValue;
		result["objectGroupID"] = $scope.editParamGrpForm.objectGroupID.$modelValue;
		result["tag"] = $scope.editParamGrpForm.tag.$viewValue;
		result["paramGroupID"] = $scope.editParamGrpForm.paramGroupID.$viewValue;
		$http({
			url : "views/updateParamGrp",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var temp = {};
			temp["appID"] = $window.appID;
			$http({
				url : "views/getParamGrpsForApp",
				method : "POST",
				data : temp,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["paramGrpsForApp"] = data;
			});
			alert("Successfully Updated");
		});
	};
}

function addParamGrpCntrl($scope,$http, $window, $rootScope){
	$scope.addParamGroup = function(){
		var result = {};
		result["paramGroupName"] = $scope.addParamGrpForm.paramGroupName.$viewValue;
		result["description"] = $scope.addParamGrpForm.description.$viewValue;
		result["objectGroupID"] = $scope.addParamGrpForm.objGrpID.$modelValue;
		result["tag"] = $scope.addParamGrpForm.tag.$viewValue;
		result["appID"] = $window.appID;
		$http({
			url : "views/addNewParamGrp",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var temp = {};
			temp["appID"] = $window.appID;
			$http({
				url : "views/getParamGrpsForApp",
				method : "POST",
				data : temp,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["paramGrpsForApp"] = data;
			});
			alert("Successfully Persisted");
		});
	};
}

function paramDataListCntrl($scope,$http, $window, $rootScope){
	$scope.editParam = function(param){
		var temp = {};
		temp["paramID"] = param.paramID;
		$http({
			url : "views/getParamDetailsToUpdate",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope["paramName"] = data.paramName;
			$scope["description"] = data.description;
			$scope["sortOrder"] = data.sortOrder;
			$scope["paramGroupID"] = data.paramGroupID;
			$scope["objectID"] = data.objectID;
			$scope["paramID"] = data.paramID;
		});
	};
	
	$scope.updateParameter = function(){
		var temp = {};
		temp["paramName"] = $scope.editParamForm.paramName.$viewValue;
		temp["description"] = $scope.editParamForm.description.$viewValue;
		temp["sortOrder"] = $scope.editParamForm.sortOrder.$viewValue;
		temp["objectID"] = $scope.editParamForm.objectID.$modelValue;
		temp["paramGroupID"] = $scope.editParamForm.paramGroupID.$modelValue;
		temp["paramID"] = $scope.editParamForm.paramID.$viewValue;
		$http({
			url : "views/updateParam",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var res = {};
			res["paramGroupID"] = $window.paramGrpID;
			$http({
				url : "views/getParamDataForGrp",
				method : "POST",
				data : res,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["paramData"] = data;
			});
			alert("Successfully Persisted");
		});
	};
}

function addParameterPopCntrl($scope,$http, $window, $rootScope){
	$scope.addParameter = function(){
		var temp = {};
		temp["paramName"] = $scope.addParamForm.paramName.$viewValue;
		temp["description"] = $scope.addParamForm.description.$viewValue;
		temp["sortOrder"] = $scope.addParamForm.sortOrder.$viewValue;
		temp["objectID"] = $scope.addParamForm.objectID.$modelValue;
		temp["paramGroupID"] = $window.paramGrpID;
		$http({
			url : "views/addNewParam",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var res = {};
			res["paramGroupID"] = $window.paramGrpID;
			$http({
				url : "views/getParamDataForGrp",
				method : "POST",
				data : res,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["paramData"] = data;
			});
			alert("Successfully Persisted");
		});
		
	};
}

function importUtilCntrl($scope, $http, $window, $rootScope){
//	 $scope.complete = function(content) {
//	     // alert($("#avatar").val());
//	     alert( $('#avatar').val().replace(/^.*[\\\/]/, ''));
//	     var path = $('#avatar').val().replace(/^.*[\\\/]/, '');
//	     var path1 = $('#avatar').val();
//	     var res = {};
//	     res["path"] = path;
//	     res["path1"] = path1;
//	     $http({
//				url : "views/uploadExcelUtil",
//				method : "POST",
//				data : res,
//				headers : {
//					'Content-Type' : 'application/json'
//				}
//			}).success(function(data) {
//				alert("Successfully Persisted");
//			});
//	    };
	
	$scope.setExcelFiles = function(element) {
	    $scope.$apply(function($scope) {
	        $scope.files = [];
	        for (var i = 0; i < element.files.length; i++) {
	          $scope.files.push(element.files[i]);
	        }
	      //  alert($scope.files.length);
	      });
	    };	
	  
	 $scope.uploadExcel = function() {
	    var projectPath=$scope.files;
	  //  alert('projectPath :: '+projectPath);
	    var result = {};
	    var fd = new FormData();
	    for (var i in projectPath) {
            fd.append("File", $scope.files[i]);
       //     fd.append("svnUrl",svnUrl);
        	var SrcPath = $scope.files[i];
           // alert('srcPathfiles'+$scope.files[i]+'SrcPath'+SrcPath);
        }
        $.ajax({
		    url:"views/uploadExcelUtil",
		    data: fd,
		//    dataType: 'text',
		    processData: false,
		    contentType: false,
            forceSync: false,
            dataType: null,
		    type: 'POST',
		    success: function(data){
		    	//result["SRCDIR"]=data;
				//result["TARGETDIR"] = targetDir;
			//	alert('Result JSON:: ' + result);
				
//				$http({
//					url : "views/addTestSteps",
//					method : "POST",
//					data : result,
//					headers : {
//						'Content-Type' : 'application/json'
//					}
//				}).success(function(data) {
//					var temp=data.path;
//					$('#"loader"').css({"display":"block"});
//					//$scope.addTestScenarioForm.$setPristine();
//					alert(data);
//					
//
//				}) ;
		    	alert('Successfully uploaded');
		    }
		  });
		};
	  }
// Changes from Ramya End here

// Changes from Jagadish Starts here

function addTestExectionCtrl($scope, $http, $window, $rootScope) {
	$scope.addTestExecutionPlan = function() {
		// alert('Submitting data to server.. Check the log to see data sent');
		var result = {};
		var schedulerName = $scope.addTestExectionForm.schedulerName.$viewValue;
		var testExePlanName = $scope.addTestExectionForm.testPlanID.$modelValue;
		var testExeDataName = $scope.addTestExectionForm.testDataID.$modelValue;
		var testagentId = $scope.addTestExectionForm.agentID.$modelValue;
		var testSchedulerTime = $scope.addTestExectionForm.schedulerTime.$viewValue;
		var frequency = $scope.addTestExectionForm.frequency.$modelValue;
		var notification = $scope.addTestExectionForm.notification.$viewValue;
		var multiLane = false;
		result["schedulerName"] = schedulerName;
		result["testPlanID"] = testExePlanName.trim();
		result["testDataID"] = testExeDataName.trim();
		result["agentID"] = testagentId.trim();
		result["scheduleTime"] = testSchedulerTime;
		result["frequency"] = frequency.trim();
		result["notifications"] = notification;
		result["appID"] = $window.appID;
		//alert("agentID"+testagentId);
		var obj = document.getElementById('checkLane');
		if (obj.checked) {
			multiLane = true;
			var laneType = $scope.addTestExectionForm.laneType.$viewValue;
			var laneUserName = $scope.addTestExectionForm.laneUser.$viewValue;
			var testExeRunID = $scope.addTestExectionForm.testExeRunID.$modelValue;
			var noOfClones = $scope.addTestExectionForm.noOfClones.$viewValue;
			var noOfIterations = $scope.addTestExectionForm.noOfIterations.$viewValue;
			var rampDelay = $scope.addTestExectionForm.rampDelay.$viewValue;
			var duration = $scope.addTestExectionForm.duration.$viewValue;
			
			result["laneType"] = laneType;
			result["laneUserName"] = laneUserName;
			result["runnerType"] = testExeRunID.trim();
			result["clones"] = noOfClones;
			result["iterations"] = noOfIterations;
			result["rampUpDelay"] = rampDelay;
			result["duration"] = duration;

		}
		result["multiLanes"] = multiLane;
		// alert('Result JSON:: ' + result);
		$http({
			url : "views/addTestExection",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$http({
				url : "views/getTestExecutionRunSuites",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["executionSuiteList"] = data.suiteData;
				 alert("data insertion is successfull !!!");
			});
		});
		
	};

}

function ExecutionRunSuiteCtrl($scope, $http, $window) {
	
	$scope.executeNow = function(scheduleId,schedulerName,testExePlanName,testExeDataName,notification,multiLanes,agentId) {
		var scheduleDetails={};
		scheduleDetails["schedulerName"] = schedulerName;
		scheduleDetails["testPlanID"] = testExePlanName;
		scheduleDetails["testDataID"] = testExeDataName;
		scheduleDetails["notifications"] = notification;
		scheduleDetails["multiLanes"] = multiLanes;
		scheduleDetails["agentID"] = agentId;
		scheduleDetails["appID"] = $window.appID;
		
		$http({
			url : "views/updateSchedulerTime",
			method : "POST",
			data : scheduleDetails,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			alert("Your Test is Scheduled");
		});
	};
	
	$scope.editSchedulerDeatils = function(scheduleId) {
		
		//alert("scheduleId: "+scheduleId);
		
		$http({
			url : "views/editScheduler",
			method : "POST",
			data : scheduleId,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//console.log(data);
			schedulerDetails = data.schedulerDetails;
			//alert(data.scheduleTime);
			$scope.format = 'yyyy-MM-dd HH:mm:ss';
			$scope.schedulerName=schedulerDetails.schedulerName;
			$scope.notification=schedulerDetails.notifications;
			$scope.testPlanID=schedulerDetails.testPlanID;
			$scope.testDataID=schedulerDetails.testDataID;
			$scope.schedulerTime=data.scheduleTime;
			$scope.frequency=schedulerDetails.frequency;
			$scope.checkLaneEdit=schedulerDetails.multiLanes;
			$scope.testScheduleId=schedulerDetails.scheduleID;
			
			schedulerLaneDetails=data.schedulerLaneDetails;
			//console.log(schedulerLaneDetails);
			//alert(schedulerLaneDetails.laneUserName);
			
			if($scope.checkLaneEdit){
				$.each(schedulerLaneDetails,function(key,value){
					if(typeof value == "object"){
						//alert("laneType: " +value["laneType"]);
						
						$scope.laneType=value["laneType"];
						$scope.laneUser=value["laneUserName"];
						$scope.testExeRunID=value["runnerType"];
						$scope.noOfClones=value["clones"];
						$scope.noOfIterations=value["iterations"];
						$scope.rampDelay=value["rampUpDelay"];
						$scope.duration=value["duration"];
					}
				});
				
				$('#checkMultiLaneEdit').show();
			}
		});
	};
	$scope.saveTestExecutionPlan = function() {
		//alert("Save");
		var result = {};
		var schedulerName = $scope.editTestExectionForm.schedulerName.$viewValue;
		var testExePlanName = $scope.editTestExectionForm.testPlanID.$modelValue;
		var testExeDataName = $scope.editTestExectionForm.testDataID.$modelValue;
		var testSchedulerTime = $scope.editTestExectionForm.schedulerTime.$viewValue;
		var frequency = $scope.editTestExectionForm.frequency.$modelValue;
		var notification = $scope.editTestExectionForm.notification.$viewValue;
		var multiLane = $scope.checkLaneEdit;
		var testScheduleId =  $scope.editTestExectionForm.testScheduleId.$viewValue;
		
		result["schedulerName"] = schedulerName;
		result["testPlanID"] = testExePlanName.trim();
		result["testDataID"] = testExeDataName.trim();
		result["scheduleTime"] = testSchedulerTime;
		result["frequency"] = frequency.trim();
		result["notifications"] = notification;
		result["multiLanes"] = multiLane;
		result["scheduleID"] = testScheduleId;
		result["appID"] = $window.appID;
		
		if(multiLane){
			
			var laneType = $scope.editTestExectionForm.laneType.$viewValue;
			var laneUserName = $scope.editTestExectionForm.laneUser.$viewValue;
			var testExeRunID = $scope.editTestExectionForm.testExeRunID.$modelValue;
			var noOfClones = $scope.editTestExectionForm.noOfClones.$viewValue;
			var noOfIterations = $scope.editTestExectionForm.noOfIterations.$viewValue;
			var rampDelay = $scope.editTestExectionForm.rampDelay.$viewValue;
			var duration = $scope.editTestExectionForm.duration.$viewValue;
			
			//alert(laneType+laneUserName+testExeRunID.trim()+noOfClones+noOfClones+noOfIterations+rampDelay+duration);	
			result["laneType"] = laneType;
			result["laneUserName"] = laneUserName;
			result["runnerType"] = testExeRunID.trim();
			result["clones"] = noOfClones;
			result["iterations"] = noOfIterations;
			result["rampUpDelay"] = rampDelay;
			result["duration"] = duration;
			
		}
		
		$http({
			url : "views/updateTestExection",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$http({
				url : "views/getTestExecutionRunSuites",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["executionSuiteList"] = data.suiteData;
				 alert("data insertion is successfull !!!");
			});
		});
		
	
	};
	
	
}

function getDataDefListCtrl($scope, $http, $rootScope) {
	// alert('getting Suite values from DB');
	
	$scope.getEditingDataDef = function(testDataID) {
		$http({
			url : "views/getEditingDataDefinition",
			method : "POST",
			data : testDataID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$("#testDataStatus").val(1);
			$scope.testDataDesc=angular.copy(data.testDataDescription);
			$scope.testDataStatus=angular.copy(data.status);
			$scope.testDataId=angular.copy(data.testDataID);
			//$("#testDataStatus").val(data.status);
			
		});
	};
	
	$scope.editTestDataDefinition = function() {
		var result = {};
		var testDataDesc = $scope.editTestDefinitionForm.testDataDesc.$viewValue;
		var testDataStatus = $scope.editTestDefinitionForm.testDataStatus.$modelValue;
		var testDataID = $scope.editTestDefinitionForm.testDataId.$viewValue;
	
		result["testDataDescription"] = testDataDesc;
		result["status"] = testDataStatus.trim();
		result["testDataID"] = testDataID;
		
		$http({
			url : "views/updateEditDataDefinition",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$http({
				url : "views/getDataDefinition",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["dataDefList"] = data;
				alert("Data Updated Succesfully");
			});

		});
	};
	
	$scope.getParameterForDataTable = function(testDataID) {
		$http({
			url : "views/getParameterForDataTable",
			method : "POST",
			data : testDataID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope["paramTableList"] = data;
		});
	};
	
	
	$scope.editPameterData = function(testParamDataID) {
		
		$http({
			url : "views/getParameterDataForEdit",
			method : "POST",
			data : testParamDataID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.testParamValue=data.paramValue;
			$scope.testParamValueBig=data.valueBig;
			$scope.testParamGroupName=data.paramGroupID;
			$scope.testParamName=data.paramID;
			$scope.testParamDataId=data.testParamDataID;
			$scope.testDataId=data.testDataID;
		});
	};
	
	$scope.saveTestParamData = function() {

		var result = {};
		var testParamGroupName = $scope.editTestParamDataForm.testParamGroupName.$modelValue;
		var testParamName = $scope.editTestParamDataForm.testParamName.$modelValue;
		var testParamValue = $scope.editTestParamDataForm.testParamValue.$viewValue;
		var testParamValueBig = $scope.editTestParamDataForm.testParamValueBig.$viewValue;
		var testParamDataID = $scope.editTestParamDataForm.testParamDataId.$viewValue;
		var testDataID = $scope.editTestParamDataForm.testDataId.$viewValue;
		
		result["paramGroupID"] = testParamGroupName.trim();
		result["paramID"] = testParamName.trim();
		result["paramValue"] = testParamValue;
		result["valueBig"] = testParamValueBig;
		result["testParamDataID"] = testParamDataID;
		
		$http({
			url : "views/updateParameterData",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$http({
				url : "views/getParameterForDataTable",
				method : "POST",
				data : testDataID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				//console.log(data);
				$scope["paramTableList"] = data;
				alert("Test Parameter data updated Successfully");
			});
		});
	};
	
}


function addTestDefinitionCtrl($scope, $http, $window, $rootScope) {
	$scope.addTestDataDefinition = function() {
		var result = {};
		var testDataDesc = $scope.addTestDefinitionForm.testDataDesc.$viewValue;
		var testDataStatus = $scope.addTestDefinitionForm.testDataStatus.$modelValue;
		result["appID"] = $window.appID;
		
		result["testDataDescription"] = testDataDesc;
		result["status"] = testDataStatus.trim();
		$http({
			url : "views/addTestDataDefinition",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			 $http({
					url : "views/getDataDefinition",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(result) {
					$rootScope["dataDefList"] = result;
					alert("data insertion is successfull !!!");
				});
			 
		});
		

	};

}


function addTestParamDataCtrl($scope, $http, $window) {
	$scope.addTestParamData = function() {
		var result = {};
		var testParamGroupName = $scope.addTestParamDataForm.testParamGroupName.$modelValue;
		var testParamName = $scope.addTestParamDataForm.testParamName.$modelValue;
		var testParamValue = $scope.addTestParamDataForm.testParamValue.$viewValue;
		var testParamValueBig = $scope.addTestParamDataForm.testParamValueBig.$viewValue;

		result["paramGroupID"] = testParamGroupName.trim();
		result["paramID"] = testParamName.trim();
		result["paramValue"] = testParamValue;
		result["valueBig"] = testParamValueBig;
		
		$http({
			url : "views/addTestParameterData",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			 alert("data insertion is successfull !!!");

		});
	};
	

	$scope.getTestParamBasedOnGroup = function() {
		var testParamGroupId = $scope.addTestParamDataForm.testParamGroupName.$modelValue;
		//alert("testParamGroupName" +testParamGroupName);
		$http({
			url : "views/getTestParamBasedOnGroupDetails",
			method : "POST",
			data : testParamGroupId,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//$rootScope["ParamGroupList"] = data.paramGroup;
			//console.log(data);
			$scope["ParamList"] = data.param;
			
		});
		
	};

	
}

// Changes from Jagadish Ends here


function addFunctionCtrl($scope, $http) {
	$scope.addFunctionScenario = function() {
		//alert('Submitting data to server.. Check the log to see data sent');
		var result = {};
		//console.log("add Function plan", $scope.addFunctionForm);
		var functionName = $scope.addFunctionForm.functionName;
		var description = $scope.addFunctionForm.description;
		result["functionalName"] = functionName;
		result["description"] = description;
		//alert('Result JSON:: ' + result);
		//console.log(result);
		$http({
			url : "views/addFunctionScenario",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			// $scope.addTestScenarioForm.$setPristine();
			//alert(data);

		});

	};

}

//Changes from Fidal for Reports table
//function schedulerRunCall($scope, $http, $window) {
//	$http({
//		url : "views/getSchedulerRunDetails/"+$window.appID,
//		method : "GET",
//		headers : {
//			'Content-Type' : 'application/json'
//		}
//	}).success(function(data) {
//		$scope.jsons = data;
//	}).error(function(status) {
//		$scope.jsons = status;
//	});
//}

function tableCntrl($scope,$rootScope) {
		//$scope.reportClick = function($event) {
		Sbi.sdk.services.setBaseUrl({
			protocol : 'http',
			host : 'localhost',
			port : '8100',
			contextPath : 'SpagoBI'
		});
		
		var html = Sbi.sdk.api.getDocumentHtml({
			documentLabel : 'tfw_summary_ckpt',
			userId : 'biadmin',
			executionRole : '/spagobi/admin',
			parameters : {
				year : new Date().getFullYear(),
				app_id : appID
			},
			displayToolbar : true,
			displaySliders : false,
			iframe : {
				height : '850px',
				width : '90%',
				style : 'border: 0px;'
			}
		});
		document.getElementById('targetDiv').innerHTML = html;
		//};
}

function spagoCallOnLoad($scope) {
                Sbi.sdk.services.setBaseUrl({
                        protocol : 'http',
                        host : 'localhost',
                        port : '8100',
                        contextPath : 'SpagoBI'
                });
            
			   var cb = function(result, args, success) {
			   if(success === true) {
							   execTest3();
			   } else {
				   alert('ERROR: Wrong username or password');
			   }
			   };
			   
			   Sbi.sdk.api.authenticate({ 
			   params: {
			   user: 'biadmin'
			   , password: 'biadmin'
				   }
			   , callback: {
				   fn: cb
				   ,scope: this
				   ,args: {}
			   }
			   });

				var execTest3 = function() {
                var html = Sbi.sdk.api.getDocumentHtml({
                        documentLabel : 'HC_AppResult',
                        executionRole : '/spagobi/admin',
                        displayToolbar : false,
                        displaySliders : false,
                        iframe : {
                                height : '325px',
                                width : '100%',
                                style : 'border: 0px;'
                        }
                });
                document.getElementById('application_status_graph').innerHTML = html;
                };
}


function addUsersController($scope, $http) {
	$scope.addUsersRoles= function() {
	var temp = {};
	
	var username = $scope.addRolesForm.username.$viewValue;
	var password = $scope.addRolesForm.password.$viewValue;
	
	temp["username"] = username;
	temp["password"] = password;
	
	var authority = $scope.addRolesForm.authority.$modelValue;
	temp["authority"] = authority;
	
	var appName = $scope.addRolesForm.appName.$modelValue;
	//alert("appName **"+appName);
	temp["appName"] = appName;
	
	$http({
		url : "views/addUsers",
		method : "POST",
		data : temp,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		
	});
	alert('User Registered Successfully!!!');
	};
	
	
	$scope.addAppsRoles= function() {
		var temp = {};
		
		var username = $scope.addRolesForm.username.$viewValue;
		var password = $scope.addRolesForm.password.$viewValue;
		
		temp["username"] = username;
		temp["password"] = password;
		
		var authority = $scope.addRolesForm.authority.$modelValue;
		temp["authority"] = authority;
		
		var appName = $scope.addRolesForm.appName.$modelValue;
		//alert("appName **"+appName);
		temp["appName"] = appName;
		
		$http({
			url : "views/addApps",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
		});
		alert('Added an Application Successfully !!!');
		
	};
	
	$http({
	url : "views/getAllRoles",
	method : "GET",
	headers : {
	'Content-Type' : 'application/json'}
	}).success(function(data) {
		dataBaseData = data;
		$scope["AllRoles"] = data;
	});
	
	$http({
		url : "views/getAllApps",
		method : "GET",
		headers : {
		'Content-Type' : 'application/json'}
		}).success(function(data) {
			dataBaseData = data;
			$scope["AllApps"] = data;
		});
}
