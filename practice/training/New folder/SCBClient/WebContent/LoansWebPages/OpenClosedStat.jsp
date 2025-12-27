<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="java.util.Map"%>
<%@page import="masterObject.loans.LoanReportObject"%>
<html>
<head>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function set(target){

   		if(document.getElementById("fromdate").value=="")
	{
		alert('Enter the From Date!');
		document.getElementById("fromdate").focus();
		return false;
		}
		if(document.getElementById("todate").value=="")
	{
		alert('Enter the To Date!');
		document.getElementById("todate").focus();
		return false;
		}
else{
document.forms[0].forward.value=target;
 document.forms[0].submit();
//return true;
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
	            				alert(" Month should be less than or equal to "+dds[1]+" !");
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
    alert("Fromdate should be less than the Todate!")
    document.getElementById("from_date").focus();
  }
 // else if(frmMonth > ToMonth && frmYear<=ToYear ){
  //  alert("From Month is greater than To Month !!!! Enter valid date")
 // }
// else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
 // alert("From day is greater than To day !!!! Enter valid date")
 // }
 }; 
 

function clearPage()
{ 

    	var ele=document.forms[0].elements;
    	document.getElementById("table1").style.display='none';  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    	}
    
 };
 
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
}  

 


 
</script>

<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>Open Closed Report</center></h2> 
</head>
<body>
<%!
    LoanReportObject[] loanreportobj;
    String date;
%>
<%
     loanreportobj = (LoanReportObject[])request.getAttribute("OpenClosed");
     date = (String)request.getAttribute("SystemDate");

     System.out.println("VINAY IS CHECKING HERE"+date);
     String message=(String)request.getAttribute("msg"); 
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

<%if(message!=null){ %>
<div id = "div1" class = "myLayersClass">
    
    <core:if test="<%=message!=null %>">
    <font color="red"><%=message %></font>
    <br><br><br><br>
    </core:if>
    </div>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/OpenClosedStat?pageidentity.pageId=5016">
<table>
<tr>
 <html:hidden property="testing" styleId="totaltesting"></html:hidden>
  <td>
  <bean:message key="label.select"></bean:message>
  <html:select property="select" styleClass="formTextFieldWithoutTransparent">
  <html:option value="Open">Open Accounts</html:option>
  <html:option value="Closed">Closed Accounts</html:option>
  </html:select>
  </td>
  <td>
  <bean:message key="label.combo_loan"></bean:message>
   <html:select property="acctype" styleClass="formTextFieldWithoutTransparent">
     
          <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      	    <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      	  </core:forEach>
      </html:select>
  </td>
  
  <tr>
  	  <td></td>	
  </tr>
  <tr>
  <td>
    <bean:message key="label.fromdate"></bean:message>
    <html:text property="fromdate" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" onblur="return datevalidation(this)"  ></html:text>
  </td>
  <td>
    <bean:message key="label.todate"></bean:message>
    <html:text property="todate" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" onblur="return datevalidation(this)" onchange="properdate(fromdate.value,todate.value)"></html:text>
  </td>
</tr>  

<tr>

   <html:hidden property="forward" value="error"/>
   <html:hidden property="sysdate" />
   <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
   <td><html:submit styleClass="ButtonBackgroundImage" onfocus="set('view');">View</html:submit></td>
   <!--
   <html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage"  value="Print"></html:button>
   -->
   <td><html:button property="but_print" onclick="set('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
   <td><html:reset styleClass="ButtonBackgroundImage" value="clear" onclick="return clearPage()"></html:reset>
 </td>
 <%}else{ %>
  <td><html:submit styleClass="ButtonBackgroundImage" disabled="true">View</html:submit></td>
   <!--
   <html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage"  value="Print"></html:button>
   -->
   <td><html:button property="but_print" disabled="true" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
   <td><html:reset styleClass="ButtonBackgroundImage" value="clear" disabled="true"></html:reset>
 </td>
 <%} %>
 
</tr>

<tr></tr>
<tr></tr>
</table>
<table>
<tr>
	<td>
	
<div id="table1" style="display: block;overflow: scroll;width: 700px;height: 250px">

			<display:table name="OpenClosed" class="its"> 
                   					
            <display:column property="accNo" ></display:column>					
			<display:column property="applicationSrlNo"></display:column>
            <display:column property="applicationDate"></display:column>
            <display:column property="requiredAmount"></display:column>
            <display:column property="sanctionDate"></display:column>
            <display:column property="sanctionedAmount"></display:column>					
	        <display:column property="noInstallments"></display:column>
			<display:column property="installmentAmount"></display:column>
	      					
		</display:table>
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