
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
<h2 class="h2">CC Periodical Inspection</h2></center></font>
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
    
    function setvals(){
     
    document.forms[0].txt_actype.value='CASH CREDIT';
    }
    
    function callmethod(){
    
    document.forms[0].hidval.value='submitting';
    document.forms[0].submit();
    }
    
    function subvals1()
    {
	    var entry;
	    entry=confirm("you want to submit the data 1");
		    if(entry==true)
		    {
			     document.forms[0].subval.value='subvalues';
			     document.forms[0].submit();
		    }
    }
    
    
    function subvals(){
    var entryval;
    entryval=confirm("You want to submit the data");
    
    if(entryval==true)
            	{
            
           		 	var a=beforesubmiting();
           		 
           		 	  
           		if(a)
           			{
           			
    document.forms[0].subval.value='subvalues';
           			 document.forms[0].submit();
           			}
           			else{
           				
           			 document.forms[0].subval.value='subvalues';
           			 
           			 }
           			 
           			
         		}
          	else 
          		{
          			
          				return false;
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
 if(document.forms[0].ac_no.value==""){
 alert("Please Enter The Account No.");
  document.forms[0].ac_no.focus();
 return false;
 }
 else if(document.forms[0].sanction_purp.value==""){
 alert("Sanction Purpose No tdefined");
  
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
    <font color="red"><%=request.getAttribute("closingmsg")%>
    </core:if>
    </div>
    <html:form action="/FrontCounter/CCPeriod?pageId=3021" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="hidval" />
     	<html:hidden property="subval"/>
     	
     	<br><br><br>
          
<table border="1" width="680" height="294" class="txtTable">
	
	<tr>
		<td height="33" width="166">AccountType :</td>
		<td height="58" width="96"><html:select property="acType" styleClass="formTextFieldWithoutTransparent" onchange="setvals()">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select></td>
             	<td height="33" width="172">
             	<html:text property="txt_actype" style="background-color:#FFFFCC;" onkeypress="return false;" readonly="true"></html:text>
		</td>
		<td height="33" width="171">&nbsp;</td>
	</tr>
	<tr>
		<td height="38" width="166">AccountNo<b>:</b></td>
		<td height="38" width="143">
		
		<html:text property="ac_no" onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
		<td height="38" width="172">&nbsp;Sanction Purpose</td>
		<td height="38" width="171">
		
		<html:text property="sanction_purp" disabled="true" style="font-color:#FFFFCC;font-family:bold"></html:text></td>
	</tr>
	<tr>
		<td height="39" width="166">Nature Of Business</td>
		<td height="39" width="143">
		<html:text property="nature" disabled="true"></html:text>
		</td>
		<td height="39" width="172">Maturity Date</td>
		<td height="39" width="171">
		<html:text property="maturity_dt" disabled="true"></html:text>
		</td>
	</tr>
	<tr>
		<td height="38" width="166">Sanction Date</td>
		<td height="38" width="143">
		<html:text property="san_dt" disabled="true"></html:text></td>
		
		<td height="38" width="172">Account Status</td>
		<td height="38" width="171">
		<html:text property="ac_status" disabled="true"></html:text>
		</td>
	</tr>
	<tr>
		<td height="44" width="166">Sanctioned Limit</td>
		<td height="44" width="143">
		
		<html:text property="san_limit" disabled="true"></html:text></td>
		<td height="44" width="172">Previous Stock Value</td>
		<td height="44" width="171">
		<html:text property="prev_stock" disabled="true"></html:text>
		</td>
	</tr>
	<tr>
		<td height="39" width="166">Previous Inspection Date</td>
		<td height="39" width="143">
		<html:text property="pre_inspec" disabled="true"></html:text>
		
		<td height="39" width="172">&nbsp;</td>
		<td height="39" width="171">&nbsp;</td>
	</tr>
	<tr>
		<td height="44" width="166">Previous Credit Limit</td>
		<td height="44" width="143">
		<html:text property="prv_credit" disabled="true"></html:text>
		</td>
		<td height="44" width="172">&nbsp;</td>
		<td height="44" width="171">&nbsp;</td>
	</tr>
</table>


	<fieldset>
	<legend><b>InspectionDetails</b> </legend>
	<table border="1" width="590" height="154" class="txtTable">
		<tr>
			<td height="44" width="279">Value Of Stock :</td>
			<td height="44" width="295">
			<html:text property="stock_value" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onblur="callmethod()" onkeyup="numberlimit(this,'10')"></html:text>
		</td>
		</tr>
		<tr>
			<td height="51" width="279">Credit Limit :</td>
			<td height="51" width="295">
			<html:text property="credit_limit" onkeypress="return false;" readonly="true"></html:text>
		
		</tr>
		<tr>
			<td height="49" width="279">Next Inspection Date :</td>
			<td height="49" width="295">
			<html:text property="next_inspec" onkeypress="return false;" readonly="true"></html:text>
		
		</tr>
	</table>
	</fieldset><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</p>
	<p align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<html:button property="B1" value="Submit" onclick="subvals1()" styleClass="ButtonBackgroundImage"></html:button>
	<%}else{ %>
	<html:button property="B1" value="Submit" onclick="subvals1()" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
	<%} %>
	<html:button  property="clear" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button></p>



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