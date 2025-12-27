<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<%@taglib prefix="display" uri="/WEB-INF/displaytag.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareMasterObject"%>
<html>
<head><title>Convertion from Temporary to Permenant Shares</title>
      <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
    
      <h2 class="h2">
      <center>Convertion from Add.Temporary to Permanent Shares</center></h2>
      <hr>
      
          
    
    

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
   
	var dt=document[0].frm_date;
	var dt1=document[0].to_date;
	if (isDate(dt.value)==false){
	       	
		return false
	}
	if (isDate(dt1.value)==false){
	       	
		return false
	}
	
    return true
 }
 
 function set(target){
       document.forms[0].forward.value=target
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
    alert("From Year is greater than To Year!!!! Enter valid date")
  }
  else if(frmMonth>ToMonth && frmYear<=ToYear ){
    alert("From Month is greater than To Month !!!! Enter valid date")
  }
 else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  alert("From day is greater than To day !!!! Enter valid date")
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
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
         
         function only_numbers_date() {
	
	var cha=event.keyCode;
	if(cha>=47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  

       
</script>
 
  </head>
<body class="Mainbody">
<%!
   ShareMasterObject[] sh_master;
   
%>
<%
   //sh_master=(ShareMasterObject[])request.getAttribute("tempperm");
%>
<html:form action="/Share/ConvertTempAdd?pageId=4008" focus="<%=""+request.getAttribute("focusto") %>">
 <table class="txtTable">
   
    <tr>
    <td><bean:message key="label.frm_dt"></bean:message>
     <html:text property="frm_date" styleClass="formTextFieldWithoutTransparent" size="10"  value="<%=""+request.getAttribute("date") %>" onblur="ValidateForm()" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_date()"></html:text></td>
 
     <td><bean:message key="label.to_dt"></bean:message> 
     <html:text property="to_date" styleClass="formTextFieldWithoutTransparent" size="10"  value="<%=""+request.getAttribute("date") %>" onblur="ValidateForm()"  onchange="properdate(frm_date.value,to_date.value)" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_date()"> </html:text></td>
    </tr> 
     <tr>
     <td>
     <html:hidden property="forward"></html:hidden>
      
     </td>
  </tr>
   
   </table>
  <display:table name="tempperm" id="tempperm" class="its" list="${tempperm}">

		<display:column title="${checkAll}" style="width:1%"><input type="checkbox" name="id" value="${tempperm.id}" style="margin:0 0 0 4px" onclick="radio(this)" <core:if test="${param.id==tempperm.id and param.method != 'Save' and param.method != 'Clear' and  param.method != 'Reset'}">checked="checked"</core:if> /></display:column>
		<display:column style="width:3%;text-align: right;" title="Trn.No">
			<core:choose>
				<core:when test="${param.method=='Save' and param.id==tempperm.id }">
					<input type="text" name="Shno"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.ShareNo}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${tempperm.ShareNo}"></core:out>
				</core:otherwise>
			</core:choose>
	    </display:column>
	    
	    <display:column style="width:3%;text-align: right;" title="Ac.No">
			<core:choose>
				<core:when test="${param.method=='Save' and param.id==tempperm.id }">
					<input type="text" name="Ac_no"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.Ac_no}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${tempperm.Ac_no}"></core:out>
				</core:otherwise>
			</core:choose>
	    </display:column>
	    
	    
	    
	    <display:column style="width:3%;text-align: right;" title="Name ">		
		<core:choose>
		  <core:when test="${param.method=='Save' and param.id==tempperm.id}">
		     <input type="text" name="Name"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.Name}"/>
		  </core:when>
		 <core:otherwise>
		   <core:out value="${tempperm.Name}"></core:out>
		 </core:otherwise>
	  </core:choose>	
	</display:column>
	
	<display:column style="width:3%;text-align: right;" title="CID ">		
		<core:choose>
		  <core:when test="${param.method=='Save' and param.id==tempperm.id}">
		     <input type="text" name="cid"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.cid}"/>
		  </core:when>
		 <core:otherwise>
		   <core:out value="${tempperm.cid}"></core:out>
		 </core:otherwise>
	  </core:choose>	
	</display:column>
	
	
	
	<display:column style="width:3%;text-align: right;" title="No Of Shares">
	  <core:choose>
	    <core:when test="${param.method=='Save' and param.id==tempperm.id}">
	      <input type="text" name="NoOfShares"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.NumofShare}"/>
	    </core:when>
	   <core:otherwise>
	     <core:out value="${tempperm.NumofShare}"></core:out>
	   </core:otherwise> 
	  </core:choose>	
    </display:column>	
    
   <display:column style="width:3%;text-align: right;" title="ShareValue">
     <core:choose>
       <core:when test="${param.method=='Save' and param.id==tempperm.id}">
         <input type="text" name="ShareValue"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.ShareValue}"/>
       </core:when>
      <core:otherwise>
        <core:out value="${tempperm.ShareValue}"></core:out>
      </core:otherwise> 
     </core:choose>
   </display:column> 
   
   <display:column style="width:3%;text-align: right;" title="Branch Code">
     <core:choose>
       <core:when test="${param.method=='Save' and param.id==tempperm.id}">
         <input type="text" name="BranchCode"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.BranchCode}"/>
       </core:when>
      <core:otherwise>
        <core:out value="${tempperm.BranchCode}"></core:out>
      </core:otherwise> 
     </core:choose>
   </display:column> 
   	
   	<display:column style="width:3%;text-align: right;" title="Sh Cat ">		
		<core:choose>
		  <core:when test="${param.method=='Save' and param.id==tempperm.id}">
		     <input type="text" name="ShCat"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.ShCat}"/>
		  </core:when>
		 <core:otherwise>
		   <core:out value="${tempperm.ShCat}"></core:out>
		 </core:otherwise>
	  </core:choose>	
	</display:column>
	
	<display:column style="width:3%;text-align: right;" title="Issue Date ">		
		<core:choose>
		  <core:when test="${param.method=='Save' and param.id==tempperm.id}">
		     <input type="text" name="IssueDate"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.IssueDate}"/>
		  </core:when>
		 <core:otherwise>
		   <core:out value="${tempperm.IssueDate}"></core:out>
		 </core:otherwise>
	  </core:choose>	
	</display:column>
		</display:table>
  
  <table>
  <tr>
   <td> 
    <input type="submit" value="Reset" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Reset'" />
	
	<input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="Save" name="method"/>
	<input type="submit" value="Clear" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Clear'"/>

	</td>
	</tr>
   </table>
 
 
</html:form>

</body>
</html>