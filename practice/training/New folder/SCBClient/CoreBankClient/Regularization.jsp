<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head><title>Share Regularization</title>
     <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Share Regularization</center></h2>
      <hr>
     
    
    <script type="text/javascript">
     function set(target){
       document.forms[0].forward.value=target
          
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


            if (  ( cha  >= 47 && cha < 58  ) ) {


              
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
   int [] temp_nos;
  
%>

<% temp_nos=(int[])request.getAttribute("regularization");

%>
<html:form action="/Share/Regularization?pageId=4011">

  <table>
    <tr>
     <td>
      <html:hidden property="forward" value="error"></html:hidden>
      <html:submit onclick="set('display')" styleClass="ButtonBackgroundImage"><bean:message key="label.display"></bean:message> </html:submit>
      <html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:submit>
      <html:submit onclick="set('clear')" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message> </html:submit>
     </td>
    </tr>
 
  
  <table border="1"  bordercolor="Orange" class="txtTable" ">
 <td></td>
 <td>Suspence A/c No</td>
 <td>Permanent A/c No</td>
 
 
 
 <%if(temp_nos==null){
	 
 %>
   
   
 <%}else {
	 for(int i=0;i<temp_nos.length;i++){
		 int rows=temp_nos.length;
		 System.out.println("The no opf roows are "+rows);
		
		   //Color c=new Color(i+50,100,i+100);
		 
 %>
 <tr style="background-color:#CDCEAE">
 <td style="background-color:#ECEBD2"><input type="checkbox"></td>
 <td style="background-color:#ECEBD2"><html:text property="temp_num" value="<%=""+temp_nos[i]%>" readonly="true" style="border:transparent;background-color:beige" onchange="submit()"></html:text></td>
 <td style="background-color:#ECEBD2"><html:text property="perm_num"   style="border:transparent;background-color:beige" ></html:text></td>
 
 </tr>
 
 
 <%} }%>
 
 </table>
   </table>
</html:form>
</body>
</html>