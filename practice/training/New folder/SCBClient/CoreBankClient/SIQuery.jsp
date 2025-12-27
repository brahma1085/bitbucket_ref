<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="masterObject.backOffice.SIEntryObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SIQuery </title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
		      
    <script type="text/javascript">
   
    function set(target)
    {
    
    document.forms[0].forward.value=target;
    };
    
    function properdate(fromdate,todate){
  
  
  var dtCh="/";
   
  var pos1=fromdate.indexOf(dtCh)
  var pos2=fromdate.indexOf(dtCh,pos1+1)
  var frmMonth=fromdate.substring(pos1+1,pos2)
  var frmDay=fromdate.substring(0,pos1)
  var frmYear=fromdate.substring(pos2+1)
  
  
  var pos3=todate.indexOf(dtCh)
  
  var pos4=todate.indexOf(dtCh,pos3+1)
  
  var ToMonth=todate.substring(pos3+1,pos4)
  
  var ToDay=todate.substring(0,pos3)
  
  var ToYear=todate.substring(pos4+1)
  
  
  
  if(frmYear>ToYear){
    alert("From Year is greater than To Year!!!! Enter valid date")
  }
  else if(frmMonth>ToMonth && frmYear<=ToYear ){
    alert("From Month is greater than To Month !!!! Enter valid date")
  }
 else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  alert("From day is greater than To day !!!! Enter valid date")
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
    
    function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only "+size+"digits allowed")
         		txt.value="";
         		return false;
          	}
         }
    
    function clears(){
        alert("clearing form");
         	    } ;  
         	    
    function CallMe(){
      
         if(document.getElementById("valid").value!=null)
          {
       	if(document.getElementById("valid").value=="Recordsnotfound")
       	{
         	alert("Records Not Found");
         } 
       }
                
       
       }; 
      
    
    
    
    
    </script>
</head>
<body class="Mainbody" style="overflow: scroll;" onload="CallMe()">
<center><h2 class="h2">Query On StandingInstructions</h2></center>
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
<html:form action="/BackOffice/SIQuery?pageId=11006">
<%AccountObject accObj=(AccountObject)request.getAttribute("frmaccname");
System.out.println("======================>"+accObj);
  SIEntryObject[] sientry;
%>
<% String displaymsg=(String)request.getAttribute("displaymsg");%>
<%if(displaymsg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=displaymsg %></font>
<%} %>
<% String accnuam;
	accnuam=(String)request.getAttribute("accountholdername");
	System.out.println(""+accnuam);

%>
<center>
<table class="txtTable">
 	<tr>
		<td><bean:message key="label.acType"></bean:message>
			<html:select property="acctype">
			 <% ModuleObject[] arraymodobj=(ModuleObject[])request.getAttribute("Acctypes");
 				if(arraymodobj!=null){ 	
	           for(int i=0;i<arraymodobj.length;i++){ %>  
	           <html:option value="<%=""+arraymodobj[i].getModuleCode()%>" ><%=""+arraymodobj[i].getModuleAbbrv()%></html:option>
 			<%} }%>
			</html:select>
		</td>
		<td></td><td></td><td></td><td></td>
		<html:hidden property="valid"></html:hidden>
	  
		<td><bean:message key="label.acNum"></bean:message>
			<html:text property="accno" size="10"   onblur="submit()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text>
		</td>
		<td></td><td></td><td></td><td></td>
		<td><bean:message key="label.ac_holder_name"></bean:message>
		       <%if(accObj!=null){ %>
			<html:text property="accholdername"  style="border:none;background:transparent;color:#996680;font-weight:bold" readonly="true" value="<%=accObj.getAccname() %>" ></html:text>
			   <%}else if(accnuam!=null) { %>
			   <html:text property="accholdername"  style="border:none;background:transparent;color:#996680;font-weight:bold" readonly="true" value="<%=accnuam%>" ></html:text>
			   <%} %>
		</td>
	</tr>
</table>
<br><br>		
<table class="txtTable">	
	<tr>
	    
	     <html:hidden property="forward" value="error"></html:hidden>
	   <td>
		  <html:submit  onclick="set('view')" styleClass="ButtonBackgroundImage"><bean:message key="label.view1" ></bean:message> </html:submit>
		   <!--<html:button  onclick="window.print()" property="printFile" styleClass="ButtonBackgroundImage"><bean:message key="label.print" ></bean:message> </html:button>-->
            
		  <html:submit onclick="set('clear')" styleClass="ButtonBackgroundImage"><bean:message key="label.clear" ></bean:message></html:submit>
		<html:submit onclick="set('save')" styleClass="ButtonBackgroundImage">DownLoad</html:submit>
		</td>  
	</tr>
</table>
</center>
<br>
<center>
<%
   sientry=(SIEntryObject[])request.getAttribute("QueryDetails");
   if(sientry!=null){
	   //System.out.println("Recipt type"+sientry[0].getStrloanopt());
%>
    
     <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table name="QueryDetails" export="true" id="currentRowObject" class="its" sort="list" pagesize="10" requestURI="/BackOffice/SIQuery.do">
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 
     
 
   		<display:column title="InstNo"        property="instNo"   				style="background-color:#F2F0D2"></display:column>
   		<display:column title="Due Date"      property="dueDt"    				style="background-color:#F2F0D2"></display:column>
   		<display:column title="priority No"   property="priorityNo"   			style="background-color:#F2F0D2"></display:column>
  		<display:column title="Amount"        property="amount"          		style="background-color:#F2F0D2"></display:column>
   		<display:column title="Trf to Name"   property="toaccholdername"              	style="background-color:#F2F0D2"></display:column>
   		<display:column title="Receipt Type"  property="strloanopt"             	style="background-color:#F2F0D2"></display:column>  
   		<display:column title="No of times"   property="noExec"           		style="background-color:#F2F0D2"></display:column> 
   		<display:column title="Last Date"     property="lastExec" 		        style="background-color:#F2F0D2"></display:column>
   		<display:column title="Deleted on"    property="delDate" 		        style="background-color:#F2F0D2"></display:column>
   		<display:column title="To AccType"    property="tomodAbbrv" 		    style="background-color:#F2F0D2"></display:column>
   		<display:column title="To AccNo"      property="toAccNo" 		        style="background-color:#F2F0D2"></display:column>
   
	</display:table>
	</div>
	<%} %>
</center>


</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>