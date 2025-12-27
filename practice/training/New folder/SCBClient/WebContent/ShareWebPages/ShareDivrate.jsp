<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.DividendRateObject"%>
<%@page import="masterObject.share.DirectorMasterObject"%>
<html>
<head><title>Share Div Rate</title>
       <style type="text/css" media="all">
            @import url("<%=request.getContextPath() %>/css/alternative.css");
            @import url("<%=request.getContextPath() %>/css/maven-theme.css");
            @import url("<%=request.getContextPath() %>/css/site.css");
            @import url("<%=request.getContextPath() %>/css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    
      <link href="<%=request.getContextPath() %>/Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Share Div Rate</center></h2>
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
      
      
      function only_numbers_date() {
	
	var cha=event.keyCode;
	if(cha>=47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  

function only_FT() {
	
	var cha=event.keyCode;
	if(cha==70 || cha==84 )
	{
		return true;
	}
	else
	{
		alert("Enter only T or F");
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
<%!
   String[] tables;
   DividendRateObject[] div_rate;
   DirectorMasterObject[] dmobj;
   DirectorMasterObject[] dmrelobj;
%>
<%
   tables=(String[])request.getAttribute("Tables");
   div_rate=(DividendRateObject[])request.getAttribute("Sharedivrates");
   //dmobj=(DirectorMasterObject[])request.getAttribute("Dir_master");
   //dmrelobj=(DirectorMasterObject[])request.getAttribute("Dir_relation");
   System.out.println("IIIIIIIIIIIIIIIIIIIIII AMMMMMMMMMMMMMMM INNNNNNNN JSPPPPPPPPPPPPPP");
%>
<html:form action="/Share/ShareDivRate?pageId=4018">
 <table class="txtTable">
  <tr>
   <td><bean:message key="label.select"></bean:message></td>
   <td>
     <html:select property="select" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
      
      <%for(int i=0;i<tables.length;i++){ %>
         <html:option value="<%=""+tables[i] %>"><%=""+tables[i]%></html:option>
      <%} %>
     </html:select>
   </td>
   
  
 </tr>
  
 <tr>
 <center>
  <display:table name="DivList" id="DivList" class="its">
		<display:column title="${checkAll}" style="width:1%"><input type="checkbox" name="id" value="${DivList.id}" style="margin:0 0 0 4px" onclick="radio(this)" <core:if test="${param.id==DivList.id and param.method != 'Save' and param.method != 'Clear' and  param.method != 'Reset'}">checked="checked"</core:if> /></display:column>
		<display:column style="width:3%;text-align: right;" title="Frm Date" >
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==DivList.id }">
					<input type="text" name="frm_dt"  style="padding:0;;text-align: right;"  value="${DivList.frm_dt}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_date()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${DivList.frm_dt}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="To Date">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==DivList.id }">
						<input type="text" name="to_dt" style="padding:0;text-align: right;"  value="${DivList.to_dt}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_date()"/>
						<%System.out.println("hi all"); %>
				</core:when>
				<core:otherwise>
					<core:out value="${DivList.to_dt}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Div Rate">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==DivList.id }">
					<input type="text" name="div_rate" style="padding:0;text-align: right;" value="${DivList.div_rate}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_doublevalue()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${DivList.div_rate}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="DRF">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==DivList.id }">
					<input type="text" name="drf" style="padding:0;text-align: right;" value="${DivList.drf}" onkeyup="numberlimit(this,'11')"	onkeypress="return only_numbers_doublevalue()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${DivList.drf}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Cal. Done">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==DivList.id }">
					<input type="text" name="cal_done" style="padding:0;text-align: right;" value="${DivList.cal_done}" onkeypress="return only_FT()" onkeyup="numberlimit(this,'2')"/>
				</core:when>
				<core:otherwise>
					<core:out value="${DivList.cal_done}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Cal opted">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==DivList.id }">
					<input type="text" name="cal_optd" style="padding:0;text-align: right;" value="${DivList.cal_optd}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_date()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${DivList.cal_optd}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
	</display:table>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="submit" value="Reset" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Reset'" />
	<input type="submit" value="Edit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Edit'" />
	<input type="submit" value="Add" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Add'"/>
	<input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="Save" name="method"/>
	<input type="submit" value="Delete" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Delete'"/>
	<input type="submit" value="Clear" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Clear'"/>
	
 </center>
 
 <tr><td><input type="submit" value="submit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=submit'"/></td></tr>
 <tr><td></td></tr>
 <tr><td></td></tr>
 <tr><td></td></tr>
 
 </table>
</html:form>
</body>
</html>