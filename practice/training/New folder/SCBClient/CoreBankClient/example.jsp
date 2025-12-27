<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="org.jacorb.concurrency.Request"%>
<%@page import="masterObject.general.AccountObject"%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Ammendments</title>
<center><h2 class="h2">Ammendments</h2></center>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />




<script type="text/javascript">
function set(target)
{
document.forms[0].forward.value=target;

document.forms[1].submit();

};
function HideShow(AttributeToHide)
{
	
	
	
	if(document.getElementById("pcombo").value=="Cash")
	{	
		alert("--cash-->");
		document.getElementById('scrol'+AttributeToHide).style.display='block';
		
		document.getElementById('Transfer'+AttributeToHide).style.display='none';
		document.getElementById('controlno'+AttributeToHide).style.display='none';
	}
	
	if(document.getElementById("pcombo").value=="Transfer")
	{
		alert("--tran-->");		
		document.getElementById('Transfer'+AttributeToHide).style.display='block';
		document.getElementById('scrol'+AttributeToHide).style.display='none';
		document.getElementById('controlno'+AttributeToHide).style.display='none';
	}	
	
	if(document.getElementById("pcombo").value=="Q/DD/PO")
	{
	
	alert("--ctrl-->");
	document.getElementById('controlno'+AttributeToHide).style.display='block';
	document.getElementById('Transfer'+AttributeToHide).style.display='none';
	document.getElementById('scrol'+AttributeToHide).style.display='none';
	}	
	
}
</script>




</head>
<body class="Mainbody" onload="HideShow(11)">
<%!
ModuleObject[] array_module,module_object;
DepositMasterObject dep_mast_ammend;
CustomerMasterObject cust_obj;
AccountObject acc_obj;
String[] details,pay_mode,combo_mat_cat,auto,int_freq;
String jsppath;
int i,j;
int value;

Double int_amt,mat_amt,balance,dep_amt;
%>
<%

System.out.println("inside accountopening");

int_amt = (Double)request.getAttribute("interestamount");
mat_amt = (Double)request.getAttribute("maturityamt");
System.out.println("in jsp  maturity mat====="+mat_amt);
acc_obj = (AccountObject)request.getAttribute("balance");

dep_mast_ammend = (DepositMasterObject)request.getAttribute("Amendments");

dep_amt = mat_amt;		
	

%>
<html:form action="/TermDeposit/Ammendments?pageId=13009" focus="<%=""+request.getAttribute("getfocus")%>">
<html:hidden property="forward" value="error" ></html:hidden>
<!-- main table-->

<table  class = "txtTable"  cellspacing="3">
	
 <td>
	<table class = "txtTable" width="20%" cellspacing="3">
		<tr>
		   		<td>
			       <bean:message key="label.td_actype"></bean:message>
			    </td>
			    <td><html:select property = "ac_type" styleClass="formTextFieldWithoutTransparent">
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
				<td>
			         <bean:message key="label.td_acno"></bean:message>
			     </td>	
			     <td><html:text property="ac_no"   size="6"  onchange="submit()" styleClass="formTextFieldWithoutTransparent"></html:text>
			     </td>
			     
			     
			     
			</tr>
   		  	
   		  	<tr>
				<td>
			   		 <bean:message key="label.cid"></bean:message>
			   	</td>
			    <%if(dep_mast_ammend!=null){
			
			    			    	
			    	 System.out.println("depreinvestment ----12-->"+dep_mast_ammend.getCustomerId());
			    	
			    	%>
			  	<td>
			    	<html:text property="cid" size="12"  value="<%=""+ dep_mast_ammend.getCustomerId() %>" style="background-color:silver"></html:text>
			    	</td>
			    <%}else{ %>
			    		<td><html:text property="cid" size="12" value="0" style="background-color:silver"></html:text>
			    </td><%} %>
			
			</tr>
			
			<tr>
				<td>
			   		 <bean:message key="label.dep_amt"></bean:message>
			   	</td>
			     <%if(dep_mast_ammend!=null){
			
			    			    	
			    	 System.out.println("depreinvestment ------>"+dep_mast_ammend.getDepositAmt());
			    	
			    	%>
			  	<td>
			    	<html:text property="dep_amt" size="12"  value="<%=""+ dep_mast_ammend.getDepositAmt() %>" style="background-color:silver"></html:text>
			    	</td>
			    <%}else{ %>
			    		<td><html:text property="dep_amt" size="12" value="0" style="background-color:silver"></html:text>
			    </td><%} %>
			   
			</tr>
			<tr>
				<td>
			    	<bean:message key="label.dep_date"></bean:message>
			    </td>
			  
			    
			    <%if(dep_mast_ammend!=null){
			
			    			    	
			    	 System.out.println("depreinvestment ---11--->"+dep_mast_ammend.getDepDate());
			    	
			    	%>
			  	<td>
			    	<html:text property="dep_date" size="12"  value="<%=""+ dep_mast_ammend.getDepDate() %>" style="background-color:silver"></html:text>
			    	</td>
			    <%}else{ %>
			    		<td><html:text property="dep_date" size="12" value="0" style="background-color:silver"></html:text>
			    </td><%} %>
			   
			 

			   
			</tr>
			
			
			  
			      
			
			
			<tr>
				<td>
			    	<bean:message key="label.Period_in_days"></bean:message>
			    </td>
			    
			    
			    
			      <%if(dep_mast_ammend!=null){
			    	   
			    	    System.out.println("deposit days=="+ dep_mast_ammend.getDepositDays());
			       %>
			           
			           		<td><html:text property="period_of_days" size="12"  value="<%=""+ dep_mast_ammend.getDepositDays() %>" ></html:text>
			           </td>	<%}else{ %>
			            
			            <td>
			          <html:text property="period_of_days"   size="6" styleClass="formTextFieldWithoutTransparent"></html:text>
			           <%} %>
			           
						 </td>   
			 
			    	
			    
			</tr>
			    
			<tr>
				<td>
			    	<bean:message key="label.mat_date"></bean:message>
			    </td>
			    
			      <%if(dep_mast_ammend!=null){
			
			    			    	
			    	 System.out.println("depreinvestment ---0--->"+dep_mast_ammend.getMaturityDate());
			    	
			    	%>
			  	<td>
			    	<html:text property="mat_date" size="12"  value="<%=""+ dep_mast_ammend.getMaturityDate() %>" style="background-color:silver"></html:text>
			    	</td>
			    <%}else{ %>
			    		<td><html:text property="mat_date" size="12" value="0" style="background-color:silver"></html:text>
			    </td><%} %>
			    
			    
			    
			    
			     <td><html:select property="combo_mat_cat" styleClass="formTextFieldWithoutTransparent">
			    	<% combo_mat_cat=(String[])request.getAttribute("combo_mat_cat");
			    			for(int i=0;i<combo_mat_cat.length;i++)
			    			{
			    			 System.out.println("combo_mat_cat"+ combo_mat_cat);
			    	%>	    	
			    			    	 
			    				<html:option value="<%=""+combo_mat_cat[i]%>"><%=""+combo_mat_cat[i]%></html:option>
			    	<%
			    		
			    			}
			    	%>	
			   		</html:select>
			    </td>
			</tr>
			
			<tr>
				<td>
			    	<bean:message key="label.combo_autorenewal"></bean:message>
				</td>
			    <td>   
			    	<html:select property="combo_autorenewal" styleClass="formTextFieldWithoutTransparent">
			    	    <% auto=(String[])request.getAttribute("auto");
			    			for(int i=0;i<auto.length;i++)
			    			{
			    	
			        		 System.out.println("auto"+ auto);
			    		%>	    	
			    			    	 
			    				<html:option value="<%=""+auto[i]%>"><%=""+auto[i]%></html:option>
			    		<%
			    		
			    			}
			    		%>	
			       	</html:select>
			    </td>
			</tr>
			    
			    
			<tr>
				<td>
			    	<bean:message key="label.introduceractypeno"></bean:message>
			    </td>
			     		      
			    <td><html:select property="intro_ac_type" styleClass="formTextFieldWithoutTransparent">
			    		<% module_object = (ModuleObject[])request.getAttribute("intro_type");
			    			for(int i=0;i<module_object.length;i++){
			    		      	 System.out.println("intro_acctype"+ module_object);
			    	
			    		%>	    	
			    				<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    		<%
			    			}
			    		%>	
			       	</html:select>
			    </td>
			          
			           <%if(dep_mast_ammend!=null){
			
			    			    	
			    	 System.out.println("depreinvestment ---1--->"+dep_mast_ammend.getIntroAccno());
			    	
			    	%>
			  	<td>
			    	<html:text property="intro_ac_no" size="12"  value="<%=""+ dep_mast_ammend.getIntroAccno() %>" style="background-color:silver"></html:text>
			    	</td>
			    <%}else{ %>
			    		<td><html:text property="intro_ac_no" size="12" value="0" style="background-color:silver"></html:text>
			    </td><%} %> 
			          
			            
			  
			</tr>
			        
			<tr>
				<td>
			    	<bean:message key="label.combo_pay_mode"></bean:message>
			    </td>
			    <td>	
			    	<html:select property="pay_mode" styleClass="formTextFieldWithoutTransparent">	
			        	<%pay_mode=(String[])request.getAttribute("pay_mode");
			             	 for(int i=0;i < pay_mode.length;i++)
			             	 {
						    	 System.out.println("pay_mode"+ pay_mode);
				    	
			    	    %>	   			    			    	 
			    				<html:option value="<%=""+pay_mode[i]%>"><%=""+pay_mode[i]%></html:option>
			    		<%	  }		%>
			        	 
			           </html:select>
			           
			        		        	 
			     </td>
			     
			     
			</tr>
			    
			    
			<tr>
				<td>
			    	<bean:message key="label.combo_pay_actype"></bean:message>
			    </td>
			    <td>
			    	<html:select property="pay_ac_type" styleClass="formTextFieldWithoutTransparent">
			        <%module_object=(ModuleObject[])request.getAttribute("pay_actype");
			        	for(int i=0;i < module_object.length;i++)
			            {
						 	 System.out.println("module_object"+ module_object);
				    	
			    	 %>	   			    			    	 
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    	<%	}	%>
			        	 
			         </html:select>
			    </td>  
			    
			      <%if(dep_mast_ammend!=null){
			
			    			    	
			    	 System.out.println("depreinvestment ---2--->"+dep_mast_ammend.getReceivedAccno());
			    	
			    	%>
			  	<td>
			    	<html:text property="pay_mode_ac_no" size="12"  value="<%=""+ dep_mast_ammend.getReceivedAccno() %>" style="background-color:silver"></html:text>
			    	</td>
			    <%}else{ %>
			    		<td><html:text property="pay_mode_ac_no" size="12" value="0" style="background-color:silver"></html:text>
			    </td><%} %> 
			          
			    			      			         
			</tr> 
			   
			    
			   <tr>
			       <td>
			       			
			      	 <bean:message key="label.combo_amt_received"></bean:message>
			      			 
			       </td>
			       <td>
			            <html:select property="amt_recv" styleClass="formTextFieldWithoutTransparent" onchange="submit()" styleId="pcombo">  
			            <%pay_mode=(String[])request.getAttribute("amt_recv");
			             	 for(int i=0;i < pay_mode.length;i++)
			             	 {
						    	 System.out.println("amt_recv"+ pay_mode);
				    	
			    	      %>	   			    			    	 
			    		<html:option value="<%=""+pay_mode[i]%>"><%=""+pay_mode[i]%></html:option>
			    	<%}%>
			        			         
			         	</html:select>
			       </td>
			    </tr> 
			    
			    <tr>
			    
			      <td>
              
    		        <bean:message key="label_name1"></bean:message>
    		   
               </td>
               <%if(acc_obj!=null){ %>
               
			     <td>
			        <html:text property="label_name"   value="<%=""+ acc_obj.getAccname() %>" styleClass="formTextFieldWithoutTransparent"></html:text>
			     </td>
			   <%}else{ %>  
			  
			  <td>
			        <html:text property="label_name"  size="6" value="0" styleClass="formTextFieldWithoutTransparent"></html:text>
			    <%} %>
			     </td>
			    
			    </tr>
			     
			     <tr>
			       <td>
			            <bean:message key="label.details"></bean:message>
			       </td>
			       <td>
			            <html:select property="details" styleClass="formTextFieldWithoutTransparent">
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
			    
    
    
	
</table>	
	<table  class = "txtTable" width="10%" cellspacing="3" style="border-bottom-color:inactiveborder;border-bottom-style:solid;border-bottom-style:solid;border-left-color:inactiveborder;border-left-style:solid;border-right-style:solid;border-right-color:inactiveborder;border-top-color:inactiveborder;border-top-style:solid;">
	<tr> 
			<td>
				<html:submit onclick="set('Submit')" styleClass="ButtonBackgroundImage">
				<bean:message key="label.submit"></bean:message></html:submit>
				</td>
				 
	</tr>	
</table>

</td>		
	
<td>

<table  class = "txtTable" width="30%" cellspacing="3" style="border-bottom-color:inactiveborder;border-bottom-style:solid;border-bottom-style:solid;border-left-color:inactiveborder;border-left-style:solid;border-right-style:solid;border-right-color:inactiveborder;border-top-color:inactiveborder;border-top-style:solid;">

			   <tr>
			       <td>
		     	         
			             <bean:message key="label.Int_freq"></bean:message>
	               </td>
	               <td>
	           			 <html:select property="interest_freq" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
			         		 <%int_freq=(String[])request.getAttribute("int_freq");
			             	 for(int i=0;i < int_freq.length;i++)
			             	 {
						    	 System.out.println("int_freq"+ int_freq);
				    	
			    	      %>	   			    			    	 
			    		<html:option value="<%=""+int_freq[i]%>"><%=""+int_freq[i]%></html:option>
			    			<%}%>
			        			         
			         	</html:select>
		        
		         </td>
		         
		          
		        	<td>
		                 <bean:message key="label.IntRate"></bean:message></td>
		                 <td><html:text property="Int_rate" readonly="true" size="6" value="8.00" styleClass="formTextFieldWithoutTransparent" ></html:text>
		             </td>    
		         
			    </tr>
			    
			    <tr>
			     <td>
			   		  
			   		   <bean:message key="label.Int_payableamt"></bean:message>
			   	  </td>
			   	  
			   	  <%if(int_amt!=null){
			   	  %>
			   		  <td><html:text property="int_payable" readonly="true" value="<%=""+int_amt %>" size="6" styleClass="formTextFieldWithoutTransparent"></html:text>
				      </td>
			   	  <%}else{ %>
			   	  
			      <td><html:text property="int_payable" readonly="true" value="0" size="6" styleClass="formTextFieldWithoutTransparent"></html:text>
			      
			 <%} %>
			 </td>
				   <td>
			          
			           <bean:message key="label.Mat_amt"></bean:message>
			       </td>
			           <%if(dep_mast_ammend!=null){
			
			    			    	
			    	 System.out.println("depreinvestment ------>"+dep_mast_ammend.getMaturityAmt());
			    	
			    	%>
			  	<td>
			    	<html:text property="mat_amt" size="12"  value="<%=""+ dep_mast_ammend.getMaturityAmt() %>" style="background-color:silver"></html:text>
			    	</td>
			    <%}else{ %>
			    		<td><html:text property="mat_amt" size="12" value="0" style="background-color:silver"></html:text>
			    </td><%} %> 
			    
			        
			 </tr>
</table>


<table  class = "txtTable" width="20%" cellspacing="3" style="border-bottom-color:inactiveborder;border-bottom-style:solid;border-bottom-style:solid;border-left-color:inactiveborder;border-left-style:solid;border-right-style:solid;border-right-color:inactiveborder;border-top-color:inactiveborder;border-top-style:solid;" >
<tr id="scrol11">
			
			 	<td>
			 		
			 		 <bean:message key="label.scroll_no"></bean:message>
			  		
			 	</td>
			 	<td>
			   		 <html:text property="scroll_no" value="0" size="10" styleClass="formTextFieldWithoutTransparent"></html:text>
    		  	</td> 
    		  	
    		  	
			 	
    		    
    		    <td>
    		       
    		        <bean:message key="label.date"></bean:message>
    		    </td>
    		    <td>
    		    <html:text property="date" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" style="background-color:silver"></html:text>
    		   		
			    </td>
</tr>

<tr id="controlno11">
			
			 	
			 	<td>
			 		
			 		 <bean:message key="label.controlno"></bean:message>
			  		
			 	</td>
			 	<td>
			   		 <html:text property="control_no" value="0" size="10" styleClass="formTextFieldWithoutTransparent"></html:text>
    		  	</td> 
    		    
    		    
    		    <td>
    		       
    		        <bean:message key="label.date"></bean:message>
    		    </td>
    		    <td>
    		    <html:text property="date" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" style="background-color:silver"></html:text>
    		   		
			    </td>

<tr>
             
			   
			    
			     <td>
			       		
			       		<bean:message key="label.Amt"></bean:message>
			     </td>
			     <td>
			     		 <html:text property="amount" value="<%=""+dep_amt %>" size="6" styleClass="formTextFieldWithoutTransparent"></html:text>
			     </td>
			
			</tr>     



<tr id="Transfer11">
			
			
				<td>
			    	<bean:message key="label.trf_actype"></bean:message>
			    </td>
			    <td>
			    	<html:select property="trf_actype" styleClass="formTextFieldWithoutTransparent">
			        <%module_object=(ModuleObject[])request.getAttribute("pay_actype");
			        	for(int i=0;i < module_object.length;i++)
			            {
						 	 System.out.println("module_object"+ module_object);
				    	
			    	 %>	   			    			    	 
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    	<%	}	%>
			        	 
			         </html:select>
			    </td>  
			    
			        			         
				
			    
    		    <td>
    		       
    		        <bean:message key="label.trf_acno"></bean:message>
    		    </td>
    		    
            
            <%if(dep_mast_ammend!=null){
			
			    			    	
			    	 System.out.println("depreinvestment ------>"+dep_mast_ammend.getTransferAccno());
			    	
			    	%>
			  	<td>
			    	<html:text property="trf_acno" size="12"  value="<%=""+ dep_mast_ammend.getTransferAccno() %>" style="background-color:silver"></html:text>
			    	</td>
			    <%}else{ %>
			    		<td><html:text property="trf_acno" size="12" value="0" style="background-color:silver"></html:text>
			    </td><%} %> 
			          
            
    		         
                 <td>
    		       
    		        <bean:message key="lable.bal"></bean:message>
    		    </td>
    		    
    		     <%if(acc_obj!=null){
				  %>
			
			           <td> <html:text property="balance_amt" readonly="true" value="<%=""+ acc_obj.getAmount() %>"   styleClass="formTextFieldWithoutTransparent"></html:text>
			      </td>
			        <%}else{ %>
			        <td> <html:text property="balance_amt" readonly="true" value="0"  size="6" styleClass="formTextFieldWithoutTransparent"></html:text>
			        <%} %>
			      </td>
    		    
    		    
    		    
    		   </tr>
</table>

<table  class = "txtTable" width="20%" cellspacing="3" style="border-bottom-color:silver;border-bottom-style:solid;border-bottom-style:solid;border-left-color:silver;border-left-style:solid;border-right-style:solid;border-right-color:silver;border-top-color:silver;border-top-style:solid;">

	<td>
				<%
		      		jsppath =(String)request.getAttribute("flag");
		      	    if(jsppath!=null){
		    			System.out.println("jsp path"+jsppath);   
		      	%>
		     			<jsp:include page="<%=jsppath.toString().trim() %>"></jsp:include>
		      	<%
		    		}
		   		%>	 
			   
	</td>


</table>

</td>	
	
	  
</table>
</html:form>


</body>
</html>