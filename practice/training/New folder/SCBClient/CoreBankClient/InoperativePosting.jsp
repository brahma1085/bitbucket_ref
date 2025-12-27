
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
<h2 class="h2">Inoperative Posting</h2></center></font>
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
    var a=checkAll();
    if(a){
    document.forms[0].hidval.value='submit';
    document.forms[0].submit();
    }
    else{
     document.forms[0].hidval.value='';
    
    }
    
    }
     function setvals(target){
    
    document.forms[0].hidval.value=target;
    document.forms[0].submit();
    
    
    }
    
    function putvals(){
    var a=document.forms[0].acType.value;
    var b=document.forms[0].actyp.value;
    alert("a====>"+a);
    if(a!="SELECT"){
    alert("a==---------------==>"+a);
   	b=a;
      alert("actyp---------->"+b);
    }
    else{
    
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
if(ele[i].type=="checkbox"){
if(ele[i].checked){

}
else{
alert("Please select Accounts to be made inoperative");
return false;
}

}

}
return true;
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
    	String actypvalue=(String)request.getAttribute("actypvalue");
    	AccountMasterObject[] inopdata=(AccountMasterObject[])request.getAttribute("passvalues");
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
    
         <html:form action="/FrontCounter/InOpProcess?pageId=3023" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="hidval" />
     	
     	<br><br><br>
          
	<table  border ="0" class="txtTable">
		<tr>
			<td>Account Type:</td>
			<td ><html:select property="acType" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select></td>
			</tr>
			</table>

	<p>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:button property="B2" value="View" onclick="setvals('view')" styleClass="ButtonBackgroundImage"></html:button>
	<%} else{%>
	<html:button property="B2" value="View" onclick="setvals('view')" styleClass="ButtonBackgroundImage"></html:button>
	<%} %>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<html:button property="B1" value="Submit" onclick="setvals('submit')" styleClass="ButtonBackgroundImage"></html:button>
	<%}else{ %>
	<html:button property="B1" value="Submit" onclick="setvals('submit')" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
	<%} %>
	<html:button property="B3" value="Clear" onclick="show(false,'div2');show(false,'div1');" styleClass="ButtonBackgroundImage"></html:button>
	
	</p>
	
	
<core:if test="<%=inopdata!=null %>">
<div style="display: block;overflow:scroll; height:300px; width:750px;" id="div2" class = "myLayersClass">
<table class="its"><tr bgcolor="#0099CC"><td>Select</td>
<td>Sno.</td>
<td>Ac_Type</td>
<td>Ac_no</td>
<td>Cid</td>
<td>Name</td>
<td>AcOpen_Dt</td>
<td>Last_trndt</td>
<td>Ac_status</td>
<td>Closig bal</td>
</tr>
<%for(int k=0;k<inopdata.length;k++) {%>
<tr>
<%int s=k+1;%>
<td><input type="checkbox" name="cbox" value="<%=""+k%>"></td>
<td><%= s%></td>
<td>
<core:if test="<%=actypvalue!=null%>">
<html:select property="actyp" styleClass="formTextFieldWithoutTransparent" value="<%=actypvalue%>" disabled="true">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>

</core:if>

</td>
<td><input type="text" name="acnum" value="<%=String.valueOf(inopdata[k].getAccNo())%>" readonly="true" size="7"></td>
<td><input type="text" name="cid" value="<%=String.valueOf(inopdata[k].getCid())%>" readonly="true" size="7"></td>
<td><input type="text" name="name" value="<%=inopdata[k].getAccName()%>" readonly="true"></td>
<td>
<%if(inopdata[k].getAccOpenDate()!=null) {%>
<input type="text" name="acopendt" value="<%=inopdata[k].getAccOpenDate()%>" readonly="true" size="7">
<%} %>
</td>
<td><input type="text" name="lasttrndt" value="<%=inopdata[k].getLastTrnDate()%>" readonly="true" size="7"></td>
<td><input type="text" name="acstatus" value="<%=inopdata[k].getAccStatus()%>" readonly="true" size="7"></td>
<td><input type="text" name="cl_bal" value="<%=String.valueOf(inopdata[k].getCloseBal())%>" readonly="true" size="7"></td>



</tr>

<%} %>
</table>
</div>
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