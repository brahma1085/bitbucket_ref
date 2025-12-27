<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@page import="java.util.Map"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
</head>
<script type="text/javascript">
function button_View(target)
{
	if(document.forms[0].clr_no.value=="")
	{
		document.forms[0].validation.value="Enter Clearing Number";
	}
	else
	{
		document.forms[0].but_value.value=target;
		document.forms[0].submit();
	}
};

function callClear()
{
    var ele= document.forms[0].elements;
    for(var i=0;i<ele.length;i++)
    {
        if(ele[i].type=="text")
         {
             ele[i].value="";
         }
     }
      
     document.forms[0].but_value.value="";  
     document.forms[0].submit();  
 }

function HideShow(AttributetoHide)
{
	if(document.forms[0].reports.value=="Clg Statements")
	{
		document.getElementById('clrstmt'+AttributetoHide).style.display='block'; 
		document.getElementById('clrslip'+AttributetoHide).style.display='none';
	}
	else if(document.forms[0].reports.value=="Clg Summary")
	{
		document.getElementById('clrstmt'+AttributetoHide).style.display='none'; 
		document.getElementById('clrslip'+AttributetoHide).style.display='block';
	}
	

};

var dtCh= "/";
var minYear=1900;
var maxYear=9999;
function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c=s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
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
	
	if (pos1==-1 || pos2==-1)
	{
		document.forms[0].validation.value="The date format should be : dd/mm/yyyy";
		document.forms[0].clr_date.focus();
		return false;
	}
	if (strMonth.length<1 || month<1 || month>12){
		document.forms[0].validation.value="Please Enter Valid Month";
		document.forms[0].clr_date.focus();
		return false;
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		document.forms[0].validation.value="Please enter a valid day as this year is not a leap year";
		document.forms[0].clr_date.focus();
		return false;
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		document.forms[0].validation.value="Please enter a valid 4 digit year between "+minYear+" and "+maxYear+" ";
		document.forms[0].clr_date.focus();
		return false;
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		document.forms[0].validation.value="Please enter a valid date";
		document.forms[0].clr_date.focus();
		return false;
	}
return true;
};

function properdate(clr_date,to_date){
  
  
  var dtCh="/";
   
  var pos1=clr_date.indexOf(dtCh)
  var pos2=clr_date.indexOf(dtCh,pos1+1)
  var frmMonth=clr_date.substring(pos1+1,pos2)
  var frmDay=clr_date.substring(0,pos1)
  var frmYear=clr_date.substring(pos2+1)
  
  
  var pos3=to_date.indexOf(dtCh)
  
  var pos4=to_date.indexOf(dtCh,pos3+1)
  
  var ToMonth=to_date.substring(pos3+1,pos4)
  
  var ToDay=to_date.substring(0,pos3)
  
  var ToYear=to_date.substring(pos4+1)
  
  
  
  if(frmYear > ToYear)
  {
    document.forms[0].validation.value="From Year is greater than To Year!!!! Enter valid date";
    document.forms[0].clr_date.focus();
  }
  else if(frmMonth > ToMonth && frmYear<=ToYear)
  {
    document.forms[0].validation.value="From Month is greater than To Month !!!! Enter valid date";
    document.forms[0].clr_date.focus();
  }
  else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear)
  {
  	document.forms[0].validation.value="From day is greater than To day !!!! Enter valid date";
  	document.forms[0].clr_date.focus();
  }
}; 

function ValidateForm()
{
	var dt=document[0].clr_date;
	if (isDate(dt.value)==false)
	{
		return false;
	}
  return true;
};
 
function onlynumbers()
{
      var cha=event.keyCode;
	if(cha>=48 && cha<=57) 
	{
          return true;
    } 
    else 
    {
       return false;
    }
};


 
</script>
<body class="Mainbody">
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
%>
<center><h2 class="h2">Outward Statement</h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/Outwardstmt?pageId=7023">
	<table class="txtTable">
		<html:hidden property="but_value"/>
		<tr>
		<td>
		<table><tr><html:text property="validation" styleId="valid" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"/></tr></table>
		</td>
		</tr>
		<tr>
		<td>
		<table>
		<tr>
			<td><bean:message key="label.rep"/>
			<html:select size="1" property="reports"  styleClass="formTextFieldWithoutTransparent">
			<html:option value="SELECT"></html:option>
			<html:option value="Clg Statements"></html:option>
			<html:option value="Enclosure Slips"></html:option>
			<html:option value="Clg Summary"></html:option>
			</html:select>
			</td>
		
			<td><bean:message key="label.clr_no"/>
			<html:text size="10" property="clr_no" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()"></html:text></td>
			<td><bean:message key="label.clr_date"/>
			<html:text size="10" property="clr_date" styleClass="formTextFieldWithoutTransparent"  onblur="ValidateForm()"></html:text></td>	
	
		</tr>
	
		</table>
		
		</td>
		</tr>
	
		<tr>
		<td>
		<table class="txtTable">
		<tr>
			<TD>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
			<html:button styleClass="ButtonBackgroundImage" onclick="button_View('View')" property="vyuu" value="View">View</html:button>
			<%}else{ %>
        	<font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        	<%} %>
			
			
			<!--<html:button property="print" styleClass="ButtonBackgroundImage" onclick="window.print()">Print</html:button>
			<html:button property="reprint" styleClass="ButtonBackgroundImage" onclick="callClear()"><bean:message key="label.clear"></bean:message></html:button>
			--><!-- 	<html:button property="storefile" styleClass="ButtonBackgroundImage" >Store File</html:button>
			<html:button property="search" styleClass="ButtonBackgroundImage">Search</html:button>
			<html:button property="query" styleClass="ButtonBackgroundImage">Query</html:button>   -->
			</TD>
		</tr>
		</table>
		</td>
		</tr>
		<tr>
		<td>
		<table class="txtTable">
		<tr id="clrstmt">
		<td>
		<display:table name="clgstatement" pagesize="6" class="its">
    			<display:column property="controlNo" title="Ctrl No" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="tranType" title="G/T" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="docBs" title="B/S" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="ackNo" title="Ack No" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="noOfDocs" title="No of" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="tranAmt" title="Amount" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="branchName" title="Source" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="retNormally" title="Destination" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="chqDDPO" title="QDP" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="qdpNo" title="QDP No" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="qdpDate" title="Cheque Deposition" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="creditACType" title="A/C Type" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="creditACNo" title="A/C No" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="despInd" title="Name" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="payeeName" title="Payee Name" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="tranMode" title="Trn_Bank" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="trfType" title="Trn_Branch" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="multiCredit" title="Multi credit" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="companyName" title="Company Name" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="discAmt" title="Disc amt" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="POCommission" title="PoCommission" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="deUser" title="De User" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="deTml" title="De Tml" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="deTime" title="De Time" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="veUser" title="Ve User" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="veTml" title="Ve Tml" style="width:3%;"  maxLength="50"></display:column>
    			<display:column property="veTime" title="Ve Time" style="width:3%;"  maxLength="50"></display:column>
    			</display:table>





    	</td>
	</tr>
	
	<tr id="clrslip">
	
		<td>
			<display:table name="clgsummery" pagesize="6" class="its">
   				
    			<display:column property="bankCode" title="Bank No" style="width:3%;"  maxLength="50"></display:column>
    			
    			<display:column property="branchName" title="Bank Name" style="width:3%;"  maxLength="50"></display:column>
    			
    			<display:column property="noOfDocs" title="Bundled No of docs" style="width:3%;"  maxLength="50"></display:column>
    			
    			<display:column property="discAmt" title="BAmount" style="width:3%;"  maxLength="50"></display:column>
    			
    			<display:column property="controlNo" title="Single No of docs" style="width:3%;"  maxLength="50"></display:column>
    			
    			<display:column property="tranAmt" title="SAmount" style="width:3%;"  maxLength="50"></display:column>
    			
    			<display:column property="qdpNo" title="Returned No of docs" style="width:3%;"  maxLength="50"></display:column>
    			
    			<display:column property="POCommission" title="RAmount" style="width:3%;"  maxLength="50"></display:column>
    			
    			<display:column property="ackNo" title="Total No of Docs" style="width:3%;"  maxLength="50"></display:column>
    			
    			<display:column property="fineAmt" title="Total Amount" style="width:3%;"  maxLength="50"></display:column>
    			
    			</display:table>
				
		</td>
	
	</tr>
	
<tr>
		<td>
			<display:table name="enclosureslips" pagesize="6" id="enSlips" class="its">
			<display:column title="ControlNo" property="controlNo" style="width:3%;"  maxLength="50"></display:column>
			<display:column title="Cr A/c Type" property="creditACType" style="width:3%;" maxLength="50"></display:column>
			<display:column title="Cr A/c No" property="creditACNo" style="width:3%;" maxLength="50"></display:column>
			<display:column title="No Of Docs" property="noOfDocs" style="width:3%;"  maxLength="50"></display:column>
			<display:column title="Chq No" property="qdpNo" style="width:3%;"  maxLength="50"></display:column>
			<display:column title="Amount" property="tranAmt" style="width:3%;"  maxLength="50"></display:column>
			</display:table>
			
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