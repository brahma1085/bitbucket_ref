<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>   
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Form1 B & C Link </title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Form1 B & C Link</center></h2>
      <hr>
      
    
    <script type="text/javascript">
     function set(target){
       document.forms[0].forward.value=target
      };
     
      function check_acnt(){
      
        if(document.getElementById("ac").value=='T'){
          alert("Account not found")
           document.forms[0].acno.value="";
        }
      };
        
   function clear_fun()
    {
    	
    	var ele=document.forms[0].elements;
    	document.getElementById("forward").style.display='none';  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    
    function numbersonly(eve){
         var cha = event.keyCode
            if (  ( cha  > 47 && cha < 58  ) ) {
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
      };
     
     
     function validate(){
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         alert(val);
       } 
       
       else{
         return false;
       }
     }; 
     
     function setFlag(flagVal){
     document.forms[0].flag.value=flagVal;
     document.forms[0].submit();
     
     
     };
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%String result=(String)request.getAttribute("result");
String[][] codename=(String[][])request.getAttribute("Form1LinkCode");
String descNdtl=(String)request.getAttribute("DescriptionNdtl");
String descCrrReq=(String)request.getAttribute("DescriptionCrrReq");
String descCrrAct=(String)request.getAttribute("DescriptionCrrAct");
String descSlrReq=(String)request.getAttribute("DescriptionSlrReq");
String descSlrMaintain=(String)request.getAttribute("DescriptionSlrMaintain");
%>
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

// Note:access=1111==>read,insert,delete,update
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/GL/GLRBIForm1LinkUpdation?pageId=12027">
<html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 
   
   <tr>
     <td>
     <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
        <html:button property="sub"  onclick="setFlag('submit') " styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message></html:button>
        <%}else{ %>
        <html:button property="sub"  onclick="setFlag('submit') " styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.submit"></bean:message></html:button>
        <%} %>
        
        
        <html:button property="clearForm"   onclick="setFlag('clear')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message></html:button>
     </td>
   </tr>
   
 </table>
 
 <table border="1" bordercolor="blue">
 <tr><td  align="center"><b>Columns</b></td><td  align="center"><b>Code</b></td><td  align="center"><b>Description</b></td></tr>
 <tr>
 <td><b><html:text property="ndtl" value="NDTL" styleClass="formTextField"></html:text></b></td>
 <td><b><html:select property="ndtlCode" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue;">
 <%
 if(codename!=null){
	   	for(int i=0;i<codename.length;i++){%>
	   	<html:option value="<%=codename[i][0] %>"><%=codename[i][0]%></html:option>
	   		
	   	<%}
	   }
 
 
 %>
 
 </html:select></b></td>
 <td><b>
 <%
 if(descNdtl!=null){
 
 %>
 
 <html:text property="ndtlDesc" value="<%=descNdtl %>" size="30" styleClass="formTextField"></html:text>
 
 <%} %>
 </b></td>
 </tr>
 
 <tr>
 <td><b><html:text property="crrReq" value="CRR Required" styleClass="formTextField"></html:text></b></td>
 <td><b><html:select property="crrReqCode" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue;">
 <%
 if(codename!=null){
	   	for(int i=0;i<codename.length;i++){%>
	   	<html:option value="<%=codename[i][0] %>"><%=codename[i][0]%></html:option>
	   		
	   	<%}
	   }
 
 
 %>
 
 </html:select></b></td>
 <td><b>
 <%
 if(descCrrReq!=null){
 
 %>
 
 <html:text property="ndtlDesc" value="<%=descCrrReq %>" size="30" styleClass="formTextField"></html:text>
 <%} %>
 
 </b></td>
 </tr>
 
 <tr>
 <td><b><html:text property="crrActual" value="CRR Actually" styleClass="formTextField"></html:text></b></td>
 <td><b><html:select property="crrActCode" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue;">
 <%
 if(codename!=null){
	   	for(int i=0;i<codename.length;i++){%>
	   	<html:option value="<%=codename[i][0] %>"><%=codename[i][0]%></html:option>
	   		
	   	<%}
	   }
 
 
 %>
 
 </html:select></b></td>
 <td><b>
 <%
 if(descCrrAct!=null){
 
 %>
 
 <html:text property="ndtlDesc" value="<%=descCrrAct %>" size="30" styleClass="formTextField"></html:text>
 
 <%} %>
 
 
 </b></td>
 </tr>
 
 <tr>
 <td><b><html:text property="slrReq" value="SLR Required" styleClass="formTextField"></html:text></b></td>
 <td><b><html:select property="slrReqCode" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue;">
 <%
 if(codename!=null){
	   	for(int i=0;i<codename.length;i++){%>
	   	<html:option value="<%=codename[i][0] %>"><%=codename[i][0]%></html:option>
	   		
	   	<%}
	   }
 
 
 %>
 
 </html:select></b></td>
 <td><b>
 <%
 if(descSlrReq!=null){
 
 %>
 
 <html:text property="ndtlDesc" value="<%=descSlrReq %>" size="30" styleClass="formTextField"></html:text>
 
 <%} %>
 
 </b></td>
 </tr>
 
 <tr>
 <td><b><html:text property="slrMaintained" value="SLR Maintained" styleClass="formTextField"></html:text></b></td>
 <td><b><html:select property="slrMaintainedCode" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue;" onblur="setFlag('from_code')">
 <%
 if(codename!=null){
	   	for(int i=0;i<codename.length;i++){%>
	   	<html:option value="<%=codename[i][0] %>"><%=codename[i][0]%></html:option>
	   		
	   	<%}
	   }
 
 
 %>
 
 </html:select></b></td>
 <td><b>
 <%
 if(descSlrMaintain!=null){
 
 %>
 
 <html:text property="ndtlDesc" value="<%=descSlrMaintain %>" size="30" styleClass="formTextField"></html:text>

 <%} %>
 
 
 </b></td>
 </tr>
  
 </table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>