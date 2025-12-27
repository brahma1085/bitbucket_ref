<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@page import="masterObject.pygmyDeposit.FormsDetailsBean"%>
<%@page import="java.util.ArrayList"%>
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
<%FormsDetailsBean[] formsdetailsbean=null;

formsdetailsbean=(FormsDetailsBean[])request.getAttribute("FormsDetail");

String addit=(String)request.getAttribute("addit");

%>
 <html:form action="/Pygmy/FormsDetail?pageId=8888">
 <html:hidden property="forward"/>
 <table>
 			  <tr>
                     <td>
                     <html:select property="modulecode">
                     <option value="CU">Customer</option>
                     <option value="FC">FrontCounter</option>
                     <option value="SH">Share</option>
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
		<%} %>
		
		
	
                
    <input type="button" value="view" onclick="set('view')" class="ButtonBackgroundImage" />
    
    <input type="button" value="AddRow" onclick="set('AddRow')" class="ButtonBackgroundImage" />
    <input type="submit" value="Edit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Edit'" />
	<input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="Save" name="method" />
	<input type="submit" value="Delete" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Delete'"/>
	<input type="submit" value="Clear" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Clear'"/>
	
	<center>
	<input type="submit" value="Submit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Submit'"/>
	</center>
                
</html:form>
</body>
</html>