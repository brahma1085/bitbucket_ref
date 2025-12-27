/* Document Ready */
$(document).ready(function(){
	
	/* Add New Application Div interaction */
	$(document).on("click", ".addNewApplication", function() {
		$(this).parents().find('#application_info_up').hide();
		$("#noApplication").hide();
		$(".application_info").removeAttr("style");
		$("#new_application_info").show();
	});
	
	/* Create Application */
	$(document).on("click", "#createApplication", function() {
			$("#new_application_info").hide();
	});
	/* Update Application Div */	
	$(document).on("click","#applications_info_wrapper .application_info",function(){
		$(this).parents().find('#new_application_info').hide();
		$(".application_info").removeAttr("style");
		$(".arrowPointingUp").remove();
		$(this).addClass('pointing_here').css(
	    {
	        "margin-bottom": $("#application_info_up").height() + 30
	    });
		$("#application_info_up").css(
		    {
		        top: $(this).position().top + $(this).height() + 35
		    }).show();
		$("<div class='arrowPointingUp'></div>").css(
		        {
		            left: $(this).position().left + $(this).width()/2 - 20
		 }).prependTo("#application_info_up");
	});
	
	$(document).on("click", "#updateApplication", function(){
		$("#application_info_up").hide();
		$(".application_info").removeAttr("style");
	});
	
	/* Close Div*/
	$(document).on("click", '.close_application_info', function(){
		$('.application_information').hide();
		$(".application_info").removeAttr("style");
	});
	
	/* Clicking Close/X on the screen */
	$(document).on("click", ".close", function () {
		
	})
	
});
/* END Document Ready Function*/
