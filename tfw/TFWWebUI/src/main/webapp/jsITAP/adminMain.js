$(document).ready(function(){
	$("#settings").hide();
	//alert("adminMain");
	//adminSettings();
});

$(document).on("click","#settings",function(e) {
	//alert("New Settings");
	window.location = "adminlogin.html";
});

$(document).on("click","#logout",function(e) {
		$.ajax({
	        type: "GET",
	        url:"views/logout",
	        async:false,
	        headers : {
				'Content-Type' : 'application/json'
			},
	        success: function (data) {
						location.href = "http://localhost:9082/TFWWebUI/";
	       	}
	      });
	});

function adminSettings() {
	if(localStorage.getItem("isPrivilage") != undefined && localStorage.getItem("isPrivilage") != "" && localStorage.getItem("isPrivilage") != null){
		//alert("adminSettings()");
		var isprev = localStorage.getItem("isPrivilage");
		var queryString = location.search.substring(1);
		var keyValues = queryString.split('=');
		var encryptedRole = keyValues[1].split('&');
		
		//alert("Previous"+isprev);
		
		if(isprev == encryptedRole[0]) {
			//alert("enjoy maddi");
			var queryString2 = location.search.substring(2);
	  		var keyValues2 = queryString.split('loggedInUser=');
	  		
	  		var guestUser = keyValues2[1];
			var guest = guestUser.charAt(0).toUpperCase() + guestUser.slice(1);
			//alert(guest);
	  		
	  		$('.floatright').find('li').find('#userName').html("Welcome,"+" "+guest+" "+"!" );
		} else {
		    //alert("go to hell");
		    localStorage.clear();
		    window.location = "http://localhost:9082/TFWWebUI/";
//		    window.location = "http://localhost:8180/UserManagement-0.0.1/login.html";
		}
		
		if(encryptedRole[0]=="IADfk0faxJlVxQ8TRh0qyw") {
			//alert("Admin Login");
			$("#settings").show();
		} else{
		//alert('Engineer Login');
		setTimeout(function() {
			$('.ody').hide();
			},1000);
		
		}
		
	} else{
		var queryString = location.search.substring(1);
		var keyValues = queryString.split('=');
		var encryptedRole = keyValues[1].split('&');
	    var queryString2 = location.search.substring(2);
	    var keyValues2 = queryString.split('loggedInUser=');
	    
	    //alert(keyValues2);
	    //alert("Welcome"+" "+keyValues2[1]);
		//alert("Hai User"+queryString2);
		
		var guestUser = keyValues2[1];
		var guest = guestUser.charAt(0).toUpperCase() + guestUser.slice(1);
		//alert(guest);
		
		localStorage.setItem("isPrivilage",encryptedRole[0]);
		$('.floatright').find('li').find('#userName').html("Welcome,"+" "+guest+" "+"!" );
		if(encryptedRole[0]=="IADfk0faxJlVxQ8TRh0qyw") {
			//alert("Admin Settings");
			$("#settings").show();
		} else{
			//alert('Else Engineer Login');
			setTimeout(function() {
				$('.ody').hide();
			},1000);
		}
	}
}