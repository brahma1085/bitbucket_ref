$(document).ready(function(e){
	$("#homepage").show();
	$("#application_status").hide();
	$("#application").hide();
	$("#testSuitesCase").hide();
	$("#reports").hide();
	$("#ApplicationDetails > div:First-child").hide();
	/*------- Application Selection----*/
	$(document).on("click",".select_application", function(e){
		$("#homepage").hide();
		$("#application_status").show();
		$("#ApplicationDetails > div:First-child").show();
		
		var application_name= $(this).find('h3').text();
		var subSpan = $("<span class='subHeading'>"+application_name+"</span><span class='tab_selected'>/ Overview</span>");
		$("#dashboard").find('h2').append(subSpan);
		
		$(".subHeading").bind("click",function(e){
		$("#application_status").show();
		$("#application").hide();
		$("#testSuitesCase").hide();
		$("#reports").hide();
		$("#ApplicationDetails > div:First-child ul li").find('a').removeClass('activeTab');
		$(".overviewTab").addClass('activeTab');
	});
	});
	/*--Add new Application---*/
	$("#add_new_app").bind("click", function(e){
		var app_name = $("#addApplication_popup > div:first-child + div").find("input[type='text']").val().trim();
		 var app_description = $("#addApplication_popup > div:first-child + div").find("textarea").val().trim();
		 var noOfItems=$('#application_lists>div').length;
		
	     for(var i=0;i<noOfItems;i++){
		 	var all_app_name = $("#application_lists>div").eq(i).find("h3").text();
		 	  if(all_app_name.toUpperCase() == app_name.toUpperCase()){
  				$("#existing_app").css({"color":"red", "display": "inline-block" }).show();
				$("#app_desc_error").hide();
				$("#app_name_error").hide();
				return false;
  			}
		 }
		
	if(app_name.length > 0 && app_description.length > 0){
		var app_name = $("#addApplication_popup > div:first-child + div").find("input[type='text']").val();
		$("#application_lists").append("<div class='select_application'><h3 class='floatleft'>" + app_name + "</h3>"
		+"<a href='#' class='cancelApplication floatright'><span>Cancel</span></a><a href='#' class='editApplication floatright'><span>Edit</span></a>"
		+"<ul class='clear'><li><span>00</span>Test Suites</li><li><span>00</span>Active Test Run</li><li><span>00</span>No. of Issues</li></ul></div>");
		$("#addApplication_popup").hide();
		$('#overLay').hide();
		}
	else if(app_name == null || app_name == ""){
			$("#app_name_error").css({"color":"red", "display": "inline-block" }).show();
			$("#app_desc_error").hide();
			$("#existing_app").hide();
			return false;
		}
	else if(app_description == null || app_description == ""){
			$("#app_desc_error").css({"color":"red", "display": "inline-block"}).show();
			$("#app_name_error").hide();
			$("#existing_app").hide();
			return false;
		} 
	 
		 $("#addApplication_popup > div:first-child + div").find("input[type='text']").val("");
 		$("#addApplication_popup > div:first-child + div").find("textarea").val("")
	});
	$(document).on("click",".cancelApplication", function(e){
	e.stopPropagation();
	e.preventDefault();
		$(this).parent().remove();
	});
	/*------Adding Steps-----*/
	$('#addSteps').click(function(e){
	e.preventDefault();
		$("#addStep").append("<tr><td><input type='text' placeholder='Add Text'/></td>"
		+"<td><input type='text' placeholder='Add Text'/></td><td><input type='text' placeholder='Add Text'/></td>"
		+"<td><input type='text' placeholder='Add Text'/></td><td><input type='text' placeholder='Add Text'/></td>"
		+"<td><input type='text' placeholder='Add Text'/></td><td><input type='text' placeholder='Add Text'/></td>"
		+"<td><a href='#' class='edit'><span>Delete</span></a></td><td><a href='#' class='cancel'><span>Delete</span></a></td></tr>");
	});
	/*----delete---*/
	$(document).on("click",".cancel",".cancelSet", function(e){
	e.preventDefault();
		$(this).parent().parent().remove();
	});
	/*---edit----*/
	
	$(document).on("click","#addCase table tr td a.edit",function(e){
	e.preventDefault();
		$("#addsteps_dataset").show();
	});
	
	/*---Navigator--*/
	
	$("#ApplicationDetails > div:First-child a").bind("click", function(e){
	e.preventDefault();
		$(".tab_selected").remove();
		var selected_tab = $(this).text();
		var tab_clicked = $("<span class='tab_selected'>"+ "  /  "+selected_tab+ "</span>");
		$("#dashboard").find('h2').append(tab_clicked);
		
		$(".subHeading").click(function(e){
			$("#application_status").show();
			$("#application").hide();
			$("#testSuitesCase").hide();
			$("#reports").hide();
		});
		
		$("#ApplicationDetails > div:First-child ul li").find('a').removeClass('activeTab');
		$(this).addClass('activeTab');
		
	});
	$('#ApplicationDetails > div:First-child .overviewTab').bind("click",function(e){
	e.preventDefault();
		$("#application_status").show();
		$("#application").hide();
		$("#testSuitesCase").hide();
		$("#reports").hide();
	});
	
	$('#ApplicationDetails > div:First-child .testRunTab').bind("click",function(e){
	e.preventDefault();
		$("#application_status").hide();
		$("#application").show();
		$("#testSuitesCase").hide();
		$("#reports").hide();
		$("#suite").hide();
		$("#trends").hide();
		$('#testRunsRusults').show();
	});
	
	$('#ApplicationDetails > div:First-child .testSuiteTab').bind("click",function(e){
	e.preventDefault();
		$("#application_status").hide();
		$("#application").hide();
		$("#testSuitesCase").show();
		$("#reports").hide();
		$("#selected_suite").hide();
		$("#testSuites").show();
		$("#case_details").hide();
		$("#case_history").hide();
	});
	
	$('#ApplicationDetails > div:First-child .reportsTab').bind("click",function(e){
	e.preventDefault();
		$("#application_status").hide();
		$("#application").hide();
		$("#testSuitesCase").hide();
		$("#reports").show();
	});
	$("#add_application").bind("click", function(e){
		$('#overLay').show();
		$(".addApplication").show();
		});
	
	$(".editThisApplication").bind("click", function(e){
		$('#overLay').show();
		$('.editApplicationPop').css({"display":"block"});
		});
	/*---Navigator ends--*/
	$("#select_all_cases").bind("click",function(){
		$('#cases li').find('input:checkbox').attr('checked', true);
	});
	
	$("#testSuites").show();
	$("#selected_suite").hide();
	$(".suites").bind("click", function(e){
	e.preventDefault();
		$("#selected_suite").show();
		$("#testSuites").hide();
		$("#suite_details").show();
		$("#case_list").hide();
		 var selectedSuite = $(this).find('h3').text();
 		var suiteClicked = $("<span class='suiteSelected'>"+ "  /  "+selectedSuite+ "</span>");
 		$("#dashboard").find('h2').append(suiteClicked);
 		
 		$(document).on("click","#ApplicationDetails > div:First-child a",function(){
            $("#dashboard span.suiteSelected,#dashboard span.caseSelected").remove();
        });
	});
	
	$(document).on("click","#dashboard",function(){
             
             $(this).find("span").last().prevAll().unbind("click");     
             $(this).find("span").last().prevAll().bind("click",function(){
                  
             $(this).nextAll().remove() ;
             });
      });
	
	$("#case_history").hide();
	$(".case").bind("click", function(e){
		e.preventDefault();
		$("#case_list").show();
		$("#suite_details").hide();
		$("#case_details").show();
		var selectedCase = $(this).text();
		var caseClicked = $("<span class='caseSelected'>"+ "  /  "+selectedCase+ "</span>");
		$("#dashboard").find('h2').append(caseClicked);
	});
	
	$("#history").bind("click", function(e){
		e.preventDefault();
		$("#case_details").hide();
		$("#case_history").show();
		$(".addTestButton").hide();
		$("#case_list ul li").find('a').removeClass('activeSuite');
		$(this).addClass('activeSuite');
	});
	/*----[Case Result]----*/
	$("#case_history > table td:contains(Passed)").css("color", "green");
	$("#case_history > table td:contains(Failed)").css("color", "red");
	$("#case_history > table td:contains(Blocked)").css("color", "grey");
	$("#case_history > table td:contains(On Hold)").css("color", "#e4c332");
	/*----[Function and Feature Popup]----*/
	var functionName , featureName;
	$(document).on("click","#functions ul li", function(e){
		e.preventDefault();
		$("#functions ul li").removeClass('current');
		$(this).addClass('current');
		functionName = $(this).text();
	});
	$(document).on("click","#features ul li", function(e){
		e.preventDefault();
		$("#features ul li").removeClass('current');
		$(this).addClass('current');
		featureName = $(this).text();
	});
	$("#done_button").bind("click", function(){
		$("#suite_functionality").text(functionName).append("<span>Functionality</span>");
		 $("#suite_feature").text(featureName).append("<span>Feature</span>");
	});
	/*---[Warning Application]-------*/
	$(".warning").css({
	'background-color' : "#fff0f0",
	 border : '1px solid #fc8383'
	});
	
	$("#details").bind("click", function(e){
		e.preventDefault();
		$("#case_details").show();
		$("#case_history").hide();
		$(".addTestButton").show();
		$("#case_list ul li").find('a').removeClass('activeSuite');
		$(this).addClass('activeSuite');
	});
 	
	$("#add_function").bind("click", function(e){
	$("#show_addFunction").slideDown("slow");
	});
	
	$("#save_function").bind("click", function(){
		var func_name = $("#show_addFunction").find("input[type='text']").val().trim();
		var function_description = $("#show_addFunction").find("textarea").val().trim();
		
		if(func_name.length > 0 && function_description.length > 0){
		$("#show_addFunction").slideUp("slow");
		$("#features ul").find("li").removeClass("current");
		$("#functions ul").prepend("<li>"+func_name+"</li>").addClass("current");
		$("#features ul").find("li").html("");
		$("#features ul").find("li").removeClass("current");
		$("#show_addFeature").slideDown("slow");
		}
		else if(func_name == null || func_name == ""){
			$("#function_name_error").css({"color":"red", "display": "inline-block" }).show();
			$("#function_desc_error").hide();
			return false;
		}
	    else if(function_description == null || function_description == ""){
			$("#function_desc_error").css({"color":"red", "display": "inline-block"}).show();
			$("#function_name_error").hide();
			return false;
		}
		$("#show_addFunction").find("input[type='text']").val('');
		$("#show_addFunction").find("textarea").val('');
	});
	
	
	$("#add_features").bind("click", function(){
		$("#show_addFeature").slideDown("slow");
	});
	
	$("#submit_feature").bind("click", function(){
		var new_feature = $("#show_addFeature").find("input[type='text']").val().trim();
		var new_feature_desc = $("#show_addFeature").find("textarea").val().trim();
		
		if(new_feature.length > 0 && new_feature_desc.length > 0){
		$("#features ul").prepend("<li>"+new_feature+"</li>").addClass("current");
		$("#show_addFeature").slideUp("slow");
		}
		else if(new_feature == null || new_feature == ""){
			$("#feature_name_error").css({"color":"red", "display": "inline-block" }).show();
			$("#feature_desc_error").hide();
			return false;
		}
	    else if(new_feature_desc == null || new_feature_desc == ""){
			$("#feature_desc_error").css({"color":"red", "display": "inline-block"}).show();
			$("#feature_name_error").hide();
			return false;
		}
	});
	
	$("#add_more_features").bind("click", function(){
		var more_feature = $("#show_addFeature").find("input[type='text']").val().trim();
		var more_feature_description = $("#show_addFeature").find("textarea").val().trim();
		
		if(more_feature.length > 0 && more_feature_description.length > 0){
		$("#features ul").prepend("<li>"+more_feature+"</li>").addClass("current");
		}
		else if(more_feature == null || more_feature == ""){
			$("#feature_name_error").css({"color":"red", "display": "inline-block" }).show();
			$("#feature_desc_error").hide();
			return false;
		}
	    else if(more_feature_description == null || more_feature_description == ""){
			$("#feature_desc_error").css({"color":"red", "display": "inline-block"}).show();
			$("#feature_name_error").hide();
			return false;
		}
		$("#show_addFeature").find("input[type='text']").val('');
		$("#show_addFeature").find("textarea").val('');
	});
	
	$(".cancel_function").bind("click", function(e){
	e.preventDefault();
		$("#show_addFunction").slideUp("slow");
		$("#show_addFeature").slideUp("slow");
	});
	
	$("#stacked_bar_graph").hide();
	$("#graph_view").bind("click", function(e){
	e.preventDefault();
		$("#testCase_details_table").find('table').hide();
		$("#stacked_bar_graph").show();
		$("#testCase_header > a").removeClass('activeForm');
		$(this).addClass('activeForm');
	});
	
	$("#table_view").bind("click", function(e){
	e.preventDefault();
		$("#testCase_details_table").find('table').show();
		$("#stacked_bar_graph").hide();
		$("#testCase_header > a").removeClass('activeForm');
		$(this).addClass('activeForm');
	});
	
	/*------Scrolling of Application list-------*/

	$('#application_lists').slimScroll({
		height: '284px',
		width: '100%',
		position: 'right',
		color: '#dcdcdc',
		size: '4px'
	});
	$('#latest_test_runs dl,#upcoming_function dl,#test_case_details>div > ul').slimScroll({
		height: '259px',
		width: '100%',
		position: 'right',
		color: '#dcdcdc',
		size: '4px'
	});
	
	/*------Test Runs And Results-------*/
	$("#active").show();
	$("#complete").hide();
	$("#suite").hide();
	$("#activeTest").click(function(e){
	e.preventDefault();
		$("#complete").hide();
		$("#active").show();
		$("#testRunsRusults ul li > a").removeClass('activeRun');
		$(this).addClass('activeRun');
	});
	$("#completeTest").click(function(e){
	e.preventDefault();
		$("#active").hide();
	$("#complete").show();
	$("#testRunsRusults ul li > a").removeClass('activeRun');
	e.preventDefault();
		$(this).addClass('activeRun');
	});
	$("#active div").click(function(e){
		$("#testRunsRusults").hide();
		$("#suite").show();
		var selectedSuite1 = $(this).find('h3').text();
		$("#suite h3").text(selectedSuite1);
	});
	$("#trends").hide();
	$("#suitesStatus").click(function(e){
	e.preventDefault();
		$("#status").show();
		$("#trends").hide();
		$("#suite ul li > a").removeClass('activeTest_suite');
		$(this).addClass('activeTest_suite');
	});
	$("#suitesTrends").click(function(e){
	e.preventDefault();
		$("#status").hide();
		$("#trends").show();
		$("#suite ul li > a").removeClass('activeTest_suite');
		$(this).addClass('activeTest_suite');
	});
	$("#editPage_popup").bind("click", function(e){
		$('#overLay').show();
		$('#editPage').css({"display":"block"});
		
		var spaceIndex = $("#suite_functionality").text().indexOf("F");
        var functionString = $("#suite_functionality").text().substring(0,spaceIndex);  
        $("#functions ul li").removeClass("current");
        $("#functions ul li").each(function(){
             if($(this).text() == functionString ){
				 $(this).addClass("current");
             }
        });   
        var spaceIndex1 = $("#suite_feature").text().indexOf("F");
        var featuresString = $("#suite_feature").text().substring(0,spaceIndex1); 
        $("#features ul li").removeClass("current");       
        $("#features ul li").each(function(){
             if($(this).text() == featuresString ){
                 $(this).addClass("current");
             }
        });    
	});
	$('#editPage').find(".close").bind("click", function(e){
		$('#overLay').hide();
		$('#editPage').hide();
	});
	$("#done_button").bind("click", function(e){
		$('#overLay').hide();
		$('#editPage').hide();
	});
	$("#add_testCases").find('.addTestButton').bind("click", function(e){
		$('#overLay').show();
		$('#addCase').css({"display":"block"});
	});
	
	/*------------[popup test suites and runs]---------*/
	
	$("#addTestPlanBtn").bind("click", function(e){
		$('#overLay').show();
		$('#addTestPlan').css({"display":"block"});
	});
	
	$('#edit').bind('click',function(e){
		//e.preventDefault();
		 e.stopPropagation();
		 e.cancelBubble = true;
		alert("dsdsdsd");
		$(this).parent().unbind('click');
		$('#addTestPlanBtn').trigger('click');
	});
	
		$('#edit1').bind('click',function(e){
		//e.preventDefault();
		 e.stopPropagation();
		 e.cancelBubble = true;
		alert("dsdsdsd");
		$(this).parent().unbind('click');
		$('#addTestPlanBtn').trigger('click');
	});
	
	$("#addTestSuiteBtn").bind("click", function(e){
		$('#overLay').show();
		$('#addTestRun').css({"display":"block"});
	});
	
	$("#addTestRunBtn").bind("click", function(e){
		$('#overLay').show();
		$('#addTestRun').css({"display":"block"});
	});
	
	/*------------[popup test suites and runs end]---------*/
	
	$('#close_dataset').bind("click", function(){
		$('#addsteps_dataset').hide();
	});
	
	$("#addTestSuitesbtn").bind("click", function(e){
		$('#overLay').show();
		$('#addSuitesPop').css({"display":"block"});
	});
	
	$('.close').bind("click", function(e){
	e.preventDefault();
		$('#overLay').hide();
		$('#addCase').hide();
		$('#addTestRun').hide();
		$('#addTestPlan').hide();
		$('#editPage').hide();
		$('#addSuitesPop').hide();
		$('#addApplication_popup').hide();
	});
	$("#functions ul, #features ul").slimScroll({
		height: '540px',
		position: 'right',
		color: '#dcdcdc',
		size: '4px'
	});
	/*-------[progressbar]-------*/
	var progress= 92;
	$( ".progressbar" ).progressbar({
		value: progress
	});
});
