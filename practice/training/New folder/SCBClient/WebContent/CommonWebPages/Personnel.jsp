<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ page import="masterObject.general.Address" %>
<%@ page language="java" session="true" import= "java.awt.image.BufferedImage,java.io.ByteArrayOutputStream,java.io.InputStream,java.sql.Blob,java.sql.Connection,
java.sql.ResultSet,javax.swing.ImageIcon,java.sql.*,java.io.*" %>

<html>
    <head>
        <title></title>
        <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    </head>
<body class="Mainbody">
    <%!
        CustomerMasterObject cmObject=new CustomerMasterObject();
        String panelName; 
        Address addr;

    %>  
    <%  
        cmObject = (CustomerMasterObject) request.getAttribute("personalInfo");
        panelName=(String)request.getAttribute("panelName");
        FileOutputStream fos;
     if(cmObject!=null){
     if(cmObject.getAddress()!=null){
    	 	addr =(Address)cmObject.getAddress().get(1);
    	 }
     }
    %> 
    <html:form action="/Common/Personal">
    <%if(cmObject!=null){ %>
    <%if(panelName!=null && cmObject!=null){ %>
    <table cellspacing="5" class="txtTable" style="border:thin solid #000000;" border="1">
    <tr><td></td><td align="center" >
    <%if(panelName!=null) {%>
        <b><%=""+panelName%></b>
        <%} %>
        </td></tr>
    
       <br>
     <tr>
        <td align="right"><bean:message key="label.custid"></bean:message></td>
         <% if(cmObject!=null && cmObject.getName().length()>1){%>
                <td><b><%=""+cmObject.getCustomerID()%></b></td>
         
        <% }else{%>
                 <td><html:text property="customer_id" size="8" styleClass="formTextField" value="" readonly="true"></html:text></td>
              <%
                  }
              %>
         

             <td align="right"><bean:message  key="label.name"></bean:message> </td>
             
             
         <% if(cmObject.getName().trim().length()>0&&cmObject.getName().trim()!=null){
         System.out.println("Hello i am here-->");
         
         %>
                <td><b><%=cmObject.getName() %></b></td>
         <% }
            else{
            	 System.out.println("Hello i am in ELSE Part");
         %>
            <td></td>
         <%
             }
         %>
        </tr>

        <tr>
        <td align="right"><bean:message key="label.category"></bean:message></td>
         <% if(cmObject!=null){%>
                <td><html:text property="category" size="8" styleClass="formTextField" value="<%=""+cmObject.getCategory()%>" readonly="true"></html:text></td>
         <% }
            else{
         %>
            <td><html:text property="category" size="8" styleClass="formTextField" value="" readonly="true"></html:text></td>
         <%
             }
         %>
                        
        <td align="right"><bean:message key="label.subcategory"></bean:message> </td>
         <% if(cmObject!=null){%>
                <td><html:text property="subcategory" size="8" styleClass="formTextField" value="<%=""+cmObject.getSubCategory()%>" readonly="true"></html:text></td>
         <% }
            else{
         %>
            <td><html:text property="subcategory" size="8" styleClass="formTextField" value="" readonly="true"></html:text></td>
         <%
             }
         %>
   </tr>

     <tr>
        <td align="right"><bean:message key="label.mailaddress"></bean:message></td>
         <% if(cmObject!=null){%>
                <td><textarea rows="4" cols="25" readonly="readonly">
                <%if(addr!=null){ %>
                <%if(addr.getAddress()!=null){ %>
                <%=""+addr.getAddress() %>
                <%} %>
                <%if(addr.getCity()!=null){ %>
                <%=""+addr.getCity() %>
                <%} %>
                <%} %>
                </textarea>
                </td>
         <% }
            else{
         %>
            <td>
             </td>
         <%
             }
         %>
   
        <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.sc/st"></bean:message></font> </td>
         <% if(cmObject.getScSt()!=null){%>
                <td><html:text property="scst" size="8" styleClass="formTextField" value="<%=cmObject.getScSt()%>" readonly="true"></html:text></td>
         <% }
            else{
         %>
            <td><html:text property="scst" size="8" styleClass="formTextField" value="" readonly="true"></html:text></td>
         <%
             }
         %>
   </tr>
   <tr>
       <% if(cmObject!=null){%>
       <td></td>
       <td><%
try
		{
	String s1=String.valueOf(cmObject.getCustomerID());
	String n1="C:/JBoss-4.0.0/Photos/p"+s1+".jpg" ;
			System.out.println("to print photo");
			if(cmObject.getBinaryImage()!=null&&cmObject.getBinaryImage().length>0){
				byte[] b=cmObject.getBinaryImage();
				System.out.println("to print photo----------128--------");
				fos=new FileOutputStream("../Photos/p"+s1+".jpg");
					fos.write(b);
					fos.close();
				%>
				
				<img src="<%=n1%>"  height="80" width="90">
			<%}
		}
		catch (Exception exc)
		{ 
			System.out.println(exc);
		}
%>
</td>
        <%}else{%>
         <td></td>
       <%}%>
      </tr>

          


        <tr>
        <td align="right"><bean:message key="label.dob"></bean:message></td>
         <% if(cmObject.getDOB()!=null){%>
                <td><html:text property="dob" size="10" styleClass="formTextField" value="<%=""+cmObject.getDOB()%>" readonly="true"></html:text></td>
         <% }
            else{
         %>
            <td><html:text property="dob" size="10" styleClass="formTextField" value="" readonly="true"></html:text></td>
         <%
             }
         %>

        <td align="right"><bean:message key="label.sex"></bean:message></td>
         <% if(cmObject.getSex()!=null){%>
                <td><html:text property="sex" size="8" styleClass="formTextField" value="<%=""+cmObject.getSex()%>" readonly="true"></html:text></td>
         <% }
            else{
         %>
            <td><html:text property="sex" size="8" styleClass="formTextField" value="" readonly="true"></html:text></td>
         <%
             }
         %>
   </tr>
    <tr>
        <td align="right">&nbsp;</td>
         <td>&nbsp;</td>
    
        <td align="right"><bean:message key="label.occupation"></bean:message></td>
         <% if(cmObject.getOccupation()!=null){%>
                <td><html:text property="occupation" size="8" styleClass="formTextField" value="<%=""+cmObject.getOccupation()%>" readonly="true"></html:text></td>
         <% }
            else{
         %>
            <td><html:text property="occupation" size="8" styleClass="formTextField" value="" readonly="true"></html:text></td>
         <%
             }
         %>
        
   </tr>
     </table>
     
     <%}%>
     <%}else{ %>
	     <font color="red">Customer/Introducer details are not found</font>
     <%} %>
    </html:form>
</body>
</html>