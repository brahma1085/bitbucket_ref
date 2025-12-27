<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %> 
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="org.jacorb.concurrency.Request"%>
<%@page import="masterObject.termDeposit.DepositReportObject"%>
<%@page import="javax.print.attribute.Size2DSyntax"%>

<%@page import="java.util.Map"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Quantum wise Report</title>


      <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
   <center> <h2 class="h2">Quantum wise Report</h2></center>
      
    
    
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
    





 function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("please enter correct date!!")
         		txt.value="";
         		return false;
          	}
         }
   

     
 function numbersonly(eve){
         var cha = event.keyCode

            if (  ( cha  >= 47 && cha < 58  ) ) {
              
                return true ;
               }
            else{
              alert("Please enter numbers only");
              return false ;
            }
                
      };
     

 
function  funclear(){

var v = document.forms[0].elements;
for(var i=0;i<=v.length;i++){

if(v[i].type =="text"){

v[i].value = "";

}
document.getElementById("table1").style.display='none';

}
 
}

function Hide()
{
   
   if(document.getElementById("totaltesting").value!=0)
	{
		
		alert(document.getElementById("totaltesting").value);	
		
			
	}



}
function set(target)
{
  
  document.forms[0].forward.value=target;
  document.forms[0].submit();

}
</script>



</head>
<body class="Mainbody" onload="Hide()">
<%!
ModuleObject[] array_module;
DepositMasterObject[] dep_mast;
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

// Note:access=1111==>read,insert,delete,update
%>



<%dep_mast = (DepositMasterObject[])request.getAttribute("quantumwise");
  
System.out.println("geetha inside quantumwise report..");
%>
<%
DepositReportObject combo_period[];
combo_period=(DepositReportObject[])request.getAttribute("quantumlimit");

System.out.println("combo values===="+combo_period.length);
%>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/QuantumwiseReport?pageId=13014" >
<html:hidden property="testing" styleId="totaltesting"/>
<html:hidden property="forward"></html:hidden>
<html:hidden property="sysdate" />
<table class="txtTable">

<tr>
		   		<td>
			       <bean:message key="label.td_actype"></bean:message>
			    
			    <html:select property = "ac_type" styleClass="formTextFieldWithoutTransparent">
			    	<% array_module=(ModuleObject[])request.getAttribute("Dep type");
			    			for(int i=0;i<array_module.length;i++)
			    			{
			        		 	System.out.println("acctype"+ array_module);
			    	%>	    	
			    				<html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%
			    			}
			    	%>	
			    	 </html:select>
			    	 
			    	
			         <bean:message key="label.Processing_date"></bean:message>
			     	
			         		         
			         <html:text property="process_date" styleClass="formTextFieldWithoutTransparent" size="10" onkeypress="return numbersonly()" onkeyup="numberlimit(this,'11')"  onblur="return datevalidation(this)" value="<%= ""+request.getAttribute("date") %>">
			     
			     </html:text>
    		   	 </td>
</tr>
<tr>
</tr>
<tr>
				<td>
			
				
			   		 <bean:message key="label.period_of_years"></bean:message>
			   	
			   <html:select property="combo_periodyears" styleClass="formTextFieldWithoutTransparent">
			   
			   
			   <%
				
				if(combo_period!=null){
					
				
				for(int i=0;i<combo_period.length;i++){
					
					System.out.println("combo_period==inside jsp="+combo_period);
					
					System.out.println("srlno="+combo_period);
					
					
				%>
				
				<html:option value="<%=""+combo_period[i].getScrollno()%>"><%=""+combo_period[i].getLmt_hdg() %></html:option>
				<%System.out.println("Scroll no in jsp="+combo_period[i].getScrollno()); %>
				<%}}else{
					System.out.println("combo_period==in else loop="+combo_period);
					%>
				<html:option value=""></html:option>
				<%}%>
				</html:select>
			   
			   
			   </td>
</tr>
<tr>

</tr>

<tr>
<td>
<center>
				<html:submit property="but_view" styleClass="ButtonBackgroundImage" onchange="submit()"><bean:message key="label.view1"></bean:message></html:submit>
				<html:button property="but_print" value="DownLoad"  styleClass="ButtonBackgroundImage" onclick="set('DownLoad')">DownLoad</html:button>
				
                <html:button property="but_clear"  onclick="return funclear()"   styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:button>
</center>				
</td>
</tr>
</table>


<hr>
<table>
<tr><td>
<div  id = "table1" style="display:block; overflow:scroll; width: 750px;height: 300px">


<%if(dep_mast!=null){
	
	%>


<display:table name="quantumwise"  id="currentRowObject" class="its" sort="list" requestURI="/TermDeposit/QuantumwiseReport.do" pagesize="100">
   			

<display:column property="accType"></display:column>
<display:column property="accNo" ></display:column>

<display:column property="customerId" ></display:column>
<display:column property="name"  title ="Depositor name"></display:column>
<display:column property="category"></display:column>
<display:column property="depDate"></display:column>
<display:column property="maturityDate"></display:column>

<display:column property="period_in_days"></display:column>
<display:column property="interestRate"></display:column>
<display:column property="interestFrq" ></display:column>
<display:column property="interestUpto"></display:column>
<display:column property="depositAmt" ></display:column>
<display:column property="maturityAmt" ></display:column>

<display:column property="interestAccured"></display:column>
<display:column property="interestPaid"></display:column>


<display:column property="closedt" ></display:column>




</display:table>

<%} %>


</div>
</td>
</tr>
</table>






</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>


</body>
</html>