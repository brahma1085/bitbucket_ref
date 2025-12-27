
<%@page import="com.eris.utils.ResourceHelper"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%><html>
	<body bgcolor="#bhaerty">
		<strong style="background-color: rgb(16, 143, 248);"></strong>
		<h2 align="center" style="background-color: rgb(255, 255, 255);">
			<div>
				<font color="#800000" face="Constantia"><u><strong>Enterprise
							Resource Information System </strong> </u> </font>
			</div>
		</h2>
		<p align="center">
			EMPLOYEE SEARCH
		</p>

		<%
			Connection db = ResourceHelper.getConnection();
			Statement statement = db.createStatement();
			ResultSet rs = statement
					.executeQuery("select employeeid  from employeedetails ");
		%>
		<p align=center>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Employee
			ID
			<select size="1" name="empid">
				<br>
				<option>
					Select Employee ID
				</option>
		</p>
		<%
			while (rs.next()) {
		%>
		<br>
		<br>

		<option>
			<%=rs.getInt(1)%>
		</option>
		</p>
		<%
			}
		%>
		</select>
		<input type="submit" value="VIEW" name="B3">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<br>
		<br>
		<table border="1" cellspacing="2" style="border-collapse: collapse"
			bordercolor="#brown" width="100%" id="AutoNumber1">
			<tr>
				<td width="16%">
					Employee ID
				</td>
				<td width="16%">
					<p align="center">
						Name
				</td>
				<td width="17%">
					<p align="center">
						Role
				</td>
				<td width="17%">
					<p align="center">
						Phone
				</td>
				<td width="17%">
					<p align="center">
						e-mail
				</td>
				<td width="17%">
					&nbsp;&nbsp;&nbsp; Client ID
				</td>
			</tr>
			<%
				String s = request.getParameter("empid");
				if (s != null) {
					ResultSet rs1 = statement.executeQuery("select employeeid,firstname,role,mobileno,email,clientid from employeedetails where employeeid=' "
									+ s + " ' ");
					if (rs1.next()) {
			%>
			<tr>
				<td width="16%"><%=request.getParameter("empid")%>&nbsp;
				</td>
				<td width="16%"><%=rs1.getString(2)%>&nbsp;
				</td>
				<td width="17%"><%=rs1.getString(3)%>&nbsp;
				</td>
				<td width="17%"><%=rs1.getString(4)%>&nbsp;
				</td>
				<td width="17%"><%=rs1.getString(5)%>&nbsp;
				</td>
				<td width="17%"><%=rs1.getString(6)%>&nbsp;
				</td>
			</tr>
			<%
				}
				}
			%>
		</table>

		<p align="center">
			<a href="recruit.htm">BACK</a>
		</p>
		</form>
	</body>
</html>