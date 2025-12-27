<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
 
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Details</title>
      <h2 style="font:small-caps; font-style:normal;">
      <center>Details</center></h2>
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
              width:100%;
          }
          td{
             background:transparent;
             widht:100%;
          }
    </style>

  </head>

<body>
<%!
String[] values;
%>

<%values=(String[])request.getAttribute("details");%>
  <center>
  <table border="1" bordercolor="maroon">
    <tr><td>Name</td><td><%=""+values[0]%></td></tr>
    <tr><td>CID</td><td><%=""+values[1]%></td></tr>
    <tr><td>Ac.No</td><td><%=""+values[2]%></td></tr>
  </table>
  </center>

</body>
</html>