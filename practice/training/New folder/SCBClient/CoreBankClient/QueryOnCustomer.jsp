<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  User: shwetha   
  Date: Nov 22, 2007
  Time: 11:17:36 PM    
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.Map"%>
<html> 
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
  <head><title>Simple jsp page</title>
  <style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
	<h2 class="h2"><center>Query On Customer</center></h2>
  <script type="text/javascript">
  function changecolor()
  {
    document.forms[0].comboname.style.background='#FF0000';
  }
  function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >= 65 && cha <=90)||cha==46||cha==32) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
  
  </script>
  </head>
  <body bgcolor="beige">
  <%!
  Map user_role;
  String access;
  %>
  <%
  String accesspageId=(String)request.getAttribute("accesspageId");
	user_role=(Map)request.getAttribute("user_role");
	
	
	if(user_role!=null&&accesspageId!=null){
		if(user_role.get(accesspageId)!=null){
			access=(String)user_role.get(accesspageId);
			System.out.println("access-----In SBOpening------>"+access);
		}

		
		}
  
  %>
  <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
    <html:form action="/customer/queryoncustomer?pageId=1007">
    
    <%! String[] QueryOnName;%>
   <%  String nstr=(String)request.getAttribute("flag");
if(nstr!=null){
System.out.println("inside 0"+nstr);
}
%>
      <%QueryOnName=(String[])request.getAttribute("QueryOnName");%>
    <%System.out.println("QueryOnName in jsp page========="+QueryOnName); %>
  
<table class="txtTable">
<td>    
<table  cellspacing="5" style="border: thin solid black;">
    <tr> 
       <td><FONT style="font-family: cursive"><bean:message key="label.custname"></bean:message></FONT>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <html:text property="name" size="30"  onkeypress="return only_alpha()" onchange="submit()"></html:text></td>
   </tr>
   <tr><td  class="loginTableFooter"></td></tr>
   <tr>
   	  	
      <td>
      <!--<hr style="color: blue">
      --><%if(QueryOnName!=null){%>
      
    <html:select property="comboname" size="14" onchange="submit()">
     <%for(int i=0;i<QueryOnName.length;i++){%> 
    <%StringTokenizer token_custname=new StringTokenizer(QueryOnName[i],"$$");%>
    <%String str_name=token_custname.nextToken();%>
   	<html:option value="<%=str_name%>"></html:option>
 	<%}%>
   	</html:select>
    <%}%>
      </td> 
   </tr>
 </table> 
 </td>
 <td>
 <table align="right">
 <% String pagenew=nstr;
 if(nstr!=null){
 %>
 
	   <jsp:include page="<%=pagenew %>"></jsp:include>
	   
	   <%} %>
  </table>
 
 </td>
 </table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>