 

<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.pygmyDeposit.PygmyMasterObject" %>
<%@ page import="masterObject.general.AccountObject"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="java.util.Map"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>


<%@page import="com.scb.common.help.Date"%>
<html>
  <head><title></title>
  <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
  
        
 
 
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
         		alert("only "+size+" digits allowed")
         		txt.value="";
         		return false;
          	}
         }
         function number_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >=48 && cha <=57)||(cha >= 65 && cha <=90)|| cha==32) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
	function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >= 65 && cha <=90)||cha==46||cha==32) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
         
    
    function generatePygmyAcno(){
     if(document.forms[0].paymode.value=="Cash"){
    document.forms[0].payactypeno.disabled = true;
    	document.forms[0].payno.disabled = true;
   		}else{
   		 document.forms[0].payactypeno.disabled = false;
    	document.forms[0].payno.disabled = false;
   		}
        var val=document.forms[0].validations.value;
        
        if( val=="" ){
   			
   			 return false; 
   		}
   		else { 
   			 alert(val);
   		}
   			clearMethod();
  	}
        
	function disEnable(){
 	 if(document.forms[0].paymode.value=="Cash"){
    	document.forms[0].payactypeno.disabled = true;
    	document.forms[0].payno.disabled = true;
 	 }
 	 else if(document.forms[0].paymode.value=="PayOrder"){
    	document.forms[0].payactypeno.disabled = true;
    	document.forms[0].payno.disabled = true;
  	}
  	else{
  
  	document.forms[0].payactypeno.disabled = false;
    document.forms[0].payno.disabled = false;
  
  	}
  	}
     function clearMethod()
     {
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++)
        	{
        		if(ele[i].type=="text")
        		{
        			ele[i].value="";
        		}
        	}
        document.forms[0].pgno.value=0;	
        document.forms[0].custid.value=0;
        document.forms[0].address.value="";	
     };
     
     function only_for_address()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >= 44 && cha <=58)||(cha >= 65 && cha <=90)||cha==32||cha==35) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
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
   
   
	var dt=document[0].opdate;
	
	
	if (isDate(dt.value)==false){
	    document.forms[0].opdate.value="";
	    
	    dt.focus();  	
		return false
		}
		else {
	       	
		//document.forms[0].submit()
		}
		return true;
		}
		
	function ValidateForm1(){
   
   
	var dt=document[0].dob;
	
	
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
 
 	function setsub()
 	{
 		if(document.forms[0].custid.value==0)
 		{
 		alert("Enter Customer ID");
		}
		else if(document.forms[0].intagno.value==0)
		{
		alert("Enter Introducer Account Number");
		}
		else
		{
 		document.forms[0].forward.value='Submit';
 		document.forms[0].submit()
 		}
 	};
 	
 	
 	function checksub(target)
 	{
 	document.forms[0].forward.value=target
 	document.forms[0].submit();
 	}
 function set(target){
       document.forms[0].forward.value=target;
       document.forms[0].submit();
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
         
 function numbersonly_date(eve){
         var cha = event.keyCode

            if (  ( cha  >= 47 && cha < 58  ) ) {
              
                return true ;
                            }
            else{
              
              return false ;
            }
         
        
      };   
        
         
   </script>
     
  </head> 
  <body class="Mainbody" onload="generatePygmyAcno()">
<center><h2 class="h2">Pygmy Opening</h2></center>
    <%!
          ModuleObject[] array_module;
          String pygdetails[];
          String paymode[];
          PygmyMasterObject pygmyMaster=null;
          AccountObject acntObject=null,acntObject1=null;
          String jspPath;
          boolean flag1;
          String value,action;
          String name1,name;
          String index;
          String enable,disable;
      %>
      
      <%!
      String access;
      String  accesspageId;
       Map user_role;

	%>

<%
		enable=(String)request.getAttribute("enable");
		disable=(String)request.getAttribute("disable");
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
     	
     	pygmyMaster=(PygmyMasterObject)request.getAttribute("PygmyDetails");
     	name=(String)request.getAttribute("IntroAgentDetails");
     	System.out.println("name of the agent"+ name);
     	acntObject=(AccountObject)request.getAttribute("AccountDetail");
     	//cust=(CustomerMasterObject)request.getAttribute("CustNum");
     	value=(String)request.getAttribute("VALUE");
     	
        System.out.println("here i am=="+pygmyMaster);
        System.out.println("here i am==------"+name);
        System.out.println("here i am==========="+acntObject);
        //System.out.println("here i am!!!!!!!!!!!!"+acntObject1);
        %>
        
        <%if(value!=null){
    	 if(Integer.parseInt(value.trim())==2){
    		 action="/Pygmy/PygmyOpeningMenu?pageid=8002&value=2";
    		 flag1=true;
    	 }else if(Integer.parseInt(value.trim())==1){
    		 action="/Pygmy/PygmyOpeningMenu?pageid=8002&value=1";
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
		
		
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
     <html:form action="/Pygmy/PygmyOpening?pageid=8002">
	 <table class="txtTable">
	 <tr>
		<td>
            <table class="txtTable">
           <tr>
               <td><bean:message key="label.pygmyactypeno"></bean:message></td>
               <td><html:select property="pgactypeno" styleClass="formTextFieldWithoutTransparent">
               		
               		<%array_module=(ModuleObject[])request.getAttribute("PygmyAcType");
               			for(int i=0;i<array_module.length;i++){%>
               				<html:option value="<%= array_module[i].getModuleCode()%>"><%= array_module[i].getModuleAbbrv()%></html:option>
               		<%} %>
               		
             </html:select></td>     
            </tr>        			
                   
            <tr>
                    <td><bean:message key="label.pygno"></bean:message></td>
                      <% if(pygmyMaster!=null){ %>
                         <td><html:text property="pgno" size="10"  onblur="submit()" value="<%=""+pygmyMaster.getAccNo()%>" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')" onkeypress=" return numbersOnly()"></html:text></td>
                      <% }else{ %>
                         <td><html:text property="pgno" size="10" onchange="submit()" value="0" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')" onkeypress=" return numbersOnly()"></html:text></td>
                      <% } %>
                 
            </tr>      
           
            <tr>
               <td><bean:message key="label.custid"></bean:message></td>
               
               <%if(pygmyMaster!=null){%>
               		<td><html:text property="custid" size="10" onblur="checksub('CustId')" readonly="true" value="<%=""+pygmyMaster.getCid()%>" styleClass="formTextFieldWithoutTransparent " onkeyup="numberlimit(this,'11')" onkeypress=" return numbersOnly()"></html:text></td>
               <%}else{ %>	
               		<td><html:text property="custid" size="10"  onblur="checksub('CustId')" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')" onkeypress=" return numbersOnly()" ></html:text></td>
               <%}%>			
           </tr>
           
            <tr>
            	<td><bean:message key="label.introduceragenttypeno"></bean:message></td>
                      
                    	<td><html:select  property="inagenttypeno" styleClass="formTextFieldWithoutTransparent">
                           	<%array_module=(ModuleObject[])request.getAttribute("IntroAgentAcType");%>
                           	<core:choose>
                         	   <core:when test="<%=pygmyMaster!=null&&pygmyMaster.getAgentType()!=null%>">
                         	      <core:if test="<%=(pygmyMaster.getAgentType().trim().length()!=0) %>">
                         	      	<%System.out.println("------------>>>>>>>>>>>>------->>>>>>>>>>>>>"+pygmyMaster.getAgentType()); %>
                               		<core:forEach var="inagenttypeno" items="${requestScope.IntroAgentAcType}" varStatus="count">
                               			
                               			<%System.out.println("befor Introducer Account type"); %>
     										<html:option value="${requestScope.IntroAgentAcType[count.index].moduleCode}">${requestScope.IntroAgentAcType[count.index].moduleAbbrv}</html:option>
     										<%System.out.println("after Introducer Account type"); %>
                        				
                        		    </core:forEach> 
                        	     </core:if>
                        	   </core:when>  	    
                         	   <core:otherwise>
                         	   		  <core:forEach var="inagenttypeno" items="${requestScope.IntroAgentAcType}" varStatus="count">
                                	     <html:option value="${inagenttypeno.moduleCode}">${inagenttypeno.moduleAbbrv}</html:option>
                                      </core:forEach>	
                               </core:otherwise>	
                           	 </core:choose>
                           </html:select> 	 
                        
 
					 
                     <core:choose>
                     
                     	  <core:when test="${requestScope.PygmyDetails!=null}">
                        		<html:text property="intagno" size="10"  onchange="submit()" value="<%=""+pygmyMaster.getAgentNo()%>" disabled="true" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')"></html:text>
                          </core:when>	
                      	  <core:otherwise>
                         		<html:text property="intagno" size="10"  onchange="submit()" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>
                          </core:otherwise>		
                     </core:choose></td>
                </tr>
                
                <tr> 
                     <td><bean:message key="label.agentname"></bean:message></td>
                       <core:choose>
                     	<core:when test="${requestScope.PygmyDetails!=null}">
                        		<td><html:text property="agname"  onchange="submit()" value="<%=pygmyMaster.getAgentName()%>" disabled="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                          </core:when>
                     	<%if(name!=null){
                     	%> 
                     			<td><html:text property="agname"  onchange="submit()" value="<%=name%>" styleClass="formTextField" readonly="true"></html:text></td>
                     			<%}else{ %>
                     			<td><html:text property="agname"  onchange="submit()" value="" styleClass="formTextField" readonly="true"></html:text></td>
                     			<%} %>
                            
                      </core:choose>	  
                </tr>
                
          		<tr>
                     <td><bean:message key="label.openingdate"></bean:message></td>
                     <core:choose>
                     
                      	  <core:when test="${requestScope.PygmyDetails!=null}">
                      	  		<td><html:text property="opdate"  size="10"  value="<%=pygmyMaster.getAccOpenDate()%>" disabled="true" styleClass="formTextField:formTextFieldWithoutTransparent" onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)"></html:text></td>
                      	  </core:when>
                      	  <core:otherwise>
                      	  		<% String date=(String)request.getAttribute("Date");%>
                      	  		<td><html:text property="opdate"   size="10"  onchange="submit()" value="<%=Date.getSysDate()%>" styleClass="formTextFieldWithoutTransparent" onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)"></html:text>
                             	<font color="red"><bean:message key="label.date1"></bean:message></font></td>
                      	  </core:otherwise>
                     </core:choose> 	  
                </tr>
           
          
           <tr>
               <td><bean:message key="label.paymentmode"></bean:message></td>
               <td><html:select property="paymode" styleClass="formTextFieldWithoutTransparent" onblur="disEnable()">
                   	
                   <%paymode=(String[])request.getAttribute("PayMode");
                   		for(int i=0;i<paymode.length;i++){%>
                                <html:option value="<%=""+paymode[i]%>"></html:option>
                        <%}%>
                   </html:select>
               </td>
                   
           </tr>

           <tr>
               <td><bean:message key="label.paymentactypeno"></bean:message></td>
                     <td><html:select  property="payactypeno" styleClass="formTextFieldWithoutTransparent">
                         <%array_module=(ModuleObject[])request.getAttribute("PayAcType");%>
                         	<core:choose>
                       			<core:when test="<%=pygmyMaster!=null&&pygmyMaster.getPayAccType()!=null%>">
                         	  
                         	      <core:if test="<%=(pygmyMaster.getPayAccType().trim().length()!=0) %>">
                               		<core:forEach var="payactypeno" items="${requestScope.PayAcType}" varStatus="count">
                               			
                            	   		
     										<html:option value="${requestScope.PayAcType[count.index].moduleCode}">${requestScope.PayAcType[count.index].moduleAbbrv}</html:option>
                        				
                        		    </core:forEach>
                        	     </core:if>
                        	   </core:when>  	    
                         	   <core:otherwise>
                         	   		
                        	       
                            	      <core:forEach var="payactypeno" items="${requestScope.PayAcType}" varStatus="count">
                                	     <html:option value="${payactypeno.moduleCode}">${payactypeno.moduleAbbrv}</html:option>
                                      </core:forEach>
                                      
                               </core:otherwise>	
                        </core:choose>
                 </html:select> 	 
                        
                        
               <core:choose>
               
                  <core:when test="${requestScope.PygmyDetails!=null}">   
                  	     <html:text property="payno" size="10" onchange="submit()" value="<%=""+pygmyMaster.getPayAccNo()%>" disabled="true" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')"></html:text>
                  </core:when>
                  <core:otherwise>
                  		<html:text property="payno" size="10" onchange="submit()"  styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>
                  </core:otherwise>	
               </core:choose></td>		
           </tr>

           <tr>
               <td><bean:message key="label.selfname"></bean:message></td>
               
                <core:choose>
                  <core:when test="${requestScope.PygmyDetails!=null}"> 
               			<td><html:text property="name"  styleClass="formTextFieldWithoutTransparent" onchange="submit()" value="<%=pygmyMaster.getPay_acc_name() %>" disabled="true"></html:text></td>
                   </core:when>
                    <core:when test="${requestScope.AccountDetail!=null}"> 
                    	<%System.out.println("Account Name====="+acntObject.getAccname()); %>
               			<td><html:text property="name"  styleClass="formTextField" onchange="submit()" readonly="true" value="<%=acntObject.getAccname()%>"></html:text></td>
                   </core:when>
                  <core:otherwise>	
                        <td><html:text property="name"  styleClass="formTextField" readonly="true" onchange="submit()" value=""></html:text></td>
                  </core:otherwise>	
               </core:choose>        		
           </tr>

           <tr>
               <td><bean:message key="label.details"></bean:message></td>
               <td><html:select property="pygdetails" onchange="submit()"  styleClass="formTextFieldWithoutTransparent">
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
    
               		
               	
               		<td><html:text property="cidis" size="10"  onblur="checksub('Cidis')" styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" ></html:text></td>
               
    	
    	<!--<td><html:text property="cidis"  styleClass="formTextFieldWithoutTransparent" onblur="submit()"></html:text> </td>
   
   --></tr>	
           
   <tr>
    <td><bean:message key="label.name"></bean:message></td>
   
    <td><html:text property="nomname"  styleClass="formTextFieldWithoutTransparent" onkeypress="return only_alpha()"></html:text> </td>
   
    
   </tr>
   
   <tr>
     <td><bean:message key="label.dob"></bean:message> </td>
     
     <td><html:text property="dob"  styleClass="formTextFieldWithoutTransparent" onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)"></html:text> </td>
    
   </tr>
   
   <tr>
    <td><bean:message key="label.gender"></bean:message></td>
    
    <td><html:text property="gender" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_alpha()"></html:text></td>
   
   </tr>
   
   <tr>
    <td><bean:message key="label.address"></bean:message></td>
     
    <td><html:textarea property="address" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_for_address()"></html:textarea></td>
     
   </tr>
   
   <tr>
     <td><bean:message key="label.rel"></bean:message></td>
     <td><html:text property="rel_ship"  styleClass="formTextFieldWithoutTransparent" onkeypress="return only_alpha()"></html:text></td>
   </tr>

   <tr>
    <td><bean:message key="label.percentage"></bean:message></td>
    <td><html:text property="percentage"  styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'4')"></html:text></td>
   </tr>
         
</table>            
<%} %>
</td>
</tr>
          			 <html:hidden property="validations" ></html:hidden>
              		 <html:hidden property="forward" value="error"/>
              		 <html:hidden property="value" ></html:hidden>
              		 <html:hidden property="sysdate" />
              		 <tr>
                    	<%if(value!=null ){
                     		if( Integer.parseInt(value)==2){ %>
                     			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            					 <td><html:submit onclick="set('verify')" styleClass="ButtonBackgroundImage">Verify</html:submit>                
             					<%}else{ %>
            					 <td><html:submit onclick="set('verify')" disabled="true" styleClass="ButtonBackgroundImage">Verify</html:submit>
             					<%} %>
                    			
                    			<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
                    		<%}else if(Integer.parseInt(value)==1){ %>		
                    		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
           					 <td><html:button property="butt" onclick="return setsub();submit()" styleClass="ButtonBackgroundImage" value="Submit"></html:button>
             				<%}else{ %>
            				<td><html:button property="butt" disabled="true" onclick="return setsub();submit()" styleClass="ButtonBackgroundImage" value="Submit"></html:button>
             				<%} %>
                    		<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
                    		<%} %>	
                    	<%} else{%>	
                    	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
          				<td><html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage">Submit</html:submit>
            			 <%}else if(disable=="disable"){ %>
           				<td><html:submit onclick="set('submit')" disabled="true" styleClass="ButtonBackgroundImage">Submit</html:submit>
            			 <%} else{%>
            			 <td><html:submit onclick="set('submit')" disabled="true" styleClass="ButtonBackgroundImage">Submit</html:submit>
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
       				
                   <td>
                     <%
                         jspPath=(String)request.getAttribute("flag");
                         System.out.println("!!!!!!"+jspPath);
                         if(jspPath!=null){
                     %>
                         <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>

                     <%  } %>
                     
               	</td>
               	</tr>
       		</table>
    </html:form>
    <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
</html>