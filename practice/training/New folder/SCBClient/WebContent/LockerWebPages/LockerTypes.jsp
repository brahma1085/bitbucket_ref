<%@ page import="com.scb.lk.forms.LockerTypesForm" %>
<%@ page import="masterObject.lockers.LockerDetailsObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="diaplay" uri="http://displaytag.sf.net" %>
<%@page import="java.util.Map"%>
<html>
  <head>
        <script type="text/javascript">
function clearForms()
{
	 var ele= document.forms[0].elements;
           for(var i=0;i<ele.length;i++)
           {
               if(ele[i].type=="text")
               {
                  
               ele[i].value="";
               }
           
           }

}  
        
function changecolor(code) { 

document.bgColor=code


}           
           //fun 1
            function validate(txt,id)
            {
           		 if(txt.length==0)
          		 {  
          		 
          		 document.forms[0].validateFlag.value="Please enter"+id+" ";
           			txt.focus(); 
           			///document.forms[0].validateFlag.value=document.getElementById("id");
           			document.getElementById("id").focus();
 		        }  
            }
           
           
           //fun4
           function validate1(id){
           
           if((document.forms[0].lockerDepth.value=="")||(document.forms[0].lockerHeight.value=="")||(document.forms[0].lockerLength.value=="")||(document.forms[0].lockerDepth.value==""))
           {
           
           	 document.forms[0].validateFlag.value="Some fields are null"; 
				return false;           		
				
				
           
            } else {
             document.forms[0].forward.value=id;
	            	
    					return true;        	
           }
           }
           
           //fun 5
           
          
           
           //fun6  used to call from submit button
           
 function set(target)
 {
  if(document.forms[0].lockerType.value=='')
  {
  	document.forms[0].validateFlag.value="Enter Locker Type";
  }
  else if(document.forms[0].description.value=='')
  {
  	document.forms[0].validateFlag.value="Enter Locker Description";
  }
  else if(document.forms[0].lockerHeight.value=='')
  {
    document.forms[0].validateFlag.value="Enter Locker Height";
  }
  else if(document.forms[0].lockerLength.value=='')
  {
  	document.forms[0].validateFlag.value="Enter Locker Length";
  }
  else if(document.forms[0].lockerDepth.value=='')
  {
  	document.forms[0].validateFlag.value="Enter Locker Depth";
  }
  else
  {
   		document.forms[0].forward.value=target;
   		document.forms[0].submit();
   }
}
		
           
           
           
           function calls(input){
			var index = -1, i =0, found = false ;
			
			
			while ( i< input.form.length && index== -1  ){
					
					if ( input.form[i] == input ){
					 index = i;
					 }
					 
					 else{
					 i++;
					 } 
			} 
		
	}
           
           
           
          //function to validate wheather input is numbers r not
          
          
        function  getNumbersOnly()
        {
			var cha = event.keyCode
		
			if((cha >= 46 && cha < 58)) 
			{
				return true ;
            }
            return false ;
        }
        
        function setCombo()
        {
        	document.forms[0].forward.value='';
        	document.forms[0].submit();
        
        } 
          
        </script>
  </head>

  <BODY> 
  <center><h2 class="h2"><bean:message key="lable.types"></bean:message></h2></center>
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
  <html:form action="/LKTypesLink?pageId=9005">
     
<table>
<tr><html:text property="validateFlag" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
	
  <tr>
     <td>          
<table class="txtTable">      

  <tr>

 
 <% String array[]={"small","medium","large"};
     pageContext.setAttribute("array",array); 
 %>
 
 
 
 
 
 <tr><td><bean:message key="lab.lock_types"></bean:message>  </td>
 
 
 <td><html:select property="combo_types" onchange="setCombo()" styleClass="formTextFieldWithoutTransparent">
			<html:option value="SELECT">Select</html:option>
			
			<c:forEach var="lktypes" items="${requestScope.lktypes}">              
            <html:option value="${lktypes}" > <c:out value="${lktypes}"></c:out>  </html:option>
			</c:forEach>
	    	
	    	<html:option value="addnew"></html:option>
  </html:select></td></tr>

  


             <% System.out.println("'m here frm LKTjsp"); %>
             <%! LockerTypesForm lk;
             LockerDetailsObject lockerdetailsobject;   %>
              
            <%lockerdetailsobject=(LockerDetailsObject)request.getAttribute("key"); %>
             
 <% System.out.println("here is the error 145"); %>
<c:choose>
			<c:when test="${requestScope.key!=null}">
			
			<% System.out.println("here is the error 149"); %>
            <tr>

            <td><bean:message key="lab.lock_types"></bean:message> </td>
            <td><html:text property="lockerType"   value="<%=lockerdetailsobject.getDescription()%>" styleClass="formTextFieldWithoutTransparent"></html:text></td></tr>


		
			<tr>	
			<td><bean:message key="label.Desc"></bean:message></td>
			
			
			<td><html:text property="description" styleClass="formTextFieldWithoutTransparent"  value="<%=lockerdetailsobject.getDescription()%>" ></html:text></td></tr>

     		<tr><td><bean:message key="lable.height"></bean:message> </td>
            <td><html:text property="lockerHeight" styleClass="formTextFieldWithoutTransparent" styleId="height" value="<%=lockerdetailsobject.getLockerHeight()%>" ></html:text></td>
         
            <tr><td><bean:message key="lable.length"></bean:message> </td>
            <td><html:text property="lockerLength"  styleClass="formTextFieldWithoutTransparent" value="<%=lockerdetailsobject.getLockerLength()%>" ></html:text></td>
            </tr>
             
             <tr><td><bean:message key="lable.depth"></bean:message> </td>
             <td><html:text property="lockerDepth" styleClass="formTextFieldWithoutTransparent"  value="<%=lockerdetailsobject.getLockerDepth()%>" ></html:text></td></tr>
			
			<tr>
			<td><html:button property="delete" onclick="set('delete')"  styleClass="ButtonBackgroundImage"><bean:message key="label.delete"></bean:message></html:button></td>
            <td><html:button property="clear" onclick="clearForms()" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:button></td>
             	  
			</tr>
</c:when>
			
<c:when test="${requestScope.key==null}">     

			
			<%System.out.println("inside null"); %>
 
			<tr><td><bean:message key="lab.lock_types"></bean:message> </td>
            <td><html:text property="lockerType"  styleClass="formTextFieldWithoutTransparent"  value=""  onblur="validate(this.value,'lockerType')" ></html:text></td></tr>

			<tr><td><bean:message key="label.Desc"></bean:message></td>
			<td><html:text property="description" styleClass="formTextFieldWithoutTransparent"  value=""  onblur="validate(this.value,'description')" ></html:text></td></tr>

     		<tr><td><bean:message key="lable.height"></bean:message> </td>
            <td><html:text property="lockerHeight" styleClass="formTextFieldWithoutTransparent" styleId="height" value=""  onblur="validate(this,height)" ></html:text> </td></tr>
            
            <tr><td><bean:message key="lable.length"></bean:message> </td>
            <td><html:text property="lockerLength" styleClass="formTextFieldWithoutTransparent" value=""  onblur="validate(this.value,'length')" ></html:text></td>
            </tr>
             
             <tr><td><bean:message key="lable.depth"></bean:message> </td>
             <td><html:text property="lockerDepth" styleClass="formTextFieldWithoutTransparent"  value=""   onblur="calls(this)"></html:text></td></tr>

			<tr>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            <td><html:button property="insert"  onclick="set('insert')"  styleClass="ButtonBackgroundImage" ><bean:message key="label.insert"></bean:message></html:button></td>
                  <%}else{ %>
              <td><html:button property="insert"  onclick="set('insert')" disabled="true" styleClass="ButtonBackgroundImage" ><bean:message key="label.insert"></bean:message></html:button></td>
                <%} %>
			
				
				<td><html:button property="clear" onclick="clearForms()" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:button></td>
             	 	
			</tr>
</c:when>
             
</c:choose>
     		 <% System.out.println("in d end of lktypesJSP"); %>
             <tr>
              	 <td><html:hidden property="forward" styleClass="ButtonBackgroundImage"></html:hidden></td>
    				            
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