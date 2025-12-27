<%--
  Created by IntelliJ IDEA.
  User: shwetha
  Date: Nov 22, 2007
  Time: 12:19:06 AM 
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<html>
  <head><title>Simple jsp page</title></head>
  <body bgcolor="beige">
  <html:form action="/Customer/Sucess?pageId=1009"> 
 
   
  <%! int customerID; %>
  <% customerID=(Integer)(request.getAttribute("custid_from_bean")); %>
  <% System.out.println("Customer ID in sucess page=========" +customerID);%>
 
  
  <%! String  notverifycust; %>
  <%notverifycust=(String)request.getAttribute("notverifycust"); %>
  <% System.out.println("notverifycust =========="+notverifycust); %>
  
  <%! int  sucess_verify; %>
  <%sucess_verify=(Integer)request.getAttribute("Verify_customer"); %>
  <% System.out.println("verify id=========="+sucess_verify); %>

   <%! String Cust_not_found; %>
   <%Cust_not_found=(String)request.getAttribute("Customer_not_found");%>
   <%System.out.println("Customer not found======"+Cust_not_found); %>
    
   <%! int Update_sucessfully; %>
   <%Update_sucessfully=(Integer)request.getAttribute("Update_sucessfully");%>
   <%System.out.println("update value==========="+Update_sucessfully);%>
	
	<%String custName,custDOB; %>   
	<%HashMap custAddress; %>
	<%custName=(String)request.getAttribute("custName");%>
	<%custDOB=(String)request.getAttribute("custDOB");%>
	<%custAddress=(HashMap)request.getAttribute("custAddress");%>
	
	
  
  <table>
  		<tr><td></td></tr>
  		<tr><td></td></tr>
  		<tr><td></td></tr>
  		<tr><td></td></tr>
  		<tr><td></td></tr>
  		<tr><td></td></tr>
    	<table style="border: thin solid black;">	
    	 
       <tr>
            <%if(customerID!=0){ %>
              <%if((customerID!=0)&&(sucess_verify==4)){ %>
              <td><font color="purple" style="font-family: serif;font: bold"><bean:message key="label.cust"></bean:message></font>
             	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font color="blue"><%=""+customerID%></font>&nbsp;&nbsp;
             <font color="purple" size="4" style="font-style: italic;font: bold;">Sucessfully Created</font>
              </td>
             <%}%>
             <%} %>
          
       </tr>
        <%if(customerID!=0){ %>
        <%if((customerID!=0)&&(sucess_verify==4)){ %>
        <% %>
       <tr>
       <td>
       <%if(custName!=null){ %>
       <font color="purple" style="font-family: serif;font: bold"><%="Customer Name:"%></font><font color="blue"> &nbsp;&nbsp;<%=custName%></font>
       </td>
       <%} %>
       <tr>
       <tr>
       <td>
       <%if(custDOB!=null){ %>
       <font color="purple" style="font-family: serif;font: bold"><%="Customer DOB:"%></font>
       <font color="blue"> &nbsp;&nbsp;<%=custDOB%></font>
       <%} %>
       </td>
       </tr>
       <!--<tr><td><font color="purple" style="font-family: serif;font: bold"><%="Customer Address:"%></font><font color="blue"> &nbsp;&nbsp;<%=custAddress%></font></td></tr>
       --><%}%>
        <%} %>          
  </table>
  
   <% if(sucess_verify==1){ %>
   
   <tr>
      <td><font color="red" style="font-family: serif;font: bold"><bean:message key="label.cust"></bean:message></font>
      <%System.out.println("sucess_verify in if condition----->"+sucess_verify+"/n"+"customerID-------->"+customerID); %>
          <%if(customerID!=0){%>
          <%System.out.println("sucess_verify in if condition----->"+sucess_verify+"/n"+"customerID-------->"+customerID); %>
        	<html:text property="cid" value="<%=""+customerID%>" size="10" style="border:transparent;background-color:beige"></html:text>  
          <%}%>
          <%System.out.println("sucess_verify in if condition----->"+sucess_verify+"/n"+"customerID-------->"+customerID); %>
         <font color="red" size="4" style="font-style: italic;font: bold;">Sucessfully verified</font>
      </td>
   </tr> 
   <%}%>
   
  <%if(notverifycust!=null){ %>
   <%if((sucess_verify==0)&&(notverifycust.equalsIgnoreCase("10"))){ %>
    <tr>
       
       <td><font color="red" style="font-family: serif;font: bold"><bean:message key="label.cust"></bean:message></font>
           <%if(customerID!=0){%>
        	<html:text property="cid" value="<%=""+customerID%>" size="10" style="border:transparent;background-color:beige"></html:text>  
          <%}%>
          <font color="red" size="3" style="font-style: italic;font: bold;"> Cid is already verified</font>       
       </td>
        
   </tr>
   <%} %>
   <%}  %>
   <%if(Update_sucessfully!=0){%> 
   
  <tr>
      <td>
         <font color="red" style="font-family: serif;font: bold"><bean:message key="label.cust"></bean:message></font>
         <%if(Update_sucessfully!=0){ %>
        <html:text property="cid"  value="<%=""+Update_sucessfully%>" style="border:transparent;background-color:beige" size="10"></html:text><font color="red" size="4" style="font-family: serif ;font: italic;font: bold;">Updated Sucessfully</font>
        <%} %>       
      </td>
  </tr>
  <%}%>
 
 <%if(notverifycust!=null){ %> 
  <%if(notverifycust.equalsIgnoreCase("update")){ %>
  
  <tr>
      <td>
         <font color="red" style="font-family: serif;font: bold"><bean:message key="label.cust"></bean:message></font>
          <%if(customerID!=0){%>
        	<html:text property="cid" value="<%=""+customerID%>" size="10" style="border:transparent;background-color:beige"></html:text>  
          <%}%>
         <font color="red" size="4" style="font-family: serif ;font: italic;font: bold;">Is Not Yet Verified</font>       
      </td>
  </tr>
 <%}%>
 <%} %>
 
  </table>
 </html:form> 
 </body> 
 </html>