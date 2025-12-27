<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
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
<html:form action="${requestScope.PagePath}">
<script type="text/javascript">
    	function clears(){
 			alert("clear");
 			document.getElementById("table1").style.display='none'; 
			return false;
 		};
 		function validateDateFormat(){
 			
 		};
 		var dtCh= "/";
		var minYear=1900;
		var maxYear=9999;
		
		function isInteger(s){
			var i;
    		for (i = 0; i < s.length; i++){   
        		// Check that current character is number.
        		var c = s.charAt(i);
        		if (((c < "0") || (c > "9"))) return false;
    		}
    // All characters are numbers.
    		return true;
		};
		function stripCharsInBag(s, bag){
			var i;
    		var returnString = "";
 		    // Search through string's characters one by one.
    		// If character is not in bag, append to returnString.
   			for (i = 0; i < s.length; i++){   
    	    	var c = s.charAt(i);
       			if (bag.indexOf(c) == -1) returnString += c;
   			}
   		 	return returnString;
		};
		function daysInFebruary (year){
			// February has 29 days in any year evenly divisible by four,
    		// EXCEPT for centurial years which are not also divisible by 400.
    		return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
		};
		function DaysArray(n) {
			for (var i = 1; i <= n; i++) {
				this[i] = 31
				if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
				if (i==2) {this[i] = 29}
   			}	 
   			return this
		};
  		function isDate(dtStr){
  			var daysInMonth = DaysArray(12)
			var pos1=dtStr.indexOf(dtCh)
			var pos2=dtStr.indexOf(dtCh,pos1+1)
			var strMonth=dtStr.substring(pos1+1,pos2)
			var strDay=dtStr.substring(0,pos1)
     		var strYear=dtStr.substring(pos2+1)
     		strYr=strYear
			if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
			if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
			for (var i = 1; i <= 3; i++) {
				if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
			}
			month=parseInt(strMonth)
			day=parseInt(strDay)
			year=parseInt(strYr)
			if (pos1==-1 || pos2==-1){
				alert("<bean:message key="label.dtFormat"/>");
				return false
			}
			if (strMonth.length<1 || month<1 || month>12){
				alert("<bean:message key="label.validMnth"/>");
				return false
			}
			if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
				alert("<bean:message key="label.validDayNtLeapYear"/>")
				return false
			}
			if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
				var message="<bean:message key="label.btwnYear"/>"+minYear+" and "+maxYear;
				alert(message);
				return false;
			}
			if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
				alert("<bean:message key="label.validDate" />");
				return false
			}
			return true
		};
		function ValidateIndividualDate(date){
  			var dt=date;
			if (isDate(dt)==false){
	    		return false;
			}
		};
  		function ValidateForm(frm_dt,to_dt){
  			
 			var dt=frm_dt;
			var dt1=to_dt;
			if (isDate(dt)==false){
	    		return false;
			}
		
			if (isDate(dt1)==false){
	    		return false;
			}
	   
 		};
 		function properdate(from_date,to_date){
 			ValidateForm(from_date,to_date);
  			var dtCh="/";
   			var pos1=from_date.indexOf(dtCh)
  			var pos2=from_date.indexOf(dtCh,pos1+1)
  			var frmMonth=from_date.substring(pos1+1,pos2)
  			var frmDay=from_date.substring(0,pos1)
  			var frmYear=from_date.substring(pos2+1)
  			var pos3=to_date.indexOf(dtCh)
			var pos4=to_date.indexOf(dtCh,pos3+1)
  			var ToMonth=to_date.substring(pos3+1,pos4)
  			var ToDay=to_date.substring(0,pos3)
  			var ToYear=to_date.substring(pos4+1)
  			if(frmYear > ToYear){
    			alert("<bean:message key="label.frmYrGrtThanToyear"/>")
  			}
  			else if(frmMonth > ToMonth && frmYear<=ToYear ){
    			alert("<bean:message key="label.frmMnthGrtThanToMnth"/>")
  			}
 			else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  				alert("<bean:message key="label.frmDayGrtThanToDay"/>")
  			}
 		}; 
  	
 		function checkIndividualDate(){
 		};
 		function checkBothDate(){
 		
 		};
 		function checkAllConditonOnSubmit(){
 		
 		};
 	</script>
<h1 class="h2"><center>${requestScope.ActnForm.heading}</center></h1>
	<table align="left" valign="top" class="txtTable">
		<table align="left" valign="top" class="txtTable">
		<tr>
			<td>
				<bean:message key="label.frm_dt"/>
			</td>
			<td>
				<html:text property="frmDt" styleClass="formTextFieldWithoutTransparent" value="${requestScope.FrmDate}" onblur="ValidateIndividualDate(frmDt.value)" ></html:text>
			</td>
			<td ><bean:message key="label.to_dt"/></td>
			<td ><html:text property="toDt" styleClass="formTextFieldWithoutTransparent" value="${requestScope.ToDate}" onblur="ValidateIndividualDate(toDt.value)" onchange="properdate(frmDt.value,toDt.value)" ></html:text></td>
		</tr>
		<tr>
			<td ><html:submit styleClass="ButtonBackgroundImage" value=""><bean:message key="label.view1"></bean:message></html:submit></td>
			<td ><html:reset styleClass="ButtonBackgroundImage" value="" onclick="clears()"><bean:message key="label.clear"></bean:message></html:reset></td>
		</tr>
	</table>
	<tr>
	<table align="left" valign="down" class="txtTable">
	<tr>		
	<td>
	<core:if test="${requestScope.POReports!=null}">
	<div id="table1" style="display: block;overflow: scroll;width: 750px;height: 300px">
	<core:if test="${requestScope.ReportNo==1}">
	<display:table name="POReports" id="POReport" class="its" list="${POReports}">
		<display:column title="Po No" class="formTextFieldWithoutTransparent"><input type="text" name="PoNo" readonly="readonly" class="dispTabDoubleTextField" value="${POReport.PoNo}" /></display:column>
		<display:column title="PO Date" class="formTextFieldWithoutTransparent"><input type="text" name="PODate" readonly="readonly" class="dispTabStringTextField" value="${POReport.PODate}" /></display:column>
		<display:column title="PO Chq No" class="formTextFieldWithoutTransparent"><input type="text" name="POChq No" readonly="readonly" class="dispTabStringTextField" value="${POReport.POChqNo}" /></display:column>
		<display:column title="PayeeName" class="formTextFieldWithoutTransparent"><input type="text" name="PayeeName" readonly="readonly" class="formTextField" value="${POReport.PayeeName}" /></display:column>
		<display:column title="TrnAmt" class="formTextFieldWithoutTransparent"><input type="text" name="TrnAmt" readonly="readonly" class="dispTabDoubleTextField" value="${POReport.TrnAmt}" /></display:column>
	</display:table>
	</core:if>
	<core:if test="${requestScope.ReportNo==2}">
	<display:table name="POReports" id="POReport" class="its" list="${POReports}">
		<display:column title="PoType" class="formTextFieldWithoutTransparent"><input type="text" name="PoType" readonly="readonly" class="dispTabDoubleTextField" value="${POReport.PoType}" /></display:column>
		<display:column title="PoSno" class="formTextFieldWithoutTransparent"><input type="text" name="PoSno" readonly="readonly" class="dispTabStringTextField" value="${POReport.PoSno}" /></display:column>
		<display:column title="PoActy" class="formTextFieldWithoutTransparent"><input type="text" name="PoActy" readonly="readonly" class="dispTabStringTextField" value="${POReport.PoActy}" /></display:column>
		<display:column title="PoPurchaserName" class="formTextFieldWithoutTransparent"><input type="text" name="PoPurchaserName" readonly="readonly" class="formTextField" value="${POReport.PoPurchaserName}" /></display:column>
		<display:column title="PoGltype" class="formTextFieldWithoutTransparent"><input type="text" name="PoGltype" readonly="readonly" class="dispTabDoubleTextField" value="${POReport.PoGltype}" /></display:column>
		<display:column title="PoGlCode" class="formTextFieldWithoutTransparent"><input type="text" name="PoGlCode" readonly="readonly" class="dispTabStringTextField" value="${POReport.PoGlCode}" /></display:column>
		<display:column title="PoAmt" class="formTextFieldWithoutTransparent"><input type="text" name="PoAmt" readonly="readonly" class="formTextField" value="${POReport.PoAmt}" /></display:column>
		<display:column title="CommissionAmt" class="formTextFieldWithoutTransparent"><input type="text" name="CommissionAmt" readonly="readonly" class="dispTabDoubleTextField" value="${POReport.CommissionAmt}" /></display:column>
	</display:table>
	</core:if>
	<core:if test="${requestScope.ReportNo==3}">
	<display:table name="POReports" id="POReport" class="its" list="${POReports}">
		<display:column title="SrlNo" class="formTextFieldWithoutTransparent"><input type="text" name="SrlNo" readonly="readonly" class="dispTabDoubleTextField" value="${POReport.SrlNo}" /></display:column>
		<display:column title="PoNo" class="formTextFieldWithoutTransparent"><input type="text" name="PoNo" readonly="readonly" class="dispTabStringTextField" value="${POReport.PoNo}" /></display:column>
		<display:column title="ChequeNo" class="formTextFieldWithoutTransparent"><input type="text" name="ChequeNo" readonly="readonly" class="dispTabStringTextField" value="${POReport.ChequeNo}" /></display:column>
		<display:column title="PayeeName" class="formTextFieldWithoutTransparent"><input type="text" name="PayeeName" readonly="readonly" class="formTextField" value="${POReport.PayeeName}" /></display:column>
		<display:column title="Amount" class="formTextFieldWithoutTransparent"><input type="text" name="Amount" readonly="readonly" class="dispTabDoubleTextField" value="${POReport.Amount}" /></display:column>
	</display:table>
	</core:if>
	</div>
	</core:if>
	</td>
	</tr>	
	</table>
	</tr>
	</table>
</html:form>
</body>
</html>