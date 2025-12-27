<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %> 
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %> 
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="masterObject.termDeposit.DepositReportObject"%>
<%@page import="java.util.Map"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Period wise Report</title>

<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
</style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
    <font color="blue" ><center>
<h2 class="h2">PeriodWise Report</h2></center></font>
          
<script type="text/javascript" language="javascript">



function set(target)
{
  
  document.forms[0].forward.value=target;
  document.forms[0].submit();

}

function HideShow()
{

if(document.getElementById("totaltesting").value!=0)
	{
		
		alert(document.getElementById("totaltesting").value);	
		
			
	}
	
	
	var ele=document.forms[0].elements;
	for(var i=0;i<ele.length;i++)
	{
	if(ele[i].type=="hidden")
	{
	ele[i].value="";
	}
	}
}

</script>




</head>
<body onload="HideShow()">

<%!
	ModuleObject[] array_module;
	DepositMasterObject[] dep_mast;
	DepositReportObject[] dep_rate;
	DepositMasterObject[] combo_period;
%>
<%	dep_mast = (DepositMasterObject[])request.getAttribute("periodwise");
    if(dep_mast!=null){
	System.out.println("geetha inside period wise report.."+dep_mast.length);
    }
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
<%
	
	System.out.println("geetha in period wise report1212112..");
	combo_period=(DepositMasterObject[])request.getAttribute("periodlimit");
	System.out.println("combo_period.length===="+combo_period.length);
	dep_rate=(DepositReportObject[])request.getAttribute("DEPRATE");
	String table=(String)request.getAttribute("table");
	System.out.println("DepRate is===="+dep_rate);
%>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/PeriodwiseReport?pageId=13013" >
<html:hidden property="testing" styleId="totaltesting"></html:hidden>
<html:hidden property="forward" ></html:hidden>
<table>

<tr>
		   		<td><font style="font-style: normal;font-size:12px;">
			       <bean:message key="label.td_actype"></bean:message></font>
			    
			    <html:select property = "ac_type" onchange="submit()" style="background-color:silver">
			    	<html:option value="Select" >Select</html:option>
			    	<% array_module=(ModuleObject[])request.getAttribute("Dep type");
			    			
			    	for(int i=0;i<array_module.length;i++)
			    			{
			        		 	System.out.println("acctype"+ array_module);
			    	%>	    	
			    				<html:option  value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%
			    			}
			    	%>	
			    	 </html:select>
			    	 
			    	 <font style="font-style: normal;font-size:12px;">
			         <bean:message key="label.Processing_date"></bean:message></font>
			     	
			         		         
			         <html:text property="process_date" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>">
			     
			     </html:text>
    		   	 </td>
</tr>
<tr>
</tr>
<tr>
				<td>
			
				<font style="font-style: normal;font-size:12px;">
			   		 <bean:message key="label.period_of_years"></bean:message></font>
			   	
			   <html:select property="combo_periodyears"   >
			   
			   
			   <%
				
				if(dep_rate!=null){%>
					
					
				<%for(int i=0;i<dep_rate.length;i++){
					
					System.out.println("combo_period==inside jsp="+combo_period.length);
					System.out.println("dep_rate[i].getLmt_hdg()="+dep_rate[i].getScrollno());
					%>
				
				<html:option value="<%=""+dep_rate[i].getScrollno()%>"><%=""+dep_rate[i].getLmt_hdg() %></html:option>
				<%}}else{
					System.out.println("combo_period==in else loop="+combo_period.length);
					%>
				<html:option value=""></html:option>
				<%}%>
				</html:select>
			   
			   
			   
			   
			       </td>
</tr>
<tr>

</tr>

<tr>
<td>
<center>
                
				
				<input type="button" name="but_view" onclick="set('View')" value="View" class="ButtonBackgroundImage"/>
				<input type="button" name="DownLoad" onclick="set('DownLoad')" value="DownLoad" class="ButtonBackgroundImage"/>
</center>				
</td>
</tr>
<td>
</table>
<core:if test="<%=table!=null %>">

<hr>
<div id = "table1"  style="overflow: scroll;width:750px;height: 200px;display: block">
<%if(dep_mast!=null){
	
	   System.out.println("jsp===> "+dep_mast[0].getAccNo());
		System.out.println("jsp===> "+dep_mast[0].getDepositAmt());
		System.out.println("jsp===> "+dep_mast[0].getMaturityDate());
         
if(combo_period!=null){
	
%>
<display:table name="periodwise"  id="currentRowObject" class="its" sort="list" requestURI="/TermDeposit/PeriodwiseReport.do" pagesize="100">

<display:column property="accNo" style="background-color:#FFCCFF"></display:column>
<display:column property="name" style="background-color:#FFCCFF" title ="Depositor Name"></display:column>
<display:column property="depDate" style="background-color:#FFCCFF" title ="Deposit Date"></display:column>
<display:column property="maturityDate" style="background-color:#FFCCFF"></display:column>
<display:column property="depositAmt" style="background-color:#FFCCFF"></display:column>
<display:column property="maturityAmt" style="background-color:#FFCCFF"></display:column>
<display:column property="interestPaid" style="background-color:#FFCCFF"></display:column>
<display:column property="cumInterest" style="background-color:#FFCCFF"></display:column>






</display:table>

<%}} %>
</div>
</core:if>






</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>


</body>
</html>