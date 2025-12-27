<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareReportObject"%>
<html>
<head><title>Share Ledger</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Share Ledger</center></h2>
      <hr>
      
    
    <script type="text/javascript">
     
     function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
     
     function set(target){
       document.forms[0].forward.value=target
      };
     
     
           function validfn(fnm,str){
             var len=fnm.length;
             if(len==0){
               alert("Enter the"+str )
              
             }
           };
     
       function fun(shnum){
       alert(shnum)
       };
     
      function check_acnt(){
      
        if(document.getElementById("ac").value=='T'){
          alert("Account not found")
           document.forms[0].acno.value="";
        }
      };
      
      function clear_fun()
    {
    	
    	var ele=document.forms[0].elements;
    	document.getElementById("forward").style.display='none';  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    
    function numbersonly(eve){
         var cha = event.keyCode


            if (  ( cha  > 47 && cha < 58  ) ) {


              
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
         
        
      };
     
    </script>


  </head>
<body onload="check_acnt()" class="Mainbody">
<%!
   ShareReportObject[] sh_report=null;
%>
<%
    sh_report=(ShareReportObject[])request.getAttribute("shareledger");
    System.out.println("The color is "+request.getAttribute("colorstat"));
String msg=(String)request.getAttribute("msg");
%>

<%if(msg!=null){ %>
  <font color="red"><%=msg %></font>
  <br><br>
<%} %>
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

// Note:access=1111==>read,insert,delete,update
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
 <html:form action="/Share/ShareLedger?pageId=4028" focus="<%=""+request.getAttribute("focusto")%>">
  <table class="txtTable">
   <tr>
    <td><bean:message key="label.acType"></bean:message></td>
        <td>
         <html:select property="acctype" styleClass="formTextFieldWithoutTransparent">
           <html:option value="1001001">SH</html:option>
         </html:select>
        </td>
   </tr>
   
   <tr>
    <td><bean:message key="lable.accno"></bean:message></td>
    
       <td><html:text property="acno" styleClass="formTextFieldWithoutTransparent" size="6" onchange="submit()" onblur="validfn(acno.value,'Share Number')" onkeypress="return numbersonly() " onkeyup="numberlimit(this,'11')"></html:text>
       <html:hidden property="ac_not_fnd" styleId="ac"></html:hidden>     
     
      <core:choose>
        <core:when test="${requestScope.shareledger!=null}">
        <html:text property="name" style="color:red"   styleClass="formTextField" value="<%=""+sh_report[0].getName() %>"></html:text>
       </core:when>
        <core:otherwise>
         <html:text property="name"  style="color:red" styleClass="formTextField"></html:text>
        </core:otherwise>
      </core:choose>
     </td>
     
     <td width="20px"></td>
     <td><bean:message key="lable.accstatus"></bean:message></td>
      <core:choose>
        <core:when test="${requestScope.shareledger!=null}">
        <td><html:text property="acc_status"  styleClass="formTextField" value="<%=""+sh_report[0].getAccStatus() %>"></html:text></td>
       </core:when>
        <core:otherwise>
         <td><html:text property="acc_status"   styleClass="formTextField"></html:text></td>
        </core:otherwise>
      </core:choose>
   </tr>
   
   <tr>
    <td><bean:message key="label.introname"></bean:message></td>
     <core:choose>
      <core:when test="${requestScope.shareledger!=null}">
        <td><html:text property="introby" value="<%=""+sh_report[0].getIntrName() %>" styleClass="formTextField"></html:text></td>
      </core:when>
      <core:otherwise>
        <td><html:text property="introby" styleClass="formTextField"></html:text></td>      
      </core:otherwise>
     </core:choose>
    
    <td width="80px"></td>
     
    
    <td><bean:message key="label.pay_type"></bean:message></td>
    <core:choose>
     <core:when test="${requestScope.shareledger!=null}">
        <td><html:text property="pay_mode" value="<%=""+sh_report[0].getPayMode() %>" styleClass="formTextField"></html:text></td>
     </core:when>
     <core:otherwise>
       <td><html:text property="pay_mode" styleClass="formTextField"></html:text></td>      
     </core:otherwise>
    </core:choose>
    
   </tr>
   
   <tr>
    <td><bean:message key="label.sh_type"></bean:message></td>
      <core:choose>
        <core:when test="${requestScope.shareledger!=null}">
          <td><html:text property="sharetype" value="<%=""+sh_report[0].getShareStatus() %>" styleClass="formTextField"></html:text></td>         
        </core:when>
       <core:otherwise>
       <td><html:text property="sharetype" styleClass="formTextField"></html:text></td>
       </core:otherwise> 
      </core:choose>
    
    
     <td width="80px">
     </td>
    
    <td><bean:message key="label.certdt"></bean:message></td>
     <core:choose>
      <core:when test="${requestScope.shareledger!=null}">
        <td><html:text property="cert_dt" value="<%=""+sh_report[0].getCertNumberFrom() %>" styleClass="formTextField"></html:text></td>
      </core:when>
      <core:otherwise>
        <td><html:text property="cert_dt" styleClass="formTextField"></html:text></td>       
      </core:otherwise>
     </core:choose>
   </tr>
   
   <tr>
    <td><bean:message key="label.branch"></bean:message></td>
      <core:choose>
       <core:when test="${requestScope.shareledger!=null}">
        <td><html:text property="branch" value="<%=""+sh_report[0].getBranchName() %>" styleClass="formTextField"></html:text></td>
       </core:when>
       <core:otherwise>
         <td><html:text property="branch" styleClass="formTextField"></html:text></td>
       </core:otherwise>
      </core:choose>
    
    
     <td width="80px">
     </td>
    
    <td><bean:message key="label.certno"></bean:message></td>
      <core:choose>
       <core:when test="${requestScope.shareledger!=null}">
        <td><html:text property="cert_no" value="<%=""+sh_report[0].getCertNumberTo() %>" styleClass="formTextField"></html:text></td>
       </core:when>
       <core:otherwise>
       <td><html:text property="cert_no" styleClass="formTextField"></html:text></td> 
       </core:otherwise>
      </core:choose>
   </tr>
   
   <tr>
    <td><bean:message key="label.nomname"></bean:message></td>
     <core:choose>
      <core:when test="${requestScope.shareledger!=null}">
      <td><html:text property="nom_name" value="<%=""+sh_report[0].getNomineeName() %>" styleClass="formTextField"></html:text></td>
      </core:when>
      <core:otherwise>
        <td><html:text property="nom_name" styleClass="formTextField"></html:text></td>      
      </core:otherwise>
     </core:choose>
   
    
     <td width="80px">
     </td>
     
     <td><bean:message key="label.lnavld"></bean:message></td>
      <core:choose>
       <core:when test="${requestScope.shareledger!=null}">
       <td><html:text property="lnavld" value="<%=""+sh_report[0].getLoanAvailed() %>" styleClass="formTextField" ></html:text></td>
       </core:when>
       <core:otherwise>
        <td><html:text property="lnavld" styleClass="formTextField" ></html:text></td>
       </core:otherwise>
      </core:choose>
      
  </tr>
  
  <tr>
    <td><bean:message key="label.rel"></bean:message></td>
      <core:choose>
       <core:when test="${requestScope.shareledger!=null}">
        <td>
        <core:if test="<%=sh_report[0].getNomineeRelation()!=null %>">
        <html:text property="rel" value="<%=""+sh_report[0].getNomineeRelation()%>" styleClass="formTextField"></html:text>
        </core:if>
        </td>
       </core:when>
       <core:otherwise>
        <td><html:text property="rel" styleClass="formTextField"></html:text></td>       
       </core:otherwise>
      </core:choose>
    
    
     <td width="80px">
     </td>
     
     <td><bean:message key="label.divupto"></bean:message></td>
      <core:choose>
       <core:when test="${requestScope.shareledger!=null}">
        <td><html:text property="div_dt" value="<%=""+sh_report[0].getDivUptoDate() %>" styleClass="formTextField"></html:text></td>
       </core:when>
       <core:otherwise>
        <td><html:text property="div_dt" styleClass="formTextField"></html:text></td>        
       </core:otherwise>
      </core:choose>
     
   </tr>
   
   <tr>
    <td width="100px"></td>
     <html:hidden property="forward" value="error"></html:hidden>
     <td>
     <html:button property="clear" onclick="clear_fun()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
	<td><html:submit  onclick="set('save')" styleClass="ButtonBackgroundImage">DownLoad</html:submit></td>    
    </tr>
  
  
  </table>
  
 <table>
  <tr>
   <td>
    <%if(sh_report!=null){ %>
      <jsp:include page="/Ledger.jsp"></jsp:include>
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