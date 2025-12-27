
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
      <h1 style="font:small-caps;font-style:normal;color: blue" ><center>inoperative posting</center></h1>
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
    
    document.forms[0].hidval.value='submit';
    document.forms[0].submit();
    }
    
    
    function putvals(){
    var a=document.forms[0].acType.value;
    var b=document.forms[0].actyp.value;
    
    if(a!="SELECT"){
    
   	b=a;
     
    }
    else{
    
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
         AccountCloseActionForm acForm;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	String actypvalue=(String)request.getAttribute("actypvalue");
    	AccountMasterObject[] inopdata=(AccountMasterObject[])request.getAttribute("passvalues");
    %>
 
    
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=closingmsg%></font>
    </core:if>
    
         <html:form action="/FrontCounter/InOpProcess?pageId=3023" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="hidval" />
     	
     	<br><br><br>
          
	<table  border ="1"width="229" height="54">
		<tr>
			<td height="48" width="132"><font size="4">Account Type:</font></td>
			<td height="48" width="81"><html:select property="acType" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select></td>
			</tr>
			</table>

	<p><input type="submit" value="  View  " name="B2" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="Submit" name="B1" onclick="setval()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="submit" value="Cancel" name="B3"></p>
<core:if test="<%=inopdata!=null %>">
<div style="display: block;overflow:scroll; height:300px; width:750px;">
<table class="its"><tr ><td>Select</td>
<td>Sno.</td>
<td>Ac_Type</td>
<td>Ac_no</td>
<td>Cid</td>
<td>Name</td>
<td>AcOpen_Dt</td>
<td>Last_trndt</td>
<td>Ac_status</td>
<td>Closig bal</td>
</tr>
<%for(int k=0;k<inopdata.length;k++) {%>
<tr>
<%int s=k+1;%>
<td><input type="checkbox" name="cbox" value="<%=""+k%>"></td>
<td><%= s%></td>
<td>
<core:if test="<%=actypvalue!=null%>">
<input type="text" name="actyp" value=<%=actypvalue%> onkeypress="return false;" size="7">
</core:if>

</td>
<td><input type="text" name="acnum" value="<%=String.valueOf(inopdata[k].getAccNo())%>" onkeypress="return false;" size="7"></td>
<td><input type="text" name="cid" value="<%=String.valueOf(inopdata[k].getCid())%>" onkeypress="return false;" size="7"></td>
<td><input type="text" name="name" value="<%=inopdata[k].getAccName()%>" onkeypress="return false;"></td>
<td><input type="text" name="acopendt" value="<%=inopdata[k].getAccOpenDate()%>" onkeypress="return false;" size="7"></td>
<td><input type="text" name="lasttrndt" value="<%=inopdata[k].getLastTrnDate()%>" onkeypress="return false;" size="7"></td>
<td><input type="text" name="acstatus" value="<%=inopdata[k].getAccStatus()%>" onkeypress="return false;" size="7"></td>
<td><input type="text" name="cl_bal" value="<%=String.valueOf(inopdata[k].getCloseBal())%>" onkeypress="return false;" size="7"></td>



</tr>

<%} %>
</table>
</div>
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