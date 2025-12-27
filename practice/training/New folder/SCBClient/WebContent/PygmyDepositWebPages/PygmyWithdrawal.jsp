<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.pygmyDeposit.PygmyMasterObject" %>
<%@ page import="masterObject.pygmyDeposit.PygmyRateObject" %>
<%@ page import="masterObject.pygmyDeposit.PygmyTransactionObject" %>
<%@ page import="com.scb.common.forms.PersonnelForm" %>
<%@ page import="com.scb.pd.forms.PygmyWithdrawalForm" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="java.util.Map"%>

<%@page import="masterObject.pygmyDeposit.PygmyMasterObject"%>
<html>
  <head><title></title>
  		<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
        <h2 class="h2"><center>Pygmy Withdrawal</center></h2>
          <script type="text/javascript">
    
  function disEnable(){
  
  if(document.forms[0].paymode.value!=""){
  if(document.forms[0].paymode.value=="Cash"){
    document.getElementById("Transfer").style.display='none';
    document.getElementById("Transfer1").style.display='none';
    }
    else if(document.forms[0].paymode.value=="PayOrder"){
    document.getElementById("Transfer").style.display='block';
    document.getElementById("Transfer1").style.display='block';
    }else if(document.forms[0].paymode.value=="Transfer"){
    document.getElementById("Transfer").style.display='block';
    document.getElementById("Transfer1").style.display='block';
    }
  }
  
  
  
}
    
         function set(target){
         	document.forms[0].forward.value=target;
         };
         
        
        
        
        function clearMethod()
        {
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
        	}document.forms[0].custid.value="";	
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
        
        function validateMethod(){
       
        	var val1=document.forms[0].validate.value;
        	
        if(val1!=0 || val1!=""){
             
   			 alert(val1);
   			 
   		}
   		else { 
   			 return false;
   		 }
   		 	clearMethod();
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
   
	var dt=document[0].from_date;
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
<body class="Mainbody" onload="disEnable();validateMethod();">
	<%
	   ModuleObject[] array_module;
	   PersonnelForm form;
	   PygmyWithdrawalForm pwForm;
	   CustomerMasterObject cmObject;
	   PygmyMasterObject pygmyMaster;
	   PygmyRateObject pyObject;
	   PygmyTransactionObject[] pTransactionObject;
	   String tyop[];
	   String paymode[];
	   String pygdetails[];
	   Double interest;
	   String loan_bal;
	   String loan_int;
	   Double fine_amts;
	   Double PenalAmt;
	   String jspPath;
	   Double max_with;
	   Double with_amt;
	   Double max_with_amt;
	   Double par_with_amt;
	   Double with_allow;
	   String action,value;
	   boolean flag1;
	%>
	<%!
	String access,enable;
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
	<%
	    pygmyMaster=(PygmyMasterObject)request.getAttribute("PygmyWithDetails");
		System.out.println("---------  In pygMaster---------"+pygmyMaster);
	    pyObject=(PygmyRateObject)request.getAttribute("PygmyRate");	
	   	System.out.println("---------  In pygRate---------"+pyObject);
	    pTransactionObject=(PygmyTransactionObject[])request.getAttribute("PygmyTranDetails");
	    System.out.println("---------  In pygTran---------"+pTransactionObject);
	    cmObject=(CustomerMasterObject)request.getAttribute("personalInfo");
		value=(String)request.getAttribute("VALUE");
		enable=(String)request.getAttribute("enable");	
		%>
		<% String msg=(String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<font color="red"><%=msg %></font>
		<br><br>
		<%} %>
		<%if(value!=null){
	    	 if(Integer.parseInt(value.trim())==2){
	    		 action="/Pygmy/PygmyWithdrawalMenu?pageid=8003&value=2";
	    		 flag1=true;
	    	 }else if(Integer.parseInt(value.trim())==1){
	    		 action="/Pygmy/PygmyWithdrawalMenu?pageid=8003&value=1";
	    		 flag1=false;
	    	 }
	     }
	   %>
	   <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:form action="/Pygmy/PygmyWithdrawal?pageid=8003">
	<table class="txtTable">
		<td>
		   <table class="txtTable">
		   		<tr>
		   			<td> <bean:message key="label.pygmyactypeno"></bean:message></td>
                     <td><html:select  property="pgactypeno" styleClass="formTextFieldWithoutTransparent">
                         
                         <% array_module=(ModuleObject[])request.getAttribute("PygmyAcType");
 							System.out.println("here i am=="+array_module); %>                       
							<core:forEach var="pygmyactypeno" items="${requestScope.PygmyAcType}" varStatus="count">
								<html:option value="${pygmyactypeno.moduleCode}">${pygmyactypeno.moduleAbbrv}</html:option>
                        	</core:forEach>
                         </html:select>
                         
                      <core:choose>
                      	 <core:when test="${requestScope.PygmyWithDetails!=null}">
                      	 	<html:text property="pgno" size="10" onchange="submit()" value="<%=""+pygmyMaster.getAccNo()%>" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>
                         </core:when>
                      	 <core:otherwise>
                      	 	<html:text property="pgno" size="10" onchange="submit()" value="0" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>
                         </core:otherwise>	 	
                     </core:choose>
                      
                      	<%if(pygmyMaster!=null){%>
                     		<html:text property="pname" readonly="true"  value="<%=""+pygmyMaster.getName()%>" styleClass="formTextField" disabled="true"></html:text>
                       <%}else{%>
                     		<html:text property="pname" readonly="true"  value="" styleClass="formTextField"></html:text>
                        <%}%>	
                     
                      </td>
                 </tr>
                 
                 <tr>
                     <td><bean:message key="label.agentcode"></bean:message></td>
                     <td><html:select  property="agentcode" styleClass="formTextFieldWithoutTransparent">
                         
                         <% array_module=(ModuleObject[])request.getAttribute("AgentAcType");
 							System.out.println("here i am=="+array_module); 
 							%>                       
							<core:forEach var="agentcode" items="${requestScope.AgentAcType}" varStatus="count">
								<html:option value="${agentcode.moduleCode}">${agentcode.moduleAbbrv}</html:option>
                        	</core:forEach>
                         </html:select>
                         
                      <core:choose>
                     	<%System.out.println("**********************   1   *******************");%>
                     	<core:when test="${requestScope.PygmyWithDetails!=null}">
                      	 	<html:text property="agentnum" size="10" readonly="true" value="<%=""+pygmyMaster.getAgentNo()%>" styleClass="formTextFieldWithoutTransparent"></html:text>
                         </core:when>
                      	 <core:otherwise>
                      	 	<html:text property="agentnum" size="10" readonly="true" value="0" styleClass="formTextFieldWithoutTransparent"></html:text>
                         </core:otherwise>	 	
                     </core:choose>
                     
                     </td>
                </tr>
                
				
                
                <tr>
                     <td><bean:message key="label.agentname"></bean:message></td>
                     <core:choose>
                     	<%System.out.println("**********************   2   *******************");%>
                      	 <core:when test="${requestScope.PygmyWithDetails!=null}">
                      	 	<td><html:text property="agentname"  readonly="true" value="${requestScope.PygmyWithDetails.agentName}" styleClass="formTextField"></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="agentname" readonly="true" value="" styleClass="formTextField"></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>
                </tr>
               
                <tr>
                     <td><bean:message key="label.openingdate"></bean:message></td>
                     <core:choose>
                     	<%System.out.println("**********************   3   *******************");%>
                      	 <core:when test="${requestScope.PygmyWithDetails!=null}">
                      	 	<td><html:text property="opdate" size="10" readonly="true" value="<%=""+pygmyMaster.getAccOpenDate() %>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="opdate" size="10"  readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>
                </tr>
                
                <tr>
                     <td><bean:message key="label.TypeOfOperation"></bean:message></td>
                     <td><html:select  property="tyop" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
                         <html:option value="SELECT">SELECT</html:option>
                         <% tyop=(String[])request.getAttribute("TypeOfOperation");%>
                        	<core:forEach var="tyop" items="${requestScope.TypeOfOperation}"  varStatus="count">
								<html:option value="${tyop}">${tyop}</html:option>
                        	</core:forEach>
                         </html:select>
                     </td>
                </tr>
                
                <tr>
                     <td><bean:message key="label.principal"></bean:message></td>
                     <core:choose>
                     	<%System.out.println("**********************   4   *******************");%>
                      	 <core:when test="${requestScope.PygmyWithDetails!=null}">
                      	 	<td><html:text property="principal" size="10" readonly="true" value="<%=""+pygmyMaster.getPrevBalance()%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="principal" size="10" readonly="true" value="0" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>
                </tr>
                
                <tr>
                     <td><bean:message key="label.interest"></bean:message></td>
                     <%interest=(Double)request.getAttribute("Interest");
                       if(interest!=null){
                     %>
									<td><html:text property="interest" size="10" readonly="true" value="<%=""+interest%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>                     
                     				
                     	<%}else{ %>			
                    	
                     			<td><html:text property="interest" size="10" readonly="true" value="0.0" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                     	<%} %>			
                </tr>
                
                <tr>
                     <td><bean:message key="label.interestRate"></bean:message></td>
                     <core:choose>
                     	<%System.out.println("**********************   6   *******************");%>
                      	 <core:when test="${requestScope.PygmyRate!=null}">
                      	 	<td><html:text property="intrate" size="10" readonly="true" value="<%=""+pyObject.getIntRate() %>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="intrate" size="10" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>
                </tr>
                
                <tr>
                     <td><bean:message key="label.maxAmtwith"></bean:message></td>
                     <%	max_with=(Double)request.getAttribute("MaxWith");
                     	max_with_amt=(Double)request.getAttribute("par_max_amt");
                     	 
                     	if(max_with!=null){
                     %>
                     		    <td><html:text property="max_amt" size="10" readonly="true" value="<%=""+max_with%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                     <%}else if(max_with_amt!=null){
                    	 
                     %>
                     			<td><html:text property="max_amt" size="10" readonly="true" value="<%=""+max_with_amt %>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                     <%}else{%>
                     			<td><html:text property="max_amt" size="10" readonly="true" value="" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                     <%} %>			
                </tr>
                
                 <tr>
                     <td><bean:message key="label.withamt"></bean:message></td>
                      <%with_amt=(Double)request.getAttribute("WithAmt");
                        par_with_amt=(Double)request.getAttribute("par_max_amt");
                        with_allow=(Double)request.getAttribute("WithDrawAmt");
                     	if(with_amt!=null){
                      %>
                             <td><html:text property="withdrawal_amt" size="10" readonly="true" onchange="submit()" value="<%=""+with_amt%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                     <%}else if(par_with_amt!=null){
                     	
                     %>
                     		<td><html:text property="withdrawal_amt" size="10"  value="<%=""+par_with_amt %>" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()"></html:text></td>
                     <%}else{%>
                     		  <td><html:text property="withdrawal_amt" size="10"   styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()"></html:text></td>
                      <%} %>		  	
                </tr>
                
                 <tr>
                     <td><bean:message key="label.loanacc"></bean:message></td>
                     
                     	<%System.out.println("**********************   9   *******************");%>
                      	<% 
                        	String lnNum=(String)request.getAttribute("LoanNo");
                        	if(lnNum!=null){
                        	System.out.println("---3534-----"+lnNum);
                        %>
                        	<td><html:text property="loan_acc" size="10" onchange="submit()" value="N/A" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
                        <%}else if(pygmyMaster!=null) { %>
                       	 	<td><html:text property="loan_acc" size="10"  value="<%="ZL-"+pygmyMaster.getLnAccNo()%>" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
                        
                        <% } %>
                </tr>
                
                  <tr>
                     <td><bean:message key="label.loanbal"></bean:message></td>
                     <td>
                    	<%loan_bal=(String.valueOf(request.getAttribute("LoanBal"))); 
                 			
									if(request.getAttribute("LoanBal")!=null ){
										System.out.println("---- LoanBal----"+loan_bal);
                 						if(Double.parseDouble(loan_bal)!=0.0){
                					%>
                			 			<html:text property="loan_bal" size="10" onchange="submit()" value="<%=""+loan_bal%>" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" readonly="true"></html:text>                  
                					<%}else{ %>

										<%System.out.println("aaaaaaaafffffffffttttttttt"); %>
                 						<html:text property="loan_bal" size="10" onchange="submit()" value="0.0" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" readonly="true"></html:text>
                   					<%}}%>
                     				
                     <%loan_int=(String.valueOf(request.getAttribute("LoanInt")));
                     if(request.getAttribute("LoanInt")!=null ){
                     if(Double.parseDouble(loan_int)!=0.0){
                    	 
                     %>
                     		<html:text property="loan_int" size="10" readonly="true"  value="<%=""+loan_int %>" styleClass="formTextFieldWithoutTransparent"></html:text>
                     <%}else{ %>	
                     		<html:text property="loan_int" size="10" readonly="true"  value="0.0" styleClass="formTextFieldWithoutTransparent"></html:text>
                     <%}}%>	</td>
                 </tr>    	
                
                <tr>
                
                	<td><bean:message key="label.finesother"></bean:message></td>
                     <%
                     	fine_amts=(Double)request.getAttribute("FINE");
                     	System.out.println("----322423$^%%&%^-----"+fine_amts);
                     	
                     	if(fine_amts!=null){
                     %>
                     		<td><html:text property="fines" size="10" readonly="true"  value="<%=""+fine_amts%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                     <%}else{%>
                     		<td><html:text property="fines" size="10" readonly="true"  value="0.0" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                     <%}%> 		
                </tr>
                
                <tr>
                
               		<td><bean:message key="label.paymentmode"></bean:message></td>
               		<td><html:select property="paymode" styleClass="formTextFieldWithoutTransparent" onchange="disEnable()">
                  		 <%paymode=(String[])request.getAttribute("PayMode");%>
                  			<core:forEach var="paymode" items="${requestScope.PayMode}" varStatus="count">
								<html:option value="${paymode}">${paymode}</html:option>
						 			<%System.out.println("Helloooooooooooooooooo"); %>
                			</core:forEach>
                   		</html:select>
               		</td>
               </tr>
               
               <tr id="Transfer" style="display:block;">
                     <td><bean:message key="label.paymentactypeno"></bean:message></td>
                     <td><html:select  property="payactypeno" styleClass="formTextFieldWithoutTransparent">
                         <%array_module=(ModuleObject[])request.getAttribute("PayAcType");%>
                         	
                        	       
                            	      <core:forEach var="payactypeno" items="${requestScope.PayAcType}" varStatus="count">
                                	     <html:option value="${payactypeno.moduleCode}">${payactypeno.moduleAbbrv}</html:option>
                                      </core:forEach>
                                      
                           
                           </html:select> 	 
                        

					 
                     <core:choose>
                      	  <core:when test="${requestScope.PygmyWithDetails!=null}">
                        		<html:text property="payno" size="10"  onchange="submit()"  styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')"></html:text>
                          </core:when>
                          
                          <core:otherwise>
                         		<html:text property="payno" size="10"  onblur="submit()" value="0" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')"></html:text>
                          </core:otherwise>		
                     </core:choose></td>
                </tr>
               
                 <tr id="Transfer1" style="display:block;">
                     <td><bean:message key="label.selfname"></bean:message></td>
                     <%String name=(String)request.getAttribute("SelfName"); %>
                     <core:choose>
                       	  <core:when test="<%=name!=null %>">
                        		<td><html:text property="name" size="10"   disabled="true" value="<%=name%>" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
                          </core:when>
                      	  <core:otherwise>
                                <td> <html:text property="name" size="10"   styleClass="formTextFieldWithoutTransparent"></html:text></td>
                      	  </core:otherwise>
                      </core:choose>	  
                </tr>
                
            <tr>
               <td><bean:message key="label.details"></bean:message></td>
               <td><html:select property="pygdetails" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
                   <html:option value="SELECT"></html:option>
                    <% pygdetails=(String[])request.getAttribute("PygmyComboDetails");
                            for(int i=0;i<pygdetails.length;i++){%>
                                <html:option value="<%=""+pygdetails[i]%>"></html:option>
                        <%}%>
                	</html:select>
               </td>
           </tr>
                <tr>
                <td>
                	<%if(enable!=null){ %>
 <table   class="txtTable">
  <html:hidden property="nomforward"></html:hidden>
  
  <html:hidden property="nomvalidations"></html:hidden>
  <tr>
    <td>Has Account</td>
    <td><html:checkbox property="has_ac" styleClass="formTextFieldWithoutTransparent"></html:checkbox>
     </td>
     
   </tr>
   
  <tr>
    <td><bean:message key="label.custid"></bean:message></td>
    
               		
               
               		<td><html:text property="cidis" size="10"  onblur="checksub('Cidis')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
               
    	
    	<!--<td><html:text property="cidis"  styleClass="formTextFieldWithoutTransparent" onblur="submit()"></html:text> </td>
   
   --></tr>	
           
   <tr>
    <td><bean:message key="label.name"></bean:message></td>
   
    <td><html:text property="nomname"  styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text> </td>
   
    
   </tr>
   
   <tr>
     <td><bean:message key="label.dob"></bean:message> </td>
     
     <td><html:text property="dob"  styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text> </td>
    
   </tr>
   
   <tr>
    <td><bean:message key="label.gender"></bean:message></td>
    
    <td><html:text property="gender" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
   
   </tr>
   
   <tr>
    <td><bean:message key="label.address"></bean:message></td>
     
    <td><html:textarea property="address" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:textarea></td>
     
   </tr>
   
   <tr>
     <td><bean:message key="label.rel"></bean:message></td>
     <td><html:text property="rel_ship"  styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
   </tr>

   <tr>
    <td><bean:message key="label.percentage"></bean:message></td>
    <td><html:text property="percentage"  styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
   </tr>
         
</table>            
<%} %>
                </td>
                </tr>
                <tr>
                	 
                    	<%
                	        if (value != null) {
                	        	if (Integer.parseInt(value) == 2) {
                	   	%>
                	   	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            			<td><html:submit onclick="set('verify')" styleClass="ButtonBackgroundImage">Verify</html:submit>
             			<%}else{ %>
            			 <td><html:submit onclick="set('verify')" disabled="true" styleClass="ButtonBackgroundImage">Verify</html:submit>
             			<%} %>
                     			
                    			<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
                    	<%
                        	} else if (Integer.parseInt(value) == 1) {
                    	%>	
                    	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
           				<td><html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage">Submit</html:submit>
            			 <%}else{ %>
          				<td><html:submit onclick="set('submit')" disabled="true" styleClass="ButtonBackgroundImage">Submit</html:submit>
             			<%} %>
                    	<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
                    	<%
                    		}
                    	%>	
                    	<%
	                    	} else {
	                    %>	
                    	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
           				<td><html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage">Submit</html:submit>
            			 <%}else{ %>
          				<td><html:submit onclick="set('submit')" disabled="true" styleClass="ButtonBackgroundImage">Submit</html:submit>
             			<%} %>
                    			<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
                    	<%
                    	}
                    	%>	
                    	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
           				<td><html:submit onclick="set('delete')" styleClass="ButtonBackgroundImage">Delete</html:submit></td>
           			 	 <%}else{ %>
           				<td><html:submit onclick="set('delete')" disabled="true" styleClass="ButtonBackgroundImage">Delete</html:submit></td>
            			 <%} %>
                    		
                    	 
                   		
                </tr>
           </table></td>
           				<html:hidden property="defaultTab" value="Personal"/>
           				
           				<html:hidden property="forward"/>
           				<html:hidden property="value"/>
           				<html:hidden property="validate"/>
           			<td>
                   
                     <%
                         jspPath=(String)request.getAttribute("flag");
                         if(jspPath!=null){
                     %>
                         <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>

                     <%  } %>
                     
               </td>
           </table>   
           				 
	</html:form>
	<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>