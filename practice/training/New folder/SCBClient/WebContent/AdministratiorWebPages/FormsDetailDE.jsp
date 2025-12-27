<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@page import="masterObject.administrator.AdministratorObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<html>
<head>
<title></title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
	
<h2 class="h2"><center>FormsDetails</center></h2>


<script type="text/javascript">
function set(target){

       document.forms[0].forward.value=target;
      
       document.forms[0].submit();
        };
</script>
<body>
<%!
Map user_role;
String access;
%>

<%AdministratorObject[] formsdetailsbean=null;

formsdetailsbean=(AdministratorObject[])request.getAttribute("FormsDetail");

String addit=(String)request.getAttribute("addit");
String accesspageId=(String)request.getAttribute("accesspageId");
user_role=(Map)request.getAttribute("user_role");


if(user_role!=null&&accesspageId!=null){
	if(user_role.get(accesspageId)!=null){
		access=(String)user_role.get(accesspageId);
		System.out.println("access-----In SBOpening------>"+access);
	}

	
	}
%>

 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
 <html:form action="/Administrator/FormsDetail?pageId=10026" >
 <html:hidden property="forward"/>
 <table>
 			  <tr>
                     <td>
                     <html:select property="modulecode">
                     <html:option value="CU">Customer</html:option>
                     <html:option value="CA">Cash</html:option>
                     <html:option value="SH">Share</html:option>
                     <html:option value="FC">Frontcounter</html:option>
                     <html:option value="TD">TermDeposits</html:option>
                     <html:option value="LN">Loans</html:option>
                     <html:option value="CL">Clearing</html:option>
                     <html:option value="PD">Pygmy Deposit</html:option>
                     <html:option value="LD">Loans on Deposit</html:option>
                     <html:option value="LK">Locker</html:option>
                     <html:option value="AD">Administrator</html:option>
                     <html:option value="GL">General Ledger</html:option>
                     
                     </html:select>
                     </td>
                     
                </tr>
                <tr>
                
                </tr>
                
</table>     
		<% if(formsdetailsbean!=null){%>
		<div style="overflow:scroll;width:700px;height:200px">
		<table><tr><td>Select</td>
		<td>Form Name</td>
		<td>Page Id</td>
		</tr>
		<%for(int k=0;k<formsdetailsbean.length;k++){ %>
		<tr>
		<td><input type="checkbox" name="id" value="<%=""+k%>"></td>
		<td><input type="text" name="formname" value="<%=formsdetailsbean[k].getFormname()%>"></td>
		<td><input type="text" name="pageno" value="<%=formsdetailsbean[k].getPageId()%>"></td>
		</tr>
		
		<%} %>
		<%if(addit!=null){ %>
		<tr>
		<td></td>
		<td><html:text property="addinformname"></html:text></td>
		<td><html:text property="addinpageno"></html:text></td>
		</tr>
		<%} %>
		</table>
		</div>
		<%} else{%>
		<table>
		<tr>
		<td></td>
		
		</tr>
		</table>
		
	<%} %>
                
    <input type="button" value="View" onclick="set('view')" class="ButtonBackgroundImage" />
    
    <input type="button" value="AddRow" onclick="set('AddRow')" class="ButtonBackgroundImage" />
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
   <input type="button" value="Submit" onclick="set('submit')" class="ButtonBackgroundImage" />
   <%}else{ %>
   <input type="button" value="Submit" onclick="set('submit')" class="ButtonBackgroundImage" disabled="disabled"/>
   <%} %>
   <input type="submit" value="Clear" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Clear'"/>
	
	<center>
	<!--<input type="submit" value="Submit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Submit'"/>
	--></center>
                
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>