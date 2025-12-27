<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.NPAObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
<script type="text/javascript">
function fun_npa_success()
{
	if(document.getElementById("npa_success").value==true)
	{
		alert("The values are successfully updated");
	}
}
function fun_clear()
{
	
	var ele=document.forms[0].elements;
	for(var i=0;i<ele.length;i++)
	{
		
		if(ele[i].type=="text")
		{
			ele[i].value="";
		}
	}
	
}
function fun_check()
{
	
	document.forms[0].txt_fromdaymonth.value="Days";
	document.forms[0].txt_todaymonth.value="Days";
	document.forms[0].txt_fromdaymonth1.value="Days";
	document.forms[0].txt_todaymonth1.value="Month";
	document.forms[0].txt_fromdaymonth2.value="Month";
	document.forms[0].txt_todaymonth2.value="Month";
	document.forms[0].txt_fromdaymonth3.value="Month";
	document.forms[0].txt_todaymonth3.value="Month";
	document.forms[0].txt_fromdaymonth4.value="Month";
	document.forms[0].txt_todaymonth4.value="Month";
	document.forms[0].submit();
	
	if(document.getElementById("valid_values").value!=null)
	{	
		
		if(document.getElementById("valid_values").value=="NoValues")
		{
			alert("No values has been set for the given account type ");
			fun_clear();
			return false;
			
		}
	}
}


function fun_vary()
{
	document.forms[0].txt_fromdaymonth.value="Month";
	document.forms[0].txt_todaymonth.value="Month";
	document.forms[0].txt_fromdaymonth1.value="Month";
	document.forms[0].txt_todaymonth1.value="Month";
	document.forms[0].txt_fromdaymonth2.value="Month";
	document.forms[0].txt_todaymonth2.value="Month";
	document.forms[0].txt_fromdaymonth3.value="Month";
	document.forms[0].txt_todaymonth3.value="Month";
	document.forms[0].txt_fromdaymonth4.value="Month";
	document.forms[0].txt_todaymonth4.value="Month";

}

function fun_add()
{
	
	document.forms[0].txt_from1.value=(document.forms[0].txt_to.value);
	document.forms[0].txt_from1.value++;
	
	
	document.forms[0].txt_from2.value=(document.forms[0].txt_to1.value);
	document.forms[0].txt_from2.value++;
	
	
	document.forms[0].txt_from3.value=(document.forms[0].txt_to2.value);
	document.forms[0].txt_from3.value++;
	
	
	document.forms[0].txt_from4.value=(document.forms[0].txt_to3.value);
	document.forms[0].txt_from4.value++;
	
}


function fun_button(target)
{
alert(target);
document.forms[0].but_val.value=target;

}


function only_Num()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 48 && cha <=57) ) 
 		{
   			return true;
       		} 
		else 
       		{
   			return false;
       		}
	};
	
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
<h2 class="h2"><center>NPA ADMIN</center></h2>
</head>
<body onload="fun_npa_success()">
<%System.out.println("This is NPA Admin Page"); %>
<%! ModuleObject laon_moduleobj[]; %>
<%NPAObject[] NpaObject;%>
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

<%laon_moduleobj=(ModuleObject[])request.getAttribute("laon_moduleobj"); %>
<%NpaObject=(NPAObject[])request.getAttribute("NpaObject"); %>
<%System.out.println("Npa object in jsp===>"+NpaObject); %>


   	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/NPAAdmin?pageidentity.pageId=5092">
	<table>
		<tr>
			<td align="right"><bean:message key="label.acctype"/>
				<html:select property="txt_acctype" onchange="return fun_check()" >
				<%if(laon_moduleobj!=null){ 
				for(int i=0;i<laon_moduleobj.length;i++){%>
					<html:option value="<%= ""+laon_moduleobj[i].getModuleCode() %>"><%=""+laon_moduleobj[i].getModuleAbbrv()%></html:option>
				<%}} %>
				</html:select></td>
			
			
			<td><bean:message key="label.select"/>
				<html:select property="txt_tab180">
					<html:option value="1">Table180</html:option>
					<html:option value="2">Table90</html:option>
				</html:select>
			</td>
		</tr>
		
		<tr>
			<th></th>
			<th>From</th>
			<th>FromDay/Month</th>
			<th>To</th>
			<th>To Day/Month</th>
			<th>Prov Amt</th>
		</tr>
		
		<tr>
			
			<%if(NpaObject!=null){ %>
			<td align="right"><html:text property="txt_standard" readonly="true" value="<%=""+NpaObject[0].getAssetDesc() %>" styleClass="formTextField"></html:text></td>
			<td><html:text property="txt_from" size="10" readonly="true" value="<%=""+ NpaObject[0].getDaysFrom() %>"></html:text></td>
			<td><html:select property="txt_fromdaymonth" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_to" size="10"  value="<%=""+ NpaObject[0].getDaysTo()%>" onchange="fun_add()" onkeypress="return only_Num()"></html:text></td>
			<td><html:select property="txt_todaymonth" onchange="return fun_vary()">
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt" size="10" value="<%=""+NpaObject[0].getProvPerc()%>" onkeypress="return only_Num()"></html:text></td>
			<%} else {%>
			<td align="right"><bean:message key="label.standard"/></td>
			<td><html:text property="txt_from" size="10" readonly="true"></html:text></td>
			<td><html:select property="txt_fromdaymonth" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			
			<td><html:text property="txt_to" size="10" ></html:text></td>
			<td><html:select property="txt_todaymonth">
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt" size="10"></html:text></td>
			<%}%>
			
		</tr>
		
		
			
		<tr>
			
			<%if(NpaObject!=null){ %>
			<td align="right"><html:text property="txt_substandard" readonly="true" value="<%=""+NpaObject[1].getAssetDesc() %>" styleClass="formTextField"></html:text></td>
			<td><html:text property="txt_from1" size="10" readonly="true" value="<%=""+ NpaObject[1].getDaysFrom() %>"></html:text></td>
			<td><html:select property="txt_fromdaymonth1" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>	
				</html:select></td>
			<td><html:text property="txt_to1" size="10"  value="<%=""+ NpaObject[1].getMonthsTo()%>" onblur="fun_add()" onkeypress="return only_Num()"></html:text></td>
			<td><html:select property="txt_todaymonth1" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>	
			</html:select></td>
			<td><html:text property="txt_provamt1" size="10" value="<%=""+NpaObject[1].getProvPerc()%>" onkeypress="return only_Num()"></html:text></td>
			<%} else { %>
			<td align="right"><bean:message key="label.substandard"/></td>
			<td><html:text property="txt_from1" size="10" readonly="true"></html:text></td>
			<td><html:select property="txt_fromdaymonth1" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>	
			</html:select></td>
			<td><html:text property="txt_to1" size="10" ></html:text></td>
			<td><html:select property="txt_todaymonth1" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>	
			</html:select></td>
			<td><html:text property="txt_provamt1" size="10"></html:text></td>
			<%} %>
			
		</tr>
		
		<tr>
			
			<%if(NpaObject!=null){ %>
			<td align="right"><html:text property="txt_default1" readonly="true" value="<%=""+NpaObject[2].getAssetDesc() %>" styleClass="formTextField"></html:text></td>
			<td><html:text property="txt_from2" size="10" readonly="true" value="<%=""+ NpaObject[2].getMonthsFrom() %>" ></html:text></td>
			<td><html:select property="txt_fromdaymonth2"  >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>		
			</html:select></td>
			<td><html:text property="txt_to2" size="10"  value="<%=""+ NpaObject[2].getMonthsTo()%>" onblur="fun_add()" onkeypress="return only_Num()"></html:text></td>
			<td><html:select property="txt_todaymonth2" >
					<html:option value="Days">Days</html:option>
					<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt2" size="10" value="<%=""+NpaObject[2].getProvPerc()%>" onkeypress="return only_Num()"></html:text></td>
			<%}else{ %>
			<td align="right"><bean:message key="label.default1"/></td>
				<td><html:text property="txt_from2" size="10" readonly="true"></html:text></td>
				<td><html:select property="txt_fromdaymonth2"  >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>		
			</html:select></td>
			<td><html:text property="txt_to2" size="10" ></html:text></td>
			<td><html:select property="txt_todaymonth2" >
					<html:option value="Days">Days</html:option>
					<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt2" size="10"></html:text></td>
			<%} %>
			
		</tr>
		
		<tr>
			
			<%if(NpaObject!=null){ %>
			<td align="right"><html:text property="txt_default2" readonly="true" value="<%=""+NpaObject[3].getAssetDesc() %>" styleClass="formTextField"></html:text></td>
			<td><html:text property="txt_from3" size="10" readonly="true" value="<%=""+ NpaObject[3].getMonthsFrom() %>"></html:text></td>
			<td><html:select property="txt_fromdaymonth3"  >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_to3" size="10" value="<%=""+ NpaObject[3].getMonthsTo()%>" onblur="fun_add()" onkeypress="return only_Num()"></html:text></td>
			<td><html:select property="txt_todaymonth3" >
					<html:option value="Days">Days</html:option>
					<html:option value="Month">Month</html:option>
				</html:select></td>
			<td><html:text property="txt_provamt3" size="10" value="<%=""+NpaObject[3].getProvPerc()%>" onkeypress="return only_Num()"></html:text></td>
			<%}else { %>
			<td align="right"><bean:message key="label.default2"/></td>
			<td><html:text property="txt_from3" size="10"  ></html:text></td>
			<td><html:select property="txt_fromdaymonth3"  >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_to3" size="10"></html:text></td>
			<td><html:select property="txt_todaymonth3" >
					<html:option value="Days">Days</html:option>
					<html:option value="Month">Month</html:option>
				</html:select></td>
			<td><html:text property="txt_provamt3" size="10" ></html:text></td>
			<%} %>
			
		</tr>
		
		<tr>
			
			<%if(NpaObject!=null){ %>
			<td align="right"><html:text property="txt_default3" value="<%=""+NpaObject[4].getAssetDesc() %>" styleClass="formTextField" readonly="true"></html:text></td>
			<td><html:text property="txt_from4" size="10" readonly="true" value="<%=""+ NpaObject[4].getMonthsFrom() %>"></html:text></td>
			<td><html:select property="txt_fromdaymonth4">
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_to4" size="10"  value="<%=""+ NpaObject[4].getMonthsTo()%>" onblur="fun_add()" onkeypress="return only_Num()"></html:text></td>
			<td><html:select property="txt_todaymonth4" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt4" size="10" value="<%=""+NpaObject[4].getProvPerc()%>" onkeypress="return only_Num()"></html:text></td>
			<%} else {%>
			<td align="right"><bean:message key="label.default3"/></td>
			<td><html:text property="txt_from4" size="10" readonly="true"></html:text></td>
			<td><html:select property="txt_fromdaymonth4"  >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_to4" size="10" ></html:text></td>
			<td><html:select property="txt_todaymonth4" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt4" size="10"></html:text></td>
			<%} %>
			
		</tr>
			<html:hidden property="but_val" value="error"/>
			<html:hidden property="valid_values" styleId="valid_values"/>
			<html:hidden property="npa_res" styleId="npa_res"/>
		<tr>
		   	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
			<td><html:submit value="Submit" styleClass="ButtonBackgroundImage" onclick="fun_button('submit')"></html:submit>
			<html:button value="Clear" property="" styleClass="ButtonBackgroundImage" onclick="fun_clear()"></html:button></td>
			<%}else{ %>
			<td><html:submit value="Submit" styleClass="ButtonBackgroundImage" disabled="true"></html:submit>
			<html:button value="Clear" property="" styleClass="ButtonBackgroundImage" disabled="true"></html:button></td>
			<%} %>
		</tr>
	</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
</body>
</html>