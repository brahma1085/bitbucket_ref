<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>

<%@page import="com.scb.loans.forms.OtherChargesReport"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
          body{
             background:beige;
          }
          table{
             background:transparent;
             }
             
          tr{
             background:transparent;
          }
          td{
             background:transparent;

          }
</style>
      
<script type="text/javascript">

function fuc_valchange()
	{

	if(document.forms[0].combo_othercharges.value=="Collected"){
	
		if(document.forms[0].acctype.value=="All Types")
		{
	
			document.forms[0].from_acno.disabled = true;
			document.forms[0].to_acno.disabled = true;
			document.forms[0].fromdate.disabled = false;
			document.forms[0].todate.disabled = false;
			document.getElementById("fromdate").focus();
			//document.forms[0].combo_actype.disabled = true;
			return true;
		}
		else
		{
			document.forms[0].from_acno.disabled = false; 
			document.forms[0].to_acno.disabled = false;
			document.forms[0].fromdate.disabled = false;
			document.forms[0].todate.disabled = false;
			//document.forms[0].combo_actype.disabled = false;
			return true;   
		}
		}else if(document.forms[0].combo_othercharges.value=="OverDue"){
		if(document.forms[0].acctype.value=="All Types"){
		
		document.forms[0].from_acno.disabled = false; 
			document.forms[0].to_acno.disabled = false;
			document.forms[0].fromdate.disabled = true;
			document.forms[0].todate.disabled = true;
			//document.forms[0].combo_actype.disabled = false;
			return true;   
		}else {
		document.forms[0].from_acno.disabled = true;
			document.forms[0].to_acno.disabled = true;
			document.forms[0].fromdate.disabled = false;
			document.forms[0].todate.disabled = false;
			document.getElementById("fromdate").focus();
			//document.forms[0].combo_actype.disabled = true;
			return true;
		
		}
		}
	};
      
     function set(target)
     {
     if(document.forms[0].combo_othercharges.value=="Collected"){
	
		if(document.forms[0].acctype.value=="All Types")
		{
		if(document.getElementById("fromdate").value=="")
	{
		alert('Enter the From Date!');
		document.getElementById("fromdate").focus();
		document.forms[0].from_acno.disabled = true;
			document.forms[0].to_acno.disabled = true;
		
		return true;
		}
	else if(document.getElementById("todate").value=="")
	{
		alert('Enter the ToDate!');
		document.getElementById("todate").focus();
		document.forms[0].from_acno.disabled = true;
			document.forms[0].to_acno.disabled = true;
		
		return true;
		}
		else{
		document.forms[0].forward.value=target;
		document.forms[0].submit();
		document.forms[0].from_acno.disabled = true;
			document.forms[0].to_acno.disabled = true;
//return true;
}}
		else
		{
			if(document.getElementById("fromdate").value=="")
	{
		alert('Enter the FromDate!');
		document.getElementById("fromdate").focus();
		return false;
		}
		else if(document.getElementById("todate").value=="")
	{
		alert('Enter the ToDate!');
		document.getElementById("todate").focus();
		return false;
		}
		else if(document.getElementById("from_acno").value=="")
	{
		alert('Enter the From Account Number!');
		document.getElementById("from_acno").focus();
		return false;
		}
		else if(document.getElementById("from_acno").value=="0")
	{
		alert('Enter the From Account Number!');
		document.getElementById("from_acno").focus();
		return false;
		}
		else if(document.getElementById("to_acno").value=="")
	{
		alert('Enter the To Account Number!');
		document.getElementById("to_acno").focus();
		return false;
		}
		else if(document.getElementById("to_acno").value=="0")
	{
		alert('Enter the To Account Number!');
		document.getElementById("to_acno").focus();
		return false;
		}
		else{
		document.forms[0].forward.value=target;
		document.forms[0].submit();
}
		}
		}else if(document.forms[0].combo_othercharges.value=="OverDue"){
		if(document.forms[0].acctype.value=="All Types"){
		
   		if(document.getElementById("from_acno").value=="0")
	{
		alert('Enter the From Account Number!');
		document.getElementById("from_acno").focus();
		return false;
		}
		else if(document.getElementById("from_acno").value=="")
	{
		alert('Enter the From Account Number!');
		document.getElementById("from_acno").focus();
		return false;
		}
		else if(document.getElementById("to_acno").value=="")
	{
		alert('Enter the To Account Number!');
		document.getElementById("to_acno").focus();
			document.forms[0].fromdate.disabled = true;
			document.forms[0].todate.disabled = true;
		return true;
		}
		else if(document.getElementById("to_acno").value=="0")
	{
		alert('Enter the To Account Number!');
		document.getElementById("to_acno").focus();
			document.forms[0].fromdate.disabled = true;
			document.forms[0].todate.disabled = true;
		return true;
		}else{
		document.forms[0].forward.value=target;
		document.forms[0].submit();
		document.forms[0].fromdate.disabled = true;
			document.forms[0].todate.disabled = true;
		}
		
		}else {
		
   		if(document.getElementById("fromdate").value=="")
	{
		alert('Enter the From date!');
		document.getElementById("fromdate").focus();
		document.forms[0].from_acno.disabled = true;
			document.forms[0].to_acno.disabled = true;
		return true;
		}else if(document.getElementById("todate").value=="")
	{
		alert('Enter the To date!');
		document.getElementById("todate").focus();
		document.forms[0].from_acno.disabled = true;
			document.forms[0].to_acno.disabled = true;
		return true;
		}else {
		document.forms[0].forward.value=target;
		document.forms[0].submit();
		document.forms[0].from_acno.disabled = true;
			document.forms[0].to_acno.disabled = true;
		}
		}
		}
		else {
		document.forms[0].forward.value=target;
		document.forms[0].submit();
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
	            				alert(" Month should be less than or equal to "+dds[1]+" !");
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
        function properdate(from_date,to_date){
  
  
  var dtCh="/";
   
  var pos1=from_date.indexOf(dtCh)
  var pos2=from_date.indexOf(dtCh,pos1+1)
  var frmMonth=from_date.substring(pos1+1,pos2)
  var frmDay=from_date.substring(0,pos1)
  var frmYear=from_date.substring(pos2+1)
  
  
  var pos3=to_date.indexOf(dtCh)
  
  var pos4=to_date.indexOf(dtCh,pos3+1)
  
  var ToMonth=to_date.substring(pos3+1,pos4)
  
  var ToDay=to_date.substring(0,pos3)
  
  var ToYear=to_date.substring(pos4+1)
  
  
  
  if(frmYear > ToYear){
    alert("Fromdate should be less than the Todate!")
  }
 // else if(frmMonth > ToMonth && frmYear<=ToYear ){
  //  alert("From Month is greater than To Month !!!! Enter valid date")
 // }
// else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
 // alert("From day is greater than To day !!!! Enter valid date")
 // }
 }; 
 
    


     
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
  function callClear()
  {
  		var form_ele = document.forms[0].elements;
  
  		for(var i=0;i< form_ele.length;i++ )
		{
			if(form_ele[i].type=="text")
				form_ele[i].value = "";
		
		}
  
  
  } ;
  function only_numbers_date() {
	
	var cha=event.keyCode;
	if(cha>=47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  
</script>
 <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />

     
      
</head>
<body>

	<center><h2 class="h2">OC Report</h2></center> 
	
	<%! 
 		Object[][] obj,obj1,obj2;
		ModuleObject object[];
 		String str=null;
 		double balance=0.0,debit=0.0,credit = 0.0;
 	%>
	
	
	<%	
		object = (ModuleObject[])request.getAttribute("LoanModuleCode");
  	%>
 	
  	<% 
  	ArrayList arraylist,arraylist2=null;
  
  		arraylist = (ArrayList)request.getAttribute("OCReportOver");
  		arraylist2 = (ArrayList)request.getAttribute("OCReportCollection");
  		
  	%>
	<%!
String access;
String  accesspageId;
 Map user_role;
%>
<%
  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");
System.out.println("11111111"+user_role);
System.out.println("222222222"+accesspageId);
if(user_role!=null&&accesspageId!=null){
System.out.println("11111111"+access);
			if(user_role.get(accesspageId)!=null){
			System.out.println("222222222"+access);
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}
			}
// Note:access=1111==>read,insert,delete,update
%>
	<%
		str = (String)request.getAttribute("flagstr");
	%>
	<%String msg=(String)request.getAttribute("msg"); 
	System.out.println("msg=============>>>"+msg);
	if(msg!=null){
	%>
	<font color="red"><%=msg %></font>
	<br><br>
	<%} %>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/OCReport?pageidentity.pageId=5023">
   <table>
   <tr>
   	<html:text property="validateFlag" styleClass="formTextField" size="100" style="color:red"></html:text>
   </tr>
     <tr>
       <td align="left">
       <table>
       <tr>
       	<td><bean:message key="label.other"></bean:message></td>
          <td><html:select property="combo_othercharges" styleClass="formTextFieldWithoutTransparent">
          <html:option value="OverDue">OverDue</html:option>
          <html:option value="Collected">Collected</html:option>
          </html:select>
       </td>
       <td>
          <bean:message key="label.combo_loan"></bean:message></td>
          <td><html:select property="acctype"  styleClass="formTextFieldWithoutTransparent" onblur="return fuc_valchange()">
      			  <html:option value="All Types">All Types</html:option>
         		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      			  </core:forEach>
       	  </html:select>
       </td>
       
     </tr>
     
     
     <tr>
      <td>
       <bean:message key="label.fromacc"></bean:message></td>
       <td><html:text property="from_acno" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'11')"></html:text>
      </td>
     
      <td>
       <bean:message key="label.toacc"></bean:message></td>
       <td><html:text property="to_acno" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'11')"></html:text>
      </td>
     </tr>
     
     
     <tr>
      <td>
       <bean:message key="label.from_date"></bean:message></td>
       <td><html:text property="fromdate" size="10" styleClass="formTextFieldWithoutTransparent" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()"></html:text>
      </td>
    
      <td><bean:message key="label.To_date"></bean:message></td>
      <td><html:text property="todate" size="10" styleClass="formTextFieldWithoutTransparent" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" onchange="properdate(fromdate.value,todate.value)"></html:text>
      </td>
     </tr>
     
     <tr>
       <td>
       	  <html:hidden property="forward" value="error"></html:hidden>
       	  <html:hidden property="sysdate" />
       	  <html:hidden property="testing" styleId="totaltesting"></html:hidden>
       	  <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
          <html:submit onfocus="set('view');" value="View" styleClass="ButtonBackgroundImage">View</html:submit>
          	<td><html:button property="but_print" onclick="set('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
          	 <html:submit value="Clear" styleClass="ButtonBackgroundImage" onclick="callClear()">Clear</html:submit>
          	 <%}else{ %>
          	   <html:submit disabled="true" value="View" styleClass="ButtonBackgroundImage">View</html:submit>
          	<td><html:button property="but_print" disabled="true" styleClass="ButtonBackgroundImage">DownLoad</html:button>
          	 <html:submit value="Clear" styleClass="ButtonBackgroundImage" disabled="true">Clear</html:submit>
          	 <%} %>
   </td>
         
          
       </td>
     </tr>
    </table>
    </td> 
	</tr>
  
   <tr>
   <td>
   <table>
   <tr>
   <td>  
   <div id="table1" style="display:block;overflow:scroll;width:750px;height:300px">

<%if(arraylist!=null){%>
<% System.out.println("m in jsp22222222222"); %>
	<table border="1">
	<tr>
		<td>S.N</td><td>A/cType</td><td>A/cNo</td><td>Name</td><td>Particulats/Narration/Ref No.</td><td>OtherChargeAmount</td>
		</tr>
	
		<%for(int j=0;j<arraylist.size();j++){ %>
		<% obj1=(Object[][])arraylist.get(j); %>
		
		<%if(obj1!=null){%>
		<%
			for(int i=0;i<obj1.length;i++){
		%>
		<tr>
			<%for(int k=0;k<6;k++){ %>
			<td>
			<%if(obj1[i][k]!=null){ %>
			<font color="red"><%=obj1[i][k] %></font>
			<%} %>
			</td>
			<%} %>
		</tr>
		<%}%>
		<%}%>	
		<%} %>

	
	</table>
	<%}%>
	
	<%if(arraylist2!=null){%>
<% System.out.println("m in jsp22222222222"); %>
	<table border="1">
	<tr>
		<td>S.N</td><td>A/cType</td><td>A/cNo</td><td>Name</td><td>Date</td><td>Particulats/Narration/Ref No.</td><td>Debit</td><td>Credit</td><td>Balance</td>
		</tr>
	
		<%for(int j=0;j<arraylist2.size();j++){ %>
		<% obj2=(Object[][])arraylist2.get(j); %>
		
		<%if(obj2!=null){%>
		<%
			for(int i=0;i<obj2.length;i++){
		%>
		<tr>
			<%for(int k=0;k<9;k++){ %>
			<td>
			<%if(obj2[i][k]!=null){ %>
			<font color="red"><%=obj2[i][k]%></font>
			<%} %>
			</td>
			<%} %>
		</tr>
		<%}%>
		<%}%>	
		<%} %>

	
	</table>
	<%}%>
	
	
</td>
	</tr>
	</table>
	
</td>
</tr>
</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
</body>
</html>