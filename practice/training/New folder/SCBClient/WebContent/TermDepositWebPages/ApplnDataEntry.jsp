<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="masterObject.general.NomineeObject"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="core" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="java.util.Map"%>

<%@page import="masterObject.general.AccountObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
    
<script type="text/javascript"><!--
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

function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only limit digits are allowed")
         		txt.value="";
         		return false;
          	}
         };
         function numberlimit1(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("plz enter limit  digits!!")
         		txt.value="";
         		return false;
          	}
         };
          function numberlimit2(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than 6 digits!!")
         		txt.value="";
         		return false;
          	}
         };
  function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57 || cha==46) ) 
		 {
   		 	return true;
          } 
          else 
          {
   			alert("Enter Numbers Only");
   			return false;
          }
	};



function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) ) {
				return true ;
                
            	}
            else{
              	alert("Please enter the numbers only ");
              	return false ;
            }
        };  
function stripCharsInBag(s, bag)
{
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

function funchidefields()
{

var payname=document.forms[0].pay_mode.value;

if(payname=='Cash' || payname=='C')
{
document.getElementById("pay_acType").style.display='none';
document.getElementById("pay_acNo").style.display='none';
document.getElementById("pay_actypelab").style.display='none';

}
else if((payname=='Transfer')||payname=='T')
{
document.getElementById("pay_acType").style.display='block';
document.getElementById("pay_acNo").style.display='block';
document.getElementById("pay_actypelab").style.display='block';
}
else if(payname=='Q/DD/PO' || payname=='Q' || payname=='D'|| payname=='P')

{
document.getElementById("pay_acType").style.display='none';
document.getElementById("pay_acNo").style.display='none';
document.getElementById("pay_actypelab").style.display='none';
}
else if(payname=='Select')
{
document.getElementById("pay_acType").style.display='block';
document.getElementById("pay_acNo").style.display='block';
document.getElementById("pay_actypelab").style.display='block';
}

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
function beforSubmit()
{
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
 
function dontdisplay()
{
   
   
   
      if(document.forms[0].ac_no.value==0){
      document.getElementById("controllab").style.display='block';
      document.getElementById("controlNO").style.display='block';
      document.getElementById("datelab").style.display='block';
      document.getElementById("date1").style.display='block';
      document.getElementById("namelab").style.display='block';
      document.getElementById("name").style.display='block';
      document.getElementById("datelable").style.display='block';
      document.getElementById("date2").style.display='block';
      document.getElementById("scrollab").style.display='block';
      document.getElementById("scrollNO").style.display='block';
      }
   
      
}
 
 
 
 
function ckValidate()
{
	
	
	var val4=document.forms[0].ac_no.value;
	
	if(val4=="")
	{
	 alert("Enter Account Number");
	}
	else
	{
	if(document.forms[0].ac_no.value!=0)
    {
     
      document.getElementById("controllab").style.display='block';
      document.getElementById("controlNO").style.display='block';
      document.getElementById("datelab").style.display='block';
      document.getElementById("date1").style.display='block';
      document.getElementById("namelab").style.display='block';
      document.getElementById("name").style.display='block';
      document.getElementById("datelable").style.display='block';
      document.getElementById("date2").style.display='block';
      document.getElementById("scrollab").style.display='block';
      document.getElementById("scrollNO").style.display='block';
   }
	document.forms[0].submit();
	}
}	
	
function paymodsubmit()
{
var val3=document.forms[0].pay_mode_ac_no.value;

	if(val3==0)
	{
	alert("Enter Account Number");
	}
	else if(val3=="")
	{
	 alert("Enter Account Number");
	}
	else
	{
	document.forms[0].submit();
	}
}
function paymodsubmit()
{
var val3=document.forms[0].pay_mode_ac_no.value;

	if(val3==0)
	{
	alert("Enter Account Number");
	}
	else if(val3=="")
	{
	 alert("Enter Account Number");
	}
	else
	{
	document.forms[0].submit();
	}
}

function trnsubmit()
{
var val3=document.forms[0].trf_acno.value;

	if(val3==0)
	{
	alert("Enter Account Number");
	}
	else if(val3=="")
	{
	 alert("Enter Account Number");
	}
	else
	{
	document.forms[0].submit();
	}
}

	
	
function introsubmit()
{	
	var val1=document.forms[0].intro_ac_no.value;
	if(val1==0)
	{
	alert("Enter Account Number");
	}
	else if(val1=="")
	{
	 alert("Enter Account Number");
	}
	else
	{
	document.forms[0].submit();
	}
	
} 
 
 
 
function funclear() 

{

    var v = document.forms[0].elements;
    for(var i=0;i<=v.length;i++)
    {
       if(v[i].type =="text")
       {
          v[i].value = "";

        }
     }
 
}

function checkblank(data,str)
{
  
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


function set(target)
{
	var val1=document.forms[0].ac_no.value;
	
	var val2=document.forms[0].cid.value;
	var val3=document.forms[0].dep_amt.value;
	var val4=document.forms[0].period_of_days.value;
		  if(val2==0)
	  {
	  alert("Please enter the CID!");
	  return false;
	  }
	  else 
	  	  if(val3==0)
	  {
	  alert("Please enter the Deposit Amount!");
	  return false;
	  }
	  else 
	  	  if(val4==0)
	  {
	  alert("Please enter the Number of Deposit Days!");
	  return false;
	  }
	  
	  if(document.forms[0].pay_mode.value=="Transfer" && document.forms[0].pay_mode_ac_no.value==0)
	  {
	     alert("Cannot Submit-Please Enter Pay Account Number");
	     return false;
	  }
	  if(document.forms[0].amt_recv.value=="Cash" && document.forms[0].scroll_no.value==0)
	  {
	     alert("Cannot Submit-Please Enter Scroll Number");
	     return false;
	  }
	  if(document.forms[0].amt_recv.value=="Transfer" && document.forms[0].trf_acno.value==0)
	  {
	     alert("Cannot Submit-Please Enter Amount Received Transfer Account Number");
	     return false;
	  }
	  if(document.forms[0].amt_recv.value=="Q/DD/PO" && document.forms[0].control_no.value==0)
	  {
	     alert("Cannot Submit-Please Enter Amount Received Control Number");
	     return false;
	  }
	else
	{
	    
	  var value = confirm("Are you sure you want to go ahead with the operation?");
	
	  if(value)
	  {
		document.forms[0].forward.value=target;
		document.forms[0].submit();
		return true;
	  }	
	  else
	  {
	 	return false;
	   }
	}
	return true;
};

function verify(target)
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
	if(document.getElementById("pcombo").value=="loantable")
	{
				
		document.getElementById('loantable'+AttributeToHide).style.display='none';
	}
	
	if(document.getElementById("validat").value!=0)
	{
		alert("Account Created Successfully!!!");
		alert("The New Account number Is:"+document.getElementById("validat").value);	
		funclear();
		return false;		
	}
	if(document.getElementById("totaltesting").value!=0)
	{
		
		alert(document.getElementById("totaltesting").value);	
		
			
	}
	if(document.getElementById("validat").value=="" )
	{
	 	
	 	 
			
	}
	
	Acountname();
	dontdisplay();
	
	funchidefields();
	
	var ele=document.forms[0].elements;
	for(var i=0;i<ele.length;i++)
	{
	if(ele[i].type=="hidden")
	{
	ele[i].value="";
	}
	}
	
	
};

function checksub(target)
 	{
 		document.forms[0].forward.value=target
 		document.forms[0].submit();
 	}
function Acountname()
{

 var name=document.forms[0].ac_type.value;
 	if(name=='1003001')
	{
		
		document.getElementById("interfrequencylable").style.display='block';
		document.getElementById("interfrequency").style.display='block';
		document.getElementById("intFREQUncylable").style.display='none';
		document.getElementById("intFREQUncy").style.display='none';
		document.getElementById("comboauto").style.display='block';
		document.getElementById("comboautolab").style.display='block';
	}
	else if(name=='1004001')
	{
		
		document.forms[0].intFREQ.value='Quarterly';
		document.getElementById("intFREQUncylable").style.display='block';
		document.getElementById("intFREQUncy").style.display='block';
	    document.getElementById("interfrequency").style.display='none';
	    document.getElementById("interfrequencylable").style.display='none';
		document.getElementById("comboauto").style.display='none';
		document.getElementById("comboautolab").style.display='none';
	}
	else if(name=='1005001')
	{
    
    document.forms[0].intFREQ.value='Quarterly';
    document.getElementById("intFREQUncylable").style.display='block';
    document.getElementById("intFREQUncy").style.display='block';
	document.getElementById("interfrequency").style.display='none';
	document.getElementById("interfrequencylable").style.display='none';
	document.getElementById("comboauto").style.display='block';
	document.getElementById("comboautolab").style.display='block';
    }
   else if(name=='1005002')
   {
    
    document.forms[0].intFREQ.value='Quarterly';
    document.getElementById("intFREQUncylable").style.display='block';
    document.getElementById("intFREQUncy").style.display='block';
	document.getElementById("interfrequency").style.display='none';
	document.getElementById("interfrequencylable").style.display='none';
    document.getElementById("comboauto").style.display='block';
   }
   else if(name=='1005003')
   {
    
    document.forms[0].intFREQ.value='Quarterly';
    document.getElementById("intFREQUncylable").style.display='block';
    document.getElementById("intFREQUncy").style.display='block';
	document.getElementById("interfrequency").style.display='none';
	document.getElementById("interfrequencylable").style.display='none';
    document.getElementById("comboauto").style.display='block';
    document.getElementById("comboautolab").style.display='block';
   }
}
--></script>
<%if(ver_value==1){
    	System.out.println("Ho my my SEE this value===1");
    	%>
    <h2 class="h2">
      <center>Deposit Opening Form</center></h2>
   <%}else if(ver_value==2){
   	System.out.println("Inside Ver value===2");
   %>      
      <h2 class="h2">
      <center>Account  Verification</center></h2>
    <%} %>
</head>
<body class="Mainbody" onload="HideShow(11)">
<%!
ModuleObject[] array_module,module_object;
DepositMasterObject verify_values,depmast_verify;
CustomerMasterObject cust_obj;
AccountObject acc_obj;
String[] details,pay_mode,auto,int_freq;
String jsppath,matdate,pagenew,Accountnotfound;
String Fetch_name;
int i,j;
int value,ver_value;
double[] combo_mat_cat,intrat;
String[]  combo_mat_cat1;
Double int_amt,mat_amt,balance,dep_amt;
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
Fetch_name="sumanth";
intrat=(double[])request.getAttribute("interestrate");
//Fetch_name=(AccountObject[])request.getAttribute("Ac_name");
if(Fetch_name!=null){
System.out.println("checkinggggggggggg56767g the interest Rate in Jsp-----2---- "+Fetch_name);
}
String new_aclab=(String)request.getAttribute("new_acno");
System.out.println("the value of new acount lable "+ new_aclab);
String nstr=(String)request.getAttribute("flag");
if(nstr!=null){
System.out.println("inside 0"+nstr);
}
//Accountnotfound=(String)request.getAttribute("Accountnotfound");
int_amt = (Double)request.getAttribute("interestamount");
mat_amt = (Double)request.getAttribute("maturityamt");
matdate = (String)request.getAttribute("matdate");
if(matdate!=null){
System.out.println("in jsp  maturity date====="+matdate);
}
System.out.println("checkingggggggggggg----1-----");
acc_obj = (AccountObject)request.getAttribute("balance");

 
dep_amt = mat_amt;		

System.out.println("checkingggggggggggg-----3----");

ver_value =(Integer)request.getAttribute("Verifyvalue");

System.out.println("***************Inside JSP ******* and value of  ver_value"+ver_value);


System.out.println("Inside JSP MaTuRiTy date-+-+-=-=-+-+-+>>>>>>>>"+ matdate);
String verified=(String)request.getAttribute("verified");
depmast_verify = (DepositMasterObject)request.getAttribute("verify");
verify_values = (DepositMasterObject)request.getAttribute("verify_values");
String message=(String)request.getAttribute("msg");
String newmsg =(String)request.getAttribute("msgnew");

//System.out.println("depmast verify???"+depmast_verify);
System.out.println("ver valueeeee???===="+ver_value);

//System.out.println("depmast verify values"+verify_values);



%>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/AccountOpening?pageId=13001" method="Post" focus="<%=""+request.getAttribute("getfocus")%>">

<%if(message!=null){ %>
<div id = "div1" class = "myLayersClass">
<%if(message!=null){ %>
    <font color="red"><%=message %></font>
    <%} %>
    <br/>
</div>
<%} %>
<html:hidden property="forward" value="error" ></html:hidden>
<html:hidden property="detail" ></html:hidden>
<html:hidden property="accountgen" />
<html:hidden property="validate" styleId="validat"></html:hidden>
<html:hidden property="testing" styleId="totaltesting"></html:hidden>
<!-- main table-->
<%if(verified!=null){ %>
<font color="red"><%=verified %></font>
<%} %>
<br>
<table  class = "txtTable"  cellspacing="3" style="border-bottom-color:green;border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:black;border-left-style:hidden;border-right-style:hidden;border-right-color:green;border-top-color:green;border-top-style:hidden;">
<tr>
 <td>
	<table class = "txtTable" width="20%" cellspacing="0"  	style="border: thin solid maroon;">
	   	<tr>
	   			<td><bean:message key="label.td_actype"></bean:message></td>
			    	<td><html:select property = "ac_type" styleClass="formTextFieldWithoutTransparent" onchange="Acountname()" tabindex="1">
			    		<% array_module=(ModuleObject[])request.getAttribute("Dep type");
			    		if(array_module!=null){ 
			    		for(int i=0;i<array_module.length;i++){%>	    	
			    				<html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()+"-"+array_module[i].getModuleDesc()%></html:option>
			    		<%}%>
			    		<%} %>
			    	 	</html:select></td>
		</tr>
   		<tr>
				<td><bean:message key="label.td_acno"></bean:message></td>
					<%if(verify_values != null){%>
			     		<td><html:text property="ac_no" onblur="ckValidate()" value="<%=""+verify_values.getAccNo() %>" tabindex="2" size="10" onkeypress="return only_numbers()" onkeyup="numberlimit1(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			   		<%}else{%>
				  		<td><html:text property="ac_no" onblur="ckValidate()" tabindex="2"  size="10" onkeypress="return only_numbers()" onkeyup="numberlimit1(this,'11')" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
				  		<td><%if(newmsg!=null){%>
				  			<%=""+newmsg %>
			     		<%}%></td>
			     	<%}%>    
			     	<%if(new_aclab!=null){%>  
			        	<td><font color="red"> <bean:message key="CLAM2"></bean:message></font></td>
			        <%}%> 
		</tr>
		<tr>
				  <td><bean:message key="label.cid"></bean:message></td>
					<%if(verify_values != null){%>
			     		<td><html:text property="cid" onkeyup="numberlimit1(this,'11')"  value="<%=""+verify_values.getCustomerId() %>" size="10"  styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" ></html:text></td>
			   		<%}else{%>
			     		<td><html:text property="cid"  onkeyup="numberlimit1(this,'11')" onkeypress="return only_numbers()"  size="10"  styleClass="formTextFieldWithoutTransparent" onblur="submit()" tabindex="3" ></html:text></td>
			       <%} %> 
		</tr>
		<tr>
					<td><bean:message key="label.dep_amt"></bean:message></td>
			   			<%if(verify_values!=null){%>
			    			<td><html:text property="dep_amt" size="10" value="<%=""+verify_values.getDepositAmt()%>" onblur="submit()" onkeyup="numberlimit1(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			    		<%}else{ %>
			    			<td><html:text property="dep_amt" size="10" onkeypress="return only_numbers()" onblur="submit()" onkeyup="numberlimit1(this,'11')" styleClass="formTextFieldWithoutTransparent" tabindex="4"></html:text></td>
			    		<%} %>
		</tr>
		<tr>
				  <td><bean:message key="label.dep_date"></bean:message></td>
					<%if(verify_values != null){%>
			     		<td><html:text property="dep_date"  value="<%= ""+verify_values.getDepDate() %>" size="10"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
			   		<%}else{%>
				   		<td><html:text property="dep_date"  value="<%=""+request.getAttribute("date") %>" size="10" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			       	<%}%> 
		</tr>
		<tr>
				<td><bean:message key="label.Period_in_days"></bean:message></td>
					<%if(verify_values != null){%>
			     		<td><html:text property="period_of_days" onkeyup="numberlimit2(this,'5')" value="<%=""+verify_values.getDepositDays() %>"   onblur="return perioddays()" size="10"  styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersOnly()"></html:text></td>
			   		<%}else{%>
			     		<td><html:text property="period_of_days"  onkeyup="numberlimit2(this,'5')"  onblur="submit()" size="10"   styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersOnly()"></html:text></td>
			       	<%} %> 
		</tr>
		<tr>
				<td><bean:message key="label.mat_date"></bean:message></td>
			    	<%if(verify_values!= null){%>
			    		<td><html:text property="mat_date" value="<%=verify_values.getMaturityDate() %>" size="10" readonly="true" ></html:text></td>
			    		<td><html:select property="combo_mat_cat"  styleClass="formTextFieldWithoutTransparent" value="<%=""+verify_values.getExtraRateType() %>">
			    			<html:option value="0">None</html:option>
			    			<html:option value="1">Catagory Individual 0.0</html:option>
			    			<html:option value="2">SubCatagory Individual 1.0</html:option>
			   			</html:select></td>
			  		<%}else{%>
				<td><html:text property="mat_date"  value="<%=matdate %>" size="10"   readonly="true"></html:text></td>
			    <td><html:select property="combo_mat_cat" styleClass="formTextFieldWithoutTransparent" onchange="submit()" >
			    		<% combo_mat_cat=(double[])request.getAttribute("Cat_type");%>
			    		<%if(combo_mat_cat!=null){%>	    				    		
	    	 					<html:option value="0">NONE</html:option>
			                    <html:option value="1">Catagory : Individual: <%=combo_mat_cat[1]%></html:option>
			    				<html:option value="2">Sub-Category: <%=combo_mat_cat[2]%></html:option>
			    				<html:option value="3">BOTH: <%=combo_mat_cat[1]+combo_mat_cat[2]%></html:option>
			    		<%}else{%>	
			    				<html:option value="0">None</html:option>
			    				<html:option value="1">Catagory Individual 0.0</html:option>
			    				<html:option value="2">SubCatagory Individual 0.0</html:option>
			   			<%}%>
			   		</html:select></td>
			     	<%}%>
		</tr>
		<tr>
				<td><div id="comboautolab" style="display:none;"><bean:message key="label.combo_autorenewal"></bean:message></div></td>
			    <%if(verify_values != null){%>
			    <td><div id="comboauto" style="display:none;">  
			    	<html:select property="combo_autorenewal" size="1" value="<%=""+verify_values.getAutoRenewal() %>" styleClass="formTextFieldWithoutTransparent">
			    	   <html:option value="N">None</html:option>
			    	   <html:option value="M">Maturity Amount</html:option>
			       	   <html:option value="D">Deposit Amount</html:option>
			       	</html:select></div>
			    </td>
			  	<%}else{%>
			    <td><div id="comboauto" style="display:none;">   
			    	<html:select property="combo_autorenewal" size="1" styleClass="formTextFieldWithoutTransparent">
			    	    <html:option value="N">None</html:option>
			    	    <html:option value="M">Maturity Amount</html:option>
			       	    <html:option value="D">Deposit Amount</html:option>
			       	</html:select></div>
			    </td>
			    <%}%>
		</tr>
		<tr>
				<td><bean:message key="label.introduceractypeno" ></bean:message></td>
			    <%if(verify_values != null){%>
			    	<td><html:select property="intro_ac_type"   value="<%=""+verify_values.getIntroAccType() %>" size="1"  styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersOnly()">
		    			<% module_object = (ModuleObject[])request.getAttribute("intro_type");%>
		    			<%if(module_object!=null){ %>	
		    			<%for(int i=0;i<module_object.length;i++){ %>
		    				<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    		<%}%>
			    		<%}%>	
		       		</html:select></td>
				<td><html:text property="intro_ac_no" size="10" onkeyup="numberlimit1(this,'11')" onkeypress="return numbersOnly()" value="<%=""+verify_values.getIntroAccno() %>" onblur= "introsubmit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			  	<%}else{%>
			  	<td><html:select property="intro_ac_type"  size="1"  styleClass="formTextFieldWithoutTransparent">
			    		<% module_object = (ModuleObject[])request.getAttribute("intro_type");%>
			    		<%if(module_object!=null){ %>
			    		<%for(int i=0;i<module_object.length;i++){ %>
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    		<%}%>
			    		<%}%>	
			       </html:select></td>
			    <td><html:text property="intro_ac_no" onkeyup="numberlimit1(this,'11')"  size="10"  onblur="introsubmit()" onkeypress="return numbersOnly()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
			    <%}%>
		</tr>
		<tr>
				<td><bean:message key="label.combo_pay_mode"></bean:message></td>
			    <%if(verify_values != null){%>
			    	<td><html:select property="pay_mode"  value="<%=""+verify_values.getInterestMode() %>" onchange="funchidefields()" styleClass="formTextFieldWithoutTransparent" >	
			       			<html:option value="T">Transfer</html:option>
			        		<html:option value="C">Cash</html:option>
			        		<html:option value="Q/DD/PO">Q/DD/PO</html:option>	 
			          </html:select></td>
			       <%}else{%>
			    	<td><html:select property="pay_mode" styleClass="formTextFieldWithoutTransparent" onchange="funchidefields()">	
			        	<%pay_mode=(String[])request.getAttribute("pay_mode");%>
			        		<%if(pay_mode!=null){ %> 	 
			        		<%for(int i=0;i < pay_mode.length;i++){ %>
			    				<html:option value="<%=""+pay_mode[i]%>"><%=""+pay_mode[i]%></html:option>
			    			<%}%>
			    			<%}%>
			           </html:select></td>
			     	<%} %>
		</tr>
		<tr>
				<td><div id="pay_actypelab" style="display:none;"><bean:message key="label.combo_pay_actype"></bean:message></div></td>
			    <%if(verify_values!= null){ %>
			    <td><div id="pay_acType" style="display:none;">
			    	<html:select property="pay_ac_type" value="<%=""+verify_values.getTransferAccType() %>" size="1" styleClass="formTextFieldWithoutTransparent">
			        <%module_object=(ModuleObject[])request.getAttribute("pay_actype");%>
			        <%if(module_object!=null){ %>	
			        <%for(int i=0;i < module_object.length;i++){ %>
			    		<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    	<%}%>
			    	<%}%>
			        </html:select></div></td> 
			    <%}else{ %>
			    <td><div id="pay_acType" style="display:none;">
			    	<html:select property="pay_ac_type"  size="1" styleClass="formTextFieldWithoutTransparent">
			        <%module_object=(ModuleObject[])request.getAttribute("pay_actype");%>
			        	<%if(module_object!=null){%>
			        	<%for(int i=0;i < module_object.length;i++){%>
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    		<%}%>
			    		<%}%>
			         </html:select></div></td> 
			    <%} %>
				<%if(verify_values != null){%>
			    <td><div id="pay_acNo" style="display:none;"><html:text property="pay_mode_ac_no" onkeypress="return numbersOnly()"  value="<%=""+verify_values.getTransferAccno() %>" onblur="paymodsubmit()"  styleClass="formTextFieldWithoutTransparent" size="10"></html:text></div></td>
		        <%}else{ %>
		        <td><div id="pay_acNo" style="display:none;"><html:text property="pay_mode_ac_no"  onkeypress="return numbersOnly()" onblur="paymodsubmit()" styleClass="formTextFieldWithoutTransparent" size="10"></html:text></div></td>
		       	<%} %>
		</tr> 
		<tr>
			    <td><bean:message key="label.combo_amt_received"></bean:message></td>
			    <td><html:select property="amt_recv" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit1(this,'11')" onchange="HideShow(11)" styleId="pcombo" onblur="javascript:document.forms[0].trf_acno.focus()">
			     <%if(verify_values!=null){%>
	             <%String trf_mode=verify_values.getReceivedBy();%>
			     <%if(trf_mode.equalsIgnoreCase("T")){%> 
			             <html:option value="T">Transfer</html:option>
			           <%}else if(trf_mode.equalsIgnoreCase("C")){ %>
			             <html:option value="C">Cash</html:option>
			           <%}else if(trf_mode.equalsIgnoreCase("Q")){ %>
			             <html:option value="Q">Cheque</html:option>
			           <%}else if(trf_mode.equalsIgnoreCase("D")){ %>
			             <html:option value="D">DD</html:option>
			           <%}else if(trf_mode.equalsIgnoreCase("P")){  %>
			             <html:option value="P">PO</html:option>
			           <%} %>
			      <%}else{%>
				       <%pay_mode=(String[])request.getAttribute("amt_recv");%>
				       		<%if(pay_mode!=null){%>
				            <%for(int i=0;i < pay_mode.length;i++){%>	   			    			    	 
				    			<html:option value="<%=""+pay_mode[i].charAt(0)%>"><%=""+pay_mode[i]%></html:option>
				    		<%}%>
				    	<%}%>
				    <%}%>
	         	</html:select></td>
		</tr> 
<!-- code for the details of personal and nominee and so on......!!			 -->
    	<tr>
			    <td><bean:message key="label.details"></bean:message></td>
			    <td><html:select property="details" styleClass="formTextFieldWithoutTransparent" onchange="beforSubmit()" styleId="pcombo" >
			          <%details=(String[])request.getAttribute("details");%>
			          <%for(int i=0;i < details.length;i++){%>	   			    			    	 
			    		<html:option value="<%=""+details[i]%>"><%=""+details[i]%></html:option>
			    	  <%}%>
			       </html:select></td>
		</tr>
		
		
		
		<tr>
		<td>
			<table class="txtTable" frame="above" cellspacing="3" style="border-bottom-color:teal;border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:teal;border-left-style:hidden;border-right-style:hidden;border-right-color:teal;border-top-color:teal;border-top-style:hidden;">
				<tr id="Transfer11">
				<td>
			    	<bean:message key="label.trf_actype"></bean:message>
			    </td>
			    <%if(verify_values!=null){%>
			    <td>
			    	<html:select property="trf_actype" value="<%=""+verify_values.getTransferAccType() %>" styleClass="formTextFieldWithoutTransparent">
			        <%module_object=(ModuleObject[])request.getAttribute("pay_actype");
			       	if(module_object!=null){
			        for(int i=0;i < module_object.length;i++)
			            {
						 	 System.out.println("module_object"+ module_object);
			    	 %>	   			    			    	 
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    	<%	}}	%>
			         </html:select>
			         </td>
			         <%}else{%>
			         <td>
			         <html:select property="trf_actype"  styleClass="formTextFieldWithoutTransparent">
			         <%module_object=(ModuleObject[])request.getAttribute("pay_actype");
			       	if(module_object!=null){
			        for(int i=0;i < module_object.length;i++)
			            {
						 	 System.out.println("module_object"+ module_object);
			    	 %>	   			    			    	 
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    	<%	}}	%>
			         </html:select>
			         </td>
			         <%} %>	 
			    <td>
    		        <bean:message key="label.trf_acno"></bean:message>
    		    </td>
    		    <%if(verify_values!=null){%>             		  
			    <td>
			    <html:text property="trf_acno" onkeypress="return only_numbers()" size="10" onkeyup="numberlimit(this,'5')" value="<%=""+verify_values.getReceivedAccno()%>" onchange="trnsubmit()"  style="formTextFieldWithoutTransparent"></html:text>
			    </td>
			    <%}else{%>
			    <td>
				<html:text property="trf_acno"   onkeypress="return only_numbers()" size="10" onkeyup="numberlimit(this,'5')" onchange="trnsubmit()"  styleClass="formTextFieldWithoutTransparent"></html:text>
				 </td>
				 <%}%>
                 <td>
    		        <bean:message key="lable.bal"></bean:message>
    		    </td>
    		     <%if(verify_values!=null){%>
		           <td><html:text property="balance_amt" size="10" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
			        <%}else{ %>
			        <td><html:text property="balance_amt" readonly="true"  size="10" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
			        <%} %>
    		   </tr>
				
				
				<tr>
			       <td><div id="interfrequencylable" style="display:none;"><bean:message key="label.Int_freq"></bean:message></div></td>
	               <td><div id="interfrequency" style="display:none;">
	               		<html:select property="interest_freq" styleClass="formTextFieldWithoutTransparent" onchange="submit()" >
	               			<%if(verify_values!=null){%>
			         		 	<%String int_freq=verify_values.getInterestFrq(); %>
			         		 	<%if(int_freq.equalsIgnoreCase("M")){ %>
			         		 		<html:option value="M">Monthly</html:option>
			         		 	<%}else if(int_freq.equalsIgnoreCase("Q")){ %>
			         		 		<html:option value="Q">Quarterly</html:option>
			         		 	<%}else if(int_freq.equalsIgnoreCase("H")){ %>
			         		 		<html:option value="H">Half-Yearly</html:option>
			         		 	<%}else if(int_freq.equalsIgnoreCase("Y")){ %>
			         		 		<html:option value="Y">Yearly</html:option>
			         		 	<%}else if(int_freq.equalsIgnoreCase("O")){ %>
			         		 		<html:option value="O">On-Maturity</html:option>
			         		 	<%}%>
			         		 <%}else{ %>
			         		 	<%int_freq=(String[])request.getAttribute("int_freq");%>
			             	 	<%if(int_freq!=null){ %>
			         		 		<%for(int i=0;i < int_freq.length;i++){ %>
			    						<html:option value="<%=""+int_freq[i].charAt(0)%>"><%=""+int_freq[i]%></html:option>
			    					<%}%>
			    				<%} %>
			    			<%}%>
			         	</html:select></div>
		         </td>
		         <td><div id="intFREQUncylable" style="display:none;"><bean:message key="label.Int_freq"></bean:message></div></td>
		         <td><div id="intFREQUncy" style="display:none;"><html:text property="intFREQ" disabled="true" size="10" style="color:red"></html:text></div></td>
		         <td><bean:message key="label.Intraterate"></bean:message></td>
		           <%if(verify_values!=null){%>
		               <td><html:text property="int_rate" readonly="true" size="10"   styleClass="formTextFieldWithoutTransparent" ></html:text></td>
		           <%}else{%>
		             	<td><html:text property="int_rate" readonly="true" size="10"  styleClass="formTextFieldWithoutTransparent" ></html:text></td>
		           <%} %>
			   </tr>
			   <tr>
			     <td><bean:message key="label.Int_payableamt"></bean:message></td>
			   	 	<%if(verify_values!=null){ %>
			      		<td><html:text property="int_payable" readonly="true"  size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			 		<%}else{%>
			 			<td><html:text property="int_payable" readonly="true"  size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			 		<%} %>
				 <td><bean:message key="label.Mat_amt"></bean:message></td>
			         <%if(mat_amt!=null){%>
			           <td><html:text property="mat_amt"  value="<%=""+mat_amt %>"  size="8" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
			      	<%}else if(verify_values!=null){ %>
			        	<td><html:text property="mat_amt" readonly="true" value="<%=""+verify_values.getMaturityAmt()%>"  size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			        <%}else{ %>
			        	<td><html:text property="mat_amt" readonly="true"  size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			        <%} %>
			 </tr>
 	</table>
		</td>
	</tr>
     
     <tr>
				<td>
					<%String enable=(String)request.getAttribute("enable"); %>
					<%if(enable!=null){ %>
 					<table class="txtTable">
  						<html:hidden property="nomforward"></html:hidden>
  						<html:hidden property="nomvalidations"></html:hidden>
  				<%String showcid=(String)request.getAttribute("showcid"); %>
  				<%NomineeObject[] nomineeObjects=(NomineeObject[])session.getAttribute("Nominee"); %>
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
			<%String enable1=(String)request.getAttribute("enable1"); %>
			<%if(enable1!=null){ %>
			<table class = "txtTable" cellspacing="0">
				<tr>
					<td><bean:message key="label.jointprime"></bean:message></td>
					<td><html:text property="primejoint" size="8" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
				</tr>
				<tr>
					<td><font color="blue"><bean:message key="label.jointsec"/></font></td>
				</tr>
				<tr>
					<td><bean:message key="label.jointnum"></bean:message></td>
					<td><html:text property="jointnum" size="8" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit1(this,'2')" onblur="submit()"></html:text></td>
				</tr>
				<%int jointnum=(Integer)request.getAttribute("jointnum"); %>
				<%if(jointnum!=0){ %>
				<%for(int i=0;i<jointnum;i++){ %>
				<%CustomerMasterObject cmobj1=(CustomerMasterObject)session.getAttribute("custnames"+i); %>
				<tr>
					<td><bean:message key="label.jointcid"></bean:message></td>
					<%if(cmobj1!=null && cmobj1.getCustomerID()!=0){ %>
					<td><html:text property="<%="jcid"+i%>" value="<%=""+cmobj1.getCustomerID() %>" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
					<%}else{ %>
					<td><html:text property="<%="jcid"+i%>" size="8" onblur="checksub('JCid')" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()"></html:text></td>
					<%} %>
					<%if(cmobj1!=null){ %>
						<td><html:text property="custname" value="<%=""+cmobj1.getFirstName()+" "+cmobj1.getMiddleName() %>" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
					<%} %>
				</tr>
				<%} %>
				<%} %>
		</table>
		<%} %>
	</td>
	</tr>
	
  <%System.out.println("Inside ApplnDataEntry.jsp Page99999999999999" + nstr);%>
  <tr>
  	<td>
		<table align="right" width="10%">
			<%if( nstr!=null ){%>
    		<tr> 
    			<td>
				<%pagenew=nstr;%>
		   		<jsp:include page="<%=pagenew %>"></jsp:include>
		   		<%System.out.println("After include------>"+pagenew);%>
	    		</td>
    		</tr>
			<%} %>
 		</table>
 	</td>
 </tr>
          
<tr>
   	<td> 
		<table class="txtTable" width="10%" cellspacing="3" style="border-bottom-color:teal; border-bottom-style:hidden;border-bottom-style:hidden;border-left-color:teal;border-left-style:hidden;border-right-style:hidden;border-right-color:teal;border-top-color:teal;border-top-style:hidden;">
				<tr> 
				<%if(ver_value==1){ %>
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
				 	<td><html:button property="sub" onclick="return set('Submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message></html:button></td>
				 <%}else{ %>
				 	<td><html:button property="sub" onclick="return set('Submit')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.submit"></bean:message></html:button></td>
				 <%}%>
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>				
					<td><html:button property="subm" onclick="set('Submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.update"></bean:message></html:button></td>
				<%}else{ %>
					<td><html:button property="subm" onclick="set('Submit')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.update"></bean:message></html:button></td>
				<%} %>
					<td><html:button property="clearbut" onclick="funclear()" value="Clear" styleClass="ButtonBackgroundImage"></html:button></td>
				<%}else if(ver_value==2){%>	 
				 	<td><html:submit property="ver" onclick="verify('Verify')" styleClass="ButtonBackgroundImage"><bean:message key="label.verify"></bean:message></html:submit></td>
					<td><html:submit onclick="set('delete')" styleClass="ButtonBackgroundImage" ><bean:message key="label.delete"></bean:message></html:submit> </td>
					<td><html:button property="clearbut" onclick="funclear()" value="Clear" styleClass="ButtonBackgroundImage"></html:button></td>
				<%} %>
				</tr>	
		</table>
	</td>
	</tr>
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