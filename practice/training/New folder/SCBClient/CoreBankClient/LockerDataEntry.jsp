<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ page import=" masterObject.lockers.LockerDetailsObject" %>

<html>
<head>
<script type="text/javascript">

function getDetails()
{
	var val=document.getElementById("no_rows").value;
	var div_ele=document.getElementById("tabs");
	var trs="";
	 for(var i=0;i<val;i++) 
	 {
		trs+="<tr><td>Enter Boxes in "+i+"Row</td><td><input type='text' name='cab-"+i+"'  id='"+i+"'></td>  <td>Type:</td> <td><input type='text' name='type-"+i+"' id='type'>  </td></tr>";
	 }
		trs+="<tr> <td><input type='button' onclick='setFlag(str)' value='CreateTable' >   </td> </tr>"
		div_ele.innerHTML+= "<table>"+ trs + "</table>";
}

function validate()
{
	if(document.getElementById("col_num").value=='')
	{
		document.forms[0].validateFlag.value="Enter Number Of Columns";
		document.getElementById("col_num").focus();
	}
	else if(document.getElementById("col_num").value==0)
	{
		document.forms[0].validateFlag.value="Column Can't Be-->1";
		document.getElementById("col_num").focus();
	}
	
}



function setFlag(flagValue)
{
	if(document.getElementById("cabDesciption").value=='')
	{
		document.forms[0].validateFlag.value="Description Can't Be Blank";
	}
	else if(document.getElementById("totLockers").value=='')
	{
		document.forms[0].validateFlag.value="Enter Total Number Of Lockers";
	}
	else if(document.getElementById("masterKey").value=='')
	{
		document.forms[0].validateFlag.value="MasterKey Can't Be Blank";
	}
	else if(document.getElementById("tot_Rows").value=='')
	{
		document.forms[0].validateFlag.value="Enter Total Number Of Rows";
		document.getElementById("tot_Rows").focus();
	}
	else
	{
		document.forms[0].flag.value=flagValue;
		document.forms[0].submit();
	}	
}


function validCheck()
{
	if(document.forms[0].rownum.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Row Number";
		document.forms[0].rownum.focus();
		return false;
	}
	else if(document.forms[0].colnum.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Column Number";
		document.forms[0].colnum.focus();
		return false;
	}
	else if(document.forms[0].keynum.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Key Number";
		document.forms[0].keynum.focus();
		return false;
	}
	else if(document.forms[0].lktype.value=="SELECT")
	{
		document.forms[0].validateFlag.value="Select Valid LockerType";
		document.forms[0].lktype.focus();
		return false;
	}
	else
	{
		return true;
	}

}

function addrow()
{
	if(validCheck())
	{
	
		var keynum=document.forms[0].keynum.value;
		var lktype=document.forms[0].lktype.value;
		var rownum=document.forms[0].rownum.value;
		var colnum=document.forms[0].colnum.value;
	
	var tbl = document.getElementById('tbody');
	var lastRow = tbl.rows.length;
	// if there's no header row in the table, then iteration = lastRow + 1
	var iteration = lastRow;
	var itr=iteration-1; 
	var row = tbl.insertRow(lastRow);

	var cellRight = row.insertCell(0);
	var el = document.createElement('input');
	el.setAttribute('type', 'text');
	el.setAttribute('name', 'rownum');
	el.setAttribute('size', '5');
	el.setAttribute('value',rownum);
	cellRight.appendChild(el);

	var cellRight = row.insertCell(1);
	var e2 = document.createElement('input');
	e2.setAttribute('type', 'text');
	e2.setAttribute('name', 'colnum');
	e2.setAttribute('size', '5');
	e2.setAttribute('value',colnum);
	cellRight.appendChild(e2);

	var cellRight = row.insertCell(2);
	var e3 = document.createElement('input');
	e3.setAttribute('type', 'text');
	e3.setAttribute('name', 'keynum');
	e3.setAttribute('size', '5');
	e3.setAttribute('value',keynum);
	cellRight.appendChild(e3);

	var cellRight = row.insertCell(3);
	var e4 = document.createElement('input');
	e4.setAttribute('type', 'text');
	e4.setAttribute('name', 'lktype');
	e4.setAttribute('size', '5');
	e4.setAttribute('value',lktype);
	cellRight.appendChild(e4);
	

	var cellRight = row.insertCell(4);
	var e5 = document.createElement('input');
	e5.setAttribute('type', 'checkbox');
	e5.setAttribute('name', 'cbox');
	e5.setAttribute('value', ' '+ itr);
	cellRight.appendChild(e5);
	
	}
}

function onlynumbers()
{
     var cha = event.keyCode;
	if(cha >= 48 && cha <= 57) 
    {
         return true;
    } 
    else 
    {
        return false;
    }
}




</script>
 <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />

</head>

<body class="Mainbody">
<%@page import="java.util.Map"%>


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
<html:form action="/LockerDataEntryLink?pageId=9010">


<div id="tabs">

</div>

<%!  
	String rows;
	int r;
%>


<center><h2 class="h2">LockerDataEntry</h2></center>

<table>
	<tr><html:text property="validateFlag" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
	<tr>	
	<td>
		
		<table border="2" align="left" class="txtTable">
		<tr style="color:black;font:bolder;"><td>Enter Cabinet Details</td></tr>
		<tr>
		<td><bean:message  key="lab.cabDescription"></bean:message> </td>
		<td><html:text property="cabDesciption"   styleId="cabDesciption"></html:text> </td></tr>
		
		<tr><td><bean:message key="lab.numLockers"></bean:message> </td>
		<td><html:text property="totLockers" onkeypress="return onlynumbers()" styleId="totLockers"></html:text> </td></tr>
		
		<tr><td><bean:message key="lab.masterKey"></bean:message> </td>
		<td><html:text property="masterKey" onkeypress="return onlynumbers()" styleId="masterKey"></html:text> </td></tr>
		
		<tr><td><bean:message key="lab.totRows"></bean:message></td>
		<td><html:text property="noRows" onkeypress="return onlynumbers()" styleId="tot_Rows" onblur="setFlag('totrows')"></html:text></td></tr>
		
		</table>
	
	</td>
		
			
	<td>
		
		<table border="3" class="txtTable">
			
			<%! LockerDetailsObject[] lockerDetailsObjec; %>
			<% lockerDetailsObjec=(LockerDetailsObject[])request.getAttribute("lkdetails");%>
			<%if(lockerDetailsObjec!=null){ %>
			<tr style="color:black;font:bolder;"><td>Locker Details</td></tr>
			
			<%for(int k=0;k<lockerDetailsObjec.length;k++){ %>
			<tr>
				<td><%=lockerDetailsObjec[k].getLockerType()%></td>
				<td><%=lockerDetailsObjec[k].getDescription()%></td>
				<td><%=lockerDetailsObjec[k].getLockerLength()%></td>
				<td><%=lockerDetailsObjec[k].getLockerHeight()%></td>
				<td><%=lockerDetailsObjec[k].getLockerDepth()%></td>
			</tr>
			<%} %>
		
			<%} %>	
		</table>
		<table border="1" id="tbody">
			<tbody>
				<tr>
					<td>RowNum</td><td>ColNum</td><td>KeyNum</td><td>LockerType</td><td>Select</td>				
				</tr>
			</tbody>
		</table>
	</td>
	
</tr>
<tr>
	<td>
	<%! String num; %>
	<% num=(String)request.getAttribute("norows");%>
	<%if(num!= null){
		int n=Integer.parseInt(num);%>
		<table border="1">
				<tr style="color:black;font:bolder;"><td>Enter Number Of Lockers</td></tr>
				<tr><td>NoRows</td><td>NoLockers</td></tr>
				<%for(int k=0;k<n;k++){ %>
				<tr>
					<td><html:text property="ronum" size="5" value="<%=""+(k+1)%>" readonly="true"></html:text></td>
					<td><html:text property="columnnum" size="5" styleId="col_num" onkeypress="return onlynumbers()" onblur="validate()"></html:text></td>
				</tr>
				<%} %>
		</table>
		
		<table border="1" id="tblSample">
			<tr style="color:black;font:bolder;"><td>Enter Locker Details</td></tr>
			<tr><td>RowNumber</td><td>ColumnNumber</td><td>KeyNumber</td><td>LockerType</td></tr>
			<tr>
				 <td><input type="text" name="rownum" size="5"  onkeypress="return onlynumbers()"></td>
				 <td><input type="text" name="colnum" size="5" onkeypress="return onlynumbers()"></td>
				 <td><input type="text" name="keynum" size="5" onkeypress="return onlynumbers()"></td>
				 <td><select name="lktype">
					<option value="SELECT">Select</option>
					<core:forEach var="lktypes" items="${requestScope.lktypes}">              
         				<option value="${lktypes}"><core:out value="${lktypes}"></core:out></option>
					</core:forEach>
					</select>
				  </td>
			</tr>
			 <tr>
			 
				  
				  <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
				  		 <td><html:button property="sub" value="AddRow" styleClass="ButtonBackGroundImage" onclick="addrow()"></html:button></td> 
				  		 <td><html:button property="subb" value="Submit" styleClass="ButtonBackGroundImage" onclick="setFlag('frmSubmit')"></html:button></td>
		          <%}else{ %>
             			<td><html:button property="sub" value="AddRow" disabled="true" styleClass="ButtonBackGroundImage"  onclick="addrow()"></html:button></td> 
				  		<td><html:button property="subb" value="Submit" disabled="true" styleClass="ButtonBackGroundImage" onclick="setFlag('frmSubmit')"></html:button></td>
             		<%} %>
				 
			 </tr>
		</table>
	<%} %>	
	</td>
</tr>
</table>
<html:hidden property="pageId"></html:hidden>
<html:hidden property="flag"></html:hidden>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>

</html>