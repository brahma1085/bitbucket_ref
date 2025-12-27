 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="masterObject.general.ModuleObject"%>

<%@page import="masterObject.backOffice.ClosingBalObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ClosingBalanceReport</title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />      
<script language="javascript">
     
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
   
	var dt=document[0].date;
	
	if (isDate(dt.value)==false){
	       	
		return false
	}
	
	
    return true
 };
 
 function properdate(fromdate,todate){
  
  
  var dtCh="/";
   
  var pos1=fromdate.indexOf(dtCh)
  var pos2=fromdate.indexOf(dtCh,pos1+1)
  var frmMonth=fromdate.substring(pos1+1,pos2)
  var frmDay=fromdate.substring(0,pos1)
  var frmYear=fromdate.substring(pos2+1)
  
  
  var pos3=todate.indexOf(dtCh)
  
  var pos4=todate.indexOf(dtCh,pos3+1)
  
  var ToMonth=todate.substring(pos3+1,pos4)
  
  var ToDay=todate.substring(0,pos3)
  
  var ToYear=todate.substring(pos4+1)
  
  
  
  if(frmYear>ToYear){
    alert("From Year is greater than To Year!!!! Enter valid date")
  }
  else if(frmMonth>ToMonth && frmYear<=ToYear ){
    alert("From Month is greater than To Month !!!! Enter valid date")
  }
 else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  alert("From day is greater than To day !!!! Enter valid date")
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
    
 function numbersonly_date(eve){
         var cha = event.keyCode

            if (  ( cha  >= 47 && cha < 58  ) ) {
              
                return true ;
                            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
         
        
      }; 
     
      
      //submitting
       function set(target){
           
       document.forms[0].forward.value=target;
      
       if(document.getElementById("valid").value!=null)
       {
       	if(document.getElementById("valid").value=="RecordsNotFound")
       	{
         	alert("Records Not Found");
        }
       }    
           
          };
          
       
       //clearing form
        function clears(){
        
         	    } ;  
         	    
       //records validation    
       function CallMe(){
      
       if(document.getElementById("valid").value!=null)
       {
       	if(document.getElementById("valid").value=="Recordsnotfound")
       	{
         	alert("Records Not Found");
        } 
       }
    
       }; 
           	    
      </script> 

</head>
<body class="Mainbody" style="overflow:scroll;" >
<%! String date; %>
<% date=(String)request.getAttribute("date");
 System.out.println("Date------>"+date);
 ModuleObject[] mod=(ModuleObject[])request.getAttribute("acctype");
 System.out.println("mod------>"+mod);
 ClosingBalObject[] closebalsum_sb=(ClosingBalObject[])request.getAttribute("SBrep");
 System.out.println("reportforSB------------->"+closebalsum_sb);
 ClosingBalObject[] closebalsum_sh=(ClosingBalObject[])request.getAttribute("SHrep");
 System.out.println("reportforSH------------->"+closebalsum_sh);
 ClosingBalObject[] closebalsum_dep=(ClosingBalObject[])request.getAttribute("TDrep");
 System.out.println("reportforDep------------->"+closebalsum_dep);
 ClosingBalObject[] closebalsum_pd=(ClosingBalObject[])request.getAttribute("PDrep");
 System.out.println("reportforPD------------->"+closebalsum_pd);
 ClosingBalObject[] closebalsum_ln=(ClosingBalObject[])request.getAttribute("Lnrep");
 System.out.println("reportforLn------------->"+closebalsum_ln);
 ClosingBalObject[] closebalsum_lnd=(ClosingBalObject[])request.getAttribute("LnDrep");
 System.out.println("reportforLnD------------->"+closebalsum_lnd);
%>
<% String displaymsg=(String)request.getAttribute("displaymsg");%>
<%if(displaymsg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=displaymsg %></font>
<%} %>
<center><h2 class="h2">ClosingBalanceReport</h2></center>
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
<html:form action="/BackOffice/ClosingBalanceSummary?pageId=11013">

<table class="txtTable">
 	<tr>
 	    <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 	    <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
		<td><bean:message key="label.acType" ></bean:message></td>
			
		<td><html:select property="acctype" size="3"  onchange="submit()">
                <% for(int i=0;i<mod.length;i++){%>	 	
				<html:option value="<%=mod[i].getModuleCode()%>" ><%=mod[i].getModuleAbbrv()%></html:option>
 		         <%} %>
			</html:select></td>
			<td style="visibility: <%=""+request.getAttribute("vis") %>">
			
			<html:text property="accabbrv" size="20" value="<%=""+request.getAttribute("moddesc")%>" style="border:none;background:transparent;color:brown" readonly="true" ></html:text>
      </td>
		</tr>
		</table>
		<br>
<center>
	<table>	
      <tr>
		<td><bean:message key="label.date"></bean:message><html:text property="date" size="10"  onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td></td>
		
     </tr>
</table>
<br><br>

		
<table class="txtTable">	
	<tr>
	    
	     <html:hidden property="forward" value="error"></html:hidden>
	     <html:hidden property="valid" styleId="valid"></html:hidden>
	     <html:hidden property="sysdate" />
	     
	     
	   <td>
	   <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
		  <html:submit  onclick="set('view')" styleClass="ButtonBackgroundImage"><bean:message key="label.view1"></bean:message> </html:submit>
		  <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
          <!--<html:button  onclick="window.print()" property="printFile" styleClass="ButtonBackgroundImage"><bean:message key="label.print" ></bean:message> </html:button>-->
		  <html:submit onclick="set('clear')" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:submit>
		  <html:submit onclick="set('save')" styleClass="ButtonBackgroundImage">DownLoad</html:submit>
		
		</td>  
	</tr>
</table>
<table class="txtTable">
<tr>
<td>
    
</tr>
</table>
<br>
<%if(closebalsum_sb!=null){ %>

 <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table name="SBrep" export="true" id="currentRowObject" class="its" sort="list" pagesize="10" requestURI="/BackOffice/ClosingBalanceSummary.do">
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 

   
   <display:column title="A/C No"   property="acNo"       	style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B"      property="clBal" 		style="background-color:#f5dfc9"></display:column>
     

</display:table>
</div>
<%} %>

<% if(closebalsum_sh!=null){ %>

   <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table name="SHrep" export="true" id="currentRowObject" class="its" sort="list" pagesize="10">
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 

   
   <display:column title="A/C No"   property="acNo"       	style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B"      property="clBal" 		style="background-color:#f5dfc9"></display:column>
     

</display:table>
</div>
<%} %>
<% if(closebalsum_dep!=null){ %>

   <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table name="TDrep" export="true" id="currentRowObject" class="its" sort="list" pagesize="10">
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 

   
   <display:column title="A/C No"   property="acNo"       	style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B"      property="clBal" 		style="background-color:#f5dfc9"></display:column>
     

</display:table>
</div>
<%} %>
<% if(closebalsum_pd!=null){ %>

   <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table name="PDrep" export="true" id="currentRowObject" class="its" sort="list" pagesize="10">
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 

   
   <display:column title="A/C No"   property="acNo"       	style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B"      property="clBal" 		style="background-color:#f5dfc9"></display:column>
     

</display:table>
</div>
<%} %>
<% if(closebalsum_lnd!=null){
	System.out.println("----->$$$<in deposit-------");
	%>

   
   <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table name="LnDrep" export="true" id="currentRowObject" class="its" sort="list" pagesize="10">
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 

   <display:column title="A/C No"   property="acNo"       	style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B"      property="clBal" 		style="background-color:#f5dfc9"></display:column>
     

</display:table>
</div>
<%} %>
<% if(closebalsum_ln!=null){
	
	System.out.println("----->$$$<in loan-------");
	%>

   <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table name="Lnrep" export="true" id="currentRowObject" class="its" sort="list" pagesize="10">
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 

   
   <display:column title="A/C No"   property="acNo"       	style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B"      property="clBal" 		style="background-color:#f5dfc9"></display:column>
     

</display:table>
</div>
<%} %>


</center>
	
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>