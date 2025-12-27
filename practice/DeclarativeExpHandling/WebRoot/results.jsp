<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<h1>
	<bean:write name="account" property="accno" />
	<br />
	<bean:write name="account" property="accname" />
	<br />
	<bean:write name="account" property="bal" />
</h1>