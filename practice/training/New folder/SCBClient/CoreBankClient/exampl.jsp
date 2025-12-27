<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>


<%@page import="masterObject.loansOnDeposit.LoanReportObject"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>TermDeposit Closure</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
   <center> <h2 class="h2">Deposit Clos</h2></center>

     
<script type="text/javascript">

function set(target)
{
	document.forms[0].forward.value=target;
	
};

function setInfo(){



if(document.forms[0].flag.value=="Abn"){

var close = confirm("with penalty,without penalty");

if(close){

alert("with penalty")
document.forms[0].closure.value="withpenalty";
document.forms[0].submit();
}
else{

alert("without penalty");
document.forms[0].closure.value="withoutpenalty";
document.forms[0].submit();
}


}

if(document.forms[0].flag.value=="Normalclosure"){

alert("Normal closure");
alert(document.getElementById("loanid").value);
if(document.getElementById("loanid").value=="LoanTrue"){

alert("Loan Availed True!!");
}
return false;
}




};


function HideShow(AttributeToHide)
{

  	
	
	if(document.getElementById("pcombo").value=="loantable")
	{
				
		document.getElementById('loantable'+AttributeToHide).style.display='block';
	}	
}
</script>


</head>


<body class="Mainbody" onload="setInfo()">
 
<%!
ModuleObject[] array_module,module_object;
DepositMasterObject dep_mast;
Double dep_int_rate,dep_int_amt,dep_closure;
CustomerMasterObject cust_obj;
LoanReportObject loan_obj;
String[] details,pay_mode,combo_mat_cat,auto,int_freq,Combodate;

int i,j;
%>
<%

System.out.println("after depmaster object---- in jsp88888--");
dep_mast = (DepositMasterObject)request.getAttribute("DepositTran");
dep_closure = (Double)request.getAttribute("normal");


//Combodate=(String[])request.getAttribute("Combodate");

System.out.println("after depmaster object---- in jsp--");

dep_int_rate = (Double)request.getAttribute("closureform");

//dep_int_amt = (Double)request.getAttribute("intpayable");

System.out.println("applied int rate=="+dep_closure);
%>
<html:form action="/TermDeposit/TDClosure?pageId=13003" focus="<%=""+request.getAttribute("getfocus")%>">
 
<table class="txtTable">
<tr>
<td>
 	<table class="txtTable">
 
 <html:hidden property="flag" styleId="flagvalid" ></html:hidden>
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
    		   	 
    		   	 <td><font style="font-style: normal;font-size:12px;">
			         <bean:message key="label.td_acno"></bean:message></font>
			     </td>	
			      
			    <td>
			    
			    
			<html:text property="ac_no"  size="6" onblur="submit()"> </html:text> </td>
			     
			    
   		  	</tr>
        	 </table> 
        </td>
    
</table>
<table cellspacing="10" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">

<tr>
				<td><font style="font-style: normal;font-size:12px;">
			    	<bean:message key="label.dep_date"></bean:message></font>
			    </td>
			    <%System.out.println("i am here ------>"); %>
			    <%if(dep_mast!=null){
			
			    	%>
			  	<td>
			    	<html:text property="dep_date" size="12" readonly="true" value="<%=""+ dep_mast.getDepDate() %>" style="background-color:silver"></html:text>
			    	</td>
			    <%}else{ %>
			    		<td><html:text property="dep_date" size="12" value="0" readonly="true"  style="background-color:silver"></html:text>
			    </td><%} %>
			    
			   			     		     
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.closure_date"></bean:message></font>
			       </td>
			       <%if(dep_mast!=null){
			    	   
			    	    System.out.println("close date=="+ request.getAttribute("date"));
			       %>
			           <td> <html:text property="closure_date" size="12" readonly="true" value="<%=""+request.getAttribute("date")%>" ></html:text>
			           </td><%}else{ %>
			            
			           <td> <html:text property="closure_date" size="12" readonly="true" value="0"   ></html:text></td>
			           <%} %>
			      
</tr>
<tr>
                <td><font style="font-style: normal;font-size:12px;">
			    	<bean:message key="label.mat_date"></bean:message></font>
			    </td>
			     <%System.out.println("i am here ------>"); %>
			    <%if(dep_mast!=null){
			    	%>
                <td>
                	<html:text property="mat_date"  value="<%=""+ dep_mast.getMaturityDate() %>"  size="12" ></html:text>
                	</td>
               <%}else{ %> 	
                    	<td><html:text property="mat_date"  value="0"  size="12" ></html:text>
			    </td><%} %>
			    
			   			    
			    <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.int_upto_date"></bean:message></font>
			       </td>
			       <%System.out.println("i am here ------>7"); %>
			       <%if(dep_mast!=null){
			    	   System.out.println(">>int upto date=="+dep_mast.getInterestUpto());
			        %>
			           <td> <html:text property="int_upto_date" readonly="true" value="<%=""+ dep_mast.getInterestUpto()%>"   size="12+"></html:text>
			      </td>
			      <%}else{ %>
			      <td> <html:text property="int_upto_date" readonly="true" value="0"   size="12"></html:text>
			      </td><%} %>
			
					    
          
<tr>
				<td><font style="font-style: normal;font-size:12px;">
			         <bean:message key="label.receipt_no"></bean:message></font>
			     </td>	
			      <%System.out.println("i am here ------>8"); %>
			     <%if(dep_mast!= null){
			    	 System.out.println(">> rct_no=="+dep_mast.getReceiptno());
			    	 %>
			     <td>
			         <html:text property="rct_no" readonly="true"  value="<%=""+ dep_mast.getReceiptno() %>" size="8" style="border:transparent;background-color:beige"></html:text>
			     </td>
			    <% }else{ %>
			    <td>
			    <html:text property="rct_no" readonly="true"  value="0"   size="8"></html:text>
			     </td><% } %>
			
			<td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.int_amt_pay"></bean:message></font>
			       </td>
			       <%if(dep_closure!=null){
			    	   System.out.println(">>>> int to be payable>>>>>>>>>>"+dep_closure);
			    	   %>
			    	   <td> <html:text property="int_amt_pay" readonly="true"  value="<%=""+ dep_closure%>"  size="10"></html:text>
			     </td> 
			     <% }else{%>
			           <td> <html:text property="int_amt_pay" readonly="true" value="0.00"  size="10"></html:text>
			      </td><% } %>
			 </tr>
			
			
			<tr>
			
				<td><font style="font-style: normal;font-size:12px;">
			   		 <bean:message key="label.dep_amt"></bean:message></font>
			   	</td>
			   	 <%System.out.println("i am here ------>9"); %>
			   	<%if(dep_mast!=null){
			   		%>
			    <td>
			    <html:text property="dep_amt" value="<%=""+ dep_mast.getDepositAmt() %>"   size="8"></html:text>
			    </td>
			    <% }else{ %>
			    <td>
			    <html:text property="dep_amt"   size="8"></html:text>
			    </td><% } %>
			
			  <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.total_amt"></bean:message></font>
			       </td>
			       
			       <%if(dep_mast!=null && dep_closure!=null){
			    	   
			    	   	 double total_amt = dep_closure + dep_mast.getDepositAmt();
			    	%>
			    <td> <html:text property="total_amt" readonly="true" size="10" value="<%=""+total_amt%>"></html:text>
			    </td>
			      <%}else{
			    	  %>
			    <td> <html:text property="total_amt" readonly="true"  size="10" value="0.00"></html:text>
			      </td>
			     <%}%>
			   	     
			 </tr>
									     
			  <tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.Mat_amt"></bean:message></font>
			       </td>
			       <%System.out.println("i am here ------>10"); %>
			       <%if(dep_mast!=null){
			    	   %>
			     <td>
			         <html:text property="mat_amt" value="<%=""+ dep_mast.getMaturityAmt()%>" readonly="true" size="8" ></html:text>
			      </td>
			      <%}else{ %>
			      <td> <html:text property="mat_amt" value="0"   size="8"></html:text>
			      </td><%} %>
			      
			      
			        <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.loan_availed"></bean:message></font>
			       </td>
			       
			       <%if(dep_mast!=null){
			      
			    	%>
			    <td> <html:text property="loan_avail" readonly="true" size="6" value="<%=dep_mast.getLoanAvailed()%>"></html:text>
			    </td>
			      <%}else{
			    	  %>
			    <td> <html:text property="loan_avail" readonly="true"  size="6"></html:text>
			      </td>
			     <%}%>
			   
			      
			  </tr>
			  
			 <tr>
				<td><font style="font-style: normal;font-size:12px;">
			   		 <bean:message key="label.Agreed_int_rate"></bean:message></font>
			   	</td>
			   	<%System.out.println("i am here ------>10.1");
			  
			   	%>
			   	<%if(dep_mast!=null){
			    	   %>
			    <td><html:text property="agreed_int_rate"  value="<%=""+ dep_mast.getInterestRate()%>" readonly="true"   size="6"></html:text>
			    		     
			       </td>
			       <% 	System.out.println("AGREED INT RATE=======>>>"+dep_mast.getInterestRate()); %>
			       <%}else{ %>
			       <td><html:text property="agreed_int_rate"  value="0" readonly="true"   size="6"></html:text>
			       </td><%} %>
			       
			</tr>
	
	  <tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.applied_int_rate"></bean:message></font>
			       </td>
			       <%if(dep_int_rate!=null){
			    	   
			    	   System.out.println("in jsp----dep_int_rate[1]-"+dep_int_rate);
			        %>
			           <td> <html:text property="applied_int_rate" readonly="true" value="<%=""+ dep_int_rate%>"  size="6"></html:text>
			           </td>
			          <%}else{%>
			          
			          <td> <html:text property="applied_int_rate" readonly="true" value="0"  size="6"></html:text> 
			      </td><%} %>
			 </tr>
			    
			  
			
			  <tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.int_amt_paid"></bean:message></font>
			       </td>
			       <%System.out.println("i am here ------>11"); %>
			       <%if(dep_mast!=null){
			    	   %>
			       
			           <td> <html:text property="int_amt_paid" readonly="true" value="<%=""+ dep_mast.getInterestPaid()%>"   size="6"></html:text>
			     	   </td>
			      <%}else{ %>
			           <td><html:text property="int_amt_paid" readonly="true" value="0.00"   size="6"></html:text>
			      </td><%} %>
			 </tr>
			 
			  <tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.no_of_jointholders"></bean:message></font>
			       </td>
			       <%System.out.println("i am here ------>12"); %>
			       <%if(dep_mast!=null){ 
			       %>
			           <td> <html:text property="no_of_joint" readonly="true"  value="<%=""+ dep_mast.getNoofJtHldrs()%>"  size="6" ></html:text>
			      </td>
			      <%}else{ %>
			      <td> <html:text property="no_of_joint" readonly="true"  value="0"  size="6"></html:text>
			      </td><%} %>
			 </tr>
			 
			 <%System.out.println("i am here ------>13"); %>
			  <tr>
		          <td>
		          
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.nominee_no"></bean:message></font>
			       </td>
			       <%if(dep_mast!=null){ 
			       %>
			        	<td> <html:text property="nominee_reg_no" value="<%=""+ dep_mast.getNomineeRegNo() %>" readonly="true"   size="6"></html:text>
			      		</td>
			      <%}else{
			      %>
			      <td> <html:text property="nominee_reg_no" value="0" readonly="true"   size="6"></html:text>
			      		</td>
			      <%
			      }
			      %>	
			 </tr>
			 <%System.out.println("i am here ------>14"); %>
			 <tr>
				<td><font style="font-style: normal;font-size:12px;">
			    	<bean:message key="label.combo_pay_mode"></bean:message></font>
			    </td>
			    <td><font style="font-style: normal;font-size:12px;">	
			    	<html:select property="pay_mode" style="background-color:silver">	
			        	<%pay_mode=(String[])request.getAttribute("pay_mode");
			             	 for(int i=0;i < pay_mode.length;i++)
			             	 {
						    	 System.out.println("pay_mode"+ pay_mode);
				    	
			    	    %>	   			    			    	 
			    				<html:option value="<%=""+pay_mode[i]%>"><%=""+pay_mode[i]%></html:option>
			    		<%	  }		%>
			        	 
			           </html:select></font>
			           
			        		        	 
			     </td>
			</tr>
			    
			    
			<tr>
				<td><font style="font-style: normal;font-size:12px;">
			    	<bean:message key="label.combo_pay_actype"></bean:message></font>
			    </td>
			    <td>
			    	<html:select property="pay_ac_type" style="background-color:silver">
			        <%module_object=(ModuleObject[])request.getAttribute("pay_actype");
			        	for(int i=0;i < module_object.length;i++)
			            {
						 	 System.out.println("module_object"+ module_object);
				    	
			    	 %>	   			    			    	 
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    	<%	}	%>
			        	 
			         </html:select>
			         			         
			          <%if(dep_mast!=null){ 
			       %>
			      		<html:text property="combo_pay_acno" value="<%=""+ dep_mast.getReceivedAccno() %>" readonly="true"   size="6"></html:text>
			      		
			      		  <%}else{ %>
			      		  
			      		  <html:text property="combo_pay_acno" value="0" readonly="true"   size="6"></html:text>
			      		  <%} %>
			      		  
		 </td>   
		 
			    
			    
			         			         
			</tr> 
			   
			   <tr>
			       <td>
			            <bean:message key="label.details"></bean:message>
			       </td>
			       <td>
			            <html:select property="details" style="background-color:silver"  onchange="HideShow(11)" styleId="pcombo">
			          <%details=(String[])request.getAttribute("details");
			             	 for(int i=0;i < details.length;i++)
			             	 {
						    	 System.out.println("details"+ details);
				    	
			    	      %>	   			    			    	 
			    		<html:option value="<%=""+details[i]%>"><%=""+details[i]%></html:option>
			    	<%}%>
			        			         
			         	</html:select>
			       	</td>
			   </tr>
			  
			
			<tr></tr> 
			
		<tr> 
			
			   <td><html:submit onclick = "set('submit')">
			   
				<bean:message key="label.submit"></bean:message></html:submit>
				
				 
				<html:button property="butt_update" >
				<bean:message key="label.update"></bean:message></html:button>
				
				
				<html:cancel property="butt_clear"><bean:message key="label.cancel"></bean:message></html:cancel></td>
				
				
				<td><html:cancel property="butt_del"><bean:message key="label.delete"></bean:message></html:cancel></td>
				
				<td><html:cancel property="butt_verify" onclick = "set('verify')"><bean:message key="label.verify"></bean:message></html:cancel></td>
				
				<td> 
			
			   <html:hidden property="forward" value="error" ></html:hidden>
			    
			    <html:hidden property="loantrue" styleId="loanid"></html:hidden>
			    
			    <html:hidden property="closure" value="0"></html:hidden></td>
			    
		</tr> 

</table>




</td>
<td>




<table  class = "txtTable" align="left" valign="top" cellspacing="3" style="border-bottom-color:teal;border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:teal;border-left-style:hidden;border-right-style:hidden;border-right-color:teal;border-top-color:teal;border-top-style:hidden;">
<tr id="loantable11">
			
			<tr>
			 	<td>
			 		 <bean:message key="label.Acc_type"></bean:message>
			  		
			 	 <html:text property="loan_ac_type" value="0" size="8" styleClass="formTextField"></html:text>
    		  	</td> 
    		  	
    		 </tr>
    		 <tr> 	
    		  	
    	 		 <td>
    	 		     
    		      <bean:message key="label.loanacc"></bean:message>
    		    
    		     <html:text property="loan_ac_no" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" styleClass="formTextField"></html:text>
    		     
    		     </td>
    		     
    		    </tr>
    		    <tr> 
    		     	 <td>
    	 		     
    		      <bean:message key="label.sancdate"></bean:message>
    		    
    		    
    		    <%if(dep_mast!=null){
    		    	
    		    	loan_obj = dep_mast.getLoan_reportobj();
    		    	System.out.println("Loan obj===="+loan_obj.getSanctionedAmount());
    		    	%>
    		    	
    		    	 <html:text property="sanc_date" size="10" readonly="true" value="<%=""+ loan_obj.getSanctionDate() %>" styleClass="formTextField"></html:text>
    		 <%}else{ %>  
    		    
    		     <html:text property="sanc_date" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" styleClass="formTextField"></html:text>
    		     
    		     <%} %>
    		     
    		     </td>
    		     </tr>
    		     
    		     <tr>
    		     	 <td>
    	 		     
    		      <bean:message key="label.sancamt"></bean:message>
    		    
    		     <html:text property="sanc_amt" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" styleClass="formTextField"></html:text>
    		     
    		     </td>
    		     
    		     </tr>
    		     
    		  <tr>
    		    <td>
    	 		     
    		      <bean:message key="label.loanee_name"></bean:message>
    		    
    		     <html:text property="loanee_name" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" styleClass="formTextField"></html:text>
    		     
    		     </td>
    		     
    		     </tr>
    		     <tr>
    		      <td>
    	 		     
    		      <bean:message key="label.loan_intuptodate"></bean:message>
    		    
    		     <html:text property="ln_int_upto_date" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" styleClass="formTextField"></html:text>
    		     
    		     </td>
    		     
    		     </tr>
    		     
    		     <tr>
    		      <td>
    	 		     
    		      <bean:message key="label.principal_bal"></bean:message>
    		    
    		     <html:text property="ln_principal_bal" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" styleClass="formTextField"></html:text>
    		     
    		     </td>
    		     </tr>
    		     
    		     <tr>
    		      <td>
    	 		     
    		      <bean:message key="label.int_bal"></bean:message>
    		    
    		     <html:text property="ln_interest_bal" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" styleClass="formTextField"></html:text>
    		     
    		     </td>
    		     
    		     </tr>
    		     
    		     <tr>
    		      <td>
    	 		     
    		      <bean:message key="label.tot_bal"></bean:message>
    		    
    		     <html:text property="tot_bal" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" styleClass="formTextField"></html:text>
    		     
    		     </td>

</tr>    		     



</table>

</td>

</tr>

</table>


</html:form>
</body>
</html>