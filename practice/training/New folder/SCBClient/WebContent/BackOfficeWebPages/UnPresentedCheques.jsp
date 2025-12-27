<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.backOffice.ChequeNoObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UnPresentedCheques</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
	
      
     <script language="javascript">
      
      //submitting

   function onlynumbers()
{
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
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
         		alert("only "+size+" digits allowed")
         		txt.value="";
         		return false;
          	}
         }
	function set(target)
{
  
  document.forms[0].forward.value=target;
  document.forms[0].submit();

}
   function getmesg(){
  
    if(document.getElementById("valid").value!=null)
       {
       	if(document.getElementById("valid").value=="Recordsnotfound")
       	{
         	alert("Records Not Found");
        } 
       }     
  };
          
       
       //clearing form
        function clears(){
        alert("clearing form");
         	    } ;  
         	    
       //records validation    
   function CallMe()
    {
        if(document.forms[0].accno.value=="")
       	{
       		alert("PLZ ENTER THE ACCOUNT NUM");
       		return false;
       	}
       	
       else if(document.getElementById("valid").value!=null)
       {
       	if(document.getElementById("valid").value=="RecordsNotfound")
       	{
         	alert("Records Not Found");
        } 
       }     
       else
       	{
       		document.forms[0].submit();
       		document.forms[0].forward.value="false";
       		
       	}
       
     };
 
      //checking for numeric
      function isnumeric(){
      
      var ch=event.keycode;
      if(ch > 47 && ch < 58){
      return true; 
      }
      else{
      alert("Enter numeric value!!!!!!!!!!! ");
      return false;
      }
      }   
      	    
         	    
      </script> 

</head>

<body class="Mainbody" style="overflow: scroll;" onload="getmesg()">
<center><h2 class="h2">List Of UnPresentedCheque Statement</h2></center>
<html:form action="/BackOffice/UnPresentedCheques?pageId=11003">
<html:hidden property="testing" styleId="totaltesting"></html:hidden>
<% String displaymsg=(String)request.getAttribute("displaymsg");%>
<%if(displaymsg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=displaymsg %></font>
<%} %>
<center>
<table class="txtTable">
 	<tr>
		<td><bean:message key="label.acType"></bean:message>
			<html:select property="acctype">
			<%ModuleObject[] arraymodobj=(ModuleObject[])request.getAttribute("AccountType");
 				if(arraymodobj!=null){ 	
	for(int i=0;i<arraymodobj.length;i++){               
 			%> 
				<html:option value="<%=""+arraymodobj[i].getModuleCode()%>" ><%=""+arraymodobj[i].getModuleAbbrv()%></html:option>
 			<%} }%>
			</html:select>
		</td>
		<td></td><td></td><td></td><td></td>
		 <html:hidden property="validaccno" styleId="validaccno"></html:hidden>
		<td><bean:message key="label.acNum" ></bean:message>
			<html:text property="accno" size="10" onblur="CallMe()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'9')" styleClass="formTextFieldWithoutTransparent"></html:text>
		</td>
		</tr>
	</table>
<br><br>		
<table class="txtTable">	
	<tr>
	    
	     <html:hidden property="forward" value="error"></html:hidden>
	     <html:hidden property="valid" styleId="valid"></html:hidden>
	     
	     
	   <td>
		  <html:submit  onclick="set('view')" styleClass="ButtonBackgroundImage"><bean:message key="label.view1"></bean:message> </html:submit>
		  <!--<html:button onclick="window.print()" property ="printFile"  styleClass="ButtonBackgroundImage"><bean:message key="label.print"></bean:message> </html:button>-->
          <html:submit onclick="set('clear')" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:submit>
		  <html:button property="but_print" onclick="set('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
		</td>  
	</tr>
</table>
</center>
<br>
<center>

 <div  id = "table1" style="display: block;width: 750px;height: 300px">
   	<display:table name="ChequeDetails" export="true" id="currentRowObject" class="its" sort="list" requestURI="/BackOffice/UnPresentedCheques.do" pagesize="10">
   	<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   	<display:column media="csv excel" title="URL" property="url" />
    <display:setProperty name="export.pdf" value="true" /> 
 
   <display:column title="AccountType" property="modAbbrv" ></display:column>
   <display:column property="accountNo" ></display:column>
   <display:column property="accountName" ></display:column>
   <display:column property="chequeNo" ></display:column>
   <display:column property="nextChequeDate" ></display:column>
   <display:column  title="Remarks" ></display:column>  

</display:table>
</div>
</center>
</html:form>
</body>
</html>