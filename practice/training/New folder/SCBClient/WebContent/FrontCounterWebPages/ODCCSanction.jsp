
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page import="masterObject.loansOnDeposit.LoanPurposeObject" %>
<%@page import="java.util.Map"%>
<%@page import="masterObject.frontCounter.ODCCMasterObject"%>
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
<h2 class="h2">OD/CC Sanctioning</h2></center></font>
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
    var entryval;
    entryval=confirm("Are you sure you want to sanction the loan");
    
    if(entryval==true)
            	{
            
           		 	
           			 document.forms[0].hidval.value='submit';
           			 document.forms[0].submit();
           			
           			 
           			
         		}
          	else 
          		{
          			
          				return false;
          		}
          
   
    }
    
    function setverify(){
    var entryval;
    entryval=confirm("Do You want to Verify Loan Sacntion");
    
    if(entryval==true)
            	{
            
           		 	
           			 document.forms[0].hidval.value='submit';
    document.forms[0].verifyhid.value='verify';
           			 document.forms[0].submit();
           			
           			 
           			
         		}
          	else 
          		{
          			
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
    
    }
    show(false,'div1');
    document.forms[0].creditlimit.value='0.00';
   document.forms[0].limit.value='0';
    
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
 if(document.forms[0].acc_no.value==""){
 alert("Please Enter The Account No.");
  document.forms[0].acc_no.focus();
 return false;
 }
 else if(document.forms[0].share_no.value==""){
 alert("Please Enter The Share Account No.");
  document.forms[0].share_no.focus();
 return false;
 }
 else if(document.forms[0].creditlimit.value==""||document.forms[0].creditlimit.value=='0.00'){
 alert("Please Enter Credit Limit");
 document.forms[0].creditlimit.focus();
 return false;
 }
 else if(document.forms[0].limit.value==""||document.forms[0].limit.value=='0'){
 alert("Please Enter Limit Upto");
 document.forms[0].limit.focus();
 return false;
 }
 else if(document.forms[0].interestrate.value==""){
 alert("Interest Rate Not Known!");
 document.forms[0].interestrate.focus();
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
         String access;
         String  accesspageId;
          Map user_role;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	String verified=(String)request.getAttribute("verified");
    	LoanPurposeObject[] loanpurposeobject=(LoanPurposeObject[])request.getAttribute("purpose");
    	request.setAttribute("purpose",loanpurposeobject);
    	ODCCMasterObject odccmaster=(ODCCMasterObject)request.getAttribute("odccmaster");
    	session.setAttribute("odccmaster",odccmaster);
    	String odaccount=(String)request.getAttribute("odaccount");
    	String accesspageId=(String)request.getAttribute("accesspageId");
    	user_role=(Map)request.getAttribute("user_role");
    	cmObject=(CustomerMasterObject)request.getAttribute("cust");
    	
    %>
  <%

  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");

if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In OD/CCSanction------>"+access);
			}

			
			}
else{
	System.out.println("access--is null---In OD/CCSanction------>");
}
// Note:access=1111==>read,insert,delete,update
%>
<core:if test="<%=access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1' %>">
    <div id = "div1" class = "myLayersClass">
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=closingmsg%></font>
    </core:if>
    </div>
   
    <table>
    <tr><td>
     <html:form action="/FrontCounter/OdccSanction?pageId=3020" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="hidval" />
     	<html:hidden property="verifyhid" />
     	<div id="tabs">
     	</div>
     	<br><br><br>
          
	<table border="1" width="516" height="172" class="txtTable">
		<tr>
			<td>Account Type:</td>
			<td><html:select property="acType" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select></td>
			<td>AccountNo:</td>
			<td>
			<html:text property="acc_no" onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text>
			</td>
		<tr>
			<td>Share Type No:</td>
			<td>
			<html:select property="cobo_sh_type" styleClass="formTextFieldWithoutTransparent">
                        
                        <core:forEach var="sh" varStatus="count" items="${requestScope.sh}" >
                        	    <html:option value="${sh.moduleCode}" ><core:out value="${sh.moduleAbbrv}-${sh.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
			
			</td>
			<td>Share No:</td>
			<td>
			<html:text property="share_no" readonly="true"></html:text>
			
			
			
			
			</td>
		</tr>
		<tr>
			<td>Details :</td>
			<td>
			<html:select property="details" onblur="submit()">
		<html:option value="SELECT">SELECT</html:option>
		<html:option value="PersonalDetails">PersonalDetails</html:option>
		<!--<html:option value="Loan&ShareDetails">Loan&ShareDetails</html:option>
		--><html:option value="ReceiptDetails">ReceiptDetails</html:option>
		<html:option value="Application">Application</html:option>
		
		
		<html:option value="SignatureInst">SignatureInst</html:option>
		
		<html:option value="EmploymentDetails">EmploymentDetails</html:option>
		
		<html:option value="DepositDetails">DepositDetails</html:option>
		
		
		</html:select>
			</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>

		</tr>
		</table>
	<p align="center"><b><font size="5">Sanction Details</b></p>
	<table border="1" width="426" height="112" class="txtTable">
		<tr>
			<td>Credit Limit :</td>
			<td>
			<html:text property="creditlimit" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text>
			</td>
		</tr>
		<tr>
			<td>Limit Upto(In Months):</td>
			<td>
			<html:text property="limit" onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'3')" ></html:text>
			</td>
		</tr>
		<tr>
			<td>Interest Rate :</td>
			<td>
			<html:text property="interestrate" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" readonly="true"></html:text>
			</td>
		</tr>
	</table>
	
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>
	<core:if test="<%=verified==null %>">
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<html:button property="B1" value="Submit" onclick="setval()" styleClass="ButtonBackgroundImage"></html:button>
	<%}else{ %>
	<html:button property="B1" value="Submit" onclick="setval()" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
	<%} %>
	</core:if>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<html:button property="but" value="Verify" onclick="setverify()" styleClass="ButtonBackgroundImage"></html:button>
	<%}else{ %>
	<html:button property="but" value="Verify" onclick="setverify()" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
	<%} %>
<html:button  property="clear" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button>
</p>
   </html:form>
   </td><td>
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
            </core:if>
     </body>
     </html>          
     