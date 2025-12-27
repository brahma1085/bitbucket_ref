<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.DividendObject"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<html>
<head><title>Unclaimed Div Notice</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Unclaimed Dividend Notice</center></h2>
      <hr>
      
    
    <script type="text/javascript">
     
     function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
     
     function set(target){
       document.forms[0].forward.value=target
      
       
     };
     
     
     function validfn(fnm,str){
      var len=fnm.length;
      if(len==0){
       alert("Enter the"+str )
       }
     };
     
  

var dtCh= "/";
var minYear=1900;
var maxYear=9999;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strMonth=dtStr.substring(pos1+1,pos2)
     
	var strDay=dtStr.substring(0,pos1)
     
	var strYear=dtStr.substring(pos2+1)
     

	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	
	if (pos1==-1 || pos2==-1){
		alert("The date format should be : dd/mm/yyyy")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Please enter a valid day ")
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Please enter a valid date")
		return false
	}
return true
}

function ValidateForm(){
   
	var dt=document[0].notice_dt;
	
	if (isDate(dt.value)==false){
	       	
		return false
	}
	
	
    return true
 }
 
 function set(target){
       document.forms[0].forward.value=target
        };
        
 function numbersonly(eve){
         var cha = event.keyCode

        
            if (  ( cha  >= 47 && cha < 58  ) ) {
         
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
     
      };
      
       function fun_validate(){
        var val=document.forms[0].validations.value;
       if(val!=0 || val!=""){
         alert(val); 
      
       } 
       else{
         return false;
       }
      };
       
    </script>
  
  </head>
<body class="Mainbody" onload="fun_validate()" style="overflow: scroll;">
<%!
    List divobj;
    String[] template=null;
    String data=null;
%>
<%
   divobj=(ArrayList)request.getAttribute("divnotice");
   template=(String[])request.getAttribute("Template");
   System.out.println("After template-------");
   data=(String)request.getAttribute("data");
   System.out.println("After data--------");
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
<html:form action="/Share/UnClaimedDividendNotice?pageId=4015">
<html:hidden property="validations"></html:hidden>
<table class="txtTable">
 <tr>
  <td>
    <bean:message key="label.acType"></bean:message>
  </td>
       
       <td>
        <html:select property="actype" styleClass="formTextFieldWithoutTransparent">
           <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}">
            <html:option value="${acType.moduleCode}">
              <core:out value="${acType.moduleAbbrv}"></core:out>
            </html:option>
          </core:forEach>
        </html:select>
       </td>
        
  <td><bean:message key="label.notice"></bean:message></td>
    <td><html:text property="notice_dt" styleClass="formTextFieldWithoutTransparent" size="10" value="<%=""+request.getAttribute("date") %>"  onblur="ValidateForm()" onkeypress="return numbersonly()" onkeyup="numberlimit(this,'11')"></html:text></td>
     <html:hidden property="forward" value="error"></html:hidden> 
       <td> <input type="submit" value="View" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=submit'" /></td>
    <td><html:submit styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:submit></td>   
 </tr>
      
</table>
<table align="left" class="txtTable">
<tr>
<td>
    <%if(divobj!=null){ %>
     <jsp:include page="/DivNotice.jsp"></jsp:include>
 	<%
 	System.out.println("SURAJ IS HERE");
    }
     %>
 </td>
 </tr>
 
  <tr>
   <td>
     <bean:message key="label.temp"></bean:message>
      <html:select property="template" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
      <%
        for(int i=0;i<template.length;i++){
      %>
      <html:option value="<%=""+i%>"><%=""+template[i]%></html:option>
      <%} %>
     </html:select>
   </td>
  </tr>
  
  <tr>
    <%
      if(data!=null){
    %>
     <td>
       <html:textarea property="content"  styleClass="formTextFieldWithoutTransparent" value="<%=""+data %>" cols="100" rows="15"></html:textarea>
     </td>
    <%
      }else{
    %>
    <td>
      <html:textarea property="content" styleClass="formTextFieldWithoutTransparent"></html:textarea>
    </td>
    <%
       } 
    %>
  </tr>
   
  <tr>
   <td>
   <%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
    <input type="submit" value="Update" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Update'" />
    <%}else{ %>
    <input type="submit" value="Update" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Update'" disabled="disabled"/>
    <%} %>
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
    <input type="submit" value="Delete" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Delete'" />
    <%}else{ %>
    <input type="submit" value="Delete" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Delete'" disabled="disabled"/>
    <%} %>
  </td>
 </tr>
 </table>
 
</html:form>
 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>

</html>