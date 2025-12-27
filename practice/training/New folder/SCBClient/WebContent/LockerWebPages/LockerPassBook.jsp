<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
    <%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
    <%@ taglib prefix="display"  uri="http://displaytag.sf.net/el" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.lockers.LockerMasterObject"%>
<%@page import="masterObject.lockers.LockerTransObject"%>
<html>		
<head>

<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    
 

<script type="text/javascript">

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
function only_numbers_date()
	{
		var cha =   event.keyCode;
		 if (cha >=47 && cha <=57) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};

function onlynumbers()
 {
       var cha =   event.keyCode;
		if ( cha >= 48 && cha <= 57  )
        {
           return true;
        } 
        else 
        {
        	return false;
        }
			   		 
 }
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

 
 
        
 function properdate(frm_dt,to_dt){
  
  
  var dtCh="/";
   
  var pos1=frm_dt.indexOf(dtCh)
  var pos2=frm_dt.indexOf(dtCh,pos1+1)
  var frmMonth=frm_dt.substring(pos1+1,pos2)
  var frmDay=frm_dt.substring(0,pos1)
  var frmYear=frm_dt.substring(pos2+1)
  
  
  var pos3=to_dt.indexOf(dtCh)
  
  var pos4=to_dt.indexOf(dtCh,pos3+1)
  
  var ToMonth=to_dt.substring(pos3+1,pos4)
  
  var ToDay=to_dt.substring(0,pos3)
  
  var ToYear=to_dt.substring(pos4+1)
  
  
  
  if(frmYear>ToYear){
    document.forms[0].verifyInd.value="From Year is greater than To Year!!!! Enter valid date";
  }
  else if(frmMonth>ToMonth && frmYear<=ToYear ){
    document.forms[0].verifyInd.value="From Month is greater than To Month !!!! Enter valid date";
  }
 else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  document.forms[0].verifyInd.value="From day is greater than To day !!!! Enter valid date";
  }
 };


function focusAcnt()
{
	document.forms[0].flag.value="1";
	document.forms[0].submit();
}

function storeFile(target)
{
	document.forms[0].flag.value=target;
	document.forms[0].submit();

}


function set(target)
{
	if(document.forms[0].acntNum.value=='')
	{
		document.forms[0].verifyInd.value="Enter Valid Locker A/c Number";
		
	}
	else if(document.forms[0].fromDate.value=='')
	{
		document.forms[0].verifyInd.value="Enter Valid From Date";
	}
	else if(document.forms[0].toDate.value=='')
	{
		document.forms[0].verifyInd.value="Enter Valid To Date";
	}
	else
	{
		document.forms[0].forward.value=target;
		document.forms[0].submit();
	}
};



function callClear()
{
    var ele= document.forms[0].elements;
           
    for(var i=0;i<ele.length;i++)
    {
       if(ele[i].type=="text")
       {
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



</script>
</head>
<body bgcolor="#f3f4c8"  class="Mainbody">
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
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/LKPassBookLink?pageId=9011">
 <center><h2 class="h2"><bean:message key="lable.passbook"></bean:message></h2></center>
<%!LockerMasterObject  lockerMasterObject; %>
<%!LockerTransObject lockerTransObject[]; %>

<%lockerMasterObject=(LockerMasterObject)request.getAttribute("details"); %>
<%lockerTransObject= (LockerTransObject[])request.getAttribute("tabDetails"); %>


<html:hidden property="flag"></html:hidden>
<html:hidden property="forward"></html:hidden>
<html:hidden property="sysdate" />

<table>
<tr><html:text property="verifyInd" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>

<tr>

<td>
<table class="txtTable">
<tr>
<td><bean:message key="lab.lockerAcNum"></bean:message></td>
<td><html:text property="acntNum" onblur="set('actnum')" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'10')" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
<td><bean:message key="label.from_date"></bean:message></td>  <td><html:text property="fromDate" value="<%=(String)request.getAttribute("fromDate")%>" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()"  onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><bean:message key="label.to_date"></bean:message></td>    <td><html:text property="toDate"  value="<%=(String)request.getAttribute("toDate")%>" onblur="return datevalidation(this)" onchange="properdate(fromDate.value,toDate.value)" onkeypress="return only_numbers_date()"  onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<tr><td> <html:button property="view" onclick="set('view')" styleClass="ButtonBackgroundImage"><bean:message key="label.view"></bean:message></html:button> </td>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

 <td><html:button property="clear" styleClass="ButtonBackgroundImage" onclick="callClear()"><bean:message key="label.clear"></bean:message></html:button></td>
<td><html:button property="store" styleClass="ButtonBackgroundImage" onclick="storeFile('File')"><bean:message key="label.file"></bean:message></html:button></td>
</tr>


<%if(lockerMasterObject!=null){ %>
<tr><td><bean:message key="label.cust"></bean:message></td>
<td><html:text property="custId" value="<%=""+lockerMasterObject.getCid() %>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text> </td> </tr>


<%if(lockerMasterObject.getCloseDate()!=null){ %>

<font color="Red"><b>This Account Number Closed  :</b></font> <%lockerMasterObject.getCloseDate(); %>
<%}else{ %>
<font color="Green"><b> Active </b> </font>
<%} %>
<tr><td><bean:message key="label.custname" ></bean:message></td>
<td><html:text property="custName" value="<%=lockerMasterObject.getName() %>" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text> </td></tr>

<tr> 
	<td> Locker Type: </td>
  	<td><html:text property="lockerType" value="<%=lockerMasterObject.getLockerType() %>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
</tr>
   
<tr> 
	<td>Locker number:</td> 
 	<td><html:text property="lockerNum" value="<%=String.valueOf(lockerMasterObject.getLockerNo()) %>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td> </tr>
	<%}else{ %>
<tr>
	<td><bean:message key="label.cust"></bean:message></td>
	<td><html:text property="custId" styleClass="formTextFieldWithoutTransparent" readonly="true" ></html:text> </td> </tr>

<tr>
	<td><bean:message key="label.custname"></bean:message></td>
	<td><html:text property="custName" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text> </td></tr>

<tr> 
	<td> Locker Type: </td>
  	<td><html:text property="lockerType" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>  </td>
</tr>
   
<tr> 
	<td>Locker number:</td> 
 	<td><html:text property="lockerNum" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>  </td> </tr>

<%} %>
</table>
</td>
</tr>
</table>
<display:table name="tabDetails" style="background-color:#E4D5BE" pagesize="8" requestURI="/LKPassBookLink.do">  
 
<display:column property="opDate" style="background-color:#F2F0D2; font:Garamond"></display:column>
<display:column property="operatedBy" style="background-color:#F2F0D2; font:Garamond"></display:column>
<display:column property="timein" style="background-color:#F2F0D2; font:Garamond"></display:column>
<display:column property="timeout" style="background-color:#F2F0D2; font:Garamond"></display:column>
<display:column property="rentBy" style="background-color:#F2F0D2; font:Garamond"></display:column>
<display:column property="transAcType" style="background-color:#F2F0D2; font:Garamond"></display:column>
<display:column property="transAcNo" style="background-color:#F2F0D2; font:Garamond"></display:column>
<display:column property="rent_upto" style="background-color:#F2F0D2; font:Garamond"></display:column>
<display:column property="rent_coll" style="background-color:#F2F0D2; font:Garamond"></display:column>
</display:table>

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>