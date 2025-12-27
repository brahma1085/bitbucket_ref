<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>   
<%@page import="java.util.Map"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareReportObject"%>
<html>
<head><title>Share Certificate</title>
     <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Share Certificate</center></h2>
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
               alert("Enter the "+str )
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
      
      function fun_validate(){
        var val=document.forms[0].validations.value;
       if(val!=0 || val!=""){
         alert(val); 
      
       } 
       else{
         return false;
       }
      };
      
      
         
    </script>


  </head>

<body class="Mainbody" onload="fun_validate()">
<%!
   ShareReportObject[] shrep;
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

// Note:access=1111==>read,insert,delete,update
%>
<%
   shrep=(ShareReportObject[])request.getAttribute("certificate"); 
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Share/Certificate?pageId=4012" focus="<%=""+request.getAttribute("focusto") %>" >
<html:hidden property="validations"></html:hidden>
 <table class="txtTable">
  <tr>
      
        <td><bean:message key="label.acType"></bean:message></td>
        
        <td><html:select property="actype" styleClass="formTextFieldWithoutTransparent">
          <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}">
          <html:option value="${acType.moduleCode}">
          <core:out value="${acType.moduleAbbrv}"></core:out>
          </html:option>
          </core:forEach>
        </html:select>
        </td>
        
        <td><bean:message key="label.acc_no2"></bean:message></td>
        <td><html:text property="ac_no" size="6" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly()" onkeyup="numberlimit(this,'11')" onchange="submit()"></html:text></td>
        
       
  </tr>
  <tr>
   <table class="txtTable" style="border: thin solid #996699">
     <tr>
      <td><bean:message key="label.name"></bean:message></td>
      <core:choose>
       <core:when test="${requestScope.certificate!=null}">
       <td><html:text property="name" style="color:#996699" styleClass="formTextField" value="<%=""+shrep[0].getName() %>"></html:text></td>
       </core:when>
       <core:otherwise>
        <td><html:text property="name" styleClass="formTextField" ></html:text></td>
       </core:otherwise>
      </core:choose>
      
      
      <td><bean:message key="label.acc_no2"></bean:message></td>
      <core:choose>
       <core:when test="${requestScope.certificate!=null}">
         <td><html:text property="ac_no" style="color:#996699" styleClass="formTextField" value="<%=""+shrep[0].getAccno() %>"  ></html:text></td>       
       </core:when>
       <core:otherwise>
         <td><html:text property="ac_no"  styleClass="formTextField" ></html:text></td>
       </core:otherwise>
      </core:choose>
      
     </tr>
     
     <tr>
      <td><bean:message key="label.address"></bean:message></td>
       <core:choose>
        <core:when test="${requestScope.certificate!=null}">
         <td><html:text property="address" style="color:#996699" styleClass="formTextField" value="<%=""+shrep[0].getAddress() %>" ></html:text></td>
        </core:when>
        <core:otherwise>
          <td><html:text property="address" styleClass="formTextField"></html:text></td>        
        </core:otherwise>
       </core:choose>
     
      
      <td><bean:message key="label.certno"></bean:message></td>
        <core:choose>
         <core:when test="${requestScope.certificate!=null}">
         <td><html:text property="sh_certno" style="color:#996699" styleClass="formTextField"  value="<%=""+shrep[0].getShareCertNumber() %>" ></html:text></td>
         </core:when>
         <core:otherwise>
          <td><html:text property="sh_certno" styleClass="formTextField"></html:text></td>         
         </core:otherwise>
        </core:choose>
      
     </tr>
     
     <tr>
      <td><bean:message key="label.sh_cat"></bean:message></td>
       <core:choose>
       <core:when test="${requestScope.certificate!=null}">
        <td><html:text property="category" style="color:#996699" styleClass="formTextField"  value="<%=""+shrep[0].getMem_cat() %>"  ></html:text></td>
      </core:when>
      <core:otherwise>
        <td><html:text property="category" styleClass="formTextField"   ></html:text></td>      
      </core:otherwise>
       </core:choose>
      
      <td><bean:message key="label.distnum"></bean:message></td>
        <core:choose>
         <core:when test="${requestScope.certificate!=null}">
          <td><html:text property="distnum" styleClass="formTextField" style="color:#996699" value="<%=""+shrep[0].getCertNumberFrom()+" - "+shrep[0].getCertNumberTo() %>" ></html:text></td>
         </core:when>
        <core:otherwise>
          <td><html:text property="distnum" styleClass="formTextField"></html:text></td> 
        </core:otherwise>
      </core:choose>
        
      
     </tr>
     
     <tr>
      <td><bean:message key="label.sh_val"></bean:message></td>
      <core:choose>
       <core:when test="${requestScope.certificate!=null}">
        <td><html:text property="sh_val" style="color:#996699"   value="<%=""+shrep[0].getShareVal() %>" styleClass="formTextField"></html:text></td>
       </core:when>
      <core:otherwise>
       <td><html:text property="sh_val" styleClass="formTextField"></html:text></td>      
      </core:otherwise> 
      </core:choose>
      
      <td><bean:message key="label.no_of_shares"></bean:message></td>
       <core:choose>
        <core:when test="${requestScope.certificate!=null}">
         <td><html:text property="no_of_shares"  style="color:#996699" value="<%=""+shrep[0].getNumberofShares() %>" styleClass="formTextField"></html:text></td>
        </core:when>
       <core:otherwise>
        <td><html:text property="no_of_shares" styleClass="formTextField"></html:text></td>       
       </core:otherwise> 
       </core:choose>
      
     </tr>
     
     <tr>
      <td><bean:message key="label.nomname"></bean:message></td>
       <core:choose>
        <core:when test="${requestScope.certificate!=null}">
         <td><html:text property="nominee"  style="color:#996699" value="<%=""+shrep[0].getNomineeName() %>" styleClass="formTextField"></html:text></td>
        </core:when>
        <core:otherwise>
         <td><html:text property="nominee" styleClass="formTextField"></html:text></td>         
        </core:otherwise>
       </core:choose>
      
      <td><bean:message key="label.certdt"></bean:message></td>
       <core:choose>
        <core:when test="${requestScope.certificate!=null}">
        <td><html:text property="cert_date" style="color:#996699" value="<%=""+shrep[0].getShareCertDate() %>" styleClass="formTextField"></html:text></td>
        </core:when>
        <core:otherwise>
         <td><html:text property="cert_date" styleClass="formTextField"></html:text></td>        
        </core:otherwise>
       </core:choose>
      
     </tr>
     
     <tr>
      <td><bean:message key="label.div_pay_mode"></bean:message></td>
       <core:choose>
        <core:when test="${requestScope.certificate!=null}">
         <td><html:text property="div_pay" style="color:#996699" value="<%=""+shrep[0].getPayMode() %>" styleClass="formTextField"></html:text></td>
        </core:when>
        <core:otherwise>
         <td><html:text property="div_pay" styleClass="formTextField"></html:text></td>         
        </core:otherwise>
       </core:choose>
      
      
     </tr>
   </table>
  </tr>
  
 </table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>