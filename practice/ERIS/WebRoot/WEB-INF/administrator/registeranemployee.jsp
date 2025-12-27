<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<body bgcolor="#52d017">
		<strong style="background-color: rgb(16, 143, 248);"></strong>
		<h2 align="center" style="background-color: rgb(255, 255, 255);">
			<div>
				<font color="#35bc1d"><u><strong><font
							face="Constantia">Enterprise Resource Information System </font>
					</strong> </u> </font>
			</div>
		</h2>
		<form>
			<h3 align="center" style="background-color: rgb(255, 255, 255);">
				<font face="Constantia" color="#35bc1d"><u><b>EMPLOYEE
							REGISTRATION</b> </u> </font>
			</h3>
			<p align="center">
				<font size="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					EMPLOYEE ID </font>
				<input type="text" name="employeeid">
				<span style="font-size: 9pt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</p>
			<table width="75%" border="0" align="center">
				<tr>
					<td colspan="2">
						<div align="center">
							<font face="Constantia" size="4"><b>PERSONAL DETAILS</b> </font>
						</div>
					</td>
					<td colspan="2">
						<div align="center">
							<font face="Constantia" size="4"><b>ADDRESS DETAILS</b> </font>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<font face="Constantia" size="4">First Name</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="firstname"> </font>
					</td>
					<td>
						<font face="Constantia" size="4">Address</font>
					</td>
					<td>
						<font face="Constantia" size="4"><textarea name="address"
								wrap="VIRTUAL"></textarea> </font>
					</td>
				</tr>
				<tr>
					<td>
						<font face="Constantia" size="4">LastName</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="lastname"> </font>
					</td>
					<td>
						<font face="Constantia" size="4">City</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="city"> </font>
					</td>
				</tr>
				<tr>
					<td>
						<font face="Constantia" size="4">Initial</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="initial"> </font>
					</td>
					<td>
						<font face="Constantia" size="4">Zip</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="zip"> </font>
					</td>
				</tr>
				<tr>
					<td>
						<font face="Constantia" size="4">Date of Dirth</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="dateofbirth"> </font>
					</td>
					<td>
						<font face="Constantia" size="4">State</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="state"> </font>
					</td>
				</tr>
				<tr>
					<td>
						<font face="Constantia" size="4">Martial Status</font>
					</td>
					<td>
						<font face="Constantia" size="4"><select
								name="maritalstatus">
								<option>
									SINGLE
								</option>
								<option>
									MARRIED
								</option>
							</select> </font>
					</td>
					<td>
						<font face="Constantia" size="4">Country</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="country"> </font>
					</td>
				</tr>
				<tr>
					<td>
						<font face="Constantia" size="4">Gender</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="radio"
								name="gender" value="radiobutton" checked> M <input
								type="radio" name="gender" value="radiobutton"> F </font>
					</td>
					<td>
						<font face="Constantia" size="4">Day Phone</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="dayphone"> </font>
					</td>
				</tr>
				<tr>
					<td>
						<font face="Constantia" size="4">Destination</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="destination"> </font>
					</td>
					<td>
						<font face="Constantia" size="4">Ext</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="ext"> </font>
					</td>
				</tr>
				<tr>
					<td>
						<font face="Constantia" size="4">Role</font>
					</td>
					<td>
						<font face="Constantia" size="4"><select name="role">
								<option>
									RECRUITER
								</option>
								<option>
									MARKETING EXECUTIVE
								</option>
							</select> </font>
					</td>
					<td>
						<font face="Constantia" size="4">Evening Phone</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="eveningphone"> </font>
					</td>
				</tr>
				<tr>
					<td>
						<font face="Constantia" size="4">Password</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="password"> </font>
					</td>
					<td>
						<font face="Constantia" size="4">Mobile Phone</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="mobilephone"> </font>
					</td>
				</tr>
				<tr>
					<td>
						<font face="Constantia" size="4">Client ID</font>
					</td>
					<td>
						<font face="Constantia" size="4"><select name="clientid">
							</select> </font>
					</td>
					<td>
						<font face="Constantia" size="4">E-Mail</font>
					</td>
					<td>
						<font face="Constantia" size="4"><input type="text"
								name="email"> </font>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="center">
							<input type="submit" name="Submit" value="save">
						</div>
					</td>
				</tr>
			</table>
			<p align="center">
				&nbsp;
			</p>
		</form>
		<p align="center">
			<a href="">HELP</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="">BACK</a>
		</p>
	</body>
</html>