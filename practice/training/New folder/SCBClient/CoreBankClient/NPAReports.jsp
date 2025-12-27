<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>    
<%@page import="java.util.Vector"%>
<%@page import="java.util.Map"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function fuc_valchange()
	{
		if(document.forms[0].acctype.value=="All Types")
		{
			document.forms[0].from_accno.disabled = true;
			document.forms[0].to_accno.disabled = true;
			document.getElementById("tabletype").focus();
			//document.forms[0].combo_actype.disabled = true;
			return false;
		}
		else
		{
			document.forms[0].from_accno.disabled = false; 
			document.forms[0].to_accno.disabled = false;
			//document.forms[0].combo_actype.disabled = false;
			return false;   
		}
	};
function set(target){

if(document.forms[0].acctype.value!="All Types"){
if(document.getElementById("from_accno").value=="0")
	{
		alert('Enter the From Account Number!');
		document.getElementById("from_accno").focus();
		return false;
		}else if(document.getElementById("from_accno").value=="")
	{
		alert('Enter the From Account Number!');
		document.getElementById("from_accno").focus();
		return false;
		}
		else if(document.getElementById("to_accno").value=="")
	{
		alert('Enter the To Account Number!');
		document.getElementById("to_accno").focus();
		return false;
		}
		else if(document.getElementById("to_accno").value=="")
	{
		alert('Enter the To Account Number!');
		document.getElementById("to_accno").focus();
		return false;
		}
else{
  document.forms[0].forward.value=target; 
  document.forms[0].submit(); 
//return true;
}



}
	//alert(target);
    
};

function clearPage()
 	   { 
 	   
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
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
}  ;
function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
   




 

</script>
</head>
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>NPA Reports</center></h2> 
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
<body onload="fuc_valchange()">
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
 <html:form action="/Loans/NPAReport?pageidentity.pageId=5047">
 <%System.out.println("******Hi /Loans/NPAReport ********");%>
 <%!
   Vector vec_date;
   String str[][];
   ModuleObject[] modobj;
   Iterator it;
   Object obj;
 
 %>

  <%
    vec_date = (Vector)request.getAttribute("NPAProcessDate");
  System.out.println("Vec===>"+vec_date);
    str = (String[][])request.getAttribute("NPACode");
    System.out.println("ProcessDate===>"+str.length);
    modobj = (ModuleObject[])request.getAttribute("LoanModuleCode");
  %>
  <table class="txtTable">
   <tr>
     <td><bean:message key="label.npaprocesseddate"></bean:message></td>
         <td><html:select property="npadate" styleClass="formTextFieldWithoutTransparent">
     	<% it = vec_date.iterator();
     	while(it.hasNext()){
     		obj = it.next();
     		%>
     	   		<html:option value="<%=""+obj%>"><%=""+obj %></html:option>
     	<%}%>	
     	 </html:select>
     </td>
   
     <td><bean:message key="label.assettype"></bean:message></td>
     <td><html:select property="assettype" styleClass="formTextFieldWithoutTransparent">
     <html:option value="All">ALL</html:option>
          <%if(str!=null){
    	 for(int i=0;i<str.length;i++){
    	   	 
    	     System.out.println("Sys====>"+str[i][1]);
     %>
           <html:option value="<%=str[i][1]%>"><%=str[i][1]%></html:option>
     <%} }%>
     </html:select>
     </td>
   </tr>
   
   <tr>  
     <td><bean:message key="label.combo_loan"></bean:message></td>
      		<td>  
      			  <html:select property="acctype" styleClass="formTextFieldWithoutTransparent" onchange="return fuc_valchange()">
      			  <html:option value="All Types"></html:option>
         		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      			  </core:forEach>
       			  </html:select>
     </td>
    <td><bean:message key="label.tabletype"></bean:message></td>
    	<td><html:select property="tabletype" styleClass="formTextFieldWithoutTransparent">
    		<html:option value="180"></html:option>
    		<html:option value="90"></html:option>
    	</html:select>
    </td>	
     
   </tr>
  
   <tr>
     <td><bean:message key="label.fromacc"></bean:message></td>
     <td><html:text property="from_accno" size="8" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
     
     <td><bean:message key="label.toacc"></bean:message></td>
     <td><html:text property="to_accno" size="8" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
   </tr>
   
   <html:hidden property="forward" value="error"/>
   	<html:hidden property="testing" styleId="totaltesting"></html:hidden>

			
   
   <tr>
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
   <td>
   <html:submit onfocus="set('view');" styleClass="ButtonBackgroundImage" value="View"></html:submit></td>
   <td><html:button property="but_print" onclick="set('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
   <!--<td><html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage"  value="Print"></html:button></td>
   --><td><html:button property="Clear" value="Clear" styleClass="ButtonBackgroundImage" onclick="clearPage()"></html:button></td>
   <%}else{ %>
   <td>
   <html:submit onfocus="set('view');" styleClass="ButtonBackgroundImage" disabled="true" value="View"></html:submit></td>
   <td><html:button property="but_print" disabled="true" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
   <!--<td><html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage"  value="Print"></html:button></td>
   --><td><html:button property="Clear" value="Clear" styleClass="ButtonBackgroundImage" disabled="true"></html:button></td>
  <%} %>
   </tr>
   </table>
   
   
   <table>
       <tr>
		          <td>
		        	<div  id = "table1" style="display: block;overflow: scroll;width: 900px;height: 250px">
		           <display:table name="NPAReport" id="NPAReport" class="its"  >
		          	<display:column title="Serial" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${NPAReport.No}" size="5" /></display:column>
					<display:column title="A/C No" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.Number}" size="6" /></display:column>
					<display:column title="Name" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.Name}"  size="25" /></display:column>
					<display:column title="Sanct Amt" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${NPAReport.Sanctioned}" size="10" /></display:column>
					<display:column title="Disbursement Date" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.Dateof}" size="10"/></display:column>
					<display:column title="No Of Inst" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.Numberof}" size="5"/></display:column>
					<display:column title="Inst Amt" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.Installment}" size="8"/></display:column>
					<display:column title="Recovery" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.TypeOf}" size="5" /></display:column>
					<display:column title="Rec Amount" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.Amount}" size="10"/></display:column>
					<display:column title="Disb Amt" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.DisbAmt}" size="10"/></display:column>
					<display:column title="Principal" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.PrinOverDue}" size="10"/></display:column>
					<display:column title="Int Upto Date" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.IntUptoDate}" size="10" /></display:column>
					<display:column title="Int OverDue Amt" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.IntOverdueAmt}" size="10" /></display:column>
					<display:column title="OverDue Period" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.IntOverduePeriod}" size="5"/></display:column>
					<display:column title="Last Tran Date" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.TranDate}" size="10"/></display:column>
					<display:column title="NPA Since Date" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.NPASince}" size="10"/></display:column>
					<display:column title="NPA Towards" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.NPATowards}" size="8"/></display:column>
					<display:column title="Loan Balance" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.Loan}" size="10"/></display:column>
					<display:column title="Provision" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${NPAReport.Provision}" size="10" /></display:column>
		          </display:table>
		          </div>
		          </td>
		       </tr> 
  </table>
</html:form> 
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	  
</body>
</html>