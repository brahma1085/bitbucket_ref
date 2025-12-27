<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="java.util.Map"%>

<html>
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
function isDate(dtStr)
{
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
		document.forms[0].validateFlag.value="The date format should be : dd/mm/yyyy" ;
		document.forms[0].clgDate.focus();
		return false;
	}
	if (strMonth.length<1 || month<1 || month>12){
		document.forms[0].validateFlag.value="Please enter a valid month" ;
		document.forms[0].clgDate.focus();
		return false;
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		document.forms[0].validateFlag.value="Please enter a valid day as this year is not a leap year";
		document.forms[0].clgDate.focus();
		return false;
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		document.forms[0].validateFlag.value="Please enter a valid 4 digit year between "+minYear+" and "+maxYear+" ";
		document.forms[0].clgDate.focus();
		return false;
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		document.forms[0].validateFlag.value="Please enter a valid date" ;
		document.forms[0].clgDate.focus();
		return false;
	}
return true;
};

function properdate(clgDate,to_date){
  
  
  var dtCh="/";
   
  var pos1=clgDate.indexOf(dtCh)
  var pos2=clgDate.indexOf(dtCh,pos1+1)
  var frmMonth=clgDate.substring(pos1+1,pos2)
  var frmDay=clgDate.substring(0,pos1)
  var frmYear=clgDate.substring(pos2+1)
  
  
  var pos3=to_date.indexOf(dtCh)
  
  var pos4=to_date.indexOf(dtCh,pos3+1)
  
  var ToMonth=to_date.substring(pos3+1,pos4)
  
  var ToDay=to_date.substring(0,pos3)
  
  var ToYear=to_date.substring(pos4+1)
  
  
  
  if(frmYear > ToYear)
  {
    document.forms[0].validateFlag.value="From Year is greater than To Year!!!! Enter valid date" ;
    document.forms[0].clgDate.focus();
  }
  else if(frmMonth > ToMonth && frmYear<=ToYear)
  {
    document.forms[0].validateFlag.value="From Month is greater than To Month !!!! Enter valid date" ;
    document.forms[0].clgDate.focus();
  }
 else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear)
 {
 	 document.forms[0].validateFlag.value="From day is greater than To day !!!! Enter valid date";
 	 document.forms[0].clgDate.focus();
  }
 }; 
 


function ValidateForm(){
   
	var dt=document[0].clgDate;
	
	if (isDate(dt.value)==false){
	       	
		return false;
	}
    return true;
 };
 

function setValue(flagVal)
{

	if(document.forms[0].clgDate.value!='')
	{
		document.forms[0].flag.value=flagVal;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Clg Date";
	}

}
function clearAll()
{
	
	var form_ele = document.forms[0].elements;
		for(var i=0;i<form_ele.length;i++)
		{
			if(form_ele[i].type=="text")
			{
				form_ele[i].value = "";
			}
		}
		
		
		document.forms[0].flag.value="";
		document.forms[0].submit();
	} 

</script>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />

<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body class="MainBody">
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
<center><h2 class="h2"><bean:message key="label.OutStationLetter"/></h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form  action="/Clearing/OutStationLetterLink?pageId=7030">
<html:hidden property="pageId"></html:hidden>
<html:hidden property="flag"></html:hidden>

<%	String date=(String)request.getAttribute("date");%>

<table>
	<tr>
	<td>
	<table><tr><html:text property="validateFlag" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr></table>
	</td>
	</tr>
	<tr>
	<td>
	<table>
	<tr>
		<td><bean:message key="label.clr_date"></bean:message>
		<html:text property="clgDate" styleClass="formTextFieldWithoutTransparent" value="<%=""+date%>" size="10" onblur="ValidateForm()"></html:text></td>
		
	</tr>
	
	<tr>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
		<td><html:button property="view" styleClass="ButtonBackGroundImage" onclick="setValue('clgDate')"><bean:message key="label.view1"></bean:message></html:button>
		<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
			<html:button property="file" styleClass="ButtonBackGroundImage"><bean:message key="label.file"></bean:message></html:button>
			<html:button property="print" onclick="window.print()" styleClass="ButtonBackGroundImage"><bean:message key="label.print"></bean:message></html:button>
			<html:submit property="clear" styleClass="ButtonBackGroundImage" onclick="clearAll()"><bean:message key="label.clear"></bean:message></html:submit>
		</td>
	</tr>
	
	<tr>
	<td>
		<display:table name="OutStationCheque" class="its">	
				<display:column property="controlNo" ></display:column>
				<display:column property="creditACType" ></display:column>
				<display:column property="creditACNo" ></display:column>
				<display:column property="payeeName" ></display:column>
				<display:column property="bankCode" ></display:column>
				<display:column property="tranAmt" ></display:column>
				<display:column property="qdpNo" title="Chq No"></display:column>
				<display:column property="qdpDate" title="Chq Date" ></display:column>
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