<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
  <%@page import="java.util.Map"%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>

<script type="text/javascript">
function only_numbers_doublevalue() {
	
	var cha=event.keyCode;
	if((cha>=48 && cha<=57) || cha==46)
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;

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
            			alert(" Year should be less than or equal to "+dds[2]+" !");
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






 function only_numbers() {
	
	var cha=event.keyCode;
	if(cha>=47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
} ;
 function only_numbers1() {
	
	var cha=event.keyCode;
	if(cha>47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
} ;
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
         };
function Validation_accno()
{ 
	document.forms[0].button_value.value="error";
	
	if(document.getElementById("loan_acc_notfound").value!=null)
	{
		if(document.getElementById("loan_acc_notfound").value=="RecordsNotFound")
		{
		document.forms[0].txt_loanaccno.focus();
		 //document.getElementById("txt_loanaccno").focus();
		   alert("Records not found");
		  
		   return false;
		}
		document.forms[0].txt_loanaccno.focus();
	}
		if(document.getElementById("splintrestset").value!=null)
		{
		
		if(document.getElementById("splintrestset").value=="IntRateSet")
		{
			
			alert("Int Rate Set");
			return false;
		}
		else if(document.getElementById("splintrestset").value=="IntRateNotSet")
		{
			
			alert("Int Rate Not Set");
			return false;
		}
		else if(document.getElementById("splintrestset").value=="NotEligibleforSpecialInterest")
		{
			
			alert("Not Eligible for Special Interest");
			return false;
		}
	}  

};   
function date_validate(ids)
	{
		var cha=event.keyCode;
    	var format = true;
    	var ff =  ids.value;
   		var dd = ff.split( '/' );
       
	if ( dd.length == 3)
	{
	    for ( var i =0; i< dd.length; i++ )
	    {
			 if ( i != 2 && dd[i].length != 2 )
		 	 {
  		     	alert ("Enter correct date format");
  		     	ids.focus();
                format = false ;
                break;
             } 
             else if (  i==2 && dd[i].length != 4 )
			 {
			 	alert (  " problem in date format " );
			 	ids.focus();
                format = false ;
                break;
	         }

        }
    }
    else
    {
    	ids.focus();
		format = false ;
    	alert(" problem in date format");
    }
    
    var months=new Array(13);
months[1]="01";
months[2]="02";
months[3]="03";
months[4]="04";
months[5]="05";
months[6]="06";
months[7]="07";
months[8]="08";
months[9]="09";
months[10]="10";
months[11]="11";
months[12]="12";
var time=new Date();
var lmonth=months[time.getMonth() + 1];
var date=time.getDate();
var year=time.getYear();
if (year < 2000)
year = year + 1900;

var system_date=date+"/"+lmonth+"/"+ year;

var date2=document.forms[0].txt_fromdate.value;

var dat_split=date2.split('/');

	dd1=dat_split[0];
	mm1=dat_split[1];
	yy1=dat_split[2];


if(yy1>year)
{
   alert("DATE CAN NOT BE FUTURE DATE");
   return false;
}  
else 
{    
 return false;   
}    
    
};

function button_action(target)
{
if(document.getElementById("txt_loanaccno").value=="0")
	{
		alert('Account Number cannot Be Zero!');
		document.getElementById("txt_loanaccno").focus();
		return false;
		}
		else if(document.getElementById("txt_loanaccno").value=="")
	{
		alert('Account Number cannot Be Blank!');
		document.getElementById("txt_loanaccno").focus();
		return false;
		}
		else if(document.getElementById("txt_fromdate").value=="")
	{
		alert('Enter the from Date!');
		document.getElementById("txt_fromdate").focus();
		return false;
		}
		 else if(document.getElementById("txt_specialintrate").value=="")
	{
		alert('Enter the Special Rate!');
		document.getElementById("txt_specialintrate").focus();
		return false;
		}
		else if(document.getElementById("txt_specialintrate").value=="0")
	{
		alert('Enter the Special Rate!');
		document.getElementById("txt_specialintrate").focus();
		return false;
		}
		 else if(document.getElementById("txt_specialintrate").value=="0.0")
	{
		alert('Enter the Special Rate!');
		document.getElementById("txt_specialintrate").focus();
		return false;
		}
else{
document.forms[0].button_value.value=target;
//return true;
}

	
};


function clearPage()
 	{ 
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
        	}	
 	};

</script>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>Loan Special Interest Rate Updation</center></h2>
</head>
<body onload="Validation_accno()">

<%! ModuleObject LoanAccType[]; 
	String ReminderNum,ReminderDesc;
%>
<%!
String access;
String  accesspageId;
 Map user_role;
%>
<%
  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");
System.out.println("11111111"+user_role);
System.out.println("222222222"+accesspageId);
if(user_role!=null&&accesspageId!=null){
System.out.println("11111111"+access);
			if(user_role.get(accesspageId)!=null){
			System.out.println("222222222"+access);
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}
			}
// Note:access=1111==>read,insert,delete,update
%>

<%LoanAccType=(ModuleObject[])request.getAttribute("LoanAccType"); 
  ReminderDesc=(String)request.getAttribute("ReminderDesc");
  ReminderNum=(String)request.getAttribute("ReminderNum");	

%>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
    <html:form action="/Loans/LoanSplIntRateUpdn?pageidentity.pageId=5031">	
    
    
    
<table class="txtTable">

	
       <tr>
		   <td align="right"><bean:message key="label.loancat" ></bean:message></td>
		   <td><html:select property="txt_loancat">
		   <%if(LoanAccType!=null){ %>
		   		<%for(int i=0;i<LoanAccType.length;i++){ %>
				<html:option value="<%= LoanAccType[i].getModuleCode() %>"><%= LoanAccType[i].getModuleAbbrv() %></html:option>		   
		        <%}}%>
		        </html:select> 
		   </td>
		   
		</tr>
		 <tr>
		   <td align="right"><bean:message key="label.loanaccno"></bean:message></td>
		   <td><html:text property="txt_loanaccno" size="10" onkeypress="return only_numbers1()" onchange="submit()" onkeyup="numberlimit(this,'11')"></html:text></td>
		</tr>		
		 <tr>
		   <td align="right"><bean:message key="label.fromdate"></bean:message></td>
		   <td><html:text property="txt_fromdate" size="10" onblur="return datevalidation(this)" onkeypress="return only_numbers()"></html:text></td>
		</tr>
		<tr>
		   <td align="right"><bean:message key="label.specialintrate"></bean:message></td>
		   <td><html:text property="txt_specialintrate" size="10" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text></td>
		</tr>
		<tr>
		   <td align="right"><bean:message key="label.laststage"></bean:message></td>
		   <td><html:text property="txt_laststage" size="10" styleClass="formTextField" readonly="true"></html:text></td>
		</tr>
		<tr>
		   <td align="right"><bean:message key="label.Desc"></bean:message></td>
		   <td><html:text property="txt_Desc" styleClass="formTextField" readonly="true"></html:text></td>
		</tr>
		
		<html:hidden property="loan_acc_notfound"/>
		<html:hidden property="button_value" value="error"/>
		<html:hidden property="splintrestset"/>
		<html:hidden property="sysdate" />
		
		
		<tr>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
		   <td align="right"><html:submit styleClass="ButtonBackgroundImage" value="Submit" onfocus="return button_action('submit')"></html:submit></td>
		    <td><html:button styleClass="ButtonBackgroundImage" property="" onclick="return clearPage()"  value="clear"/></td>
		   <%}else{ %>
		     <td align="right"><html:submit styleClass="ButtonBackgroundImage" value="Submit" disabled="true"></html:submit></td>
		   <td><html:button styleClass="ButtonBackgroundImage" property="" disabled="true"  value="clear"/></td>
		 <%} %>
		</tr>
			
</table>
	</html:form>
	<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>