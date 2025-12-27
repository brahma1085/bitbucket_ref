<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.lockers.LockerMasterObject" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c"    uri="/WEB-INF/c-rt.tld"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.StringTokenizer" %>


<html>
  <head>
  <script type="text/javascript">
            
   function validate(txt,id)
   {
      
      if(txt=="")
      {  
       alert(id);  
      }             
      
   }
           
           
function set(target)
{
  	if(document.forms[0].acn_no.value=='')
  	{ 
  		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
  		document.forms[0].acn_no.focus();
  		
  	}
  	else if(document.forms[0].lockerNo.value=='')
  	{
  		document.forms[0].validateFlag.value="Enter Valid Locker Number";
  	}
  	else if(document.forms[0].lockerType.value=='')
  	{
  		document.forms[0].validateFlag.value="Enter Valid Locker Type";
  	}
  	else if(document.forms[0].txt_operatedby.value=='')
  	{
  		document.forms[0].validateFlag.value="Enter Operated Name";
  	}
  	else if(document.forms[0].txt_operatedon.value=='')
  	{
  		document.forms[0].validateFlag.value="Enter Operated Date";
  	}
  	else
  	{
  		document.forms[0].forward.value=target;
  		document.forms[0].submit();
  	}	
       
};
           
function onlynumbers()
{
     var cha =   event.keyCode;
	if(cha >= 48 && cha <= 57) 
    {
         return true;
    } 
    else 
    {
        return false;
    }
}        
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
     

      
 function validate_form(thisform)
 {
			with (thisform)
			{
				if (validate_required(email,"Email must be filled out!")==false)
  				{ 
  					email.focus();return false
  				}

				if (validate_required(name,"name must be filled out!")==false)
  				{  
    				name.focus();return false
  				}

			}
 }
 
var current_field;

function focusField(val)
{
	if(current_field == null ) 
	{
		document.forms[0].validateFlag.value="Current Field";
		current_field = val;
	}
	if(val.value=="" && current_field == val)
  	{  
  		document.forms[0].validateFlag.value="You Left The Field Empty"; 
		val.focus();
	 	return false;
	}
	else
	{
		current_field = null;
		return true;
		document.forms[0].submit();
	}
}
  
  function focusBack(acntNum)
  {
	
	if(acntNum!=0 && acntNum.length!=0 && acntNum!="")
	{
		
		document.forms[0].submit();
	}
	else 
	{
		document.forms[0].validateFlag.value="Enter Account Number";
		document.forms[0].acn_no.focus();
		return false;
	}
}   
	
	 
          
    function callClear()
 {
           
          var ele= document.forms[0].elements;
           for(var i=0;i<ele.length;i++){
               if(ele[i].type=="text")
               {
              		 ele[i].value="";
               }
           
           }
           
  }      
           
        </script>
  </head>


 <body bgcolor=#f3f4c8>
 
 <center><h2 class="h2"><bean:message key="lable.operation"></bean:message></h2></center>
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
  
  <html:form action="/LKopLink?pageId=9003" >
      <% System.out.println("Top of JSP"); %>                           
   <body bgcolor="#f2fbdd">
   
   <table>
   <tr><html:text property="validateFlag" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
   <tr>
   <td>
   <table class="txtTable">

       <%!    String s1,s2,s3,s4,s5,timeInValue,timeOutValue;  %>

            <%  
                Date dat = new Date();
                String s = dat.toString();

                StringTokenizer s1 = new StringTokenizer(s, " ");
                if (s1.hasMoreTokens()) {
                    s1.nextToken();
                    s1.nextToken();
                    s1.nextToken();
                    s2 = s1.nextToken();
                    s1.nextToken();
                    s1.nextToken();
                }
                String strDat = s2;
            %>
       

          <tr><td><bean:message key="lab.ac_type"></bean:message> </td>
        <%! ModuleObject[] array_module;
            LockerMasterObject lockermasterobject;
        %>
        
        
        <td><html:select property="combo_types" styleClass="formTextFieldWithoutTransparent">
   		<c:forEach var="parm" items="${requestScope.param}"> 
        <html:option value="1009001" > <c:out value="${parm.moduleAbbrv}"> </c:out> </html:option>
        </c:forEach>
        </html:select></td>

		
        <td><html:text property="acn_no"  onblur="focusBack(this.value)" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'10')" onkeypress="return onlynumbers()"></html:text></td>
        </tr>
 		
 	<% lockermasterobject=(LockerMasterObject)request.getAttribute("lkparams"); %>
   
    
      
      <c:choose>
      <c:when test="${requestScope.lkparams != null}">
      
      <tr>
      <td><bean:message key="lab.acType_num" /></td>
      <td><html:text property="lockerType" value="<%=lockermasterobject.getLockerType() %>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
      <td><html:text property="lockerNo" value="<%=""+lockermasterobject.getLockerNo() %>" readonly="true" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
      </tr>
      
  <!--      <%if(lockermasterobject.getLockerPW()!=null){ %>
     <tr>
      <td><bean:message key="lab.password"></bean:message></td>
      <td><html:password property="txt_password" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=lockermasterobject.getLockerPW()%>" onblur="alert"  ></html:password></td>
      </tr>
	<%} %>	-->																		
      
      <tr><td>
      <bean:message key="lab.operatedby" ></bean:message> </td>
      <td><html:textarea property="txt_operatedby" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=lockermasterobject.getName() %>"></html:textarea><br> </td></tr>
          																		
      <tr>
      <td><bean:message key="lab.operatedon" ></bean:message></td>
      <td><html:text property="txt_operatedon" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=(String)request.getAttribute("date")%>" ></html:text></td>  </tr>

      <% timeInValue=(String)request.getAttribute("timeInValue"); 
         timeOutValue=(String)request.getAttribute("timeOutValue");
	   System.out.println("timeIN Value from Jsp"+timeInValue);
	   System.out.println("timeOut value from Jsp"+timeOutValue);%>
	   
	   
	   
	   <c:choose>
	   
	   <c:when test="${requestScope.timeInValue!=null && requestScope.timeOutValue==null}">
	   <%System.out.println("Line----Number---110"); %> 
	   <tr>
       <td><bean:message key="lab.timein"></bean:message></td>
	   <td><html:text property="txt_timein" styleClass="formTextFieldWithoutTransparent" value="<%=timeInValue%>" readonly="true"></html:text></td>
	   <td><html:button property="tim" onclick="set('timein');" styleClass="ButtonBackgroundImage"><bean:message key="lab.timein"></bean:message></html:button></td>
       </tr>
       
       <tr><td>
       <bean:message key="lab.timeout"></bean:message></td>
       <td><html:text   property="txt_timeout" readonly="true" styleClass="formTextFieldWithoutTransparent" value="<%=strDat%>" ></html:text></td>
       <td><html:button property="tim" onclick="set('timeout');" styleClass="ButtonBackgroundImage" styleId="timeOut"><bean:message key="lab.timeout"></bean:message></html:button></td> </tr>
	  </c:when>
	  
	   <c:when test="${requestScope.timeInValue!=null && requestScope.timeOutValue!=null}">
	   <tr>
       <td><bean:message key="lab.timein"></bean:message></td>
	   <td><html:text property="txt_timein" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=timeInValue%>"></html:text></td>
	   <td><html:button property="tim" onclick="set('timein');" styleClass="ButtonBackgroundImage"><bean:message key="lab.timein"></bean:message></html:button></td>
       </tr>
       <tr><td>
       <bean:message key="lab.timeout"></bean:message></td>
       <td><html:text   property="txt_timeout" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=timeOutValue%>" ></html:text></td>
       <td><html:button property="tim" onclick="set('timeout');" styleClass="ButtonBackgroundImage" styleId="timeOut"><bean:message key="lab.timeout"></bean:message></html:button></td> 
       </tr>
	</c:when>
	
    <c:otherwise>
    
     <%if(timeInValue==null && timeOutValue==null){ %>
    <%System.out.println("Line----Number---137"); %>
    <tr><td><bean:message key="lab.timein"></bean:message></td>
		 <td><html:text property="txt_timein" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=strDat%>"></html:text></td>
		 <td><html:button property="tim" onclick="set('timein');" styleClass="ButtonBackgroundImage"><bean:message key="lab.timein"></bean:message></html:button></td>
    	 </tr>
	   <tr><td>
     	  <bean:message key="lab.timeout"></bean:message></td>
       		<td><html:text   property="txt_timeout" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=strDat%>" ></html:text></td>
       		<td><html:button property="tim" onclick="set('timeout');" styleClass="ButtonBackgroundImage" styleId="timeOut"><bean:message key="lab.timeout"></bean:message></html:button></td> </tr>
			
    <%}else{ %>
    <%System.out.println("Line----Number---148"); %>
    
	   <tr><td><bean:message key="lab.timein"></bean:message></td>
		 <td><html:text property="txt_timein"  styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=timeInValue%>"></html:text></td>
		 <td><html:button property="tim" onclick="set('timein');" styleClass="ButtonBackgroundImage"><bean:message key="lab.timein"></bean:message></html:button></td>
    	 </tr>
	   <tr><td>
     	  <bean:message key="lab.timeout"></bean:message></td>
       		<td><html:text   property="txt_timeout" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=timeOutValue%>" ></html:text></td>
       		<td><html:button property="tim" onclick="set('timeout');"  styleId="timeOut" styleClass="ButtonBackgroundImage"><bean:message key="lab.timeout"></bean:message></html:button></td> </tr>
	
	<%} %>
	 </c:otherwise>

</c:choose>
      
     
       </c:when>
      
       <c:otherwise>
      <tr>
      <td><bean:message key="lab.acType_num" /></td>
      <td><html:text property="lockerType" styleClass="formTextFieldWithoutTransparent" value=""></html:text></td>
      <td><html:text property="lockerNo"  styleClass="formTextFieldWithoutTransparent"  value=""></html:text></td>
      </tr><!--
       
     <tr>
      <td><bean:message key="lab.password"></bean:message></td>
      <td><html:password property="txt_password"  value=""  styleClass="formTextFieldWithoutTransparent" ></html:password></td>
      </tr>
      

	  --><tr><td>
      <bean:message key="lab.operatedby" ></bean:message> </td>
      <td><html:textarea property="txt_operatedby" cols="14" value="" ></html:textarea><br> </td></tr>

       <tr>
       <td><bean:message key="lab.operatedon" ></bean:message></td>
       <td><html:text property="txt_operatedon" styleClass="formTextFieldWithoutTransparent" value="" ></html:text></td>  </tr>

      <tr><td>
      <bean:message key="lab.timein"></bean:message></td>
      <td><html:text property="txt_timein" value="<%=strDat%>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
      <td><html:button property="tim" onclick="set('timein');" styleClass="ButtonBackgroundImage"><bean:message key="lab.timein"></bean:message></html:button></td>
       <!--<td><html:button property="butt_timein" value="TimeIn" title="time in" onclick="submit()"></html:button></td>-->
      </tr>

       <tr><td>
       <bean:message key="lab.timeout"></bean:message></td>
       <td><html:text   property="txt_timeout" value="<%=strDat%>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
       <td><html:button property="tim" onclick="set('timeout');" styleId="timeOut" styleClass="ButtonBackgroundImage"><bean:message key="lab.timeout"></bean:message></html:button></td> 
       <!--<td><html:button value="TimeOut" property="butt_timeout" onclick="submit()"></html:button></td>--></tr>
  </c:otherwise>
  </c:choose>

  
      <tr><td>
      <html:hidden property="hiddenTime"></html:hidden>
      <html:hidden property="forward" value="error/"></html:hidden>
      <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <html:button property="der" onclick="set('submit');" styleClass="ButtonBackgroundImage"><bean:message key="label.submit" ></bean:message>  </html:button>
             <%}else{ %>
             <html:button property="der" disabled="true" onclick="set('submit');" styleClass="ButtonBackgroundImage"><bean:message key="label.submit" ></bean:message>  </html:button>
             <%} %>
      <%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
            <html:button property="degf" onclick="set('delete');" styleClass="ButtonBackgroundImage"><bean:message key="label.delete" ></bean:message>  </html:button>
             <%}else{ %>
              <html:button property="degf" disabled="true" onclick="set('delete');" styleClass="ButtonBackgroundImage"><bean:message key="label.delete" ></bean:message>  </html:button>
             <%} %>
     
 	  <html:button property="clr" styleClass="ButtonBackgroundImage" onclick="callClear()"><bean:message key="label.clear" ></bean:message></html:button>	
      </td> </tr>
      </table>
</td>
</tr>
</table>
        
  
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>