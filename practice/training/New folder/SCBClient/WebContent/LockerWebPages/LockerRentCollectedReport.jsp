<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.lockers.LockerMasterObject"%>
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

function cle_fun()
{
	
	
	var ele=document.forms[0].elements;
	for(var i=0;i<ele.length;i++)
	{
		if(ele[i].type=="text")
		{
			ele[i].value="";
		}
	}

}

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
         function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  >= 47 && cha < 58  ) ) {
				return true ;
                
            	}
            else{
              	alert("Alphabets are not allowed!!!!!!!Please enter numbers only ");
              	return false ;
            }
        };  

     



 

        
 function properdate(frm_dt,to_dt){
  
  
  var dtCh="/";
   
  var pos1=frm_dt.indexOf(dtCh)
  var pos2=frm_dt.indexOf(dtCh,pos1+1)
  var frmMonth=frm_dt.substring(pos1+1,pos2)
  var frmDay=frm_dt.substring(0,pos1)
  var frmYear=frm_dt.substring(pos2+1)
  
  
  var pos3=to_dt.indexOf(dtCh)
  
  var pos4=to_dt.indexOf(dtCh,pos3+1)
  
  var ToMonth=to_dt.substring(pos3+1,pos4)
  
  var ToDay=to_dt.substring(0,pos3)
  
  var ToYear=to_dt.substring(pos4+1)
  
  
  
  if(frmYear>ToYear){
    document.forms[0].validateFlag.value="From Year is greater than To Year!!!! Enter valid date";
  }
  else if(frmMonth>ToMonth && frmYear<=ToYear ){
    document.forms[0].validateFlag.value="From Month is greater than To Month !!!! Enter valid date";
  }
 else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  document.forms[0].validateFlag.value="From day is greater than To day !!!!  Enter valid date";
  }
 };




function storeFile(target)
{
	document.forms[0].flag.value=target;
	document.forms[0].submit();

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


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="#f3f4c8" class="Mainbody"  style="overflow: scroll;">
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
<CENTER><h2 class="h2"><bean:message key="lable.rentCollected"></bean:message></h2> </CENTER> 
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/LockerRentCollectedReportLink?pageId=9016">
<%! LockerMasterObject[] repObj; %>
<% repObj=(LockerMasterObject[])request.getAttribute("details"); %>

<html:hidden property="flag"></html:hidden>
<html:hidden property="sysdate" />
<table>
<tr><html:text property="validateFlag" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>

<tr>
<td>
<table class="txtTable">
<tr>
<td><bean:message key="label.frm_dt"></bean:message></td>  <td><html:text property="fromDate" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent" value="<%=(String)request.getAttribute("fromDate")%>" onblur="return datevalidation(this)"></html:text></td>
<td><bean:message key="label.to_dt" ></bean:message></td>    <td><html:text property="toDate" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"  value="<%=(String)request.getAttribute("toDate")%>" onblur="return datevalidation(this)" onchange="properdate(fromDate.value,toDate.value)"></html:text></td>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<tr><td> <html:submit styleClass="ButtonBackgroundImage"> <bean:message key="label.view1"></bean:message> </html:submit> </td>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
	<td> <html:button property="store" styleClass="ButtonBackgroundImage" onclick="storeFile('File')"><bean:message key="label.file"></bean:message></html:button> </td>
	<td> <html:button property="butt" onclick="cle_fun()" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message> </html:button>  </td>
</tr>


</table>

</td>
</tr>
</table>   
    
<display:table name="details"  id="currentRowObject" class="its" sort="list" pagesize="8" requestURI="/LockerRentCollectedReportLink.do" style="background-color:#E4D5BE">
<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   	<display:column media="csv excel" title="URL" property="url" />
<display:setProperty name="export.pdf" value="true" /> 
  
<display:column property="lockerAcType"  style="background-color:#F2F0D2"></display:column>
<display:column property="lockerAcNo" 		style="background-color:#F2F0D2"></display:column>
<display:column property="name"		style="background-color:#F2F0D2"></display:column>
<display:column property="lockerType"  style="background-color:#F2F0D2"></display:column>
<display:column property="lockerNo"  style="background-color:#F2F0D2"></display:column>
<display:column property="memberType"  style="background-color:#F2F0D2"></display:column>
<display:column property="memberNo" style="background-color:#F2F0D2"></display:column>
<display:column property="rentBy" style="background-color:#F2F0D2"></display:column>
<display:column property="rentUpto" style="background-color:#F2F0D2"></display:column>
<display:column property="rentColl" style="background-color:#F2F0D2"></display:column>
</display:table>


</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>