<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@page import="java.util.Map"%>

<html>
 

<head>

<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
 


<script type="text/javascript">

function setFlag(flagValue)
{
	
	if(document.forms[0].controlno.value==0)
	{
		document.forms[0].validateFlag.value="Enter Valid Control Number";
	}
	else
	{	
		document.forms[0].flag.value=flagValue;	
		document.forms[0].submit();
	}

}


function clearAll()
{
	
		var form_ele = document.forms[0].elements;
		
		var val = document.getElementById("ctrl_no").value;
		
		for ( var i = 0 ; i< form_ele.length; i++ )
		{
			if(form_ele[i].type=="text")
			{
				form_ele[i].value = "";
			}
		}
		
		document.getElementById("bounce_id").checked = false; 
		document.getElementById("discount_id").checked=false;
		document.getElementById("mul_credit").checked=false;
		return false;
}


function onlynumbers()
{
 	var cha = event.keyCode;

    if(cha>=48 && cha<=57) 
    {
          return true;
    } 
    else 
    {
        return false;
    }
			        
}

function checkformat(ids) 
{
            
     var cha =event.keyCode;
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

                            document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
                          	format = false ;
                          	allow=false;
                        } 
                        else if(i==2 && dd[i].length != 4 )
                        {
							document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
                            format = false ;
                            allow=false;
                        }

            	 	}
                 } 
                 else
                 {
                    allow=false;
             		format = false ;
             		document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
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
          				document.forms[0].validateFlag.value="Month should be greater than 0 and \n lessthan 13  "+mth+" ";
          				allow=false;
          			}
          		}
     			
     			if(dd[2].length==4)
     			{
          			if((parseInt(dd[2])<parseInt(date.getYear())))
          			{
          				document.forms[0].validateFlag.value="Year should be "+date.getYear()+" ";
          				allow=false;
          			}
         			
          		}
          }
		if(allow)
		{
		
		  return true;
		}
		else
		{
			document.forms[0].clr_date.focus();
			return false;
		}

}	
  
  
	
 
 

</script>

</head>



<body  class="Mainbody">
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

<center><h2 class="h2"><i><bean:message key="label.lateReturns"></bean:message> </i></h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/LateReturnsLink?pageId=7012">

<table>
	<tr>
	<td>
	<table>
	<tr><html:text property="validateFlag" styleId="valid" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
	</table>
	</td>
	</tr>
	<tr>
	<td>
	<table>
	<tr><td><bean:message key="label.controlno"></bean:message></td>
	<td><html:text property="controlno" onblur="setFlag('frmCtrlNum')" styleId="ctrl_no" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>  
	<tr><td><bean:message key="label.bankCodeName"></bean:message></td>
	<td><html:text property="bankCodeName" readonly="true" value="${requestScope.chequedeposaitionobject[0].bankCode}" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>
	<tr><td><bean:message key="label.branch_name"></bean:message></td>
	<td><html:text property="branch_name" readonly="true" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequedeposaitionobject[0].branchName}"></html:text></td>
	</tr>
	<tr><td><bean:message key="label.multi_credit"></bean:message></td>
	<td><html:checkbox property="multi_credit" styleId="mul_credit" styleClass="formTextFieldWithoutTransparent"></html:checkbox>
	Yes/No</td>
	</tr>
	<tr><td><bean:message key="label.acTypNum"></bean:message></td>
	<td><html:select property="creditAcType"   styleClass="formTextFieldWithoutTransparent">
		<html:option value="select">Select</html:option>
		<core:forEach var="module"  varStatus="count" items="${requestScope.Loan_Module_code}" >
		<html:option value="${module.moduleCode}"><core:out value="${module.moduleAbbrv}"></core:out></html:option>
		</core:forEach>
		</html:select>						
	<html:text property="creditAcNum" size="7" readonly="true" value="${requestScope.chequedeposaitionobject[0].creditACNo}" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	<tr><td></td>
	<td><html:text property="creditAcName"  readonly="true" styleClass="formTextField"></html:text></td>
	</tr>
	<tr><td><bean:message key="label.amount"></bean:message></td>
	<td><html:text property="amount" readonly="true" value="${requestScope.chequedeposaitionobject[0].tranAmt}" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>
	<tr><td><bean:message key="label.bounce"></bean:message></td>
	<td><html:checkbox property="bounce" styleId="bounce_id"  styleClass="formTextFieldWithoutTransparent"></html:checkbox>Yes/No</td>
	</tr>
	<tr><td><bean:message key="label.discount"></bean:message></td>
	<td><html:checkbox property="discount" styleId="discount_id" styleClass="formTextFieldWithoutTransparent"></html:checkbox>Yes/No </td>
	</tr>

<tr><td><bean:message key="label.chqddpo"></bean:message></td>
	<td><html:text property="chqddpo" readonly="true" value="${requestScope.chequedeposaitionobject[0].chqDDPO}" styleClass="formTextFieldWithoutTransparent"></html:text></td>
</tr>
<tr><td><bean:message key="label.chqno"></bean:message></td>
	<td><html:text property="chqno" readonly="true" value="${requestScope.chequedeposaitionobject[0].qdpNo}" styleClass="formTextFieldWithoutTransparent"></html:text></td>
</tr>
<tr><td><bean:message key="label.chqdate"></bean:message></td>
	<td><html:text property="chqdate" readonly="true" value="${requestScope.chequedeposaitionobject[0].qdpDate}" styleClass="formTextFieldWithoutTransparent"></html:text></td>
</tr>
<tr><td><bean:message key="label.pocomm"></bean:message></td>
	<td><html:text property="pocomm" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
</tr>
<tr><td><bean:message key="label.dis_chr"></bean:message></td>
	<td><html:text property="discountCharge" readonly="true" value="${requestScope.chequedeposaitionobject[0].discChg}" styleClass="formTextFieldWithoutTransparent"></html:text></td>
</tr>
<tr><td><bean:message key="label.clr_date"></bean:message></td>
	<td><html:text property="clr_date" value="${requestScope.date}" onblur="return checkformat(this)" styleClass="formTextFieldWithoutTransparent"></html:text></td>
</tr>

<html:hidden property="flag"></html:hidden>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <tr><td><html:button property="button_submit" styleClass="ButtonBackgroundImage" onclick="setFlag('submit')"><bean:message key="label.update"></bean:message></html:button>
             <%}else{ %>
           <tr><td><html:button property="button_submit" styleClass="ButtonBackgroundImage" onclick="setFlag('submit')"><bean:message key="label.update"></bean:message></html:button>
             <%} %>


<html:button property="clear" styleClass="ButtonBackgroundImage" value="Clear" onclick="clearAll()"></html:button></td></tr>

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