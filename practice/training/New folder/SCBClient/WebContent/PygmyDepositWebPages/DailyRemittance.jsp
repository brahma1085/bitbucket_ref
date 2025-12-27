<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.pygmyDeposit.AgentMasterObject" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
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
	
	    document.forms[0].collDate.value="";
	    dt.focus();
	    
	      	
		return false
		}
		else {
	       	
		//document.forms[0].submit()
		}
		return true;
		}
		
		
	function ValidateForm1(){
   
   
	var dt=document[0].remiDate;
	
	
	if (isDate(dt.value)==false){
	    document.forms[0].remiDate.value="";
	    
	    dt.focus();  	
		return false
		}
		else {
	     	
		document.forms[0].submit()
		}
		return true;
		}
  	function set(target)
  	{
  	if(document.forms[0].agType.value=="SELECT")
         {
         alert('Select Agent Type');
         }
        else{
         	document.forms[0].forward.value=target;
         	document.forms[0].submit();
         	}
         };
  	function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) ) {
				return true ;
                
            	}
            else{
              	alert("Alphabets are not allowed,Please enter numbers only ");
              	return false ;
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
         
   function onlynumbers()
   {
        var cha = event.keyCode;
		
		if(cha > 47 && cha <= 57) 
        {
          return true;
        }
        else
        {
        	return false;
        }
			        
 	}   
 	function numbersOnlydot(eve){
         var cha = event.keyCode
				if (  ( cha   >47 && cha < 58  ) || cha==46 ) {
				return true ;
                
            	}
            else{
              	alert("Alphabets are not allowed!!!!!!!Please enter numbers only ");
              	return false ;
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
  
  
  
  if(frmYear > ToYear){
    alert("Fromdate should be less than the Todate!")
  }
 // else if(frmMonth > ToMonth && frmYear<=ToYear ){
  //  alert("From Month is greater than To Month !!!! Enter valid date")
 // }
// else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
 // alert("From day is greater than To day !!!! Enter valid date")
 // }
 }; 
   
       
       
    </script>
	</head>
	<body class="Mainbody">
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
	<center><h2 class="h2">Daily Remittance</h2></center>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:form action="/Pygmy/DailyRemittance?pageid=8016">
	
	<%! ModuleObject[] array_module; 
		AgentMasterObject[] agentMaster;
		String name,msg,addrow;
		String date,coll_date;
		
	%>
	<% 
		msg = (String)request.getAttribute("msg");
	   	name=(String)request.getAttribute("Name");
	   	date=(String)request.getAttribute("Date");
	   	coll_date=(String)request.getAttribute("COLLDATE");
	   	agentMaster=(AgentMasterObject[])request.getAttribute("AgNum");
	  	addrow=(String)request.getAttribute("ADDROW");
	%>
	<% if(msg!=null){ %>
	<font color="red"><%=msg %></font>
	<%} %>
	
	
	<table>
	<tr><html:text property="valid" readonly="true" size="100" styleClass="formTextField"/></tr>
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
                     		<td><html:text property="agName"  value="<%=""+name%>" styleClass="formTextField" readonly="true"></html:text></td>
                     	<%}else{ %>	
                     		<td><html:text property="agName"   styleClass="formTextField" readonly="true"></html:text></td>
                     	<%} %>	
                    	
               </tr>
             
        <tr>
        			
        			 <td><bean:message key="label.colldt"/></td> 
        			 		<td><html:text property="collDate" size="10" value="<%=coll_date %>" styleClass="formTextFieldWithoutTransparent" onblur="ValidateForm()" onkeypress="return numbersonly_date(this)" ></html:text></td>
        			 <td><bean:message key="label.remidt"/></td> 
        			 		<td><html:text property="remiDate" size="10" value="<%=date %>" styleClass="formTextFieldWithoutTransparent" onblur="ValidateForm1()" onkeypress="return numbersonly_date(this)" ></html:text></td>
        </tr>	
        
         <tr>		
        			 <td><bean:message key="label.scrNo"/></td>
        		 	<td><html:text property="scrNo" size="10" onblur="set('scrollno')" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'6')"></html:text></td>
        			
        			 <td><bean:message key="label.scrAmt"/></td> 
        			 		<td><html:text property="amt" size="10" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
        		 			
        </tr>			
        
        <tr>
        
				<td><bean:message key="label.selected"/></td>
				<td><html:radio property="selected" value="selected" onclick="set('selected')"></html:radio></td>	
				<td><bean:message key="label.all"></bean:message></td>
				<td><html:radio property="selected" value="all" onclick="set('all')"></html:radio></td>	
        </tr> 
        
        <html:hidden property="forward" value="error"/>
</table>
		
		<display:table name="DailyRemiList" id="DailyRemiList" class="its" list="${DailyRemiList}">
		<display:column style="width:3%;text-align: right;" title="Account Type">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==DailyRemiList.update}">
					<%System.out.println("Inside edit---------->"); %>
					<input type="text" name="PD"   style="padding:0;background:transparent;border:0px" style="padding:0" value="${DailyRemiList.AccountType}" readonly="true" />
				</core:when>
				<core:otherwise>
					<%System.out.println("Inside otherwise"); %>
					<core:out value="${DailyRemiList.AccountType}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Account No">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==DailyRemiList.update}">
					<input type="text" name="AccountNo"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${DailyRemiList.AccountNo}" onkeypress=" return numbersOnly()" readonly="true"/>
				</core:when>
				<core:otherwise>
					<core:out value="${DailyRemiList.AccountNo}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
		<display:column style="width:3%;text-align: right;" title="Prev Bal">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==DailyRemiList.update }">
					<input type="text" name="PrevBal"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${DailyRemiList.PrevBal}" onkeypress=" return numbersOnlydot()" readonly="true"/>
				</core:when>
				<core:otherwise>
					<core:out value="${DailyRemiList.PrevBal}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Remi Amt">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.update==DailyRemiList.update }">
					<input type="text" name="RemiAmt"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${DailyRemiList.RemiAmt}" onkeypress=" return numbersOnlydot()" onkeyup="numberlimit(this,'10')" />
				</core:when>
				<core:otherwise>
					<core:out value="${DailyRemiList.RemiAmt}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		

		<display:column title="Update" style="width:1%">
				<input type="checkbox" name="update" value="${DailyRemiList.update}"  style="margin:0 0 0 4px"  <core:if test="${param.update==DailyRemiList.update and param.method != 'Save' and param.method != 'Clear' and  param.method != 'Reset'}">checked="checked"</core:if> />
		</display:column>
		
		</display:table>
		
		
		<input type="submit" value="Edit" name="method" class="ButtonBackgroundImage"/>
	<%if(addrow!=null){ %>	
		<input type="button" value="AddRow" disabled="disabled" name="method" class="ButtonBackgroundImage" onclick="set('AddRow')"/>
		
		<%}else{ %>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
        <input type="button" value="AddRow" name="method" class="ButtonBackgroundImage" onclick="set('AddRow')"/>
        <%}else{ %>
        <input type="button" value="AddRow" name="method" disabled="disabled" class="ButtonBackgroundImage" onclick="set('AddRow')"/>
         <%} %>
		
		
		<%} %>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
         <input type="button" value="Submit" name="method" class="ButtonBackgroundImage" onclick="set('Submit')"/>
             <%}else{ %>
           <input type="button" value="Submit" disabled="disabled" name="method" class="ButtonBackgroundImage" onclick="set('Submit')"/>
             <%} %>
		
		<input type="button" value="Clear" name="method" class="ButtonBackgroundImage" onclick="set('Clear')"/>
		
		<br/>


</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>