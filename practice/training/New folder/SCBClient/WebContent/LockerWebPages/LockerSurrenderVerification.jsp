
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%@page import="java.util.Map"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c"    uri="/WEB-INF/c-rt.tld"%>

<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.lockers.LockerMasterObject"%>




<html>
<head>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
 
<script type="text/javascript">


function setDetails()
{
	if(document.forms[0].txt_acNo.value!='')
	{
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
		document.forms[0].txt_acNo.focus();
	}

}


function callClear(){
           
          var ele= document.forms[0].elements;
           for(var i=0;i<ele.length;i++){
               if(ele[i].type=="text"){
                  
               ele[i].value="";
               }
           
           }
           
           };
           

function set(target)
{
	if(document.forms[0].txt_acNo.value!='')
	{
		document.forms[0].forward.value=target;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid LockerA/c Number";
		document.forms[0].txt_acNo.focus();
	}
	
};



function focusBack(flagValue)
{
	var acntNum=document.forms[0].txt_acNo.value;

	if(acntNum!=0 && acntNum.length!=0 && acntNum!="")
	{
		document.forms[0].forward.value=flagValue;
		document.forms[0].submit(); 
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid LockerA/c Number";
		document.forms[0].txt_acNo.focus();
		return false;
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
     



function getHelp(){

var key=event.keyCode;

if(key==96){
window.open("http://localhost:8080/SCBClient/MyHelpLink.do?pageId=9025","Popup");
return false;
}
else return false;
}

</script>
</head>

<body  class="Mainbody">
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

 <center><h2 class="h2"><bean:message key="lable.surrender"></bean:message></h2></center>
               <% System.out.println("Begging of jsp page"); %>
               
               <%! ModuleObject[] array_module;
                   LockerMasterObject lockermasterobject;
               %>
			
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
			<html:form action="/lockers/LockerSurrenderVerificationLink?pageId=9033">
			               
			<table class="txtTable"> 
			<tr><html:text property="validateFlag" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
	
			<tr>	
			<td>
               <table class="txtTable"><% System.out.println("hi from lk surrender"); %>
               <tr><td><bean:message key="lab.ac_type"></bean:message></td>
            
               <td><html:select property="txt_acType" >
                               
 	          <c:forEach var="parm" items="${requestScope.param}"> 
  			    <html:option value="${parm.moduleAbbrv}" > <c:out value="${parm.moduleAbbrv}"> </c:out> </html:option>
			  </c:forEach>
                 
                </html:select>
                </td>

                <td><html:text property="txt_acNo"  styleClass="formTextFieldWithoutTransparent"  onblur="focusBack('fromAcntNum')"  size="8" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'10')"></html:text></td>
                </tr>
               
                <% lockermasterobject=(LockerMasterObject)request.getAttribute("lkparams"); %>
                <%System.out.println("begging of jsp");  %> 

                
               <c:if test="${requestScope.lkparams!=null}">
               
            <%  System.out.println("begging of jsp"+lockermasterobject.getLockerNo()); 
                System.out.println("Sydney test"+String.valueOf(lockermasterobject.getLockerNo())); 

                System.out.println("when mast obj is not null"); %>
                <tr><td><bean:message key="lab.acType_num" ></bean:message></td>
                <td><html:text property="txt_lockType" styleClass="formTextField" readonly="true" value="<%=lockermasterobject.getLockerType()%>" size="10" >"></html:text></td>
                <td><html:text property="txt_lockNo"   styleClass="formTextField" readonly="true" size="10" value="<%=String.valueOf(lockermasterobject.getLockerNo())%>"></html:text></td>
                </tr>



                <tr><td><bean:message key="lab.allotExpiry"></bean:message></td>
                <td><html:text property="txt_allotDate" styleClass="formTextField" readonly="true" value="<%=lockermasterobject.getAllotDate()%>" size="10"></html:text></td>
                <td><html:text property="txt_expiryDate" styleClass="formTextField" readonly="true" value="<%=lockermasterobject.getMatDate()%>" size="10"></html:text></td>
                </tr>

                <tr><td><bean:message key="lab.rent"></bean:message></td>
                <td><html:text property="txt_rentUpto" styleClass="formTextField" readonly="true" value="<%=lockermasterobject.getRentUpto()%>" size="10"></html:text></td>
                </tr>

                <tr><td><bean:message key="lab.totRent"></bean:message> </td>
                <td><html:text property="txt_totRent" styleClass="formTextField" readonly="true" value="<%= String.valueOf(lockermasterobject.getRentColl()) %>" size="10"></html:text>  </td>
                </tr>

				<tr>
                <td><bean:message key="lab.operatingInstr"></bean:message></td>
                
                <td><html:text property="select_opInstr" styleClass="formTextField" readonly="true" value="<%=String.valueOf(lockermasterobject.getOprInstrn()) %>" size="10"></html:text>
                </td>
                </tr>

              </c:if>

			<c:if test="${requestScope.lkparams==null}">
                <%System.out.println("when mast object is  null"); %>
                <tr><td><bean:message key="lab.acType_num" ></bean:message></td>
                <td><html:text property="txt_lockType" styleClass="formTextField" value="" size="8" ></html:text></td>
                <td><html:text property="txt_lockNo"   styleClass="formTextField" value="" size="8"></html:text></td>
                </tr>



                <tr><td><bean:message key="lab.allotExpiry"></bean:message></td>
                <td><html:text property="txt_allotDate"  styleClass="formTextField" value="" size="8"></html:text></td>
                <td><html:text property="txt_expiryDate"  styleClass="formTextField" value="" size="8"></html:text></td>
                </tr>

                <tr><td><bean:message key="lab.rent"></bean:message></td>
                <td><html:text property="txt_rentUpto" size="8" styleClass="formTextField"></html:text></td>
                </tr>
                <tr><td><bean:message key="lab.totRent"></bean:message> </td>
                <td><html:text property="txt_totRent" size="8" styleClass="formTextField"></html:text>  </td>
                </tr>
				 <tr>
                <td><bean:message key="lab.operatingInstr"></bean:message></td>
                
                <td><html:select property="select_opInstr">
                    <html:option value="Individual">Individual</html:option>
                    <html:option value="Jointly">Jointly</html:option>
                    <html:option value="EorS">EorS</html:option>
                    </html:select>
                </td>
                </tr>
               </c:if>
             <% System.out.println("out side "); %>



                <tr>
                <td><bean:message key="lab.details"></bean:message> </td>
                <td><html:select property="select_details" onchange="setDetails()" >
                    <html:option value="personal">Personal</html:option>
                    <html:option value="IntroducerType">Introducer</html:option>
                    <html:option value="signature">Signature</html:option>
                    <html:option value="JointHolder">JointHolder</html:option>
                    <html:option value="Nominee">Nominee</html:option>
                    <html:option value="Deposit">Deposit</html:option>
                    </html:select>
                </td>
                </tr>
             
    
                <tr>
	        
                <html:hidden property="disableBut"></html:hidden>
                <html:hidden property="forward"></html:hidden>
                <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
              <td>  <html:button property="clc" disabled="false" onclick="set('verify')" styleClass="ButtonBackgroundImage"><bean:message key="label.verify"></bean:message></html:button>
             <%}else{ %>
              <td>  <html:button property="clc" disabled="true" onclick="set('verify')" styleClass="ButtonBackgroundImage"><bean:message key="label.verify"></bean:message></html:button>
             <%} %>
                
                
                
                <html:button property="clearButton" onclick="callClear()" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:button>
              </td>  
                
                </tr>
</table>

			

</td>
	

					<html:hidden property="defaultSignIndex" value="0"/>
       				<html:hidden property="defaultTab" value="Personal"/>
     				<html:hidden property="defSIndex" value="1"/>




<td align="left">
      	
            <%  String jspPath=(String)request.getAttribute("flag");
            System.out.println("'m inside panel"+jspPath);
            
            if(jspPath!=null){
            	System.out.println("wen 'm  null");
            %>
            <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
            
            <%}else{	%>
            
            <jsp:include page="/LockerWebPages/blank.jsp"></jsp:include>
            <%} %>
            
</td>  
</tr>
</table>

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>