
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Dec 8, 2008
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<%@page import="com.scb.fc.forms.AccountCloseActionForm"%>
<html>
  <head><title>AccountOpening</title>
       <font color="blue" ><center>
       <core:if test="<%=(String)request.getAttribute("Pagehead")!=null %>">
<h2 class="h2"><%=(String)request.getAttribute("Pagehead")%></h2>
</core:if>
</center></font>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
       
      
      <!--<style type="text/css">
          body{
              font-size:10px;
              font-family:serif;
              font-style:oblique;
              font-weight:bold;
              background:beige;
          }
          table{
               background:transperent; 
          }
          
    </style>
    --><link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    <script>

    
     function reset123(){
    
    var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="text")
    {
    ele[i].value="";
    show(false,'div1');
    }
    else if(ele[i].type=="checkbox"){
    ele[i].checked=false;
    }
    
    }
    
    }
    
     var ns4 = (document.layers) ? true : false;
var ie4 = (document.all && !document.getElementById) ? true : false;
var ie5 = (document.all && document.getElementById) ? true : false;
var ns6 = (!document.all && document.getElementById) ? true : false;

function show(sw,obj) {
  // show/hide the divisions
  if (sw && (ie4 || ie5) ) document.all[obj].style.visibility = 'visible';
  if (!sw && (ie4 || ie5) ) document.all[obj].style.visibility = 'hidden';
  if (sw && ns4) document.layers[obj].visibility = 'visible';
  if (!sw && ns4) document.layers[obj].visibility = 'hidden';
}


    </script>
    
  </head>
  <body class="Mainbody">
    <%!
        ModuleObject[] array_module;
        String details[];
        AccountMasterObject acountMaster;
        String jspPath;
        ModuleObject[] mod;
        CustomerMasterObject cmObject;
         AccountCloseActionForm acForm;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	
    %>
 
    <div id = "div1" class = "myLayersClass">
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=request.getAttribute("closingmsg")%>
    </core:if>
    </div>
    <html:form action="/FrontCounter/unverifiedtokens?pageId=3058" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	
<%String cols[]=(String[])request.getAttribute("sbcols");
Object colData[][]=(Object[][])request.getAttribute("columnData");
int k,j;
%>
<%if(colData!=null){
	if(colData.length!=0){
%>
<table border="1" style="background-color:#CDCEAE">
<tr bgcolor="#0099CC">
<% for( k=0;k<cols.length;k++){%>
<td style="background-color:#CDCEAE">
<font style="font: bold;"> <%=cols[k]%></font>
</td>
<%}%>
</tr>
<%if(colData!=null){
for(j=0;j<colData.length;j++){%>
<tr>
	<%for( k=0;k<cols.length;k++){
	System.out.println("column data is not null");
%> 
<td style="background-color:#ECEBD2">
<core:if test="<%=colData[j][k]!=null %>">
<%=colData[j][k].toString() %>
</core:if>
</td>
<%}%>
</tr>
<%}}
else {out.println("data is null");}
%>


</table>
<%}
	else{%>
		<font color="red"> Records Not Found</font>
		
<%	}	
} else{%>
		<font color="red"> Records Not Found</font>
		
<%	}%>
   </html:form>
             
     </body>
     </html>          