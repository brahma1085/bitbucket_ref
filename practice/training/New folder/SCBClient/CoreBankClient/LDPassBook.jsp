<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>    
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loansOnDeposit.LoanReportObject"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page language="java" import="javax.swing.filechooser.*" %>
<%@page import="javax.swing.JFileChooser"%>
<%@page import="java.io.FileOutputStream"%>
<html>
<head>

<script type="text/javascript">

   function but_value(target)
	{
	   document.forms[0].button_value.value=target;
		document.forms[0].submit();
		if(document.forms[0].txt_acno.value=="0")
		{
			alert("Plz enter Account Num");
			document.forms[0].txt_acnoo.focus();
			return false;
		}
		
    };
    
    function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) ) {
				return true ;
                
            	}
            else{
              	alert("Alphabets are not allowed!!!!!!!Please enter numbers only ");
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
         		alert("only "+size+" digits allowed")
         		txt.value="";
         		return false;
          	}
         }
    
    
    function clear_fun()
    {
    	alert("clearing");
    	var ele=document.forms[0].elements;
    	document.getElementById("table1").style.display='none';  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    
    
    
    
    
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>LDPassBook</center></h2> 
</head>
<body class="Mainbody">


<%!ModuleObject[] LoanACType; %>
<%LoanACType=(ModuleObject[])request.getAttribute("LoanACType");%>

<%! String Name,Dep_date,sanc_date,Dep_actype,Mat_dat,acc_status,mailid; %>
<%! double sanc_amt,Int_rate,Loan_rate,Mat_amt,Dep_amt; %>
<%! int period_in_days,Dep_acno,cid,phonenum,Nom_num; %>

<%period_in_days=(Integer)request.getAttribute("period_in_days");%>

<%Dep_acno=(Integer)request.getAttribute("Dep_acno");%>

<%cid=(Integer)request.getAttribute("cid");%>

<%Nom_num=(Integer)request.getAttribute("Nom_num");%>

<%phonenum=(Integer)request.getAttribute("phonenum");%>

<%sanc_amt=(Double)request.getAttribute("sanc_amt");%>

<%Int_rate=(Double)request.getAttribute("Int_rate");%>

<%Loan_rate=(Double)request.getAttribute("Loan_rate");%>

<%Mat_amt=(Double)request.getAttribute("Mat_amt");%>

<%Dep_amt=(Double)request.getAttribute("Dep_amt");%>

<%Name=(String)request.getAttribute("Name");%>
<%mailid=(String)request.getAttribute("mailid");%>


<%Dep_date=(String)request.getAttribute("Dep_date");%>

<%sanc_date=(String)request.getAttribute("sanc_date");%>

<%Dep_actype=(String)request.getAttribute("Dep_actype");%>

<%Mat_dat=(String)request.getAttribute("Mat_dat");%>

<%acc_status=(String)request.getAttribute("acc_status");%>

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
<html:form action="/LDPassbook?pageId=6008" styleId="form1">





<table class="txtTable">
<tr>
	<td>
<table class="txtTable"> 
     
	<tr>
	   <td align="right"><bean:message key="lable.ac&ano"></bean:message></td>
	       <td><html:select property="combo_actype">
	       <%if(LoanACType!=null){ %>
	       <%for(int i=0;i<LoanACType.length;i++){%>
	       <html:option value="<%=""+i%>"><%=LoanACType[i].getModuleAbbrv()+"----"+LoanACType[i].getModuleDesc()%></html:option>
	       <%} %>
	       <%}%>
	       </html:select>
	       <html:text property="txt_acno" size="10" onkeypress="return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>
	   </td>
	</tr> 
	<tr>
	   <td align="right"><bean:message key="label.custname"></bean:message></td>
	       <%if(Name!=null){%>
	       <td><html:text property="txt_name" value="<%=Name%>" size="20" styleClass="formTextField" readonly="true"></html:text></td>
	       <%}%>
	       
	</tr>
	<tr>
	   <td align="right"><bean:message key="lable.dep_date"></bean:message></td>
	       <%if(Dep_date!=null){%> 
	       <td><html:text property="txt_dep_date" value="<%=Dep_date%>" size="10" styleClass="formTextField" readonly="true"></html:text></td>
	       <%}%>
	       
	</tr>
	<tr>
	   <td align="right"><bean:message key="lable.dep_amt"></bean:message></td>
	   <%if(Dep_amt!=0){%>
	       <td><html:text property="txt_dep_amt" value="<%=""+Dep_amt%>" size="10" styleClass="formTextField" readonly="true"></html:text></td>
	       <%}%>
	       
	   
	</tr>
	<tr>
	   <td align="right"><bean:message key="lable.san_date"></bean:message></td>
	   <%if(sanc_date!=null){%>
	       <td><html:text property="txt_san_date" value="<%=sanc_date%>" size="10" styleClass="formTextField" readonly="true"></html:text></td>
	       <%}%>
	       
	</tr>
	<tr>
	   <td align="right"><bean:message key="lable.Dep_ac"></bean:message></td>
	       <%if(Dep_actype!=null){%>
	       <td><html:text property="txt_Dep_ac" value="<%=Dep_actype%>" size="10" styleClass="formTextField" readonly="true"></html:text></td>
	       <%}%>
   	</tr>
	
</table>
	</td>
		<td>
		<table class="txtTable">
		   <tr>
	   			<td align="right"><bean:message key="lable.acstatus"></bean:message></td>
	   			<%if(acc_status!=null){%>
	       		<td><html:text property="txt_status" value="<%=acc_status%>" size="10" styleClass="formTextField" readonly="true"></html:text></td>
	       		<%}%>
		   </tr>
		    <tr>
	   			<td align="right"><bean:message key="lable.period"></bean:message></td>
	   			<%if(period_in_days!=0){%>
	       		<td><html:text property="txt_period" value="<%=""+period_in_days%>" size="10" styleClass="formTextField" readonly="true"></html:text></td>
	       		<%}%>
		   </tr>
		   <tr>
	   			<td align="right"><bean:message key="lable.mat_date"></bean:message></td>
	   			<%if(Mat_dat!=null){%> 
	       		<td><html:text property="txt_mat_date" value="<%=Mat_dat%>" size="10" styleClass="formTextField" readonly="true"></html:text></td>
	       		<%}%>
		   </tr>
		   <tr>
	   			<td align="right"><bean:message key="lable.mat_amt"></bean:message></td>
	   			<%if(Mat_amt!=0){%> 
	       		<td><html:text property="txt_mat_amt" value="<%=""+Mat_amt%>" size="10" styleClass="formTextField" ></html:text></td>
	       		<%}%>
		   </tr>
		   <tr>
	   			<td align="right"><bean:message key="lable.san_amt"></bean:message></td>
	   			<%if(sanc_amt!=0){%> 
	       		<td><html:text property="txt_san_amt" value="<%=""+sanc_amt%>" size="10" styleClass="formTextField" readonly="true"></html:text></td>
	       		<%}%>
		   </tr>
		   <tr>
	   			<td align="right"><bean:message key="lable.depacno"></bean:message></td>
	   			<%if(Dep_acno!=0){%> 
	       		<td><html:text property="txt_depacno" value="<%=""+Dep_acno%>" size="10" styleClass="formTextField" readonly="true"></html:text></td>
	       		<%}%>
		   </tr>

		</table>
		</td>
		<td>
			<table class="txtTable">
				
		        <tr>
	   				<td align="right"><bean:message key="lable.cid"></bean:message></td> 
	   				<%if(cid!=0){%>
	       			<td><html:text property="txt_cid" value="<%=""+cid%>" size="10" styleClass="formTextField" readonly="true" ></html:text></td>
	       			<%}%>
		        </tr>
		        <tr>
	   				<td align="right"><bean:message key="lable.phoneno"></bean:message></td>
	   				<%if(phonenum!=0){%> 
	       			<td><html:text property="txt_phoneno" value="<%=""+phonenum%>" size="10" styleClass="formTextField" readonly="true" ></html:text></td>
	       			<%}%>
		        </tr>
		        <tr>
	   				<td align="right"><bean:message key="lable.Nom"></bean:message></td>
	   				<%if(Nom_num!=0){%> 
	       			<td><html:text property="txt_Nom" value="<%=""+Nom_num%>" size="10" styleClass="formTextField" readonly="true"></html:text></td>
	       			<%}%>
		        </tr>
		       
		        <tr>
	   				<td align="right"><bean:message key="label.mailid"></bean:message></td>
	   				<%if(mailid!=null){%> 
	       			<td><html:text property="txt_mailid" value="<%=""+mailid%>" size="10" styleClass="formTextField" readonly="true" ></html:text></td>
	       			<%}%>
		        </tr>
		         <tr>
	   				<td align="right"><bean:message key="lable.intrate"></bean:message></td>
	   				<%if(Int_rate!=0){%> 
	       			<td><html:text property="txt_intrate" value="<%=""+Int_rate%>" size="10" styleClass="formTextField" readonly="true" ></html:text></td>
	       			<%}%>
		        </tr>
		        <tr>
	   				<td align="right"><bean:message key="lable.loanrate"></bean:message></td>
	   				<%if(Loan_rate!=0){%> 
	       			<td><html:text property="txt_loanrate" value="<%=""+Loan_rate%>" size="10" styleClass="formTextField" readonly="true"></html:text></td>
	       			<%}%>
		        </tr>
			</table>
		</td> 
		</tr>
		 <html:hidden property="button_value" value="error"></html:hidden>
		<tr>
		
	
	
		
		
			<table align="center">
				<tr>
					 <td><html:submit value="VIEW" onclick="but_value('View')" styleClass="ButtonBackgroundImage"></html:submit></td>
					 <td><html:submit style="visibility:hidden;"></html:submit></td>
					 
					 <td><html:button property="but_print" onclick="but_value('save')" styleClass="ButtonBackgroundImage">DownLoad</html:button></td>     
					      
					 <td><html:submit style="visibility:hidden;"></html:submit></td>     
					 <td><html:button property="clear" onclick="clear_fun()" styleClass="ButtonBackgroundImage">CLEAR</html:button></td>
					 <td><html:submit style="visibility:hidden;" ></html:submit></td>
     
				</tr>
			</table>
		</tr>
		
		<tr> 
		    <td>
		    <table class="txtTable">
		    	<tr>
		          <td>
		        	<div  id = "table1" style="display: block;overflow: scroll;width: 600px;height: 150px">
		           <display:table name="PassBook" id="PassBook" class="its"  >
		          	<display:column title="TranDate" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${PassBook.TranDate}"  /></display:column>
					<display:column title="Particular/Narr" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PassBook.ParticularNarr}"  /></display:column>
					<display:column title="Principal" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PassBook.Principal}"  /></display:column>
					<display:column title="Interest" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="formTextField" value="${PassBook.Interest}" /></display:column>
					<display:column title="Other" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${PassBook.Other}" /></display:column>
					<display:column title="total" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PassBook.total}" /></display:column>
					<display:column title="Debit" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PassBook.Debit}" /></display:column>
					<display:column title="balance" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${PassBook.balance}" /></display:column>
					
		          </display:table>
		          </div>
		          </td>
		       </tr> 
		       </table> 
		      
		      
			
	</table>
	</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</body>
</html>