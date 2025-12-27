<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Enumeration"%>

<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.share.ShareMasterObject"%>
<html>
<head><title>Dividend Data Entry</title>
       <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Dividend Data Entry</center></h2>
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
      
      function floatonly(eve){
         var cha = event.keyCode


            if ((cha>=48 && cha< 58)|| (cha==46)) {


              
                return true ;
                
            }
            else{
              alert("Please enter numbers only ");
              return false ;
            }
            
        
      };
      
      function numbers_only(eve){
         var cha = event.keyCode


            if (  ( cha  >= 47 && cha < 58  ) ) {


              
                return true ;
                
            }
            else{
              alert("Please enter valid date ");
              return false ;
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
		alert("Please enter a valid day as this year is not a leap year")
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
   
	var dt=document[0].div_dt;
	var dt1=document[0].to_dt;
	if (isDate(dt.value)==false){
	       	
		return false
	}
	if (isDate(dt1.value)==false){
	       	
		return false
	}
	
    return true
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

 <body class="Mainbody" onload="fun_validate()">

 <%!
   Hashtable hash_type,hash_div;
   String[] sh_type,div_paymode,paymode,details;
   ModuleObject[] array_module;
   ShareMasterObject shobj;
   String name;
 %>

<% 
   shobj=(ShareMasterObject)request.getAttribute("name");
   name=(String)request.getAttribute("acname");
   String message=(String)request.getAttribute("msg");
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
 <html:form action="/Share/DividendEntry?pageId=4029" focus="<%=""+request.getAttribute("focusto")%>">
 <html:hidden property="validations"></html:hidden>
 <core:if test="<%=message!=null&&message.length()>0%>">
    <font color="red"><%=message %></font>
    </core:if>
    <br><br>
 <center>
 <table class="txtTable">
  <tr>
       <td><bean:message key="label.acType"></bean:message></td>
        <td>
         <html:select property="acctype" styleClass="formTextFieldWithoutTransparent">
           <html:option value="1001001">SH</html:option>
         </html:select>
        </td>
  </tr>
  
  <tr>
   <td><bean:message key="lable.accno"></bean:message></td>
   <td><html:text property="acno" styleClass="formTextFieldWithoutTransparent" size="6"  onkeypress="return numbersonly()"  onblur="validfn(acno.value,'Share Number')" onchange="submit()" onkeyup="numberlimit(this,'11')"></html:text></td>
   <core:choose>
    <core:when test="${requestScope.name!=null}">
     <td><html:text property="name" styleClass="formTextField" value="<%=""+shobj.getName()%>" readonly="true" ></html:text></td>  
    </core:when>
    <core:otherwise>
     <td><html:text property="name" styleClass="formTextField" ></html:text></td>
    </core:otherwise>
   </core:choose>
  </tr>
  
  <tr>
   <td><bean:message key="label.divdate"></bean:message></td>
   <td><html:text property="div_dt" styleClass="formTextFieldWithoutTransparent" size="10" value="<%=""+request.getAttribute("date")%>" onkeypress="return numbers_only()" onblur="ValidateForm()" onkeyup="numberlimit(this,'11')"></html:text></td>
  </tr>
  
  <tr>
   <td><bean:message key="label.divamt"></bean:message></td>
   <td><html:text property="div_amt" styleClass="formTextFieldWithoutTransparent"   size="8" onkeypress="return floatonly()" onkeyup="numberlimit(this,'11')"></html:text></td>
  </tr>
  
    <tr> 
     <td ><bean:message key="label.pay_type"></bean:message></td>
     <td ><html:select property="pay_mode" styleClass="formTextFieldWithoutTransparent">
     <%hash_div=(Hashtable)request.getAttribute("Divpaymode");%>
     <% System.out.println("Checking here3");
     
     System.out.println("Checking here4");
     %>
     
     <html:option value="SELECT">SELECT</html:option>
     
     <%
        Object div_key;
        Enumeration en_div=hash_div.keys();
        while(en_div.hasMoreElements()){
        	div_key=en_div.nextElement();
        
     %>
     <html:option value="<%=""+hash_div.get(div_key)%>"><%=""+hash_div.get(div_key)%></html:option>
     <%
        }
     %>
     </html:select></td>
     </tr>
     
     <tr>
     <td><bean:message key="label.acType"></bean:message></td>
     <td><html:select property="pay_ac_type" styleClass="formTextFieldWithoutTransparent" disabled="<%=(Boolean)request.getAttribute("Disable")%>" onchange="submit()">
     <%array_module=(ModuleObject[])request.getAttribute("AcTypes");%>
     <core:choose>
     <core:when test="${empty requestScope.ShareDetails}">
     <core:forEach var="pay_ac_types" items="${requestScope.AcTypes}">
     <html:option value="${pay_ac_types.moduleCode}">
     <core:out value="${pay_ac_types.moduleAbbrv}"></core:out>
     </html:option>
     </core:forEach>
     </core:when>
     </core:choose>
     </html:select>
     </td>
   </tr>
   
    <tr><td>
     <bean:message key="label.div_ac_num"></bean:message></td>
     <td><html:text property="pay_ac_no" styleClass="formTextFieldWithoutTransparent" size="6" onkeypress="return numbersonly()"  onchange="submit()" onkeyup="numberlimit(this,'11')" ></html:text></td>
     <core:choose>
       <core:when test="${requestScope.acname!=null}">
         <td><html:text property="acname" styleClass="formTextField" value="<%=""+name%>" readonly="true" ></html:text></td>
       </core:when>
       <core:otherwise>
         <td><html:text property="acname" styleClass="formTextField"  ></html:text></td>
       </core:otherwise>
      </core:choose>
    </tr>
   
   <tr>
    <td>
      <html:hidden property="forward" value="error"></html:hidden> 
      <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
      <html:submit  onclick="set('submit')" styleClass="ButtonBackgroundImage" ><bean:message key="label.submit"></bean:message> </html:submit>
      <%}else{ %>
      <html:submit  onclick="set('submit')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.submit"></bean:message> </html:submit>
      <%} %>
      <html:button property="clear" onclick="clear_fun()" styleClass="ButtonBackgroundImage">Clear</html:button>
    </td>
   </tr>
  
 </table>
 </center>
 </html:form>
  <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>