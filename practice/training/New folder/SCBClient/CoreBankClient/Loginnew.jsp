<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="masterObject.general.BranchObject"%>
<%@page import="com.scb.designPatterns.AdministratorDelegate"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Login</title>

<link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	function validate() {
		var val = document.forms[0].validations.value;
		if (val != 0 || val != "") {
			alert(val);
		}

		else {
			return false;
		}
	};

	function clear() {
		document.getElementById("uid").value = "";
		document.getElementById("pass").value = "";
		document.getElementById("tml").value = "";
	}

	function MakeArrayday(size) {
		this.length = size;
		for (var i = 1; i <= size; i++) {
			this[i] = "";
		}
		return this;
	}
	function MakeArraymonth(size) {
		this.length = size;
		for (var i = 1; i <= size; i++) {
			this[i] = "";
		}
		return this;
	}
	function funClock() {
		if (!document.layers && !document.all)
			return;
		var runTime = new Date();
		var hours = runTime.getHours();
		var minutes = runTime.getMinutes();
		var seconds = runTime.getSeconds();
		var dn = "AM";
		if (hours >= 12) {
			dn = "PM";
			hours = hours - 12;
		}
		if (hours == 0) {
			hours = 12;
		}
		if (minutes <= 9) {
			minutes = "0" + minutes;
		}
		if (seconds <= 9) {
			seconds = "0" + seconds;
		}
		movingtime = "<b>" + hours + ":" + minutes + ":" + seconds + " " + dn
				+ "</b>";
		if (document.layers) {
			document.layers.clock.document.write(movingtime);
			document.layers.clock.document.close();
		} else if (document.all) {
			clock.innerHTML = movingtime;
		}
		setTimeout("funClock()", 1000)
	}
	window.onload = funClock;

	function give_alert() {
		if (document.forms[0].alert.value != "") {
			alert("You Are Logging into Another Branch");
			return false;
		} else {
			return true;
		}
	}

	function checkValidation() {

		if (document.forms[0].userName.value == "") {
			alert("UserName should not be Empty");
			document.forms[0].userName.focus();
		}
	}
	function checkValidation1() {

		if (document.forms[0].userPwd.value == "") {
			alert("Password should not be Empty");
			document.forms[0].userPwd.foucs();
		}
	}
	function checkValidation2() {

		if (document.forms[0].userTml.value == "") {
			alert("Enter Terminal Number");
			document.forms[0].userTml.focus();
		}
	}

	function makedis() {

		window.history.back(1);
		window.history.foward(1);
	}

	function loginnew() {
		window.history.back(1);
		window.history.forward(1);
		window.history.go(1);
	}
</script>

</head>
<body class="Mainbody" onload="return validate()">
	<%!BranchObject[] branchObjects = null;
	AdministratorDelegate administratorDelegate = null;
	String msg = "User Login";
	Date now, now1;
	String strDate, strTime;
	String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July",
			"Aug", "Sep", "Oct", "Nov", "Dec" };
	DateFormat fmt, fmt1;%>
	<%
		msg = (String) request.getAttribute("errmsg");
		now = new Date();
		now1 = new Date();
		fmt = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
		fmt1 = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.UK);
		strDate = fmt.format(now);
		strTime = fmt1.format(now1);
		System.out.println("====date===" + now);
		System.out.println("====date===" + now.getDate());
		System.out.println("====date===" + months[now.getMonth()]);
		System.out.println("====date===" + now.getYear());
		administratorDelegate = new AdministratorDelegate();
		branchObjects = administratorDelegate.getBranchMaster();
	%>




	<table class="txtTableHeader" width="120%">
		<thead class="txtTableHeader" align="right" valign="bottom">in-Softech
			Solutions Pvt Ltd.
		</thead>
		<tr>
			<td align="right"><span id=clock style="position: relative;"></span>
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>


	</table>
	<center>
		<br> <br>
		<html:form action="loginnew" method="POST" focus="userName">
			<html:hidden property="validations"></html:hidden>
			<br>


			<br>
			<br>
			<%
				if (msg != null) {
			%>
			<font color="red"><b><%=msg%></b></font>
			<%
				}
			%>

			<br>
			<br>
			<html:hidden property="userTml" value="CA99" />
			<table border="1" class="txtTable">


				<tr bgcolor="LIGHT RED">
					<td colspan="2" color="white">Login Screen</td>
				</tr>
				<tr>
					<td></td>
					<td><b><span id=clock style="position: relative;"></span></b></td>
				</tr>

				<tr>
					<td>UserName</td>
					<td><html:text styleId="uid" property="userName"
							style="font-family:bold;color:blue;"></html:text></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><html:password styleId="pass" property="userPwd"
							style="font-family:bold;color:blue;"></html:password></td>
				</tr>

				<tr>
					<td colspan="2" align="center"><html:submit value="Login"
							styleClass="ButtonBackgroundImage" onfocus="return give_alert()"
							onclick="loginnew()"></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</table>
			<html:hidden property="pageId" />
			<html:hidden property="alert" />
		</html:form>
	</center>
	<br>
	<br>
	<!--<table class="loginTableFooter" width="120%">
  	<thead class="loginTableFooter"></thead>
  		<tr><td class="loginTableFooter"></td></tr>
  	<table>
-->
</body>
</html>