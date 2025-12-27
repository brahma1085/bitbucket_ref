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


<%@page import="masterObject.termDeposit.DepositTransactionObject"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>TD PassBook </title>


<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
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

function numbersonly(eve){
         var cha = event.keyCode


            if (  ( cha  >= 47 && cha < 58  ) ) {


              
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
         
        
      };
     


</script>

</head>

<body class="Mainbody" onload="Validatefields()">



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


<html:form action="/TermDeposit/TDPassbook?pageId=13017" focus="<%=""+request.getAttribute("getfocus")%>">
 
 <html:hidden property="validation" ></html:hidden>
 
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
			      
			    <td><html:text property="ac_no"  size="6" onchange="submit()" onkeypress="return numbersonly()" styleClass="formTextFieldWithoutTransparent"> </html:text> </td>
			     
			    
			    
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
			  <html:text property="cid"  size="6" value="<%=""+depmast_obj[0].getCustomerId() %>" styleClass="formTextField "> </html:text>
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
             <html:text property="name"   value="<%=""+depmast_obj[0].getName() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="dep_date"  size="6" value="<%=""+depmast_obj[0].getDepDate() %>" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="dep_date"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
    
    <td>
          <bean:message key="label.mat_date"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="mat_date"  size="6" value="<%=""+depmast_obj[0].getMaturityDate() %>" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="mat_date"  size="6" styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
    
    </tr>
 <tr>
  <td>
          <bean:message key="lable.dep_amt"></bean:message>
          <core:choose>
           	<core:when test="${requestScope.passbook!=null}">
           		<html:text property="dep_amt"  size="6" value="<%=""+depmast_obj[0].getDepositAmt() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="mat_amt"  size="6" value="<%=""+depmast_obj[0].getMaturityAmt() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="email"  size="6" value="<%=""+depmast_obj[0].getMaturityAmt() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="period_in_days"  size="6" value="<%=""+depmast_obj[0].getPeriod_in_days() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="credit_acctype"   value="<%=""+depmast_obj[0].getTransferAccType() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="nominee_no"   value="<%=""+depmast_obj[0].getNomineeRegNo() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="ln_avail"  size="6" value="<%=""+depmast_obj[0].getLoanAvailed() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="credit_accno"   value="<%=""+depmast_obj[0].getTransferAccno() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="int_upto_date"   value="<%=""+depmast_obj[0].getInterestUpto() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="ln_actype"  size="6" value="<%=""+depmast_obj[0].getLoanAcType() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="int_mode"   value="<%=""+depmast_obj[0].getInterestMode() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="ph_no"   value="<%=""+depmast_obj[0].getPhoneNo() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="ln_no"  size="6" value="<%=""+depmast_obj[0].getLoanAccno() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="receipt_no"  size="6" value="<%=""+depmast_obj[0].getLoanAccno() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="auto_renewal"  size="6" value="<%=""+depmast_obj[0].getLoanAccno() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="int_freq"   value="<%=""+depmast_obj[0].getInterestFrq() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="int_rate"   value="<%=""+depmast_obj[0].getInterestRate() %>" styleClass="formTextField "> </html:text>
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
           		<html:text property="loan_sanc_amt"   value="<%=""+depmast_obj[0].getLastRctNo() %>" styleClass="formTextField "> </html:text>
           </core:when>
          	 <core:otherwise>
           		<html:text property="loan_sanc_amt"   styleClass="formTextField "> </html:text>
           </core:otherwise>
           
          </core:choose>
    </td>
 
 </tr>


</table>


<hr>
<div  id = "table1" style="display:block; overflow:scroll; width: 750px;height: 300px">
<%if(depmast_obj!=null){
	
	%>

<display:table name="passbooktable" export="true" id="currentRowObject" class="its" sort="list" pagesize="10">
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 
   


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
</body>
</html>