<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<html>
<head>
<title></title>
	<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
	<h2 class="h2"><center>Pygmy Interest Calculation</center></h2>
   
  
    <script type="text/javascript">
         function set(target){
         	document.forms[0].forward.value=target;
         	
         };
         
         function intialFocus(){
         document.forms[0].agentnum.focus();
         }
         
         function chk(){
          var val=document.forms[0].valid.value;
          
          if(val==""){
          		return false;
          }
          else{
          		alert(val);
          }		
         
         }
         
   </script>

</head>
<body class="Mainbody">
<%@page import="java.util.Map"%>


<%!
String access;
String  accesspageId;
 Map user_role;

%>

<%

  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");

if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}
%>
<% String msg=(String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<font color="red"><%=msg %></font>
		<br><br>
		<%} %>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:form action="/Pygmy/PygmyInterestCal?pageid=8011">
	
	<table class="txtTable">
		<tr>
			<html:hidden property="valid" ></html:hidden>
			<html:hidden property="forward" value="error"/>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            <td><html:submit onclick="set('Calculate Interest')"  value="Calculate Interest" styleClass="ButtonBackgroundImage">Calculate Interest</html:submit></td>
             <%}else{ %>
            <td><html:submit onclick="set('Calculate Interest')"  value="Calculate Interest" disabled="true" styleClass="ButtonBackgroundImage">Calculate Interest</html:submit></td>
             <%} %>
             
                 
		</tr>
	</table>		
	




	</html:form>
	<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>