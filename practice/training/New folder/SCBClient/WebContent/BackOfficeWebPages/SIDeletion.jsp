<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: Nov 22, 2007
  Time: 11:04:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.scb.bk.forms.SIEntryComboElements" %>
<%@ page import="com.scb.bk.forms.SIDeleteForm" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ page import="masterObject.backOffice.SIEntryObject"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>

<%@page import="masterObject.general.ModuleObject"%>
<%String deleted = (String)request.getAttribute("deleted"); %>

<html>
  <head><title>SIDelete</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <script language="javascript">
      
      
      function onlynumbers()
{
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        };
      
     function showalert(){
      	if(document.getElementById("sucess_del").value=="deleted")
         	{
         	  alert("Instruction Number Deleted Sucessfully");
         	  clears();
         	};
         	
         	if(document.getElementById("sucess_del").value=="verified")
         	{
         	  alert("Verified Successfully");
         	  clears();
         	};
         	
         	if(document.getElementById("sucess_del").value=="alDeleted")
         	{
         	  alert("Instruction Number Already Deleted");
         	  clears();
         	}
         	
         	if(document.getElementById("sucess_del").value=="alverified")
         	{
         	  alert("Instruction Number Already Verified");
         	  clears();
         	}
         	if(document.getElementById("sucess_del").value=="invalid")
         	{
         	  alert("Please Enter Valid Instruction Number");
         	  clears();
         	}
      }
       function set(target){
            
          document.forms[0].forward.value=target;
          };
          
       
       //clearing form
        function clears(){
     
             document.forms[0].instruction_num.value="0";
         	 document.forms[0].priority_num.value="";                  
             document.forms[0].from_ac_ty.value="";
         	 document.forms[0].from_ac_no.value="";
        	 document.forms[0].to_ac_ty.value="";
         	 document.forms[0].to_ac_no.value="";
         	 document.forms[0].loan_option.value="";
         	 document.forms[0].due_date.value="";
         	 document.forms[0].period_mon.value="";
         	 document.forms[0].days.value="";
         	 document.forms[0].amount.value="";
         	 document.forms[0].amount_adjusted.value="";
         	 document.forms[0].no_of_times_exec.value="";
         	 document.forms[0].last_exec_date.value="";
         	 document.forms[0].expiry_days.value="";
                } ;  
                
                
           function callClear(){
           
         	 var ele= document.forms[0].elements;
             for(var i=0;i<ele.length;i++){
               if(ele[i].type=="text"){
                  
               ele[i].value="";
               }
           
           }
            };
                
                
         function button_delete(target){
         if(document.forms[0].instruction_num.value=="0")
         {
          alert("Please Enter Valid Instruction Number")
         }
         else{
          alert(target);
           document.forms[0].forward.value=target;
         	var deleteval=confirm("Confirm Delete????");
         
         	
         	if(deleteval==true)
         	{
         		document.getElementById("delete_ins").value="true";
         		document.forms[0].submit();
         		
         		
         	}
         	else
         	{ 
         		document.getElementById("delete_ins").value="false";
         		return false;
         	}
         	}
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
         	
         	function button_verify(target){
         	    alert(target);
                document.forms[0].forward.value=target;
         	    var verificationval=confirm("Confirm Verify????");
         	
             	if(verificationval==true)
         	      {
         		document.getElementById("sucess_verified").value="true";
         		document.forms[0].submit();
         		
         	     }
         	   else
         	     { 
         		 document.getElementById("sucess_verified").value="false";
         		 return false;
         	   }
            };         
                
      </script>
  </head>
  <body class="Mainbody" onload="showalert()" style="overflow: scroll;">

 <center><h2 class="h2">Standing Instruction Deletion</h2></center>
 <% String displaymsg=(String)request.getAttribute("displaymsg");%>
<%if(displaymsg!=null){ %>
<font style="font-style:bold;font-size:12px" color="red"><%=displaymsg %></font>
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
 <%!  SIEntryObject sidelObj;
      ModuleObject modObj;   
      int i;  
 %>
 <% System.out.println("i am here&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");%>
 <% System.out.println("i inside si"); %>
 <% sidelObj = (masterObject.backOffice.SIEntryObject) request.getAttribute("SIDel");%>
 
 <table>
    <td width="40%">
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
    <html:form action="/BackOffice/SIDelete?pageId=11002">
     <table align="left">
     <%System.out.println("line 1");%>
        <tr>    
           <html:hidden property="deleteinstnum" styleId="deleteinstnum" value="error"/>
           <td align="right" ><font style="font-style:normal;font-size:12px;" color="brown"><bean:message key="label.instruction_num" ></bean:message></font></td>
           <core:choose>
           <core:when test="${empty requestScope.SIDel}">
           <td><html:text property="instruction_num" onkeypress="return onlynumbers()"  onchange="submit()" onkeyup="numberlimit(this,'6')" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
           <td><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.for_deletion"></bean:message></font></td>
           <%Boolean validInstNo=(Boolean)request.getAttribute("InstNo");%>
           <%System.out.println("valid-----"+validInstNo);%>
           <core:if test="<%=validInstNo!=null%>">
           <tr><td align="right"><font style="font-style:normal;font-size:11px" color="red"><bean:message key="label.Instnotfound" ></bean:message></font></td></tr>
           </core:if>
           </core:when>
           <core:otherwise>
           <td><html:text property="instruction_num" onchange="submit()" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
           <td><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.for_deletion"></bean:message></font></td>
           </core:otherwise>
           </core:choose>
          </tr>
        
          <% System.out.println(" line 2");%>
            
        <tr>
           <td align="right" ><font style="font-style:normal;font-size:12px"color="brown" ><bean:message key="label.priority_num" ></bean:message></font></td>
                 <core:choose>
                 <core:when test="${requestScope.SIDel!=null}">
                	<td><html:text property="priority_num" size="8" value="${requestScope.SIDel.priorityNo}" style="border:none;background:transparent" readonly="true"></html:text></td>
                 </core:when>
                 <core:otherwise>
					<td><html:text property="priority_num" size="8" value="" style="border:none;background:transparent" readonly="true"></html:text></td>
                 </core:otherwise>
                 </core:choose>
         </tr>
         <%System.out.println("line 3"); %>
        <tr>
          <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.from_acc_num" ></bean:message></font></td>
          <core:choose>
          <core:when test="${requestScope.SIDel!=null}">
          <td><html:text property="from_ac_ty" size="8" value="${requestScope.SIDel.frommodAbbrv}" style="border:none;background:transparent" readonly="true"></html:text></td>
          </core:when>
          <core:otherwise>                   
          <td><html:text property="from_ac_ty" size="8" value="" style="border:none;background:transparent" readonly="true"></html:text></td>
          </core:otherwise>
          </core:choose>
          <core:choose>
          <core:when test="${requestScope.SIDel!=null}">     
          <td><html:text property="from_ac_no" size="8"  value="${requestScope.SIDel.fromAccNo}"style="border:none;background:transparent" readonly="true"></html:text></td>
          </core:when>
          <core:otherwise>
          <td><html:text property="from_ac_no" size="8"  value="" style="border:none;background:transparent" readonly="true"></html:text></td>
          </core:otherwise>
          </core:choose>
         
        </tr>
        <%System.out.println("line 4"); %>
        <tr>
        
           <td align="right" ><font style="font-style:normal;font-size:12px" color="brown" ><bean:message key="label.to_acc_num" ></bean:message></font></td>
           <core:choose>
           <core:when test="${requestScope.SIDel!=null}">   
           <td><html:text property="to_ac_ty" size="8"  value="${requestScope.SIDel.tomodAbbrv}" style="border:none;background:transparent" readonly="true"></html:text></td>
           </core:when>
           <core:otherwise>
           <td><html:text property="to_ac_ty" size="8"  value="" style="border:none;background:transparent" readonly="true"></html:text></td>
           </core:otherwise>
           </core:choose>        
           <core:choose>
           <core:when test="${requestScope.SIDel!=null}">   
           <td><html:text property="to_ac_no" size="8"  value="${requestScope.SIDel.toAccNo}" style="border:none;background:transparent" readonly="true"></html:text></td>
           </core:when>
           <core:otherwise>
           <td><html:text property="to_ac_no" size="8"  value="" style="border:none;background:transparent" readonly="true"></html:text></td>
           </core:otherwise>
          </core:choose>
           
        </tr>
            <%System.out.println("line 5");%>
        <tr>
            <%String loanoptions=(String) request.getAttribute("loanoption"); 
              System.out.println("Loan option value"+loanoptions);            
            %>           
                <td align="right" ><font style="font-style:normal;font-size:12px" color="brown" ><bean:message key="label.loan_option" ></bean:message></font></td>
                <core:choose>
                <core:when test="${requestScope.loanoption!=null}">   
                <td><html:text property="loan_option" size="8"  value="${requestScope.loanoption}" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:when>
                <core:otherwise>
                <td><html:text property="loan_option" size="8"  value="" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:otherwise>
                </core:choose>           
            
         </tr>
         <%System.out.println("line 6"); %>
        <tr>
           
                <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.due_date" ></bean:message></font></td>
                 <core:choose>
                 <core:when test="${requestScope.SIDel!=null}">   
                    <td><html:text property="due_date" size="8" value="${requestScope.SIDel.dueDt}" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:when>
                <core:otherwise>
                    <td><html:text property="due_date" size="8" value="" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:otherwise>
                </core:choose>
            
        </tr>
         <%System.out.println("line 7"); %>        
        <tr>
            
                <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.period(m)" ></bean:message></font></td>
                 <core:choose>
                 <core:when test="${requestScope.SIDel!=null}">   
                     <td><html:text property="period_mon" size="8" value="${requestScope.SIDel.periodMonths}" style="border:none;background:transparent" readonly="true"></html:text></td>
                 </core:when>
                 <core:otherwise>
                     <td><html:text property="period_mon" size="8" value="" style="border:none;background:transparent" readonly="true"></html:text></td>
                 </core:otherwise>
                 </core:choose>                
               <td><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.days"></bean:message></font></td>
                <core:choose>
                <core:when test="${requestScope.SIDel!=null}">   
                    <td><html:text property="days" size="4" value="${requestScope.SIDel.periodDays}" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:when>
                <core:otherwise>
                    <td><html:text property="days" size="4" value="" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:otherwise>
                </core:choose>
           <%System.out.println("line 8"); %>
        </tr>
        <tr>
           
                <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.amount" ></bean:message></font></td>
                 <core:choose>
                 <core:when test="${requestScope.SIDel!=null}">                    
                    <td><html:text property="amount" size="8" value="${requestScope.SIDel.amount}" style="border:none;background:transparent" readonly="true"></html:text></td>
                 </core:when>
                <core:otherwise> 
                    <td><html:text property="amount" size="8" value="" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:otherwise>
                </core:choose>
           
       </tr>
       <%System.out.println("line 9"); %>
       <tr>
           
                <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.amount_adjusted" ></bean:message></font></td>
                <core:choose>
                <core:when test="${requestScope.SIDel!=null}">    
                  <td><html:text property="amount_adjusted" size="8" value="${requestScope.SIDel.amtAdj}" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:when>
                <core:otherwise>
                  <td><html:text property="amount_adjusted" size="8" value="" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:otherwise>
                </core:choose>
           
       </tr>   
       <%System.out.println("line 10"); %>
       <tr>
           
                <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.no_of_times_to_exec"></bean:message></font></td>
                <core:choose>
                <core:when test="${requestScope.SIDel!=null}">   
                    <td><html:text property="no_of_times_exec" size="8" value="${requestScope.SIDel.noExec}" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:when>
                <core:otherwise>
                    <td><html:text property="no_of_times_exec" size="8" value="" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:otherwise>
                </core:choose>
           
       </tr>
      <%System.out.println("line 11"); %>
       <tr>
           
                <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.last_exec_date" ></bean:message></font></td>
                 <core:choose>
                 <core:when test="${requestScope.SIDel!=null}">   
                    <td><html:text property="last_exec_date" size="8" value="${requestScope.SIDel.lastExec}" style="border:none;background:transparent" readonly="true"></html:text></td>
                 </core:when>
                 <core:otherwise>
                     <td><html:text property="last_exec_date" size="8" value="" style="border:none;background:transparent" readonly="true"></html:text></td>
                 </core:otherwise>
                </core:choose> 
           
       </tr>
       <%System.out.println("line 12"); %>
       <tr>
           
               <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.expiry_days" ></bean:message></font></td>
                <core:choose>
                <core:when test="${requestScope.SIDel!=null}">   
                   <td><html:text property="expiry_days" size="8" value="${requestScope.SIDel.expiryDays}" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:when>
                <core:otherwise>
                   <td><html:text property="expiry_days" size="8" value="" style="border:none;background:transparent" readonly="true"></html:text></td>
                </core:otherwise>
                </core:choose>
           
       </tr>
       <%System.out.println("line 13"); %>
       <tr>
           <html:hidden property="forward" value="error"></html:hidden>
           <html:hidden property="delete_ins" styleId="delete_ins"></html:hidden>
           <html:hidden property="sucess_del" styleId="sucess_delt"></html:hidden>
            <html:hidden property="sucess_verify" styleId="sucess_verified"></html:hidden>
            <%if(deleted!=null){ %>
            
             <td align="right"><html:submit disabled="true" styleClass="ButtonBackgroundImage">DELETE</html:submit></td>
           <% }else{%> 
           <%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
           <td align="right"><html:button property="del" onclick="button_delete('delete');" styleClass="ButtonBackgroundImage">DELETE</html:button></td>
             <%}else{ %>
             <td align="right"><html:button property="del" disabled="true" onclick="button_delete('delete');" styleClass="ButtonBackgroundImage">DELETE</html:button></td>
             <%} %>
           
           
           <%} %>
           <td><html:button property="clear"  onclick="callClear()" styleClass="ButtonBackgroundImage">CLEAR</html:button></td>
           
           <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
			<td><html:submit  onclick="button_verify('verify');" styleClass="ButtonBackgroundImage">VERIFY</html:submit></td>
			<%}else{ %>
        	<td><html:submit disabled="true"  onclick="button_verify('verify');" styleClass="ButtonBackgroundImage">VERIFY</html:submit></td>
        	<%} %>
           
      </tr>
    </table>
   </html:form>  
   <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
 </td>
<td>
     <!--<table align="right">
        <jsp:include page="/CommonWebPages/Personnel.jsp"></jsp:include>
     </table>
-->
    
</td>

 </table>
</body>
</html>