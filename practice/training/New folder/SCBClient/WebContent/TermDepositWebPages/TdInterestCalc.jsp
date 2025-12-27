<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
    <%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
	<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
	<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositIntRepObject"%>
<%@page import="java.util.Map"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">`
<title>Deposit Interest Calculation </title>

<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />

   
    <h2 class="h2">
      <center>Interest Calculation</center></h2>


<script type="text/javascript">
function set(target)
{

document.forms[0].forward.value = target

};

</script>



</head>
<body>
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

// Note:access=1111==>read,insert,delete,update
%>

<table>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/InterestCalculation?pageId=13002">
	
	<%!

ModuleObject[] array_module;

DepositIntRepObject[] dep_rep_obj;

%>

<%
dep_rep_obj = (DepositIntRepObject[])request.getAttribute("interestcalculation");
%>
			     
<tr> 
<td></td>
		<td> 
		
		   
			       <bean:message key="label.td_actype"></bean:message>
			       		    
			       <html:select property = "ac_type" styleClass="formTextFieldWithoutTransparent">
			    	
			    	 <% array_module = (ModuleObject[])request.getAttribute("Dep type");
			    	
			    			for(int i=0;i<array_module.length;i++)
			    			{
			        		 	System.out.println("acctype===="+ array_module);
			    	%>	    	
			    				<html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%
			    			}
			    	%>	
			    	 </html:select>
		
		       <html:hidden property="forward" value="error"></html:hidden>
			    
			
			   <td>
			   <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
			   <html:submit onclick ="set('submit')" styleClass="ButtonBackgroundImage">
			   <bean:message key="label.interest_payment"></bean:message>
			   </html:submit>
			     <%}else{ %>
			     <html:submit onclick ="set('submit')" styleClass="ButtonBackgroundImage" disabled="true">
			   <bean:message key="label.interest_payment"></bean:message>
			   </html:submit>
			     <%} %>
		      	 	
		        <html:submit onclick ="set('view')" styleClass="ButtonBackgroundImage">
				<bean:message key="label.view1"></bean:message></html:submit>
		
		
		  		
		    
		      
		</td>		
				
</tr> 

	
<table>
<tr>
<td>
 
<%if(dep_rep_obj!=null){
	
	%>
	<jsp:include page="TdIntDisplay.jsp"></jsp:include>


<%} %>
</td>
</tr>
</table>


</html:form>

<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</table>

</body>
</html>