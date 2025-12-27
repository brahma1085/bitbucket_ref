

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="bean"
	uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="java.util.Map"%>
<%--
  User: Amzad
  Date: Feb 16, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
<title>List Of Holidays</title>
<center>
	<h2 class="h2">List Of Holidays</h2>
</center>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />


<!--<style type="text/css">
          body{
              font-size:10px;
              font-family:serif;
              font-style:oblique;
              font-weight:bold;
              background:beige;
          }
          table{
               background:transperent; 
          }
          
    </style>
    -->
<link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<script>
	function confirmsubmit() {

	}

	function setFlag(flagValue) {
		document.forms[0].flag.value = flagValue;
		document.forms[0].submit();

	}

	function validate() {
		var val = document.forms[0].validations.value;
		if (val != 0 || val != "") {
			alert(val);
		}

		else {
			return false;
		}
	};
</script>
</head>
<body class="Mainbody" onload="return validate()">
	<%!Map user_role;
	String access;%>
	<%
		String accesspageId=(String)request.getAttribute("accesspageId");
	%>
	<%
		user_role=(Map)request.getAttribute("user_role");
	%>


	<%
		if(user_role!=null&&accesspageId!=null)
	%>
	<%
		{
	%>
	<%
		if(user_role.get(accesspageId)!=null)
	%>
	<%
		{
	%>
	<%
		access=(String)user_role.get(accesspageId);
	%>
	<%
		System.out.println("access-----In SBOpening------>"+access);
	%>
	<%
		}
	%>


	<%
		}
	%>


	<%
		if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1')
	%>
	<%
		{
	%>

	<html:form action="/Administrator/HolidayMaster?pageId=10007"
		focus="<%=(String)request.getAttribute("FocusTo")%>">
		<html:hidden property="defaultSignIndex" value="0" />
		<html:hidden property="validations" />
		<html:hidden property="flag" />
		<html:hidden property="pageId" value="3005" />
		<%
			Object[][] holidayArray=(Object[][])request.getAttribute("HolidayObj");
		%>
		<%
			String addRow=(String)request.getAttribute("AddRow");
		%>


		<table name="button_table" align="center">
			<html:button property="insert" onclick="setFlag('addrow'); "
				styleClass="ButtonBackgroundImage">
				<bean:message key="label.add.row"></bean:message>
			</html:button>
			<html:button property="view" onclick="setFlag('view'); "
				styleClass="ButtonBackgroundImage">
				<bean:message key="gl.label.view"></bean:message>
			</html:button>
			<%
				if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){
			%>
			<html:button property="sub" onclick="setFlag('submit')"
				styleClass="ButtonBackgroundImage">
				<bean:message key="label.submit"></bean:message>
			</html:button>
			<%
				}else{
			%>
			<html:button property="sub" onclick="setFlag('submit')"
				styleClass="ButtonBackgroundImage" disabled="true">
				<bean:message key="label.submit"></bean:message>
			</html:button>
			<%
				}
			%>
			<%
				if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){
			%>
			<html:button property="del" onclick="setFlag('delete')"
				styleClass="ButtonBackgroundImage">
				<bean:message key="gl.label.delete"></bean:message>
			</html:button>
			<%
				} else {
			%>
			<html:button property="del" onclick="setFlag('delete')"
				styleClass="ButtonBackgroundImage" disabled="true">
				<bean:message key="gl.label.delete"></bean:message>
			</html:button>
			<%
				}
			%>
			<html:reset styleClass="ButtonBackgroundImage">
				<bean:message key="gl.label.clear"></bean:message>
			</html:reset>

		</table>

		<div id="holidaymaster"
			style="display: block; overflow: scroll; width: 700px; height: 250px">
			<table border="1">
				<tr>
					<td
						style="background-color: #fffccc; font-family: bold; color: yellow"">
						<b>Date</b>
					</td>
					<td
						style="background-color: #fffccc; font-family: bold; color: yellow"">
						<b>Day</b>
					</td>
					<td
						style="background-color: #fffccc; font-family: bold; color: yellow"">
						<b>Reason</b>
					</td>
					<td
						style="background-color: #fffccc; font-family: bold; color: yellow"">
						<b>Br_Name</b>
					</td>
					<td
						style="background-color: #fffccc; font-family: bold; color: yellow"">
						<b>Terminal</b>
					</td>
					<td
						style="background-color: #fffccc; font-family: bold; color: yellow"">
						<b>User</b>
					</td>
					<td
						style="background-color: #fffccc; font-family: bold; color: yellow"">
						<b>Date & Time</b>
					</td>
					<td
						style="background-color: #fffccc; font-family: bold; color: yellow"">
						<b>Select</b>
					</td>
				</tr>
				<%!int count = 0;%>
				<%
					int z=1;
				%>

				<%
					if(holidayArray!=null)
				%>
				<%
					{
				%>
				<%
					for(int j=0;j<holidayArray.length;j++)
				%>
				<%
					{
				%>
				<%
					if(count!=7)
				%>
				<%
					{
				%>

				<tr bordercolor="none">
					<%
						for(int k=0;k<7;k++)
					%>
					<%
						{
					%>
					<%
						if(holidayArray[j][k]!=null){
					%>
					<td bordercolor="none"><input type="text" name="txt<%=z++%>"
						value="<%=holidayArray[j][k]%>"></td>

					<%
						}else
					%>
					<%
						count++;
					%>
					<%
						}
					%>
					<td bordercolor="none"><input type="checkbox" name="ckBox"
						value=<%=""+j%>></td>

				</tr>

				<%
					}
				%>
				<%
					z=1;
				%>
				<%
					}
				%>
				<%
					}
				%>
				<%
					int len=holidayArray.length;
				%>
				<%
					if(addRow!=null)
				%>
				<%
					{
				%>


				<tr>
					<td bordercolor="none"><input type="text" name="txt1">
					</td>
					<td bordercolor="none"><input type="text" name="txt2">
					</td>
					<td bordercolor="none"><input type="text" name="txt3">
					</td>
					<td bordercolor="none"><input type="text" name="txt4">
					</td>
					<td bordercolor="none"><input type="text" name="txt5">
					</td>
					<td bordercolor="none"><input type="text" name="txt6">
					</td>
					<td bordercolor="none"><input type="text" name="txt7">
					</td>
					<td bordercolor="none"><input type="checkbox" name="ckBox"
						value="<%=len%>"></td>

				</tr>
				<%
					}
				%>



			</table>
		</div>
	</html:form>
	<%
		}
	%>
	<%
		else
	%>
	<%
		{
	%>
	<font color="red"><bean:message key="label.accessdenied"></bean:message></font>
	<%
		}
	%>
</body>
</html>
