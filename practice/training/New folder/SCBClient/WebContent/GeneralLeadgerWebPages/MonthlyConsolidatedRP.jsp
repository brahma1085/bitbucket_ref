<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Monthly Consolidated Reciepts And Payments</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Consolidated Reciepts And Payments</center></h2>
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
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%String result=(String)request.getAttribute("result"); %>
<html:form action="/GL/GLMonthlyConsolidatedRP?pageId=12021">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
   <tr>
    <td><b>From Year</b>    
      <html:select property="fromYear">
      <%
      for(int i=1980;i<=2030;i++){
      
      %>
      <html:option value="<%=""+i %>"><%=i %></html:option>
      <%}%>
      </html:select>
      <b>From Month</b>    
      <html:select property="fromMonth">
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
      <html:select property="toYear">
      <%
      for(int i=1980;i<=2030;i++){
      
      %>
      <html:option value="<%=""+i %>"><%=i %></html:option>
      <%}%>
      </html:select>
      <b>To Month</b>    
      <html:select property="toMonth">
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
        <html:submit  onclick="set('submit'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message> </html:submit>
        <html:submit  onclick="set('file')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.file"></bean:message> </html:submit>
        <html:button  property="printFile" onclick="window.print()" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button>
        <html:submit  onclick="clear_fun(); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:submit>
     </td>
   </tr>
   
 </table>
 
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
 </table>
</html:form>
</body>
</html>