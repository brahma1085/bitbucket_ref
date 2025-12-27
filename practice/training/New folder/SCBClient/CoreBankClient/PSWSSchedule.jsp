<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>  
<%@page import="java.util.Map"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.loans.PSWSObject"%>
  

<html>
<head>

<script type="text/javascript">
function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
   



function fun_button(target)
{

   		if(document.getElementById("txt_priorityseccode").value=="0")
	{
		alert('Enter priority Sector code!');
		document.getElementById("txt_priorityseccode").focus();
		return false;
		}
		if(document.getElementById("txt_priorityseccode").value=="")
	{
		alert('Enter Text priority Sector code!');
		document.getElementById("txt_priorityseccode").focus();
		return false;
		}
else{
document.forms[0].button_value.value=target;
document.forms[0].submit();
//return true;
}
	//alert(target);
	
	
	
}

function fun_delete(target)
{
	if(target=="Delete")
	{
		var value=confirm("Do You Want Delete");
		if(value==true)
		{
			document.forms[0].button_value.value=target;
		
		}
		else
		{
			return false;
		}
	}

}

function only_Numbers()
{
   var cha =   event.keyCode;
		if ( (cha >= 48 && cha <= 57 ) ) 
 		{
	    	return true;
   		} 
   		else 
   		{
		    return false;
   		}
	   }; 
function fun_Validation()
{
	
		if(document.getElementById("delete_value").value!=null)
		{
			if(document.getElementById("delete_value").value=="Deleted")
			{
				alert("Deleted Sucessfully");
				return false;
			}
			else if(document.getElementById("delete_value").value=="CannotDelete")
			{
				alert("Can Not Be Deleted");
				return false;
			}	
		}
}

</script>
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
<h2 class="h2"><center>PSWS Schedule Report</center></h2>
</head>
<body class="Mainbody" onload="fun_Validation()">
<%String message=(String)request.getAttribute("msg"); %>



<%! String processdates[]; %>
<%! PSWSObject Pswssheduledetail[]; %>


<%processdates=(String[])request.getAttribute("processdates");

Pswssheduledetail=(PSWSObject[])request.getAttribute("Pswssheduledetail"); 

System.out.println("PrioritySector==============>"+Pswssheduledetail);
%>
<%!
String access;
String  accesspageId;
 Map user_role;
%>
<%
  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");
System.out.println("11111111"+user_role);
System.out.println("222222222"+accesspageId);
if(user_role!=null&&accesspageId!=null){
System.out.println("11111111"+access);
			if(user_role.get(accesspageId)!=null){
			System.out.println("222222222"+access);
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}
			}
// Note:access=1111==>read,insert,delete,update
%>

<%if(message!=null){ %>
<div id = "div1" class = "myLayersClass">
    
    <core:if test="<%=message!=null %>">
    <font color="red"><%=message %></font>
    <br><br><br><br>
    </core:if>
    </div>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:form action="/Loans/PSWSSchedule?pageidentity.pageId=5036">
	
	
	
		 <table>
			<tr>
			<td>
			<table>
				<tr>
				<td align="right"><bean:message key="label.repdate"></bean:message></td>
				<td><html:select  property="txt_repdate">
					<%if(processdates!=null){ %>
					<%for(int i=0;i<processdates.length;i++){ %>
					<html:option value="<%= ""+processdates[i] %>"><%= ""+processdates[i] %></html:option>
					<%} }%>
					</html:select>
				</td>
				</tr>
				</table>
				</td>
			</tr>
			
			<tr>
			<td>
			<table>
				<tr>
				<td align="right"><bean:message key="label.priorityseccode"></bean:message></td>
				<td><html:text  property="txt_priorityseccode" size="10" onkeypress="return only_Numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
				<td><html:button property="priority" styleClass="ButtonBackgroundImage" value="PrioritySector" onclick="fun_button('PrioritySector')" ></html:button></td>
				<td><html:button property="Weaker" styleClass="ButtonBackgroundImage" value="WeakerSector" onclick="fun_button('WeekerSector')"></html:button></td>
				<td><html:button property="but_print" onclick="fun_button('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
				<td><html:submit styleClass="ButtonBackgroundImage" onclick="fun_delete('Delete')" value="Delete"></html:submit></td>
				<%}else{ %>
				<td><html:button property="priority" styleClass="ButtonBackgroundImage" value="PrioritySector" disabled="true"></html:button></td>
				<td><html:button property="Weaker" styleClass="ButtonBackgroundImage" value="WeakerSector" disabled="true"></html:button></td>
				<td><html:button property="but_print" disabled="true" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
				<td><html:submit styleClass="ButtonBackgroundImage" disabled="true" value="Delete"></html:submit></td>
				<%} %>
				</tr>
				</table>
				</td>
			</tr>
			
			<html:hidden  property="button_value" value="erroer"/>
			<html:hidden  property="delete_value" styleId="delete_value"/>
			<html:hidden property="testing" styleId="totaltesting"></html:hidden>
			
			
			<tr>
				<td>
					<div  id = "table1" style="display: block;overflow: scroll;width: 600px;height: 250px">
		           <display:table name="PSWSSchedule" id="PSWSSchedule" class="its"  >
		          	<display:column title="Loan A/C No" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${PSWSSchedule.LoanACNo}" size="5" /></display:column>
					<display:column title="Name" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSSchedule.Name}"  /></display:column>
					<display:column title="Limit Sactioned" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="formTextField" value="${PSWSSchedule.LimitSactioned}" /></display:column>
					<display:column title="Amount Advanced" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${PSWSSchedule.AmountAdvanced}" /></display:column>
					<display:column title="Balance OutStanding" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSSchedule.BalanceOutStanding}" /></display:column>
					<display:column title="Amount Over due" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSSchedule.AmountOverdue}" /></display:column>
					<display:column title="Limit Sactioned" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSSchedule.LimitSactioned}" /></display:column>
					<display:column title="Amount Advanced" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${PSWSSchedule.AmountAdvanced}" /></display:column>
					<display:column title="Amount Advanced" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSSchedule.BalanceOutStanding}" /></display:column>
					<display:column title="Balance OutStanding" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSSchedule.AmountOverdue}" /></display:column>
		          </display:table>
		          </div>
				</td>
			</tr>
			
		 </table>
	</html:form>
	<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
</body>
</html>