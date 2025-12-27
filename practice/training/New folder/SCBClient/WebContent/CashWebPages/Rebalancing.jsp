<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix = "html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix = "bean" uri="/WEB-INF/struts-bean.tld"%>
<%@page import="java.util.Map"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<html>
<head>
<title>Insert title here</title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function validations()
{
	if(document.getElementById("valid").value!=null)
	{
		if(document.getElementById("valid").value=="success")
		{
			alert("Rebalnced Successfull!!!");
			return false;
		}
		if(document.getElementById("valid").value=="noentry")
		{
			alert("No entry in Currency_Stock");
			return false;
		}
		if(document.getElementById("valid").value=="notransction")
		{
			alert("No Transacion");
			return false;
		}
		
	
	}
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

function validate1()
	{

		if(document.forms[0].date.value=="")
		{
			alert("Enter Date");
			return false;
		}
	}	
	
function validate()
	{
	alert("U want cancel");
	document.forms[0].date.value="";
	}

	   function checkformat( ids ,eve  ) {

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

                        //alert(days + "invalid date ");

                        if ( dd[0] > days  ){

                                    alert ( "invalid date ") ;
                        }

                        var current = new Date();
                        if ( current.getYear() >= dt.getYear() ){

                          // alert ( current.getYear()   +"  current year --given year  "+ dt.getYear()  );
								

						//alert ( (current.getMonth()+1) + "  >  "+ dt.getMonth());
					
							
	
                            if ( (current.getMonth()+1) == ( dt.getMonth() )  ){


                               // alert ( (current.getMonth()+1) + "  >  "+ dt.getMonth());
                                
							if ( current.getDate() >= dt.getDate() ) {
											
												alert( "Rebalance Can Be Done" );	
                                              

                                }  else {
                                    
										alert( "Rebalance Cannot Be done For Future Date" );
                                }

                            }  else if ( (current.getMonth()+1) > ( dt.getMonth() )  ){
								
												alert( "Rebalance Can Be Done" );

							}  
							else {

                                                 alert( "Rebalance Cannot Be done For Future Date " );
                            }

                        } else {

                            alert( "Rebalance Cannot Be done For Future Date" );
                        }
            }

        };

</script>
</head>
<body class="Mainbody" onload="validations()">
<h2 class="h2">

<center>Rebalancing Scroll Report</center></h2>

	<%!
  
     String date2;
	%>
<%date2 = (String)request.getAttribute("date");%>
<%double tml_runable=(Double)request.getAttribute("tml_runable");%>
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

<h3 align="left" style="font-size:x-small;color: red;font-family:Comic Sans MS "><bean:message key="label.terminalrs"/><%=tml_runable%></h3>

<%if(access!=null&&accesspageId!=null&&access.charAt(0)==1){ %>
<html:form action="/Rebalancing?pageId=2011&value=1">
<table cellspacing="10" style="border:thin solid #339999;" class="txtTable">
<tr><td>
	<html:hidden property="validation" styleId="valid"/>
	<table class="txtTable">
		
		<tr>
		<td><bean:message key ="label.redate"></bean:message></td>
		<td><html:text property = "date" value="<%=date2%>" onblur="checkformat(this)" onkeydown="return dateLimit()" styleClass="formTextFieldWithoutTransparent"><%=date2%></html:text></td></tr>
	
	</table>
	</td>
	<td>
	<table>	
		
		<tr><td>
		<%if(access!=null&&accesspageId!=null&&access.charAt(1)=='1'){%>
		<html:submit property="submit" value="Rebalance" onclick="return validate1(this.value,'submit')" styleClass="ButtonBackgroundImage"></html:submit>
		<%}else{ %>
		<html:submit property="submit" value="Rebalance" onclick="return validate1(this.value,'submit')" styleClass="ButtonBackgroundImage" disabled="true" ></html:submit>
		<%} %>
		</td>
		
		<td><html:button property="clear" value="Clear" onclick="validate()" styleClass="ButtonBackgroundImage"></html:button></td></tr>
	
	</table>
	</td>
	</tr>
	</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
	
</body>
</html>