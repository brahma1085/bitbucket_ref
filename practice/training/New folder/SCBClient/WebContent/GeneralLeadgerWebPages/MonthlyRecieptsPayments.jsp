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
<head><title>Reciepts And Payments</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Reciepts And Payments</center></h2>
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
     
     function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
     function setValue(flagValue){
     var val=prompt("Enter the file name without extension(it will save in .xls format)");
     document.forms[0].fname.value=val;
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%!
int count=0;
%>
<%
System.out.println("===================Hi=========================");
String result=(String)request.getAttribute("result"); 
Object[][] rpObj=(Object[][])request.getAttribute("rpdataObj");
Object[][] rpObj1=(Object[][])request.getAttribute("rpdataObj1");
String msg=(String)request.getAttribute("msg");
%>

<%if(msg!=null){ %>
  <font color="red"><%=msg %></font>
  <br><br>
<%} %>
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
<html:form action="/GL/GLMonthlyRecieptsPayments?pageId=12016">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
  <input type="hidden" name="fname" />
   <tr>
    <td><b>From Year</b>    
      <html:select property="fromYear" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <%
      for(int i=1980;i<=2030;i++){
      
      %>
      <html:option value="<%=""+i %>"><%=i %></html:option>
      <%}%>
      </html:select>
      <b>From Month</b>    
      <html:select property="fromMonth" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
       <%
      for(int i=01;i<=12;i++){
		   
		   %>	
		    <html:option value="<%=""+i %>"> <%=i%></html:option>
		
		<%
		    }
		%>
      </html:select>
      
    </td>
    
   </tr>
   <tr>
    <td><b>To Year</b>    
      <html:select property="toYear" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
      <%
      for(int i=1980;i<=2030;i++){
      
      %>
      <html:option value="<%=""+i %>"><%=i %></html:option>
      <%}%>
      </html:select>
      <b>To Month</b>    
      <html:select property="toMonth" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
       <%
      for(int i=01;i<=12;i++){
		   
		   %>	
		    <html:option value="<%=""+i %>"> <%=i%></html:option>
		
		<%
		    }
		%>
      </html:select>
      
    </td>
    
   </tr>
   
   <tr>
     <td>
        <html:button property="view"  onclick="setFlag('view'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message></html:button>
        <html:button property="saveFile"  onclick="setValue('file')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.file"></bean:message> </html:button>
        <html:button  property="printFile" onclick="window.print() " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button>
        <html:button property="clear"  onclick="setFlag('clear'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:button>
        <html:button property="save"  onclick="setFlag('save'); " styleClass="ButtonBackgroundImage">DownLoad</html:button>
     </td>
   </tr>
   
 </table>
 <div id="monthlyRP" style="display:block;overflow:scroll;width:700px;height:250px">
 <table border="1" bordercolor="blue">
 <tr><td  align="center"><b>Serial No</b></td><td colspan="3" align="center"><b>Account Details</b></td><td  align="center"><b>Receipts</b></td><td  align="center"><b>Payments</b></td></tr>
 <tr>
 <td align="center"><b>Serial No</b></td>
 <td align="center"><b>GL Type</b></td>
 <td align="center"><b>GL Code</b></td>
 <td align="center"><b>Name</b></td>
 <td align="center"><b>Reciepts</b></td>
 <td align="center"><b>Payments</b></td>
 
  </tr>
  <%
 if(rpObj1!=null ){
	 for(int j=0;j<1;j++){%>
	
	 <tr bordercolor="none">
	 <%for(int k=0;k<6;k++){
		 if(rpObj1[j][k]!=null){
%>
  
 
 <td bordercolor="none"><%=rpObj1[j][k]%>
 </td>

 <%}
		 }%>
 
 
 </tr>
 
 <%}%>
 
  
 <%
	 }
	 %>
 
  <%
 if(rpObj!=null ){
	 for(int j=0;j<rpObj.length;j++){
	 if(count!=6){%>
	
	 <tr bordercolor="none">
	 <%for(int k=0;k<6;k++){
		 if(rpObj[j][k]!=null){
%>
  
 
 <td bordercolor="none"><%=rpObj[j][k]%>
 </td>

 <%}else
	 count++;
		 }%>
 
 
 </tr>
 
 <%}
 }%>
 
  
 <%
	 }
	 %>
 <%
 if(rpObj1!=null ){
	 for(int j=1;j<rpObj1.length;j++){%>
	
	 <tr bordercolor="none">
	 <%for(int k=0;k<6;k++){
		 if(rpObj1[j][k]!=null){
%>
  
 
 <td bordercolor="none"><%=rpObj1[j][k]%>
 </td>

 <%}
		 }%>
 
 
 </tr>
 
 <%}%>
 
  
 <%
	 }
	 %>
 
 
  
 </table>
 </div>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>