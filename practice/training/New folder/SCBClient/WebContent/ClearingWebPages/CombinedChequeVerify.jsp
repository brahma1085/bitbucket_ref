<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ page import="masterObject.clearing.CompanyMaster" %>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CombinedChequeEntry</title>
<script type="text/javascript">
  
 
 
 function onlynumbers()
 {
        	var cha =   event.keyCode;
           if(cha>=48 && cha<=57) 
            {
            		return true;
        	} 
        	else 
        	{
        			return false;
        	}
			        
  }
        
  
 function set(flagVal)
 {
 	if(document.forms[0].ctrlno.value!=0)
 	{
 		document.forms[0].formflag.value=flagVal;
 		document.forms[0].submit();
 	}
 	else
 	{
 		document.forms[0].validateFlag.value="Enter Valid Control Number";
 	}
 } 
  
  
function setFlag(flagVal)
{
		if(document.forms[0].ctrlno.value!=0)
		{
 			if(flagVal=='frmSubmit')
 			{
 				if(document.forms[0].totamount.value!=0)
 					{	
 						document.forms[0].flag.value=flagVal;
 						document.forms[0].booleanFlag.value=1;
 						document.forms[0].submit();
 					}
 					else
 					{
 						document.forms[0].validateFlag.value="Enter Total Amount";
 					}	
 			}
 			else if(flagVal=='frmUpdate')
 			{
 				if(document.forms[0].totamount.value!=0)
 					{
 						document.forms[0].flag.value='frmSubmit';
 						document.forms[0].booleanFlag.value=true;
 						document.forms[0].submit();
 					}
 				else
 					{
 						document.forms[0].validateFlag.value="Enter Total Amount";
 					}
 			}
 			else 
 			{
 				document.forms[0].flag.value=flagVal;
 				document.forms[0].submit();
 			}	
 		}
 		else
 		{ 
 		 document.forms[0].validateFlag.value="Enter Valid Control Number";
 		}	
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

function clearAll (){
	
		var form_ele = document.forms[0].elements;
		
		var val = document.getElementById("ctrl_no").value;
		
		for ( var i = 0 ; i< form_ele.length; i++ )
		{
			if(form_ele[i].type=='text')
				form_ele[i].value = "";
		
		}
		
		
		return false;
	}
 
 
 
 
 function addRowToTable()
{
	var tbl = document.getElementById('tblSample');
	var lastRow = tbl.rows.length;
	// if there's no header row in the table, then iteration = lastRow + 1
	var iteration = lastRow;
	var itr=iteration-1; 
	var row = tbl.insertRow(lastRow);

	// left cell
	var cellLeft = row.insertCell(0);
	var textNode = document.createTextNode(iteration);
	cellLeft.appendChild(textNode);

	// right cell
	var cellRight = row.insertCell(1);
	var el = document.createElement('input');
	 el.setAttribute('type', 'text');
	el.setAttribute('name', 'accTyp');
	 el.setAttribute('size', '20');
	cellRight.appendChild(el);

	var cellRight = row.insertCell(2);
	var e2 = document.createElement('input');
	e2.setAttribute('type', 'text');
	e2.setAttribute('name', 'accNum');
	e2.setAttribute('size', '20');
	cellRight.appendChild(e2);


	var cellRight = row.insertCell(3);
	var e3 = document.createElement('input');
	e3.setAttribute('type', 'text');
	e3.setAttribute('name', 'companyName');
	e3.setAttribute('size', '20');
	cellRight.appendChild(e3);

	var cellRight = row.insertCell(4);
	var e4 = document.createElement('input');
	e4.setAttribute('type', 'text');
	e4.setAttribute('name', 'amount');
	e4.setAttribute('size', '20');
	cellRight.appendChild(e4);

	var cellRight = row.insertCell(5);
	var e5 = document.createElement('input');
	e5.setAttribute('type', 'checkbox');
	e5.setAttribute('name', 'chkBox');
	e5.setAttribute('value', ' '+ itr);
	cellRight.appendChild(e5);

}

function removeRowFromTable()
{
	var tbl = document.getElementById('tblSample');
	var lastRow = tbl.rows.length;
	if (lastRow > 2) tbl.deleteRow(lastRow - 1);
}

function onlyDoublenumbers()
{
        	
        	var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || cha == 46 ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
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
 
 <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
</head>
<body class="MainBody">
<%! CompanyMaster[] companyMasters; %>

<center><h2 class="h2"><bean:message key="label.CombinedVerify"></bean:message></h2></center>



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
<html:form action="/Clearing/CombinedChequeLink?pageId=7051">

<table>
	
	<html:hidden property="formflag"/>
	<html:hidden property="pageId"/>
	<html:hidden property="flag"/>
	<html:hidden property="booleanFlag"/>
	
	<tr><html:text property="validateFlag" styleClass="formTextField" style="color:red;font-family:bold;" size="100"/></tr>	
	<tr>
	<td>
	<table>
	<tr>
	<td><bean:message key="label.controlno"/></td>
	<td><html:text property="ctrlno" styleId="ctrl_no" onkeypress="return onlynumbers()" onblur="setFlag('frmCtrlNum')" size="5" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	<td><bean:message key="label.totalAmount"/></td>
	<td><html:text property="totamount" readonly="true" onkeypress="return onlyDoublenumbers()" styleClass="formTextFieldWithoutTransparent" size="5" ></html:text></td>
	<td><bean:message key="label.selectAll"></bean:message></td>
	<td><input type="checkbox" name="selectAll"  onclick="setChkBox()"></td>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <td><html:button property="submitme" value="Verify" styleClass="ButtonBackGroundImage" onclick="setFlag('frmSubmit')"></html:button></td>
             <%}else{ %>
             <td><html:button property="submitme" value="Verify" styleClass="ButtonBackGroundImage" disabled="true" onclick="setFlag('frmSubmit')"></html:button></td>
             <%} %>
	
	<td><html:button property="clear" value="Clear" styleClass="ButtonBackGroundImage" onclick="clearAll()"></html:button></td>
	
	</tr>
	</table>
	<tr>
	<% companyMasters=(CompanyMaster[])request.getAttribute("chqdetails"); %>
	<%if(companyMasters!=null){ %>
	<td>
	<table id="tblSample" border="1">
	
		<tr>
		<td>SN</td><td>AccTyp</td><td>AccNum</td><td>Name</td><td>Amount</td><td>LoanA/cTyp</td><td>LoanA/cNum</td><td>Select</td>
		</tr>
		<% for(int i=0;i<companyMasters.length;i++){%>
		 <tr>
			  	<td><%=""+(i+1)%></td>
			    
			    <td><input type="text" name="accTyp" value="<%=companyMasters[i].getAccType()%>"></td>
			    <td><input type="text" name="accNum" size="7" value="<%=companyMasters[i].getAccNo()%>"/></td>
			    <td><input type="text" name="companyName" value="<%=companyMasters[i].getCompanyName()%>"/></td>
			    <td><input type="text" name="amount" size="9" value="<%=companyMasters[i].getDeTml()%>"/></td>
			    <td><input type="text" name="loanActyp" value="<%=companyMasters[i].getLoanAccType()%>"/></td>
			    <td><input type="text" name="loanAcnum" size="7" value="<%=companyMasters[i].getLoanAccNo()%>"/></td>
			    <td><input type="checkbox" name="chkBox" value="<%=" "+i%>"/></td>
		</tr>
		<%}%>
		</table>
		</td>
		<%} %>
	</tr>
	</table>
	

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>