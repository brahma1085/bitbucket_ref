<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>iTAP Login Page</title>
<style>
.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body bgcolor="#d3d3d3" onload='document.f.j_username.focus();'>

	<!-- <marquee direction="left" scrolldelay="50"> -->
		<%-- <center>
			<h3 style="color: #0000FF">Welcome to iTAP(integrated Test
				Automation Platform) Login with Username and Password
				(Authentication and Authorization with iTAP Database) !!!</h3>
		</center> --%>
	<!-- </marquee> -->

	<%-- <c:if test="${not empty error}">
		<div class="errorblock">
			Your login attempt was not successful, try again.<br /> Caused :
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if> --%>

	<center>
		<c:if test="${not empty error}">
			<div style="color: red">
				Please re-enter your password. The password you entered is
				incorrect. Please try again (make sure your caps lock is off).<br />
				Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
			</div>
		</c:if>
	<center>
		<div style="width: 800px; height: 200px; border: 2px solid grey;" align="center" style="vertical-align:bottom">
		<style> 
div
{
border:2px solid #a1a1a1;
padding:10px 40px; 
background:#dddddd;
width:300px;
border-radius:25px;
}
</style>
			<h3 style="color: #2F4F4F">Login to iTAP Admin Page</h3>
			<hr width=85% size="3">
			<form name='f' action="<c:url value='j_spring_security_check' />"
				method='POST'>
				<center>
				
					<table>
						<tr>
							<td><font color="#2F4F4F">Username</td>
							<td><input type='text' name='j_username' value=''></td>
                        </tr>
						<tr>
							<td><font color="#2F4F4F">Password</td>
							<td><input type='password' name='j_password' /></td>
						</tr>
						<tr>
							<td colspan='2'><input name="reset" type="reset" style="height: 200px; width: 100px" /></td>
							<td colspan='2'><input name="submit" type="submit" value="Login" style=" height: 200px; width: 100px " /></td>
						</tr>
					</table>
					</center>
			</form>
		</div>
		</center>
	</center>
	<center>
			<marquee><h3 style="color: #2F4F4F">Welcome to iTAP(integrated Test
				Automation Platform) Login with Username and Password
				(Authentication and Authorization with iTAP Database) !!!</h3></marquee>
		</center>
</body>
</html>