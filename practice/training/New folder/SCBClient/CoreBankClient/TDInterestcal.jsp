<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
    <%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
	<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
	<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositIntRepObject"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
<style type="text/css">
      body{
      font-size:8px;
      font-family:serif;
      font-style:oblique;
      font-weight:bold;
      background:beige;
    }
    table{
     background: transparent;
     }
     tr{
     background: transparent;
     }
     td{
     background: transparent;
     }
    
</style>
<script type="text/javascript">
function set(target)
{

document.forms[0].forward.value = target

};
function validate()
{
if(document.getElementById("totaltesting").value!=0)
{
alert(document.getElementById("totaltesting").value)
}
}
</script>



</head>
<body onload="validate()">
<%!

ModuleObject[] array_module;

DepositIntRepObject[] dep_rep_obj;

%>

<%
dep_rep_obj = (DepositIntRepObject[])request.getAttribute("interestcalculation");
%>
<table>

<html:form action="/TermDeposit/InterestCalculation?pageId=13002">
<html:hidden property="testing" styleId="totaltesting"/>	
			     
<tr> 
<td></td>
		<td> 
		
		   <font style="font-style: normal;font-size:12px;">
			       <bean:message key="label.td_actype"></bean:message></font>
			       		    
			       <html:select property = "ac_type" style="background-color:silver">
			    	
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
			    
			   
			    
			    
			    
			   <td><html:submit onclick ="set('submit')"  styleClass="ButtonBackgroundImage">
			   <bean:message key="label.interest_payment" ></bean:message>
			   </html:submit>
			   
		      	 	
		        <html:submit onclick ="set('view')"  styleClass="ButtonBackgroundImage">
				<bean:message key="label.view1" ></bean:message></html:submit>
		
		
		  		<html:button property="but_print" styleClass="ButtonBackgroundImage" onclick="window.print()">
				<bean:message key="label.print" ></bean:message></html:button>
		    
		      
		</td>		
				
</tr> 

	

<table>
<tr>
<td>
 
<%if(dep_rep_obj!=null){
	
	%>
	<jsp:include page="/TermDepositWebPages/IntDisplay.jsp"></jsp:include>


<%} %>
</td>
</tr>
</table>


</html:form>
</table>

</body>
</html>