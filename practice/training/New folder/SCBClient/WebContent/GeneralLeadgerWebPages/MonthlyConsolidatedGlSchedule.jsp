<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Consolidated GL Schedule</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Monthly Consolidated GL Schedule</center></h2>
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
String[][] comboCodeArrayMonthly=(String[][])request.getAttribute("MonthlyGlNo");
if(comboCodeArrayMonthly!=null){
System.out.println("===============Lenght=============>>>>>>>>>>>>>>>"+comboCodeArrayMonthly.length);
}
String msgType=(String)request.getAttribute("StringType");
String msgCode=(String)request.getAttribute("StringCode");
%>
<html:form action="/GL/GLMonthlyConsolidatedGlSchedule?pageId=12019">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
   <tr>
    <td><b>Year</b>    
      <html:select property="year">
       <%
      for(int i=1980;i<=2030;i++){
      
      %>
      <html:option value="<%=""+i %>"><%=i %></html:option>
      <%}%>
      </html:select>
      <b>Month</b>    
      <html:select property="month">
       <%
      for(int i=01;i<=12;i++){
		   
		   %>	
		    <html:option value="<%=""+i %>"> <%=i%></html:option>
		
		<%
		    }
		%>
      </html:select>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <html:radio property="branchWise" value="BranchWise" >Branchwise</html:radio>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <html:radio property="branchWise" value="Consolidated">Consolidated</html:radio>
      
      
    </td>
    
   </tr>
   <tr>
   <td>
   <b>From GL Type/No:</b><html:select property="types">
     <html:option value="alltypes">All Types</html:option>
     <c:if test="${requestScope.moduleobj!=null}">
     <c:forEach var="modules" varStatus="count" items="${requestScope.moduleobj}" >
                        	    <html:option value="${modules.moduleAbbrv}" ><c:out value="${modules.moduleAbbrv}"></c:out></html:option>
                        </c:forEach>
                        </c:if>
     
   </html:select>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <html:select property="codes">
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
   <b>To GL Type/No:</b><html:select property="toTypes">
     <html:option value="alltypes">All Types</html:option>
     <c:if test="${requestScope.moduleobj!=null}">
     <c:forEach var="modules" varStatus="count" items="${requestScope.moduleobj}" >
                        	    <html:option value="${modules.moduleAbbrv}" ><c:out value="${modules.moduleAbbrv}"></c:out></html:option>
                        </c:forEach>
                        </c:if>
     
   </html:select>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <html:select property="toCodes" onblur="setFlag('to_glno')">
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
        <html:submit  onclick="setFlag('view'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message> </html:submit>
        <html:submit  onclick="set('file')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.file"></bean:message> </html:submit>
        <html:button  property="printFile" onclick="window.print()" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button>
        <html:submit  onclick="setFlag('clear') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:submit>
     </td>
   </tr>
   
 </table>
 
 <table border="1" bordercolor="blue">
 <tr><td colspan="3" align="center"><b>Account Details</b></td><td ><b>Transaction</b></td><td colspan="2" align="center"><b> Amount</b></td><td ><b>Balance</b></td></tr>
 <tr>
 <td><b>GL Type</b></td>
 <td><b>GL No</b></td>
 <td><b>GL Name</b></td>
 <td><b>Date</b></td>
 <td><b>Debit</b></td>
 <td><b>Credit</b></td>
 <td><b>Amount</b></td>
  </tr>
 </table>
</html:form>
</body>
</html>