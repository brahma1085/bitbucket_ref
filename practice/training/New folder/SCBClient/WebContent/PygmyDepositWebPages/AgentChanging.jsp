<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>

<html>
<head>

<title></title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
        <h2 style="font:small-caps;font-style:normal;"><center>Agent Changing</center></h2>
      
   
   <script type="text/javascript">
   
   		function generateAcno(){
   		var val=0;
        val=document.forms[0].validation.value;
        if(val!=0 || val!=""){
   			 alert("Agent No Generated is=="+document.forms[0].validation.value);
   		}
   		else { 
   			return false; 
   		}
  	}
        
   
         function set(target){
         	document.forms[0].forward.value=target;
         };
         
         function intialFocus(){
         document.forms[0].agentnum.focus();
         }
         
        function clearMethod()
        {
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}else{
        			ele[i].value="0";
        		}
        	}	
        };
         
   </script>

</head>
<body>
	<html:form action="/Pygmy/AgentChange?PageId=8018">
</html:form>
</body>
</html>