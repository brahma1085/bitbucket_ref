<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>   
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">

function set(target)
{
    document.forms[0].forward.value=target;
    document.forms[0].submit();
};

function selectItem()
{
  
  if(document.forms[0].loanmodules.value!="LoanEntryIns" && document.forms[0].loanmodules.value!="Select Tables" && document.forms[0].loanmodules.value!="DocParam" && document.forms[0].loanmodules.value!="LoanPurposes" )
  {
     document.getElementById("loanacctype").style.display="block";
  }
  else
  {
    document.getElementById("loanacctype").style.display="none";
  }
  

}


</script>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
	 
</head>
<body onload="selectItem()">
	<center><h2 class="h2">Loan Admin</h2></center>

<%!
	String[] str;
    ModuleObject object[];
    String tablenum;
    boolean flag=true;
     boolean flag1=false;
%>

<%
  str = (String[])request.getAttribute("LoanTable");
  object=(ModuleObject[])request.getAttribute("LoanModuleCode");
  tablenum = (String)request.getAttribute("Table");
%>
<%!
String access;
String  accesspageId;
 Map user_role;
%>
<%
  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");
System.out.println("11111111"+user_role);
System.out.println("222222222"+accesspageId);
if(user_role!=null&&accesspageId!=null){
System.out.println("11111111"+access);
			if(user_role.get(accesspageId)!=null){
			System.out.println("222222222"+access);
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}
			}
// Note:access=1111==>read,insert,delete,update
%> 
<%
    String disabled=(String)request.getAttribute("Disable");
	String[][] entryIns = (String[][])request.getAttribute("EntryIns");
	String[][] catRate = (String[][])request.getAttribute("CategoryRate");
	String[][] intRate = (String[][])request.getAttribute("IntRate");
	String[][] periodRate = (String[][])request.getAttribute("PeriodRate");
	String[][] penalIntRate = (String[][])request.getAttribute("StrPenalIntRate");
	String[][] purposes = (String[][])request.getAttribute("Purposes");
	String[][] docParam = (String[][])request.getAttribute("StrDocParam");
	String msg = (String)request.getAttribute("msg");
%>
<%if(disabled!=null){ 
flag=false;
flag1=true;
}
else{
flag=true;
flag1=false;
}


%>




<% if(msg!=null){ %>
<font color="red"><%=""+msg%></font>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/LoanAdmin?pageidentity.pageId=5045">
<table>
<tr>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<td>
<bean:message key="label.loanmodules"></bean:message>
   <html:select property="loanmodules" onblur="return selectItem()" onchange="submit()">
  		<html:option value="Select Tables"></html:option>
   		<% for(int i=0;i<str.length;i++) {%>
   		<html:option value="<%=""+str[i]%>"></html:option>
   		<%}%>
	</html:select>
</td>
<%}else{ %>
<td>
<bean:message key="label.loanmodules"></bean:message>
   <html:select property="loanmodules" disabled="true">
  		<html:option value="Select Tables"></html:option>
   		<% for(int i=0;i<str.length;i++) {%>
   		<html:option value="<%=""+str[i]%>"></html:option>
   		<%}%>
	</html:select>
</td>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<td id="loanacctype" style="display:none">
   <bean:message key="label.combo_loan"></bean:message>
   <html:select property="acctype" styleClass="formTextFieldWithoutTransparent" onblur="submit()">
      	<html:option value="All Types"></html:option>
     		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      		  </core:forEach>
   </html:select>
</td>
<%}else{ %>
<td id="loanacctype" style="display:none">
   <bean:message key="label.combo_loan"></bean:message>
   <html:select property="acctype" styleClass="formTextFieldWithoutTransparent" disabled="true">
      	<html:option value="All Types"></html:option>
     		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      		  </core:forEach>
   </html:select>
</td>
<%} %>
</tr>

<tr>
<td>
 <html:hidden property="forward" value="error"/>
 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:button styleClass="ButtonBackgroundImage" property="sub" value="Submit" onclick="set('submit')" disabled="<%=flag %>"></html:button>
	<html:button property="add" styleClass="ButtonBackgroundImage" value="Add Row"  onclick="set('addrow')" disabled="<%=flag %>"></html:button>
	<%}else{ %>
	<html:button styleClass="ButtonBackgroundImage" property="sub" value="Submit" disabled="true"></html:button>
	<html:button property="add" styleClass="ButtonBackgroundImage" value="Add Row"  disabled="true"></html:button>
	<%} %>
	
</td>

</tr>	
</table>



<core:if test="${requestScope.EntryIns!=null}">	
	<div id="LoanEntryIns" style="display:block;overflow:scroll;width:650px;height:210px;">
	<table>
	  
	<tr><b><th>ModuleCode</th><th>ModuleAbbr</th><th>Personal</th><th>Relative</th><th>Employment</th><th>Application</th>
		<th>Loan/ShareDetails</th><th>Signature</th><th>Property</th><th>CoBorrowers</th><th>Surity</th><th>Vehicle</th><th>Gold</th><th>OverDraft</th>
	</b></tr> 
    
    <%for(int i=0;i<entryIns.length;i++){ %>
    <tr>
    	<td><input type="text" name="ModuleCode"  value="<%=""+entryIns[i][0] %>" size="10"/></td>
  		<td><input type="text" name="ModuleAbbr"  value="<%=""+entryIns[i][1]%>" size="10"/></td>
   		<td><input type="text" name="Personal"  value="<%=""+entryIns[i][2]%>" size="10"/></td>
    	<td><input type="text" name="Relative"  value="<%=""+entryIns[i][3]%>" size="10"/></td>
    	<td><input type="text" name="Employment"  value="<%=""+entryIns[i][4]%>" size="10"/></td>
    	<td><input type="text" name="Application"  value="<%=""+entryIns[i][5]%>" size="10"/></td>
    	<td><input type="text" name="LoanShareDetails" value="<%=""+entryIns[i][6]%>" size="15"/></td>
    	<td><input type="text" name="Signature"  value="<%=""+entryIns[i][7]%>" size="10"/></td>
   		<td><input type="text" name="Property" value="<%=""+entryIns[i][8]%>" size="10"/></td>
   		<td><input type="text" name="CoBorrowers" value="<%=""+entryIns[i][9]%>" size="10"/></td>
    	<td><input type="text" name="Surity" value="<%=""+entryIns[i][10]%>" size="10"/></td>
    	<td><input type="text" name="Vehicle" value="<%=""+entryIns[i][11]%>" size="10"/></td>
   		<td><input type="text" name="Gold" value="<%=""+entryIns[i][12]%>" size="10"/></td>
    	<td><input type="text" name="OverDraft" value="<%=""+entryIns[i][13]%>" size="10"/></td>
    	
	</tr>
	<%} %>
	
			<core:if test="${requestScope.AddRow!=null}">
	   		<tr>
	   						<td><input type="text"  name="ModuleCode"   size="10"/></td>
	   						<td><input type="text"  name="ModuleAbbr" size="10"/></td>
							<td><select  name="Personal"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							<td><select  name="Relative"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							<td><select  name="Employment"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							<td><select  name="Application"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							<td><select  name="LoanShareDetails"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							<td><select  name="Signature"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							<td><select  name="Property"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							<td><select  name="CoBorrowers"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							<td><select  name="Surity"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							<td><select  name="Vehicle"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							<td><select  name="Gold"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							<td><select  name="OverDraft"><option value="Y">Y</option><option value="N">N</option><option value="O">O</option></select></td>
							
	   		</tr>
	   		</core:if>	
	
  </table>
  </div>
 </core:if>  
  
   <core:if test="<%=(catRate!=null)%>">
  		
  		 <div id="LoanCategoryRate" style="display:block;table-layout:auto;overflow:scroll;width:650px;height:210px;">
		<table>
		
		<thead>
		
		<tr><b>
			<th>LoanType</th><th>DateFrom</th><th>DateTo</th><th>Category</th><th>Rate</th><th>De_Tml</th><th>De_User</th><th>De_Datetime</th></b>
		</tr></thead>
		<tbody>
		<% for(int i=0;i<catRate.length;i++){ %>
      	<tr>
  			<td><input type="text" name="LoanType" value="<%=""+catRate[i][0]%>" size="10"/></td>
    		<td><input type="text" name="DateFrom"  value="<%=""+catRate[i][1]%>" size="10"/></td>
    		<td><input type="text" name="DateTo"  value="<%=""+catRate[i][2]%>" size="10"/></td>
    		<td><input type="text" name="Category"  value="<%=""+catRate[i][3]%>" size="10"/></td>
    		<td><input type="text" name="Rate"  value="<%=""+catRate[i][4]%>" size="10"/></td>
    	    <td><input type="text" name="De_Tml"  value="<%=""+catRate[i][5]%>" size="10"/></td>
    		<td><input type="text" name="De_User"  value="<%=""+catRate[i][6]%>" size="10"/></td>
    		<td><input type="text" name="De_Datetime"  value="<%=""+catRate[i][7]%>" size="10"/></td>
    	</tr>	
		<%} %>
				<core:if test="${requestScope.AddRow!=null}">
	   				<tr>
	   						<td><input type="text"  name="LoanType"   size="10"/></td>
	   						<td><input type="text"  name="DateFrom" size="10"/></td>
							<td><input type="text"  name="DateTo" size="10"/></td>
							<td><input type="text"  name="Category" size="10"/></td>
							<td><input type="text"  name="Rate" size="10"/></td>
							<td><input type="text"  name="De_Tml" size="10"/></td>
							<td><input type="text"  name="De_User" size="10"/></td>
							<td><input type="text"  name="De_Datetime" size="10"/></td>
					</tr>
		   		</core:if>	
</tbody>		
	</table>
    </div>
    </core:if>
    
    <core:if test="${requestScope.IntRate!=null}">
  		
  		 <div id="LoanIntRate" style="display:block;overflow:scroll;width:650px;height:210px;">
		<table>
		<tr><b>
		<th>LoanType</th><th>PurposeCode</th><th>DateFrom</th><th>DateTo</th><th>MinimumBalance</th><th>MaximumBalance</th>
		<th>Rate</th><th>De_Tml</th><th>De_User</th><th>De_Datetime</th></b>
		</tr>
		<% for(int i=0;i<intRate.length;i++){ %>
		
    		<tr>	
    			<td><input type="text" name="LoanType" value="<%=""+intRate[i][0]%>" size="10"/></td>
    			<td><input type="text" name="PurposeCode" value="<%=""+intRate[i][1]%>" size="10"/></td>
    			<td><input type="text" name="DateFrom"  value="<%=""+intRate[i][2]%>" size="10"/></td>
   				<td><input type="text" name="DateTo" value="<%=""+intRate[i][3]%>" size="10"/></td>
    			<td><input type="text" name="MinimumBalance" value="<%=""+intRate[i][4]%>" size="10"/></td>
    			<td><input type="text" name="MaximumBalance" value="<%=""+intRate[i][5]%>" size="10"/></td>
   				<td><input type="text" name="Rate"  value="<%=""+intRate[i][6]%>" size="10"/></td>
   				<td><input type="text" name="De_Tml" value="<%=""+intRate[i][7]%>" size="10"/></td>
   				<td><input type="text" name="De_User" value="<%=""+intRate[i][8]%>" size="10"/></td>
   				<td><input type="text" name="De_Datetime" value="<%=""+intRate[i][9]%>" size="10"/></td>
    		</tr>
	   	<%} %>
	   			<core:if test="${requestScope.AddRow!=null}">
	   				<tr>
	   						<td><input type="text" name="LoanType" size="10"/></td>
	   						<td><input type="text" name="PurposeCode"  size="10"/></td>
							<td><input type="text" name="DateFrom" size="10"/></td>
							<td><input type="text" name="DateTo" size="10"/></td>
							<td><input type="text" name="MinimumBalance"  size="10"/></td>
							<td><input type="text" name="MaximumBalance"size="10"/></td>
							<td><input type="text" name="Rate" size="10"/></td>
							<td><input type="text" name="De_Tml" size="10"/></td>
							<td><input type="text" name="De_User" size="10"/></td>
							<td><input type="text" name="De_Datetime" size="10"/></td>
					
	   				</tr>
		   		</core:if>	
	   	</table>	
    	</div>
    	</core:if>
    
        <core:if test="${requestScope.PeriodRate!=null}">
  	 
         <div id="LoanPeriodRate" style="display:block;overflow:scroll;width:650px;height:210px;">
		<table>
		<tr>
		<td>LoanType</td><td>DateFrom</td><td>DateTo</td><td>FromMonth</td><td>ToMonth</td><td>Rate</td><td>De_Tml</td><td>De_User</td><td>De_Datetime</td>
		</tr>
		<%for(int i=0;i<periodRate.length;i++) { %>
    	<tr>
      		<td><input type="text" name="LoanType" value="<%=""+periodRate[i][0]%>" size="10"/></td>
    		<td><input type="text" name="DateFrom" value="<%=""+periodRate[i][1]%>" size="10"/></td>
    		<td><input type="text" name="DateTo" value="<%=""+periodRate[i][2]%>" size="10"/></td>
    		<td><input type="text" name="FromMonth" value="<%=""+periodRate[i][3]%>" size="10"/></td>
   			<td><input type="text" name="ToMonth" value="<%=""+periodRate[i][4]%>" size="10"/></td>
    		<td><input type="text" name="Rate" value="<%=""+periodRate[i][5]%>" size="10"/></td>
    		<td><input type="text" name="De_Tml" value="<%=""+periodRate[i][6]%>" size="10"/></td>
    		<td><input type="text" name="De_User" value="<%=""+periodRate[i][7]%>" size="10"/></td>
   			<td><input type="text" name="De_Datetime" value="<%=""+periodRate[i][8]%>" size="10"/></td>
   		</tr>
		<%} %>
		
				<core:if test="${requestScope.AddRow!=null}">
	   				<tr>
	   						<td><input type="text" name="LoanType"  size="10"/></td>
							<td><input type="text" name="DateFrom" size="10"/></td>
							<td><input type="text" name="DateTo"  size="10"/></td>
							<td><input type="text" name="FromMonth" size="10"/></td>
							<td><input type="text" name="ToMonth" size="10"/></td>
							<td><input type="text" name="Rate" size="10"/></td>
							<td><input type="text" name="De_Tml" size="10"/></td>
							<td><input type="text" name="De_User" size="10"/></td>
							<td><input type="text" name="De_Datetime" size="10"/></td>
					</tr>
		   		</core:if>	
		</table>
		</div>
		</core:if>	
    
    <core:if test="${requestScope.StrPenalIntRate!=null}">
  	 
         <div id="PenalIntRate" style="display:block;overflow:scroll;width:650px;height:210px;">
		<table>
		<tr>
			<td>LoanType</td><td>DateFrom</td><td>DateTo</td><td>Category</td><td>PenalRate</td><td>De_Tml</td><td>De_User</td><td>De_Datetime</td>
		</tr>
		<%for(int i=0;i<penalIntRate.length;i++){ %>
    	<tr>
     			<td><input type="text" name="LoanType" value="<%=""+penalIntRate[i][0]%>" size="10"/></td>
    			<td><input type="text" name="DateFrom" value="<%=""+penalIntRate[i][1]%>" size="10"/></td>
    			<td><input type="text" name="DateTo" value="<%=""+penalIntRate[i][2]%>" size="10"/></td>
    			<td><input type="text" name="Category" value="<%=""+penalIntRate[i][3]%>" size="10"/></td>
   				<td><input type="text" name="PenalRate" value="<%=""+penalIntRate[i][4]%>" size="10"/></td>
   				<td><input type="text" name="De_Tml" value="<%=""+penalIntRate[i][5]%>" size="10"/></td>
    			<td><input type="text" name="De_User" value="<%=""+penalIntRate[i][6]%>" size="10"/></td>
   				<td><input type="text" name="De_Datetime" value="<%=""+penalIntRate[i][7]%>" size="10"/></td>
   			
		</tr>
		<%} %>
				<core:if test="${requestScope.AddRow!=null}">
	   				<tr>
	   						<td><input type="text" name="LoanType"   size="10"/></td>
							<td><input type="text" name="DateFrom" size="10"/></td>
							<td><input type="text" name="DateTo" size="10"/></td>
							<td><input type="text" name="Category" size="10"/></td>
							<td><input type="text" name="PenalRate" size="10"/></td>
							<td><input type="text" name="De_Tml" size="10"/></td>
							<td><input type="text" name="De_User" size="10"/></td>
							<td><input type="text" name="De_Datetime" size="10"/></td>
					</tr>
		   		</core:if>	
		</table>
		</div>
		</core:if>	 

    <core:if test="${requestScope.Purposes!=null}">
  	 
         <div id="LoanPurposes" style="display:block;overflow:scroll;width:650px;height:210px;">
		<table>
		<tr>
			<td>PurposeNo</td><td>PurposeDesc</td><td>De_User</td><td>De_Tml</td><td>De_Datetime</td>
		</tr>
		<%for(int i=0;i<purposes.length;i++){ %>
    	<tr>
     			<td><input type="text" name="PurposeNo"  value="<%=""+purposes[i][0]%>" size="10"/></td>
    			<td><input type="text" name="PurposeDesc" value="<%=""+purposes[i][1]%>" size="10"/></td>
    			<td><input type="text" name="De_User" value="<%=""+purposes[i][2]%>" size="10"/></td>
    			<td><input type="text" name="De_Tml" value="<%=""+purposes[i][3]%>" size="10"/></td>
  				<td><input type="text" name="De_Datetime" value="<%=""+purposes[i][4]%>" size="10"/></td>
   		</tr>	
		<%} %> 
				<core:if test="${requestScope.AddRow!=null}">
	   				<tr>
	   						<td><input type="text" name="PurposeNo"  size="10"/></td>
							<td><input type="text" name="PurposeDesc" size="10"/></td>
							<td><input type="text" name="De_User" size="10"/></td>
							<td><input type="text" name="De_Tml" size="10"/></td>
							<td><input type="text" name="De_Datetime" size="10"/></td>
					</tr>
		   		</core:if>	
		</table>
 		</div>
 	</core:if>	


 <core:if test="${requestScope.StrDocParam!=null}">
  	 
         <div id="LoanPurposes" style="display:block;overflow:scroll;width:650px;height:210px;">
		<table>
		<tr>
			<td>DocNo</td><td>DocDesc</td><td>LoanCat</td><td>De_Tml</td><td>De_User</td><td>De_Datetime</td>
		</tr>
		<%for(int i=0;i<docParam.length;i++){ %>
    	<tr>
    		<td><input type="text" name="DocNo"   value="<%=""+docParam[i][0]%>"  size="10"/></td>
			<td><input type="text" name="DocDesc"  value="<%=""+docParam[i][1]%>" size="10"/></td>
			<td><input type="text" name="LoanCat"  value="<%=""+docParam[i][2]%>" size="10"/></td>
	    	<td><input type="text" name="De_Tml"  value="<%=""+docParam[i][3]%>" size="10"/></td>
			<td><input type="text"  name="De_User" value="<%=""+docParam[i][4]%>" size="10"/></td>
			<td><input type="text"  name="De_Datetime"  value="<%=""+docParam[i][5]%>" size="10"/></td>
		
	 	</tr>
	 	<%} %>
	 		<core:if test="${requestScope.AddRow!=null}">
	   				<tr>
	   					<td><input type="text" name="DocNo"    size="10"/></td>
						<td><input type="text" name="DocDesc"  size="10"/></td>
						<td><input type="text" name="LoanCat"  size="10"/></td>
	    				<td><input type="text" name="De_Tml"   size="10"/></td>
						<td><input type="text" name="De_User"  size="10"/></td>
						<td><input type="text" name="De_Datetime" size="10"/></td>
	   				</tr>
		   	</core:if>	
	 
	</table>
  	</div>
  </core:if>
				
</html:form> 
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	     
</body>
</html>



