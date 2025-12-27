<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.PropertyObject"%>

<html>
<head>
<script type="text/javascript">
function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) ) {
				return true ;
                
            	}
            else{
              //	alert("Please enter the numbers only!!");
              	return false ;
            }
        };
        function only_numbers_doublevalue() {
	
	var cha=event.keyCode;
	if((cha>=48 && cha<=57 )|| cha==46)
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;

function number_alpha_prop()
	{
		var cha =   event.keyCode;

 		if ((cha >= 65 && cha <=90) || (cha >= 97 && cha <=122)||(cha >=46 && cha <=57)||cha==32 ||(cha==40 || cha==41)) 
 		{
   			return true;
       	} else 
       	{
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
function only_for_address()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 65 && cha <=90) || (cha >= 97 && cha <=122)||(cha >= 44 && cha <=58)||cha==32||cha==35) 
 		{
   			return true;
       	} else 
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
        
        
		
	
	 



</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
<title>Insert title here</title>
</head>
<body>

<html:form action="/Loans/LoanApplicationDE?pageId=5001">
<%PropertyObject object = (PropertyObject)request.getAttribute("PROPOBJ"); %>
<%String disable; %>
<%disable=(String)request.getAttribute("disable"); %>
<%boolean flag=true; %>
<%if(disable!=null){ 
flag=true;
}else{
flag=false;
}
%>
<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
<tr>
  <td>
    <h2 class="h2"><center>Property Details</center></h2>
  </td>
</tr>
<tr>
	<%if(object!=null){ %>
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.propertyno"></bean:message></font></td>
    <td><html:text property="propertyno" value="<%=""+object.getPropertyNo()%>" readonly="true"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.address"></bean:message></font></td>
    <td><html:text property="addr" value="<%=""+object.getAddress() %>" readonly="true"></html:text></td>
    
    <%}else{ %>
     <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.propertyno"></bean:message></font></td>
    <td><html:text property="propertyno" onkeypress="return number_alpha_prop()"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.address"></bean:message></font></td>
    <td><html:text property="addr" onkeypress="return only_for_address()"></html:text></td>
    
    <%} %>
    
</tr>
   
<tr>      
 <td align="left"><b><font style="font-style:normal;font-size:12px">
    <bean:message key="label.measurements"></bean:message></b></font>
 </td>   
</tr>

<tr>   

	<%if(object!=null){ %>
	<td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.EWbyfeets" ></bean:message></font></td>
    <td><html:text property="ewbyfeets" value="<%=""+object.getMeasurementEW()%>" readonly="true"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.NSbyfeets"></bean:message></font></td>
    <td><html:text property="nsbyfeets" value="<%=""+object.getMeasurementNS()%>" readonly="true"></html:text></td>
 	<%}else{ %>
 	<td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.EWbyfeets"></bean:message></font></td>
    <td><html:text property="ewbyfeets" onkeypress="return only_numbers_doublevalue()"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.NSbyfeets"></bean:message></font></td>
    <td><html:text property="nsbyfeets" onkeypress="return only_numbers_doublevalue()"></html:text></td>
 	<%} %>
    
</tr>

<tr>    
	<%if(object!=null){ %>
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.eastby"></bean:message></font></td>
    <td><html:text property="eastby" value="<%=""+object.getEastBy()%>" readonly="true"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.westby"></bean:message></font></td>
    <td><html:text property="westby" value="<%=""+object.getWestBy()%>" readonly="true"></html:text></td>
    <%}else{ %>
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.eastby"></bean:message></font></td>
    <td><html:text property="eastby" onkeypress="return only_numbers_doublevalue()"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.westby"></bean:message></font></td>
    <td><html:text property="westby" onkeypress="return only_numbers_doublevalue()"></html:text></td>
    <%} %>
</tr>

<tr> 
  <%if(object!=null){ %>   
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.northby"></bean:message></font></td>
    <td><html:text property="northby" value="<%=""+object.getNorthBy()%>" readonly="true"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.southby"></bean:message></font></td>
    <td><html:text property="southby" value="<%=""+object.getSouthBy()%>" readonly="true"></html:text></td>
    <%}else{ %>
     <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.northby"></bean:message></font></td>
    <td><html:text property="northby" onkeypress="return only_numbers_doublevalue()"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.southby"></bean:message></font></td>
    <td><html:text property="southby" onkeypress="return only_numbers_doublevalue()"></html:text></td>
    <%} %>
</tr>

<tr> 
	<%if(object!=null){ %>   
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.marketvalue"></bean:message></font></td>
    <td><html:text property="value" value="<%=""+object.getPropertyValue()%>" readonly="true"></html:text></td>
    <%}else{ %>
     <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.marketvalue"></bean:message></font></td>
    <td><html:text property="value" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text></td>
    <%} %>
</tr>

<tr> 
	<%if(object!=null){ %>   
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.propertyacquired"></bean:message></font></td>
    <td><html:select property="propertyacquiredby">
        <html:option value="<%=""+object.getPropertyAqdBy()%>" disabled="true"></html:option>
     </html:select></td>
     <%}else{ %>
      <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.propertyacquired"></bean:message></font></td>
    	<td><html:select property="propertyacquiredby">
        <html:option value="Ancestoral Property">Ancestoral Property</html:option>
        <html:option value="Partitioned Property">Partitioned Property</html:option>
        <html:option value="Self Acquired">Self Acquired</html:option>
        <html:option value="Joint Family">Joint Family</html:option>
     </html:select></td>
     <%} %>
</tr>

<tr>   
<%if(object!=null){ %> 
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.nature"></bean:message></font></td>
    <td><html:select property="nature">
        <html:option value="<%=""+object.getPropertyNature()%>" disabled="true"></html:option>
        </html:select></td>
  <%}else{ %>
  		 <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.nature"></bean:message></font></td>
    <td><html:select property="nature">
        <html:option value="Site">Site</html:option>
        <html:option value="Site with Building">Site with Building</html:option>
        <html:option value="Residential">Residential</html:option>
        <html:option value="Commercial">Commercial</html:option>
        <html:option value="Cornersite">Cornersite</html:option>
        <html:option value="Partly residential & Partly commercial">Partly residential & Partly commercial</html:option></html:select></td>
   <%} %>     
</tr>

<tr>
<%if(disable!=null) {%>  
<td><input type="submit" value="SaveProperty" name="method" class="ButtonBackgroundImage"  disabled="<%=flag %>" /></td>
<%}else{ %>
<td><input type="submit" value="SaveProperty" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=SaveProperty'" /></td>
<%} %>
<td><html:submit styleClass="ButtonBackgroundImage" onclick="func_clear()">Clear</html:submit></td>
    <td><html:submit styleClass="ButtonBackgroundImage">Update</html:submit></td>  
</tr>
</table>
</html:form>
</body>
</html>