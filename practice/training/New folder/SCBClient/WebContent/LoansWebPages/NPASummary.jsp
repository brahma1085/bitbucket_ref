<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Vector"%>
<html>
<head>
<script type="text/javascript">
	function set(target)
	{
		//alert(target);
		//alert(document.forms[0].forward.value);
		document.forms[0].forward.value=target;
		document.forms[0].submit();
	}


</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>NPA Summary</center></h2> 
<body>
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

<%!Vector NPAProcessedDate; %>
<%NPAProcessedDate=(Vector)request.getAttribute("NPAProcessedDate");
  System.out.println("NPAProcess date=====>swe==========>"+NPAProcessedDate);	
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/NPALoanSummary?pageidentity.pageId=5025">
  <table>
    <tr>
       <td>
          <bean:message key="label.npaprocesseddate"></bean:message>
          <html:select property="processdate" styleClass="formTextFieldWithoutTransparent">
          <%for(int i=0;i<NPAProcessedDate.size();i++){ %>
          <html:option value="<%=""+NPAProcessedDate.get(i)%>"><%=""+NPAProcessedDate.get(i)%></html:option>
          <%}%>
          </html:select>
       </td>
       <td>
       	  <bean:message key="label.days"></bean:message>	
       	  <html:select property="num_days" styleClass="formTextFieldWithoutTransparent" onchange="submit()">	
       	  <html:option value="180">180</html:option>
       	  <html:option value="90">90</html:option>
          </html:select>
       </td>
       
    </tr>
    
    <tr>
      <td><html:hidden property="forward" value="error"></html:hidden></td>
      	<html:hidden property="testing" styleId="totaltesting"></html:hidden>
      <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
      <td><html:submit onclick="set('view');" value="View" styleClass="ButtonBackgroundImage">View</html:submit></td>
      
			<td><html:button property="but_print" onclick="set('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
      <td><html:submit property="Clear" styleClass="ButtonBackgroundImage">Clear</html:submit></td>
      <%}else{ %>
      
        <td><html:submit disabled="true" value="View" styleClass="ButtonBackgroundImage">View</html:submit></td>
      
			<td><html:button property="but_print" disabled="true" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
      <td><html:submit property="Clear" disabled="true" styleClass="ButtonBackgroundImage">Clear</html:submit></td>
      <%} %>
    </tr>
  </table>
  
  				<tr>
		          <td>
		        	<div  id = "table1" style="display: block;overflow: scroll;width: 600px;height: 150px">
		           <display:table name="NpaSummary" id="NpaSummary" class="its"  >
		          	<display:column title="Srl.No" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${NpaSummary.SrlNo}"  /></display:column>
					<display:column title="Loan Type" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NpaSummary.LoanType}"  /></display:column>
					<display:column title="No of A/C's" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NpaSummary.NoofACs}"  /></display:column>
					<display:column title="Balance O/S" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="formTextField" value="${NpaSummary.BalanceOS}" /></display:column>
					<display:column title="Amt OD" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${NpaSummary.AmtOD}" /></display:column>
					<display:column title="Asset Code" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NpaSummary.AssetCode}" /></display:column>
					<display:column title="Asset Desc" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NpaSummary.AssetDesc}" /></display:column>
					<display:column title="No of A/C's per AssetCode" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NpaSummary.NoofAcsperAssetCode}" /></display:column>
					<display:column title="Bal Amount" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NpaSummary.BalAmount}" /></display:column>
					<display:column title="Prov Perc(%)" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NpaSummary.ProvPerc}" /></display:column>
					<display:column title="Prov Amount" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NpaSummary.ProvAmount}" /></display:column>
					<display:column title="Total Provision" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NpaSummary.TotalProvision}" /></display:column>
					
		          </display:table>
		          </div>
		          </td>
		       </tr> 
  
</html:form>  
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	 
</body>
</html>