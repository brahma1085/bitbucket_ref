
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountInfoObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@page import="java.util.Map"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Dec 24, 2008
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<html>
  <head><title>AccountOpening</title>
      <font color="blue" ><center>
<h2 class="h2">Cheque Issue</h2></center></font>
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
    
    function setval(val){
    var con="Is data Correct";
    if(confirm(con)){
    var a=checkAll();
    if(a){
     document.forms[0].submiting.value=val;
    document.forms[0].submit();
    }
    else{
    document.forms[0].submiting.value='';
    }
    
    }
    
   
    
    
    }
    function setvals(val){
    var con="Are you sure you want to go ahead with the operation ";
    if(confirm(con)){
   
     document.forms[0].submiting.value=val;
    document.forms[0].submit();
    }
    else{
    document.forms[0].submiting.value='';
    }
    
    }
    
   
    
    
    
    
    
    
    function add(){
    var a=document.forms[0].fromno.value;
    var b=document.forms[0].leaveno.value;
    var c=parseInt(a)+parseInt(b);
    
    document.forms[0].tono.value=c-1;
    
    
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

function checkAll(){
var ele=document.forms[0].elements;
for(var i=0;i<ele.length;i++){
if(ele[i].type=="text"){
if(ele[i].value==""){
alert("Please Fill All The Fields");
ele[i].focus();
return false;
}
else{

}

}

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
        SBOpeningActionForm sbForm;
        Map user_role;
        String access;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    	sbForm=(SBOpeningActionForm)request.getAttribute("Account");
    	String msg=(String)request.getAttribute("empty");
    	String[] chqdetail=(String[])request.getAttribute("chqissuedetails");
    	AccountInfoObject acinfo=(AccountInfoObject)request.getAttribute("acinfo");
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
    <html:form action="/FrontCounter/ChequeIssue?pageId=3009"  focus="<%=(String)request.getAttribute("FocusTo") %>" >
    <html:hidden property="submiting" />
    <html:hidden property="deleting" />
       
     	<html:hidden property="pageId" value="3005"/>
 <div id = "div1" class = "myLayersClass">
         <core:if test="<%=msg!=null%>">
         <font color="red"><%=msg%></font>
         </core:if>   
         </div>
         <br><br>
<table border="1" style="border:thin solid navy" class="txtTable">
	<tr>
		<td>Account Type:</td>
		<td>
<html:select property="acType" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>

</td>
		
	</tr>
	<tr>
		<td>Account No.</td>
		<td colspan="3"><html:text property="acnum" onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
	</tr>
	</table>
	
	<br><br><br>
	
	  <table style="border:thin solid navy" class="txtTable">
   
	<tr>
		<td>Name:</td>
		<td 

colspan="3"><html:text property="accname" readonly="true" style="color:navy"></html:text></td>
	</tr>
	<tr>
		<td>Date of 

Issue:</td>
		<td 

colspan="3"><html:text property="dateissue" readonly="true" ></html:text></td>
	</tr>
	<tr>
		<td colspan="2">First chq no. of previous chq book:</td>
		<td colspan="2"><html:text property="prechbook" readonly="true"></html:text></td>
	</tr>
	<tr>
		<td>Chq Book No.</td>
		<td colspan="3"><html:text property="chbookno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
	</tr>
	
</table>
<br><br>
 <table border="1" bgcolor="#8AE18A" style="border:thin solid navy" class="txtTable">
	<tr>
		<td colspan="4" align="center">Details of cheque book being issued</td>
	</tr>
	<tr>
		<td colspan="2" align="right">Number of Leaves:</td>
		<td 

colspan="2"><html:text property="leaveno"  onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'5')"></html:text></td>
	</tr>
	<tr>
		<td align="right">From:</td>
		<td><html:text property="fromno"  onblur="add()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'7')"></html:text></td>
		<td align="right">To:</td>
		<td><html:text property="tono"  readonly="true" ></html:text></td>
	</tr>
	<tr>
	<td>
	
	

	</td>
	</tr>
</table>
<br><br>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
<html:button property="b1" onclick="setvals('update')" value="Submit" styleClass="ButtonBackgroundImage"></html:button>
<%}else{ %>
<html:button property="b1" onclick="setvals('update')" value="Submit" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
<html:button property="updt1" value="Delete" onclick="setvals('delete')" styleClass="ButtonBackgroundImage"></html:button>
<%}else{ %>
<html:button property="updt1" value="Delete" onclick="setvals('delete')" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
<html:button property="updt2" value="Verify" onclick="setvals('verify')" styleClass="ButtonBackgroundImage"></html:button>
<%}else{ %>
<html:button property="updt2" value="Verify" onclick="setvals('verify')" styleClass="ButtonBackgroundImage" disabled="true"></html:button>

<%} %>
<html:button  property="B3" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button>
			
    </html:form>
    
      		<%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
      		
      		
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim()%>"></jsp:include>
            </core:if>
            
            <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
</html>