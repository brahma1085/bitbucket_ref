<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.VehicleObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
</style>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" /><h2 class="h2"><center>Vehicle Details</center></h2>
  
</head>
<body>
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
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
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
        };
 function numbersOnly()
{
	 var cha = event.keyCode
   		if (  ( cha  > 47 && cha < 58  ) ) 
   		{
			return true ;
   		}
   		else
   		{
      		//alert("Please enter the numbers only ");
       		return false ;
   		}
   		};
   		 function numbersOnly1()
{
	 var cha = event.keyCode
   		if (  ( cha  >=47 && cha < 58  ) ) 
   		{
			return true ;
   		}
   		else
   		{
      		//alert("Please enter the numbers only ");
       		return false ;
   		}
   		};
   		function only_for_address(){
	
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) || (cha >= 65 && cha <=90)||(cha >= 44 && cha <=58)||cha==32||cha==35) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
	
function only_numbers_doublevalue() {
	
	var cha=event.keyCode;
	if((cha>=48 && cha<=57 )|| cha==46)
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;
        
function func_clear()
{

    var v = document.forms[0].elements;
    for(var i=0;i<=v.length;i++)
    {
       if(v[i].type =="text")
       {
          v[i].value = "";

        }
     }
 
};
   		
   		</script>

<html:form action="/Loans/LoanApplicationDE?pageId=5001">
<%! VehicleObject vecObj; %>
<% vecObj=(VehicleObject)request.getAttribute("VECHOBJ"); %>
<%System.out.println("This is from Vehicle Form"); 
String panelName; 
panelName=(String)request.getAttribute("panelName");%>
<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
<tr>
  <td>
    <h1 style="font:small-caps;font-style:normal;font-size:small;"><center>Vehicle Details</center></h1>
  </td>
</tr>
 <tr>
 <td>
 <center><b>Vehicle Details Form</b></center>
 </td>
 </tr>
   <tr>
       <td><font style="font-style:normal;font-size:12px"><bean:message key="label.make&type"></bean:message></font>
       	<%if(vecObj!=null){%>
       		<html:text property="maketype" size="10" value="<%=""+vecObj.getVehicleMake() %>" readonly="true"></html:text>
       	<%}else{ %>
			<html:text property="maketype" size="10"></html:text>
		<%} %>       	
		</td>
  </tr>
  <tr>
       <td>
       		<font style="font-style:normal;font-size:12px"><bean:message key="label.costVehicle"></bean:message></font>
       
       	<%if(vecObj!=null){%>
       		<html:text property="cost" size="10" value="<%=""+vecObj.getVehicleCost() %>" readonly="true"></html:text>
       	<%}else{ %>
       		<html:text property="cost" size="10" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
       	<%} %>
       		</td>
  </tr>
  <tr><td><font style="font-style:normal;font-size:12px"><bean:message key="label.nameDealer"></bean:message></font>
       
       		<%if(vecObj!=null){%>
       			<html:text property="dealername" size="10" value="<%= ""+vecObj.getAddressDealer() %>" readonly="true"></html:text>
       		<%}else{ %>
       			<html:text property="dealername" size="10"></html:text>
       		<%} %>
       	</td>
  </tr>
  <tr>
       <td>
       	<font style="font-style:normal;font-size:12px"><bean:message key="label.licenseno"></bean:message></font>
  
              		<%if(vecObj!=null){%>
       			<html:text property="licenseno" size="5" value="<%=""+vecObj.getLicenceNo() %>" readonly="true"></html:text>
				<html:text property="validity" size="10" value="<%=""+vecObj.getLicenceValidity() %>" readonly="true"></html:text>       		
       		<%}else{%>
       			<html:text property="licenseno" size="5" onkeypress="return numbersOnly()"></html:text>	
       			<html:text property="validity" size="10" onblur="return datevalidation(this)" onkeypress="return numbersOnly1()"></html:text></td>
 			<%} %>
 			
 			 </tr>		
  <tr>
       <td>
       		<font style="font-style:normal;font-size:12px"><bean:message key="label.permitno"></bean:message></font>
           	<%if(vecObj!=null){%>
       		   	<html:text property="permitno" size="5" value="<%=""+vecObj.getPermitNo() %>" readonly="true"></html:text>
       			<html:text property="permitvalid" size="10" value="<%=""+vecObj.getPermitValidity()%>" readonly="true"></html:text>
       		<%}else{ %>
       			<html:text property="permitno" size="5" onkeypress="return numbersOnly()"></html:text>
       			<html:text property="permitvalid" size="10" onblur="return datevalidation(this)" onkeypress="return numbersOnly1()"></html:text>
       		<%} %>
       		</td>
  </tr>
  <tr>
       <td ><font style="font-style:normal;font-size:12px"><bean:message key="label.vehicletype"></bean:message></font>
       <html:select property="vehicletype" >
       		<html:option value="0">Light Motor Vehicle</html:option>
       		<html:option value="1">Heavy Motor Vehicle</html:option>
       		</html:select></td>
  </tr>
  <tr>
       <td><font style="font-style:normal;font-size:12px"><bean:message key="label.vehiclefor"></bean:message></font>
       <html:select property="vehiclefor" >
       		<html:option value="0">Own use</html:option>
       		<html:option value="1">Hire</html:option>
       		</html:select></td>
  </tr>
  <tr>
       <td>
       	<font style="font-style:normal;font-size:12px"><bean:message key="label.vehicleuse"></bean:message></font>
       <%if(vecObj!=null){ 
       		System.out.println("Area is ========"+vecObj.getArea());%>
       		<html:text property="vehiclearea" size="10" value="<%=""+vecObj.getArea()%>" readonly="true"></html:text>
       <%}else {%>
       		<html:text property="vehiclearea" size="10" onkeypress="return only_for_address()"></html:text>
       	<%} %>
       </td>
       		
  </tr>
  <tr>
       <td><font style="font-style:normal;font-size:12px"><bean:message key="label.vehiclepark"></bean:message></font>
       <%if(vecObj!=null){ %>
       		<html:text property="vehicleparked" size="10" value="<%=""+vecObj.getAddressParking() %>" readonly="true"></html:text>
       	<%}else{ %>
       		<html:text property="vehicleparked" size="10" onkeypress="return only_for_address()"></html:text>
       	<%} %>
       	</td>
       	
  </tr>
  <tr>
       <td><font style="font-style:normal;font-size:12px"><bean:message key="label.othervehicle"></bean:message></font>
       
       <%if(vecObj!=null){ %>
       	<html:text property="othervehicle" size="10" value="<%=""+vecObj.getOtherDet() %>" readonly="true"></html:text>
       <%}else{ %>
       	<html:text property="othervehicle" size="10"></html:text>
       <%} %>
       </td>
  </tr>
 <tr> <html:hidden property="sysdate" /></tr>
  <tr>
     <td>
       <%if(vecObj!=null){ %>
       <html:submit styleClass="ButtonBackgroundImage" disabled="true">Submit</html:submit>
		<%}else{ %>       
      <input type="submit" value="SaveVehicleDet" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=SaveVehicleDet'" />
       <%} %></td></tr>
      <tr> <td><html:submit styleClass="ButtonBackgroundImage" onclick="func_clear()">Clear</html:submit>
       <html:submit styleClass="ButtonBackgroundImage">Update</html:submit></td>
  </tr>  
  </table>   
  </html:form>
  </body>
</html>