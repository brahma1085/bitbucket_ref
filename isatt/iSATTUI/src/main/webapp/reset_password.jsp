<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html class="no-js" data-ng-app="iTap">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Forgot Password</title>
<meta name="description" content="" />
<meta name="viewport" content="width=device-width" />

<script src="js-isatt/vendor/jquery-1.9.0.min.js"></script>
<script src="js-isatt/vendor/jquery-ui.js"></script>
<script src="js-isatt/chosen.jquery.min.js"></script>
<script src="angular/angular.min.js"></script>

<script src="angular-dragdrop.js"></script>
<script src="angular-dragdrop.min.js"></script>

<script src="js/spagojs/sbisdk-all-production.js"></script>

<script src="controllersITAP.js"></script>
<script type="text/javascript" src="js-isatt/adminMain.js"></script>
<!-- <script type="text/javascript" src="js-isatt/main.js"></script> -->

<script src="js/spagojs/sbisdk-all-production.js"></script>

<script src="controllersITAP.js"></script>
 <script src="js-isatt/DateUtil.js"></script>
     <link rel="stylesheet" href="css-isatt/reset_password.css"/>
</head>
<%
String username = (String) request.getParameter("username") ;
%>
<body id="reset_password_page">
  		<div id="reset_password_wrapper">
        <header class="header">
          	<div id="logo"><img src="img-isatt/logo.png" alt="iTap" class="logoImg"></div><br />
          
          	<h2>Reset Your Password</h2>
        </header>
        
        <div class="inputFields" data-ng-controller="resetPasswordController">
        <form name="resetPasswordForm"> 
      
         <input type="hidden" name="username" data-ng-model="username" value="<%=username%>"/><br />
          <label for="new_password">New Password</label>
          <input type="password" name = "newpassword" data-ng-model="newpassword" required/><br />
          <div class="error"
					data-ng-show="resetPasswordForm.newpassword.$dirty && resetPasswordForm.newpassword.$invalid">
					<span class="error"
						data-ng-show="resetPasswordForm.newpassword.$error.required">
						Please enter new password </span>
				</div>	
          <label for="confirm_password">Confirm Password</label>
          <input type="password" name = "confirmpassword" data-ng-model="confirmpassword" required/>
          <div class="error"
					data-ng-show="resetPasswordForm.confirmpassword.$dirty && resetPasswordForm.confirmpassword.$invalid">
					<span class="error"
						data-ng-show="resetPasswordForm.confirmpassword.$error.required">
						Please enter confirm password </span>
				</div>	<br /><br /><br />
          <input type="submit" name="resetPassBtn" value="Reset Password" data-ng-disabled="resetPasswordForm.$invalid" 
					data-ng-click="resetPassword()" id="resetPassBtn"/>
          </form>
        </div>
        <footer class="footer">
          <div class="bottomNav">
            <p><span>An email will be sent confirming the change in password.</span></p>
          </div>
        </footer>
      </div>
  </body>
</html>