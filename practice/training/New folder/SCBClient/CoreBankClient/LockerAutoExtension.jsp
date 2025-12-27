<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c"    uri="/WEB-INF/c-rt.tld"%>



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

function sendAcnt(myVal){
 
document.forms[0].forward.value='extend';

document.forms[0].acntNum1.value=myVal;
document.forms[0].lkNumType.value=myVal;
document.forms[0].submit();

}


function set(target){
document.forms[0].forward.value=target;
};

function goToAction(acntNum)
{
alert(acntNum);
document.forms[0].forward.value='';
document.forms[0].dummyAcntNum.value=acntNum;
document.forms[0].submit();

}

function callMe(){
	
	if(document.forms[0].eligibleAcntNum.value==null || document.forms[0].eligibleAcntNum.value==""){
		return false;
	}	
	else
	{ 
		alert(document.forms[0].eligibleAcntNum.value);
		var elementss= document.forms[0].elements;
		for(var i=0;i<elementss.length;i++)
		{
			if(elementss[i].type=="submit")
				
			document.forms[0].extendButton.disabled = true;
		}
	}
}
</script>

</head>
<body bgcolor="#f3f4c8" onload="callMe()" class="Mainbody">
<center><h2 class="h2"><bean:message key="lable.autoExtension"></bean:message></h2></center>
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
<html:form action="/LKAutoExtensionLink?pageId=9006">

<%!LockerMasterObject array_lockermasterobject[];
	String[] lkDetails;
%>

<%array_lockermasterobject=(LockerMasterObject[])request.getAttribute("details"); 
	lkDetails=(String[])request.getAttribute("lkDetail");
%>
<table class="txtTable">
<tr>
<td>
<table class="txtTable" border="1">
<tr><td>LockerA/cType</td><td>LockerA/cNum</td><td>LockerExpDate</td></tr>
	<%if(array_lockermasterobject!=null){%>

		
	<% for(int i=0;i<array_lockermasterobject.length;i++) { %>
<tr>
	<td><html:text property="acntType" value="<%=array_lockermasterobject[i].getLockerAcType()%>"     size="12"     styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>  </td>
	<td><html:text property="acntNum"  value="<%=""+array_lockermasterobject[i].getLockerAcNo() %>"   size="12"     styleClass="formTextFieldWithoutTransparent" readonly="true" onclick="goToAction(this.value)"></html:text>  </td>
	<td><html:text property="expDate"  value="<%=array_lockermasterobject[i].getMatDate() %>"         size="12" 	   styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>  </td>
</tr>
<%}} %>


<html:hidden property="forward"></html:hidden>
<html:hidden property="dummyAcntNum"></html:hidden>
<html:hidden property="eligibleAcntNum"></html:hidden>
<html:hidden property="acntNum1"></html:hidden>
<html:hidden property="lkNumType"></html:hidden>

      		
      
</table>
</td>

<td valign="top">
      
      		
     <!--<html:submit onclick="set('extend')" property="extendButton" styleClass="ButtonBackgroundImage">EXTEND</html:submit>
	-->
	
	<% 
      	if(lkDetails!=null)
      	{  
    %>
      	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            <html:text property="acntNum1"  value="<%="Extend-"+lkDetails[0]+"-"+lkDetails[2]%>"   size="13"  styleClass="ButtonBackgroundImage"  onclick="sendAcnt(this.value)">   </html:text>
             <%}else{ %>
           <html:text property="acntNum1"  value="<%="Extend-"+lkDetails[0]+"-"+lkDetails[2]%>"   size="13"  styleClass="ButtonBackgroundImage"  disabled="true" onclick="sendAcnt(this.value)">   </html:text>
             <%} %>
      	
      	<html:text property="lockerNum" value="<%="locker num is-"+lkDetails[1] %>" size="17"    styleClass="ButtonBackgroundImage"></html:text>
      	<%} %>
      		
      		
            <%    String jspPath = (String) request.getAttribute("flag");   %>
            
            <c:if  test="${requestScope.flag !=null}">
          		<jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
            </c:if>
            
                       
	  
	   
	   
 	   </td>
 	   </tr>
 	   </table> 
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>