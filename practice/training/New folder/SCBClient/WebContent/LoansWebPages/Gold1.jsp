<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.GoldObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
</style>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" /><h2 class="h2"><center>Gold Details</center></h2>
  
</head>
<body>
<script type="text/javascript">
 function numbersOnly()
{
	 var cha = event.keyCode
   		if (  ( cha  > 47 && cha < 58  ) ) 
   		{
			return true ;
   		}
   		else
   		{
      		//alert("Please enter the numbers only ");
       		return false ;
   		}
   		};
   		
   		function only_numbers_doublevalue() {
	
	var cha=event.keyCode;
	if((cha>=48 && cha<=57)||cha==46 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;

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

<html:form action="/Loans/LoanApplicationDE?pageId=5001">
<%Object[][] goldObject = (Object[][])request.getAttribute("GoldDet"); %>

<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
	
	<tr>
 <td><center><h1 style="font:small-caps;font-style:normal;font-size:small;">
 <b>Gold Details Form</b></h1></center>
 </td>
 </tr>
 <%if(goldObject!=null){ %>
	<%for(int i=0;i<goldObject.length;i++){ %>
	<tr>
		<td><font style="font-style:normal;font-size:12px"> Sl. No : </font>
			<html:text property="slno" size="10" value="<%=""+goldObject[i][0]%>" readonly="true" ></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Description: </font>
			<html:text property="desc" value="<%=""+goldObject[i][1]%>" size="10" readonly="true"></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Gr. Weight: </font>
			<html:text property="grWeight" value="<%=""+goldObject[i][2] %>" size="10" readonly="true"></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Net. Weight: </font> 
			<html:text property="netWeight" value="<%=""+goldObject[i][3] %>" size="10" readonly="true"></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Rate / Gms: </font>
			<html:text size="10" property="rate" value="<%=""+goldObject[i][4] %>" readonly="true"></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Net Rate: </font> 
			<html:text size="10" property="netRate" value="<%=""+goldObject[i][5] %>" readonly="true"></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Appriser's Code: </font> 
			<html:text size="10" property="appCode" value="<%=""+goldObject[i][6] %>" readonly="true"></html:text>
		</td>
	</tr>
	<%} %>
	<%}else{ %>
	<tr>
		<td><font style="font-style:normal;font-size:12px"> Sl. No : </font>
			<html:text property="slno" size="10" onkeypress="return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Description: </font>
			<html:text property="desc" size="10"></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Gr. Weight: </font>
			<html:text property="grWeight" size="10" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Net. Weight: </font> 
			<html:text size="10" property="netWeight" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Rate / Gms: </font>
			<html:text property="rate" size="10" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Net Rate: </font> 
			<html:text size="10" property="netRate" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
		</td>
	</tr>
	<tr>
		<td><font style="font-style:normal;font-size:12px">	Appriser's Code: </font> 
			<html:text size="10" property="appCode" onkeypress="return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>
		</td>
	</tr>
	<%} %>
	<tr>
       <td>
       <%if(goldObject!=null){ %>
       <html:submit styleClass="ButtonBackgroundImage" disabled="true">Submit</html:submit>
		<%}else{ %>       
       <input type="submit" value="SaveGoldDet" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=SaveGoldDet'" />
       </td><%} %><td>
       <html:button property="dummyclear" styleClass="ButtonBackgroundImage" onclick="func_clear()">Clear</html:button></td>
       <td><html:button property="dummyclear" styleClass="ButtonBackgroundImage">Update</html:button></td>
  </tr> 
</table>
</html:form>
</body>
</html>