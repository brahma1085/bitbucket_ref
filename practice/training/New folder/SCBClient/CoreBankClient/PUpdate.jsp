<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@page import="java.util.Map"%>

<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.pygmyDeposit.PygmyMasterObject"%>
<%@page import="masterObject.general.AddressTypesObject"%>
<%@page import="masterObject.pygmyDeposit.AgentMasterObject"%>
<%@page import="masterObject.general.AccountObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
        <h2 class="h2"><center>Pygmy Master Update</center></h2>
      
 <script type="text/javascript">
   
   function set(target){
         if(document.forms[0].pyg_Type.value=="SELECT")
  		{
  			alert("Please select Pygmy Account Type!");
  			
  		}
  		else if(document.forms[0].pyg_no.value=="")
  		{
  			alert("Enter pygmy number");
  			
  		}
  		else{
       document.forms[0].forward.value=target;
       document.forms[0].submit();
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
   		function generateAcno(){
   		if(document.forms[0].pay_mode.value=="Cash"){
    document.forms[0].pay_ac_Type.disabled = true;
    	document.forms[0].pay_ac_no.disabled = true;
   		}else{
   		 document.forms[0].pay_ac_Type.disabled = false;
    	document.forms[0].pay_ac_no.disabled = false;
   		}
   		
        var val=document.forms[0].validation.value;
        
        if( val=="" ){
   			
   			 return false; 
   		}
   		else { 
   			 alert(val);
   		}
   			
   			clearMethod();
  		};
  		
  		
  		function chk(){
  			var val=document.forms[0].flag.value;
  			if( val=="" ){
   			
   			 return false; 
   		}
   		else { 
   			 alert(val);
   		}
  		}
  		
  	function disEnable(val){
    if(document.forms[0].pay_mode.value=="Cash"){
    	document.forms[0].pay_ac_Type.disabled = true;
    	document.forms[0].pay_ac_no.disabled = true;
 	 }
 	 else if(document.forms[0].pay_mode.value=="PayOrder"){
    	document.forms[0].pay_ac_Type.disabled = true;
    	document.forms[0].pay_ac_no.disabled = true;
  	}
  	else{
  
  	document.forms[0].pay_ac_Type.disabled = false;
    document.forms[0].pay_ac_no.disabled = false;
  
  	}
  	}
        
   
        
         
         
         
        function clearMethod()
        { 
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
        	}	
        	document.forms[0].address.value="";
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
 
  	function checksub(target)
   	{
   	document.forms[0].forward.value=target;
   	document.forms[0].submit();
   	}     
      
      
 function setFlag(flagValue){

	document.forms[0].flag.value=flagValue;
 	document.forms[0].submit();
 
 }        
   </script>
</head>



<body class="Mainbody" onload="generateAcno()">
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

<%	pygmyMaster=(PygmyMasterObject)request.getAttribute("PygmyUpdateDetails"); 
	System.out.println("Hey plz display for me-----"+pygmyMaster);
	acntObject=(AccountObject)request.getAttribute("AccountDetail");
	System.out.println("Hey plz display for me-----"+acntObject);
	agm=(AgentMasterObject)request.getAttribute("AgentInfo");
	enable=(String)request.getAttribute("enable");
%>
<%
String msg=(String)request.getAttribute("msg");
if(msg!=null){
%>
<font color="red"><%=msg %></font>
<br><br>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Pygmy/PUpdate?pageid=8090">
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
                     <td><html:text property="pyg_no" size="10" onblur="checksub('PygmyAccNo')" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text></td>
                     </tr>
                
               	<tr>
               		 <td><bean:message key="label.pygname"></bean:message></td>
                     <td><html:text property="pyg_name"   readonly="<%=(Boolean)request.getAttribute("Disabled")%>" styleClass="formTextField"></html:text></td>
               	</tr>
               	
               	<tr>	
               		<td><bean:message key="label.custid"></bean:message></td>
                    <td><html:text property="cust_id" size="10" onblur="checksub('CustId')" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" readonly="true"></html:text></td>
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

                      	 	<html:text property="ag_no" size="10" onblur="checksub('AgentNo')"  styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" readonly="true"></html:text>
                      </td>
                </tr>     
               <%System.out.println("----2----");%>
               
                <tr>
                	<td><bean:message key="label.agentname"/></td>
                      	 	<td><html:text property="ag_Name"  styleClass="formTextField" readonly="true"></html:text></td>
                </tr>
                <%System.out.println("----3----");%>
                <tr>
                	<td><bean:message key="label.openingdate"/></td>
                    <td><html:text property="op_date" size="10"  styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly_date(this)" readonly="true"></html:text></td>
                </tr>
               <%System.out.println("----4----");%>
               
                  <tr>
                	<td><bean:message key="label.status"></bean:message></td>
                      	 		<td><html:text property="ac_Status" size="10"  styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
                </tr>
                
                <tr>
                	<td><bean:message key="label.last_int_date"/></td>
               		<td><html:text property="lst_int_date" size="10"  styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
               	</tr>	
               	
               	<tr>
                	<td><bean:message key="label.loanAvail"/></td>
               		<td><html:text property="loan_avail" size="10" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
               	</tr>	
               	
               	<tr>
                	<td><bean:message key="label.loanacc"/></td>
                	<td>
               				<html:text property="loan_ac_Type" size="10"  value="ZL" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>
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
                        
               <html:text property="pay_ac_no" size="10" onblur="checksub('PayAccNo')" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" readonly="true"></html:text>
           </tr>
           <%System.out.println("----10----");%>
           <tr>
               <td><bean:message key="label.selfname"></bean:message></td>
               	<td><html:text property="pay_ac_name"  styleClass="formTextField"  readonly="true"></html:text></td>
           </tr>
           
           <tr>
                <td><bean:message key="label.clDate"/></td>
               	<td> 
                  	     <html:text property="close_date" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly_date(this)" readonly="true"></html:text>
                </td>
                
           </tr>  
           
           <tr>
               <td><bean:message key="label.details"></bean:message></td>
               <td><html:select property="details"  styleClass="formTextFieldWithoutTransparent" onchange="submit()">
                   <html:option value="SELECT"></html:option>
                    <% details=(String[])request.getAttribute("Details");%>
                    	<core:forEach var="details" items="${requestScope.Details}" varStatus="count">        
                                <html:option value="${details}"></html:option>
                        </core:forEach>        
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
    <%if(pygmyMaster!=null){%>
               		<td><html:text property="cidis" size="10" onblur="checksub('Cidis')" readonly="true" value="<%=""+pygmyMaster.getCid()%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
               <%}else{ %>	
               		<td><html:text property="cidis" size="10"  onblur="checksub('Cidis')" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
               <%}%>
    	
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
              	
              	<html:hidden property="flag" value=""/> 
             	<html:hidden property="forward" value="error"/>
             	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
            <td><html:submit onclick="set('update')"  styleClass="ButtonBackgroundImage">Update</html:submit>
             <%}else{ %>
           <td><html:submit onclick="set('update')" disabled="true" styleClass="ButtonBackgroundImage">Update</html:submit>
             <%} %>
                
                     <html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button>
                 </td>     
                    
		</table>
		 <td>
                   
                     <%
                         jspPath=(String)request.getAttribute("flag");
                     	  System.out.println("---->>>>"+jspPath);
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