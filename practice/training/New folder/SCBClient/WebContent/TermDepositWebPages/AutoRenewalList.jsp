<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %> 
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositTransactionObject"%>
<%@page import="java.util.Map"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>AutoRenewalList</title>


<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
    <center><h2 class="h2">
      AutoRenewalList</h2></center>
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

function only_numbers_date()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57)||cha==47) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
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
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
          	}
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
  
  
  
  if(frmYear > ToYear){
    alert("From Year is greater than To Year!!!! Enter valid date")
  }
  else if(frmMonth > ToMonth && frmYear<=ToYear ){
    alert("From Month is greater than To Month !!!! Enter valid date")
  }
 else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  alert("From day is greater than To day !!!! Enter valid date")
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
	 
	};

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
    










function set(target)
{
  
  document.forms[0].forward.value=target;
  document.forms[0].submit();

}
</script>



</head>
<body class="Mainbody">
<%!
ModuleObject[] array_module;
DepositTransactionObject[] deptranobj;
%>
<%deptranobj = (DepositTransactionObject[])request.getAttribute("autorenewallist");
  
System.out.println("geetha inside auto renewal list..");
%>


<html:form action="/TermDeposit/AutoRenewalList?pageId=13016" >
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
    		   	 </td>
</tr>
<tr>
</tr>
<tr>
				<td>
			         <bean:message key="label.from_date"></bean:message>
			     	
			     <html:text property="from_date" size="12"   onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text>
			     
			
				
			   		 <bean:message key="label.to_date"></bean:message>
			   	
			    <html:text property="to_date" size="12"   onblur="return datevalidation(this)" onchange="properdate(from_date.value,to_date.value)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text>
			       </td>
</tr>
<tr>

</tr>

<tr>
<td>
<center>
				<html:submit property="but_view" onchange="submit()"  styleClass="ButtonBackgroundImage"><bean:message key="label.view1"></bean:message></html:submit>
	       		<html:button property="but_query"  styleClass="ButtonBackgroundImage"><bean:message key="label.query"></bean:message></html:button>  
				<html:button property="butt_search"  styleClass="ButtonBackgroundImage"><bean:message key="label.search"></bean:message></html:button>
				<html:button property="but_file"  onclick="set('DownLoad')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
				<!--<html:button property="but_print"  styleClass="ButtonBackgroundImage" onclick="window.print()"><bean:message key="label.print"></bean:message></html:button>
				<html:button property="but_reprint"  styleClass="ButtonBackgroundImage" onclick="window.print()"><bean:message key="label.reprint"></bean:message></html:button>

				--><html:button property="but_clear" onclick="return funclear()" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:button>
</center>				
</td>
</tr>
</table>


<hr>

<br>
<div  id = "table1" style="display:block; overflow:scroll; width: 750px;height: 300px">



<%if(deptranobj!=null){
	
	%>
	
	<display:table name="autorenewallist"  id="currentRowObject" class="its" sort="list">
   			

<display:column property="tranSou"   sortable="true" title="Account Type"></display:column>
<display:column property="accNo"   sortable="true"></display:column>
<display:column property="cdind"   title ="Deposit date"></display:column>

<display:column property="paidDate"  title="Maturity Date"></display:column>
<display:column property="depositAmt" ></display:column>
<display:column property="interestDate"  maxLength="10" title="Auto Renewal Instruction"></display:column>

<display:column property="tranKey"  title="Period in days"></display:column>
<display:column property="referenceNo"   title="New Actype/Acno"></display:column>

<display:column property="RDBalance" ></display:column>


 

</display:table>

<%} %>
</div>







</html:form>
</body>
</html>