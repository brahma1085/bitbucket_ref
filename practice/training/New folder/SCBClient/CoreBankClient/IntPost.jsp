
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@page import="java.util.Map"%>
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
<h2 class="h2">Interest Posting</h2></center></font>
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
    function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only "+size+"digits allowed")
         		txt.value="";
         		return false;
          	}
         }
    
    function whichis(){

for(var i=0;i<1;i++){


if(this.document.forms[0].rad[i].value=="all"){

document.forms[0].acno.disabled='disabled';


}
else{
}
}
}
    
    
     function reset123(){
   
    var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="text")
    {
    ele[i].value="";
    
    }
    else if(ele[i].type=="checkbox"){
    ele[i].checked=false;
    }
    else if(ele[i].type=="hidden"){
     ele[i].value="";
    }
    else if(ele[i].type=="radio"){
    ele[i].checked=false;
    }
    }
    
   show(false,'div1');
    document.forms[0].minibal.value='0.0';
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
         Map user_role;
         String access;
         Object[][] odccintdetails;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	odccintdetails=(Object[][])request.getAttribute("odccintdetails");
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
    <div id = "div1" class = "myLayersClass">
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=request.getAttribute("closingmsg")%></font>
    </core:if>
    </div>
     <html:form action="/FrontCounter/IntPost?pageId=3017" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<div id="tabs">
     	</div>
     	<br><br><br>
            <table border="1" width="455" height="194" style="border:thin solid navy" class="txtTable">
	<tr>
		<td height="44" width="211"><html:radio property="rad" value="selected" onclick="javascript:document.forms[0].acno.disabled=false">Selected Account</html:radio></td>
		<td height="44" width="228"><html:radio property="rad" value="all" onclick="javascript:document.forms[0].acno.disabled=true">All Account's</html:radio></td>
	</tr>
	<tr>
		<td height="46" width="211">Account Type:</td>
		<td height="46" width="228">
<html:select property="acType" styleClass="formTextFieldWithoutTransparent" >
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>

</td>
	</tr>
	<tr>
		<td height="47" width="211">Account No:</td>
		<td height="47" width="228"><html:text property="acno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
	</tr>
	<tr>
		<td height="45" width="455" colspan="2">
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
		<html:submit value="Post" styleClass="ButtonBackgroundImage"></html:submit>
		<%}else{ %>
		<html:submit value="Post" styleClass="ButtonBackgroundImage" disabled="true"></html:submit>
		<%} %>
		</td>
		
		<td height="35" width="60"><html:button property="rest" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button></td>
	</tr>
</table>

<br><br>
<core:if test="<%=odccintdetails!=null&&odccintdetails.length>0 %>">
<table border="1" style="background-color:#CDCEAE">
<tr style="background-color:#CDCEAE">
<td ><font style="font: bold;">A/C Type</font></td>
<td><font style="font: bold;">A/C No.</font></td>
<td><font style="font: bold;">Name</font></td>
<td><font style="font: bold;">Prev. Bal</font></td>
<td><font style="font: bold;">Int Amount</font></td>
<td><font style="font: bold;">Penal Int</font></td>
<td><font style="font: bold;">Calculated Bal</font></td>
<td><font style="font: bold;">Sanc Amt</font></td>
</tr>
<%for(int j=0;j<odccintdetails.length;j++){%>
<tr>
	<%for(int k=0;k<7;k++){
	System.out.println("column data is not null");
%> 
<td style="background-color:#ECEBD2">
<core:if test="<%=odccintdetails[j][k]!=null %>">
<%=odccintdetails[j][k].toString() %>
</core:if>
</td>
<%}%>
<td style="background-color:#ECEBD2">
<core:if test="<%=odccintdetails[j][9]!=null %>">
<%=odccintdetails[j][9].toString() %>
</core:if>
</td>
</tr>
<%} %>
</table>
</core:if>
                </html:form>
                <%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
             <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
  </html>          