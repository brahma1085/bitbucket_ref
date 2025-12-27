<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@page import="masterObject.pygmyDeposit.PygmyMasterObject"%>
<%@page import="masterObject.pygmyDeposit.PygmyTransactionObject"%>
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
	
<h2 class="h2"><center>Ledger Printing</center></h2>
     	    
       <script type="text/javascript">
         function set(target)
         {
         	if(document.forms[0].stAcno.value=="")
         	{
         		alert('Enter Starting Account Number');
         		document.forms[0].stAcno.focus();
         	}
         	else if(document.forms[0].endAcno.value=="")
         	{
         	alert('Enter Ending Account Number');
         	document.forms[0].endAcno.focus();
         	}
         	else{
         	document.forms[0].forward.value=target;
         	document.forms[0].submit();
         	}
         };
         
         function intialFocus(){
         document.forms[0].agentnum.focus();
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
   
	var dt=document[0].trn_dt;
	var dt1=document[0].trn_dt_upto;
	if (isDate(dt.value)==false){
	       	
		return false
	}
	if (isDate(dt1.value)==false){
	       	
		return false
	}
	
    return true
 }
 
 
        
 function properdate(trn_dt,trn_dt_upto){
  
  
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
  
  
  
  if(frmYear>ToYear ){
    alert("From Year is greater than To Year!!!! Enter valid date")
  }
  else if(frmMonth>ToMonth && frmYear<=ToYear ){
    alert("From Month is greater than To Month !!!! Enter valid date")
  }
 else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  alert("From day is greater than To day !!!! Enter valid date")
  }
  document.forms[0].submit();
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
        
        function numbersOnly(){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) ) {
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
         		alert("only 7 digits allowed")
         		txt.value="";
         		return false;
          	}
         }
         
   </script>
   
     
</head>
<body class="Mainbody">


<%! PygmyMasterObject[] pMaster=null;
String date;
PygmyTransactionObject[] ptran; %>		
		
		
		<% 
		    date=(String)request.getAttribute("Date");
			pMaster=(PygmyMasterObject[])request.getAttribute("LEDGERDETAILS");
			System.out.println("----Hiiiiiiiiiiiii-----"+pMaster);
			ptran=(PygmyTransactionObject[])request.getAttribute("LedgerTable");
		%>
		<% String msg=(String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<font color="red"><%=msg %></font>
		<br><br>
		<%} %>
		<html:form action="/Pygmy/LedgerPrinting?pageid=8012">
		<table class="txtTable">
		<tr>
			<td><bean:message key="label.ledType"></bean:message></td>
				<td><html:select  property="comboLedger" styleClass="formTextFieldWithoutTransparent">
                         
                          <core:choose>
                          <core:when test="${requestScope.LedgerType!=null}">
                          <core:forEach var="comboLedger" items="${requestScope.LedgerType}" varStatus="count">        
                         	
                            <html:option value="${comboLedger}">${comboLedger}</html:option>
                            <%System.out.println("Helloooooooooooooooooo"); %>
                            
                          </core:forEach>
                          </core:when>
                          </core:choose>  
                 </html:select></td>
                 
                 <td><bean:message key="label.stAcno"></bean:message>
					<html:text property="stAcno" size="10" onblur="set('stAcno')" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'6')"></html:text></td>
                 
             	<td><bean:message key="label.endAcno"></bean:message>
             		<html:text property="endAcno" size="10" onblur="set('endAcno')" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'6')"></html:text></td>
             	
             	
		</tr>
		
		<tr>
				<td><bean:message key="label.trndate"></bean:message></td>
				<td><html:text property="trn_dt" size="10"  styleClass="formTextFieldWithoutTransparent" onblur="ValidateForm()"></html:text></td>
				
				<td><bean:message key="label.trndateto"></bean:message>
					<html:text property="trn_dt_upto" size="10"   styleClass="formTextFieldWithoutTransparent" onblur="ValidateForm()"  onchange="properdate(from_date.value,to_date.value)"></html:text></td>
				
					
				<td><bean:message key="label.pygno"></bean:message>
				
                      		<% if(pMaster!=null){
                      	 		
                      	 		for(int i=0;i<pMaster.length;i++){ %>
									<html:text property="pgno" size="10" readonly="true" value="<%=""+pMaster[i].getAccNo() %>" styleClass="formTextField" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'6')"></html:text>
							<%}} %>	
						 
				</td>		
		</tr>
		
		
		
		<tr>
		
				<td><bean:message key="label.openingdate"></bean:message></td>
				<td><core:choose>
                      	 <core:when test="${requestScope.LEDGERDETAILS!=null}">
                      	
                      	 	<%for(int i=0;i<pMaster.length;i++){ %>
								<html:text property="open_dt" size="10" readonly="true" value="<%=pMaster[i].getAccOpenDate() %>" styleClass="formTextField" onblur="ValidateForm()"></html:text>
							   <%} %>	
						 
						 </core:when>
					</core:choose></td>	
		
		
				<td><bean:message key="label.agentno"></bean:message>
				<core:choose>
                      	 <core:when test="${requestScope.LEDGERDETAILS!=null}">
                      	 
                      	 	<%for(int i=0;i<pMaster.length;i++){ %>
								<html:text property="agno" size="8" readonly="true" value="<%=""+pMaster[i].getAgentNo() %>" styleClass="formTextField" onkeypress=" return numbersOnly()"></html:text>
						 	<%} %>	
						 
						 </core:when>
						 
					</core:choose></td>
					
					<td><bean:message key="label.agentname"></bean:message>
					<core:choose>
                      	 <core:when test="${requestScope.LEDGERDETAILS!=null}">
                      	
                      	 	<%for(int i=0;i<pMaster.length;i++){ %>	 
								<html:text property="agname" readonly="true"  value="<%=pMaster[i].getAgentName() %>" styleClass="formTextField"></html:text>
						     <%} %>	
						
						 </core:when>
					</core:choose></td>	
				
					
		</tr>
		
		<tr>
				<td><bean:message key="label.loanAvail"></bean:message></td>
				<td><core:choose>
                      	 <core:when test="${requestScope.LEDGERDETAILS!=null}">
                      	 
                      	 	<%for(int i=0;i<pMaster.length;i++){ %>
								<html:text property="lnAvail" readonly="true"  value="<%=pMaster[i].getLnAvailed() %>" styleClass="formTextField"></html:text>
							<%} %>	
						 
						 </core:when>
					</core:choose></td>		
				
				<td><bean:message key="label.intPaidUpto"></bean:message>
				<core:choose>
                      	 <core:when test="${requestScope.LEDGERDETAILS!=null}">
                      	 
                      	 	<%for(int i=0;i<pMaster.length;i++){ %>
								<html:text property="int_paid_upto" size="10" readonly="true" value="<%=pMaster[i].getLastIntrDate() %>" styleClass="formTextField" onblur="ValidateForm()"></html:text>
							<%} %>	
						 
						 </core:when>
					</core:choose></td>	
				
				<td><bean:message key="label.nomNo"></bean:message>
				<core:choose>
                      	 <core:when test="${requestScope.LEDGERDETAILS!=null}">
                      	 
                      	 	<%for(int i=0;i<pMaster.length;i++){ %>
								<html:text property="nomiNo" size="10" readonly="true" value="<%=""+pMaster[i].getNomineeNo() %>" styleClass="formTextField" onkeypress=" return numbersOnly()"></html:text>
						    	<%} %>	
						
						 </core:when>
					</core:choose></td>			
		</tr>
		
		<tr>
				<td><bean:message key="label.paymentmode"></bean:message></td>
				<td><core:choose>
                      	 <core:when test="${requestScope.LEDGERDETAILS!=null}">
                      	 
                      	 	<%for(int i=0;i<pMaster.length;i++){ %>
                      	 		<html:text property="payMode" size="10" readonly="true" value="<%=pMaster[i].getPayMode()%>" styleClass="formTextField"></html:text>
                      	 	<%} %>	
						 
						 </core:when>
					</core:choose></td>		
                       
                     
                <td><bean:message key="label.nomiName"></bean:message>
                <core:choose>
                      	 <core:when test="${requestScope.LEDGERDETAILS!=null}">
                      	 <core:if test="<%=pMaster!=null %>">
                      	 	<%for(int i=0;i<pMaster.length;i++){ %>
                				<html:text property="nomName"  readonly="true" value="<%=pMaster[i].getNom_details() %>" styleClass="formTextField"></html:text>
               				<%} %>	
						 </core:if>
						 </core:when>
					</core:choose></td>	     
		</tr>
		
		
		
		<tr>
					<html:hidden property="forward" value="error"/>
					
             		<td><html:button property="but" onclick="set('view')" styleClass="ButtonBackgroundImage">View</html:button>
                	
           	     	<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button>
           	     	
           	     	<html:button property="PrintFile" onclick="window.print()"  styleClass="ButtonBackgroundImage">Print</html:button></td>
         </tr> 	
		</table>
		
		 <%if(ptran!=null){ %>
	  	  <div  id = "table1" style="display: block;overflow:scroll;width: 750px;height: 300px">
   			<display:table name="LedgerTable"  id="currentRowObject" class="its" >
   			
               		
                   	<display:column property="tranDate" style="background-color:#F2F0D2; font:Garamond;width:100px " title="Date" ></display:column>
            
      				<display:column property="tranNarration" style="background-color:#F2F0D2;width:100px " title="Narration"></display:column>
                    
      				<display:column property="debAmt" style="background-color:#F2F0D2;width:100px " title="Debit"></display:column>
      				
      				<display:column property="crAmt" style="background-color:#F2F0D2;width:100px " title="Credit"></display:column>
    
      				<display:column property="closeBal" style="background-color:#F2F0D2;width:100px " title="Balance"></display:column>
     	      </display:table>
     	</div>
	 	 <%
	 	 }
	 	 %>
</html:form>		
</body>

</html>