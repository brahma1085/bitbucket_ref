<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>   
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.DividendRateObject"%>
<html>
<head>
 <title>Dividend Calculation</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css"/>
      <h2 class="h2">
      <center>Dividend Calculation</center></h2>
      <hr>
      
    
    <script type="text/javascript">
     
     
     function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
     
     function set(target){
       document.forms[0].forward.value=target
        };
        
    function validfn(fnm,str)
    {
      var len=fnm.length;
      if(len==0){
        alert("Enter the "+str )
       }
    };
     
   function fun(shnum)
   {
       alert(shnum)
   };
       
   function divrate()
   {
        alert("Share Div Rate Stored Successfully")
   };
       
  function numbersonly(eve)
  {
    var cha = event.keyCode
    if (  ( cha  >= 47 && cha < 58  ) ) 
    {
        return true ;
       
    }
    else
    {
      alert("Alphabets are not allowed,Please enter numbers only ");
      return false ;
    }
 };
     
  function floatonly(eve)
  {
    var cha = event.keyCode
    if ((cha>=48 && cha< 58)|| (cha==46)) 
    {
      return true ;
    }
    else
    {
     alert("Alphabets are not allowed,Please enter numbers only ");
     return false ;
    }
 };
     
 function clearMethod()
 {
  
   var ele=document.forms[0].elements;
   var count=ele.length;
   
   for(var i=0;i<ele.length;i++){
      if(ele[i].type=="text"){
       	ele[i].value="";
       	}
    }	
 };   
     
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

function ValidateForm(){
   
	var dt=document[0].from_dt;
	var dt1=document[0].to_dt;
	if (isDate(dt.value)==false){
	       	
		return false
	}
	if (isDate(dt1.value)==false){
	       	
		return false
	}
	
    return true
 }
 
 
 function properdate(from_dt,to_dt){
  
  
  var dtCh="/";
   
  var pos1=from_dt.indexOf(dtCh)
  var pos2=from_dt.indexOf(dtCh,pos1+1)
  var frmMonth=from_dt.substring(pos1+1,pos2)
  var frmDay=from_dt.substring(0,pos1)
  var frmYear=from_dt.substring(pos2+1)
  
  
  var pos3=to_dt.indexOf(dtCh)
  
  var pos4=to_dt.indexOf(dtCh,pos3+1)
  
  var ToMonth=to_dt.substring(pos3+1,pos4)
  
  var ToDay=to_dt.substring(0,pos3)
  
  var ToYear=to_dt.substring(pos4+1)
  
  
  
  if(frmYear>ToYear){
    alert("From Year is greater than To Year!!!! Enter valid date")
  }
  else if(frmMonth>ToMonth && frmYear<=ToYear ){
    alert("From Month is greater than To Month !!!! Enter valid date")
  }
 else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  alert("From day is greater than To day !!!! Enter valid date")
  }
 }; 
     
     
    </script>


  </head>
<body class="Mainbody">
<%!
   Integer res=0;
   DividendRateObject[] divobj;
   
%>
<% 
String message=(String)request.getAttribute("msg");
divobj=(DividendRateObject[])request.getAttribute("divrate"); %>
  <%if(message!=null&&message.length()>0){%>
    <font color="red"><%=message %></font>
    <%}%>
    <br><br>
 
 <center> 
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
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
  <table style="border:thin solid #339999;" class="txtTable">
  
    <html:form action="/Share/DividendCal?pageId=4003" focus="<%=""+request.getAttribute("focusto")  %>">
    
       <tr>
         <td><bean:message key="label.frm_dt"></bean:message></td>
         <td><html:text property="from_dt"  styleClass="formTextFieldWithoutTransparent" size="10" onblur="ValidateForm()" onkeypress=" return numbersonly()" onkeyup="numberlimit(this,'11')"></html:text></td>
       </tr>
      
       <tr>
        <td><bean:message key="label.to_dt"></bean:message></td>
        <td><html:text property="to_dt" styleClass="formTextFieldWithoutTransparent" size="10" onblur="ValidateForm()" onkeypress="return numbersonly()" onchange="submit()"  onkeyup="numberlimit(this,'11')"></html:text></td>
       </tr>
       
       <tr>
         <td><bean:message key="label.divrate"></bean:message></td>
         <core:choose>
          <core:when test="${empty requestScope.divrate}">
          <td><html:text property="setrate" styleClass="formTextFieldWithoutTransparent" size="10" onblur="validfn(setrate.value,'Div Rate')" onkeypress="return floatonly()"></html:text></td>
          
          </core:when>
         <core:otherwise>
         <td><html:text property="setrate" styleClass="formTextFieldWithoutTransparent" size="10" value="<%=""+divobj[0].getRate()%>" onkeypress="return floatonly()" readonly="true"></html:text></td>                   
         </core:otherwise> 
         </core:choose>
         
       </tr>
       
       <tr>
        <td><bean:message key="label.drfamt"></bean:message></td>
        <core:choose>
        <core:when test="${empty requestScope.divrate}">
        <td><html:text property="drfamt" styleClass="formTextFieldWithoutTransparent" size="10" onblur="validfn(drfamt.value,'DRF Amount')" onkeypress="return floatonly()"></html:text></td>
        </core:when>
        <core:otherwise>
          <td><html:text property="drfamt" styleClass="formTextFieldWithoutTransparent" size="10" value="<%=""+divobj[0].getAmount() %>" onkeypress="return floatonly()" readonly="true"></html:text></td>
        </core:otherwise>
        </core:choose>
        
       </tr>
     
       <tr>
        <html:hidden property="forward" value="error"></html:hidden>
        <%
          res=(Integer)request.getAttribute("Divrate");
          System.out.println("The result n jsp is"+res);
        %>
        
        <td><html:submit property="butt_setrate" onclick="set('Set Rate')" styleClass="ButtonBackgroundImage"><bean:message key="label.setrate"></bean:message></html:submit></td>        
        <td><html:submit property="butt_cal" onclick="set('Calculate')" styleClass="ButtonBackgroundImage"><bean:message key="label.cal" ></bean:message></html:submit></td>
        <td><html:submit property="butt_recal" onclick="set('Re-Calculate')" styleClass="ButtonBackgroundImage"><bean:message key="label.recal" ></bean:message></html:submit></td>
        <td><html:reset property="butt_clear" onclick="clearMethod()" styleClass="ButtonBackgroundImage"><bean:message key="label.clear" ></bean:message></html:reset></td>
        
       </tr>
     
     
    </html:form>
    
  </table>
   <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </center>
</body>
</html>