<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.clearing.ChequeDepositionObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">

function call() 
{
	if(document.getElementById("flagset").value == 4) 
	 {
		 checkError();
	 }
	  
}

function set(falgVal)
{
	document.forms[0].flag.value=falgVal;
	document.forms[0].submit();
}
function setChkBox()
{
	
	var v=document.forms[0].chkBox;
	
	if(document.forms[0].selectAll.checked)
	{
	  for(var i=0;i<v.length;i++)
	  {
		v[i].checked=true;
	  }
	}
	else
	{
		for(var i=0;i<v.length;i++)
	  	{
			v[i].checked=false;
	  	}
	}
}
	


function checkformat(ids) 
{
            
   var cha = event.keyCode;
   var format = true;
   var allow=true;

   var ff =  ids.value;
            
   var dd = ff.split( '/' );

   if(dd.length == 3)
   {
       for(var i =0; i< dd.length; i++ )
       {
           if(i!= 2 && dd[i].length!= 2)
           {
				document.forms[0].validateFlag.value= "Problem In Date Format-DD/MM/YYYY ";
                format = false ;
                allow=false;
            } 
            else if(i==2 && dd[i].length != 4)
            {
	            document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
                format = false ;
                allow=false;
            }

        }
   } 
   else
   {
        allow=false;
        format = false ;
        document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
   }

   if(format)
   {
         var date=new Date();
         var dayCheck=dd[0];
         var monthCheck=dd[1];
         var yrCheck=dd[2];
         var mth=dd[1];
         dd[1]=dd[1]-1;
	     var days = 32 - new Date(dd[2],dd[1],32).getDate();
         if(dd[0].length==2)
         {
          if(dd[0]>days)
           {
			 document.forms[0].validateFlag.value="Day should not be greater than "+days+" ";
           	 allow=false;
           }
          if(dd[0]==00)
          {
          	document.forms[0].validateFlag.value="There is no date with 00";
          	allow=false;
          }
          if(mth<1 || mth>12)
          {
          document.forms[0].validateFlag.value="Month should be greater than 0 and \n lessthan 13"+mth+" ";
          allow=false;
          }
         }
         if(dd[2].length==4)
         {
          if((parseInt(dd[2])<=parseInt(date.getYear())))
          {
          }
          else
          {
          	document.forms[0].validateFlag.value="Year should be less than "+date.getYear()+" ";
          	allow=false;
          }
         }
        }
		if(allow)
		{
		    return true;
		}
		else
		{
		 document.forms[0].clgDate.focus();
		 return false;
		}
	}
	
	function checkClearingNo() 
	{
		var valu = document.getElementById("clg_no").value;
			
		if(valu!= "")
		{
		 if(valu>4 || valu<0)
		 {
			document.forms[0].validateFlag.value="Please Enter Proper Clearing Number ";
		 }  
		}
	}
	

	
	function checkClearingBank(ids) 
	{
	
		var valu = document.getElementById("sent_to").value;
			
		if(valu!="")
		{
			if((valu>4 || valu<0)&& valu!=9999)
			{
				document.forms[0].validateFlag.value="Please Enter Proper Clearing Number ";
			}
			else
			{
				 setflag(1,ids);
			}  
		}
	}
	
 function getTodayDate()
 {
   	var date = new Date();
  	var day = date.getDate();
   	var month = date.getMonth()+1; 
    var year = date.getYear();
        	
	
    if(day <= 9)
    {
     	day = "0"+day;
    }  
   	if(month <= 9)
   	{
		month = "0"+month;        	
   	}
     	return day+"/"+month+"/"+year;
 }
	
	
function checkError()
{

	if(document.getElementById("err_id").value != 0)
	{
		document.forms[0].validateFlag.value=document.getElementById("err_mess_id").value;	
													            				
	}		
	if(document.getElementById("err_id").value > 1)
	{
		document.getElementById("bounce_id").checked='checked';
		callBounce();
						
	} 
			
return false;	
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
	
<body  class="MainBody" bgcolor="beige"> 

<% ChequeDepositionObject[] chq = (ChequeDepositionObject[]) request.getAttribute("Unidentified");
   ModuleObject[] moduleObject=(ModuleObject[])request.getAttribute("Main_Module_code");
   String acctype="";
%>

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
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/ClearingIdentification?pageId=7003">
<center><h2 class="h2"><bean:message key="label.Identify" ></bean:message></h2></center>
				
				<html:hidden property="form_flag" styleId="flagset"></html:hidden>
				<html:hidden property="reason_codes" styleId="reason"></html:hidden>
				<html:hidden property="error_flag" styleId="err_id"></html:hidden>
				<html:hidden property="error_message" styleId="err_mess_id"></html:hidden>
				<html:hidden property="today_date" styleId="today_dt"></html:hidden>
				<html:hidden property="flag" styleId="flag"></html:hidden>
				<html:hidden property="pageId" styleId="pageId"></html:hidden>
								

<table>
	<tr><html:text property="validateFlag" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
	
	<tr>
	<td>
		<table>
	
		<tr>
			<td><bean:message key="label.clr_date"/></td>
			<td><html:text property="clgDate" styleId="clg_Date" styleClass="formTextFieldWithoutTransparent" value="<%=(String)request.getAttribute("date")%>" onblur="return checkformat(this)"></html:text></td>
		</tr>
				
		<tr>
			<td><bean:message key="label.clr_no"/></td>	
			<td><html:select property="clgNum"  styleClass="formTextFieldWithoutTransparent" >
				<%for(int i=1;i<6;i++){ %>
				<html:option value="<%=""+i %>"><%=""+i%></html:option>
				<%} %>
				</html:select>
			</td>
		</tr>
		<tr>
			<td><bean:message key="label.sent_to"/></td>
			<td>
				<html:select property="sendTo" styleClass="formTextFieldWithoutTransparent" onblur="set('frmSendTo')"  styleId="sent_to">
					<html:option value="2">2</html:option>
					<html:option value="3">3</html:option>
					<html:option value="4">4</html:option>
					<html:option value="9999">9999</html:option>
				</html:select>
			</td>
			<td><html:text property="sourceName" readonly="true" styleClass="formTextField"  ></html:text></td>
			<td><bean:message key="label.selectAll"/></td>
			<td><input type="checkbox" name="selectAll" onclick="setChkBox()"></td>
		</tr>
		</table>
		</td>
		</tr>
		
		<tr><td>
		<table>
		<tr>	
		<td>
				<%if(chq!=null){ %>	
					<table>
					<tr>
					<td>SN</td>
					<td>CtrlNum</td>
					<td>BankCode</td>
					<td>ChqDate</td>
					<td>ChqNum</td>
					<td>CredAcTyp</td>
					<td>CredAcNum</td>
					<td>TranAmt</td>
					<td>Src</td>
					<td>ClgTyp</td>
					<td>Select</td>
					</tr>
							<%  for ( int i=0;i<chq.length;i++ ) {acctype=""; %> 
					
					<tr>
					<td><%=""+(i+1) %></td>
					<td><input type = "text" name="txtCtrlNum" readonly="readonly" size = 5 value = "<%=chq[i].getControlNo()%>"></td>
					<td> <input type = "text" name="txtBankCode" readonly="readonly" size="10" value = "<%=chq[i].getBankCode()%>"></td>
					<td> <input type = "text" name="txtChqDate" readonly="readonly" size="10" value = "<%=chq[i].getQdpDate()%>"></td>
					<td> <input type = "text" name="txtChqNum" size = 8 readonly="readonly" value = "<%=chq[i].getQdpNo()%>"> </td>
					
					<%for(int k=0;k<moduleObject.length;k++)
					{	//System.out.println(moduleObject[k].getModuleCode()+"<--Module COde--><--AccountType--->"+chq[i].getCreditACType());
						if((moduleObject[k].getModuleCode().equalsIgnoreCase(chq[i].getCreditACType())))
						{
							acctype=moduleObject[i].getModuleAbbrv();
					 	}
					}	%>
					
					<td> <input type = "text" name="txtCreAccTyp" size = 8 readonly="readonly" value = "<%=chq[i].getCreditACType()%>" ></td> 
					<td><input type="text" name="txtCreAccNum" size="8" readonly="readonly" value="<%=chq[i].getCreditACNo()%>"></td>
					<td> <input type = "text" name="txtTrnAmt" size = 8 readonly="readonly" value = "<%=chq[i].getTranAmt()%>"></td>
					<td> <input type = "text" name="txtDocSrc" size = 3 readonly="readonly" value = "<%=chq[i].getDocSource()%>"></td>
					<td><input type="text" name="txtClgTyp" size=3 readonly="readonly" value="<%=chq[i].getClgType()%>"></td>
					<td><input type='checkbox' name ="chkBox" value="<%=i%>" ></td>
					</tr>
				
				<%}%>
					
				</table>
				
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <html:submit property="submits" onclick="set('frmSubmit')" styleClass="ButtonBackgroundImage" value="Submit" ></html:submit>
             <%}else{ %>
            <html:submit property="submits" onclick="set('frmSubmit')" styleClass="ButtonBackgroundImage" disabled="true" value="Submit" ></html:submit>
             <%} %>
				
					
				</td>
			  <%}%>
	  			</tr>
	  			
		</table>
		</td>
		</tr>	
	</table>
				
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
	 
</body>

</html>