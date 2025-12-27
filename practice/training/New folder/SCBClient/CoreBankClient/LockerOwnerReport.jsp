<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="c"     uri="/WEB-INF/c-rt.tld"  %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="masterObject.lockers.LockerMasterObject" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
    
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
function only_numbers_date()
	{
		var cha =   event.keyCode;
		 if (cha >=47 && cha <=57) 
		 {
   		 	return true;
          } 
          else 
          {
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
         		alert("only "+size+" digits allowed")
         		txt.value="";
         		return false;
          	}
         }

function storeFile(target)
{

document.forms[0].flag.value=target;
document.forms[0].submit();

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
	
	if (pos1==-1 || pos2==-1){
		document.forms[0].validateFlag.value="The date format should be : dd/mm/yyyy";
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		document.forms[0].validateFlag.value="Please enter a valid month";
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		document.forms[0].validateFlag.value="Please enter a valid day as this year is not a leap year";
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		document.forms[0].validateFlag.value="Please enter a valid 4 digit year between "+minYear+" and "+maxYear+"";
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		document.forms[0].validateFlag.value="Please enter a valid date";
		return false
	}
return true
}

 function datevalidation(ids){
    	var format = true;
        var allow=true;
    	var ff=ids.value;
    	var dd=ff.split('/');
    	
    	var ss=document.forms[0].sysdate.value;
    	var dds=ss.split('/');
    	
    	if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          alert (  " Date format should be:DD/MM/YYYY" );
                         ids.value="";
                          format = false ;
                          allow=false;
                          

                      } else if (  i==2 && dd[i].length != 4 ){

                           alert (  " Date format should be:DD/MM/YYYY " );
                          ids.value="";
                           format = false ;
                           allow=false;
                      }

            	 }
             } else{
                    allow=false;
             		format = false ;
             		alert (  " Date format should be:DD/MM/YYYY " );
             		document.forms[0].fdate.value="";
             }
            if(allow){
            	
            	var day=dd[0];
            	var month=dd[1];
            	var year=dd[2];
            	var fdays;
            	if(month==2)
            	{
            	if((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0)))
            	{
            		if(day>29)
            		{
            			alert("Days should be less than 29 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	else
            	{
            		if(day>28)
            		{
            			alert("Days should be less than 28 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	}
            	
            	if(month>1 || month<12){
            	if(month == 4 || month==6||month==9||month==11)
            	{
            		if(day>30)
            		{
            			alert("Days should be less than 31 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	else
            	{
            		if(day>31)
            		{
            			alert("Days should be less than 32 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            		
            	}
            	}
            	if(month>12)
            	{
            		alert("Month should  be greater than 0 and lesser than 13");
            		ids.value="";
                           format = false ;
                           allow=false;
            	}
            	
            	if(year<1900 || year>9999)
            	{
            		alert("Year should be in between 1900 to 9999 ");
            		ids.value="";
                           format = false ;
                           allow=false;
            	}
            	if(dd[0].length==2||dd[1].length==2||dd[2].length==4)
            	{
            		if(dd[2]>dds[2])
            		{
            			alert(" year should be less than or equal to"+dds[2]+" !");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}	
            		else{
            			if(dd[2]==dds[2]){
            				if(dd[1]>dds[1]){
	            				alert(" Month should be less than or equal to"+dds[1]+" !");
	            				ids.value="";
	                           	format = false ;
	                          	 allow=false;		
            						
            				}else{
            					if(dd[1]==dds[1]){
            						if(dd[0]>dds[0]){
											alert(" Day should be less than or equal to"+dds[0]+" !");
				            				ids.value="";
				                           	format = false ;
				                          	allow=false;           							
            						}else{
            								format = true ;
				                          	allow=true; 
            						}
            					
            					}
            				}
            			}
            		}
            	}            	          	
             }
        }
    



 


</script>
</head>
<body bgcolor="#f3f4c8" class="Mainbody">
<center><h2 class="h2"><bean:message key="lable.ownerReport"></bean:message></h2></center>

<%@page import="java.util.Map"%>


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
<html:form action="/LockerOwnerReportLink?pageId=9012">

<%!LockerMasterObject[] reportobject; %>
                                                                  
<% reportobject=(LockerMasterObject[])request.getAttribute("details");  %>

<% if(reportobject!=null){
	for(int i=0;i<reportobject.length;i++){

	System.out.println("--Key Num--"+reportobject[i].getAllotDate()+"--LockerAcType--"+reportobject[i].getNomRegNo()+"--LockerType--"+reportobject[i].getMemberNo());
}}
%>
          
<html:hidden property="flag"></html:hidden>
<html:hidden property="sysdate" />

<table>
<tr><html:text property="validateFlag" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
	
<tr>
<td>
<table class="txtTable">
<tr>
<td><bean:message key="label.date"></bean:message></td> <td><html:text property="fromDate" value="<%=(String)request.getAttribute("date")%>" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td></tr>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<tr><td><html:submit styleClass="ButtonBackgroundImage"><bean:message key="label.view"></bean:message></html:submit></td>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

<td><html:button property="store" styleClass="ButtonBackgroundImage" onclick="storeFile('File')"><bean:message key="label.file"></bean:message></html:button></td>
</tr>


</table>

</td>
</tr>
</table>

<display:table name="details" style="background-color:#E4D5BE" pagesize="8" requestURI="/LockerOwnerReportLink.do" id="currentRowObject" class="its" sort="list">
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 
   
<display:column property="keyNo"		 style="background-color:#F2F0D2"></display:column>
<display:column property="lockerAcType"  style="background-color:#F2F0D2"></display:column>
<display:column property="lockerType"	 style="background-color:#F2F0D2"></display:column>
<display:column property="lockerNo" 	 style="background-color:#F2F0D2"></display:column>
<display:column property="oprInstrn"     style="background-color:#F2F0D2"></display:column>
<display:column property="reqdMonths"    style="background-color:#F2F0D2"></display:column>
<display:column property="requiredDays"  style="background-color:#F2F0D2"></display:column>
<display:column property="rentUpto"		 style="background-color:#F2F0D2"></display:column>
<display:column property="memberType"  	 style="background-color:#F2F0D2"></display:column>
<display:column property="memberNo" 	 style="background-color:#F2F0D2"></display:column>
<display:column property="rentBy" 		 style="background-color:#F2F0D2"></display:column>
</display:table>


</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>