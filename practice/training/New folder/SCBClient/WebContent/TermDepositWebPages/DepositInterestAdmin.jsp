<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>  
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>    
<%@page import="java.util.Map"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Deposit Admin</title>
	
	<style type="text/css" media="all">
            @import url("<%=request.getContextPath() %>/css/alternative.css");
            @import url("<%=request.getContextPath() %>/css/maven-theme.css");
            @import url("<%=request.getContextPath() %>/css/site.css");
            @import url("<%=request.getContextPath() %>/css/screen.css");
	</style>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/print.css" type="text/css" media="print" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/Images/DMTStyle.css" type="text/css" media="print" />
    <script type="text/javascript">
    
    	function set(target){
    	
        	document.forms[0].forward.value=target;
        	alert(document.forms[0].forward.value);
        	document.forms[0].submit();
        };
        
        function showmessage()
        {
        
        if(document.getElementById("totaltesting").value!=0)
	    {
		
		alert(document.getElementById("totaltesting").value);	
		
			
	    }
	    
	    
        var ele=document.forms[0].elements;
    	for(var i=0;i<ele.length;i++)
	    {
	      if(ele[i].type=="hidden")
	      {
	      ele[i].value="";
	      }
	    }
        
      }
        
        
    </script>
    <h1 class="h2"><center>  Deposit Interest  </center></h1>
</head>
<body  onload="showmessage()">
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
	<html:form action="/TermDeposit/Admin?pageId=13021">
	
	
	<html:hidden property="forward"></html:hidden>
	<html:hidden property="testing" styleId="totaltesting"></html:hidden>
	<br/>
	<br/>
	<br/>
	<br/>
	
		<html:select property="fd_actype" >
			<core:forEach var="FdAcType" items="${requestScope.FdAcType}" >
				<html:option value="${FdAcType.moduleCode}">${FdAcType.moduleAbbrv}-${FdAcType.moduleCode}</html:option>
			</core:forEach>
			
		</html:select>
		<html:select property="fd_table">
			<core:forEach var="IntType" items="${requestScope.IntType}">
				<html:option value="${IntType}">${IntType}</html:option>
			</core:forEach>
		</html:select>	
		<input type="submit" value="Refresh" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Refresh'" />
		<br/>
		<br/>
		<table>
		<tr>
		<td>
		<div style="display:block; overflow:scroll; width: 950px;height: 300px">
		<display:table name="IntList" id="IntList" class="its" list="${IntList}">
		
		<display:column title="${checkAll}" style="width:1%"><input type="checkbox" name="id" value="${IntList.id}" style="margin:0 0 0 4px" onclick="radio(this)" <core:if test="${param.id==IntList.id and param.method != 'Save' and param.method != 'Clear' and  param.method != 'Reset'}">checked="checked"</core:if> /></display:column>
		
		<core:if test="<%=request.getAttribute("table").equals("DepositIntRate")||request.getAttribute("table").equals("DepositQuantumRate")||request.getAttribute("table").equals("DepositCategoryRate")%>">
		<display:column style="width:3%" title="ID">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="id"  readonly="readonly"  style="padding:0;background: transparent;border: 0px" style="padding:0" value="${IntList.id}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.id}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		
			<core:if test="<%=request.getAttribute("table").equals("DepositIntRate")||request.getAttribute("table").equals("DepositQuantumRate")||request.getAttribute("table").equals("DepositCategoryRate")%>">
			<display:column style="width:3%" title="Frm Date">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="frm_date"  style="padding:0;" style="padding:0" value="${IntList.frm_date}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.frm_date}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("DepositIntRate")||request.getAttribute("table").equals("DepositQuantumRate")||request.getAttribute("table").equals("DepositCategoryRate")%>">
		<display:column style="width:3%" title="To Date">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<core:if test="${AdminList.Mem_cat==''}">
						<input type="text" name="to_date" style="padding:0;"  value=""   
						/>
						<%System.out.println("hi all"); %>
					</core:if>
					<core:if test="${IntList.to_date!=''}">
						<input type="text" name="to_date" style="padding:0;"  value="${IntList.to_date}"   
						/>
							<%System.out.println("hi all ashjasdgjg"); %>
					</core:if>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.to_date}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("DepositIntRate")||request.getAttribute("table").equals("DepositQuantumRate")||request.getAttribute("table").equals("DepositCategoryRate")%>">
		<display:column style="width:3%" title="Frm Days">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="frm_days" style="padding:0" value="${IntList.frm_days}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.frm_days}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("DepositIntRate")||request.getAttribute("table").equals("DepositQuantumRate")||request.getAttribute("table").equals("DepositCategoryRate")%>">
		<display:column style="width:3%" title="To Days">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="to_days" style="padding:0" value="${IntList.to_days}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.to_days}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("DepositQuantumRate")||request.getAttribute("table").equals("DepositCategoryRate")%>">
		<display:column style="width:3%"   title="Category">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="cat" style="padding:0" value="${IntList.to_days}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.cat}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("DepositQuantumRate")%>">
		<display:column style="width:3%" title="MinAmt">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="min_amt" style="padding:0" value="${IntList.to_days}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.min_amt}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("DepositQuantumRate")%>">
		<display:column style="width:3%" title="MaxAmt">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="max_amt" style="padding:0" value="${IntList.max_amt}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.max_amt}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("DepositIntRate")%>">
		<display:column style="width:3%" title="Int Rate">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="int_rate" style="padding:0" value="${IntList.int_rate}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.int_rate}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("DepositQuantumRate")||request.getAttribute("table").equals("DepositCategoryRate")%>">
		<display:column style="width:3%;" title="Extra Rate">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="extr_rate" style="padding:0" value="${IntList.extr_rate}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.extr_rate}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("DepositCategoryRate")%>">
		<display:column style="width:3%;" title="Extra Loan Rate">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="extr_LoanRate" style="padding:0" value="${IntList.extr_LoanRate}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.extr_LoanRate}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		
		
		<core:if test="<%=request.getAttribute("table").equals("PeriodLimit")||request.getAttribute("table").equals("QuantumLimit")%>">
		<display:column style="width:3%;" title="Mod Type">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="mod_Ty" style="padding:0" value="${IntList.mod_Ty}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.mod_Ty}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("PeriodLimit")||request.getAttribute("table").equals("QuantumLimit")%>">
		<display:column style="width:3%;" title="Srl No.">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="srl_No" style="padding:0" value="${IntList.srl_No}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.srl_No}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("PeriodLimit")||request.getAttribute("table").equals("QuantumLimit")%>">
		<display:column style="width:3%;visibility" title="Limit">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="limit" style="padding:0" value="${IntList.limit}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.limit}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("PeriodLimit")||request.getAttribute("table").equals("QuantumLimit")%>">
		<display:column style="width:3%;visibility" title="Frm Limit">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="frm_limit" style="padding:0" value="${IntList.frm_limit}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.frm_limit}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("PeriodLimit")||request.getAttribute("table").equals("QuantumLimit")%>">
		<display:column style="width:3%;visibility" title="To Limit">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="to_limit" style="padding:0" value="${IntList.to_limit}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.to_limit}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		
		<core:if test="<%=request.getAttribute("table").equals("Products")%>">
		<display:column style="width:3%;visibility" title="Dpdl Date">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="dpdl_date" style="padding:0" value="${IntList.dpdl_date}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.dpdl_date}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("Products")%>">
		<display:column style="width:3%;visibility" title="Prod date">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="prod_date" style="padding:0" value="${IntList.prod_date}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.prod_date}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("Products")%>">
		<display:column style="width:3%;visibility" title="Rinve Prod Date">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="rinve_prod_date" style="padding:0" value="${IntList.rinve_prod_date}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.rinve_prod_date}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		
		<core:if test="<%=request.getAttribute("table").equals("QtrDefinition")%>">
		<display:column style="width:3%;visibility" title="Month">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="month" style="padding:0" value="${IntList.month}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.month}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("QtrDefinition")%>">
		<display:column style="width:3%;visibility" title="Quartr Defn">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="quart_defn" style="padding:0" value="${IntList.quart_defn}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.quart_defn}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("QtrDefinition")%>">
		<display:column style="width:3%;visibility" title="Half yearly Defn">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="halfyear_defn" style="padding:0" value="${IntList.halfyear_defn}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.halfyear_defn}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		<core:if test="<%=request.getAttribute("table").equals("QtrDefinition")%>">
		<display:column style="width:3%;visibility" title="Yearly Defn">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==IntList.id }">
					<input type="text" name="yearly_defn" style="padding:0" value="${IntList.yearly_defn}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${IntList.yearly_defn}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		</core:if>
		
		
	</display:table>
		
		
		</div>
		</td>
		</tr>
		</table>
		
	<br/>
	
	<input type="submit" value="Edit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Edit'" />
	<input type="submit" value="Add" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Add'"/>
	<input type="submit" onclick="location.href='?method=Save'" class="ButtonBackgroundImage" value="Save" name="method"/>
	<input type="submit" value="Delete" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Delete'"/>
	<input type="submit" value="Clear" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Clear'"/>
	<br/>
	
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<center>
	<input type="submit" value="Submit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Submit'" />
	</center>
	<%}else{%>
	<center>
	<input type="submit" value="Submit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Submit'" disabled="true"/>
	</center>
	<%} %>
	</html:form>
	
	<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
	
	
	
</body>
</html>