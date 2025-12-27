<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<html>
	<body bgcolor="grewf">
		<center>
			<h1>
				<u>Enter Your Account Number</u>
			</h1>
			<h3>
				<html:form action="/myaccount" method="get"><html:errors/>
					Account Number : <html:text value="0" property="accno" name="accno"></html:text>
					<br />
					<br />
					<html:submit>SUBMIT</html:submit>
				</html:form>
			</h3>
		</center>
	</body>
</html>