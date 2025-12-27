<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="java.util.Map"%>
<%@page import="masterObject.loans.LoanReportObject"%>
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


 
<script type="text/javascript">
function set(target){
if(document.getElementById("from_accno").value=="0")
	{
		alert('Enter the From Account Number!');
		document.getElementById("from_accno").focus();
		return false;
		}else if(document.getElementById("from_accno").value=="")
	{
		alert('Enter the From Account Number!');
		document.getElementById("from_accno").focus();
		return false;
		}else if(document.getElementById("to_accno").value=="")
	{
		alert('Enter the To Account Number!');
		document.getElementById("to_accno").focus();
		return false;
		}
		else if(document.getElementById("to_accno").value=="0")
	{
		alert('Enter the To Account Number!');
		document.getElementById("to_accno").focus();
		return false;
		}
		else if(document.getElementById("fromintamt").value=="0")
	{
		alert('Enter the Interest From  Amount!');
		document.getElementById("fromintamt").focus();
		return false;
		}
		else if(document.getElementById("fromintamt").value=="")
	{
		alert('Enter the Interest From  Amount!');
		document.getElementById("fromintamt").focus();
		return false;
		}
		else if(document.getElementById("fromintamt").value=="0.0")
	{
		alert('Enter the Interest From  Amount!');
		document.getElementById("fromintamt").focus();
		return false;
		}
		else if(document.getElementById("tointamt").value=="")
	{
		alert('Enter the Interest To  Amount!');
		document.getElementById("tointamt").focus();
		return false;
		}
		else if(document.getElementById("tointamt").value=="0")
	{
		alert('Enter the Interest To  Amount!');
		document.getElementById("tointamt").focus();
		return false;
		}
		else if(document.getElementById("tointamt").value=="0.0")
	{
		alert('Enter the Interest To Amount!');
		document.getElementById("tointamt").focus();
		return false;
		}
else{
 document.forms[0].forward.value=target;
  document.forms[0].submit();
//return true;
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
         function only_numbers_doublevalue() {
	
	var cha=event.keyCode;
	if((cha>=48 && cha<=57) || cha==46)
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;




         
   





function function_clear()
{
	var ele= document.forms[0].elements;
	for(var i=0;i<ele.length;i++)
	{
		if(ele[i].type=="text")
		{
			ele[i].value="";
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
</script>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>Interest Accured Report </center></h2>     
</head>
<body class="Mainbody">
<%!			 
    LoanReportObject[] rep;
	ModuleObject object[];
	String SysDate;
	
%>
<%String message=(String)request.getAttribute("msg"); %>
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


<%
    System.out.println("in the JSP of InterestAccruedReport");
	rep = (LoanReportObject[])request.getAttribute("IntAccruedReport");
	if(rep!=null){
	for (int i = 0; i < rep.length; i++)
	{	
		System.out.println("vector size Object-"+ i);
		//System.out.println("vecreportobj-"+vecreportobj.get(i));
		
		System.out.println("*****Loan Report*****"+rep[i].getAccNo());
	}
	}
	object=(ModuleObject[])request.getAttribute("LoanModuleCode"); 
	SysDate=(String)request.getAttribute("SysDate");
     
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
<html:form action="/Loans/InterestAccuredReport?pageidentity.pageId=5015">

<table class="txtTable">
 <tr>
   <td>
   <html:hidden property="testing" styleId="totaltesting"></html:hidden>
     <bean:message key="label.combo_loan"></bean:message>
     
      <html:select property="acctype">
      <html:option value="All Types"></html:option>
     	<%if(object!=null){ %>
     	<%for(int i=0;i<object.length;i++){ %>
     	<html:option value="<%=""+object[i].getModuleCode()%>"><%=object[i].getModuleAbbrv()%></html:option>
     	<%}%>
     	<%} %>
          </html:select>
   </td>
   
    
   <td>   
     <bean:message key="label.reportdate"></bean:message>
     <html:text property="reportdate"  size="10" value="<%= ""+SysDate %>" readonly="true" ></html:text>
     <!--
     onblur="return datevalidation(this)" onkeypress="return only_numbers1()"
   --></td>
  </tr>


 <tr>
   <td>
      <bean:message key="label.fromacc"></bean:message>
      <html:text property="from_accno" size="8" onkeypress="return only_Numbers()" onkeyup="numberlimit(this,'11')"></html:text>
   </td>

   <td>
     <bean:message key="label.toacc"></bean:message>
     <html:text property="to_accno" size="8" onkeypress="return only_Numbers()" onkeyup="numberlimit(this,'11')"></html:text>
   </td>
 </tr>

<tr>
    <td>
       <bean:message key="label.fromint"></bean:message>
       <html:text property="fromintamt" size="8" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
    </td>
     <td>
       <bean:message key="label.toint"></bean:message>
       <html:text property="tointamt" size="8" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
     </td>
</tr>

<tr>
  
  <html:hidden property="sysdate" />
   <html:hidden property="forward" value="error"/>
   <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
  <td> <html:submit onfocus="set('view');" styleClass="ButtonBackgroundImage" value="View"></html:submit>
  </td>
  <!--<td>
  	<html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage"  value="Print"></html:button>
  </td>
  
   -->
   <td><html:button property="but_print" onclick="set('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
   <td>
   		<html:button onclick="function_clear()" styleClass="ButtonBackgroundImage" property="clear" value="clear"></html:button>
   </td>
   <%}else{ %>
    <td> <html:submit disabled="true" styleClass="ButtonBackgroundImage" value="View"></html:submit>
  </td>
  <!--<td>
  	<html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage"  value="Print"></html:button>
  </td>
  
   -->
   <td><html:button property="but_print" disabled="true" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
   <td>
   		<html:button disabled="true" styleClass="ButtonBackgroundImage" property="clear" value="clear"></html:button>
   </td>
   <%} %>
</tr>
</table>
<%
	if(rep!=null) {
		

%>
 
	  	
<display:table htmlId="table2" name="IntAccruedReport" id="currentRowObject" class="its" sort="list" pagesize="10" requestURI="/Loans/InterestAccuredReport.do">   
          <display:column property="accNo"  title="A/C No"></display:column>
          <display:column property="name"  title="Name"></display:column>
          <display:column property="disburseAmount" title='Amount Advanced'></display:column>
          <display:column property="intOverDueAmt" title='Interest OutStanding'></display:column>
           <display:column property="interestPaid" title='Interest Paid'></display:column>
</display:table>
     	
	 	 <%
	 	 }
	 	 %>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
</body>
</html>
