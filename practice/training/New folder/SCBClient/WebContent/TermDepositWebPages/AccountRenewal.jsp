<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Deposit Account Renewal</title>



<h2 style="font:small-caps; font-style:normal;">
      <center>Deposit Account Renewal</center></h2>

<style type="text/css">
      body{
      font-size:8px;
      font-family:serif;
      font-style:oblique;
      font-weight:bold;
      background:beige;
    }
    table{
     background: transparent;
     }
     tr{
     background: transparent;
     }
     td{
     background: transparent;
     }
    
</style>

<script type="text/javascript">
function beforesubmit(){
alert("hiii..")
document.forms[0].detail.value='done';
document.forms[0].submit();
};

function set(target)
{
 //   alert("!!!!!!!!!");
    document.forms[0].forward.value=target;
	document.forms[0].submit();
	
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

function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("please enter only limit digits")
         		txt.value="";
         		return false;
          	}
         };


      
  function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >=48 && cha <=57) ) 
		 {
   		 	return true;
          } 
          else 
          {
   			alert("Enter Numbers Only");
   			return false;
          }
	};
      

function Validations()
{
	if(document.getElementById("totaltesting").value!="")
		{
		
		alert(document.getElementById("totaltesting").value);	
		
			
		}
}
</script>



<body onload="Validations()">

<%!
ModuleObject[] array_module,module_object;
DepositMasterObject dep_mast;
CustomerMasterObject cust_obj;
String[] details,pay_mode,combo_mat_cat,auto,int_freq;
String jsppath,pagenew;
int i,j;
Double intpayamount,totamt,int_amt_payed;
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
<%

int_amt_payed=(Double)request.getAttribute("INtAmtPayed");

String nstr=(String)request.getAttribute("flag");
System.out.println(" inside AccountRenewal.jsp----->"+ nstr);
intpayamount=(Double)request.getAttribute("intrestpayable");
System.out.println("The interest amount is---> "+intpayamount);
totamt=(Double)request.getAttribute("Tot_amt");
System.out.println("The total amount is------->>>> "+totamt);
String dnthide=(String)request.getAttribute("Dont_hide");
%>
<%dep_mast = (DepositMasterObject)request.getAttribute("DepositTran");  %>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/AccountRenewal?pageId=13010" focus="<%=""+request.getAttribute("getfocus")%>">

<html:hidden property="detail" ></html:hidden>
<html:hidden property="forward" ></html:hidden>
<html:hidden property="testing" styleId="totaltesting"></html:hidden>
<table>
<td>
 <table>
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
			      
			    <td><html:text property="ac_no"  size="6" onchange="ckValidate()"  onkeypress="return only_numbers()" onkeyup="numberlimit(this,'10')" > </html:text> </td>
			     
			    
			    
   		  	</tr>
        	 </table> 
        </td>
    




</table>



<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">


<tr>
				<td><font style="font-style: normal;font-size:12px;">
			    	<bean:message key="label.dep_date"></bean:message></font>
			    </td>
			    <%System.out.println("i am here ------>"); %>
			    <%if(dep_mast!=null){
			
			    	%>
			  	<td>
			    	<html:text property="dep_date" size="6" readonly="true" value="<%=""+ dep_mast.getDepDate() %>" style="background-color:silver"></html:text>
			    	</td>
			    <%}else{ %>
			    		<td><html:text property="dep_date" size="6" value="0" readonly="true"  style="background-color:silver"></html:text>
			    </td><%} %>
</tr>
<tr>
                <td><font style="font-style: normal;font-size:12px;">
			    	<bean:message key="label.mat_date"></bean:message></font>
			    </td>
			     <%System.out.println("i am here ------>"); %>
			    <%if(dep_mast!=null){
			    	%>
                <td>
                	<html:text property="mat_date"  value="<%=""+ dep_mast.getMaturityDate() %>"  size="6" readonly="true"></html:text>
                	</td>
               <%}else{ %> 	
                    	<td><html:text property="mat_date"  value="0"  size="6" readonly="true"></html:text>
			    </td><%} %>
</tr>
<tr>
			       <td>
			       
			       <font style="font-style: normal;font-size:12px;">
			           <bean:message key="lable.intamt"></bean:message></font>
			       
			       </td>
			       <%if(int_amt_payed!=null){
			    	   %>
			       
			           <td><html:text property="int_amt_just" readonly="true" value="<%=""+ int_amt_payed%>"   size="6"></html:text>
			     	   </td>
			      <%}else{ %>
			           <td><html:text property="int_amt_just" readonly="true" value="0"   size="6"></html:text>
			      </td><%} %>
</tr>


<tr>
				<td><font style="font-style: normal;font-size:12px;">
			         <bean:message key="label.receipt_no"></bean:message></font>
			     </td>	
			      <%System.out.println("i am here ------>"); %>
			     <%if(dep_mast!= null){
			    	 System.out.println(">> rct_no=="+dep_mast.getReceiptno());
			    	 %>
			     <td>
			         <html:text property="rct_no" readonly="true"  value="<%=""+ dep_mast.getReceiptno() %>"  style="border:transparent;background-color:beige"></html:text>
			     </td>
			    <% }else{ %>
			    <td>
			    <html:text property="rct_no" readonly="true"  value="0"  style="border:transparent;background-color:white" size="6"></html:text>
			     </td><% } %>
</tr>
			
<tr>
			
				<td><font style="font-style: normal;font-size:12px;">
			   		 <bean:message key="label.dep_amt"></bean:message></font>
			   	</td>
			   	 <%System.out.println("i am here ------>"); %>
			   	<%if(dep_mast!=null){
			   		%>
			    <td>
			    <html:text property="dep_amt" value="<%=""+ dep_mast.getDepositAmt() %>"  style="border:transparent;background-color:beige" size="6" onkeypress="return only_numbers()"></html:text>
			    </td>
			    <% }else{ %>
			    <td>
			    <html:text property="dep_amt" value="0"  style="border:transparent;background-color:beige" size="6" onkeypress="return only_numbers()"></html:text>
			    </td><% } %>
			
</tr>
			     
<tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.Mat_amt"></bean:message></font>
			       </td>
			       <%System.out.println("i am here ------>5"); %>
			       <%if(dep_mast!=null){
			    	   %>
			     <td>
			         <html:text property="mat_amt" value="<%=""+ dep_mast.getMaturityAmt()%>" readonly="true"  ></html:text>
			      </td>
			      <%}else{ %>
			      <td> <html:text property="mat_amt" value="0"   size="6"></html:text>
			      </td><%} %>
</tr>
			  
<tr>
				<td><font style="font-style: normal;font-size:12px;">
			   		 <bean:message key="label.Agreed_int_rate"></bean:message></font>
			   	</td>
			   	<%System.out.println("i am here ------>6"); %>
			   	<%if(dep_mast!=null){
			    	   %>
			    <td><html:text property="agreed_int_rate"  value="<%=""+ dep_mast.getInterestRate()%>" readonly="true"   size="6"></html:text> 
			       </td>
			       <%}else{ %>
			       <td><html:text property="agreed_int_rate"  value="0" readonly="true"   size="6"></html:text>
			       </td><%} %>
			       
</tr>
	
			    
<tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.int_upto_date"></bean:message></font>
			       </td>
			       <%System.out.println("i am here ------>7"); %>
			       <%if(dep_mast!=null){
			    	   System.out.println(">> rct_no=="+dep_mast.getInterestUpto());
			        %>
			           <td> <html:text property="int_upto_date" readonly="true" value="<%=""+ dep_mast.getInterestUpto()%>"   size="6"></html:text>
			      </td>
			      <%}else{ %>
			      <td> <html:text property="int_upto_date" readonly="true" value="0"   size="6"></html:text>
			      </td><%} %>
</tr>
			
<tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.int_amt_paid"></bean:message></font>
			       </td>
			       <%System.out.println("i am here ------>"); %>
			       <%if(dep_mast!=null){
			    	   %>
			       
			           <td> <html:text property="int_amt_paid" readonly="true" value="<%=""+ dep_mast.getInterestPaid()%>"   size="6"></html:text>
			     	   </td>
			      <%}else{ %>
			           <td><html:text property="int_amt_paid" readonly="true" value="0"   size="6"></html:text>
			      </td><%} %>
</tr>
			 
<tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.no_of_jointholders"></bean:message></font>
			       </td>
			       <%System.out.println("i am here ------>9"); %>
			       <%if(dep_mast!=null){ 
			       %>
			           <td> <html:text property="no_of_joint" readonly="true"  value="<%=""+ dep_mast.getNoofJtHldrs()%>"  ></html:text>
			      </td>
			      <%}else{ %>
			      <td> <html:text property="no_of_joint"  value="0" readonly="true"   size="6"></html:text>
			      </td><%} %>
</tr>
			 
			 <%System.out.println("i am here ------>9"); %>



<tr>
<td>
<font style="font-style: normal;font-size:12px;">
			           <bean:message key="lable.totalamm"></bean:message></font>

</td>
               <%if(totamt!=null){ %>
                
                <td><html:text property="total_amt" value="<%=""+totamt%>" readonly="true"   size="6"></html:text></td>
                <%}else{%> 
                <td><html:text property="total_amt" value="0" readonly="true"   size="6"></html:text></td> 
                <%}%>
</tr>

<tr>
              <td>
              <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.receip_new"></bean:message></font>
              </td>         
                       
                       
                       <td><html:checkbox property="new_respt" value="">Yes/No</html:checkbox></td>
</tr>
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
			 <%System.out.println("i am here ------>10"); %>
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
			    	<html:select property="pay_ac_type" style="background-color:silver" onchange="submit()">
			    	<html:option value="Select">Select</html:option>
			        <%module_object=(ModuleObject[])request.getAttribute("pay_actype");
			        	for(int i=0;i < module_object.length;i++)
			            {
						 	 System.out.println("module_object"+ module_object);
				    	
			    	 %>	   			    			    	 
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    	<%	}	%>
			        	 
			         </html:select>
			         
			    </td>  
			    		         
              <%if(module_object!=null){%>
              <%if(dnthide!=null){ %>
			    <%System.out.println("INSIDE TRN acount-------??????"); %>
                <td> 
                <html:text property="trn_acno" onchange="submit()" size="6"></html:text>
                 </td>
                
                <%}}else{ %>
                 <td>
                 <html:text property="trn_acno" value="0" size="6"></html:text>
                 </td>
                <%} %>
</tr> 
			   
<tr>
			       <td>
			            <bean:message key="label.details"></bean:message>
			       </td>
			       <td>
			            <html:select property="details" style="background-color:silver" onblur="beforesubmit()">
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
			  
			 
<tr>
			     
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.closure_date"></bean:message></font>
			       </td>
			       <%if(dep_mast!=null){
			    	   
			    	    System.out.println("close date=="+ request.getAttribute("date"));
			       %>
			           <td> <html:text property="closure_date" readonly="true" value="<%=""+request.getAttribute("date")%>" ></html:text>
			           </td><%}else{ %>
			            
			           <td> <html:text property="closure_date" readonly="true" value="0"   ></html:text></td>
			           <%} %>
			      
</tr>

			
<tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.applied_int_rate"></bean:message></font>
			       </td>
			           <td> <html:text property="applied_int_rate" readonly="true" value="0"  size="6"></html:text>
			      </td>
</tr>
			 
			 
<tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.int_amt_pay"></bean:message></font>
			       </td>
			       <%if(intpayamount!=null){%> 
			               <td>  
			               <html:hidden property="hidval" value="<%=""+intpayamount%>" /> 
			               <html:text property="int_amt_pay"  value="<%=""+intpayamount%>"  readonly="true" size="6"></html:text></td>
			         <%}else{ %>
			           <td> <html:text property="int_amt_pay" readonly="true" value="0"  size="6"></html:text></td>
                      <%}%>   
</tr>
			
			
<tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.loan_availed"></bean:message></font>
			       </td>
			       
			       <%if(dep_mast!=null){
			      
			    	%>
			    <td> <html:text property="loan_avail" readonly="true" value="<%=""+dep_mast.getLoanAvailed()%>"></html:text>
			    </td>
			      <%}else{
			    	  %>
			    <td> <html:text property="loan_avail" readonly="true" value="0"  size="6"></html:text>
			      </td>
			     <%}%>
			      
</tr>
			
									
			
<tr> 
			<td> 
			    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
				<html:button property="butt_submit" onclick="set('Submit')"><bean:message key="label.submit"></bean:message></html:button>
				<%}else{ %>
				<html:button property="butt_submit" onclick="set('Submit')" disabled="true"><bean:message key="label.submit"></bean:message></html:button>
				
				<%} %>
				
				
				
				 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
				<html:button property="butt_update" onclick="set('Update')" >
				<bean:message key="label.update"></bean:message></html:button>
				 <%}else{ %>
				 <html:button property="butt_update" onclick="set('Update')" disabled="true">
				<bean:message key="label.update"></bean:message></html:button>
				 <%} %>
				
				<html:cancel property="butt_clear" onclick="submit()"><bean:message key="label.cancel" ></bean:message></html:cancel></td>
				
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
				<td><html:cancel property="butt_del"  onclick="set('Delete')" ><bean:message key="label.delete" ></bean:message></html:cancel></td>
			    <%} %>
</tr> 
                
       

</table>
</td>
<td>
	<%if(nstr!=null){ %>
	<jsp:include page="<%=nstr %>"></jsp:include>
	<%} %>
</td>
</table>

</html:form>
<%} %></body>

</html>