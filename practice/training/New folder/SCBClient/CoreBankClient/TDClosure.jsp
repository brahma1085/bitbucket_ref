<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="java.util.Map"%>

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
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
    
   <center> <h2 class="h2">Deposit Closure</h2></center>

     
<script type="text/javascript">
function deletedata()
{
   alert("Are You Sure You Want To Delete!!!")
   document.forms[0].detail.value='delete';
   alert(document.forms[0].detail.value);
   document.forms[0].submit();
}

function dontshow()
{

  
  if(document.forms[0].pay_mode.value=='S')
  {
    document.getElementById("pay_ac_typelab").style.display='block';
    document.getElementById("pay_ac_typelabselect").style.display='block';
    document.getElementById("combo_pay_acnotxt").style.display='block';
  }
  else if(document.forms[0].pay_mode.value=='T')
  {  
    document.getElementById("pay_ac_typelab").style.display='block';
    document.getElementById("pay_ac_typelabselect").style.display='block';
    document.getElementById("combo_pay_acnotxt").style.display='block';
  }
  else if(document.forms[0].pay_mode.value=='C')
  {
  document.getElementById("pay_ac_typelab").style.display='none';
    document.getElementById("pay_ac_typelabselect").style.display='none';
    document.getElementById("combo_pay_acnotxt").style.display='none';
  }
  else if(document.forms[0].pay_mode.value=='G')
  {
  document.getElementById("pay_ac_typelab").style.display='none';
    document.getElementById("pay_ac_typelabselect").style.display='none';
    document.getElementById("combo_pay_acnotxt").style.display='none';
  }




}



 function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57) ) 
		 {
   		 	return true;
          } 
          else 
          {
   			alert("Enter Numbers Only");
   			return false;
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
         		alert("only limit digits allowed")
         		txt.value="";
         		return false;
          	}
         };

     

function  funclear()
{

    var v = document.forms[0].elements;
    for(var i=0;i<=v.length;i++)
    {
       if(v[i].type=="text")
       {
          v[i].value = "";

        }
     }
 
}
function set(target)
{
     
    
     if(document.forms[0].ac_no.value>0){
     
     
     if(document.forms[0].flag.value=='NormalClosure')
     {
     
     alert(document.forms[0].flag.value);
     document.forms[0].flag.value='Normalclosure';
	 document.forms[0].forward.value=target;
     document.forms[0].submit();
     }
     else if(document.forms[0].flag.value=='WithPenalty')
     {
     
     alert(document.forms[0].flag.value);
     document.forms[0].flag.value='withpenalty';
	 document.forms[0].forward.value=target;
     document.forms[0].submit();
     }
      else if(document.forms[0].flag.value=='WithoutPenalty')
      {
     
     alert(document.forms[0].flag.value);
     document.forms[0].flag.value='withoutpenalty';
	 document.forms[0].forward.value=target;
     document.forms[0].submit();
      }
     }
     else
     alert("Cannot Submit-Please Enter Account Number ");	
}
function beforSubmit(){

document.forms[0].detail.value='done';
document.forms[0].submit();

}

function validalerts()
{

   if(document.getElementById("totaltesting").value!=0)
	{
		
		alert(document.getElementById("totaltesting").value);
		
	}
	else if(document.getElementById("totoalert").value!=0)
	{
		alert(document.getElementById("totoalert").value);
		
	}


}


function setInfo(){
 
 setIntUp();
 dontshow();
 validalerts();
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

alert("Normal closure=Normal Closure");

if(document.getElementById("loanid").value=="LoanTrue"){

alert("Loan Availed True!!");
}
return false;
}
if(document.forms[0].nojonthd.value=""){
if(document.forms[0].nojonthd.value="oknojoint"){
alert("No-Joint-Holders");
}

}

};


function setIntUp()
{

	if(document.forms[0].ac_type.value=='1003001')
	{
		document.getElementById("intup").style.display = 'block';
		document.getElementById("labelint").style.display = 'block';
		document.getElementById("int_payabl").style.display='block';
   		document.getElementById("int_payable_lable").style.display='block';
        document.getElementById("lab_intamt_paid").style.display='block'; 
        document.getElementById("txt_intamt_paid").style.display='block';
        document.getElementById("tot_amt").style.display='block';   
        document.getElementById("tot_amt_lable").style.display='block';
        document.getElementById("NetAmt").style.display='none';
        document.getElementById("Netamttext").style.display='none';
      	document.getElementById("InstAmt").style.display='none';
      	document.getElementById("Instamttext").style.display='none';
      	document.getElementById("AmtClt").style.display='none';
      	document.getElementById("Amtclttext").style.display='none';
	}
	else if(document.forms[0].ac_type.value=='1005001'||document.forms[0].ac_type.value=='1005002'||document.forms[0].ac_type.value=='1005003')
	{
	document.getElementById("intup").style.display = 'block';
		document.getElementById("labelint").style.display = 'block';
		document.getElementById("int_payabl").style.display='block';
   		document.getElementById("int_payable_lable").style.display='block';
        document.getElementById("lab_intamt_paid").style.display='none'; 
        document.getElementById("txt_intamt_paid").style.display='none';
        document.getElementById("tot_amt").style.display='block';   
        document.getElementById("tot_amt_lable").style.display='block';
        document.getElementById("NetAmt").style.display='none';
        document.getElementById("Netamttext").style.display='none';
      	document.getElementById("InstAmt").style.display='none';
      	document.getElementById("Instamttext").style.display='none';
      	document.getElementById("AmtClt").style.display='none';
      	document.getElementById("Amtclttext").style.display='none';
	}
	else
	{
		
		document.getElementById("intup").style.display = 'none';
		document.getElementById("labelint").style.display = 'none';
		document.getElementById("int_payabl").style.display='block';
		document.getElementById("int_payable_lable").style.display='block';
	    document.getElementById("tot_amt").style.display='none';   
        document.getElementById("tot_amt_lable").style.display='none';
	    document.getElementById("NetAmt").style.display='block';
        document.getElementById("Netamttext").style.display='block';
        document.getElementById("InstAmt").style.display='block';
      	document.getElementById("Instamttext").style.display='block';
      	document.getElementById("AmtClt").style.display='block';
      	document.getElementById("Amtclttext").style.display='block';
	}
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

function HideShow(AttributeToHide)
{

  	
	
	if(document.getElementById("pcombo").value=="loantable")
	{
				
		document.getElementById('loantable'+AttributeToHide).style.display='none';
	}
		
}
</script>


</head>


<body class="Mainbody" onload="setInfo()">
 
<%!
ModuleObject[] array_module,module_object;
DepositMasterObject dep_mast;
DepositMasterObject[] dep_reivestment;
Double dep_int_rate,dep_int_amt,dep_closure;
CustomerMasterObject cust_obj;
LoanReportObject loan_obj;
String[] details,pay_mode,combo_mat_cat,auto,int_freq,Combodate;
String pagenew;
int i,j,ver_value;



boolean flagvalue;
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
if(nstr!=null){
System.out.println("inside 0---->"+nstr);
}
int disable=0;
disable=(Integer)request.getAttribute("boolflag");


System.out.println("================------disable------> "+disable);

dep_reivestment = (DepositMasterObject[])request.getAttribute("DepositTranRI");
System.out.println("after depmaster object---- in jsp555555");
dep_mast = (DepositMasterObject)request.getAttribute("DepositTran");
System.out.println("after depmaster object---- in jsp6666666");
//dep_closure = (Double)request.getAttribute("normal");
System.out.println("after depmaster object---- in jsp7777777");

//Combodate=(String[])request.getAttribute("Combodate");

System.out.println("after depmaster object---- in jsp--hmmmmmmm");

dep_int_rate = (Double)request.getAttribute("closureform");

//dep_int_amt = (Double)request.getAttribute("intpayable");

Double interest_payable_penalty = (Double)request.getAttribute("int_loan");

ver_value =(Integer)request.getAttribute("Verifyvalue");
if(ver_value>0){
System.out.println("***************Inside JSP ******* and value of  ver_value"+ver_value);
}

if(disable!=0)
	{ 
		if(disable==2)
		{
			flagvalue=true; 
		}else
		{  
			flagvalue=false; 
		}
	}


System.out.println("***************IAFTTTTTERRRRRRR=======");
%>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/TDClosure?pageId=13003" focus="<%=""+request.getAttribute("getfocus")%>">
 
<table class="txtTable">
<tr>
<td>
 	<table class="txtTable">
 
 <html:hidden property="flag" styleId="flagvalid" ></html:hidden>
 <html:hidden property="nojonthd" styleId="flagvalid" ></html:hidden>
 <html:hidden property="testing" styleId="totaltesting"></html:hidden>
 <html:hidden property="ac_noo" styleId="ACNO"></html:hidden>
 <html:hidden property="alertdispay" styleId="totoalert"></html:hidden>
 	 <td width="40%">
 	         <table align="left">

    	    <tr>
        	    <td align="right" >
        	  	  <font style="font-style: normal;font-size:12px;">
			    
			     <bean:message key="label.td_actype"></bean:message></font>
			    </td>
			    
			    <td><html:select property = "ac_type" style="background-color:silver" onchange="setIntUp()">
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
			    
			   <html:hidden property="ac_noo" styleId="ACNO"></html:hidden> 
			   <html:text property="ac_no"  size="6" onblur="submit()" onkeypress="return numbersonly(this)" onkeyup="numberlimit(this,'11')"> </html:text> </td>
			     
			    
   		  	</tr>
        	 </table> 
        </td>
    
</table>
<table cellspacing="10" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">

<tr>
				<td><font style="font-style: normal;font-size:12px;">
			    	<bean:message key="label.dep_date"></bean:message></font>
			    </td>
			    
			    <%if(dep_mast!=null){
			         System.out.println("Checking hererrrrrrrr++++++++++++++++"); 
			    	//dep_mast = dep_reivestment[0];
			    	
			    	 //System.out.println("depreinvestment ------>"+dep_reivestment[0].getDepDate());
			    	
			    	%>
			  	<td>
			    	<html:text property="dep_date" size="12" value="<%=""+ dep_mast.getDepDate() %>"  readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
			    	</td>
			   <%}else if(dep_reivestment!=null){%>
			   <td>
			    	<html:text property="dep_date" size="12" value="<%=""+ dep_reivestment[0].getDepDate() %>"  readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
			    	</td>
			   
			    <%}else{ %>
			    		<td><html:text property="dep_date" size="12" value="0"   styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>
			    </td><%} %>
			   
			 
			   			     		     
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.closure_date"></bean:message></font>
			       </td>
			       <%if(dep_mast!=null){
			    	   
			    	    System.out.println("close date=="+ request.getAttribute("date"));
			       %>
			           <td> <html:text property="closure_date" size="12"  value="<%=""+request.getAttribute("date")%>"readonly="true" ></html:text>
			           </td>
			           <%}else if(dep_reivestment!=null){%>
			           <td> <html:text property="closure_date" size="12"  value="<%=""+request.getAttribute("date")%>"readonly="true" ></html:text>
			           </td>
			           <%}else{ %>
			            
			           <td> <html:text property="closure_date" size="12"  value="0" readonly="true"  ></html:text></td>
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
                	<html:text property="mat_date"   value="<%=""+ dep_mast.getMaturityDate() %>" size="12" readonly="true" ></html:text>
                	</td>
                <%}else if(dep_reivestment!=null){%>	
                	<td>
                	<html:text property="mat_date"   value="<%=""+ dep_reivestment[0].getMaturityDate() %>" size="12" readonly="true" ></html:text>
                	</td>
               <%}else{ %> 	
                    	<td><html:text property="mat_date"  value="0"  size="12" readonly="true"></html:text>
			    </td><%} %>
			    
			   			    
			    <td><div id="labelint" style="display:none;">
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.int_upto_date"></bean:message></font>
			           </div>
			       </td>
			       <%System.out.println("i am here ------>7"); %>
			       <%if(dep_mast!=null){
			    	   System.out.println(">>int upto date=="+dep_mast.getInterestUpto());
			        %>
			           <td><div id="intup" style="display:none;"> <html:text property="int_upto_date"  value="<%=""+ dep_mast.getInterestUpto()%>"   size="12+" readonly="true"></html:text></div>
			      </td>
			      <%}else if(dep_reivestment!=null){%>	
			      <td><div id="intup" style="display:none;"><html:text property="int_upto_date"  value="<%=""+ dep_reivestment[0].getInterestUpto()%>"   size="12+" readonly="true"></html:text></div>
			      </td>
			      <%}else{ %>
			      <td> <div id="intup" style="display:none;"><html:text property="int_upto_date"  value="0"   size="12" readonly="true"></html:text></div>
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
			         <html:text property="rct_no"   value="<%=""+ dep_mast.getReceiptno() %>" size="8" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
			     </td>
			     <%}else if(dep_reivestment!=null){%>	
			     <td>
			         <html:text property="rct_no"   value="<%=""+ dep_reivestment[0].getReceiptno() %>" size="8" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
			     </td>
			    <% }else{ %>
			    <td>
			    <html:text property="rct_no"   value="0"   size="8" readonly="true"></html:text>
			     </td><% } %>
			
			<td>
			        <div id="int_payable_lable" style="display:none"><font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.int_amt_pay"></bean:message></font></div>
			       </td>
			       
			           <td><div id="int_payabl" style="display:none"><html:text property="int_amt_pay"  readonly="true" size="10"></html:text></div>
			      </td>
			 </tr>
			
			
			<tr>
			
				<td><font style="font-style: normal;font-size:12px;">
			   		 <bean:message key="label.dep_amt"></bean:message></font>
			   	</td>
			   	 <%System.out.println("i am here ------>9"); %>
			   	<%if(dep_mast!=null){
			   		%>
			    <td>
			       <html:text property="dep_amt"  value="<%=""+ dep_mast.getDepositAmt() %>"   size="8" readonly="true"></html:text>
			    </td>
			    <%}else if(dep_reivestment!=null){
			    		    
			    %>
			    <td>
			      <html:text property="dep_amt"  value="<%=""+ dep_reivestment[0].getDepositAmt()%>"   size="8" readonly="true"></html:text>
			    </td>
			    
			    <% }else{ %>
			    <td>
			<html:text property="dep_amt"   size="8" readonly="true"></html:text>
			    </td>
			    <% } %>
			
			  <td>
			        <div id="tot_amt_lable" style="display:none"><font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.total_amt"></bean:message></font></div>
			       </td>
			       
			       
			    <td><div id="tot_amt" style="display:none"><html:text property="total_amt"   size="10"  readonly="true"></html:text></div>
			    			      </td>
			     
			   	     
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
			         <html:text property="mat_amt"   value="<%=""+ dep_mast.getMaturityAmt()%>" readonly="true" size="8" ></html:text>
			      </td>
			      <%}else if(dep_reivestment!=null){%>
			      <td>
			         <html:text property="mat_amt"   value="<%=""+ dep_reivestment[0].getMaturityAmt()%>" readonly="true" size="8" ></html:text>
			      </td>
			      <%}else{ %>
			      <td> <html:text property="mat_amt"  value="0"  readonly="true" size="8"></html:text>
			      </td><%} %>
			      
			      
			        <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.loan_availed"></bean:message></font>
			       </td>
			       
			       <%if(dep_mast!=null){
			      
			    	%>
			    <td> <html:text property="loan_avail"  size="6" value="<%=dep_mast.getLoanAvailed()%>" readonly="true"></html:text>
			    </td>
			     <%}else if(dep_reivestment!=null){ %>
			     <td> <html:text property="loan_avail"  size="6" value="<%=dep_reivestment[0].getLoanAvailed()%>" readonly="true"></html:text>
			    </td>
			      <%}else{
			    	  %>
			    <td> <html:text property="loan_avail"   size="6" readonly="true"></html:text>
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
			       <%}else if(dep_reivestment!=null){%>
			       <td><html:text property="agreed_int_rate"  value="<%=""+ dep_reivestment[0].getInterestRate()%>" readonly="true"   size="6"></html:text>
			    	</td>
			       <%}else{ %>
			       <td><html:text property="agreed_int_rate"  value="0" readonly="true"   size="6"></html:text>
			       </td><%} %>
			       
			   <td><div id="NetAmt" style="display:none"><font style="font-style: normal;font-size:12px;">
			   		 <bean:message key="lable.Net.amt"></bean:message></font></div>
			   	</td>    
			   <%if(dep_mast!=null){%>    
			   <td><div id="Netamttext" style="display:none"><html:text property="net_amt" readonly="true" size="8"></html:text></div></td>    
			   <%}else if(dep_reivestment!=null){%>    
			    <td><div id="Netamttext" style="display:none"><html:text property="net_amt" readonly="true" size="8" ></html:text></div></td>  
			  <%}else{%>
			  <td><div id="Netamttext" style="display:none"><html:text property="net_amt" readonly="true" size="8"></html:text></div></td> 
			  <%} %>  
			</tr>
	
	  <tr>
		          <td>
			        <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.applied_int_rate"></bean:message></font>
			       </td>
			       <%if(dep_int_rate!=null){
			    	   
			    	   System.out.println("in jsp----dep_int_rate[1]-"+dep_int_rate);
			        %>
			           <td> <html:text property="applied_int_rate"  value="<%=""+ dep_int_rate%>" readonly="true" size="6"></html:text>
			           </td>
			           <%}else if(dep_reivestment!=null){%>
			              <td> <html:text property="applied_int_rate"  size="6" readonly="true"></html:text> 
			      </td>
			      <%}else if(dep_mast!=null){%>
			              <td> <html:text property="applied_int_rate"  size="6" readonly="true"></html:text> 
			      </td>
			          <%}else{%>
			          
			          <td> <html:text property="applied_int_rate"  size="6" readonly="true"></html:text> 
			      </td><%} %>
			 <td>
			           <div id="InstAmt" style="display:none"><font style="font-style: normal;font-size:12px;">
			           <bean:message key="lable.instal_amt"></bean:message></font></div>
			 </td>
			 <%if(dep_mast!=null){ %>
			 <td><div id="Instamttext" style="display:none"><html:text property="inst_amt" readonly="true" size="8" ></html:text></div></td>
			 <%}else if(dep_reivestment!=null){ %>
			 <td><div id="Instamttext" style="display:none"><html:text property="inst_amt" readonly="true" size="8"></html:text></div></td>
			 <%}else{ %>
			 <td><div id="Instamttext" style="display:none"><html:text property="inst_amt" readonly="true" size="8"></html:text></div></td>
			 <%} %>
			 </tr>
			    
			  
			
			  <tr>
		          <td><div id="lab_intamt_paid" style="display:none;">
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.int_amt_paid"></bean:message></font></div>
			       </td>
			       <%System.out.println("i am here ------>11"); %>
			       
			           <td><div id="txt_intamt_paid" style="display:none;">
			           <html:text property="int_amt_paid"  size="6" readonly="true"></html:text>
			      </div></td>
			    <td>
			           <div id="AmtClt" style="display:none"><font style="font-style: normal;font-size:12px;">
			           <bean:message key="lable.amt_collected"></bean:message></font></div>
			    </td>  
			     <%if(dep_mast!=null){%>
			     <td><div id="Amtclttext" style="display:none"><html:text property="amt_collected" readonly="true" size="8"></html:text></div></td>
			     <%}else if(dep_reivestment!=null){%>
			     <td><div id="Amtclttext" style="display:none"><html:text property="amt_collected" readonly="true" size="8"></html:text></div></td>
			     <%}else{ %>
			     <td><div id="Amtclttext" style="display:none"><html:text property="amt_collected" readonly="true" size="8"></html:text></div></td>
			     <%} %> 
			      
			 </tr>
			 
			  <tr>
		          <td>
			           <font style="font-style: normal;font-size:12px;">
			           <bean:message key="label.no_of_jointholders"></bean:message></font>
			       </td>
			       <%System.out.println("i am here ------>12"); %>
			       <%if(dep_mast!=null){ 
			       %>
			           <td> <html:text property="no_of_joint"   value="<%=""+ dep_mast.getNoofJtHldrs()%>" readonly="true" size="6" ></html:text>
			      </td>
			      <%}else if(dep_reivestment!=null){ %>
			      <td> <html:text property="no_of_joint"   value="<%=""+ dep_reivestment[0].getNoofJtHldrs()%>" readonly="true" size="6" ></html:text>
			      </td>
			      <%}else{ %>
			      <td> <html:text property="no_of_joint"   value="0" readonly="true" size="6"></html:text>
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
			      <%}else if(dep_reivestment!=null){%>		
			      	<td> <html:text property="nominee_reg_no" value="<%=""+ dep_reivestment[0].getNomineeRegNo() %>" readonly="true"   size="6"></html:text>
			      		</td>	
			      <%}else{
			      %>
			      <td> <html:text property="nominee_reg_no" value="0" readonly="true"   size="6"></html:text>
			      		</td>
			      <%
			      }
			      %>	
			 </tr>
			
			 <tr>
				<td><font style="font-style: normal;font-size:12px;">
			    	<bean:message key="label.combo_pay_mode"></bean:message></font>
			    </td>
			     			 
			    	<td><font style="font-style: normal;font-size:12px;">
			        <html:select property="pay_mode" styleClass="formTextFieldWithoutTransparent" onchange="dontshow()" >
			    	<html:option value="S">Select</html:option>
			    	<html:option value="T">Transfer</html:option>
			    	<html:option value="C">Cash</html:option>
			    	<html:option value="P">Q/DD/PO</html:option>
			    	</html:select></font>
			      
			        		        	 
			     </td>
			</tr>
			    
			    
			<tr>
				<td><div id="pay_ac_typelab" style="display:none"><font style="font-style: normal;font-size:12px;">
			    	<bean:message key="label.combo_pay_actype"></bean:message></font>
			    </div></td>
			   		   
			    <td><div id="pay_ac_typelabselect" style="display:none">
			    	<html:select property="pay_ac_type"  styleClass="formTextFieldWithoutTransparent"   disabled="<%=flagvalue%>" >
			        <html:option value="Select">Select</html:option>
			        <html:option value="1002001">SB</html:option>
			        <html:option value="1007001">CA</html:option>
			        <html:option value="1018001">CA Br</html:option>
			        <html:option value="1003001">FD</html:option>
			        <html:option value="1004001">RD</html:option>
			        <html:option value="1005001">RI</html:option>	 
			         </html:select></div>
			         </td>
			         	         
			          
			      		  <td>		      		  
			      		  <div id="combo_pay_acnotxt" style="display:none"><html:text property="combo_pay_acno" disabled="<%=flagvalue%>" onkeypress="return numbersonly(this)" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent" size="6"></html:text>
			      		  </div></td>
		 	         			         
			</tr> 
			   <tr>
			       <td>
			            <bean:message key="label.details"></bean:message>
			       </td>
			       <td>
			            <html:select property="details" styleClass="formTextFieldWithoutTransparent"   onchange="beforSubmit()" styleId="pcombo">
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
			<%if(ver_value==1){ %>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
			   <td><html:button onclick = "set('submit')" property="Submit" styleClass="ButtonBackgroundImage">
			   
				<bean:message key="label.submit"></bean:message></html:button>
				<%}else{ %>
				td><html:button onclick = "set('submit')" property="Submit" styleClass="ButtonBackgroundImage" disabled="true">
			   
				<bean:message key="label.submit"></bean:message></html:button>
				 <%} %>
				 
				 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
				<html:button property="butt_update"  styleClass="ButtonBackgroundImage">
				<bean:message key="label.update"></bean:message></html:button>
				<%}else{ %>
				<html:button property="butt_update" disabled="true" styleClass="ButtonBackgroundImage" >
				<bean:message key="label.update"></bean:message></html:button>
			
				<%} %>
				<html:button property="butt_clear" onclick="funclear()"  styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:button>
				</td>
				
				<%}else if(ver_value==2){ %>
				<td><html:button property="butt_del" disabled="true" onclick="deletedata()" styleClass="ButtonBackgroundImage"><bean:message key="label.delete"></bean:message></html:button></td>
				
				<td><html:button property="butt_verify" onclick = "set('verify')" styleClass="ButtonBackgroundImage"><bean:message key="label.verify"></bean:message></html:button></td>
				<td><html:button property="butt_clear" onclick="funclear()"  styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:button></td>
				
				<%} %>
				<td> 
			
			   <html:hidden property="forward" value="error" ></html:hidden>
			    
			    <html:hidden property="loantrue" styleId="loanid"></html:hidden>
			    
			    <html:hidden property="closure" value="0"></html:hidden></td>
			    <html:hidden property="detail" ></html:hidden>
			    
		</tr> 

</table>

</td>
<td>
<%System.out.println("Inside TDClosure.jsp Page99999999999999" + nstr);%>
   
    
        
        

<table align="right"  valign="top" width="10%">
<%if( nstr!=null ){%>
    <tr> 
    <td>
	<% System.out.println("  inside TDCLOSURE.jsp===============================================nstr  >"+nstr);
    pagenew=nstr;%>
	   <jsp:include page="<%=pagenew %>"></jsp:include>
	   
			   <%System.out.println("After include------>"+pagenew);%>
    </td>
    </tr>
      
		 
				 
				 
	<%} %>
        
  
 </table>

	
</td>

</tr>










</table>


</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>
