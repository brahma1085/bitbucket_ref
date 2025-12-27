<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>    


<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.pygmyDeposit.PygmyMasterObject"%>
<%@page import="masterObject.pygmyDeposit.AgentMasterObject"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@page import="com.scb.pd.forms.AgentChangeForm"%>
<html>
<head>

<title>Insert title here</title><style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<h2 class="h2"><center>Agent Change</center></h2>
</head>


	
	<!--<script language="javaScript">
		function from(){
		var from=+pMaster+;
		
		alert(from);
	
	    var to=new Array(0);
	    
	    };
	</script>

	-->
	<script language="javaScript"><!--
	
		function set(target){
		
       		document.forms[0].forward.value=target
        };
        
        function validateValue(){
        	var chk=document.forms[0].validate.value;
        	
        	if(chk==0 || chk==""){
        		return false;
         	}
         	else{
         		alert(chk);
         	}
         	clear_textfield();
         };	
         
         	
        
		/*function adding()
		{
		
		var from_ar=document.getElementById('xy');
		
		var len=from_ar.length;
        
        
		var to_ar=document.getElementById('yz');
		
		for(var j=0;j<len;j++)
		{
		if(from_ar[j].selected)
		{

		var tmp=from_ar[j].text;
		alert(tmp);
		var tmp1=from_ar[j].value;

		from_ar.remove(j);
		j--;
		var y=document.createElement('option');
		y.text=tmp;

		try
		{
			to_ar.add(y,null);
		}
		catch(ex){
		//alert("bug");
		to_ar.add(y);
		}
		break;
		}

		}
		};
		
		function remove(){
		var to_ar=document.getElementById('yz');
		var len=to_ar.length;

		var from_ar=document.getElementById('xy');
		for(var j=0;j<len;j++){
			if(to_ar[j].selected){

		var tmp=to_ar[j].text;
		alert(tmp);
		var tmp1=to_ar[j].value;

		to_ar.remove(j);
		j--;
		var y=document.createElement('option');
		y.text=tmp;

		try
		{
			from_ar.add(y,null);
		}
		catch(ex){
		//alert("bug");
		from_ar.add(y);
		}
		break;
		}

		}
		} ;
		*/
		
		 function clear_textfield()
  		{
  			
  		
  			var ele=document.forms[1].elements;
  			for(var i=0;i<ele.length;i++)
  			{
  				if(ele[i].type=="text")
  				{
  			  		ele[i].value="";
  				}
  		
  			}
  		};	
  		
		
--></script>
	
	
     


<body onload="validateValue()" >
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
<html:form action="/Pygmy/AgChange?pageid=8031">
	<%!ModuleObject[] array_module;
	  AgentMasterObject[] agm;
	  PygmyMasterObject[] pMaster;
	  PygmyMasterObject[] pMaster1;
	  AgentMasterObject agname;
	  String[] name;
	
	%>
	<%
	agm=(AgentMasterObject[])request.getAttribute("AgNum");
	System.out.println("<<<<<<========>>>>>>>"+agm); 
	//name=(String[])request.getAttribute("Name");
	//System.out.println("<<<<<<========>>>>>>>"+name);
	//agname=(AgentMasterObject)request.getAttribute("AgName");
	 
	%><table>
	
			<tr>
                 <td><bean:message key="label.agentcode"></bean:message></td>
                  <td><html:select  property="agType" styleClass="formTextFieldWithoutTransparent">
                         <html:option value="SELECT"></html:option>
                         <% array_module=(ModuleObject[])request.getAttribute("AgAcType");
 							System.out.println("here i am=="+array_module); %>                       
							<core:forEach var="agType" items="${requestScope.AgAcType}" varStatus="count">
								<html:option value="${agType.moduleCode}">${agType.moduleAbbrv}</html:option>
                        	</core:forEach>
                         </html:select></td>
            
            	<td><bean:message key="label.agentno"></bean:message></td>
            		<td><html:select property="agNum" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
            		<html:option value="SELECT"></html:option>
               		<%if (agm!=null){ %>
               		<%	for(int i=0;i<agm.length;i++) {%>
                     		<html:option value="<%=""+agm[i].getAgentNumber()%>"><%=""+agm[i].getAgentNumber()%></html:option>
                     	<%} }%>
                 </html:select></td>    	
               		 
               		 
           </tr>
           
           <tr>
                 <td><bean:message key="label.agtype"></bean:message></td>
                  <td><html:select  property="alt_agType" styleClass="formTextFieldWithoutTransparent" >
                         <html:option value="SELECT"></html:option>
                         <% array_module=(ModuleObject[])request.getAttribute("AgAcType");
 							System.out.println("here i am=="+array_module); %>                       
							<core:forEach var="alt_agType" items="${requestScope.AgAcType}" varStatus="count">
								<html:option value="${alt_agType.moduleCode}">${alt_agType.moduleAbbrv}</html:option>
                        	</core:forEach>
                         </html:select></td>
            
            	<td><bean:message key="label.agno"></bean:message></td>
            	<td><html:select property="alt_agNo" styleClass="formTextFieldWithoutTransparent">
               		<html:option value="SELECT"></html:option>
               		<%if (agm!=null){ %>
               		<%	for(int i=0;i<agm.length;i++) {%>
                     		<html:option value="<%=""+agm[i].getAgentNumber()%>"><%=""+agm[i].getAgentNumber()%></html:option>
                     	<%} }%>
                 </html:select></td>
                   	
                   	
           </tr>
      
			<tr><td>
			<%pMaster=(PygmyMasterObject[])request.getAttribute("PygmyNum"); 
			AgentChangeForm acgForm=null;
			
			 if(pMaster!=null){%>
			<%String agnum=request.getAttribute("agno").toString();
			System.out.println("agnum=================>??????????"+agnum);%>
			<h4><b>Pygmy Accounts Under AgentNo:  <%=agnum %></b></h4>
				<select  id="xy" multiple="multiple" style="width:300" size=10 name="xyz">
				<% for(int i=0;i<pMaster.length;i++)
					{%>
					<option  value="<%=""+pMaster[i].getAccNo()%>">
					<%= pMaster[i].getAccType()+ " " + pMaster[i].getAccNo()+ " " + pMaster[i].getName()%>
					</option>
					<% String agno=pMaster[i].getAgentNo();%>
					<%} %>
				</select>
            
		    <%}%>
	      </td>
           <td></td>
           	<td>
           	<%pMaster1=(PygmyMasterObject[])request.getAttribute("PygmyNumber"); 
           	if(pMaster1!=null){%>
           	<%String alt_agno=request.getAttribute("alt_agno").toString();
			System.out.println("agnum=================>??????????"+alt_agno);%>
			<h4><b>Pygmy Accounts under Alternative AgentNo :  <%=alt_agno %></b></h4>
           	<html:select  style="width:300" multiple="true" styleId="yz" size="7" property="selectedone">
           				<% for(int j=0;j<pMaster1.length;j++)
					{%>
					<option  value="<%=""+pMaster1[j].getAccNo()%>">
					<%= pMaster1[j].getAccType()+ " " + pMaster1[j].getAccNo()+ " " + pMaster1[j].getName()%>
					</option>
					
					<%} %>
		</html:select>
		<%} %>
		</td></tr>	 
						
			<tr>
						
			<td>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
			<html:submit onclick="set('view')" styleClass="ButtonBackgroundImage">View</html:submit>
			<%}else{ %>
        	<font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        	<%} %>
			
			
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            <html:submit onclick="set('verify')" styleClass="ButtonBackgroundImage">Verify</html:submit>
             <%}else{ %>
            <html:submit onclick="set('verify')" disabled="true" styleClass="ButtonBackgroundImage">Verify</html:submit>
             <%} %>
			
           <html:button property="ClearButton" onclick="clear_textfield()" styleClass="ButtonBackgroundImage">Clear</html:button></td></tr>			
           		 
           		 
           </table>
           <html:hidden property="validate"/>
           <html:hidden property="valid"/>
           <html:hidden property="forward" value="error"/>
           
           
           
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>																																			