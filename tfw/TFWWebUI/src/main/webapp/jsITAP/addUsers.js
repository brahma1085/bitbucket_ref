/* Document Ready */
var appList = [];
var roleList = [];
$(document).ready(function(){
	$('.filterchosen').chosen();
	
	/* Add New User Div interaction */
	$(document).on("click", ".addNewUser", function() {
		//alert("plus clicked");
//		$("#noUsers").hide();
		
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
		//$("#noUsers").hide();
		$(this).parents().find('#user_info_up').hide();
		$(this).parents().find('.pointing_here').removeClass('pointing_here');debugger;
		/* Clearing the contents */
		$(this).parents().find('#new_user_info .typeWrapper .gName.groupTypeRoles').remove();
		//$(this).parents().find('#new_user_info .typeWrapper').contents().remove();
		$("#user_info_up").hide();
		$(".user_info").removeAttr("style");
		$(this).parents().find("#new_user_info input.username").val('');
		$(this).parents().find("#new_user_info input.password").val('');
		$(this).parents().find("#new_user_info input.emailText").val('');
		$("#new_user_info").show();
		$("#groupType .typeWrapper .user_info:first-child").addClass("pointing_here");
	});
	
	/* Create User info */
	$(document).on("click", "#createUser", function() {
			$("#new_user_info").hide();
	});
	/* Update User info Div */	
	$(document).on("click","#users_info_wrapper .user_info",function(){
		$(this).parents().find('#new_user_info').hide();
		$(".user_info").removeAttr("style");
		$(".user_info").removeClass("pointing_here");
		$(".arrowPointingUp").remove();
		$(this).addClass('pointing_here').css(
	    {
	        "margin-bottom": $("#user_info_up").height() + 30
	    });
		$("#user_info_up").css(
		    {
		        top: $(this).position().top + $(this).height() + 10
		    }).show();
		$("<div class='arrowPointingUp'></div>").css(
		        {
		            left: $(this).position().left + $(this).width()/2 - 20
		 }).prependTo("#user_info_up");
		
		$("#groupType .typeWrapper .user_info:first-child").addClass("pointing_here");
//		$("#applicationType .typeWrapper").contents().empty();
	});
	
	$(document).on("click", "#updateUser", function(){ 
		$("#user_info_up").find(".typeWrapper .user_info").remove();
		$("#user_info_up").hide();
		$(".user_info").removeAttr("style");
		$(document).find('.pointing_here').removeClass("pointing_here");
	});
	
	/* Close Div*/
	$(document).on("click", '.close_user_info', function(){
		$('.user_information').hide();
		$(".user_info").removeAttr("style");
		$(document).find('.pointing_here').removeClass("pointing_here");
	});
	
	/* Add New User Group event */
	$(document).on("click",".addNewUserGroup", function() {
		
		$(this).parent().siblings().find('.pointing_here').removeClass('pointing_here');
		$('#groupTypeDivContainer').show();
//		var groupContent = '<div class="user_info groupTypeUp"><select class="filterchosen newUserGroupDropDown" data-placeholder="Select Group">'+roleList+'</select></div><div class="closeUpdatebuttons"><span class="closeAddGroupType"></span><span class="saveAddGroupType"></span></div>';
//		$(this).parent().siblings().append(groupContent);
		/* Filter Chosen */
		//$('.filterchosen').chosen();
// 		$(this).parents().find('#applicationType').find('.typeWrapper').contents().empty();
		//$(this).parent().siblings().animate({ scrollTop: $(this).parents("#groupType").find('.user_info .chosen-container').offset().top }, 1000);
	});
	
	/* Group Type----Cancel Click Event */
	$("#cancelAddToGrouplist").bind("click", function(e) {
		e.preventDefault();
		//alert("cancel adding Group clicked");
		$("#groupTypeDivContainer").hide();
	});
	
	/* Application Type ------ Add New Application Click Event */
	$(document).on("click",".addNewUserApplication", function() {
		$(this).parents().find('#applicationType').find('.addAppText').remove();
		$('#applicationTypeDivContainer').show();
		//var applicationContent = '<div class="user_info applicationTypeUp"><select class="filterchosen newUserApplicationDropDown" data-placeholder="Select Application">'+appList+'</select></div><div class="user_info"><div class="closeUpdatebuttons"><span class="closeAddApplicationType"></span><span class="saveAddApplicationType"></span></div></div>';
		//$("#applicationType .typeWrapper").append(applicationContent);
		/* Filter Chosen */
		//$('.filterchosen').chosen();
		//$('#applicationType .typeWrapper').animate({ scrollTop: $(this).parents("#applicationType").find(".user_info .chosen-container").offset().top }, 1000);
	});
	
	/* Application Type----Cancel Click Event */
	$("#cancelAddToApplicationList").bind("click", function(e) {
		e.preventDefault();
		//alert("cancel Adding Appliaction clicked");
		$("#applicationTypeDivContainer").hide();
	});
	
	
	
	/* Group Type Save Event */
	$(document).on("click", ".saveAddGroupType", function() {
 		var selectedGroup = $(this).parent().parent().find('.groupTypeUp span').text();
		var newGroupDiv = '<div class="user_info pointing_here"><div class="gName"><span>'+selectedGroup+'</span></div></div>';
		$(this).parents().find("#applicationType").find(".typeWrapper").contents().remove();
		$(this).parents().find("#applicationType").find(".typeWrapper").html("<div class='addAppText'>Add Application.</div>");
		$(this).parent().parent().find('.user_info:last').before(newGroupDiv)
		$(this).parent().parent().find('.user_info:last').remove();
		$(this).parent().remove();
	});
	
	/* Group Type Close Event */
	$(document).on("click",".closeAddGroupType", function() {
		$('.closeUpdatebuttons').remove();
		$('.groupTypeUp').remove();
	});
	
	$(document).on("click", "#groupType .user_info", function() {
		$("#groupType .typeWrapper").find('.pointing_here').removeClass("pointing_here");
		$(this).addClass("pointing_here");
	});
	
	
	
	/* Application Type Close/Update Event */
    $(document).on("click",".closeAddApplicationType, .saveAddApplicationType", function() {
            var selectedApplication =  $(this).parent().parent().parent().find('.applicationTypeUp span').text();
            var newApplicationDiv = '<div class="user_info floatleft pointing_here"><div class="aName"><span>'+selectedApplication+'</span></div></div>';
            $(this).parent().parent().parent().find('.user_info:eq(-2)').before(newApplicationDiv);
            $(this).parent().parent().parent().find('.user_info:eq(-2)').remove();
            $(this).parent().parent().parent().find('.user_info:last').remove();
    });
});
/* END Document Ready Function*/
