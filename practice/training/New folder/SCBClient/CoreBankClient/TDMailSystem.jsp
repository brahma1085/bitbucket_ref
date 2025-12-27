<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<title>Insert title here</title>
<script type="text/javascript">
function set(target)
{
   alert(target);
   document.forms[0].flag.value=target;
   alert(document.forms[0].flag.value);
   document.forms[0].submit();

}
function show()
{
  if(document.getElementById("totaltesting").value!=0)
  {
     alert(document.getElementById("totaltesting").value);
  }
}

</script>
</head>
<body onload="show()">
<%!
ModuleObject[] array_module,module_object;

DepositMasterObject maildata;
List reciptlist;
%>
<%
maildata=(DepositMasterObject)request.getAttribute("emaildata");
if(maildata!=null){
System.out.println("The value is====> "+maildata.getEmailID());
}
%>
<html:form action="/TermDeposit/EmailSystem?pageId=13029">
<html:hidden property="testing" styleId="totaltesting"/>
<html:hidden property="flag"></html:hidden>
<table class="txtTable">
<tr>
<td>
 	<table class="txtTable">
 
 
 	 <td width="40%">
 	         <table align="left">

    	    <tr>
        	    <td align="right" >
        	  	  <font style="font-style: normal;font-size:12px;">
			    
			     <bean:message key="label.td_actype"></bean:message></font>
			    </td>
			    
			    <td><html:select property = "ac_type" style="background-color:silver">
			    	<html:option value="Select">Select</html:option>
			    	<html:option value="1004001">RD</html:option>
			    	<html:option value="1005001">BD</html:option>
			    	<html:option value="1005002">CD</html:option>
			    	<html:option value="1005003">VD</html:option>
			    	 </html:select>
    		   	 </td>
    		   	<td>
    		   	</td>
    		   	</tr>
    		   	<tr> 
    		   	 <td><font style="font-style: normal;font-size:12px;">
			         <bean:message key="label.next_pay_date"></bean:message></font>
			     </td>	
			      
			    <td>
			    
			    
			<html:text property="paydate"  size="6" onblur="submit()"> </html:text> 
			</td>
			    		    		    
   		  	</tr>
   		  	
   		  	
        	 </table> 
        </td>
    


</table>



<hr>

<div  id = "table1" style="display:block; overflow:scroll; width: 750px;height: 300px">




<display:table name="emaildata" list="receipt_updation"  id="emaildata"  class="its" requestURI="/TermDeposit/EmailSystem.do" pagesize="10">
   			 
<display:column title="Select"><input type="checkbox" name="id" value="${emaildata.closeInd}" ></display:column> 			 
 
<display:column title="Email-ID"><input type="text" name="email" value="${emaildata.email}" readonly="readonly" ></display:column>

<display:column title="Name"><input type="text" name="Name" value="${emaildata.name}" readonly="readonly" ></display:column>

<display:column title="PayDate"><input type="text" name="depositdate" value="${emaildata.nextPaydt}" readonly="readonly" ></display:column>

<display:column title=""></display:column>





</display:table>


</div>

<center>
<html:button property="mailbut" value="SendMail"  onclick="set('Submit')" styleClass="ButtonBackgroundImage"></html:button>
</center>

</td>
</tr>
</table>
</html:form>
</body>
</html>