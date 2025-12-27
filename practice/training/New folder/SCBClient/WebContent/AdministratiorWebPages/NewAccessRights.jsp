

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
 
  
  To change this template use File | Settings | File Templates.
--%>

<%@page import="masterObject.administrator.TerminalObject"%>
<%@page import="java.util.ArrayList"%>
<html>
  <head><title>IP Configuration</title>
     <center>
<h2 class="h2">User Access Rights</h2></center>
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
    <%String message=(String)request.getAttribute("msg"); %>
    
     <html:form action="/Administrator/NewAccessRights?pageId=10023">
     
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
     <td><html:button property="B1" value="View" onclick="submit()" styleClass="ButtonBackgroundImage"></html:button> </td>
     </tr>
     <tr><!--<td>Terminal</td>
     
     --><td>
     <!--<html:select property="tmlNo" styleClass="formTextFieldWithoutTransparent" onblur="submit()">
     	<core:if test="${requestScope.TerminalNoArray!=null}">
     	<core:forEach var="tmlno" items="${requestScope.TerminalNoArray}">
     	<html:option value="${tmlno}" style="color:blue"><core:out value="${tmlno}"></core:out></html:option>
          </core:forEach>
     	</core:if>
     	</html:select>
     
     -->
     <html:hidden property="tmlNo" value="CA99" />
     </td>
     
     </tr>
     </table>
     
     <br><br><br><br>
     	<table class="txtTable" align="center">
     	<tr><td><font color="blue"> Select All</font></td><td><html:checkbox property="selectall" onclick="selectAll()"></html:checkbox></td></tr>
<tr>
<td>Customer</td><td><html:checkbox property="chkbox1" value="1"></html:checkbox></td></tr>
<tr><td>Cash</td><td><html:checkbox property="chkbox2" value="1"></html:checkbox></td></tr>
<tr><td>Verification</td><td><html:checkbox property="chkbox3" value="1"></html:checkbox></td></tr>
<tr><td>Share</td><td><html:checkbox property="chkbox4" value="1"></html:checkbox></td></tr>
<tr><td>FontCounter</td><td><html:checkbox property="chkbox5" value="1"></html:checkbox></td></tr>
<tr><td>TermDeposit</td><td><html:checkbox property="chkbox6" value="1"></html:checkbox></td></tr>
<tr><td>Loans</td><td><html:checkbox property="chkbox7" value="1"></html:checkbox></td></tr>
<tr><td>Clearing</td><td><html:checkbox property="chkbox8" value="1"></html:checkbox></td></tr>
<tr><td>PygmyDeposit</td><td><html:checkbox property="chkbox9" value="1"></html:checkbox></td></tr>
<tr><td>LoansOnDeposit</td><td><html:checkbox property="chkbox10" value="1"></html:checkbox></td></tr>
<tr><td>Lockers</td><td><html:checkbox property="chkbox11" value="1"></html:checkbox></td></tr>
<tr><td>BackOffice</td><td><html:checkbox property="chkbox12" value="1"></html:checkbox></td></tr>
<tr><td>GeneralLedger</td><td><html:checkbox property="chkbox13" value="1"></html:checkbox></td></tr>
<tr><td>Admin</td><td><html:checkbox property="chkbox14" value="1"></html:checkbox></td>


</tr>
<tr></tr>
<tr></tr>
<tr>
<td><html:button property="b1" value="Submit" styleClass="ButtonBackgroundImage" onclick="setsubmit('submit')"></html:button></td>
<td><html:button property="b2" value="Update" styleClass="ButtonBackgroundImage" onclick="setupdate('update')"></html:button></td>
<td><html:button property="b3" value="Clear" styleClass="ButtonBackgroundImage" onclick="reset123()"></html:button></td>
</tr>
</table>
            </html:form>
  </body>
  </html>          