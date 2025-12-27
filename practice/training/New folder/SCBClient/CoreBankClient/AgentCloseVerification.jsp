<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>    

<%@ page import="com.scb.pd.forms.AgentClosingForm" %>
<%@ page import="com.scb.common.forms.PersonnelForm" %>
<%@ page import="com.scb.common.forms.SignatureInstructionForm" %>
<%@ page import="com.scb.pd.forms.SelfActypeForm" %>
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.pygmyDeposit.AgentMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@page import="masterObject.pygmyDeposit.PygmyMasterObject"%>
<html>
<head>
<title></title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    <h2 class="h2"><center>Agent Closing Verification</center></h2>
     
   
   <script type="text/javascript">
         function set(target){
         	document.forms[0].forward.value=target;
         };
         
         function intialFocus(){
         document.forms[0].agentnum.focus();
         };
         
         function checkTable(){
        	var chk=document.forms[0].validating.value;
        	
        	if(chk==0 || chk==""){
        		return false;
         	}
         	else{
         		alert(chk);
         	}
         };		
         
        
        function clearMethod()
        { 
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
        	}	
        };
        
        function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  >= 47 && cha < 58  ) ) {
				return true ;
                
            	}
            else{
              	alert("Alphabets are not allowed,Please enter numbers only ");
              	return false ;
            }
        };
         
   </script>

</head>
<body class="Mainbody" onload="checkTable()">
	<%! 	ModuleObject[] array_module=null;
			AgentMasterObject[] agentMaster=null,agclose=null;
			PygmyMasterObject[] pm=null;
		 	String jspPath;
		 	String value,action;
		 	boolean flag1;
		
	%>
	<%	agentMaster=(AgentMasterObject[])request.getAttribute("AgentDetails");
		//System.out.println("agentMaster"+agentMaster);	
		pm=(PygmyMasterObject[])request.getAttribute("AgentClose");
		value=(String)request.getAttribute("AGENTCLOSE");
		agclose=(AgentMasterObject[])request.getAttribute("AgClose");
		//System.out.println("------->>>>><<<<<<<-------"+agclose.length);
	%>
	
	<%
	System.out.println("value1==============>"+value);
	if(value!=null){
		System.out.println("value1==============>>>>>>>>>>>>>>"+value);
    	 if(Integer.parseInt(value.trim())==2){
    		 System.out.println("value2==============>>>>>>>>>>>>>>"+value);
    		 action="/Pygmy/AgentClosingMenu?pageid=8010&value=2";
    		 flag1=true;
    	 }else if(Integer.parseInt(value.trim())==1){
    		 System.out.println("value3==============>>>>>>>>>>>>>>"+value);
    		 action="/Pygmy/AgentClosingMenu?pageid=8010&value=1";
    		 flag1=false;
    	 }
     }
	%>
	<% String msg=(String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<font color="red"><%=msg %></font>
		<br><br>
		<%} %>
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
	<html:form action="/Pygmy/AgentClosing?pageid=8030">
	
	<table class="txtTable">
		<td>
            <table class="txtTable">
           		<tr>
                     <td><bean:message key="label.agentcode"></bean:message></td>
                     <td><html:select  property="agType" styleClass="formTextFieldWithoutTransparent">
                         <html:option value="SELECT"></html:option>
                         <% array_module=(ModuleObject[])request.getAttribute("AgentAcType");
 							System.out.println("here i am=="+array_module); %>                       
							<core:forEach var="agType" items="${requestScope.AgentAcType}" varStatus="count">
								<html:option value="${agType.moduleCode}">${agType.moduleAbbrv}</html:option>
                        	</core:forEach>
                         </html:select></td>
                

                
                     <td><bean:message key="label.agentno"></bean:message></td>
                     <td><html:select property="agNo"  styleClass="formTextFieldWithoutTransparent" >
                     	<%if(agentMaster!=null){ %>
                   	 		<%for(int i=0;i<agentMaster.length;i++) {%>
                     			<html:option value="<%=""+agentMaster[i].getAgentNumber()%>"><%=""+agentMaster[i].getAgentNumber()%></html:option>
                     		<%}%>
                     	<%}else if(agclose!=null){ %>
                     		<%for(int i=0;i<agclose.length;i++) {%>
                     			<html:option value="<%=""+agclose[i].getAgentNumber()%>"><%=""+agclose[i].getAgentNumber()%></html:option>
                     		<%}%>
                     	<%} %>		
                     </html:select></td>
                </tr>
                
                	
              		 <html:hidden property="forward" value="error"/>
              		 <html:hidden property="value" ></html:hidden>
              		 <html:hidden property="validating"/>
                 
                <tr>
                		<%if(value!=null ){
                			   System.out.println("m insise if==================>"+value);
                     		if( Integer.parseInt(value)==2){ %>
                     	
                     	<%System.out.println("m in verify==============="+value); %>
                     	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
           			  <td><html:submit onclick="set('verify')" styleClass="ButtonBackgroundImage">Verify</html:submit>
             			<%}else{ %>
             			<td><html:submit onclick="set('verify')" disabled="true" styleClass="ButtonBackgroundImage">Verify</html:submit>
             			<%} %>
                    			
                    			<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
                    		<%}else if(Integer.parseInt(value)==1){ %>	
                    		<%System.out.println("m in submit=========="+value); %>	
                    			<td><html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage">Submit</html:submit>
                    			<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
                    		<%} %>	
                    	<%} else{%>	
                    	<%System.out.println("m in else of submit222222222==============="+value); %>
                    			<td><html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage">Submit</html:submit>
                    			<html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
                    	<%} %>	
                    		

                    </tr>	 
                   		
               
                
               
            </table></td>
            
            <td>
            <table class="txtTable">
             <td width="50" align="right" >
                   
                     <%
                         jspPath=(String)request.getAttribute("flag");
                         if(jspPath!=null){
                     %>
                         <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>

                     <%  } %>
                     
               </td>
            
			</table></td>
			
			
</table>		

			<%if(pm!=null){%>
			<center>
			<div  id = "table1" style="display: block;overflow:scroll;width: 750px;height: 300px">
           	   
  				<display:table name="AgentClose" id="currentRowObject" class="its">
  
      				<display:column property="accType" style="background-color:#F2F0D2; font:Garamond;width:100px " title="Agent Type" ></display:column>
            
      				<display:column property="accNo" style="background-color:#F2F0D2;width:100px " title="Agent Number"></display:column>
                    
      				<display:column property="name" style="background-color:#F2F0D2;width:100px " title="Name"></display:column>
      				
      				
     			</display:table>
     			
			</div></center>
			<%} %>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>