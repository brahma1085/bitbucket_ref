<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %> 
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="java.util.Map"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Maturity Ledger</title>

<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
    <h2 class = "h2">
      <center>Maturity Ledger</center></h2>
      
<script type="text/javascript">

function only_numbers_date()
	{
		var cha =   event.keyCode;
		 if (cha >=47 && cha <=57) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	
	
	function  funclear(){

var v = document.forms[0].elements;
for(var i=0;i<=v.length;i++)
{

if(v[i].type =="text"){

v[i].value = "";

}
}
document.getElementById("table1").style.display='none';
 
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
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!")
         		txt.value="";
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

function Hide()
{

if(document.getElementById("totaltesting").value!="")
	{
		alert(document.getElementById("totaltesting").value);	
			
	
var ele=document.forms[0].elements;
	for(var i=0;i<ele.length;i++)
	{
	if(ele[i].type=="hidden")
	{
	ele[i].value="";
	}
	}
	}
}
</script>

</head>
<body onload="Hide()">
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
<%dep_mast = (DepositMasterObject[])request.getAttribute("maturityledger");
  
System.out.println("geetha inside Maturity ledger..");
%>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/MaturityLedgerReport?pageId=13019" >

<html:hidden property="testing" styleId="totaltesting"></html:hidden>
<html:hidden property="forward"></html:hidden>
<html:hidden property="sysdate" />
<table>

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
			         <bean:message key="label.from_date" ></bean:message>
			     	
			     <html:text property="from_date" size="8"  onblur="return datevalidation(this)"  onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text>
			     
			
				
			   		 <bean:message key="label.to_date"  ></bean:message>
			   	
			    <html:text property="to_date" size="8"   onblur="return datevalidation(this)" onchange="properdate(from_date.value,to_date.value)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent" ></html:text>
			       </td>
</tr>
<tr>

</tr>

<tr>
<td>
<center>
				<html:submit property="but_view" onchange="submit()"  styleClass="ButtonBackgroundImage"><bean:message key="label.view1" ></bean:message></html:submit>
				<!--<html:button property="but_query" styleClass="ButtonBackgroundImage"><bean:message key="label.query" ></bean:message></html:button>
				<html:button property="butt_search" styleClass="ButtonBackgroundImage"><bean:message key="label.search"  ></bean:message></html:button>
				<html:button property="but_file" styleClass="ButtonBackgroundImage"><bean:message key="label.file"  ></bean:message></html:button>
				-->
				<html:button property="but_print" styleClass="ButtonBackgroundImage" onclick="set('DownLoad')">DownLoad</html:button>
				<!--
				<html:button property="but_reprint" styleClass="ButtonBackgroundImage" onclick="window.print()"><bean:message key="label.reprint" ></bean:message></html:button>

				-->
				<html:button property="but_clear" styleClass="ButtonBackgroundImage"  onclick="return funclear()"><bean:message key="label.clear" ></bean:message></html:button>
</center>				
</td>
</tr>
</table>


<hr>
<div  id = "table1" style="display:block; overflow:scroll; width: 750px;height: 300px">
<%if(dep_mast!=null){
	
	%>
	
	<display:table name="maturityledger"  id="currentRowObject" class="its" sort="list" >
   			
   

<display:column property="srl_no" ></display:column>
<display:column property="accNo" ></display:column>
<display:column property="name"  title ="Depositor Name"></display:column>
<display:column property="depDate"  title ="Deposit Date"></display:column>
<display:column property="maturityDate" ></display:column>
<display:column property="depositAmt" ></display:column>
<display:column property="maturityAmt" ></display:column>
<display:column property="interestPaid" ></display:column>
<display:column property="cumInterest" ></display:column>





</display:table>

<%} %>
</div>







</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>


</body>
</html>