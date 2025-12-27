<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="java.util.Map"%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="masterObject.general.NomineeObject;"%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Deposit Account Renewal</title>





<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
    
    <%if(ver_value==1){
    	
    	System.out.println("Inside Ver value===1");
    	
    	%>
    <h2 class="h2">

      <center>Deposit Account Renewal Form</center></h2>
      
   <%}else if(ver_value==2){
   
   	System.out.println("Inside Ver value===2");

   %>      
      <h2 class="h2">

      <center>Deposit Account Renewal Verification</center></h2>
    <%} %>
    
    
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
<script type="text/javascript">
    var dtCh= "/";
var minYear=1900;
var maxYear=9999;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}
function hello(){
alert("Submit");
}

function intfreq()
{
  alert(document.forms[0].interest_freq.value);
  document.forms[0].intfreqee.value=document.forms[0].interest_freq.value;
  alert(document.forms[0].intfreqee.value);
  document.forms[0].submit();
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
   

     


function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}




function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strMonth=dtStr.substring(pos1+1,pos2)
     
	var strDay=dtStr.substring(0,pos1)
     
	var strYear=dtStr.substring(pos2+1)
     

	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	
	if (pos1==-1 || pos2==-1){
		alert("The date format should be : dd/mm/yyyy")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Please enter a valid day as this year is not a leap year")
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Please enter a valid date")
		return false
	}
return true
}
//Changed by sumanth on 6 Jan 2009
function beforSubmit(){
alert("hi");
document.forms[0].detail.value='done';
document.forms[0].submit();

};
function ValidateForm(){
   
	var dt=document[0].process_date;
	var dt1=document[0].to_dt;
	if (isDate(dt.value)==false){
	       	
		return false
	}
	if (isDate(dt1.value)==false){
	       	
		return false
	}
	
    return true
 };
 
 
function  funclear(){

var v = document.forms[0].elements;
for(var i=0;i<=v.length;i++){

if(v[i].type =="text"){

v[i].value = "";

}

}
 
}

function checkblank(data,str){

  var len= data.length;
  if(len==0){
  
    alert("Please Enter the"+str);
  
  }
  else
  {
  	document.forms[0].forward.value="period";
  	document.forms[0].submit();
  
  }

};

function trnsubmit()
{
alert(document.forms[0].dep_amt.value);

document.forms[0].amou.value=document.forms[0].dep_amt.value;
document.forms[0].submit();
}
function set(target)
{
	
	if(document.forms[0].ac_no.value=="" )
	{
	
	alert("Cannot Submit-Please Enter mandatory feilds");
	
	}
	
  else
  {
	alert(target);
	var value = confirm("Data Ok??");
	
	if(value!=0)
	{
		document.forms[0].mysub.value=target;
		alert(document.forms[0].mysub.value);
		document.forms[0].submit();
	}	
	else
	{
	 	return false;
	}
  }	
};

function setChange(target)
{
document.forms[0].forward.value=target;
document.forms[0].submit();
}

function Validatefields(){ 

var val = document.forms[0].validation.value;

if(val!=0 ||val!=""){
alert(val);


}else
{
	return false;
}
};


function HideShow(AttributeToHide)
{

  	
	
	if(document.getElementById("pcombo").value=="Transfer")
	{
				
		document.getElementById('Transfer'+AttributeToHide).style.display='block';
		document.getElementById('scrol'+AttributeToHide).style.display='none';
		document.getElementById('controlno'+AttributeToHide).style.display='none';
	}	
	
	if(document.getElementById("pcombo").value=="Cash")
	{	
		
		document.getElementById('scrol'+AttributeToHide).style.display='block';
		
		document.getElementById('Transfer'+AttributeToHide).style.display='none';
		document.getElementById('controlno'+AttributeToHide).style.display='none';
	}
	
	if(document.getElementById("pcombo").value=="Q/DD/PO")
	{
	
		
		document.getElementById('controlno'+AttributeToHide).style.display='block';
		document.getElementById('Transfer'+AttributeToHide).style.display='none';
		document.getElementById('scrol'+AttributeToHide).style.display='none';
	}
	
	
	if(document.getElementById("validat").value!=0)
	{
		alert("Account Created Successfully!!!");
		alert("The New Account number Is:"+document.getElementById("validat").value);	
		funclear();
		return false;		
	}
	
	
	
};

</script>




</head>
<body class="Mainbody" onload="HideShow(11)">
<%!
ModuleObject[] array_module,module_object;
DepositMasterObject verify_values,depmast_verify;
CustomerMasterObject cust_obj;
AccountObject acc_obj;
NomineeObject[] nomineeObjects;
String[] details,pay_mode,combo_mat_cat,auto,int_freq;
String jsppath,matdate,pagenew;
int i,j;
int value,ver_value;
int cid=0;
int account_number;
String intra_acc_type,account_type,intra_acc_no;

Double int_amt,mat_amt,balance,dep_amt,new_DEPAMTO;
%>
<%
String Prevamt=(String)request.getAttribute("TolAmt");
String ccid=(String)request.getAttribute("ccid_Newpage");
account_number=(Integer)request.getAttribute("account_number");
account_type=(String)request.getAttribute("account_type");
intra_acc_no=(String)request.getAttribute("intra_acc_no");
intra_acc_type=(String)request.getAttribute("intra_acc_type");

if(ccid!=null){
	 cid=Integer.parseInt(ccid);

}
String prev_Date=(String)request.getAttribute("DepDate");
String pvr_date=(String)request.getAttribute("PerdDays");
new_DEPAMTO=(Double)request.getAttribute("NewDepAMt");
String new_aclab=(String)request.getAttribute("new_acno");
new_aclab="NewAccount";
String nstr=(String)request.getAttribute("flag");
int_amt = (Double)request.getAttribute("interestamount");
mat_amt = (Double)request.getAttribute("maturityamt");
matdate = (String)request.getAttribute("matdate");
System.out.println("-----------------Testing 6 the problem is here actually--------------- ");

String Depdate=(String)request.getAttribute("oldMAT=newDEP");
if(Depdate!=null){
	System.out.println("in jsp  DepositDate====="+Depdate);
}
if(mat_amt!=null){
System.out.println("in jsp  maturity mat====="+mat_amt);
}
if(matdate!=null){
System.out.println("in jsp  maturity date====="+matdate);
}
System.out.println("checkingggggggggggg----1-----");
acc_obj = (AccountObject)request.getAttribute("balance");
System.out.println("checkingggggggggggg the interest Rate in Jsp-----2---- "+int_amt);
 
dep_amt =mat_amt;

System.out.println("checkingggggggggggg-----3----");

ver_value =(Integer)request.getAttribute("Verifyvalue");
if(ver_value!=0){
System.out.println("***************Inside JSP ******* and value of  ver_value"+ver_value);
}


depmast_verify = (DepositMasterObject)request.getAttribute("verify");
verify_values = (DepositMasterObject)request.getAttribute("verify_values");


//System.out.println("depmast verify???"+depmast_verify);


//System.out.println("depmast verify values"+verify_values);



%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/DepositAccountRenewal?pageId=13027" focus="<%=""+request.getAttribute("getfocus")%>">

<html:hidden property="forward" value="error" ></html:hidden>
<html:hidden property="detail" ></html:hidden>
<html:hidden property="accountgen" />
<html:hidden property="amou"/>
<html:hidden property="intfreqee"></html:hidden>
<html:hidden property="validate" styleId="validat"></html:hidden>
<html:hidden property="mysub" />
<html:hidden property="account_number" value="<%=""+account_number%>"/>
<html:hidden property="account_type" value="<%=""+account_type%>"/>
<!-- main table-->

<table  class = "txtTable"  cellspacing="3" style="border-bottom-color:green;border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:black;border-left-style:hidden;border-right-style:hidden;border-right-color:green;border-top-color:green;border-top-style:hidden;">
<tr>
 <td>
	<table class = "txtTable" width="20%" cellspacing="0"  	style="border: thin solid maroon;">
	   <tr>
	   		<td><bean:message key="label.td_actype"></bean:message></td>
			    <td><html:select property = "ac_type" styleClass="formTextFieldWithoutTransparent">
			    	<% array_module=(ModuleObject[])request.getAttribute("Dep type");
			    			for(int i=0;i<array_module.length;i++)
			    			{ 	System.out.println("acctype--------------1--->"+ array_module);
			    	%>	    	
			    				<html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%}%>	
			    	 </html:select>
    		   	 </td>
     </tr>
   		  	<tr>
				  <td><bean:message key="label.td_acno"></bean:message></td>
			      <%if(verify_values != null){
				  System.out.println("Verify Not null-------in verification-----------"); %>
			     <td><html:text property="ac_no"  value="<%=""+verify_values.getAccNo() %>" tabindex="0" size="10" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'10')" styleClass="formTextFieldWithoutTransparent"></html:text>
			     <%System.out.println("JSP VALUE ac_no verify value =="+verify_values.getAccNo()); %>
			       </td >
			     <%}else{System.out.println("-------in opening-----------"); %>
				  <td><html:text property="ac_no"   tabindex="0"  size="10" onkeyup="numberlimit(this,'10')" onkeypress="return only_numbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			     <% }  
			     if(new_aclab!= null){ %>  
			        <td><font color="red"> <bean:message key="CLAM2"></bean:message></font></td>
			        <%}%> 
			</tr>
			<tr>
				  <td><bean:message key="label.cid"></bean:message></td>
				<%if(cid!=0){
					System.out.println("Verify Not null--cid--------in verification--------");%>
			     <td><html:text property="cid" value="<%=String.valueOf(cid)%>" size="10" tabindex="1" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'7')"  readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			   <%}else{
				   System.out.println("------Cid- in opening----------"); %>
			     <td><html:text property="cid"   size="10" tabindex="1" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'7')" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
			       <%} %> 
			</tr>
			<tr>
				<td><bean:message key="label.dep_amt"></bean:message></td>
			   	<%if(Prevamt!=null){
			   		System.out.println("Verify Not null--  dep amount---in verification-------------"+Prevamt);
			   		%>
			   	<html:hidden property="new_dpamt" value="<%=""+Prevamt %>"/>
			    <td><html:text property="dep_amt" size="10"   value="<%=""+Prevamt%>" onchange="setChange('Amt')" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'10')" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
			    </td>
			   <%}else if(new_DEPAMTO!=null){ 
			    System.out.println("IN ELSE BLOCK  dep amount----------------"+new_DEPAMTO);  %>
			    <td><html:text property="dep_amt" size="10" value="<%=""+new_DEPAMTO%>" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'10')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			    <%}else{ 
			    System.out.println("IN ELSE BLOCK  dep amount----------------"+new_DEPAMTO);  %>
			    <td><html:text property="dep_amt" size="10"   onblur="checkblank(dep_amt.value,'Deposit Amount')" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'10')" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
			    <%} %>
			</tr>
			<tr>
				  <td><bean:message key="label.dep_date"></bean:message></td>
			<%if(prev_Date!= null){
				System.out.println("Verify Not null--  dep date---in verification-------------");%>
				<html:hidden property="pvr_date" value="<%=""+prev_Date %>"/>
			     <td><html:text property="dep_date"  value="<%= ""+prev_Date %>" size="10" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
			   <%}else{
				   System.out.println("Verify  null-------dep date-----in opening------> "+Depdate);  %>
				   <td> <html:text property="dep_date" value="<%= ""+Depdate %>" size="10" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
			       <%} %> 
			</tr>
			<tr>
			<td> 	<bean:message key="label.Period_in_days"></bean:message></td>
					<%if(pvr_date != null){
							System.out.println("Verify Not null--no of days-------------in verification---");	%>
			     <td><html:text property="period_of_days"  value="<%=""+pvr_date%>"   onblur="checkblank(period_of_days.value,'Deposit Days >30')" size="10" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text>	  </td>
			   <%}else{
				   System.out.println("Verify  -------no of days-------opening----");   %>
			     <td><html:text property="period_of_days"    onblur="checkblank(period_of_days.value,'Deposit Days >30')" size="10"  onchange="submit()" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text>          </td>
			       <%} %> 
			</tr>
			<tr>
				<td>  	<bean:message key="label.mat_date"></bean:message>    </td>
			    <%if(verify_values != null){
			    	System.out.println("Verify Not mat date-----in verification----------");  	%>
			    <td>    <html:text property="mat_date"  value="<%=""+verify_values.getMaturityDate() %>" readonly="true" size="10" ></html:text>    </td>
			    
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
			  <%}else{%>
				   <%System.out.println("-------mat date-----opening------");%>
				 <td>
			    <html:text property="mat_date"  value="<%=matdate %>" size="10" readonly="true"></html:text>
			    </td>
			    <td><html:select property="combo_mat_cat" styleClass="formTextFieldWithoutTransparent">
			    	<% combo_mat_cat=(String[])request.getAttribute("combo_mat_cat1");
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
			     <%} %>
			</tr>
			<tr>
				<td>
			    	<bean:message key="label.combo_autorenewal"></bean:message>
				</td>
			    <%if(verify_values != null){
			    	System.out.println("Verify Not null--autorenewal-------Verification---------");
				%>
			  <%}else{%>
				   <%System.out.println("Verify  nulls-------no of days-----opening------");%>
			    <td>   
			    	<html:select property="combo_autorenewal" size="1" styleClass="formTextFieldWithoutTransparent">
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
			    <%}%>
			</tr>
		<tr>
				<td>
			    	<bean:message key="label.introduceractypeno"></bean:message>
				</td>
				
			    <%if(verify_values != null){
			    	System.out.println("Verify Not null--------introducer----verification------");
			    	%>
			    	<td>
			    	<html:select property="intro_ac_type"   value="<%=""+verify_values.getIntroAccType() %>" size="1"   styleClass="formTextFieldWithoutTransparent">
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
			<html:text property="intro_ac_no" size="10" value="<%=""+verify_values.getIntroAccno() %>" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text>
			    </td>
			  <%}else{%>
			  <%if(intra_acc_no!=null && intra_acc_type!=null){ %>
			  
			  <td>
			    	<html:select property="intro_ac_type"   value="<%=""+intra_acc_type%>" size="1"   styleClass="formTextFieldWithoutTransparent">
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
			<html:text property="intro_ac_no" size="10" value="<%=""+intra_acc_no%>" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text>
			    </td>
			  
			  <%}else{ %>
				   <%System.out.println("Verify  nulls-------introducer-------opening----");%>
			  <td><html:select property="intro_ac_type"  size="1"  styleClass="formTextFieldWithoutTransparent">
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
			    <td><html:text property="intro_ac_no" size="10" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'6')"  styleClass="formTextFieldWithoutTransparent"></html:text>
			    </td>
			    <%}}%>
			</tr>
			<tr>
				<td>
			    	<bean:message key="label.combo_pay_mode"></bean:message>
			    </td>
			      <%if(verify_values != null){
			    	System.out.println("Verify Not null----------verification--------");
				%>
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
			       <%}else{%>
			    	<% System.out.println("Verify Not null --pay mode-----opening-----------");%>
				
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
			     <%} %>
			  	
					     
			</tr>
			  
			  
			  <tr>
				<td>
			    	<bean:message key="label.combo_pay_actype"></bean:message>
			    </td>
			    
			    <%if(verify_values!= null){ %>
			    
			    <%System.out.println("Inside Combo pay actype----"+verify_values.getCategory()); %>
			    
			    <td>
			    	<html:select property="pay_ac_type"  size="1" styleClass="formTextFieldWithoutTransparent">
			        <%module_object=(ModuleObject[])request.getAttribute("pay_actype");
			        	for(int i=0;i < module_object.length;i++)
			            {
						 	 System.out.println("module_object"+ module_object);
				    	
			    	 %>	   			    			    	 
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    	<%	}	%>
			        	 
			         </html:select>
			    </td> 
			    <%}else{ %>
			    
			    
			    <td>
			    	<html:select property="pay_ac_type"  size="1" styleClass="formTextFieldWithoutTransparent">
			        <%module_object=(ModuleObject[])request.getAttribute("pay_actype");
			        	for(int i=0;i < module_object.length;i++)
			            {
						 	 System.out.println("module_object"+ module_object);
				    	
			    	 %>	   			    			    	 
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    	<%	}	%>
			        	 
			         </html:select>
			    </td> 
			    <%} %>
			<%if(verify_values != null){ 
			System.out.println("Inside pay ac_no");
			%>
			    <td>
			       		<html:text property="pay_mode_ac_no"   value="<%=""+verify_values.getReceivedAccno() %>" size="10" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text>
		       </td>
		       <%}else{ %>
		        <td>
			       		<html:text property="pay_mode_ac_no"    size="10" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text>
		       </td>
		       <%} %>
			</tr> 
		 <tr>
			       <td> <bean:message key="label.combo_amt_received"></bean:message> </td>
			       <td> <html:select property="amt_recv" styleClass="formTextFieldWithoutTransparent" onchange="HideShow(11)" styleId="pcombo">			            <%pay_mode=(String[])request.getAttribute("amt_recv");
			             	 for(int i=0;i < pay_mode.length;i++)
			             	 { 	 System.out.println("amt_recv"+ pay_mode);
			    	      %>	   			    			    	 
			    		<html:option value="<%=""+pay_mode[i]%>"><%=""+pay_mode[i]%></html:option>
			    	<%}%>	        			         
			         	</html:select>
			       </td>
			    </tr> 
			    <td><bean:message key="label.trf_acno"></bean:message></td>
			    <td><html:text property="trf_acno" size="10"  onchange="trnsubmit()" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'6')" style="background-color:silver"></html:text> </td>
			<tr> <td><bean:message key="label_name1"></bean:message></td>
			  <td><html:text property="label_name"  size="20" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			    </tr>
			     <tr>
			       <td><bean:message key="label.details"></bean:message></td>
			       <td>
			            <html:select property="details" styleClass="formTextFieldWithoutTransparent" onblur="submit()">
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
<table  class ="txtTable" width="10%" cellspacing="3" style="border-bottom-color:teal;border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:teal;border-left-style:hidden;border-right-style:hidden;border-right-color:teal;border-top-color:teal;border-top-style:hidden;">
<tr> 
			<td>
				 <%if(ver_value==1){ %>
				 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
				<html:button  property="subm" onclick="set('Submit')" styleClass="ButtonBackgroundImage">
				<bean:message key="label.submit"></bean:message></html:button></td>
				<%}else{ %>
				<td><html:button  property="subm" onclick="set('Submit')" styleClass="ButtonBackgroundImage" disabled="true">
				<bean:message key="label.submit"></bean:message></html:button>
				</td>
				<%} %>
				
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
				<td><html:submit onclick="set('Submit')" styleClass="ButtonBackgroundImage" >
				<bean:message key="label.update"></bean:message></html:submit></td>
				<%}else{ %>
				<td><html:submit onclick="set('Submit')" styleClass="ButtonBackgroundImage" disabled="true">
				<bean:message key="label.update"></bean:message></html:submit></td>
				<%} %>
				
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
				<td><html:submit onclick="set('delete')" styleClass="ButtonBackgroundImage" disabled="true">
				<bean:message key="label.delete"></bean:message></html:submit></td>
				<%} %>
				<td><html:button property="clr" onclick="funclear()" value="Clear" styleClass="ButtonBackgroundImage">
				</html:button></td>
				 <%} %>
				 
				 <%if(ver_value==2){ %>
				 <td><html:submit onclick="set('Verify')" styleClass="ButtonBackgroundImage">
				<bean:message key="label.verify"></bean:message></html:submit></td>
				
				<td><html:button property="clr" onclick="return funclear()" value="Clear" styleClass="ButtonBackgroundImage">
				</html:button></td>
				 
				<%} %>
	</tr>	
</table>


	
<td>

<table  class = "txtTable" frame="above" cellspacing="3" style="border-bottom-color:olive;border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:aqua;border-left-style:hidden;border-right-style:hidden; ;border-right-color:lime;border-top-color:;border-top-style:hidden;">
			   
<tr>
			       <td>
		     	         
			             <bean:message key="label.Int_freq"></bean:message>
	               </td>
	               
	               <td>
	           			 <html:select property="interest_freq" styleClass="formTextFieldWithoutTransparent" onchange="intfreq()">
			         		 <%if(verify_values!=null){
			         		 String int_freq=verify_values.getInterestFrq();
			         		 System.out.println("The Interest Freq is----> "+int_freq);
			         		%>
			         		 <%if(int_freq.equalsIgnoreCase("M")){ %>
			         		 <html:option value="M">Monthly</html:option>
			         		 <%}else if(int_freq.equalsIgnoreCase("Q")){ %>
			         		 <html:option value="Q">Quarterly</html:option>
			         		 <%}else if(int_freq.equalsIgnoreCase("H")){ %>
			         		 <html:option value="H">Half-Yearly</html:option>
			         		 <%}else if(int_freq.equalsIgnoreCase("O")){ %>
			         		 <html:option value="O">On-Maturity</html:option>
			         		 <%}}else{ %>
			         		 <%int_freq=(String[])request.getAttribute("int_freq");
			             	 if(int_freq!=null){
			         		 for(int i=0;i < int_freq.length;i++)
			             	 {
						    	 System.out.println("int_freq"+ int_freq);
				    	
			    	      %>	   			    			    	 
			    		<html:option value="<%=""+int_freq[i]%>"><%=""+int_freq[i]%></html:option>
			    			<%}}}%>
			        			         
			         	</html:select>
		        
		         </td>
		         
		          
		        	<td>
		                 <bean:message key="label.IntRate"></bean:message></td>
		                 <td><html:text property="int_rate" readonly="true" size="10"  styleClass="formTextFieldWithoutTransparent" ></html:text>
		             </td>    
		         
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
			        
			        <td> <html:text property="mat_amt" readonly="true"  size="8" styleClass="formTextFieldWithoutTransparent"></html:text>
			        
			      </td>
			        
			 </tr>
</table>



<table  class = "txtTable" frame="above" cellspacing="3" style="border-bottom-color:teal;border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:teal;border-left-style:hidden;border-right-style:hidden;border-right-color:teal;border-top-color:teal;border-top-style:hidden;">
<tr id="scrol11">
			
			 	<td>
			 		 <bean:message key="label.scroll_no"></bean:message>
			  		
			 	</td>
			 	<td>
			   		 <html:text property="scroll_no" value="0" size="8" styleClass="formTextFieldWithoutTransparent"></html:text>
    		  	</td> 
    		  	
    		    
    		    <td>
    		        
    		        <bean:message key="label.date"></bean:message></td>
    		    <td>
    		    <html:text property="date" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" styleClass="formTextFieldWithoutTransparent"></html:text>
    		   		
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
    		    <html:text property="date" size="10" readonly="true" value="<%= ""+request.getAttribute("date") %>" styleClass="formTextFieldWithoutTransparent"></html:text>
    		   		
			    </td>

<tr>
               <td>
			    <bean:message key="label.name"></bean:message></td>
			    
			    
               
			     
			  <td>
			        <html:text property="label_name"  size="15"  styleClass="formTextFieldWithoutTransparent"></html:text>
			   
			     </td>
			   
			    
			     <td>
			       		
			       		<bean:message key="label.Amt"></bean:message>
			     </td>
			     <td>
			     		 <html:text property="amount"  size="8" styleClass="formTextFieldWithoutTransparent"></html:text>
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
    		       
    		        <bean:message key="lable.bal"></bean:message>
    		    </td>
    		    
    		     
			        
			        <td> <html:text property="balance_amt" readonly="true"  size="8" styleClass="formTextFieldWithoutTransparent"></html:text>
			        
			      </td>
    		    
    		    
    		    
    		   </tr>
</table>
<!--
added by sumanth to include personal.jsp
-->


  <%System.out.println("Inside Ammendments.jsp Page99999999999999" + nstr);%>
   
    
        
        

<table align="right"  valign="top" width="10%">
<%if( nstr!=null ){%>
    <tr> 
    <td>
	<% System.out.println("At 1086 ApplDataEntry.jsp===============================================nstr  >"+nstr);
    pagenew=nstr;%>
	   <jsp:include page="<%=pagenew %>"></jsp:include>
	   
			   <%System.out.println("After include------>"+pagenew);%>
    </td>
    </tr>
      
		 
				 
				 
	<%} else{%><tr> 
    <td>
    <%String smg=(String)request.getAttribute("msg");
   if(smg!=null){
    %>
    
	    <font color="red"><%=""+smg%></font>
	    <%} %>
	  </td>
    </tr>
      
	<%} %>
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