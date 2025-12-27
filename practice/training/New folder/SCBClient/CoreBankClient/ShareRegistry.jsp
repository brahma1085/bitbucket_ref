<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareReportObject"%>
<html>
<head><title>Share Registry</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Share Registry</center></h2>
      <hr>
      
    
    <script type="text/javascript">
     
     function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
     
     function set(target){
       document.forms[0].forward.value=target
        };
     																																					
     
           function validfn(fnm,str){
             var len=fnm.length;
             if(len==0){
               alert("Enter the "+str )
             }
           };
     
      function numbersonly(eve){
         var cha = event.keyCode


            if (  ( cha  > 46 && cha < 58  ) ) {


              
                return true ;
                
            }
            else{
              alert("Alphabets are not allowed,Please enter numbers only ");
              return false ;
            }
            
        
      };
     
    </script>


  </head>

<body class="Mainbody">
<%!
   String date;
   ShareReportObject[] shreport;
%>

<%
   date=(String)request.getAttribute("date");
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
<html:form action="/Share/ShareRegistry?pageId=4025">
   <center>
 <table class="txtTable">
 
 <tr>
    <td><bean:message key="label.frm_dt"></bean:message>
     <html:text property="frm_dt" styleClass="formTextFieldWithoutTransparent" size="10"  onkeypress=" return numbersonly()" onkeyup="numberlimit(this,'11')"></html:text></td>
 
     <td><bean:message key="label.to_dt"></bean:message> 
     <html:text property="to_dt" styleClass="formTextFieldWithoutTransparent"  size="10"  onkeypress=" return numbersonly()" onkeyup="numberlimit(this,'11')"></html:text></td> 
  </tr>
 

  <tr>
    <html:hidden property="forward" value="error"></html:hidden>
    <td>
    <html:submit  onclick="set('view')" styleClass="ButtonBackgroundImage"><bean:message key="label.view1"></bean:message> </html:submit>

    <html:submit onclick="set('clear')" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:submit>   
 	 <html:submit onclick="set('save')" styleClass="ButtonBackgroundImage">DownLoad</html:submit>
 <!--<html:button property="printfile" onclick="window.print()" styleClass="ButtonBackgroundImage" value="Print"></html:button>
    --></td>
  </tr>

  </table>
</center>
<hr>
 <table align="left">
<tr>
<td>
    <%if(shreport!=null){ %>
     <jsp:include page="/ViewRegistry.jsp"></jsp:include>
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