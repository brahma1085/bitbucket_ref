<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.frontCounter.AccountTransObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SB/CA Ledger</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
	
 <script language="javascript">
 
 function set(target)
{
  
  document.forms[0].forward.value=target;
  document.forms[0].submit();

}

          
          
  function enable(){
  alert("enabling txtfield");
  document.forms[0].accno.value=false;
  document.forms[0].fromacno.value=false;
  document.forms[0].toacno.value=false;
  };        
       
  function clears(){
        alert("clearing form");
         	    } ;
         	    
     
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
};

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
};

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
function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only "+size+" digits allowed")
         		txt.value="";
         		return false;
          	}
         }	
function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
};
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
};

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
};

function ValidateForm(){
   
	var dt=document[0].frm_dt;
	var dt1=document[0].to_dt;
	if (isDate(dt.value)==false){
	       	
		return false
	}
	if (isDate(dt1.value)==false){
	       	
		return false
	}
	
    return true
 };
     
      
      
</script>
</head>
<% String displaymsg=(String)request.getAttribute("displaymsg");%>
<%if(displaymsg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=displaymsg %></font>
<%} %>
<body class="Mainbody" style="overflow: scroll;">
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

<center><h2 class="h2">SB/CA Ledger</h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/BackOffice/SBCALedger?pageId=11011">


<%System.out.println("in jsp"); %>
<% String date=(String)request.getAttribute("date");
   String[] select=(String[])request.getAttribute("select");
   ModuleObject[] modcode=(ModuleObject[])request.getAttribute("AccTypes");
   AccountTransObject[] accounttransobject_view=(AccountTransObject[])request.getAttribute("AccTranDetails");
   %>
 <center> 
 <table class="txtTable">
   <tr>
       <td><bean:message key="lable.acctye"></bean:message>
     		<html:select property="accType"  >
     		  <%for(int i=0;i<modcode.length;i++){ %>
	 			<html:option value="<%=modcode[i].getModuleCode()%>"><%=modcode[i].getModuleAbbrv()%></html:option>
	 		<%} %>
     		</html:select></td>
     		
        <td><html:hidden property="fromacno"></html:hidden></td>
		
		<td><bean:message key="label.endac"></bean:message><html:text property="toacno" size="10" onblur="submit()" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'9')" ></html:text></td>	
    </tr>
    </table>
    <br>
    <table class="txtTable">
    
 </table>
 </center>
 <br>
 <center>
 <table align="" style="border:thin solid #339999;">
     <tr>
        <td align="right"><bean:message key="label.acNum"></bean:message><html:text property="accno" size="10" onblur="submit()" onkeypress="return only_numbers()" readonly="true" ></html:text></td>
        <td align="right"><bean:message key="label.accname"></bean:message><html:text property="accname" size="22" onkeypress="return only_numbers()" readonly="true" ></html:text></td>
	</tr>
	<tr>
        <td align="right"><bean:message key="label.cust"></bean:message><html:text property="custid" size="10" readonly="true" ></html:text></td>
        <td align="right"><bean:message key="label.openedon"></bean:message><html:text property="openedon" size="10" readonly="true" ></html:text></td>
        <td align="right"><bean:message key="label.accstatus"></bean:message><html:text property="accstatus" size="12" readonly="true" ></html:text></td>
	</tr>
	<tr>
        <td align="right"><bean:message key="label.acccategory"></bean:message><html:text property="accategory" size="10" readonly="true" ></html:text></td>
        <td align="right"><bean:message key="label.chk_bk_issue"></bean:message><html:text property="chq_bk_issue" size="10" readonly="true"></html:text></td>
	</tr>
	<tr>
      <td align="right"><bean:message key="label.nominee_name"></bean:message><html:text property="nominee_name" size="10" readonly="true" ></html:text></td>
        <td align="right"><bean:message key="label.intpdupto"></bean:message><html:text property="int_pd_upto" size="10" readonly="true" ></html:text></td>
	</tr>
	<tr>
        <td align="right"><bean:message key="label.relationship"></bean:message><html:text property="relationship" size="10" readonly="true" ></html:text></td>
        <td align="right"><bean:message key="label.intrecvupto"></bean:message><html:text property="int_revd_upto" size="10" readonly="true" ></html:text></td>
	</tr>
 </table>
 </center>
 <br>
 <center>
 <table class="txtTable">  
   <tr>
		<html:hidden property="forward" value="error"></html:hidden>
		<html:hidden property="testing" styleId="totaltesting"></html:hidden>
		<td>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	    <!--<html:button onclick="window.print()" property ="printFile"  styleClass="ButtonBackgroundImage"><bean:message key="label.print"></bean:message> </html:button>-->
	    	<html:button property="but_print" onclick="set('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
	    <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
        </td>
	</tr>    
</table>
</center>
<br><br>
<center>
<br>
<div style="overflow:scroll;width:600px;height:300px">

	<display:table name="AccTranDetails" style="background-color:#E4D5BE" requestURI="/BackOffice/SBCALedger.do">
        <%System.out.println("Accoun tran Obj======");
        System.out.println("Accoun tran Obj======"+accounttransobject_view);
        if(accounttransobject_view!=null){%>
	    System.out.println("Accoun tran Obj======"+accounttransobject_view);
         
   		<display:column title="Date"               		property="transDate"   			style="background-color:#F2F0D2"></display:column>
   		<display:column title="Cheque No"          		property="chqDDNo"   		style="background-color:#F2F0D2"></display:column>
   		<display:column title="Narration/PayeeNAme"     property="transNarr"   			style="background-color:#F2F0D2"></display:column>
   		
   		<display:column title="Narration/PayeeNAme"     property="payeeName"   			style="background-color:#F2F0D2"></display:column>
   		
   		<display:column title="Closing Balance"     	property="closeBal" 		        style="background-color:#F2F0D2"></display:column>
   		
   		<display:column title="Debit"           		property="transAmount"   	style="background-color:#F2F0D2"></display:column>
   		<display:column title="Credit"    	   			property="transAmount" 		    style="background-color:#F2F0D2"></display:column>
   		
 <%} %>  		
   
	</display:table>
	</div>
 
</center>
 

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>