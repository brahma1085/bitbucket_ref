

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Amzad
  Date: Feb 20, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<html>
  <head><title>Joint Holders</title>
      <h1 style="font:small-caps;font-style:normal;color: blue" ><center>Joint Holders Details</center></h1>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
       
      
      <!--<style type="text/css">
          body{
              font-size:10px;
              font-family:serif;
              font-style:oblique;
              font-weight:bold;
              background:beige;
          }
          table{
               background:transperent; 
          }
          
    </style>
    --><link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    <script>
   function confirmsubmit(){
  
   
   
   }
   
   function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
     
     function validate(){
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         alert(val);
       } 
       
       else{
         return false;
       }
     }; 
     function clearFunc()
    {
    	
    	var ele=document.forms[0].elements;
    	  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    		if(ele[i].type=="password")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    
    </script>
  </head>
  <body class="Mainbody" onload="return validate()">
    <%
    String addJointHolder=(String)request.getAttribute("AddJointHolder");
    String cid=(String)request.getAttribute("cid");
    
    %>
    
    
     <html:form action="/FrontCounter/JointHolders?pageId=3043">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="pageId" value="3005"/>
     	<table align="center">
     	   <tr>
     	      <td>
     	         <html:button  property="add"  onclick="setFlag('add'); " styleClass="ButtonBackgroundImage" ><bean:message key="gl.label.add"></bean:message> </html:button>
                 <html:button  property="delAll" onclick="setFlag('deleteAll')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.delAll"></bean:message> </html:button>
                 <html:button  property="del" onclick="setFlag('delete')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.delete"></bean:message></html:button>
                 <html:button  property="done" onclick="setFlag('done')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.done"></bean:message></html:button>
     	
     	      </td>
     	    
           </tr>
           <%
           if(addJointHolder!=null){
           %>
     	<tr>
     	 
     	  <font Style="font-family:bold;color:blue">  Customer Id:</font><html:text property="cid" onblur="setFlag('from_cid')" size="6"></html:text>
     	  <html:button  property="cancel" onclick="setFlag('cancel')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.cancel"></bean:message></html:button>
     	  
     	</tr>
     	  
     	 
     	 
     	   
     	    
     	
     	<%} %>
     	<%if(cid!=null){ %>
     	<tr>
     	  <td>
     	     <table style="border:thin solid #000000;">

  
 <%  String jspPath=(String)request.getAttribute("flag");
	System.out.println("'m inside panel"+jspPath);
	if(jspPath!=null)
	{
	System.out.println("wen 'm  null");
    %>
    <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
    
    <%}else{	%>
    <jsp:include page="/LockerWebPages/blank.jsp"></jsp:include>
    <%} %>
    
 	</table> 
     	  </td>
     	</tr>
     	<%} %>
     	</table>
     	 
     </html:form>	
     	
     	  
     	   

													
 		
    	
 														
 	
     	   
     	   
     	   
     	   
     	 	
     	
  </body>
  </html>          