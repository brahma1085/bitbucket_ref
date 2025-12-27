<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareMasterObject"%>
<html>
<head><title>Dividend Registry</title>
      <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
      
      
      <h2 class="h2">
      <center>Dividend Registry</center></h2>
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
   
	var dt=document[0].date;
	var dt1=document[0].to_dt;
	if (isDate(dt.value)==false){
	       	
		return false
	}
	if (isDate(dt1.value)==false){
	       	
		return false
	}
	
    return true
 }
     
     
     
     
           function validfn(fnm,str){
             var len=fnm.length;
             if(len==0){
               alert("Enter the"+str )
              
             }
           };
     
       function fun(shnum){
       alert(shnum)
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
     
       
    </script>


  </head>

<body class="Mainbody">

  <%!
       ShareMasterObject[] sh_obj=null;
  %>
  
  <% sh_obj=(ShareMasterObject[])request.getAttribute("divregistery"); %>
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
  <html:form action="/Share/DividendRegistry?pageId=4007" focus="<%=""+request.getAttribute("focusto") %>">
   <table class="txtTable">
     <tr>
      <td><bean:message key="label.shdate"></bean:message></td>
      <td><html:text property="date" styleClass="formTextFieldWithoutTransparent"  size="10" onkeypress="return numbers_only()" onblur="ValidateForm()" onkeyup="numberlimit(this,'11')"></html:text></td>
     </tr>
     
     <tr>
      <td><bean:message key="label.actype"></bean:message></td>
       <td><html:select property="comb_branch" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
        <core:forEach var="comb_branch" items="${requestScope.div_reg}">
         <html:option value="${comb_branch}">
          <core:out value="${comb_branch}"></core:out>
         </html:option>
        </core:forEach>      
      </html:select></td>
      
      <td><bean:message key="label.actype"></bean:message>
      <html:select property="individual" disabled="<%=(Boolean)request.getAttribute("Disable2") %>" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
        <core:forEach var="individual" items="${requestScope.div_types}">
         <html:option value="${individual}">
          <core:out value="${individual}"></core:out>
         </html:option>
        </core:forEach>      
      </html:select>
      </td>
      
      <td><bean:message key="label.branch"></bean:message></td>
       <td><html:text property="brcode" styleClass="formTextFieldWithoutTransparent" size="4" onkeyup="numberlimit(this,'11')"></html:text>
      </td>
     </tr>
     
     <tr>
      <td>
       <html:hidden property="forward" value="error"></html:hidden>
        <html:submit  onclick="set('view')" styleClass="ButtonBackgroundImage"><bean:message key="label.view1"></bean:message> </html:submit>
        <html:submit onclick="set('clear')" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:submit>
        <html:submit onclick="set('save')" styleClass="ButtonBackgroundImage">DownLoad</html:submit>
        <html:button property="printfile" onclick="window.print()" styleClass="ButtonBackgroundImage" value="Print"></html:button>   
      </td>
     </tr>
  </table>
  
  
   <%if(sh_obj!=null){ %>
   <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table name="divregistery" export="true" id="currentRowObject" class="its" sort="list" pagesize="10" requestURI="/Share/DividendRegistry.do">
   			<!--<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 

  
   
      --><display:column property="shareNumber" ></display:column>
      <display:column property="name" maxWords="2"  ></display:column>
      <display:column property="issueDate" ></display:column>
      <display:column property="numberofShares" ></display:column>
      <display:column property="shareBalance" ></display:column>
      <display:column property="payMode" ></display:column>
      <display:column property="paymentAccno" ></display:column>
      <display:column property="branchName" ></display:column>
      <display:column property="divAmount" ></display:column>
      <display:column property="drfAmount" ></display:column>
      
     </display:table>
   <%} %>
  </div>
 </html:form>
  <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>