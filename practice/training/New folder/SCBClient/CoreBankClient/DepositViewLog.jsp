<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %> 
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="java.util.Map"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Deposit View Log</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
    <h2 class="h2">
      <center>Deposit View Log</center></h2>
      
<script type="text/javascript">   
   
function Validatefields(){


if(document.getElementById("totaltesting").value!=0)
	{
		
		alert(document.getElementById("totaltesting").value);	
		
			
	}



var val = document.forms[0].validation.value;

if(val!=0 ||val!=""){
alert(val);


}else
{
return false;
}


   if(document.getElementById("totaltesting").value!=0)
	{
		
		alert(document.getElementById("totaltesting").value);	
		
			
	}



};
function ckValidate()
{
	var val=document.forms[0].ac_no.value;
	
	if(parseInt(val)==0)
	{
	   alert("Please Enter the Account Number");
	 }
	else if(val=="")
	{
	 alert("Enter Account Number");
	}
	
	else
	{
	document.forms[0].submit();
	}
	
}

function numbersonly(eve){
         var cha = event.keyCode


            if (  ( cha  >= 48 && cha < 58  ) ) {


              
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
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
         		alert("plz enter limit digits only!!!");
         		txt.value="";
         		return false;
          	}
         };
  


</script>






</head>
<body   class="Mainbody" onload="Validatefields()">
<%!
ModuleObject[] array_module;
Object dep_mast[][];
String[] detail;
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
<%dep_mast = (Object[][])request.getAttribute("viewlog");

  
System.out.println("geetha inside Deposit view log..");
%>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/DepositViewLog?pageId=13004">
<table  class="txtTable">
<html:hidden property="validation" ></html:hidden>
<html:hidden property="testing" styleId="totaltesting"></html:hidden>
<tr>

              <td>
                    <bean:message key="label.td_actype"></bean:message>
			    
			    <html:select property = "ac_type" styleClass="formTextFieldWithoutTransparent">
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
				<td>
			         <bean:message key="label.td_acno"></bean:message>
			     	
			     <html:text property="ac_no" size="8" onkeypress="return numbersonly()" onchange="ckValidate()" onkeyup="numberlimit(this,'10')" styleClass="formTextFieldWithoutTransparent"></html:text>
			     
			    </td>
</tr>

<tr>
<td>
		
</td>
</tr>
</table>


<hr>

<div  id = "table1" style="display:block; overflow:scroll; width: 750px;height: 300px">
<%if(dep_mast!=null){
	
	%>


<table border="1" bordercolor="black">
<tr>
<td>Account Type</td>
<td>Account No</td>
<td>Cid</td>
<td>Add_Type</td>
<td>auto_renw_no</td>
<td>JointHolder No</td>
<td>deposit_date</td>
<td>Maturity date</td>
<td>Deposit Years</td>
<td>Deposit Months</td>
<td>Deposit Days</td>
<td>Deposit Amount</td>
<td>Maturity Amont</td>
<td>NextPayDate</td>
<td>Maturity Post</td>
<td>Post date</td>
<td>Intr AccType</td>
<td>Intr Accno</td>
<td>Nominee Number</td>
<td>int_rate</td>
<td>extra_rate_type</td>
<td>recd_by</td>
<td>recd_ac_type</td>
<td>recd_ac_no</td>
<td>int_freq</td>
<td>int_mode</td>
<td>trf_ac_type</td>
<td>trf_ac_no</td>
<td>int_upto_dt</td>
<td>lst_pr_date</td>
<td>lst_pr_seq</td>
<td>ln_availed</td>
<td>ln_ac_no</td>
<td>Deposit_renwd</td>
<td>new rct</td>
<td>rct_no</td>
<td>rct_prtd</td>
<td>rct_sign</td>
<td>auto_renwal</td>
<td>exc_amt</td>
<td>close_ind</td>
<td>close_date</td>
<td>renw_ac_type</td>
<td>renw_ac_no</td>
<td>ldaadjreq</td>
<td>ldgprtdt</td>
<td>int_paid_dt</td>
<td>De Tml</td>
<td>De User</td>
<td>Dedate</td>
</tr>
<%
for(int i=0;i<dep_mast.length;i++){
%>
<tr>
<%for(int j=0;j<50;j++){ %>
<td>
<%if(dep_mast[i][j]!=null){ 
	%>

<%=dep_mast[i][j] %></td>
<%} }%>
</tr>
<%}%>
</table>
<%}
%>
</div>




</html:form>

<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>