<%@ page
	import="javax.servlet.http.*,java.sql.*,javax.servlet.*,com.eris.*"%>
<jsp:useBean id="add" class="com.eris.AddJobBean" scope="session" />
<jsp:setProperty name="add" property="*" />

<%
	DBConnection db = new DBConnection();
	String i = request.getParameter("jobid");
	ResultSet rs = db
			.executeQuery("select * from joborder where jobid=' " + i
					+ " ' ");
	if (rs.next()) {
		System.out.println(rs.next());

		response.sendRedirect("update.html");
		System.out.println("in the senddirect");
	} else {
		System.out.println("in the add");
		add.addJob();
		//response.sendRedirect("admin.htm");
	}
%>

<jsp:include page="admin.htm" />