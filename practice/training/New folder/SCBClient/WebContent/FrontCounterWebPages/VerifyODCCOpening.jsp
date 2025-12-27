
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
<%@page import="masterObject.frontCounter.ODCCMasterObject"%>
<%@page import="com.scb.fc.forms.AccountCloseActionForm"%>
<html>
  <head><title>AccountOpening</title>
     <font color="blue" ><center>
<h2 class="h2">OD/CC Verification</h2></center>
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
    document.forms[0].hidvar.value='verify';
    document.forms[0].submit();
    
    }
    
    
    function clearAll(){
    var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    if(ele[i].type=="text"){
    ele[i].value="";
    }
    if(ele[i].type=="hidden"){
    ele[i].value="";
    }
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
    <% String disabling=(String)request.getAttribute("disabling");
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	ODCCMasterObject odccmaster=(ODCCMasterObject)request.getAttribute("odccmasterobj");
    	request.setAttribute("odccmasterobj",odccmaster);
    	request.setAttribute("odccmaster",odccmaster);
    	
    	String[][] unverified = (String[][]) request
    			.getAttribute("unverified");
    %>
    <core:if test="${one!=null}">
    <core:if test="<%= one.equals("true")%>">
       <font color="red"><bean:message key="label.empty"></bean:message>
       <%System.out.println("i am here"); %>
    </core:if>
    </core:if>
    
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=request.getAttribute("closingmsg")%>
    </core:if>
    
     <html:form action="/FrontCounter/VerifyODCCOpen?pageId=3054" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	<html:hidden property="hidvar"/>
     	<html:hidden property="pageId" />
     	<div id="tabs">
     	</div>
     	<br><br><br>
     	<table><tr><td>
           <table style="border:thin solid navy" class="txtTable">
	<tr>
		<td>Account Type:</td>
			<td><html:select property="acType" styleClass="formTextFieldWithoutTransparent" onblur="submit()">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}--${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select></td>
	</tr>
		<tr>
		<td>Account No:</td>
			<td>
			<html:text property="ac_no" size="20" onblur="submit()"></html:text></td>
	</tr> 
	
	</table>
	
	
	<br><br>
	
		<table style="border:thin solid navy;" class="txtTable">
	<tr>
	         
		<td>Share Type :</td>
		<td>
		<html:select property="combo_share_type" styleClass="formTextFieldWithoutTransparent">
                        
                        <core:forEach var="sh" varStatus="count" items="${requestScope.sh}" >
                        	    <html:option value="${sh.moduleCode}" ><core:out value="${sh.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
		</td>
	</tr>
	<tr>
	         
		<td>Share No:</td>
		<td>
		<html:text property="txt_sh_type" onblur="submit()"></html:text>
		</td>
	</tr>
	<tr>
		<td>Name :</td>
		<td>
		<html:text property="name" size="30"></html:text>
		</td>
	</tr>
	<tr>
		<td>Details :</td>
		<td>
		<html:select property="details" onblur="submit()">
		<html:option value="SELECT">SELECT</html:option>
		<html:option value="PersonalDetails">PersonalDetails</html:option>
		<!--<html:option value="Loan&ShareDetails">Loan&ShareDetails</html:option>
		--><html:option value="ReceiptDetails">ReceiptDetails</html:option>
		<html:option value="Application">Application</html:option>
		
		
		<html:option value="SignatureInst">SignatureInst</html:option>
		
		<html:option value="EmploymentDetails">EmploymentDetails</html:option>
		
		<html:option value="DepositDetails">DepositDetails</html:option>
		
		</html:select>
		</td>
	</tr>
</table>
</td><td>
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
<tr><td>
<br><br>
<html:button property="but" onclick="setval()" value="Verify"  styleClass="ButtonBackgroundImage"></html:button>
<html:button property="but2" onclick="clearAll()" value="Clear"  styleClass="ButtonBackgroundImage"></html:button>
       
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
        
                </html:form>
                
  </body>
  </html>          