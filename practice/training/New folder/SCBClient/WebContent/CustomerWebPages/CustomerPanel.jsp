	<%@ page import="java.util.StringTokenizer" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt" %>
 
<%--  
  @ author SWETHA 
  Project:Core Banking Project 
  Date: Nov 12, 2007     
  Time: 10:43:48 PM     
  --%>
    
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="java.util.HashMap"%>         
<%@page import="java.util.Iterator"%>
<%@page import="masterObject.general.Address"%> 
<%@page import="masterObject.general.AddressTypesObject"%>
<%@page import="java.util.Vector"%>
<%@page import="com.scb.cm.forms.*"%>

<%@page import="com.scb.cm.actions.CustomerInformationForm"%>
<%@page import="java.util.Enumeration"%>
<%@page import="masterObject.general.AccSubCategoryObject"%>
<%@page import="java.awt.Checkbox"%>
<%@page import="com.scb.cm.actions.CustomerAction"%>
<%@page import="javax.swing.ImageIcon"%>
<%@page import="java.io.OutputStream"%>
<html>
  	<head>
  		<script type="text/javascript">
  		function show()
{
alert("hi am in show method");
refresh();
   var ele=document.forms[1].elements;
	for(var i=0;i<ele.length;i++)
	{
	if(ele[i].type=="hidden")
	{
	ele[i].value="";
	}
	}

}
function refresh(){
alert("Refreshing");
	var refresh_key=event.keycode;
	if(refresh_key==116){
		return false;
	}
}
  		function clearfields(target)
  		{
  		   document.forms[1].forward.value=target;
  		   document.forms[1].submit();
  		      
  		}
  		function setButtton(target)
  		{
  		if(document.forms[1].name.value=="")	
  		{
  			alert("ENTER A VALID NAME");
  			return false;
  		}
  		else if(document.forms[1].dob.value=="")
  		{
  			alert("ENTER A VALID DATE");
  			return false;
  		}
  		else if(document.forms[1].intro.value=="")
  		{
  			alert("ENTER A INTRODUCERID");
  			return false;
  		}
  		else if(document.forms[1].city.value=="")
  		{
  			alert("ENTER City");
  			return false;
  		}
  		else if(document.forms[1].addressarea.value=="")
  		{
  			alert("ENTER A Customer Address");
  			return false;
  		}
  		else
  		{
			document.forms[1].button.value=target;
			return true;
		}	
			
  		};
	function button_action(target)
	{
		document.forms[1].button.value=target;
		return true;
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
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
   function numbersonly_date(eve){
         var cha = event.keyCode
            if (  ( cha  > 46 && cha < 58 ) ) {
                return true ;
                            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
      };
      function alphanumericonly_id(eve){
         var cha = event.keyCode
            if (  ( cha  > 45 && cha < 58 || ch==64 ) ) {
                return true ;
                            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
      };
  	function dateLimit()
 	{
 	if(document.forms[0].dob.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].dob.value="";
 	document.forms[0].dob.focus;
 	alert("Enter dd/mm/yyyy Format!!!");
 	return false;
 	}
 	}	
  	function ValidName(name){
  	if(name.length==0){
  	alert("Enter Customer Name"); 
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
	var dt=document[1].dob;
	if (isDate(dt.value)==false){
	    dt.value="";
	    //alert("enter correct date");
	    dt.focus();  	
		return false
		}
		else {
		document.forms[1].submit()
		}
		return true;
		}
		function ValidateForm1()
		{
			var dt1=document[1].issuedate;
			if (isDate(dt1.value)==false){
			    dt1.value="";
			    dt1.focus();  	
				return false
			}
			else
			{
				return true;
			}
		}
	function ValidateForm2()
	{	
	var dt2=document[1].expirydate;
	 if (isDate(dt2.value)==false){
	    dt2.value="";
	    dt2.focus();  	
		return false
		}
		else
		{
	    return true
		}
 }
  	function clear_textfield()
  	{
  		alert("Clearing Text Fields");
  		alert(document.forms[1].elements);
  		var ele=document.forms[1].elements;
  		for(var i=0;i<ele.length;i++)
  		{
  			if(ele[i].type=="text" || ele[i].type=="textarea")
  			{
  			  ele[i].value="";
  			}
  		
  		}
  		show(false,'div1');
  	};	
  	function fun_passport(ids)
  	{
  		if(ids.checked)
  	    {
  	    	document.getElementById("passport_detail").style.display ='block';
  	    }
  	    else    
  	    {
  	    	document.getElementById("passport_detail").style.display = 'none';
  	    }
  	};
  	function date_validation_lessthen_currentdate(da){
  		var date1=da.value;
  		var date=new Date(); 
  		var dd=date1.split('/');
  		if(dd.length==3)
  		{
				 if((parseInt(dd[2])>parseInt(date.getYear())))
				{
					alert("date should be less then the current date "+parseInt(date.getDate())+"/"+parseInt(date.getMonth()+1)+"/"+parseInt(date.getYear())+"!");
					da.value="";
					da.focus();
				}	  				  			
		  		else
		  		{
		  			if((parseInt(dd[2])==parseInt(date.getYear())))
			  			{
				  			if((parseInt(dd[1])>parseInt(date.getMonth()+1)))
							{
								alert("date should be less then the current date "+parseInt(date.getDate())+"/"+parseInt(date.getMonth()+1)+"/"+parseInt(date.getYear())+"!");
								da.value="";
								da.focus();
							}
							else
							{		
									if((parseInt(dd[1])<=parseInt(date.getMonth()+1)))
									{
										if((parseInt(dd[0])>parseInt(date.getDate())))
								  		{
									  		alert("date should be less then the current date "+parseInt(date.getDate())+"/"+parseInt(date.getMonth()+1)+"/"+parseInt(date.getYear())+"!");
									  		da.value="";
									  		da.focus();
									  		return false;
								  		}
								  		else
								  		{
								  			return true;
								  		}
							 		}
							}
						}
		  			return  true;
		  		}
  		}
  	}
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
	function number_valid()
	{
		if(document.forms[1].mobile.value.length<11)
		{
			//alert("plz enter the valid number");
			return false;
		}
		else
		{
			return true;
		}
	}
  	function number_limit(){
  	if(document.forms[1].mobile.value.length>11 && document.forms[1].mobile.value.length<=12)
  	{
  		//alert("plz enter the valid number");
  		return false;
  	}
  	else{
  		return true;  
  	}
  	}
  	function mobile_validate(mobile)
	{
 		var valu=mobile.value.length;
 		var cha =event.keyCode;
 		if( valu >11)
 		{	
 			return false;
 		}
 		else		
 		{	
			return true;
 		}	
	};
	function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >= 65 && cha <=90)) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
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
	function number_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >=48 && cha <=57)||(cha >= 65 && cha <=90)|| cha==32) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	}; 
	function only_for_address()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >= 44 && cha <=58)||(cha >= 65 && cha <=90)||cha==32||cha==35) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
function only_alpha1()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) || (cha >= 65 && cha <=90) ||cha==32 ||cha==46) 
 		{
   			return true;
       	} else 

       	{
   			return false;
       	}
	};

	

	function setclear()
	{
	   var elel=document.forms[1].elements;
  			for(var i=0;i<elel.length;i++)
  			{
  				if(elel[i].type=="text" || elel[i].type=="textarea")
  				{
  			  		elel[i].value="";
  				}
  			}
	}	
	function agevalu()
	{	
				
					if(document.forms[1].categories.value==202 && document.forms[1].txt_age.value<=60 )
					{
						alert("please enter the correct date");
						document.forms[1].dob.value=""
						document.forms[1].txt_age.value=""
						document.forms[1].dob.focus();
						return false;
					}
					else
					{
						return true;
					}
				
	}
	
	
	function clear()
	{
		
		if(document.getElementById("clear_value").value=="1")
		{
			var ele=document.forms[1].elements;
  			for(var i=0;i<ele.length;i++)
  			{
  				if(ele[i].type=="text" || ele[i].type=="textarea")
  				{
  			  		ele[i].value="";
  				}
  		
  			}
  			show(false,'div1');
		}
		 show(false,'div1');
	}
	
	var ns4 = (document.layers) ? true : false;
var ie4 = (document.all && !document.getElementById) ? true : false;
var ie5 = (document.all && document.getElementById) ? true : false;
var ns6 = (!document.all && document.getElementById) ? true : false;

function show(sw,obj) {

  if (sw && (ie4 || ie5) ) document.all[obj].style.visibility = 'visible';
  if (!sw && (ie4 || ie5) ) document.all[obj].style.visibility = 'hidden';
  if (sw && ns4) document.layers[obj].visibility = 'visible';
  if (!sw && ns4) document.layers[obj].visibility = 'hidden';
}


function setclearnone()
{
document.forms[1].forward.value="clearall";
document.forms[1].submit();
}

function spousenamecheck()
{
	if(document.forms[1].marital.value=="Bachelor"||document.forms[1].marital.value=="Spinster")
	{
		document.forms[1].spousename.disabled=true;
		return true;
	}
	else
	{
		document.forms[1].spousename.disabled=false;
		return false;
	}
}


function pinvalidation(d)
{
	if(d.value.length>10)
	{
	alert("invalid pin number");
	d.value="";
	return false;
	}
	else
	{
		return true;
	}
}
function panvalidation(d)
{
	if(d.value.length>10)
	{
	alert("invalid pan number");
	d.value="";
	return false;
	}
	else
	{
		return true;
	}
}
function passportnovalidation(d)
{
	if(d.value.length>10)
	{
	alert("invalid passportno number");
	d.value="";
	return false;
	}
	else
	{
		return true;
	}
}








  	</script>
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
	<h2 class="h2"><center>New Customer</center></h2>
  	</head>
  	
  	<%System.out.println("@@@@@@@@%%%%%%%%%Hi from Customer Page%%%%%%%%@@@@@@@@@"); %>
  	
  	<%! String clear_val;%>
       <% clear_val=(String)request.getAttribute("clear"); 
       	  System.out.println("clear value in customer panel=====>"+clear_val);
       	        	  
       	        	  
       	  %>
  	    <html:hidden property="" value="clear_val" styleId="clear_value"/>
  	    
  	    <%System.out.println("************Before OnLoad**************"); %>
  	    
  	<body   bgcolor="beige" onload="show()">  
       
       <%System.out.println("************After OnLoad**************"); %>
       
  	<html:form action="/CustomerPanel?pageId=0" method="post" enctype="multipart/form-data">
     
     <%System.out.println("***********************Testing*********************"); %>
 	 <%! Customerform custform; %>
  	 <% custform=(Customerform)request.getAttribute("customerform"); %>

  		<%if(custform!=null)
  		{%>
			<html:hidden property="custform.custid" value="<%=""+custform.getCustid()%>"></html:hidden>
			<html:hidden property="custform.custtype" value="<%=""+custform.getCusttype()%>"></html:hidden>
			<html:hidden property="custform.value" value="<%=""+custform.getValue()%>"></html:hidden>
			<html:hidden property="custform.pageId" value="<%=""+custform.getPageId()%>"></html:hidden>
  		<%}%>
  


	              
  	<%! HashMap address; %>
  	<% address=(HashMap)request.getAttribute("Address"); %>
  	<%! Iterator addr_ittr;%>           
         
    
  	<%! String value;%>
  	<%value=(String)request.getAttribute("submit");%>
  	
  	  	
    <%!String update_value,AGE_ATT,SubCatCode,SubCatDesc;%>
    
    <% update_value=(String)request.getAttribute("button_values");%>
    
    <% SubCatCode=(String)request.getAttribute("SubCatCode");%>
    
    <% SubCatDesc=(String)request.getAttribute("SubCatDesc");%>
    
    
    <% AGE_ATT=(String)request.getAttribute("AGE_ATT");%>
     
    <%! String checkbox; %>
    <%checkbox=(String)request.getAttribute("checkbox"); %>
    <%System.out.println("checkbox value in jsp page======="+checkbox); %> 
    
    <%! String Cust_Name,AgeAttribute,GuardianDetail; %>
    
    <%GuardianDetail=(String)request.getAttribute("GuardianDetail");
    System.out.println("===============>"+GuardianDetail);
    %>
    
    
    <%Cust_Name=(String)request.getAttribute("Cust_Name");%>
 
 <%AgeAttribute=(String)request.getAttribute("AgeAttribute");%>
 <%System.out.println("Cust_Name after fetching introducerId========"+Cust_Name); %>   
 
 
  
   
  	<%!boolean flag;%> 	   
  	<%if(value!=null)
  	{ 
  		if((Integer.parseInt(value)==3)||(Integer.parseInt(value)==2))
  		{
  			flag=true; 
  		}else
  		{  
  		flag=false; 
  		}
  	}%>
  
  	<%! CustomerMasterObject obj; %>
  	<% obj=(CustomerMasterObject)request.getAttribute("Detail");%>
    <%String clearmypage=(String)request.getAttribute("pleaseclear"); %>
    <% System.out.println("Inside Clear my page First------->"+clearmypage); %>
    
    <%if(clearmypage==null ){ %>
    <% System.out.println("Inside Clear my page------->"+clearmypage); %>
   
	
	<%} %>
    <%if(obj!=null){ 
     System.out.println("pp number---------->"+obj.getPassPortNumber());
     System.out.println("pp issue date------>"+obj.getPPIssueDate());
     System.out.println("pp exp date-------->"+obj.getPPExpiryDate());
    // System.out.println("category----------->"+obj.getCategory());
    
     
     }%>
   
  	<%!String  flag2,Introducer_Name;%>
  	
  	<%flag2=(String)request.getAttribute("update_flag_value");%>
  	
  	<%Introducer_Name=(String)request.getAttribute("Introducer_Name");
  	 String newcuAccess=(String)request.getAttribute("newcuAccess");
  	%>
  	
  	
  	<%AccSubCategoryObject accobj[]; %>
  	<%accobj=(AccSubCategoryObject[])request.getAttribute("AccountSubCat"); %>
  
  	<%if(flag2!=null)
  	{
  		if(flag2.equalsIgnoreCase("1"))
  		{
    		flag=false;
    	}else
    	{
			flag=true;	   
    	}
  	}%> 
       
     <html:hidden property="cid_first"/>
     <html:hidden property="cid_fi"/>
     

<table  align="center" cellspacing="2" style="border: thin solid black;" class="txtTable">
<tr>
<td>
<font  color="red"><bean:message key="label.mandatory"></bean:message>
  		    </font>
</td>
</tr> 
<tr>
<td>
  		    
  		      	<table  style="border:thin solid black;" class="txtTable" height="100" width="650" cellspacing="1">
       
          <tr> 
              
              	    <core:choose> 
        		<core:when test="${empty requestScope.Detail}">
        		<%System.out.println("I am in if null block of custpanel"); %>
        			<td align="left"><font color="red"><bean:message key="label.custname"></bean:message></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            		<html:text property="name" styleId="myname" size="10"  disabled="<%=flag%>" onblur="ValidName(this.value)" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
            	</core:when>
        		<core:otherwise> 
        		<%System.out.println("I am in if NOT null block of custpanel"); %>
        			<td><font color="red"><bean:message key="label.custname"></bean:message></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       				<html:text property="name" styleId="myname" size="10" value="<%= obj.getFirstName()%>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
       			</core:otherwise> 
       		</core:choose> 
                    
           
        	<core:choose>
        		<core:when test="${empty requestScope.Detail}">
        			<td><html:text property="midname" styleId="mymidname" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
        		</core:when>
         		<core:otherwise>
        			<td><html:text property="midname" styleId="mymidname" size="10" value="<%=obj.getMiddleName()%>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
         		</core:otherwise>
        	</core:choose>
        
        	<core:choose> 
        		<core:when test="${empty requestScope.Detail}">
         			<td><html:text property="lastname" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
        		</core:when>
        		<core:otherwise>
        			<td><html:text property="lastname" size="10" value="<%= obj.getLastName()%>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
        		</core:otherwise>
        	</core:choose>     
       
       
       
          <core:choose>
          		<core:when test="${empty requestScope.Detail}">
          			<td><html:select property="salutation" disabled="<%=flag%>">
          		<core:forEach var="combosalutationvalues" items="${requestScope.salutation}">
           			<html:option value="${combosalutationvalues}"><core:out value="${combosalutationvalues}"></core:out></html:option>
          		</core:forEach>
          				</html:select>
          			</td>&nbsp;&nbsp;
          		</core:when>
          		<core:otherwise>
           			<td><html:select property="salutation" disabled="<%=flag%>">
               		<html:option value="<%=obj.getSalute()%>"></html:option>
              		</html:select></td>
          			</core:otherwise>
          </core:choose>
            
            <%if(SubCatCode!=null && SubCatDesc!=null){%>
            <td><html:select property="categories" disabled="<%=flag%>">
                  <html:option value="<%=""+SubCatCode%>"><%=SubCatDesc%></html:option>
                   </html:select>
		     </td>
            <%}else{ %>
            <td><html:select property="categories" disabled="<%=flag%>">
                  <%if(accobj!=null){ %>
                  <%for(int i=0;i<accobj.length;i++){ %>
                   <html:option value="<%=""+accobj[i].getSubCategoryCode()%>"><%=accobj[i].getSubCategoryDesc()%></html:option>
                   <%} %>
                   <%} else if(obj!=null){%>
                   <html:option value="<%=""+obj.getCategory()%>"><%=""+obj.getCategory()%></html:option>
                   <%}%>
                   </html:select>
		     </td>
            <%}%>      
                    
          			
   		</tr>
                  
   
   
 		<tr></tr>
 

<tr>
     	    <td></td>
     		<td><bean:message key="label.midname"></bean:message></td>
     		<td><bean:message key="label.lastname"></bean:message></td>
     		<td><bean:message key="label.sal"></bean:message></td>
     		<td><bean:message key="label.cat"></bean:message></td>
		</tr>
	</table>



<table  style="border:thin solid black;" class="txtTable" height="100" width="650" cellspacing="1">
<tr>
      
      		<core:choose>
          		<core:when test="${empty requestScope.Detail}">
           			<td align="left"><bean:message key="label.fathername"></bean:message>
           			<html:text property="fathername" styleId="myfathname" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:when>
          		<core:otherwise>
           			<td align="left"><bean:message key="label.fathername"></bean:message>
           			<html:text property="fathername" styleId="myfathname" size="10" value="<%= obj.getFatherName()%>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:otherwise>
           	</core:choose>
         
 
      		<core:choose>
          		<core:when test="${empty requestScope.Detail}">
          			<td align="left"><bean:message key="label.mothername"></bean:message><html:text property="mothername" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:when>
          		<core:otherwise>
           			<td align="left"><bean:message key="label.mothername"></bean:message><html:text property="mothername" size="10" value="<%= obj.getMotherName() %>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:otherwise>
          	</core:choose>
     
     
     		<core:choose>
          		<core:when test="${empty requestScope.Detail}">
           			<td align="left"><bean:message key="label.maritalstatus"></bean:message>
          			<html:select property="marital" disabled="<%=flag%>" onblur="return spousenamecheck()">
          		<core:forEach var="combo_maritial_values" items="${requestScope.MaritalStatus}">
          			<html:option value="${combo_maritial_values}"><core:out value="${combo_maritial_values}"></core:out></html:option>
          		</core:forEach>
          			</html:select>
          			</td> 
          		</core:when>
          		<core:otherwise>
          			<td align="left"><bean:message key="label.maritalstatus"></bean:message>
          			<html:select property="marital" disabled="<%=flag%>">
          			<html:option value="<%= obj.getMaritalStatus() %>"></html:option>
          			</html:select>
          			</td>
          		</core:otherwise>
          	</core:choose>
      
	   	</tr>
   

   
 		<tr></tr>

 		<tr> 
 
        	<core:choose>
          		<core:when test="${empty requestScope.Detail}">
           			<td align="left"><bean:message key="label.sposename"></bean:message><html:text property="spousename" size="10" disabled="<%=flag%>" onkeypress="return only_alpha1()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
          		</core:when>
          		<core:otherwise>
          			<td align="left"><bean:message key="label.sposename"></bean:message><html:text property="spousename" size="10" value="<%= obj.getSpouseName() %>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:otherwise>
          	</core:choose>
    
            <core:choose>
	            <core:when test="${empty requestScope.Detail}">
    	   	        <td align="left"><bean:message key="label.nationality"></bean:message>
    	   	        <html:text property="nation" size="10" value="INDIAN" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
          	    </core:when>
          	    <core:otherwise>
          		    <td align="left"><bean:message key="label.nationality"></bean:message><html:text property="nation" size="10" value="<%= obj.getNationality() %>" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
          	    </core:otherwise>
            </core:choose>
    
    
    	    <core:choose>
          		<core:when test="${empty requestScope.Detail}">
           			<td align="left"><bean:message key="label.religion"></bean:message><html:text property="religion" size="10"  onkeypress="return only_alpha()"   styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:when>
          		<core:otherwise>
          			<td align="left"><bean:message key="label.religion"></bean:message></td>
          		<td>
          		<%if(obj.getReligion()!=null){ %>
          		<html:text property="religion" size="10" readonly="true" value="<%= obj.getReligion()%>" onkeypress="return only_alpha()"  styleClass="formTextFieldWithoutTransparent" > </html:text>
          		<%}else{%>
          		<html:text property="religion" size="10" readonly="true"  onkeypress="return only_alpha()"  styleClass="formTextFieldWithoutTransparent" > </html:text>
          		<%}%>
          		</td>
          		</core:otherwise>
            </core:choose>
    
    
    
 		</tr>

	 	<tr></tr>

 		<tr>
     	    <core:choose>
            	<core:when test="${empty requestScope.Detail}">
        			<td align="left"><font color="red"><bean:message key="label.dob"></bean:message></font><html:text property="dob" size="10" disabled="<%=flag%>"  onblur="ValidateForm()"  styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly_date(this)" onkeydown="return dateLimit()" ></html:text></td>
        		</core:when>
          		<core:otherwise>
          			<td align="left"><font color="red"><bean:message key="label.dob"></bean:message></font><html:text property="dob" size="10" value="<%= obj.getDOB() %>" readonly="true" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly_date(this)" onkeydown="return dateLimit()" ></html:text></td>
          		</core:otherwise>
          	</core:choose>
          
          <%if(AgeAttribute!=null){%>
              <td align="left">
              <bean:message key="label.age"></bean:message>
              <html:text property="txt_age"   value="<%= AgeAttribute %>"  size="10" style="border:transparent;background-color:beige;color:red" onblur="return agevalu()" readonly="true"></html:text></td>
            <%}else if(AGE_ATT!=null){%>
            <td align="left">
            <bean:message key="label.age"></bean:message><html:text property="txt_age"  value="<%=AGE_ATT%>" size="10" style="border:transparent;background-color:beige;color:red" onblur="return agevalu()" readonly="true"></html:text></td>
            <%}%>
            
        	<td align="left"><bean:message key="label.sex"></bean:message>
            <%if(obj!=null){ %>
            <html:select property="sex" disabled="<%=flag%>" value="<%= obj.getSex()%>" onblur="return agevalu()"  >
            
        	<html:option value="M">male</html:option>
        	<html:option value="F">female</html:option>
        	
        	</html:select>
        	<%}else{ %>
        	<html:select property="sex" onblur="return agevalu()" >
        	<html:option value="sel">Select</html:option>
        	<html:option value="M">male</html:option>
        	<html:option value="F">female</html:option>
        	</html:select>
        	<%} %>
        	</td>

        	
 	</tr>
	
	<tr>
	      <%if(GuardianDetail!=null){
			if(GuardianDetail.equalsIgnoreCase("GuardianDetail"))
			{%>
			  <td>
			  	<font color="red"><bean:message key="label.headguar"/></font>
			  </td>
			  
			  	<%}
		} %>
		
</tr>
<tr style="border-color: maroon">	   
		<%if(GuardianDetail!=null){
			if(GuardianDetail.equalsIgnoreCase("GuardianDetail"))
			{%>
			 
			   <td  align="left">
			        <bean:message key="label.type"/>
			   		<html:select property="txt_gurtype" onblur="return agevalu()">
					<html:option value="Lawfull">Lawful</html:option>
					<html:option value="Lawfull">Natural</html:option>
					</html:select>
			    </td>
				<td align="left">
				<font color="red"><bean:message key="label.custname"/></font>
				<html:text property="txt_guarname" size="10" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text>	
				</td>
				
				<td  align="left">
					<font color="red"><bean:message key="label.address"/></font>
					<html:textarea property="txt_guaraddress" rows="2" cols="15" onkeypress="return only_for_address()" styleClass="formTextFieldWithoutTransparent"></html:textarea>
					
				</td>
			<%}
		} %>
		
	</tr>
	
	<tr style="border-color: maroon;border-style: dotted">
	<%if(GuardianDetail!=null){
			if(GuardianDetail.equalsIgnoreCase("GuardianDetail"))
			{%>
		    <td  align="left">
			        <bean:message key="label.sex"/>
			   		<html:select property="txt_gursex">
					<html:option value="male">Male</html:option>
					<html:option value="female">Female</html:option>
					</html:select>
			    </td>
				<td align="left">
				<font color="red"><bean:message key="label.Courtdate"/></font>
				<html:text property="txt_guarcourtno" size="10" onblur="ValidateForm()" onkeypress="return numbersonly_date(this)" onkeydown="return dateLimit()" styleClass="formTextFieldWithoutTransparent"></html:text>	
				</td>
				
				<td  align="left">
					<font color="red"><bean:message key="label.date"/></font>
				<html:text property="txt_guardate" size="10" onblur="ValidateForm()" onkeypress="return numbersonly_date(this)" onkeydown="return dateLimit()" styleClass="formTextFieldWithoutTransparent"></html:text>	
				
				</td>
		<%}
		}%>
		
	</tr>
 	<tr></tr>

 	<tr>
        <core:choose>
        	<core:when test="${empty requestScope.Detail}">
           		<td align="left"><bean:message key="label.castedet"></bean:message><html:text property="caste" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          	</core:when>
          	<core:otherwise>
          		<td align="left"><bean:message key="label.castedet"></bean:message><html:text property="caste" size="10" value="<%= obj.getCaste() %>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          	</core:otherwise>
        </core:choose>
    
        <core:choose>
        	<core:when test="${empty requestScope.Detail}">
          		<td align="left"> <bean:message key="label.sc/st"></bean:message>
    			<html:select property="scst" disabled="<%=flag%>">
    		<core:forEach var="sc_st_values" items="${requestScope.caste}">
    			<html:option value="${sc_st_values}"><core:out value="${sc_st_values}"></core:out></html:option>
    		</core:forEach>
    			</html:select>
    			</td>
          	</core:when>
          	<core:otherwise>
			    <td align="left"><bean:message key="label.sc/st"></bean:message>
    			<html:select property="scst"  disabled="<%=flag%>">
    			<html:option value="<%= obj.getScSt()%>"></html:option>
    			</html:select>
    			</td>  
        	</core:otherwise>
         </core:choose>
    
    </tr>
    <tr>
    	<core:choose>
        	<core:when test="${empty requestScope.Detail}">
          		<td align="left"><font color="red"><bean:message key="label.Intro"></bean:message> </font>  <html:text property="intro" size="10" disabled="<%=flag%>" onchange="submit()" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          	</core:when>
          	<core:otherwise>
          		<td align="left"><font color="red"><bean:message key="label.Intro"></bean:message></font>        <html:text property="intro" size="10" value="<%= obj.getIntroducerId() %>" readonly="true" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          	</core:otherwise>
        </core:choose>
     
   
     
       
    	<%if(Cust_Name!=null){%>
             <td align="left"><html:text property="introid2"  disabled="<%=flag%>" value="<%=""+Cust_Name%>" size="20" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text></td>
           <%}else if(Introducer_Name!=null){%>
 			<td align="left"><html:text property="introid2" value="<%=Introducer_Name%>" size="20" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text></td>       
        <%}%>
 	</tr>

 	<tr></tr> 

	<tr>
		<td align="left">
		<div>
		<bean:message key="label.passportDet"/>
		<html:checkbox property="check_passport" styleId="check_pp" onclick="return fun_passport(this)"></html:checkbox>
		</div>
		</td>
		
	</tr>
	
	<tr>
       <td>
	   <div id="passport_detail" style="display: none;">
	  
	   <table class="txtTable">
	    
	    
	    <tr>
	    <td align="left"> 
    	<core:choose>
         	<core:when test="${empty requestScope.Detail}">
          		<bean:message key="label.passport"></bean:message><html:text property="passportno" size="10" disabled="<%=flag%>" onkeypress="return number_alpha()" onblur="passportnovalidation(this)" styleClass="formTextFieldWithoutTransparent"></html:text>
          	</core:when>
          	<core:otherwise>
          		<bean:message key="label.passport"></bean:message><html:text property="passportno" size="10" value="<%= obj.getPassPortNumber() %>" readonly="true" onkeypress="return number_alpha()" onblur="passportnovalidation(this)" styleClass="formTextFieldWithoutTransparent"></html:text>
          	</core:otherwise>
        </core:choose>
       </td>
        </tr>
      
      <tr>
      <td align="left">  
    	<core:choose>
        	<core:when test="${empty requestScope.Detail}">
          		<bean:message key="label.issudate"></bean:message><html:text property="issuedate" size="10" disabled="<%=flag%>" onchange="ValidateForm1()" onblur="date_validation_lessthen_currentdate(this)" onkeypress="return numbersonly_date(this)" styleClass="formTextFieldWithoutTransparent"></html:text>
          	</core:when>
          	<core:otherwise>
          		<bean:message key="label.issudate"></bean:message><html:text property="issuedate" size="10" value="<%= obj.getPPIssueDate() %>" readonly="true" onkeypress="return numbersonly_date(this)" styleClass="formTextFieldWithoutTransparent"></html:text>
          	</core:otherwise>
        </core:choose>
      </td>
       </tr>
        
      <tr>
       <td align="left"> 
	    <core:choose>
    	     <core:when test="${empty requestScope.Detail}">
         		 <bean:message key="label.expirydate"></bean:message><html:text property="expirydate" size="10" disabled="<%=flag%>" onchange="ValidateForm2()" onkeypress="return numbersonly_date(this)"  styleClass="formTextFieldWithoutTransparent"></html:text>
          	 </core:when>
          	 <core:otherwise>
          		 <bean:message key="label.expirydate"></bean:message><html:text property="expirydate" size="10" value="<%= obj.getPPExpiryDate() %>" readonly="true" onkeypress="return numbersonly_date(this)"  styleId="ppexpdate" styleClass="formTextFieldWithoutTransparent"></html:text>
          	</core:otherwise>
        </core:choose>
       </td>
       
       </tr> 
    
    </table>    
    </div>
    </td>  
	</tr>



	 

	<tr>

    	<core:choose>
          	<core:when test="${empty requestScope.Detail}">
          		<td align="left"><bean:message key="label.occupation"></bean:message>
     			<html:select property="occupation" disabled="<%=flag%>">
     		<core:forEach var="occ_values" items="${requestScope.occupation}">
     			<html:option value="${occ_values}"><core:out value="${occ_values}"></core:out></html:option>
     		</core:forEach>
     			</html:select></td>
          	</core:when>
          	<core:otherwise>
          		<td align="left"><bean:message key="label.occupation"></bean:message>
    			<html:select property="occupation" disabled="<%=flag%>">
    			<html:option value="<%= obj.getOccupation()%>"></html:option>
    			</html:select></td>
          	</core:otherwise>
        </core:choose>
 
     
    	<core:choose>
        	<core:when test="${empty requestScope.Detail}">
          	<td align="left"><html:select property="pvt_sector" disabled="<%=flag%>">
        <core:forEach var="sub_occ_values"  items="${requestScope.sub_occ}">
        	<html:option value="${sub_occ_values}"><core:out value="${sub_occ_values}"></core:out></html:option>
       	</core:forEach>
        	</html:select></td>
        </core:when>
        <core:otherwise>
          	<td align="left"><html:select property="pvt_sector" disabled="<%=flag%>">
         	<html:option value="<%=obj.getSubOccupation()%>"></html:option>
    		</html:select></td>
         </core:otherwise>
         </core:choose>
 
    
    	 <core:choose>
             <core:when test="${empty requestScope.Detail}">
             	 <td align="left"><bean:message key="label.pan"></bean:message><html:text property="pan" disabled="<%=flag%>"  size="10" onkeypress="return number_alpha()" onblur="panvalidation(this)" styleClass="formTextFieldWithoutTransparent"></html:text></td>      
             </core:when>
             <core:otherwise>
           		 <td align="left"><bean:message key="label.pan"></bean:message><html:text property="pan" value="<%= obj.getPanno() %>" readonly="true" onkeypress="return number_alpha()"  onblur="panvalidation(this)" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>          
          	 </core:otherwise>
         </core:choose>
       
    
	</tr>
 

</table>
		    

	 
	  <table  style="border:thin solid black;" class="txtTable" height="100" width="650" cellspacing="1">
	  <tr>
	  <td>
	  <table  style="border:thin solid black;" class="txtTable" height="100" width="500" cellspacing="1">
	  <tr>
	    <%! AddressTypesObject AddressType[];%>
   		<%! Vector vect_add=new Vector();%>
   		<% AddressType=(AddressTypesObject[])request.getAttribute("AddressType"); %>
     
   
   		<% if((AddressType!=null)&& (obj!=null))
   		{ %>
   			<td align="left"><font color="red"><bean:message key="label.address"></bean:message></font>
   			<html:select property="addrestype"  onchange="clearfields('clearitem')" tabindex="0" >
   			<% for(int i=0;i<AddressType.length;i++)
   			{%> 
   				<%vect_add.addElement(AddressType[i].getAddressDesc());%>
   				<html:option value="<%=""+AddressType[i].getAddressCode()%>"><%=""+AddressType[i].getAddressDesc()%></html:option>
   			<%}%>
   			</html:select></td>
   		<%}else
   			{ %> 
   			<td align="left"><font color="red"><bean:message key="label.address"></bean:message></font></td>
   			<td align="left">
   				<html:select property="addrestype" onchange="clearfields('clearitem')" tabindex="0">
				<core:forEach items="${requestScope.AddrType}" var="AddrType">
					<html:option value="${AddrType.addressCode}">${AddrType.addressDesc}</html:option>
				</core:forEach>
				</html:select>
			</td>
   		<%}%> 
      
     	
     	
     	<%address=(HashMap)request.getAttribute("Address");%>
     	<%!Integer address_flag; %>
     	<% address_flag=(Integer)request.getAttribute("AddressFlag"); %>
     
     	<%if((address!=null)&&(obj!=null))
     	{%>
    	 <td align="left"><%System.out.println("middle text area"+address_flag); %>
     		 <% Address addr=(Address)address.get(address_flag);%>
     		 <%if(addr!=null){ %>
     		<html:textarea property="addressarea"  styleClass="formTextFieldWithoutTransparent" onkeypress="return only_for_address()" value="<%=addr.getAddress()%>" disabled="<%=flag%>"></html:textarea></td>
     		<%} %>
     	<%}else
     	{ %> 
     		<td align="left"><html:textarea property="addressarea" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_for_address()" disabled="<%=flag%>" ></html:textarea></td>
     		
     	<%}%> 
	  
	  
	  </tr>
	  <tr>  
	   <%if((address!=null)&&(obj!=null))
	   {%>
     		<% Address addr=(Address)address.get(address_flag);
     		System.out.println("address===========>"+addr);%>
         	<td align="left"><bean:message key="label.country"></bean:message> 
         	<%if(addr!=null){ %>  
         	<html:select property="country" disabled="<%=flag%>">
         	<html:option value="<%=addr.getCountry()%>"></html:option>   	
         	</html:select>
         	<%}else{ %>
         	<html:select property="country" disabled="<%=flag%>">
         	<html:option value=" "></html:option>   	
         	</html:select>
         	<%} %>
         	</td>
     	<%}else
     	{%>
         	<td align="left"><bean:message key="label.country"></bean:message>
         	<html:select property="country" disabled="<%=flag%>">
         	<core:forEach var="Combo_country_values" items="${requestScope.Country}">
         	<html:option value="${Combo_country_values}"><core:out value="${Combo_country_values}"></core:out></html:option>
         	</core:forEach>
         	</html:select></td>
      	<%}%>
    
     <%System.out.println("**********Testing 2************1234***"); %>
     
     	<%address=(HashMap)request.getAttribute("Address");%>
     	<%if((address!=null)&&(obj!=null))
     	{%>
      		<%addr_ittr=address.values().iterator();%>
     		<td align="left"><bean:message key="label.State"></bean:message>
     		<html:select property="state" disabled="<%=flag%>">
     		<%while(addr_ittr.hasNext())
     		{ %>
    			<% Address addr=(Address)addr_ittr.next();%>
     			<% String country= addr.getState();%>
     			<html:option value="<%=country%>" disabled="<%=flag%>"></html:option>
     		<%}%>
     		</html:select></td>
     	<%}else
     	{%>
        	<td align="left"><bean:message key="label.State"></bean:message>
        	      	<html:select property="state" disabled="<%=flag%>">
       		<core:forEach var="state_values" items="${requestScope.statevalues}">
       				<html:option value="${state_values}"><core:out value="${state_values}"></core:out></html:option>
        	</core:forEach>
        	</html:select> 
        	</td>
     	<%}%>
    </tr>
	  <tr>
      <%address=(HashMap)request.getAttribute("Address");%>
      <%if((address!=null)&&(obj!=null)){%>
      <% Address addr=(Address)address.get(address_flag);%>
      <td align="left"><font color="red"><bean:message key="label.city"></bean:message></font>
      <%if(addr!=null){ %>
      <html:text property="city" size="10" styleClass="formTextFieldWithoutTransparent" value="<%=addr.getCity()%>" disabled="<%=flag%>"></html:text>
      <%}else{ %>
      <html:text property="city" size="10" styleClass="formTextFieldWithoutTransparent" disabled="<%=flag%>"></html:text></td>
      <%} %>
      <%}else{ %>  
      <td align="left"><bean:message key="label.city"></bean:message>
      <html:text property="city" size="10" styleClass="formTextFieldWithoutTransparent" disabled="<%=flag%>" onkeypress="return only_alpha()"></html:text></td>
      <%}%>
      <%System.out.println("**********Testing 5***************"); %>
      
     <%address=(HashMap)request.getAttribute("Address");%>
     <%if((address!=null)&&(obj!=null)){%>
     <% Address addr=(Address)address.get(address_flag);%>
     <td align="left"><bean:message key="label.pin"></bean:message>
     <%if(addr!=null){ %>
     <html:text styleClass="formTextFieldWithoutTransparent" property="pin" size="10" value="<%=addr.getPin()%>" disabled="<%=flag%>" onblur="pinvalidation(this)" ></html:text></td>
     <%}else{ %>
     <html:text styleClass="formTextFieldWithoutTransparent" property="pin" size="10" disabled="<%=flag%>" onblur="pinvalidation(this)" ></html:text></td>
     <%} %>
     <%}else{ %>    
     <td align="left"> <bean:message key="label.pin"></bean:message><html:text property="pin" size="10" disabled="<%=flag%>" onkeypress="return only_numbers()" onblur="pinvalidation(this)"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
     <%}%>
      <%address=(HashMap)request.getAttribute("Address");%>
    	<%if((address!=null)&&(obj!=null))
    	{%>
  		  <%Address addr=(Address)address.get(address_flag);%>
    	  <td align="left"><bean:message key="label.landline"></bean:message>
    	   <%if(addr!=null && addr.getPhno()!=null){ %>
     	      <html:text property="phnum1" size="10"  value="<%=addr.getPhno()%>" disabled="<%=flag%>" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
     	      <%}else{ %>
     	      <td align="left"><bean:message key="label.landline"></bean:message><html:text property="phnum1" size="10" readonly="true" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
     	      <%} %>
    	<%}else
    	{%>
    	  <td align="left"><bean:message key="label.landline"></bean:message><html:text property="phnum1" size="10" disabled="<%=flag%>" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text>
    	  </td>
    	<%}%>
    	
    </tr>
	  
	  </table>
	  </td>
	  <td>
	  <input type="submit" value="SaveAddress" name="method" class="ButtonBackgroundImage"  onclick="location.href='?method=Save'" />
	  </td>
	  
	
	  </tr>
	  </table>
	  
	   
   		
	 


<table  cellpadding="2" style="border:thin solid black;" class="txtTable" height="100" width="650" cellspacing="1">
<tr>
    <%if(obj!=null){ %>
    <td><bean:message key="label.addrproof"></bean:message>
    <html:select property="addrproof" disabled="<%=flag%>">
    <html:option value="<%= obj.getAddressProof() %>"></html:option>
    </html:select></td>
    <%}else{ %>
    <td><bean:message key="label.addrproof"></bean:message>
    <html:select property="addrproof" disabled="<%=flag%>">
    <core:forEach var="combo_addrproof_values" items="${requestScope.AddressProof}">
    <html:option value="${combo_addrproof_values}"><core:out value="${combo_addrproof_values}"></core:out></html:option>
    </core:forEach>
    </html:select>
    </td>  
     <%}%>
</tr>

	<%System.out.println("**********Testing 6***************"); %>

         
 	<tr>
    	<%if((address!=null)&&(obj!=null))
    	{%>
    		<% Address addr=(Address)address.get(address_flag);%>
    		<td align="left"><bean:message key="label.mobile"></bean:message>
    		<%if(addr!=null){ %>
    		   			<html:text property="mobile" size="10" value="<%=addr.getMobile() %>" disabled="<%=flag%>" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td>    
    		   			<%}else{ %>
    		   			<html:text property="mobile" size="10" disabled="<%=flag%>" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
    		   			<%} %>
    	<%}else
    	{%>
      		<td align="left"><bean:message key="label.mobile"></bean:message><html:text property="mobile" size="10" disabled="<%=flag%>" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" onblur="number_valid()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
    	<%}%>
  
  	
    
    	<%if(obj!=null) 
    	{%>
    		<td align="center"><bean:message key="label.nameproof"></bean:message>
        	<html:select property="nameproof" disabled="<%=flag%>">
        	<html:option value="<%= obj.getNameProof() %>"></html:option>
        	</html:select></td>
    	<%}else
    	{ %>
    		<td align="center"><bean:message key="label.nameproof"></bean:message>
        		<html:select property="nameproof" disabled="<%=flag%>">
        		<core:forEach var="combo_nameproof_values" items="${requestScope.NameProof}">
        		<html:option value="${combo_nameproof_values}"><core:out value="${combo_nameproof_values}"></core:out></html:option>
        		</core:forEach>
       			</html:select></td>
    	<%}%>
    
 	</tr>
 
	<%System.out.println("**********Testing7***************"); %>

 	<tr>
    	<%address=(HashMap)request.getAttribute("Address");%>
    	<%if((address!=null)&&(obj!=null))
    	{%>
			<% Address addr=(Address)address.get(address_flag);%>
    		<td align="left"><bean:message key="label.mailid"></bean:message>
    		<%if(addr!=null){ %>
       			<html:text property="mailid" size="10" value="<%= addr.getEmail() %>" disabled="<%=flag%>" onkeypress="return alphanumericonly_id(this)" styleClass="formTextFieldWithoutTransparent" ></html:text></td> 
       			<%}else{ %>
       			<html:text property="mailid" size="10" readonly="true" onkeypress="return alphanumericonly_id(this)" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
       			<%} %>
    	<%}else
    	{%>
    		<td align="left"><bean:message key="label.mailid"></bean:message><html:text property="mailid" size="10" disabled="<%=flag%>" onkeypress="return alphanumericonly_id(this)" onchange="return email_check()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
    	<%}%>
     
    	<%if((address!=null)&&(obj!=null))
    	{%>
   			<% Address addr=(Address)address.get(address_flag);%>
    		<td align="left"><bean:message key="label.faxno"></bean:message>
    		<%if(addr!=null){ %>
    			<html:text property="faxno" size="10" value="<%= addr.getFax() %>" disabled="<%=flag%>" onkeyup="numberlimit(this,'11')"  styleClass="formTextFieldWithoutTransparent"></html:text>
    			<%}else{ %>
    			<html:text property="faxno" size="10" readonly="true" onkeyup="numberlimit(this,'11')"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
    			<%} %>
        <%}else
        {%> 
    		<td align="left"><bean:message key="label.faxno"></bean:message><html:text property="faxno" size="10" disabled="<%=flag%>" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
    	<%}%>   
   	</tr> 
   
   	<tr>
   		<%if(obj==null) {%>
   		<td align="center">Photo File:
   		<html:file property="theFile" onkeypress="return false;" size="10"></html:file></td>
   		<%}else{ %>
   		<td align="center">Photo File:
   		<html:file property="theFile"  size="10" disabled="<%=flag%>"></html:file></td>
   		<td align="center"><html:img src="C:\princess\Image.jpg" width="60" height="60"></html:img></td>
   		<%} %> 		
   	</tr>
   	
   	<tr>
   		<%if(obj==null) {%>
   		<td align="center">Signature File:
   		<html:file property="secfile" onkeypress="return false;" size="10"></html:file></td>
   		<%}else {%>
   		<td align="center">Signature File:
   		<html:file property="secfile" size="10" disabled="<%=flag%>"></html:file></td>
   		<%} %>
   	</tr>
   	
   <tr>  
     <table align="center">
     <tr>
     
     <%System.out.println("**********Testing 8***************"); %>
     
     <html:hidden property="button" value="error"/>
     <html:hidden property="ver_cid" styleId="vinay"/>
     <html:hidden property="forward" />
          
     <%if(Integer.parseInt(value.trim())==1){%>   
       <td align="right">
        <%if(newcuAccess!=null&&newcuAccess.toCharArray()[1]=='1'){%>
       <font size="50"><html:submit onclick="return setButtton('submit');" styleClass="ButtonBackgroundImage">SUBMIT</html:submit></font>
       <%}else{ %>
       <font size="50"><html:submit onclick="return setButtton('submit');" styleClass="ButtonBackgroundImage" disabled="true">SUBMIT</html:submit></font>
       <%} %>
       </td>
        <%}else if(Integer.parseInt(value.trim())==2){%>
        <td align="right"><font size="50"><html:submit onclick="return button_action('verify');"  styleClass="ButtonBackgroundImage">VERIFY</html:submit></font></td>
        <%}else if((Integer.parseInt(value.trim())==3)&&(flag2==null)){%>
        <td align="left"><font size="50"><html:submit onclick="return button_action('update');" styleClass="ButtonBackgroundImage">UPDATE</html:submit></font></td>
        <%}if(flag2!=null){
      	if(flag2.equalsIgnoreCase("1")){%>
        <td align="left"><font size="50"><html:submit onclick="return button_action('update_submit');" styleClass="ButtonBackgroundImage">UPDATE_SUBMIT</html:submit></font></td>
        <%}}%>
         <td align="center"><font size="50"><html:button property="clear" value="CLEAR" onclick="setclear()" styleClass="ButtonBackgroundImage"></html:button></font></td>
         </tr>
         </table>
         </tr>
</table>
</td>
</tr>
</table>  
 </html:form> 
 </body>
 </html>