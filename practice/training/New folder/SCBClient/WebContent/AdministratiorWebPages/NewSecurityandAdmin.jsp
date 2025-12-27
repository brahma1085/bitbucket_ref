

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>

<%--
  User: Mohsin
  
  
  To change this template use File | Settings | File Templates.
--%>

<%@page import="masterObject.administrator.TerminalObject"%>
<%@page import="masterObject.administrator.AdministratorObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="masterObject.administrator.AdministratorObject;"%>
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




function seeme1(){
var ele=document.forms[0].elements;
   if(document.forms[0].select1.checked==true){
    for(var i=0;i<ele.length;i++){
    if(ele[i].name=="cbx1"){
    ele[i].checked=true;
    
    }
}
}
else{
for(var i=0;i<ele.length;i++){
    if(ele[i].name=="cbx1"){
    ele[i].checked=false;
    
    }

}
}
}
function seeme2(){
var ele=document.forms[0].elements;
   if(document.forms[0].select2.checked==true){
    for(var i=0;i<ele.length;i++){
    if(ele[i].name=="cbx2"){
    ele[i].checked=true;
    
    }
}
}
else{
for(var i=0;i<ele.length;i++){
    if(ele[i].name=="cbx2"){
    ele[i].checked=false;
    
    }

}
}
}
function seeme3(){
var ele=document.forms[0].elements;
   if(document.forms[0].select3.checked==true){
    for(var i=0;i<ele.length;i++){
    if(ele[i].name=="cbx3"){
    ele[i].checked=true;
    
    }
}
}
else{
for(var i=0;i<ele.length;i++){
    if(ele[i].name=="cbx3"){
    ele[i].checked=false;
    
    }

}
}
}
function seeme4(){
var ele=document.forms[0].elements;
   if(document.forms[0].select4.checked==true){
    for(var i=0;i<ele.length;i++){
    if(ele[i].name=="cbx4"){
    ele[i].checked=true;
    
    }
}
}
else{
for(var i=0;i<ele.length;i++){
    if(ele[i].name=="cbx4"){
    ele[i].checked=false;
    
    }

}
}
}
    </script>
  </head>
  <body class="Mainbody">
  <%!
  AdministratorObject[] formsdetail;
  Map user_role;
  String access;
  %>
    <%String message=(String)request.getAttribute("msg");
    String jspPath;
    
    Object[][] securityObject=(Object[][])request.getAttribute("securityObject");
    formsdetail=(AdministratorObject[])request.getAttribute("formsdetail");
    String accesspageId=(String)request.getAttribute("accesspageId");
	user_role=(Map)request.getAttribute("user_role");
	
	
	if(user_role!=null&&accesspageId!=null){
		if(user_role.get(accesspageId)!=null){
			access=(String)user_role.get(accesspageId);
			System.out.println("access-----In SBOpening------>"+access);
		}

		
		}
    
    %>
  
     
     <html:form action="/Administrator/NewSecurityandAdmin?pageId=10024">
     
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
     <tr><td>Role's</td>
     
     <td>
     <html:select property="roletype" styleClass="formTextFieldWithoutTransparent">
     	<core:if test="${requestScope.roleType!=null}">
     	<core:forEach var="role" items="${requestScope.roleType}">
     	<html:option value="${role.rolecode}" style="color:blue"><core:out value="${role.roledesc}"></core:out></html:option>
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
                     <html:option value="BK">BackOffice</html:option>
                     <html:option value="AD">Administrator</html:option>
                     <html:option value="GL">General Ledger</html:option>
     
     </html:select>
     </td>
     </tr>
     </table>
     
     <br><br><br><br>
     	
     	<core:if test="<%=securityObject!=null&&securityObject.length>0 %>">
     	
     	<table border="1" bordercolor="#0000FF" class="txtTable">
     	<tr>
     	<td><b>Srl No:</b></td>
     	<td><b>Form Name</b></td>
     	<td><b>Read</b></td>
     	<td><b>Insert</b></td>
     	<td><b>Delete</b></td>
     	<td><b>Update</b></td>
     	
     	</tr>
     	<tr>
     	<td></td><td></td>
     	<td><b>Select All:</b><input type="checkbox" name="select1" onclick="seeme1()"></td>
     	<td><b>Select All:</b><input type="checkbox" name="select2" onclick="seeme2()"></td>
     	<td><b>Select All:</b><input type="checkbox" name="select3" onclick="seeme3()"></td>
     	<td><b>Select All:</b><input type="checkbox" name="select4" onclick="seeme4()"></td>
     	</tr>
     	<%for(int i=0;i<securityObject.length;i++){
     		int k=i+1;
     		%>
     	<tr>
     	<td><%=k %></td>
     	<td>
     	<core:if test="<%=securityObject[i][0].toString()!=null %>">
     	<%=securityObject[i][0].toString() %>
     	</core:if>
     	</td>
     
     	<td>
     	<%if(securityObject[i][1].toString()!=null&&securityObject[i][1].toString().toCharArray()[0]=='1') {%>
     	<input type="checkbox" name="cbx1" checked="checked" value="<%=i %>">
     	<%}else{ %>
     	<input type="checkbox" name="cbx1"  value="<%=i %>">
     	<%} %>
     	</td>
     	<td>
     	<%if(securityObject[i][1].toString()!=null&&securityObject[i][1].toString().toCharArray()[1]=='1') {%>
     	<input type="checkbox" name="cbx2" checked="checked" value="<%=i %>">
     	<%}else{ %>
     	<input type="checkbox" name="cbx2"  value="<%=i %>">
     	<%} %>
     	</td>
     	<td>
     	<%if(securityObject[i][1].toString()!=null&&securityObject[i][1].toString().toCharArray()[2]=='1') {%>
     	<input type="checkbox" name="cbx3" checked="checked" value="<%=i %>">
     	<%}else{ %>
     	<input type="checkbox" name="cbx3"  value="<%=i %>">
     	<%} %>
     	</td>
     	<td>
     	<%if(securityObject[i][1].toString()!=null&&securityObject[i][1].toString().toCharArray()[3]=='1') {%>
     	<input type="checkbox" name="cbx4" checked="checked" value="<%=i %>">
     	<%}else{ %>
     	<input type="checkbox" name="cbx4"  value="<%=i %>">
     	<%} %>
     	</td>
     	
     	
     	</tr>
     	<%} %>
     	
     	</table>
     	
     	<br><br>
     	
     	<html:button property="b1" value="Submit" styleClass="ButtonBackgroundImage" onclick="setsubmit('submit')"></html:button>
     	
     	<html:button property="b2" value="Clear" styleClass="ButtonBackgroundImage" onclick="reset123()"></html:button>
     	
     	</core:if>
     	
     	
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