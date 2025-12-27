
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@page import="java.util.Map"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Dec 23, 2008
 
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>
<html>

  <head><title>AccountOpening</title>
     <font color="blue" ><center>
<h2 class="h2">Cheque Withdrawal</h2></center></font>
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
    
    function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) ) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
    function checkval(){
    
    var a=document.forms[0].amount.value;
    var b=document.forms[0].acbalance.value;
    
    if(a!=""&&b!=""){
    if(parseInt(a)>parseInt(b))
    {
    alert("Account Balance Rs."+b+" is less than Amount to be drawn");
    document.forms[0].amount.focus();
    }
    }
    }
    
    
    
    function setval(val){
    var c=true;
    var a=document.forms[0].amount.value;
    var b=document.forms[0].acbalance.value;
     c=beforesubmiting();
     
    if(c){
    if(a!=""&&b!=""){
    if(parseInt(a)>parseInt(b))
    {
    alert("Account Balance Rs."+b+" is less than Amount to draw");
    }
    else{
    
    document.forms[0].hidval.value=val;
    
    document.forms[0].submit();
    }
    }
    }
    else{
    document.forms[0].hidval.value='';
    
    }
    }
    function setdelval(val){
    
    document.forms[0].hidval.value='submit';
    document.forms[0].delet.value='delete';
    msg="Are you sure you want to Delete Withdrawal";
    if(confirm(msg)){
    c=beforesubmiting();
    if(c){
    document.forms[0].submit();
    }
    else{
    document.forms[0].hidval.value='';
    document.forms[0].delet.value='';
    }
    }
    
    }
    
    
    function setenable(){
   
    if(document.forms[0].chqslpo.value=='PO'){
    document.forms[0].acType.disabled=true;
    document.forms[0].acno.disabled=true;
    document.forms[0].detailsCombo.disabled=true;
    document.forms[0].acbalance.disabled=true;
    
    }
    else{
   
    document.forms[0].acType.disabled=false;
    document.forms[0].acno.disabled=false;
    document.forms[0].detailsCombo.disabled=false;
    document.forms[0].acbalance.disabled=false;
    }
    
    }
    
    function setright(){
    if(document.forms[0].chqslpo.value=='PO'){
    document.forms[0].acType.disabled=true;
    document.forms[0].acno.disabled=true;
    document.forms[0].detailsCombo.disabled=true;
    document.forms[0].acbalance.disabled=true;
    
    }
    else{
   
    document.forms[0].acType.disabled=false;
    document.forms[0].acno.disabled=false;
    document.forms[0].detailsCombo.disabled=false;
    document.forms[0].acbalance.disabled=false;
    }
    }
    
    
    function gettosub(){
    if(document.forms[0].chqslpo.value=='PO'){
    document.forms[0].submit();
    
    }
    else{
    document.forms[0].submit();
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
    
    }
    document.forms[0].amount.value='0.0';
   
    
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
    if(document.forms[0].tokenno.value==""){
    alert("Please Enter Proper Token Number");
    document.forms[0].tokenno.focus();
    return false;
    }
    else if(document.forms[0].acno.value==""){
    alert("Please Enter Proper Account Number");
    document.forms[0].acno.focus();
    return false;
    }
    else if(document.forms[0].chqslno.value==""){
    alert("Please Enter Cheque/Slip/Po Number");
    document.forms[0].chqslno.focus();
    return false;
    }
    else if(document.forms[0].date.value==""){
    alert("Please Enter Date");
    document.forms[0].date.focus();
    return false;
    }
     else if(document.forms[0].amount.value==""){
    alert("Please Enter valid Amount");
    document.forms[0].amount.focus();
    return false;
    }
     else if(document.forms[0].payee.value==""){
    alert("Please Enter Payee name");
    document.forms[0].payee.focus();
    return false;
    }
    else{
    return true;
    }
    }
    </script>
  </head>
  <body class="Mainbody" onload="setright()">
    <%!
        ModuleObject[] array_module;
        String details[];
        AccountMasterObject acountMaster;
        String jspPath;
        ModuleObject[] mod;
        CustomerMasterObject cmObject;
        SBOpeningActionForm sbForm;
        Map user_role;
        String access;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    	sbForm=(SBOpeningActionForm)request.getAttribute("Account");
    	String msg=(String)request.getAttribute("chqw");
    	String[] ChwDetail=(String[])request.getAttribute("chwdetail");
    	Vector vec=(Vector)request.getAttribute("chqdet");
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
    <core:if test="<%=msg!=null%>">
         <font color="red"><%=msg%></font>
         </core:if>
         </div>
         <br><br>
    <table align="left" valign="top" class="txtTable">
     <html:form action="/FrontCounter/ChqWithdraw?pageId=3008" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	<html:hidden property="defaultSignIndex" value="0"/>
     	<html:hidden property="hidval" />
     	<html:hidden property="delet" />
     	<html:hidden property="clicked" />
       
         <td >
            <table border="0" width="451" height="448" class="txtTable" style="border:thin solid navy">
	<tr>
		<td height="40" width="155">Token Number:</td>
		<td height="40" width="360" colspan="2"><html:text property="tokenno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"/></td>
	</tr>
	<tr>
		<td height="35" width="155">Chq/Slip/PO</td>
		<td height="35" width="360" colspan="2">
<html:select property="chqslpo" styleClass="formTextFieldWithoutTransparent" onblur="setenable()">
                        <html:option value="SELECT" >SELECT</html:option>
                        <html:option value="Cheque" >Cheque</html:option>
                        <html:option value="Slip" >Slip</html:option>
                        <html:option value="PO" >PO</html:option>
</html:select>
</td>
	</tr>
	<tr>
		<td height="36" width="155">Account Type</td>
		<td height="36" width="360" colspan="2">
		<html:select property="acType" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
		
		</td>
	</tr>
	<tr>
		<td height="38" width="155">Account No.</td>
		<td height="38" width="360" colspan="2"><html:text property="acno"  onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"/></td>
	</tr>
	<tr>
		<td height="40" width="155">Name</td>
		<td height="40" width="360" colspan="2">
<core:if test="<%=ChwDetail!=null%>">
         <font color="navy"><%=ChwDetail[0]%></font>
         </core:if>

</td>
	</tr>
	<tr>
	<td>Account Balance:</td>
	<td><html:text property="acbalance" readonly="true" style="color: #FFFFFF; font-family: Verdana; font-weight: bold; font-size: 12px; background-color: navy;" size="10" maxlength="30"></html:text></td>
	</tr>
	<tr>
		<td height="34" width="155">Chq/Slip/PO No.</td>
		<td height="34" width="360" colspan="2"><html:text property="chqslno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onblur="gettosub()" onkeyup="numberlimit(this,'7')"/></td>
	</tr>
	<tr>
		<td height="39" width="155">Account Details</td>
		<td height="39" width="360" colspan="2">
<core:if test="<%=ChwDetail!=null%>">
         <font color="navy"><%=ChwDetail[1]+"  "+ChwDetail[2]%></font>
         </core:if>
</td>
	</tr>
	<core:if test="<%=vec!=null %>">
	<tr>
	<td>Cheque Book Details</td>
	
	</tr>
	
	<tr background="orange" bgcolor="orange">
	<td>ChequeBook No.</td>
	<td>First & Last Cheque No.</td>
	<td>Date of Issue</td>
	</tr>
	<%
	Enumeration enumeration=vec.elements();
	while(enumeration.hasMoreElements())
    {
        StringTokenizer st=new StringTokenizer(enumeration.nextElement().toString()," ");
        %>
        <tr bordercolor="orange">
       <td><%=st.nextToken().toString()%></td>
        <td><%=st.nextToken().toString()%></td>
        <td><%=st.nextToken().toString()%></td>
       </tr>
    <%}
	%>
	</core:if>
	<tr>
		<td height="38" width="155">Date</td>
		<td height="38" width="360" colspan="2"><html:text property="date" readonly="true" /></td>
	</tr>
	<tr>
		<td height="39" width="155">Amount Rs.</td>
		<td height="39" width="360" colspan="2"><html:text property="amount" onblur="checkval()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"/></td>
	</tr>
	<tr>
		<td height="40" width="155">Payees Name</td>
		<td height="40" width="360" colspan="2"><html:text property="payee" onkeypress="return only_alpha()" /></td>
	</tr>
	<tr>
		<td height="40" width="155">Details</td>
		<td height="40" width="360" colspan="2">
		<html:select property="detailsCombo" onblur="submit()">
		<html:option value="Personal">Personal Details</html:option>
		<html:option value="Signature Ins">Signature Details</html:option>
		<html:option value="unverifiedtokens">Unverified Tokens</html:option>
		
		</html:select>
		
		</td>
	
	</tr>
	<tr>
	<core:if test="<%=ChwDetail!=null%>">
		<td height="40" width="155">
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
		<html:button property="sub" value="Submit" onclick="setval('submit')" styleClass="ButtonBackgroundImage"/>
		<%}else{ %>
		<html:button property="sub" value="Submit" onclick="setval('submit')" styleClass="ButtonBackgroundImage" disabled="true"/>
		<%} %>
		</td>
		
		<!--<td height="40" width="146"><html:submit property="del1" value="Delete" onclick="setdelval('delete')" styleClass="ButtonBackgroundImage"/></td>
	
		--></core:if>
			<td height="40" width="208"><html:button property="B2" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button> </td>
	</tr>
	
</table>

        </td>
        <td>

        </td>
               	
       </html:form>
      </table>
      
       <table align="left" valign="top">
      		<tr>
      		<td>
      		<%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
            </td>
            </tr>
        </table>
   <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
</html>