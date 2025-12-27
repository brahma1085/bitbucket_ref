<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loansOnDeposit.LoanReportObject"%>

<html>
<head>

<script type="text/javascript">

    function clears()
 	{
 		
 		
 		
		document.forms[0].txt_fromdate.value="";
		document.forms[0].txt_todate.value="";
		
		document.getElementById("table1").style.display='none'; 
		return false;
 	}

 function but_value(target)
 {
	if(document.forms[0].txt_fromdate.value==""  || document.forms[0].txt_fromdate.value==0 )
	{
		
		alert("Enter the from date in the field provided"); 
		return false;
	}
	if(document.forms[0].txt_todate.value=="" || document.forms[0].txt_todate.value==0 )
	{
		alert("Enter the from date in the field provided");
		return false;
	}
 	else
    {
 	
 	document.forms[0].button_value.value=target;
 	document.forms[0].submit();
 	return true;
 	}
 }
 
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
              
              return false ;
            }
         
        
      }; 
 
 function date_validate(ids,ids2)
	{
		var current_field;
		var cha=event.keyCode;
    	var format = true;
    	var ff =  ids.value;
   		var dd = ff.split( '/' );

	if ( dd.length == 3)
	{
	    for ( var i =0; i< dd.length; i++ )
	    {
			 if ( i != 2 && dd[i].length != 2 )
		 	 {
  		     	alert ("Enter correct date format");
  		     	ids.focus();
                format = false ;
                break;
             } 
             else if (  i==2 && dd[i].length != 4 )
			 {
			 	alert (  " problem in date format " );
			 	ids.focus();
                format = false ;
                break;
	         }

        }
    }
    else
    {
       ids.focus();
		format = false ;
    	alert (" problem in date format ");
    }
    var dd1,mm1,yy1,dd2,mm2,yy2;
	
	
	var date1=document.forms[0].txt_fromdate.value;
	var date2=document.forms[0].txt_todate.value;
	

	var dat_split=date1.split('/');
	var dat_split1=date2.split('/');
	

	dd1=dat_split[0];
	mm1=dat_split[1];
	yy1=dat_split[2];
	
	

	dd2=dat_split1[0];
	mm2=dat_split1[1];
	yy2=dat_split1[2];
	
	

	if(yy1>yy2)
	{
	alert("from year can not be greater than to year");
	document.forms[0].txt_fromdate.focus();
	return false;
	}
	else if(mm1>mm2)
	{ 
		alert("from month cannot be greater than to month");
		document.forms[0].txt_fromdate.focus();
		return false;	
	}
	else if(dd1>dd2)
	{ 
		alert("from date cannot be greater than to date");
		document.forms[0].txt_fromdate.focus();
		return false;	
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
  	
  	function isDate(dtStr)
  	{
  	
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
 	var dt=document[0].txt_fromdate;
	
	var dt1=document[0].txt_todate;
	
	if (isDate(dt.value)==false){
	       	
		return false;
	}
		
	if (isDate(dt1.value)==false){
	       	
		return false;
	}
	   
 }
	
	function properdate(txt_fromdate,txt_todate){
  
  
  var dtCh="/";
   
  var pos1=txt_fromdate.indexOf(dtCh)
  var pos2=txt_fromdate.indexOf(dtCh,pos1+1)
  var frmMonth=txt_fromdate.substring(pos1+1,pos2)
  var frmDay=txt_fromdate.substring(0,pos1)
  var frmYear=txt_fromdate.substring(pos2+1)
  
  
  var pos3=txt_todate.indexOf(dtCh)
  
  var pos4=txt_todate.indexOf(dtCh,pos3+1)
  
  var ToMonth=txt_todate.substring(pos3+1,pos4)
  
  var ToDay=txt_todate.substring(0,pos3)
  
  var ToYear=txt_todate.substring(pos4+1)
  
  
  
  if(frmYear > ToYear){
    alert("From Year is greater than To Year!!!! Enter valid date")
  }
  //else if(frmMonth > ToMonth && frmYear<=ToYear ){
    //alert("From Month is greater than To Month !!!! Enter valid date")
  //}
 //else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  //alert("From day is greater than To day !!!! Enter valid date")
  //}
 }; 
 

	
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" /><h2 class="h2"><center>Maturity List</center></h2> </head>

<%!ModuleObject[] LoanACType;%>
<%LoanACType=(ModuleObject[])request.getAttribute("LoanACType");%>

<%! String SystemDate,displaymsg;%>
<%SystemDate=(String)request.getAttribute("SystemDate");%>
<%displaymsg=(String)request.getAttribute("displaymsg"); %>


<body class="Mainbody">
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

<html:form action="/LDMaturityList?pageId=6012">
<html:hidden property="testing" styleId="totaltesting"></html:hidden>
	
	<table class="txtTable">
		<tr>		
	       <td><bean:message key="lable.acctye"/></td>
			<td><html:select property="combo_acctype" styleClass="formTextFieldWithoutTransparent">
				<html:option value="alltype">Alltype</html:option>
			    <%if(LoanACType!=null){%>
			    <%for(int i=0;i<LoanACType.length;i++){%>
                <html:option value="<%=""+i%>"><%=LoanACType[i].getModuleAbbrv()%></html:option>
			    <%}%>
			    <%}%>
	       	  </html:select>
	       </td>
	    </tr>  
		
		
		
		<tr>		
	       <td><bean:message key="label.frm_dt"/></td>
	       <td><html:text property="txt_fromdate" onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)"></html:text></td>
        		
	       <td><bean:message key="label.to_dt"/></td>
	       <td><html:text property="txt_todate" onchange="properdate(txt_fromdate.value,txt_todate.value)" onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)"></html:text></td>
	    </tr> 
		    
		 
	<tr>
	   <table class="txtTable">
	   
	   <html:hidden property="button_value" value="error"></html:hidden>
	   <html:hidden property="sysdate" />
	   <tr>
	   		<td><html:submit styleClass="ButtonBackgroundImage" value="VIEW" onclick="return but_value('View')" ></html:submit></td>
	   		<td></td>
	   		
	   		<td><html:button property="but_print" onclick="but_value('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button></td>
	   		
	   		<td></td>
	   			<td><html:button styleClass="ButtonBackgroundImage" property="clear"  onclick="clears()">CLEAR</html:button></td>
       </tr>
   
   
   
	</table>	
	
			<table>
			<tr>
				<td>
					<core:if test="${requestScope.MatruityList!=null}">
					<div id="table1" style="display: block;overflow: scroll;width: 750px;height: 300px">
					<display:table name="MatruityList" id="MatruityList" class="its" >
						<display:column title="AcType" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${MatruityList.AcType}"  size="5" /></display:column>
						<display:column title="AccNo" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${MatruityList.AccNo}" size="10" /></display:column>
						<display:column title="Name" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${MatruityList.Name}" size="60" /></display:column>
						<display:column title="DepDate" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="formTextField" value="${MatruityList.DepDate}" /></display:column>
						<display:column title="MatDate" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${MatruityList.MatDate}" /></display:column>
						<display:column title="DepAmt" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${MatruityList.DepAmt}" /></display:column>
						<display:column title="MatAmt" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="formTextField" value="${MatruityList.MatAmt}" /></display:column>
						<display:column title="TranMode" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${MatruityList.TranMode}" /></display:column>
						<display:column title="TranNarr" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${MatruityList.TranNarr}" /></display:column>
						
						<display:column title="IntPaidDate" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${MatruityList.IntPaidDate}" /></display:column>
						<display:column title="LoanNumber" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabStringTextField" value="${MatruityList.LoanNumber}" /></display:column>
						<display:column title="LoanIntRat" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="formTextField" value="${MatruityList.LoanIntRat}" /></display:column>
						<display:column title="PrinBal" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${MatruityList.PrinBal}" /></display:column>
						<display:column title="IntDue" class="formTextFieldWithoutTransparent"><input type="text" readonly="readonly" class="dispTabDoubleTextField" value="${MatruityList.IntDue}" /></display:column>
						<display:column title="TotalBala" class="formTextFieldWithoutTransparent"><input type="text"  readonly="readonly" class="dispTabDoubleTextField" value="${MatruityList.TotalBala}" /></display:column>
					
						
					</display:table>
					</div>
					</core:if>
				</td>
			</tr>
		</table>	
	</table>
	
	
	
	</html:form>
	<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>