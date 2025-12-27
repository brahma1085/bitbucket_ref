<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="c"    uri="/WEB-INF/c-rt.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.BranchObject"%>
<%@page import="masterObject.clearing.AckObject"%>
<%@page import="java.util.Map"%>
<html>
<head>

<script  type="text/javascript">


function getClearingType()
{
document.forms[0].flag.value="1";
document.forms[0].submit();

}
function clearAll()
{
	document.forms[0].ackNumber.value=0;
	document.forms[0].amount.value="";
	
}

function onlynumbers()
 {
       var cha = event.keyCode;
		if ( cha >= 48 && cha <= 57  )
        {
           return true;
        } 
        else 
        {
        	return false;
        }
			   		 
 }   



function getMessages()
{

	if(document.forms[0].errorFlag.value=='1')
	{
		clearAll();
	}

}
function set(target)
{
   if(document.forms[0].amount.value=="")
   {	
   	   document.forms[0].validateMsg.value='Enter amount';
   	   
       return false;
   }
   else if(document.forms[0].ackNumber.value=="")
   {
   		document.forms[0].validateMsg.value='Enter Valid Ack Num';
   }
   else
   {
      
      if(target=='submit')
      {
      		if(document.forms[0].ackNumber.value==0)
      		{
        		document.forms[0].flag.value=target;
        		document.forms[0].booleanFlag.value=0;
        		document.forms[0].submit();
        	}
        	else
        	{
        		document.forms[0].validateMsg.value="You Can't Submit Values";
        	}	
      }
      else if(target=='update')
      { 
      		if(document.forms[0].ackNumber.value!=0)
      		{
      			document.forms[0].flag.value=target;
        		document.forms[0].booleanFlag.value=0;
        		document.forms[0].submit();
      		}
      		else
      		{
      			document.forms[0].validateMsg.value="You Can't Update Values";
      		}
      	
      }
   }
           
};

function setFlag(flagVal)
{
	
	if(document.forms[0].ackNumber.value!=0)
	{
		document.forms[0].flag.value=flagVal;
		document.forms[0].booleanFlag.value=0;
		document.forms[0].submit();
		clearAll();
	}

}
 function checkformat(ids) 
{
            
                      var cha =   event.keyCode;
                      var format = true;
                      var allow=true;

            	 var ff =  ids.value;
            	 var dd = ff.split( '/' );

				if(dd.length == 3)
				{
					for(var i =0; i< dd.length; i++ )
					{
						if(i != 2 && dd[i].length != 2 )
						{

                           document.forms[0].validateMsg.value = " problem in date format " ;
                          	format = false ;
                          	allow=false;
                        } 
                        else if(i==2 && dd[i].length != 4 )
                        {
							document.forms[0].validateMsg.value= " problem in date format " ;
                            format = false ;
                            allow=false;
                        }

            	 	}
                 } 
                 else
                 {
                    allow=false;
             		format = false ;
             		document.forms[0].validateMsg.value = " problem in date format " ;
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
						document.forms[0].validateMsg.value= "Day should not be greater than "+days+" " ;
                        allow=false;
                              
                    }
                    if(dd[0]==00)
                    {
         				 document.forms[0].validateMsg.value= "There is no date with 00" ;
          				 allow=false;
          			}
          			if(mth<1 || mth>12)
          			{
          				document.forms[0].validateMsg.value= "Month should be greater than 0 and \n lessthan 13  "+mth+" ";
          				allow=false;
          			}
          		}
     			
     			if(dd[2].length==4)
     			{
          			if((parseInt(dd[2])<parseInt(date.getYear())))
          			{
          				document.forms[0].validateMsg.value= "Year should be "+date.getYear()+" ";
          				allow=false;
          			}
         			
          		}
          }
		if(allow)
		{
		
		  document.forms[0].submit();
		  return true;
		}
		else
		{
			document.forms[0].ackDate.focus();
			return false;
		}

}	
 function onlyDoublenumbers()
 {
        	
        	var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || cha == 46 ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        }

</script>

</head>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
 
<body class="Mainbody"  onload="getMessages()">
<center><h2 class="h2">Acknowledgement DataEntry</h2></center>
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
<html:form action="/Clearing/ackDataEntryLink?pageId=7015">

<%! BranchObject[] branchObjects;
	AckObject ackObject;
%>
<% branchObjects=(BranchObject[])request.getAttribute("sourceNums");
	ackObject=(AckObject)request.getAttribute("ackObject");%>

<html:hidden property="flag"></html:hidden>
<html:hidden property="validateFlag"></html:hidden>
<html:hidden property="booleanFlag"></html:hidden>
<html:hidden property="errorFlag"></html:hidden>

<table>
	<tr><td><html:text property="validateMsg" readonly="true" size="100" style="color:red" styleClass="formTextField"></html:text></td></tr>
	<tr><td>
	<table class="txtTable">
		<tr><td><bean:message key="label.ack"></bean:message></td>
		<td><html:text property="ackNumber" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" onblur="setFlag('frmAccountNumber')"> </html:text></td>
</tr>
<%if(ackObject==null){ %>
<tr><td><bean:message key="label.ack_date"></bean:message></td>
<td><html:text property="ackDate" value="<%=(String)request.getAttribute("sysDate")%>" styleClass="formTextFieldWithoutTransparent" onblur="return checkformat(this)"></html:text></td>
</tr>


<c:if test="<%=request.getAttribute("sourceNums")!=null %>">

<tr><td><bean:message key="label.source"></bean:message></td>
<td><html:select property="source"  styleClass="formTextFieldWithoutTransparent" onblur="getClearingType()">
	<% for(int i=0;i<branchObjects.length;i++) {	%> 
	<html:option value="<%=""+branchObjects[i].getBranchCode()%>"> <%=""+branchObjects[i].getBranchCode()%> </html:option>
	<%} %>
</html:select> </td>
<td><html:text property="bname" styleClass="formTextField" readonly="true"></html:text></td>
</tr>
</c:if>




<tr><td><bean:message key="label.clgType"></bean:message></td>
<c:if test="<%=request.getAttribute("clgTypes")!=null %>">

<td><html:select property="clgType" styleClass="formTextFieldWithoutTransparent">

<c:forEach var="clgTypes" items="${requestScope.clgTypes}">
<html:option value="${clgTypes}"><c:out value="${clgTypes}"></c:out> </html:option>
</c:forEach>

</html:select> </td>
</c:if>
</tr>


<tr><td>Amount:</td>
<td><html:text property="amount" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlyDoublenumbers()"></html:text></td>
</tr>


<%}else{ %>


<tr><td><bean:message key="label.ack_date"></bean:message></td>
<td><html:text property="ackDate" value="<%=ackObject.getAckDate()%>" styleClass="formTextFieldWithoutTransparent" onblur="return checkformat(this)"></html:text></td>
</tr>




<tr><td><bean:message key="label.source"></bean:message></td>
<td><html:select property="source" styleClass="formTextFieldWithoutTransparent" onchange="getClearingType()">
	<% for(int i=0;i<branchObjects.length;i++) {	%> 
	<html:option value="<%=""+branchObjects[i].getBranchCode()%>"> <%=""+branchObjects[i].getBranchCode()%> </html:option>
	<%} %>
</html:select></td>
</tr>



<tr><td><bean:message key="label.clgType"></bean:message></td>
	<td><html:select property="clgType" styleClass="formTextFieldWithoutTransparent">
		<c:forEach var="clgTypes" items="${requestScope.clgTypes}">
			<html:option value="${clgTypes}"><c:out value="${clgTypes}"></c:out> </html:option>
		</c:forEach>
		</html:select>
	</td>	
</tr>


<tr><td>Amount:</td>
<td><html:text property="amount" styleClass="formTextFieldWithoutTransparent" value="<%=""+ackObject.getTotal() %>" onkeypress="return onlyDoublenumbers()"></html:text></td>
</tr>
                          

<%} %>

<tr>
<td>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            <html:button property="submitbutton" styleClass="ButtonBackgroundImage" onclick="set('submit')"><bean:message key="label.submit"></bean:message></html:button>
             <%}else{ %>
            <html:button property="submitbutton" disabled="true" styleClass="ButtonBackgroundImage" onclick="set('submit')"><bean:message key="label.submit"></bean:message></html:button>
             <%} %>
             
             
             <%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
           <html:button property="updatebutton" styleClass="ButtonBackgroundImage" onclick="set('update')"><bean:message key="label.update"></bean:message></html:button>
             <%}else{ %>
           <html:button property="updatebutton" disabled="true" styleClass="ButtonBackgroundImage" onclick="set('update')"><bean:message key="label.update"></bean:message></html:button>
             <%} %>


</td>
<td>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
            <html:button property="deletebutton" styleClass="ButtonBackgroundImage" onclick="setFlag('delete')"><bean:message key="label.delete"></bean:message></html:button>
             <%}else{ %>
            <html:button property="deletebutton" disabled="true" styleClass="ButtonBackgroundImage" onclick="setFlag('delete')"><bean:message key="label.delete"></bean:message></html:button>
             <%} %>

<html:button property="clearbutton"  styleClass="ButtonBackgroundImage" onclick="clearAll()"><bean:message key="label.clear"></bean:message></html:button>
</td>
</tr> 



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