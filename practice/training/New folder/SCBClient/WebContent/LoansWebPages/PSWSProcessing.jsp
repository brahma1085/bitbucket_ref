<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.loans.PSWSObject"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<html>
<head>

<script type="text/javascript">
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

 


function function_butval(target)
{
//	alert(target);
	document.forms[0].button_val.value=target;
	document.forms[0].submit();
}

function pswsprocessing()
{
	if(document.getElementById("process").value!=null)
	{
		
		if(document.getElementById("process").value=="PrevRecorNotPrinted")
		{
		  var value=confirm("Previous Records are not Printed Do u want to Process Again");
		  alert(value);
		  if(value==true)
		  {
		  	
		  	document.getElementById("provalue").value="process_value";
		  }else
		  {
		  	
		  	document.getElementById("provalue").value="error_process";
		  	return false;
		  }
		}
	}

}
function function_ShowPart1(target)
{
	alert(target);
	document.forms[0].button_val.value=target;
}
function function_ShowPart2(target)
{
	alert(target);
	document.forms[0].button_val.value=target;
}
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

<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>PSWS Process Report</center></h2>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%String ShowPart1; %>
<%ShowPart1=(String)request.getAttribute("ShowPart1"); 
  System.out.println("Show part one---------------<>"+ShowPart1);	
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




<body onload="pswsprocessing()">
<%String message=(String)request.getAttribute("msg"); %>
<%if(message!=null){ %>
<div id = "div1" class = "myLayersClass">
    
    <core:if test="<%=message!=null %>">
    <font color="red"><%=message %></font>
    <br><br><br><br>
    </core:if>
    </div>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/PSWSProcessing?pageidentity.pageId=5035">
	
	<table>
			<tr>
			<td>
			<table>
			<tr>
			  <html:hidden property="testing" styleId="totaltesting"></html:hidden>
				<td align="right"><bean:message key="label.date"></bean:message></td>
				<td><html:text property="txt_date" size="10" 
onblur="return datevalidation(this)" onkeypress="return only_numbers()"></html:text></td>
				
				<html:hidden property="button_val" value="error" />		
				<html:hidden property="pswsprocessing"  styleId="process"/>
				<html:hidden property="pro_value"  styleId="provalue" />
				<html:hidden property="sysdate" /> 
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 	
				<td align="right"><html:submit value="Process" onclick="function_butval('Process')" styleClass="ButtonBackgroundImage"></html:submit></td>
				<td><html:submit value="ShowPart1" onclick="function_ShowPart1('ShowPart1')" styleClass="ButtonBackgroundImage"></html:submit></td>
				<td><html:submit value="ShowPart2" onclick="function_ShowPart2('ShowPart2')" styleClass="ButtonBackgroundImage"></html:submit></td>
				<%}else{ %>
				<td align="right"><html:submit value="Process" disabled="true" styleClass="ButtonBackgroundImage"></html:submit></td>
				<td><html:submit value="ShowPart1" disabled="true" styleClass="ButtonBackgroundImage"></html:submit></td>
				<td><html:submit value="ShowPart2" disabled="true" styleClass="ButtonBackgroundImage"></html:submit></td>
				<%} %>
				
				</tr>
			</table>
			</td>	
			</tr>
			
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			
			<tr>
				<td>
					<table>
						  <tr>	
						  <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 	
						   	<td><html:button property="clear" value="Clear" styleClass="ButtonBackgroundImage" onclick="return clearPage()"></html:button>
						   		<!--<html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage"  value="Print"></html:button>
						   	--></td>
						   	  <td><html:button property="but_print" onclick="function_butval('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
						   	  <%}else{ %>
						   	  <td><html:button property="clear" value="Clear" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
						   		<!--<html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage"  value="Print"></html:button>
						   	--></td>
						   	  <td><html:button property="but_print" disabled="true" styleClass="ButtonBackgroundImage">DownLoad</html:button>
						   	  <%} %>
   </td>
						   	</tr>
					</table>	   
			    </tr>
			    
			    <tr>
		          <td id="ShowPart1">
		          	
		        	<div  id = "table1" style="display: block;overflow: scroll;width: 600px;height: 150px">
		           <display:table name="PSWSProcess" id="PSWSProcess" class="its"  >
		          	<display:column title="Srl No" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${PSWSProcess.SrlNo}" size="5" /></display:column>
					<display:column title="No Of borrower units" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSProcess.PrioritySectorItems}"  /></display:column>
					<display:column title="Limit Sactioned" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="formTextField" value="${PSWSProcess.NoOfborrowerunits}" /></display:column>
					<display:column title="Amount Advanced" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${PSWSProcess.LimitSactioned}" /></display:column>
					<display:column title="Balance OutStanding" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSProcess.AmountAdvanced}" /></display:column>
					<display:column title="Amount overdue under Column-6" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSProcess.BalanceOutStanding}" /></display:column>
					<display:column title="No Of borrower units" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSProcess.AmountoverdueunderColumn}" /></display:column>
					<display:column title="Limit Sactioned" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${PSWSProcess.NoOfborrowerunits}" /></display:column>
					<display:column title="Amount Advanced" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSProcess.LimitSactioned}" /></display:column>
					<display:column title="Balance OutStanding" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSProcess.AmountAdvanced}" /></display:column>
					<display:column title="Amount overdue under Column-12" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PSWSProcess.BalanceOutStanding}" /></display:column>
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