<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
    <%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
    
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositIntRepObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quarterly Interest Calculation</title>

<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
    <h2 class="h2">
      <center>Quarterly Interest Calculation</center></h2>

<script type="text/javascript">

function set(target){

document.forms[0].forward.value=target

};

</script>



</head>
<body>
<%!
    ModuleObject[] array_module;
	String[] combo_date=null;
	DepositIntRepObject[] dep_int_rep=null;
%>
<%

System.out.println("combo_date.length====");
dep_int_rep = (DepositIntRepObject[])request.getAttribute("quarterlyinterestcalc");
%>


<%combo_date=(String[])request.getAttribute("Combodate");
System.out.println("combo_date.length===="+combo_date);

%>

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
<table width="70%">
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/QuarterlyInterest?pageId=13008">
	
			     
<tr> 
		<td> 
		
		
		  <font style="font-style: normal;font-size:12px;">
			       <bean:message key="label.td_actype"></bean:message></font>
			       		    
			       <html:select property = "ac_type" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
			    	
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
			 
			    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
			   <td><html:submit onclick ="set('submit')"  styleClass="ButtonBackgroundImage">
			   <bean:message key="label.quarter_interest_payment" ></bean:message>
		  	   </html:submit>
              <%}else{ %>
              <html:submit onclick ="set('submit')"  styleClass="ButtonBackgroundImage" disabled="true">
			   <bean:message key="label.quarter_interest_payment" ></bean:message>
		  	   </html:submit>			   
		      <%} %>
		        <html:submit onclick ="set('view')" styleClass="ButtonBackgroundImage">
				<bean:message key="label.view1"></bean:message></html:submit>
		
		 	   	<html:submit property="but_clear" value="Clear" styleClass="ButtonBackgroundImage" onclick="set('clear')"></html:submit>
		 	    		          
		        		
				
				
				<bean:message key="label.date"></bean:message>
				<html:select property="combo_select" styleClass="ButtonBackgroundImage">
				
				 
				<%
				
				if(combo_date!=null){
					
				
				for(int i=0;i<combo_date.length;i++){
					
					System.out.println("combo_date==inside jsp="+combo_date);
				%>
				
				<html:option value="<%=combo_date[i]%>"><%=combo_date[i]%></html:option>
				<%}}else{
					System.out.println("combo_date==in else loop="+combo_date);
					%>
				<html:option value=""></html:option>
				<%}%>
				</html:select>
		    
		</td>		
				
</tr> 



<table>
<tr>
<td>
 
<%if(dep_int_rep!=null){
	
	%>
	<jsp:include page="QuartInt.jsp"></jsp:include>


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