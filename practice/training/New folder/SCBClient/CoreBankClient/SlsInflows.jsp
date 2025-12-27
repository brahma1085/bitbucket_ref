<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>   
<%--
  User: RaghunathaReddy.P
  Date: July 21-2010   
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.alm.ALMReportObject"%>
<html>
<head><title>SLSInflows</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>SLSInflows</center></h2>
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
<%
	String date1=(String)request.getAttribute("PrevMthDate");
ALMReportObject[] inflowsReportObj=(ALMReportObject[])request.getAttribute("InflowsReportData");
%>
<html:form action="/ALM/SlsInflows?pageId=15001">

 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden> 
   <tr>
    <td><b>Compared with Year:</b>    
      <html:select property="comparedYear" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <%
      for(int i=1980;i<=2030;i++){      
      %>
      <html:option value="<%=""+i %>"><%=i %></html:option>
      <%}%>
      </html:select>
      <b>Month</b>    
      <html:select property="comparedMonth" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
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
        <html:button property="clear"  onclick="serFlag('clear') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message></html:button>
     </td>
   </tr>
   
 </table>
 <div id="inflows" style="display:block;overflow:scroll;width:700px;height:250px">
 <%if(inflowsReportObj!=null) {%>
 	<table border="1" bordercolor="blue">
 		<tr>
 			<td colspan="4"><b>As On
 			<%
 				//if(date1!=null){
 			%>
 			<font color="red"></font>
 			<%
 				//}
 			%>
 				</b>
 			</td>
 			<td colspan="8" align="center"><b>Inflows</b></td> 			
 		</tr>	 	
 		
	</table>
	<%} %>
 </div>
</html:form>
</body>
</html>