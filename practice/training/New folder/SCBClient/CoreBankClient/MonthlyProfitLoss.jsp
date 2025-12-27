<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@page import="java.util.Map"%>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>  
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Profit And Loss</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Profit And Loss</center></h2>
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
<%String result=(String)request.getAttribute("result"); 
String date1=(String)request.getAttribute("PrevMthDate");
String date2=(String)request.getAttribute("PresentMthDate");
String[][] plReportObj=(String[][])request.getAttribute("MonthlyPLReportData");
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
<html:form action="/GL/GLMonthlyProfitLoss?pageId=12014">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
 <input type="hidden" name="fname" />
   <tr>
    <td><b>Compared with Year</b>    
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
    <td><b>Required for Year</b>    
      <html:select property="reqYear" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <%
      for(int i=1980;i<=2030;i++){
      
      %>
      <html:option value="<%=""+i %>"><%=i %></html:option>
      <%}%>
      </html:select>
      <b>Month</b>    
      <html:select property="reqMonth" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
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
 <div id="profitloss" style="display:block;overflow:scroll;width:700px;height:250px">
 <table border="1" bordercolor="blue">
 <tr><td colspan="5" align="center"><b>Expenses</b></td><td colspan="5" align="center"><b>Income</b></td></tr>
 <tr>
 <td><b>As On
 <%
 if(date1!=null){
 %>
 <font color="red"><%=date1%></font>
 <%
 }
 %>
 </b></td>
 <td><b>GL Type</b></td>
 <td><b>GL Code</b></td>
 <td><b>GL Name</b></td>
 <td><b>As On
 <%
 if(date2!=null){
 %>
 <font color="red"><%=date2%></font>
 <%
 }
 %>
 </b></td>
 <td><b>As On
 <%
 if(date1!=null){
 %>
 <font color="red"><%=date1%></font>
 <%
 }
 %>
 </b></td>
 <td><b>GL Type</b></td>
 <td><b>GL Code</b></td>
 <td><b>GL Name</b></td>
 <td><b>As On
 <%
 if(date2!=null){
 %>
 <font color="red"><%=date2%></font>
 <%
 }
 %>
 </b></td>
  </tr>
  
  <%
 if(plReportObj!=null ){
	 for(int j=0;j<plReportObj.length;j++){%><tr>
	 <%for(int k=0;k<10;k++){	 
%>
 
 <%if(plReportObj[j][k]!=null){ %>
 <td><%=plReportObj[j][k]%>
 </td>
 <%}else{%>
 <td>&nbsp;</td>
	 <%}%> 
	
 
 
 
 <%}%>
 </tr><%
	 }
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