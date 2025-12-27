<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>.
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="masterObject.general.NomineeObject"%>
<%@page import="java.util.Map"%>


<%@page import="masterObject.general.AccountObject"%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Ammendments</title>
    <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
    <h2 class="h2"> <center> Ammendments </center></h2>
    
<script type="text/javascript">

function ckValidate(target)
{
    alert(target);
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
    document.forms[0].first.value=target;
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
         		alert("plz enter limit digits only");
         		txt.value="";
         		return false;
          	}
         }
 
function HideShow(AttributeToHide)
{

 	if(document.getElementById("totaltesting").value!=0)
	{
		
		alert(document.getElementById("totaltesting").value);	
		
			
	}
	 var ele=document.forms[0].elements;
	for(var i=0;i<ele.length;i++)
	{
	if(ele[i].type=="hidden")
	{
	ele[i].value="";
	}
	}
	
};



function perioddayssubmit(target)
{
   alert(target);
   document.forms[0].forward.value=target;
   alert(document.forms[0].forward.value);
   document.forms[0].submit();
}

function introsubmit(target)
{
   alert(target);
   document.forms[0].forward.value=target;
   alert(document.forms[0].forward.value);
   document.forms[0].submit();

}

function paymodesubmit(target)
{
   alert(target);
   document.forms[0].forward.value=target;
   alert(document.forms[0].forward.value);
   document.forms[0].submit();

}

function intfreqsubmit(target)
{
  
  document.forms[0].freqint.value=target;
  alert(document.forms[0].freqint.value);
  document.forms[0].submit();
}


function set(target)
{
    
    if(document.forms[0].ac_no.value==0)
    {
    alert("Deposit Account Number Cannot Be Zero");
    }
    else if(document.forms[0].ac_no.value=="")
    {
    alert("Deposit Account Number Cannot Be Blank");
    }
    else if(document.forms[0].period_of_daystwo.value=="")
    {
    alert("Deposit Days Cannot Be Blank");
    }
    else if(document.forms[0].period_of_daystwo.value==0)
    {
    alert("Deposit Days Cannot Be Zero");
    }
    else
    {
    alert(target);
    document.forms[0].forward.value=target;
    document.forms[0].submit();
    }
}
function  funclear(){
alert("Clear")

var v = document.forms[0].elements;
for(var i=0;i<=v.length;i++){

if(v[i].type =="text"){

v[i].value = " ";

   }
 }
};

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
function only_numbers()
{
        
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57) ) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
};





</script>







</head>
<body class="Mainbody" onload="HideShow(11)">
<%!
ModuleObject[] array_module,module_object;
DepositMasterObject dep_mast_ammend;
CustomerMasterObject cust_obj;
AccountObject acc_obj;
String[] details,pay_mode,combo_mat_cat,auto,int_freq;
String pagenew,MatDateCal;
int i,j;
int value;
String Ac_amount,Accountnotfound;
NomineeObject[] nomineeObjects;
Double mat_amt,balance,dep_amt;
Double int_amt;
String tran_name;
double[] combo_mat_cat1;
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
String nstr=(String)request.getAttribute("flag");
Accountnotfound=(String)request.getAttribute("Accountnotfound");
if(nstr!=null){
System.out.println("inside Ammendments.jsp valus----->"+nstr);
}
String maturity=(String)request.getAttribute("matdate");
if(maturity!=null){
System.out.println("Maturity date"+ maturity);
}
tran_name=(String)request.getAttribute("name");
if(tran_name!=null){
System.out.println("TRAN _NAme----<>"+tran_name);
}
Ac_amount=(String)request.getAttribute("Amount");
if(Ac_amount!=null){
System.out.println("THE AMONT IN JSP IS----<>"+Ac_amount);
}
int_amt = (Double)request.getAttribute("intereste");
if(int_amt!=null){
System.out.println("The interest payable is in JSP=========>< "+int_amt);
}
mat_amt = (Double)request.getAttribute("maturityamt");
if(mat_amt!=null){
System.out.println("in jsp  maturity mat====="+mat_amt);
}
acc_obj = (AccountObject)request.getAttribute("ACOUNTDEtail");
if(acc_obj!=null){
System.out.println("The Acc_Nme----> "+acc_obj);
System.out.println("The Acc_Nme----> "+acc_obj.getAccname());
System.out.println("The AC_Amount----> "+acc_obj.getAmount());
}


String amendate=(String)request.getAttribute("MatDateCal");
String hide=(String)request.getAttribute("Todide");


dep_mast_ammend = (DepositMasterObject)request.getAttribute("DepMastObj");

dep_amt = mat_amt;	
Integer periodchng=(Integer)request.getAttribute("chperddays");

System.out.println("The changed period of days is "+periodchng);
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/Ammendments?pageId=13009" focus="<%=""+request.getAttribute("getfocus")%>">
<html:hidden property="forward" value="error" ></html:hidden>
<html:hidden property="detail" ></html:hidden>
<html:hidden property="amd_det" ></html:hidden>
<html:hidden property="accountgen" />
<html:hidden property="freqint"></html:hidden>
<html:hidden property="validate" styleId="validat"></html:hidden>
<html:hidden property="testing" styleId="totaltesting"></html:hidden>
<html:hidden property="first"></html:hidden>

<!-- main table-->

<table  class = "txtTable"  cellspacing="3" style="border-bottom-color:green;border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:black;border-left-style:hidden;border-right-style:hidden;border-right-color:green;border-top-color:green;border-top-style:hidden;">
	
<tr>
	
 <td>
	<table class = "txtTable" width="20%" cellspacing="0"  	style="border: thin solid maroon;">
	   		<td>
			       <bean:message key="label.td_actype"></bean:message>
			    </td>
			    <td><html:select property = "ac_type" tabindex="1" styleClass="formTextFieldWithoutTransparent" onchange="HideShow()" >
			    	<% array_module=(ModuleObject[])request.getAttribute("Dep type");
			    	if(array_module!=null){
			    			for(int i=0;i<array_module.length;i++)
			    			{
			        		 	
			    	%>	    	
			    				<html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%
			    			}}
			    	%>	
			    	 </html:select>
    		   	 </td>
    		   	 
    	
  
   		  		<tr>
				<td>
			         <bean:message key="label.td_acno"></bean:message>
			     </td>	
			     <td><html:text property="ac_no" tabindex="2"  size="6" onkeypress="return numbersonly(this)" onblur="ckValidate('firsttime')" onkeyup="numberlimit(this,'10')" styleClass="formTextFieldWithoutTransparent" ></html:text>
			     </td>
			     
			     			     
			</tr>
			<tr>
			<td>
			<bean:message key="label.custname"/>
			</td>
			<td>
			<html:text property="cust_name" size="15" readonly="true"></html:text>
			</td>
			</tr>
			
   		  	<tr>
				<td>
			   		 <bean:message key="label.cid"></bean:message>
			   	</td>
			    
			    
			    		<td><html:text property="cid"  size="12" onkeypress="return numbersonly(this)" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>
			    </td>
			
			</tr>
			
			
					<tr>
				<td>
			   		 <bean:message key="label.dep_amt"></bean:message>
			   	</td>
			     
			    		<td><html:text property="dep_amttwo"  size="12" onkeypress="return numbersonly(this)" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
			    </td>
			   
			</tr>
			<tr>
				<td>
			    	<bean:message key="label.dep_date"></bean:message>
			    </td>
			  
			    
			    
			    
			    		<td><html:text property="dep_date" size="12" readonly="true" onkeypress="return numbersonly(this)"  styleClass="formTextFieldWithoutTransparent"></html:text>
			    </td>
			   
			 			   
			</tr>
			
			
			
			<tr>
				<td>
			    	<bean:message key="label.Period_in_days"></bean:message>
			    </td>
			  
			     
			   
			    	<td><html:text property="period_of_daystwo"   size="6" onkeypress="return numbersonly(this)" onchange="perioddayssubmit('depositdays')" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>	    
			     
			</tr>
			    
			  <tr>
				<td>
			    	<bean:message key="label.mat_date"></bean:message>
			    </td>
			   <%if(amendate!=null){ %>
			   	<td><html:text property="mat_date" onkeypress="return numbersonly(this)" value="<%=""+amendate%>" size="12" onblur="submit()" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
			    <%}else{%>
			    <td><html:text property="mat_date" size="12" onkeypress="return numbersonly(this)" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			   
			    <%}%>
			    
			    
			      <td>
			       <html:select property="combo_mat_cat" styleClass="formTextFieldWithoutTransparent">
			    	<% combo_mat_cat1=(double[])request.getAttribute("Cat_type");
			    	 if(combo_mat_cat1!=null){ 		
			    	
			    			
			    			 	System.out.println("combo_mat_cat"+ combo_mat_cat1);
			    	%>	    				    		

	    	 					<html:option value="0">NONE</html:option>
			                    <html:option value="1">Catagory : Individual: <%=combo_mat_cat1[1]%></html:option>
			    				<html:option value="2">Sub-Category: <%=combo_mat_cat1[2]%></html:option>
			    				<html:option value="3">BOTH: <%=combo_mat_cat1[1]+combo_mat_cat1[2]%></html:option>
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
			    	   <html:option value="N">None</html:option>
			    	   <html:option value="M">Maturity Amount</html:option>
			       	   <html:option value="D">Deposit Amount</html:option>
			       	</html:select>
			    </td>
			   
			</tr>
			    
			<tr>
				<td>
			    	<bean:message key="label.introduceractypeno"></bean:message>
			    </td>
			      
			    <td><html:select property="intro_ac_type" styleClass="formTextFieldWithoutTransparent" >
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
			          
			           
			  	<td>
			    	<html:text property="intro_ac_no" size="12" onkeypress="return numbersonly(this)" onchange="introsubmit('introacno')" onkeyup="numberlimit(this,'10')" styleClass="formTextFieldWithoutTransparent"></html:text>
			    	</td>
			    
			            
			  
			</tr>
			        
		<tr>
				<td>
			    	<bean:message key="label.combo_pay_mode"></bean:message>
			    </td>
			    
			    <td>	
			    	<html:select property="pay_mode" styleClass="formTextFieldWithoutTransparent"  onchange="view()">	
			        	<html:option value="Select">Select</html:option>
			        	<html:option value="T">Transfer</html:option>
			        	<html:option value="C">Cash</html:option>
			        	<html:option value="G">Q/DD/PO</html:option>
			           </html:select>
			           			        		        	 
			     </td> 
			    	     
			</tr>
			<tr>
				<td>
			    	<bean:message key="label.combo_pay_actype"></bean:message>
			    </td>
			    
			    <td>
			    	<html:select property="pay_ac_typetwo" styleClass="formTextFieldWithoutTransparent">
			    	<html:option value="1002001">SB</html:option>
			        <html:option value="1007001">CA</html:option>
			        <html:option value="1018001">CA Br</html:option>     
			        	 
			         </html:select>
			    </td>  
			    
			      
			    		<td><html:text property="pay_mode_ac_no" onkeypress="return numbersonly(this)" size="12" onkeyup="numberlimit(this,'10')" styleClass="formTextFieldWithoutTransparent" onchange="paymodesubmit('payacno')"></html:text>
			    </td> 
			          
			    			      			         
			</tr> 
			   			     

			    
			  <tr>
 	    
		 
			    <tr>
			    
			      <td>
              
    		        <bean:message key="label_name1"></bean:message>
    		   
               </td>
		  <td>
			        <html:text property="label_name" readonly="true" style="border:transparent;background-color:beige;color:red"></html:text>
			</td>
			    
			    </tr>
			     
			     <tr>
			       <td>
			            <bean:message key="label.details"></bean:message>
			       </td>
			       <td>
			            <html:select property="details" styleClass="formTextFieldWithoutTransparent" onblur="beforSubmit()">
			            <html:option value="Select">Select</html:option>
			            <html:option value="Personal">Personal</html:option>
			        	<html:option value="Introducer">Introducer</html:option>
			        	<html:option value="JointHolders">JointHolders</html:option>
			        	<html:option value="Nominee">Nominee</html:option>
			        	<html:option value="SignatureDetails">SignatureDetails</html:option>
			         	</html:select>
			       	</td>
			   </tr>
			    
    
    
	
</table>	
	<table  class = "txtTable" width="10%" cellspacing="3" style="border-bottom-color:teal;border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:teal;border-left-style:hidden;border-right-style:hidden;border-right-color:teal;border-top-color:teal;border-top-style:hidden;">
<tr> 
			<td>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
				<html:button property="sub" onclick="set('Submit')" styleClass="ButtonBackgroundImage">
				<bean:message key="label.submit"></bean:message></html:button>
				<%}else{ %>
				<html:button property="sub" onclick="set('Submit')" styleClass="ButtonBackgroundImage" disabled="true">
				<bean:message key="label.submit"></bean:message></html:button>
				<%} %>
			</td>
			<td> <html:button property="cler" onclick="return funclear()" styleClass="ButtonBackgroundImage" >
			<bean:message key="label.clear"></bean:message></html:button>
			</td>	 
	</tr>	
</table>
<td>
<table  class = "txtTable" width="10%" cellspacing="5" style="border-bottom-color:teal;border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:teal;border-left-style:hidden;border-right-style:hidden;border-right-color:teal;border-top-color:teal;border-top-style:hidden;">
<tr>

</tr>
	
</table>	
<td>

<table  class = "txtTable" frame="above" cellspacing="3" style="border-bottom-color:olive;border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:aqua;border-left-style:hidden;border-right-style:hidden; ;border-right-color:lime;border-top-color:;border-top-style:hidden;">

			   <tr>
			       <td>
		     	         
			             <bean:message key="label.Int_freq"></bean:message>
	               </td>
	               <td>
	           			 <html:select property="interest_freq" styleClass="formTextFieldWithoutTransparent" onchange="intfreqsubmit('inrfreq')">
			         		 <html:option value="Select">Select</html:option>
			         		 <html:option value="M">Monthly</html:option>
			         		 <html:option value="Q">Quarterly</html:option>
			         		 <html:option value="Y">Yearly</html:option>
			         		 <html:option value="O">On-Maturity</html:option>			        			         
			         	</html:select>
		        
		         </td>
		         
		          
		        	<td>
		                 <bean:message key="label.intrate"></bean:message></td>
		                 
		                 
		                 <td><html:text property="int_rate"  size="10"  readonly="true" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
		                 
			    </tr>
			    
			    <tr>
			     <td>
			   		   
			   		   <bean:message key="label.Int_payableamt"></bean:message>
			   	  </td>
			   	  
			   	  
			   	  
			      <td><html:text property="int_payable" readonly="true"  size="8" styleClass="formTextFieldWithoutTransparent"></html:text>
			      
			 
			 </td>
				   
				   <td>
			          
			           <bean:message key="label.Mat_amt"></bean:message>
			       </td>
			           
			  	
			  
			    		<td><html:text property="mat_amt" readonly="true" size="12" styleClass="formTextFieldWithoutTransparent"></html:text>
			    </td> 
			    
			        
			 </tr>
</table>




<table align="right"  valign="top" width="10%">
<%if(nstr!=null){%>
    <tr> 
    <td>
	<% System.out.println("At 726 Ammendments.jsp===============================================nstr  >"+nstr);
    pagenew=nstr;%>
	   <jsp:include page="<%=pagenew %>"></jsp:include>
	   
			   <%System.out.println("After include------>"+nstr);%>
    </td>
    </tr>
      
		 
				 
				 
	<%} %>
        
  
 </table>
 <tr>
				<td>
					<%String enable=(String)request.getAttribute("enable"); %>
					<%if(enable!=null){ %>
 					<table class = "txtTable" width="20%" cellspacing="0"  	style="border: thin solid maroon;">
  						<html:hidden property="nomforward"></html:hidden>
  						<html:hidden property="nomvalidations"></html:hidden>
  				<%String showcid=(String)request.getAttribute("showcid"); %>
  				<% nomineeObjects=(NomineeObject[])session.getAttribute("Nominee"); %>
  					<%if(showcid==null){ %>
  					<tr>
    					<td>Has Account</td>
    					<td><html:select property="has_ac" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
    						<html:option value="SELECT">SELECT</html:option>
    						<html:option value="yes">yes</html:option>
    						<html:option value="no">no</html:option>
    					</html:select></td>
   					</tr>
   					<%}else{ %>
  					<tr>
  						<td>Has Account</td>
  						<td><html:text property="has_ac" styleClass="formTextFieldWithoutTransparent" value="<%=""+showcid %>"></html:text> </td>
  					</tr>
					<%}%>
   					<%if(showcid!=null){ %>
   				<tr>
   						<td><bean:message key="label.custid"></bean:message></td>
   						<%if(nomineeObjects!=null){ %>
      					<td><html:text property="cidis" size="10" value="<%=""+nomineeObjects[0].getCustomerId() %>" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
    					<%}else{ %>
      					<td><html:text property="cidis" size="10" onblur="checksub('Cidis')" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" ></html:text></td>
    					<%} %>
    				</tr>
    				<%} %>	
     				<%if(nomineeObjects!=null){ %>      
   					<tr>
    					<td><bean:message key="label.name"></bean:message></td>
   						<%if(nomineeObjects[0].getNomineeName()!=null){ %>
    					<td><html:text property="nomname"  styleClass="formTextFieldWithoutTransparent" value="<%=""+nomineeObjects[0].getNomineeName() %>"></html:text> </td>
    					<%}%>
   					</tr>
   					<tr>
     					<td><bean:message key="label.dob"></bean:message> </td>
     					<%if(nomineeObjects[0].getNomineeDOB()!=null){ %>
   						<td><html:text property="dob" value="<%=""+nomineeObjects[0].getNomineeDOB() %>" styleClass="formTextFieldWithoutTransparent"></html:text> </td>  
						<%} %>    
   					</tr>
   					<tr>
    					<td><bean:message key="label.gender"></bean:message></td>
    					<%if(nomineeObjects[0].getSex()!=0){ %>
    					<td><html:text property="gender" styleClass="formTextFieldWithoutTransparent" value="Male" readonly="true"></html:text></td>
    					<%}else{ %>
    					<td><html:text property="gender" styleClass="formTextFieldWithoutTransparent" value="Female" readonly="true"></html:text></td>
    					<%} %>
   					</tr>
   					<tr>
    					<td><bean:message key="label.address"></bean:message></td>
     					<%if(nomineeObjects[0].getNomineeAddress()!=null){ %>
    					<td><html:textarea property="address" value="<%=""+nomineeObjects[0].getNomineeAddress() %>" styleClass="formTextFieldWithoutTransparent"></html:textarea></td>
     					<%} %>
   					</tr>
			   		<tr>
     					<td><bean:message key="label.rel"></bean:message></td>
     					<%if(nomineeObjects[0].getNomineeRelation()!=null){ %>
     					<td><html:text property="rel_ship" value="<%=""+nomineeObjects[0].getNomineeRelation() %>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
   						<%} %>
   					</tr>
   					<tr>
    					<td><bean:message key="label.percentage"></bean:message></td>
    					<%if(nomineeObjects[0].getPercentage()!=0){ %>
						<td><html:text property="percentage" value="<%=""+nomineeObjects[0].getPercentage() %>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
						<%} %>    
   					</tr>
    				<%}else{ %>      
   					<tr>
    					<td><bean:message key="label.name"></bean:message></td>
						<td><html:text property="nomname"  styleClass="formTextFieldWithoutTransparent" onkeypress="return only_alpha1()"></html:text> </td>    
   					</tr>
   					<tr>
     					<td><bean:message key="label.dob"></bean:message> </td>
						<td><html:text property="dob"  styleClass="formTextFieldWithoutTransparent" onblur="ValidateForm1()" onkeypress="return numbersonly_date(this)" ></html:text> </td>
   					</tr>
   					<tr>
    					<td><bean:message key="label.gender"></bean:message></td>
    					<td><html:select property="gender" styleClass="formTextFieldWithoutTransparent">
    						<html:option value="1">Male</html:option>
							<html:option value="0">Female</html:option>    
    					</html:select></td>
   					</tr>
   					<tr>
    					<td><bean:message key="label.address"></bean:message></td>
    					<td><html:textarea property="address" styleClass="formTextFieldWithoutTransparent"></html:textarea></td> 
   					</tr>
   					<tr>
     					<td><bean:message key="label.rel"></bean:message></td>
   						<td><html:text property="rel_ship"  styleClass="formTextFieldWithoutTransparent" onkeypress="return only_alpha()"></html:text></td>
   					</tr>
   					<tr>
    					<td><bean:message key="label.percentage"></bean:message></td>
						<td><html:text property="percentage" onblur="submit()" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()"></html:text></td>
   					</tr>
    				<%} %>     
			</table>            
			<%} %>
		</td>
	</tr>
        <tr>
        <td>
        <%if(request.getAttribute("enable1")!=null && request.getAttribute("joint")!=null){ %>
         <table style="border: thin solid maroon;"><tr><td style="border: thin solid maroon;">CID</td></tr>
        <%int[] cid=(int[])request.getAttribute("joint");
        for(int i=0;i<cid.length;i++)
        {
        %>
     <tr bordercolor="red"><td style="border: thin solid maroon;"><%=""+cid[i]%> </td></tr>
     <br/>
        <%} %>
        </table>
        <%} %>
        </td>
        </tr>


	
	
	  
</table>

</html:form>

<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>