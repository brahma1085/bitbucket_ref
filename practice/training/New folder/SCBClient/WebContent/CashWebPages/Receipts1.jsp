<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ taglib prefix="html"  uri="http://struts.apache.org/tags-html" %>
<%@page import="java.util.Map"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html> 
<script type="text/javascript"> 
    function HideShow(AttributetoHide){ 
        if(document.getElementById){ 
 
            document.getElementById('amt10').style.display='none'; 
            document.getElementById('amt100').style.display='none'; 
            document.getElementById('amt10').disabled='disabled'; 
            document.getElementById('amt100').disabled='disabled'; 
            document.getElementById('txtShow'+AttributetoHide).style.display='block'; 
            document.getElementById('txtShow'+AttributetoHide).disabled=''; 
 
        } 
        else{ 
            alert('Sorry Browser does not support'); 
        } 
    } 
 
</script> 
 
<head><title>Simple jsp page</title></head> 
<body> 

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


<table align="center"border="2"> 
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/si"> 
    <tr> 
    <td>If SB Clickthis Checkbox<html:checkbox property="bankRunBalance" onchange="HideShow(11)"/><br></td> 
    <%--<td>If FD Clickthis Checkbox<html:checkbox property="prtotal" onchange="HideShow(2)"/><br></td>--%> 
    <td>if Select <html:select property="combo_option" onblur="HideShow(2)"> 
                  <html:option value="SB" ></html:option> 
                  <html:option value="FD" ></html:option> 
                  </html:select></td> 
    </tr> 
    <tr> 
          <td><html:text property="amt10" styleId="txtShow11" value="Details SB1"  style="display:none" size="12"/><br></td> 
    <%--<input type="text" value="Details SB2" id="txtShow12" name="txtShow3" 
           style="display:none" size="12"/><br>--%> 
 
 
    <td><html:text property="amt100" styleId="txtShow2" value="Details FD"  style="display:none" size="12"/><br></td> 
    </tr> 
 
</html:form> 
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
<%} %>
</table> 
</body> 
</html>