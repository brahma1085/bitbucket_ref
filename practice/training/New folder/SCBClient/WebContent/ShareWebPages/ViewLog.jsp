<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="java.util.Map"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="masterObject.share.ShareMasterObject"%>
<html>
<head><title>Share Master Log</title>
       <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Share Master Log</center></h2>
      <hr>
      
    
<script type="text/javascript">
  function numbersonly(eve){
         var cha = event.keyCode


            if (  ( cha  >= 47 && cha < 58  ) ) {


              
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
         
        
      };
       function set(target){
       document.forms[0].forward.value=target
      };
      
      function fun_validate(){
       var val=document.forms[0].validations.value;
       if(val!=0 || val!=""){
         alert(val); 
      
       } 
       else{
         return false;
       }
      };
</script>

 
  </head>
<body class="Mainbody" onload="fun_validate()">
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
<html:form action="/Share/ViewLog?pageId=4013">
<html:hidden property="validations"></html:hidden>
<html:hidden property="forward"></html:hidden>
<center>
 <table class="txtTable">
   <tr>
    <td><bean:message key="label.acType"></bean:message></td>
     <td>
      <html:select property="actype" styleClass="formTextFieldWithoutTransparent">
       <html:option value="1001001">SH</html:option>
      </html:select>
     </td>
     
     <td></td>
     <td><bean:message key="label.acNum"></bean:message>
       <html:text property="shnum" styleClass="formTextFieldWithoutTransparent"  size="6"  onchange="submit()" onkeypress="return numbersonly()"></html:text></td>
   </tr>

</table>
 </center>
 <tr>
 <td><center><html:submit onclick="set('save')" styleClass="ButtonBackgroundImage">DownLoad</html:submit></center></td>
 </tr>

<table class="txtTable">
<center>

  <%!
      ShareMasterObject[] shobj;
  %>
  <%
     shobj=(ShareMasterObject[])request.getAttribute("Viewlog1");
  %>
     <%if(shobj!=null){ %>
     
     <display:table name="Viewlog1" style="background-color:#CDCEAE">
      <display:column property="shareNumber" style="background-color:#ECEBD2"></display:column>
      <display:column property="mailingAddress" style="background-color:#ECEBD2"></display:column>
      <display:column property="divUptoDate" style="background-color:#ECEBD2"></display:column>
      <display:column property="issueDate" style="background-color:#ECEBD2"></display:column>
      <display:column property="closeDate" style="background-color:#ECEBD2"></display:column>
      <display:column property="loanAvailed" style="background-color:#ECEBD2"></display:column>
      <display:column property="payMode" style="background-color:#ECEBD2"></display:column>
      <display:column property="paymentAcctype" style="background-color:#ECEBD2"></display:column>
      <display:column property="paymentAccno" style="background-color:#ECEBD2"></display:column>
     </display:table>
     <%} %>
   
   </center> 
</table>     
 
 
 
    
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>
