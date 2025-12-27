<%@ taglib prefix="bean"
	uri="http://jakarta.apache.org/struts/tags-bean"%>
<h1>
	<bean:write name="account" property="accno" />
	<bean:write name="account" property="accname" />
	<bean:write name="account" property="bal" />
</h1>