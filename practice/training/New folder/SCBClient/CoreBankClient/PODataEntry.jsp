<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page import="masterObject.general.GLMasterObject" %>
<%@page import="java.util.Map"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
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
<h2 class="h2">PO Data Entry</h2></center></font>
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
    
    
    
    function setval(){
    if(confirm("Are you sure you want to Create Pay Order")){
	    var a=beforesubmiting();
	    if(a){
	  
		    document.forms[0].subval.value='generate';
		    document.forms[0].submit();
	    }
	    else{
	     	document.forms[0].subval.value='';
	    }
    	}
    }
    
    
     function setvals(){
  var a=beforesubmiting();
    if(a){ 
   if(confirm("Are you sure you want to Create Pay Order")){
    document.forms[0].subval.value='generate';
    document.forms[0].submit();
    }
    }
     else{
     document.forms[0].subval.value='';
     }
    }
    
    
    function setval1(){
    if(confirm("Are you sure you want to Verify Pay Order")){
    var a=beforesubmiting();
    if(a){
  
     document.forms[0].subval.value='generate';
    document.forms[0].verval.value='verify';
    document.forms[0].submit();
    }
    else{
     document.forms[0].subval.value='';
       document.forms[0].verval.value='';
    }
    }
    
    }
    
    function setvals1(){
	    if(document.forms[0].srlno.value==""||document.forms[0].srlno.value=='0')
	    {
	    	alert("plz enter the details");
	    }
	    else
	    {
		    if(confirm("Are you sure you want to Verify Pay Order")){
		    document.forms[0].subval.value='generate';
		    document.forms[0].verval.value='verify';
		    document.forms[0].submit();
		    }
	    }
    }
    
    function only_alpa()
	{
	var cha=event.keyCode;
	if((cha >= 97 && cha <= 122)||(cha >= 65 && cha <= 90)||cha==32){
	return true;
	}else{
	return false;
	}
	
	}
    
    function setval2(){
    if(confirm("Are you sure you want to delete Pay Order")){
      var a=beforesubmiting();
    if(a){
  
     document.forms[0].subval.value='generate';
    document.forms[0].verval.value='delete';
    document.forms[0].submit();
    }
    else{
     document.forms[0].subval.value='';
      document.forms[0].verval.value='';
    }
    
   
    }
    else{}
    }
    
    function setval3(){
    if(confirm("Are you sure you want to delete Pay Order")){
      
  
     document.forms[0].subval.value='generate';
    document.forms[0].verval.value='delete';
    document.forms[0].submit();
    
   
    }
    else{}
    }
    
    function checksrlno(){
    if(document.forms[0].srlno.value!=""){
    var a=parseInt(document.forms[0].srlno.value);
    if(a>0){
    document.forms[0].srno.value='hello';
    document.forms[0].submit();
    }
    else{
    
    }
    }
    else{
    document.forms[0].srlno.value='0';
    }
    }
    
    function setimp(){
    var a=parseInt(document.forms[0].srlno.value);
    if(a>0){
    document.forms[0].srno.value='hello';
    }
    else{
    
    }
    }
    
    
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
    else if(ele[i].type=="hidden"){
     ele[i].value="";
    }
    }
    document.forms[0].comission.value='0.0';
   
    
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

 function beforesubmiting(){
 if(document.forms[0].to.value=="Customer"){
    if(document.forms[0].acno.value==""){
    alert("Please Enter Proper Account Number");
    document.forms[0].acno.focus();
    return false;
    }
    else if(document.forms[0].favour.value==""){
    alert("Please Enter in favour of name");
    document.forms[0].favour.focus();
    return false;
    }
    else if(document.forms[0].cat.value=="SELECT"){
    alert("Please Select a Proper Customer Type");
    document.forms[0].cat.focus();
    return false;
    }
    else if(document.forms[0].amount.value==""){
    alert("Please Enter the amount");
    document.forms[0].amount.focus();
    return false;
    }
    else if(document.forms[0].comission.value==""){
   	if(document.forms[0].po.value=="Customer"){
    alert("comission not known !");
    document.forms[0].comission.focus();
    }
    return false;
    }
    
  }
  else{
  if(document.forms[0].bankpurName.value==""){
    alert("Please Enter Purchaser's Name");
    document.forms[0].bankpurName.focus();
    return false;
    }
    else if(document.forms[0].favour.value==""){
    alert("Please Enter in favour of name");
    document.forms[0].favour.focus();
    return false;
    }
    else if(document.forms[0].amount.value==""){
    alert("Please Enter the amount");
    document.forms[0].amount.focus();
    return false;
    }
  else if(document.forms[0].po.value=="Customer"){
  if(document.forms[0].comission.value==""||document.forms[0].comission.value=='0.0'){
    alert("comission not known !");
    document.forms[0].comission.focus();
    return false;
    }
    }
  
  }
  	return true;  
    }
    
    function checksubmit(){
    if(document.forms[0].cat.value=="SELECT"){
    alert("Please Select a Proper Customer Type");
    document.forms[0].cat.focus();
    return false;
    }
    else{
    document.forms[0].submit();
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
         		alert("only "+size+"digits allowed")
         		txt.value="";
         		return false;
          	}
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
       
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	String cust=(String)request.getAttribute("cust");
    	String bank=(String)request.getAttribute("bank");
    	GLMasterObject[] glmObj=(GLMasterObject[])request.getAttribute("gl");
    	String sub=(String)request.getAttribute("sub");
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
    <font color="red"><%=closingmsg%></font>
    </core:if>
    </div>
    
     <html:form action="/FrontCounter/PoDe?pageId=3018" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="subval" />
     	<html:hidden property="verval" />
     	<html:hidden property="srno" />
     	<div id="tabs">
     	</div>
     	<br><br><br>
   <table border="1" class="txtTable">
	<tr>
		<td>PO Srl No.:</td>
		<td><html:text property="srlno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onblur="checksrlno()" onkeyup="numberlimit(this,'10')"></html:text></td>
		<td>PO Type:</td>
		<td ><html:select property="po" styleClass="formTextFieldWithoutTransparent">
                       
                        <core:forEach var="po" varStatus="count" items="${requestScope.po}" >
                        	    <html:option value="${po.moduleCode}" ><core:out value="${po.moduleAbbrv}-${po.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                    </td>
	</tr>
	<tr>
		<td>Generate PO to:</td>
		<td>
		<html:select property="to" onblur="submit()">
		<html:option value="SELECT">SELECT</html:option>
		<html:option value="customer">Customer</html:option>
		<html:option value="bank">Bank</html:option>
		</html:select>
		</td>
	</tr>
	
	<core:if test="<%=cust!=null %>">
	<core:if test="<%=cust.equals("forcustomer") %>">
	<tr>
		<td>Customer Type:</td>
				<td ><html:select property="cat" styleClass="formTextFieldWithoutTransparent" >
                        <html:option value="SELECT" >SELECT</html:option>
                        
                        <core:forEach var="cat" varStatus="count" items="${requestScope.cat}" >
                        	    <html:option value="${cat.subCategoryCode}" ><core:out value="${cat.subCategoryDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                    </td>
		
		
	</tr>
	
	<tr>
		<td>Ac Type:</td>
		<td><html:select property="acType" styleClass="formTextFieldWithoutTransparent" >
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
		</td>
		<td>
		Ac No.:</td>
		<td>
		<html:text property="acno" style="color:#FFFFFF;background-color:#0083C1;font-weight: bold;" onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
	</tr>
	<tr>
		<td>Purchaser:</td>
		<td><html:text property="purName" style="color:#FFFFFF;background-color:#0083C1;font-weight: bold;" readonly="true"></html:text></td>
	</tr>
	<tr>
		<td>Debit GLCode</td>
		<td><html:text property="glcode" style="color:#FFFFFF;background-color:#0083C1;font-weight: bold;" readonly="true"></html:text></td>
		<td>GL Type:</td>
		<td><html:text property="gltype" style="color:#FFFFFF;background-color:#0083C1;font-weight: bold;" readonly="true"></html:text></td>
	</tr>
	<!--<tr>
		<td>GL Name:</td>
		<td><html:text property="glname" onkeypress="return false;"></html:text></td>
	</tr>
	--><tr>
		<td>Details:</td>
		<td><select name="details" style="color:#FFFFFF;background-color:#0083C1;font-weight: bold;">
		<option value="balance">Account Balance</option>
		<!--<option value="sign">Signature Details</option>

		--></select></td>
		<td><html:text property="balance" style="color:#FFFFFF;background-color:#0083C1;font-weight: bold;" readonly="true"></html:text></td>
	</tr>
	</core:if>
	
	</core:if>
	<core:if test="<%=bank!=null %>">
	
	<core:if test="<%=bank.equals("forbank")%>">
	<tr>
		<td>Purchaser:</td>
		<td><html:text property="bankpurName" style="color:#FFFFFF;background-color:#0083C1;font-weight: bold;" onkeypress="return only_alpa()"></html:text></td>
	</tr>
	<tr><td>Debit GL Code:</td>
	<td><html:select property="gl" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <%for(int k=0;k<1;k++){ %>
						<html:option value="<%=glmObj[k].getGLType()%>"><%=glmObj[k].getGLAbbreviation()%></html:option>	                       
                        <%} %>
                        </html:select>
                        </td>
	<td>
	<html:select property="gltyp" styleClass="formTextFieldWithoutTransparent" >
                        <html:option value="SELECT" >SELECT</html:option>
     <%for(int k=0;k<glmObj.length;k++){ %>
						<html:option value="<%=glmObj[k].getGLCode()%>"><%=glmObj[k].getGLName()%></html:option>	                       
                        <%} %>
                        </html:select>

</td>
	
	</tr>
	</core:if>
	</core:if>
	
	<tr>
		<td>In Favour Of:</td>
		<td><html:text property="favour" onkeypress="return only_alpa()" style="color:#FFFFFF;background-color:#0083C1"></html:text></td>
	</tr>
	<tr>
		<td>PO Amount/Comission:</td>
		<td><html:text property="amount" style="color:#FFFFFF;background-color:#0083C1" onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
		<td><html:text property="comission" style="color:#FFFFFF;background-color:#0083C1" readonly="true"></html:text></td>
	</tr>
</table>

<core:if test="<%=sub==null %>">
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
<html:button property="sub" onclick="setvals()" value="Create" styleClass="ButtonBackgroundImage"></html:button>
<%}else{ %>
<html:button property="sub" onclick="setvals()" value="Create" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
<html:button property="ver" onclick="setvals1()" value="Verify" styleClass="ButtonBackgroundImage"></html:button>
<%}else{ %>
<html:button property="ver" onclick="setvals1()" value="Verify" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
<html:button property="del" onclick="setval3()" value="Delete" styleClass="ButtonBackgroundImage"></html:button>
<%}else{ %>
<html:button property="del" onclick="setval3()" value="Delete" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
<%} %>
<html:button  property="B3" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button>
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