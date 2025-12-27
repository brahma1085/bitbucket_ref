
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AdminObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@page import="java.util.Map"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
 
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<%@page import="com.scb.fc.forms.AccountCloseActionForm"%>

<%@page import="masterObject.frontCounter.AdminObject"%>
<%@page import="com.scb.designPatterns.FrontCounterDelegate"%>
<html>
  <head><title>AccountOpening</title>
      <font color="blue" ><center>
<h2 class="h2">SB/CA Admin</h2></center></font>
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

function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57)) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	
	 function only_numbers_date()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57 ||cha==47)) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};

function getDetails(val){

document.forms[0].hidval.value=val;
document.forms[0].submit();
}
function setDetails(val){

if(document.forms[0].hidval.value==""){
alert("Please click on add row to add Parameters");
}
if(document.forms[0].selectedval.value=="SavingsIntRate"){
if(document.forms[0].int_rate.value==""){
alert("Please enter Interest Rate");
document.forms[0].int_rate.focus();
}
else{

document.forms[0].hidval.value='toupdate';
document.forms[0].insertval.value=val;
document.forms[0].submit();
}
}
else if(document.forms[0].selectedval.value=="SavingsCatRate"){
if(document.forms[0].date_to.value==""){
alert("Please Enter To date ");
document.forms[0].date_to.focus();
}
else if(document.forms[0].days_fr.value==""){
alert("Please Enter From Days");
document.forms[0].days_fr.focus();
}
else if(document.forms[0].days_to.value==""){
alert("Please Enter To Days");
document.forms[0].days_to.focus();
}
else if(document.forms[0].int_rate.value==""){
alert("Please Enter Interest Rate");
document.forms[0].int_rate.focus();
}
else{

document.forms[0].hidval.value='toupdate';
document.forms[0].insertval.value=val;
document.forms[0].submit();
}
}
if(document.forms[0].selectedval.value=="TokenNumbers"){
if(document.forms[0].tokenno.value==""){
alert("Please Enter Token No.");
document.forms[0].tokenno.focus();
}
else if(document.forms[0].tdate.value==""){
alert("Please Enter To Date");
document.forms[0].tdate.focus();
}
else{

document.forms[0].hidval.value='toupdate';
document.forms[0].insertval.value=val;
document.forms[0].submit();
}
}

}
function setIDetails(val){
if(document.forms[0].hidval.value==""){
alert("Please click on add row to add Parameters");
}
if(document.forms[0].selectedval.value=="SavingsIntRate"){
if(document.forms[0].int_rate.value==""){
alert("Please enter Interest Rate");
document.forms[0].int_rate.focus();
}
else{
document.forms[0].hidval.value='toupdate';
document.forms[0].insertval.value=val;
document.forms[0].submit();
}
}
else if(document.forms[0].selectedval.value=="SavingsCatRate"){
if(document.forms[0].date_to.value==""){
alert("Please Enter To date ");
document.forms[0].date_to.focus();
}
else if(document.forms[0].days_fr.value==""){
alert("Please Enter From Days");
document.forms[0].days_fr.focus();
}
else if(document.forms[0].days_to.value==""){
alert("Please Enter To Days");
document.forms[0].days_to.focus();
}
else if(document.forms[0].int_rate.value==""){
alert("Please Enter Interest Rate");
document.forms[0].int_rate.focus();
}
else{
document.forms[0].hidval.value='toupdate';
document.forms[0].insertval.value=val;
document.forms[0].submit();
}
}
if(document.forms[0].selectedval.value=="TokenNumbers"){
if(document.forms[0].tokenno.value==""){
alert("Please Enter Token No.");
document.forms[0].tokenno.focus();
}
else if(document.forms[0].tdate.value==""){
alert("Please Enter To Date");
document.forms[0].tdate.focus();
}
else{

document.forms[0].hidval.value='toupdate';
document.forms[0].insertval.value=val;
document.forms[0].submit();
}
}

}

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
            			alert(" year should be less than or equal to"+dds[2]+" !");
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
        }

function resetting(){
document.forms[0].action='/FrontCounter/SbCaAdminMenu?pageId=3025';
document.forms[0].submit();
}


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
    show(false,'div1');
     show(false,'div2');
}


var ns4 = (document.layers) ? true : false;
var ie4 = (document.all && !document.getElementById) ? true : false;
var ie5 = (document.all && document.getElementById) ? true : false;
var ns6 = (!document.all && document.getElementById) ? true : false;

function show(sw,obj) {
  // show/hide the divisions
  if (sw && (ie4 || ie5) ) document.all[obj].style.visibility = 'visible';
  if (!sw && (ie4 || ie5) ) document.all[obj].style.visibility = 'hidden';
  if (sw && ns4) document.layers[obj].visibility = 'visible';
  if (!sw && ns4) document.layers[obj].visibility = 'hidden';
}
    
    
</script>
    
  </head>
  <body class="Mainbody">
    <%!
        ModuleObject[] array_module;
        String details[];
        AccountMasterObject acountMaster;
        String jspPath;
        ModuleObject[] mod;
        CustomerMasterObject cmObject;
        Map user_role;
        String access;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
   
    	AdminObject[] adminobj=(AdminObject[])request.getAttribute("sbcaAdminvalues");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	FrontCounterDelegate fcdel=new FrontCounterDelegate();
    	String letupdate=(String)request.getAttribute("letupdate");
    	String accesspageId=(String)request.getAttribute("accesspageId");
    	user_role=(Map)request.getAttribute("user_role");
    	
    	
    	if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}
    %>
 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
    <div id = "div2" class = "myLayersClass">
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=request.getAttribute("closingmsg")%></font>
    </core:if>
    </div>
    
     <html:form  action="/FrontCounter/SbCaAdmin?pageId=3025" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="hidval" />
     	<html:hidden property="insertval" />
     	<html:hidden property="sysdate" />
     	<br><br><br>
          
<table border="1" width="565" height="66" class="txtTable">
	<tr>
		<td height="60" width="220">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp; Select Table :</td>
		<td height="60" width="329">&nbsp;&nbsp;&nbsp;
		<html:select property="selectedval">
		<html:option value="SELECT">SELECT</html:option>
		<html:option value="SavingsIntRate">Saving Interest Rate</html:option>
		<html:option value="SavingsCatRate">SavingCatRate</html:option>
		<html:option value="TokenNumbers">Token Numbers</html:option>
		</html:select>
		
		</td>
	</tr>
</table>

	<!--webbot bot="SaveResults" U-File="E:\Mohsin's DOCS\SUNRISE\Frontcounter(New)\_private\form_results.csv" S-Format="TEXT/CSV" S-Label-Fields="TRUE" -->
	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:button property="B1" value="VIEW" styleClass="ButtonBackgroundImage" onclick="submit()"></html:button>
	<%}else{ %>
	<html:button property="B1" value="VIEW" styleClass="ButtonBackgroundImage" onclick="submit()" disabled="true"></html:button>
	<%} %>
	<html:button property="B2" value="ADD ROW" onclick="getDetails('toadd')" styleClass="ButtonBackgroundImage"></html:button>
	<!--<input type="button" value="Update" name="B3" onclick="setDetails('toupdate')">&nbsp;&nbsp;
	<input type="button" value="  Insert  " name="B4" onclick="setIDetails('toinsert')">
	<input type="reset" value="  Clear  " name="B5" onclick="resetting()">
	<input type="submit" value="   View " name="B1">&nbsp;&nbsp;
	<input type="button" value=" AddRow  " name="B2" onclick="getDetails('toadd')">&nbsp;&nbsp;
	
	-->
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<html:button property="B3" styleClass="ButtonBackgroundImage" value="INSERT" onclick="setIDetails('toinsert')"></html:button>
	<%}else{ %>
	<html:button property="B3" styleClass="ButtonBackgroundImage" value="INSERT" onclick="setIDetails('toinsert')" disabled="true"></html:button>
	<%} %>
	<core:if test="<%=letupdate!=null %>">
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
	<html:button property="B6" value="Update" onclick="setDetails('toupdate')" styleClass="ButtonBackgroundImage" ></html:button>
	<%}else{ %>
	<html:button property="B6" value="Update" onclick="setDetails('toupdate')" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
	<%} %>
	</core:if>
	<html:button property="B3" value="CLEAR" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button>
	</p>
	    <div id = "div1" class = "myLayersClass" >
<table border="1" style="background-color:#CDCEAE">
<%
if(adminobj!=null&&adminobj.length>0){
for(int i=0;i<adminobj.length;i++){
	if(adminobj[i].getData()!=null){
	String[] strArry=adminobj[i].getCol_names();%>
	<tr>
	<%for(int p=1;p<strArry.length;p++){%>
		<td style="background-color:#CDCEAE"><%= strArry[p]%></td>
		<%
		System.out.println("Name of columns are "+strArry[p]);
	}
	%></tr>
	<%
	Object[][] admobj2=adminobj[i].getData();
	for(int k=0;k<admobj2.length;k++){%>
		<tr>
		<%for(int j=1;j<strArry.length;j++){ %>
		<td style="background-color:#ECEBD2"><%=admobj2[k][j].toString() %></td>
		
		
		
		
	<%}%>
	</tr>
		<%}
}
	}
}
else{
%>

<%} %>
</table>

<br><br>
<%
if(request.getAttribute("settrue")!=null){
	%>
		<table border="1">
<%
if(adminobj!=null){
for(int i=0;i<adminobj.length;i++){
	String[] strArry=adminobj[i].getCol_names();%>
	<tr>
	<%for(int p=1;p<strArry.length;p++){%>
		<td bgcolor=#FF9933><%= strArry[p]%></td>
		<%
		
	}
	%></tr>		
	<%}}
	if(request.getAttribute("settrue").equals("savingInt")){%>
	<tr><td><html:select property="savacType" styleClass="formTextFieldWithoutTransparent">
                      
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select></td>
     <td><html:text property="date" value="<%= fcdel.getSysDate()%>" disabled="true"></html:text></td>                   
            <td><html:text property="int_rate" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;"></html:text>    </td>        
             <td><html:text property="de_tml" readonly="true"></html:text>    </td>
             <td><html:text property="de_user" readonly="true" ></html:text>    </td>           
              <td><html:text property="de_date" value="<%= fcdel.getSysDate()+""+fcdel.getSysTime()%>" disabled="true"></html:text>    
              </td>          
                        
                        </tr>
	
	<%}
	else if(request.getAttribute("settrue").equals("savingCat")){
	%>
		<tr><td><html:select property="savacType" styleClass="formTextFieldWithoutTransparent">
                      
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select></td>
     <td><html:text property="date" value="<%= fcdel.getSysDate()%>" disabled="true"></html:text></td>
     <td><html:text property="date_to" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()"></html:text></td>       
     <td><html:text property="days_fr" onkeypress="return only_numbers()"></html:text></td>   
     <td><html:text property="days_to" onblur="return datevalidation(this)" onkeypress="return only_numbers()"></html:text></td>               
            <td><html:text property="int_rate" onkeypress="return only_numbers()"></html:text>    </td>        
                <td><html:select property="subcat">
             
                        <core:forEach var="subcat" varStatus="count" items="${requestScope.subcat}" >
                        <html:option value="${subcat.subCategoryCode}" ><core:out value="${subcat.subCategoryDesc}"></core:out></html:option>
                        	    
                        </core:forEach> 
                
                </html:select>   </td>        
                <td><html:text property="de_tml" readonly="true"></html:text>    </td>
             <td><html:text property="de_user" readonly="true"></html:text>    </td>           
              <td><html:text property="de_date" value="<%= fcdel.getSysDate()+""+fcdel.getSysTime()%>" disabled="true"></html:text>    
              </td>        
                        
                        </tr>
	
	<%}
	else if(request.getAttribute("settrue").equals("token")){%>
	
		<tr><td><html:text property="tokenno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;"></html:text></td>
     <td><html:text property="fdate" value="<%= fcdel.getSysDate()%>" readonly="true"></html:text></td>                   
            <td><html:text property="tdate" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()"></html:text>    </td>        
            <td><html:text property="de_tml" readonly="true"></html:text>    </td>
             <td><html:text property="de_user" readonly="true"></html:text>    </td>           
              <td><html:text property="de_date" value="<%= fcdel.getSysDate()+""+fcdel.getSysTime()%>" disabled="true"></html:text>    
              </td>            
                        
                        
                       </tr>
	
	<%}%>
	</table>
<%}


%>
</div>
   </html:form>
                <%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
            
             <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
     </body>
     </html>          <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html>