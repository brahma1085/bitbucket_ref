<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %> 
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %> 
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="java.util.Map"%>

<%@page import="masterObject.termDeposit.DepositTransactionObject"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>TD PassBook </title>


<!--<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    --><link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
   <center> <h2 class="h2">
      TD PassBook</h2></center>


<script type="text/javascript">

function Validatefields(){


var val = document.forms[0].validation.value;

if(val!=0 ||val!=""){
alert(val);


}else
{
return false;
}


};

function  funclear(){

var v = document.forms[0].elements;
for(var i=0;i<=v.length;i++){

if(v[i].type =="text"){

v[i].value = "";

}
document.getElementById("table1").style.display='none';

}
 
}

function setprint(target)
{
alert(target);
document.forms[0].flag.value=target;
document.forms[0].submit();
}




function numbersonly(eve){
         var cha = event.keyCode


            if (  ( cha  >=48 && cha < 58  ) ) {
            
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
         		alert("only "+size+" digits allowed")
         		txt.value="";
         		return false;
          	}
         }

     


</script>

</head>

<body class="Mainbody" onload="Validatefields()">


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
<%!
ModuleObject[] array_module;


DepositMasterObject[] depmast_obj;
DepositTransactionObject[] deptranobj;
%>
<%depmast_obj = (DepositMasterObject[])request.getAttribute("passbook");
deptranobj = (DepositTransactionObject[])request.getAttribute("passbooktable"); 
if(deptranobj!=null)
System.out.println(deptranobj[0].getVe_user());
System.out.println("geetha inside passbook..");
%>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/TDPassbook?pageId=13017" focus="<%=""+request.getAttribute("getfocus")%>">
 
 <html:hidden property="validation" ></html:hidden>
 <html:hidden property="flag"/>
 <table  class="txtTable">
   <tr> 
 	 <td width="40%" colspan="3">
 	         <table align="left">

    	    <tr>
        	    <td align="right" >
        	  	  
			      <bean:message key="label.td_actype"></bean:message>
			    </td>
			    <td><html:select property = "ac_type" styleClass="formTextFieldWithoutTransparent">
			    	<% array_module=(ModuleObject[])request.getAttribute("Dep type");
			    	
			    			for(int i=0;i<array_module.length;i++)
			    			{
			        		 	System.out.println("ac_type"+ array_module);
			    	%>	    	
			    				<html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%
			    			}
			    	%>	
			    	 </html:select>
    		   	 </td>
    		   	 
    		   	 <td>
			         <bean:message key="label.td_acno"></bean:message>
			     </td>	
			      
			    <td><html:text property="ac_no"  size="6" onchange="submit()" onkeypress="return numbersonly()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"> </html:text> </td>
			     
			    
			    
   		  	</tr>
        	 </table> 
        	 
        </td>
   </tr>
   <hr> 
    
 <tr>
    <tr>
     	<td>
			 <bean:message key="lable.cid"></bean:message>
		
			 <core:choose>
			 <core:when test="${requestScope.passbook!=null}">
			  <html:text property="cid"  size="6" value="<%=""+depmast_obj[0].getCustomerId() %>" readonly="true" styleClass="formTextField "> </html:text>
			 </core:when>
			 <core:otherwise>
			 <html:text property="cid"  size="6" styleClass="formTextField "> </html:text> 
			  </core:otherwise>
			 </core:choose>
			 </td>
	 </tr>
			     
	<tr>
      	 <td>
			         <bean:message key="label.name1"></bean:message>
			         
	         <core:choose>

             <core:when test="${requestScope.passbook!=null}">
             <html:text property="name"   value="<%=""+depmast_obj[0].getName() %>" readonly="true" styleClass="formTextField "> </html:text>
              </core:when>	         
	         	
	         <core:otherwise>
	         <html:text property="name"  styleClass="formTextField "> </html:text> 
	         </core:otherwise>
	           
	    	</core:choose>	     
      </td>
      
      <td>
          <bean:message key="lable.dep_date"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="dep_date"  size="10" value="<%=""+depmast_obj[0].getDepDate() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="dep_date"  size="10" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
    
    <td>
          <bean:message key="label.mat_date"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="mat_date"  size="10" value="<%=""+depmast_obj[0].getMaturityDate() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="mat_date"  size="10" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
    
    </tr>
 <tr>
  <td>
          <bean:message key="lable.dep_amt"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="dep_amt"  size="6" value="<%=""+depmast_obj[0].getDepositAmt() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="dep_amt"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
    
    <td>
          <bean:message key="label.Mat_amt"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="mat_amt"  size="6" value="<%=""+depmast_obj[0].getMaturityAmt() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="mat_amt"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 <td>
          <bean:message key="label.email"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="email"  size="16" value="<%=""+depmast_obj[0].getEmailID() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="email"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 
 </tr>

<tr>
  <td>
          <bean:message key="label.Period_in_days"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="period_in_days"  size="6" value="<%=""+depmast_obj[0].getDepositDays() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="period_in_days"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
          
          </core:choose>
    </td>
    
     <td>
          <bean:message key="label.credit_actype"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="credit_acctype"   value="<%=""+depmast_obj[0].getTransferAccType() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="credit_acctype"   styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
    
     <td>
          <bean:message key="label.nominee_no"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="nominee_no"   value="<%=""+depmast_obj[0].getNomineeRegNo() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="nominee_no"   styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 
 </tr>
 <tr>
  <td>
          <bean:message key="label.loan_availed"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="ln_avail"  size="6" value="<%=""+depmast_obj[0].getLoanAvailed() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="ln_avail"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 
   <td>
          <bean:message key="label.credit_ac_no"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="credit_accno"   value="<%=""+depmast_obj[0].getTransferAccno() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="credit_accno"   styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 <td>
          <bean:message key="label.int_upto_date"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="int_upto_date"   value="<%=""+depmast_obj[0].getInterestUpto() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="int_upto_date"   styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 
 
 </tr>
 <tr>
  <td>
          <bean:message key="label.combo_loan"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="ln_actype"  size="6" value="<%=""+depmast_obj[0].getLoanAcType() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="ln_actype"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
    
    <td>
          <bean:message key="label.int_mode"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="int_mode"   value="<%=""+depmast_obj[0].getInterestMode() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="int_mode"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
  <td>
          <bean:message key="lable.phoneno"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="ph_no"   value="<%=""+depmast_obj[0].getMobileNo() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="ph_no"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 
 </tr>

<tr>
  <td>
          <bean:message key="label.loanaccno"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="ln_no"  size="6" value="<%=""+depmast_obj[0].getLoanAccno() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="ln_no"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
     <td>
          <bean:message key="label.receipt_no"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="receipt_no"  size="6" value="<%=""+depmast_obj[0].getLoanAccno() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="receipt_no"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
    
    <td>
          <bean:message key="label.combo_autorenewal"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="auto_renewal"  size="6" value="<%=""+depmast_obj[0].getLoanAccno() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="auto_renewal"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 
 </tr>
 <tr>
  <td>
          <bean:message key="label.Int_freq"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="int_freq"   value="<%=""+depmast_obj[0].getInterestFrq() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="int_freq"   styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 <td>
          <bean:message key="lable.intrate"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="int_rate"   value="<%=""+depmast_obj[0].getInterestRate() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="int_rate"   styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 <td>
          <bean:message key="label.san_amt"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="loan_sanc_amt"   value="<%=""+depmast_obj[0].getExcessAmt() %>" readonly="true" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="loan_sanc_amt"   styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 </tr>  
 <tr>
<td> 
       <!--<a href="TDPassbook.do" >Print Data</a> -->



<html:button property="butt_file" styleClass="ButtonBackgroundImage" onclick="setprint('DownLoad')">DownLoad</html:button>
<html:button property="clear" onclick="funclear()" styleClass="ButtonBackgroundImage">Clear</html:button>
</td>

</tr>
</table>


<hr>
<div  id = "table1" style="display:block; overflow:scroll; width: 750px;height: 300px">
<%if(depmast_obj!=null){
	
	%>

<display:table name="passbooktable"  id="currentRowObject" class="its" sort="list" requestURI="/TermDeposit/TDPassbook.do" pagesize="10" >
   			


<display:column property="tranSequence" ></display:column>
<display:column property="tranDate" ></display:column>
<display:column property="tranType" ></display:column>
<display:column property="tranNarration" ></display:column>
<display:column property="paidDate" ></display:column>


<display:column property="de_user"  title="Entd.By"></display:column>
<display:column property="obj_userverifier.verId" title="Verfd.By"></display:column>
</display:table>

<%} %>
</div>

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>


</body>
</html>