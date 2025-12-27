<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@ page import="masterObject.general.ModuleObject" %>
<%@page import="masterObject.pygmyDeposit.PygmyTransactionObject"%>
<%@ page import="masterObject.pygmyDeposit.AgentMasterObject" %>
<%@ page import="masterObject.pygmyDeposit.PygmyMasterObject" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<html>
<head>
<title></title>
<h2 style="font:small-caps;font-style:normal;"><center>PassBook</center></h2>
      <style type="text/css">
          body{
              font-size:8px;
              font-family:serif;
              font-style:oblique;
              font-weight:bold;
              background:#f3f4c8;
          }
        /*h1,h2,h3,h4{

              font:"Times New Roman", Times, serif;
              font-size:18px;
              font-style:normal;

              font-weight:bold;
              text-align:center;
        }*/

        body{
             *//* background-image:url("/CommonWebPages/goldfade1.JPG");
              background:url("/CommonWebPages/goldfade1.JPG");*//*
         *//* background-color:"#ededd4"*//*
              

         }*/
        

       table{
          
             background:transparent;
            /* border:solid black;*/
             border:gray;
         }

       tr{
            background:transparent;

         }

       td{
          background:transparent;
         
       }

   </style>
   
   <script type="text/javascript">
        
        
        function shoe()
        {
        	alert("777777777777777");
        
        }
        
        
         function setVal(target)
         {
         	alert('m in ');
         	if(document.forms[0].pygno.value==0)
         	{
        		 alert('Enter Account Number');
         	}
         	else if(document.forms[0].pygno.value==" ")
         	{
         	alert('Enter Account Number');
         	}
         	else{
         		document.forms[0].forward.value=target;
         		document.forms[0].submit();
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
        
         function intialFocus(){
         document.forms[0].agentnum.focus();
         }
         
   </script>
</head>
<body>
		<%! ModuleObject[] array_module=null;
		    PygmyMasterObject pygmyMaster=null;
		    String paymode[];
		    AgentMasterObject agentMaster;
		    String date;
		    PygmyTransactionObject[] pbtran;
		%>
		
		<%
		    array_module=(ModuleObject[])request.getAttribute("PygmyType");
		    pygmyMaster=(PygmyMasterObject)request.getAttribute("PygmyDetails");
		    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+pygmyMaster);
		    agentMaster=(AgentMasterObject)request.getAttribute("AgentDetails");
		   	paymode=(String[])request.getAttribute("PayMode");
		   	date=(String)request.getAttribute("Date");
		   	pbtran=(PygmyTransactionObject[])request.getAttribute("PassBook");
		%>
		
		<% String msg=(String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<font color="red"><%=msg %></font>
		<br><br>
		<%} %>
		
		<html:form action="/Pygmy/PassBook?pageid=8008">
		<table>
			<tr>
				<td><bean:message key="label.pygmyactypeno"/>
				<td><html:select  property="pygtype" >
                         <html:option value="SELECT">SELECT</html:option>
                         <% array_module=(ModuleObject[])request.getAttribute("PygmyType");
 							System.out.println("here i am=="+array_module); %>                       
							<core:forEach var="pygtype" items="${requestScope.PygmyType}" varStatus="count">
								<html:option value="${pygtype.moduleCode}">${pygtype.moduleAbbrv}</html:option>
                        	</core:forEach>
                         </html:select></td>
                         
                 <td><bean:message key="label.pygno"></bean:message></td>
                 <td><font style="font-style:normal;font-size:8px"><html:text property="pygno"  size="10" onblur="shoe()"></html:text></font></td>
                         
                 <td><bean:message key="label.custid"></bean:message></td>   
                      <core:choose>
                      	 <core:when test="${requestScope.PygmyDetails!=null}">
                      	 	<td><font style="font-style:normal;font-size:8px"><html:text property="cid" size="10"  value="<%=""+pygmyMaster.getCid()%>" style="border:none;background:transparent"></html:text></font></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><font style="font-style:normal;font-size:8px"><html:text property="cid" size="10" value="0" style="border:none;background:transparent"></html:text></font></td>
                         </core:otherwise>	 	
                     </core:choose>  
			</tr>
			<tr><td></td></tr>
			<tr><td></td></tr>
			<tr><td></td></tr>
			<tr><td></td></tr>
			
			<tr>
				 <td><bean:message key="label.pygname"></bean:message></td>
				  <core:choose>
                      	 <core:when test="${requestScope.PygmyDetails!=null}">
                      	 	<td><font style="font-style:normal;font-size:8px"><html:text property="pygname"   value="<%=pygmyMaster.getName()%>" style="border:none;background:transparent"></html:text></font></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><font style="font-style:normal;font-size:8px"><html:text property="pygname"  value="0" style="border:none;background:transparent"></html:text></font></td>
                         </core:otherwise>	 	
                     </core:choose> 
                     
                 
                     
                     <td><bean:message key="label.paymentmode"></bean:message></td>
					 <core:choose>
                      	 <core:when test="${requestScope.PygmyDetails!=null}">
                      	 	<td><font style="font-style:normal;font-size:8px"><html:text property="paymode" size="10"  value="<%=pygmyMaster.getPayMode()%>" style="border:none;background:transparent"></html:text></font></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><font style="font-style:normal;font-size:8px"><html:text property="paymode" size="10" value="0" style="border:none;background:transparent"></html:text></font></td>
                         </core:otherwise>	 	
                     </core:choose> 
                     
            </tr>   
            
             <tr>
                     <td><bean:message key="label.agentno"></bean:message></td>
                     <core:choose>
                      	 <core:when test="${requestScope.PygmyDetails!=null}">
                      	 	<td><font style="font-style:normal;font-size:8px"><html:text property="agno" size="10"  value="<%=""+pygmyMaster.getAgentNo()%>" style="border:none;background:transparent"></html:text></font></td>
                         </core:when>
                      	 <core:otherwise>
                      	 	<td><font style="font-style:normal;font-size:8px"><html:text property="agno" size="10" value="0" style="border:none;background:transparent"></html:text></font></td>
                         </core:otherwise>	 	
                     </core:choose>
                
                    <td><bean:message key="label.agentname"></bean:message></td> 	
                    <core:choose>
                      	  <core:when test="${requestScope.PygmyDetails!=null}">
                        	<td><font style="font-style:normal;font-size:8px;color:red"><html:text property="agname"  disabled="true" value="<%=pygmyMaster.getAgentName()%>" size="10" style="border:none;background:transparent"></html:text></font></td>
                          </core:when> 
                          <core:otherwise> 
                            <td><font style="font-style:normal;font-size:8px;color:red"><html:text property="agname" size="10"  value="" style="border:none;background:transparent"></html:text></font></td>
                          </core:otherwise>	
                      </core:choose>
                      
                      <td><bean:message key="label.intrupto"></bean:message></td>
                      <core:choose>
                      	  <core:when test="${requestScope.PygmyDetails!=null}">
                        	<td><font style="font-style:normal;font-size:8px;color:red"><html:text property="intrupto"  disabled="true" value="<%=pygmyMaster.getLastIntrDate()%>" size="10" style="border:none;background:transparent"></html:text></font></td>
                          </core:when> 
                          <core:otherwise> 
                            <td><font style="font-style:normal;font-size:8px;color:red"><html:text property="intrupto" size="10"  value="" style="border:none;background:transparent"></html:text></font></td>
                          </core:otherwise>	
                      </core:choose> 
             </tr>    
             
             <tr>
             	  	<td><bean:message key="label.jtHolders"></bean:message></td>
             	  	<core:choose>
                      	  <core:when test="${requestScope.PygmyDetails!=null}">
                        	<td><font style="font-style:normal;font-size:8px;color:red"><html:text property="jt_hlds"  disabled="true" value="<%=""+pygmyMaster.getJointHolders()%>" size="10" style="border:none;background:transparent"></html:text></font></td>
                          </core:when> 
                          <core:otherwise> 
                            <td><font style="font-style:normal;font-size:8px;color:red"><html:text property="jt_hlds" size="10"  value="" style="border:none;background:transparent"></html:text></font></td>
                          </core:otherwise>	
                     </core:choose>
                      
                    <td><bean:message key="label.loanAvail"></bean:message></td>
             	  	<core:choose>
                      	  <core:when test="${requestScope.PygmyDetails!=null}">
                        	<td><font style="font-style:normal;font-size:8px;color:red"><html:text property="loanavail"  disabled="true" value="<%=pygmyMaster.getLnAvailed()%>" size="10" style="border:none;background:transparent"></html:text></font></td>
                          </core:when> 
                          <core:otherwise> 
                            <td><font style="font-style:normal;font-size:8px;color:red"><html:text property="loanavail" size="10"  value="" style="border:none;background:transparent"></html:text></font></td>
                          </core:otherwise>	
                     </core:choose>
             </tr>
             
             
			 <tr>
	 				<td><bean:message key="label.from_date"/></td>
	 				<td><html:text property="from_date" size="8" value="<%=date%>"></html:text></td>
	 			
	 				<td><bean:message key="label.to_date"/></td>
	 				<td><html:text property="to_date" size="8" value="<%=date%>" ></html:text></td>
	 		  </tr>      
	 		  
	 		  
	 		  	<tr>
					<html:hidden property="forward" value="error"/>
					<td><html:button property="but" onclick="setVal('view')" >View</html:button>
             		<html:submit onclick="set('file')" >File</html:submit>
                	 <html:submit onclick="set('print')">Print</html:submit></td>
           	    	<td><html:submit onclick="set('reprint')">RePrint</html:submit>
           	     	<html:button property="clear" onclick="clearMethod()">Clear</html:button></td>
           	    </tr> 	
           	    </table>
           	    <%if(pbtran!=null){ %> 
           	    <div style="overflow:scroll;width:550px;height:100px">
           	   
  				<display:table name="PassBook" style="background-color:#E4D5BE">
  
      				<display:column property="tranDate" style="background-color:#F2F0D2; font:Garamond;width:100px " title="Date" ></display:column>
            
      				<display:column property="tranNarration" style="background-color:#F2F0D2;width:100px " title="Narration"></display:column>
                    
      				<display:column property="debAmt" style="background-color:#F2F0D2;width:100px " title="Debit"></display:column>
      				
      				<display:column property="crAmt" style="background-color:#F2F0D2;width:100px " title="Credit"></display:column>
    
      				<display:column property="closeBal" style="background-color:#F2F0D2;width:100px " title="Balance"></display:column>
      				
     			</display:table>
     			
			</div>
	 	   	   <%} %>
                       
		
		</html:form>
</body>
</html>