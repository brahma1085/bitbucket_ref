<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@page import="java.util.Map"%>
<html:html>
 <font color="blue" ><center>
<h2 class="h2">Address Ammendments</h2></center></font>
<head>
<script type="text/javascript">
function beforesubmit(){
document.forms[0].hid.value='Hidden';
document.forms[0].submit();
}
function atsubmit(){
if(document.forms[0].cid.value=="")
{
alert("Please Enter Cid");
 document.forms[0].cid.focus();
 return false;
}
if(document.forms[0].cid.value==0)
{
 alert("Please Enter Cid");
 document.forms[0].cid.focus();
 return false;
}
else{
document.forms[0].sub.value='submit';
document.forms[0].submit();
}
}

function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57) ) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) ) {
				return true ;
                
            	}
            else{
              	alert("Please enter the numbers only ");
              	return false ;
            }
        };  	
	function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) ) 
 		{
   			return true;
       	} else 
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
   function email_check() {
		
		var str=document.forms[1].mailid.value;
		var at="@"
		var dot="."
		var lat=str.indexOf(at)
		var lstr=str.length
		var ldot=str.indexOf(dot)
		if (str.indexOf(at)==-1){
		   alert("Invalid E-mail ID")
		   return false
		}

		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   alert("Invalid E-mail ID")
		   return false
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		    alert("Invalid E-mail ID")
		    return false
		}

		 if (str.indexOf(at,(lat+1))!=-1){
		    alert("Invalid E-mail ID")
		    return false
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    alert("Invalid E-mail ID")
		    return false
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
		    alert("Invalid E-mail ID")
		    return false
		 }
		
		 if (str.indexOf(" ")!=-1){
		    alert("Invalid E-mail ID")
		    return false
		 }

 		 return true					
	};
	function number_valid(c)
	{
		var d=c.value;
		if(d.length<10 || d.length>10)
		{
		
			c.value=""
			c.focus;
			alert("plz enter the valid number");
			return false;
			
		}
		else
		{
			return true;
		}
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
    else if(ele[i].type=="textarea")
    {
    ele[i].value="";
    }
    
    }
   
    
    }
</script>

</head>
<%!
Map user_role;
String access;
%>
<%String msg=(String)request.getAttribute("msg"); %>
<%String value=(String)request.getAttribute("HideAddresstype");
if(value!=null)
System.out.println("value in jsp=====> "+value);
%>
<%
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
<html:form action="/adderss?pageId=1009">
<html:hidden property="hid"></html:hidden>
<html:hidden property="sub"></html:hidden>
<%if(msg!=null){ %>
<font color="red"><%=msg %></font>
<br><br>
<%} %>
<table class="txtTable" cellspacing="0">
<tr><td>Cid:</td><td><html:text property="cid" onblur="beforesubmit()" onkeypress=" return numbersOnly()"></html:text></td></tr>
<tr><td>Udate:</td><td><html:text property="udate" ></html:text></td></tr>
<%if(value==null){ %>
<tr><td>Residential Address:</td><td><html:textarea property="residential"></html:textarea></td>
<td>City</td><td><html:text property="city" size="7" onkeypress="return only_alpha()"></html:text></td>
<td>State</td><td><html:text property="state" size="7" onkeypress="return only_alpha()"></html:text></td>
<td>Pin</td><td><html:text property="pin" size="7" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
<td>Country</td><td><html:text property="country" size="7" onkeypress="return only_alpha()"></html:text></td>
<td>PhoneNo</td><td><html:text property="phno" size="7" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" ></html:text></td>
</tr>
<tr><td>Communication Address</td><td><html:textarea property="communication" ></html:textarea></td>
<td>City</td><td><html:text property="comcity" size="7" onkeypress="return only_alpha()"></html:text></td>
<td>State</td><td><html:text property="comstate" size="7" onkeypress="return only_alpha()"></html:text></td>
<td>Pin</td><td><html:text property="compin" size="7" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
<td>Country</td><td><html:text property="comcountry" size="7" onkeypress="return only_alpha()"></html:text></td>
<td>PhoneNo</td><td><html:text property="comphno" size="7" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" ></html:text></td>
</tr>
<%}%>
<tr><td>Office Address</td><td><html:textarea property="office" ></html:textarea></td>
<td>City</td><td><html:text property="offcity" size="7" onkeypress="return only_alpha()"></html:text></td>
<td>State</td><td><html:text property="offstate" size="7" onkeypress="return only_alpha()"></html:text></td>
<td>Pin</td><td><html:text property="offpin" size="7" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
<td>Country</td><td><html:text property="offcountry" size="7" onkeypress="return only_alpha()"></html:text></td>
<td>PhoneNo</td><td><html:text property="offphno" size="7" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" ></html:text></td>
</tr>
<tr><td>PhoneSTD</td><td><html:text property="phstd" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" ></html:text></td></tr>
<tr><td>Fax</td><td><html:text property="fax" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text></td></tr>
<tr><td>FaxSTD</td><td><html:text property="faxstd" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text></td></tr>
<tr><td>MOBILE</td><td><html:text property="mobile" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" ></html:text></td></tr>
<tr><td>Email</td><td><html:text property="email" onchange="return email_check()"></html:text></td></tr>
<tr><td>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
<html:button property="but" value="Submit" onclick="atsubmit()" styleClass="ButtonBackgroundImage"></html:button> 
<%}else{ %>
<html:button property="but" value="Submit" onclick="atsubmit()" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
<%} %>
</td>
<td><html:button  property="b4" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button></td>
</tr>
</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</html:html>