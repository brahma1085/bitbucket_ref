
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.pygmyDeposit.PygmyMasterObject" %>
<%@ page import="masterObject.general.AccountObject"%>
<%@ page import="masterObject.general.AddressTypesObject"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
 
<%@page import="masterObject.pygmyDeposit.AgentMasterObject"%>
<html>
<head>
<title></title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
        <h2 class="h2"><center>Pygmy Master Update</center></h2>
      
   
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
         		alert("only 7 digits allowed")
         		txt.value="";
         		return false;
          	}
         }
   		function generateAcno(){
   		
        var val=document.forms[0].validation.value;
        
        if( val=="" ){
   			
   			 return false; 
   		}
   		else { 
   			 alert(val);
   		}
   			clearMethod();
  		}
  		
  	function disEnable(val){
    if(val.value=="Cash"){
    
    document.forms[0].payactypeno.disabled = true;
    document.forms[0].payno.disabled = true;
  }
  else{
  
  	document.forms[0].payactypeno.disabled = false;
    document.forms[0].payno.disabled = false;
  
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
        	}	
        };
        
        function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  >= 47 && cha < 58  ) ) {
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
      
          
 function setFlag(flagValue){

	document.forms[0].flag.value=flagValue;
 	document.forms[0].submit();
 
 }        
   </script>
</head>
<body class="Mainbody">
<%! ModuleObject[] array_module; 
	String details[];
	String pay_mode[];
	String addr_Type[];
	PygmyMasterObject pygmyMaster;
	AddressTypesObject[] addr_obj=null;
	AgentMasterObject agm;
	
	AccountObject acntObject=null,acntObject1=null;
	String jspPath;
	String name1,name;
	String index;
	
%>

<%	pygmyMaster=(PygmyMasterObject)request.getAttribute("PygmyUpdateDetails"); 
	System.out.println("Hey plz display for me-----"+pygmyMaster);
	acntObject=(AccountObject)request.getAttribute("AccountDetail");
	System.out.println("Hey plz display for me-----"+acntObject);
	agm=(AgentMasterObject)request.getAttribute("AgentInfo");
%>

<html:form action="/Pygmy/PygmyUpdate?PageId=8019">
     <table class="txtTable">
     	<td>
            <table class="txtTable">
           		<tr>
                     <td><bean:message key="label.pygmyactypeno"></bean:message></td>
                     <td><html:select  property="pyg_Type" styleClass="formTextFieldWithoutTransparent">
                         <html:option value="SELECT">SELECT</html:option>
                         <% array_module=(ModuleObject[])request.getAttribute("PygmyAcType");
 							System.out.println("here i am=="+array_module); %>                       
							<core:forEach var="pyg_Type" items="${requestScope.PygmyAcType}" varStatus="count">
								<html:option value="${pyg_Type.moduleCode}">${pyg_Type.moduleAbbrv}</html:option>
                        	</core:forEach>
                         </html:select></td>
                </tr>

                <tr>
                     <td><bean:message key="label.pygno"></bean:message></td>
                      <core:choose>
                      	 <core:when test="${requestScope.PygmyUpdateDetails!=null}">
                      	 	<td><html:text property="pyg_no" onchange="submit()" size="10" value="${requestScope.PygmyUpdateDetails.accNo}" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'6')"></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="pyg_no" onchange="submit()" size="10" value="0" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'6')"></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>
                </tr>
                
               	<tr>
               		 <td><bean:message key="label.pygname"></bean:message></td>
                      <core:choose>
                      	 <core:when test="${requestScope.PygmyUpdateDetails!=null}">
                      	 	<td><html:text property="pyg_name"  onchange="submit()" value="${requestScope.PygmyUpdateDetails.name}" readonly="<%=(Boolean)request.getAttribute("Disabled")%>" styleClass="formTextField"></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="pyg_name" size="10" onchange="submit()"  styleClass="formTextField"></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>
               	</tr>
               	
               	<tr>	
               		<td><bean:message key="label.custid"></bean:message></td>
               		<core:choose>
                      	 <core:when test="${requestScope.PygmyUpdateDetails!=null}">
                      	 	<td><html:text property="cust_id" size="10"  value="${requestScope.PygmyUpdateDetails.cid}" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()"></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="cust_id" size="10"  styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()"></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>
               				
				</tr>
				
				
				<%System.out.println("----1----");%>
					
				<tr>
					<td><bean:message key="label.introduceractypeno"/></td>		
					<td><html:select  property="ag_Type" styleClass="formTextFieldWithoutTransparent" >
                         
                         <% array_module=(ModuleObject[])request.getAttribute("AgentAcType"); %>                       
							<core:choose>
                         	   <core:when test="<%=pygmyMaster!=null%>">
                         	      <core:if test="<%=(pygmyMaster.getAgentType().trim().length()!=0) %>">                      
									<core:forEach var="ag_Type" items="${requestScope.AgentAcType}" varStatus="count">
										<html:option value="${ag_Type.moduleCode}">${ag_Type.moduleAbbrv}</html:option>
                        			</core:forEach>
                        		   </core:if>
                        	   </core:when>  	   
                        	    
                         	   <core:otherwise>
                         	   <html:option value="SELECT">SELECT</html:option>
                            	      <core:forEach var="ag_Type" items="${requestScope.AgentAcType}" varStatus="count">
                                	     <html:option value="${ag_Type.moduleCode}">${ag_Type.moduleAbbrv}</html:option>
                                      </core:forEach>	
                               </core:otherwise>	
                           	 </core:choose>	
                         </html:select>

                      <core:choose>
                      	 <core:when test="${requestScope.PygmyUpdateDetails!=null}">
                      	 	<td><html:text property="ag_no" size="10" onchange="submit()" value="${requestScope.PygmyUpdateDetails.agentNo}" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()"></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="ag_no" size="10" onchange="submit()"  styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()"></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>  </td>
                         
                </tr>     
               <%System.out.println("----2----");%>
               
                <tr>
                	<td><bean:message key="label.agentname"/></td>
                	
                    <core:choose>
                      	 <core:when test="${requestScope.PygmyUpdateDetails!=null}">
                      	 	<%System.out.println("Display AgName--------"+pygmyMaster.getAgentName()); %>
                      	 	<td><html:text property="ag_Name" onchange="submit()"  value="<%=pygmyMaster.getAgentName()%>" styleClass="formTextField"></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<core:if test="<%=agm!=null %>">
   							<td><html:text property="ag_Name"   value="<%=agm.getName() %>" onchange="submit()" styleClass="formTextFieldWithoutTransparent"></html:text></td></core:if>
                         </core:otherwise>	 	
                     </core:choose>
                	
                			
                </tr>
                <%System.out.println("----3----");%>
                <tr>
                	<td><bean:message key="label.openingdate"/></td>
                	<core:choose>
                      	 <core:when test="${requestScope.PygmyUpdateDetails!=null}">
                      	 	<td><html:text property="op_date" size="10"  value="${requestScope.PygmyUpdateDetails.accOpenDate}" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="op_date" size="10"  styleClass="formTextFieldWithoutTransparent" ></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>
                </tr>
               <%System.out.println("----4----");%>
               
                  <tr>
                	<td><bean:message key="label.status"></bean:message></td>
                	<core:choose>
                      	 <core:when test="${requestScope.PygmyUpdateDetails!=null}">
                			  <core:if test="<%=pygmyMaster.getAccStatus().equalsIgnoreCase("O") %>">
                      	 		<%System.out.println("<----->"+pygmyMaster.getAccStatus()); %>
                      	 		<td><html:text property="ac_Status" size="10"  value="OPEN" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
                      	 	</core:if>
                      	 	<core:if test="<%=pygmyMaster.getAccStatus().equalsIgnoreCase("C") %>" >
                      	 		
                      	 		<td><html:text property="ac_Status" size="10"  value="CLOSE" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
                      	 	</core:if>	
                      	 	<core:if test="<%=pygmyMaster.getAccStatus().equalsIgnoreCase("V") %>" >
                      	 		
                      	 		<td><html:text property="ac_Status" size="10"  value="OPEN" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
                      	 	</core:if>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="ac_Status" size="10"   styleClass="formTextFieldWithoutTransparent" ></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>
                </tr>
                
                <tr>
                	<td><bean:message key="label.last_int_date"/></td>
                	<%if(pygmyMaster!=null){ %>
               				<td><html:text property="lst_int_date" size="10"  value="<%=pygmyMaster.getLastIntrDate() %>" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
               		<%}else{ %>
               				<td><html:text property="lst_int_date" size="10"   styleClass="formTextFieldWithoutTransparent" ></html:text></td>
               		<%} %>	
               	</tr>	
               	
               	<tr>
                	<td><bean:message key="label.loanAvail"/></td>
                	<%if(pygmyMaster!=null){ 
                		if((pygmyMaster.getLnAvailed().equalsIgnoreCase("T"))){
                	%>
               				<td><html:text property="loan_avail" size="10"  value="YES" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
               			<%}else{ %>
               				<td><html:text property="loan_avail" size="10"  value="NO" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
               		<%}} %>	
               	</tr>	
               	
               	<tr>
                	<td><bean:message key="label.loanacc"/></td>
                	
                	<%if(pygmyMaster!=null){ %>
               				<td><html:text property="loan_ac_Type" size="10"  value="ZL" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
               		<%}else{ %>
               				<td><html:text property="loan_ac_Type" size="10"   styleClass="formTextFieldWithoutTransparent" ></html:text></td>
               		<%} %>	
               		
               		<%if(pygmyMaster!=null){ %>
               				<td><html:text property="loan_ac_No" size="10"  value="<%=""+pygmyMaster.getLnAccNo()%>" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
               		<%}else{ %>
               				<td><html:text property="loan_ac_No" size="10"  styleClass="formTextFieldWithoutTransparent" ></html:text></td>
               		<%} %>	
               	</tr>	
               	
               	<tr>
                
               		<td><bean:message key="label.paymentmode"></bean:message></td>
               		<td><html:select property="pay_mode" styleClass="formTextFieldWithoutTransparent" onchange="disEnable(this)">
                  		 <%pay_mode=(String[])request.getAttribute("PayMode");%>
                  			<core:forEach var="pay_mode" items="${requestScope.PayMode}" varStatus="count">
								<html:option value="${pay_mode}">${pay_mode}</html:option>
						 			<%System.out.println("Helloooooooooooooooooo"); %>
                			</core:forEach>
                   		</html:select>
               		</td>
               </tr>
               
               <tr>
               <td><bean:message key="label.paymentactypeno"></bean:message></td>
               <td><html:select property="pay_ac_Type" styleClass="formTextFieldWithoutTransparent " >
               		<%array_module=(ModuleObject[])request.getAttribute("PayAcType");%>
                   <core:choose>
                       <core:when test="<%=pygmyMaster!=null&&pygmyMaster.getPayAccType()!=null%>">
                         	  <core:if test="<%=(pygmyMaster.getPayAccType().trim().length()!=0) %>">
                               		<core:forEach var="pay_ac_Type" items="${requestScope.PayAcType}" varStatus="count">
                               			<core:if test="${requestScope.PygmyDetails.payAccType==payactypeno.moduleCode}">
     										<html:option value="${requestScope.PayAcType[count.index].moduleCode}">${requestScope.PayAcType[count.index].moduleAbbrv}</html:option>
                        				</core:if>
                        		    </core:forEach>
                        	     </core:if>
                        	   </core:when>  	    
                         	   <core:otherwise>
                         	   		<html:option value="SELECT">SELECT</html:option>
                            	      <core:forEach var="pay_ac_Type" items="${requestScope.PayAcType}" varStatus="count">
                                	     <html:option value="${pay_ac_Type.moduleCode}">${pay_ac_Type.moduleAbbrv}</html:option>
                                      </core:forEach>
                              </core:otherwise>	
                   </core:choose>
             </html:select>  	
                        
               <core:choose>
               
                  <core:when test="${requestScope.PygmyUpdateDetails!=null}">   
                  	     <html:text property="pay_ac_no" size="10"  value="${requestScope.PygmyUpdateDetails.payAccNo}"  styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()"></html:text>
                  </core:when>
                  <core:otherwise>
                  		<html:text property="pay_ac_no" size="10"   styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" ></html:text>
                  </core:otherwise>	
               </core:choose></td>		
           </tr>
           <%System.out.println("----10----");%>
           <tr>
               <td><bean:message key="label.selfname"></bean:message></td>
               
                <core:choose>
                  <core:when test="${requestScope.AccountDetail!=null}"> 
               			<td><html:text property="pay_ac_name"  styleClass="formTextField" onchange="submit()" value="<%=acntObject.getAccname()%>" readonly="true"></html:text></td>
                   </core:when>
                    
                  <core:otherwise>	
                        <td><html:text property="pay_ac_name"  styleClass="formTextField" onchange="submit()" value=""></html:text></td>
                  </core:otherwise>	
               </core:choose>        		
           </tr>
           
           <tr>
                <td><bean:message key="label.clDate"/></td>
                	
               	<td> <core:choose>	
                  <core:when test="${requestScope.PygmyUpdateDetails!=null}">   
                  	     <html:text property="close_date" size="10"  value="${requestScope.PygmyUpdateDetails.accCloseDate}" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
                  </core:when>
                  <core:otherwise>
                  		<html:text property="close_date" size="10"  value="0" styleClass="formTextFieldWithoutTransparent" ></html:text>
                  </core:otherwise>	
                </core:choose></td>
                
           </tr>  
           
           <tr>
               <td><bean:message key="label.details"></bean:message></td>
               <td><html:select property="details"  styleClass="formTextFieldWithoutTransparent">
                   <html:option value="SELECT"></html:option>
                    <% details=(String[])request.getAttribute("Details");%>
                    	<core:forEach var="details" items="${requestScope.Details}" varStatus="count">        
                                <html:option value="${details}"></html:option>
                        </core:forEach>        
                   </html:select>
               </td>
           </tr>    
               				
              	<html:hidden property="defaultSignIndex" value="0"/>
       			<html:hidden property="defaultTab" value="Personal"/>
     			<html:hidden property="defSIndex" value="1"/>
              	<html:hidden property="flag" value=""/> 
             	<html:hidden property="forward" value="error"/>
                 <td><html:submit onclick="set('update')"  styleClass="ButtonBackgroundImage">Update</html:submit>
                     <html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button>
                 </td>     
                    
		</table>
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
</body>
</html>