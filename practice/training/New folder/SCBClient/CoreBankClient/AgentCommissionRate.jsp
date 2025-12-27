<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@page import="masterObject.pygmyDeposit.PygmyMasterObject"%>
<%@page import="masterObject.pygmyDeposit.PygmyTransactionObject"%>
<%@page import="java.util.Map"%>
<html>
<head>

<title></title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />

<h2 class="h2"><center>Agent Commission Rate</center></h2>
         
   <script type="text/javascript">
   
   
         function set(target){
         	document.forms[0].forward.value=target;
         };
         
         function intialFocus(){
         document.forms[0].agentnum.focus();
         }
         
         function checkNum(){
        	var val=document.forms[0].valid.value;
         
         if( val=="" ){
   			
   			 return false; 
   		}
   		else { 
   			 alert(val);
   		}
   			clearMethod();
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
         
         


 
 function set(target){
       document.forms[0].forward.value=target
        };
        
 function properdate(From,To){
  
  
  var dtCh="/";
   
  var pos1=From.indexOf(dtCh)
  var pos2=From.indexOf(dtCh,pos1+1)
  var frmMonth=From.substring(pos1+1,pos2)
  var frmDay=From.substring(0,pos1)
  var frmYear=From.substring(pos2+1)
  
  
  var pos3=To.indexOf(dtCh)
  
  var pos4=To.indexOf(dtCh,pos3+1)
  
  var ToMonth=To.substring(pos3+1,pos4)
  
  var ToDay=To.substring(0,pos3)
  
  var ToYear=To.substring(pos4+1)
  
  
  
  if(frmYear>ToYear){
    alert("From Year is greater than To Year!!!! Enter valid date")
  }
  //else if(frmMonth>ToMonth && frmYear<=ToYear ){
    //alert("From Month is greater than To Month !!!! Enter valid date")
  //}
 //else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  //alert("From day is greater than To day !!!! Enter valid date")
  //}
 }; 
 
 		function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) || cha==46 ) {
				return true ;
                
            	}
            else{
              	alert("Alphabets are not allowed!!!!!!!Please enter numbers only ");
              	return false ;
            }
        };  
        
        function clearMethod()
        {
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
        		document.getElementById("table1").style.display="none";
        	}	
        };  
        function numbersonly_date(eve){
         var cha = event.keyCode

            if (  ( cha  >= 47 && cha < 58  ) ) {
              
                return true ;
                            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
          };
  function dateLimit()
 	{
 	if(document.forms[0].From.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].From.value="";
 	document.forms[0].From.focus;
 	alert("Enter dd/mm/yyyy Format!!!");
 	return false;
 	}
 	}
 	
 	function dateLimit1()
 	{
 	if(document.forms[0].To.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].To.value="";
 	document.forms[0].To.focus;
 	alert("Enter dd/mm/yyyy Format!!!");
 	return false;
 	}
 	}      
        
         
   </script>

</head>
<body class="Mainbody" onload="checkNum()" >

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
<% String msg=(String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<font color="red"><%=msg %></font>
		<br><br>
		<%} %>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Pygmy/AgentCommission?pageid=8014">

	<display:table name="AdminList" id="AdminList" class="its" list="${AdminList}">
		
		<display:column style="width:3%;text-align: right;" title="Agent Type">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==AdminList.update }">
					<input type="text" name="PAG" readonly="readonly"  style="padding:0;background: transparent;border: 0px" style="padding:0" value="${AdminList.AgentType}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.AgentType}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="From">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==AdminList.update }">
					<input type="text" name="From"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${AdminList.From}"  onkeypress="return numbersonly_date(this)" onkeydown="return dateLimit()"
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.From}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="TO">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==AdminList.update }">
					<input type="text" name="To"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${AdminList.To}" onchange="properdate(From.value,To.value)" onkeypress="return numbersonly_date(this)" onkeydown="return dateLimit1()"
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.To}"></core:out>
			   </core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Min Amount">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==AdminList.update }">
					<input type="text" name="MinAmount"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${AdminList.MinAmount}" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.MinAmount}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Max Amount">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==AdminList.update }">
					<input type="text" name="MaxAmount"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${AdminList.MaxAmount}"  onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.MaxAmount}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Comm Rate">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==AdminList.update }">
					<input type="text" name="CommRate"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${AdminList.CommRate}"  onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'5')"
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.CommRate}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Security Dep Rate">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==AdminList.update }">
					<input type="text" name="SecurityDepRate"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${AdminList.Security_Dep_Rate}" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'5')"
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.Security_Dep_Rate}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="DeUser">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==AdminList.update }">
					<input type="text" name="DeUser"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${AdminList.DeUser}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.DeUser}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="DeTml">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==AdminList.update }">
					<input type="text" name="DeTml"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${AdminList.DeTml}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.DeTml}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="DeDate">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==AdminList.update }">
					<input type="text" name="DeDate"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${AdminList.DeDate}" onblur="ValidateForm()"  
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.DeDate}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column title="Update" style="width:1%">
		<input type="checkbox" name="update" value="${AdminList.update}"  style="margin:0 0 0 4px" onclick="radio(this)" <core:if test="${param.update==AdminList.update and param.method != 'Save' and param.method != 'Clear' and  param.method != 'Reset'}">checked="checked"</core:if> /></display:column>
		</display:table>
	
    <input type="submit" value="Refresh" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Reset'" />
	<input type="submit" value="Edit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Edit'" />
	<input type="submit" value="AddRow" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=AddRow'"/>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
          <input type="submit" value="Update" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Update'"/>
          <%}else{ %>
          <input type="submit" value="Update" disabled="disabled" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Update'"/>
           <%} %>
	
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
           <input type="submit" value="Delete" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Delete'"/>
             <%}else{ %>
           <input type="submit" value="Delete" disabled="disabled" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Delete'"/>
             <%} %>
	
	
		
	
	
	<!--<input type="submit" value="Clear" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Clear'"/>
	
	--><html:hidden property="valid"/>
	<br/>
	
	
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>