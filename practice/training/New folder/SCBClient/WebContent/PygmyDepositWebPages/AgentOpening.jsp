
<%@ page import="com.scb.pd.forms.AgentOpeningForm" %>
<%@ page import="com.scb.common.forms.PersonnelForm" %>
<%@ page import="com.scb.common.forms.SignatureInstructionForm" %>
<%@ page import="com.scb.pd.forms.SelfActypeForm" %>
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.pygmyDeposit.AgentMasterObject" %>
<%@ page import="masterObject.general.AccountObject"%>
<%@ page import="masterObject.customer.CustomerMasterObject" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>

<%@page import="com.scb.common.help.Date"%>
<%@page import="java.util.Map"%>
<html>
  <head><script type="text/javascript">

function fun_clear()
{
	
	var ele=document.forms[0].elements;
	for(var i=0;i<ele.length;i++)
	{
		
		if(ele[i].type=="text")
		{
			ele[i].value="";
		}
		document.getElementById("agentcode").selectedIndex='0';
		}
	
}

</script><title></title>
     <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
        <h2 class="h2"><center>Agent Opening</center></h2>
      
   
   <script type="text/javascript">
   
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
        function checksub()
   		{
   			if(document.forms[0].selfAcno.value==0)
   			{
   			alert("Enter SelfAccount Number");
   			return false;
   			}
   			else if(document.forms[0].jointAcno.value==0)
   			{
   			alert("Enter JointAccount Number");
   			return false;
   			}
   			else if(document.forms[0].introducerAcno.value==0)
   			{
   			alert("Enter IntroducerAccount Number");
   			return false;
   			}
   			else
   			{
   			document.forms[0].forward.value='Submit';
   			document.forms[0].submit();
   			}
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
         
         
         
         function datevalidation(ids){
    	var format = true;
        var allow=true;
    	var ff=ids.value;
    	var dd=ff.split('/');
    	
    	var ss=document.forms[0].sysdate.value;
    	var dds=ss.split('/');
    	
    	if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          alert (  " Date format should be:DD/MM/YYYY" );
                         ids.value="";
                          format = false ;
                          allow=false;
                          

                      } else if (  i==2 && dd[i].length != 4 ){

                           alert (  " Date format should be:DD/MM/YYYY " );
                          ids.value="";
                           format = false ;
                           allow=false;
                      }

            	 }
             } else{
                    allow=false;
             		format = false ;
             		alert (  " Date format should be:DD/MM/YYYY " );
             		document.forms[0].fdate.value="";
             }
            if(allow){
            	
            	var day=dd[0];
            	var month=dd[1];
            	var year=dd[2];
            	var fdays;
            	if(month==2)
            	{
            	if((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0)))
            	{
            		if(day>29)
            		{
            			alert("Days should be less than 29 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	else
            	{
            		if(day>28)
            		{
            			alert("Days should be less than 28 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	}
            	
            	if(month>1 || month<12){
            	if(month == 4 || month==6||month==9||month==11)
            	{
            		if(day>30)
            		{
            			alert("Days should be less than 31 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	else
            	{
            		if(day>31)
            		{
            			alert("Days should be less than 32 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            		
            	}
            	}
            	if(month>12)
            	{
            		alert("Month should  be greater than 0 and lesser than 13");
            		ids.value="";
                           format = false ;
                           allow=false;
            	}
            	
            	if(year<1900 || year>9999)
            	{
            		alert("Year should be in between 1900 to 9999 ");
            		ids.value="";
                           format = false ;
                           allow=false;
            	}
            	if(dd[0].length==2||dd[1].length==2||dd[2].length==4)
            	{
            		if(dd[2]>dds[2])
            		{
            			alert(" year should be less than or equal to"+dds[2]+" !");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}	
            		else{
            			if(dd[2]==dds[2]){
            				if(dd[1]>dds[1]){
	            				alert(" Month should be less than or equal to"+dds[1]+" !");
	            				ids.value="";
	                           	format = false ;
	                          	 allow=false;		
            						
            				}else{
            					if(dd[1]==dds[1]){
            						if(dd[0]>dds[0]){
											alert(" Day should be less than or equal to"+dds[0]+" !");
				            				ids.value="";
				                           	format = false ;
				                          	allow=false;           							
            						}else{
            								format = true ;
				                          	allow=true; 
            						}
            					
            					}
            				}
            			}
            		}
            	}            	          	
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
        	document.forms[0].agentnum.value=0;
        };
        
        function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) ) {
				return true ;
                
            	}
            else{
              	
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
   
   
	var dt=document[0].date;
	
	
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

 <body class="Mainbody" onload="generateAcno()">

 
    
      <%!
           
          ModuleObject[] array_module;
          String details[];
          AgentMasterObject agentMaster;
          PersonnelForm form;
          AccountObject acntObject;
          AccountObject selfObject;
          AccountObject jointObject;
          AccountObject intrObject;
          String jspPath;
          String jname;
          String name1;
          Boolean flag=false,flag1;
          CustomerMasterObject cmObj;
          SignatureInstructionForm siForm;
          String value,action,disabled;
          boolean flag2=true;
      %>
     <% 
        agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");
        System.out.println("here i am=="+agentMaster);
     	acntObject=(AccountObject)request.getAttribute("Introducer Details");
     	System.out.println("here i am=="+acntObject);
     	selfObject=(AccountObject)request.getAttribute("SelfAccountDetails");
     	jointObject=(AccountObject)request.getAttribute("JointAccountDetails");
     	intrObject=(AccountObject)request.getAttribute("IntrAccountDetails");
     	cmObj=(CustomerMasterObject)request.getAttribute("personalInfo");
     	System.out.println("^^^^^^CMOBJ^^^"+cmObj);
     	value=(String)request.getAttribute("VALUE");
     	disabled=(String)request.getAttribute("disabled");
     %>
     <%if(disabled!=null){
     flag2=true;
     }
     else
     {
     flag2=false;
     }
      %>
     <%if(value!=null){
    	 if(Integer.parseInt(value.trim())==2){
    		 action="/Pygmy/AgentOpening?PageId=8001&value=2";
    		 flag1=true;
    	 }else if(Integer.parseInt(value.trim())==1){
    		 action="/Pygmy/AgentOpening?PageId=8001&value=1";
    		 flag1=false;
    	 }
     }
    	 %>
    	 <% String msg=(String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<font color="red"><%=msg %></font>
		<br><br>
		<%} %>
     


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
				System.out.println("access-----In AgentOpening------>"+access);
			}

			
			}
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
     <html:form action="/Pygmy/AgentOpening?pageid=8801">
     <table class="txtTable">
     	<td>
            <table class="txtTable">
           		<tr>
                     <td><bean:message key="label.agentcode"></bean:message></td>
                     <td><html:select property="agentcode" styleClass="formTextFieldWithoutTransparent">
                         
                         <% array_module=(ModuleObject[])request.getAttribute("AgentAcType");
 							System.out.println("here i am=="+array_module); %>                       
							<core:forEach var="agentcode" items="${requestScope.AgentAcType}" varStatus="count">
								<html:option value="${agentcode.moduleCode}">${agentcode.moduleAbbrv}</html:option>
                        	</core:forEach>
                         </html:select></td>
                </tr>

                <tr>
                     <td><bean:message key="label.agentno"></bean:message></td>
                     <core:choose>
                      	 <core:when test="${requestScope.AgentDetails!=null}">
                      	 	<td><html:text property="agentnum" size="10" value="${requestScope.AgentDetails.agentNo}" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="agentnum" size="10" onblur="set('AgentNo')"  styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>
                </tr>

                <tr>
                     <td><bean:message key="label.agentname"></bean:message></td>
                     <%name1=(String)request.getAttribute("NewAgent"); %>
                     
                     <%System.out.println("!!!!!!!!!!!!!!!!!!!!="+name1); %>
                      <core:choose>
                      <%agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");%>
                      	  <core:when test="${requestScope.AgentDetails!=null}">
                        	<td><html:text property="agentname" onchange="submit()"  value="<%=agentMaster.getName()%>"  styleClass="formTextField" readonly="true"></html:text></td>
                          </core:when> 
                          <core:when test="${requestScope.NewAgent!=null}">
                         	<td><html:text property="agentname"  onblur="submit()"  value="<%=name1%>" styleClass="formTextField" readonly="true"></html:text></td>
                      	  </core:when>	
                          <core:otherwise> 
                            <td><html:text property="agentname"  onblur="submit()" value="<%=name1%>" styleClass="formTextField" readonly="true"></html:text></td>
                          </core:otherwise>	
                      </core:choose> 
                </tr>

                <tr>
                     <td><bean:message key="label.selfactypeno"></bean:message></td>
                     <td><html:select  property="selfactypeno" styleClass="formTextFieldWithoutTransparent">
                         <%array_module=(ModuleObject[])request.getAttribute("SelfAcType");%>
                         	<core:choose>
                         	<%agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");%>
                         	   <core:when test="<%=agentMaster!=null%>">
                         	     
                               		<core:forEach var="selfactypeno" items="${requestScope.SelfAcType}" varStatus="count">
                               			<% System.out.println("HIIIIIIIIIIIIIIIIIII"+agentMaster.getAccType()); %>
                            	   		<core:if test="${requestScope.AgentDetails.accType==selfactypeno.moduleAbbrv}">
     										<html:option value="${requestScope.SelfAcType[count.index].moduleCode}">${requestScope.SelfAcType[count.index].moduleAbbrv}</html:option>
                        				</core:if>
                        		    </core:forEach>
                        	     
                        	   </core:when>  	    
                         	   <core:otherwise>
                        	       <html:option value="SELECT">SELECT</html:option>
                            	      <core:forEach var="selfactypeno" items="${requestScope.SelfAcType}" varStatus="count">
                                	     <html:option value="${selfactypeno.moduleCode}">${selfactypeno.moduleAbbrv}</html:option>
                                      </core:forEach>	
                               </core:otherwise>	
                           	 </core:choose>
                           </html:select> 	 
                        

					 
                     <core:choose>
                     <%agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");%>
                      	  <core:when test="${requestScope.AgentDetails!=null}">
                        		<html:text property="selfAcno" size="10"   value="<%=""+agentMaster.getAccNo()%>" styleClass="formTextFieldWithoutTransparent" ></html:text>
                          </core:when>
                          <core:when test="${requestScope.SelfAccountDetails!=null}">
                          		<%System.out.println(" M in Checking Mode 1-------"+selfObject.getAccno()); %>
                        		<html:text property="selfAcno" size="10"  value="<%=""+selfObject.getAccno()%>" styleClass="formTextFieldWithoutTransparent" ></html:text>
                          </core:when>
                          <core:otherwise>
                          		<%System.out.println(" M in Checking Mode 2-------"); %>
                         		<html:text property="selfAcno" size="10"  onchange="submit()" value="0" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>
                          </core:otherwise>		
                     </core:choose></td>
                </tr>

                <tr>
                     <td><bean:message key="label.selfname"></bean:message></td>
                     <core:choose>
                     <%agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");%>
                       	  <core:when test="${requestScope.AgentDetails!=null}">
                        		<td><html:text property="selfname"  value="<%=""+agentMaster.getName()%>" styleClass="formTextField" readonly="true"></html:text> </td>
                          </core:when>
                      	  <core:when test="${requestScope.SelfAccountDetails!=null}">
                      	 		<td> <html:text property="selfname"  value="<%=selfObject.getAccname()%>" styleClass="formTextField" readonly="true"></html:text></td>
                          </core:when>
                          <core:otherwise>
                                <td> <html:text property="selfname"  onblur="submit()" styleClass="formTextField" readonly="true"></html:text></td>
                      	  </core:otherwise>
                      </core:choose>	  
                </tr>

                <tr>
                     <td><bean:message key="label.appointeddate"></bean:message></td>
                     <core:choose>
                     <%agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");%>
                      	  <core:when test="${requestScope.AgentDetails!=null}">
                            <td> <html:text property="date"  size="10" value="<%=agentMaster.getAppointDate()%>" styleClass="formTextFieldWithoutTransparent" onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)"></html:text></td>
                      	  </core:when>
                      	  <core:otherwise>
                      	  	<% String date=(String)request.getAttribute("Date");%>
                            <td><html:text property="date" size="10" value="<%=Date.getSysDate()%>"  styleClass="formTextFieldWithoutTransparent" onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)" ></html:text>
                            <%System.out.println("m in jsp page"+Date.getSysDate()); %>
                            	<font color="red"><bean:message key="label.date1"></bean:message></font>
                            </td>
                      	  </core:otherwise>
                     </core:choose> 	  
                </tr>

                <tr>
                     <td><bean:message key="label.jointactypeno"></bean:message></td>
                     <td><html:select  property="jointactypeno"  styleClass="formTextFieldWithoutTransparent">
                         
                         <%array_module=(ModuleObject[])request.getAttribute("JointAcType");%>
                         <core:choose>
                         <%agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");%>
                         	   <core:when test="<%=agentMaster!=null%>">
                         	      <core:if test="<%=(agentMaster.getJointAccType().trim().length()!=0) %>">
                         	      <%System.out.println("22222222222"); %>
                               		<core:forEach var="jointactypeno" items="${requestScope.JointAcType}" varStatus="count">
                               		  <core:if test="${requestScope.AgentDetails.jointAccType==jointactypeno.moduleAbbrv}">
                                        <html:option value="${requestScope.JointAcType[count.index].moduleCode}">${requestScope.JointAcType[count.index].moduleAbbrv}</html:option>
                          			  </core:if>
                        		    </core:forEach>
                        	     </core:if>
                        	   </core:when>  	
                          	   <core:otherwise>
                          			<html:option value="SELECT">SELECT</html:option>
                          			<%System.out.println("55555555"); %>
                          				<core:forEach var="jointactypeno" items="${requestScope.JointAcType}" varStatus="count">
                                   			 <html:option value="${jointactypeno.moduleCode}">${jointactypeno.moduleAbbrv}</html:option>
                           				</core:forEach>
                           	   </core:otherwise>
                           	 </core:choose>  			
                         </html:select>
                         
                      <core:choose>
                      <%agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");%>
                      	   <core:when test="${requestScope.AgentDetails!=null}">
                        		<html:text property="jointAcno" size="10" value="<%=""+agentMaster.getJointAccNo()%>" styleClass="formTextFieldWithoutTransparent"></html:text>
                          </core:when>
                      	  <core:when test="${requestScope.JointAccountDetails!=null}">
                           <html:text property="jointAcno" size="10" value="<%=""+jointObject.getAccno()%>" styleClass="formTextFieldWithoutTransparent"></html:text>
                          </core:when>
                          <core:otherwise>
                         	<html:text property="jointAcno" size="10" onkeypress=" return numbersOnly()" onblur="submit()" value="0" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')"></html:text>
                          </core:otherwise>
                     </core:choose></td>    
                
                </tr>
                

                <tr>
                     <td><bean:message key="label.jointname"></bean:message></td>
                     <%jname=(String)request.getAttribute("JName");%>
                     <%System.out.println("#################=JNANE"+jname); %>
                     <core:choose>
                     	  	
                      	  <core:when test="${requestScope.JName!=null}">
                        		<td><html:text property="jointname" size="50" readonly="true" value="<%=jname%>"  styleClass="formTextField"></html:text></td>
                          </core:when>
                          <core:otherwise>
                         		<td><html:text property="jointname" size="50" onchange="submit()" readonly="true" styleClass="formTextField"></html:text></td>
                       	  </core:otherwise>
                     </core:choose>  
                </tr>

                <tr>
                     <td><bean:message key="label.introduceractypeno"></bean:message></td>
                     <td><html:select  property="introduceractypeno" styleClass="formTextFieldWithoutTransparent">
                        
                            <%array_module=(ModuleObject[])request.getAttribute("IntroAcType");%>
                               <core:choose>
                               <%agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");%>
                         	   <core:when test="<%=agentMaster!=null%>">
                         	      <core:if test="<%=(agentMaster.getIntrAccType().trim().length()!=0) %>">
                               		<core:forEach var="introduceractypeno" items="${requestScope.IntroAcType}" varStatus="count">
                               		  <core:if test="${requestScope.AgentDetails.intrAccType==introduceractypeno.moduleCode}">
                                        <html:option value="${requestScope.IntroAcType[count.index].moduleCode}">${requestScope.IntroAcType[count.index].moduleAbbrv}</html:option>
                          			  </core:if>
                        		    </core:forEach>
                        	     </core:if>
                        	   </core:when>  	
                             
                             	<core:otherwise>
                          			<html:option value="SELECT">SELECT</html:option>
                          				<core:forEach var="introduceractypeno" items="${requestScope.IntroAcType}" varStatus="count">
                                   			 <html:option value="${requestScope.IntroAcType[count.index].moduleCode}">${requestScope.IntroAcType[count.index].moduleAbbrv}</html:option>
                           				</core:forEach>
                           	   </core:otherwise>
                           	 </core:choose>  			
                         </html:select>
                         
                      <core:choose>
                      <%agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");%>
                      	  <core:when test="${requestScope.AgentDetails!=null}">
                        		<html:text property="introducerAcno" size="10" value="<%=Integer.valueOf(agentMaster.getIntrAccNo()).toString()%>" styleClass="formTextFieldWithoutTransparent"></html:text> 
                          </core:when>
                      	  <core:when test="${requestScope.IntrAccountDetails!=null}">
                            <html:text property="introducerAcno" size="10" value="<%=""+intrObject.getAccno()%>" styleClass="formTextFieldWithoutTransparent"></html:text>
                       	  </core:when>
                          <core:otherwise>
                         	<html:text property="introducerAcno" size="10" onkeypress=" return numbersOnly()" onchange="submit()" value="0" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')"></html:text>
                       	 </core:otherwise>
                     </core:choose></td>  
                </tr>

                <tr>
               <td><bean:message key="label.details"></bean:message></td>
               <td><html:select property="details" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
                   <html:option value="SELECT"></html:option>
                    <% details=(String[])request.getAttribute("Details");
                            for(int i=0;i<details.length;i++){%>
                                <html:option value="<%=""+details[i]%>"></html:option>
                        <%}%>
                	</html:select>
               </td>
               
           </tr>
                		<html:hidden property="validation" ></html:hidden>
                		<html:hidden property="value"></html:hidden>
                     	<html:hidden property="forward" value="error"/>
                 
                
                
                 <tr>  
                     	<%if(value!=null ){
                     	if( Integer.parseInt(value)==2){ %>
                     	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
                     	<%if(disabled!=null){ %>
             			<td><html:button property="ver" disabled="<%=flag2 %>" onclick="set('verify')" styleClass="ButtonBackgroundImage">Verify</html:button>
             			<%}else{ %>
             			<td><html:button property="ver" onclick="set('verify')" styleClass="ButtonBackgroundImage">Verify</html:button>
             			<%} %>
             			<%}else{ %>
             			<td><html:button property="ver" onclick="set('verify')" disabled="true" styleClass="ButtonBackgroundImage">Verify</html:button>
             			<%} %>
                   				
                    			<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
                    		<%}else if(Integer.parseInt(value)==1){ %>	
                    		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
                    		    	<%if(disabled!=null){ %>
             				<td><html:button property="sub" disabled="<%=flag2 %>" onclick="return checksub()" styleClass="ButtonBackgroundImage">Submit</html:button>
             				<%}else{ %>
             				<td><html:button property="sub" onclick="return checksub()" styleClass="ButtonBackgroundImage">Submit</html:button>
             				<%} %>
             				<%}else{ %>
             				<td><html:button property="sub" onclick="return checksub()" disabled="true" styleClass="ButtonBackgroundImage">Submit</html:button>
             				<%} %>
                    			
                    			
                    			<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
                    		<%} %>	
                    	<%} else{%>	
                    	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            			 <td><html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage">Update</html:submit>
             			<%}else{ %>
             			<td><html:submit onclick="set('submit')" disabled="true" styleClass="ButtonBackgroundImage">Update</html:submit>
             			<%} %>
                    			
                    			<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
                    	<%} %>		
                    	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
            			<td><html:submit onclick="set('delete')" styleClass="ButtonBackgroundImage">Delete</html:submit></td>
             			<%}else{ %>
             			<td><html:submit onclick="set('delete')" disabled="true" styleClass="ButtonBackgroundImage">Delete</html:submit></td>
             			<%} %>	
                    	 
                    	
                 </tr>  		
                    	
               </table></td>
               
              		<html:hidden property="defaultSignIndex" value="0"/>
       				<html:hidden property="defaultTab" value="Personal"/>
     				<html:hidden property="defSIndex" value="1"/>
     				<html:hidden property="sysdate" />
     				
                 <core:if test="<%=cmObj!=null%>">
                  <%System.out.println("-------- HI M Cending CID-----------"+cmObj.getCustomerID()); %>
                		<html:hidden property="cid" value="<%=""+cmObj.getCustomerID()%>"/>
                	   
                 </core:if>
                 
                 <core:if test="<%=siForm!=null%>">
                 	<html:hidden property="siForm.signPageID" value="0005"/>
                 	<html:hidden property="siForm.cid" value="<%=siForm.getCid()%>"/>
                 	<html:hidden property="siForm.acType" value="<%=siForm.getAcType()%>"/>
                 	<html:hidden property="siForm.acNum" value="<%=siForm.getAcNum()%>"/>
                 	<html:hidden property="siForm.name" value="<%=siForm.getName()%>"/>
                 	<html:hidden property="siForm.tyop" value="<%=siForm.getTyop()%>"/>
                 	<html:hidden property="siForm.sign" value="<%=siForm.getSign()%>"/>
                 </core:if>
                   
                <td>
                   
<%
                         jspPath=(String)request.getAttribute("flag");
                     		System.out.println("------>>>>>"+jspPath);
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
