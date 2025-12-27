
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
      <h1 style="font:small-caps;font-style:normal;color: blue" ><center>SAVINGS ACCOUNT MODULE</center></h1>
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
    function settingval(){
    document.forms[0].hidvar.value='submit';
    document.forms[0].submit();
    
    }
    function checknewac(){
    if(document.forms[0].newactype.checked)
    {
    alert("Please Enter the values to Create new Account Type");
    }
    else{
    
    document.forms[0].submit();}
    }
    
    function setval(){
    document.forms[0].setv.value='submit';
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
         AccountCloseActionForm acForm;
    %>
    <% String disabling=(String)request.getAttribute("disabling");
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    %>
    <core:if test="${one!=null}">
    <core:if test="<%= one.equals("true")%>">
       <font color="red"><bean:message key="label.empty"></bean:message></font>
       <%System.out.println("i am here"); %>
    </core:if>
    </core:if>
    
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=request.getAttribute("closingmsg")%></font>
    </core:if>
    
     <html:form action="/Administrator/AdmSBModule?pageId=10013" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="setv" />
     	<div id="tabs">
     	</div>
     	<br><br><br>
     <table class="txtTable" style="border:thin solid #0099CC">
     		<tr>
     		<td>New Account Type</td>
     		<td>
     		<html:radio property="newactype" value="newAccountType"></html:radio>
     		</td>
     		<td>
     		<html:button property="views" value="View Details" onclick="checknewac()"></html:button>
     		</td>
     		</tr>           
     </table>
     <br><br>
     <table  style="border:thin solid navy">
     <tr>
     <td>Account Name: </td>
     <td><html:text property="acname"></html:text> </td>
     </tr>
     <tr>
     <td>Short Name: </td>
     <td><html:text property="sname"></html:text> </td>
     </tr>
     <tr>
     <td>Last A/C No.: </td>
     <td><html:text property="lastacno"></html:text> </td>
     </tr>
    <tr>
     <td>Min Balance: </td>
     <td><html:text property="minbal"></html:text> </td>
     </tr>
     <tr>
     <td>Interest From Date: </td>
     <td><html:text property="ifdate"></html:text> </td>
     </tr>
     <tr>
     <td>Interest To Date: </td>
     <td><html:text property="itdate"></html:text> </td>
     </tr>
     <tr>
     <td>Tran per Month: </td>
     <td><html:text property="trnprmonth"></html:text> </td>
     </tr>
     <tr>
     <td>Last Int Paid Date: </td>
     <td><html:text property="lintpaydate"></html:text> </td>
     </tr>
     <tr>
     <td>Maximum No. of JointHolders: </td>
     <td><html:text property="maxjoints"></html:text> </td>
     </tr>
     <tr>
     <td>No. of Lines in PassBook: </td>
     <td><html:text property="linpassbook"></html:text> </td>
     </tr>
     <tr>
     <td>InOperative Days: </td>
     <td><html:text property="inopDays"></html:text> </td>
     </tr>
     <tr>
     <td>Cheque Validity Months: </td>
     <td><html:text property="chqvalid"></html:text> </td>
     </tr>
      <tr>
     <td>Min Balance -Cheque: </td>
     <td><html:text property="minbalchqm"></html:text> </td>
     </tr>
      <tr>
     <td>Min Balance -Clearing: </td>
     <td><html:text property="minbalclr"></html:text> </td>
     </tr>
     </table>
     <br><br>
     <br><br>
     <table>
     <!--<tr>
     <td><html:text property="codedesc"></html:text> </td>
     </tr>
     <tr>
     <td>GL Type:</td>
     <td></td>
     <td>GL Code:</td>
     <td></td>
     </tr>
     <tr><td>GL KeyDescription:</td>
     <td> Desciption</td>
     </tr>
     <tr>
     <td><html:button property="sub1" value="Submit" onclick="setval()"></html:button> </td>
     </tr>
     </table>
     <table  style="border:thin solid orange">
     <tr>
     <td></td>
     <td><font color="Red" > CREDIT</font></td>
     <td><font color="Red" > DEBIT</font></td>
     </tr>
     <tr>
     <td><font color="Red" > ALLOTMENT</font></td>
     <td><html:select property="ac">
     <html:option value="No Value">No Value</html:option>
     <html:option value="-1">-1</html:option>
     <html:option value="1">1</html:option>
     
     </html:select> </td>
     
		<td><html:select property="ad">
     <html:option value="No Value">No Value</html:option>
     <html:option value="-1">-1</html:option>
     <html:option value="1">1</html:option>
     
     </html:select> 
	</td>
     </tr>
     <tr>
     <td><font color="Red" > WIDTHDRAWAL</font></td>
     <td><html:select property="wc">
     <html:option value="No Value">No Value</html:option>
     <html:option value="-1">-1</html:option>
     <html:option value="1">1</html:option>
     
     </html:select> </td>
     
		<td><html:select property="wd">
     <html:option value="No Value">No Value</html:option>
     <html:option value="-1">-1</html:option>
     <html:option value="1">1</html:option>
     
     </html:select> 
	</td>
     </tr>
     --><tr><td><html:submit></html:submit> </td></tr>
     </table>
                </html:form>
                <%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
  </body>
  </html>          