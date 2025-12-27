<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>    
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareMasterObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
</head>
<body class="Mainbody">
<%!
   ShareMasterObject[] shobj=null;
%>

<%
   shobj=(ShareMasterObject[])request.getAttribute("LoanDetails");
%>
     <table class="txtTable" style="border: thin;border-color: black;width: 300px;">
  <tr>
   <td>Linked Shares</td>
   <td>ModuleAbbr</td>
   <td>Ac_no</td>
   <td>Disb_left</td>
   <td>Pr_bal</td> 
  </tr>
  
  <%
  if(shobj!=null){
   for(int i=0;i<shobj.length;i++){	  
  %>
  <tr>
     <td style="background-color:#ECEBD2"><html:text property="lnk_shares" style="border:thin;background-color:beige" value="<%=""+shobj[i].getLnk_shares() %>" size="10"></html:text></td>
     <td style="background-color:#ECEBD2"><html:text property="moduleabbr" style="border:thin;background-color:beige" value="<%=""+shobj[i].getModuleabbr() %>" size="10"></html:text></td>
     <td style="background-color:#ECEBD2"><html:text property="ac_no" style="border:thin;background-color:beige" value="<%=""+shobj[i].getAc_no() %>" size="10"></html:text></td>
     <td style="background-color:#ECEBD2"><html:text property="disb_left" style="border:thin;background-color:beige" value="<%=""+shobj[i].getDisb_left() %>" size="10"></html:text></td>
     <td style="background-color:#ECEBD2"><html:text property="pr_bal" style="border:thin;background-color:beige" value="<%=""+shobj[i].getPr_bal() %>" size="10"></html:text></td>
  </tr>
	  
  <%
   }}
  %>
  </table>
</body>
</html>