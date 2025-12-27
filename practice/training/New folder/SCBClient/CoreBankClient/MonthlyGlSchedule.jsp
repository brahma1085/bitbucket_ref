<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@page import="java.util.Map"%>
<%@taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>   
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>MonthlyGlSchedule</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Monthly GL Schedule</center></h2>
      <hr>
      
    
    <script type="text/javascript">
     function set(target){
       document.forms[0].forward.value=target
      };
      
      function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
     
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
<%String result=(String)request.getAttribute("result");
String[][] comboCodeArrayMonthly=(String[][])request.getAttribute("MonthlyGlNo");
if(comboCodeArrayMonthly!=null){
System.out.println("===============Lenght=============>>>>>>>>>>>>>>>"+comboCodeArrayMonthly.length);
}
String msgType=(String)request.getAttribute("StringType");
String msgCode=(String)request.getAttribute("StringCode");

Object[][] mthglschObj=(Object[][])request.getAttribute("monthlyglschObj");
Object[][] mthglschObj1=(Object[][])request.getAttribute("monthlyglschObj1");
String[][] mthglschReport=(String[][])request.getAttribute("monthlyglschReport");
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
<html:form action="/GL/GLMonthlyGlSchedule?pageId=12012">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
 <input type="hidden" name="fname" />
   <tr>
    <td><b>Year</b>    
      <html:select property="year" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
      <%
      for(int i=1980;i<=2030;i++){
      
      %>
      <html:option value="<%=""+i %>"><%=i %></html:option>
      <%}%>
      </html:select>
      <b>Month</b>    
      <html:select property="month" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
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
   <b>From GL Type & Code:</b><html:select property="types" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <html:option value="alltypes">All Types</html:option>
      <c:if test="${requestScope.moduleobj!=null}">
     <c:forEach var="modules" varStatus="count" items="${requestScope.moduleobj}" >
                        	    <html:option value="${modules.moduleAbbrv}" ><c:out value="${modules.moduleAbbrv}"></c:out></html:option>
                        </c:forEach>
                        </c:if>
     
     
   </html:select>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <html:select property="codes" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <html:option value="allcodes">All Codes</html:option>
     <%      for(int i=0;i<comboCodeArrayMonthly.length;i++)
                            {
                                if(!comboCodeArrayMonthly[i][1].endsWith("000"))
                                {%>
                                <html:option value="<%=comboCodeArrayMonthly[i][1] %>" ><%=comboCodeArrayMonthly[i][1] %></html:option>
                                   <%
                                }
                            }
     
     %>      
     
     
     </html:select>
     <%
   if(msgType!=null)
   {
   %>
   <font color='red'><%=msgType %></font>
   <%} %>
   </td>
   
   
   
   </tr>
   
   <tr>
   <td>
   <b>To GL Type & Code:</b><html:select property="toTypes" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <html:option value="alltypes">All Types</html:option>
      <c:if test="${requestScope.moduleobj!=null}">
     <c:forEach var="modules" varStatus="count" items="${requestScope.moduleobj}" >
                        	    <html:option value="${modules.moduleAbbrv}" ><c:out value="${modules.moduleAbbrv}"></c:out></html:option>
                        </c:forEach>
                        </c:if>
     
    
     
   </html:select>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <html:select property="toCodes" onblur="setFlag('to_glno')" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <html:option value="allcodes">All Codes</html:option>
     
     <%      for(int i=0;i<comboCodeArrayMonthly.length;i++)
                            {
                                if(!comboCodeArrayMonthly[i][1].endsWith("000"))
                                {%>
                                <html:option value="<%=comboCodeArrayMonthly[i][1] %>" ><%=comboCodeArrayMonthly[i][1] %></html:option>
                                   <%
                                }
                            }
     
     %>      
     
       </html:select>
       <%
   if(msgCode!=null)
   {
   %>
   <font color='red'><%=msgCode %></font>
   <%} %>
   </td>
   </tr>
   
   <tr>
     <td>
        <html:button property="view"  onclick="setFlag('view'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message> </html:button>
        <html:button property="saveFile"  onclick="setValue('file')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.file"></bean:message></html:button>
        <html:button  property="printFile" onclick="window.print()" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button>
        <html:button property="clear"  onclick="setFlag('clear'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message></html:button>
         <html:button property="download"  onclick="setFlag('save'); " styleClass="ButtonBackgroundImage">DownLoad</html:button>
     </td>
   </tr>
   
 </table>
 <div id="mthglschedule" style="display:block;overflow:scroll;width:700px;height:250px">
 <table border="1" bordercolor="blue">
 <tr><td colspan="3" align="center"><b>Account Details</b></td><td ><b>Transaction</b></td><td colspan="2" align="center"><b> Amount</b></td><td ><b>Balance</b></td></tr>
 <tr>
 <td><b>GL Type</b></td>
 <td><b>GL Code</b></td>
 <td><b>GL Name</b></td>
 <td><b>Date</b></td>
 <td><b>Debit</b></td>
 <td><b>Credit</b></td>
 <td><b>Balance</b></td>
  </tr>
  <%
 if(mthglschObj!=null ){
	 System.out.println("----"+mthglschObj[0][6]);
	 for(int j=0;j<mthglschObj.length;j++){
	 if(count!=7){%>
	
	 <tr bordercolor="none">
	 <%for(int k=0;k<7;k++){
		 if(mthglschObj[j][k]!=null){
%> 
  
 
 <td bordercolor="none" ><%=mthglschObj[j][k]%>
 </td>

 <%}else
	 count++;
		 }%>
 
 </tr>
 
 <%}
	 }
 %>
 
  
 <%
	 }
	 %>
 <%
 if(mthglschObj1!=null ){
	 for(int j=0;j<mthglschObj1.length;j++){%>
	
	 <tr bordercolor="none">
	 <%for(int k=0;k<7;k++){
		 if(mthglschObj1[j][k]!=null){
%> 
  
 
 <td bordercolor="none" ><%=mthglschObj1[j][k]%>
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