<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib  prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib  prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
  <%@page import="java.util.Map"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.DocumentsObject"%>
<html>
<head>

<script type="text/javascript">

function button_fun(target)
{
if(document.getElementById("ac_no").value=="0")
	{
		alert('Accoount Number Cannot be Zero!');
		document.getElementById("ac_no").focus();
		return false;
		}else if(document.getElementById("ac_no").value=="")
	{
		alert('Accoount Number Cannot be Blank!');
		document.getElementById("ac_no").focus();
		return false;
		}
else{
//alert(target);
document.forms[0].button_value.value=target;
return true;
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
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
   
function func_clear()
{

    var v = document.forms[0].elements;
    for(var i=0;i<=v.length;i++)
    {
       if(v[i].type =="text")
       {
          v[i].value = "";

        }
     }
 
};


 



</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
	
	function fun_addrow(ids)
	{ 
		
		if(ids.checked)  
		{   
			
		    var val=document.getElementById("div1");
		    alert(val);
		    val.innerHTML+="<table>";
		   	val.innerHTML+="<tr><td><input type=text value="+ids.value+"></td>"
		   	val.innerHTML+="</tr>"
		    val.innerHTML+="</table>";
		    
			
		} 
	}; 
function fun_removerow(idsss)
{			if(idsss==checked)
			{
			var val=document.getElementById("div2");
		    alert(val);
		    val.innerHTML+="<table>";
		   	val.innerHTML+="<tr><td><input type=text value="+idsss.value+"></td>"
		   	val.innerHTML+="</tr>"
		    val.innerHTML+="</table>";
		    }
} ;     
    function fun_help()  
    {
    	var cha =   event.keyCode;
    	if(cha==104)
    	{
    		
    		window.open("/SCBClient/Help.jsp?path=Loans/LoanDocsUpdation-24");
    		var url="/SCBClient/Help.jsp?path=/Loans/LoanDocsUpdation?help=24";
    		return false;
    	}
    	else
    	{
    		document.forms[0].submit();
    		return true;
    	}
    	
    }
             
</script>


<title>Insert title here</title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<%System.out.println("*********Hi from Loan Default process**********");%>
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
System.out.println("======1111111111========>");
 ModuleObject LoanAccType[];
   DocumentsObject PledgeDoc[]; 
   Object[][] LoanDocumentList;	
   System.out.println("=====888888888=========>");
%>
</head>
<%
System.out.println("======66666666========>");
  LoanAccType=(ModuleObject[])request.getAttribute("LoanAccType"); 
  System.out.println("=======444444444=======>"+LoanAccType);
  LoanDocumentList=(Object[][])request.getAttribute("LoanDocumentList");
  System.out.println("=======3333=======>"+LoanDocumentList);	
  PledgeDoc=(DocumentsObject[])request.getAttribute("PledgeDoc");
 System.out.println("=======5555555=======>"+PledgeDoc); 

System.out.println("LoanDocumentList==============>"+LoanDocumentList); %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<html:form action="/Loans/LoanDocsUpdation?pageidentity.pageId=5026">
<body class="Mainbody">
<h2 align="center" style="font-size: medium;color: purple" ><bean:message  key="label.head3"></bean:message></h2>
	
<table>	
	<td>
	
	<table class="txtTable">
	
	<tr>
		<td align="right"><bean:message key="label.actype"/></td>
		<td><html:select property="ac_type">
		<%if(LoanAccType!=null){ %>
		<%for(int i=0;i<LoanAccType.length;i++){%>
		<html:option value="<%=""+LoanAccType[i].getModuleCode()%>"><%= LoanAccType[i].getModuleAbbrv()%></html:option>
		<%}}%>
		</html:select></td>
	</tr>
	
	<tr>
		<td align="right"><bean:message key="label.acno"/></td>
		<td><html:text property="ac_no"  onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" 
		 onblur="return fun_help()"></html:text></td>
	</tr>
	
	<html:hidden property="button_value" value="error"/>
	
	  
	<tr>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
		<td align="right"><html:submit styleClass="ButtonBackgroundImage"  onfocus="button_fun('submit')"></html:submit></td>
		<td><html:button property="clear" value="clear" styleClass="ButtonBackgroundImage" onclick="func_clear()"></html:button></td>
		<%}else{ %>
		<td align="right"><html:submit styleClass="ButtonBackgroundImage" disabled="true" onfocus="button_fun('submit')"></html:submit></td>
		
			<td><html:button property="clear" value="clear" styleClass="ButtonBackgroundImage" disabled="true" onclick="func_clear()"></html:button></td>
		<%} %>
	</tr>
	
	<tr>
		<td>
		   
		</td>
	</tr>
	</table>
	
	</td>
	
	
	
	<td>
	<div style="overflow: scroll;width:350px;height:200px;">
		<table align="center" border="1" cellspacing="1" cellpadding="0.5"  bordercolor="blue" style="border-style:outset;">
		      
		      	 <th class="PageHeader"><%="Serial Num"%></th>	
		      	 <th class="PageHeader"><%="Documents"%></th>
		      	 <th class="PageHeader"><%="  Select  "%></th>
		       <%if(LoanDocumentList!=null)
			   { 
			   		for(int i=0;i<LoanDocumentList.length;i++)
					{%>	
						
						 <tr>
						 	<td width="150px"><%=""+(i+1)%></td>	  
							<td width="150%" id="id2"><%=LoanDocumentList[i][1]%></td>
							<td width="50%"><html:checkbox property="check_box" value="<%=(i+1)+""+LoanDocumentList[i][1]%>" onclick="fun_addrow(this)"></html:checkbox></td>
						</tr>
						
					<%} 		
				}%>
		</table>	
	</div>	
	</td>
	
	<tr>
	</tr>
	
	<tr>
	<td>
		<table width="300px" height="200px" style="border-bottom-color:black;border-bottom-style:solid;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
		
		<tr>
			<td>
				<h2 align="center" style="font-size: medium;color: purple" ><bean:message  key="label.head1"></bean:message></h2>
			</td>
		</tr>
	
	<tr>
		<td>
		
		<div align="center" style="overflow: scroll;width:300px;height:150px;">
		<table  class="txtTable" align="center" border="1" cellspacing="1" cellpadding="0.5"  bordercolor="blue" style="border-style:outset;">
			<tr class="PageHeader">
				<td class="PageHeader"><%="SerialNum"%></td>
				<td class="PageHeader"><%="Documents"%></td>
				<td class="PageHeader"><%="OtherDetails"%></td>
				<td class="PageHeader"><%="Pledge Date"%></td>
				<td class="PageHeader"><%="Select"%></td>
			</tr>   
			<%if(PledgeDoc!=null)
			{
				for(int i=0;i<PledgeDoc.length;i++)
				{%>
					<%if(PledgeDoc[i].getReturned_date()==null){%>
					<tr>
						<td><html:text property="doccode" value="<%=""+PledgeDoc[i].getDoccode()%>" styleClass="formTextField"></html:text></td>
						<td width="150%"><%=PledgeDoc[i].getDoc_desc()%></td>
						<td width="150%"><%=PledgeDoc[i].getOther_details()%></td>
						<td width="50%"><%=PledgeDoc[i].getPledged_date()%></td>
						<td width="50%"><html:checkbox styleId="check1"  property="check_box" value="<%=PledgeDoc[i].getDoc_desc()%>" onclick="fun_removerow(this)"></html:checkbox></td>
					</tr>
					<%}%>
				<%}
			}%>
			
			<tr>
				<td id="div1"></td>
			</tr>
			
			</table>
			</div>
			
		</td>
		
	</tr>
		</table>
	</td>	
	
	
	
	<td>
		<table width="300px" height="200px" style="border-bottom-color:black;border-bottom-style:solid;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
		
		<tr>
			<td>
				<h2 align="center" style="font-size: medium;color: purple" ><bean:message  key="label.head2"></bean:message></h2>
			</td>
		</tr>
		
		<tr>
		<td>
		<div style="overflow:scroll;width:300px;height:150px;">
		<table align="center" border="1" cellspacing="1" cellpadding="0.5"  bordercolor="blue" style="border-style:outset;">
	
				<tr class="PageHeader">
					<td class="PageHeader"><%="SerialNum"%></td>
					<td class="PageHeader"><%="Documents"%></td>
					<td class="PageHeader"><%="OtherDetails"%></td>
					<td class="PageHeader"><%="Pledge Date"%></td>
					<td class="PageHeader"><%="Select"%></td>
				</tr>
				<tr>
				<td id="div2"></td>
			</tr>
					<tr>
						<td></td>
						</tr>
					<tr>
						
					</tr>
					<tr>
						
					</tr>
					
	</table>
	</div>
	
	
	</td>
	</tr>
	</table>
	
	</td>
	
	
	<html:hidden property="help" styleId="help"/>
	
	</tr>
	</table>
	</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>