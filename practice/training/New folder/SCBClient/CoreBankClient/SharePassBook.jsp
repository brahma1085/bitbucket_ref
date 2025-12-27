<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareReportObject"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="com.scb.designPatterns.ShareDelegate"%>

<html>
<head><title>Share Pass Book</title>
  <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Share Pass Book</center></h2>
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
      
      
      function validate(){
      
      var val=document.forms[0].validation.value;
       
       if(val!=0 || val!=""){
         alert(val);
       } 
       else if(val1!=0 || val1!=""){
        alert(val1);
       
       }
       else{
         return false;
       }
     };
     
      function clearfun(){
       var v=document.forms[0].elements;
       for(var i=0;i<v.length;i++){
         if(v[i].type=="text"){
            v[i].value="";
         }
       }
     };
     function setFlag(flagValue){
     document.forms[0].flag.value='Download';
     document.forms[0].submit();
     } 
     
    </script>


  </head>
<body class="Mainbody">
<%!
String jsppath;

ModuleObject[] mod;
ShareReportObject []shrep;
String passbookdetails;
%>

<%shrep=(ShareReportObject[])request.getAttribute("passBook");
passbookdetails=(String)request.getAttribute("PassbookDetails");
%>
<%!
String access;
String  accesspageId;
 Map user_role;
String dwnload=null;
%>

<%
  dwnload=(String)request.getAttribute("Download");
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
<html:form action="/Share/PassBook?pageId=4004" focus="<%=""+request.getAttribute("focusto")  %>" >
<html:hidden property="flag" value="error"></html:hidden>
<%
if(passbookdetails!=null){
%> 
<font color="red" face="+3">No details exists for this account number</font>
<%} %>
  <table align="left" valign="top" class="txtTable">
   <html:hidden property="validation"></html:hidden>
     <tr>
      <td>
       <bean:message key="label.actype"></bean:message>
      </td>
      <td>
      <html:select property="code" styleClass="formTextField">
       <html:option value="1001001" styleClass="formTextField">SH</html:option>
      </html:select>
       
      </td>
     </tr>
     
     <tr>
      <td>
       <bean:message key="label.acNum"></bean:message>
       
      </td>
      <td>
       <html:text  property="shnum" size="6" onblur="validfn(shnum.value,'Share Number')" onchange="submit()" onkeypress="return numbersonly()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text>
      </td>
     </tr>
     
     <tr>
      <td>
       <bean:message key="label.sh_cat"></bean:message>
      </td>
      <td>
      <%if(shrep==null){ %>
       <html:text styleClass="formTextField" property="cat" ></html:text>
       <%
      }else{
      %>
       <html:text styleClass="formTextField" property="cat"  value="<%=""+shrep[0].getShareStatus() %>"></html:text>
       <%
      }
       %>
      </td>
     </tr>
     
     <tr>
      <td>
       <bean:message key="label.lnavld"></bean:message>
      </td>
      <td>
      <%if(shrep==null){ %>
       <html:text property="lnavld"  styleClass="formTextField"></html:text>
       <%
      }else{
       %>
          <html:text property="lnavld" styleClass="formTextField" value="<%=""+shrep[0].getLoanAvailed() %>"></html:text>
       <%
      }
       %>   
      </td>
     </tr>

     <tr>
      <td>
        <bean:message key="label.branch"></bean:message>
      </td>
      <td>
      <%if(shrep==null){ %>
       <html:text property="branch" styleClass="formTextField"></html:text>
       <%
      }else{
       %>
       <html:text property="branch" styleClass="formTextField" value="<%=""+shrep[0].getBranchName() %>"></html:text>
      <%
      }
      %>       
      </td>
     </tr>
     <tr>
      <td>
        <bean:message key="label.divupto"></bean:message>
      </td>
      <td>
      <%if(shrep==null){ %>
        <html:text property="div_dt" styleClass="formTextField"></html:text>
        <%
      }else{
        %>
        <html:text property="div_dt" styleClass="formTextField" value="<%=""+shrep[0].getDivUptoDate() %>"></html:text>
        <%
      }
        %>
      </td>
     </tr>    
    <tr>
     <td>
      <bean:message key="label.acstat"></bean:message>
     </td>
     <td>
     <%if(shrep==null){ %>
      <html:text property="accstat" style="color: green"  styleClass="formTextField" value="--" ></html:text>
      <%
     }else{
      %>
      <html:text property="accstat" style="color: green" styleClass="formTextField" value="<%=""+shrep[0].getAccStatus()%>" ></html:text>
      <%
     }
      %>
     </td>
    </tr> 
    <tr>
    <td></td>
     <td>
     <html:submit value="Clear" styleClass="ButtonBackgroundImage" onclick="clearfun()"></html:submit>
     <%if(dwnload!=null){ %>
     <html:button property="downLoad"  styleClass="ButtonBackgroundImage" onclick="setFlag(this.value)">Download</html:button>
     <%} %>
     </td>
    </tr>
        <html:hidden property="defaultTab" value="Personal"></html:hidden> 
    
    </table>
    </html:form>
    
    
   <table align="right"  valign="top" height="10%">
    <tr> 
    <td>
        
        <%
           jsppath=(String)request.getAttribute("flag");
           System.out.println("the jsppath is "+jsppath);
           if(jsppath!=null){
        %>
           <jsp:include page="<%=jsppath%>"></jsp:include>
                        
        <%
           }
        %>
    </td>
      </tr>
      <tr>
<td>
    <%if(shrep!=null){ %>
     <display:table name="passBook" id="currentRowObject" class="its" >
   			<!--<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" />
    		 
	  --><display:column property="transDate"   title="Trn_date"></display:column>
            
      <display:column property="debitAmount"  title="Deb_amt"></display:column>
      
      <display:column property="creditAmount"  total="true"   title="Cr_amt"></display:column>
      <display:column property="share_bal"  title="Sh_Bal"></display:column>
      
      <display:column property="numberofShares"  title="No_of_shares"></display:column>
      <display:column property="deUserId" ></display:column>
      <display:column property="veUserId" ></display:column>
     </display:table>
 	<%
    }
     %>
 </td>
 </tr>  
  
 </table>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<br>


<table align="left"  width="15%" height="15%">

 </table>  
  <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>  
 </body>
</html>
