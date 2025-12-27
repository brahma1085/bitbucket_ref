<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html"  uri="/WEB-INF/struts-html.tld"%> 
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>   
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
 <%@page import="java.util.Map"%>   
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>AutoRenewal</title>
<center><h2 class="h2">AutoRenewal</h2></center>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">

function setacno(){

if(document.forms[0].auto_acno.value!=0)
	{
		alert("AutoRenewed Sucessully and Ac No is "+document.forms[0].auto_acno.value);
	}

}

</script>


</head>
<body class="Mainbody()"  onload="setacno()">

<%
List auto_renewal;

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
System.out.println("R u getting class cast exception?????????????????????????????");
auto_renewal = (ArrayList)request.getAttribute("autorenewal");
System.out.println("After Requesting@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
%>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/AutoRenewal?pageId=13022" focus="<%=""+request.getAttribute("getfocus")%>">

<table  class = "txtTable"  cellspacing="3">
	
	
 <td>
	<table class = "txtTable" width="20%" cellspacing="3">
		 	
   		 <html:hidden property="auto_acno" /> 				   
</table>			   
<table>	
		
		   
		<tr>
			 <td>
			 <input type="submit" value="AutoRenewal" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=submit'" />
			 
			 </td>
		</tr>
	
</table>
<table>
<tr>

<td>
<div id = "table1" style="display:block; overflow:scroll; width: 650px;height: 200px">


<%if(auto_renewal!=null){
	
	System.out.println("Inside display tag------------------AUTORENEWAL");
	
%>


<display:table name="autorenewal" list="autorenewal"  id="autorenewal"  class="its" >
   			 
  <display:column title="Select"><input type="checkbox" name="id" value="${autorenewal.id}"></display:column> 			 
   
<display:column title="acc_no">

  <input type="text" value="${autorenewal.acc_no}"  name="acc_no" readonly="readonly">  

 </display:column>
 
<display:column title="Ac Type"><input type="text" name="ac_type" value="${autorenewal.ac_type}" readonly="readonly" ></display:column>


 
<display:column title="Max Renewal Times"><input type="text" name="dep_amt" value="${autorenewal.max_renewal_times}" readonly="readonly" ></display:column>

<display:column title="Renewal Times"><input type="text" name="Interest_date" value="${autorenewal.renewal_times}" readonly="readonly" ></display:column>

<display:column title="Deposit date"><input type="text" name="dep_date" value="${autorenewal.dep_date}" readonly="readonly" ></display:column>

<display:column title="Maturity Date"><input type="text" name="mat_date" value="${autorenewal.mat_date}" readonly="readonly" ></display:column>

<display:column title="Period of Days"><input type="text" name="period_of_days" value="${autorenewal.period_of_days}" readonly="readonly" ></display:column>




<display:column title="Renewal Instruction"><input type="text" name="Interest_date" value="${autorenewal.Interest_date}" readonly="readonly" ></display:column>

remark

<display:column title="Remark"><input type="text" name="remark" value="${autorenewal.remark}" readonly="readonly" ></display:column>


  
</display:table>

<%} %>	
</div>
</td>
</tr>		   

</table>			   
	
 
		<table>
		
			<tr>
			 <td>
			 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
			 <input type="submit" value="SUBMIT" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=submit'" />
			 <%}else{ %>
			 <input type="submit" value="SUBMIT" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=submit'" disabled="true" />
			<%} %>
			 </td>
		</tr>
	
		
		</table>	   


</td>

 
</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>


</body>