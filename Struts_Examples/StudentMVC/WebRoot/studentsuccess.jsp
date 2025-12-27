<%@page import="edu.forms.StudentForm"%>
<html>
	<body>
		<center>
			<h1>
				<u>Student Details </u>
				<br />
				<%
					StudentForm form = (StudentForm) request
							.getAttribute("StudentForm");
				%>
				Student No :
				<%
					out.println(form.getStudentNo());
				%>
				<br />
				Student name :
				<%
					out.println(form.getStudentName());
				%>
			</h1>
		</center>
	</body>
</html>