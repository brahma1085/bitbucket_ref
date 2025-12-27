<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>   
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %> 
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
  <%@page import="java.util.Map"%> 
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.DCBObject"%>
<html>
<head>
<script type="text/javascript">
function only_numbers() {
	
	var cha=event.keyCode;
	if(cha>47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;
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
   



	function fuc_valchange()
	{
		if(document.forms[0].combo_pay_actype.value=="ALL")
		{
			document.forms[0].txt_fromacnum.disabled = true;
			document.forms[0].txt_toaccnum.disabled = true;
			document.forms[0].combo_actype.disabled = true;
			return false;
		}
		if(document.forms[0].combo_pay_actype.value=="selected")
		{
			document.forms[0].txt_fromacnum.disabled = false; 
			document.forms[0].txt_toaccnum.disabled = false;
			document.forms[0].combo_actype.disabled = false;
			return false;   
		}
	};
	
	function fun_button(target)
	{
	if(document.forms[0].combo_pay_actype.value=="selected"){
 if(document.forms[0].txt_fromacnum.value==""){
alert("From number Cannot Be Blank");
document.getElementById("txt_fromacnum").focus();
}
else if(document.forms[0].txt_toaccnum.value==""){
alert("To number Cannot Be Blank");
document.getElementById("txt_toaccnum").focus();
}else{
document.forms[0].button_val.value=target;
}
}
	
		
	
	}
	
	function fun_clear()
	{
		alert("Clear");
		var ele=document.forms[0].elements;
		for(var i=0;i<ele.length;i++)
		{
			if(ele[i].type=="text")
			{
				ele[i].value="";
			}
		}
	}

</script>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>DCB Scheduling</center></h2> 

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body onload="fuc_valchange()">
<%!String Months[]; 
GregorianCalendar cal=new GregorianCalendar();
int yr=cal.get(Calendar.YEAR);
ModuleObject modobj[];
DCBObject dcb[];
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
<%Months=(String[])request.getAttribute("Months"); 
modobj=(ModuleObject[])request.getAttribute("AccountType");
dcb=(DCBObject[])request.getAttribute("DCBObject");
System.out.println("dcb object-------------->in JSP--->"+dcb);
%>



<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/DCBScheduling?pageidentity.pageId=5038">
<table>
		<tr>
			<td align="right"><bean:message key="label.monthandyear"/></td>
			<td><html:select property="txt_monthandyear">
				<%if(Months!=null){ %>
				<%for(int i=0;i<Months.length;i++){ %>
				<html:option value="<%= ""+(i+1)%>"><%= ""+Months[i] %></html:option>			
				<%}} %>
			</html:select></td>
			
			<td><html:select property="combo_year">
				<%for(int i=0;i<25;i++){ %>
				<html:option value="<%= ""+(yr-i)%>"><%= ""+(yr-i)%></html:option>
				<%} %>
			</html:select></td>
			
		</tr>
		
		<tr>
			<td align="right"><bean:message key="label.combo_pay_actype"/></td>
			<td><html:select property="combo_pay_actype" onchange="return fuc_valchange()">
				<html:option value="ALL">ALL</html:option>
				<html:option value="selected">selected</html:option>
			</html:select></td>
			
			<td><html:select property="combo_actype">
				<%if(modobj!=null){ 
					for(int i=0;i<modobj.length;i++){%>
						<html:option value="<%= ""+modobj[i].getModuleCode() %>"><%= ""+modobj[i].getModuleAbbrv() %></html:option>	
					<%}
				
				} %>
			</html:select></td>
			
			<td align="right"><bean:message key="label.fromacnum"/></td>
			<td><html:text property="txt_fromacnum"  size="10" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()" ></html:text></td>
			
			<td align="right"><bean:message key="label.toaccnum"/></td>
			<td><html:text property="txt_toaccnum" styleId="toaccnum" size="10" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
			
		</tr>
		
		<html:hidden property="button_val" value="error"/>
		
		<tr>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
			<td ><html:submit value="View" styleClass="ButtonBackgroundImage" onfocus="return fun_button('View')"></html:submit></td>
			<td><html:button property="clear" value="clear" styleClass="ButtonBackgroundImage" onclick="return fun_clear()" ></html:button>  </td>
			<%}else{ %>
			<td ><html:submit value="View" styleClass="ButtonBackgroundImage" disabled="true"></html:submit></td>
			<td><html:button property="clear" value="clear" styleClass="ButtonBackgroundImage" disabled="true"></html:button>  </td>
			<%} %>
		</tr>
		
		<tr>
			<td>
				
			
				
			</td>
		</tr>
		
</table>
	<div style="display: block;overflow: scroll;width: 600px;height: 200px">
	<display:table name="list" id="list" class="its" >
				
					<display:column style="width:6%"  title=" Ln Bal "><core:out value="${list.LoanBalance}"></core:out></display:column>
					
					<display:column style="width:6%" title="  Prini OD  "><core:out value="${list.PrincipalArr}"></core:out></display:column>
					
					<display:column style="width:2%" title="Intr OD"><core:out value="${list.IntrArr}"></core:out></display:column>
					
					<display:column style="width:2%" title="PenalInt"><core:out value="${list.PenalIntArr}"></core:out></display:column>
					
					<display:column style="width:2%" title="OtherArr"><core:out value="${list.OtherArr}"></core:out></display:column>
					
					<display:column style="width:2%" title="AdvPaid"><core:out value="${list.AdvPaid}"></core:out></display:column>
					
					<display:column style="width:2%" title="PriniDemand"><core:out value="${list.PrincipalDemand}"></core:out></display:column>
					<display:column style="width:2%" title="IntrDemand"><core:out value="${list.IntrDemand}"></core:out></display:column>
					<display:column style="width:2%" title="OtherDemand"><core:out value="${list.OtherDemand}"></core:out></display:column>
					<display:column style="width:2%" title="PriniColl"><core:out value="${list.PrincipalCollected}"></core:out></display:column>
					<display:column style="width:2%" title="IntrColl"><core:out value="${list.IntrCollected}"></core:out></display:column>
					<display:column style="width:2%" title="PenalColl"><core:out value="${list.PenalCollected}"></core:out></display:column>
					<display:column style="width:2%" title="OtherColl"><core:out value="${list.OtherCollected}"></core:out></display:column>
					<display:column style="width:2%" title="AdvColl"><core:out value="${list.AdvCollected}"></core:out></display:column>
					<display:column style="width:2%" title="data15"><core:out value="${list.data15}"></core:out></display:column>
					<display:column style="width:2%" title="data16"><core:out value="${list.data16}"></core:out></display:column>
					<display:column style="width:2%" title="data17"><core:out value="${list.data17}"></core:out></display:column>
					<display:column style="width:2%" title="data18"><core:out value="${list.data18}"></core:out></display:column>
					<display:column style="width:2%" title="data19"><core:out value="${list.data19}"></core:out></display:column>
				</display:table>
				</div>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>		
</body>
</html>