<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Enumeration"%>
<%@page import="masterObject.share.DividendObject"%>
<html>
<head><title>Dividend Report</title>
     <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
      
     
      <h2 class="h2">
      <center>Dividend Report</center></h2>
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
     function set(target){
       document.forms[0].forward.value=target
        };
     																																					
     
           function validfn(fnm,str){
             var len=fnm.length;
             if(len==0){
               alert("Enter the "+str )
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
   
	var dt=document[0].frm_dt;
	var dt1=document[0].to_dt;
	if (isDate(dt.value)==false){
	       	
		return false
	}
	if (isDate(dt1.value)==false){
	       	
		return false
	}
	
    return true
 }
 

        
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
       
       function numbersonly(eve){
         var cha = event.keyCode


            if (  ( cha  > 47 && cha < 58  ) ) {


              
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
         
        
      };
         
    </script>


  </head>

<body class="Mainbody">
<%!
   Hashtable hash_div;
   DividendObject[] div;
%>

<%
   div=(DividendObject[])request.getAttribute("div_report");
%>
<html:form action="/Share/DividendReport?pageId=4031">
<table class="txtTable">
 <tr>
  <td>
   <bean:message key="label.actype"></bean:message>
  </td>
  <td>
   <html:select property="actype" styleClass="formTextFieldWithoutTransparent">
    <html:option value="1001001">SH</html:option>
   </html:select>
   
 </td>
 </tr>
 <tr>
   <td><bean:message key="label.acNum"></bean:message></td>
       <td><html:text property="acno" styleClass="formTextFieldWithoutTransparent" size="6" onblur="validfn(acno.value,'Share Number')" onchange="submit()" onkeypress="return numbersonly()"></html:text></td>
 </tr>
   
    <tr>
    <td><bean:message key="label.frm_dt"></bean:message></td>
     <td><html:text property="frm_dt" styleClass="formTextFieldWithoutTransparent" size="10" value="<%=""+request.getAttribute("date")%>" onblur="ValidateForm()" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_date()"></html:text></td>
  </tr>
 
 <tr>
    <td><bean:message key="label.to_dt"></bean:message></td>
     <td><html:text property="to_dt" styleClass="formTextFieldWithoutTransparent" size="10" value="<%=""+request.getAttribute("date")%>" onblur="ValidateForm()" onchange="properdate(frm_dt.value,to_dt.value)" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_date()"></html:text></td>
     
     <td><bean:message key="label.paymentmode"></bean:message></td>
          <td><html:select property="pay_type" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
     <%hash_div=(Hashtable)request.getAttribute("Paymode");%>
     <% System.out.println("Checking here3");
     
     System.out.println("Checking here4");
     %>
     
     <html:option value="SELECT">SELECT</html:option>
     
     <%
        Object div_key;
        Enumeration en_div=hash_div.keys();
        while(en_div.hasMoreElements()){
        	div_key=en_div.nextElement();
        
     %>
     <html:option value="<%=""+div_key%>"><%=""+hash_div.get(div_key)%></html:option>
     <%
        }
     %>
     </html:select></td>
  </tr>
 <br>
 <tr>
 <center>
 <table>
 <tr>
   <html:hidden property="forward" value="error"></html:hidden>
  <td>
    <html:submit onclick="set('view')" styleClass="ButtonBackgroundImage"><bean:message key="label.view1"></bean:message></html:submit>
     
    <html:submit onclick="set('clear')" styleClass="ButtonBackgroundImage" ><bean:message key="label.clear"></bean:message> </html:submit>
  </td>
 </tr>
 
 </center>
 </tr>
 <tr>
   <%if(div!=null){ %>
    <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table name="div_report" export="true" id="currentRowObject" class="its" sort="list" pagesize="10" requestURI="/Share/DividendReport.do">
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 

   
  
   <display:column property="customerID" ></display:column>
   <display:column property="name" ></display:column>
   <display:column property="shnum"  ></display:column>
   <display:column property="branchCode" ></display:column>
   <display:column property="payMode" ></display:column>
   <display:column property="divAmount" ></display:column>
   <display:column property="drfAmount"  ></display:column>
   
   
  </display:table>
  <%} %>
 </tr>
 </table>
</table>
</html:form>
</body>
</html>