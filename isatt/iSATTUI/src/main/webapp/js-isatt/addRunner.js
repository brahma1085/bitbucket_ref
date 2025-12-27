/* Document Ready */
$(document).ready(function(){
	
	/* Add New Runner Div interaction */
	$(document).on("click", ".addNewRunner", function() {
		$(this).parents().find('#runner_info_up').hide();
		$("#noRunner").hide();
		$("#runner_info_up").hide();
		$(".runner_info").removeAttr("style");
		$("#new_runner_info").show();
	});
	
	/* Create Runner */
	$(document).on("click", "#createRunner", function() {
			$("#new_runner_info").hide();
	});
	/* Update Runner Div */	
	$(document).on("click","#runners_info_wrapper .runner_info",function(){
		$(this).parents().find('#new_runner_info').hide();
		$(".runner_info").removeAttr("style");
		$(".arrowPointingUp").remove();
		$(this).addClass('pointing_here').css(
	    {
	        "margin-bottom": $("#runner_info_up").height() + 30
	    });
		$("#runner_info_up").css(
		    {
		        top: $(this).position().top + $(this).height() + 35
		    }).show();
		$("<div class='arrowPointingUp'></div>").css(
		        {
		            left: $(this).position().left + $(this).width()/2 - 20
		 }).prependTo("#runner_info_up");
	});
	
	$(document).on("click", "#updateRunner", function(){
		$("#runner_info_up").hide();
		$(".runner_info").removeAttr("style");
	});
	
	/* Close Div*/
	$(document).on("click", '.close_runner_info', function(){
		$('.runner_information').hide();
		$(".runner_info").removeAttr("style");
	});
	
	/* Clicking Close/X on the screen */
	$(document).on("click", ".close", function () {
		
	})
	
});
/* END Document Ready Function*/
