<html>
	<body bgcolor="wheat">
		<script Language="JavaScript">
<!--
function signup_Validator(theForm)
{
if (theForm.userid.value == "")
{
alert("You must enter an alias.");
theForm.userid.focus();
return (false);
}
if (theForm.userid.value.length < 3)
{
alert("Please enter at least 3 characters in the \"Alias\" field.");
theForm.userid.focus();
return (false);
}
var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
var checkStr = theForm.userid.value;
var allValid = true;
for (i = 0;  i < checkStr.length;  i++)
{
ch = checkStr.charAt(i);
for (j = 0;  j < checkOK.length;  j++)
if (ch == checkOK.charAt(j))
break;
if (j == checkOK.length)
{
allValid = false;
break;
}
}
if (!allValid)
{
alert("Please enter only letter and numeric characters in the \"Alias\" field.");
theForm.userid.focus();
return (false);
}
if (theForm.password.value.length < 5)
{
alert("Please enter at least 5 characters in the \"Password\" field.");
theForm.password.focus();
return (false);
}
}--></script>
		<form method="post" name="signup" action="LoginServlet">
			<h2 align="center">
				<u><strong><font face="Constantia">Enter Your
							Details</font> </strong> </u>
			</h2>
			<div align="center">
				<u><strong></strong> </u><strong><font size="4"><font
						face="Constantia">User ID : </font> </font> </strong>
				<input type="text" maxlength="20" name="userid">
				<br>
				<br>
				<strong><font size="4"><font face="Constantia">Password
							: </font> </font> </strong>
				<input type="password" maxlength="20" name="password">
				<br>
				<br>
				<strong><font size="4"><font face="Constantia">Previlization
							: </font> </font> </strong>
				<select name="privilize">
					<option>
						administrator
					</option>
					<option>
						recruitor
					</option>
					<option>
						marketing
					</option>
				</select>
				<br>
				<br>
				<input type="submit" value="sign up" name="signup"
					onclick="signup_Validator(theForm)">
				<br>
			</div>
		</form>
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; &nbsp;
		<a href="index.jsp"><font color="green" size="4">back</font>
		</a>
	</body>
</html>