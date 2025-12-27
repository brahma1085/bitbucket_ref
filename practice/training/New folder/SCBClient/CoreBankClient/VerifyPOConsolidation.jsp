
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page import="masterObject.frontCounter.PayOrderObject" %>
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
<h2 class="h2">Verify PO Consolidation</h2></center></font>
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
    document.forms[0].hidval.value='verify';
    document.forms[0].submit();
    
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
         Object[][] unverified;
    %>
    <% String disabling=(String)request.getAttribute("disabling");
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	PayOrderObject[] poconsol=(PayOrderObject[])request.getAttribute("payorderobject");
    	unverified=(Object[][])request.getAttribute("unverified");
    		
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
    
     <html:form action="/FrontCounter/VerifyPOConsol?pageId=3049" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="hidval" />
     	
     	<br><br><br>
    <table border="1" >
     	<tr><td>
        <table border="1" >
	<tr>
		<td height="38" width="116"><font size="4">Pay Order No:</font></td>
		<td height="38" width="159">
		<html:text property="payorder" onblur="submit()"></html:text> </td>
		</tr>
		<tr>
		<td height="36" width="114"><font size="4">Pay ChequeNo:</font></td>
		<td height="36" width="156"><font size="4">
		<html:text property="chequeno" onblur="submit()"></html:text> </font></td>
		</tr>
		<tr>
		<td height="33" width="124"><font size="4">Payee property:</font></td>
		<td height="33" width="148"><font size="4">
		<html:text property="payeename"></html:text> </font></td>
	</tr>
	<tr><td>
	
	
	
	
	
	</td></tr>
</table>
</td>
<td valign="top">
<table border="1">
<tr><td align="center">
</td>
<td><center><u>Unverified PayOrder's</u></center>
</td></tr>
<tr><td></td></tr>
<tr><td>PayOrder No.</td>
<td>Payee Name</td>
<td>PO ChqNo</td>
<td>Amount</td>

</tr>
<core:if test="<%=unverified!=null %>">
<%for(int i=0;i<unverified.length;i++){ %>
<tr>
<td><%=unverified[i][0].toString() %></td>
<td><%=unverified[i][1].toString() %></td>
<td><%=unverified[i][2].toString() %></td>
<td><%=unverified[i][3].toString() %></td>

</tr>

<%} %>
</core:if>

</table>

</td>


</tr></table>

<br><br><br>

<core:if test="<%=poconsol!=null %>">
<div id="pocon" style="display:block;overflow:scroll;width:450px;height:100px">
<table border="1">
<tr bgcolor="0099CC">
<td>SELECT</td>
<td>S No</td>
<td>PO Type</td>
<td>Date</td>
<td>A/c Ty/No</td>
<td>Gl Type</td>
<td>Gl code</td>
<td>Purchaser Name</td>
<td>Favour Name</td>
<td>Amount</td>

</tr>
<%for(int k=0;k<poconsol.length;k++){ %>
<tr>
<td><%=k %></td>
<td><%=String.valueOf(poconsol[k].getPOSerialNo())%></td>
<td><%=poconsol[k].getPOAccType() %></td>
<td><%=poconsol[k].getPODate() %></td>
<td><%=poconsol[k].getPOAccType()+" "+String.valueOf(poconsol[k].getPOAccNo()) %></td>
<td><%=poconsol[k].getPOGlType() %></td>
<td><%=String.valueOf(poconsol[k].getPOGlCode()) %></td>
<td><%=poconsol[k].getPOPayee() %></td>
<td><%=poconsol[k].getPOFavour() %></td>
<td><%=String.valueOf(poconsol[k].getPOAmount()) %></td>
</tr>
<%} %>
</table>


</div>
<html:button property="verifying" value="Verify" onclick="setval()" styleClass="ButtonBackgroundImage"></html:button>
</core:if>

	
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