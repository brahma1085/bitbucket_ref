<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %> 
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="java.util.Map"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Query On Receipt No</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
    <h2 class="h2">
      <center>Query On Receipt No </center></h2>

<script type="text/javascript">
function  funclear(){

var v = document.forms[0].elements;
for(var i=0;i<=v.length;i++){

if(v[i].type =="text"){

v[i].value = "";

}
document.getElementById("table1").style.display='none';

}
 
};

function numbersonly(eve){
         var cha = event.keyCode


            if (  ( cha  >47 && cha < 58  ) ) {
         
                return true ;
            }
            else{
            
              alert("Please enter numbers only");
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
         		alert("please enter within eight digits ")
         		txt.value="";
         		return false;
          	}
         };


        
function Validatefields(){
if(document.getElementById("totaltesting").value!=0)
{
  alert(document.getElementById("totaltesting").value);
}

  var v = document.forms[0].elements;
    for(var i=0;i<=v.length;i++)
    {
       if(v[i].type =="hidden")
       {
          v[i].value = "";

        }
     }


};

function checkblank(data,str){

  var len= data.length;
  if(len==0){
  
    alert("Please Enter the"+str);
  
  }

};

     

</script>

</head>
<body class="Mainbody" onload="Validatefields()">
<%!
ModuleObject[] array_module;
DepositMasterObject[] dep_mast;
String[] detail;
%>

<%dep_mast = (DepositMasterObject[])request.getAttribute("queryreceipt");

  
System.out.println("geetha inside Query on receipt accounts..");

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

// Note:access=1111==>read,insert,delete,update
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/QueryOnReceiptNo?pageId=13005" >
<table class="txtTable" >

<html:hidden property="validate" ></html:hidden>
<html:hidden property="testing" styleId="totaltesting"></html:hidden>
<tr>
              <td>
                      
			       <bean:message key="label.td_actype"></bean:message>
			    
			    <html:select property = "ac_type" styleClass="formTextFieldWithoutTransparent">
			    	<% array_module=(ModuleObject[])request.getAttribute("Dep type");
			    			for(int i=0;i<array_module.length;i++)
			    			{
			        		 	System.out.println("acctype"+ array_module);
			    	%>	    	
			    				<html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%
			    			}
			    	%>	
			    	 </html:select>
    		   </td>


<td width="15%"></td>
                  <td>
			         <bean:message key="label.receipt_no"></bean:message>
			     	
			     <html:text property="receipt_no" size="10" onkeypress="return numbersonly()"   onblur="checkblank(receipt_no.value,'Receipt No')" onkeyup="numberlimit(this,'9')" styleClass="formTextFieldWithoutTransparent"></html:text>
			     
			
			       </td>
			       
</tr>

<br>

<tr height="50%">


<td height="30%"></td>
</tr>

<tr>
<td width="20%"></td>
<td>
				<html:submit property="view" onchange="submit()" styleClass="ButtonBackgroundImage"><bean:message key="label.view1"></bean:message></html:submit></td>
				<td><html:cancel property="cancel"  onclick="return funclear()" styleClass="ButtonBackgroundImage"></html:cancel>
				
</td>
</tr>
</table>


<hr>
<div  id = "table1" style="display:block; overflow:scroll; width: 750px;height: 300px">


<%if(dep_mast!=null){
	
	%>


<display:table name="queryreceipt"  id="currentRowObject" requestURI="/TermDeposit/QueryOnReceiptNo.do" class="its" sort="list" >
   			
   



<display:column property="accType" ></display:column>
<display:column property="accNo" ></display:column>
<display:column property="name"  title ="Depositor Name"></display:column>
<display:column property="details_address" maxWords="8"  ></display:column>
<display:column property="depositDays" ></display:column>
<display:column property="depositAmt"> </display:column>
<display:column property="depDate"  title ="Deposit Date"></display:column>
<display:column property="maturityAmt" ></display:column>
<display:column property="maturityDate" ></display:column>

<display:column property="interestMode"  title="Payment Mode"></display:column>

<display:column property="nomineeRegNo" ></display:column>



</display:table>

<%} %>
</div>




</html:form>

<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>