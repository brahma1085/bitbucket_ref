<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="masterObject.loans.LoanTransactionObject"%>
<%@page import="java.util.*"%>

<html>
<head>

<script type="text/javascript">


 function  set1(target)
 {
 if(document.getElementById("quarterdates").value=='Select')
	{
		alert('Please Select the Date!');
		document.getElementById("quarterdates").focus();
		return false;
		}else{
 	document.forms[0].forward.value=target;
 	document.forms[0].submit();
 }
 };



</script>

 </head>
 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
</style>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
 
<body>
 <%!
String access;
String  accesspageId;
 Map user_role;
%>
<%
  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");
System.out.println("11111111"+user_role);
System.out.println("222222222"+accesspageId);
if(user_role!=null&&accesspageId!=null){
System.out.println("11111111"+access);
			if(user_role.get(accesspageId)!=null){
			System.out.println("222222222"+access);
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}
			}
// Note:access=1111==>read,insert,delete,update
%> 


<%! String[] QuaterlyDates=null; 
	List objectdata=new ArrayList();
	Object[] obj2; %>
<% objectdata=(List)request.getAttribute("objectdata"); %>
<% String message=(String)request.getAttribute("msg");%>
<center>
<h2 class="h2">Loan Quarterly Interest Calculation/Report Generation</h2></center>
<%if(message!=null){ %>
<div id = "div1" class = "myLayersClass">
    
    <core:if test="<%=message!=null %>">
    <font color="red"><%=message %></font>
    <br><br><br><br>
    </core:if>
    </div>
<%} %>

   	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/QuarterlyInterestCalc?pageidentity.pageId=5048">
<html:hidden property="forward"/>
<table>
	<tr>
		<td><html:submit property="calc_submit" styleClass="ButtonBackgroundImage" onclick="set1('InterestCalculation')" >Interest Calculation</html:submit></td>
	</tr>
	<tr></tr>
	
	<% QuaterlyDates=(String[])request.getAttribute("QuaterlyDates"); %>
	<tr>
	<% if(QuaterlyDates!=null){ %>
		<td><html:select property="quarterdates">
			<html:option value="Select"></html:option>
				<%for(int i=0;i<QuaterlyDates.length;i++){ %>
			<html:option value="<%=QuaterlyDates[i]%>"><%=QuaterlyDates[i]%></html:option>
       			<%} %>
      </html:select></td>
      <% } else { %>
      <html:select property="quarterdates">
      <html:option value="Select"></html:option>
      </html:select>
      <% } %>
      
	</tr>
	<html:hidden property="button_value" value="error"/>
	<tr>
	</tr>
	<tr>
	
   	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
		<td><html:button property="view" styleClass="ButtonBackgroundImage" onfocus="set1('View')">View</html:button>
		<html:button property="file" value="File" styleClass="ButtonBackgroundImage" onfocus="set1('File')">File</html:button>
		<%}else{ %>
		<td><html:button property="view" styleClass="ButtonBackgroundImage" disabled="true">View</html:button>
		<html:button property="file" value="File" styleClass="ButtonBackgroundImage" disabled="true">File</html:button>
		<%} %>
		<!--<html:button onclick="window.print()" property ="printFile"  styleClass="ButtonBackgroundImage"><bean:message key="label.print"></bean:message> </html:button></td>
	--></tr>
	<tr>
	<td>	
		<%if(objectdata!=null){
			%>			
			<div  style="display:block;overflow:scroll;width:650px;height:260px;">						
			<table border="1">
      			<tr>
 					<th>Srl NO.</th>
 					<th>A/C Type</th>
 					<th>A/C NO</th>
 					<th>Name</th>
 					<th>Interest Amt</th>
 					<th>Sanc Amt</th>
				</tr>
				<%
			for(int i=0;i<objectdata.size();i++){
			%>
			<tr>
			<% obj2=(Object[])objectdata.get(i); %>
			
			<%
				for(int j=0;j<obj2.length;j++){			
			%>				
				<td><font color="red"><%=obj2[j]%></font></td>
			<%}%>
			</tr>
			<%} %>				
		</table>		
		 </div>
		
	      <%} %>
	   </td>   
	 </tr>      		
</table>

<!-- Desigened By RaghunathaReddy.P -->

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
</body>
</html>