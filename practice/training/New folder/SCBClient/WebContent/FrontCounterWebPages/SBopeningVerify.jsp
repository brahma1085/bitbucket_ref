
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Dec 8, 2008
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<%@page import="com.scb.fc.forms.AccountCloseActionForm"%>
<html>
  <head><title>AccountOpening</title>
       <font color="blue" ><center>
<h2 class="h2">SB/CA Verification</h2></center></font>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
       
      
      <!--<style type="text/css">
          body{
              font-size:10px;
              font-family:serif;
              font-style:oblique;
              font-weight:bold;
              background:beige;
          }
          table{
               background:transperent; 
          }
          
    </style>
    --><link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    <script>
    function setval(val){
    if(confirm("Are you sure you want to verify Account Opening"))
    {
    var a=beforesubmiting();
    if(a){
    document.forms[0].forward.value=val;
    document.forms[0].submit();
    }
    else{
    document.forms[0].forward.value='';
    
    }
    }
    
    }
    
    function onlyNumbers(val)
    {
		var cha =   event.keyCode;
				
		 if ( (cha >= 48 && cha <=57)) 
		 {	
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	
	
	function limitValue(d)
	{
	var c=parseInt(d);
	if(document.forms[0].acno.value.length<=c)
	{
		return true;
	}
	else
	{
		
		document.forms[0].acno.value="";
		document.forms[0].acno.focus;
		alert("digit limit 10 only");
		return false;
	}
	}
    
    function reset123(){
    var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="text")
    {
    ele[i].value="";
    }
    
    
    }
    document.forms[0].acno.value='0';
    document.forms[0].cid.value='0';
    }
    
    function beforesubmiting(){
    if(document.forms[0].acno.value==""){
    alert("Please Enter Account Number");
    document.forms[0].acno.focus();
    return false;
    }
  else if(document.forms[0].cid.value==""){
    alert("Please Enter Customer ID");
    document.forms[0].cid.focus();
    return false;
    }
    else if(document.forms[0].introacno.value==""){
    alert("Please Enter Introducer Account Number");
    document.forms[0].introacno.focus();
    return false;
    }
    else{
    return true;
    }
    
    }
    </script>
    
  </head>
  <body class="Mainbody">
    <%!ModuleObject[] array_module;
	String details[];
	AccountMasterObject acountMaster;
	String jspPath;
	ModuleObject[] mod;
	CustomerMasterObject cmObject;
	AccountCloseActionForm acForm;
	String intradesc = null;%>
    <%
    	acountMaster = (AccountMasterObject) request
    			.getAttribute("AccountDetails");
    	acForm = (AccountCloseActionForm) request.getAttribute("Account");
    	String one = (String) request.getAttribute("error1");
    	String closingmsg = (String) request.getAttribute("closingmsg");
    	String[][] unverified = (String[][]) request
    			.getAttribute("unverified");
    	intradesc = (String) request.getAttribute("Introducer");
    %>
    
    
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=closingmsg%></font>
    </core:if>
    
     <html:form action="/FrontCounter/VerifySBopen?pageId=3045" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="forward" />
     	<div id="tabs">
     	</div>
     	<br><br><br>
            
            <table>
            <tr><td>
            <table>
            <tr><td>Account Type</td>
            <td ><html:select property="acType" styleClass="formTextFieldWithoutTransparent" onblur="submit()">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        	    
                        </core:forEach>
                        </html:select>
                        <%
                        	if(intradesc!= null) {
                        %>
									<font color="red"><%=intradesc%></font>
									<%}%>
                        
                       
                    </td>
            </tr>
            <tr>
            <td height="39" width="128">Account No.:</td>
		<td height="39" width="123"><html:text property="acno" onblur="submit()" onkeypress="return onlyNumbers()" onkeydown="return limitValue('10')"></html:text></td>
		
	</tr>
	<tr>
		<td height="34" width="128">Customer Id:</td>
		<td height="34" width="123"><html:text property="cid" readonly="true"></html:text></td>
	</tr>
	<tr>
		<td height="38" width="128">Introducer Ac type:</td>
		<!--<td ><html:select property="introType"  styleClass="formTextFieldWithoutTransparent" onblur="submit()" >
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                    </td>
	-->
	
	<td height="36" width="123"><html:text property="introType" readonly="true"></html:text>
	</tr>
	<tr>
		<td height="36" width="128">Intor. Ac no:</td>
		<td height="36" width="123"><html:text property="introacno" readonly="true"></html:text></td>
		</tr>
		
		<tr >
                    <td ><bean:message key="label.details"></bean:message></td>
                    <%!String path;%>
                    <%
                    	//path="/SCBClient/CommonWebPages/Personnel.jsp?personalInfo="+request.getAttribute("personalInfo")+"&panelName";
                    %>
                    
                    <td ><html:select property="detailsCombo" onblur="submit()" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT">SELECT</html:option>
                        <%
                        	details = (String[]) request.getAttribute("Details");
                        			if (details != null) {
                        				for (int k = 0; k < details.length; k++) {
                        %>
                        <html:option value="<%=details[k]%>"><%=details[k]%></html:option>
                        	
                        	
                        <%
                        	                        	                        	}
                        	                        	                        			}
                        	                        	                        %>
                        
   	                  	</html:select>
   	                </td>
   	                </tr>
            </table>
         
            </td>
            <td>
            <core:if test="<%=unverified!=null %>">
            <table>
            <tr bgcolor="#FFFFCC">
            <td>Account Type</td>
            <td>Account No</td>
            <td>Name</td>
             </tr>
             <%
             	for (int i = 0; i < unverified.length; i++) {
             %>
             <tr>
             <td><%=unverified[i][0]%></td>
             <td><%=unverified[i][1]%></td>
             <td><%=unverified[i][2]%></td>
             </tr>
             <%
             	}
             %>
            </table>
            </core:if>
            </td>
            </tr>
            
            <tr><td><html:button property="verify" onclick="setval('verify')" value="Verify" styleClass="ButtonBackgroundImage"></html:button></td>
            <td><html:button property="rest" onclick="reset123()" value="Clear" styleClass="ButtonBackgroundImage"></html:button></td>
            
            </tr>
            </table>
            
            
            
            
                </html:form>
                <%
                	jspPath = (String) request.getAttribute("flag");
                	System.out.println("jspPath==" + jspPath);
                %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
  </body>
  </html>          