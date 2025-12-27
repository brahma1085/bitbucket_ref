$(document).ready(function () {
	$('#loginBtn').on('click', function () {
		$('.error').fadeIn(1000);
		$('label[for=user]').css('padding','20px 20px 20px 0px');
	});
});