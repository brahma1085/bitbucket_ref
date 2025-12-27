
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@page import="java.util.Map"%>
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
<h2 class="h2">Interest Calculation</h2></center></font>
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
    function calculate(){
    document.forms[0].cal.value='calculate';
    
    }
     function recalculate(){
    document.forms[0].cal.value='calculate';
    document.forms[0].recal.value='recalculate';
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

function Checksubmit(){

var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){

   if(ele[i].type=="radio"){
    
    if(ele[i].checked){
    if(ele[i].value=='account'){
    
    document.forms[0].submit();
    }
   else if(ele[i].value=='all'){
   
   document.forms[0].submit();
   }
    else{
    alert("Please select All Account's or selected account");
    break;
    }
   
    }
    }

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
  String msg=(String)request.getAttribute("msg");
    	String recalc=(String)request.getAttribute("recalc");
    	String all=(String)request.getAttribute("all");
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
    
     <html:form action="/FrontCounter/InterestCalc?pageId=3016"  >
     	<%System.out.println("At 63 inside InterestCalc.jsp"); %>
     	<html:hidden property="pageId" value="3016" />
     	<html:hidden property="cal"/>
     	<html:hidden property="recal"/>
     	<div id = "div1" class = "myLayersClass">
     	<core:if test="<%=msg!=null %>">
     	<font color="red"><%=msg %></font>
     	</core:if>
     	</div>
     	<br><br><br><br>
     	<table border="1" width="550" height="90" style="border:thin solid navy" class="txtTable">
	<tr>
		<td height="41" width="173"><html:radio property="rad" value="account"></html:radio> Selected Account</td>
		<td height="41" width="223"><html:radio property="rad" value="all"></html:radio>All Account's</td>
		<td height="41" width="132">&nbsp;</td>
	</tr>
	<tr>
		<td height="41" width="173">Account Type:</td>
		<td height="41" width="223">
<html:select property="acType" styleClass="formTextFieldWithoutTransparent" onblur="Checksubmit()">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
</td>
		<td height="41" width="132">&nbsp;</td>
	</tr></table>
	<core:if test="<%=all!=null%>">
	<core:if test="<%=all.equals("yes")%>">
	<br><br>
    <table>
 	<tr>
		<td height="38" width="173">Account No.:</td>
		<td height="38" width="223"><html:text property="acno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onblur="submit()" onkeyup="numberlimit(this,'10')"></html:text></td>
		<td height="38" width="132">&nbsp;</td>
	</tr>
	
	</table>
	</core:if></core:if>
	<table>
	<tr>
		<td height="37" width="173">Minimum Balance:</td>
		<td height="37" width="223"><html:text property="minibal" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
		<td height="37" width="132">&nbsp;</td>
	</tr>
	<tr>
		<td height="38" width="238" colspan="2">Date upto which interest is calculated:</td>
		<td height="38" width="296"><html:text property="todate" disabled="true"></html:text></td>
	</tr>
	<tr>
		<td height="35" width="238" colspan="2">Next Date to calculate Interest:</td>
		<td height="35" width="296"><html:text property="nextdate" disabled="true"></html:text></td>
	</tr>
	
	<tr>
	<core:if test="<%=request.getAttribute("enable")!=null%>">
		<td height="35" width="173">
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
		<html:submit value="Calculate" onclick="calculate()" styleClass="ButtonBackgroundImage"></html:submit>
		<%}else{ %>
		<html:submit value="Calculate" onclick="calculate()" styleClass="ButtonBackgroundImage" disabled="true"></html:submit>
		<%} %>
		</td>
		
		<td height="35" width="296">
		<core:if test="<%=recalc!=null %>">
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
		<html:submit value="Recalculate" onclick="recalculate()" styleClass="ButtonBackgroundImage"></html:submit>
		<%}else{ %>
		<html:submit value="Recalculate" onclick="recalculate()" styleClass="ButtonBackgroundImage" disabled="true"></html:submit>
		<%} %>
		</core:if>
		</td>
		</core:if>
		<td height="35" width="60"><html:button property="rest" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button></td>
	</tr>
	
</table>
          
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