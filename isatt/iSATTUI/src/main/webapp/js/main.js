$(document).ready(function(e) {
	
					$("#application_status").hide();
					$("#application").hide();
					$("#testSuitesCase").hide();
					$("#executionMain").hide();
					$("#object").hide();
					$("#reports").hide();
					$("#TestDataDefinition").hide();
					$(".EditTestDefinition").hide();
					$('.editTestParamData').hide();
					$("#selected_testData").hide();
					$("#addTestPop").hide();
					$(".editStpsPop").hide();
					$("#editTestPop").hide();
    				$("#addFunctional").hide();
    				$('.addTestStepPops').hide();
    				$("#selected_feature").hide();
    				$("#ApplicationFunction").hide();
    				$("#TestApplicationFeature").hide();
    				$(".editApplicationPop").hide();
    				$(".editFunctionPop").hide();
    				$(".addObjectGrpPop").hide();
    				$(".addTestSuite").hide();
    				$(".testSuitesForPlan").hide();
    				$(".editTestPlan").hide();
    				$(".editTestSuite").hide();
    				$(".addObjectPop").hide();
    				$(".addActionPop").hide();
    				$(".addObjectTypePop").hide();
    				$(".objGrpsForScreen").hide();
    				$(".editObjGrp").hide();
    				$(".editScreenDetails").hide();
    				$(".addParamGrpDetailsPop").hide();
    				$(".addFeaturePop").hide();
    				$(".featuresForFunction").hide();
    				$("#appFunctionals").hide();
    				$(".addNewScreenPop").hide();
    				$(".editAppFeaturePop").hide();
    				$(".objsForObjGrpList").hide();
    				$(".editObjectDetailsPop").hide();
    				$(".conditionGrp").hide();
    				$(".IdentifierTabGrp").hide();
    				$(".ReplacementTabGrp").hide();
    				$(".addConditionGrpPop").hide();
    				$(".addIdentifierTypesPop").hide();
    				$(".addReplacementStringsPopUps").hide();
    				$(".editConditionGrpPop").hide();
    				$(".editidentifierTyPop").hide();
    				$(".editReplaceStringsTyPop").hide();
    				$(".conditionForConGrpList").hide();
    				$(".addNewCondition").hide();
    				$(".editConditionDetailsPop").hide();
    				 $("#testImport").hide();
    				 $("#addImport").hide();
    				 $(".paramGrp").hide();
    				 $(".addParamGrpPop").hide();
    				 $(".editParamGrpPop").hide(); 
    				 $(".paramsForParamGrpList").hide();
    				 $(".addParameterPop").hide();
    				 $(".editParameterPop").hide();
    				 $("#homepage").show();
    				 $(".importExcelPopUp").hide();
    				 $(".importExcelInAppPopUp").hide();
    				 $("#settings").hide();
					$("#ApplicationDetails > div:First-child").hide();
					/*------- Application Selection----*/
					$(document)
							.on(
									"click",
									"#select_application",
									function(e) {
										$("#homepage").hide();
										$("#application_status").hide();
										$(
												"#ApplicationDetails > div:First-child")
												.show();
										$("#reports")
										.show();
										var application_name = $(this).find(
												'h3').text();
										var subSpan = $("<span class='subHeading'>"
												+ application_name
												+ "</span><span class='tab_selected'>/ Overview</span>");
										$("#dashboard").find('h2').append(
												subSpan);

										$(".subHeading")
												.bind(
														"click",
														function(e) {
															$(
																	"#application_status")
																	.hide();
															$("#application")
																	.hide();
															$("#testSuitesCase")
																	.hide();
															$("#executionMain")
																	.hide();
															$("#object").hide();
															$("#reports")
																	.show();
															$("#executionMain")
																	.hide();
															$(".EditTestDefinition")
																	.hide();
															$('.editTestParamData').hide();
															$("#addTestPop")
																	.hide();
															$("#editTestPop")
																	.hide();
															$(".addTestSuite").hide();
															$(".addObjectGrpPop").hide();
															$(".testSuitesForPlan").hide();
															$(".editTestPlan").hide();
															$(".editTestSuite").hide();
															$(".addObjectPop").hide();
															$(".addActionPop").hide();
															$(".addObjectTypePop").hide();
															$(".objGrpsForScreen").hide();
															$(".editObjGrp").hide();
															$(".editScreenDetails").hide();
															$(".addParamGrpDetailsPop").hide();
															$(".addFeaturePop").hide();
															$(".featuresForFunction").hide();
															$("#appFunctionals").hide();
															$(".addNewScreenPop").hide();
															$(".editAppFeaturePop").hide();
															$(".objsForObjGrpList").hide();
															$(".editObjectDetailsPop").hide();
															$(".conditionGrp").hide();
															$(".IdentifierTabGrp").hide();
															$(".ReplacementTabGrp").hide();
															$(".addConditionGrpPop").hide();
															$(".addIdentifierTypesPop").hide();
															$(".addReplacementStringsPopUps").hide();
															$(".editConditionGrpPop").hide();
															$(".editidentifierTyPop").hide();
															$(".editReplaceStringsTyPop").hide();
															$(".conditionForConGrpList").hide();
															$(".addNewCondition").hide();
															$(".editConditionDetailsPop").hide();
															 $("#testImport").hide();
															 $("#addImport").hide();
															 $(".paramGrp").hide();
															 $(".addParamGrpPop").hide();
										    				 $(".editParamGrpPop").hide(); 
										    				 $(".paramsForParamGrpList").hide();
										    				 $(".addParameterPop").hide();
										    				 $(".editParameterPop").hide();
										    				 $(".importExcelPopUp").hide();
										    				 $(".importExcelInAppPopUp").hide();
															$(
																	"#ApplicationDetails > div:First-child ul li")
																	.find('a')
																	.removeClass(
																			'activeTab');
															$(".overviewTab")
																	.addClass(
																			'activeTab');
														});
									});
					/*--Add new Application---*/
					$("#add_new_app")
							.bind(
									"click",
									function(e) {
										var app_name = $(
												"#addApplication_popup > div:first-child + div")
												.find("input[type='text']")
												.val().trim();
										var app_description = $(
												"#addApplication_popup > div:first-child + div")
												.find("textarea").val().trim();
										var noOfItems = $('#application_lists>div').length;

										for ( var i = 0; i < noOfItems; i++) {
											var all_app_name = $(
													"#application_lists>div")
													.eq(i).find("h3").text();
											if (all_app_name.toUpperCase() == app_name
													.toUpperCase()) {
												$("#existing_app").css({
													"color" : "red",
													"display" : "inline-block"
												}).show();
												$("#app_desc_error").hide();
												$("#app_name_error").hide();
												return false;
											}
										}

										if (app_name.length > 0
												&& app_description.length > 0) {
											var app_name = $(
													"#addApplication_popup > div:first-child + div")
													.find("input[type='text']")
													.val();
											$("#application_lists")
													.append(
															"<div id='select_application'><h3 class='floatleft'>"
																	+ app_name
																	+ "</h3>"
																	+ "<a href='#' class='cancelApplication floatright'><span>Cancel</span></a><a href='#' class='editApplication floatright'><span>Edit</span></a>"
																	+ "<ul class='clear'><li><span>00</span>Test Suites</li><li><span>00</span>Active Test Run</li><li><span>00</span>No. of Issues</li></ul></div>");
											$("#addApplication_popup").hide();
											$('#overLay').hide();
										} else if (app_name == null
												|| app_name == "") {
											$("#app_name_error").css({
												"color" : "red",
												"display" : "inline-block"
											}).show();
											$("#app_desc_error").hide();
											$("#existing_app").hide();
											return false;
										} else if (app_description == null
												|| app_description == "") {
											$("#app_desc_error").css({
												"color" : "red",
												"display" : "inline-block"
											}).show();
											$("#app_name_error").hide();
											$("#existing_app").hide();
											return false;
										}

										$(
												"#addApplication_popup > div:first-child + div")
												.find("input[type='text']")
												.val("");
										$(
												"#addApplication_popup > div:first-child + div")
												.find("textarea").val("")
									});
					$(document).on("click", ".cancelApplication", function(e) {
						e.stopPropagation();
						e.preventDefault();
						$(this).parent().remove();
					});
					/*------Adding Steps-----*/
					$('#addSteps')
							.click(
									function(e) {
										e.preventDefault();
										$("#addStep")
												.append(
														"<tr><td><input type='text' placeholder='Add Text'/></td>"
																+ "<td><input type='text' placeholder='Add Text'/></td><td><input type='text' placeholder='Add Text'/></td>"
																+ "<td><input type='text' placeholder='Add Text'/></td><td><input type='text' placeholder='Add Text'/></td>"
																+ "<td><input type='text' placeholder='Add Text'/></td><td><input type='text' placeholder='Add Text'/></td>"
																+ "<td><a href='#' class='edit'><span>Delete</span></a></td><td><a href='#' class='cancel'><span>Delete</span></a></td></tr>");
									});
					/*----delete---*/
					$(document).on("click", ".cancel", ".cancelSet",
							function(e) {
								e.preventDefault();
								$(this).parent().parent().remove();
							});
					/*---edit----*/

					$(document).on("click", "#addCase table tr td a.edit",
							function(e) {
								e.preventDefault();
								$("#addsteps_dataset").show();
							});

					/*---Navigator--*/

					$("#ApplicationDetails > div:First-child a")
							.bind(
									"click",
									function(e) {
										e.preventDefault();
										$(".tab_selected").remove();
										var selected_tab = $(this).text();
										var tab_clicked = $("<span class='tab_selected'>"
												+ "  /  "
												+ selected_tab
												+ "</span>");
										$("#dashboard").find('h2').append(
												tab_clicked);

										$(".subHeading").click(function(e) {
											$("#reports").show();
											$("#application_status").hide();
											$("#application").hide();
											$("#testSuitesCase").hide();
											$("#object").hide();
											$("#executionMain").hide();
											$("#TestDataDefinition").hide();
											$(".EditTestDefinition").hide();
											$('.editTestParamData').hide();
											$("#addTestPop").hide();
											$("#editTestPop").hide();
											$("#TestApplicationFeature").hide();
											$(".addObjectGrpPop").hide();
											$(".addTestSuite").hide();
											$(".testSuitesForPlan").hide();
											$(".editTestPlan").hide();
											$(".editTestSuite").hide();
											$(".addObjectPop").hide();
											$(".addActionPop").hide();
											$(".addObjectTypePop").hide();
											$(".objGrpsForScreen").hide();
											$(".editObjGrp").hide();
											$(".editScreenDetails").hide();
											$(".addParamGrpDetailsPop").hide();
											$(".addFeaturePop").hide();
											$(".featuresForFunction").hide();
											$("#appFunctionals").hide();
											$(".addNewScreenPop").hide();
											$(".editAppFeaturePop").hide();
											$(".objsForObjGrpList").hide();
											$(".editObjectDetailsPop").hide();
											$(".conditionGrp").hide();
											$(".IdentifierTabGrp").hide();
											$(".ReplacementTabGrp").hide();
											$(".addConditionGrpPop").hide();
											$(".addIdentifierTypesPop").hide();
											$(".addReplacementStringsPopUps").hide();
											$(".editConditionGrpPop").hide();
											$(".editidentifierTyPop").hide();
											$(".editReplaceStringsTyPop").hide();
											$(".conditionForConGrpList").hide();
											$(".addNewCondition").hide();
											$(".editConditionDetailsPop").hide();
											 $("#testImport").hide();
											 $("#addImport").hide();
											 $(".paramGrp").hide();
											 $(".addParamGrpPop").hide();
						    				 $(".editParamGrpPop").hide(); 
						    				 $(".paramsForParamGrpList").hide();
						    				 $(".addParameterPop").hide();
						    				 $(".editParameterPop").hide();
						    				 $(".importExcelPopUp").hide();
						    				 $(".importExcelInAppPopUp").hide();
										});

										$(
												"#ApplicationDetails > div:First-child ul li")
												.find('a').removeClass(
														'activeTab');
										$(this).addClass('activeTab');

									});
					$('#ApplicationDetails > div:First-child .overviewTab')
							.bind("click", function(e) {
							//	alert('in overview Tab1');
								e.preventDefault();
								//$("#application_status").show();
								$("#reports").show();
								$("#application").hide();
								$("#testSuitesCase").hide();
								$("#object").hide();
								$("#executionMain").hide();
								$("#TestDataDefinition").hide();
								$(".EditTestDefinition").hide();
								$('.editTestParamData').hide();
								$("#addTestPop").hide();
								$("#editTestPop").hide();
								$("#addFunctional").hide();
								$("#selected_feature").hide();
								$("#ApplicationFunction").hide();
									$("#TestApplicationFeature").hide();
									$(".addObjectGrpPop").hide();
									$(".addTestSuite").hide();
									$(".testSuitesForPlan").hide();
									$(".editTestPlan").hide();
									$(".editTestSuite").hide();
									$(".addObjectPop").hide();
									$(".addActionPop").hide();
									$(".addObjectTypePop").hide();
									$(".objGrpsForScreen").hide();
									$(".editObjGrp").hide();
									$(".editScreenDetails").hide();
									$(".addParamGrpDetailsPop").hide();
									$(".addFeaturePop").hide();
									$(".featuresForFunction").hide();
									$("#appFunctionals").hide();
									$(".addNewScreenPop").hide();
									$(".editAppFeaturePop").hide();
									$(".objsForObjGrpList").hide();
									$(".editObjectDetailsPop").hide();
									$(".conditionGrp").hide();
									$(".IdentifierTabGrp").hide();
									$(".ReplacementTabGrp").hide();
									$(".addConditionGrpPop").hide();
									$(".addIdentifierTypesPop").hide();
									$(".addReplacementStringsPopUps").hide();
									$(".editConditionGrpPop").hide();
									$(".editidentifierTyPop").hide();
									$(".editReplaceStringsTyPop").hide();
									$(".conditionForConGrpList").hide();
									$(".addNewCondition").hide();
									$(".editConditionDetailsPop").hide();
									 $("#testImport").hide();
									 $("#addImport").hide();
									 $(".paramGrp").hide();
									 $(".addParamGrpPop").hide();
				    				 $(".editParamGrpPop").hide(); 
				    				 $(".paramsForParamGrpList").hide();
				    				 $(".addParameterPop").hide();
				    				 $(".editParameterPop").hide();
				    				 $(".importExcelPopUp").hide();
				    				 $(".importExcelInAppPopUp").hide();
							});

					$('#ApplicationDetails > div:First-child .testRunTab')
							.bind("click", function(e) {
								e.preventDefault();
								$("#application_status").hide();
								$("#application").show();
								$("#testSuitesCase").hide();
								$("#object").hide();
								$("#reports").hide();
								$("#executionMain").hide();
								$("#TestDataDefinition").hide();
								$(".EditTestDefinition").hide();
								$('.editTestParamData').hide();
								$("#suite").hide();
								$("#trends").hide();
								$('#testRunsRusults').show();
								$("#addTestPop").hide();
								$("#editTestPop").hide();
								$("#addtest_steps").hide();
								$("#addFunctional").hide();
								$("#selected_feature").hide();
								$("#ApplicationFunction").hide();
									$("#TestApplicationFeature").hide();
									$(".addObjectGrpPop").hide();
									$(".addTestSuite").hide();
									$(".testSuitesForPlan").hide();
									$(".editTestPlan").hide();
									$(".editTestSuite").hide();
									$(".addObjectPop").hide();
									$(".addActionPop").hide();
									$(".addObjectTypePop").hide();
									$(".objGrpsForScreen").hide();
									$(".editObjGrp").hide();
									$(".editScreenDetails").hide();
									$(".addParamGrpDetailsPop").hide();
									$(".addFeaturePop").hide();
									$(".featuresForFunction").hide();
									$("#appFunctionals").hide();
									$(".addNewScreenPop").hide();
									$(".editAppFeaturePop").hide();
									$(".objsForObjGrpList").hide();
									$(".editObjectDetailsPop").hide();
									$(".conditionGrp").hide();
									$(".IdentifierTabGrp").hide();
									$(".ReplacementTabGrp").hide();
									$(".addConditionGrpPop").hide();
									$(".addIdentifierTypesPop").hide();
									$(".addReplacementStringsPopUps").hide();
									$(".editConditionGrpPop").hide();
									$(".editidentifierTyPop").hide();
									$(".editReplaceStringsTyPop").hide();
									$(".conditionForConGrpList").hide();
									$(".addNewCondition").hide();
									$(".editConditionDetailsPop").hide();
									 $("#testImport").hide();
									 $("#addImport").hide();
									 $(".paramGrp").hide();
									 $(".addParamGrpPop").hide();
				    				 $(".editParamGrpPop").hide(); 
				    				 $(".paramsForParamGrpList").hide();
				    				 $(".addParameterPop").hide();
				    				 $(".editParameterPop").hide();
				    				 $(".importExcelPopUp").hide();
				    				 $(".importExcelInAppPopUp").hide();
							});

					$('#ApplicationDetails > div:First-child .testSuiteTab')
							.bind("click", function(e) {
								e.preventDefault();
								$("#application_status").hide();
								$("#application").hide();
								$("#testSuitesCase").show();
								$("#object").hide();
								$("#reports").hide();
								$(".editScenariosPop").hide();
								$(".editCaesPop").hide();
								$(".editStpsPop").hide();
								$("#executionMain").hide();
								$("#TestDataDefinition").hide();
								$(".EditTestDefinition").hide();
								$('.editTestParamData').hide();
								$("#selected_suite").hide();
							//	$("#testSuites").show();
								$("#case_details").hide();
								$("#addCondParamTable").hide();
								$("#case_history").hide();
								$("#addTestPop").hide();
								$("#editTestPop").hide();
								$("#addFunctional").hide();
								$("#selected_feature").hide();
								$("#ApplicationFunction").hide();
									$("#TestApplicationFeature").hide();
									$(".addObjectGrpPop").hide();
									$(".addTestSuite").hide();
									$(".testSuitesForPlan").hide();
									$(".editTestPlan").hide();
									$(".editTestSuite").hide();
									$(".addObjectPop").hide();
									$(".addActionPop").hide();
									$(".addObjectTypePop").hide();
									$(".objGrpsForScreen").hide();
									$(".editObjGrp").hide();
									$(".editScreenDetails").hide();
									$(".addParamGrpDetailsPop").hide();
									$(".addFeaturePop").hide();
									$(".featuresForFunction").hide();
									$("#appFunctionals").hide();
									$(".addNewScreenPop").hide();
									$(".editAppFeaturePop").hide();
									$(".objsForObjGrpList").hide();
									$(".editObjectDetailsPop").hide();
									$(".conditionGrp").hide();
									$(".IdentifierTabGrp").hide();
									$(".ReplacementTabGrp").hide();
									$(".addConditionGrpPop").hide();
									$(".addIdentifierTypesPop").hide();
									$(".addReplacementStringsPopUps").hide();
									$(".editConditionGrpPop").hide();
									$(".editidentifierTyPop").hide();
									$(".editReplaceStringsTyPop").hide();
									$(".conditionForConGrpList").hide();
									$(".addNewCondition").hide();
									$(".editConditionDetailsPop").hide();
									 $("#testImport").hide();
									 $("#addImport").hide();
									 $(".paramGrp").hide();
									 $(".addParamGrpPop").hide();
				    				 $(".editParamGrpPop").hide(); 
				    				 $(".paramsForParamGrpList").hide();
				    				 $(".addParameterPop").hide();
				    				 $(".editParameterPop").hide();
				    				 $(".importExcelPopUp").hide();
				    				 $(".importExcelInAppPopUp").hide();
							});
							
					$('#ApplicationDetails > div:First-child .tstDefinition').bind("click",function(e){
						e.preventDefault();
						$("#application_status").hide();
						$('#testSuitesCase').hide();
	       				$("#testRunsRusults").hide();
	       				$('#addApplication_popup').hide();
	        			$('#application_status').hide();
	       				$('#reports').hide();
	        			$("#executionMain").hide();
	        			$('#object').hide();
	        			$('#parameterGrp').hide();
	        			$('#suite').hide();
	        			$("#trends").hide();
			//	        $("#objectGrpDetail").hide();
			//	        $("#parameterGrpDetail").hide();
	        			$("#TestDataDefinition").show();
	        			$(".addFeaturePop").hide();
	        			$(".featuresForFunction").hide();
	        			$("#appFunctionals").hide();
	        			$(".addNewScreenPop").hide();
	        			$(".editAppFeaturePop").hide();
	        			$(".objsForObjGrpList").hide();
	        			$(".editObjectDetailsPop").hide();
	        			$(".conditionGrp").hide();
	        			$(".IdentifierTabGrp").hide();
	        			$(".ReplacementTabGrp").hide();
	        			$(".addConditionGrpPop").hide();
	        			$(".addIdentifierTypesPop").hide();
	        			$(".addReplacementStringsPopUps").hide();
	        			$(".editConditionGrpPop").hide();
	        			$(".editidentifierTyPop").hide();
	        			$(".editReplaceStringsTyPop").hide();
	        			$(".conditionForConGrpList").hide();
	        			$(".addNewCondition").hide();
	        			$(".editConditionDetailsPop").hide();
	        			 $("#testImport").hide();
	        			 $("#addImport").hide();
	        			 $(".paramGrp").hide();
	        			 $(".addParamGrpPop").hide();
	    				 $(".editParamGrpPop").hide(); 
	    				 $(".paramsForParamGrpList").hide();
	    				 $(".addParameterPop").hide();
	    				 $(".editParameterPop").hide();
	    				 $(".objGrpsForScreen").hide();
	    				$("#addTestPop").hide();
	    				$("#editTestPop").hide();
	    				$("#application").hide();
	    				$(".importExcelPopUp").hide();
	    				 $(".importExcelInAppPopUp").hide();
	    	});	
							
				$('#ApplicationDetails > div:First-child .testFeature').bind("click",function(e){
		e.preventDefault();
		 $("#appFunctionals").show();
			$('#testSuitesCase').hide();
	        $("#testRunsRusults").hide();
	        $('#addApplication_popup').hide();
	        $('#application_status').hide();
	        $('#reports').hide();
	        $("#executionMain").hide();
	        $("#TestDataDefinition").hide();
	        $('#object').hide();
	        $('#parameterGrp').hide();
	        $('#suite').hide();
	        $("#trends").hide();
	        $("#objectGrpDetail").hide();
	        $("#parameterGrpDetail").hide();
	        $("#TestDataDefinition").hide();
	        $(".featuresForFunction").hide();
	        $("#TestApplicationFeature").hide();
	    	$(".addObjectGrpPop").hide();
	    	$(".addTestSuite").hide();
	    	$(".testSuitesForPlan").hide();
	    	$(".editTestPlan").hide();
	    	$(".editTestSuite").hide();
	    	$(".addObjectPop").hide();
	    	$(".addActionPop").hide();
	    	$(".addObjectTypePop").hide();
	    	$(".objGrpsForScreen").hide();
	    	$(".editObjGrp").hide();
	    	$(".editScreenDetails").hide();
	    	$(".addParamGrpDetailsPop").hide();
	    	$(".addFeaturePop").hide();
	    	$(".addNewScreenPop").hide();
	    	$(".editAppFeaturePop").hide();
	    	$(".objsForObjGrpList").hide();
	    	$(".editObjectDetailsPop").hide();
	    	$(".conditionGrp").hide();
	    	$(".IdentifierTabGrp").hide();
	    	$(".ReplacementTabGrp").hide();
	    	$(".addConditionGrpPop").hide();
	    	$(".addIdentifierTypesPop").hide();
	    	$(".addReplacementStringsPopUps").hide();
	    	$(".editConditionGrpPop").hide();
	    	$(".editidentifierTyPop").hide();
	    	$(".editReplaceStringsTyPop").hide();
	    	$(".conditionForConGrpList").hide();
	    	$(".addNewCondition").hide();
	    	$(".editConditionDetailsPop").hide();
	    	 $("#testImport").hide();
	    	 $("#addImport").hide();
	    	 $("#application").hide();
	    	 $(".paramGrp").hide();
	    	 $(".addParamGrpPop").hide();
			 $(".editParamGrpPop").hide(); 
			 $(".paramsForParamGrpList").hide();
			 $(".addParameterPop").hide();
			 $(".editParameterPop").hide();
				$("#addTestPop").hide();
				$("#editTestPop").hide();
				$(".importExcelPopUp").hide();
				 $(".importExcelInAppPopUp").hide();
	    });
				$('#ApplicationDetails > div:First-child .testImport').bind("click",function(e){
					e.preventDefault();
					$("#application_status").hide();
					$("#application").hide();
					$("#testSuitesCase").hide();
					$("#executionMain").hide();
					$("#TestDataDefinition").hide();
					$(".EditTestDefinition").hide();
					$('.editTestParamData').hide();
					$("#object").hide();
					$("#reports").hide();
					$("#addTestPop").hide();
					$("#editTestPop").hide();
					$("#addFunctional").hide();
					$("#selected_feature").hide();
					$("#ApplicationFunction").hide();
					 $("#TestApplicationFeature").hide();
						$(".addObjectGrpPop").hide();
						$(".addTestSuite").hide();
						$(".testSuitesForPlan").hide();
						$(".editTestPlan").hide();
						$(".editTestSuite").hide();
						$(".addObjectPop").hide();
						$(".addActionPop").hide();
						$(".addObjectTypePop").hide();
						$(".objGrpsForScreen").hide();
						$(".editObjGrp").hide();
						$(".editScreenDetails").hide();
						$(".addParamGrpDetailsPop").hide();
						$(".addFeaturePop").hide();
						$(".featuresForFunction").hide();
						$("#appFunctionals").hide();
						$(".addNewScreenPop").hide();
						$(".editAppFeaturePop").hide();
						$(".objsForObjGrpList").hide();
						$(".editObjectDetailsPop").hide();
						$(".conditionGrp").hide();
						$(".IdentifierTabGrp").hide();
						$(".ReplacementTabGrp").hide();
						$(".addConditionGrpPop").hide();
						$(".editConditionGrpPop").hide();
						$(".editidentifierTyPop").hide();
						$(".editReplaceStringsTyPop").hide();
						$(".conditionForConGrpList").hide();
						$(".addNewCondition").hide();
						$(".editConditionDetailsPop").hide();
						 $("#testImport").show();
						 $("#addImport").show();
						 $("#application").hide();
						 $(".paramGrp").hide();
						 $(".addParamGrpPop").hide();
	    				 $(".editParamGrpPop").hide(); 
	    				 $(".paramsForParamGrpList").hide();
	    				 $(".addParameterPop").hide();
	    				 $(".editParameterPop").hide();
	    				 $(".importExcelPopUp").hide();
	    				 $(".importExcelInAppPopUp").hide();
				});
					
					$('#ApplicationDetails > div:First-child .reportsTab')
							.bind("click", function(e) {
								e.preventDefault();
								$("#application_status").hide();
								$("#application").hide();
								$("#testSuitesCase").hide();
								$("#executionMain").hide();
								$("#TestDataDefinition").hide();
								$(".EditTestDefinition").hide();
								$('.editTestParamData').hide();
								$("#object").hide();
								$("#reports").show();
								$("#addTestPop").hide();
								$("#editTestPop").hide();
								$("#addFunctional").hide();
								$("#selected_feature").hide();
								$("#ApplicationFunction").hide();
								 $("#TestApplicationFeature").hide();
									$(".addObjectGrpPop").hide();
									$(".addTestSuite").hide();
									$(".testSuitesForPlan").hide();
									$(".editTestPlan").hide();
									$(".editTestSuite").hide();
									$(".addObjectPop").hide();
									$(".addActionPop").hide();
									$(".addObjectTypePop").hide();
									$(".objGrpsForScreen").hide();
									$(".editObjGrp").hide();
									$(".editScreenDetails").hide();
									$(".addParamGrpDetailsPop").hide();
									$(".addFeaturePop").hide();
									$(".featuresForFunction").hide();
									$("#appFunctionals").hide();
									$(".addNewScreenPop").hide();
									$(".editAppFeaturePop").hide();
									$(".objsForObjGrpList").hide();
									$(".editObjectDetailsPop").hide();
									$(".conditionGrp").hide();
									$(".IdentifierTabGrp").hide();
									$(".ReplacementTabGrp").hide();
									$(".addConditionGrpPop").hide();
									$(".addIdentifierTypesPop").hide();
									$(".addReplacementStringsPopUps").hide();
									$(".editConditionGrpPop").hide();
									$(".editidentifierTyPop").hide();
									$(".editReplaceStringsTyPop").hide();
									$(".conditionForConGrpList").hide();
									$(".addNewCondition").hide();
									$(".editConditionDetailsPop").hide();
									 $("#testImport").hide();
									 $("#addImport").hide();
									 $(".paramGrp").hide();
									 $(".addParamGrpPop").hide();
				    				 $(".editParamGrpPop").hide(); 
				    				 $(".paramsForParamGrpList").hide();
				    				 $(".addParameterPop").hide();
				    				 $(".editParameterPop").hide();
				    				 $(".importExcelPopUp").hide();
				    				 $(".importExcelInAppPopUp").hide();
							});
					$("#add_application").bind("click", function(e){
						$('#overLay').show();
						$('.addApplication').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$(".addApplication").show();
							});
						});
					
					$("#addApplicationDataBtn").bind("click", function(e) {
						$('#overLay').hide();
						$('.addApplication').hide();
						});
					
//					$(document).on("click","#functionBox",function(e){
//						//$("#TestDataDefinition").hide();
//						$("#selected_feature").show();
//
//						});
					$(document).on("click","#functionBox",function(e){
						$("#appFunctionals").hide();
						$(".featuresForFunction").show();
					});
					
					/*---Navigator ends--*/
					$("#select_all_cases").bind(
							"click",
							function() {
								$('#cases li').find('input:checkbox').attr(
										'checked', true);
							});

					//$("#testSuites").show();
					//$(".testScenariosHide").show();
					$("#selected_suite").hide();
					$(document).on("click",".suites",function() {
					
										//alert("inside main.js");
										//e.preventDefault();
										$("#selected_suite").show();
									//	$("#testSuites").hide();
										//$(".testScenariosHide").hide();
										$("#suite_details").show();
										$("#case_list").hide();
										var selectedSuite = $(this).find('h3')
												.text();
										var suiteClicked = $("<span class='suiteSelected'>"
												+ "  /  "
												+ selectedSuite
												+ "</span>");
										$("#dashboard").find('h2').append(
												suiteClicked);

										$(document)
												.on(
														"click",
														"#ApplicationDetails > div:First-child a",
														function() {
															$(
																	"#dashboard span.suiteSelected,#dashboard span.caseSelected")
																	.remove();
														});
									});

					/*------ screen table ----*/
					$(".testScreen").show();
					$(".addObjectScreen").hide();
					$(".addObjectHref")
							.bind(
									"click",
									function(e) {
										e.preventDefault();
										$(".addObjectScreen").show();
										$(".testScreen").hide();
										$(".addCaseDetails").show();

										var selectedSuite = $(this).find('h3')
												.text();
										var suiteClicked = $("<span class='suiteSelected'>"
												+ "  /  "
												+ selectedSuite
												+ "</span>");
										$("#dashboard").find('h2').append(
												suiteClicked);

										$(document)
												.on(
														"click",
														"#ApplicationDetails > div:First-child a",
														function() {
															$(
																	"#dashboard span.suiteSelected,#dashboard span.caseSelected")
																	.remove();
														});
									});

					$(document).on(
							"click",
							"#dashboard",
							function() {

								$(this).find("span").last().prevAll().unbind(
										"click");
								$(this).find("span").last().prevAll().bind(
										"click", function() {

											$(this).nextAll().remove();
										});
							});

					$("#case_history").hide();
					$(document).on("click",".case",function() {
										$("#case_list").show();
										$("#suite_details").hide();
										$("#case_details").show();
										var selectedCase = $(this).text();
										var caseClicked = $("<span class='caseSelected'>"
												+ "  /  "
												+ selectedCase
												+ "</span>");
										$("#dashboard").find('h2').append(
												caseClicked);
									});

					$("#history").bind(
							"click",
							function(e) {
								e.preventDefault();
								$("#case_details").hide();
								$("#case_history").show();
								$(".addTestButton").hide();
								$("#case_list ul li").find('a').removeClass(
										'activeSuite');
								$(this).addClass('activeSuite');
							});
					/*----[Case Result]----*/
					$("#case_history > table td:contains(Passed)").css("color",
							"green");
					$("#case_history > table td:contains(Failed)").css("color",
							"red");
					$("#case_history > table td:contains(Blocked)").css(
							"color", "grey");
					$("#case_history > table td:contains(On Hold)").css(
							"color", "#e4c332");
					/*----[Function and Feature Popup]----*/
					var functionName, featureName;
					$(document).on("click", "#functions ul li", function(e) {
						e.preventDefault();
						$("#functions ul li").removeClass('current');
						$(this).addClass('current');
						functionName = $(this).text();
					});
					$(document).on("click", "#features ul li", function(e) {
						e.preventDefault();
						$("#features ul li").removeClass('current');
						$(this).addClass('current');
						featureName = $(this).text();
					});
					$("#done_button").bind(
							"click",
							function() {
								$("#suite_functionality").text(functionName)
										.append("<span>Functionality</span>");
								$("#suite_feature").text(featureName).append(
										"<span>Feature</span>");
							});
					/*---[Warning Application]-------*/
					$(".warning").css({
						'background-color' : "#fff0f0",
						border : '1px solid #fc8383'
					});

					$("#details").bind(
							"click",
							function(e) {
								e.preventDefault();
								$("#case_details").show();
								$("#case_history").hide();
								$(".addTestButton").show();
								$("#case_list ul li").find('a').removeClass(
										'activeSuite');
								$(this).addClass('activeSuite');
							});

					$("#add_function").bind("click", function(e) {
						$("#show_addFunction").slideDown("slow");
					});

					$("#save_function")
							.bind(
									"click",
									function() {
										var func_name = $("#show_addFunction")
												.find("input[type='text']")
												.val().trim();
										var function_description = $(
												"#show_addFunction").find(
												"textarea").val().trim();

										if (func_name.length > 0
												&& function_description.length > 0) {
											$("#show_addFunction").slideUp(
													"slow");
											$("#features ul").find("li")
													.removeClass("current");
											$("#functions ul").prepend(
													"<li>" + func_name
															+ "</li>")
													.addClass("current");
											$("#features ul").find("li").html(
													"");
											$("#features ul").find("li")
													.removeClass("current");
											$("#show_addFeature").slideDown(
													"slow");
										} else if (func_name == null
												|| func_name == "") {
											$("#function_name_error").css({
												"color" : "red",
												"display" : "inline-block"
											}).show();
											$("#function_desc_error").hide();
											return false;
										} else if (function_description == null
												|| function_description == "") {
											$("#function_desc_error").css({
												"color" : "red",
												"display" : "inline-block"
											}).show();
											$("#function_name_error").hide();
											return false;
										}
										$("#show_addFunction").find(
												"input[type='text']").val('');
										$("#show_addFunction").find("textarea")
												.val('');
									});

					$("#add_features").bind("click", function() {
						$("#show_addFeature").slideDown("slow");
					});

					$("#submit_feature").bind(
							"click",
							function() {
								var new_feature = $("#show_addFeature").find(
										"input[type='text']").val().trim();
								var new_feature_desc = $("#show_addFeature")
										.find("textarea").val().trim();

								if (new_feature.length > 0
										&& new_feature_desc.length > 0) {
									$("#features ul").prepend(
											"<li>" + new_feature + "</li>")
											.addClass("current");
									$("#show_addFeature").slideUp("slow");
								} else if (new_feature == null
										|| new_feature == "") {
									$("#feature_name_error").css({
										"color" : "red",
										"display" : "inline-block"
									}).show();
									$("#feature_desc_error").hide();
									return false;
								} else if (new_feature_desc == null
										|| new_feature_desc == "") {
									$("#feature_desc_error").css({
										"color" : "red",
										"display" : "inline-block"
									}).show();
									$("#feature_name_error").hide();
									return false;
								}
							});

					$("#add_more_features")
							.bind(
									"click",
									function() {
										var more_feature = $("#show_addFeature")
												.find("input[type='text']")
												.val().trim();
										var more_feature_description = $(
												"#show_addFeature").find(
												"textarea").val().trim();

										if (more_feature.length > 0
												&& more_feature_description.length > 0) {
											$("#features ul").prepend(
													"<li>" + more_feature
															+ "</li>")
													.addClass("current");
										} else if (more_feature == null
												|| more_feature == "") {
											$("#feature_name_error").css({
												"color" : "red",
												"display" : "inline-block"
											}).show();
											$("#feature_desc_error").hide();
											return false;
										} else if (more_feature_description == null
												|| more_feature_description == "") {
											$("#feature_desc_error").css({
												"color" : "red",
												"display" : "inline-block"
											}).show();
											$("#feature_name_error").hide();
											return false;
										}
										$("#show_addFeature").find(
												"input[type='text']").val('');
										$("#show_addFeature").find("textarea")
												.val('');
									});

					$(".cancel_function").bind("click", function(e) {
						e.preventDefault();
						$("#show_addFunction").slideUp("slow");
						$("#show_addFeature").slideUp("slow");
					});

					$("#stacked_bar_graph").hide();
					$("#graph_view").bind("click", function(e) {
						e.preventDefault();
						$("#testCase_details_table").find('table').hide();
						$("#stacked_bar_graph").show();
						$("#testCase_header > a").removeClass('activeForm');
						$(this).addClass('activeForm');
					});

					$("#table_view").bind("click", function(e) {
						e.preventDefault();
						$("#testCase_details_table").find('table').show();
						$("#stacked_bar_graph").hide();
						$("#testCase_header > a").removeClass('activeForm');
						$(this).addClass('activeForm');
					});

					/*------Scrolling of Application list-------*/

					$('#application_lists').slimScroll({
						height : '284px',
						width : '100%',
						position : 'right',
						color : '#dcdcdc',
						size : '4px'
					});
					$(
							'#latest_test_runs dl,#upcoming_function dl,#test_case_details>div > ul')
							.slimScroll({
								height : '259px',
								width : '100%',
								position : 'right',
								color : '#dcdcdc',
								size : '4px'
							});

					/*------Test Runs And Results-------*/
					$("#active").show();
					$("#complete").hide();
					$("#suite").hide();
					$("#activeTest").click(
							function(e) {
								e.preventDefault();
								$("#complete").hide();
								$("#active").show();
								$("#testRunsRusults ul li > a").removeClass(
										'activeRun');
								$(this).addClass('activeRun');
							});
					$("#completeTest").click(
							function(e) {
								e.preventDefault();
								$("#active").hide();
								$("#complete").show();
								$("#testRunsRusults ul li > a").removeClass(
										'activeRun');
								e.preventDefault();
								$(this).addClass('activeRun');
							});
					$("#active div").click(function(e) {
						$("#testRunsRusults").hide();
						$("#suite").show();
						var selectedSuite1 = $(this).find('h3').text();
						$("#suite h3").text(selectedSuite1);
					});
					$("#trends").hide();
					$("#suitesStatus").click(function(e) {
						e.preventDefault();
						$("#status").show();
						$("#trends").hide();
						$("#suite ul li > a").removeClass('activeTest_suite');
						$(this).addClass('activeTest_suite');
					});
					$("#suitesTrends").click(function(e) {
						e.preventDefault();
						$("#status").hide();
						$("#trends").show();
						$("#suite ul li > a").removeClass('activeTest_suite');
						$(this).addClass('activeTest_suite');
					});
					$("#editPage_popup").bind(
							"click",
							function(e) {
								$('#overLay').show();
								$('#editPage').css({
									"display" : "block"
								});

								var spaceIndex = $("#suite_functionality")
										.text().indexOf("F");
								var functionString = $("#suite_functionality")
										.text().substring(0, spaceIndex);
								$("#functions ul li").removeClass("current");
								$("#functions ul li").each(function() {
									if ($(this).text() == functionString) {
										$(this).addClass("current");
									}
								});
								var spaceIndex1 = $("#suite_feature").text()
										.indexOf("F");
								var featuresString = $("#suite_feature").text()
										.substring(0, spaceIndex1);
								$("#features ul li").removeClass("current");
								$("#features ul li").each(function() {
									if ($(this).text() == featuresString) {
										$(this).addClass("current");
									}
								});
							});
					$('#editPage').find(".close").bind("click", function(e) {
						$('#overLay').hide();
						$('#editPage').hide();
					});
					$("#done_button").bind("click", function(e) {
						$('#overLay').hide();
						$('#editPage').hide();
					});
					$("#add_testCases").find('.addTestButton').bind("click",
							function(e) {
								$('#overLay').show();
								$('#addCase').css({
									"display" : "block"
								});
							});

					/*------------[popup test Plans & suites]---------*/

					$("#addTestPlanBtn").bind("click", function(e) {
						$('#overLay').show();
						$('#addTestPlan').css({
							"display" : "block"
						});
					});

					$("#addTestPlanPopUpBtn").bind("click", function(e) {
						$('#overLay').show();
						//$('#addTestPop').css({"display" : "block"});
						$('#addTestPop').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$("#addTestPop").css({"display" : "block"});
						});
					});
					// ~~~~~~~~~~~ Changes from Ramya Starts here ~~~~~~~~~~~~~~~~~~ -->	
					$("#addObjectGrpBtn").bind("click", function(e) {
						$('#overLay').show();
						//$(".addObjectGrpPop").show();
						$('.addObjectGrpPop').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.addObjectGrpPop').show();
							
						});
					});	
					$(".addParamGrpDetailsBtn").bind("click", function(e) {
						$('#overLay').show();
						$(".addParamGrpDetailsPop").show();
					});
					$("#addObjectBtn").bind("click", function(e) {
						$('#overLay').show();
						//$(".addObjectPop").show();
						$('.addObjectPop').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.addObjectPop').show();
							
						});
					});	
					$(".addActionBtn").bind("click", function(e) {
						$('#overLay').show();
						$(".addActionPop").show();
					});	
					$("#addObjectTypeBtn").bind("click", function(e) {
						$('#overLay').show();
						//$(".addObjectTypePop").show();
						$('.addObjectTypePop').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.addObjectTypePop').show();
							
						});
					});
					$(document).on("click","#editThisTestPlan",function(e){
								e.stopPropagation();
								$("#overlay").show();
								$(".editTestPlan").show();
							});
					$(document).on("click","#editThisFeature",function(e){
						e.stopPropagation();
						$("#overlay").show();
						$(".editAppFeaturePop").show();
					});
					
					$(document).on("click","#objectsListForObjGrp",function(e){
						$(".objGrpsForScreen").hide();
						$(".objsForObjGrpList").show();
					});
					
					$(document).on("click","#editThisObject",function(e){
						//$('#overLay').show();
						e.stopPropagation();
						$(".editObjectDetailsPop").show();
					});
					$(document).on("click","#testSuitesList",function(e){
						$("#testRunsRusults").hide();
						 $("#application").hide();
						$(".testSuitesForPlan").show();
						$(".testSuitesTable").show();
					});
						$("#addFunctionBtn").bind("click", function(e){
						$('#overLay').show();
						//$('#addFunctional').css({"display":"block"});
						$('#addFunctional').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('#addFunctional').show();
						});
					});
					$(document).on("click","#objGrpsList",function(e){
							$("#object").hide();
							$(".objGrpsForScreen").show();
							$(".objGrpsTable").show();
						});
					$(document).on("click","#editThisObjGrp",function(e){
								e.stopPropagation();
								$("#overlay").show();
								$(".editObjGrp").show();
						});
					$(document).on("click","#editThisTestSuite",function(e){
							$("#overlay").show();
							$(".editTestSuite").show();
						});
					$(document).on("click","#editThisScreen",function(e){
							$("#overlay").show();
							$(".editScreenDetails").show();
						});
					$("#addAppFeatureBtn").bind("click", function(e) {
						$('#overLay').show();
						//$(".addFeaturePop").show();
						$('.addFeaturePop').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.addFeaturePop').show();
						});
					});	
					
					$("#addScreenBtn").bind("click", function(e) {
						$('#overLay').show();
						//$(".addNewScreenPop").show();
						$('.addNewScreenPop').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.addNewScreenPop').show();
							
						});
					});	
					
					$("#addConditionGrpBtn").bind("click", function(e) {
						$('#overLay').show();
						//$(".addConditionGrpPop").show();
						$('.addConditionGrpPop').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.addConditionGrpPop').show();
							
						});
					});	
					
					$(document).on("click","#editThisConditionGrp",function(e){
							$("#overlay").show();
							$(".editConditionGrpPop").show();
						});
					$(document).on("click","#editThisCondition",function(e){
							$("#overlay").show();
							$(".editConditionDetailsPop").show();
						});
					$(document).on("click","#conditionGrpList",function(e){
						$(".conditionGrp").hide();
						$(".conditionForConGrpList").show();
					});
					
					
					$("#addConditionBtn").bind("click", function(e) {
						$('#overLay').show();
						//$(".addNewCondition").show();
						$('.addNewCondition').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.addNewCondition').show();
							
						});
					});	
					
					/** Import Excel*/
					
					$("#addEntry").bind("click",function(e) {
						  $("#innerDiv").append('<span> <p><input type="checkbox" name="myCheckBox"/> Please specify a File Name, or a set of Files:<input type="file" name="datafile" size="20"/></p></span>');
                	});
					
					   $("#addRemove").click(function(e){
					   		var myParagraphs = $('#innerDiv span').children();
					   		var myFlag = false;
							for(var i =0 ; i < myParagraphs.length ; i++){
								var myInputElement = $(myParagraphs[i]).children();
								if(myInputElement[0].type == "checkbox" && myInputElement[0].checked == true){
									$(myInputElement[0]).parent().parent().remove();
									myFlag = true;									
								}
							}
							if(myFlag == false){
								alert("Please select a record to remove");
							}
  						});
					
					$("#addImportBtn").bind("click", function(e){
					$('#overLay').show();
						$('#addImport').css({"display":"block"});
					});
					/** Import Excel Ends*/
					
					
					$("#addParamGrpBtn").bind("click", function(e) {
						$('#overLay').show();
						//$(".addParamGrpPop").show();
						$('.addParamGrpPop').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.addParamGrpPop').show();
							
						});
					});	
					
					$(document).on("click","#paramDataList",function(e){
						$("#testRunsRusults").hide();
						 $(".paramGrp").hide();
						$(".paramsForParamGrpList").show();
					});
					
					$(document).on("click","#editThisParamGrp",function(e){
						e.stopPropagation();
						$("#overlay").show();
						$(".editParamGrpPop").show();
					});
					
					$("#addParameterBtn").bind("click", function(e) {
						$('#overLay').show();
						//$(".addParameterPop").show();
						$('.addParameterPop').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.addParameterPop').show();
							
						});
					});	
					$(document).on("click","#editThisParam",function(e){
						e.stopPropagation();
						$("#overlay").show();
						$(".editParameterPop").show();
					});
					
					$(document).on("click","#addTestPlanButton",function(e){
						$(".overLayScreen").hide();
						$("#addTestPop").hide();
					});
					$(document).on("click","#addTestSuiteButton",function(e){
						$(".overLayScreen").hide();
						$(".addTestSuite").hide();
					});
					$(document).on("click","#addAppFunctionalityButton",function(e){
						$(".overLayScreen").hide();
						$("#addFunctional").hide();
					});
					$(document).on("click","#addAppFeatureButton",function(e){
						$(".overLayScreen").hide();
						$(".addFeaturePop").hide();
					});
					$(document).on("click","#addAppScreenButton",function(e){
						$(".overLayScreen").hide();
						$(".addNewScreenPop").hide();
					});
					$(document).on("click","#addObjGroupButton",function(e){
						$(".overLayScreen").hide();
						$(".addObjectGrpPop").hide();
					});
					$(document).on("click","#addObjTypeButton",function(e){
						$(".overLayScreen").hide();
						$(".addObjectTypePop").hide();
					});
					$(document).on("click","#addObjectButton",function(e){
						$(".overLayScreen").hide();
						$(".addObjectPop").hide();
					});
					$(document).on("click","#addConditionGrpButton",function(e){
						$(".overLayScreen").hide();
						$(".addConditionGrpPop").hide();
					});
					$(document).on("click","#addConditionButton",function(e){
						$(".overLayScreen").hide();
						$(".addNewCondition").hide();
					});
					$(document).on("click","#addParamGroupButton",function(e){
						$(".overLayScreen").hide();
						$(".addParamGrpPop").hide();
					});
					$(document).on("click","#addParamButton",function(e){
						$(".overLayScreen").hide();
						$(".addParameterPop").hide();
					});
					
					$(".importExcelBtn").bind("click", function(e) {
						$('#overLay').show();
					//	$('.importExcelPopUp').css({"display" : "block"});
						$('.importExcelPopUp').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.importExcelPopUp').show();
							
						});
						
						$('#uploadExcelBtn').val("Upload");
					
					});
					
					$(document).on("click","#uploadExcelBtn",function(e){
						$(".overLayScreen").hide();
						$(".importExcelPopUp").hide();
					});
					//----
					$(document).on("click","#uploadExcel",function(e){
						$(".overLayScreen").show();
						//$(".importExcelInAppPopUp").show();
						$('.importExcelInAppPopUp').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.importExcelInAppPopUp').show();
							
							$('#importExcelInAppPopUpBtn').val("Upload");
							
						});
					});
					$(document).on("click","#importExcelInAppPopUpBtn",function(e){
						$(".overLayScreen").hide();
						$(".importExcelInAppPopUp").hide();
					});
					// ~~~~~~~~~~~ Changes from Ramya Ends here ~~~~~~~~~~~~~~~~~~ -->	
					$('#edit').bind('click', function(e) {
						e.stopPropagation();
						e.cancelBubble = true;
						$(this).parent().unbind('click');
						$('#addTestPlanBtn').trigger('click');
					});

					$('#edit1').bind('click', function(e) {
						// e.preventDefault();
						e.stopPropagation();
						e.cancelBubble = true;
						//alert("dsdsdsd");
						$(this).parent().unbind('click');
						$('#addTestPlanBtn').trigger('click');
					});
					$("#addTestRunBtn").bind("click", function(e) {
						$('#overLay').show();
						$('#addTestRun').css({
							"display" : "block"
						});
					});

					$("#addTestSuiteBtn").bind("click", function(e) {
						$('#overLay').show();
					//	$('.addTestSuite').css({"display" : "block"});
						$('.addTestSuite').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('textarea').val('');
							$('.addTestSuite').show();
							
						});
					});

					// $("#addTestCasesbtn").bind("click", function(e){
					// $('#overLay').show();
					// $('#addCase1').css({"display":"block"});
					// });

					/*------------[popup test suites and runs end]---------*/

					$('#close_dataset').bind("click", function() {
						$('#addsteps_dataset').hide();
					});
					$("#addTestSuitesbtn").bind("click", function(e) {
						$('#overLay').show();
						$('#addSuitesPop').css({
							"display" : "block"
						});
					});
					$("#addTestCasebtn").bind("click", function(e) {
						$('#overLay').show();
						$('#addSuitesPop').css({
							"display" : "block"
						});
					});
					
					$(".addtest").bind("click", function(e) {
						$('#overLay').show();
						$('.addCasetest').css({
							"display" : "block"
						});
						$(".addTestStepsHide").hide();
						$("#addtest_JavaAPIsteps_table").hide();
						$("#addtest_Junitsteps_table").hide();
						//$('#runnerID').val(1002);
						//$('#runnerID').change();
					});

					$(".addStepName").bind("click", function(e) {
						$('#overLay').show();
						$('.addTestStep').css({
							"display" : "block"
						});
					});

					$(".addTestSce").bind("click", function(e) {
						$('#overLay').show();
						$('#addSuitesPop').css({
							"display" : "block"
						});
					});

					$(".addObjectSce").bind("click", function(e) {
						$('#overLay').show();
						$('.AddScreen').css({
							"display" : "block"
						});
					});

					$(".addObjectSce").bind("click", function(e) {
						$('#overLay').show();
						$('.EditScreen').css({
							"display" : "block"
						});
					});

					// $(".addTestPlansce").bind("click", function(e){
					// $('#overLay').show();
					// $('#addTestPop').css({"display":"block"});
					// });
					//	

					$(".addTestrunplanbtn").bind("click", function(e) {
						$('#overLay').show();
						$(".addTestSuite").hide();
						$('.addTestrunPlan').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('select').val('');
						});
						
						$('.addTestrunPlan').css({
							"display" : "block"
						});
					});
					$("#addTestExecutionSuiteBtn").bind("click", function(e) {
						$('#overLay').hide();
						$('.addTestrunPlan').hide();
					});
					
					$(document).on("click","#editTestrunplanbtn",function(e){
						$("#overlay").show();
						$(".editTestrunPlan").show();
					});
					

					$("#editTestExecutionSuiteBtn").bind("click", function(e) {
						$('#overLay').hide();
						$('#checkMultiLaneEdit').hide();
						$('.editTestrunPlan').hide();
					});

					$("#insertTestDef").bind("click", function(e) {
						$('#overLay').hide();
						$('.AddTestDefinition').hide();
					});
					
					$("#editTestDataDef").bind("click", function(e) {
						$('#overLay').hide();
						$('.EditTestDefinition').hide();
					});
					
					$("#insertTestParamData").bind("click", function(e) {
						$('#overLay').hide();
						$('.AddTestParamData').hide();
					});
					
					$(document).on("click","#editParamData",function(e){
						$("#overlay").show();
						$(".editTestParamData").show();
					});
					
					$("#saveTestParamData").bind("click", function(e) {
						$('#overLay').hide();
						$('.editTestParamData').hide();
					});
					
					$(document).on("click","#dashBoardId",function(e){
						location.reload();
					});

					$(".addApplicationBut").bind("click", function(e) {
						$('#overLay').show();
						$('.addApplication').css({
							"display" : "block"
						});
					});
					
					$(document).on("click","#settings",function(e){	
							//alert("settings");
							window.location = "adminlogin.html";
					});
					
					$(document).on("click","#logout",function(e){
						//alert("logout");
						localStorage.clear();
						//window.location = "http://10.1.0.64:8085/UserManagement/login.html";
						window.location = "http://localhost:9082/UserManagement/login.html";
					});

//					$(".editApplication").bind("click", function(e) {
//						$('#overLay').show();
//						$('.editApplicationPop').css({
//							"display" : "block"
//						});
//					});
					$(document).on("click","#editThisApplication",function(e){
						e.stopPropagation();
						$("#overlay").show();
						$(".editApplicationPop").show();
						});
					

					/* Edit TestScenarios PopUP */
					
					$(document).on("click","#editThisTestScenarios",function(e){
						e.stopPropagation();
						$("#overlay").show();
						$(".editScenariosPop").show();
						});
					
					$(document).on("click",".addStepName",function(e){
						e.stopPropagation();
						$("#overlay").show();
						$(".addTestStepPops").show();
						
						});
					
					
					

					$(document).on("click","#editThisFunction",function(e){
						e.stopPropagation();
						$("#overlay").show();
						$(".editFunctionPop").show();
						});
					

					$(".addObjectButton").bind("click", function(e) {
						$('#overLay').show();
						$('.addObjectPop').css({
							"display" : "block"
						});
					});

					/**
					 * $(".addObjectHref").bind("click", function(e){
					 * $('#overLay').show();
					 * $('.addObjectScreen').css({"display":"block"}); });
					 */

					$('.close').bind("click", function(e) {
						e.preventDefault();
						$('#overLay').hide();
						$('#addCase').hide();
						$('.addCasetest').hide();
						$('.addTestStep').hide();
						$('.addIdentifierTypesPop').hide();
						$('.addReplacementStringsPopUps').hide();
						$('.editApplicationPop').hide();
						$(".editFunctionPop").hide();
						$('.addApplication').hide();
						$('.addObjectPop').hide();
						$('.addTestStepPops').hide();
						$('.addObjectScreen').hide();
						$('#addTestRun').hide();
						$('#addTestPlan').hide();
						$('.AddTestPop').hide();
						$('#editPage').hide();
						$('#addSuitesPop').hide();
						$('#addTestPop').hide();
						$('#editTestPop').hide();
						$('#addApplication_popup').hide();
						$('.addTestrunPlan').hide();
						$('.AddTestDefinition').hide();
						$('.EditTestDefinition').hide();
						$('.editTestParamData').hide();
						$('.AddTestParamData').hide();
						$('.editTestrunPlan').hide();
						$('.editScenariosPop').hide();
						$('.editCaesPop').hide();
						$('.editStpsPop').hide();
						$('.EditScreen').hide();
						$('.editScreen').hide();
						$('#addFunctional').hide();
						$("#selected_feature").hide();
						$("#ApplicationFunction").hide();
						$(".editApplicationPop").hide();
						$(".addObjectGrpPop").hide();
						$(".editTestPlan").hide();
						$(".editTestSuite").hide();
						$(".addObjectPop").hide();
						$(".addActionPop").hide();
						$(".addObjectTypePop").hide();
						$(".editObjGrp").hide();
						$(".editScreenDetails").hide();
						$(".addParamGrpDetailsPop").hide();
						$(".addFeaturePop").hide();
						$(".addNewScreenPop").hide();
						$(".editAppFeaturePop").hide();
						$(".editObjectDetailsPop").hide();
						$(".addConditionGrpPop").hide();
						$(".editConditionGrpPop").hide();
						$(".editidentifierTyPop").hide();
						$(".editReplaceStringsTyPop").hide();
						$(".addNewCondition").hide();
						$(".editConditionDetailsPop").hide();
						 $(".addParamGrpPop").hide();
	    				 $(".editParamGrpPop").hide(); 
	    				 $(".addParameterPop").hide();
	    				 $(".editParameterPop").hide();
	    				 $('#checkMultiLaneEdit').hide();
	    				 $(".importExcelPopUp").hide();
	    				 $(".importExcelInAppPopUp").hide();
					});
					$("#functions ul, #features ul").slimScroll({
						height : '540px',
						position : 'right',
						color : '#dcdcdc',
						size : '4px'
					});
					/*-------[progressbar]-------*/
					var progress = 92;
					$(".progressbar").progressbar({
						value : progress
					});

					/*------------[popup screens]---------*/
					$("#addScreenbtn").bind("click", function(e) {
						$('#overLay').show();
						$('#addTestPlan').css({
							"display" : "block"
						});
					});
					
					/* --------[Add Test Data Def pop Up]------*/
					$("#addTestDefinitionBtn").bind("click", function(e){
						$('#overLay').show();
						$('.AddTestDefinition').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('select').val('');
						});
						$('.AddTestDefinition').css({"display":"block"});
					});
					
					/* --------[Add Test Parameter Data pop Up]------*/
					$("#addTestParamDataBtn").bind("click", function(e){
						$('#overLay').show();
						$('.AddTestParamData').find('form').find('div.popupHeader').next().each(function(){
							$(this).find('input').val('');
							$(this).find('select').val('');
							$(this).find('textarea').val('');
						});
						$('.AddTestParamData').css({"display":"block"});
					});
					
						/* --------[Edit Test Data Def pop Up]------*/
					
					$(document).on("click",".editTestDataDefValues",function(e){
						$("#overlay").show();
						$("#selected_testData").hide();
						$('.editTestParamData').hide();
						$(".EditTestDefinition").show();
						
						
					});
					
					$(document).on("click","#testDatabox",function(e){
							//$("#TestDataDefinition").hide();
							$("#selected_testData").show();
							
					});
					
					

					/*------------[Add Screen]---------*/
					$("#addScreen")
							.bind(
									"click",
									function(e) {
										var screenName = $('#name').val();
										var temp = $('<div class="suites"><h3>'
												+ screenName
												+ '</h3><ul><li><span class="completedBy">By Firdos on 22/10/2013</span></li><li><a href="#">Edit</a></li></ul><ul><li>Number of Objects</li><li></li></ul></div>');
										$(temp).appendTo($('.testScreen'));
										$("input:text").val("");
										$('#overLay').hide();
										$('#addTestPlan').css({
											"display" : "none"
										});
									});

					/*------------[Edit Screen]---------*/
					$("#editScreen")
							.bind(
									"click",
									function(e) {
										var screenName = $('#name').val();
										var temp = $('<div class="suites"><h3>'
												+ screenName
												+ '</h3><ul><li><span class="completedBy">By Firdos on 22/10/2013</span></li><li><a href="#">Edit</a></li></ul><ul><li>Number of Objects</li><li></li></ul></div>');
										// $(temp).appendTo($('.testScreen'));
										$("input:text").val("");
										$('#overLay').hide();
										$('#addTestPlan').css({
											"display" : "none"
										});
									});

					/*------------[popup screens]---------*/
					$("#addObjectBtn").bind("click", function(e) {
						$('#overLay').show();
						$('#addObjectPopup').css({
							"display" : "block"
						});
					});

				});

function sample() {
//	alert('Hello');
	$("#executionMain").show();
	$('#testSuitesCase').hide();
	$("#testRunsRusults").hide();
	$('#addApplication_popup').hide();
	$('#application_status').hide();
	$("#object").hide();
	$("#TestDataDefinition").hide();
	$('#reports').hide();
	$("#TestApplicationFeature").hide();
	$(".addObjectGrpPop").hide();
	$(".addTestSuite").hide();
	$("#ApplicationFunction").hide();
	$(".testSuitesForPlan").hide();
	$(".editTestPlan").hide();
	$(".editTestSuite").hide();
	$(".addObjectPop").hide();
	$(".addActionPop").hide();
	$(".addObjectTypePop").hide();
	$(".objGrpsForScreen").hide();
	$(".editScreenDetails").hide();
	$(".addParamGrpDetailsPop").hide();
	$(".addFeaturePop").hide();
	$(".featuresForFunction").hide();
	$("#appFunctionals").hide();
	$(".addNewScreenPop").hide();
	$(".editAppFeaturePop").hide();
	$(".objsForObjGrpList").hide();
	$(".editObjectDetailsPop").hide();
	$(".conditionGrp").hide();
	$(".IdentifierTabGrp").hide();
	$(".ReplacementTabGrp").hide();
	$(".addConditionGrpPop").hide();
	$(".addIdentifierTypesPop").hide();
	$(".addReplacementStringsPopUps").hide();
	$(".editConditionGrpPop").hide();
	$(".editidentifierTyPop").hide();
	$(".editReplaceStringsTyPop").hide();
	$(".conditionForConGrpList").hide();
	$(".addNewCondition").hide();
	$(".editConditionDetailsPop").hide();
	 $("#testImport").hide();
	 $("#addImport").hide();
	 $(".addParamGrpPop").hide();
	 $(".editParamGrpPop").hide(); 
	 $(".paramsForParamGrpList").hide();
	 $(".addParameterPop").hide();
	 $(".editParameterPop").hide();
	 $(".paramGrp").hide();
		$("#addTestPop").hide();
		$("#editTestPop").hide();
		$(".importExcelPopUp").hide();
		 $(".importExcelInAppPopUp").hide();
}

function showMultiLanefields(boxName) {
	if (boxName.checked == true) {
		$('#checkMultiLane').css({
			"display" : "block"
		});

	} else {
		$('#checkMultiLane').css({
			"display" : "none"
		});

	}

}

function showMultiLanefieldsEdit(boxName) {
	boxName = document.getElementById('checkLaneEdit');
	if (boxName.checked == true) {
		$('#checkMultiLaneEdit').css({
			"display" : "block"
		});

	} else {
		$('#checkMultiLaneEdit').css({
			"display" : "none"
		});
	}

}

function showIdentifierfields(boxName) {
	if (boxName.checked == true) {
		$('#checkIdentifier').css({
			"display" : "block"
		});

	} else {
		$('#checkIdentifier').css({
			"display" : "none"
		});
	}

}

function showIdentifierfieldsEdit(boxName) {
	if (boxName.checked == true) {
		$('#checkIdentifierEdit').css({
			"display" : "block"
		});

	} else {
		$('#checkIdentifierEdit').css({
			"display" : "none"
		});
	}

}

function showJunitInputFields(boxName) {
	
	if (boxName == 1000) {
		$('#addtest_Junitsteps_table').css({
			"display" : "block"
		});
		$('.addTestStepsHide').css({
			"display" : "none"
		});
		$('#addtest_JavaAPIsteps_table').css({
			"display" : "none"
		});
	}
	else if(boxName == 1001){
		
		$('#addtest_JavaAPIsteps_table').css({
			"display" : "block"
		});
		$('#addtest_Junitsteps_table').css({
			"display" : "none"
		});
		$('.addTestStepsHide').css({
			"display" : "none"
		});
	}
	else {
		$('.addTestStepsHide').css({
			"display" : "block"
		});
		$('#addtest_Junitsteps_table').css({
			"display" : "none"
		});
		$('#addtest_JavaAPIsteps_table').css({
			"display" : "none"
		});
	}

}


function IdentifierType() {
	// alert('You have Clicked Object Creation Tab');
	$('#testSuitesCase').hide();
	$("#testRunsRusults").hide();
	$('#addApplication_popup').hide();
	$('#application_status').hide();
	$('#reports').hide();
	$("#executionMain").hide();
	$("#TestDataDefinition").hide();
	$('#object').hide();
	$('#suite').hide();
	$(".addObjectGrpPop").hide();
	$(".addTestSuite").hide();
	$(".testSuitesForPlan").hide();
	$(".editTestPlan").hide();
	$(".editTestSuite").hide();
	$(".addObjectPop").hide();
	$(".addActionPop").hide();
	$(".addObjectTypePop").hide();
	$(".objGrpsForScreen").hide();
	$(".editObjGrp").hide();
	$("#ApplicationFunction").hide();
	$(".editScreenDetails").hide();
	$(".addParamGrpDetailsPop").hide();
	$(".addFeaturePop").hide();
	$(".featuresForFunction").hide();
	$("#appFunctionals").hide();
	$(".addNewScreenPop").hide();
	$(".editAppFeaturePop").hide();
	$(".objsForObjGrpList").hide();
	$(".editObjectDetailsPop").hide();
	$(".conditionGrp").hide();
	$(".IdentifierTabGrp").show();
	$(".ReplacementTabGrp").hide();
	$(".addConditionGrpPop").hide();
	$(".addIdentifierTypesPop").hide();
	$(".addReplacementStringsPopUps").hide();
	$(".editConditionGrpPop").hide();
	$(".editidentifierTyPop").hide();
	$(".editReplaceStringsTyPop").hide();
	$(".conditionForConGrpList").hide();
	$(".addNewCondition").hide();
	$(".editConditionDetailsPop").hide();
	 $("#testImport").hide();
	 $("#addImport").hide();
	 $(".paramGrp").hide();
	 $(".addParamGrpPop").hide();
	 $(".editParamGrpPop").hide(); 
	 $(".paramsForParamGrpList").hide();
	 $(".addParameterPop").hide();
	 $(".editParameterPop").hide();
		$("#addTestPop").hide();
		$("#editTestPop").hide();
		$(".importExcelPopUp").hide();
		 $(".importExcelInAppPopUp").hide();
}

function ReplacementStrings() {
	
	$('#testSuitesCase').hide();
	$("#testRunsRusults").hide();
	$('#addApplication_popup').hide();
	$('#application_status').hide();
	$('#reports').hide();
	$("#executionMain").hide();
	$("#TestDataDefinition").hide();
	$('#object').hide();
	$('#suite').hide();
	$(".addObjectGrpPop").hide();
	$(".addTestSuite").hide();
	$(".testSuitesForPlan").hide();
	$(".editTestPlan").hide();
	$(".editTestSuite").hide();
	$(".addObjectPop").hide();
	$(".addActionPop").hide();
	$(".addObjectTypePop").hide();
	$(".objGrpsForScreen").hide();
	$(".editObjGrp").hide();
	$("#ApplicationFunction").hide();
	$(".editScreenDetails").hide();
	$(".addParamGrpDetailsPop").hide();
	$(".addFeaturePop").hide();
	$(".featuresForFunction").hide();
	$("#appFunctionals").hide();
	$(".addNewScreenPop").hide();
	$(".editAppFeaturePop").hide();
	$(".objsForObjGrpList").hide();
	$(".editObjectDetailsPop").hide();
	$(".conditionGrp").hide();
	$(".IdentifierTabGrp").hide();
	$(".ReplacementTabGrp").show();
	$(".addConditionGrpPop").hide();
	$(".addIdentifierTypesPop").hide();
	$(".addReplacementStringsPopUps").hide();
	$(".editConditionGrpPop").hide();
	$(".editidentifierTyPop").hide();
	$(".editReplaceStringsTyPop").hide();
	$(".conditionForConGrpList").hide();
	$(".addNewCondition").hide();
	$(".editConditionDetailsPop").hide();
	 $("#testImport").hide();
	 $("#addImport").hide();
	 $(".paramGrp").hide();
	 $(".addParamGrpPop").hide();
	 $(".editParamGrpPop").hide(); 
	 $(".paramsForParamGrpList").hide();
	 $(".addParameterPop").hide();
	 $(".editParameterPop").hide();
		$("#addTestPop").hide();
		$("#editTestPop").hide();
		$(".importExcelPopUp").hide();
		 $(".importExcelInAppPopUp").hide();
}



function objectCreation() {
	// alert('You have Clicked Object Creation Tab');
	$('#testSuitesCase').hide();
	$("#testRunsRusults").hide();
	$('#addApplication_popup').hide();
	$('#application_status').hide();
	$('#reports').hide();
	$("#executionMain").hide();
	$("#TestDataDefinition").hide();
	$('#object').show();
	$('#suite').hide();
	$(".addObjectGrpPop").hide();
	$(".addTestSuite").hide();
	$(".testSuitesForPlan").hide();
	$(".editTestPlan").hide();
	$(".editTestSuite").hide();
	$(".addObjectPop").hide();
	$(".addActionPop").hide();
	$(".addObjectTypePop").hide();
	$(".objGrpsForScreen").hide();
	$(".editObjGrp").hide();
	$("#ApplicationFunction").hide();
	$(".editScreenDetails").hide();
	$(".addParamGrpDetailsPop").hide();
	$(".addFeaturePop").hide();
	$(".featuresForFunction").hide();
	$("#appFunctionals").hide();
	$(".addNewScreenPop").hide();
	$(".editAppFeaturePop").hide();
	$(".objsForObjGrpList").hide();
	$(".editObjectDetailsPop").hide();
	$(".conditionGrp").hide();
	$(".IdentifierTabGrp").hide();
	$(".ReplacementTabGrp").hide();
	$(".addConditionGrpPop").hide();
	$(".addIdentifierTypesPop").hide();
	$(".addReplacementStringsPopUps").hide();
	$(".editConditionGrpPop").hide();
	$(".editidentifierTyPop").hide();
	$(".editReplaceStringsTyPop").hide();
	$(".conditionForConGrpList").hide();
	$(".addNewCondition").hide();
	$(".editConditionDetailsPop").hide();
	 $("#testImport").hide();
	 $("#addImport").hide();
	 $("#application").hide();
	 $(".addParamGrpPop").hide();
	 $(".editParamGrpPop").hide(); 
	 $(".paramGrp").hide();
	 $(".paramsForParamGrpList").hide();
	 $(".addParameterPop").hide();
	 $(".editParameterPop").hide();
		$("#addTestPop").hide();
		$("#editTestPop").hide();
		$(".importExcelPopUp").hide();
		 $(".importExcelInAppPopUp").hide();
}
function hideUploadPath(){
    $('#projPath').hide();
    $('#svnPath').show();
    $('#userNameid').show();
    $('#pwdId').show();
    $('#targetId').show();
    $("#projectPathId").val("Yes");
//    $('#svnPathId').unchecked();
    document.getElementById("svnPathId").checked = false;
}

function hideSvnPath(){
	$('#svnPath').hide();
	$('#userNameid').hide();
	$('#pwdId').hide();
	$('#projPath').show();
	$('#targetId').show();
	$("#projectPathId").val("No");
	document.getElementById("projectPathId").checked = false;
}

$(document).on("click","#addTestScenariosButton",function(e){
	$(".overLayScreen").hide();
	$(".addTestScenariosPops").hide();
	
});

//$(document).on("click","#addtestCasesButtons",function(e){
//	$(".overLayScreen").hide();
//	$(".addCasetest").hide();
//	$(".addTestScenariosPops").hide();
//	
//});

$(document).on("click","#addtestStepsButtons",function(e){
	e.preventDefault();
	$(".overLayScreen").hide();
	$(".addCasetest").hide();
	$(".addTestScenariosPops").hide();
	
});

$(document).on("click","#editTestScenariosButtons",function(e){
	$(".editScenariosPop").hide();
	
});


function conditionGrpTab() {
	// alert('You have Clicked Object Creation Tab');
	$('#testSuitesCase').hide();
	$("#testRunsRusults").hide();
	$('#addApplication_popup').hide();
	$('#application_status').hide();
	$('#reports').hide();
	$("#executionMain").hide();
	$("#TestDataDefinition").hide();
	$('#object').hide();
	$('#suite').hide();
	$(".addObjectGrpPop").hide();
	$(".addTestSuite").hide();
	$(".testSuitesForPlan").hide();
	$(".editTestPlan").hide();
	$(".editTestSuite").hide();
	$(".addObjectPop").hide();
	$(".addActionPop").hide();
	$(".addObjectTypePop").hide();
	$(".objGrpsForScreen").hide();
	$(".editObjGrp").hide();
	$("#ApplicationFunction").hide();
	$(".editScreenDetails").hide();
	$(".addParamGrpDetailsPop").hide();
	$(".addFeaturePop").hide();
	$(".featuresForFunction").hide();
	$("#appFunctionals").hide();
	$(".addNewScreenPop").hide();
	$(".editAppFeaturePop").hide();
	$(".objsForObjGrpList").hide();
	$(".editObjectDetailsPop").hide();
	$(".conditionGrp").show();
	$(".IdentifierTabGrp").hide();
	$(".ReplacementTabGrp").hide();
	$(".addConditionGrpPop").hide();
	$(".addIdentifierTypesPop").hide();
	$(".addReplacementStringsPopUps").hide();
	$(".editConditionGrpPop").hide();
	$(".editidentifierTyPop").hide();
	$(".editReplaceStringsTyPop").hide();
	$(".conditionForConGrpList").hide();
	$(".addNewCondition").hide();
	$(".editConditionDetailsPop").hide();
	 $("#testImport").hide();
	 $("#addImport").hide();
	 $("#application").hide();
	 $(".paramGrp").hide();
	 $(".addParamGrpPop").hide();
	 $(".editParamGrpPop").hide(); 
	 $(".paramsForParamGrpList").hide();
	 $(".addParameterPop").hide();
	 $(".editParameterPop").hide();
		$("#addTestPop").hide();
		$("#editTestPop").hide();
		$(".importExcelPopUp").hide();
		 $(".importExcelInAppPopUp").hide();
}


$(document).on("click","#editThisTestCases",function(e){
	e.stopPropagation();
	$("#overlay").show();
	$(".editCaesPop").show();
	});


$(document).on("click","#editTestCassButtons",function(e){
	$(".editCaesPop").hide();
	
});


$(document).on("click","#editThisTestStpss",function(e){
	e.stopPropagation();
	$("#overlay").show();
	$(".editStpsPop").show();
	});


$(document).on("click","#editTestStpsButtons",function(e){
	$(".editStpsPop").hide();
	
});

$(document).on("click","#addIdentifierTypesBtns",function(e){
	$('#overLay').show();
	$(".addIdentifierTypesPop").show();
});


$(document).on("click","#addIdentifierTyButton",function(e){
	$(".overLayScreen").hide();
	$(".addIdentifierTypesPop").hide();
	
});


$(document).on("click",".addReplacementStringsBtns",function(e){
	$('#overLay').show();
	$(".addReplacementStringsPopUps").show();
});


$(document).on("click","#addReplaceStringButton",function(e){
	$(".overLayScreen").hide();
	$(".addReplacementStringsPopUps").hide();
	
});

$(document).on("click","#editThisidentifierTy",function(e){
	$("#overlay").show();
	$(".editidentifierTyPop").show();
});

$(document).on("click","#editIdentifierTypesButtons",function(e){
	$(".editidentifierTyPop").hide();
	
});



function paramGrpTab(){
	$('#testSuitesCase').hide();
	$("#testRunsRusults").hide();
	$('#addApplication_popup').hide();
	$('#application_status').hide();
	$('#reports').hide();
	$("#executionMain").hide();
	$("#TestDataDefinition").hide();
	$('#object').hide();
	$('#suite').hide();
	$(".addObjectGrpPop").hide();
	$(".addTestSuite").hide();
	$(".testSuitesForPlan").hide();
	$(".editTestPlan").hide();
	$(".editTestSuite").hide();
	$(".addObjectPop").hide();
	$(".addActionPop").hide();
	$(".addObjectTypePop").hide();
	$(".objGrpsForScreen").hide();
	$(".editObjGrp").hide();
	$("#ApplicationFunction").hide();
	$(".editScreenDetails").hide();
	$(".addParamGrpDetailsPop").hide();
	$(".addFeaturePop").hide();
	$(".featuresForFunction").hide();
	$("#appFunctionals").hide();
	$(".addNewScreenPop").hide();
	$(".editAppFeaturePop").hide();
	$(".objsForObjGrpList").hide();
	$(".editObjectDetailsPop").hide();
	$(".conditionGrp").hide();
	$(".addConditionGrpPop").hide();
	$(".addIdentifierTypesPop").hide();
	$(".addReplacementStringsPopUps").hide();
	$(".editConditionGrpPop").hide();
	$(".conditionForConGrpList").hide();
	$(".addNewCondition").hide();
	$(".editConditionDetailsPop").hide();
	 $("#testImport").hide();
	 $("#addImport").hide();
	 $("#application").hide();
	 $(".paramGrp").show();
	 $(".addParamGrpPop").hide();
	 $(".editParamGrpPop").hide(); 
	 $(".paramsForParamGrpList").hide();
	 $(".IdentifierTabGrp").hide();
	 $(".ReplacementTabGrp").hide();
	 $(".addParameterPop").hide();
	 $(".editParameterPop").hide();
		$("#addTestPop").hide();
		$("#editTestPop").hide();
		$(".importExcelPopUp").hide();
		 $(".importExcelInAppPopUp").hide();
}



$(document).on("click","#editThisidentifierTy",function(e){
	$("#overlay").show();
	$(".editidentifierTyPop").show();
});

$(document).on("click","#editIdentifierTypesButtons",function(e){
	$(".editidentifierTyPop").hide();
	
});



$(document).on("click","#editThisReplaceStringsTy",function(e){
	$("#overlay").show();
	$(".editReplaceStringsTyPop").show();
});

$(document).on("click","#editReplaceStringsButtons",function(e){
	$(".editReplaceStringsTyPop").hide();
	
});

$(document).on("click","#editThisReplaceStringsTy",function(e){
	$("#overlay").show();
	$(".editReplaceStringsTyPop").show();
});

$(document).on("click","#editReplaceStringsButtons",function(e){
	$(".editReplaceStringsTyPop").hide();
	
});

$(document).on("click",".StepTable",function(e){
	$("#addCondParamTable").show();
	
});

