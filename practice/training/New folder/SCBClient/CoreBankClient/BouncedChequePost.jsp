<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="core"  uri="/WEB-INF/c-rt.tld"%> 
<%@page import="java.util.Map"%>

<html>
<head>    
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function setFlag(flagValue)
{	
	document.forms[0].flag.value=flagValue;
	document.forms[0].submit();
}

function getAlertMessages()
{	
	if(document.forms[0].chooseFlag.value!="")
	{
		var con=confirm("Shall I Resend The Cheque To Clearing Bank ?");
           if(con)
           {
          	 document.forms[0].chooseFlag.value="1";
         	  document.forms[0].submit();
           }
		}
	else if(document.forms[0].errorFlag.value!="")
	{
		var con=confirm("Amount Is Less ,Shall I Allow Negative Balance ?");
           if(con)
           {
          	 document.forms[0].errorFlag.value="1";
          	 document.forms[0].submit();
           }
		}
}

  
function clearAll()
{
	var form_ele = document.forms[0].elements;
		
		for( var i = 0 ; i< form_ele.length; i++ )
		{
			if(form_ele[i].type=="text")
			{
				form_ele[i].value = "";
			}	
		}
}

 function checkformat(ids) 
{
            
    var cha = event.keyCode;
    var format = true;
    var allow=true;
	var ff = ids.value;
    var dd = ff.split( '/' );

	if(dd.length == 3)
	{

         for( var i =0; i< dd.length; i++ )
         {
	        if(i!=2 && dd[i].length!=2)
            {
	            document.forms[0].validateFlag.value="Problem In Date Format ";
                format = false ;
                allow=false;
                          
             }
			 else if(i==2 && dd[i].length != 4 )
			 {
                 document.forms[0].validateFlag.value="Problem In Date Format " ;
                  format = false ;
                  allow=false;
             }

          }
     } 
     else
     {
         allow=false;
         format = false ;
         document.forms[0].validateFlag.value="Problem In Date Format";
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
          if(dd[0] > days )
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
          document.forms[0].validateFlag.value="Month should be greater than 0 and \n lessthan 13  "+mth;
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
			document.forms[0].flag.value='frmClrDate';
			document.forms[0].submit();
		  	return true;
		}
		else
		{
			document.forms[0].clr_date.focus();
		 return false;
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
<body  class="MainBody" onload="getAlertMessages()">
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


<center><h2 class="h2">Recieved Cheque Post</h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:form action="Clearing/BouncedChequePostLink?pageId=7011">
	
		<table>
			<tr><html:text property="validateFlag" styleClass="formTextField" size="100" style="color:red;font-family:bold;" readonly="true"></html:text></tr>
			<tr>
			<td>
				<table>
				
				<tr>
				<td><bean:message key="label.clr_date"></bean:message></td>
				<td><html:text property="clr_date"  onblur="return checkformat(this)" styleClass="formTextFieldWithoutTransparent" value="<%=(String)request.getAttribute("date")%>"></html:text>  </td>
				</tr>

				<core:if test="${requestScope.chequeDepositionObjects==null}">
				<tr>
				<td><bean:message key="label.controlno"></bean:message></td>
				<td><html:text property="controlNum" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.ack"></bean:message></td>
				<td><html:text property="ackNumber" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.sourceNum"></bean:message></td>
				<td><html:text property="sourceNum" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.sourcenm"></bean:message></td>
				<td><html:text property="sourceName" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.payee"></bean:message></td>
				<td><html:text property="payee" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.bankCode"></bean:message></td>
				<td><html:text property="bankCode" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.bankName"></bean:message></td>
				<td><html:text property="bankName" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.branch_name"></bean:message></td>
				<td><html:text property="branchName" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.amount"></bean:message></td>
				<td><html:text property="amount" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.chqPoWarrant"></bean:message></td>
				<td><html:text property="chqPoWarrant" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
				</tr>

				</core:if>
				<core:if test="${requestScope.chequeDepositionObjects!=null}">


				<tr>
				<td><bean:message key="label.controlno"></bean:message></td>
				<td><html:text property="controlNum" readonly="true"  styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObjects[0].controlNo}"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.ack"></bean:message></td>
				<td><html:text property="ackNumber" readonly="true"  styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObjects[0].ackNo}"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.sourceNum"></bean:message></td>
				<td><html:text property="sourceNum" readonly="true"  styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObjects[0].sendTo}"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.sourcenm"></bean:message></td>
				<td><html:text property="sourceName" readonly="true" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObjects[0].companyName}"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.payee"></bean:message></td>
				<td><html:text property="payee"  readonly="true" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObjects[0].payeeName}"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.bankCode"></bean:message></td>
				<td><html:text property="bankCode" readonly="true" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObjects[0].bankCode}"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.bankName"></bean:message></td>
				<td><html:text property="bankName" readonly="true" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObjects[0].bankName}"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.branch_name"></bean:message></td>
				<td><html:text property="branchName" readonly="true" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObjects[0].branchName}"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.amount"></bean:message></td>
				<td><html:text property="amount" readonly="true" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObjects[0].tranAmt}"></html:text>  </td>
				</tr>

				<tr>
				<td><bean:message key="label.chqPoWarrant"></bean:message></td>
				<td><html:text property="chqPoWarrant" readonly="true" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObjects[0].chqDDPO}"></html:text>  </td>
				</tr>
				</core:if>
				
				<tr>
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             	<td><html:button property="buttonSubmit" styleClass="ButtonBackGroundImage" onclick="setFlag('frmSubmit')"></html:button>
             	<%}else{ %>
            	<html:button property="buttonSubmit" styleClass="ButtonBackGroundImage" disabled="true" onclick="setFlag('frmSubmit')" >
             	<bean:message key="label.submit"></bean:message></html:button></td>
             	<%} %>
				<td>
					<html:button property="clearsd" value="Clear" styleClass="ButtonBackGroundImage" onclick="clearAll()"></html:button>
				</td>
				</tr>
				
				</table>
			</td>

			<td valign="top">
				<table style="border:thin solid #000000;">
				<core:if test="${requestScope.chequeDepositionObject!=null}">
				<tr>
				<td><bean:message key="lable.accno"></bean:message> </td>
				<td><html:text property="ackNumber" size="10" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObject[0].debitACNo}"></html:text>  </td>
				<td><bean:message key="label.actype"></bean:message></td>
				<td><html:text property="acntType" size="10" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObject[0].debitACType}"></html:text> </td>
				</tr>

				<tr>
				<td><bean:message key="label.chqno"></bean:message> </td>
				<td><html:text property="chqno" size="10" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObject[0].qdpNo}"></html:text>  </td>
				<td><bean:message key="label.chqdate"></bean:message></td>
				<td><html:text property="chqddpo" size="10" styleClass="formTextFieldWithoutTransparent" value="${requestScope.chequeDepositionObject[0].qdpDate}"></html:text> </td>
				</tr>


				<tr>
				<td><bean:message key="label.balance"></bean:message> </td>
				<td><html:text property="balance" size="10" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
				<td><bean:message key="label.sha_balance"></bean:message></td>
				<td><html:text property="shadowBalance" size="10" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
				</tr>
				</core:if>
				</table>
			</td>
			</tr>
		</table>
	
	
<html:hidden property="flag"></html:hidden>

<html:hidden property="chooseFlag"></html:hidden>
<html:hidden property="errorFlag"></html:hidden>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>