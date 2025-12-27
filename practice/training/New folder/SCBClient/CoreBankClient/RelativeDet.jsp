<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@page import="masterObject.loans.LoanMasterObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
<title></title>

</head>
<body>
<script type="text/javascript">
	
         
         function numbersonly_date(){
         var cha = event.keyCode

            if (  ( cha  >= 47 && cha < 58  ) ) {
              
                return true ;
                            }
            else{
             // alert("Please enter the numbers only!!");
              return false ;
            }
         
        
      }; 
      	function datevalidation(ids){
    	var format = true;
        var allow=true;
    	var ff=ids.value;
    	var dd=ff.split('/');
    	
    	var ss=document.forms[0].sysdate.value;
    	var dds=ss.split('/');
    	
    	if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          alert (  " Date format should be:DD/MM/YYYY" );
                         ids.value="";
                          format = false ;
                          allow=false;
                          

                      } else if (  i==2 && dd[i].length != 4 ){

                           alert (  " Date format should be:DD/MM/YYYY " );
                          ids.value="";
                           format = false ;
                           allow=false;
                      }

            	 }
             } else{
                    allow=false;
             		format = false ;
             		alert (  " Date format should be:DD/MM/YYYY " );
             		document.forms[0].fdate.value="";
             }
            if(allow){
            	
            	var day=dd[0];
            	var month=dd[1];
            	var year=dd[2];
            	var fdays;
            	if(month==2)
            	{
            	if((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0)))
            	{
            		if(day>29)
            		{
            			alert("Days should be less than 29 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	else
            	{
            		if(day>28)
            		{
            			alert("Days should be less than 28 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	}
            	
            	if(month>1 || month<12){
            	if(month == 4 || month==6||month==9||month==11)
            	{
            		if(day>30)
            		{
            			alert("Days should be less than 31 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	else
            	{
            		if(day>31)
            		{
            			alert("Days should be less than 32 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            		
            	}
            	}
            	if(month>12)
            	{
            		alert("Month should  be greater than 0 and lesser than 13");
            		ids.value="";
                           format = false ;
                           allow=false;
            	}
            	
            	if(year<1900 || year>9999)
            	{
            		alert("Year should be in between 1900 to 9999 ");
            		ids.value="";
                           format = false ;
                           allow=false;
            	}
            	if(dd[0].length==2||dd[1].length==2||dd[2].length==4)
            	{
            		if(dd[2]>dds[2])
            		{
            			alert(" Year should be less than or equal to"+dds[2]+" !");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}	
            		else{
            			if(dd[2]==dds[2]){
            				if(dd[1]>dds[1]){
	            				alert(" Month should be less than or equal to"+dds[1]+" !");
	            				ids.value="";
	                           	format = false ;
	                          	 allow=false;		
            						
            				}else{
            					if(dd[1]==dds[1]){
            						if(dd[0]>dds[0]){
											alert(" Day should be less than or equal to"+dds[0]+" !");
				            				ids.value="";
				                           	format = false ;
				                          	allow=false;           							
            						}else{
            								format = true ;
				                          	allow=true; 
            						}
            					
            					}
            				}
            			}
            		}
            	}            	          	
             }
        };
      function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) || (cha >= 65 && cha <=90) ||cha==32 || cha==46) 
 		{
   			return true;
       	} else 

       	{
   			return false;
       	}
	};

	
</script>



<%

	Object[][] relation=new Object[1][6];
	Object[][] indent=new Object[1][5];
	Object[][] dependents=new Object[1][4];
	
	
	relation=(Object[][])request.getAttribute("RelObj");
	indent=(Object[][])request.getAttribute("RelIndObj");
	dependents=(Object[][])request.getAttribute("RelDepObj");
	
	if(relation!=null && indent!=null && dependents!=null)
	{
		System.out.println("Size relation-->"+relation.length);
		System.out.println("Size indent-->"+indent.length);
		System.out.println("Size dependents-->"+dependents.length);
	}
	
	
 %>
 <%String disable; %>
<%disable=(String)request.getAttribute("disable"); %>
<%boolean flag=true; %>
<%if(disable!=null){ 
flag=true;
}else{
flag=false;
}
%>

<html:form action="/Loans/LoanApplicationDE?pageId=5001">
<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
<tr>
  <td>
    <h2 class="h2"><center>Relative Details</center></h2>
  </td>
</tr>
<tr>
<tr>
	<td align="left">
		<font color="blue">Relation Details</font>
	</td>
</tr>
<tr>
	<% 
	if(relation!=null){
		if(relation[0][0]!=null){
	%>
	<td>
		Name<html:text property="relName" styleId="relName" value="<%=""+relation[0][0]%>" readonly="true"></html:text> 
	</td>
	<% 
	}}else{ %>
	<td>
		Name<html:text property="relName" styleId="relName" onkeypress="return only_alpha()"></html:text> 
	</td>
	<%} %>
	
	<% 
	if(relation!=null){
		if(relation[0][1]!=null){
	%>
	<td align="left">
		DOB &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="relDob" styleId="relDob" value="<%=""+relation[0][1]%>" readonly="true"  ></html:text>
	</td>
	<% 
	}}else{ %>
	<td>
		DOB &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="relDob" styleId="relDob" onblur="return datevalidation(this)" onkeypress=" return numbersonly_date()" ></html:text>
	</td>
	<%} %>
</tr>
<tr>

	<%
	if(relation!=null){
		if(relation[0][2]!=null){
	%>

	<td>
		Type &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:select property="relTor" styleId="relTor" disabled="true">
		<html:option value="<%=""+relation[0][2]%>"></html:option>
		</html:select>
		
	</td>
	
	<% 
	}}else { %>
		<td>
		Type &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:select property="relTor" styleId="relTor">
		<html:option value="Father">Father</html:option>
		<html:option value="Mother">Mother</html:option>
		<html:option value="Self">Self</html:option>
		<html:option value="Son">Son</html:option>
		<html:option value="Daughter">Daughter</html:option>
		</html:select>
		
		</td>	
	
	<%} %>
	
	<%
	if(relation!=null){
		if(relation[0][3]!=null){
	%>
		<td>
		Sex &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:select property="relTos" styleId="relTos" disabled="true">
		<html:option value="<%=""+relation[0][3]%>"></html:option>
		</html:select>
		</td>
	
		<% 
	}}else { %>
		<td>
			Sex &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:select property="relTos" styleId="relTos">
			<html:option value="M">Male</html:option>
			<html:option value="F">Female</html:option>
			</html:select>
		</td>
	<%} %>
</tr>
<tr>

	<%
	if(relation!=null){
		if(relation[0][4]!=null){
	%>
	<td>Martial &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="relTom" styleId="relTom" disabled="true">
	<html:option value="<%=""+relation[0][4]%>"></html:option>
	</html:select>
	</td>
	<% 
	}}else { %>
	<td>Martial &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="relTom" styleId="relTom">
	<html:option value="U">UnMarried</html:option>
	<html:option value="M">Married</html:option>
	</html:select>
	</td>
	<%} %>
	
	<%
	if(relation!=null){
		if(relation[0][5]!=null){
	%>
	
	<td>Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="relTostatus" styleId="relTos" disabled="true">
	<html:option value="<%=""+relation[0][5]%>"></html:option>
	</html:select>
	</td>
	<% 
	}}else { %>
	<td>Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="relTostatus" styleId="relTos">
	<html:option value="L">Late</html:option>
	<html:option value="A">Alive</html:option>
	</html:select>
	</td>
	<%} %>
</tr>
<tr>
	<td align="left"><font color="blue">
		Indent Details</font>
	</td>
</tr>
<tr>
	
	<% 
	if(indent!=null){
		if(indent[0][0]!=null){
	%>
	<td>
		Name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="indName" styleId="indName" value="<%=""+indent[0][0]%>" readonly="true"></html:text>
	</td>
	<%}}else{ %>
	<td>
		Name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="indName" styleId="indName" onkeypress="return only_alpha()"></html:text>
	</td>
	<%} %>
	<% 
	if(indent!=null){
		if(indent[0][1]!=null){
	%>
	<td>
		DOB &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="infDob" styleId="infDob" value="<%=""+indent[0][1]%>" readonly="true"></html:text>
	</td>
	<%}}else{ %>
	<td>
		DOB &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="infDob" styleId="infDob" onblur="return datevalidation(this)" onkeypress=" return numbersonly_date()"></html:text>
	</td>
	<%} %>
</tr>
<tr>
	
	<%
	if(indent!=null){
		if(indent[0][2]!=null){
	%>
	<td>Sex &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="indTos" styleId="indTos" disabled="true">
	<html:option value="<%=""+indent[0][2]%>"></html:option>
	</html:select>
	</td>
	<%}}else{ %>
	<td>Sex &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="indTos" styleId="indTos">
	<html:option value="M">Male</html:option>
	<html:option value="F">Female</html:option>
	</html:select>
	</td>
	<%} %>
	
	
	<% 
	if(indent!=null){
		if(indent[0][3]!=null){
	%>
	
	<td>Martial 
	<html:select property="indTom" styleId="indTom" disabled="true">
	<html:option value="<%=""+indent[0][3]%>"></html:option>
	</html:select>
	</td>
	<%}}else{ %>
	<td>Martial 
	<html:select property="indTom" styleId="indTom">
	<html:option value="U">UnMarried</html:option>
	<html:option value="M">Married</html:option>
	</html:select>
	</td>
	<%} %>
	
</tr>
<tr>
	<%
	if(indent!=null){
		if(indent[0][4]!=null){
	%>
	<td>Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="indTostatus" styleId="indTostatus" disabled="true">
	<html:option value="<%=""+indent[0][4]%>"></html:option>
	</html:select>
	</td>
	<%}}else{ %>
	<td>Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="indTostatus" styleId="indTostatus">
	<html:option value="L">Late</html:option>
	<html:option value="A">Alive</html:option>
	</html:select>
	</td>
	<%} %>
</tr>
<tr>
	<td align="left">
		<font color="blue">Dependent Details</font>
	</td>
</tr>
<tr>
	<% 
	if(dependents!=null){
		if(dependents[0][0]!=null){
	%>
	<td>
		Name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="depName" styleId="depName" value="<%=""+dependents[0][0]%>" 
readonly="true"></html:text>
	</td>
	<%}}else{ %>
	<td>
		Name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="depName" styleId="depName" 
onkeypress="return only_alpha()"></html:text>
	</td>
	<%} %>
	<% 
	if(dependents!=null){
		if(dependents[0][1]!=null){
	%>
	<td>
		DOB &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="depDob" styleId="depDob" value="<%=""+dependents[0][1]%>" readonly="true"></html:text>
	</td>
	<%}}else{ %>
	<td>
		DOB &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="depDob" styleId="depDob" onkeypress=" return numbersonly_date()" onblur="return datevalidation(this)" ></html:text>
	</td>
	<%} %>
</tr>
<tr>
	<% 
	if(dependents!=null){
		if(dependents[0][2]!=null){
	%>
	<td>Sex &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="depTos" styleId="depTos" disabled="true">
	<html:option value="<%=""+dependents[0][2]%>"></html:option>
	</html:select>
	</td>
	<%}}else{ %>
	<td>Sex &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="depTos" styleId="depTos">
	<html:option value="M">Male</html:option>
	<html:option value="F">Female</html:option>
	</html:select>
	</td>
	<%} %>
	<% 
	if(dependents!=null){
		if(dependents[0][3]!=null){
	%>
	<td>
		Relation With &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:select property="depTor" styleId="depTor" disabled="true">
		<html:option value="<%=""+dependents[0][3]%>"></html:option>
		</html:select>
	</td>
	<%}}else{ %>
	<td>
		Relation With &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:select property="depTor" styleId="depTor">
		<html:option value="Father">Father</html:option>
		<html:option value="Mother">Mother</html:option>
		<html:option value="Self">Self</html:option>
		<html:option value="Son">Son</html:option>
		<html:option value="Daughter">Daughter</html:option>
		</html:select>
	</td>
	<%} %>
	</tr>
	<html:hidden property="sysdate" />
	<tr>
	<%if(disable!=null) {%>  
		<td><input type="submit" value="SaveRelative" name="method" class="ButtonBackgroundImage" disabled="<%=flag %>" /></td>
		<%}else{ %>
		<td><input type="submit" value="SaveRelative" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=SaveRelative'" /></td>
		<%} %>                                             
	</tr>
</table>
</html:form>
</body>

</html>