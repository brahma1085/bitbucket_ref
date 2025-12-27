<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@ page import="masterObject.general.ModuleObject" %>
<%@page import="masterObject.pygmyDeposit.PygmyTransactionObject"%>
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

<h2 class="h2"><center>Monthly Remittance Report</center></h2>
     
   
   <script type="text/javascript">
   
   function set(target){
         if(document.forms[0].comboAgents.value=="SELECT")
         {
         alert('Select Agent Type');
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
    function validateMethod(){
        	var chk=document.forms[0].validate.value;
        	
        	if(chk==0 || chk==""){
        		return false;
         	}
         	else{
         		alert(chk);
         	}
         	clearMethod();
         };	
   
   function getSelect(){
   
   
   
   if(document.forms[0].comboAgents.value=="All"){
   
   document.forms[0].agent_no.disabled=true;
   document.forms[0].agentname.disabled=true;
   document.forms[0].agent_code.disabled=true;
   
   
   }if(document.forms[0].comboAgents.value=="Individual"){
   document.forms[0].agent_no.disabled=false;
   document.forms[0].agentname.disabled=false;
   document.forms[0].agent_code.disabled=false;
   document.forms[0].submit();
   
   }
   
   }
         
         
         function intialFocus(){
         document.forms[0].agentnum.focus();
         }
         
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
	
	
	if (isDate(dt.value)==false){
	    dt.value="";
	    
	    dt.focus();  	
		return false
		}
		else {
	       	
		//document.forms[0].submit()
		}
		return true;
		}
 
 function ValidateForm1(){
   
   
	var dt=document[0].to_date;
	
	
	if (isDate(dt.value)==false){
	    document.forms[0].to_date.value="";
	    
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
  //else if(frmMonth>ToMonth && frmYear<=ToYear ){
    //alert("From Month is greater than To Month !!!! Enter valid date")
  //}
 //else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  //alert("From day is greater than To day !!!! Enter valid date")
 // }
  document.forms[0].submit();
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
              
              return false ;
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
   </script>
   
</head>
<body class="Mainbody" onload="validateMethod()">
		<%! ModuleObject[] array_module;
			String[] comboAgents=null;
			AgentMasterObject agentMaster;
			String date;
			PygmyTransactionObject[] pmtrn;
		%>
		


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
		

		<%
			 comboAgents=(String[])request.getAttribute("COMBOAGENTS");
		     System.out.println("The combo agents"+comboAgents.length);
		     agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");
		     System.out.println("The master agents"+agentMaster);
		     date=(String)request.getAttribute("Date");
		     System.out.println("The date"+date);
		     pmtrn=(PygmyTransactionObject[])request.getAttribute("MnthRemi");
		    
		%>
		<% String msg=(String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<font color="red"><%=msg %></font>
		<br><br>
		<%} %>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
		<html:form action="/Pygmy/MonthlyRemittance?pageid=8007">
		<table class="txtTable">
		<tr>
			<td><bean:message key="label.comboAgents"></bean:message></td>
				<td><html:select  property="comboAgents" onchange="getSelect()" styleClass="formTextFieldWithoutTransparent">
                    	<html:option value="SELECT">SELECT</html:option>
                      <core:choose>
                        <core:when test="${requestScope.COMBOAGENTS!=null}">
                          <core:forEach var="comboAgents" items="${requestScope.COMBOAGENTS}" varStatus="count">        
                         	
                            <html:option value="${comboAgents}">${comboAgents}</html:option>
                            <%System.out.println("Helloooooooooooooooooo"); %>
                            
                          </core:forEach>
                        </core:when>
                     </core:choose>  
                 </html:select></td>
		</tr>
		
			<tr>
		 		<td><bean:message key="label.agentcode"></bean:message></td>
                     <td><html:select  property="agent_code" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
                         
                         <% array_module=(ModuleObject[])request.getAttribute("AgentAcType1");
 							System.out.println("here i am=="+array_module);
 							if(array_module!=null){
 								for(int i=0;i<array_module.length;i++){%>
 										<html:option value="<%=""+array_module[0].getModuleCode()%>"><%=""+array_module[0].getModuleAbbrv()%></html:option>                      
							<%}}else{ %>
										<html:option value="SELECT">SELECT</html:option>
										
							<%} %>
                         </html:select></td>
                </tr>
            
                 <tr>
                     <td><bean:message key="label.agentno"></bean:message></td>
                     <core:choose>
                      	 <core:when test="${requestScope.AgentDetails!=null}">
                      	 	<td><html:text property="agent_no" size="10" value="${requestScope.AgentDetails.agentNo}" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersOnly()" onkeyup="numberlimit(this,'11')" ></html:text></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><html:text property="agent_no" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersOnly()" onkeyup="numberlimit(this,'11')" ></html:text></td>
                         </core:otherwise>	 	
                     </core:choose>
                   
                     <!--<core:choose>
                     
                      	  <core:when test="${requestScope.AgentDetails!=null}">
                        	<td><html:text property="agentname"   value="" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                          </core:when> 
                          
                          <core:otherwise>
                          
                            <td><html:text property="agentname"   size="10"  value="" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                          </core:otherwise>	
                      </core:choose> 
                --></tr>
                
                  
				<tr>
	 				<td><bean:message key="label.from_date"/></td>
	 				<td><html:text property="from_date" size="10"  styleClass="formTextFieldWithoutTransparent" onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)" ></html:text></td>
	 			
	 				<td><bean:message key="label.to_date"/></td>
	 				<td><html:text property="to_date" size="10"   styleClass="formTextFieldWithoutTransparent" onchange="properdate(from_date.value,to_date.value)" onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)" ></html:text></td>
	 		   </tr>
	 		   
	 		   	<tr>
	 		   		<html:hidden property="forward" value="error"/>
	 		   		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
             		<td><html:button property="but" onclick="set('view')" styleClass="ButtonBackgroundImage">View</html:button>
                	<%}else{ %>
       				<font color="red"><bean:message key="label.accessdenied"></bean:message></font>
       				<%} %>
       				<html:button property="download" onclick="set('save')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
           	    	<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button>
           	    	
           	    	<!--<html:button property="PrintFile" onclick="window.print()"  styleClass="ButtonBackgroundImage">Print</html:button>--></td>	
				</tr>
		</table>	
		<html:hidden property="validate"/>
		<html:hidden property="sysdate" />
		<% if (pmtrn!=null) { 	%> 	
		
			
        <div  id = "table1" style="display: block;overflow:scroll;width: 750px;height: 300px">
   			<display:table name="MnthRemi"  id="currentRowObject" class="its" >
   			
               		
               		
                    	<display:column property="accType" style="background-color:#F2F0D2; font:Garamond " title="Pygmy Type"  ></display:column>
                    
            	    	<display:column property="accNo" style="background-color:#F2F0D2; font:Garamond " title="Pygmy No" ></display:column>
            	    		
                    	<display:column property="collectionDate" style="background-color:#F2F0D2; font:Garamond " title="Collected On" ></display:column>
                    	<display:column property="tranDate" style="background-color:#F2F0D2; font:Garamond " title="Remitted On" ></display:column>
                    	<display:column property="tranNarration" style="background-color:#F2F0D2; font:Garamond " title="Narration" ></display:column>
                    	<display:column property="tranAmt" style="background-color:#F2F0D2; font:Garamond " title="Amount Credit" ></display:column>
                    	<display:column property="closeBal" style="background-color:#F2F0D2; font:Garamond " title="Close Balance" ></display:column>
                    	
                	
                	</display:table>
     	      </div>
	 	 <%}%>
	 	 
			
		</html:form>
		<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>