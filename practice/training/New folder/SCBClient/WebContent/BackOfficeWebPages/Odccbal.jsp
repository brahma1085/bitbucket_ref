<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.backOffice.OdccObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>odccbalance</title>
<link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
<link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />

<script type="text/javascript">
   
    function set(target)
    {
  
    document.forms[0].forward.value=target;
    
    };
    
    function getmesg(){
  
    if(document.getElementById("valid").value!=null)
       {
       	if(document.getElementById("valid").value=="RecordsNotfound")
       	{
         	alert("Records Not Found");
        } 
       }     
  };
    
    function callClear(target){
           document.forms[0].forward.value=target;
         	 var ele= document.forms[0].elements;
             for(var i=0;i<ele.length;i++){
               if(ele[i].type=="text"){
                  
               ele[i].value="";
               }
           
           }
            };
            
       
    </script>
	

</head>
<% String displaymsg=(String)request.getAttribute("displaymsg");%>
<%if(displaymsg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=displaymsg %></font>
<%} %>
<body class="Mainbody" style="overflow: scroll;" onload="getmesg()">
<center><h2 class="h2">ODCC Balance</h2></center>

<%@page import="java.util.Map"%>


<%!
String access;
String  accesspageId;
 Map user_role;

%>

<%

  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");

if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/BackOffice/Odccbal?pageId=11017">

<center>
<table class="txtTable">	
 <tr>
 <td><bean:message key="label.acType"></bean:message>
 <html:select property="accountType">
			<%ModuleObject[] arraymodobj=(ModuleObject[])request.getAttribute("accType");
 				if(arraymodobj!=null){ 	
	              for(int i=0;i<arraymodobj.length;i++){               
 			%> 
				<html:option value="<%=""+arraymodobj[i].getModuleCode()%>" ><%=""+arraymodobj[i].getModuleAbbrv()%></html:option>
 			<%} }%>
</html:select>
 </td>
 </tr>
 </table>
 <br><br><br>
 <table class="txtTable">	
	<tr>
	    
	     <html:hidden property="forward" value="error"></html:hidden>
	   <td>
		  <html:submit  onclick="set('view')"  styleClass="ButtonBackgroundImage"><bean:message key="label.view1" ></bean:message> </html:submit>

           <!--<html:button onclick="window.print()" property ="printFile"  styleClass="ButtonBackgroundImage"><bean:message key="label.print"></bean:message> </html:button>-->
		  <html:submit onclick="set('clear')" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:submit>
			 <html:submit onclick="set('save')" styleClass="ButtonBackgroundImage">DownLoad</html:submit>
		</td>  
	</tr>
</table>
<br><br>
<div style="overflow:scroll;width:550px;height:150px">
   <% OdccObject[] odccObj=(OdccObject[])request.getAttribute("odcc");
     System.out.println("odccObj--->"+odccObj);%>
     
   <display:table name="odcc" style="background-color:#E4D5BE" requestURI="/BackOffice/Odccbal.do"  >
   
   <display:column title="A/c Type"  property="accountType" style="background-color:#f5dfc9" ></display:column>
   <display:column title="A/c No"  property="accountNo" style="background-color:#f5dfc9"></display:column>
   <display:column title="Name"  property="accountName" style="background-color:#f5dfc9"></display:column>
   <display:column title="Balance" property="accountBalance" style="background-color:#f5dfc9"></display:column>
   <display:column title="Date of A/c Opened" property="accountOpenDate" style="background-color:#f5dfc9"></display:column>
   <display:column title="Credit Limit" property="creditLimit"  style="background-color:#f5dfc9"></display:column>  

</display:table>

</div>

	
</center>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>