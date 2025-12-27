
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Dec 23, 2008
 
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>
<html>

  <head><title>AccountOpening</title>
      <h1 style="font:small-caps;font-style:normal;color: blue" ><center>Cheque/Slip Withdrawal</center></h1>
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
    function setval(){
    if(confirm("Are you sure you want to go ahead with the operation")){
    var a=beforesubmiting();
    if(a){
     document.forms[0].hidval.value='submit';
    document.forms[0].submit();
    
    }
    else{
     document.forms[0].hidval.value='';
    }
    }
    
   
    }
    
    function beforesubmiting(){
    if(document.forms[0].tokenno.value==""){
    alert("Please Enter Proper Token Number");
    document.forms[0].tokenno.focus();
    return false;
    }
    else if(document.forms[0].acno.value==""){
    alert("Please Enter Proper Account Number");
    document.forms[0].acno.focus();
    return false;
    }
    else if(document.forms[0].chqslno.value==""){
    alert("Please Enter Cheque/Slip/Po Number");
    document.forms[0].chqslno.focus();
    return false;
    }
    else if(document.forms[0].date.value==""){
    alert("Please Enter Date");
    document.forms[0].date.focus();
    return false;
    }
     else if(document.forms[0].amount.value==""){
    alert("Please Enter valid Amount");
    document.forms[0].amount.focus();
    return false;
    }
     else if(document.forms[0].payee.value==""){
    alert("Please Enter Payee name");
    document.forms[0].payee.focus();
    return false;
    }
    else{
    return true;
    }
    }
    
    function KeyCheck(e)

{

   var KeyID = (window.event) ? event.keyCode : e.keyCode;
   if(KeyID==113){
   document.forms[0].onhelp.value="help";
   document.forms[0].submit();
   }
    
    }
    </script>
  </head>
  <body class="Mainbody">
    <%!
        ModuleObject[] array_module;
        String details[];
        AccountMasterObject acountMaster;
        String jspPath;
        ModuleObject[] mod;
        CustomerMasterObject cmObject;
        SBOpeningActionForm sbForm;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    	sbForm=(SBOpeningActionForm)request.getAttribute("Account");
    	String msg=(String)request.getAttribute("chqw");
    	String[] ChwDetail=(String[])request.getAttribute("chwdetail");
    	
    	
    	
    %>
    
    <core:if test="<%=msg!=null%>">
         <font color="red"><%=msg%></font>
         </core:if>
    <table align="left" valign="top" class="txtTable">
     <html:form action="/FrontCounter/VerifyFCChqwithdrawal?pageId=3040" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	<html:hidden property="defaultSignIndex" value="0"/>
     	<html:hidden property="hidval" />
     	<html:hidden property="onhelp" />
       
         <td >
            <table border="0" width="451" height="448" class="txtTable">
	<tr>
		<td height="40" width="155">Token Number:</td>
		<td height="40" width="360" colspan="2"><html:text property="tokenno" onkeypress="KeyCheck(this)"/></td>
	</tr>
	<tr>
		<td height="35" width="155">Chq/Slip/PO</td>
		<td height="35" width="360" colspan="2">
<html:select property="chqslpo" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <html:option value="Cheque" >Cheque</html:option>
                        <html:option value="Slip" >Slip</html:option>
                        
</html:select>
</td>
	</tr>
	<tr>
		<td height="36" width="155">Account Type</td>
		<td height="36" width="360" colspan="2">
		<html:select property="acType" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
		
		</td>
	</tr>
	<tr>
		<td height="38" width="155">Account No.</td>
		<td height="38" width="360" colspan="2"><html:text property="acno" onblur="submit()"/></td>
	</tr>
	<tr>
		<td height="40" width="155">Name</td>
		<td height="40" width="360" colspan="2">
<core:if test="<%=ChwDetail!=null%>">
         <font color="red"><%=ChwDetail[0]%></font>
         </core:if>

</td>
	</tr>
	<tr>
		<td height="34" width="155">Chq/Slip/PO No.</td>
		<td height="34" width="360" colspan="2"><html:text property="chqslno" /></td>
	</tr>
	<tr>
		<td height="39" width="155">Details</td>
		<td height="39" width="360" colspan="2">
<core:if test="<%=ChwDetail!=null%>">
         <font color="red"><%=ChwDetail[1]+""+ChwDetail[2]%></font>
         </core:if>
</td>
	</tr>
	<tr>
		<td height="38" width="155">Date</td>
		<td height="38" width="360" colspan="2"><html:text property="date" /></td>
	</tr>
	<tr>
		<td height="39" width="155">Amount Rs.</td>
		<td height="39" width="360" colspan="2"><html:text property="amount" /></td>
	</tr>
	<tr>
		<td height="40" width="155">Payees Name</td>
		<td height="40" width="360" colspan="2"><html:text property="payee" /></td>
	</tr>
	
	<tr>
		<td height="40" width="155"><html:button property="sub" value="Submit" onclick="setval()"/></td>
		<td height="40" width="146"><html:submit value="Delete"/></td>
		<td height="40" width="208"><html:submit value="Cancel"/></td>
	</tr>
	
</table>

        </td>
        <td>

        </td>
               	
       </html:form>
      </table>
      
       <table align="left" valign="top">
      		<tr>
      		<td>
      		<%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
            </td>
            </tr>
        </table>
   
  </body>
</html>