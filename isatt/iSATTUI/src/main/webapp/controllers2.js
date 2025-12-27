var globalObj ;
// Start from Gaurav
var objectGroupIDs;
var selectedStatus;
var testDataIDsGlb;
var scheduleIds;
var groupList = new Array();
var app = null;
var groups = "";
var appIDforReport = 0; 

var App = angular.module('iTap', [ 'ngDragDrop' ]);

App.directive('chosen', function() {
	var linker = function(scope, element, attrs) {
		var list = attrs['chosen'];

		scope.$watch(list, function() {
			element.trigger("chosen:updated");
		});

		scope.$watch(attrs['ngModel'], function() {
			element.trigger('chosen:updated');
		});

		element.chosen();
	};

	return {
		restrict : 'A',
		link : linker
	};
});

App.directive('duplicate', function () {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function (viewValue) {
            	var duplicate1 = scope[attrs.duplicate];
            	var name = scope.$eval(attrs['attributename'])["name"];
                for ( var i = 0; i < duplicate1.length; i++) {
        			if(viewValue ==duplicate1[i][name]){
        				 ctrl.$setValidity('duplicate', false);
                         return undefined;
        				}
        		}
                ctrl.$setValidity('duplicate', true);
                return viewValue;
            });
        }
    };
});

App.directive('customrequired', function () {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function (viewValue) {
            	if(viewValue.length  < 1){
        			ctrl.$setValidity('customrequired', false);
                    	return undefined;
        			}
                ctrl.$setValidity('customrequired', true);
                return viewValue;
            });
        }
    };
});
function getSysDateCntrl($scope, $http, $window, $rootScope) {
	var s = "2012-10-16T17:57:28.556094Z";
    $scope.v = {
        Dt: Date.now(),
        sDt: s,
        DDt: Date.parse(s)
    };
}
var suiteName = new Array();
var caseName = new Array();
var planNameList = new Array();
var scenarioName = new Array();
var scenarioListName;

$(document).on("click", ".saveAddApplicationType", function() {
	app =$(this).parents("#applicationType").find('.typeWrapper .chosen-container span').text();
//	$('.applicationTypeUp').each(function(){
//		if($(this).find('select').length > 0){
//			app =$(this).find('select').val();
//		}
//	});
	if (groups!= null){
	groupList.push({apps:app , group:groups});
	}
	});


	$(document).on("click", ".saveAddGroupType", function() {
//	$('.groupTypeUp').each(function(){
//		if($(this).find('select').length > 0){
//			groups = $(this).find('select').val();
//			appList=[];
//		}	
//	});
		groups = $(this).parents(".typeContent").find('.typeWrapper .user_info.groupTypeUp span').text();
		alert(groups);
	});
	
	

function addApplicationControllerITAP($scope, $http, $window, $rootScope, $filter) {
	
	var queryString = location.search.substring(1);
	
	var keyValues = queryString.split('loggedInUser=');
	//alert(keyValues[1]);
	var guestName = keyValues[1];
	
	$http({
		url : "views/getAllApplicationsITAP",
		//method : "GET",
		method : "POST",
		data : guestName,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {

		$rootScope["AllApplications"] = data;
	});

	$window.selectedScenariosToBeLinked = [];
	$window.linkedScenariosForSuites = [];

	$scope.toggleScenario = function(item) {
		
		item.selected = !item.selected;
		if (item.selected == true) {
			$window.selectedScenariosToBeLinked.push(item.testScenarioID);
		}

		if (item.selected == false) {
			for ( var i = 0; i < $window.selectedScenariosToBeLinked.length; i++) {
				if ($window.selectedScenariosToBeLinked[i] == item.testScenarioID) {
					$window.selectedScenariosToBeLinked.splice(i, 1);
				}
			}

		}
	};

	$window.selectedSuites = [];
	$window.selectedSuitesToBeLinked = [];
	$window.suitesFromPopUpForScenarios = [];
	$scope.getSuitesFromPopUp = function(item) {
		
		item.selected = !item.selected;
		if (item.selected == true) {
			$window.suitesFromPopUpForScenarios.push(item.testSuiteID);
		}
		if (item.selected == false) {
			for ( var i = 0; i < $window.suitesFromPopUpForScenarios.length; i++) {
				if ($window.suitesFromPopUpForScenarios[i] == item.testSuiteID) {
					$window.suitesFromPopUpForScenarios.splice(i, 1);
				}
			}
		}
	};
	$window.selectedPlansToBeLinked = [];
	$scope.togglePlan = function(item) {
		item.selected = !item.selected;
		if (item.selected == true) {
			$window.selectedPlansToBeLinked.push(item.testPlanID);
		}
	
		if (item.selected == false) {
			for ( var i = 0; i < $window.selectedPlansToBeLinked.length; i++) {
				if ($window.selectedPlansToBeLinked[i] == item.testPlanID) {
					$window.selectedPlansToBeLinked.splice(i, 1);
				}
			}

		}
	};
	
	$window.casesFromPopUpForScenarios = [];
	$scope.getCaseFromPopUp = function(item) {
		
		item.selected = !item.selected;
		if (item.selected == true) {
			$window.casesFromPopUpForScenarios.push(item.testCaseID);
		}
		if (item.selected == false) {
			for ( var i = 0; i < $window.casesFromPopUpForScenarios.length; i++) {
				if ($window.casesFromPopUpForScenarios[i] == item.testCaseID) {
					$window.casesFromPopUpForScenarios.splice(i, 1);
				}
			}
		}
	};
	
	
	$scope.setAppId = function(appID) {

		$window.appID = appID;
		appIDforReport = appID;
		window.appIDs = appID;	
		$http({
			url : "views/getTestPlanBoxByAppIdITAPWithSuiteCount",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {

			$rootScope["TestPlansListWithSuiteCount"] = data;
			for(var i=0; i<data.length; i++) {
                planNameList.push(data[i]);
            }
			$rootScope.list4 = [];
            
			$window.linkedSuitesForPlan = [];

			$scope.hideMe = function() {
				return $rootScope.list4.length > 0;
			};

		});
		
		$http({
			url : "views/getTestPlanBoxByAppIdITAP",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["TestPlansList"] = data;
			$rootScope.plansForSuites = [];

		});
		
		$http({
			url : "views/getScreensForApplicationITAP",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["AppScreens"] = data;
		});
		$http({
			url : "views/getTestPlanWithNoSuiteCount",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["planWithNoSuite"] = data;
		});
		$http({
			url : "views/getSuiteWithNoScenarioCount",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["SuiteWithNoScenario"] = data;
		});
		$http({
			url : "views/getTestScenariosWithNoCaseCount",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["ScenariosWithNoCase"] = data;
		});
		$http({
			url : "views/getTestCasewithNoStepCount",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["CasewithNoStepCount"] = data;
		});

		$http({
			url : "views/getTestDataDefinitionList",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["dataDefList"] = data;
		});
		
		$http({
			url : "views/getTestDataDefinitionByActiveStatus",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["dataDefListByActiveStatus"] = data;
		});
		
		$http({
			url : "views/getTestDataDefinitionByINActiveStatus",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["dataDefListByINActiveStatus"] = data;
		});
		
		$http({
			url : "views/getTestSuitesListFilterByAppIdITAP",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {

			$rootScope["suitelist"] = data;
			for(var i=0; i<data.length; i++) {
                suiteName.push(data[i].suiteName);
             } 
			//$rootScope["suiteListName"] = suiteName;
			var suiteListName = suiteName;
			
			$rootScope.listOfSuites = [];

			$window.linkedSuitesForPlan = [];

			$scope.hideMe = function() {
				return $rootScope.listOfSuites.length > 0;
			};

		});
		
		$http({
			url : "views/getSuiteBoxByAppIdITAPWithPlanandScenarioCount",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["TestSuiteswithPlanAndScenarioCountList"] = data;

		});
		
		$http({
			url : "views/getObjectGroupBoxByAppIdITAP",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["objectGroupList"] = data;
			
		});

		$http({
			url : "views/getIdentifierTypeByAppIDITAP",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["IdentifierTypesBoxList"] = data;
		});
		
		$http({
			url : "views/getTestScenariosListsFilterByAppIdAndGetCount",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["TestScenariosAndCount"] = data;
			$rootScope.listScenarios = [];
			
			for(var i=0; i<data.length; i++) {
				scenarioName.push(data[i].testScenarioName);
             } 
			
			scenarioListName = scenarioName;
			
			$rootScope.hideMe = function() {
				//alert("inside hide me");
				return $rootScope.listScenarios.length > 0;
			};

		});
		
		$http({
			url : "views/getFunctionFilterByAppIdITAP",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["AppFuncPopUpsName"] = data;
		});
		
		$http({
			url : "views/getAppFunctionalNameByAppIdITAP",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {

			$rootScope["AppFuncPopUpsNameTestcase"] = data;
		});

		$http({
			url : "views/getConditionGrpITAP",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {

			$rootScope["AppConditionPopUpsName"] = data;
		});

		$http({
			url : "views/getCaseListFilterByAppIdGetCount",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["TestCasesAndCount"] = data;
			$rootScope.AddCaselist = [];
	
			for(var i=0; i<data.length; i++) {
				caseName.push(data[i].caseName);
             } 
			
			var caseListName = caseName;
			
			$rootScope.hideMe = function() {
				return $rootScope.AddCaselist.length > 0;
			};

		});

		$http({
			url : "views/getParamGroupNamesByAppIdITAP",
			method : "POST",
			data : appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {

			$rootScope["AppParamPopUpsNameITAP"] = data;
		});
		
		//Added by Jagadish - Starts
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
			url : "views/getAgentData",
			method : "GET",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//resultData = data;
			$rootScope["agentDataList"] = data;
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
			$rootScope["scheduledList"] = data;
		});
		
			$http({
				url : "views/getTestExectionRunId",
				method : "GET",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				//console.log(data);
				$rootScope["runnerList"] = data;
			});
		
  //Added by Jagadish - Ends
			$http({
				url : "views/getSchedulerRunDetails?runId="+appID+"&type=recent",
				method : "GET",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["jsons"]=[];
	            for(var i=0; i<data.length; i++)
                $rootScope["jsons"].push({
                    testRunID : data[i].testRunID,
                    result : ( data[i].result === true ) ? "PASS" : "FAIL",
                    startDateTime : $filter('date')(data[i].startDateTime,'medium')
                });
			}).error(function(status) {
				$scope.jsons = status;
			});

			//Reports on overview Tab
			Sbi.sdk.services.setBaseUrl({
				protocol : 'http',
				host : 'localhost',
				port : '8091',
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
			document.getElementById('overviewSpago').innerHTML = html;
	};
	
	$scope.addApplication = function(item) {
		var result = {};
		var appName = $scope.addApplicationForm.applicationName.$viewValue;
		var description = $scope.addApplicationForm.description.$viewValue;
		//alert("appNameÊÊ"+appName);
		//alert("description"+description);

		result["appName"] = appName;
		result["description"] = description;
		$http({
		url : "views/addApplicationITAP",
		method : "POST",
		data : result,
		headers : {
		'Content-Type' : 'application/json'
		}
		}).success(function(data) {
			$rootScope["AllApplications"] = data;
			$http({
				url : "views/getAllApplicationsITAP",
				//method : "GET",
				method : "POST",
				data : guestName,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {

				$rootScope["AllApplications"] = data;
			});
			alert("New Application" + data["appName"]+ "has been successfully added...");
		});
		};
		$scope.editApplication = function(AppId) {
			$window.editAppID = AppId;
			$http({
				url : "views/editApplicationiTAP",
				method : "POST",
				data : editAppID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$scope.editAppName = data.appName;
				$scope.appDescription = data.description;
				$scope.editAppID = data.appID;
			});
			};
			
			$scope.updateApplication = function(item) {
				//alert('update Application Controller invoked');
				var result = {};
				var appName = $scope.updateApplicationForm.editAppName.$viewValue;
				var description = $scope.updateApplicationForm.appDescription.$viewValue;
				var appID = $scope.updateApplicationForm.editAppID.$viewValue;
				//alert("appNameÊÊ"+appName);
				//alert("description"+description);
				//alert("appID "+appID);
				result["appName"] = appName;
				result["description"] = description;
				result["appID"] = appID;
				$http({
				url : "views/updateApplicationITAP",
				method : "POST",
				data : result,
				headers : {
				'Content-Type' : 'application/json'
				}
				}).success(function(data) {
					$rootScope["AllApplications"] = data;
					$http({
						url : "views/getAllApplicationsITAP",
						//method : "GET",
						method : "POST",
						data : guestName,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data) {

						$rootScope["AllApplications"] = data;
					});
					alert( data["appName"]+ " Application has been successfully updated...");
				});
				};	

}

function addTestScenarioCtrl($scope, $http, $window, $rootScope) {
	
	$scope.set_color = function(TestScenario) {
		if (TestScenario.testSuiteCount == 0 || TestScenario.testCasesCount==0) {
		return {
			color : 'red'
		};
		}
    };

	$scope.closeTestScenario = function() {
		$scope.testScenarioName='';
		$scope.description='';
		$scope.sortOrder='';
		//$rootScope.listOfSuites='';
		//$rootScope.AddCaselist='';
		//$scope.addTestScenarioForm.$setPristine();
		$(document).find(".linkedSuites ul li").remove();
		$(document).find(".linkedCases ul li").remove();
	};
	
	var suitestoBLinked;
	var casestoBLinked;
	$scope.getSuiteNames = function() {
		$(document).find(".linkedSuites ul li").remove();
		$(document).find(".linkedCases ul li").remove();
		 suitestoBLinked = new Array();
		 casestoBLinked = new Array();
		$scope.testScenarioName = [];
		$scope.description = [];
		$scope.sortOrder = [];
		$rootScope["suiteListName"] = suiteName;
		$rootScope["caseListName"] = caseName;
		 $rootScope["listOfSuitesForEdit"] = [];
		 $rootScope["listOfCasesForEdit"] = [];
		 
	};
	
	$scope.getListFromSuitePopUP = function() {
		
		 suitestoBLinked = new Array();
			$('#scenarioUp .linkedSuites ul li').each(function(){
				suitestoBLinked.push($(this).text().trim());
		});
		$rootScope.suiteListName1 = [];	
		var cont = 0;
		for ( var i = 0; i < $rootScope.suiteListName.length; i++) {
			
			for(var j=0;j<suitestoBLinked.length;j++){
				if(suitestoBLinked[j]==$rootScope.suiteListName[i]){
					cont=1;
				}
			}
			if(cont==0){
				$rootScope.suiteListName1.push($rootScope.suiteListName[i]);
			}
			cont=0;
		}
		$rootScope.suiteListName =$rootScope.suiteListName1;

	};
	
	$scope.getListFromCasePopUP = function() {
		
		casestoBLinked = new Array();
			$('#scenarioUp .linkedCases ul li').each(function(){
				casestoBLinked.push($(this).text().trim());
		});
		$rootScope.caseListName1 = [];	
		var counts = 0;
		for ( var i = 0; i < $rootScope.caseListName.length; i++) {
			
			for(var j=0;j<casestoBLinked.length;j++){
				if(casestoBLinked[j]==$rootScope.caseListName[i]){
					counts=1;
				}
			}
			if(counts==0){
				$rootScope.caseListName1.push($rootScope.caseListName[i]);
			}
			counts=0;
		}
		$rootScope.caseListName =$rootScope.caseListName1;

	};
	
	$scope.addNewTestScenario = function() {
		var result = {};
		var testScenarioName = $scope.addTestScenarioForm.testScenarioName.$viewValue;
		var description = $scope.addTestScenarioForm.description.$viewValue;
		var sortOrder = $scope.addTestScenarioForm.sortOrder.$viewValue;
		result["testScenarioName"] = testScenarioName;
		result["description"] = description;
		result["sortOrder"] = sortOrder;
		result["appID"] = $window.appID;
		$http({
			url : "views/addTestScenarioITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$scope.testScenarioName='';
			$scope.description='';
			$scope.sortOrder='';
			
			$http({
				url : "views/getTestScenariosListsFilterByAppIdAndGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosAndCount"] = data;

			});
			
			$http({
				url : "views/getSuiteWithNoScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["SuiteWithNoScenario"] = data;
			});
			
			$http({
				url : "views/getSuiteBoxByAppIdITAPWithPlanandScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestSuiteswithPlanAndScenarioCountList"] = data;

			});
			$http({
				url : "views/getTestScenariosWithNoCaseCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ScenariosWithNoCase"] = data;
			});
		});
	};
	
	$scope.addTestScenario = function() {
		var selectedSuites = new Array();
		$('.tableScenario .linkedSuites ul li').each(function(){
			selectedSuites.push($(this).text().trim());
		});
		
		var selectedCase = new Array();
		$('#linkedCasesForScenario ul li').each(function(){
			selectedCase.push($(this).text().trim());
		});
		
		var selSuite = {};
		selSuite["Suites"] = selectedSuites;
		//console.log("suites list in add scenario:"+selSuite["Suites"]);
		$http({
			url : "views/getTestSuiteIDsITAP",
			method : "POST",
			data : selSuite,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			var suitesSelectedfromPopUP = [];
			
			suitesSelectedfromPopUP.push(data);
			
			var selCases = {};
			selCases["Cases"] = selectedCase;
			//console.log("Cases list in add scenario:"+selCases["Cases"]);
			$http({
				url : "views/getTestCaseIDsITAP",
				method : "POST",
				data : selCases,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				
				var CasesSelectedfromPopUP = [];
				
				CasesSelectedfromPopUP.push(data);
			
		var result = {};
		var testScenarioName = $scope.addTestScenarioForm.testScenarioName.$viewValue;
		var description = $scope.addTestScenarioForm.description.$viewValue;
		var sortOrder = $scope.addTestScenarioForm.sortOrder.$viewValue;
		result["testScenarioName"] = testScenarioName;
		result["description"] = description;
		result["sortOrder"] = sortOrder;
		result["appID"] = $window.appID;
	
		result["linkedSuitesfromPopUp"] = suitesSelectedfromPopUP;
		
		result["linkedCasesfromPopUp"] = CasesSelectedfromPopUP;

		$window.newScenarioForSuite;

		$http({
			url : "views/addTestScenarioITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//console.log(data);
			$window.newScenarioForSuite = data;
			$scope.testScenarioName='';
			$scope.description='';
			$scope.sortOrder='';
			//$rootScope.listOfSuites='';
			//$rootScope.AddCaselist='';
			$(document).find(".linkedSuites ul li").remove();
			$(document).find(".linkedCases ul li").remove();
			$scope.addTestScenarioForm.$setPristine();
			$http({
				url : "views/getTestScenariosListsFilterByAppIdAndGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosAndCount"] = data;

			});
			

			$http({
				url : "views/getSuiteWithNoScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["SuiteWithNoScenario"] = data;
			});
			
			$http({
				url : "views/getSuiteBoxByAppIdITAPWithPlanandScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestSuiteswithPlanAndScenarioCountList"] = data;

			});
			$http({
				url : "views/getTestScenariosWithNoCaseCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ScenariosWithNoCase"] = data;
			});
			
		});
		});
		});
	};
	
	$scope.editTestScenarios = function(testScenarios) {
		var suiteListName = new Array();
		var caseListName = new Array();
		var result = {};
		var testScenarioID = testScenarios.testScenarioID;
		result["testScenarioID"] = testScenarioID;
		$http({
			url : "views/editTestScenariosITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.testScenarioID = data.testScenarioID;
			$scope.testScenarioName = data.testScenarioName;
			$scope.description = data.description;
			$scope.sortOrder = data.sortOrder;
			$rootScope["listOfSuitesForEdit"] = data.suiteList;
			$rootScope["listOfCasesForEdit"] = data.caseList;
			var count =0;
			 
			for(var i=0; i<suiteName.length;i++){
				for(var j=0;j<data.suiteList.length;j++){
				
					if(data.suiteList[j].suiteName==suiteName[i]){
						
						count=1;
					}	
				}
				if(count==0){
				suiteListName.push(suiteName[i]);
				}
				count=0;
			}
		
		 $rootScope["suiteListName"] =  suiteListName;
		 
		 var countForCase =0;
		 
			for(var i=0; i<caseName.length;i++){
				for(var j=0;j<data.caseList.length;j++){
				
					if(data.caseList[j].caseName==caseName[i]){
						
						countForCase=1;
					}	
				}
				if(countForCase==0){
					caseListName.push(caseName[i]);
				}
				countForCase=0;
			}
		
		 $rootScope["caseListName"] =  caseListName;
		 
			
		});
	};


	$scope.updateTestScenarios = function() {
		
		var selectedSuites = new Array();
		$('#updateLinkedSuitesForUpdateSce ul li').each(function(){
			selectedSuites.push($(this).text().trim());
		});
		
		var selectedCase = new Array();
		$('#updateLinkedCasesForUpdateSce ul li').each(function(){
			selectedCase.push($(this).text().trim());
		});
		
		var selSuite = {};
		selSuite["Suites"] = selectedSuites;
		//console.log("suites list in update scenario:"+selSuite["Suites"]);
		$http({
			url : "views/getTestSuiteIDsITAP",
			method : "POST",
			data : selSuite,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			var suitesSelectedfromPopUP = [];
			
			suitesSelectedfromPopUP.push(data);
			
			var selCases = {};
			selCases["Cases"] = selectedCase;
			//console.log("Cases list in update scenario:"+selCases["Cases"]);
			$http({
				url : "views/getTestCaseIDsITAP",
				method : "POST",
				data : selCases,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				
				var CasesSelectedfromPopUP = [];
				
				CasesSelectedfromPopUP.push(data);
				
		var temp = {};
		temp["testScenarioName"] = $scope.updateTestScenarioForm.testScenarioName.$modelValue;
		temp["description"] = $scope.updateTestScenarioForm.description.$modelValue;
		temp["testScenarioID"]= $scope.updateTestScenarioForm.testScenarioID.$modelValue;
		temp["sortOrder"]= $scope.updateTestScenarioForm.sortOrder.$modelValue;
		temp["linkedSuitesfromPopUp"] = suitesSelectedfromPopUP;
		temp["linkedCasesfromPopUp"] = CasesSelectedfromPopUP;
		
		$http({
			url : "views/updateTestScenariosITAP",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$http({
				url : "views/getTestScenariosListsFilterByAppIdAndGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosAndCount"] = data;

			});
			
			$http({
				url : "views/getSuiteWithNoScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["SuiteWithNoScenario"] = data;
			});
			
			$http({
				url : "views/getSuiteBoxByAppIdITAPWithPlanandScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestSuiteswithPlanAndScenarioCountList"] = data;

			});
			$http({
				url : "views/getTestScenariosWithNoCaseCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ScenariosWithNoCase"] = data;
			});
			
		});
			});
		});
	};
	
	
}

function addTestCasesCtrlITAP($scope, $http, $rootScope, $window) {
	
	$scope.set_color = function(item) {
		if (item.testScenariosCount == 0 || item.testStepsCount == 0) {
		return {
			color : 'red'
		};
		}
	};

	$http({
		url : "views/getRunnerNameListITAP",
		method : "GET",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {

		$scope["runnerNameLists"] = data;
	});

	$scope.getAppFeatureByFunctionalId = function() {

		var functionalID = $scope.addTestCasesFormITAP.functionalID.$modelValue;

		$http({
			url : "views/getAllAppFeaturesListByFunctionalIdITAP",
			method : "POST",
			data : functionalID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope["FeatureNameList"] = data;

		});
	};
	
	$scope.getAppFeatureByFunctionalIdInUpdateCase = function() {

		var functionalID = $scope.updateTestCasesFormITAP.functionalID.$modelValue;

		$http({
			url : "views/getAllAppFeaturesListByFunctionalIdITAP",
			method : "POST",
			data : functionalID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope["FeatureNameListUpdate"] = data;

		});
	};

	$scope.showRespectiveDivForSteps = function() {

		var runnerID = $scope.addTestCasesFormITAP.runnerID.$modelValue;

		if (runnerID == 1004) {
			$('.testStep_HTTP').show();
		} else if (runnerID == 1000) {

			$('.testStep_JUnit').show();
		} else {
			$('.testStep_Java').show();
		}
	};
	
	$scope.showRespectiveDivForStepsInUpdateCases = function() {

		var runnerID = $scope.updateTestCasesFormITAP.runnerID.$modelValue;

		if (runnerID == 1004) {
			$('.testStep_HTTP').show();
		} else if (runnerID == 1000) {

			$('.testStep_JUnit').show();
		} else {
			$('.testStep_Java').show();
		}
	};

	$scope.closeTestCase = function() {
		
		$scope.caseName='';
		$scope.description='';
		$scope.active='';
		$scope.classificationTag='';
		$scope.positive='';
		$scope.functionalID='';
		$scope.featureID='';
		$scope.sortOrder='';
		$scope.conditionGroupID='';
		$scope.runnerID='';
		$scope.listScenarios='';
		$scope.addTestCasesFormITAP.$setPristine();
	};
	
	$scope.actives = [{active:'Y'},{active:'N'} ];
	
	$scope.positives = [{positive:'true'},{positive:'false'} ];
	
	$scope.classificationTags = [{classificationTag:'smoke'},{classificationTag:'regression'} ];
	
	var scenariotoBLinked;
	$scope.getScenarioNames = function() {
		$(document).find(".linkedScenarios ul li").remove();
		scenariotoBLinked = new Array();
		$scope.caseName = [];
		$scope.description= [];
		$scope.active= [];
		$scope.classificationTag= [];
		$scope.positive= [];
		$scope.functionalID= [];
		$scope.featureID= [];
		$scope.sortOrder= [];
		$scope.conditionGroupID= [];
		$scope.runnerID= [];
		$rootScope["scenarioListName"] = scenarioName;
		$rootScope["listOfScenarioForEdit"] = [];
		$rootScope["listOfStepsForEdit"] = [];
		$rootScope["TestStepsList"] = []; 
	};
	
	$scope.getListFromScenarioPopUP = function() {
		
		scenariotoBLinked = new Array();
			$('#caseUp .linkedScenarios ul li').each(function(){
				scenariotoBLinked.push($(this).text().trim());
		});
		$rootScope.scenarioListName1 = [];	
		var counts = 0;
		for ( var i = 0; i < $rootScope.scenarioListName.length; i++) {
			
			for(var j=0;j<scenariotoBLinked.length;j++){
				if(scenariotoBLinked[j]==$rootScope.scenarioListName[i]){
					counts=1;
				}
			}
			if(counts==0){
				$rootScope.scenarioListName1.push($rootScope.scenarioListName[i]);
			}
			counts=0;
		}
		$rootScope.scenarioListName =$rootScope.scenarioListName1;

	};
	
	$scope.addNewTestCases = function() {
		var result = {};

		var caseName = $scope.addTestCasesFormITAP.caseName.$viewValue;
		var description = $scope.addTestCasesFormITAP.description.$viewValue;
		var active = $scope.addTestCasesFormITAP.active.$modelValue;
		var classificationTag = $scope.addTestCasesFormITAP.classificationTag.$modelValue;
		var positive = $scope.addTestCasesFormITAP.positive.$modelValue;
		var functionalID = $scope.addTestCasesFormITAP.functionalID.$modelValue;
		var featureID = $scope.addTestCasesFormITAP.featureID.$modelValue;
		var sortOrder = $scope.addTestCasesFormITAP.sortOrder.$modelValue;
		var conditionGroupID = $scope.addTestCasesFormITAP.conditionGroupID.$modelValue;
		var runnerID = $scope.addTestCasesFormITAP.runnerID.$modelValue;

		result["caseName"] = caseName;
		result["description"] = description;
		result["active"] = active;
		result["classificationTag"] = classificationTag;
		result["positive"] = positive;
		result["functionalID"] = functionalID;
		result["featureID"] = featureID;
		result["sortOrder"] = sortOrder;
		result["conditionGroupID"] = conditionGroupID;
		result["runnerID"] = runnerID;
		
		$http({
			url : "views/addTestCasesITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$scope.caseName='';
			$scope.description='';
			$scope.active='';
			$scope.classificationTag='';
			$scope.positive='';
			$scope.functionalID='';
			$scope.featureID='';
			$scope.sortOrder='';
			$scope.conditionGroupID='';
			$scope.runnerID='';
			$scope.addTestCasesFormITAP.$setPristine();
			
			$http({
				url : "views/getCaseListFilterByAppIdGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestCasesAndCount"] = data;

			});
			
			$http({
				url : "views/getTestCasewithNoStepCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["CasewithNoStepCount"] = data;
			});
			
			$http({
				url : "views/getTestScenariosWithNoCaseCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ScenariosWithNoCase"] = data;
			});

			$http({
				url : "views/getTestCasewithNoStepCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["CasewithNoStepCount"] = data;
			});
			
			$http({
				url : "views/getCaseListFilterByAppIdGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestCasesAndCount"] = data;

			});
			$http({
				url : "views/getTestScenariosListsFilterByAppIdAndGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosAndCount"] = data;

			});
			
		});
		
	};
	
	$scope.addTestCases = function() {
		
		var selectedscenario = new Array();
		$('#linkedScenarioForCase ul li').each(function(){
			selectedscenario.push($(this).text().trim());
		});
		
			var setScenario = {};
			setScenario["Scenario"] = selectedscenario;
			$http({
				url : "views/getTestScenarioIDsITAP",
				method : "POST",
				data : setScenario,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
	           
				var scenarioSelectedfromPopUP = [];
	            scenarioSelectedfromPopUP.push(data);
				

		var result = {};

		var caseName = $scope.addTestCasesFormITAP.caseName.$viewValue;
		var description = $scope.addTestCasesFormITAP.description.$viewValue;
		var active = $scope.addTestCasesFormITAP.active.$modelValue;
		var classificationTag = $scope.addTestCasesFormITAP.classificationTag.$modelValue;
		var positive = $scope.addTestCasesFormITAP.positive.$modelValue;
		var functionalID = $scope.addTestCasesFormITAP.functionalID.$modelValue;
		var featureID = $scope.addTestCasesFormITAP.featureID.$modelValue;
		var sortOrder = $scope.addTestCasesFormITAP.sortOrder.$modelValue;
		var conditionGroupID = $scope.addTestCasesFormITAP.conditionGroupID.$modelValue;
		var runnerID = $scope.addTestCasesFormITAP.runnerID.$modelValue;

		result["caseName"] = caseName;
		result["description"] = description;
		result["active"] = active;
		result["classificationTag"] = classificationTag;
		result["positive"] = positive;
		result["functionalID"] = functionalID;
		result["featureID"] = featureID;
		result["sortOrder"] = sortOrder;
		result["conditionGroupID"] = conditionGroupID;
		result["runnerID"] = runnerID;

		var stepsSelected = [];
		
		if ($rootScope["TestStepsList"] != null
				&& $rootScope["TestStepsList"] != 0) {
		for ( var i = 0; i <$rootScope["TestStepsList"].length; i++) {
			stepsSelected.push($rootScope["TestStepsList"][i].testStepID);
		}
		}
		
		result["linkedSteps"] = stepList1;
		result["scenarioSelectedfromPopUP"] = scenarioSelectedfromPopUP;
		
		$http({
			url : "views/addTestCasesITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$window.casesCreatedFrompopup = data;
			selectedscenario = new Array();
			$rootScope.testCaseID = data;
			
			$scope.caseName='';
			$scope.description='';
			$scope.active='';
			$scope.classificationTag='';
			$scope.positive='';
			$scope.functionalID='';
			$scope.featureID='';
			$scope.sortOrder='';
			$scope.conditionGroupID='';
			$scope.runnerID='';
			$scope.listScenarios='';
			$scope.addTestCasesFormITAP.$setPristine();
			
			$http({
				url : "views/getCaseListFilterByAppIdGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestCasesAndCount"] = data;

			});
			
			$http({
				url : "views/getTestCasewithNoStepCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["CasewithNoStepCount"] = data;
			});
			
			$http({
				url : "views/getTestScenariosWithNoCaseCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ScenariosWithNoCase"] = data;
			});

			$http({
				url : "views/getTestCasewithNoStepCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["CasewithNoStepCount"] = data;
			});
			
			$http({
				url : "views/getCaseListFilterByAppIdGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestCasesAndCount"] = data;

			});
			$http({
				url : "views/getTestScenariosListsFilterByAppIdAndGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosAndCount"] = data;

			});
		});
			});
	};
	
	$scope.editTestCases = function(testCases) {
		var scenarioListName = new Array();
		var result = {};
		var testCaseID = testCases.testCaseID;
		result["testCaseID"] = testCaseID;
		$http({
			url : "views/editTestCaseITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.testCaseID = data.testCaseID;
			$scope.caseName = data.caseName;
			$scope.description = data.description;
			$rootScope["listOfScenarioForEdit"] = data.scenarioList;
			$rootScope["listOfStepsForEdit"] = data.stepList;
			$scope.classificationTag = data.classificationTag;
			$scope.runnerID = data.runnerID;
			$scope.functionalID = data.functionalID;
			$scope.featureID = data.featureID;
			$scope.active = data.active;
			$scope.positive = data.positive;
			$scope.sortOrder = data.sortOrder;
			$scope.conditionGroupID = data.conditionGroupID;
			
			var count =0;
			 
			for(var i=0; i<scenarioName.length;i++){
				for(var j=0;j<data.scenarioList.length;j++){
				
					if(data.scenarioList[j].scenarioName==scenarioName[i]){
						
						count=1;
					}	
				}
				if(count==0){
					scenarioListName.push(scenarioName[i]);
				}
				count=0;
			}
		
		 $rootScope["scenarioListName"] =  scenarioListName;
		});
	};
	
	$scope.updateTestCases = function() {
		
		var selectedscenario = new Array();
		$('#linkedScenarioForCaseUpdate ul li').each(function(){
			selectedscenario.push($(this).text().trim());
		});
		
			var setScenario = {};
			setScenario["Scenario"] = selectedscenario;
			$http({
				url : "views/getTestScenarioIDsITAP",
				method : "POST",
				data : setScenario,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
	           
				var scenarioSelectedfromPopUP = [];
	            scenarioSelectedfromPopUP.push(data);
				
		
		var temp = {};
		
		temp["caseName"] = $scope.updateTestCasesFormITAP.caseName.$modelValue;
		temp["description"] = $scope.updateTestCasesFormITAP.description.$modelValue;
		temp["testCaseID"]= $scope.updateTestCasesFormITAP.testCaseID.$modelValue;
		temp["active"]= $scope.updateTestCasesFormITAP.active.$modelValue;
		temp["classificationTag"]= $scope.updateTestCasesFormITAP.classificationTag.$modelValue;
		temp["sortOrder"]= $scope.updateTestCasesFormITAP.sortOrder.$modelValue;
		temp["runnerID"]= $scope.updateTestCasesFormITAP.runnerID.$modelValue;
		temp["functionalID"]= $scope.updateTestCasesFormITAP.functionalID.$modelValue;
		temp["featureID"]= $scope.updateTestCasesFormITAP.featureID.$modelValue;
		temp["conditionGroupID"]= $scope.updateTestCasesFormITAP.conditionGroupID.$modelValue;
		temp["positive"]= $scope.updateTestCasesFormITAP.positive.$modelValue;
		
		var stepsSelected = [];
		if ($rootScope["TestStepsList"] != null
				&& $rootScope["TestStepsList"] != 0) {
		for ( var i = 0; i <$rootScope["TestStepsList"].length; i++) {
			stepsSelected.push($rootScope["TestStepsList"][i].testStepID);
		}
		}
		
		temp["linkedSteps"] = stepsSelected;
		temp["scenarioSelectedfromPopUP"] = scenarioSelectedfromPopUP;
		
		$http({
			url : "views/updateTestCasesITAP",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$http({
				url : "views/getCaseListFilterByAppIdGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestCasesAndCount"] = data;

			});
			
			$http({
				url : "views/getTestScenariosWithNoCaseCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ScenariosWithNoCase"] = data;
			});

			$http({
				url : "views/getTestScenariosListsFilterByAppIdAndGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosAndCount"] = data;

			});
			$http({
				url : "views/getTestCasewithNoStepCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["CasewithNoStepCount"] = data;
			});
			
			$http({
				url : "views/getTestCasewithNoStepCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["CasewithNoStepCount"] = data;
			});
			
			$http({
				url : "views/getCaseListFilterByAppIdGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestCasesAndCount"] = data;

			});
		});
		});
	};	
	
}
var stepList1 =[];
function addTestStepsCtrlITAP($scope, $http, $rootScope, $window) {
	
	$http({
		url : "views/getActionListITAP",
		method : "GET",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {

		$rootScope["actionListsITAP"] = data;
	});

	$scope.closeTestSteps = function() {
		
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
		
		$scope.addTestStepsForm.$setPristine();
	};
	
	$scope.actives = [{active:'Y'},{active:'N'} ];
	
	$scope.positives = [{positive:'true'},{positive:'false'} ];
	
	$scope.addTestStepHttpITAP = function() {

		var result = {};

		var stepName = $scope.addTestStepsForm.stepName.$viewValue;
		var description = $scope.addTestStepsForm.descriptions.$viewValue;
		var expectedResult = $scope.addTestStepsForm.expectedResult.$viewValue;
		var sortOrder = $scope.addTestStepsForm.sortOrders.$modelValue;
		var testStepType = $scope.addTestStepsForm.testStepType.$modelValue;
		var preConditionGroupID = $scope.addTestStepsForm.preConditionGroupID.$modelValue;
		var postConditionGroupID = $scope.addTestStepsForm.postConditionGroupID.$modelValue;
		var inputParamGroupID = $scope.addTestStepsForm.inputParamGroupID.$modelValue;
		var outputParamGroupID = $scope.addTestStepsForm.outputParamGroupID.$modelValue;
		var active = $scope.addTestStepsForm.active.$modelValue;
		var actionID = $scope.addTestStepsForm.actionID.$modelValue;
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

		$http({
			url : "views/addTestStepsHttp",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$http({
				url : "views/getAllStepsListByStepsID",
				method : "POST",
				data : data,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				stepList1.push(data[0]);
			});
			$rootScope["TestStepsList"]= stepList1;
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
			$scope.addTestStepsForm.$setPristine();
		});

	};
	
	$scope.selectedStatusJunit = function(selected){
		
		$window.selectedStatusJunit=selected;
	};
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
	    
	    $scope.closeTestStepsJunit = function() {
			
	    	$scope.dataID='';
			$scope.svnPath='';
			$scope.userJunit='';
			$scope.passwordJunit='';
			$scope.targetPath='';
			
			$scope.addTestStepsJunitForm.$setPristine();
		};
	    
	    
	$scope.addTestStepJunitITAP = function() {
		
	     if($window.selectedStatusJunit == "Active"){
	    	alert("Active");
			var svnUrl = $scope.addTestStepsJunitForm.svnPath.$viewValue;
	 		var userJunit = $scope.addTestStepsJunitForm.userJunit.$viewValue;
	 		var passwordJunit = $scope.addTestStepsJunitForm.passwordJunit.$viewValue;
			var targetDir = $scope.addTestStepsJunitForm.targetPath.$viewValue;;
			var testDataID=$scope.addTestStepsJunitForm.dataID.$modelValue;
		    var result = {};
		    result["appId"] = appIDforReport;
			result["svnUrl"] = svnUrl;
			result["userJunit"] = userJunit;
			result["passwordJunit"] = passwordJunit;
			result["targetDir"] = targetDir;
			result["testDataID"] = testDataID;
			alert(result);
			$http({
				url : "views/addTestStepsJunit",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				
				$http({
					url : "views/getAllStepsListByStepsID",
					method : "POST",
					data : data,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					stepList1.push(data[0]);
				});
				$rootScope["TestStepsList"]= stepList1;
				$scope.dataID='';
				$scope.svnPath='';
				$scope.userJunit='';
				$scope.passwordJunit='';
				$scope.targetPath='';
			})
			
	 }else{
		alert("InActive");
		var result = {};
		var targetDir = $scope.addTestStepsJunitForm.targetPath.$viewValue;;
		var testDataID=$scope.addTestStepsJunitForm.dataID.$modelValue;
		alert("testDataID"+testDataID);
		var projectPath=$scope.files;
		var fd = new FormData();
		    for (var i in projectPath) {
		        fd.append("File", $scope.files[i]);
		   //     fd.append("svnUrl",svnUrl);
		    	var SrcPath = $scope.files[i];
		    	console.log(SrcPath);
		        alert('srcPathfiles'+$scope.files[i]+'SrcPath'+SrcPath);
		    }
		    $.ajax({
			   url: 'views/uploadfileJunit',
			   data: fd,
			   processData: false,
			   contentType: false,
		       forceSync: false,
		       dataType: null,
			   type: 'POST',
			   success: function(data){
			    	result["appId"] = appIDforReport;
			    	result["srcDir"]=data;
					result["targetDir"] = targetDir;
					result["testDataID"] = testDataID;
					$http({
						url : "views/addTestStepsJunit",
						method : "POST",
						data : result,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data) {
						$http({
							url : "views/getAllStepsListByStepsID",
							method : "POST",
							data : data,
							headers : {
								'Content-Type' : 'application/json'
							}
						}).success(function(data) {
							stepList1.push(data[0]);
						});
						$rootScope["TestStepsList"]= stepList1;
						$scope.dataID='     ';
						$scope.svnPath='    ';
						$scope.userJunit='    ';
						$scope.passwordJunit='   ';
						$scope.targetPath='    ';
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
	};
	
	$scope.addTestStepJavaITAP = function() {
		var result = {};
		var classFileName = $scope.addTestStepsJavaForm.classFile.$viewValue;
		var testDataID=$scope.addTestStepsJavaForm.dataID.$modelValue;
		
	  //testing :UI Data for java API: Start
		var fd = new FormData();
	        for (var i in $scope.files) {
	            fd.append("File", $scope.files[i]);
	       //     fd.append("svnUrl",svnUrl);
	        	var SrcPath = $scope.files[i];
	        }
	        $.ajax({
			    url: 'views/uploadfileJunit',
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
					result["classFileName"] = classFileName;
					
					
					console.log(result);
					
					$http({
						url : "views/getClassDetails",
						method : "POST",
						data : result,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data) {
						$scope["publicationData"]=data;
						
						var resultdata = {};
						var result={};
						var index=1;
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
						result["METHODDATA"]=resultdata;
						result["testCaseID"] = $rootScope.testCaseID;
						result["AppParamPopUpsName"]=$window.appID;
						result["classFileName"]=classFileName;
						result["SRCDIR"]=path;
						result["TestDataID"]=testDataID;
						
						
						$http({
							url : "views/addTestJavaApiSteps",
							method : "POST",
							data : result,
							headers : {
								'Content-Type' : 'application/json'
							}
						}).success(function(data) {
								alert(data);
								$http({
									url : "views/getAllStepsListByStepsID",
									method : "POST",
									data : data,
									headers : {
										'Content-Type' : 'application/json'
									}
								}).success(function(data) {
									stepList1.push(data[0]);
								});
								$rootScope["TestStepsList"]= stepList1;
						});
						


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
	
	
//	$scope.addTestStepJunitITAP = function() {
//		
//		var result = {};
//		var dataDesc = $scope.addTestStepsJunitForm.dataID.$modelValue;
//		var svnPath = $scope.addTestStepsJunitForm.svnPath.$viewValue;
//		var userJunit = $scope.addTestStepsJunitForm.userJunit.$viewValue;
//		var passwordJunit = $scope.addTestStepsJunitForm.passwordJunit.$viewValue;
//		var targetPath = $scope.addTestStepsJunitForm.targetPath.$viewValue;
//		result["dataDesc"] = dataDesc;
//		result["svnPath"] = svnPath;
//		result["checkout"] = $window.selectedStatusJunit;
//		result["userJunit"] = userJunit;
//		result["passwordJunit"] = passwordJunit;
//		result["targetPath"] = targetPath;
//		$http({
//			url : "views/addTestStepsJunit",
//			method : "POST",
//			data : result,
//			headers : {
//				'Content-Type' : 'application/json'
//			}
//		}).success(function(data) {
//			
//			$http({
//				url : "views/getAllStepsListByStepsID",
//				method : "POST",
//				data : data,
//				headers : {
//					'Content-Type' : 'application/json'
//				}
//			}).success(function(data) {
//				stepList1.push(data[0]);
//			});
//			$rootScope["TestStepsList"]= stepList1;
//			$scope.dataDesc='';
//			$scope.svnPath='';
//			$scope.userJunit='';
//			$scope.passwordJunit='';
//			$scope.targetPath='';
//			$scope.addTestStepsJunitForm.$setPristine();
//		});
//
//	};
//	
	
}

function addIdentifierTypesCntrlITAP($scope,$http, $window,$rootScope){
	
	$scope.closeIdentifierTypes = function() {
		$scope.identifierTypeName='';
		$scope.description='';
		$scope.addIdentifierTypesForm.$setPristine();
	};
	
	$scope.addIdentifierTypes = function(){
		var result={};
		result["identifierTypeName"] = $scope.addIdentifierTypesForm.identifierTypeName.$viewValue;
		result["description"] = $scope.addIdentifierTypesForm.description.$viewValue;
		result["appID"] = $window.appID;
		$http({
			url : "views/addIdentifierTypesITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.identifierTypeName='';
			$scope.description='';
			$scope.addIdentifierTypesForm.$setPristine();
			$http({
				url : "views/getIdentifierTypeByAppIDITAP",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["IdentifierTypesBoxList"] = data;
			});
		});
	};
	
	$scope.editIdentifierType = function(IdentifierType){
		
		var id = IdentifierType.identifierTypeID;
		var temp = {};
		temp["identifierTypeID"] = id;
		$http({
			url : "views/editIdentifierTypeITAP",
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
			url : "views/updateIdentifierTypeITAP",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//alert("Successfully Updated TestScenario");
			$scope.identifierTypeName='';
			$scope.description='';
			
			$http({
				url : "views/getIdentifierTypeByAppIDITAP",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["IdentifierTypesBoxList"] = data;
			});
			
		});
		
	};
	
}


function addTestDefinitionCtrl($scope, $http, $window, $rootScope) {
	
	$scope.closeTestDataDefinition = function() {
		
		$scope.testDataDescription='';
		$scope.status='';
	};
	
	$scope.addTestDataDefinition = function() {
		
		var result = {};
		var testDataDescription = $scope.addTestDefinitionForm.testDataDescription.$viewValue;
		var status = $scope.addTestDefinitionForm.status.$modelValue;
		result["appID"] = $window.appID;
		
		var paramDataArr = [];
		$('#newTestDataParamTable').find('tr').each(function(){
			
			var paramrowArr = [];
			$(this).find('td').each(function(){
				if($(this).find('select').length > 0){
					if($(this).find('select').val() != "" && $(this).find('select').val() != undefined && $(this).find('select').val() != null){
						paramrowArr.push($(this).find('select').val().trim());
					}else{
						paramrowArr.push("");
					}
					
					
				}else if($(this).find('input:text').length > 0){
					if($(this).find('input:text').val() != "" && $(this).find('input:text').val() != undefined && $(this).find('input:text').val() != null){
						paramrowArr.push($(this).find('input:text').val().trim());
					}else{
						paramrowArr.push("");
					}
				}

			});
			
			paramDataArr.push(paramrowArr);
			//alert(paramDataArr);
			
		});
		//console.log("paramDataArr",paramDataArr);
		result["paramDataArr"] = paramDataArr;		
		result["testDataDescription"] = testDataDescription;
		result["status"] = status;
		$http({
			url : "views/addTestDataDefITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$scope.testDataDescription='';
			$scope.status='';
			
			$http({
				url : "views/getTestDataDefinitionList",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["dataDefList"] = data;
			});
			
			$http({
				url : "views/getTestDataDefinitionByActiveStatus",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["dataDefListByActiveStatus"] = data;
			});
			
			$http({
				url : "views/getTestDataDefinitionByINActiveStatus",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["dataDefListByINActiveStatus"] = data;
			});
		});
	};
	
	
	$scope.editTestDataDefn = function(testDataID){
		var result = {};
			result["testDataID"] = testDataID;
			window.testDataIDsGlb = testDataID;	
			
			$http({
				url : "views/editTestDataDescITAP",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$scope.testDataID = data.testDataID;
				$scope.testDataDescription = data.testDataDescription;
//				$scope.status = data.status;
			});
	};
	
	
	$scope.selectedStatus = function(selected){
		//alert("selected"+selected);
		$window.selectedStatus=selected;
	};
	
	$scope.saveTestDataDefn = function(){
		var temp = {};
		temp["testDataDescription"] = $scope.editTestDataDefnForm.testDataDescription.$modelValue;
		temp["testDataID"] = $scope.editTestDataDefnForm.testDataID.$modelValue;
		//temp["status"]= $scope.editTestDataDefnForm.status.$modelValue;
		temp["status"]=$window.selectedStatus;
		//console.log("status ::"+temp["status"]);
		temp["appID"] = $window.appID;
		
		
		var updateTestDataArr = [];
		$('#editTestData').find('tr').each(function(){
			
			var testDataRowArr = [];
			
			var updateParamValue=$(this).find('#edit_para_value').text().trim();
			testDataRowArr.push(updateParamValue);
			var updateValueBig=$(this).find('#edit_value_big').text().trim();
			testDataRowArr.push(updateValueBig);			
				
			
			$(this).find('td').each(function(){
				if($(this).find('select').length > 0){
					if($(this).find('select').val() != "" && $(this).find('select').val() != undefined && $(this).find('select').val() != null){
						testDataRowArr.push($(this).find('select').val().trim());
					}else{
						testDataRowArr.push("");
					}
				}else if($(this).find('input:hidden').length > 0){
					if($(this).find('input:hidden').val() != "" && $(this).find('input:hidden').val() != undefined && $(this).find('input:hidden').val() != null){
						testDataRowArr.push($(this).find('input:hidden').val().trim());
					}else{
						testDataRowArr.push("");
					}
				}else if($(this).find('input:text').length > 0){
					if($(this).find('input:text').val() != "" && $(this).find('input:text').val() != undefined && $(this).find('input:text').val() != null){
						testDataRowArr.push($(this).find('input:text').val().trim());
					}else{
						testDataRowArr.push("");
					}
				}

			});
			
			updateTestDataArr.push(testDataRowArr);
			
		});
		//console.log("updateTestDataArr",updateTestDataArr);
		temp["updateTestDataArr"] = updateTestDataArr;
		
		
		$http({
			url : "views/updateTestDataDefnITAP",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$http({
				url : "views/getTestDataDefinitionList",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["dataDefList"] = data;
			});
			
			$http({
				url : "views/getTestDataDefinitionByActiveStatus",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["dataDefListByActiveStatus"] = data;
			});
			
			$http({
				url : "views/getTestDataDefinitionByINActiveStatus",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["dataDefListByINActiveStatus"] = data;
			});
			
		});
	};	
	
	

}

/**Changes for Object Group start here*/
function addObjTypeControllerITAP($scope, $http, $window, $rootScope){
	
	$scope.closeObjGroup = function() {
		$scope.ObjGrpName='';
		$scope.ObjGrpDescription='';
		
		$scope.objectName='';
		$scope.description='';
		$scope.objectTypeID='';
		$scope.identifierTypeID='';
		
	};
	
	$scope.addObjGrp=function(){
		var result = {};
		var screenID = $scope.addObjectGroupForm.screenID.$modelValue;
		var objGrpName = $scope.addObjectGroupForm.ObjGrpName.$viewValue;
		var objGrpDesc = $scope.addObjectGroupForm.ObjGrpDescription.$viewValue;
		result["screenID"]=screenID;
		result["objGrpName"]=objGrpName;
		result["objGrpDesc"]=objGrpDesc;
		result["appID"] = $window.appID;
		
		var objectArr = [];
		$('#newObjGroupTable').find('tr').each(function(){
			
			var objrowArr = [];
			$(this).find('td').each(function(){
				if($(this).find('select').length > 0){
					if($(this).find('select').val() != "" && $(this).find('select').val() != undefined && $(this).find('select').val() != null){
						objrowArr.push($(this).find('select').val().trim());
					}else{
						objrowArr.push("");
					}
					
					
				}else if($(this).find('input:text').length > 0){
					if($(this).find('input:text').val() != "" && $(this).find('input:text').val() != undefined && $(this).find('input:text').val() != null){
						objrowArr.push($(this).find('input:text').val().trim());
					}else{
						objrowArr.push("");
					}
				}

			});
			
			objectArr.push(objrowArr);
			
		});
		//console.log("objectArr",objectArr);
		result["objectArr"] = objectArr;
		
		
		$http({
			url : "views/addObjGrpITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.ObjGrpName='';
			$scope.ObjGrpDescription='';
			$scope.screenID='';
			
			$scope.objectName='';
			$scope.description='';
			$scope.objectTypeID='';
			$scope.identifierTypeID='';
			
			$http({
				url : "views/getObjectGroupBoxByAppIdITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["objectGroupList"] = data;
				
			});
		});
	};
	
	$scope.editObjGrp = function(objectGroupID){
		var result = {};
		//	alert("editTestSuite "+tSuiteID);
			result["objectGroupID"] = objectGroupID;
			window.objectGroupIDs = objectGroupID;	
			
			$http({
				url : "views/editObjectGroupITAP",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$scope.objectGroupID = data.objectGroupID;
				$scope.objectGroupName = data.objectGroupName;
				$scope.description = data.description;
				$scope.screenID = data.screenID;
			});
	};
	
	$scope.saveObjGrp = function(){
		var temp = {};
		temp["objectGroupName"] = $scope.editObjGrpForm.objectGroupName.$modelValue;
		temp["description"] = $scope.editObjGrpForm.description.$modelValue;
		temp["screenID"]=$scope.editObjGrpForm.screenID.$modelValue;
		temp["objectGroupID"]= $scope.editObjGrpForm.objectGroupID.$modelValue;
		temp["appID"] = $window.appID;
		
		var updateObjectArr = [];
		$('#updateObjGroupTable').find('tr').each(function(){
			
			var objrowArr = [];
			
			var updateObjName=$(this).find('#new_obj_name').text().trim();
			objrowArr.push(updateObjName);
			var updateObjDesc=$(this).find('#new_obj_desc').text().trim();
			objrowArr.push(updateObjDesc);			
				
			
			$(this).find('td').each(function(){
				if($(this).find('select').length > 0){
					if($(this).find('select').val() != "" && $(this).find('select').val() != undefined && $(this).find('select').val() != null){
						objrowArr.push($(this).find('select').val().trim());
					}else{
						objrowArr.push("");
					}
				}else if($(this).find('input:hidden').length > 0){
					if($(this).find('input:hidden').val() != "" && $(this).find('input:hidden').val() != undefined && $(this).find('input:hidden').val() != null){
						objrowArr.push($(this).find('input:hidden').val().trim());
					}else{
						objrowArr.push("");
					}
				}else if($(this).find('input:text').length > 0){
					if($(this).find('input:text').val() != "" && $(this).find('input:text').val() != undefined && $(this).find('input:text').val() != null){
						objrowArr.push($(this).find('input:text').val().trim());
					}else{
						objrowArr.push("");
					}
				}

			});
			
			updateObjectArr.push(objrowArr);
			
		});
		//console.log("updateObjectArr",updateObjectArr);
		temp["updateObjectArr"] = updateObjectArr;
		
		
		$http({
			url : "views/updateObjectGroupITAP",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$http({
				url : "views/getObjectGroupBoxByAppIdITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["objectGroupList"] = data;
				
			});
		});
	};	
	
	
	
}

// End by Gaurav
// starts from Manoj


function addTestSuiteContrl($scope, $http, $window, $rootScope) {
	
	$scope.set_color = function(testPlan) {
		if (testPlan.testScenariosCount == 0 || testPlan.testPlanCount == 0) {
		return {
			color : 'red'
		};
		}
};
	globalObj = $scope;
	
	$scope.closeTestSuite = function() {
		$scope.addTestSuiteForm.$setPristine();
	};
	
	$scope.clearList4 = function(){
		$rootScope.list4 = [];
	};
	
	$scope.getAvaliableTestSuites = function() {
		if($rootScope["getAvaliableTestSuites"]!= undefined){
		$rootScope.suiteListName = $rootScope["getAvaliableTestSuites"];
		alert("$rootScope.suiteListName "+$rootScope.suiteListName);
	}
	};
	
	$scope.clearsuiteUp = function(){
		$(document).find(".linkedPlans ul li").remove();
		$(document).find(".linkedScenarios ul li").remove();
		$rootScope.suiteName = [];
		$rootScope.suiteDescription = [];
	};
	
	$scope.getValue = function(){
	     var linkedSuites = new Array();
	     $('.linkedPlans ul li').each(function(){
	     linkedSuites.push($(this).text().trim());
	     });

	     planNameList1 = [];
	     var cont = 0;
	     for ( var i = 0; i <planNameList.length; i++) {
	     for(var j=0;j<linkedSuites.length;j++){
	   
	     if(linkedSuites[j].trim()==planNameList[i].testPlanName.trim()){
	     cont=1;
	     }
	     }
	     if(cont==0){
	     planNameList1.push(planNameList[i].testPlanName.trim());
	     }
	     cont=0;
	     }
	     $rootScope["TestPlansList"] =planNameList1;
	     };
	     
	     $scope.getListFromSuitePopUP = function() {
	 		scenariotoBLinked = new Array();
	 			$('#suiteUp .linkedScenarios ul li').each(function(){
	 				scenariotoBLinked.push($(this).text().trim());
	 				
	 		});
	 		$rootScope.scenarioListName1 = [];	
	 		var counts = 0;
	 
	 		for ( var i = 0; i < scenarioListName.length; i++) {
	 			
	 			for(var j=0;j<scenariotoBLinked.length;j++){
	 				if(scenariotoBLinked[j]==scenarioListName[i]){
	 					counts=1;
	 				}
	 			}
	 			if(counts==0){
	 				$rootScope.scenarioListName1.push(scenarioListName[i]);
	 			}
	 			counts=0;
	 		}
	 		$rootScope.scenarioListName =$rootScope.scenarioListName1;

	 	};

	$scope.addNewTestSuite = function() {
		
		var result = {};
		var suiteName = $scope.addTestSuiteForm.suiteName.$viewValue;
		var description = $scope.addTestSuiteForm.description.$viewValue;
		var sortOrder = $scope.addTestSuiteForm.sortOrder.$viewValue;
		result["testSuiteName"] = suiteName;
		result["description"] = description;
		result["sortOrder"] = sortOrder;
		result["appID"] = $window.appID;
		
		$http({
			url : "views/addTestSuiteITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$rootScope.suiteName='';
			$rootScope.description = '';
			$rootScope.sortOrder = '';
			$scope.addTestSuiteForm.$setPristine();
			
			$http({
				url : "views/getTestSuitesListFilterByAppIdITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["suitelist"] = data;
			});
			
			$http({
				url : "views/getTestPlanWithNoSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["planWithNoSuite"] = data;
			});
			
			$http({
				url : "views/getSuiteWithNoScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["SuiteWithNoScenario"] = data;
			});
			
			$http({
				url : "views/getTestPlanBoxByAppIdITAPWithSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestPlansListWithSuiteCount"] = data;
			});
			

			
			$http({
				url : "views/getTestScenariosWithNoCaseCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ScenariosWithNoCase"] = data;
			});

			$http({
				url : "views/getTestScenariosListsFilterByAppIdAndGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosAndCount"] = data;

			});
			$http({
				url : "views/getSuiteBoxByAppIdITAPWithPlanandScenarioCount",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestSuiteswithPlanAndScenarioCountList"] = data;

			});
			
		});
	};
	$scope.addTestSuite = function() {
		
		 var selectedPlans = new Array();
			$('#suiteUp .linkedPlans ul li').each(function(){
				selectedPlans.push($(this).text().trim());
		});
			var selectedscenario = new Array();
			$('#suiteUp .linkedScenarios ul li').each(function(){
				selectedscenario.push($(this).text().trim());
		});
			
			
		var setPlans = {};
		var planSelectedfromPopUP = [];
		var scenarioSelectedfromPopUP = [];
		var setScenario = {};
		setScenario["Scenario"] = selectedscenario;
		$http({
			url : "views/getTestScenarioIDsITAP",
			method : "POST",
			data : setScenario,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
          
			
           scenarioSelectedfromPopUP.push(data);
			
//		});
			setPlans["Plans"] = selectedPlans;
			$http({
				url : "views/getTestPlanIDsITAP",
				method : "POST",
				data : setPlans,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
	            
				
	             planSelectedfromPopUP.push(data); 
				
			//});
			

		var result = {};
		var suiteName = $scope.addTestSuiteForm.suiteName.$viewValue;
		var description = $scope.addTestSuiteForm.description.$viewValue;
		var sortOrder = $scope.addTestSuiteForm.sortOrder.$viewValue;
		result["testSuiteName"] = suiteName;
		result["description"] = description;
		result["sortOrder"] = sortOrder;
		result["appID"] = $window.appID;
		
		var plansSelected = [];
		var sceanriosSelected = [];
		
		for (var i = 0; i < $rootScope.plansForSuites.length; i++) {
			plansSelected.push($rootScope.plansForSuites[i].testPlanId);
		}

//		for (var i = 0; i < $window.selectedPlansToBeLinked.length; i++) {
//			plansSelected.push($window.selectedPlansToBeLinked[i]);
//		}
//		if ($window.newPlanForSuite != null && $window.newPlanForSuite != 0) {
//			plansSelected.push($window.newPlanForSuite);
//		}
//		
//		
//		for (var i = 0; i < $rootScope.listScenarios.length; i++) {
//			sceanriosSelected.push($rootScope.listScenarios[i].testScenarioID);
//		}

//		for (var i = 0; i < $window.selectedScenariosToBeLinked.length; i++) {
//			sceanriosSelected.push($window.selectedScenariosToBeLinked[i]);
//		}
		
		if ($window.newScenarioForSuite != null
				&& $window.newScenarioForSuite != 0) {
			sceanriosSelected.push($window.newScenarioForSuite);
		}

//		result["linkedAllPlansForSuites"] = plansSelected;
//		result["linkedScenariosForSuites"] = sceanriosSelected;
		result["scenarioSelectedfromPopUP"] = scenarioSelectedfromPopUP;
		result["planSelectedfromPopUP"] = planSelectedfromPopUP;

		$http({
			url : "views/addTestSuiteITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			selectedPlans = new Array();
			selectedscenario = new Array();
			$rootScope.suiteName='';
			$rootScope.description = '';
			$rootScope.sortOrder = '';
			$scope.addTestSuiteForm.$setPristine();
		//	alert(data);
			$scope.addTestSuiteForm.$setPristine();
			$window.suitesCreatedFrompopup = data;
			$http({
				url : "views/getTestSuitesListFilterByAppIdITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["suitelist"] = data;
			});
			
			$http({
				url : "views/getTestPlanWithNoSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["planWithNoSuite"] = data;
			});
			
			$http({
				url : "views/getSuiteWithNoScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["SuiteWithNoScenario"] = data;
			});
			
			$http({
				url : "views/getTestPlanBoxByAppIdITAPWithSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestPlansListWithSuiteCount"] = data;
			});
			

			
			$http({
				url : "views/getTestScenariosWithNoCaseCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ScenariosWithNoCase"] = data;
			});

			$http({
				url : "views/getTestScenariosListsFilterByAppIdAndGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosAndCount"] = data;

			});
			$http({
				url : "views/getSuiteBoxByAppIdITAPWithPlanandScenarioCount",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestSuiteswithPlanAndScenarioCountList"] = data;

			});
		});
		});
		});
	};
	$scope.editSuite = function(item) {
		
		var testSuiteID = item.testSuiteId;
		
		$http({
			url : "views/getSuiteBySuiteID",
			method : "POST",
			data : testSuiteID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$rootScope.testSuiteID = data.testSuiteId;
			$rootScope.suiteName = data.testSuiteName;
			$rootScope.suiteDescription = data.testSuiteDesc;
			$rootScope.suiteSortOrder = data.sortOrder;
//			alert("List of Plans"+data.plansList);
			$rootScope["editplansForSuites"] = data.plansList;
			$rootScope["editlistScenarios"] = data.scenarioList;
			
		});
		

	};
	
	
	$scope.updateTestSuite = function(){
		
		 var selectedPlans = new Array();
			$('#suiteUp .linkedPlans ul li').each(function(){
				selectedPlans.push($(this).text().trim());
		});
			var selectedscenario = new Array();
			$('#suiteUp .linkedScenarios ul li').each(function(){
				selectedscenario.push($(this).text().trim());
		});
			
			
		var setPlans = {};
		 var planSelectedfromPopUP = [];
		 var scenarioSelectedfromPopUP = [];
		var setScenario = {};
		setScenario["Scenario"] = selectedscenario;
		$http({
			url : "views/getTestScenarioIDsITAP",
			method : "POST",
			data : setScenario,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
      
			
       scenarioSelectedfromPopUP.push(data);
			
//		});
			setPlans["Plans"] = selectedPlans;
			$http({
				url : "views/getTestPlanIDsITAP",
				method : "POST",
				data : setPlans,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
	            
				
	             planSelectedfromPopUP.push(data); 
		var result = {};
		var testSuiteID = $scope.updateTestSuiteForm.testSuiteID.$viewValue;
		var suiteName = $scope.updateTestSuiteForm.suiteName.$viewValue;
		var description = $scope.updateTestSuiteForm.suiteDescription.$viewValue;
		var sortOrder = $scope.updateTestSuiteForm.suiteSortOrder.$viewValue;
		result["testSuiteID"] = testSuiteID;
		result["testSuiteName"] = suiteName;
		result["description"] = description;
		result["sortOrder"] = sortOrder;
		result["appID"] = $window.appID;
//		
//		var plansSelected = [];
//		var sceanriosSelected = [];
//		
//		for (var i = 0; i < $rootScope.plansForSuites.length; i++) {
//			plansSelected.push($rootScope.plansForSuites[i].testPlanId);
//		}
//
//		for (var i = 0; i < $window.selectedPlansToBeLinked.length; i++) {
//			plansSelected.push($window.selectedPlansToBeLinked[i]);
//		}
//		
//		if ($window.newPlanForSuite != null && $window.newPlanForSuite != 0) {
//			plansSelected.push($window.newPlanForSuite);
//		}
//		
//		for (var i = 0; i < $rootScope.listScenarios.length; i++) {
//			sceanriosSelected.push($rootScope.listScenarios[i].testScenarioID);
//		}
//
//		for (var i = 0; i < $window.selectedScenariosToBeLinked.length; i++) {
//			sceanriosSelected.push($window.selectedScenariosToBeLinked[i]);
//		}
//		
//		if ($window.newScenarioForSuite != null
//				&& $window.newScenarioForSuite != 0) {
//			sceanriosSelected.push($window.newScenarioForSuite);
//		}
//		
//		result["linkedAllPlansForSuites"] = plansSelected;
//		result["linkedScenariosForSuites"] = sceanriosSelected;

		result["scenarioSelectedfromPopUP"] = scenarioSelectedfromPopUP;
		result["planSelectedfromPopUP"] = planSelectedfromPopUP;
		$http({
			url : "views/updateTestSuiteITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
		//	alert(data);
			$window.suitesCreatedFrompopup = data;
			
			$http({
				url : "views/getSuiteWithNoScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["SuiteWithNoScenario"] = data;
			});
			
			$http({
				url : "views/getTestSuitesListFilterByAppIdITAP",
			method : "POST",
			data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["suitelist"] = data;
			});
			
			$http({
				url : "views/getSuiteBoxByAppIdITAPWithPlanandScenarioCount",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestSuiteswithPlanAndScenarioCountList"] = data;

			});
			
			$http({
				url : "views/getTestScenariosWithNoCaseCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["ScenariosWithNoCase"] = data;
			});

			$http({
				url : "views/getTestScenariosListsFilterByAppIdAndGetCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestScenariosAndCount"] = data;

			});
			$http({
				url : "views/getTestPlanWithNoSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["planWithNoSuite"] = data;
			});
			
			$http({
				url : "views/getTestPlanBoxByAppIdITAPWithSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestPlansListWithSuiteCount"] = data;
			});
		});
			});
		});
	};
	
	
}



// ends of manoj

// adding starts from shilpa

function getTestPlanswithSuiteCount($scope, $http, $rootScope, $window) {
	// alert("inside getTestPlanBoxByAppIdITAP");
	$http({
		url : "views/getTestPlanBoxByAppIdITAPWithSuiteCount",
		method : "GET",
		headers : {
			'Content-Type' : 'application/json'
		}
	})
			.success(
					function(data) {
						$rootScope["TestPlansListWithSuiteCount"] = data;
						$rootScope.linkedPlansForSuites = [];

						$scope.hideMe = function() {
							return $scope.linkedPlansForSuites.length > 0;
						};

						$scope.dropCallback = function(event, ui) {
							$window.linkedAllPlansForSuites = [];
							for ( var i = 0; i < $rootScope.linkedPlansForSuites.length; i++) {
								$window.linkedAllPlansForSuites
										.push($rootScope.linkedPlansForSuites[i].testPlanID);
							}

						};
					});
};


function getTestPlansForApp($scope, $http, $rootScope, $window) {
	$window.linkedAllPlansForSuites = [];
	$window.selectedPlansToBeLinked = [];
	//$rootScope.plansForSuites = [];
	var appId =  $window.appID;
//	$http({
//		url : "views/getTestPlanBoxByAppIdITAP",
//		method : "POST",
//		data : appId,
//		headers : {
//			'Content-Type' : 'application/json'
//		}
//	}).success(function(data) {
//		$rootScope["TestPlansList"] = data;
//		$rootScope.linkedPlansForSuites1 = [];
//		
//
//	});


	$scope.hideMe = function() {
		return $rootScope.plansForSuites.length > 0;
	};

	

	$scope.toggleClearPlan = function(item) {
		$window.selectedPlansToBeLinked = [];
	};
};


function addTestPlan($scope, $http, $rootScope, $window, $compile) {

		$scope.set_color = function(testPlan) {
				if (testPlan.testSuiteCount == 0) {
				return {
					color : 'red'
				};
				}
	};

	var suitestoBLinked;
	$scope.getsuiteNames = function() {
		$(document).find(".linkedSuites ul li").remove();
		 suitestoBLinked = new Array();
		 $rootScope.planName = [];
		 $rootScope.planDescription = [];
		$rootScope["suiteListName"] = suiteName;	
		 $rootScope["listOfSuitesFromPlanEdit"] = [];
		
	};
	
	$scope.getList4 = function() {
		
		 suitestoBLinked = new Array();
			$('#planUp .linkedSuites ul li').each(function(){
				suitestoBLinked.push($(this).text().trim());
		});
		$rootScope.suiteListName1 = [];	
		var cont = 0;
		for ( var i = 0; i < $rootScope.suiteListName.length; i++) {
			
			for(var j=0;j<suitestoBLinked.length;j++){
				if(suitestoBLinked[j]==$rootScope.suiteListName[i]){
					cont=1;
				}
			}
			if(cont==0){
				$rootScope.suiteListName1.push($rootScope.suiteListName[i]);
			}
			cont=0;
		}
		$rootScope.suiteListName =$rootScope.suiteListName1;
	};
	
	globalObj = $scope;
	$scope.closeTestPlan = function() {
//		$rootScope.planName = '';
//		$rootScope.planDescription = '';
//		$rootScope.list4 = '';
		$scope.addTestPlanForm.$setPristine();
	};
	
	$scope.updateTestPlan = function() {
		
		var testPlanName = $scope.updateTestPlanForm.planName1.$viewValue;
		var description = $scope.updateTestPlanForm.planDescription1.$viewValue;
		var testPlanId = $scope.updateTestPlanForm.testPlanId.$viewValue;
		 var selectedSuites = new Array();
			$('#planUp .linkedSuites ul li').each(function(){
				selectedSuites.push($(this).text().trim());
		});
		var selSuite = {};
		selSuite["Suites"] = selectedSuites;
		$http({
			url : "views/getTestSuiteIDsITAP",
			method : "POST",
			data : selSuite,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var suitesSelectedfromPopUP = [];
			suitesSelectedfromPopUP.push(data);
		var result = {};
		result["testPlanId"] = testPlanId;
		result["testPlanName"] = testPlanName;
		result["description"] = description;
		result["appID"] = $window.appID;
		result["linkedSuitespopUP"] = suitesSelectedfromPopUP;
		$http({
			url : "views/updateTestPlanITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//$scope.planName = '';
			//$scope.description = '';
			//$scope.updateTestPlanForm.$setPristine();
			$http({
				url : "views/getTestPlanBoxByAppIdITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {

				$rootScope["TestPlansList"] = data;
			});
			
			$http({
				url : "views/getTestPlanWithNoSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["planWithNoSuite"] = data;
			});
			
			$http({
				url : "views/getSuiteWithNoScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["SuiteWithNoScenario"] = data;
			});
			
			$http({
				url : "views/getSuiteBoxByAppIdITAPWithPlanandScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestSuiteswithPlanAndScenarioCountList"] = data;

			});
			
			$http({
				url : "views/getTestPlanBoxByAppIdITAPWithSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {

				$rootScope["TestPlansListWithSuiteCount"] = data;
				$rootScope.list4 = [];

				$window.linkedSuitesForPlan = [];

				$scope.hideMe = function() {
					return $rootScope.list4.length > 0;
				};

			});
			
		});
		});
	};
	
	
	$scope.dropSuccessHandler = function($event,index){
		$rootScope.TestPlansListWithSuiteCount.splice(index,1);
	};
	
	$scope.addNewTestPlan = function() {
		var result = {};
		var testPlanName = $scope.addTestPlanForm.planName.$viewValue;
		var description = $scope.addTestPlanForm.description.$viewValue;

		result["testPlanName"] = testPlanName;
		result["description"] = description;
		result["appID"] = $window.appID;
		$http({
			url : "views/addTestPlanITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope.planName = '';
			$rootScope.planDescription = '';
			
			$http({
				url : "views/getTestPlanBoxByAppIdITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {

				//$rootScope["TestPlansList"] = data;
			});
			
			$http({
				url : "views/getSuiteBoxByAppIdITAPWithPlanandScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestSuiteswithPlanAndScenarioCountList"] = data;
			});
			$http({
				url : "views/getSuiteWithNoScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["SuiteWithNoScenario"] = data;
			});
			$http({
				url : "views/getTestPlanWithNoSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["planWithNoSuite"] = data;
			});
			
			$http({
				url : "views/getTestPlanBoxByAppIdITAPWithSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestPlansListWithSuiteCount"] = data;
				$rootScope.list4 = [];

				$window.linkedSuitesForPlan = [];

				$scope.hideMe = function() {
					return $rootScope.list4.length > 0;
				};
				$scope.addTestPlanForm.$setPristine();
			});
			
		});
	};
	
	$scope.addTestPlan = function() {
		
		 var selectedSuites = new Array();
			$('#planUp .linkedSuites ul li').each(function(){
				selectedSuites.push($(this).text().trim());
		});
		var selSuite = {};
		selSuite["Suites"] = selectedSuites;
		$http({
			url : "views/getTestSuiteIDsITAP",
			method : "POST",
			data : selSuite,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			var suitesSelected = [];
			var suitesSelectedfromPopUP = [];
			
			suitesSelectedfromPopUP.push(data);
		var result = {};
		var testPlanName = $scope.addTestPlanForm.planName.$viewValue;
		var description = $scope.addTestPlanForm.description.$viewValue;

		result["testPlanName"] = testPlanName;
		result["description"] = description;
		result["appID"] = $window.appID;
		
	
	
		$window.newPlanForSuite;
//		for ( var i = 0; i < $window.suitesFromPopUpForScenarios.length; i++) {
//			suitesSelected.push($window.suitesFromPopUpForScenarios[i]);
//		}

//		for ( var i = 0; i < $rootScope.list4.length; i++) {
//			suitesSelected.push($rootScope.list4[i].testSuiteId);
//		}
	     
		suitesSelected.push($window.suitesCreatedFrompopup);
		
		//result["linkedSuites"] = suitesSelected;
		result["linkedSuitespopUP"] = suitesSelectedfromPopUP;
		
		$http({
			url : "views/addTestPlanITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			selectedSuites = new Array();
//			$rootScope.planName = '';
//			$rootScope.planDescription = '';
//			$scope.list4 = '';
			$http({
				url : "views/getTestPlanBoxByAppIdITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {

				$rootScope["TestPlansList"] = data;
			});
			
			$http({
				url : "views/getSuiteBoxByAppIdITAPWithPlanandScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestSuiteswithPlanAndScenarioCountList"] = data;
			});
			$http({
				url : "views/getSuiteWithNoScenarioCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["SuiteWithNoScenario"] = data;
			});
			$http({
				url : "views/getTestPlanWithNoSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["planWithNoSuite"] = data;
			});
			
			$http({
				url : "views/getTestPlanBoxByAppIdITAPWithSuiteCount",
				method : "POST",
				data : appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["TestPlansListWithSuiteCount"] = data;
				$rootScope.list4 = [];

				$window.linkedSuitesForPlan = [];

				$scope.hideMe = function() {
					return $rootScope.list4.length > 0;
				};
				$scope.addTestPlanForm.$setPristine();
			});
			$window.newPlanForSuite = data;
		});
		});
	};
	
	$scope.editPlan1 = function(testPlanId) {
		var suiteListName = new Array();
		$window.testPlanId = testPlanId;
		$http({
			url : "views/editTestPlanBoxByPlanIdITAP",
			method : "POST",
			data : testPlanId,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			 $scope.planName1 = data.testPlanName;
//			alert("$scope.planName  "+$scope.planName);
			 $scope.planDescription1 = data.testPlanDesc;
			 $scope.testPlanId =  data.testPlanId;
			//alert(data.testPlanName+" "+data.testPlanDesc+" "+data.testPlanId);
			 $scope["listOfSuitesFromPlanEdit"] = data.suiteList;
			 var count =0;
			 
				for(var i=0; i<suiteName.length;i++){
					for(var j=0;j<data.suiteList.length;j++){
					
						if(data.suiteList[j].suiteName==suiteName[i]){
							
							count=1;
						}	
					}
					if(count==0){
					suiteListName.push(suiteName[i]);
					}
					count=0;
				}
			
				$scope["suiteListName"] =  suiteListName;
				$scope["getAvaliableTestSuites"] = suiteListName;
			
		});
		};
		
	$scope.editPlan = function(testPlanId) {
		var suiteListName = new Array();
		$window.testPlanId = testPlanId;
		$http({
			url : "views/editTestPlanBoxByPlanIdITAP",
			method : "POST",
			data : testPlanId,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$scope.planName1 = data.testPlanName;
			$scope.planDescription1 = data.testPlanDesc;
			$scope.testPlanId =  data.testPlanId;
			//alert(data.testPlanName+" "+data.testPlanDesc+" "+data.testPlanId);
			$scope["listOfSuitesFromPlanEdit"] = data.suiteList;
			 var count =0;
			 
				for(var i=0; i<suiteName.length;i++){
					for(var j=0;j<data.suiteList.length;j++){
					
						if(data.suiteList[j].suiteName==suiteName[i]){
							
							count=1;
						}	
					}
					if(count==0){
					suiteListName.push(suiteName[i]);
					}
					count=0;
				}
			
				$scope["suiteListName"] =  suiteListName;
				$scope["getAvaliableTestSuites"] = suiteListName;
			
		});
		};
		
	
		
}
function usersController($scope, $http, $rootScope, $window){
	
	$http({
		url : "views/getAllUsers",
		method : "GET",
		headers : {
		'Content-Type' : 'application/json'
		}
		}).success(function(data) {
		$rootScope["AllUsers"] = data;
		});
	
    $scope.addUsers = function() {  	
    var result = {};
    var userName = $scope.addUserForm.username.$viewValue;
    var password = $scope.addUserForm.password.$viewValue;
    var emailID =  $scope.addUserForm.emailID.$viewValue;
    result["username"] = userName;
    result["password"] = password;
    result["emailID"] = emailID;
    result["roleApps"] = groupList;
    $http({
    url : "views/addUser",
    method : "POST",
    data : result,
    headers : {
    'Content-Type' : 'application/json'
    }
    }).success(function(data) {
        $http({
            url : "views/getAllUsers",
            method : "GET",
            headers : {
                'Content-Type' : 'application/json'
            }
        }).success(function(data) {
            $rootScope["AllUsers"] = data;
        });
    });
    };
    var userId = null;
    $scope.editUsers = function(item) {
        $http({
        url : "views/getUserByUserID",
        method : "POST",
        data : item,
        headers : {
        'Content-Type' : 'application/json'
        }
        }).success(function(data) {
        	  $scope.editUserID = data.userID;
              userId = data.userID;
              $scope.updateusername = data.username;
              $scope.updatepassword = data.password;
              $scope.emailID = data.emailID;
              $rootScope["UserRoles"] = data.userRoles; 
        	$.ajax({
    			type: "GET",
    			url:"views/getAllRole",
    			headers : {
    		'Content-Type' : 'application/json'
    		},
    		success: function (data) {
    			var arr1 = [];
    			$.each(data,function(i,data){
    				var div_data="<option value="+data.authority+">"+data.authority+"</option>";
    				arr1.push(div_data);
    				});
    			roleList.push(arr1);
    			$.ajax({
    				type: "POST",
    				url:"views/getApplications",
    				headers : {
    			'Content-Type' : 'application/json'
    			},
    			success: function (data) {
    				var arr = [];
    				$.each(data,function(i,data){
    					var div_data="<option value="+data.appID+">"+data.appName+"</option>";
    					arr.push(div_data);
    					});
    				appList.push(arr);
    				}
    			});
    			}
    		});
        });
        };
        $scope.w = {
                w: [new Model()]
            };
            
            $scope.add = function() {
                $scope.w.w.push(new Model());
            };
        
        $scope.getApps = function(role,userID) {
//        alert("role "+role+"  userID "+userId);
        groups = role;
       	var result = {};
        result["role"] = role;
        result["userID"] = userId;
        $http({
        url : "views/getApplicationsByRoleAndUserID",
        method : "POST",
        data : result,
        headers : {
        'Content-Type' : 'application/json'
        }
        }).success(function(data) {
        	$rootScope["UserApplicationRoles"] = data; 
        });
        };
        
        $scope.getApps1 = function(role) {
//          alert("role "+role+"  userID "+userId);
          groups = role;
         	var result = {};
          result["role"] = role;
          result["userID"] = userId;
          $http({
          url : "views/getApplicationsByRoleAndUserID",
          method : "POST",
          data : result,
          headers : {
          'Content-Type' : 'application/json'
          }
          }).success(function(data) {
        	  var roleList1 =[];
        	  for (var int = 0; int < data.length; int++) {
        		  roleList1.push(data[int]);
			}
        	  alert(roleList1);
        	  $rootScope["UserApplicationRoles"] = roleList1; 
          });
          };

        $scope.updateUsers = function() {
//        	alert("Update ");
            var result = {};
            var userID = $scope.updateUserForm.editUserID.$viewValue;
            var userName = $scope.updateUserForm.updateusername.$viewValue;
            var password = $scope.updateUserForm.updatepassword.$viewValue;
            var emailID = $scope.updateUserForm.emailID.$viewValue;
            result["userID"] = userID;
            result["username"] = userName;
            result["password"] = password;
            result["emailID"] = emailID;
            result["roleApps"] = groupList;
            $http({
            url : "views/updateUser",
            method : "POST",
            data : result,
            headers : {
            'Content-Type' : 'application/json'
            }
            }).success(function(data) {
                $http({
                    url : "views/getAllUsers",
                    method : "GET",
                    headers : {
                        'Content-Type' : 'application/json'
                    }
                }).success(function(data) {
                    $rootScope["AllUsers"] = data;
                });
            });
            };
}

	


function Model(json) {
    json = json || {};
    this.roless = json.roless || null;
}

function addParamGroupCtrl($scope, $http, $rootScope, $window) {
	
	$scope.addParamGroup = function() {
	var result = {};
	var paramGroupName = $scope.addParamGroupForm.ParamGroupName.$viewValue;
	var description = $scope.addParamGroupForm.description.$viewValue;
	var tagName = $scope.addParamGroupForm.tagName.$viewValue;
	var objectGroupID = $scope.addParamGroupForm.objectGroupID.$modelValue;
	
	result["paramGroupName"] = paramGroupName;
	result["description"] = description;	
	result["objectGroupID"] = objectGroupID;
	result["tagName"] = tagName;
	result["appID"] = $window.appID;	
	
	
	var paramArr = [];
	$('#newParamsGroupTable').find('tr').each(function(){
		
		var paramrowArr = [];
		$(this).find('td').each(function(){
			if($(this).find('select').length > 0){
				if($(this).find('select').val() != "" && $(this).find('select').val() != undefined && $(this).find('select').val() != null){
					paramrowArr.push($(this).find('select').val().trim());
				}else{
					paramrowArr.push("");
				}
				
				
			}else if($(this).find('input').length > 0){
				if($(this).find('input').val() != "" && $(this).find('input').val() != undefined && $(this).find('input').val() != null){
					paramrowArr.push($(this).find('input').val().trim());
				}else{
					paramrowArr.push("");
				}
			}

		});
		
		paramArr.push(paramrowArr);
		
	});
	//console.log("paramArr",paramArr);
	result["paramArr"] = paramArr;
	
	
	$http({
		url : "views/addParamGroupITAP",
		method : "POST",
		data : result,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		$scope.ParamGroupName='';
		$scope.description='';
		$scope.tagName='';
		$scope.objectGroupID='';
		$http({
			url : "views/getParamGroupNamesByAppIdITAP",
			method : "POST",
			data : $window.appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {

			$rootScope["AppParamPopUpsNameITAP"] = data;
		});
	});

};

$scope.editParamGroup = function(param){
	
	var paramGrpid = param.paramGroupID;
	window.paramGrpID = param.paramGroupID;
	var temp = {};
	temp["paramGrpid"] = paramGrpid;
	$http({
		url : "views/editParamGrpITAP",
		method : "POST",
		data : temp,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {		
		$scope.editParanGrpID = data.paramGroupID;
		$scope.editParanGrpName = data.paramGroupName;
		$scope.editParanGrpDesc = data.description;
		$scope.editParanGrpTag = data.tag;
		$scope.editobjectGroupID = data.objectGroupID;
		window["objGrpIDs"] = data.objectGroupID;
		
//		$(function() {
//			editParam();
//		});
		//editParam();
		var objGrpId = data.objectGroupID;
		var obGrID = {};
		obGrID["paramGrpid"] = objGrpId;		
	});
};
$scope.updateParamGrp = function(){
	var result = {};
	var editParanGrpID = $scope.editParamGrpForm.editParanGrpID.$viewValue;
	var paramGroupName = $scope.editParamGrpForm.editParanGrpName.$viewValue;
	var description = $scope.editParamGrpForm.editParanGrpDesc.$viewValue;
	var tagName = $scope.editParamGrpForm.editParanGrpTag.$viewValue;
	var objectGroupID = $scope.editParamGrpForm.editobjectGroupID.$modelValue;
	var updateparamArr = [];
	$('#updateParamsGroupTable').find('tr').each(function(){
		
		var paramrowArr = [];
		
		var updateParamName=$(this).find('#new_param_name').text().trim();
		paramrowArr.push(updateParamName);
		var updateParamDesc=$(this).find('#new_param_desc').text().trim();
		paramrowArr.push(updateParamDesc);			
		var updateParamSort=$(this).find('#new_param_sort').text().trim();
		paramrowArr.push(updateParamSort);	
		
		$(this).find('td').each(function(){
			if($(this).find('select').length > 0){
				if($(this).find('select').val() != "" && $(this).find('select').val() != undefined && $(this).find('select').val() != null){
					paramrowArr.push($(this).find('select').val().trim());
				}else{
					paramrowArr.push("");
				}
			}else if($(this).find('input').length > 0){
				if($(this).find('input').val() != "" && $(this).find('input').val() != undefined && $(this).find('input').val() != null){
					paramrowArr.push($(this).find('input').val().trim());
				}else{
					paramrowArr.push("");
				}
			}

		});
		
		updateparamArr.push(paramrowArr);
		
	});
	result["updateparamArr"] = updateparamArr; 
	
	result["paramGroupName"] = paramGroupName;
	result["description"] = description;	
	result["objectGroupID"] = objectGroupID;
	result["tagName"] = tagName;
	result["appID"] = $window.appID;	
	result["editParanGrpID"] = editParanGrpID;
	$http({
		url : "views/updateParamGrpITAP",
		method : "POST",
		data : result,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		$scope.ParamGroupName='';
		$scope.description='';
		$scope.tagName='';
		$scope.objectGroupID='';
		$http({
			url : "views/getParamGroupNamesByAppIdITAP",
			method : "POST",
			data : $window.appID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {

			$rootScope["AppParamPopUpsNameITAP"] = data;
		});
		
	});
	
	
};

$scope.getObjectGroupId = function(item) {
	$window.objGrpID = item;
};


$scope.getObjectGroup = function(item) {
	$window.objectOrpParam = item;
	alert("**since ObjectGroup Name is changed, Its mandatory to select objectNames for all params**");
	$http({
		url : "views/getObjsITAP",
		method : "POST",
		data : $window.objectOrpParam,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		//alert("sucess: "+data);
		window.objsByObjGrpId = data;
//		
//		$("#ObjNambyGrp").parent().find('select').find('option').remove();   	
//    	$("#ObjNambyGrp").parent().find('select').html(data);
//    	$("#ObjNambyGrp").parent().find('select').show();
    				
	});
};
}
function addScreenCtrl($scope, $http, $rootScope, $window) {
	
	$scope.editscreen = function(screenID) {
		$http({
			url : "views/editScreenITAP",
			method : "POST",
			data : screenID,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.screenID = data.screenID;
			$scope.screenName = data.screenName;
			$scope.screenDescription = data.description;
			
		});
	};

	$scope.updateScreen = function(){
		var temp = {};
		temp["screenName"] = $scope.updateScreenForm.screenName.$viewValue;
		temp["screenDescription"] = $scope.updateScreenForm.screenDescription.$viewValue;
		temp["screenID"]= $scope.updateScreenForm.screenID.$viewValue;
		temp["appID"] = $window.appID;
		$http({
			url : "views/updateScreenITAP",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$http({
				url : "views/getScreensForApplicationITAP",
				method : "POST",
				data :  $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["AppScreens"] = data;
				$rootScope["screenList"] = data;
	
			});
			
		});
		
	};
	
	
	$scope.addScreen = function() {
		var result = {};
		var flag=0;
		var screenName = $scope.addScreenForm.screenName.$viewValue;
		var description = $scope.addScreenForm.description.$viewValue;
		
		for (var i=0;i<$rootScope.screenList.length; i++){
			if(screenName == $rootScope.screenList[i].screenName)
				flag=1;
			}
		
		if(flag == 1){
			alert('Screen Name already exists in The Function. Try with different Data');
			return false;
		}
		
		result["screenName"] = screenName;
		result["description"] = description;
		result["appID"] = $window.appID;
		result["featureID"] = $window.featureID;
		$http({
			url : "views/addScreenITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.screenName='';
			$scope.description='';
			$scope.addScreenForm.$setPristine();
			
			$http({
				url : "views/getScreenFilterByFeatureIdAndAppIDITAP",
				method : "POST",
				data : result,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				
				$rootScope["screenList"] = data;
			});
		});
	};
}


function addConditionGroupCtrl($scope, $http, $rootScope, $window) {
	$scope.closeConditionGroup = function() {
		$scope.conditionGroupName='';
		$scope.description='';
	};
	$scope.addConditionGroup = function() {
		var result = {};
		var conditionGroupName = $scope.addConditionGroupForm.conditionGroupName.$viewValue;
		var description = $scope.addConditionGroupForm.description.$viewValue;

		result["conditionGroupName"] = conditionGroupName;
		result["description"] = description;
		result["appID"] = $window.appID;
		
		var mainArr = [];
		$('#newConditionGroupTable').find('tr').each(function(){
			var rowArr = [];
			$(this).find('td').each(function(){
			 if($(this).find('input:text').length > 0){
					if($(this).find('input:text').val() != "" && $(this).find('input:text').val() != undefined && $(this).find('input:text').val() != null){
						rowArr.push($(this).find('input:text').val().trim());
					}else{
						rowArr.push("");
					}
				}

			});
			mainArr.push(rowArr);
		});
		
		
		//console.log("mainArr",mainArr);
		result["conditions"] = mainArr;
		$http({
			url : "views/addConditionGroupITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//alert(data);
			$http({
				url : "views/getConditionGrpITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["AppConditionPopUpsName"] = data;
			});
		});
	};
	
	$scope.editConditionsGrp = function(conditionGroup){
		window["cndGrpIDs"] = conditionGroup;
		$http({
			url : "views/getConditionsByIdITAP",
			method : "POST",
			data : conditionGroup,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {		
			$window.conditionGroupID = data.conditionGroupID;
			$scope.conditionGroupID = data.conditionGroupID;
			$scope.conditionGroupName = data.conditionGroupName;
			$scope.conditionGroupdescription = data.description;
			
		});
		
	};
	
	$scope.updateConditionsGrp = function(){
		var temp = {};
		temp["conditionGroupName"] = $scope.editConditionGrpForm.conditionGroupName.$viewValue;
		temp["conditionGroupdescription"] = $scope.editConditionGrpForm.conditionGroupdescription.$viewValue;
		temp["conditionGroupID"]= $scope.editConditionGrpForm.conditionGroupID.$viewValue;
		temp["appID"] = $window.appID;
		$window.ConditionGrpIDs = $scope.editConditionGrpForm.conditionGroupID.$viewValue;;
		var updateCondGrpArr = [];
		$('#updateCondGroupTable').find('tr').each(function(){
			
			var condrowArr = [];
			
			var updateCondName=$(this).find('#new_cnd_name').text().trim();
			condrowArr.push(updateCondName);
			var updateCondDesc=$(this).find('#new_cnd_desc').text().trim();
			condrowArr.push(updateCondDesc);	
			var updateCondExpr=$(this).find('#new_cnd_expr').text().trim();
			condrowArr.push(updateCondExpr);		
				
			
			$(this).find('td').each(function(){
				 if($(this).find('input:hidden').length > 0){
					if($(this).find('input:hidden').val() != "" && $(this).find('input:hidden').val() != undefined && $(this).find('input:hidden').val() != null){
						condrowArr.push($(this).find('input:hidden').val().trim());
					}else{
						condrowArr.push("");
					}
				}else if($(this).find('input:text').length > 0){
					if($(this).find('input:text').val() != "" && $(this).find('input:text').val() != undefined && $(this).find('input:text').val() != null){
						condrowArr.push($(this).find('input:text').val().trim());
					}else{
						condrowArr.push("");
					}
				}

			});
			
			updateCondGrpArr.push(condrowArr);
			
		});
		temp["updateCondGrpArr"] = updateCondGrpArr;
		
		$http({
			url : "views/updateconditionGrpITAP",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$http({
				url : "views/getConditionsITAP",
				method : "POST",
				data : $window.ConditionGrpIDs,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["AppConditionName"] = data;
			});
			
			$http({
				url : "views/getConditionGrpITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["AppConditionPopUpsName"] = data;
			});
			
		});
		
	};	
}

function getScreensCtrl($scope, $http, $rootScope, $window) {
//	$http({
//		url : "views/getScreenFilterByFeatureIdAndAppIDITAP",
//		method : "POST",
//		data : $window.featureID,
//		headers : {
//			'Content-Type' : 'application/json'
//		}
//	}).success(function(data) {
//		$rootScope["screenList"] = data;
//	});
};
/*
function getObjectGroupsForApp($scope, $http, $rootScope, $window) {
	$http({
		url : "views/getObjectGroupBoxByAppIdITAP",
		method : "GET",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		$rootScope["objectGroupList"] = data;
		$rootScope.linkedobjectGroupsForApp = [];
		$scope.hideMe = function() {
			return $rootScope.linkedobjectGroupsForApp.length > 0;
		};
	});
}
*/
function addFeatureCtrl($scope, $http, $rootScope, $window) {
	$window.newScreenArr = [];
	$window.screenArr = [];
	
	
	$scope.addFeature = function() {
		var result = {};
		var flag=0;
		var testFeature = $scope.addFeatureForm.featureName.$viewValue;
		var description = $scope.addFeatureForm.description.$viewValue;
		for (var i=0;i<$rootScope.Featurelist.length; i++){
			if(testFeature == $rootScope.Featurelist[i].appFeatureName)
				flag=1;
			}
		
		if(flag == 1){
			alert('Feature Name already exists in The Function. Try with different Data');
			return false;
		}
		result["testFeature"] = testFeature;
		result["description"] = description;
		result["appID"] = $window.appID;
		result["screenArr"] = $window.screenArr;
		result["functionId"] = $window.functionId;
		$http({
			url : "views/addFeatureITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.featureName='';
			 $scope.description='';
			 $scope.addFeatureForm.$setPristine();
			 alert("Successfully Persisted");
			 
			$window.featureID = data.featureID;
			$http({
				url : "views/getFeatureFilterByFunctionalIdITAP",
				method : "POST",
				data : $window.functionId,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["Featurelist"] = data;
			});
		});
	};

	$scope.clickNewScreen = function() {
		var obj = {};
		key = $scope.addFeatureForm.screenName.$viewValue;
		val = $scope.addFeatureForm.screenDescription.$viewValue;
		obj[key] = val;
		$window.screenArr.push(obj);
	};
	
	$scope.newScreen = function() {
		var obj = {};
		key = $scope.updatefeatureForm.screenName.$viewValue;
		val = $scope.updatefeatureForm.screenDescription.$viewValue;
		obj[key] = val;
		$window.newScreenArr.push(obj);
	};
	
	$scope.editFeature = function(testFeatureId) {
		$http({
			url : "views/editFeatureITAP",
			method : "POST",
			data : testFeatureId,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.editAppFeatureId = data.appFeatureId;
			$scope.editAppFeatureDesc = data.appFeatureDesc;
			$scope.editAppFeatureName =  data.appFeatureName;
		    $rootScope["listOfScreenFromfeatureEdit"] = data.screen;
			
		});
	};
	
	$scope.updateFeature = function() {
		var result = {};
		var testFeatureID = $scope.updatefeatureForm.editAppFeatureId.$viewValue;
		var testFeatureName = $scope.updatefeatureForm.editAppFeatureName.$viewValue;
		var description = $scope.updatefeatureForm.editAppFeatureName.$viewValue;
		
		result["testFeatureID"] = testFeatureID;
		result["description"] = description;
		result["appID"] = $window.appID;
		result["newScreenArr"] = $window.newScreenArr;
		result["testFeatureName"] = testFeatureName;
		result["functionId"] = $window.functionId;
		$http({
			url : "views/updateFeatureITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			 alert("Successfully Persisted");
			 
			$window.featureID = data.featureID;
			$http({
				url : "views/getFeatureFilterByFunctionalIdITAP",
				method : "POST",
				data : $window.functionId,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["Featurelist"] = data;
			});
		});
	};
}

function getFeatureController($scope, $http, $rootScope, $window) {
//	$http({
//		url : "views/getFeatureFilterByFunctionalIdITAP",
//		method : "POST",
//		data : $window.functionId,
//		headers : {
//			'Content-Type' : 'application/json'
//		}
//	}).success(function(data) {
//		$rootScope["Featurelist"] = data;
//	});

	$scope.fetchAppFeatureID = function(item) {
		var result = {};
		
		$window.featureID = item;
		result["featureID"]=$window.featureID;
		result["appID"] = $window.appID;
		$http({
			url : "views/getScreenFilterByFeatureIdAndAppIDITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["screenList"] = data;
		});

	};
};

function importController($scope, $http, $window, $rootScope) {
	$window.filesList = 0;
	$scope.setExcelFiles = function(element) {
		$scope.$apply(function($scope) {
			$scope.files = [];
			for ( var i = 0; i < element.files.length; i++) {
				$scope.files.push(element.files[i]);

			}
			//  alert($scope.files.length);
		});
		//   };	
		$window.filesList = $scope.files.length;
		// $scope.uploadExcel = function() {
		var projectPath = $scope.files;
		//  alert('projectPath :: '+projectPath);
		var result = {};
		var blnValid = false;
		var fd = new FormData();
		for ( var i in projectPath) {
			fd.append("File", $scope.files[i]);
			//     fd.append("svnUrl",svnUrl);
			var SrcPath = $scope.files[i];
			var fileName = $scope.files[i].name;
			console.log(SrcPath);
			if (fileName.length > 0) {

				var sCurExtension = ".xls";
				if (fileName.substr(fileName.length - sCurExtension.length,
						sCurExtension.length).toLowerCase() == sCurExtension
						.toLowerCase()) {
					blnValid = true;
					break;
				}
			}
			if (!blnValid) {
				alert("Sorry, " + fileName
						+ " is invalid, allowed extension is .xls");
				return false;
			}

			//    alert('srcPathfiles'+$scope.files[i]+'SrcPath'+SrcPath);
		}
		$.ajax({
			url : "views/uploadExcelUtil",
			data : fd,
			//    dataType: 'text',
			processData : false,
			contentType : false,
			forceSync : false,
			dataType : null,
			type : 'POST',
			success : function(data) {
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
		//alert($scope.files.length);
		var len = $window.filesList;
		if (len == 0) {
			alert("No File uploaded");
			return false;
		}
		var flag = 0;
		var fName = $scope.importExcelForm.functionName.$viewValue;
		var planName = $scope.importExcelForm.testPlanName.$viewValue;
				for (var i=0;i<$rootScope.AppFuncPopUpsName.length; i++){
					if(fName==$rootScope.AppFuncPopUpsName[i].functionalName)
						flag=1;
					}
	
		for ( var i = 0; i < $rootScope.TestPlansListWithSuiteCount.length; i++) {
			if (planName == $rootScope.TestPlansListWithSuiteCount[i].testPlanName)
				flag = 2;
			//	alert('Flag = '+flag);
		}
		if (flag == 1) {
			alert('Function Name already exists in The Application. Try with different Data');
			return false;
		} else if (flag == 2) {
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
			alert(data);
			$scope.functionName="";
			$scope.featureName="";
			$scope.testPlanName="";
			$scope.testSuiteName="";
			$scope.testScenarioName="";
			$scope.files=[];
			$window.filesList.length=0;
			$scope.importExcelForm.$setPristine();
				appID = $window.appID;	
				$http({
					url : "views/getTestPlanBoxByAppIdITAPWithSuiteCount",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {

					$rootScope["TestPlansListWithSuiteCount"] = data;
					for(var i=0; i<data.length; i++) {
		                planNameList.push(data[i]);
		            }
					$rootScope.list4 = [];
		            
					$window.linkedSuitesForPlan = [];

					$scope.hideMe = function() {
						return $rootScope.list4.length > 0;
					};

				});
				
				$http({
					url : "views/getTestPlanBoxByAppIdITAP",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["TestPlansList"] = data;
					$rootScope.plansForSuites = [];

				});
				
				$http({
					url : "views/getScreensForApplicationITAP",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["AppScreens"] = data;
				});
				$http({
					url : "views/getTestPlanWithNoSuiteCount",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["planWithNoSuite"] = data;
				});
				$http({
					url : "views/getSuiteWithNoScenarioCount",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["SuiteWithNoScenario"] = data;
				});
				$http({
					url : "views/getTestScenariosWithNoCaseCount",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["ScenariosWithNoCase"] = data;
				});
				$http({
					url : "views/getTestCasewithNoStepCount",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["CasewithNoStepCount"] = data;
				});

				$http({
					url : "views/getTestDataDefinitionList",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["dataDefList"] = data;
				});
				
				$http({
					url : "views/getTestDataDefinitionByActiveStatus",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["dataDefListByActiveStatus"] = data;
				});
				
				$http({
					url : "views/getTestDataDefinitionByINActiveStatus",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["dataDefListByINActiveStatus"] = data;
				});
				
				$http({
					url : "views/getTestSuitesListFilterByAppIdITAP",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {

					$rootScope["suitelist"] = data;
					for(var i=0; i<data.length; i++) {
		                suiteName.push(data[i].suiteName);
		             } 
					//$rootScope["suiteListName"] = suiteName;
					
					$rootScope.listOfSuites = [];

					$window.linkedSuitesForPlan = [];

					$scope.hideMe = function() {
						return $rootScope.listOfSuites.length > 0;
					};

				});
				
				$http({
					url : "views/getSuiteBoxByAppIdITAPWithPlanandScenarioCount",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["TestSuiteswithPlanAndScenarioCountList"] = data;

				});
				
				$http({
					url : "views/getObjectGroupBoxByAppIdITAP",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["objectGroupList"] = data;
					
				});

				$http({
					url : "views/getIdentifierTypeByAppIDITAP",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["IdentifierTypesBoxList"] = data;
				});
				
				$http({
					url : "views/getTestScenariosListsFilterByAppIdAndGetCount",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["TestScenariosAndCount"] = data;
					$rootScope.listScenarios = [];
					
					for(var i=0; i<data.length; i++) {
						scenarioName.push(data[i].testScenarioName);
		             } 
					
					scenarioListName = scenarioName;
					
					$rootScope.hideMe = function() {
						//alert("inside hide me");
						return $rootScope.listScenarios.length > 0;
					};

				});
				
				$http({
					url : "views/getFunctionFilterByAppIdITAP",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["AppFuncPopUpsName"] = data;
				});
				
				$http({
					url : "views/getAppFunctionalNameByAppIdITAP",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {

					$rootScope["AppFuncPopUpsNameTestcase"] = data;
				});

				$http({
					url : "views/getConditionGrpITAP",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {

					$rootScope["AppConditionPopUpsName"] = data;
				});

				$http({
					url : "views/getCaseListFilterByAppIdGetCount",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["TestCasesAndCount"] = data;
					$rootScope.AddCaselist = [];
			
					for(var i=0; i<data.length; i++) {
						caseName.push(data[i].caseName);
		             } 
					
					$rootScope.hideMe = function() {
						return $rootScope.AddCaselist.length > 0;
					};

				});

				$http({
					url : "views/getParamGroupNamesByAppIdITAP",
					method : "POST",
					data : appID,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {

					$rootScope["AppParamPopUpsNameITAP"] = data;
				});
				
				//Added by Jagadish - Starts
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
					url : "views/getAgentData",
					method : "GET",
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					//resultData = data;
					$rootScope["agentDataList"] = data;
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
					$rootScope["scheduledList"] = data;
				});
				
					$http({
						url : "views/getTestExectionRunId",
						method : "GET",
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data) {
						//console.log(data);
						$rootScope["runnerList"] = data;
					});
		});
		//}

	};
	
	$scope.cancelImport=function(){
		$scope.functionName="";
		$scope.featureName="";
		$scope.testPlanName="";
		$scope.testSuiteName="";
		$scope.testScenarioName="";
		$scope.files="";
		$window.filesList.length=0;
		$scope.importExcelForm.$setPristine();
	};
}

function getFunctionController($scope, $http, $rootScope, $window) {
	$scope.getFunctionID = function(testFunction) {
		$window.functionId = testFunction;
		$http({
			url : "views/getFeatureFilterByFunctionalIdITAP",
			method : "POST",
			data : testFunction,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["Featurelist"] = data;
		});
	};
};

function addFunctionCtrl($scope, $http, $rootScope, $window) {
	var newfeature = [];
	var newfeatureforUpdate=[];
	
	$scope.addFunction = function() {
		var result = {};
		var flag = 0;
		var testFunction = $scope.addFunctionForm.functionName.$viewValue;
		var description = $scope.addFunctionForm.description.$viewValue;
		
		for (var i=0;i<$rootScope.AppFuncPopUpsName.length; i++){
			if(testFunction == $rootScope.AppFuncPopUpsName[i].appFunctionName)
				flag=1;
			}
		
		if(flag == 1){
			alert('Function Name already exists in The Application. Try with different Data');
			return false;
		}
		result["testFunction"] = testFunction;
		result["description"] = description;
		result["appID"] = $window.appID;
		result["newfeature"] = newfeature;
		$http({
			url : "views/addFunctionITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.functionName='';
			 $scope.description='';
			 $scope.addFunctionForm.$setPristine();
			 alert("Successfully Persisted");
			$window.functionId = data.functionalID;
			$http({
				url : "views/getFunctionFilterByAppIdITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["AppFuncPopUpsName"] = data;
			});

		});
	};
	
	$scope.editFunction = function(appfunctionality) {
		
		var functionalID = appfunctionality.appFunctionId;
		var temp = {};
		temp["functionalID"] = functionalID;
		
		$http({
			url : "views/editFunctionITAP",
			method : "POST",
			data : temp,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.functionalID = data.appFunctionId;
			$scope.functionalName = data.appFunctionName;
			$scope.description = data.appFunctionDesc;
			$scope["listOfFeatureForEdit"] = data.appFeature;
			
		});
	};
	
	$scope.updateFunction = function() {
		var result = {};
		var flag = 0;
		var testFunction = $scope.updateOfFunctionForm.functionalName.$modelValue;
		var description = $scope.updateOfFunctionForm.description.$modelValue;
		result["functionalID"]= $scope.updateOfFunctionForm.functionalID.$modelValue;
		
		for (var i=0;i<$rootScope.AppFuncPopUpsName.length; i++){
			if(testFunction == $rootScope.AppFuncPopUpsName[i].appFunctionName)
				flag=1;
			}
		
		if(flag == 1){
			alert('Function Name already exists in The Application. Try with different Data');
			return false;
		}
		result["testFunction"] = testFunction;
		result["description"] = description;
		result["appID"] = $window.appID;
		result["newfeature"] = newfeatureforUpdate;
		$http({
			url : "views/updateFunctionITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$window.functionId = data.functionalID;
			$http({
				url : "views/getFunctionFilterByAppIdITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["AppFuncPopUpsName"] = data;
			});

		});
	};

	$scope.addFeature = function() {
		var obj = {};
		var key = $scope.addFunctionForm.featureName.$viewValue;
		var value = $scope.addFunctionForm.featureDescription.$viewValue;
		obj[key] = value;
		newfeature.push(obj);
		
//		$scope.addFunctionForm.$setPristine();
	};
	
	$scope.addFeatureUpdate = function() {
		var obj = {};
		var key = $scope.updateOfFunctionForm.featureName.$viewValue;
		var value = $scope.updateOfFunctionForm.featureDescription.$viewValue;
		obj[key] = value;
		newfeatureforUpdate.push(obj);
		
	};
	
}

//Scheduler - Starts

function getSchedulerController($scope, $http, $rootScope, $window, $filter) {
	var taskDate = "";
	var currDate = new Date();
	 $(document).on("change","#datepicker", function () {
		 taskDate = new Date(this.value); 
		   var selectedDate= $filter('date')(taskDate,'yyyy-MM-dd');
			var sysDate= $filter('date')(currDate,'yyyy-MM-dd');
			if(selectedDate < sysDate){
				alert("Scheduler Date should not be less the current Date");
				$(this).val('');
				return false;
			}
		});
	 
	 var taskDateUp = "";
	 var currDateUp = new Date();
	 $(document).on("change",".datepicker", function () {
		 taskDateUp = new Date(this.value); 
			var selectedDate= $filter('date')(taskDateUp,'yyyy-MM-dd');
			var sysDate= $filter('date')(currDateUp,'yyyy-MM-dd');
			if(selectedDate < sysDate){
				alert("Scheduler Date should not be less the current Date");
				$(this).val('');
				return false;
			}
		}); 
	 var taskDateUp = "";
	 var currDateUp = new Date();
	 $(document).on("change",".datepicker", function () {
		 taskDateUp = new Date(this.value); 
			var selectedDate= $filter('date')(taskDateUp,'yyyy-MM-dd');
			var sysDate= $filter('date')(currDateUp,'yyyy-MM-dd');
			if(selectedDate < sysDate){
				alert("Scheduler Date should not be less the current Date");
				$(this).val('');
				return false;
			}
		}); 
	 $scope.checkHour = function(){
			var hour = $scope.addTestExectionForm.hours.$viewValue;
			if(hour<0 || hour > 23){
				alert("Invalid Value for Hour");
				return false;
			}
		};
		
		
	 $scope.checkMins = function(){
			var mins = $scope.addTestExectionForm.mins.$viewValue;
			if(mins < 0 ||mins > 59){
				alert("Invalid Value for Minutes");
				return false;
			}
		};
		
	 $scope.checkSeconds = function(){
			var seconds = $scope.addTestExectionForm.seconds.$viewValue;
			if(seconds < 0 || seconds > 59){
				alert("Invalid Value for Seconds");
				return false;
			}
		};
		
	 $scope.checkHourUp = function(){
		var hour = $scope.updateTestExectionForm.scheduleHr.$viewValue;
		if(hour<0 || hour > 23){
			alert("Invalid Value for Hour");
			return false;
		}
	};
		
		
	 $scope.checkMinsUp = function(){
		var mins = $scope.updateTestExectionForm.scheduleMin.$viewValue;
		if(mins < 0 ||mins > 59){
			alert("Invalid Value for Minutes");
			return false;
		}
	};
		
	 $scope.checkSecondsUp = function(){
		var seconds = $scope.updateTestExectionForm.scheduleSec.$viewValue;
		if(seconds < 0 || seconds > 59){
			alert("Invalid Value for Seconds");
			return false;
		}
	};
	
	$scope.closeTestExecutionPlan=function() {
		
		$scope.schedulerName="";
		$scope.testExePlanID="";
		$scope.testDataID="";
		$scope.agentID="";
		$scope.frequency="";
		$scope.notification="";
		$scope.hours="";
		$scope.mins="";
		$scope.seconds="";
		$scope.mydate="";
		$scope.mainArr="";
		$scope.schedulerTime="";
		$scope.scheduleDate="";
		$scope.addTestExectionForm.$setPristine();
	};
	
	$scope.addTestExecutionPlan = function() {
		var result = {};
		var schedulerName = $scope.addTestExectionForm.schedulerName.$viewValue;
		var testExePlanName = $scope.addTestExectionForm.testExePlanID.$modelValue;
		var testExeDataName = $scope.addTestExectionForm.testDataID.$modelValue;
		var testagentId = $scope.addTestExectionForm.agentID.$modelValue;
		var frequency = $scope.addTestExectionForm.frequency.$modelValue;
		var notification = $scope.addTestExectionForm.notification.$viewValue;
		var schedulerTime =  $scope.addTestExectionForm.hours.$viewValue +":"+
								$scope.addTestExectionForm.mins.$viewValue +":"+
								 $scope.addTestExectionForm.seconds.$viewValue ;
		
		$scope.mydate= $filter('date')(taskDate,'yyyy-MM-dd');
		
		var multiLane = $scope.multiLane;

		result["schedulerName"] = schedulerName;
		result["testPlanID"] = testExePlanName;
		result["testDataID"] = testExeDataName;
		result["agentID"] = testagentId;
		result["scheduleTime"] = $scope.mydate+" "+schedulerTime;
		result["frequency"] = frequency;
		result["notifications"] = notification;
		result["appID"] = $window.appID;
		
		if(multiLane=="yes"){
		
			var mainArr = [];
			$('#scheduleTable').find('tbody').find('tr').each(function(){
				var rowArr = [];
				$(this).find('td').each(function(){
					if($(this).find('select').length > 0){
						if($(this).find('select').val() != "" && $(this).find('select').val() != undefined && $(this).find('select').val() != null){
							rowArr.push($(this).find('select').val().trim());
						}else{
							rowArr.push("");
						}
						
						
					}else if($(this).find('input:text').length > 0){
						if($(this).find('input:text').val() != "" && $(this).find('input:text').val() != undefined && $(this).find('input:text').val() != null){
							rowArr.push($(this).find('input:text').val().trim());
						}else{
							rowArr.push("");
						}
					}

				});
				
				mainArr.push(rowArr);
				
			});
			
			result["mainArr"] = mainArr;
		}
		
		if(multiLane=="yes"){
			result["multiLanes"] = true;
		}
		else{
			result["multiLanes"] = false;
		}
		
		$http({
			url : "views/addTestExection",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$scope.schedulerName="";
			$scope.testExePlanID="";
			$scope.testDataID="";
			$scope.agentID="";
			$scope.frequency="";
			$scope.notification="";
			$scope.hours="";
			$scope.mins="";
			$scope.seconds="";
			$scope.mydate="";
			$scope.mainArr="";
			$scope.schedulerTime="";
			$scope.scheduleDate="";
			$scope.addTestExectionForm.$setPristine();
			
			$http({
				url : "views/getTestExecutionRunSuites",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {

				$rootScope["scheduledList"] = data;
				alert("data insertion is successfull !!!");
			});
		});

	};
	
	$scope.executeNow = function(scheduleId) {
		
		$http({
			url : "views/updateSchedulerTime",
			method : "POST",
			data : scheduleId,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			
			$http({
				url : "views/getTestExecutionRunSuites",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				alert("Your Test is Scheduled");
				$rootScope["scheduledList"] = data;
			});
			
		});
	};
	
	$scope.editSchedulerDeatils = function(scheduleId) {
		window.scheduleIds = scheduleId;	
		$http({
			url : "views/editScheduler",
			method : "POST",
			data : scheduleId,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			schedulerDetails = data.schedulerDetails;
			if (schedulerDetails == undefined){
				alert("Scheduler is completed");
			}
			else{
			$scope.format = 'yyyy-MM-dd HH:mm:ss';
			$scope.schedulerName=schedulerDetails.schedulerName;
			$scope.notifications=schedulerDetails.notifications;
			$scope.testExePlanID=schedulerDetails.testPlanID;
			$scope.testDataID=schedulerDetails.testDataID;
			$scope.agentID=schedulerDetails.agentID;
			$scope.frequency=schedulerDetails.frequency;
			$scope.schedulerTime=data.scheduleTime;
			$scope.checkLaneEdit=schedulerDetails.multiLanes;
			$scope.scheduleID=schedulerDetails.scheduleID;
			$scope.scheduleDate=data.scheduleDate;
			$scope.scheduleHr=data.scheduleHr;
			$scope.scheduleMin=data.scheduleMin;
			$scope.scheduleSec=data.scheduleSec;
			
			schedulerLaneDetails=data.schedulerLaneDetails;
		}
		});
	};
	
	$scope.saveTestExecutionPlan = function() {
		
		var result = {};
		result["schedulerName"] = $scope.updateTestExectionForm.schedulerName.$modelValue;
		result["scheduleID"] = $scope.updateTestExectionForm.scheduleID.$modelValue;
		result["notifications"] = $scope.updateTestExectionForm.notifications.$modelValue;
		result["testExePlanID"] = $scope.updateTestExectionForm.testExePlanID.$modelValue;
		result["frequency"] = $scope.updateTestExectionForm.frequency.$modelValue;
		result["testDataID"] = $scope.updateTestExectionForm.testDataID.$modelValue;
		result["agentID"] = $scope.updateTestExectionForm.agentID.$modelValue;
		result["appID"] = $window.appID;
		
		var schedulerTime =  $scope.updateTestExectionForm.scheduleHr.$viewValue +":"+
								$scope.updateTestExectionForm.scheduleMin.$viewValue +":"+
									$scope.updateTestExectionForm.scheduleSec.$viewValue ;

		$scope.mydate= $filter('date')(taskDateUp,'yyyy-MM-dd');
		
		result["scheduleTime"] = $scope.mydate+" "+schedulerTime;
		
		var multiLane = $scope.multiLane;
		if(multiLane=="yes"){
		var mainArrUpdate = [];
		$('#scheduleTables').find('tbody').find('tr').each(function(){
			var rowArr = [];
			
			var updateAgentName=$(this).find('#agentName').text().trim();
			rowArr.push(updateAgentName);
			var updateLaneName=$(this).find('#laneName').text().trim();
			rowArr.push(updateLaneName);
			var updateClone=$(this).find('#clone').text().trim();
			rowArr.push(updateClone);
			var updateIterations=$(this).find('#iterations').text().trim();
			rowArr.push(updateIterations);
			var updateRamp=$(this).find('#ramp').text().trim();
			rowArr.push(updateRamp);
			var updateDuration=$(this).find('#duration').text().trim();
			rowArr.push(updateDuration);
			
			$(this).find('td').each(function(){
				if($(this).find('select').length > 0){
					if($(this).find('select').val() != "" && $(this).find('select').val() != undefined && $(this).find('select').val() != null){
						rowArr.push($(this).find('select').val().trim());
					}else{
						rowArr.push("");
					}
					
					
				}else if($(this).find('input:hidden').length > 0){
					if($(this).find('input:hidden').val() != "" && $(this).find('input:hidden').val() != undefined && $(this).find('input:hidden').val() != null){
						rowArr.push($(this).find('input:hidden').val().trim());
					}else{
						rowArr.push("");
					}
				}
				
				else if($(this).find('input:text').length > 0){
					if($(this).find('input:text').val() != "" && $(this).find('input:text').val() != undefined && $(this).find('input:text').val() != null){
						rowArr.push($(this).find('input:text').val().trim());
					}else{
						rowArr.push("");
					}
				}

			});
			
			mainArrUpdate.push(rowArr);
			
		});
		
		result["mainArrUpdate"] = mainArrUpdate;
	};
	
	if(multiLane=="yes"){
		result["multiLanes"] = true;
	}
	else{
		result["multiLanes"] = false;
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
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {

				$rootScope["scheduledList"] = data;
				alert("data updation is successfull !!!");
			});
		});
		
	
	};
	
	$scope.getAllRunningJobs=function(){
		//alert("Running Jobs List");
		var appId = $window.appID;
		$http({
			url : "views/getAllRunningJobsiTAP",
			method : "POST",
			data : appId,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//resultData = data;
			$rootScope["runningJobsList"] = data;
		});
	};
	
	$scope.getAllScheduledJobs=function(){
		var appId = $window.appID;
		$http({
			url : "views/getAllScheduledJobsiTAP",
			method : "POST",
			data : appId,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			//resultData = data;
			$rootScope["schJobsList"] = data;
		});
	};
	
	$scope.getAllCompletedJobs=function(){
		var appId = $window.appID;
		$http({
			url : "views/getAllCompletedJobsiTAP",
			method : "POST",
			data : appId,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			
			$rootScope["completedJobsList"] = data;
		});
	};
}



// LandingPage DAshboard
function spagoCallOnLoad($scope) {
	Sbi.sdk.services.setBaseUrl({
		protocol : 'http',
		host : 'localhost',
		port : '8091',
		contextPath : 'SpagoBI'
	});

	var cb = function(result, args, success) {
		if (success === true) {
			execTest3();
		} else {
			//alert('ERROR: Wrong username or password');
		}
	};

	Sbi.sdk.api.authenticate({
		params : {
			user : 'biadmin',
			password : 'biadmin'
		},
		callback : {
			fn : cb,
			scope : this,
			args : {}
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
		document.getElementById('chart').innerHTML = html;
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

function tableCntrl($scope,$http,$rootScope,$window,$filter) {
    $scope.boolChk=false;
    $scope.value="recent";
    $scope.dateDiv=false;
    $scope.fromDate="";
    $scope.toDate="";
    $scope.repNavBarBool="lastruns";
    
    /*Radio selection Methods*/
    $scope.divChange = function(event) {
		
    	var clikedElm = event.srcElement || event.target;
		$scope.repNavBarBool = clikedElm.id;
		var type = clikedElm.id;
    	
		if (type == "currentweek") {
    		$scope.loadJson("week");
    	}else if(type == "lastruns"){
    		$scope.loadJson("recent");
    	}else if(type == "date"){
    		$rootScope["jsons"]=[];	
    	}else if(type == "allreport") {
    		$scope.loadJson("all");
    	}
    };
    
    $scope.setDate = function(date,id) {
    	if(id === "fromDate") {
		$scope.fromDate=date;
	} else {
		$scope.toDate=date;
    }
	};
	
	
	$scope.testFlow = function() {
		$http({
		url : "views/getTestingFlow",
		method : "POST",
		data :appIDforReport,
		headers : {
		'Content-Type' : 'application/json'
		}
		}).success(function(data) {
			 var result = data;
			var spanElement = document.createElement('span');
			spanElement.innerHTML = result.name;
			var brElement = document.createElement('br');
			var divElement = document.createElement('div');
			divElement.id = "node_" + result.id;
			divElement.className = "applicationClass";
			divElement.appendChild(spanElement);
			divElement.appendChild(brElement);
			document.body.appendChild(divElement);

			for(var i = 0; i < result.flow.length; i++) {
				var item = result.flow[i];
				var cspanElement = document.createElement('span');
				cspanElement.innerHTML = item.name;
				var cbrElement = document.createElement('br');
				var cdivElement = document.createElement('div');
				cdivElement.id = "node_" + item.id;
				cdivElement.appendChild(cspanElement);
				switch(item.type) {
				case "TestPlan":
					cdivElement.className = "testPlanClass";
					cdivElement.appendChild(cbrElement);
					document.getElementById("node_" + item.parentId).appendChild(cdivElement);
					break;
				case "TestSuite":
					cdivElement.className = "testSuiteClass";
					cdivElement.appendChild(cbrElement);
					document.getElementById("node_" + item.parentId).appendChild(cdivElement);
					break;
				case "TestScenario":
					cdivElement.className = "testScenarioClass";
					cdivElement.appendChild(cbrElement);
					document.getElementById("node_" + item.parentId).appendChild(cdivElement);
					break;
				case "TestCase":
					cdivElement.className = "testCaseClass";
					document.getElementById("node_" + item.parentId).appendChild(cdivElement);
					if(i < result.flow.length - 1) {
						var adivElement = document.createElement('div');
						adivElement.className = "arrowClass";
						document.getElementById("node_" + item.parentId).appendChild(adivElement);
					}
					break;
				}
				console.log("Adding " + item.id + " to " + item.parentId);
			}
		});
		};
		
	$scope.dateGoClick = function() {
		if( $scope.fromDate == null | $scope.toDate == null || $scope.toDate.length == 0  || $scope.fromDate.length == 0) {
			alert("FromDate & ToDate are mandatory");
		} else if ( $scope.toDate < $scope.fromDate) {
			alert("FromDate must be greater than ToDate");
		} else {
			var type = "time:"+$scope.fromDate+":"+$scope.toDate;
			$scope.loadJson(type);
		}
	};
	
	$scope.loadJson = function(type) {
		$http({
			url : "views/getSchedulerRunDetails?runId="+appID+"&type="+type,
			method : "GET",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["jsons"]=[];
			for(var i=0; i<data.length; i++)
				$rootScope["jsons"].push({
					testRunID : data[i].testRunID,
                    result : ( data[i].result === true ) ? "PASS" : "FAIL",
					startDateTime : $filter('date')(data[i].startDateTime,'medium')
				});
			//$rootScope["jsons"]=data;
			//$scope.jsons = data;
		}).error(function(status) {
			$scope.jsons = status;
		});
//	    $scope.currentPage=0;
//	    $scope.pageStart=0;
	};
	
    /*Report PopUp Method*/
	$scope.reportClick = function(runID) {
	$scope.boolChk=true;
	Sbi.sdk.services.setBaseUrl({
		protocol : 'http',
		host : 'localhost',
		port : '8091',
		contextPath : 'SpagoBI'
	});
	
	var html = Sbi.sdk.api.getDocumentHtml({
		documentLabel : 'testrunsummary',
		userId : 'biadmin',
		executionRole : '/spagobi/admin',
		parameters : {
			run_id : runID
		},
		displayToolbar : true,
		displaySliders : false,
		iframe : {
			height : '850px',
			width : '90%',
			style : 'border: 0px;'
		}
	});
	document.getElementById('spagoLoad').innerHTML = html;
	};
	$scope.closeClk = function(){
		$scope.boolChk=false;
	};
}

//Directive for jquery datepicker
App.directive('jqdatepicker', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attrs, ngModelCtrl) {
            element.datepicker({
                dateFormat: 'yy-mm-dd',
                onSelect: function (date) {
                    var datArr = date.split("-");
                    var utcVal = Date.UTC(datArr[0], datArr[1]-1, datArr[2]);
                    scope.setDate(utcVal,attrs.id);
                    scope.$apply();
                }
            });
        }
    };
});

function forgotPasswordController($scope, $http, $rootScope, $window) {
//	alert("client side controller invoked");
	$scope.forgotPassword = function(item) {
		var result = {};
		var username = $scope.forgotPasswordForm.username.$viewValue;
		var emailID = $scope.forgotPasswordForm.emailID.$viewValue;

//		alert("Username"+username);
//		alert("Email ID"+emailID);
		
		result["username"] = username;
		result["emailID"] = emailID;
		//alert("Result " + result);
	$http({
		url : "views/forgotPasswordITAP",
		method : "POST",
		data : result,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		alert("Your password has been successfully sent...");
	});

	};
}

function resetPasswordController($scope, $http, $rootScope, $window) {
	
	$scope.resetPassword = function(item) {
	var result = {};
	var newPassword = $scope.resetPasswordForm.newpassword.$viewValue;
	var confirmNewPassword = $scope.resetPasswordForm.confirmpassword.$viewValue;
	var username = $('#encryptedUserName').val();
	if(newPassword == confirmNewPassword){
		result["newPassword"] = newPassword;
		result["confirmNewPassword"] = confirmNewPassword;
		result["username"] =username;
		$http({
			url : "views/resetPasswordITAP",
			method : "POST",
			data : result,
			headers : {
			'Content-Type' : 'application/json'
			}
			
			}).success(function(data) {
			alert("Your password has been successfully reset...");
			});
	}else{
		alert("conform password is incorrect");
	}
	};
	}


function rolesController($scope, $http, $rootScope, $window){
	$scope.addRoles = function() {
	var result = {};
	var roleName = $scope.addRoleForm.roleName.$viewValue;
	var roleDescription = $scope.addRoleForm.roledescription.$viewValue;
	result["roleName"] = roleName;
	result["roleDescription"] = roleDescription;
	$http({
	url : "views/addRole",
	method : "POST",
	data : result,
	headers : {
	'Content-Type' : 'application/json'
	}
	}).success(function(data) {
		$http({
			url : "views/getAllRole",
			method : "GET",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["roles"] = data;
		});
	});
	};
	
	$scope.editRoles = function(item) {
		$http({
		url : "views/getRoleByRoleID",
		method : "POST",
		data : item,
		headers : {
		'Content-Type' : 'application/json'
		}
		}).success(function(data) {
			$scope.editRoleID = data.roleID;
			$scope.roleName = data.authority;
			$scope.roledescription = data.rolesDescription;
		});
		};
		
		$scope.updateRoles = function() {
			var result = {};
			var roleID = $scope.updateRoleForm.editRoleID.$viewValue;
			var roleName = $scope.updateRoleForm.roleName.$viewValue;
			var roleDescription = $scope.updateRoleForm.roledescription.$viewValue;
			result["roleID"] = roleID;
			result["roleName"] = roleName;
			result["roleDescription"] = roleDescription;
			$http({
			url : "views/updateRole",
			method : "POST",
			data : result,
			headers : {
			'Content-Type' : 'application/json'
			}
			}).success(function(data) {
				$http({
					url : "views/getAllRole",
					method : "GET",
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data) {
					$rootScope["roles"] = data;
				});
			});
			};	
}

function getRolesController($scope, $http, $rootScope, $window) {
		$http({
			url : "views/getAllRole",
			method : "GET",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$rootScope["roles"] = data;
		});
};

function getRunnersController($scope, $http, $rootScope, $window) {

$http({
	url : "views/getRunnerNameListITAP",
	method : "GET",
	headers : {
		'Content-Type' : 'application/json'
	}
}).success(function(data) {

	$scope["runnerNameLists"] = data;
});


$scope.editRunners = function(runnerID) {
	$http({
	url : "views/getRunnerById",
	method : "POST",
	data : runnerID,
	headers : {
	'Content-Type' : 'application/json'
	}
	}).success(function(data) {
		$scope.editRunnerID = data.runnerID;
		$scope.editRunnerName = data.runnerName;
		$scope.runnerdescription = data.description;
	});
	};
	
$scope.updateRunners = function() {
	var result = {};
	var runnerID = $scope.updateRunnerForm.editRunnerID.$viewValue;
	var runnerName = $scope.updateRunnerForm.editRunnerName.$viewValue;
	var runnerDescription = $scope.updateRunnerForm.runnerdescription.$viewValue;
	result["runnerID"] = runnerID;
	result["runnerName"] = runnerName;
	result["runnerDescription"] = runnerDescription;
	$http({
	url : "views/updateRunnerTAP",
	method : "POST",
	data : result,
	headers : {
	'Content-Type' : 'application/json'
	}
	}).success(function(data) {
		$http({
			url : "views/getRunnerNameListITAP",
			method : "GET",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {

			$scope["runnerNameLists"] = data;
		});
		});
	};	

	
	$scope.addRunners = function() {
		
		var result = {};
		var runnerName = $scope.addRunnerForm.runnerName.$viewValue;
		var runnerDescription = $scope.addRunnerForm.runnerDes.$viewValue;
		result["runnerName"] = runnerName;
		result["runnerDescription"] = runnerDescription;
	
		$http({
		url : "views/addRunnerITAP",
		method : "POST",
		data : result,
		headers : {
		'Content-Type' : 'application/json'
		}
		}).success(function(data) {
			$http({
				url : "views/getRunnerNameListITAP",
				method : "GET",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {

				$scope["runnerNameLists"] = data;
			});
		});
		};	
};


function testFlowController($scope, $http, $rootScope, $window) {
	
};




/**Changes for Object Group start here*/
/**

function addObjTypeControllerITAP($scope, $http, $window, $rootScope){
	$scope.closeObjGroup = function() {
		$scope.ObjGrpName='';
		$scope.ObjGrpDescription='';
		
	};
	$scope.addObjGrp=function(){
		var result = {};
		var screenID = $scope.addObjectGroupForm.screenID.$modelValue;
		var objGrpName = $scope.addObjectGroupForm.ObjGrpName.$viewValue;
		var objGrpDesc = $scope.addObjectGroupForm.ObjGrpDescription.$viewValue;
		result["screenID"]=screenID;
		result["objGrpName"]=objGrpName;
		result["objGrpDesc"]=objGrpDesc;
		result["appID"] = $window.appID;
		$http({
			url : "views/addObjGrpITAP",
			method : "POST",
			data : result,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			$scope.ObjGrpName='';
			$scope.ObjGrpDescription='';
			$scope.screenID='';
			$http({
				url : "views/getObjectGroupBoxByAppIdITAP",
				method : "POST",
				data : $window.appID,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data) {
				$rootScope["objectGroupList"] = data;
				
			});
		});
	};
}
*/

/**Changes for Object Group End here*/

