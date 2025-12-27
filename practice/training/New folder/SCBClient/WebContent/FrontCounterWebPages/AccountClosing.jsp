
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@page import="java.util.Map"%>
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
<h2 class="h2">Account Closing</h2></center></font>
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
    function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only "+size+"digits allowed")
         		txt.value="";
         		return false;
          	}
         }
    
    function settingval(){
    document.forms[0].hidvar.value='submit';
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
         Map user_role;
         String access;
    %>
    <% String disabling=(String)request.getAttribute("disabling");
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	String accesspageId=(String)request.getAttribute("accesspageId");
    	user_role=(Map)request.getAttribute("user_role");
    	
    	
    	if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}
    %>
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
    <core:if test="${one!=null}">
    <core:if test="<%= one.equals("true")%>">
       <font color="red"><bean:message key="label.empty"></bean:message></font>
       <%System.out.println("i am here"); %>
    </core:if>
    </core:if>
    
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=request.getAttribute("closingmsg")%></font>
    </core:if>
    
     <html:form action="/FrontCounter/AccountClosing?pageId=3007" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	<html:hidden property="hidvar"/>
     	<html:hidden property="pageId" />
     	<div id="tabs">
     	</div>
     	<br><br><br>
            <table class="txtTable" style="border:thin solid navy">
                <tr >
                    <td ><bean:message key="label.acType"></bean:message></td>
                    <td ><html:select property="acType" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                    </td>
                </tr>
                <tr >
                	<td ><bean:message key="label.acNum"></bean:message></td>
                	<core:choose>
                	<core:when test="${empty requestScope.AccountDetails}">
                			<td>
                				<html:text property="acNum" styleClass="formTextFieldWithoutTransparent" size="10"  onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text>
                				<%Boolean acntNotFound=(Boolean)request.getAttribute("AccountNotFound");
                				  Boolean newAcnt=(Boolean)request.getAttribute("NewAccount");	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
                				%>
                				<core:if test="<%= acntNotFound!=null %>">
                					<bean:message key="label.acntNotFound"></bean:message>
                					<%System.out.println("i am here"); %>
                				</core:if>
                				<core:if test="<%=newAcnt!=null %>">
                					<bean:message key="label.newAccount"></bean:message>
                				</core:if>
                			</td>
                		</core:when>
                		<core:otherwise>
                			<td >
                				<html:text property="acNum" styleClass="formTextFieldWithoutTransparent"  size="8" onchange="callme()" value="${requestScope.AccountDetails.accNo}"></html:text>
                			</td>
                		</core:otherwise>
                	</core:choose>
                </tr>
                <tr>
                <td>
                <%if(disabling==null){%>
                <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
                <html:button property="but" value="Close" onclick="settingval()"  styleClass="ButtonBackgroundImage"></html:button>
                <%}else{ %>
                <html:button property="but" value="Close" onclick="settingval()"  styleClass="ButtonBackgroundImage" disabled="true"></html:button>
                <%}}else{ %>
                
                 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
                <html:button property="but" value="Close" onclick="settingval()" styleClass="ButtonBackgroundImage"></html:button>
                <%}else{ %>
                <html:button property="but" value="Close" onclick="settingval()" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
                <%}} %>
                </td>
                </tr>
                </table>
                </html:form>
                <%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
            <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
  </html>          