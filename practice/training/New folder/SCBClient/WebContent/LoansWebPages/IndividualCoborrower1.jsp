<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="masterObject.general.AccountObject"%>
<%@page import="masterObject.share.ShareMasterObject"%>
<%@page import="masterObject.general.ModuleObject" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">

      function set(){
        
        alert(document.forms[1].forward.value);
       if(document.forms[1].forward.value="Add")
       {
         document.getElementById("blok").style.display='block'
         return false;
        }
        
      };
      function only_numbers() {
	
	var cha=event.keyCode;
	if(cha>47 && cha<57 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;
function func_clear()
{

    var v = document.forms[0].elements;
    for(var i=0;i<=v.length;i++)
    {
       if(v[i].type =="text")
       {
          v[i].value = "";

        }
     }
 
};



</script>
</head>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
</style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
<body >

<%!ModuleObject[] shobj;%>
<% shobj = (ModuleObject[])request.getAttribute("ShareModule"); %>
<%!AccountObject ShareAccountObject[]; %>
<%ShareAccountObject = (AccountObject[])request.getAttribute("ShareAccountObject");%>
<%!String button; %>
<%button=(String)request.getAttribute("CoButton"); %>

<html:form action="/Loans/LoanApplicationDE?pageId=5001">

 
<%System.out.println("ShareDetails==============>"+ShareAccountObject);%>

<% System.out.print("-----inside the CoBorrowers Page-----");  %>
<%if(ShareAccountObject[0]!=null)
	{
		System.out.println("share value==============>"+ShareAccountObject[0].getAccname()+"Acctype"+ShareAccountObject[0].getAcctype()+"AccNo"+ShareAccountObject[0].getAccno());
	}
%>

<table  cellspacing="4" style="border-bottom-color:black;border-bottom-style:solid;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
<tr>
<td>
<table>
<tr>
  <td><center>
  <%if(button=="CoBorrower"){ %>
    <h3 style="font:small-caps;font-style:normal;font-size:small;">CoBorrower Details</h3></center>
    <%}else if(button=="Surity"){ %>
    <h3 style="font:small-caps;font-style:normal;font-size:small;">Surity Details</h3></center>
    <%} %>
  </td>
</tr>
 <tr>
    <td id="blok">
       <html:select property="cosharetype">
       			 <core:forEach var="shType" varStatus="count"  items="${requestScope.ShareModule}" step="1">
      			 	 <html:option value="${count.index}"><core:out value="${shType.moduleAbbrv}"></core:out></html:option>
      			 	 ${count} 
      			 </core:forEach>
      			  </html:select>
       <bean:message key="label.coshno"></bean:message> 
       <html:text property="coshareno" onchange="submit()" onkeypress="return only_numbers()"></html:text>
    </td>
  </tr>
  
</table>
</td>

<tr>
<td>

	<table>
  		<tr>
  		<td>
 			<div style="overflow: scroll;width:500px;height: 250px">
  				<jsp:include page="/Personnel.jsp"></jsp:include>
	  		</div>
	 	</td>   
  	</tr>
  </table>
  
  <table>
   

  <tr>
  	<td><bean:message key="label.surityname"></bean:message></td>
    <td><%if(ShareAccountObject[0]!=null){%>
    		<html:text property="surityname" value="<%=ShareAccountObject[0].getAccname()%>" style="border:transparent;background-color:beige;color:red" size="45" readonly="true"></html:text>
    	<%}%>
    </td>
    
  </tr>
  <tr>
  
    <td><bean:message key="label.shareval"></bean:message></td>
    <td><%if(ShareAccountObject[0]!=null){%>
    	<html:text property="covalue" value="<%= ""+ShareAccountObject[0].getAmount() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
    	<%}%>
	</td>
	 </tr>
	<tr>
	 <td><bean:message key="label.brcode"></bean:message></td>
    <td>
    <%if(ShareAccountObject[0]!=null){%>
    	<html:text property="brcode" value="<%=""+ ShareAccountObject[0].getScrollno() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
    	<%}%>
    </td>
        
  </tr>
  
  <tr>
   
    <td><bean:message key="label.brname"></bean:message></td>
    <td> <%if(ShareAccountObject[0]!=null){%>
    		<html:text property="brname" value="<%= ""+ShareAccountObject[0].getBranchname() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
    	<%}%>
    </td>
    
  </tr>
  
  </table>
 </td>
 </tr>
 <tr>
  
 <td>
       <%if(ShareAccountObject[0]==null){ %>
       <html:submit styleClass="ButtonBackgroundImage" disabled="true">Submit</html:submit>
		<%}else if(button=="CoBorrower"){ %>       
     <input type="submit" value="SaveCoborrowerDet" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=SaveCoborrowerDet'" />
       <%} else if(button=="Surity"){%>
       <input type="submit" value="SaveSurityDet" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=SaveSurityDet'" />
       <%} %>
         </td>
         </tr>
        <tr>
       <td><html:submit styleClass="ButtonBackgroundImage" onclick="func_clear()">Clear</html:submit>
      <html:submit styleClass="ButtonBackgroundImage">Update</html:submit></td>
 </tr>
 

 

  </table>
   </html:form>
</body>
</html>