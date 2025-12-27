<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
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
<html:form action="/FrontCounter/PassSheet?pageId=3016">
	<script type="text/javascript">
    	function clears(){
 			alert("clear");
 			document.forms[0].custDetails.value="";
 			document.forms[0].status.value=""; 
 			document.getElementById("status").style.display='none';
 			document.getElementById("custDetails").style.display='none'; 
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
	<h1 class="h2"><center><bean:message key="label.passSheetAc"/></center></h1>
	<table>
		<table>
		<tr>
			<td><bean:message key="label.acType"/></td>
			<td><html:select  property="acType" styleClass="formTextFieldWithoutTransparent" >
            	    <html:option value="SELECT" ><bean:message key="label.select"/></html:option>
                	<core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                    	<html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                     </core:forEach>
                </html:select>
                <html:text property="acNo" value=""></html:text>
            </td>
			<td><bean:message key="label.frm_dt"/></td>
			<td><html:text property="frmDt" styleClass="formTextFieldWithoutTransparent" value="${requestScope.FrmDate}" onblur="ValidateIndividualDate(frmDt.value)" ></html:text></td>
			<td><bean:message key="label.to_dt"/></td>
			<td><html:text property="toDt" styleClass="formTextFieldWithoutTransparent" value="${requestScope.ToDate}" onblur="ValidateIndividualDate(toDt.value)" onchange="properdate(frmDt.value,toDt.value)" ></html:text></td>
			<td><bean:message key="label.acStatus"/>
				<core:if test="${requestScope.AcStatus!=null}">
						<input type="text" id="status" class="formTextField" value="<core:out  value="${requestScope.AcStatus}" ></core:out>"/>
				</core:if>
			</td>
		</tr>
		<tr>
			<td ><html:submit styleClass="ButtonBackgroundImage" value=""><bean:message key="label.view1"></bean:message></html:submit></td>
			<td ><html:reset styleClass="ButtonBackgroundImage" value="" onclick="clears()"><bean:message key="label.clear"></bean:message></html:reset></td>
		</tr>
		</table>
		<core:if test="${requestScope.PersDetails!=null}">
		<table>
			<tr>
				<td>
					<html:textarea property="custDetails" styleId="custDetails" styleClass="formTextField" readonly="readonly" value="${requestScope.PersDetails}"></html:textarea>
				</td>
			</tr>
		</table>
		</core:if>
		<table>
			<tr>
				<td>
					<core:if test="${requestScope.TranDetails!=null}">
					<div id="table1" style="display: block;overflow: scroll;width: 750px;height: 300px">
					<display:table name="TranDetails" id="TranDetail" class="its" list="TranDetails">
						<display:column title="Srl" class="formTextFieldWithoutTransparent"><input type="text" name="Srl" readonly="readonly" class="dispTabDoubleTextField" value="${TranDetail.Srl}" /></display:column>
						<display:column title="TrnDate" class="formTextFieldWithoutTransparent"><input type="text" name="TrnDate" readonly="readonly" class="dispTabStringTextField" value="${TranDetail.TrnDate}" /></display:column>
						<display:column title="ChqNo" class="formTextFieldWithoutTransparent"><input type="text" name="ChqNo" readonly="readonly" class="dispTabStringTextField" value="${TranDetail.ChqNo}" /></display:column>
						<display:column title="Narration/Payee" class="formTextFieldWithoutTransparent"><input type="text" name="Narration/Payee" readonly="readonly" class="formTextField" value="${TranDetail.Narration/Payee}" /></display:column>
						<display:column title="Debit" class="formTextFieldWithoutTransparent"><input type="text" name="Debit" readonly="readonly" class="dispTabDoubleTextField" value="${TranDetail.Debit}" /></display:column>
						<display:column title="Credit" class="formTextFieldWithoutTransparent"><input type="text" name="Credit" readonly="readonly" class="dispTabStringTextField" value="${TranDetail.Credit}" /></display:column>
						<display:column title="ClosingBal" class="formTextFieldWithoutTransparent"><input type="text" name="ClosingBal" readonly="readonly" class="formTextField" value="${TranDetail.ClosingBal}" /></display:column>
						<display:column title="EntBy" class="formTextFieldWithoutTransparent"><input type="text" name="EntBy" readonly="readonly" class="dispTabDoubleTextField" value="${TranDetail.EntBy}" /></display:column>
						<display:column title="VerBy" class="formTextFieldWithoutTransparent"><input type="text" name="VerBy" readonly="readonly" class="dispTabDoubleTextField" value="${TranDetail.VerBy}" /></display:column>
					</display:table>
					</div>
					</core:if>
				</td>
			</tr>
		</table>	
	</table>
</html:form>
</body>
</html>