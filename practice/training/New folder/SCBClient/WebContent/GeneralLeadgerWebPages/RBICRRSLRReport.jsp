<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>   
<%@page import="java.util.Map"%>
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Calendar"%>
<html>
<head><title>CRR SLR REPORT</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>CRR & SLR Report</center></h2>
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
<%!
String yy=null;
String mm=null;
%>
<%String result=(String)request.getAttribute("result");
String yymm=(String)request.getAttribute("yearmonth");

String[][] tabledata=(String[][])request.getAttribute("CrrSlrtabledata");

if(yymm!=null){
	if(yymm.length()==7){
yy=(String)yymm.substring(3);
 mm=(String)yymm.substring(0,2);
 System.out.println("7===============yy"+yy);
 System.out.println("7===============yy"+mm);
	}else
	{
		yy=(String)yymm.substring(2);
		 mm=(String)yymm.substring(0,1);
		 System.out.println("7======else=========yy"+yy);
		 System.out.println("7==========else=====yy"+mm);
	}
}
System.out.println("value of yymm====="+yymm);
System.out.println("value of yy====="+yy);
System.out.println("value of mm====="+mm);

Calendar cal=Calendar.getInstance();
int index=0;
String day="Dummy";
String dummy_data="aaa";

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
<html:form action="/GL/GLRBICRRSLRReport?pageId=12029">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
   <tr>
    <td><b>Year</b>    
      <html:select property="year" styleClass="formTextFieldWithoutTransparent">
     <%
      for(int i=1980;i<=2030;i++){
      
      %>
      <html:option value="<%=""+i %>"><%=i %></html:option>
      <%}%>
      </html:select>
      <b>Month</b>    
      <html:select property="month" styleClass="formTextFieldWithoutTransparent">
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
        <html:button property="view" onclick="setFlag('view'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message> </html:button>
        <!--<html:submit  onclick="set('file')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.file"></bean:message> </html:submit>
        --><html:button  property="printFile" onclick="window.print() " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button>
        <html:button property="clearForm"  onclick="setFlag('clear'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:button>
     </td>
   </tr>
   
 </table>
 
 
<%
if(yymm!=null ){
	System.out.println("changed value of mm====="+yymm.charAt(0));
	System.out.println("changed value of mm====="+yymm.substring(2));
%>
<div id="rbi_crrslr" style="display:block;overflow:scroll;width:700px;height:250px">
<table border="1" bordercolor="blue">
<tr>
<td>RBI</td>
<td>RBI</td>
<%
for(int i=1;i<32;i++){
	%>
<td><%=i %>/<%=yymm %></td>
<%} %>				

</tr>
<tr>
<td>Code</td>
<td>Name</td>
<%

System.out.println("++++++++++++++"+cal.get(Calendar.DAY_OF_WEEK_IN_MONTH));
for(int i=1;i<32;i++){
	cal.set(Integer.parseInt(yy),Integer.parseInt(mm)-1,i);
	
	index=cal.get(Calendar.DAY_OF_WEEK);
	
	
	if(index==1)
		day="Sun";
	if(index==2)
		day="Mon";
		
	if(index==3)
		day="Tue";
	if(index==4)
		day="Wed";
	if(index==5)
		day="Thur";
	if(index==6)
		day="Fri";
	if(index==7)
		day="Sat";
	if(index<=0)
		day="Dummy";

	%>
<td><%=day %></td>
<%} %>				

</tr>
<%
if(tabledata!=null){
  for(int i=0;i<tabledata.length;i++){	

%>
<tr>
<td><%=tabledata[i][0] %></td>
<td><%=tabledata[i][1] %></td>
<%
for(int j=2;j<33;j++){
%>
<%if(tabledata[i][j]!=null){ %>
<td><%=tabledata[i][j]%></td>
<%}else{ %>
<td>&nbsp;</td>
<%} %>
<%} %>
</tr>
<%
  }
}
%>
</table>
</div>
<%} %>

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>