<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>  
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>  
<%@taglib prefix="display" uri="/WEB-INF/displaytag.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareMasterObject"%>
<html>
<head><title>Share Dispatch</title>
       <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
    
      <h2 class="h2">
      <center>Share Dispatch</center></h2>
      <hr>
              
    

<script type="text/javascript">
 function numbersonly(){
         var cha = event.keyCode
            if (cha  > 47 && cha < 58) {
                return true ;
            }
            else{
              alert("Please enter numbers only");
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
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
</script>
 
  </head>
<body class="Mainbody">
<%!
   ShareMasterObject[] sh_master;
   
%>
<%
   //sh_master=(ShareMasterObject[])request.getAttribute("tempperm");
%>
<html:form action="/Share/ShareDispatch?pageId=4009" focus="<%=""+request.getAttribute("focusto") %>">
 <table class="txtTable">
   <html:checkbox property="chk" value="on" disabled="false"></html:checkbox>
   
    <tr>
    <td><bean:message key="label.frm_dt"></bean:message>
     <html:text property="frm_date" styleClass="formTextFieldWithoutTransparent" size="10"  value="<%=""+request.getAttribute("date") %>"  onkeypress="return numbersonly()" onkeyup="numberlimit(this,'11')"></html:text></td>
 
     <td><bean:message key="label.to_dt"></bean:message> 
     <html:text property="to_date" styleClass="formTextFieldWithoutTransparent" size="10"  value="<%=""+request.getAttribute("date") %>"  onkeypress="return numbersonly()" onkeyup="numberlimit(this,'11')"> </html:text></td>
    </tr> 
     <tr>
     <td>
     <html:hidden property="forward"></html:hidden>
      
     </td>
  </tr>
</table>

 <display:table name="tempperm" id="tempperm" class="its" list="${tempperm}">

		<display:column title="${checkAll}" style="width:1%"><input type="checkbox" name="id" value="${tempperm.id}" style="margin:0 0 0 4px" onclick="radio(this)" <core:if test="${param.id==tempperm.id and param.method != 'Save' and param.method != 'Clear' and  param.method != 'Reset'}">checked="checked"</core:if> /></display:column>
		
	    
	    <display:column style="width:3%;text-align: right;" title="Ac.No">
			<core:choose>
				<core:when test="${param.method=='Save' and param.id==tempperm.id }">
					<input type="text" name="ShareNo"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${tempperm.ShareNo}"   
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
   	
   	<display:column style="width:3%;text-align: right;" title="Sh Cat ">		
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
  <tr>
   <td> 
    <input type="submit" value="Reset" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Reset'" />
	
	<input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="Save" name="method"/>
	<input type="submit" value="Clear" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Clear'"/>

	</td>
	</tr>
   </table>
   
  
 
</html:form>

</body>
</html>