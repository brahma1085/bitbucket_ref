<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
   <%@page import="java.util.Map"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>
<script type="text/javascript">

function fun_check()
{
	
	document.forms[0].checkbox_InOperativewise.disabled=true;	
	
	if(document.forms[0].txt_loanacctype.value=="All")
	{
		document.forms[0].txt_loantype.disabled=true;
		document.forms[0].txt_endNo.disabled=true;
	document.forms[0].txt_startno.disabled=true;
	}
	else if(document.forms[0].txt_loanacctype.value=="Selected")
	{
	document.forms[0].txt_loantype.disabled=false;
	document.forms[0].txt_endNo.disabled=false;
	document.forms[0].txt_startno.disabled=false;
	}
	
};

function button_fun(target)
{
if(document.forms[0].txt_loanacctype.value=="Selected"){
if(document.forms[0].txt_loantype.value=="Select"){
alert("Please Select the loan type");
document.getElementById("txt_loantype").focus();
}else if(document.forms[0].txt_startno.value==""){
alert("Start number Cannot Be Blank");
document.getElementById("txt_startno").focus();
}
else if(document.forms[0].txt_endNo.value==""){
alert("End number Cannot Be Blank");
document.getElementById("txt_endNo").focus();
}else{
document.forms[0].button_val.value=target;
//return true;
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
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
         function only_numbers() {
	
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
        function only_numbers1() {
	
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
        }
    

 


         
   



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
<h2 class="h2"><center>NPA Process</center></h2> 
</head>
<body class="Mainbody" onload="fun_check()">
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
<%ModuleObject LoanType[]; %>
<%LoanType=(ModuleObject[])request.getAttribute("LoanType"); %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/NPAProcess?pageidentity.pageId=5043">
<table class="txtTable">
	
	<tr>
		<td align="right"><bean:message key="label.processdate"></bean:message></td>
		<td><html:text property="txt_processdate" styleClass="formTextFieldWithoutTransparent" size="10" styleClass="formTextFieldWithoutTransparent" onblur="return datevalidation(this)" onkeypress="return only_numbers1()"

	></html:text></td>
		
		<td><bean:message key="label.principalwise"></bean:message>
		<html:checkbox property="checkbox_prinwise"></html:checkbox></td>
		
		<td><bean:message key="label.Intrestwise"></bean:message>
		<html:checkbox property="checkbox_Intrestwise"></html:checkbox></td>
		
		<td><bean:message key="label.Inoperative"></bean:message>
		<html:checkbox property="checkbox_InOperativewise"></html:checkbox></td>
	</tr>
	
	<tr>
		
		<td></td>
		<td align="right"><bean:message key="label.180days"></bean:message>
		<html:radio property="radio_180" value="true"></html:radio> </td>
		
		<td align="right"><bean:message key="label.90days"></bean:message>
		<html:radio property="radio_90" value="false"></html:radio> </td>
	</tr>
	
	<tr>
		<td align="right"><bean:message key="label.loanacctype"/></td>
		<td><html:select property="txt_loanacctype" styleClass="formTextFieldWithoutTransparent" onchange="return fun_check()">
			<html:option value="All">All</html:option>
			<html:option value="Selected">Selected</html:option>
			</html:select>
				
		
			<html:select property="txt_loantype" styleClass="formTextFieldWithoutTransparent">
			<html:option value="Select">Select</html:option>
			<%if(LoanType!=null){ 
			for(int i=0;i<LoanType.length;i++){%>
			<html:option value="<%=""+LoanType[i].getModuleCode()%>"><%=""+LoanType[i].getModuleAbbrv()%></html:option>
			<%}} %>
			</html:select>
		</td>
		
		<td><bean:message key="label.startno"></bean:message>
		<html:text property="txt_startno" size="10" styleClass="formTextFieldWithoutTransparent" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
		
		<td><bean:message key="label.endNo"></bean:message>
		<html:text property="txt_endNo" size="10" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"></html:text></td>
		
	</tr>
	
	<html:hidden property="button_val" value="error"/>
	<html:hidden property="sysdate" />
	
	
	<tr>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
		<td align="center"><html:submit value="Process" styleClass="ButtonBackgroundImage" styleId="valid_dcbpro_futuredate" onfocus="button_fun('Process')"></html:submit></td>
		<%}else{ %>
		<td align="center"><html:submit value="Process" styleClass="ButtonBackgroundImage" styleId="valid_dcbpro_futuredate" disabled="true"></html:submit></td>
		<%} %>
	</tr>
	
</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
</body>
</html>