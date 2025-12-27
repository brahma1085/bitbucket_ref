<%@taglib prefix="html" uri="accountapplication"%>
<html>
	<body bgcolor="trrerfdf">
		<center>
			<html:form action="/account" method="get">
				<h1>
					<u>Enter Your Account Number</u>
				</h1>
				<br />
				<h3>
					Account Number :
					<html:text property="accno"></html:text>
					<br />
					<br />
					<html:submit>submit</html:submit>
				</h3>
			</html:form>
		</center>
	</body>
</html>