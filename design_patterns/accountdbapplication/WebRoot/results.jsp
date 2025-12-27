<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<html>
	<body bgcolor="wheat">
		<h1>
			<bean:write name="acc" property="accno" />
			<br />
			<bean:write name="acc" property="name" />
			<br />
			<bean:write name="acc" property="balance" />
		</h1>
	</body>
</html>