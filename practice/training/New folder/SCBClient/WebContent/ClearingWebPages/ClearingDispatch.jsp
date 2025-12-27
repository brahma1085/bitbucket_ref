<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c"    uri="/WEB-INF/c-rt.tld" %>

<%@page import="java.util.Map"%>


<%@page import="masterObject.clearing.ChequeDepositionObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">

function setFlag(flagVal)
{
	document.forms[0].flag.value=flagVal;
	document.forms[0].submit();

}

function checkformat(ids) 
{
            
   var cha = event.keyCode;
   var format=true;
   var allow=true;

   var ff =  ids.value;
   var dd = ff.split('/');

	if(dd.length == 3)
	{
		for(var i =0;i< dd.length;i++)
        {
        	if(i != 2 && dd[i].length != 2)
            {
				document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
                format = false;
                allow=false;
            } 
            else if(i==2 && dd[i].length != 4)
            {
				document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
                format=false;
                allow=false;
            }
         }
     }
     else
     {
     	 document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
         allow=false;
         format = false ;
         
     }
	
	if(format)
    {
       var date=new Date();
       var dayCheck=dd[0];
       var monthCheck=dd[1];
       var yrCheck=dd[2];
       var mth=dd[1];
       dd[1]=dd[1]-1;
	   var days = 32 - new Date(dd[2],dd[1],32).getDate();
        	 
       if(dd[0].length==2)
       {
         	if(dd[0] > days)
            {
				document.forms[0].validateFlag.value="Day should not be greater than "+days+" ";
                allow=false;
            }
            if(dd[0]==00)
      		{
          		document.forms[0].validateFlag.value="There is no date with 00";
          		allow=false;
          	}
          	if(mth<1 || mth>12)
          	{
          		document.forms[0].validateFlag.value="Month should be greater than 0 and \n lessthan 13"+mth+" ";
          		allow=false;
          	}
       }
       if(dd[2].length==4)
       {
          	if((parseInt(dd[2])<=parseInt(date.getYear())))
          	{
                          
          	}
          	else
          	{
          		document.forms[0].validateFlag.value="Year should be less than "+date.getYear()+" ";
          		allow=false;
          	}
        }
    }
	if(allow)
	{
		 
		document.forms[0].flag.value='frmDate';
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].clr_date.focus();
		 
	}

}	

function setChkBox()
{
	
	var v=document.forms[0].chkBox;
	
	if(document.forms[0].selectAll.checked)
	{
	  for(var i=0;i<v.length;i++)
	  {
		v[i].checked=true;
	  }
	}
	else
	{
		for(var i=0;i<v.length;i++)
	  	{
			v[i].checked=false;
	  	}
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
 
</head>


<body class="Mainbody">
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
<html:form action="/Clearing/ClearingDispathcLink?pageId=7006">
<center><h2 class="h2">Clearing Dispatch</h2></center>
<% ChequeDepositionObject[] chequeDepositionObjects=(ChequeDepositionObject[])request.getAttribute("dispatchDeatils"); 
%>

<html:hidden property="flag"></html:hidden>
<table>
	<tr><html:text property="validateFlag" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
	<tr>
	<td>	
		<table>
		<tr><td><bean:message key="label.clr_date"></bean:message></td>
			<td><html:text property="clr_date" value="${requestScope.date}" onblur="checkformat(this)" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		</tr>

	<tr>
	<td><bean:message key="label.bankName"></bean:message></td>
	<td><html:select property="clgBank" onblur="setFlag('frmClgBank')" styleClass="formTextFieldWithoutTransparent" >

	<c:if test="${requestScope.bankNums!=null}">
	<c:forEach var="bankNums" items="${requestScope.bankNums}">
	<html:option value="${bankNums}"></html:option>
	</c:forEach>
	</c:if>
	</html:select>
	</td>
	</tr>


	<tr>
	<td><bean:message key="label.clr_no"></bean:message></td>
	<td><html:select property="clr_no" onblur="setFlag('frmClr_no')" styleClass="formTextFieldWithoutTransparent">
	<c:forEach var="clgNums" items="${requestScope.clgNums}">
	<html:option value="${clgNums}"></html:option>
	</c:forEach>
	</html:select></td>
	<td><bean:message key="label.selectAll"></bean:message></td>
	<td><input type="checkbox" name="selectAll" onclick="setChkBox()"></td>
	</tr>


</table>
</td>
</tr>
<tr><td>
	<table>
		
	<tr>
	<td>
	<table>
	<%if(chequeDepositionObjects!=null){ %>
	<tr>
	<td>SN</td>
	<td>CtrlNum</td>
	<td>ClearingDate</td>
	<td>ChequeAmt</td>
	<td>Destination</td>
	<td>ClgType</td>
	<td>Select</td>
	</tr>
		<%for(int i=0;i<chequeDepositionObjects.length;i++){%>
			<tr>
			<td><%=(i+1)%></td>
			<td><html:text property="txtCtrlNum" value="<%=""+chequeDepositionObjects[i].getControlNo()%>" readonly="true" size="7"/></td>
			<td><html:text property="txtClgBank" value="<%=""+chequeDepositionObjects[i].getExpectedClgDate()%>" readonly="true" size="10" /></td>
			<td><html:text property="txtChqAmt" value="<%=""+chequeDepositionObjects[i].getTranAmt()%>" readonly="true" size="10"/></td>
			<td><html:text property="txtDestination" value="<%=""+chequeDepositionObjects[i].getDocDestination()%>" readonly="true" size="8"/></td>
			<td><html:text property="txtClgType" value="<%=""+chequeDepositionObjects[i].getClgType() %>" readonly="true" size="5"/></td>
			<td><input type="checkbox" name="chkBox" value="<%=i %>"></td>
			</tr>
			
		<%}%>
	
	</table>
	</td>
	</tr>
	<tr>
 		<td> 
 		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            <html:button property="button_submit"  value="Dispatch" styleClass="ButtonBackgroundImage"   onclick="setFlag('submit')"></html:button>
             <%}else{ %>
            <html:button property="button_submit"  value="Dispatch" styleClass="ButtonBackgroundImage" disabled="true"  onclick="setFlag('submit')"></html:button>
             <%} %>
 		
		    
 		</td>
	</tr>
	
	<%} %>
		
	</table>
</td></tr>
</table>


</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>