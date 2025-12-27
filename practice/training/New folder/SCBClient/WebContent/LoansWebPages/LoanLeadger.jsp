<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
  <%@page import="java.util.Map"%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.LedgerObject"%>
<%@page import="masterObject.loans.LoanMasterObject"%>
<html>
<head>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function validation_fields(){
if(document.forms[0].combo_accounts.value=="AllAccounts")
	{
	if(document.getElementById("txt_startaccnum").value=="0")
	{
		alert('Enter the Starting Account Number!');
		document.getElementById("txt_startaccnum").focus();
		return false;
		}
		else if(document.getElementById("txt_startaccnum").value=="")
	{
		alert('Enter the Starting Account Number!');
		document.getElementById("txt_startaccnum").focus();
		return false;
		}
		else if(document.getElementById("txt_endingaccnum").value=="")
	{
		alert('Enter the Ending Account Number!');
		document.getElementById("txt_endingaccnum").focus();
		return false;
		}
		else if(document.getElementById("txt_endingaccnum").value=="0")
	{
		alert('Enter the Ending Account Number!');
		document.getElementById("txt_endingaccnum").focus();
		return false;
		}
		else if(document.getElementById("txt_startdate").value=="")
	{
		alert('Enter the Starting Date!');
		document.getElementById("txt_startdate").focus();
		return false;
		}
		else if(document.getElementById("txt_enddate").value=="")
	{
		alert('Enter the Ending Date!');
		document.getElementById("txt_enddate").focus();
		return false;
		}
	}
	if(document.forms[0].combo_accounts.value=="OpenAccounts")
	{
	if(document.getElementById("txt_startaccnum").value=="0")
	{
		alert('Enter the Starting Account Number!');
		document.getElementById("txt_startaccnum").focus();
		return false;
		}
		else if(document.getElementById("txt_startaccnum").value=="")
	{
		alert('Enter the Starting Account Number!');
		document.getElementById("txt_startaccnum").focus();
		return false;
		}
		else if(document.getElementById("txt_endingaccnum").value=="")
	{
		alert('Enter the Ending Account Number!');
		document.getElementById("txt_endingaccnum").focus();
		return false;
		}
		else if(document.getElementById("txt_endingaccnum").value=="0")
	{
		alert('Enter the Ending Account Number!');
		document.getElementById("txt_endingaccnum").focus();
		return false;
		}
		}
		if(document.forms[0].combo_accounts.value=="ClosedAccounts")
	{
	if(document.getElementById("txt_startdate").value=="")
	{
		alert('Enter the Starting date!');
		document.getElementById("txt_startdate").focus();
		return false;
		}
		
		
		else if(document.getElementById("txt_enddate").value=="")
	{
		alert('Enter the Ending Date!');
		document.getElementById("txt_enddate").focus();
		return false;
		}
		}


};



function combo_select()
{ 
	//alert(document.forms[0].combo_accounts.value);
	if(document.forms[0].combo_accounts.value=="AllAccounts")
	{
		document.getElementById("accnumid").style.display = 'block';
	    document.getElementById("accnumid1").style.display = 'block';
		document.getElementById("dis_block").style.display = 'block';
	}
	if( document.forms[0].combo_accounts.value=="ClosedAccounts")
	{
	    document.getElementById("accnumid").style.display = 'none';
	    document.getElementById("dis_block").style.display = 'block';
	    document.getElementById("accnumid1").style.display = 'none';
	    
	}
	if(document.forms[0].combo_accounts.value=="OpenAccounts")
	{
		 document.getElementById("accnumid").style.display = 'block';
	    document.getElementById("accnumid1").style.display = 'block';
	    document.getElementById("dis_block").style.display = 'none';
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
}  ;
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
<h2 class="h2"><center>Loan Leadger</center></h2>

</head>
<body class="Mainbody">

<%ModuleObject LoanCategory[]; %>
<%!String CatDesc; 
LedgerObject[] ledger_obj;
LoanMasterObject lm=new LoanMasterObject();%>
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


<% 
   LoanCategory=(ModuleObject[])request.getAttribute("LoanCategory");
  CatDesc=(String)request.getAttribute("CatDesc");
  String message=(String)request.getAttribute("msg");
  ledger_obj=(LedgerObject[])request.getAttribute("LedgerObject");
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
<html:form action="/Loans/LoanLeadger?pageidentity.pageId=5028">



<table>
		
	<table>	
		<tr>
		    
			<td align="right"><bean:message key="label.loancat"/>
			<html:select  property="combo_loancat" onblur="submit()">
				<%if(LoanCategory!=null){ %>
					<%for(int i=0;i<LoanCategory.length;i++){ %>
					<html:option value="<%= ""+i %>"><%= LoanCategory[i].getModuleAbbrv() %></html:option>
				<%}}%>	
				</html:select>
			<%if(CatDesc!=null){ %>
			<font size="3" color="red">
				<html:text property="catdescription" styleClass="formTextField" value="<%= ""+CatDesc %>"></html:text></font>
			<%}%>		
			</td>
		</tr>
		
	</table>
	<table>	
		<tr>
		<td><bean:message key="label.accounts"/></td>
			<td align="right"><html:select property="combo_accounts" onblur="combo_select()">
			    <html:option value="OpenAccounts">OpenAccounts</html:option>
				<html:option value="ClosedAccounts">ClosedAccounts</html:option>
				<html:option value="AllAccounts">AllAccounts</html:option>
				</html:select>
			</td>
		</tr>
		<tr>
			<td>
			<div id="accnumid" style="display: none;">
				<bean:message key="label.startaccnum"></bean:message>
				<html:text property="txt_startaccnum" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"></html:text>
			</div>
			</td>
		</tr>
		<tr>
			<td align="right">
				<div id="accnumid1" style="display: none;">
				<bean:message key="label.Endingaccnum"></bean:message>
				<html:text property="txt_endingaccnum" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"></html:text>
				</div>
			  </td>
		</tr>
		
	   <tr>
			<td align="right">
				<div id="dis_block" style="display: none;" >
				<table>
					  <tr>
						<td><bean:message key="label.staedate"></bean:message></td>
						<td><html:text property="txt_startdate" size="10" onblur="return datevalidation(this)" onkeypress="return only_numbers1()"></html:text></td>
					</tr>
					<tr>
						<td><bean:message key="label.enddate"></bean:message>  </td>
						<td><html:text property="txt_enddate" size="10" onblur="return datevalidation(this)" onkeypress="return only_numbers1()"></html:text>  </td>
					</tr>
				</table>
				</div>
			</td>
		</tr>
		
   
		<html:hidden property="forward" value="error"/>	
		<html:hidden property="sysdate" />
		<tr>
			<!--<td><html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage" onblur="combo_select()" onfocus="validation_fields()" value="Print"></html:button></td>
			
			-->
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
			<td><html:submit property="file" onclick="submit()" styleClass="ButtonBackgroundImage" onblur="combo_select()" onfocus="validation_fields()" value="File"></html:submit></td>
			<%}else{ %>
			<td><html:submit property="file" onclick="submit()" styleClass="ButtonBackgroundImage" disabled="true" value="File"></html:submit></td>
			<%} %>
		</tr>
		
</table>		
</table>
<%if(ledger_obj!=null){
	for(int i=0;i<ledger_obj.length;i++){
	System.out.println("Ledger Object" + ledger_obj[i].lm.getShareAccType());
	System.out.println("Ledger Object" + ledger_obj[i].lm.getAccNo());
	
	
     }} %>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>