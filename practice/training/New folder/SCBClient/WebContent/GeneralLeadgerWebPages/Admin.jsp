<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>   
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.generalLedger.GLObject"%>
<html>
<head><title>GL Administration Screen</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>GL Administration Screen</center></h2>
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
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%String result=(String)request.getAttribute("result"); 
String[] tableCols=(String[])request.getAttribute("tableinfo");
String[] tableAddRow=(String[])request.getAttribute("tablecols");
String nameoftable=(String)request.getAttribute("nameOfTable");
Object[][] objArrayData=(Object[][])request.getAttribute("objectArrayData");


%>
<html:form action="/GL/GLAdmin?pageId=12024">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
  <html:hidden property="flag"></html:hidden>
   <tr>
    <td>Select Table:&nbsp;&nbsp;&nbsp;&nbsp;<html:select property="table" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue;">
    <html:option value="GLPost">GLPost</html:option>
    <html:option value="GLTransactionType">GLTransactionType</html:option>
    <html:option value="GLMaster">GLMaster</html:option>
    <html:option value="GLKeyParam">GLKeyParam</html:option>
    <html:option value="Modules">Modules</html:option>
    <html:option value="DailyStatus">DailyStatus</html:option>
    <html:option value="MonthlyStatus">MonthlyStatus</html:option>
    <html:option value="DailyConStatus">DailyConStatus</html:option>
    <html:option value="MonthlyConStatus">MonthlyConStatus</html:option>
    <html:option value="YearlyStatus">YearlyStatus</html:option>
    </html:select>
    </td>
   </tr>
   
   <tr>
     <td>
        <html:button  property="view" onclick="setFlag('view'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message></html:button>
        <html:button  property="addRow" onclick="setFlag('addRow'); " styleClass="ButtonBackgroundImage"><bean:message key="label.add.row"></bean:message> </html:button>
        <!--<html:button  property="update" onclick="set('update'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.update"></bean:message> </html:button>
        --><html:button  property="insert" onclick="setFlag('insert'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.insert"></bean:message> </html:button>
        <!--<html:button  property="delete" onclick="set('delete'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.delete"></bean:message> </html:button>
        --><html:button  property="clear" onclick="setFlag('clear')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:button>
     </td>
   </tr>
 </table>
 <%
 if(tableCols!=null){
 %>
 <div id="gl_admin" style="display:block;overflow:scroll;width:700px;height:250px">
 <table border=1 bordercolor="blue">
 <thead><b><font color="red">
 <%
 if(nameoftable!=null){
 %>
 <%=nameoftable %>
 <%} %></font></b></thead>
 <tr>
 <%
 if(tableCols!=null){
 for(int i=0;i<tableCols.length;i++){
	 System.out.println("====Array items===="+tableCols[i]);
 %>
  
   <td><b>
   <%=tableCols[i].toString() %>
   </b>
   </td>
   <%}
 }
 %>
 </tr>
 
 <%
 
 if(objArrayData!=null ){
	 for(int j=0;j<objArrayData.length;j++){%><tr>
	 <%for(int k=0;k<tableCols.length;k++){	 
%>
 
 
 <td><%=objArrayData[j][k]%>
 </td>
 <%} %>
 
 
 
 <%}%>
 </tr><%
	 }
	 %>
 
  </table>
  </div>
  <%} %>
  <%
  if(tableAddRow!=null){
  %>
  <table border=1 bordercolor="blue">
 <thead><b><font color="red">
 <%
 if(nameoftable!=null){
 %>
 <%=nameoftable %>
 <%} %></font></b></thead>
 <tr>
 <%
 
 for(int i=0;i<tableAddRow.length;i++){
	 
 %>
 
   <td><b>
   <%=tableAddRow[i].toString() %>
   </b>
   </td>
   <%}
  
 %>
 </tr>
 <tr>
 <%
 
 for(int i=0;i<tableAddRow.length;i++){
	 
 %>
 
   <td>
   <input type="text" name="txtFld" >
   
   </td>
   <%}
  
 %>
 </tr>
 </table>
 <%} %> 
  
  
  
</html:form>
</body>
</html>
