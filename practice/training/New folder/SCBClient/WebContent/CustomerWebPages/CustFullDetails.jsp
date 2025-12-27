<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="masterObject.general.ModuleObject"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>

<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function change()
{
   if(document.forms[0].acc_option.value=="Select")
   {
     document.getElementById("ac_typediv").style.display='none';
     document.getElementById("ac_nodiv").style.display='none';
   }
   else if(document.forms[0].acc_option.value=="acno")
   {
     document.getElementById("ac_typediv").style.display='block';
     document.getElementById("ac_nodiv").style.display='block';
   }
   else if(document.forms[0].acc_option.value=="custid")
   {
     document.getElementById("ac_typediv").style.display='none';
     document.getElementById("ac_nodiv").style.display='block';
   }
   else if(document.forms[0].acc_option.value=="Ename")
   {
     document.getElementById("ac_typediv").style.display='none';
     document.getElementById("ac_nodiv").style.display='none';
   }
   
}
function show()
{
change();
}

</script>
</head>
<body onload="show()">
<%!
ModuleObject[] array_module,module_object;
String jsppath,matdate,pagenew,Accountnotfound;

%>
<%
 array_module=(ModuleObject[])request.getAttribute("modabvr");
if(array_module!=null){
	System.out.println("Module code is not null");
}

String nstr=(String)request.getAttribute("flag");

%>
<html:form action="/CustomerWebPages/CustFullDet?pageId=1009">

<table>
<tr><td>
<html:select property="acc_option" onchange="change()">
<html:option value="Select">Select</html:option>
<html:option value="acno">A/C-No</html:option>
<html:option value="custid">Cust.ID</html:option>
<html:option value="Ename">E.Name</html:option>
</html:select>
</td>
<td><div id="ac_typediv" style="display:none;">
<html:select property = "ac_type" styleClass="formTextFieldWithoutTransparent">
<html:option value="Select">Select</html:option>			    	
			    			
			    	<%if(array_module!=null){ %>
			    	
			    	<% for(int i=0;i<array_module.length;i++){%>
			        <html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%}}%>	
			    	 </html:select>
</div></td>
<td>

<td>
<div id="ac_nodiv" style="dispaly:none;">
<html:text property="ac_no" size="3" onblur="submit()"></html:text>
</div>
</td>

<td>&nbsp&nbsp&nbsp&nbsp</td>
<td>
<html:textarea property="custinfo"></html:textarea>
</td>


<td>&nbsp&nbsp&nbsp&nbsp</td>
<td>
<html:select property="ac_status" styleClass="formTextFieldWithoutTransparent">
<html:option value="select">Select</html:option>
<html:option value="allacounts">All-A/Cs</html:option>
<html:option value="openacs">Open-A/Cs</html:option>
<html:option value="closedacs">Closed-A/Cs</html:option>
</html:select>
</td>
</tr>
</table>
<table>
<tr>&nbsp&nbsp&nbsp</tr>
<tr>
<td>
<div style="overflow:scroll;height:100;width:200;" >
<table >
<tr>
<th>Category</th><th>Amount</th>
</tr>
<tr>

</tr>
</table>
</div>
</td>
<td>
<div style="overflow:scroll;height:100;width:200;" >
<table >
<tr>
<th>Category</th><th>Balance</th>
</tr>
<tr>

</tr>
</table>
</div>
</td>

</tr>

</table>
<table>
<tr>
<td>
<div style="overflow:scroll;height:100;width:400;">
<table >
<tr>
<th>A/C Type</th><th>A/C No</th><th>Balance</th><th>OpenDate</th><th>CloseDate</th>
</tr>
<tr>

</tr>
</table>
</div>
</td>
<td>
<table align="right"  valign="top" width="10%">
<%if( nstr!=null ){%>
    <tr> 
    <td>
	<% System.out.println("At 1086 ApplDataEntry.jsp===============================================nstr  >"+nstr);
    pagenew=nstr;%>
	   <jsp:include page="<%=pagenew %>"></jsp:include>
	   
			   <%System.out.println("After include------>"+pagenew);%>
    </td>
    </tr>
      
		 
				 
				 
	<%} %>
        
  
 </table>



</td>
</tr>
</table>

</html:form>
</body>
</html>