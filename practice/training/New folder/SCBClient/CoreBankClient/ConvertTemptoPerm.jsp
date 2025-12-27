<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareMasterObject"%>
<html>
<head><title>Convertion from Temporary to Permenant Shares</title>
     <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
      <h2 class="h2">
      <center>Convertion from Temporary to Permanent Shares</center></h2>
      <hr>
      
    <script type="text/javascript">
    
     function only_numbers() {
	var cha=event.keyCode;
	if(cha>46 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
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
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
         
        
         
    
 
 function set(target){
       document.forms[0].forward.value=target;
        };
        
 

 

</script>
 
 
  </head>
<body class="Mainbody" style="overflow: scroll;" onload="validation()">
<%!
String access;
String  accesspageId;
 Map user_role;
String flag=null;

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
<%!
   ShareMasterObject[] sh_master;
   
%>
<%
   sh_master=(ShareMasterObject[])request.getAttribute("idvalue");
%>
 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<html:form action="/Share/ConvertTemp?pageId=4030" focus="<%=""+request.getAttribute("focusto") %>">
<table class="txtTable">
      <%
   // Paste code here file name is convert temp to perm
   %>
   <html:hidden property="validations"></html:hidden>
    <tr>
    <td><bean:message key="label.frm_dt"></bean:message>
     <html:text property="frm_date" size="12" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" ></html:text></td>
 
     <td><bean:message key="label.to_dt"></bean:message> 
     <html:text property="to_date" size="12"  styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" > </html:text></td>
         
    </tr>
     
     <tr>
     <td>
     <html:hidden property="forward"></html:hidden>
      
     </td>
  </tr>
 </table> 
  
   
   <display:table name="tempperm" id="tempperm" class="its" list="${tempperm}">
   
   
		<display:column  style="width:1%">
		
		
		<input type="checkbox" name="id" value="${tempperm.id}" style="margin:0 0 0 4px"/>
		 </display:column>
		<display:column style="width:3%;text-align: right;" title="AcNo">
			<core:choose>
				<core:when test="${param.method=='Save' and param.id==tempperm.id }">
					<input type="text" name="Shno"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.ShareNo}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${tempperm.ShareNo}"></core:out>
				</core:otherwise>
			</core:choose>
	    </display:column>
	    
	    <display:column style="width:3%;text-align: right;" title="Name ">		
		<core:choose>
		  <core:when test="${param.method=='Save' and param.id==tempperm.id}">
		     <input type="text" name="Name"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.Name}"/>
		  </core:when>
		 <core:otherwise>
		   <core:out value="${tempperm.Name}"></core:out>
		 </core:otherwise>
	  </core:choose>	
	</display:column>
	
	<display:column style="width:3%;text-align: right;" title="CID ">		
		<core:choose>
		  <core:when test="${param.method=='Save' and param.id==tempperm.id}">
		     <input type="text" name="cid"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.cid}"/>
		  </core:when>
		 <core:otherwise>
		   <core:out value="${tempperm.cid}"></core:out>
		 </core:otherwise>
	  </core:choose>	
	</display:column>
	
	
	<display:column style="width:3%;text-align: right;" title="No Of Shares">
	  <core:choose>
	    <core:when test="${param.method=='Save' and param.id==tempperm.id}">
	      <input type="text" name="NoOfShares"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.NumofShare}"/>
	    </core:when>
	   <core:otherwise>
	     <core:out value="${tempperm.NumofShare}"></core:out>
	   </core:otherwise> 
	  </core:choose>	
    </display:column>	
    
   <display:column style="width:3%;text-align: right;" title="ShareValue">
     <core:choose>
       <core:when test="${param.method=='Save' and param.id==tempperm.id}">
         <input type="text" name="ShareValue"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.ShareValue}"/>
       </core:when>
      <core:otherwise>
        <core:out value="${tempperm.ShareValue}"></core:out>
      </core:otherwise> 
     </core:choose>
   </display:column> 
   
   <display:column style="width:3%;text-align: right;" title="Branch Code">
     <core:choose>
       <core:when test="${param.method=='Save' and param.id==tempperm.id}">
         <input type="text" name="BranchCode"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.BranchCode}"/>
       </core:when>
      <core:otherwise>
        <core:out value="${tempperm.BranchCode}"></core:out>
      </core:otherwise> 
     </core:choose>
   </display:column> 
   	
   	<display:column style="width:3%;text-align: right;" title="Sh Cat">		
		<core:choose>
		  <core:when test="${param.method=='Save' and param.id==tempperm.id}">
		     <input type="text" name="ShCat"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.ShCat}"/>
		  </core:when>
		 <core:otherwise>
		   <core:out value="${tempperm.ShCat}"></core:out>
		 </core:otherwise>
	  </core:choose>	
	</display:column>
	
	<display:column style="width:3%;text-align: right;" title="Issue Date ">		
		<core:choose>
		  <core:when test="${param.method=='Save' and param.id==tempperm.id}">
		     <input type="text" name="IssueDate"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.IssueDate}"/>
		  </core:when>
		 <core:otherwise>
		   <core:out value="${tempperm.IssueDate}"></core:out>
		 </core:otherwise>
	  </core:choose>	
	</display:column>
		</display:table>
  
  <table>
  <TR>
   <td>
    <center> 
    <%if(request.getAttribute("disabled")!=null){
    		flag="disabled";
    	}else{
    		flag=null;
    	}
     %>
    <input type="submit" value="Reset" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Reset'" />
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'&& flag!=null){%>
	<input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="Save" name="method" />
	<%}else{ %>
	<input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="Save" name="method" disabled="disabled"/>
	<%} %>
	<input type="submit" value="Clear" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Clear'"/>
	
	</center>
	</td>
	</tr>
   </table>
 
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>