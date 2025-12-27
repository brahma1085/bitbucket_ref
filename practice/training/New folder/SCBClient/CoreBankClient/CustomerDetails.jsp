
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
<%@page import="com.scb.cm.actions.CustomerAction;"%>
<html>
  	<head>
  		<script type="text/javascript">
  		function setButtton(target)
  		{
  		 
  		 if(document.forms[1].name.value=="")	
  		{
  			alert("ENTER A VALID NAME");
  			return false;
  		}
  		else if(document.forms[1].dob.value=="")
  		{
  			alert("ENTER A VALID DATE")
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
		alert(target);
		
		document.forms[1].button.value=target;
		return true;
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
	       	
		return false
	}
	else {
	       	
		document.forms[1].submit()
	}   
	
    return true
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
  	};	
  	function fun_passport(ids)
  	{
  		if(ids.checked)
  	    {
  	    	alert("checked");
  	    	document.getElementById("passport_detail").style.display ='block';
  	    }
  	    else    
  	    {
  	    	alert("block");
  	    	document.getElementById("passport_detail").style.display = 'none';
  	    	
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

 		if ( (cha >= 97 && cha <=122) ) 
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
	function clear(target)
	{
		alert(target);
		if(target==1)
		{
			var ele=document.forms[1].elements;
  			for(var i=0;i<ele.length;i++)
  			{
  				if(ele[i].type=="text" || ele[i].type=="textarea")
  				{
  			  		ele.value="";
  				}
  		
  			}
		}
	}
  	</script>
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
	<h2 class="h2"><center>New Customer</center></h2>
  	</head>    
  	<body onload="return clear(<%=request.getAttribute("clear")%>)"  bgcolor="beige" >  
       
       
  	<html:form action="/custdetails?pageId=0">
     
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
  		if((Integer.parseInt(value.trim())==3)||(Integer.parseInt(value.trim())==2))
  		{System.out.println("-----------------inside if condition");
  			flag=true; 
  		}else
  		{  
  			System.out.println("-----incustomerdetail------------inside if condition");
  		flag=false; 
  		}
  	}%>
  
  	<%! CustomerMasterObject obj; %>
  	<% obj=(CustomerMasterObject)request.getAttribute("Detail");%>
    <%if(obj!=null){ 
     System.out.println("pp number---------->"+obj.getPassPortNumber());
     System.out.println("pp issue date------>"+obj.getPPIssueDate());
     System.out.println("pp exp date-------->"+obj.getPPExpiryDate());
    // System.out.println("category----------->"+obj.getCategory());
     
     
     }%>
   
  	<%!String  flag2,Introducer_Name;%>
  	
  	<%flag2=(String)request.getAttribute("update_flag_value");%>
  	
  	<%Introducer_Name=(String)request.getAttribute("Introducer_Name");%>
  	
  	
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
       
     
<table  cellspacing="5" style="border: thin solid black;"> 
  		 
  		    <tr></tr>
  		    <tr></tr>
  		    
  		    
     		<td> 

     <table align="right" class="txtTable"><tr><td><font  color="blue"><bean:message key="label.mandatory"></bean:message></font></td></tr></table>
   
     
     
  	<table class="txtTable">
       
          <tr> 
    	    <core:choose> 
        		<core:when test="${empty requestScope.Detail}">
        			<td align="right"><font color="blue"><bean:message key="label.custname"></bean:message></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            		<html:text property="name" styleId="myname" size="10"  disabled="<%=flag%>" onblur="ValidName(this.value)" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
            	</core:when>
        		<core:otherwise> 
        			<td><font color="blue"><bean:message key="label.custname"></bean:message></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       				<html:text property="name" styleId="myname" size="10" value="<%= obj.getFirstName()%>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
       			</core:otherwise> 
       		</core:choose> 
                    
           
        	<core:choose>
        		<core:when test="${empty requestScope.Detail}">
        			<td><html:text property="midname" styleId="mymidname" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
        		</core:when>
         		<core:otherwise>
        			<td><html:text property="midname" styleId="mymidname" size="10" value="<%=obj.getMiddleName()%>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
         		</core:otherwise>
        	</core:choose>
        
        	<core:choose> 
        		<core:when test="${empty requestScope.Detail}">
         			<td><html:text property="lastname" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
        		</core:when>
        		<core:otherwise>
        			<td><html:text property="lastname" size="10" value="<%= obj.getLastName()%>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
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


	<table align="left" class="txtTable">
   		<tr>
      
      		<core:choose>
          		<core:when test="${empty requestScope.Detail}">
           			<td align="right"><bean:message key="label.fathername"></bean:message>
           			<html:text property="fathername" styleId="myfathname" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:when>
          		<core:otherwise>
           			<td align="right"><bean:message key="label.fathername"></bean:message>
           			<html:text property="fathername" styleId="myfathname" size="10" value="<%= obj.getFatherName()%>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:otherwise>
           	</core:choose>
         
 
      		<core:choose>
          		<core:when test="${empty requestScope.Detail}">
          			<td align="right"><bean:message key="label.mothername"></bean:message><html:text property="mothername" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:when>
          		<core:otherwise>
           			<td align="right"><bean:message key="label.mothername"></bean:message><html:text property="mothername" size="10" value="<%= obj.getMotherName() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:otherwise>
          	</core:choose>
     
     
     		<core:choose>
          		<core:when test="${empty requestScope.Detail}">
           			<td align="right"><bean:message key="label.maritalstatus"></bean:message>
          			<html:select property="marital" disabled="<%=flag%>">
          		<core:forEach var="combo_maritial_values" items="${requestScope.MaritalStatus}">
          			<html:option value="${combo_maritial_values}"><core:out value="${combo_maritial_values}"></core:out></html:option>
          		</core:forEach>
          			</html:select>
          			</td> 
          		</core:when>
          		<core:otherwise>
          			<td align="right"><bean:message key="label.maritalstatus"></bean:message>
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
           			<td align="right"><bean:message key="label.sposename"></bean:message><html:text property="spousename" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
          		</core:when>
          		<core:otherwise>
          			<td align="right"><bean:message key="label.sposename"></bean:message><html:text property="spousename" size="10" value="<%= obj.getSpouseName() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:otherwise>
          	</core:choose>
    
            <core:choose>
	            <core:when test="${empty requestScope.Detail}">
    	   	        <td align="right"><bean:message key="label.nationality"></bean:message>
    	   	        <html:text property="nation" size="10" value="INDIAN" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent" disabled="<%=flag%>"></html:text></td>
          	    </core:when>
          	    <core:otherwise>
          		    <td align="right"><bean:message key="label.nationality"></bean:message><html:text property="nation" size="10" value="<%= obj.getNationality() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          	    </core:otherwise>
            </core:choose>
    
    
    	    <core:choose>
          		<core:when test="${empty requestScope.Detail}">
           			<td align="right"><bean:message key="label.religion"></bean:message><html:text property="religion" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:when>
          		<core:otherwise>
          			<td align="right"><bean:message key="label.religion"></bean:message><html:text property="religion" size="10" value="<%= obj.getReligion() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:otherwise>
            </core:choose>
    
    
    
 		</tr>

	 	<tr></tr>

 		<tr>
     	    <core:choose>
            	<core:when test="${empty requestScope.Detail}">
        			<td align="right"><font color="blue"><bean:message key="label.dob"></bean:message></font><html:text property="dob" size="10" disabled="<%=flag%>"  onchange="ValidateForm()"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
        		</core:when>
          		<core:otherwise>
          			<td align="right"><font color="blue"><bean:message key="label.dob"></bean:message></font><html:text property="dob" size="10" value="<%= obj.getDOB() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          		</core:otherwise>
          	</core:choose>
          
          <%if(AgeAttribute!=null){%>
              <td align="right">
              <bean:message key="label.age"></bean:message>
              <html:text property="txt_age"  disabled="<%=flag%>" value="<%= AgeAttribute %>" size="10" style="border:transparent;background-color:beige;color:red" ></html:text></td>
            <%}else if(AGE_ATT!=null){%>
            <td align="right">
            <bean:message key="label.age"></bean:message><html:text property="txt_age"  value="<%=AGE_ATT%>"  size="10" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text></td>
            <%}%>
            
        	<td align="right"><bean:message key="label.sex"></bean:message>
        	<html:select property="sex" disabled="<%=flag%>">
        	<html:option value="male">male</html:option>
        	<html:option value="female">female</html:option>
        	</html:select>
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
			 
			   <td  align="right">
			        <bean:message key="label.type"/>
			   		<html:select property="txt_gurtype">
					<html:option value="Lawfull">Lawful</html:option>
					<html:option value="Lawfull">Natural</html:option>
					</html:select>
			    </td>
				<td align="right">
				<font color="blue"><bean:message key="label.custname"/></font>
				<html:text property="txt_guarname" size="10" styleClass="formTextFieldWithoutTransparent"></html:text>	
				</td>
				
				<td  align="right">
					<font color="blue"><bean:message key="label.address"/></font>
					<html:textarea property="txt_guaraddress" rows="2" cols="15" styleClass="formTextFieldWithoutTransparent"></html:textarea>
					
				</td>
			<%}
		} %>
		
	</tr>
	
	<tr style="border-color: maroon;border-style: dotted">
	<%if(GuardianDetail!=null){
			if(GuardianDetail.equalsIgnoreCase("GuardianDetail"))
			{%>
		    <td  align="right">
			        <bean:message key="label.sex"/>
			   		<html:select property="txt_gursex">
					<html:option value="male">Male</html:option>
					<html:option value="female">Female</html:option>
					</html:select>
			    </td>
				<td align="right">
				<font color="blue"><bean:message key="label.Courtdate"/></font>
				<html:text property="txt_guarcourtno" size="10" styleClass="formTextFieldWithoutTransparent"></html:text>	
				</td>
				
				<td  align="right">
					<font color="blue"><bean:message key="label.date"/></font>
				<html:text property="txt_guardate" size="10" styleClass="formTextFieldWithoutTransparent"></html:text>	
				
				</td>
		<%}
		}%>
		
	</tr>
 	<tr></tr>

 	<tr>
        <core:choose>
        	<core:when test="${empty requestScope.Detail}">
           		<td align="right"><bean:message key="label.castedet"></bean:message><html:text property="caste" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          	</core:when>
          	<core:otherwise>
          		<td align="right"><bean:message key="label.castedet"></bean:message><html:text property="caste" size="10" value="<%= obj.getCaste() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          	</core:otherwise>
        </core:choose>
    
        <core:choose>
        	<core:when test="${empty requestScope.Detail}">
          		<td align="right"> <bean:message key="label.sc/st"></bean:message>
    			<html:select property="scst" disabled="<%=flag%>">
    		<core:forEach var="sc_st_values" items="${requestScope.caste}">
    			<html:option value="${sc_st_values}"><core:out value="${sc_st_values}"></core:out></html:option>
    		</core:forEach>
    			</html:select>
    			</td>
          	</core:when>
          	<core:otherwise>
			    <td align="right"><bean:message key="label.sc/st"></bean:message>
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
          		<td align="right"><bean:message key="label.Intro"></bean:message><html:text property="intro" size="10" disabled="<%=flag%>" onchange="submit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          	</core:when>
          	<core:otherwise>
          		<td align="right"><bean:message key="label.Intro"></bean:message><html:text property="intro" size="10" value="<%= obj.getIntroducerId() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
          	</core:otherwise>
        </core:choose>
     
   
     
       
    	<%if(Cust_Name!=null){%>
             <td align="right"><html:text property="introid2"  disabled="<%=flag%>" value="<%=""+Cust_Name%>" size="20" style="border:transparent;background-color:beige;color:red"></html:text></td>
           <%}else if(Introducer_Name!=null){%>
 			<td align="right"><html:text property="introid2" value="<%=Introducer_Name%>" size="20" style="border:transparent;background-color:beige;color:red"></html:text></td>       
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
	  
	   <table>
	    
	    
	    <tr>
	    <td align="right"> 
    	<core:choose>
         	<core:when test="${empty requestScope.Detail}">
          		<bean:message key="label.passport"></bean:message><html:text property="passportno" size="10" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text>
          	</core:when>
          	<core:otherwise>
          		<bean:message key="label.passport"></bean:message><html:text property="passportno" size="10" value="<%= obj.getPassPortNumber() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text>
          	</core:otherwise>
        </core:choose>
       </td>
        </tr>
      
      <tr>
      <td align="right">  
    	<core:choose>
        	<core:when test="${empty requestScope.Detail}">
          		<bean:message key="label.issudate"></bean:message><html:text property="issuedate" size="10" disabled="<%=flag%>" onchange="ValidateForm()" styleClass="formTextFieldWithoutTransparent"></html:text>
          	</core:when>
          	<core:otherwise>
          		<bean:message key="label.issudate"></bean:message><html:text property="issuedate" size="10" value="<%= obj.getPPIssueDate() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text>
          	</core:otherwise>
        </core:choose>
      </td>
       </tr>
        
      <tr>
       <td align="right"> 
	    <core:choose>
    	     <core:when test="${empty requestScope.Detail}">
         		 <bean:message key="label.expirydate"></bean:message><html:text property="expirydate" size="10" disabled="<%=flag%>" onchange="ValidateForm()" styleClass="formTextFieldWithoutTransparent"></html:text>
          	 </core:when>
          	 <core:otherwise>
          		 <bean:message key="label.expirydate"></bean:message><html:text property="expirydate" size="10" value="<%= obj.getPPExpiryDate() %>" disabled="<%=flag%>" styleId="ppexpdate" styleClass="formTextFieldWithoutTransparent"></html:text>
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
          		<td align="right"><bean:message key="label.occupation"></bean:message>
     			<html:select property="occupation" disabled="<%=flag%>">
     		<core:forEach var="occ_values" items="${requestScope.occupation}">
     			<html:option value="${occ_values}"><core:out value="${occ_values}"></core:out></html:option>
     		</core:forEach>
     			</html:select></td>
          	</core:when>
          	<core:otherwise>
          		<td align="right"><bean:message key="label.occupation"></bean:message>
    			<html:select property="occupation" disabled="<%=flag%>">
    			<html:option value="<%= obj.getOccupation()%>"></html:option>
    			</html:select></td>
          	</core:otherwise>
        </core:choose>
 
     
    	<core:choose>
        	<core:when test="${empty requestScope.Detail}">
          	<td align="right"><html:select property="pvt_sector" disabled="<%=flag%>">
        <core:forEach var="sub_occ_values"  items="${requestScope.sub_occ}">
        	<html:option value="${sub_occ_values}"><core:out value="${sub_occ_values}"></core:out></html:option>
       	</core:forEach>
        	</html:select></td>
        </core:when>
        <core:otherwise>
          	<td align="right"><html:select property="pvt_sector" disabled="<%=flag%>">
         	<html:option value="<%=obj.getSubOccupation()%>"></html:option>
    		</html:select></td>
         </core:otherwise>
         </core:choose>
 
    
    	 <core:choose>
             <core:when test="${empty requestScope.Detail}">
             	 <td align="right"><bean:message key="label.pan"></bean:message><html:text property="pan" disabled="<%=flag%>"  size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>      
             </core:when>
             <core:otherwise>
           		 <td align="right"><bean:message key="label.pan"></bean:message><html:text property="pan" value="<%= obj.getPanno() %>" disabled="<%=flag%>"  size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>          
          	 </core:otherwise>
         </core:choose>
       
    
	</tr>
 
 
    <!--<tr>
    <td><bean:message key="label.passpoerder"></bean:message>
    <html:checkbox property="passportdetail" onclick="submit()"></html:checkbox></td>
    </tr>
  
 
 

	--><tr></tr>
 
              
	<tr>   
   		<%! AddressTypesObject AddressType[];%>
   		<%! Vector vect_add=new Vector();%>
   		<% AddressType=(AddressTypesObject[])request.getAttribute("AddressType"); %>
     
   
   		<% if((AddressType!=null)&& (obj!=null))
   		{ %>
   			<td align="right"><font color="blue"><bean:message key="label.address"></bean:message></font>
   			<html:select property="addrestype"  onchange="submit()" tabindex="0" >
   			<% for(int i=0;i<AddressType.length;i++)
   			{%> 
   				<%vect_add.addElement(AddressType[i].getAddressDesc());%>
   				<html:option value="<%=""+AddressType[i].getAddressCode()%>"><%=""+AddressType[i].getAddressDesc()%></html:option>
   			<%}%>
   			</html:select></td>
   		<%}else
   			{ %> 
   			<td align="right"><font color="blue"><bean:message key="label.address"></bean:message></font>
   			<td>
   				<html:select property="addrestype" onchange="submit()" tabindex="0">
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
    	 <td><%System.out.println("middle text area"+address_flag); %>
     		 <% Address addr=(Address)address.get(address_flag);%>
     		 <%if(addr!=null){ %>
     		<html:textarea property="addressarea"  styleClass="formTextFieldWithoutTransparent" value="<%=addr.getAddress()%>" disabled="<%=flag%>"></html:textarea></td>
     		<%} %>
     	<%}else
     	{ %> 
     		<td><html:textarea property="addressarea" styleClass="formTextFieldWithoutTransparent" disabled="<%=flag%>" value="${addr2}"></html:textarea></td>
     		<td><input type="submit" value="Save" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Save'" /></td>
     	<%}%> 
	</tr>
    <tr></tr>
    
     
         
	<tr>  
	   <%if((address!=null)&&(obj!=null))
	   {%>
     		<% Address addr=(Address)address.get(address_flag);%>
         	<td align="right"><bean:message key="label.country"></bean:message>   
         	<html:select property="country" disabled="<%=flag%>">
         	<html:option value="<%=addr.getCountry()%>"></html:option>   	
         	</html:select></td>
     	<%}else
     	{%>
         	<td align="right"><bean:message key="label.country"></bean:message>
         	<html:select property="country" disabled="<%=flag%>">
         	<core:forEach var="Combo_country_values" items="${requestScope.Country}">
         	<html:option value="${Combo_country_values}"><core:out value="${Combo_country_values}"></core:out></html:option>
         	</core:forEach>
         	</html:select></td>
      	<%}%>
    
     
     
     	<%address=(HashMap)request.getAttribute("Address");%>
     	<%if((address!=null)&&(obj!=null))
     	{%>
      		<%addr_ittr=address.values().iterator();%>
     		<td align="right"><bean:message key="label.State"></bean:message>
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
        	<td align="right"><bean:message key="label.State"></bean:message>
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
      <td align="right"><bean:message key="label.city"></bean:message>
      
      <html:text property="city" size="10" styleClass="formTextFieldWithoutTransparent" value="<%=addr.getCity()%>" disabled="<%=flag%>"></html:text></td>
      <%}else{ %>  
      <td align="right"><bean:message key="label.city"></bean:message>
      <html:text property="city" size="10" styleClass="formTextFieldWithoutTransparent" disabled="<%=flag%>" onkeypress="return only_alpha()"></html:text></td>
      <%}%>
      
      
     <%address=(HashMap)request.getAttribute("Address");%>
     <%if((address!=null)&&(obj!=null)){%>
     <% Address addr=(Address)address.get(address_flag);%>
     <td align="right"><bean:message key="label.pin"></bean:message>
     <html:text styleClass="formTextFieldWithoutTransparent" property="pin" size="10" value="<%=addr.getPin()%>" disabled="<%=flag%>"></html:text></td>
     <%}else{ %>    
     <td align="right"> <bean:message key="label.pin"></bean:message><html:text property="pin" size="10" disabled="<%=flag%>"  onkeypress="return only_numbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
     <%}%>
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


         
 	<tr>
    	<%if((address!=null)&&(obj!=null))
    	{%>
    		<% Address addr=(Address)address.get(address_flag);%>
    		<td align="right"><bean:message key="label.mobile"></bean:message>
    		   			<html:text property="mobile" size="10" value="<%=addr.getMobile() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>    
    	<%}else
    	{%>
      		<td align="right"><bean:message key="label.mobile"></bean:message><html:text property="mobile" size="10" disabled="<%=flag%>" onkeypress="return mobile_validate(this)" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
    	<%}%>
  
  
  
    	<%address=(HashMap)request.getAttribute("Address");%>
    	<%if((address!=null)&&(obj!=null))
    	{%>
  		  <%Address addr=(Address)address.get(address_flag);%>
    	  <td align="right"><bean:message key="label.landline"></bean:message>
     	      <html:text property="phnum1" size="10" value="<%= addr.getPhno() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
    	<%}else
    	{%>
    	  <td align="right"><bean:message key="label.landline"></bean:message><html:text property="phnum1" size="10" disabled="<%=flag%>" onkeypress="return only_numbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
    	<%}%>
    
    	<%if(obj!=null) 
    	{%>
    		<td align="right"><bean:message key="label.nameproof"></bean:message>
        	<html:select property="nameproof" disabled="<%=flag%>">
        	<html:option value="<%= obj.getNameProof() %>"></html:option>
        	</html:select></td>
    	<%}else
    	{ %>
    		<td align="right"><bean:message key="label.nameproof"></bean:message>
        		<html:select property="nameproof" disabled="<%=flag%>">
        		<core:forEach var="combo_nameproof_values" items="${requestScope.NameProof}">
        		<html:option value="${combo_nameproof_values}"><core:out value="${combo_nameproof_values}"></core:out></html:option>
        		</core:forEach>
       			</html:select></td>
    	<%}%>
    
 	</tr>
 


 	<tr>
    	<%address=(HashMap)request.getAttribute("Address");%>
    	<%if((address!=null)&&(obj!=null))
    	{%>
			<% Address addr=(Address)address.get(address_flag);%>
    		<td align="right"><bean:message key="label.mailid"></bean:message>
       			<html:text property="mailid" size="10" value="<%= addr.getEmail() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td> 
    	<%}else
    	{%>
    		<td align="right"><bean:message key="label.mailid"></bean:message><html:text property="mailid" size="10" disabled="<%=flag%>" onchange="return email_check()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
    	<%}%>
     
    	<%if((address!=null)&&(obj!=null))
    	{%>
   			<% Address addr=(Address)address.get(address_flag);%>
    		<td align="right"><bean:message key="label.faxno"></bean:message>
    			<html:text property="faxno" size="10" value="<%= addr.getFax() %>" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
        <%}else
        {%> 
    		<td align="right"><bean:message key="label.faxno"></bean:message><html:text property="faxno" size="10" disabled="<%=flag%>" onkeypress="return only_numbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
    	<%}%>   
   	</tr> 
   	<tr>
   		<td>FileName:<html:file property="theFile"></html:file></td>
   	</tr>
   	
    <tr>  
     <html:hidden property="button" value="error"/>
     <html:hidden property="ver_cid" styleId="vinay"/>
          
     <%if(Integer.parseInt(value.trim())==1){ %>   
     
       <td align="right"><font size="50"> <html:submit onclick="return setButtton('submit');" styleClass="ButtonBackgroundImage">SUBMIT</html:submit></font></td>
        <%}else if(Integer.parseInt(value.trim())==2){%>
        <td align="right"><font size="50"><html:submit onclick="return button_action('verify');"  styleClass="ButtonBackgroundImage">VERIFY</html:submit></font></td>
        <%}else if((Integer.parseInt(value.trim())==3)&&(flag2==null)){%>
        <td align="right"><font size="50"><html:submit onclick="button_action('update');" styleClass="ButtonBackgroundImage">UPDATE</html:submit></font></td>
        <%}if(flag2!=null){
      	if(flag2.equalsIgnoreCase("1")){%>
        <td align="right"><font size="50"><html:submit onclick="button_action('update_submit');" styleClass="ButtonBackgroundImage">UPDATE_SUBMIT</html:submit></font></td>
        <%}}%>
         <td align="right"><font size="50"><html:button property="clear" value="CLEAR" onclick="clear_textfield()" styleClass="ButtonBackgroundImage"></html:button></font></td>
         </tr>      
</table> 
   
 
  </td>  
 </table>  
   
 </html:form> 
 </body>
 </html>