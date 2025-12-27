<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.lockers.LockerDetailsObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>

<script type="text/javascript">

 function  getNumbersOnly()
  {
	var cha = event.keyCode
	
	if((cha >= 46 && cha < 58)) 
	{
		return true ;
    }
    return false ;
  }
        




function getDetails(){


document.forms[0].lockerType1.value=document.getElementById('d1').value;

document.forms[0].dateFrom1.value=document.getElementById('d2').value;
document.forms[0].dateTo1.value=document.getElementById('d3').value;

document.forms[0].daysFrom1.value=document.getElementById('d4').value;
document.forms[0].daysTo1.value=document.getElementById('d5').value;

document.forms[0].lockerRate1.value=document.getElementById('d6').value;
document.forms[0].securityDeposit1.value=document.getElementById('d7').value;


document.forms[0].forward.value='submit';

///var conVar=confirm(document.forms[0].lockerType1.value+"<-->"+document.forms[0].dateFrom1.value+"<-->"+document.forms[0].dateTo1.value +"<-->"+document.forms[0].daysFrom1.value+"<-->"+document.getElementById('d5').value+"<-->"+document.getElementById('d6').value+"<-->"+document.getElementById('d7').value);

var conVar=confirm("Do You Want Insert Details");

	if(conVar)
	{
		document.forms[0].submit();
	}
	else
	{
		return false;
	}



}
function onlyDoublenumbers(){
        	
        	var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || cha == 46 ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        }
function set(name){

document.forms[0].forward.value=name;

}

  function getMessage(){
  
  if(document.forms[0].validateFlag.value==""){
  return false;
  }
  else{
  alert(document.forms[0].validateFlag.value);
  }
  
  }
  
  
  function checkformat(ids) 
	        {
            
                      var cha =   event.keyCode;
                      var format = true;
                      var allow=true;

             var ff =  ids.value;
            
            var dd = ff.split( '/' );

			if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          alert("Problem In Date Format");
                          format = false ;
                          allow=false;
                          

                      } else if (  i==2 && dd[i].length != 4 ){

                           alert("Problem In Date Format");
                           format = false ;
                           allow=false;
                      }

            	 }
             } else{
                    allow=false;
             		format = false ;
             		alert ("Date should be in the formatt like DD/MM/YYYY");
             }

        if(format){
        var date=new Date();
         var dayCheck=dd[0];
         
         var monthCheck=dd[1];
         var yrCheck=dd[2];
         var mth=dd[1];
         
         dd[1]=dd[1]-1;
	   var days = 32 - new Date(dd[2],dd[1],32).getDate();
         if(dd[0].length==2){
          if ( dd[0] > days  )
                        {

                              alert("Day should not be greater than "+days);
                              allow=false;
                              
                        }
                        
          
          if(dd[0]==00){
          alert("There is no date with 00");
          allow=false;
          }
          

          if(mth<1 || mth>12){
          alert("Month should be greater than 0 and \n lessthan 13  "+mth);
          allow=false;
          }
          }
       
         if(dd[2].length==4){
          if((parseInt(dd[2])<=parseInt(date.getYear())))
          {
          	allow=true;
                        
           }
          
          }
          else
          {
          	alert("Enter Valid Date");
          }
          


         }
		if(allow)
		{
			return true;
		}
		else
		{
		
		 return false;
		}

        } 
  
</script>

</head>
<body bgcolor="#f3f4c8" onload="getMessage()">
<center><h2 class="h2"><bean:message key="lable.rateAdmin"></bean:message></h2></center>
<%@page import="java.util.Map"%>


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
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/RateAdminLink?pageId=9008">
<%! LockerDetailsObject[] lockerDetailsObject; 
	String insert;
	String[] lkTypes;
%>
<% lockerDetailsObject=(LockerDetailsObject[])request.getAttribute("tableDetails"); 
	insert=(String)request.getAttribute("insert");
	lkTypes=(String[])request.getAttribute("lktypes");
%>
<table class="txtTable">

<tr><td ><bean:message key="lab.lockerTypes" ></bean:message> </td><td ><bean:message key="label.dateFrom" ></bean:message> </td> <td> <bean:message key="label.dateTo" ></bean:message> </td> <td> <bean:message key="label.daysFrom" ></bean:message> </td> <td><bean:message key="label.daysTo" ></bean:message> </td> <td><bean:message key="label.lockerRate" ></bean:message> </td> <td><bean:message key="label.securityDeposit" ></bean:message> </td> </tr>

<%if(lockerDetailsObject!=null){
	for(int i=0;i<lockerDetailsObject.length;i++){ %>

<tr>
<td><html:text property="lockerType" readonly="true" value="<%=lockerDetailsObject[i].getLockerType() %>" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><html:text property="dateFrom" 	 readonly="true" value="<%=lockerDetailsObject[i].getDateFrom() %>" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><html:text property="dateTo"	 readonly="true" value="<%=lockerDetailsObject[i].getDateTo()%>" size="10"    styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><html:text property="daysFrom" 	 readonly="true" value="<%=""+lockerDetailsObject[i].getDaysFrom()%>" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><html:text property="daysTo" 	 readonly="true" value="<%=""+lockerDetailsObject[i].getDaysTo()%>" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><html:text property="lockerRate" readonly="true" value="<%=""+lockerDetailsObject[i].getLockerRate()%>" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><html:text property="securityDeposit" readonly="true"  value="<%=""+lockerDetailsObject[i].getSecurityDeposit()%>" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>

</tr>

<%}%>

<%
System.out.println("####----->"+lkTypes);
if(insert!=null && lkTypes!=null){ 
	
%>
<tr>

<td><html:select property="lockerType" styleId="d1">

<% for(int i=0;i<lkTypes.length;i++){ %>
<html:option value="<%=lkTypes[i] %>"></html:option>
<% } %>

</html:select></td>



<td><html:text property="dateFrom"  styleId="d2" onblur="return checkformat(this)"  size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><html:text property="dateTo"	styleId="d3" onblur="return checkformat(this)" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>

<td><html:text property="daysFrom" onkeypress="return getNumbersOnly()" styleId="d4"  size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><html:text property="daysTo"   onkeypress="return getNumbersOnly()" styleId="d5"  size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>

<td><html:text property="lockerRate" onkeypress="return onlyDoublenumbers()" styleId="d6"  size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><html:text property="securityDeposit" onkeypress="return onlyDoublenumbers()" styleId="d7"  size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><html:button property="chkBox" styleClass="ButtonBackgroundImage" onclick="getDetails()">SUBMITD-DATA</html:button> </td>
</tr>

<% } }  %>
</table>
<html:hidden property="lockerType1"></html:hidden>
<html:hidden property="dateFrom1"></html:hidden>
<html:hidden property="dateTo1"></html:hidden>
<html:hidden property="daysFrom1"></html:hidden>
<html:hidden property="daysTo1"></html:hidden>
<html:hidden property="lockerRate1"></html:hidden>
<html:hidden property="securityDeposit1"></html:hidden>
<html:hidden property="validateFlag"></html:hidden>

<html:hidden property="forward"></html:hidden>

<!--<html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message></html:submit>
-->
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <html:submit onclick="set('insert')" styleClass="ButtonBackgroundImage"><bean:message key="label.insert"></bean:message></html:submit>
             <%}else{ %>
           <html:submit onclick="set('insert')" disabled="true" styleClass="ButtonBackgroundImage"><bean:message key="label.insert"></bean:message></html:submit>
             <%} %>



</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>