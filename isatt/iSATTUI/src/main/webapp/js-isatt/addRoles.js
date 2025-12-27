/* Document Ready */
$(document).ready(function(){
	
	/* Add New Role Div interaction */
	$(document).on("click", ".addNewRole", function() {
		$(this).parents().find('#role_info_up').hide();
		$("#noRoles").hide();
		$(".role_info").removeAttr("style");
		$("#new_role_info").show();
	});
	
	/* Create Role */
	$(document).on("click", "#createRole", function() {
			$("#new_role_info").hide();
	});
	/* Update Role Div */	
	$(document).on("click","#roles_info_wrapper .role_info",function(){
		$(this).parents().find('#new_role_info').hide();
		$(".role_info").removeAttr("style");
		$(".arrowPointingUp").remove();
		$(this).addClass('pointing_here').css(
	    {
	        "margin-bottom": $("#role_info_up").height() + 30
	    });
		$("#role_info_up").css(
		    {
		        top: $(this).position().top + $(this).height() + 35
		    }).show();
		$("<div class='arrowPointingUp'></div>").css(
		        {
		            left: $(this).position().left + $(this).width()/2 - 20
		 }).prependTo("#role_info_up");
	});
	
	$(document).on("click", "#updateRole", function(){
		$("#role_info_up").hide();
		$(".role_info").removeAttr("style");
	});
	
	/* Close Div*/
	$(document).on("click", '.close_role_info', function(){
		$('.role_information').hide();
		$(".role_info").removeAttr("style");
	});
	
	/* Close Div*/	
	$(document).on("click", '#close_info_up', function(){
		$('#role_info_up').hide();
		$(".role_info").removeAttr("style");
	});
	
	/* Clicking Close/X on the screen */
	$(document).on("click", ".close", function () {
		
	})
	
});
/* END Document Ready Function*/
