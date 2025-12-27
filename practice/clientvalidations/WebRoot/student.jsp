<html>
	<head>
		<title>student</title>
		<script type="text/javascript">
	function validate() {
		if (document.getElementById("username").value == null
				|| document.getElementById("username").value == ""
				|| document.getElementById("password").value == null
				|| document.getElementById("password").value == "")
			alert('please enter the values');
		else
			document.studentform.submit();
	}
</script>
	</head>
	<body bgcolor="grewf">
		<center>
			<form action="StudentServlet" name="studentform" method="post">
				<h1>
					<u> Enter Student Details</u>
				</h1>
				<h3>
					Username :
					<input type="text" name="username" />
					<br />
					Password :
					<input type="password" name="password" />
					<br />
				</h3>
				<input type="submit" value="submit" onclick="validate();" />
			</form>
		</center>
	</body>
</html>