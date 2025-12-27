<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareReportObject"%>

<html>
<head><title>Voter List</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" media="excel"/>  
      <h2 class="h2">
      <center>Voters </center></h2>
      <hr>
    
    <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
            
    </style>
          
    <script type="text/javascript">
     function set(target){
       document.forms[0].forward.value=target;
       
        };
     																																					
     
           function validfn(fnm,str){
             var len=fnm.length;
             if(len==0){
               alert("Enter the "+str )
             }
           };
     
      
     
    </script>


  </head>

<body class="Mainbody">
<%!
  
   ShareReportObject[] shreport;
   HttpSession session; 
%>

<%
   
   shreport=(ShareReportObject[])request.getAttribute("Report");
%>

<html:form action="/Share/Voter?pageId=4024">
   <center>
 <table class="txtTable">
 

  <tr>
    <html:hidden property="forward" value="error"></html:hidden>
    <td>
    <html:submit  onclick="set('view')" styleClass="ButtonBackgroundImage"><bean:message key="label.view1"></bean:message> </html:submit>
    <html:submit  onclick="set('download')" styleClass="ButtonBackgroundImage">Download</html:submit>
    <html:submit onclick="set('clear')" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:submit>
    <!--<html:button property="printfile" onclick="window.print()" styleClass="ButtonBackgroundImage" value="Print"></html:button>   
 
    --></td>
  </tr>

  </table>
</center>
<hr>
 <table align="left" class="txtTable">
<tr> 
<td>
    
    <%
       
     //session=request.getSession(true);
     
    if(shreport!=null){
    	//session.setAttribute("Report",shreport);
        //System.out.println("First....");
    	%>
    <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			
     
     <display:table name="Report"  id="currentRowObject" class="its" sort="list" pagesize="10" requestURI="/Share/Voter.do">
   			<!--<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 
   
         --><display:column property="shareNumber" ></display:column>
       <display:column property="name" ></display:column>
       <display:column property="address" ></display:column>
       <display:column property="city" ></display:column>
       <display:column property="pin" ></display:column>
       <display:column property="shareStatus"  ></display:column>
     
    <display:column media="csv excel" title="URL" property="url" />
    <display:setProperty name="export.excel" value="true" />
    
     </display:table>
</div>
    
 	<%
 	System.out.println("SURAJ IS HERE");
 	
 	//session.getAttribute("Report");
 	
    }
     %>
 </td>
 </tr>
 </table>  
 
 </html:form>

</body>
</html>