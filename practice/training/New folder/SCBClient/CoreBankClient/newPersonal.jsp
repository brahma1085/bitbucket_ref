<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>

<html>
    <head>
        <title></title>
        <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    </head>
<body class="Mainbody">
    <%!
        CustomerMasterObject cmObject=new CustomerMasterObject();
        String panelName; 
       

    %>  
    <%  
        cmObject = (CustomerMasterObject) request.getAttribute("personalInfo");
        panelName=(String)request.getAttribute("panelName");
       
       
    %> 
    <html:form action="/Common/Personal">
    <table cellspacing="5" class="txtTable" style="border:thin solid #000000;">
    <tr><td></td><td align="center" ><b><%=""+panelName%></b></td></tr>
       <br>
     <tr>
        <td align="right"><bean:message key="label.custid"></bean:message></td>
         <% if(cmObject!=null){%>
                <td><html:text property="customer_id" size="8" styleClass="formTextField"  value="<%=""+cmObject.getCustomerID()%>" readonly="true" ></html:text></td>
         
        <% }else{%>
                 <td><html:text property="customer_id" size="8" styleClass="formTextField" value="" readonly="true"></html:text></td>
              <%
                  }
              %>
         

             <td align="right"><bean:message  key="label.name"></bean:message> </td>
         <% if(cmObject!=null){%>
                <td><html:text property="name" size="8" styleClass="formTextField" value="<%=""+cmObject.getName()%>" readonly="true"></html:text></td>
         <% }
            else{
         %>
            <td><html:text property="name" size="8" styleClass="formTextField" value="" readonly="true"></html:text></td>
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
                <td><html:select property="mailaddress" styleClass="formTextField" disabled="true">
                    <html:option value="<%="Address is not set"%>"></html:option>
                    </html:select>
                </td>
         <% }
            else{
         %>
            <td><html:select property="mailaddress"  styleClass="formTextField" disabled="true">
                <html:option value="SELECT"></html:option>
                </html:select>
             </td>
         <%
             }
         %>
   
        <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.sc/st"></bean:message></font> </td>
         <% if(cmObject!=null){%>
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
       <td align="right"><bean:message key="label.address"></bean:message></td>
        <% if(cmObject!=null){%>
            <td><html:textarea property="address"  styleClass="formTextField" value="<%="Address is not set"%>" disabled="true"></html:textarea></td>
       <%}else{%>
            <td><html:textarea property="address" styleClass="formTextField"  disabled="true"></html:textarea></td>
       <%}%>
       
        <% if(cmObject!=null){%>
       <td></td>
       <td><html:img  src="" border="1" height="80" width="70"></html:img></td>
        <%}else{%>
         <td><html:img src="" border="1" height="80" width="70"></html:img></td>
       <%}%>
      </tr>

          


        <tr>
        <td align="right"><bean:message key="label.dob"></bean:message></td>
         <% if(cmObject!=null){%>
                <td><html:text property="dob" size="8" styleClass="formTextField" value="<%=""+cmObject.getDOB()%>" readonly="true"></html:text></td>
         <% }
            else{
         %>
            <td><html:text property="dob" size="8" styleClass="formTextField" value="" readonly="true"></html:text></td>
         <%
             }
         %>

        <td align="right"><bean:message key="label.sex"></bean:message></td>
         <% if(cmObject!=null){%>
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
        <td align="right"><bean:message key="label.age"></bean:message></td>
         <% if(cmObject!=null){%>
                <td><html:text property="age" size="8" styleClass="formTextField" value="<%=""+cmObject.getDOB()%>" readonly="true"></html:text></td>
         <% }
            else{
         %>
            <td><html:text property="age" size="8" styleClass="formTextField" value="" readonly="true"></html:text></td>
         <%
             }
         %>
    
        <td align="right"><bean:message key="label.occupation"></bean:message></td>
         <% if(cmObject!=null){%>
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
    </html:form>
</body>
</html>