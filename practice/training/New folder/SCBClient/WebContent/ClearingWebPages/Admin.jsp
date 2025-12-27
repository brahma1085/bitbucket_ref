<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="/WEB-INF/c-rt.tld" prefix="core" %>
<%@page import="masterObject.clearing.*" %>
<%@page import="masterObject.general.*" %>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

function onSubmit()
{
	document.forms[0].flag.value='';
	document.forms[0].submit();

}
function onlynumbers()
{       	
    var cha =   event.keyCode;

    if(cha >= 48 && cha <= 57) 
    {
       return true;
            		
    } 
    else 
    {
        return false;
  	}
} 	
        
function onlyDoublenumbers()
{
    var cha = event.keyCode;

    if ( (cha >= 48 && cha <= 57 )  || cha == 46 ) 
    {
         return true;
            		
    } 
    else 
    {
        return false;
    }
			        
}

function setFlag(flagVal)
{	
	document.forms[0].flag.value=flagVal;
	document.forms[0].submit();

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
<body class="MainBody" >
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
<html:form action="/Clearing/Admin?pageId=7020">
<center><h2 class="h2">Clearing Admin</h2></center>


<% BranchObject[] array_branchobject=(BranchObject[])request.getAttribute("BranchMaster");
   BranchObject[] clearingObjects=(BranchObject[])request.getAttribute("ClrBank");
   BankMaster[] array_bankmaster=(BankMaster[])request.getAttribute("BankMaster");
   CompanyMaster[] array_companymaster=(CompanyMaster[])request.getAttribute("CompanyMaster");
   ReasonMaster[] array_reasonmaster=(ReasonMaster[])request.getAttribute("ReasonMaster");
   BounceFineObject[] array_bouncefineobject=(BounceFineObject[])request.getAttribute("BounceFine");
   DiscountCharges[] charges=(DiscountCharges[])request.getAttribute("DiscountCharges");
%>


<table>
	<tr><html:text property="validateFlag" size="100" style="color:red;font-family:bold;" styleClass="formTextField" readonly="true"></html:text></tr>
	<tr>
	<td>
		<table>
		<tr>
		<td>
		<bean:message key="label.adModules"></bean:message>
		<html:select property="adminModules" styleClass="formTextFieldWithoutTransparent" onblur="onSubmit()">
			<html:option value="Select">Select</html:option>
			<html:option value="ClearingNo">ClearingNo</html:option>		
			<html:option value="ClearingBanks">ClearingBanks</html:option>		
			<html:option value="BankMaster">BankMaster</html:option>		
			<html:option value="BranchMaster">BranchMaster</html:option>		
			<html:option value="CompanyMaster">CompanyMaster</html:option>		
			<html:option value="ReasonMaster">ReasonMaster</html:option>	
			<html:option value="ReasonLink">ReasonLink</html:option>	
			<html:option value="BounceFine">BounceFine</html:option>	
			<html:option value="DiscountCharges">DiscountCharges</html:option>	
		</html:select>
		</td>
		
       
        
        
        <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <td><html:button property="submitupdate" onclick="setFlag('frmSubmit')" value="Submit" styleClass="ButtonBackGroundImage"></html:button></td>
             <%}else{ %>
            <td><html:button property="submitupdate" onclick="setFlag('frmSubmit')" value="Submit" disabled="true" styleClass="ButtonBackGroundImage"></html:button></td>
             <%} %>
		
		<td><html:button property="addRow" onclick="setFlag('frmaddRow')" value="AddRow" styleClass="ButtonBackGroundImage"></html:button></td>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
           <td><html:button property="deleteRow" onclick="setFlag('frmdeleteRow')" value="DeleteRow" styleClass="ButtonBackGroundImage"></html:button></td>
             <%}else{ %>
            <td><html:button property="deleteRow" onclick="setFlag('frmdeleteRow')" disabled="true" value="DeleteRow" styleClass="ButtonBackGroundImage"></html:button></td>
             <%} %>
		
		</tr>
		</table>
	</td>
	</tr>
	<tr>
	<td>
	<core:if test="${requestScope.clearingNum!=null}">
	<table>
	
			<tr><td>S.N</td><td>ClearingNumber</td></tr>
			<core:forEach var="clrNum" items="${requestScope.clearingNum}">
			<tr>
			<td>1</td>
			<td><html:text property="txtClrNum" onkeypress="return onlynumbers()" value="${clrNum}"></html:text></td>
			</tr>
			</core:forEach>
		<html:button property="update" value="Update" styleClass="ButtonBackGroundImage" onclick="setFlag('Update')"></html:button>
	</table>
	</core:if>
	
	
	
	<core:if test="${requestScope.ClrBank!=null}">
	
	<div id="clr_bank" style="display:block;overflow:scroll;width:650px;height:210px;">
		<table  border="1" style="border:thin solid #000000;">
			<tr><td>BranchCode</td><td>BranchName</td><td>ShortName</td><td>BranchAddress</td><td>BranchType</td><td>A/cType</td><td>A/cNumber</td><td>Select</td></tr>
			<%for(int i=0;i<clearingObjects.length;i++){ %>
			<tr>
			<td><html:text property="txtBranchCode" size="9" value="<%=""+clearingObjects[i].getBranchCode()%>"></html:text></td>
			<td><html:text property="txtBranchName"  value="<%=""+clearingObjects[i].getBranchName() %>"></html:text></td>
			<td><html:text property="txtShortName"  size="9" value="<%=""+clearingObjects[i].getShortName()%>"></html:text></td>
			<td><html:text property="txtBranchAddress"  value="<%=""+clearingObjects[i].getBranchAddress()%>"></html:text></td>
			<td><html:text property="txtBranchTyp" size="9" value="<%=""+clearingObjects[i].getBranchType()%>"></html:text></td>
			<td><html:text property="txtBranchACTyp" size="9" value="<%=""+clearingObjects[i].getBranchACType()%>"></html:text></td>
			<td><html:text property="txtBranchACNum" size="9" value="<%=""+clearingObjects[i].getBranchACNo()%>"></html:text></td>
			<td><input type="checkbox" name="chkBox" value="<%=""+i%>"></td>
			</tr>
			<%} %>
			<core:if test="${requestScope.Arow!=null}">
				<tr>
				<td><html:text property="branchCode" size="9"></html:text></td>
				<td><html:text property="branchName"></html:text></td>
				<td><html:text property="shortName" size="9"></html:text></td>
				<td><html:text property="branchAddress"></html:text></td>
				<td><html:text property="branchTyp" size="9"></html:text></td>
				<td><html:text property="branchACTyp" size="9"></html:text></td>
				<td><html:text property="branchACNum" size="9"></html:text></td>
				<td><input type="checkbox" name="chkBox"></td>
				</tr>
			</core:if>
		</table>
		</div>
	</core:if>
	
	
	<core:if test="${requestScope.BankMaster!=null}">
	<div id="bank_master" style="display:block;overflow:scroll;width:650px;height:210px;">
	<table>
			<tr><td>BankCode</td><td>BankName</td><td>BankAbbr</td><td>Select</td></tr>
			<%for(int i=0;i<array_bankmaster.length;i++){ %>
			<tr>
			<td><html:text property="txtBankCode" size="9" value="<%=""+array_bankmaster[i].getBankCode()%>"></html:text></td>
			<td><html:text property="txtBankName"  value="<%=""+array_bankmaster[i].getBankName()%>"></html:text></td>
			<td><html:text property="txtBankAbbr" size="9"  value="<%=""+array_bankmaster[i].getabbrevation()%>"></html:text></td>
			<td><input type="checkbox" name="chkBox" value="<%=""+i%>"></td>
			</tr>
			<%} %>
			
			<tr>
			<core:if test="${requestScope.Arow!=null}">
				<tr>
				<td><html:text property="bankCode" size="9"></html:text></td>
				<td><html:text property="bankName"/></td>
				<td><html:text property="bankAbbr" size="9"></html:text></td>
				<td><input type="checkbox" name="chkBox"></td>
				</tr>
			</core:if>
			</tr>
			
	</table>
	</div>
	</core:if>
	
	
	<core:if test="${requestScope.BranchMaster!=null}">
	<div id="branch_master" style="display:block;overflow:scroll;width:650px;height:210px;">
	<table>
			<tr><td>BranchCode</td><td>BranchName</td><td>BranchAddress</td><td>ShortName</td><td>BranchType</td><td>A/cType</td><td>A/cNumber</td><td>Select</td></tr>
			<%for(int i=0;i<array_branchobject.length;i++){ %>
			<tr>
			<td><html:text property="txtBranchCode" size="9" value="<%=""+array_branchobject[i].getBranchCode()%>"></html:text></td>
			<td><html:text property="txtBranchName"  value="<%=""+array_branchobject[i].getBranchName()%>"></html:text></td>
			<td><html:text property="txtBranchAddress"  value="<%=""+array_branchobject[i].getBranchAddress()%>"></html:text></td>
			<td><html:text property="txtShortName" size="9" value="<%=""+array_branchobject[i].getShortName()%>"></html:text></td>
			<td><html:text property="txtBranchTyp" size="9" value="<%=""+array_branchobject[i].getBranchType()%>"></html:text></td>
			<td><html:text property="txtBranchACTyp" size="9" value="<%=""+array_branchobject[i].getBranchACType()%>"></html:text></td>
			<td><html:text property="txtBranchACNum" size="9" value="<%=""+array_branchobject[i].getBranchACNo()%>"></html:text></td>
			<td><input type="checkbox" name="chkBox" value="<%=""+i%>"></td>
			</tr>
			<% }%>
			<core:if test="${requestScope.Arow!=null}">
			<tr>
				<td><html:text property="branchCode" size="9"></html:text></td>
				<td><html:text property="branchName"></html:text></td>
				<td><html:text property="branchAddress"></html:text></td>
				<td><html:text property="shortName" size="9"></html:text></td>
				<td><html:text property="branchTyp" size="9"></html:text></td>
				<td><html:text property="branchACTyp" size="9"></html:text></td>
				<td><html:text property="branchACNum" size="9"></html:text></td>
				<td><input type="checkbox" name="chkBox"></td>
			</tr>
			</core:if>
	</table>
	</div>
	</core:if>
	
	
	<core:if test="${requestScope.CompanyMaster!=null}">
	<div id="com_mas" style="display:block;overflow:scroll;width:650px;height:210px;">
	<table>
			<tr><td>CompanyCode</td><td>CompanyName</td><td>A/cType</td><td>A/cNumber</td><td>Select</td></tr>
			<%for(int i=0;i<array_companymaster.length;i++){ %>
			<tr>
			<td><html:text property="txtCompanyCode" size="9"  value="<%=""+array_companymaster[i].getCompanyCode()%>"></html:text></td>
			<td><html:text property="txtCompanyName"  value="<%=""+array_companymaster[i].getCompanyName()%>"></html:text></td>
			<td><html:text property="txtAccTyp" size="9" value="<%=""+array_companymaster[i].getAccType()%>"></html:text></td>
			<td><html:text property="txtAccNum" size="9" value="<%=""+array_companymaster[i].getAccNo()%>"></html:text></td>
			<td><input type="checkbox" name="chkBox" value="<%=""+i%>"></td>
			</tr>
			<%} %>
			<core:if test="${requestScope.Arow!=null}">
				<tr>
				<td><html:text property="companyCode" size="9"></html:text></td>
				<td><html:text property="companyName"></html:text></td>
				<td><html:text property="accTyp" size="9"></html:text></td>
				<td><html:text property="accNum" size="9"></html:text></td>
				<td><input type="checkbox" name="chkBox"></td>
				</tr>
			</core:if>
	</table>
	</div>
	</core:if>
	
	
	
	<core:if test="${requestScope.ReasonMaster!=null}">
	<div id="rea_mas" style="display:block;overflow:scroll;width:650px;height:210px;">
	<table>
			<tr><td>Code</td><td>Reason</td><td>Select</td></tr>
			<%for(int i=0;i<array_reasonmaster.length;i++){ %>
			<tr>
			<td><html:text property="txtcode" size="5" value="<%=""+array_reasonmaster[i].getCode()%>"></html:text></td>
			<td><html:text property="txtreason"  value="<%=""+array_reasonmaster[i].getReason()%>"></html:text></td>
			<td><input type="checkbox" name="chkBox" value="<%=""+i%>"></td>
			</tr>
			<%} %>
			<core:if test="${requestScope.Arow!=null}">
			<tr>
			<td><html:text property="code" size="5"></html:text></td>
			<td><html:text property="reason"></html:text></td>
			<td><input type="checkbox" name="chkBox"></td>
			</tr>
			</core:if>
	</table>
	</div>
	</core:if>
	
	
	<core:if test="${requestScope.ReasonLink!=null}">
	<div id="rea_link" style="display:block;overflow:scroll;width:650px;height:210px;">
	<table>
			<tr><td>LinkCode</td><td>LinkDescription</td><td>ReasonCode</td></tr>
			<core:forEach var="ReasonLink"  items="${requestScope.ReasonLink}">
			<tr>
			<td><html:text property="txtcode" size="8" value="${ReasonLink.linkCode}"></html:text></td>
			<td><html:text property="txtlinkDiscription"  value="${ReasonLink.linkDiscription}"></html:text></td>
			<td><html:text property="txtreasonCode" size="10" value="${ReasonLink.reasonCode}"></html:text></td>
			
			</tr>
			</core:forEach>
			<core:if test="${requestScope.Arow!=null}">
			<tr>
			<td><html:text property="code" size="8"></html:text></td>
			<td><html:text property="linkDiscription"></html:text></td>
			<td><html:text property="reasonCode" size="10"></html:text></td>
			
			</tr>
			</core:if>
	</table>
	</div>
	</core:if>
	
	
	<core:if test="${requestScope.BounceFine!=null}">
	<div id="bou_fine" style="display:block;overflow:scroll;width:650px;height:210px;">
	<table>
			<tr><td>BounceCode</td><td>A/cType</td><td>Fine</td><td>ReturnFine</td><td>MailingCharge</td><td>SmsCharge</td><td>EmailCharge</td><td>Select</td></tr>
			<%for(int i=0;i<array_bouncefineobject.length;i++){ %>
			<tr>
			<td><html:text property="txtbounceCode" size="10" value="<%=""+array_bouncefineobject[i].getBounceCode()%>"></html:text></td>
			<td><html:text property="txtaccountType" readonly="true" size="5" value="<%=""+array_bouncefineobject[i].getAccountType() %>"></html:text></td>
			<td><html:text property="txtfine" size="5" onkeypress="return onlyDoublenumbers()" value="<%=""+array_bouncefineobject[i].getFine()%>"></html:text></td>
			<td><html:text property="txtreturnFine" size="9" onkeypress="return onlyDoublenumbers()" value="<%=""+array_bouncefineobject[i].getReturnFine()%>"></html:text></td>
			<td><html:text property="txtmailingChg" size="9" onkeypress="return onlyDoublenumbers()" value="<%=""+array_bouncefineobject[i].getMailingChg()%>"></html:text></td>
			<td><html:text property="txtsmsChg" size="9" onkeypress="return onlyDoublenumbers()" value="<%=""+array_bouncefineobject[i].getSmsChg()%>"></html:text></td>
			<td><html:text property="txtemailChg" size="9" onkeypress="return onlyDoublenumbers()" value="<%=""+array_bouncefineobject[i].getEmailChg()%>"></html:text></td>
			<td><input type="checkbox" name="chkBox" value="<%=""+i%>"></td>
			</tr>
			<%} %>
			<core:if test="${requestScope.Arow!=null}">
			<tr>
			<td><html:text property="bounceCode" size="10"></html:text></td>
			<td><html:text property="accountType" size="5"></html:text></td>
			<td><html:text property="fine" onkeypress="return onlyDoublenumbers()" size="5"></html:text></td>
			<td><html:text property="returnFine" onkeypress="return onlyDoublenumbers()" size="9"></html:text></td>
			<td><html:text property="mailingChg" onkeypress="return onlyDoublenumbers()" size="9"></html:text></td>
			<td><html:text property="smsChg" onkeypress="return onlyDoublenumbers()" size="9"></html:text></td>
			<td><html:text property="emailChg" onkeypress="return onlyDoublenumbers()" size="9"></html:text></td>
			<td><input type="checkbox" name="chkBox"></td>
			</tr>
			</core:if>
			<tr>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
            <html:button property="update" value="Update" styleClass="ButtonBackGroundImage" onclick="setFlag('Update')"></html:button>
             <%}else{ %>
           <html:button property="update" disabled="true" value="Update" styleClass="ButtonBackGroundImage" onclick="setFlag('Update')"></html:button>
             <%} %>
			
			</tr>
	</table>
	</div>
	</core:if>
	
	
	<core:if test="${requestScope.DiscountCharges!=null}">
	<div id="dis_chag" style="display:block;overflow:scroll;width:650px;height:210px;">
	<table>
			<tr><td>FromAmt</td><td>ToAmt</td><td>FineAmt</td><td>IntRate</td><td>IntType</td></tr>
			<%for(int i=0;i<charges.length;i++){ %>
			<tr>
			<td><html:text property="txtfromAmt" size="8"  value="<%=""+charges[i].getFromAmt()%>"></html:text></td>
			<td><html:text property="txttoAmt" size="8" onkeypress="return onlyDoublenumbers()" value="<%=""+charges[i].getToAmt()%>"></html:text></td>
			<td><html:text property="txtfineAmt" size="8" onkeypress="return onlyDoublenumbers()" value="<%=""+charges[i].getFineAmt()%>"></html:text></td>
			<td><html:text property="txtint_rate" size="8" onkeypress="return onlyDoublenumbers()" value="<%=""+charges[i].getInt_rate()%>"></html:text></td>
			<td><html:text property="txtint_type" size="8" readonly="true" value="<%=""+charges[i].getInt_type()%>"></html:text></td>
			<td><input type="checkbox" name="chkBox" value="<%=""+i%>"></td>
			</tr>
			<%} %>
			<core:if test="${requestScope.Arow!=null}">
			<tr>
			<td><html:text property="fromAmt" onkeypress="return onlyDoublenumbers()" size="8"></html:text></td>
			<td><html:text property="toAmt" onkeypress="return onlyDoublenumbers()" size="8"></html:text></td>
			<td><html:text property="fineAmt" onkeypress="return onlyDoublenumbers()" size="8"></html:text></td>
			<td><html:text property="int_rate" onkeypress="return onlyDoublenumbers()" size="8"></html:text></td>
			<td><html:text property="int_type" size="8"></html:text></td>
			</tr>
			</core:if>
			<!--<tr>
			<html:button property="update" value="Update" styleClass="ButtonBackGroundImage" onclick="setFlag('Update')"></html:button>
			</tr>
	--></table>
	</div>
	</core:if>
	
	
	<core:if test="${requestScope.MailingCharges!=null}">
	<table>
	<tr><td>MailingCharges</td></tr>
			<core:forEach var="MailingCharges"  items="${requestScope.MailingCharges}">
			<tr>
			<td><html:text property="txtMailingChg" size="5" value="${MailingCharges}"></html:text></td>
			</tr>
			</core:forEach>
	</table>
	</core:if>
	
	
	<html:hidden property="pageId"></html:hidden>
	<html:hidden property="flag"></html:hidden>
	
	</td>
	</tr>
	
</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>