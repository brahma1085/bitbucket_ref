<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>    
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>


    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.lockers.LockerMasterObject"%>

<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>

<script type="text/javascript">

function setForward(fwdVal)
{
	if(document.forms[0].lkacNum.value!='')
	{
		document.forms[0].forward.value=fwdVal;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
		document.forms[0].lkacNum.focus();
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
     


 

function set(fwdVal)
{
	var conVar=confirm("Do You Want To Verify");
	if(conVar)
	{
		document.forms[0].forward.value=fwdVal;
		document.forms[0].submit()
	}

	else return false;

}

function Validate()
{
	if(document.forms[0].lkacNum.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
	}
	else if(document.forms[0].lkacNum.value==0)
	{
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
	}
	else
	{
		document.forms[0].submit();
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

</script>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
</head>
<body class="Mainbody">
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


 <center><h2 class="h2">LockerIssueVerification</h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/lockers/LockerIssueVerificationLink?pageId=9030">

<%!LockerMasterObject acntDetails; %>
<% acntDetails=(LockerMasterObject)request.getAttribute("LockerMasterObject"); %>
    
        <%! ModuleObject[] array_module;
            ModuleObject[] modobj;
            ModuleObject[] modobjIntro;
            LockerMasterObject lockermasterobject;
            String[] rentExpirydate;
         %>
         
         
<table class="txtTable">

<tr><html:text property="validateFlag" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
<tr>
<td>
	<table class="txtTable">
      <tr>
      <td><bean:message key="lab.ac_type"></bean:message> </td>
      <td><html:select property="lkacType" styleClass="formTextFieldWithoutTransparent">
    		<c:forEach var="parm" items="${requestScope.param}"> 
      			<html:option value="${param.moduleCode}" > <c:out value="${parm.moduleAbbrv}"> </c:out> </html:option>
   			</c:forEach>		  
          </html:select>
       </td>
       <td><html:text property="lkacNum" size="8" styleClass="formTextFieldWithoutTransparent" styleId="lkacNumber" onkeyup="numberlimit(this,'10')" onkeypress="return onlynumbers()" onblur="setForward('frmAcntNum')"></html:text> </td>
       </tr>
       
 <%if(acntDetails==null){ %>      
 <tr>
        <td><bean:message key="label.cid"></bean:message> </td>
        <td><html:text property="txt_cid" size="8" value="" styleClass="formTextFieldWithoutTransparent"></html:text></td>
      </tr>

	   <tr>
	   
	   <td><bean:message key="label.tabView"></bean:message> </td>
	   <td><html:button property="tabView" value="LockerTable" styleClass="ButtonBackgroundImage"></html:button></td>
	   
	   </tr> 

       <tr><td><bean:message key="lab.acType_num"></bean:message> </td>

<td><html:select property="lkType" styleClass="formTextFieldWithoutTransparent">


<c:forEach var="lktypes" items="${requestScope.lktypes}">              
              <html:option value="${lktypes}" >   </html:option>
</c:forEach>
</html:select></td>

<td><html:text property="lkNum" size="8" styleClass="formTextFieldWithoutTransparent"  ></html:text> </td>
          </tr>
          
          <tr><td><bean:message key="label.keyNo&Rent"></bean:message> </td>
          <td><html:text property="lkKey" size="8" value="" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
          </tr>
           
         
              <!--<tr>
              <td><bean:message key="lab.password"></bean:message> </td>
              <td><html:text property="pass" size="8" styleClass="formTextFieldWithoutTransparent" value=""></html:text> </td>
         	  </tr>
         	  -->
         	  
         	  <tr><td>Period:</td>
              <td><html:text property="months"  size="8" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
              <td><html:text property="days"    size="8" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
          </tr>



           <tr><td><bean:message key="lab.allotExpiry"></bean:message> </td>
              <td><html:text styleClass="formTextFieldWithoutTransparent" property="allotDate" size="8"></html:text> </td>
             
          
              <td><html:text styleClass="formTextFieldWithoutTransparent" property="expDate"  size="8"></html:text> </td>
             <tr>
              <td><bean:message key="label.rentAmount"></bean:message> </td>
              <td><html:text styleClass="formTextFieldWithoutTransparent" property="lkRent"   size="8"></html:text> </td>
            </tr>
            
            
<tr>
<td> TranferAccount From:</td>
<td>
<html:text property="transferAcntType" styleClass="formTextFieldWithoutTransparent" size="8"></html:text>
</td>

<%System.out.println("111111$$111119"); %>


<td><html:text property="transferAcntNum"  styleClass="formTextFieldWithoutTransparent" size="8"></html:text></td>
</tr>
              
          

           <tr><td><bean:message key="lab.operatingInstr"></bean:message> </td>
               <td><html:select property="opInstr" styleClass="formTextFieldWithoutTransparent"  >
                               <html:option value="Individual"></html:option>
                               <html:option value="E or S"></html:option>
                               <html:option value="Jointly"></html:option>
                    </html:select></td>
           </tr>


           <tr><td><bean:message key="label.membNo"></bean:message> </td>
              <td><html:select property="membType" styleClass="formTextFieldWithoutTransparent">
              <html:option value="1001001">SH</html:option>
              </html:select></td>
              
<%System.out.println("111111$$111122119"); %>
              
               
<%System.out.println("111111$$111119"); %>               
            <td><html:text styleClass="formTextFieldWithoutTransparent" property="intrNum"  size="8"></html:text> </td>
          </tr>

          <tr><td><bean:message key="label.nomReg"></bean:message> </td>
              <td><html:text property="nomReg" size="8" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
          </tr>

          <tr><td><bean:message key="label.details"></bean:message> </td>
              <td><html:select property="details" styleClass="formTextFieldWithoutTransparent" onchange="Validate()"  >
              	  <html:option value="Select"></html:option>
              	  <html:option value="Personal"></html:option>
              	  <html:option value="Introducer"></html:option>
              </html:select> </td>
          </tr>

<%System.out.println("11&&&&&&&&&119"); %>
          <tr><td><bean:message key="label.autoExtn"></bean:message> </td>
              <td><html:select property="autoExtn" styleClass="formTextFieldWithoutTransparent">
                  <html:option value="yes">YES</html:option>
         
              </html:select>

               </td>
          </tr>      
       
       <%} %>
<%if(acntDetails!=null){ %>
 <tr>
        <td><bean:message key="label.cid"></bean:message> </td>
        <td><html:text property="txt_cid" size="10" readonly="true" value="<%=""+acntDetails.getCid() %>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
      </tr>

	   <tr>
	   
	   <td><bean:message key="label.tabView"></bean:message> </td>
	   <td><html:button property="tabView" value="LockerTable" styleClass="ButtonBackgroundImage" onclick="getTable()"></html:button></td>
	   
	   </tr> 

       <tr><td><bean:message key="lab.acType_num"></bean:message> </td>

<td><html:select property="lkType" styleClass="formTextFieldWithoutTransparent">


<c:forEach var="lktypes" items="${requestScope.lktypes}">              
              <html:option value="${lktypes}" > <c:out value="<%=acntDetails.getLockerType() %>"></c:out>  </html:option>
</c:forEach>
</html:select></td>

<td><html:text property="lkNum" size="10" readonly="true" styleClass="formTextFieldWithoutTransparent" value="<%=""+acntDetails.getLockerNo() %>" ></html:text> </td>
          </tr>
          
          <tr><td><bean:message key="label.keyNo&Rent"></bean:message> </td>
          <td><html:text property="lkKey" size="10" readonly="true" value="<%=""+acntDetails.getKeyNo() %>" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
          </tr>
           
<%System.out.println("111111111111111"); %>           
              <!--<tr>
              <td><bean:message key="lab.password"></bean:message> </td>
              <td><html:text property="pass" size="8" styleClass="formTextFieldWithoutTransparent" value=""></html:text> </td>
         	  </tr>
         	  -->
<%System.out.println("111111111111111"); %>         	  

           <tr><td>Period:</td>
              <td><html:text property="months"  size="10" readonly="true" styleClass="formTextFieldWithoutTransparent" value="<%=""+acntDetails.getReqdMonths() %>"></html:text> </td>
              <td><html:text property="days"    size="10" readonly="true" value="<%=""+acntDetails.getRequiredDays() %>" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
          </tr>

<%System.out.println("11111111111119"); %>

           <tr><td><bean:message key="lab.allotExpiry"></bean:message> </td>
              <td><html:text styleClass="formTextFieldWithoutTransparent" property="allotDate" value="<%=acntDetails.getAllotDate() %>" size="10" readonly="true"></html:text> </td>
             
          
              <td><html:text styleClass="formTextFieldWithoutTransparent" property="expDate" value="<%=acntDetails.getRentUpto() %>" size="10" readonly="true"></html:text> </td>
             <tr>
              <td><bean:message key="label.rentAmount"></bean:message> </td>
              <td><html:text styleClass="formTextFieldWithoutTransparent" property="lkRent"  value="<%=""+acntDetails.getRentColl() %>" size="10" readonly="true"></html:text> </td>
            </tr>
            
<%System.out.println("11111111111119"); %>    

<tr>
<td>Receipt:</td>
<td><html:text property="receiptDetails" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true" value="<%=acntDetails.getRentBy() %>"></html:text> </td>

</tr>        
<tr>

<%if(acntDetails.getRentBy().equalsIgnoreCase("T")){ %>
<td> TranferAccount From:</td>
<td>
<html:text property="transferAcntType" value="<%=acntDetails.getTransAcType() %>" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true"></html:text>
</td>

<%System.out.println("111111$$111119"); %>


<td><html:text property="transferAcntNum" value="<%=""+acntDetails.getTransAcNo()%>" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true"></html:text></td>

<%}if(acntDetails.getRentBy().equalsIgnoreCase("C")){ %>              
 <td>Scroll number:</td>
 <td><html:text property="transferAcntNum" value="<%=""+acntDetails.getTransAcNo()%>" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true"></html:text></td>
 </tr>	         
<%} %>
           <tr><td><bean:message key="lab.operatingInstr"></bean:message> </td>
               <td><html:text property="opInstr" styleClass="formTextFieldWithoutTransparent" value="<%=""+acntDetails.getOprInstrn()%>" size="10" readonly="true"> </html:text></td>
           </tr>


           <tr><td><bean:message key="label.membNo"></bean:message> </td>
              <td><html:select property="membType" styleClass="formTextFieldWithoutTransparent">
              <html:option value="1001001">SH</html:option>
              </html:select></td>

				<td><html:text styleClass="formTextFieldWithoutTransparent" property="membAc" value="<%=""+acntDetails.getMemberNo() %>" size="10" readonly="true"></html:text> </td>
			</tr>

 			<tr><td><bean:message key="label.Intro"></bean:message>   </td>
           
             <td><html:text property="intrType" value="<%=""+acntDetails.getIntrAcTy() %>" readonly="true" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
               
               
            <td><html:text styleClass="formTextFieldWithoutTransparent" property="intrNum" value="<%=""+acntDetails.getIntrAcNo() %>"  size="10" readonly="true"></html:text> </td>
          </tr>
          
          
          
          
          <tr><td><bean:message key="label.nomReg"></bean:message> </td>
              <td><html:text property="nomReg" size="10" readonly="true" styleClass="formTextFieldWithoutTransparent" value="<%=""+acntDetails.getNomRegNo()%>" ></html:text> </td>
          </tr>

          <tr><td><bean:message key="label.details"></bean:message> </td>
              <td><html:select property="details" styleClass="formTextFieldWithoutTransparent" onchange="Validate()"  >
              	  <html:option value="Select"></html:option>
              	  <html:option value="Personal"></html:option>
              	  <html:option value="Introducer"></html:option>
              	  
              </html:select> </td>
          </tr>

<%System.out.println("11&&&&&&&&&119"); %>
          <tr><td><bean:message key="label.autoExtn"></bean:message> </td>
              <td><html:text property="autoExtn" styleClass="formTextFieldWithoutTransparent" value="<%=""+acntDetails.getAutoExtn()%>" size="10" readonly="true"></html:text>

               </td>
          </tr>
        	
        	<tr><td>
        	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <html:button property="button_submit" onclick="set('verify')" styleClass="ButtonBackgroundImage"><bean:message key="label.verify" ></bean:message></html:button>
             <%}else{ %>
            <html:button property="button_submit" disabled="true" onclick="set('verify')" styleClass="ButtonBackgroundImage"><bean:message key="label.verify" ></bean:message></html:button>
             <%} %>
         	
       		<html:button property="buttonClear"  styleClass="ButtonBackgroundImage" onclick="callClear()"><bean:message key="label.clear" ></bean:message></html:button>
            </td></tr>
          
          
     <%} %>     
     
</table>     
     </td>
     
 		
        <html:hidden property="forward"></html:hidden>
 
 <td align="left" valign="top"> 
 
 
  <%    String jspPath = (String) request.getAttribute("flag");   %>
            
            <c:if  test="${requestScope.flag !=null}">
          		<jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
            </c:if>  
 
      <%System.out.println("111111$$111119"); %>       
       </td> </tr> 
        </table>      


</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>