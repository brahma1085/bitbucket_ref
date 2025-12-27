<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>  
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %> 
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
  <%@page import="java.util.Map"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%> 
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.DCBObject"%>
<html>
<head>
<script type="text/javascript">

	function fuc_valchange()
	{
		
		if(document.forms[0].combo_pay_actype.value=="All")
		{
			document.forms[0].txt_fromacnum.disabled = true;
			document.forms[0].txt_toaccnum.disabled = true;
			document.forms[0].combo_actype.disabled = true;
			
			return false;
		}
		if(document.forms[0].combo_pay_actype.value=="Selected")
		{
			document.forms[0].txt_fromacnum.disabled = false; 
			document.forms[0].txt_toaccnum.disabled = false;
			document.forms[0].combo_actype.disabled = false;
			return false;   
		}
		
		alert(document.getElementById("valid_dcbpro_futuredate").value);
		if(document.getElementById("valid_dcbpro_futuredate").value!=null)
		{
			if(document.getElementById("valid_dcbpro_futuredate").value=="CanotProcessDcb")
			{
			    alert("You can't process DCB for the future dates...");
			}
		}
		if(document.getElementById("validate_dcbprocess").value!=null)
		{
			if(document.getElementById("validate_dcbprocess").value=="SomeRecordsExit")
			{
				var records=alert("Some records exists for this month..do u want to continue?");
			}
		}
		
	};
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
}  
	function process_fun(target)
	{
	if(document.forms[0].combo_pay_actype.value=="Selected"){
 if(document.forms[0].txt_fromacnum.value==""){
alert("From number Cannot Be Blank");
document.getElementById("txt_fromacnum").focus();
}
else if(document.forms[0].txt_toaccnum.value==""){
alert("To number Cannot Be Blank");
document.getElementById("txt_toaccnum").focus();
}else{
document.forms[0].button_process.value=target;
}
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
<h2 class="h2"><center>DCB Process</center></h2> 
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
</head>
<body class="Mainbody" onload="return fuc_valchange()">
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/DCBProcess?pageidentity.pageId=5041">
	<table class="txtTable">
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
		 	<td align="right"><bean:message key="label.acctype"></bean:message></td>
		 	<td><html:select property="combo_pay_actype" styleClass="formTextFieldWithoutTransparent" onchange="return fuc_valchange()">
				<html:option value="All">ALL</html:option>
				<html:option value="Selected">Selected</html:option>	 	
		 	</html:select></td>	
		 	
		 	<td><html:select property="combo_actype" styleClass="formTextFieldWithoutTransparent">
				<%if(modobj!=null){ 
					for(int i=0;i<modobj.length;i++){%>
						<html:option value="<%= ""+modobj[i].getModuleCode() %>"><%= ""+modobj[i].getModuleAbbrv() %></html:option>	
					<%}
				
				} %>
			</html:select></td>
		 	
		 	<td><bean:message key="label.from_acc_num"></bean:message></td>
		 	<td><html:text property="txt_fromacnum" styleClass="formTextFieldWithoutTransparent" size="10" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"></html:text></td>
		 	
		 	<td><bean:message key="label.to_acc_num"></bean:message></td>
		 	<td><html:text property="txt_toaccnum" styleClass="formTextFieldWithoutTransparent" size="10" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"></html:text></td>
		 	
	 	 </tr>
	 	 
	 	 <html:hidden property="button_process" value="error" />
	 	 <html:hidden property="valid_dcbpro_futuredate" styleId="valid_dcbpro_futuredate"/>
	 	 <html:hidden property="validate_dcbprocess" styleId="validate_dcbprocess" />
	 	 
	 	 <tr>
	 	 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	 	 	<td align="center"><html:submit value="Process" styleClass="ButtonBackgroundImage"  onfocus="process_fun('Process')" /></td>
	 	 <%}else{ %>
	 	 	<td align="center"><html:submit value="Process" styleClass="ButtonBackgroundImage" disabled="true"/></td>
	 	 	<%} %>
	 	 </tr>
	</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>		
</body>
</html>