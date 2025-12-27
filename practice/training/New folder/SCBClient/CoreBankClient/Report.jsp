<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    

<%@page import="masterObject.share.ShareReportObject"%>
<%@page import="java.awt.Color"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report</title>
<h2 style="font:small-caps; font-style:normal;">
      <center>Share Report</center></h2>
      
      <style type="text/css">
          body{
              font-size:8px;
              font-family:serif;
              font-style:oblique;
              font-weight:bold;
              background:beige;
          }
          table{
               background:transparent;
          }
          tr{
              background:transparent;
          }
          td{
             
             width:5%;
          }
    </style>

</head>
<body>
<%!
   String date;
   ShareReportObject[] shreport;
%>
<%shreport=(ShareReportObject[])request.getAttribute("Report"); %>
 <html:form action="/Share/Report?pageId=4010">
 <table>
 <tr>
 <td><bean:message key="label.frm_dt"></bean:message></td>

 <%
     date=(String)request.getAttribute("date");
 %>
 <td><html:text property="frm_dt" value="<%=""+date%>"></html:text> </td>
 
 <td><bean:message key="label.to_dt"></bean:message></td>
 <td><html:text property="to_dt" value="<%=""+date%>" onchange="submit()"></html:text> </td>
 
 </tr>
 <tr>
 <td><html:submit value="VIEW"></html:submit></td>
 <td><html:cancel value="CLEAR"></html:cancel></td>
 
 </tr>
 </table>
 
 <table border="1"  bordercolor="Orange" style="border-style: dashed; ">
 <td>Share.No</td>
 <td>Name</td>
 <td>Num of Share</td>
 <td>Share Value</td>
 
 <%if(shreport==null){
     	 
 %>
   
   
 <%}else {
	 for(int i=0;i<shreport.length;i++){
		 
		   //Color c=new Color(i+50,100,i+100);
		 
 %>
 <tr>
 
 <td><%=""+shreport[i].getShareNumber() %></td>
 <td><%=""+shreport[i].getName() %></td>
 <td><%=""+shreport[i].getNumberofShares() %></td>
 <td><%=""+shreport[i].getShareVal() %></td>
 </tr>
 
 
 <%} }%>
 
 </table>
 </html:form>
</body>
</html>