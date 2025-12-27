<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>  
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareCategoryObject"%>
<%@page import="masterObject.share.ShareParamObject"%>
<html>
<head><title>Share Admin</title>
     <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
    <script type="text/javascript">
      
     function only_numbers() {
	
	var cha=event.keyCode;
	if(cha>47 && cha<=57 )
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
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
     
      function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) || (cha >= 65 && cha <=90) ||cha==32) 
 		{
   			return true;
       	} else 

       	{
   			return false;
       	}
	};
      
      function only_numbers() {
	
	var cha=event.keyCode;
	if(cha>47 && cha<=57 )
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
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
      
        function set(target){
        document.forms[0].forward.value=target;
        storeArray();
        };
        function storeArray(){
        	var table=document.getElementById('AdminTable').getElementsByTagName('tr');
        	var length=table.length;
        	var tableValues=new Array(table.length);
        	var id;
        	alert(document.getElementById('14').getElementsByName('cat_name'))	
        	
        	
        };
        
        function check(){
                  };
</script>
</head>        
        
<body class="Mainbody">
<h2 class="h2">
      <center>Share Admin</center></h2>
      <hr>
      <%
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
<html:form action="/Share/ShareAdmin?pageId=4017">
<%!
    ShareCategoryObject[] sh_cat;
    ShareParamObject[] sh_param;
	String id; 
%>

<%
   sh_cat=(ShareCategoryObject[])request.getAttribute("ShareCategory");
   sh_param=(ShareParamObject[])request.getAttribute("ShareParam");
%>
<%
   if(sh_cat!=null){
	   System.out.println("hi");
%>
<html:hidden property="forward" value="error"></html:hidden>
	<display:table name="AdminList" id="AdminList" class="its"  list="${AdminList}">
		<display:column title="${checkAll}" style="width:1%">
		<input type="checkbox" name="id1" value="${AdminList.id}" style="margin:0 0 0 4px" onclick="radio(this)" <core:if test="${param.id==AdminList.id and param.method != 'Save' and param.method != 'Clear' and  param.method != 'Reset'}">checked="checked"</core:if> /></display:column>
		<display:column style="width:3%;text-align: right;" title="Ac Type">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id1==AdminList.id }">
					<input type="text" name="Share_type"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${AdminList.Sh_type}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.Sh_type}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Mem Cat">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id1==AdminList.id }">
					<core:if test="${AdminList.Mem_cat==''}">
						<input type="text" name="Member_cat" readonly="readonly"  style="padding:2;text-align: right;"  value=""   
						/>
						<%System.out.println("hi all"); %>
					</core:if>
					<core:if test="${AdminList.Mem_cat!=''}">
						<input type="text" name="Member_cat"  readonly="readonly" style="padding:2;text-align: right; "  value="${AdminList.Mem_cat}"   
						/>
							<%System.out.println("hi all ashjasdgjg"); %>
					</core:if>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.Mem_cat}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Cat Name">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id1==AdminList.id }">
					<input type="text" name="Cat_Name" style="padding:3;text-align: right;" readonly="readonly" value="${AdminList.Cat_Name}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.Cat_Name}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Min Share">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id1==AdminList.id }">
					<input type="text" name="Min_Share" style="padding:0;text-align: right;" value="${AdminList.Min_Share}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()" />
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.Min_Share}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Max Share">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id1==AdminList.id }">
					<input type="text" name="Max_Share" style="padding:0;text-align: right;" value="${AdminList.Max_Share}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.Max_Share}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align:right" title="Share val">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id1==AdminList.id }">
					<input type="text" name="Share_Val" style="padding:0;text-align: right;" value="${AdminList.Share_Val}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.Share_Val}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Vote Power">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id1==AdminList.id }">
					<input type="text" name="Vote_Power" style="padding:0;text-align: right;" value="${AdminList.Vote_Power}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${AdminList.Vote_Power}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
	</display:table>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<!--<input type="submit" value="Reset" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Reset'" />
	-->
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<input type="submit" value="Edit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Edit'" />
	<!--<input type="submit" value="Add" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Add'"/>
	--><input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="Save" name="method"/>
	<%}else{ %>
	<input type="submit" value="Edit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Edit'" disabled="disabled" />
	<input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="Save" name="method" disabled="disabled"/>
	<%} %>
	<!--<input type="submit" value="Delete" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Delete'"/>
	--><!--<input type="submit" value="Clear" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Clear'"/>
	--><br/>
	<center>
	<!--<html:submit  onclick="set('submit')" styleClass="ButtonBackgroundImage">Submit All</html:submit>
	--></center>
	
<%} %>


<%if(sh_param!=null){ %>
<table>
<tr>
<td>
<div style="width: 700px;height: 200px;overflow-x:scroll;overflow-y:scroll;">
 <html:hidden property="forward" value="error"></html:hidden>
	<display:table name="param_list" id="param_list" class="its" list="${param_list}" >
		<display:column title="${checkAll}" style="width:1%"><input type="checkbox" name="id" value="${param_list.id}" style="margin:0 0 0 4px" onclick="radio(this)" <core:if test="${param.id==param_list.id and param.method != 'Save' and param.method != 'Clear' and  param.method != 'Reset'}">checked="checked"</core:if> /></display:column>
		<display:column style="width:3%;text-align: right;" title="Sh_type">
			<core:choose>
				<core:when test="${param.method=='EditP' and param.id==param_list.id}">
					<input type="text" name="Sh_type"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${param_list.Sh_type}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${param_list.Sh_type}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Mem Cat">
			<core:choose>
				<core:when test="${param.method=='EditP' and param.id==param_list.id }">
					<core:if test="${param_list.Mem_cat==''}">
						<input type="text" name="Mem_cat"   style="padding:2;text-align: right;"  value=""   
						/>
						<%System.out.println("hi all"); %>
					</core:if>
					<core:if test="${param_list.Mem_cat!=''}">
						<input type="text" name="Mem_cat"  readonly="readonly" style="padding:2;text-align: right; "  value="${param_list.Mem_cat}"   
						/>
							<%System.out.println("hi all ashjasdgjg"); %>
					</core:if>
				</core:when>
				<core:otherwise>
					<core:out value="${param_list.Mem_cat}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Prm_code">
			<core:choose>
				<core:when test="${param.method=='EditP' and param.id==param_list.id }">
					<input type="text" name="prm_code" readonly="readonly" style="padding:3;text-align: right;" value="${param_list.prm_code}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${param_list.prm_code}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Prm_desc">
			<core:choose>
				<core:when test="${param.method=='EditP' and param.id==param_list.id }">
					<input type="text" name="prm_desc" style="padding:0;text-align: right;" value="${param_list.prm_desc}" onkeypress="return only_alpha()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${param_list.prm_desc}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%;text-align: right;" title="Prm_amt">
			<core:choose>
				<core:when test="${param.method=='EditP' and param.id==param_list.id }">
					<input type="text" name="prm_amt" style="padding:0;text-align: right;" value="${param_list.prm_amt}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${param_list.prm_amt}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
		
		<display:column style="width:3%;text-align: right;" title="Prm_freq">
			<core:choose>
				<core:when test="${param.method=='EditP' and param.id==param_list.id }">
					<input type="text" name="prm_freq" style="padding:0;text-align: right;" value="${param_list.prm_freq}" onkeypress="return only_alpha()"	/>
				</core:when>
				<core:otherwise>
					<core:out value="${param_list.prm_freq}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
		<display:column style="width:3%;text-align: right;" title="Prm_type">
			<core:choose>
				<core:when test="${param.method=='EditP' and param.id==param_list.id }">
					<input type="text" name="prm_type" style="padding:0;text-align: right;" value="${param_list.prm_type}" onkeypress="return only_alpha()"	/>
				</core:when>
				<core:otherwise>
					<core:out value="${param_list.prm_type}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
		<display:column style="width:3%;text-align: right;" title="Prm_gl_code">
			<core:choose>
				<core:when test="${param.method=='EditP' and param.id==param_list.id }">
					<input type="text" name="prm_gl_code" style="padding:0;text-align: right;" value="${param_list.prm_gl_code}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${param_list.prm_gl_code}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
		<display:column style="width:3%;text-align: right;" title="Prm_gl_type">
			<core:choose>
				<core:when test="${param.method=='EditP' and param.id==param_list.id }">
					<input type="text" name="prm_gl_type" style="padding:0;text-align: right;" value="${param_list.prm_gl_type}" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"/>
				</core:when>
				<core:otherwise>
					<core:out value="${param_list.prm_gl_type}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
	
		
	</display:table>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
 </div>
</td>
</tr>
</table>
  

<%} %>
<!--<input type="submit" value="ResetP" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Reset'" />
	-->
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<input type="submit" value="EditP" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Edit'" />
	<!--<input type="submit" value="AddP" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Add'" onfocus="check()"/>
	--><input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="SaveP" name="method"/>
	<%}else{ %>
	<input type="submit" value="EditP" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Edit'" disabled="disabled"/>
	<input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="SaveP" name="method" disabled="disabled"/>
	<%} %>
	<!--<input type="submit" value="DeleteP" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Delete'"/>
	--><!--<input type="submit" value="ClearP" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Clear'"/>
	--><br/>
	<center>
	<!--<html:submit  onclick="set('submit')" styleClass="ButtonBackgroundImage">Submit All</html:submit>
	--></center>
	</html:form>
	 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>

</html>