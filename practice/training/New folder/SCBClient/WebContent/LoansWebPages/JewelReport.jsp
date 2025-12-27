<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib  prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib  prefix="bean" uri="http://struts.apache.org/tags-bean" %>  
<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Vector"%>

<html>
<head>
<script type="text/javascript">
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
        };
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
}  ;
function properdate(txt_fromdate,to_date){
  
  
  var dtCh="/";
   
  var pos1=txt_fromdate.indexOf(dtCh)
  var pos2=txt_fromdate.indexOf(dtCh,pos1+1)
  var frmMonth=txt_fromdate.substring(pos1+1,pos2)
  var frmDay=txt_fromdate.substring(0,pos1)
  var frmYear=txt_fromdate.substring(pos2+1)
  
  
  var pos3=txt_To_date.indexOf(dtCh)
  
  var pos4=txt_To_date.indexOf(dtCh,pos3+1)
  
  var ToMonth=txt_To_date.substring(pos3+1,pos4)
  
  var ToDay=txt_To_date.substring(0,pos3)
  
  var ToYear=txt_To_date.substring(pos4+1)
  
  
  
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
 

function set(target){
 document.forms[0].forward.value=target;
  document.forms[0].submit();
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
<h2 class="h2"><center>Jewel Report</center></h2>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body class="Mainbody">
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
<%String message=(String)request.getAttribute("msg"); %>

<%if(message!=null){ %>
<div id = "div1" class = "myLayersClass">
    
    <core:if test="<%=message!=null %>">
    <font color="red"><%=message %></font>
    <br><br><br><br>
    </core:if>
    </div>
<%} %>

 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
  
<html:form action="/Loans/JewelReport?pageidentity.pageId=5037">

<table>
		<tr>
		<td>
		<table>
		
			<tr>
			<td><bean:message key="label.from_date"></bean:message></td>
			<td><html:text property="txt_fromdate" size="10" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" ></html:text></td>
			
			
			<td><bean:message key="label.To_date"></bean:message></td>
			 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
			<td><html:text property="txt_To_date" size="10" readonly="true" onchange="properdate(txt_fromdate.value,txt_To_date.value)" onclick="submit()" value="<%=""+request.getAttribute("To_date") %>"></html:text></td>
			<%}else{ %>
			<td><html:text property="txt_To_date" size="10" readonly="true" onchange="properdate(txt_fromdate.value,txt_To_date.value)" disabled="true" value="<%=""+request.getAttribute("To_date") %>"></html:text></td>
			<%} %>
			</tr>
			<tr>
			<html:hidden property="testing" styleId="totaltesting"></html:hidden>
  <html:hidden property="forward" value="error"></html:hidden>
  <html:hidden property="sysdate" />
  <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
			<td><html:button property="but_print" onclick="set('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
   <%}else{ %>
   <td><html:button property="but_print" disabled="true" styleClass="ButtonBackgroundImage">DownLoad</html:button>
   </td>
   <%} %>
			</tr>
		</table>	
		</td>	
		</tr>
		<tr>
		<td>
		<div  id = "table1" style="display: block;overflow: scroll;width: 600px;height: 200px">
		           <display:table name="JewelReport" id="JewelReport" class="its"  >
		           <display:column title="Date" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${JewelReport.Date}"  /></display:column>
					<display:column title="AccType and No" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${JewelReport.AccTypeandNo}"  /></display:column>
					<display:column title="Appriser Name" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${JewelReport.AppriserName}"  /></display:column>
					<display:column title="Description" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="formTextField" value="${JewelReport.Description}" /></display:column>
					<display:column title="Gross Weight" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${JewelReport.GrossWeight}" /></display:column>
					<display:column title="Net Weight" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${JewelReport.NetWeight}" /></display:column>
					<display:column title="Rate" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${JewelReport.Rate}" /></display:column>
					<display:column title="Net Gold" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${JewelReport.NetGold}" /></display:column>
		</display:table>
		</div>			
		</td></tr>
</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
</body>
</html>