<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<h1 style="font:small-caps;font-style:normal;color: blue" ><center>ODCCApplicationDE</center></h1>
    <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />

</head>
<body class="Mainbody">
	<table align="left" valign="top" class="txtTable">
    	<html:form action="/FrontCounter/OdccApplication?pageId=3004" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="defaultTab" value="PersonalDetails"/>
     	<html:hidden property="defSIndex" value="1"/>
        <td >
        	<table class="txtTable">
            	<tr >
                    <td ><bean:message key="lable.ac&ano"></bean:message></td>
                    <td ><html:select property="ac_type" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                        <bean:message key="label.acNum"></bean:message>
                    </td>
                    
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
                <tr >
                    <td><bean:message key="lable.ac&ano"></bean:message></td>
                    <td ><html:select property="sh_type" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.ShareType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                        <core:choose>
                		<core:when test="${empty requestScope.AccountDetails}">
                			<html:text property="sh_no" styleClass="formTextFieldWithoutTransparent" size="10" onchange="submit()" onblur="submit()" value="0"></html:text>
                		</core:when>
                		<core:otherwise>
                			<html:text property="sh_no" styleClass="formTextFieldWithoutTransparent"  size="8" onchange="submit()" value="${requestScope.AccountDetails.accNo}"></html:text>
                		</core:otherwise>
                		</core:choose>
                    </td>
                </tr>
                <tr >
                    <td ><bean:message key="label.custname"></bean:message></td>
                    <core:choose>
                    	<core:when test="${requestScope.personalInfo!=null}">
    						<td ><html:text styleClass="formTextFieldWithoutTransparent" property="cid" size="8" onchange="submit()" value=""></html:text></td>		                	
                    	</core:when>
                    	<core:otherwise>
                    		<td><html:text styleClass="formTextFieldWithoutTransparent" property="cid" size="8" onchange="submit()" value="${requestScope.AccountDetails.cid}"></html:text></td>
                    	</core:otherwise>
                    </core:choose>
                </tr>
                <tr >
                    <td ><bean:message key="label.details"></bean:message></td>
                    <td ><html:select property="detailsCombo" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:if test="${requestScope.Details !=null }">
                        	<core:forEach var="detail" items="${requestScope.Details}" varStatus="count">
                        		<html:option value="${count}">${detail}</html:option>
                        	</core:forEach>
                      	</core:if>
   	                  	</html:select>
   	                </td>
				</tr>
				<tr>
				</tr>
				<core:if test="${requestScope.SanctionFlag}">
				<tr>
					<td><bean:message key="label.creditLimit"/></td>
					<td><html:text property="credit_limit" value="0.0" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				</tr>
				<tr>
					<td><bean:message key="label.limitUpto"/> </td>
					<td><html:text property="limit_upto" value="0" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				</tr>
				<tr>
					<td><bean:message key="label.interestRate"/> </td>
					<td><html:text property="int_rate" styleClass="formTextField"></html:text></td>
				</tr>
				</core:if>
                <tr >
                    <td align="right"><html:submit styleClass="ButtonBackgroundImage"><bean:message key="label.submit"/></html:submit></td>
                    <td align="left"><html:submit styleClass="ButtonBackgroundImage"><bean:message key="label.clear"/></html:submit></td>
                </tr>
            </table>
        </td>
        <td>

        </td>
               	
       </html:form>
      </table>
      
       <table align="left" valign="top">
      		<tr>
      			<td>
      				<core:if test="${requestScope.subPanels !=null}">	     
            			<jsp:include page="${requestScope.subPanels}"></jsp:include>
            		</core:if>
            	</td>
            </tr>
       </table>

</body>
</html>