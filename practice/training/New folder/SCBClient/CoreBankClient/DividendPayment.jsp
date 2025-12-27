<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Hashtable"%>
<%@page import="masterObject.share.DividendObject"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<html>
<head><title>Dividend Payment</title>
     <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
     
     
      <h2 class="h2">
      <center>Dividend Payment</center></h2>
      <hr>
      
     <script type="text/javascript">
     
     function only_numbers_date() {
	
	var cha=event.keyCode;
	if(cha>=47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  


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
     
     function reset123(){
    var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="text")
    {
    ele[i].value="";
    }
   else if(ele[i].type=="hidden")
    {
    ele[i].value="";
    }
    else if(ele[i].type=="checkbox")
    {
    ele[i].checked=false;
    }
    
    }
    
    }
     
        function set(target){
        document.forms[0].forward.value=target
        };
       function setval(){
       
        document.getElementById("Trans").style.display='block';
        
        }; 
        
        function showvchTable(){
        
          if(document.forms[0].cashPanel.value=="PayByCash"){
          document.getElementById("vchTable").style.display='block';
          document.getElementById("payTable").style.display='block';
          document.forms[0].cashPanel.value="Pay By Cash";
          }
          else{
          document.getElementById("vchTable").style.display='none';
          document.getElementById("payTable").style.display='none';
          document.forms[0].cashPanel.value="PayByCash";
          }
        };
        function showTrans(){
     
      if(document.forms[0].cashPayMode.value=="Transfer"){
         document.getElementById("Trans").style.display='block';
         
      }else{
         document.getElementById("Trans").style.display='none';
      }
    
     };
     
     function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     if(flagValue=="Transfer"){
     var con=confirm("Do you want transfe Dividend Amount to Respective accounts");
     if(con){
     var con1=confirm("Reconfiem Do you want transfe Dividend Amount to Respective accounts");
      if(con1){
      document.forms[0].submit();
      }
      else{
      return false;
      }
     }else{
     return false;
     }
     }else{
     document.forms[0].submit();
     }
     }
        
</script>
</head>        
<body class="Mainbody" onload="setval()">

<%!
    
    DividendObject[] div_pay;
    List div_list;
%>

<%
String divCustName=(String)request.getAttribute("DividendCustomerName");


  //div_pay=(DividendObject[])request.getAttribute("div_payment");
  div_list=(List)request.getAttribute("div_payment");
  if(div_list!=null){
	  System.out.println("The length of div list"+div_list.size());
  }
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
<html:form action="/Share/DividendPayment?pageId=4016">
<html:hidden property="flag"></html:hidden>
<input type="button" value="PayByCash" name="cashPanel" class="ButtonBackgroundImage" onclick="showvchTable()" />
   <input type="button" value="Transfer" name="method" class="ButtonBackgroundImage" onclick="setFlag(this.value)" />
 <table class="txtTable">
  <tr>
   <td><bean:message key="label.voucherno"></bean:message>
   <html:text property="voucher_no" styleClass="formTextFieldWithoutTransparent" size="8" onblur="javascript:document.forms[0].submit()" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"> </html:text></td>
  
<td><bean:message key="label.acType"></bean:message>  
  

 <html:select property="actype" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
   <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}">
    <html:option value="${acType.moduleCode}">
     <core:out value="${acType.moduleAbbrv}"></core:out>
   </html:option>
  </core:forEach>
</html:select>
 </td> 
</tr>  

<tr>
  <html:hidden property="forward" value="error"></html:hidden>
   <td>
   
    <!--<html:submit onclick="set('PayByCash')" styleClass="ButtonBackgroundImage"><bean:message key="label.paycash"></bean:message> </html:submit>
    --><!--<html:submit onclick="set('Transfer')" styleClass="ButtonBackgroundImage"><bean:message key="label.Transfer"></bean:message> </html:submit>
     
   --></td>
</tr>

<tr>
 <td>
 <bean:message key="label.shnum"></bean:message>
 <html:text property="acnum" styleClass="formTextFieldWithoutTransparent"  size="8"  onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"></html:text>
 </td>
 
 <td>
 <bean:message key="label.fromdate"></bean:message>
 <html:text property="frm_dt" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_date()"></html:text>
 </td>
 
 <td>
 <bean:message key="label.todate"></bean:message> 
 <html:text property="to_dt" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_date()"></html:text>
 </td>
</tr>

<tr>
 <td>
   <input type="submit" value="View" name="method" class="ButtonBackgroundImage" onclick="setFlag(this.value)" />
   <input type="button" value="Clear" name="cl" onclick="reset123()" class="ButtonBackgroundImage"/>
 </td>
</tr>


<tr>

 
  <%if(div_list!=null){
	  System.out.println("In JSPPPPPPPPPPP");
	  %>
  
  
<display:table name="div_payment" id="div_payment" class="its" list="${div_payment}">
		<display:column title="${checkAll}" style="width:1%"><input type="checkbox" name="id" value="${div_payment.id}" style="margin:0 0 0 4px" onclick="radio(this)"/></display:column>
		<display:column style="width:3%;text-align: right;" title="Shno">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==div_payment.id }">
					<input type="text" name="Sh_no"  readonly="readonly"  onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${div_payment.Sh_no}"	/>
				</core:when>
				<core:otherwise>
					<core:out value="${div_payment.Sh_no}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
	    
	    <display:column style="width:3%;text-align: right;" title="ShType">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==div_payment.id }">
					<input type="text" name="Shtype"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${div_payment.Shtype}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${div_payment.Shtype}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
	    
	    
	    <display:column style="width:3%;text-align: right;" title="Paymode">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==div_payment.id }">
					<input type="text" name="paymode"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${div_payment.paymode}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${div_payment.paymode}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
  
        <display:column style="width:3%;text-align: right;" title="Divdate">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==div_payment.id }">
					<input type="text" name="divdate"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${div_payment.divdate}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${div_payment.divdate}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
 
       <display:column style="width:3%;text-align: right;" title="Divamt">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==div_payment.id }">
					<input type="text" name="divamt"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${div_payment.divamt}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${div_payment.divamt}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
  
        <display:column style="width:3%;text-align: right;" title="Drfamt">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==div_payment.id }">
					<input type="text" name="drfamt"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${div_payment.drfamt}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${div_payment.drfamt}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
        
        
        <display:column style="width:3%;text-align: right;" title="cvnum">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==div_payment.id }">
					<input type="text" name="cvnum"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${div_payment.cvnum}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${div_payment.cvnum}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
		<display:column style="width:3%;text-align: right;" title="voucno">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==div_payment.id }">
					<input type="text" name="voucno"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${div_payment.voucno}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${div_payment.voucno}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
  
 </display:table>
 
 <%} %>


</tr>
</table>

<table>
<tr>
 <td colspan="2">PayMode:<html:select property="cashPayMode" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue" onchange="showTrans()">
 <html:option value="Pay By Cash">Pay By Cash</html:option>
 <html:option value="Transfer">Transfer</html:option>
 <html:option value="PayOrder">Pay Order</html:option>
 </html:select>
 </td>
</tr>
<tr id="Trans" style="display:none;">
<td>AccountType:<html:select property="cashAcType" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue" onchange="showTrans()">
<html:option value="1002001">SB</html:option>
<html:option value="1007001">CA</html:option>
<html:option value="1014001">CC</html:option>
<html:option value="1015001">OD</html:option>
</html:select>
</td>
<td>
Account No:<html:text property="cashAcno" size="5" onblur="setFlag('accountNumber')"></html:text>
<%if(divCustName!=null){ %>
<font color="red"><%=divCustName%></font>
<%} %>
</td>
</tr>
</table>
<%
String ver=(String)request.getAttribute("DividendVerify");
String payDiv=(String)request.getAttribute("DividendPay");
if(ver!=null){
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
<input type="button" name="verify" value="Verify" onclick="setFlag(this.value)" class="ButtonBackgroundImage" />
<%}else{ %>
<input type="button" name="verify" value="Verify" onclick="setFlag(this.value)" class="ButtonBackgroundImage" disabled="disabled" />
<%} %>
<%}else if(payDiv!=null){ %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
<input type="submit" value="Pay" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Edit'" />
<%}else{ %>
<input type="submit" value="Pay" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Edit'" disabled="disabled"/>
<%} %>
<%} %>
</html:form>
 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>