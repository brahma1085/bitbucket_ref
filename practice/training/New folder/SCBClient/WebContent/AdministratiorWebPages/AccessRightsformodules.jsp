

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Amzad
  Date: Feb 13, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<%@page import="masterObject.administrator.TerminalObject"%>
<%@page import="java.util.ArrayList"%>
<html>
  <head><title>IP Configuration</title>
       <font color="blue" ><center>
<h2 class="h2">Security And Administration</h2></center></font>
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
  function setsubmit(v){
   
    document.forms[0].sub.value=v;
  document.forms[0].submit();
    }
    
    
  function setupdate(u){
  document.forms[0].sub.value='submit'
  document.forms[0].updt.value=u;
  document.forms[0].submit();
  }
  
  
  function reset123(){
    var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="text")
    {
    ele[i].value="";
    }
   else if(ele[i].type=="hidden")
    {
    ele[i].value="";
    }
    else if(ele[i].type=="checkbox")
    {
    ele[i].checked=false;
    }
    
    }
    show(false,'div1');
    
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

function selectAll(){
var ele=document.forms[0].elements;
   if(document.forms[0].selectall.checked==true){
    for(var i=0;i<ele.length;i++){
    if(ele[i].type=="checkbox")
    {
    ele[i].checked=true;
    }
    }
    }
    else{
     for(var i=0;i<ele.length;i++){
    if(ele[i].type=="checkbox")
    {
    ele[i].checked=false;
    }
    }
    
    }
}
    </script>
  </head>
  <body class="Mainbody">
    <%String message=(String)request.getAttribute("msg");
    String jspPath;
    %>
    
     <html:form action="/Administrator/AccessRightsforModules?pageId=10030">
     
     <div id = "div1" class = "myLayersClass">
    <core:if test="<%=message!=null %>">
    <font color="red"><%=message %></font>
    </core:if>
    </div>
     <br><br>
     <html:hidden property="sub"/>
    <html:hidden property="updt"/>
    <html:hidden property="first"/>
     <table class="txtTable" align="center">
     <tr><td>User</td>
     <td>
     <html:select property="uidValue" styleClass="formTextFieldWithoutTransparent">
     	<core:if test="${requestScope.UserDetails!=null}">
     	<core:forEach var="usr" items="${requestScope.UserDetails}">
     	<html:option value="${usr}" style="color:blue"><core:out value="${usr}"></core:out></html:option>
          </core:forEach>
     	</core:if>
     	</html:select>
     </td>
     
     </tr>
     <!--<tr><td>Terminal</td>
     
     <td>
     <html:select property="tmlNo" styleClass="formTextFieldWithoutTransparent" onblur="submit()">
     	<core:if test="${requestScope.TerminalNoArray!=null}">
     	<core:forEach var="tmlno" items="${requestScope.TerminalNoArray}">
     	<html:option value="${tmlno}" style="color:blue"><core:out value="${tmlno}"></core:out></html:option>
          </core:forEach>
     	</core:if>
     	</html:select>
     
     </td>
     
     </tr>
     --><tr><td>Module:</td>
     <td><html:select property="mods" onchange="submit()"> 
     <html:option value="Select">SELECT</html:option>
     <html:option value="TD">Term Deposit</html:option>
     <html:option value="customer">Customer</html:option>
     </html:select>
     </td>
     </tr>
     </table>
     
     <br><br><br><br>
     	
            </html:form>
             <%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
  </body>
  </html>          