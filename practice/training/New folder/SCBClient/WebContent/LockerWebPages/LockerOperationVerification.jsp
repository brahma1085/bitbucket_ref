<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>

<%@page import="java.util.Date"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.lockers.LockerMasterObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">


function set(target){

var conVar=confirm("Are You Want To Verify??");
if(conVar){
document.forms[0].forward.value=target;
document.forms[0].submit();
}
else {
return false;
}
}


function setForward(val)
{
	if(document.forms[0].acn_no.value!='')
	{
		document.forms[0].forward.value=val;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
		document.forms[0].acn_no.focus();
	}
}




function callClear()
 {
           
          var ele= document.forms[0].elements;
           for(var i=0;i<ele.length;i++){
               if(ele[i].type=="text"){
                  
               ele[i].value="";
               }
           
           }
           
  }


function onlynumbers()
 {
       var cha =   event.keyCode;
		if ( cha >= 48 && cha <= 57  )
        {
           return true;
        } 
        else 
        {
        	return false;
        }
			   		 
 }
 function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha <= 58  ) ) {
				return true ;
                
            	}
            else{
              	alert("Alphabets are not allowed!!!!!!!Please enter numbers only ");
              	return false ;
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
     
 
</script>

</head>


<body>
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

 <center><h2 class="h2">LockerOperationVerification</h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/lockers/LockerOperationVerificationLink?pageId=9031">


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

		
        <td><html:text property="acn_no"  onblur="setForward('acntNum')" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'10')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
        </tr>
 		
 	<% lockermasterobject=(LockerMasterObject)request.getAttribute("lkparams"); %>
   
    
      
      <c:choose>
      <c:when test="${requestScope.lkparams != null}">
   		
   	 <tr>
      <td><bean:message key="lab.acType_num" /></td>
      <td><html:text property="lockerType" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=lockermasterobject.getLockerType() %>"></html:text></td>
      <td><html:text property="lockerNo"  styleClass="formTextFieldWithoutTransparent"  readonly="true" value="<%=""+lockermasterobject.getLockerNo() %>"></html:text></td>
      </tr>
       
      <!--<tr>
      <td><bean:message key="lab.password"></bean:message></td>
      <td><html:password property="txt_password"  value="" readonly="true"  styleClass="formTextFieldWithoutTransparent" ></html:password></td></tr>
	  
	  
	  
	  --><tr><td>
      <bean:message key="lab.operatedby" ></bean:message> </td>
      <td><html:textarea property="txt_operatedby" readonly="true" value="<%=lockermasterobject.getName() %>" ></html:textarea><br> </td></tr>

       <tr>
       <td><bean:message key="lab.operatedon" ></bean:message></td>
       <td><html:text property="txt_operatedon" styleClass="formTextFieldWithoutTransparent" readonly="true"  value="<%=lockermasterobject.getTrnDate()%>" ></html:text></td>  </tr>

      <tr><td>
      <bean:message key="lab.timein"></bean:message></td>
      <td><html:text property="txt_timein" value="<%=lockermasterobject.getTimeIn() %>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
       </tr>

       <tr><td>
       <bean:message key="lab.timeout"></bean:message></td>
       <td><html:text   property="txt_timeout" value="<%=lockermasterobject.getTimeOut() %>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
  
   
   
   	
 <tr>
 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            <td><html:button property="button_verify"  onclick="set('verify')" styleClass="ButtonBackgroundImage" ><bean:message key="label.verify"></bean:message></html:button></td>
             <%}else{ %>
            <td><html:button property="button_verify" disabled="true" onclick="set('verify')" styleClass="ButtonBackgroundImage" ><bean:message key="label.verify"></bean:message></html:button></td>
             <%} %>
 	
 	<td><html:button property="cdl" styleClass="ButtonBackgroundImage" onclick="callClear()"><bean:message key="label.clear"></bean:message></html:button></td>
 </tr>   		
   		
   	</c:when>
   
   
   <c:otherwise>
      <tr>
      <td><bean:message key="lab.acType_num" /></td>
      <td><html:text property="lockerType" styleClass="formTextFieldWithoutTransparent" value=""></html:text></td>
      <td><html:text property="lockerNo"  styleClass="formTextFieldWithoutTransparent"  value=""></html:text></td>
      </tr>
      
      <!--
       
      <tr>
      <td><bean:message key="lab.password"></bean:message></td>
      <td><html:password property="txt_password"  value=""  styleClass="formTextFieldWithoutTransparent" ></html:password></td></tr>
	  
	  -->
	  <tr><td>
      <bean:message key="lab.operatedby" ></bean:message> </td>
      <td><html:textarea property="txt_operatedby"  value="" cols="14"></html:textarea><br> </td></tr>

       <tr>
       <td><bean:message key="lab.operatedon" ></bean:message></td>
       <td><html:text property="txt_operatedon" styleClass="formTextFieldWithoutTransparent" value="" ></html:text></td>  </tr>

      <tr><td>
      <bean:message key="lab.timein"></bean:message></td>
      <td><html:text property="txt_timein" value="<%=strDat%>" onkeypress=" return numbersOnly()"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
       </tr>

       <tr><td>
       <bean:message key="lab.timeout"></bean:message></td>
       <td><html:text   property="txt_timeout" value="<%=strDat%>" onkeypress=" return numbersOnly()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
     
     	 <tr>
      	<td><html:button property="cdl" styleClass="ButtonBackgroundImage" onclick="callClear()"><bean:message key="label.clear"></bean:message></html:button></td>
      	
      </tr>
     
      </c:otherwise>
      
     
  </c:choose>

  
      <tr>
      <td>
      	<html:hidden property="forward" value="error/"></html:hidden>
      	
      </td> 
      </tr>
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