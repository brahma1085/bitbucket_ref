<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>    

<html>
<head>

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
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
   




function only_numbers()
	{
		var cha = event.keyCode;
		
		 if((cha>47 && cha<=57) ) 
		  {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	function only_numbers1()
	{
		var cha = event.keyCode;
		
		 if((cha>=47 && cha<=57) ) 
		  {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	

function chkvalidamount(){

    if(document.getElementById("Amountbetween").value!=null)
	{ 
	   alert("Enter amount between 1 and "+document.getElementById("Amountbetween").value);
	}	
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
        };
    
   function chkAccountExist()
   { 
   
   		if(document.getElementById("found").value!=null)
  		{
  			if(document.getElementById("found").value=="notfound")
  			{
  				alert("Account not Found");
  				document.getElementById("found").value=""; 
  				return false;
  			}
  		}
  		if(document.getElementById("result_reschedule").value!=null)
  		{
  			if(document.getElementById("result_reschedule").value=="ALLREADYSCHEDULE")
  			{
  				alert("Loan account rescheduled ");
  			}
  			else if(document.getElementById("result_reschedule").value=="UNABLETORESCHEDULE")
  			{
  				alert("Unable to reschedule try again");
  			}
  		}
  	
    }
    
    
    function clearPage()
 	{ 
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++)
        	{
        		if(ele[i].type=="text")
        		{
        			ele[i].value="";
        		}
        	}
        	document.forms[0].submit();	
 	};
 	
 	
 	function set(target)
 	{
 	
 		
   		if(document.getElementById("accno").value=="0")
	{
		alert('Account Number Cannot be Blank!');
		document.getElementById("accno").focus();
		return false;
		}	else if(document.getElementById("accno").value=="")
	{
		alert('Account Number Cannot Be Zero!');
		document.getElementById("accno").focus();
		return false;
		}
		else if(document.getElementById("installments").value=="")
	{
		alert('Enter the Installments!');
		document.getElementById("installments").focus();
		return false;
		}
		else if(document.getElementById("installments").value=="0")
	{
		alert('Enter the Installments!');
		document.getElementById("installments").focus();
		return false;
		}
		else if(document.getElementById("effective_date").value=="")
	{
		alert('Enter the Effective Date!');
		document.getElementById("effective_date").focus();
		return false;
		}
			else if(document.getElementById("amount").value=="")
	{
		alert('Enter the Amount!');
		document.getElementById("amount").focus();
		return false;
		}
			else if(document.getElementById("amount").value=="0")
	{
		alert('Enter the Amount!');
		document.getElementById("amount").focus();
		return false;
		}
		else if(document.getElementById("amount").value=="0.0")
	{
		alert('Enter the Amount!');
		document.getElementById("amount").focus();
		return false;
		}
		else{
		//return true;
 	 	document.forms[0].forward.value=target;
 	 document.forms[0].submit();
 	 	}
 	};
 	
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




 	
 	
 	
</script>



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
     
</head>
<body onload="return chkAccountExist()">

 <%! String msg ; %>
 <%
  msg = (String)request.getAttribute("msg");
 %>
 <center><h2 class="h2">Rescheduling</h2></center>	
 
 <% if(msg!=null){ %>
 <font color="red"><%=msg%></font>
 <%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<html:form action="/Loans/Rescheduling?pageidentity.pageId=5014">
<table>
<tr>
<td><bean:message key="label.combo_loan"></bean:message></td>
<td><html:select property="acctype">
      			  
         		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      			  </core:forEach>
       			  </html:select></td>
       			  
<td><html:text property="accno"  onblur="submit()" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
<td><bean:message key="label.name"></bean:message> </td>
<td><html:text property="name" size="35" styleClass="formTextField" readonly="true"></html:text></td>
</tr>
<tr>
<td><bean:message key="label.Intrate"></bean:message></td>
<td><html:text property="intrate" size="8" styleClass="formTextField" readonly="true"></html:text></td>
<td><bean:message key="label.purpose"></bean:message></td>
<td><html:text property="purpose" size="8" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.SBNo"></bean:message></td>
<td><html:text property="sbaccno" size="8" styleClass="formTextField" readonly="true"></html:text></td>
<td><bean:message key="label.Shno"></bean:message></td>
<td><html:text property="shno" size="8" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.disb_date"></bean:message></td>
<td><html:text property="disbdate" size="8" styleClass="formTextField" readonly="true"></html:text></td>
<td><bean:message key="label.sanctionedamount"></bean:message></td>
<td><html:text property="sanctioned_amt" size="8" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.noofInstallment"></bean:message></td>
<td><html:text property="noofinst" size="8" styleClass="formTextField" readonly="true"></html:text></td>
<td><bean:message key="label.Installmentamt"></bean:message></td>
<td><html:text property="instal_amt" size="8" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.period"></bean:message></td>
<td><html:text property="period" size="8" styleClass="formTextField" readonly="true"></html:text></td>
<td><bean:message key="label.loanbal"></bean:message></td>
<td><html:text property="loan_bal" size="8" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.intuptodate"></bean:message></td>
<td><html:text property="int_upto_date" size="10" styleClass="formTextField" readonly="true"></html:text></td>
<td><bean:message key="label.effectivedate"></bean:message></td>
<td><html:text property="effective_date" size="10" onblur="return datevalidation(this)" onkeypress="return only_numbers1()"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.instal"></bean:message></td>
<td><html:text property="installments" size="8" onkeypress="return only_numbers()"></html:text></td>
<td><bean:message key="label.amount"></bean:message></td>
<td><html:text property="amount" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text></td>

</tr>
<tr>

		<td><html:hidden property="forward" value="error"/></td>
		<html:hidden property="sysdate" />
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
         <td><html:button property="sub" styleClass="ButtonBackgroundImage"  onclick="set('reschedule');" value="Reschedule"></html:button></td>
         <td>  <html:button property="clear" styleClass="ButtonBackgroundImage"  onclick="return clearPage()"  value="clear"></html:button></td>
         <%}else{ %>
         <td><html:button property="sub" styleClass="ButtonBackgroundImage"  disabled="true" value="Reschedule"></html:button></td>
       <td>  <html:button property="clear" styleClass="ButtonBackgroundImage" disabled="true" value="clear"></html:button>
<%} %>
</td>         
</tr>

<html:hidden property="amountbetween" styleId="Amountbetween"></html:hidden>
<html:hidden property="accountfound" styleId="found"></html:hidden>
<html:hidden property="result_reschedule" styleId="result_reschedule"/>



<tr>
<td colspan="5">
</td>
</tr>
 
  
</table>
<table>
<tr>
<td>
<%System.out.println("**********************Hi from scroll**************************"); %>

</td>
</tr>
</table>
<%System.out.println("===========");%>
<div id="table1" style="display:block;width:750px;height:300px;overflow:scroll;" >
		
	<display:table name="list" export="false" id="currentRowObject" class="its" sort="list" requestURI="/Loans/Rescheduling.do"  >
	   
	   
	   <display:column title="Serial No"  style="width:1%;"  maxLength="50">
             <core:out value="${currentRowObject.id}"></core:out>
       </display:column>
       
	   <display:column title="Transaction Date"  style="width:1%;"  maxLength="50">
              <core:out value="${currentRowObject.LoanTrandate}"></core:out>
       </display:column>
       
       <display:column title="Principle Amount"  style="width:1%;"  maxLength="50">
              <core:out value="${currentRowObject.PrincipalPaid}"></core:out>
       </display:column>
       
       <display:column title="Interest Amount"  style="width:1%;"  maxLength="50">
              <core:out value="${currentRowObject.IntPaid}"></core:out>
       </display:column>
       
        <display:column title="Recovery Amount"  style="width:1%;"  maxLength="50">
              <core:out value="${currentRowObject.TranAmt}"></core:out>
       </display:column>
       
       <display:column title="Loan Balance"  style="width:1%;" maxLength="50">
              <core:out value="${currentRowObject.LoanBal}"></core:out>
       </display:column>
       </display:table> 
       <table>
       	<tr>
       	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
       	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
       	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
       	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
       	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
       	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
       	<core:if test="${requestScope.principle!=null}">
       	<td>Total :</td>
       	</core:if>
       	<td><core:out value="${requestScope.principle}"></core:out></td>
       	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
       	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
       	<td><core:out value="${requestScope.interst}"></core:out></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><core:out value="${requestScope.recovery}"></core:out></td>
		</tr>
		</table>
		
</div>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>