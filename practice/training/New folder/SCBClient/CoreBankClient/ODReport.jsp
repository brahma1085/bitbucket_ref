<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="com.scb.common.help.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../mages/DMTStyle.css" type="text/css" media="print" />
    <h2 class="h2"><center>OD Report</center></h2>

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
   


 function only_numbers() {
	
	var cha=event.keyCode;
	if(cha>47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  
function only_numbers1() {
	
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

 

      function set(target){
      document.forms[0].forward.value=target;
      document.forms[0].submit();
      };
      
      function clearPage()
 	  { 
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
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
            			alert(" year should be less than or equal to"+dds[2]+" !");
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
        }
    


       
</script>
</head>
<body>

<%ModuleObject object[];
  object = (ModuleObject[])request.getAttribute("LoanModuleCode");
  %>
  
<%String stagecode[][]=null;
   stagecode = (String[][])request.getAttribute("StageCode");%>  
   <%System.out.println("In JSP===========>"+stagecode); %>
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
	ArrayList list =null;

	list = (ArrayList)request.getAttribute("OverdueReport");
%>
	<% String msg=(String)request.getAttribute("msg"); 
	System.out.println("msg=============>>>"+msg);
	
	if(msg!=null){
	%>
	<font color="red"><%=msg %></font>
	<br><br>
	<%} %>
   <%Object[] obj2; %>
   	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/ODReport?pageidentity.pageId=5024">
   <table>
     <tr>
       <td>
       <bean:message key="label.combo_loan"></bean:message></td>
          <td><html:select property="acctype"  styleClass="formTextFieldWithoutTransparent">
      			  <html:option value="All Types">All Types</html:option>
         		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      			  </core:forEach>
       	  </html:select>
       </td>
       
   	  		
       <td>
   	  		<bean:message key="label.stagetype"></bean:message>
      		<html:select property="stagetype" styleClass="formTextFieldWithoutTransparent">
      		<html:option value="All">All</html:option>
      		<%if(stagecode!=null){ %>
      		
      		<%for(int i=0;i<stagecode.length;i++){ %>
      		
      		<html:option value="<%=""+(i+1)%>"><%=stagecode[i][1]%></html:option>
      		<%} %>
      		<%} %>
      		</html:select>
      		
       </td>
       <td>
           <bean:message key="label.reportdate"></bean:message>
           <html:text property="reportdate" size="10"  value="<%=Date.getSysDate()%>" styleClass="formTextFieldWithoutTransparent" onblur="return datevalidation(this)" onkeypress="return only_numbers1()"></html:text>
        </td>
      </tr>
      
      <tr>
        <td>
          <bean:message key="label.fromacc"></bean:message>
          <html:text property="from_accno" size="10" value="1" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text>
        </td>
        
        <td>
          <bean:message key="label.toacc"></bean:message>
          <html:text property="to_accno"  size="10" value="9999999999" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()"></html:text>
        </td>
        
        <td>
          <bean:message key="label.inuptodt"></bean:message>
          <html:text property="intuptodate" size="10" value="<%=Date.getSysDate()%>" styleClass="formTextFieldWithoutTransparent" onblur="return datevalidation(this)" onkeypress="return only_numbers1()"></html:text>
        </td>
       </tr>
       
       <tr>
         <td>
            <bean:message key="label.prnfromamt"></bean:message>
          	<html:text property="prn_fromamt" size="10" value="1" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
         </td>
         
         <td>
            <bean:message key="label.prntoamt"></bean:message>
          	<html:text property="prn_toamt" size="10" value="9999999999" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()"></html:text>
         </td>
       </tr>
       
       
       <tr>
         <td>
            <bean:message key="label.intfromamt"></bean:message>
          	<html:text property="int_fromamt" size="10" value="1" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
         </td>
         
         <td>
            <bean:message key="label.inttoamt"></bean:message>
          	<html:text property="into_toamt" size="10" value="9999999999" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()"></html:text>
         </td>
       </tr>
       
       
       <tr>
         <td>
            <html:hidden property="forward" value="error"></html:hidden>
            <html:hidden property="testing" styleId="totaltesting"></html:hidden>
            <html:hidden property="sysdate" />
            	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
       	    <html:submit onclick="set('view');" value="View" styleClass="ButtonBackgroundImage">View</html:submit>
       	    <td><html:button property="but_print" onclick="set('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
       	      <html:submit property="Clear" onclick="return clearPage()" styleClass="ButtonBackgroundImage" value="Clear"></html:submit>
       	      <%}else{ %>
       	        <html:submit disabled="true" value="View" styleClass="ButtonBackgroundImage">View</html:submit>
       	    <td><html:button property="but_print" disabled="true" styleClass="ButtonBackgroundImage">DownLoad</html:button>
       	      <html:submit property="Clear" disabled="true" styleClass="ButtonBackgroundImage" value="Clear"></html:submit>
       	      <%} %>
   </td>
          
         </td>
       </tr>
       <%if(list!=null){ 
       System.out.println("++++++++++Object++++jsp++++++++" + list.size());%>
       <table border="1">
      
       <tr>
 <th>Account Number</th><th>Name of Borrower</th><th>Share Type</th><th>Share Number</th><th>Amount</th><th>Date</th><th>Amount</th><th>A/C No</th>
 <th>Int Type</th><th>No Int</th><th>Inst Amt</th><th>UptoDate</th><th>Date</th><th>Out Standing</th><th>No</th><th>LastDate</th>
 <th>Principle</th><th>Interest</th><th>PenalInt</th><th>Other Charges</th><th>Total</th><th>Months</th>
       </tr>
       <%
		for(int i=0;i<list.size();i++){
		%>
		<tr>
		<% obj2=(Object[])list.get(i); %>
		
		<%
			for(int j=0;j<obj2.length;j++){
		%>
			<td><font color="red"><%=obj2[j]%></font></td>
		<%}%>
		</tr>
		<%} %>
		
		
	</table>
       
      <%} %> 
       </table>
  </html:form> 
  <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
</body>
</html>