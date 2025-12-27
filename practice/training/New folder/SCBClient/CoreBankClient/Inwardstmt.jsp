<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
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

function button_view(target)
{

	if(document.forms[0].bank_name.value=='')
	{
		document.forms[0].validation.value="Enter Bank Name";
	}
	else if(document.forms[0].clr_date.value=='')
	{
		document.forms[0].validation.value="Enter Clearing Date";
	}
	else if(document.forms[0].clr_no.value=='')
	{
		document.forms[0].validation.value="Enter Clearing No";
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
     document.forms[0].submit();     
 }





	var dtCh= "/";
	var minYear=1900;
	var maxYear=9999;
function isInteger(s)
{
	var i;
    for (i = 0; i < s.length; i++)
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
    for (i=0;i< s.length;i++)
    {   
        var c = s.charAt(i);
        if(bag.indexOf(c)==-1) returnString += c;
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
	for(var i=1;i<=3;i++) 
	{
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	
	if(pos1==-1 || pos2==-1)
	{
		document.forms[0].validation.value="The date format should be : dd/mm/yyyy";
		document.forms[0].clr_date.focus();
		return false;
	}
	if(strMonth.length<1 || month<1 || month>12)
	{
		document.forms[0].validation.value="Please enter a valid month";
		document.forms[0].clr_date.focus();
		return false;
	}
	if(strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month])
	{
		document.forms[0].validation.value="Please enter a valid day as this year is not a leap year";
		document.forms[0].clr_date.focus();
		return false;
	}
	if(strYear.length != 4 || year==0 || year<minYear || year>maxYear)
	{
		document.forms[0].validation.value="Please enter a valid 4 digit year between "+minYear+" and "+maxYear+" ";
		document.forms[0].clr_date.focus();
		return false;
	}
	if(dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false)
	{
		document.forms[0].validation.value="Please enter a valid date";
		document.forms[0].clr_date.focus();
		return false;
	}
return true;
};

function properdate(clr_date,to_date)
{
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
  
  if(frmYear>ToYear)
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
 
function storeFile(target)
{

	if(document.forms[0].bank_name.value=='')
	{
		document.forms[0].validation.value="Enter Bank Name";
	}
	else if(document.forms[0].clr_date.value=='')
	{
		document.forms[0].validation.value="Enter Clearing Date";
	}
	else if(document.forms[0].clr_no.value=='')
	{
		document.forms[0].validation.value="Enter Clearing No";
	}
	else
	{
		document.forms[0].but_value.value=target;
		document.forms[0].submit();
	}

}

function ValidateForm()
{
   
	var dt=document[0].clr_date;
	
	if(isDate(dt.value)==false)
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
  
 function setPrint(flagVal)
 {
 	
 	document.forms[0].but_value.value=flagVal;
 	document.forms[0].submit();
 } 
      

</script>

<body>

<center><h2 class="h2">Inward Statement</h2></center>

<%String clrdate=(String)request.getAttribute("clrdate"); %>

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
<html:form action="/Clearing/Inwardstmt?pageId=7022">

<table class="txtTable">
	<tr>
	<td>
	<table>
	<tr><html:text property="validation" styleId="valid" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
	</table>
	</td>
	</tr>
	<tr>
	<td>
	<table>
	<tr>
		<td><bean:message key="label.bank"/>
		<html:text size="10" property="bank_name" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()"></html:text></td>
	
	
		<td><bean:message key="label.clr_date"/>
		<%if(clrdate!=null){ %>
		<html:text size="10" property="clr_date" styleClass="formTextFieldWithoutTransparent" value="<%=clrdate%>" onblur="return ValidateForm()"></html:text></td>
		<%} %>
	

		<td><bean:message key="label.clr_no"/>
		<html:text size="10" property="clr_no" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()"></html:text></td>
		</tr>
		<html:hidden property="but_value"/>
		<tr>
		<td>
		<table class="txtTable">
		<tr>
			<TD>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
			<html:button property="view" styleClass="ButtonBackgroundImage" onclick="button_view('View')" value="View">View</html:button>
			<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
				
			 	<html:button property="store" styleClass="ButtonBackgroundImage" onclick="storeFile('Store')">StoreFile</html:button>  
				<html:button property="print" styleClass="ButtonBackgroundImage" onclick="setPrint('print')">Print</html:button>
				<html:button property="reprint" styleClass="ButtonBackgroundImage" onclick="callClear()"><bean:message key="label.clear"></bean:message></html:button>  
			
			</TD>
		</tr>
	</table>
	</td>
	</tr>
	</table>
	</td>
	</tr>
	       <display:table name="inward_list" id="inward_list" pagesize="10" requestURI="/Clearing/Inwardstmt.do" class="its">
				<display:column title="Srl No" style="width:3%;"  maxLength="50"><core:out value="${inward_list.srlno}"/></display:column>
				<display:column title="Ctrl No" style="width:3%;"  maxLength="50"><core:out value="${inward_list.ctrlno}"/></display:column>
				<display:column title="CLG/TRF" style="width:3%;"  maxLength="50"><core:out value="${inward_list.clgtype}"/></display:column>
				<display:column title="Bdl/Sgl" style="width:3%;"  maxLength="50"><core:out value="${inward_list.docbs}"/></display:column>
				<display:column title="Ack No" style="width:3%;"  maxLength="50"><core:out value="${inward_list.ackno}"/></display:column>
				<display:column title="No Of Docs" style="width:3%;"  maxLength="50"><core:out value="${inward_list.nofdocs}"/></display:column>
				<display:column title="Amount" style="width:3%;"  maxLength="50"><core:out value="${inward_list.trnamt}"/></display:column>
				<display:column title="Source" style="width:3%;"  maxLength="50"><core:out value="${inward_list.srcname}"/></display:column>
				<display:column title="Destination" style="width:3%;"  maxLength="50"><core:out value="${inward_list.destname}"/></display:column>
				<display:column title="Q/D/P" style="width:3%;"  maxLength="50"><core:out value="${inward_list.chqdpo}"/></display:column>
				<display:column title="QDP No" style="width:3%;"  maxLength="50"><core:out value="${inward_list.qdpno}"/></display:column>
				<display:column title="QDP Date" style="width:3%;"  maxLength="50"><core:out value="${inward_list.qdpdt}"/></display:column>
				<display:column title="Dr.A/C" style="width:3%;"  maxLength="50"><core:out value="${inward_list.mabbrv}"/></display:column>
				<display:column title="Dr A/C name" style="width:3%;"  maxLength="50"><core:out value="${inward_list.cmpname}"/></display:column>
				<display:column title="Payee" style="width:3%;"  maxLength="50"><core:out value="${inward_list.payeename}"/></display:column>
				<display:column title="Bank" style="width:3%;"  maxLength="50"><core:out value="${inward_list.bkname}"/></display:column>
				<display:column title="Branch" style="width:3%;"  maxLength="50"><core:out value="${inward_list.brname}"/></display:column>
				<display:column title="O/W Ctl No" style="width:3%;"  maxLength="50"><core:out value="${inward_list.prevctrlno}"/></display:column>
				<display:column title="DE Usr/Tml" style="width:3%;"  maxLength="50"><core:out value="${inward_list.deuser}"/></display:column>
				<display:column title="VE Usr/Tml" style="width:3%;"  maxLength="50"><core:out value="${inward_list.veuser}"/></display:column>
			</display:table>
				
   </table>
   
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>