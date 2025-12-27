                 

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="java.util.Map"%>
<%--
  To change this template use File | Settings | File Templates.
--%>

<html>
  <head><title>Create Users</title>
     <center>
<h2 class="h2">Create Users</h2></center>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
       
      
      <!--<style type="text/css">
          body{
              font-size:10px;
              font-family:serif;
              font-style:oblique;
              font-weight:bold;
              background:beige;
          }
          table{
               background:transperent; 
          }
          
    </style>
    --><link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    <script>
   function confirmsubmit(){
  
   
   
   }
   
   function setFlag(flagValue)
   {
   	document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     return true;        
}
     
     function validate(){
      var ele=document.forms[0].elements;
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         alert(val);
       } 
       
       else{
       for(var i=0;i<ele.length;i++){
       if(ele[i].type=="hidden"){
       ele[i].value="";
       }
       }
         return false;
       }
        
       for(var i=0;i<ele.length;i++){
       if(ele[i].type=="hidden"){
       ele[i].value="";
       }
       }
     }; 
     function clearFunc()
    {
    	
    	var ele=document.forms[0].elements;
    	  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    		if(ele[i].type=="password")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    
    
    function checkAll(){
    var ele=document.forms[0].elements;
    for(var j=0;j<ele.length;j++){
    if(ele[j].type=="text"){
    if(ele[j].value==""){
    alert("Please provide all the details ");
    ele[j].focus();
    return false;
    }
    }
    
    else if(ele[j].type=="password"){
    if(ele[j].value==""){
    alert("Please provide password");
    ele[j].focus();
    return false;
    }
    }
    else if(ele[j].type=="radio"){
    if(ele[j].checked){
    
    }
    else{
    alert("Please select Enable or Disable");
    ele[j].focus();
     return false;
    
    }
    }
    else{
    
    }
    }
    
    }
    
    function num_Limit()
 	{
 	if(document.forms[0].uid.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].uid.value="";
 	document.forms[0].uid.focus;
 	alert("Digit Limit is 10");
 	return false;
 	}
 	}
 	
 	function num_Limit1()
 	{
 	if(document.forms[0].cid.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].cid.value="";
 	document.forms[0].cid.focus;
 	alert("Digit Limit is 10");
 	return false;
 	}
 	}
 	
 	function name_Limit()
 	{
 	if(document.forms[0].name.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].name.value="";
 	document.forms[0].name.focus;
 	alert("Enter not more than 10 characters");
 	return false;
 	}
 	}
 	
 	function pwd_Limit()
 	{
 	if(document.forms[0].pwd.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].pwd.value="";
 	document.forms[0].pwd.focus;
 	alert("Enter not more than 10 characters");
 	return false;
 	}
 	}
 	
 	function rePwd_Limit()
 	{
 	if(document.forms[0].rePwd.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].rePwd.value="";
 	document.forms[0].rePwd.focus;
 	alert("Enter not more than 10 characters");
 	return false;
 	}
 	}
    
    function pwdexp_Limit()
 	{
 	if(document.forms[0].pwdExpPeriod.value.length<=5)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].pwdExpPeriod.value="";
 	document.forms[0].pwdExpPeriod.focus;
 	alert("Warning:\nDigit Limit is 5!!");
 	return false;
 	}
 	}
 	
 	function pwdexpdate_Limit()
 	{
 	if(document.forms[0].pwdExpDate.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].pwdExpDate.value="";
 	document.forms[0].pwdExpDate.focus;
 	alert("Enter dd/mm/yyyy Format!!!");
 	return false;
 	}
 	}
    
    function acFrmDate_Limit()
    {
 	if(document.forms[0].acFrmDate.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].acFrmDate.value="";
 	document.forms[0].acFrmDate.focus;
 	alert("Enter dd/mm/yyyy Format!!!");
 	return false;
 	}
 	}
 	
 	function acToDate_Limit()
 	{
 	if(document.forms[0].acToDate.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].acToDate.value="";
 	document.forms[0].acToDate.focus;
 	alert("Enter dd/mm/yyyy Format!!!");
 	return false;
 	}
 	}
    
     function onlyalpha()
	{
 		var cha =event.keyCode;
		if ( (cha >= 97 && cha <= 122 )||(cha>=65 && cha<=90) ) 
		{
			return true;
            		
        	} else {
        		
        	return false;
        	}
	};	
   
   function onlynumbers()
{
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        };
        
        function onlychar_num()
	{
        	var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || (cha >= 97 && cha <= 122 )|| cha==46 || cha==95 ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
    };
    
    function onlypwdchar()
    {
    var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || (cha >= 97 && cha <= 122 ) ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
    };
    
    function chckdetails()
{
	var uid=document.forms[0].uid.value;
	var br_code=document.forms[0].br_code.value;
	alert("Creating User id as : "+uid+"_"+br_code);
	if(document.forms[0].uid.value="")
	{
		alert("Enter the User ID..!!");
		document.getElementById("uid").focos();
		return false;
	}
	else if(document.forms[0].cid.value="")
	{
		alert("Enter  the Customer ID..!!");
		document.getElementById("cid").focos();
		return false;
	}
	else if(document.forms[0].pwd.value="")
	{
		alert("Enter  the Password..!!");
		document.getElementById("pwd").focos();
		return false;
	}
	else if(document.forms[0].rePwd.value="")
	{
		alert("Retype the Password..!!");
		document.getElementById("rePwd").focos();
		return false;
	}
	else
	{
		return true;
	}
}
    
    function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57)||cha==47 ) 
		 {
   		 	return true;
          } 
          else 
          {
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
    
    function IsValidDate(Date,Mn,Yr)
    {
    var DateVal=Day+"/"+Mn+"/"+Yr;
    var dt=new Date(DateVal);
    if(dt.getDate()!=Day)
    {
    	alert("Invalid Date!!");
    	return false;
    }
    else if(dt.getMonth()!=Mn-1)
    {
    	alert("Invalid Date!!");
    	return false;
    }
    else if(dt.getfullyear()!=Yr)
    {
    	alert("Invalid Date!!");
    	return false;
    }
    return true;
    }
    	
   function passwordcheck()
   {
   var pwd=document.forms[0].pwd.value;
   var repwd=document.forms[0].repwd.value;
   if(pwd!=repwd)
   {
   	alert("Please enter the correct password");
   	document.forms[0].pwd.value="";
   	document.forms[0].repwd.value="";
   	document.forms[0].pwd.focus();
   	return false;
   }
   else
   {
   	return true;
   }
   } 	
    </script>
  </head>
  <body class="Mainbody" onload="return validate()">
    <%!
    Map user_role;
    String access,branchname;
    %>
    <%
    String accesspageId=(String)request.getAttribute("accesspageId");
	user_role=(Map)request.getAttribute("user_role");
	
	if(user_role!=null&&accesspageId!=null){
		if(user_role.get(accesspageId)!=null){
			access=(String)user_role.get(accesspageId);
			System.out.println("access-----In Create users------>"+access);
		}
		}
    %>
  <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>   
     <html:form action="/Administrator/CreateUsers?pageId=10008">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="pageId" value="3005"/>
     	<table>
     	<tr>
     	   <td>
     	   <table background="#fffccc" style="border:thin solid #000000;">
     	
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  User Id:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="uid" onblur="setFlag('from_uid')" onkeypress="return onlychar_num()" onkeydown="return num_Limit()"></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Customer Id:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="cid" onblur="setFlag('from_cid')" onkeypress="return onlynumbers()" onkeydown="return num_Limit1()"></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Short Name:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="name" onkeypress="return onlyalpha()" onkeydown="return name_Limit()"></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Enter Password:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:password property="pwd" onkeypress="return onlypwdchar()" onkeydown="return pwd_Limit()"></html:password>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Re-type Password:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:password property="rePwd" onkeypress="return onlypwdchar()" onkeydown="return rePwd_Limit()" onblur="return passwordcheck()"></html:password>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 Password Expiry period:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue" >
     	  <html:text property="pwdExpPeriod"  onkeypress="return onlynumbers()" onkeydown="return pwdexp_Limit()"></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 Password Expiry Date:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="pwdExpDate"  onkeydown="return pwdexpdate_Limit()" onkeypress="return only_numbers()" ></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 Ac Operation From Date:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="acFrmDate" onkeypress="return only_numbers()" onkeydown="return acFrmDate_Limit()" ></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 Ac Operation To Date:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	 <html:text property="acToDate" onkeypress="return only_numbers()" onkeydown="return acToDate_Limit()" ></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 <html:radio property="userChoice" value="Disable">Disable</html:radio>
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:radio property="userChoice" value="Enable">Enable</html:radio>
     	 </td>
     	</tr>
     	</table>
     	   </td>
     	   <td>
     	   <table>
<tr>
<td>
<%  String jspPath=(String)request.getAttribute("flag");
	System.out.println("'m inside panel"+jspPath);
	if(jspPath!=null)
	{
	System.out.println("wen 'm  null");
    %>
    <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
    
    <%}else{	%>
    <jsp:include page="/LockerWebPages/blank.jsp"></jsp:include>
    <%} %>
 	</td>
 	</tr>
 	</table>													
 	</td>
    </tr>
    </table>	
    <br>	
 	<table name="button_table" align="center">
     	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
     	<html:button  property="update" onclick="setFlag('update'); " styleClass="ButtonBackgroundImage" ><bean:message key="gl.label.update"></bean:message> </html:button>
     	<%}else{ %>
     	<html:button  property="update"  onclick="setFlag('update'); " styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="gl.label.update"></bean:message> </html:button>
     	<%} %>
     	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
        <html:button  property="sub" onclick="setFlag('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:button>
        <%}else{ %>
        <html:button  property="sub" onclick="setFlag('submit')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.submit"></bean:message> </html:button>
        <%} %>
        <%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
        <html:button  property="del" onclick="setFlag('delete')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.delete"></bean:message></html:button>
        <%}else{ %>
        <html:button  property="del" onclick="setFlag('delete')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="gl.label.delete"></bean:message></html:button>
        <%} %>
       <html:button  property="b4" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button>
     	</table>
               </html:form>													
     	<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>   
  </body>
  </html>          