<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
 <%@page import="java.util.Map"%>   
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<title>Insert title here</title>


<script type="text/javascript">
function Validatefields()
{
if(document.getElementById("totaltesting").value!=0)
{
  alert(document.getElementById("totaltesting").value);
}

};


</script>
</head>
<body class="Mainbody" onload="Validatefields()">


<%!
ModuleObject[] array_module,module_object;

DepositMasterObject[] dep_receipt_updation;
List reciptlist;
%>
<%

//dep_receipt_updation = (DepositMasterObject[])request.getAttribute("receiptupdation");
System.out.println("R u getting class cast exception?????????????????????????????");
reciptlist=(ArrayList)request.getAttribute("receipt_updation");
if(reciptlist!=null){
System.out.println("The VAlueeeeeeeeee====>>> "+reciptlist.get(0)); }
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


<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/ReceiptPrinting?pageId=13006" focus="<%=""+request.getAttribute("getfocus")%>">
<html:hidden property="testing" styleId="totaltesting"></html:hidden>
<html:hidden   property="forward"></html:hidden>

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
			    	<% array_module=(ModuleObject[])request.getAttribute("Dep type");
			    	
			    			for(int i=0;i<array_module.length;i++)
			    			{
			        		 	System.out.println("acctype"+ array_module);
			    	%>	    	
			    				<html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%
			    			}
			    	%>	
			    	 </html:select>
    		   	 </td>
    		   	
    		   	</tr>
    		   	<tr> 
    		   	 <td><font style="font-style: normal;font-size:12px;">
			         <bean:message key="label.from_ac_no"></bean:message></font>
			     </td>	
			      
			    <td>
			    
			    
			<html:text property="from_ac_no"  size="6" onkeypress="return numbersonly()"> </html:text> </td>
			     
			     
			     <td><font style="font-style: normal;font-size:12px;">
			         <bean:message key="label.to_ac_no"></bean:message></font>
			     </td>	
			      
			    <td>
			    
			    
			<html:text property="to_ac_no"  size="6" onkeypress="return numbersonly()" > </html:text> </td>
			     
			    
   		  	</tr>
   		  	
   		  	
        	 </table> 
        </td>
    


</table>



<hr>


<table  width="212" height="46">
	<tr>
		<td height="15" width="47"><html:button property="butt"  value="AssignRct-No"  styleClass="ButtonBackgroundImage" onclick="submit()"></html:button></td>
		<td height="15" width="76"><html:button property="butt_print"    styleClass="ButtonBackgroundImage" onclick="submit()" ><bean:message key="label.print"></bean:message></html:button></td>
		<td height="15" width="67"><html:button property="but_clear"  onclick="return funclear()"  styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:button></td>
	</tr>
	<tr>
		
		
	</tr>
</table>




<div  id = "table1" style="display:block; overflow:scroll; width: 750px;height: 300px">


<%if(reciptlist!=null){
	
	%>
	
	
	

 <display:table name="receipt_updation" list="receipt_updation" requestURI="/TermDeposit/ReceiptPrinting.do" id="receipt_updation"  class="its"  >
   			 
  <display:column title="Select"><input type="checkbox" name="id" value="${receipt_updation.id}" ></display:column> 			 
   
  <display:column title="AccNo">

  <input type="text" value="${receipt_updation.acc_no}" readonly="readonly" name="accno">  

 </display:column>
 
 
 
<display:column title="Name"><input type="text" name="name" value="${receipt_updation.depositorname}" readonly="readonly" ></display:column>

<display:column title="Dep.Date"><input type="text" name="depositdate" value="${receipt_updation.dep_date}" readonly="readonly" ></display:column>

<display:column title="MatDate"><input type="text" name="matdate" value="${receipt_updation.mat_date}" readonly="readonly" ></display:column>

<display:column title="Dep.Amt"><input type="text" name="depamt" value="${receipt_updation.depamt}" readonly="readonly" ></display:column>

<display:column title="Mat.Amt"><input type="text" name="matamt" value="${receipt_updation.matamt}" readonly="readonly" ></display:column>

<display:column title="Int.Rate"><input type="text" name="intrate" value="${receipt_updation.intrate}" readonly="readonly" ></display:column>



<display:column title="Curr.RcptNo"><input type="text" name="currrctno" value="${receipt_updation.receiptno}"  ></display:column>



<display:column title="New RcptNo">


  <input type="text" name="newrctno" value="${receipt_updation.newreceiptno}"  >  
 

</display:column>







<display:column title=""></display:column>





</display:table>

<%} %>
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