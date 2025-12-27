<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>  
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.NPAObject"%>
<html>
<head>
<script type="text/javascript">
function only_numbers_doublevalue() {
	
	var cha=event.keyCode;
	if((cha>=48 && cha<=57) || cha==46)
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;





function fun_clear()
{
	
	var ele=document.forms[0].elements;
	for(var i=0;i<ele.length;i++)
	{
		
		if(ele[i].type=="text")
		{
			ele[i].value="";
		}
	}
	
}
function fun_check()
{
	
	document.forms[0].txt_fromdaymonth.value="Days";
	document.forms[0].txt_todaymonth.value="Days";
	document.forms[0].txt_fromdaymonth1.value="Days";
	document.forms[0].txt_todaymonth1.value="Month";
	document.forms[0].txt_fromdaymonth2.value="Month";
	document.forms[0].txt_todaymonth2.value="Month";
	document.forms[0].txt_fromdaymonth3.value="Month";
	document.forms[0].txt_todaymonth3.value="Month";
	document.forms[0].txt_fromdaymonth4.value="Month";
	document.forms[0].txt_todaymonth4.value="Month";
	document.forms[0].submit();
	
	if(document.getElementById("valid_values").value!=null)
	{	
		alert(document.getElementById("valid_values").value);
		if(document.getElementById("valid_values").value=="NoValues")
		{
			alert("No values has been set for the given account type ");
			fun_clear();
			return false;
			
		}
	}
}


function fun_vary()
{
	document.forms[0].txt_fromdaymonth.value="Month";
	document.forms[0].txt_todaymonth.value="Month";
	document.forms[0].txt_fromdaymonth1.value="Month";
	document.forms[0].txt_todaymonth1.value="Month";
	document.forms[0].txt_fromdaymonth2.value="Month";
	document.forms[0].txt_todaymonth2.value="Month";
	document.forms[0].txt_fromdaymonth3.value="Month";
	document.forms[0].txt_todaymonth3.value="Month";
	document.forms[0].txt_fromdaymonth4.value="Month";
	document.forms[0].txt_todaymonth4.value="Month";

}

function fun_add()
{
	
	document.forms[0].txt_from1.value=(document.forms[0].txt_to.value);
	document.forms[0].txt_from1.value++;
	
	
	document.forms[0].txt_from2.value=(document.forms[0].txt_to1.value);
	document.forms[0].txt_from2.value++;
	
	
	document.forms[0].txt_from3.value=(document.forms[0].txt_to2.value);
	document.forms[0].txt_from3.value++;
	
	
	document.forms[0].txt_from4.value=(document.forms[0].txt_to3.value);
	document.forms[0].txt_from4.value++;
	
}


function fun_button(target)
{
	alert(target);
	alert(document.forms[0].but_val.value);
	document.forms[0].but_val.value=target;
	alert(document.forms[0].but_val.value);
	
}


function only_Num()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 48 && cha <=57) ) 
 		{
   			return true;
       		} 
		else 
       		{
   			return false;
       		}
	}
	
</script>
</head>
<%System.out.println("This is NPA Admin Page"); %>
<%ModuleObject laon_moduleobj[];%>
<%NPAObject[] NpaObject;%>
<%laon_moduleobj=(ModuleObject[])request.getAttribute("laon_moduleobj"); 
  System.out.println("loan Mod Obj----"+laon_moduleobj);	%>
<%NpaObject=(NPAObject[])request.getAttribute("NpaObject"); %>
<%System.out.println("Npa object in jsp===>"+NpaObject); %>
<html:form action="/Loans/NPAAdmin?pageidentity.pageId=5092">
	<table>
		<tr>
			<td align="right"><bean:message key="label.acctype"/>
				<html:select property="txt_acctype" onchange="return fun_check()" >
				<%if(laon_moduleobj!=null){ 
				for(int i=0;i<laon_moduleobj.length;i++){%>
					<html:option value="<%= ""+laon_moduleobj[i].getModuleCode() %>"><%=""+laon_moduleobj[i].getModuleAbbrv()%></html:option>
				<%}} %>
				</html:select></td>
			
			
			<td><bean:message key="label.select"/>
				<html:select property="txt_tab180">
					<html:option value="1">Table180</html:option>
					<html:option value="2">Table90</html:option>
				</html:select>
			</td>
		</tr>
		
		<tr>
			<th></th>
			<th>From</th>
			<th>FromDay/Month</th>
			<th>To</th>
			<th>To Day/Month</th>
			<th>Prov Amt</th>
		</tr>
		
		<tr>
			
			<%if(NpaObject!=null){ %>
			<td align="right"><html:text property="txt_standard" readonly="true" value="<%=""+NpaObject[0].getAssetDesc() %>" styleClass="formTextField"></html:text></td>
			<td><html:text property="txt_from" size="10" readonly="true" value="<%=""+ NpaObject[0].getDaysFrom() %>"></html:text></td>
			<td><html:select property="txt_fromdaymonth" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_to" size="10"  value="<%=""+ NpaObject[0].getDaysTo()%>" onchange="fun_add()" onkeypress="return only_Num()"></html:text></td>
			<td><html:select property="txt_todaymonth" onchange="return fun_vary()">
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt" size="10" value="<%=""+NpaObject[0].getProvPerc()%>" onkeypress="return only_numbers_doublevalue()"></html:text></td>
			<%} else {%>
			<td align="right"><bean:message key="label.standard"/></td>
			<td><html:text property="txt_from" size="10" readonly="true"></html:text></td>
			<td><html:select property="txt_fromdaymonth" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			
			<td><html:text property="txt_to" size="10" ></html:text></td>
			<td><html:select property="txt_todaymonth">
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt" size="10" onkeypress="return only_numbers_doublevalue()"></html:text></td>
			<%}%>
			
		</tr>
		
		
			
		<tr>
			
			<%if(NpaObject!=null){ %>
			<td align="right"><html:text property="txt_substandard" readonly="true" value="<%=""+NpaObject[1].getAssetDesc() %>" styleClass="formTextField"></html:text></td>
			<td><html:text property="txt_from1" size="10" readonly="true" value="<%=""+ NpaObject[1].getDaysFrom() %>"></html:text></td>
			<td><html:select property="txt_fromdaymonth1" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>	
				</html:select></td>
			<td><html:text property="txt_to1" size="10"  value="<%=""+ NpaObject[1].getMonthsTo()%>" onblur="fun_add()" onkeypress="return only_Num()"></html:text></td>
			<td><html:select property="txt_todaymonth1" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>	
			</html:select></td>
			<td><html:text property="txt_provamt1" size="10" value="<%=""+NpaObject[1].getProvPerc()%>" onkeypress="return only_numbers_doublevalue()"></html:text></td>
			<%} else { %>
			<td align="right"><bean:message key="label.substandard"/></td>
			<td><html:text property="txt_from1" size="10" readonly="true"></html:text></td>
			<td><html:select property="txt_fromdaymonth1" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>	
			</html:select></td>
			<td><html:text property="txt_to1" size="10" ></html:text></td>
			<td><html:select property="txt_todaymonth1" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>	
			</html:select></td>
			<td><html:text property="txt_provamt1" size="10" onkeypress="return only_numbers_doublevalue()"></html:text></td>
			<%} %>
			
		</tr>
		
		<tr>
			
			<%if(NpaObject!=null){ %>
			<td align="right"><html:text property="txt_default1" readonly="true" value="<%=""+NpaObject[2].getAssetDesc() %>" styleClass="formTextField"></html:text></td>
			<td><html:text property="txt_from2" size="10" readonly="true" value="<%=""+ NpaObject[2].getMonthsFrom() %>" ></html:text></td>
			<td><html:select property="txt_fromdaymonth2"  >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>		
			</html:select></td>
			<td><html:text property="txt_to2" size="10"  value="<%=""+ NpaObject[2].getMonthsTo()%>" onblur="fun_add()" onkeypress="return only_Num()"></html:text></td>
			<td><html:select property="txt_todaymonth2" >
					<html:option value="Days">Days</html:option>
					<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt2" size="10" value="<%=""+NpaObject[2].getProvPerc()%>" onkeypress="return only_numbers_doublevalue()"></html:text></td>
			<%}else{ %>
			<td align="right"><bean:message key="label.default1"/></td>
				<td><html:text property="txt_from2" size="10" readonly="true"></html:text></td>
				<td><html:select property="txt_fromdaymonth2"  >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>		
			</html:select></td>
			<td><html:text property="txt_to2" size="10" ></html:text></td>
			<td><html:select property="txt_todaymonth2" >
					<html:option value="Days">Days</html:option>
					<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt2" size="10" onkeypress="return only_numbers_doublevalue()"></html:text></td>
			<%} %>
			
		</tr>
		
		<tr>
			
			<%if(NpaObject!=null){ %>
			<td align="right"><html:text property="txt_default2" readonly="true" value="<%=""+NpaObject[3].getAssetDesc() %>" styleClass="formTextField"></html:text></td>
			<td><html:text property="txt_from3" size="10" readonly="true" value="<%=""+ NpaObject[3].getMonthsFrom() %>"></html:text></td>
			<td><html:select property="txt_fromdaymonth3"  >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_to3" size="10" value="<%=""+ NpaObject[3].getMonthsTo()%>" onblur="fun_add()" onkeypress="return only_Num()"></html:text></td>
			<td><html:select property="txt_todaymonth3" >
					<html:option value="Days">Days</html:option>
					<html:option value="Month">Month</html:option>
				</html:select></td>
			<td><html:text property="txt_provamt3" size="10" value="<%=""+NpaObject[3].getProvPerc()%>" onkeypress="return only_numbers_doublevalue()"></html:text></td>
			<%}else { %>
			<td align="right"><bean:message key="label.default2"/></td>
			<td><html:text property="txt_from3" size="10"  ></html:text></td>
			<td><html:select property="txt_fromdaymonth3"  >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_to3" size="10"></html:text></td>
			<td><html:select property="txt_todaymonth3" >
					<html:option value="Days">Days</html:option>
					<html:option value="Month">Month</html:option>
				</html:select></td>
			<td><html:text property="txt_provamt3" size="10" onkeypress="return only_numbers_doublevalue()"></html:text></td>
			<%} %>
			
		</tr>
		
		<tr>
			
			<%if(NpaObject!=null){ %>
			<td align="right"><html:text property="txt_default3" value="<%=""+NpaObject[4].getAssetDesc() %>" styleClass="formTextField" readonly="true"></html:text></td>
			<td><html:text property="txt_from4" size="10" readonly="true" value="<%=""+ NpaObject[4].getMonthsFrom() %>"></html:text></td>
			<td><html:select property="txt_fromdaymonth4">
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_to4" size="10"  value="<%=""+ NpaObject[4].getMonthsTo()%>" onblur="fun_add()" onkeypress="return only_Num()"></html:text></td>
			<td><html:select property="txt_todaymonth4" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt4" size="10" value="<%=""+NpaObject[4].getProvPerc()%>" onkeypress="return only_numbers_doublevalue()"></html:text></td>
			<%} else {%>
			<td align="right"><bean:message key="label.default3"/></td>
			<td><html:text property="txt_from4" size="10" readonly="true"></html:text></td>
			<td><html:select property="txt_fromdaymonth4"  >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_to4" size="10" ></html:text></td>
			<td><html:select property="txt_todaymonth4" >
				<html:option value="Days">Days</html:option>
				<html:option value="Month">Month</html:option>
			</html:select></td>
			<td><html:text property="txt_provamt4" size="10" onkeypress="return only_numbers_doublevalue()"></html:text></td>
			<%} %>
			
		</tr>
			<html:hidden property="but_val" value="error"/>
			<html:hidden property="valid_values" styleId="valid_values"/>
		<tr>
			<td><html:submit value="Submit" styleClass="ButtonBackgroundImage" onclick="fun_button('submit')"></html:submit>
			<html:button value="Clear" property="" styleClass="ButtonBackgroundImage" onclick="fun_clear()"></html:button></td>
		</tr>
	</table>
</html:form>
</html>
