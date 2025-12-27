<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>  
<%@page import="java.util.Map"%>
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>RBI Form I & IX Admin </title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Preceding Marking Date Entry </center></h2>
      <hr>
      
    
    <script type="text/javascript">
     function set(target){
       document.forms[0].forward.value=target
      };
     
      function check_acnt(){
      
        if(document.getElementById("ac").value=='T'){
          alert("Account not found")
           document.forms[0].acno.value="";
        }
      };
      
      function addRow(){
      alert("hi starting addrow");
      var tbl = document.getElementById('MarkingDateEntryTable');
      var lastRow = tbl.rows.length;
      var iteration = lastRow;
var itr=iteration-1; 
      
    var row = tbl.insertRow(lastRow);
    alert("hi starting addrow----1");
      var cellRight = row.insertCell(0);
var el = document.createElement('input');
el.setAttribute('type', 'text');
el.setAttribute('name', 'txtRow1');
el.setAttribute('size', '30');
cellRight.appendChild(el);
      alert("hi starting addrow------2");
  var cellRight = row.insertCell(1);
var e2 = document.createElement('input');
e2.setAttribute('type', 'text');
e2.setAttribute('name', 'txtRow2');
e2.setAttribute('size', '30');
cellRight.appendChild(e2); 
var cellRight = row.insertCell(2);
var e3 = document.createElement('input');
e3.setAttribute('type', 'text');
e3.setAttribute('name', 'txtRow3');
e3.setAttribute('size', '30');
cellRight.appendChild(e3); 

var cellRight = row.insertCell(3);
var e4 = document.createElement('input');
e4.setAttribute('type', 'text');
e4.setAttribute('name', 'txtRow4');
e4.setAttribute('size', '30');
cellRight.appendChild(e4);

var cellRight = row.insertCell(4);
var e5 = document.createElement('input');
e5.setAttribute('type', 'text');
e5.setAttribute('name', 'txtRow5');
e5.setAttribute('size', '30');
cellRight.appendChild(e5);
var cellRight = row.insertCell(5);
var e6 = document.createElement('input');
e6.setAttribute('type', 'text');
e6.setAttribute('name', 'txtRow6');
e6.setAttribute('size', '30');
cellRight.appendChild(e6);
alert("hi starting addrow------.3");
var cellRight = row.insertCell(6);
var e7 = document.createElement('input');
e7.setAttribute('type', 'text');
e7.setAttribute('name', 'txtRow7');
e7.setAttribute('size', '30');
cellRight.appendChild(e7);
 alert("hi starting addrow------4");
var cellRight = row.insertCell(7);
var e8 = document.createElement('input');
e8.setAttribute('type', 'radio');
e8.setAttribute('name', 'selcted');
cellRight.appendChild(e8);   
      
      }
        
   function clear_fun()
    {
    	
    	var ele=document.forms[0].elements;
    	document.getElementById("forward").style.display='none';  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    
    function numbersonly(eve){
         var cha = event.keyCode
            if (  ( cha  > 47 && cha < 58  ) ) {
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
      };
     
     
     function validate(){
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         alert(val);
       } 
       
       else{
         return false;
       }
     }; 
     
     function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%!
String[] form19master=null;
String[][] tabledata=null;
int tablelength=0;
int i;

%>
<%String result=(String)request.getAttribute("result");
String nothing=(String)request.getAttribute("Nothing");
 String addrow=(String)request.getAttribute("addnewrow");
 tabledata=(String[][])request.getAttribute("MarkingDateEntry");
 String msg=(String)request.getAttribute("msg");
 %>

 <%if(msg!=null){ %>
  <font color="red"><%=msg %></font>
  <br><br>
<%} %>
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
<html:form action="/GL/GLRBIMarkingDateEntry?pageId=12026">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
     
   
   <tr>
     <td>
        <html:button property="refresh" onclick="setFlag('refresh') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.fetch"></bean:message> </html:button>
        <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
        <html:button  property="addRow" onclick="setFlag('insert')" styleClass="ButtonBackgroundImage"><bean:message key="label.add.row"></bean:message> </html:button>
        <!--<html:submit  onclick="setFlag('update') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.update"></bean:message> </html:submit>
        <html:submit  onclick="setFlag('delete') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.delete"></bean:message> </html:submit>
        --><html:button property="submitForm" onclick="setFlag('submit') " styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:button>
        <%}else{ %>
        <html:button  property="addRow" onclick="setFlag('insert')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.add.row"></bean:message> </html:button>
        <html:button property="submitForm" onclick="setFlag('submit') " styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.submit"></bean:message> </html:button>
        <%} %>
        <!--<html:submit  onclick="setFlag('next') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.next"></bean:message> </html:submit>
        --><html:button property="exitForm"  onclick="setFlag('exit') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.exit"></bean:message></html:button>
     </td>
   </tr>
   
 </table>
 <%
 if(nothing!=null){%>
	<table bgcolor="white">
	<tr>
	  <td colspan="4" style="font-family:bold;color:red"><%=nothing %></td>
	</tr>
	</table> 
<% 	 
 }
 
 %>
 <%if(tabledata!=null ){ %>
 <table border="1" bordercolor="blue" id="markingDateEntryTable">
 <tr>
 <td><b>Day</b></td>
 <td><b>Marking Date</b></td>
 <td><b>Recurring Days</b></td>
 <td><b>NDTL Amount</b></td>
 <td><b>De Tml</b></td>
 <td><b>De User</b></td>
 <td><b>De Date&Time</b></td>
 <td><b>Update</b></td>
 </tr>
 <%
 
	 
	 for(int j=0;j<tabledata.length;j++){i=1;%><tr>
	 <%for(int k=0;k<7;k++){	 
%>
 
 
 <td><input type="text" name="txtRow<%=i++%>" value="<%=tabledata[j][k] %>" class="formTextField">
 </td>
 <%}
	 
	 %>
 <td><html:radio property="selcted" value=""></html:radio>
 </td>
 
 
 <%}
	 i=1;
 %>
 </tr><%
	 }
	 %>
	 <%
	 if(addrow!=null){%>
	 <tr>
	 <%
		 for(int i=0;i<7;i++){
	 
	 %>
	 
 <td style="color: red;" class="formTextField"><b><input type="text"  name="txtFld"></b></td>
 
 
 <%
		 
	 }
 %>
 <td><html:radio property="selcted" value=""></html:radio>
 </td>
</tr>
 <%
		 
	 }
 %>	 
 </table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>