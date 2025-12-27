<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix = "html" uri = "http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.cashier.TerminalObject"%>
<%@page import="masterObject.cashier.CashObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style type="text/css" media="all">
            @import url("css/alternative.css");
            @import url("css/maven-theme.css");
            @import url("css/site.css");
            @import url("css/screen.css");
    </style>
<link rel="stylesheet" href="css/print.css" type="text/css" media="print" />

<link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
 <script type="text/javascript">
 
 function set(target)
{
  
  document.forms[0].but_value.value=target;
  document.forms[0].submit();

} 
 
function validations()
{
	if(document.getElementById("valid").value!=null)
	{
		if(document.getElementById("valid").value=="invaliddata")
		{
			alert("The date entered is not Valid");
			return false;
		}
	}
	
};

function button_value(target)
{
	var value=confirm("denomination required?");
   	if(value==true)
   	{
 		document.getElementById("value").value="true";
 		document.forms[0].submit();
	}
 	else 
 	{
 		document.getElementById("value").value="false";
 		document.forms[0].submit();
 	}

};

function clearfun()
{
  	alert("Clearing TextFields");
  	var ele=document.forms[0].elements;
  		
  	for(var i=0;i<ele.length;i++)
  		{
  			if(ele[i].type=="text")
  			{
  				ele[i].value="";   
  			}
  		}
};	

function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57)||cha==47 ) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};



function checkformat( ids ,eve ) {

                      var cha =   event.keyCode;
                      var format = true;


             var ff =  ids.value;

            var dd = ff.split( '/' );

			if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          alert (  " problem in date format " );
                          format = false ;
                          break;

                      } else if (  i==2 && dd[i].length != 4 ){

                           alert (  " problem in date format " );
                           format = false ;
                           break;
                      }

            	 }
             } else{

             		format = false ;
             		alert (  " problem in date format " );
             }


			if ( format ){

						var dt = new Date();

						dt.setYear(dd[2]);
						dt.setMonth(dd[1]);
						dt.setDate(dd[0]);

						dd[1] = dd[1] -1;
						var days = 32 - new Date(dd[2],dd[1],32).getDate();

                        

                        if ( dd[0] > days  ){

                                    alert ( " ") ;
                        }

                        var current = new Date();
                        if ( current.getYear() >= dt.getYear() ){

                          // alert ( current.getYear()   +"  current year --given year  "+ dt.getYear()  );
								

						//alert ( (current.getMonth()+1) + "  >  "+ dt.getMonth());
					
							
	
                            if ( (current.getMonth()+1) == ( dt.getMonth() )  ){


                               // alert ( (current.getMonth()+1) + "  >  "+ dt.getMonth());
                                
							if ( current.getDate() >= dt.getDate() ) {
											
													
                                              

                                }  else {
                                    
										alert( "for future date scroll report has not been generated " );
                                }

                            }  else if ( (current.getMonth()+1) > ( dt.getMonth() )  ){
								
												alert( "show scroll report " );

							}  
							else {

                                                 alert( "for future date scroll report has not been generated " );
                            }

                        } else {

                            alert( "for future date scroll report has not been generated" );
                        }
            }

        };
        function report(){
		alert(document.forms[0].report.getSelectedIndex);        
        return false;
        };
        
      
   function dateLimit()
 	{
 	if(document.forms[0].date.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].date.value="";
 	document.forms[0].date.focus;
 	alert("Enter dd/mm/yyyy Format!!!");
 	return false;
 	}
 	}
        
        
        
        

	</script>
        

</head>
<body class="Mainbody" onload="validations()">
<h2 class="h2">
<center>Consolidated Scroll Report</center></h2>

<%!CashObject[] cashobject_withdenom;%> 
<%cashobject_withdenom =(CashObject[])request.getAttribute("cashobject_withdenom"); %>

<%!String value;%>
<%value=(String)request.getAttribute("report"); %>

<%!boolean flag;%>
	
	<%if(value!=null){ %>
	
	<%if(value.equalsIgnoreCase("All Terminals")){
		flag = true;
		
		System.out.print("value in JSP=====>"+value);
	}
	else
	if(value.equalsIgnoreCase("Selected Terminals")){
		flag = false;
		System.out.print("value in JSP=====>"+value);
	}

	}
	%>

<%!String date2;%>
<%date2 = (String)request.getAttribute("date");System.out.println("date"+date2);%>
<% TerminalObject[] terObject= (TerminalObject[])request.getAttribute("combo_terminal");%>
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
<%double tml_runable=(Double)request.getAttribute("tml_runable");%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Scroll?pageId=2006">

<table class="txtTable">
		
		<tr>
		<td><bean:message key ="label.redate"></bean:message></td><br><br>
		<td><html:text property = "date" value = "<%=date2 %>" onblur="checkformat(this)" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" onkeydown="return dateLimit()"></html:text></td><br><br>
		<td><bean:message key ="label.report"></bean:message></td>
		<td><html:select property="report" onchange="submit()" styleClass="formTextFieldWithoutTransparent"><br><br>
		<html:option value="Selected Terminals"></html:option>
		
		</html:select></td>
		<td><html:select property="terminal" disabled="<%=flag%>" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
		
		<%if(terObject!=null){ %>
		<%for(int i=0;i<terObject.length;i++) {%>
		<html:option value="<%=terObject[i].getTerminals()%>" ><%=terObject[i].getTerminals()%></html:option>
		<%} %>
		<% }%>
		
		</html:select></td>
		</tr>
		</table>
		<html:hidden property="but_value"></html:hidden>
		<html:hidden property="value" styleId="value"></html:hidden>
		<html:hidden property="testing" styleId="totaltesting"></html:hidden>
		<html:hidden property="validation" styleId="valid"></html:hidden>
		
		
		
		
		<%System.out.println("+++++++======+++++++"+cashobject_withdenom);%>
		<%!CashObject cashObject; %>
		<%cashObject = (CashObject)request.getAttribute("CashObject"); %>
		
		<%!String denom; %>
		<%denom=(String)request.getAttribute("denomination");
		System.out.print("denomination"+denom);%>
		
		<table class="txtTable">
		<tr>
			<td>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
			<html:submit value="View" onclick="return button_value('View')" styleClass="ButtonBackgroundImage"></html:submit>
			<%}%>
			<html:submit style="visibility:hidden;"></html:submit>
			<html:button onclick="window.print()" property ="printFile"  styleClass="ButtonBackgroundImage"><bean:message key="label.print"></bean:message> </html:button>
			<html:button property="but_print" onclick="set('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
			<html:submit onclick="return clearfun()" styleClass="ButtonBackgroundImage">Clear</html:submit>
          </td>
			
			</tr>
	</table>
		
		
		
		 <table class="txtTable">
		    
		        <tr>
		          <td>
		        	
		        	<%if(denom!=null){%>
		        	<%if(denom.equalsIgnoreCase("denomination")){ %>	          
		        	<div  id = "table1" style="display: block;width: 750px;height: 300px;overflow: scroll">
   				<display:table name="cashobject_withdenom"  pagesize="10" requestURI="/Scroll.do">
   				<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   				<display:column media="csv excel" title="URL" property="url" />
    			<display:setProperty name="export.pdf" value="true" /> 
		        	
		          	<display:column property="scrollno" title="Scr No"></display:column>	 
		          	
		          	<display:column property="acctype" title="Ac Type"></display:column>	 
		          	
		          	<display:column property="accno" title="Ac No"></display:column>	 
		          
		          	<display:column property="accname" title="Payee Name"></display:column>	 
		          	
		          	<display:column property="tokenno" title="Tkn No"></display:column>	 
		          	
		          	
		          	<display:column property="amount" title="Csh Reced"></display:column>	 
		          	
		          	<display:column property="amount" title="Csh Paid"></display:column>	 
		         	
		         	
		         	<display:column property="commamt" title="Trf Recd"></display:column>	 
		          	
		          	<display:column property="commamt" title="Trf Paid"></display:column>	 
		          	
		          	<display:column property="r1000" title="1000"></display:column>	 
		          	
		          	<display:column property="r500" title="500"></display:column>	 
		          
		          	<display:column property="r100" title="100"></display:column>	 
		          	
		          	<display:column property="r50" title="50"></display:column>	 
		          	
		          	<display:column property="r20" title="20"></display:column>	 
		          	
		          	<display:column property="r10" title="10"></display:column>	
		          	
		          	<display:column property="r5" title="5"></display:column>	 
		          	
		          	<display:column property="r2" title="2"></display:column>
		          	
		          	<display:column property="r1" title="1"></display:column>	 	  
		          
		          	<display:column property="rcoins" title="coins"></display:column>	 
		          	
		          	<display:column property="runbal" title="Runabal"></display:column>	 
		          	
		          	<display:column property="terminalNo" title="Tml No"></display:column>	 
		          	
		          	<display:column property="userTml" title="Recd By"></display:column>	 
		          
		          	<display:column property="userTml" title="Verf By"></display:column>	 
		          	
		          	<display:column property="userTml" title="Paid By"></display:column>	 
		         
		          	</display:table >
				   
		     		<table align="center" class="txtTable">
				   	<tr><td>
				         
		     		<display:table name="CashObject" export="true" requestURI="/Scroll.do" id="currentRowObject" class="its" sort="list" pagesize="10">
   					<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   					<display:column media="csv excel" title="URL" property="url" />
    				<display:setProperty name="export.pdf" value="true" /> 
		           	<display:column title="O/B"></display:column>	 
		          	
		          	<display:column property="dayOpeningBalance" title="Total"></display:column>	 
		          	
		          	<display:column title="Reciepts"></display:column>	 
		          
		          	<display:column property="dayReceipts" title="Total"></display:column>	 
		          	
		          	<display:column title="Payments"></display:column>	 
		          	
		          	<display:column property="dayPayments" title="Total"></display:column>	 
		          	
		          	<display:column title="C/B"></display:column>	 
		         	
		         	<display:column property="dayClosingBalance" title="Total"></display:column>	 
		          	
		          	</display:table>
		         	</td></tr></table>
		         	</div>
		       		</td>
		       		</tr>
		       		 </table>
		       	
		       	<table class="txtTable">
		       	<tr>
		       	<td>
		       <%} else if(denom.equalsIgnoreCase("woutdenomination")) {%>
		      		
		      		<display:table name="cashobject_withdenom" export="true" id="currentRowObject" requestURI="/Scroll.do" class="its" pagesize="10">
   					<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   					<display:column media="csv excel" title="URL" property="url" />
    				<display:setProperty name="export.pdf" value="true" />
		      		<display:column property="scrollno" title="Scr No"></display:column>	 
		          	
		          	<display:column property="acctype" title="Ac Type"></display:column>	 
		      		
		      			<display:column property="accno" title="Ac No"></display:column>	 
		          
		          	<display:column property="accname" title="Payee Name"></display:column>	 
		          	
		          	<display:column property="tokenno" title="Tkn No"></display:column>	 
		          	
		          	
		          	<display:column property="amount" title="Csh Reced"></display:column>	 
		          	
		          	<display:column property="amount" title="Csh Paid"></display:column>	 
		         	
		        	<display:column property="commamt" title="Trf Recd"></display:column>	 
		          	
		          	<display:column property="commamt" title="Trf Paid"></display:column>	 
		          	
		          		<display:column property="runbal" title="Runabal"></display:column>	 
		          	
		          	<display:column property="terminalNo" title="Tml No"></display:column>	 
		          	
		          	<display:column property="userTml" title="Recd By"></display:column>	 
		          
		          	<display:column property="userTml" title="Verf By"></display:column>	 
		          	
		          	<display:column property="userTml" title="Paid By"></display:column>	 
		      		</display:table> 
				<%} %>	
				<%} %>	
				</td></tr>
				</table>
		      
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
<%} %>

</body>
</html>