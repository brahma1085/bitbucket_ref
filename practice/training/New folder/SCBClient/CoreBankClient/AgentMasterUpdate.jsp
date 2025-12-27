<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ page import="masterObject.pygmyDeposit.AgentMasterObject"%>
<%@ page import="masterObject.general.AccountObject"%>
<%@ page import="masterObject.general.ModuleObject" %>
<%@page import="java.util.Map"%>
<html>
<head>
<title></title>
	<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
	<h2 class="h2"><center>Agent Master Updation</center></h2>
	
	<script type="text/javascript">
         function set(target){
         	if(document.forms[0].agNo.value=="0")
         {
         alert('Enter Agent Number');
         }
        else{
         	document.forms[0].forward.value=target;
         	document.forms[0].submit();
         	}
         };
         
         function getChk(){
         	
         	var chk=document.forms[0].valid.value;
         	
         	if(chk==""||chk==null){
         		return false;
         	}	
         	else{
         		alert(chk);
         	}	
         	
			clearMethod();
         };
         function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only "+size+" digits allowed")
         		txt.value="";
         		return false;
          	}
         }
         
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
				if (  ( cha  > 47 && cha < 58  ) ) {
				return true ;
                
            	}
            else{
              	alert("Alphabets are not allowed,Please enter numbers only ");
              	return false ;
            }
        };
        
        function chkUpdate(){
        var val=document.forms[0].valid.value;
        
        if( val=="" ){
   			
   			 return false; 
   		}
   		else { 
   			 alert(val);
   		}
   			
  	};
  	function numbersonly_date(eve){
         var cha = event.keyCode

            if (  ( cha  >= 47 && cha < 58  ) ) {
              
                return true ;
                            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
         
        
      };     
        
        function checksub(target)
   		{
   			document.forms[0].forward.value=target;
   			document.forms[0].submit();
   		}
   		
    function setsub(){
    
    }
    </script>   
    
     
</head>
<body class="Mainbody" onload="getChk()">
	<%!  ModuleObject[] array_module; 
	     AgentMasterObject amo;
	     AccountObject acnt;
	     String[] details;
	     String CloseInd;
	     String name,name1;
	     int cid;
	     String jspPath;
	     String date;
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
%>
	
			<% amo=(AgentMasterObject)request.getAttribute("AgentDetails"); 
	   			//CloseInd=(String)request.getAttribute("CloseInd");
	  
	  // cid=(Integer)request.getAttribute("Check");
	  // name1=(String)request.getAttribute("SelfName");
	  // System.out.println("Self Account Name====>>"+name1);
	   
	  // System.out.println("Hi SelfDetails------------"+acnt);
	String msg=(String)request.getAttribute("msg");
%>
<%
if(msg!=null){
%>
<font color="red"><%=msg %></font>
<br><br>
<%} %>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:form action="/Pygmy/AgentUpdate?pageid=8017">
	 <table class="txtTable">
	 	<td>
	 		<table class="txtTable">
           		<tr>
                     <td><bean:message key="label.agentcode"></bean:message></td>
                     <td><html:select  property="agType" styleClass="formTextFieldWithoutTransparent">
                         <% array_module=(ModuleObject[])request.getAttribute("AgentAcType");
 							System.out.println("here i am=agent type="+array_module); %> 
							<core:forEach var="agType" items="${requestScope.AgentAcType}" varStatus="count">
								<html:option value="${agType.moduleCode}">${agType.moduleAbbrv}</html:option>
                        	</core:forEach>
                         </html:select>
                         	
                     		<html:text property="agNo" size="10" onblur="checksub('AgAccNo')"  styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>
                     </td>	
               	</tr>
               	
               	<tr>
               		 <td><bean:message key="label.agentname"></bean:message></td>
                     <td><html:text property="agName" size="50" readonly="<%=(Boolean)request.getAttribute("Disabled") %>"   styleClass="formTextField" ></html:text></td>
               	</tr>
               	
               	<tr>
               		 <td><bean:message key="label.custid"></bean:message></td>
                     <td><html:text property="custId" onblur="checksub('CustId')" readonly="<%=(Boolean)request.getAttribute("Disabled") %>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
               </tr>
               
               	<tr>
               		 <td><bean:message key="label.appointeddate"></bean:message></td>
                        	<td><html:text property="appDate" readonly="<%=(Boolean)request.getAttribute("Disabled") %>"  styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly_date(this)"></html:text></td>
               </tr>
               
               <tr>
               		<td><bean:message key="label.selfactypeno"/></td>
               		<td><html:select  property="selfAccType"  styleClass="formTextFieldWithoutTransparent" >
                         <% array_module=(ModuleObject[])request.getAttribute("SelfAcType");
 							System.out.println("here i am=self ac type="+array_module); %> 
 							<core:choose>
                         	   <core:when test="<%=amo!=null%>">
                         	   <%System.out.println("before joint a/c type------before-->"); %>
                         	    
                         	      <%System.out.println("before joint a/c type----After---->"); %>                   
									<core:forEach var="selfAccType" items="${requestScope.SelfAcType}" varStatus="count">
										<html:option value="${selfAccType.moduleCode}">${selfAccType.moduleAbbrv}</html:option>
                        			</core:forEach>
                        		 
                        	   </core:when>  	    
                         	   <core:otherwise>
                         	   <html:option value="SELECT">SELECT</html:option>
                            	      <core:forEach var="selfAccType" items="${requestScope.SelfAcType}" varStatus="count">
                                	     <html:option value="${selfAccType.moduleCode}">${selfAccType.moduleAbbrv}</html:option>
                                      </core:forEach>	
                               </core:otherwise>	
                           	 </core:choose>	
                         </html:select>
                         
                     			<html:text property="selfAcNo" size="10" readonly="<%=(Boolean)request.getAttribute("Disabled") %>" onblur="checksub('SelfAccNo')"  styleClass="formTextFieldWithoutTransparent"></html:text>
                          </td>	
                </tr>
                <tr>
               		 <td><bean:message key="label.selfname"></bean:message></td>
                     <td><html:text property="selfAccName"  styleClass="formTextField" readonly="<%=(Boolean)request.getAttribute("Disabled") %>"></html:text></td>
               	</tr>
               		<%System.out.println("before joint a/c type-------->"); %>
               <tr>
               		<td><bean:message key="label.jointactypeno"/></td>
               		<td><html:select  property="jtAccType" styleClass="formTextFieldWithoutTransparent">
                         
                         <% array_module=(ModuleObject[])request.getAttribute("JointAcType");
 							System.out.println("here i am=joint ac type="+array_module); %> 
 							<core:choose>
                         	   <core:when test="<%=(amo!=null)%>">
                         	                                               
									<core:forEach var="jtAccType" items="${requestScope.JointAcType}" varStatus="count">
										<html:option value="${jtAccType.moduleCode}">${jtAccType.moduleAbbrv}</html:option>
                        			</core:forEach>
                        		 
                        	   </core:when>  	    
                         	   <core:otherwise>
                         	   <html:option value="SELECT">SELECT</html:option>
                            	      <core:forEach var="jtAccType" items="${requestScope.JointAcType}" varStatus="count">
                                	     <html:option value="${jtAccType.moduleCode}">${jtAccType.moduleAbbrv}</html:option>
                                      </core:forEach>	
                               </core:otherwise>	
                           	 </core:choose>		
                         </html:select>

                    			<html:text property="jtAcNo" size="10" readonly="<%=(Boolean)request.getAttribute("Disabled") %>" onblur="checksub('JointAccNo')"  styleClass="formTextFieldWithoutTransparent" ></html:text>
                        </td>  	
                     
                </tr>
                
                <tr>
            
            		<td><bean:message key="label.jointname"></bean:message></td>
                    <td><html:text property="jtAccName" size="50" readonly="<%=(Boolean)request.getAttribute("Disabled") %>" onchange="submit()" styleClass="formTextField" ></html:text></td>
               	</tr>
               	
               	<tr>
               		<td><bean:message key="label.introduceractypeno"/></td>
               		<td><html:select  property="intAccType" styleClass="formTextFieldWithoutTransparent" >
                         
                         <% array_module=(ModuleObject[])request.getAttribute("IntroAcType");
 							System.out.println("here i am= intro ac type="+array_module); %> 
 							 <core:choose>
                         	   <core:when test="<%=amo!=null%>">
                         	                          
									<core:forEach var="intAccType" items="${requestScope.IntroAcType}" varStatus="count">
										<html:option value="${intAccType.moduleCode}">${intAccType.moduleAbbrv}</html:option>
                        			</core:forEach>
                        		   
                        	   </core:when>  	    
                         	   <core:otherwise>
                         	   <html:option value="SELECT">SELECT</html:option>
                            	      <core:forEach var="intAccType" items="${requestScope.IntroAcType}" varStatus="count">
                                	     <html:option value="${intAccType.moduleCode}">${intAccType.moduleAbbrv}</html:option>
                                      </core:forEach>	
                               </core:otherwise>	
                           	 </core:choose>	
                         </html:select>
							<%System.out.println("-----dddd------");  %>
                     		 <html:text property="intAccNo" size="10" readonly="true" onblur="submit()"  styleClass="formTextFieldWithoutTransparent" ></html:text>
                         </td>	
                     
                </tr>
                
                 <tr>
                	<td><bean:message key="label.clInd"></bean:message></td>
                	<%System.out.println("-----1------"); %>
                	<td><html:text property="closeInd" size="10"  styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
                </tr>
                
                <tr>
                	<td><bean:message key="label.clDate"></bean:message></td>
                	<%System.out.println("-----2------"); %>
                	
                			<td><html:text property="closeDate" size="10"  styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly_date(this)" readonly="true"></html:text></td>
                </tr>
                <tr>
                	<td><bean:message key="label.details"></bean:message></td>
                	<%System.out.println("-----3------"); %>
                     <td><html:select  property="details" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
                          <html:option value="SELECT">SELECT</html:option>
                         <% details=(String[])request.getAttribute("Details");%>
                          <core:forEach var="details" items="${requestScope.Details}" varStatus="count">        
                         	
                            <html:option value="${details}">${details}</html:option>
                            <%System.out.println("Helloooooooooooooooooo"); %>
                          </core:forEach>  
                       </html:select></td>
                </tr>
                
                	
     				<html:hidden property="valid"/>
                <html:hidden property="first"/>
                <html:hidden property="forward" value="error"/>
                <td>
                <%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
         		 <html:submit onclick="set('update')"  styleClass="ButtonBackgroundImage">Update</html:submit>
             	<%}else{ %>
          		<html:submit onclick="set('update')" disabled="true" styleClass="ButtonBackgroundImage">Update</html:submit>
             	<%} %>
                 </td>
                  <td><html:button property="ClearButton" onclick="clearMethod()" styleClass="ButtonBackgroundImage">Clear</html:button>
                 </td>
           
	 		</table>
	 		
	 		
	 		 <td>
                   
                     <%
                         jspPath=(String)request.getAttribute("flag");
                     		System.out.println("-----"+jspPath);
                         if(jspPath!=null){
                     %>
                         <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>

                     <%  } %>
                     
               </td>
	 </table>
</html:form>	
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>