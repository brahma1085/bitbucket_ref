<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>

<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.general.Address"%>
<html>
    <head>
        <title></title>
        <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    </head>
<body class="Mainbody">
    <%!
        CustomerMasterObject cmObject;
    	DepositMasterObject object;
    	Address address;
        String panelName; 
        String depNum;
        String depTyp;
    
    %>  
    <%  
        cmObject = (CustomerMasterObject) request.getAttribute("personalInfo");
    	object=(DepositMasterObject)request.getAttribute("depositInfo");
    	address=(Address)request.getAttribute("addressInfo");
        panelName=(String)request.getAttribute("panelName");
       depNum=String.valueOf(request.getAttribute("DepNumInfo"));
       depTyp=String.valueOf(request.getAttribute("DepTypInfo"));
    %> 
    <html:form action="/Common/Deposit">
    <table  cellspacing="5" style="border: thin solid #000000" class="txtTable">
    <tr><td></td><td align="center" ><b><%=""+panelName%></b></td></tr>
       <br>
     <%if(depNum!=null && depTyp!=null){ %>
	   <tr>
	   		<td><bean:message key="lable.Dep_ac"></bean:message></td>
	   		<td><html:text property="depacTyp" styleClass="formTextField" value="<%=""+depTyp%>" readonly="true"></html:text></td>
	   		<td><bean:message key="lable.depacnum"></bean:message></td>
	   		<td><html:text property="depacNum" styleClass="formTextField"  value="<%=""+depNum %>" readonly="true"></html:text></td>
	   </tr>
	   <%} %>
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
                <td><html:select property="mailaddress" styleClass="formTextField" >
                    <html:option value="<%=""+cmObject.getAddressProof()%>"></html:option>
                    </html:select>
                </td>
         <% }
            else{
         %>
            <td><html:select property="mailaddress"  styleClass="formTextField" >
                <html:option value="SELECT"></html:option>
                </html:select>
             </td>
         <%
             }
         %>
   
        <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.sc/st"></bean:message></font> </td>
         <% if(cmObject!=null){%>
                <td><html:text property="scst" size="8" styleClass="formTextField" value="<%=""+cmObject.getScSt()%>" readonly="true"></html:text></td>
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
        <% if(address!=null){%>
            <td><html:textarea property="address"  styleClass="formTextField" value="<%=""+address.getAddress()%>" readonly="true"></html:textarea></td>
       <%}else{%>
            <td><html:textarea property="address" styleClass="formTextField" ></html:textarea></td>
       <%}%>
       
        <% if(cmObject!=null){%>
       <td></td>
       <td><html:img  src="swe.jpg" border="1" height="80" width="70"></html:img></td>
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
   <tr>
   		<td align="right"><bean:message key="label.dep_date"></bean:message></td>
   		<%if(object!=null){ %>
   		<td><html:text property="dep_date" value="<%=""+object.getDepDate()%>" styleClass="formTextField" readonly="true"></html:text></td>
   		<%} %>
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.mat_date"></bean:message></td>
   		<%if(object!=null){ %>
   		<td><html:text property="mat_date" value="<%=""+object.getMaturityDate()%>" styleClass="formTextField" readonly="true"></html:text></td>
   		<%} %>
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.Period_in_days"></bean:message></td>
   		<%if(object!=null){ %>
   		<td><html:text property="period_in_days" value="<%=""+object.getDepositDays()%>" styleClass="formTextField" readonly="true"></html:text></td>
  		<%} %>
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.IR"></bean:message></td>
   		<%if(object!=null){ %>
   		<td><html:text property="ir" value="<%=""+object.getInterestRate()%>" styleClass="formTextField" readonly="true"></html:text></td>
		<%} %>   
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.dep_amt"></bean:message></td>
   		<%if(object!=null){ %>
   		<td><html:text property="dep_amt" value="<%=""+object.getDepositAmt()%>" styleClass="formTextField" readonly="true"></html:text></td>
   		<%} %>
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.Mat_amt"></bean:message></td>
   		<%if(object!=null){ %>
   		<td><html:text property="mat_amt" value="<%=""+object.getMaturityAmt()%>" styleClass="formTextField" readonly="true"></html:text></td>
  		<%} %>
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.IntRate"></bean:message></td>
   		<%if(object!=null){ %>
   		<td><html:text property="intRate" value="<%=""+object.getInterestAccured()%>" styleClass="formTextField" readonly="true"></html:text></td>
  		<%} %>
   </tr>
   
     </table>
    </html:form>
</body>
</html>