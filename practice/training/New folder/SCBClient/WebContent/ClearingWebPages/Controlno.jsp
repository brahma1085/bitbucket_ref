<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>

<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>
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
	if(document.forms[0].from_date.value=="")
	{
		document.forms[0].validation.value=" Enter From Control Number";
	}
	else if(document.forms[0].to_date.value=="")
	{
		document.forms[0].validation.value=" Enter To Control Number";
	}
	else
	{
		document.forms[0].but_value.value=target;
		document.forms[0].submit();
	}
};

	var dtCh= "/";
	var minYear=1900;
	var maxYear=9999;
	function isInteger(s)
	{
		var i;
    	for(i = 0; i < s.length; i++)
    	{   
        // Check that current character is number.
        	var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    	}
    	// All characters are numbers.
    	return true;
	}

	function stripCharsInBag(s, bag)
	{
		var i;
   		 var returnString = "";
    	// Search through string's characters one by one.
    	// If character is not in bag, append to returnString.
    	for (i = 0; i < s.length; i++)
    	{   
        	var c = s.charAt(i);
        	if (bag.indexOf(c) == -1) returnString += c;
    	}
    	return returnString;
	}

	function daysInFebruary (year)
	{
		// February has 29 days in any year evenly divisible by four,
    	// EXCEPT for centurial years which are not also divisible by 400.
    	return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
	}
	function DaysArray(n) 
	{
		for (var i = 1; i <= n; i++) 
		{
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
		document.forms[0].validation.value= " The date format should be : dd/mm/yyyy ";
		return false;
	}
	if (strMonth.length<1 || month<1 || month>12){
		document.forms[0].validation.value= " Please enter a valid month ";
		return false;
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		document.forms[0].validation.value= " Please enter a valid day as this year is not a leap year ";
		return false;
		
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		document.forms[0].validation.value=" Please enter a valid 4 digit year between "+minYear+" and "+maxYear+" ";
		return false;
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		document.forms[0].validation.value= " Please enter a valid date ";
		return false;
	}
return true;
};

function properdate(from_date,to_date){
  
  
  var dtCh="/";
   
  var pos1=from_date.indexOf(dtCh)
  var pos2=from_date.indexOf(dtCh,pos1+1)
  var frmMonth=from_date.substring(pos1+1,pos2)
  var frmDay=from_date.substring(0,pos1)
  var frmYear=from_date.substring(pos2+1)
  
  
  var pos3=to_date.indexOf(dtCh)
  
  var pos4=to_date.indexOf(dtCh,pos3+1)
  
  var ToMonth=to_date.substring(pos3+1,pos4)
  
  var ToDay=to_date.substring(0,pos3)
  
  var ToYear=to_date.substring(pos4+1)
  
  
  
  if(frmYear > ToYear)
  {
    document.forms[0].validation.value= " From Year is greater than To Year!!!! Enter valid date ";
  }
  else if(frmMonth > ToMonth && frmYear<=ToYear )
  {
    document.forms[0].validation.value= " From Month is greater than To Month !!!! Enter valid date ";
  }
  else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear)
  {
  	document.forms[0].validation.value=" From day is greater than To day " ;
  }
 }; 
 


function ValidateForm(){
   
	var dt=document[0].from_date;
	
	if (isDate(dt.value)==false){
	       	
		return false;
	}
	
	
    return true;
 };
 
 function onlynumbers(){
        	
        	var cha =   event.keyCode;
			            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        };
function validations()
{
	if(document.getElementById("valid").value!=null)
	{
		if(document.getElementById("valid").value=="norecords")
		{
			document.forms[0].validation.value=" Records Not Found ";
			return false;
		}
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
</script>
<center><h2 class="h2">Control Number Details</h2></center>
<body class="Mainbody" onload="validations()">
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
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/Controlno?pageId=7026">

<table class="txtTable">
	<tr>
	<td>
	<table>
	<tr><html:text property="validation" styleId="valid" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"/></tr>
	</table>
	</td>
	</tr>
	<tr>
	<td>	
	<table>
	
	<tr>
		<td>
			<bean:message key="label.controlno"/><bean:message key="label.from"/>
			<html:text property="from_date" size="12" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" ></html:text>
		</td>
		
		<td><bean:message key="label.to"/>
			<html:text property="to_date" size="12" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()"></html:text></td>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
		<td><html:button styleClass="ButtonBackgroundImage" onclick="button_View('View')" property="vir" value="view">View</html:button></td>
		<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
		<!--<td><html:submit styleClass="ButtonBackgroundImage" onclick="button_View('Store')">StoreFile</html:submit></td>
		-->
		
		<td><html:submit styleClass="ButtonBackgroundImage" onclick="callClear()">Clear</html:submit></td>
		
	</tr>
	</table>
	</td>
	</tr>
	<tr>
			<display:table name="chequelist" id="chequelist" pagesize="10" class="its" requestURI="/Clearing/Controlno.do">
			
			<display:column title="Clg Dt"  style="width:3%;"  maxLength="50"><core:out value="${chequelist.cldate}"/></display:column>
			
			<display:column title="Clg No"  style="width:3%;"  maxLength="50"><core:out value="${chequelist.clrno}"/></display:column>
			
			<display:column title="Send To"  style="width:3%;"  maxLength="50"><core:out value="${chequelist.sendto}"/></display:column>
			
			<display:column title="Ctrl No" style="width:3%;"  maxLength="50"><core:out value="${chequelist.ctrlno}"/></display:column>
			
			<display:column title="Trn Type" style="width:3%;"  maxLength="50"><core:out value="${chequelist.trntype}"/></display:column>
			
			<display:column title="Clg Type" style="width:3%;"  maxLength="50"><core:out value="${chequelist.clrtype}"/></display:column>
			
			<display:column title="ack no" style="width:3%;"  maxLength="50"><core:out value="${chequelist.ackno}"/></display:column>
			
			
			<display:column title="Multi Credit" style="width:3%;"  maxLength="50"><core:out value="${chequelist.mlcredit}"/></display:column>
			
			<display:column title="Company" style="width:3%;"  maxLength="50"><core:out value="${chequelist.cmpname}"/></display:column>
			
			<display:column title="Q/D/P" style="width:3%;"  maxLength="50"><core:out value="${chequelist.chqddpo}"/></display:column>
			
			<display:column title="Q/D/P no" style="width:3%;"  maxLength="50"><core:out value="${chequelist.qdpno}"/></display:column>
			
			<display:column title="Q/D/P Dt" style="width:3%;"  maxLength="50"><core:out value="${chequelist.qdpdate}"/></display:column>
			
			<display:column title="ret_normal" style="width:3%;"  maxLength="50"><core:out value="${chequelist.retnormal}"/></display:column>
			
			<display:column title="prev ctrl no" style="width:3%;"  maxLength="50"><core:out value="${chequelist.prevctrlno}"/></display:column>
			
			
			<display:column title="Bank" style="width:3%;"  maxLength="50"><core:out value="${chequelist.bkcode}"/></display:column>
			
			<display:column title="Branch" style="width:3%;"  maxLength="50"><core:out value="${chequelist.brname}"/></display:column>
			
			<display:column title="trn_amt" style="width:3%;"  maxLength="50"><core:out value="${chequelist.trnamt}"/></display:column>
			
			<display:column title="Bounce" style="width:3%;"  maxLength="50"><core:out value="${chequelist.tobounce}"/></display:column>
			
			
			<display:column title="fine amt" style="width:3%;"  maxLength="50"><core:out value="${chequelist.fineamt}"/></display:column>
			
			
			
			</display:table>
		</tr>
		</table>
		
<html:hidden property="but_value"/>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>