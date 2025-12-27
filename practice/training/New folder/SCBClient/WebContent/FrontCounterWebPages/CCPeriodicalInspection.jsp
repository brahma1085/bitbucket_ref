<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<h1 style="font:small-caps;font-style:normal;color: blue" ><center>CC Periodical Inspection</center></h1>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
</head>
<body class="Mainbody">
	<table align="left" valign="top" class="txtTable">
		<html:form action="/FrontCounter/OdccApplication?pageId=3004" focus="<%=(String)request.getAttribute("FocusTo") %>" >
		<td>
        	<table class="txtTable">
            	<tr >
                	<td ><bean:message key="label.acType"></bean:message></td>
                    <td ><html:select property="ac_type" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                    </td>
                </tr>
                <tr>
                	<td><bean:message key="label.acNum"></bean:message></td>
                	<core:choose>
                		<core:when test="${empty requestScope.AccountDetails}">
                			<td>
                				<html:text property="ac_no" styleClass="formTextFieldWithoutTransparent" size="10" onchange="submit()" onblur="submit()" value="0"></html:text>
                			</td>
                		</core:when>
                		<core:otherwise>
                			<td >
                				<html:text property="ac_no" styleClass="formTextFieldWithoutTransparent"  size="8" onchange="submit()" value="${requestScope.AccountDetails.accNo}"></html:text>
                			</td>
                		</core:otherwise>
                	</core:choose>
                </tr>
                <tr>
                	<td>
                		<bean:message key="label.natureBus"/>
                	</td>
                	<td>
                		<html:text property="nat_of_bus" styleClass="formTextField"></html:text>
                	</td>
                	<td>
                		<bean:message key="label.sancPurpse"/>
                	</td>
                	<td>
                		<html:text property="sanc_per" styleClass="formTextField"></html:text>
                	</td>	   
                	
                </tr>
                <tr>
                	<td>
                		<bean:message key="label.sancDate"/>
                	</td>
                	<td>
                		<html:text property="sanc_date" styleClass="formTextField"></html:text>
                	</td>
                	<td>
                		<bean:message key="label.matDate"/>
                	</td>
                	<td>
                		<html:text property="mat_date" styleClass="formTextField"></html:text>
                	</td>
                </tr>
                <tr >
                    <td>
                		<bean:message key="label.sancLimit"/>
                	</td>
                	<td>
                		<html:text property="sanc_limit" styleClass="formTextField"></html:text>
                	</td>
                	<td>
                		<bean:message key="label.acStatus"/>
                	</td>
                	<td>
                		<html:text property="ac_status" styleClass="formTextField"></html:text>
                	</td>
				</tr>
				<tr >
                    <td>
                		<bean:message key="label.prevInspDate"/>
                	</td>
                	<td>
                		<html:text property="prev_insp_date" styleClass="formTextField"></html:text>
                	</td>
                	<td>
                		<bean:message key="label.prevStockValue"/>
                	</td>
                	<td>
                		<html:text property="prev_stck_value" styleClass="formTextField"></html:text>
                	</td>
				</tr>
				<tr >
                    <td>
                		<bean:message key="label.prevCrLimit"/>
                	</td>
                	<td>
                		<html:text property="prev_cr_limit" styleClass="formTextField"></html:text>
                	</td>
                </tr>
			</table>
			<table>
				<tr>
					<td>
                		<bean:message key="label.valOfStock"/>
                	</td>
                	<td>
                		<html:text property="value_stock" styleClass="formTextFieldWithoutTransparent"></html:text>
                	</td>
				</tr>
				<tr>
					<td>
                		<bean:message key="label.crLimit"/>
                	</td>
                	<td>
                		<html:text property="cr_limit" styleClass="formTextField"></html:text>
                	</td>
				</tr>
				<tr>
					<td>
                		<bean:message key="label.nextInspDate"/>
                	</td>
                	<td>
                		<html:text property="next_insp_date" styleClass="formTextField"></html:text>
                	</td>
				</tr>	
                <tr>
                    <td align="right"><html:submit styleClass="ButtonBackgroundImage"><bean:message key="label.submit"/></html:submit></td>
                    <td align="left"><html:submit styleClass="ButtonBackgroundImage"><bean:message key="label.clear"/></html:submit></td>
                </tr>
            </table>
           </td>
		
		</html:form>
	</table>

</body>
</html>