
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.frontCounter.IntPayObject" %>
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
<h2 class="h2">Interest Register</h2></center></font>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    
      <link href="<%=request.getContextPath() %>/Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      
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
         IntPayObject[] intpayobj;
         double[] prod;
         Map user_role;
         String access;
    %>
    <% 
    	
    intpayobj=(IntPayObject[])request.getAttribute("intpayobj");
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
    <core:if test="<%= closingmsg!=null%>">
    <font color="red" size=3><%=request.getAttribute("closingmsg")%></font>
    </core:if>
    
     <html:form action="/FrontCounter/InterestRegister?pageId=3048" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<br><br>
     	<core:if test="<%= closingmsg==null%>">
     	<html:button property="Print" onclick="window.print()" value="Print" styleClass="ButtonBackgroundImage"></html:button>
     	 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
     	<html:submit value="Download" styleClass="ButtonBackgroundImage"></html:submit>
     	<%}else{ %>
     	<html:submit value="Download" styleClass="ButtonBackgroundImage" disabled="true"></html:submit>
     	<%} %>
     	</core:if>
     	<br><br><br>
     	<core:if test="<%=intpayobj!=null %>">
         <table border="1">
         <tr bgcolor="#0099CC">
         <td>Sr No.</td>
         <td>A/C No</td>
         <td>Name</td>
         <td>prod1</td>
         <td>prod2</td>
         <td>prod3</td>
         <td>prod4</td>
         <td>prod5</td>
         <td>prod6</td>
         <td>IntRate</td>
         <td>Int Amt</td>
         <td>Cal By</td>
         </tr>
         <%for(int i=0;i<intpayobj.length;i++){ %>
         <tr>
         <td><%=i %></td>
         <td><%=intpayobj[i].getAccNo() %></td>
         <td><%=intpayobj[i].getName() %></td>
         <%prod=intpayobj[i].getProducts(); 
         for(int j=0;j<prod.length;j++){
         %>
         <td><%=prod[j] %></td>
         
         <%} %>
         <td><%=intpayobj[i].getIntRate() %></td>
         <td><%=intpayobj[i].getIntAmt() %></td>
         <td><%=intpayobj[i].getCalBy() %></td>
         </tr>
         
         <%} %>
         </table>
         </core:if>
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