<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SIExecution</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />

    <script type="text/javascript">
   
    function set(target)
    {
    alert(target);
    document.forms[0].forward.value=target;
    };
    
    
    function callClear(target){
           document.forms[0].forward.value=target;
         	 var ele= document.forms[0].elements;
             for(var i=0;i<ele.length;i++){
               if(ele[i].type=="text"){
                  
               ele[i].value="";
               }
           
           }
            };
            
       function button_execute(target)
        {
       alert(target);
       document.forms[0].forward.value=target;
       alert("....Executed.....");
        };
          
    </script>
</head>
<body class="Mainbody" style="overflow: scroll;">
<center><h2 class="h2">Standing Instruction Execution</h2></center>
<%@page import="java.util.Map"%>


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
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/BackOffice/SIExecution?pageId=11015">
<% String displaymsg=(String)request.getAttribute("displaymsg");%>
<%if(displaymsg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=displaymsg %></font>
<%} %>
<%int[] si_no=(int[])request.getAttribute("SINO");
System.out.println("The si entry ------------"+si_no);
%>

<%String[][] str=(String[][])request.getAttribute("Siexecdetails"); %>
<%String[][] execdetails=(String[][])request.getAttribute("executionDetails");
System.out.println("The si entry ------------"+execdetails);
%>

<br><br>
<center>
<table border="1">
<th>Select</th><th>Instn no</th><th>From A/c</th><th>A/c no</th><th>Name</th><th>To A/c</th><th>A/c no</th><th>Name</th><th>Amount</th>
<%if(si_no!=null){ %>
<%for(int i=0;i<si_no.length;i++){
	System.out.println("EXEXCTION[0][1--------]"+execdetails[i][0]);
%>
	<td><input type="checkbox" name="cbox"></td>
	<%if(execdetails[i][0]!=null){%>
	<td><input type="text" disabled="true" size="5" value="<%=""+execdetails[i][0]%>"></td>
	<%} %>
	
	
	<% }%>
	<%} %>
</table>
</center>
<br><br>	
<center>	
<table class="txtTable">	
	<tr>
	    
	     <html:hidden property="forward" value="error"></html:hidden>
	   <td>
		  <html:submit  onclick="set('clear')" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message> </html:submit>
          
		  <html:submit onclick="button_execute('execute')" styleClass="ButtonBackgroundImage"><bean:message key="label.execute"></bean:message></html:submit>

          
		</td>  
	</tr>
</table>
</center>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>