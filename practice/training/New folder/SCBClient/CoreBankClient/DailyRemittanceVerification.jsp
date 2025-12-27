<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.pygmyDeposit.AgentMasterObject" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@page import="java.util.List"%>

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
   
   
	var dt=document[0].collDate;
	
	
	if (isDate(dt.value)==false){
	
	    dt.value="";
	    dt.focus();
	    
	      	
		return false
		}
		else {
	       	
		document.forms[0].submit()
		}
		return true;
		}
		
		
	function ValidateForm1(){
   
   
	var dt=document[0].remiDate;
	
	
	if (isDate(dt.value)==false){
	    dt.value="";
	    
	    dt.focus();  	
		return false
		}
		else {
	       	
		document.forms[0].submit()
		}
		return true;
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

  function set(target)
  {
   	document.forms[0].forward.value=target;
   	document.forms[0].submit();
  };
  function numbersonly_date(eve){
         var cha = event.keyCode

            if (  ( cha  >= 47 && cha < 58  ) ) {
              
                return true ;
                            }
            else{
              //alert("Please enter numbers only");
              return false ;
            }
         
        
      };  
       function onlynumbers()
   {
        var cha = event.keyCode;
		
		if(cha >= 48 && cha <= 57) 
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



<h2 class="h2"><center>Daily Remittance Verify</center></h2>
	<html:form action="/Pygmy/DailyRemittance?pageid=8022">
	<% String msg=(String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<font color="red"><%=msg %></font>
		<br><br>
		<%} %>
	
	
	<%! ModuleObject[] array_module; 
		AgentMasterObject[] agentMaster;
		String name;
		String date,coll_date;
		
	%>
	<% 
	   name=(String)request.getAttribute("Name");
	   date=(String)request.getAttribute("Date");
	   coll_date=(String)request.getAttribute("COLLDATE");
	   agentMaster=(AgentMasterObject[])request.getAttribute("AgNum");
	  
	  
	%>
	<table>
		<tr><html:text property="valid" readonly="true" styleClass="formTextField"/></tr>
	</table>
	<table>
	   <tr>
                  <td><bean:message key="label.agentcode"></bean:message></td>
                   <td><html:select  property="agType" styleClass="formTextField">
                         <html:option value="SELECT">SELECT</html:option>
                         <% array_module=(ModuleObject[])request.getAttribute("AgentType"); %>                       
							<core:forEach var="agType" items="${requestScope.AgentType}" varStatus="count">
								<html:option value="${agType.moduleCode}">${agType.moduleAbbrv}</html:option>
                        	</core:forEach>
                         </html:select></td>
                         
               	   <td><bean:message key="label.agentno"></bean:message></td>
               	   		<td><html:select property="agNo" onblur="set('agentno')" styleClass="formTextFieldWithoutTransparent">
            			
               			<%if (agentMaster!=null){ %>
               			<%	for(int i=0;i<agentMaster.length;i++) {%>
                     		<html:option value="<%=""+agentMaster[i].getAgentNumber()%>"><%=""+agentMaster[i].getAgentNumber()%></html:option>
                     	<%} }%>
                 			</html:select>
                 		</td>
                    	
                    	
                    <td><bean:message key="label.agentname"/></td> 
                    	<%if(name!=null){ %>   	
                     		<td><html:text property="agName"  value="<%=""+name%>" styleClass="formTextField"></html:text></td>
                     	<%}else{ %>	
                     		<td><html:text property="agName"   styleClass="formTextField" ></html:text></td>
                     	<%} %>	
                    	
               </tr>
             
        <tr>
        			 <td><bean:message key="label.colldt"/></td> 
        			 <td><html:text property="collDate" size="10" value="<%=coll_date %>" styleClass="formTextFieldWithoutTransparent" onblur="ValidateForm()" onkeypress="return numbersonly_date(this)" readonly="true"></html:text></td>
        			 <td><bean:message key="label.remidt"/></td> 
        			 <td><html:text property="remiDate" size="10" value="<%=date %>" styleClass="formTextFieldWithoutTransparent" onblur="ValidateForm1()" onkeypress="return numbersonly_date(this)" readonly="true"></html:text></td>
        </tr>	
        
         <tr>		
        			 <td><bean:message key="label.scrNo"/></td>
        		 	 <td><html:text property="scrNo" size="10" onblur="set('scrollnoverify')"  styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')"></html:text></td>
        			 <td><bean:message key="label.scrAmt"/></td> 
        			 <td><html:text property="amt" size="10"  styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()"></html:text></td>
   
        </tr>			
        
        
        
        <html:hidden property="forward" value="error"/>
</table>
		
		<display:table name="DailyRemiList" id="DailyRemiList" class="its" list="${DailyRemiList}">
			
			<display:column style="width:3%;text-align: right;" property="AccountType" title="AccountType"></display:column>
			<display:column style="width:3%;text-align: right;" property="AccountNo" title="AccountNo"></display:column>
			<display:column style="width:3%;text-align: right;" property="Name" title="Name"></display:column>
			<display:column style="width:3%;text-align: right;" property="PrevBal" title="PrevBal"></display:column>
			<display:column style="width:3%;text-align: right;" property="RemiAmt" title="RemiAmt"></display:column>
		
		</display:table>
		
		
		<input type="button" value="Verify" name="method" class="ButtonBackgroundImage" onclick="set('Verify')" />
		<input type="button" value="Clear" name="method" class="ButtonBackgroundImage" onclick="set('Clear')"/>
		
		<br/>


</html:form>
</body>
</html>