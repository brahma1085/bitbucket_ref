<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@page import="masterObject.general.ModuleObject" %>
<%@page import="masterObject.clearing.ChequeDepositionObject"%>
<%@page import="masterObject.clearing.DiscountCharges"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ChequeDiscounting</title>
<script type="text/javascript">
function setFlag(flagVal)
{
		if(document.forms[0].acc_no.value!=0)
		{
 			document.forms[0].flag.value=flagVal;
 			document.forms[0].submit();
 		}
 		else
 		{
 			document.forms[0].validateFlag.value="Enter Valid Account Number";
 		}	
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
        
        
    
 
</script>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
 
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
</head>
<body class="MainBody">
<%!
   	
ModuleObject[] acountTypes = null;
ChequeDepositionObject[] chequedepositionobject=null;	
DiscountCharges[] array_discountcharges=null;

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
%>


 <% 
 acountTypes = (ModuleObject[])request.getAttribute("acountTypes");
 chequedepositionobject=(ChequeDepositionObject[])request.getAttribute("chequedetails");
 array_discountcharges=(DiscountCharges[])request.getAttribute("array_disCharges");
 	
 %>
	<center><h2 class="h2">Cheque Discounting</h2></center>
	<html:form action="Clearing/ChequeDiscountingLink?pageId=7004">
		
		
		
		<table>
			
			<tr>
				<td>
				<table>
				<tr>
				<html:text property="validateFlag" size="100" styleClass="formTextField" style="color:red;font-family:bold;"></html:text>
				</tr>
				</table>
				</td>				
			</tr>		
		
			<tr>
				<td>
					
					<bean:message key="label.acType"/>
					<html:select property="acc_type" styleClass="formTextField">
					<core:forEach var="acountTypes" items="${requestScope.acountTypes}" >
					<html:option value="${acountTypes.moduleCode}" ><core:out value="${acountTypes.moduleAbbrv}"/></html:option>
					</core:forEach>
					</html:select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<bean:message key="label.acNumber"/>
					<html:text property="acc_no" styleClass="formTextFieldWithoutTransparent"  styleId="acc_no" onblur="setFlag('frmAccNum')" onkeypress="return onlynumbers()"/>
	                
				</td>
			</tr>
			<%if(array_discountcharges!=null){%>
			<tr>
			<td>
			<table border="1" cellspacing="1">
			
				<tr>
				
				<td>FromAmt</td>
				<td>ToAmt</td>
				<td>FineAmt</td>
				</tr>
					<% for(int i=0;i<array_discountcharges.length;i++){%>
		    <tr>
			  	
			    <td><%=array_discountcharges[i].getFromAmt()%></td>
			    <td><%=array_discountcharges[i].getToAmt()%></td>
			    <td><%=array_discountcharges[i].getFineAmt()%></td>
			</tr>
				<%} }%>
		    </table>
			
			</td>
			</tr>
				<%-- 	<core:if test="${requestScope.array_disCharges!=null}">
					<core:forEach var="array_discountcharges" items="${requestScope.array_disCharges}">
					<table>
						<tr>
						  <td><core:out value="${array_disCharges.getFrmAmt}"></core:out></td>
					      <td><core:out value="${array_disCharges.getToAmt}"></core:out></td>
					      <td><core:out value="${array_disCharges.getFineAmt}"/></td>
					    </tr>
					</table>      
					
					</core:forEach>
						--%>
						
				<tr>
				<td>
				
				<%if(chequedepositionobject!=null){%>
				<table>
				<tr>
				<td>SN</td>
				<td>CnrtNo</td>
				<td>BankCode</td>
				<td>BranchName</td>
				<td>InsntType</td>
				<td>CheqNo</td>
				<td>CheqDate</td>
				<td>CheqAmt</td>
				<td>DisAmt</td>
				<td>DisChg</td>
				<td>DisCountAmt</td>
				<td>DisCountChg</td>
				<td>Select</td>
				</tr>
				
				<% for(int i=0;i<chequedepositionobject.length;i++){ %>
				<tr>
				<td><%=(i+1)%></td>
				<td><html:text property="txtCtrlNum" readonly="true" value="<%=String.valueOf(chequedepositionobject[i].getControlNo())%>"  size="5"></html:text></td>
				<td><html:text property="txtBnkName"  readonly="true" value="<%=String.valueOf(chequedepositionobject[i].getDeTime())%>"  size="7"></html:text></td>
				<td><html:text property="txtBrhName"  readonly="true" value="<%=String.valueOf(chequedepositionobject[i].getBranchName())%>"  size="10"></html:text></td>
				<td><html:text property="txtInstType" readonly="true" value="<%=String.valueOf(chequedepositionobject[i].getChqDDPO()) %>"  size="7"></html:text></td>	
				<td><html:text property="txtChqNum"  readonly="true" value="<%=String.valueOf(chequedepositionobject[i].getQdpNo()) %>"  size="7"></html:text></td>
				<td><html:text property="txtChqDate" readonly="true" value="<%=String.valueOf(chequedepositionobject[i].getQdpDate()) %>"  size="7"></html:text></td>
				<td><html:text property="txtChqAmt"  readonly="true" value="<%=String.valueOf(chequedepositionobject[i].getTranAmt()) %>"  size="7"></html:text></td>
				<td><html:text property="txtDisAmt" readonly="true"  value="<%=String.valueOf(chequedepositionobject[i].getDiscAmt()) %>"  size="5"></html:text></td>
				<td><html:text property="txtDisChg"  readonly="true" value="<%=String.valueOf(chequedepositionobject[i].getDiscChg()) %>"  size="5"></html:text></td>
				<td><html:text property="txtDisCountAmt"  value="0.0"   size="10"  ></html:text></td>
				<td><html:text property="txtDisCountChg"  value="0.0"  size="10" ></html:text></td>
				<td><input type="checkbox" name="cbox" value="<%=i %>"></td>
				
				<%} %>
				
				</tr>
				
				
				</table>
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <html:button property="buttonsubmit" value="Submit" styleClass="ButtonBackGroundImage" onclick="setFlag('frmButt')"/>
             <%}else{ %>
            <html:button property="buttonsubmit" disabled="true" value="Submit" styleClass="ButtonBackGroundImage" onclick="setFlag('frmButt')"/>
             <%} %>
				
				
				
				<%}%>
				</td>
				</tr>	
		</table>							
			<html:hidden property="pageId"></html:hidden>
			<html:hidden property="flag"></html:hidden>
			
			
	</html:form>
</body>
</html>