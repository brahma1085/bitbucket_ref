<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareReportObject"%>
<html>
<head><title>Branch Wise Report</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Branch Wise Registry</center></h2>
      <hr>
          
    <script type="text/javascript">
     function set(target){
       document.forms[0].forward.value=target
        };
     																																					
     
           function validfn(fnm,str){
             var len=fnm.length;
             if(len==0){
               alert("Enter the "+str )
             }
           };
     
      
     
    </script>


  </head>

<body class="Mainbody">
<%!
  
   ShareReportObject[] shreport;
%>

<%
   
   shreport=(ShareReportObject[])request.getAttribute("Report");
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
<html:form action="/Share/ShareRegistrySum?pageId=4027">
   <center>
 <table class="txtTable">
 

  <tr>
    <html:hidden property="forward" value="error"></html:hidden>
    <td>
    <html:submit  onclick="set('view')" styleClass="ButtonBackgroundImage"><bean:message key="label.view1"></bean:message> </html:submit>
	<html:submit  onclick="set('save')" styleClass="ButtonBackgroundImage">DownLoad</html:submit>
    <html:submit onclick="set('clear')" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:submit>   
 
 <!--<html:button property="printfile" onclick="window.print()" styleClass="ButtonBackgroundImage" value="Print"></html:button>
   --></td>
  </tr>

  </table>
</center>
<hr>
 <table align="left" class="txtTable">
<tr>
<td>
    <%if(shreport!=null){ %>
     <jsp:include page="/BranchWiseView.jsp"></jsp:include>
 	<%
 	System.out.println("SURAJ IS HERE");
    }
     %>
 </td>
 </tr>
 </table>  
 </html:form>
 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>