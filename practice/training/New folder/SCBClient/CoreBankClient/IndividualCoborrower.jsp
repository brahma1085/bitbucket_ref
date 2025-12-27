<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="masterObject.general.AccountObject"%>
<%@page import="masterObject.share.ShareMasterObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%!AccountObject ShareAccountObject[]; %>
<%ShareAccountObject = (AccountObject[])request.getAttribute("ShareAccountObject");%> 
<%System.out.println("ShareDetails==============>"+ShareAccountObject);
	if(ShareAccountObject!=null)
	{
		System.out.println("share value==============>"+ShareAccountObject[0].getAccname()+"Acctype"+ShareAccountObject[0].getAcctype()+"AccNo"+ShareAccountObject[0].getAccno());
	}
%>
<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:solid;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">

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


<html:form action="/Loans/IndividualCoBorrower?pageidentity.pageId=5027">
  <table>
   

  <tr>
  	<td><bean:message key="label.surityname"></bean:message></td>
    <td><%if(ShareAccountObject!=null){%>
    		<html:text property="surityname" value="<%=ShareAccountObject[0].getAccname()%>" style="border:transparent;background-color:beige;color:red" size="45"></html:text>
    	<%}%>
    </td>
    
  </tr>
  <tr>
  
    <td><bean:message key="label.shareval"></bean:message></td>
    <td><%if(ShareAccountObject!=null){%>
    	<html:text property="value" value="<%= ""+ShareAccountObject[0].getAmount() %>" style="border:transparent;background-color:beige;color:red"></html:text>
    	<%}%>
	</td>
	
	 <td><bean:message key="label.brcode"></bean:message></td>
    <td>
    <%if(ShareAccountObject!=null){%>
    	<html:text property="brcode" value="<%=""+ ShareAccountObject[0].getScrollno() %>" style="border:transparent;background-color:beige;color:red"></html:text>
    	<%}%>
    </td>
        
  </tr>
  
  <tr>
   
    <td><bean:message key="label.brname"></bean:message></td>
    <td> <%if(ShareAccountObject!=null){%>
    		<html:text property="brname" value="<%= ""+ShareAccountObject[0].getBranchname() %>" style="border:transparent;background-color:beige;color:red"></html:text>
    	<%}%>
    </td>
    
  </tr>
  
  </table>
  </html:form>
  
 </td>
 </tr> 
  
  </table>
</body>
</html>