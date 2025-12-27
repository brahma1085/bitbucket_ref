<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="display"  uri="http://displaytag.sf.net/el"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head><title>Change of Modules</title>
       <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />

      <h2 class="h2">
      <center>Change of Module</center></h2>
      <hr>
      
     <script type="text/javascript">
        
       function only_numbers_doublevalue() {
	
	var cha=event.keyCode;
	if((cha>=48 && cha<=57) || cha==46)
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;
       
       
        function only_numbers() {
	
	var cha=event.keyCode;
	if(cha>47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  
        
        function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
        
        function set(target){
        document.forms[0].forward.value=target
        };
        
</script>
</head>        
<body class="Mainbody">
<%
   ModuleObject modobj;
   String id;
%>
<%
  // modobj=(ModuleObject)request.getAttribute("chmod");
%>
<html:form action="/Share/ChangeofModules?pageId=4019">
<table class="txtTable">
  <tr>
  <td>Mod.Abbr
  <html:select property="actype" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
          <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}">
         
          <html:option value="${acType.moduleCode}">
          <core:out value="${acType.moduleAbbrv}"></core:out>
          </html:option>
          </core:forEach>
        </html:select>
        
  </td> 
  
 </tr>

<tr>
<td>
 <div style="width: 760px;height: 100px;overflow-x:scroll;overflow-y:hidden">
 
 <core:if test="${requestScope.chmod!=null}">
  <display:table name="chmod" class="its" id="chmod" export="true">
    <display:column title="${checkAll}" style="width:1%"><input type="checkbox" name="id" value="${chmod.id}" style="margin:0 0 0 4px" onclick="radio(this)" <core:if test="${param.id==chmod.id and param.method != 'Save' and param.method != 'Clear' and  param.method != 'Reset'}">checked="checked"</core:if>/></display:column>
     		<display:column style="width:3%;text-align: right;" title="Mod.Code">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="Mod_Code"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.mod_code}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.mod_code}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
     		<display:column style="width:3%;text-align: right;" title="Mod.des">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="Mod_des"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.mod_desc}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.mod_desc}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
      
      
     <display:column style="width:3%;text-align: right;" title="Mod.abbr">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="Mod_abbr"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.mod_abbr}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.mod_abbr}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
     
     <display:column style="width:3%;text-align: right;" title="LstAccNo">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="LstAccno"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.Lst_ac_no}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.Lst_ac_no}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
     
     <display:column style="width:3%;text-align: right;" title="MaxRCount">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="LstAccno"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.max_R_Count}" onkeyup="numberlimit(this,'11')"	onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.max_R_Count}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
     
     <display:column style="width:3%;text-align: right;" title="Std_inst">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="std_inst"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.std_inst}" onkeyup="numberlimit(this,'11')"	onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.std_inst}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
     
     <display:column style="width:3%;text-align: right;" title="MaxRDays">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="maxrDays"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.max_R_days}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.max_R_days}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
     
    <display:column style="width:3%;text-align: right;" title="renewalInt">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="renewalInt"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.RCTNo}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.RCTNo}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
     
     <display:column style="width:3%;text-align: right;" title="penalty">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="penalty"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.Penalty_Rate}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_doublevalue()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.Penalty_Rate}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
		
	<display:column style="width:3%;text-align: right;" title="lstVoucher">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="lstVoucher"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.lst_vou}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.lst_vou}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>	
		
	<display:column style="width:3%;text-align: right;" title="lstTrfScrNo">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="lstTrfScrNo"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.lst_TRF_scroll_no}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.lst_TRF_scroll_no}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>		
     
     
     <display:column style="width:3%;text-align: right;" title="insPeriod">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="insPeriod"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.Inspection_Period}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.Inspection_Period}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>	
  
  
  <display:column style="width:3%;text-align: right;" title="Ln">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==chmod.id }">
					<input type="text" name="Loan"   style="padding:0;background: transparent;border: 0px" style="padding:0" value="${chmod.loan_mod}" onkeyup="numberlimit(this,'11')"	onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${chmod.loan_mod}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>	
  </display:table>
   
 </core:if>
 
 </div>
 </td>
 </tr>
 <tr>
  <html:hidden property="forward" value="error"></html:hidden>
  <td>
  <input type="submit" value="Reset" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Reset'" />
	<input type="submit" value="Edit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Edit'" />
	<input type="submit" value="Add" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Add'"/>
	<input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="Save" name="method"/>
	<input type="submit" value="Delete" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Delete'"/>
	<input type="submit" value="Clear" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Clear'"/>
  </td>
      
</tr>
</table>
</html:form>
</body>
</html>