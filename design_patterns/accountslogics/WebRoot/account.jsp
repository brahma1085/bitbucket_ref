<%@taglib prefix="html" uri="htmltags"%>
<html>
	<body bgcolor="grewf">
		<center>
			<h1>
				<u>Account Details Retrieval Screen</u>
			</h1>
			<html:form action="/account">
				<h3>
					Balance :
					<html:text property="balance"></html:text>
					<br />
					<br />
					<html:submit>Get Details</html:submit>
				</h3>
			</html:form>
		</center>
	</body>
</html>