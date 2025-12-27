<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>    
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %> 
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="java.util.Map"%> 
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
   




	function clear_fun()
    {
    	//alert("clearing");
    	var ele=document.forms[0].elements;
    	document.getElementById("table1").style.display='none';  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };

	function but_value(target)
	{
	  //  alert(target);
		if(document.forms[0].txt_accnum.value=="")
		{
			alert("Please Enter the Account Number");
			document.forms[0].txt_accnum.focus();
			return false;
		}
		else if(document.forms[0].txt_accnum.value=="0")
		{
			alert(" Account Number cannot be Zero");
			document.forms[0].txt_accnum.focus();
			return false;
		}
		
		
	document.forms[0].button_value.value=target;
	document.forms[0].submit();
 		return true;
       
    };


	function fun_check()
	{
		
		if(document.getElementById("validate_accnum").value!=null)
		{
			if(document.getElementById("validate_accnum").value=="AccountNotFound")
			{
				alert("Account Not Found");
				
				var ele=document.forms[0].elements;
  				for(var i=0;i<ele.length;i++)
  				{
  					if(ele[i].type=="text" || ele[i].type=="textarea")
  					{
  			  			ele.value="";
  					}
  		
  				}
				return false;
			}
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
} ; 
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
             
            
    </style>
    
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
   <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />

<h2 class="h2"><center>Loan PassBook</center></h2> 
</head>
<body onload="fun_check()">
<%System.out.println("*************Hi from LoanPassbook****************"); %>
<%!ModuleObject[] LoanAccType; %>
<%LoanAccType=(ModuleObject[])request.getAttribute("LoanAccType"); %>
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

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<html:form action="/Loans/LoanPassBook?pageidentity.pageId=5042">


	<table>
			<tr>
				<td align="right">
				<table>
				<tr><td>
					<bean:message key="label.loancatAccno"/>
					<html:select property="txt_loancatAccno">
					<%if(LoanAccType!=null){ %>
					<%for(int i=0;i<LoanAccType.length;i++){ %>
					<html:option value="<%=""+LoanAccType[i].getModuleCode() %>"><%=""+LoanAccType[i].getModuleAbbrv()+" "+LoanAccType[i].getModuleDesc()%></html:option>
					<%}} %>
					</html:select>
					<html:text property="txt_accnum" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"></html:text>
					</td></tr>
				
				
				<td align="right"><bean:message key="label_name1"/>
				<html:text property="txt_name" size="20" styleClass="formTextField" style="border:transparent;background-color:beige;color:red"  readonly="true"></html:text></td>
				
				</table>
				</td>
			</tr>
			
		<table>
			<tr>
				<td align="right">
					<bean:message key="label.SBNo"/>
					<html:text property="txt_SBNo" size="10" style="border:transparent;background-color:beige;color:red" styleClass="formTextField" readonly="true"></html:text>
				</td>
				
				<td align="right">
					<bean:message key="label.purpose"/>
					<html:text property="txt_purpose" size="10" styleClass="formTextField" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
				</td>
			</tr>
			
			<tr>
				<td align="right">
					<bean:message key="label.Shno"/>
					<html:text property="txt_sharenum" size="10" style="border:transparent;background-color:beige;color:red" styleClass="formTextField" readonly="true"></html:text>
				</td>
				<td align="right">
					<bean:message key="label.intrate"/>
					<html:text property="txt_intrate" size="10" styleClass="formTextField" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
				</td>
			</tr>
			<tr>
				<td align="right">
					<bean:message key="label.intpaidupto"/>
					<html:text property="txt_intpaidupto" size="10" styleClass="formTextField" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
				</td>
				<td align="right">
					<bean:message key="label.Sanctiondate"/>
					<html:text property="txt_Sanctiondate" size="10" styleClass="formTextField" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
				</td>
			</tr>
			
			<tr>
				<td align="right">
					<bean:message key="label.nominee_no"/>
					<html:text property="txt_nominee_no" size="10" styleClass="formTextField" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
				</td>
				<td align="right">
					<bean:message key="label.sanctionammt"/>
					<html:text property="txt_sanctionammt" size="10" styleClass="formTextField" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
				</td>
			</tr>
				
			<tr>
				<td align="right">
					<bean:message key="lable.phoneno"/>
					<html:text property="txt_phoneno" size="10" styleClass="formTextField" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
				</td>
				<td align="right">
					<bean:message key="label.noofInstallment"/>
					<html:text property="txt_noofInstallment" size="10" styleClass="formTextField" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
				</td>
			</tr>
			
			
			<tr>
				<td align="right">                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
					<bean:message key="label.email"/>
					<html:text property="txt_email" size="10" styleClass="formTextField" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
				</td>
				<td align="right">
					<bean:message key="label.Installmentamt"/>
					<html:text property="txt_Installmentamt" size="10" styleClass="formTextField" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
				</td>
			</tr>			
			
			<tr>
				<td align="right"><bean:message key="label.suritydetail"/>
				<html:textarea property="txt_suritydetail" styleClass="formTextFieldWithoutTransparent" rows="5" readonly="true"></html:textarea></td>
			</tr>	
			<tr>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
				<td><html:submit value="View" onclick="but_value('View')" styleClass="ButtonBackgroundImage"></html:submit></td>
				<%}else{ %>
				<td><html:submit value="View" disabled="true" styleClass="ButtonBackgroundImage"></html:submit></td>
				<% }%>
				<!--
					 
					 
					 <td><html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage"  value="Print"></html:button></td>
					      
					     
					 --><td><html:button property="clear" onclick="clear_fun()" styleClass="ButtonBackgroundImage">CLEAR</html:button></td>
					 
     
			</tr>
    </table>
    </table>
	<html:hidden property="validate_accnum" styleId="validate_accnum"/>
	<html:hidden property="button_value" value="error"></html:hidden>

    <div id="table1" style="display: block;overflow: scroll;width: 600px;height: 200px">
    
	<display:table name="list" id="list" class="its" >
		<display:column title="TranDate"  style="width:3%;"  maxLength="50"><input type="text" align="middle"  value="<core:out value="${list.TranDate}"></core:out>" size="10"  class="formTextField" readonly="readonly"/></display:column>
		<display:column title="Debit"  style="width:3%;"  maxLength="50"><input type="text" align="middle"  value="<core:out value="${list.Debit}"></core:out>" size="10" class="formTextField" readonly="readonly"/></display:column>
		<display:column title="Credit"  style="width:3%;"  maxLength="50"><input type="text" align="middle"  value="<core:out value="${list.Credit}"></core:out>" size="10" class="formTextField" readonly="readonly"/></display:column>
		<display:column title="LoanBal"  style="width:3%;"  maxLength="50"><input type="text" align="middle"  value="<core:out value="${list.LoanBal}"></core:out>" size="10" class="formTextField" readonly="readonly"/></display:column>
    </display:table>
    </div>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>
