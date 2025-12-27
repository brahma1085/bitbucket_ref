
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page import="masterObject.frontCounter.PayOrderObject" %>
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
<%@page import="masterObject.frontCounter.PayOrderObject;"%>
<html>
  <head><title>AccountOpening</title>
      <font color="blue" ><center>
<h2 class="h2">PO Consolidation</h2></center></font>
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
    
    alert("submitting values");
    document.forms[0].hidval.value='submit';
    document.forms[0].submit();
    }
    function clearall(){
    alert("Resetting values");
    
    }
    
    function only_alpa()
	{
	var cha=event.keyCode;
	if((cha >= 97 && cha <= 122)||cha==32){
	return true;
	}else{
	return false;
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
    
    
    }
    show(false,'div1');
    show(false,'div2');
    document.forms[0].payorder.value='0';
   
    
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
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	PayOrderObject[] poconsol=(PayOrderObject[])request.getAttribute("poconsol");
    	String result=(String)request.getAttribute("result");
    	String disablesub=(String)request.getAttribute("disablesub");
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
    <font color="red"><%=closingmsg%>
    </core:if>
    <core:if test="<%=result!=null %>">
    <font color="red"><%=result %>
    </core:if>
    </div>
     <html:form action="/FrontCounter/PoCo?pageId=3024" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="hidval" />
     	<div id="tabs">
     	</div>
     	<br><br><br>
          
<table border="1" width="855" height="103" class="txtTable">
	<tr>
		<td height="97" width="116">Pay Order No:</td>
		<td height="97" width="159">
		<html:text property="payorder" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')" ></html:text> </td>
		<td height="97" width="114">PayChequeNo:</td>
		<td height="97" width="156">
		<html:text property="chequeno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onblur="submit()" onkeyup="numberlimit(this,'10')"></html:text> </td>
		<td height="97" width="124">Payee Name:</td>
		<td height="97" width="148">
		<html:text property="payeename" onkeypress="return only_alpa()"></html:text> </td>
	</tr>
</table>
<br><br><br><br>
<div  style="display:block;overflow:scroll;width:750px;height:300px" id="div2" class = "myLayersClass">

<core:if test="<%=poconsol!=null %>">

<table border="1">
<tr bgcolor="0099CC">
<td>SELECT</td>
<td>S No</td>
<td>PO Type</td>
<td>Date</td>
<td>A/c Ty/No</td>
<td>Gl Type</td>
<td>Gl code</td>
<td>Purchaser Name</td>
<td>Favour Name</td>
<td>Amount</td>

</tr>
<%for(int k=0;k<poconsol.length;k++){ %>
<tr>
<td><input type="checkbox" name="cbox" value="<%=""+k %>"></td>
<td><%=k %><input type="text" name="sno" value="<%=String.valueOf(poconsol[k].getPOSerialNo())%>" onkeypress="return false;"></td>
<td><input type="text" name="potype" value="<%=poconsol[k].getPOAccType() %>" onkeypress="return false;"></td>
<td><input type="text" name="date" value="<%=poconsol[k].getPOMakeDate()%>" onkeypress="return false;"></td>
<td><input type="text" name="actypno" value="<%=poconsol[k].getPOAccType()+" "+String.valueOf(poconsol[k].getPOAccNo()) %>" onkeypress="return false;"></td>
<td><input type="text" name="gltype" value="<%=poconsol[k].getPOGlType() %>" onkeypress="return false;"></td>
<td><input type="text" name="glcode" value="<%=String.valueOf(poconsol[k].getPOGlCode()) %>" onkeypress="return false;"></td>
<td><input type="text" name="purname" value="<%=poconsol[k].getPOPayee() %>" onkeypress="return false;"></td>
<td><input type="text" name="favour" value="<%=poconsol[k].getPOFavour() %>" onkeypress="return false;"></td>
<td><input type="text" name="amount" value="<%=String.valueOf(poconsol[k].getPOAmount()) %>" onkeypress="return false;"></td>
</tr>
<%} %>
</table>



</core:if>
</div>

	<p>
	<core:if test="<%=disablesub==null %>">
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<html:button property="Consol" value="Consolidate" onclick="setval()" styleClass="ButtonBackgroundImage"></html:button>
	<%}else{ %>
	<html:button property="Consol" value="Consolidate" onclick="setval()" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
	<%} %>
	</core:if>
<html:button property="rest" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button> </p>

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