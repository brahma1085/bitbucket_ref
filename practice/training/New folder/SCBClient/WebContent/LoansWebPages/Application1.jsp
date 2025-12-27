<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.DirectorMasterObject"%>
<%@page import="com.scb.designPatterns.ShareDelegate"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.LoanMasterObject"%>
<html>
<head>

<script type="text/javascript">

function check_box(){
if(document.getElementById("relativetodir").checked==true)
	{
		document.forms[0].dirrelations.disabled = false;
			document.forms[0].dirdetails.disabled = false;
		document.getElementById("dirrelations").focus();
		//return true;
		}
		else{
		document.forms[0].dirrelations.disabled = true;
		document.forms[0].dirdetails.disabled = true;
		document.getElementById("paymtmode").focus();
			//return false;
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

                          alert (  " Date format should be:DD/MM/YYYY " );
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
            			alert(" Year should be less than or equal to"+dds[2]+" !");
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
function combo_box(){
if(document.getElementById("paymtmode").value=='Cash')
	{
		document.forms[0].payaccno.disabled = true;
		document.forms[0].payactype.disabled = true;
		document.getElementById("interesttype").focus();
		return true;
		}
		else if(document.getElementById("paymtmode").value=='PayOrder')
	{
		document.forms[0].payaccno.disabled = true;
		document.forms[0].payactype.disabled = true;
		document.getElementById("interesttype").focus();
		return true;
		}
		else{
		document.forms[0].payaccno.disabled = false;
				document.forms[0].payactype.disabled = false;
		document.getElementById("payactype").focus();
			return true;
		}
};


function only_numbers_doublevalue() {
	
	var cha=event.keyCode;
	if((cha>=48 && cha<=57) || cha==46)
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;

function numbersOnly1(eve){
         var cha = event.keyCode
				if (  ( cha  >= 47 && cha < 58) ) {
				return true ;
                
            	}
            else{
              	//alert("Please enter the numbers only!! ");
              	return false ;
            }
        }; 



function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58) ) {
				return true ;
                
            	}
            else{
              	//alert("Please enter the numbers only!! ");
              	return false ;
            }
        }; 

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
   








</script>

<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<% System.out.println("******************Application1.jsp**************************** "); %>

	<%	String ApplicationDate,pay_mode; 
		LoanMasterObject lMasterObject;
		ShareDelegate sharedel=null;
	%>
	<%! ModuleObject module_object[]=null; %>
	<% DirectorMasterObject DirRelations[];  %>
	<%  ApplicationDate=(String)request.getAttribute("ApplicationDate");
		lMasterObject = (LoanMasterObject)request.getAttribute("LoanDetails");
		module_object=(ModuleObject[])request.getAttribute("pay_actype");
		//pay_mode=(String)request.getAttribute("pay_mode");
	%>
	<%String disable; %>
<%disable=(String)request.getAttribute("disable"); %>
<%boolean flag=true; %>
<%if(disable!=null){ 
flag=true;
}else{
flag=false;
}
%>


</head>

<body >

<html:form action="/Loans/LoanApplicationDE?pageId=5001">


<html:hidden property="sysdate" />
<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">

<tr>
  <td>
    <center><h2 class="h2">Application</h2></center>
  </td>
</tr>
<tr>
   <%if(lMasterObject!=null){ %>
   <td>
    <font style="font-style:normal;font-size:12px"><bean:message key="label.applnserial"></bean:message></font>
    <html:text property="appln_no" value="<%=""+lMasterObject.getApplicationSrlNo()%>" size="5" readonly="true" ></html:text>
    </td>
   
   <%} else{ %>
    <td>
    <font style="font-style:normal;font-size:12px"><bean:message key="label.applnserial"></bean:message></font>
    <html:text property="appln_no" size="5" onkeypress=" return numbersOnly()"  onkeyup="numberlimit(this,'11')"></html:text>
    </td>
    <%} %>
</tr>
<tr>  
	<%if(lMasterObject!=null){ %>  
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.appndate" ></bean:message></font>
    <html:text property="appndate" size="10" value="<%=""+lMasterObject.getApplicationDate()%>" readonly="true"  ></html:text></td>
    <%}else { %>
     <td><font style="font-style:normal;font-size:12px"><bean:message key="label.appndate" ></bean:message></font>
    <html:text property="appndate" size="10" onkeypress=" return numbersOnly1()" onblur="return datevalidation(this)"></html:text></td>
    <%} %>
</tr>
<tr>  
	<%if(lMasterObject!=null){ %>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.reqamount"></bean:message></font>
    <html:text property="reqamount" value="<%=""+lMasterObject.getRequiredAmount()%>" size="10" readonly="true" ></html:text></td>
    <%}else{ %>
     <td><font style="font-style:normal;font-size:12px"><bean:message key="label.reqamount"></bean:message></font>
    <html:text property="reqamount" size="10" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text></td>
    <%} %>
</tr>

<tr>  
<%if(lMasterObject!=null){ %>  
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.reltodir"></bean:message></font>
    <html:checkbox property="relativetodir"  value="true" disabled="true"></html:checkbox></td>
<%}else{ %>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.reltodir"></bean:message></font>
    <html:checkbox property="relativetodir" value="false" onclick="return check_box()"></html:checkbox></td>
<%} %>    
</tr>
<% String[] dirrelations=(String[])request.getAttribute("Dirrelations"); %>

<tr>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.dirrelations"></bean:message></font>
        <html:select property="dirrelations" >
        
        <%if(dirrelations!= null){
        	for(int i=0;i<dirrelations.length;i++){
        	%>
            <html:option value="<%=""+dirrelations[i]%>"></html:option>
                        <% }}%></html:select></td>
 </tr>

<% String dirdetails[][]=(String[][])request.getAttribute("DirDetails"); %>
<tr>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.dirdetails"></bean:message></font>
        <html:select property="dirdetails" >
        <%if(dirdetails!= null){
        	for(int i=0;i<dirdetails.length;i++){
        	%>
            <html:option value="<%=""+dirdetails[i][0]%>"><%=""+dirdetails[i][2]%></html:option>
        <% }}%>
        </html:select></td>
 </tr>



<tr>
<%if(lMasterObject!=null){ %>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.paymentmode1"></bean:message></font>
        <html:select property="paymtmode" disabled="true">
        <%if(lMasterObject.getPayMode().equalsIgnoreCase("C")){ %>
        	<html:option value="C">Cash</html:option>
        <%}else if(lMasterObject.getPayMode().equalsIgnoreCase("T")){ %>
        	<html:option value="T">Transfer</html:option>
        <%}else{ %>
        	<html:option value="PO">PayOrder</html:option>
        <%} %>
        </html:select>
    </td>
  <%}else{ %>
   <td><font style="font-style:normal;font-size:12px"><bean:message key="label.paymentmode1"></bean:message></font>
        <html:select property="paymtmode" onblur="combo_box()">
        <html:option value="C">Cash</html:option>
        <html:option value="T">Transfer</html:option>
        <html:option value="PO">PayOrder</html:option></html:select>
    </td>
  
  <%} %>  
 </tr>


 
 <tr>
 		
 			<td><bean:message key="label.paymentactype"></bean:message>
 			
			<html:select  property="payactype" styleClass="formTextFieldWithoutTransparent">
           
            <% 
            	for(int i=0;i<module_object.length;i++)
			    {
					System.out.println("module_object"+ module_object[i].getModuleAbbrv());
			 %>	   			    			    	 
			    	<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			 <%	} %>
			        	 
			</html:select>
			
		  </td>
            
</tr>   
<tr>  
<%if(lMasterObject!=null){ %>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.payaccno"></bean:message></font>
    <html:text property="payaccno" size="10" value="<%=""+lMasterObject.getPaymentAccno() %>" readonly="true"></html:text></td>
<%}else{ %> 
 <td>fl<font style="font-style:normal;font-size:12px"><bean:message key="label.payaccno"></bean:message></font>
    <html:text property="payaccno" size="10" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')" onblur="submit()"></html:text></td>

<%} %>   
</tr>         
<tr>
<%if(lMasterObject!=null){ %>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.interesttype"></bean:message></font>
        <html:select property="interesttype" disabled="true" >
        <%if(lMasterObject.getInterestType()==0){ %>
      	  <html:option value="0">Reducing Balance</html:option>
        <%}else{ %>
        	<html:option value="1">EMI</html:option>
    	<% } %>
    	</html:select>
    </td>
 <% }else{ %>
 	<td><font style="font-style:normal;font-size:12px"><bean:message key="label.interesttype"></bean:message></font>
        <html:select property="interesttype">
        <html:option value="1">EMI</html:option>
        <html:option value="0">Reducing Balance</html:option></html:select>
    </td>
 <%} %>   
 </tr>
<tr>   
	<%if(lMasterObject!=null){ %> 
    	<td><font style="font-style:normal;font-size:12px"><bean:message key="label.interestcalctype"></bean:message></font>
        <html:select property="interestcalctype" disabled="true">
        <%if(lMasterObject.getInterestRateType()==0){ %>
        	<html:option value="0">Fixed</html:option>
        <%}else{ %>
        	<html:option value="1">Floating</html:option>
        <%} %>
        </html:select>
        </td>
    <%} else{ %>
    	<td><font style="font-style:normal;font-size:12px"><bean:message key="label.interestcalctype"></bean:message></font>
        <html:select property="interestcalctype">
        <html:option value="0">Fixed</html:option>
        <html:option value="1">Floating</html:option>
        </html:select></td>
    <%} %>
</tr>
<tr>
<%if(disable!=null) {%>                                                                                           
    <td><input type="submit"  value="SaveApplication" name="method" class="ButtonBackgroundImage"  disabled="<%=flag %>"/>
    <%}else{ %>
     <td><input type="submit"  value="SaveApplication" name="method" class="ButtonBackgroundImage"  onclick="location.href='?method=SaveApplication'"/>
     <%} %>
       <html:reset  property="clear" value="Clear" styleClass="ButtonBackgroundImage" onclick="func_clear()"></html:reset>
       <html:submit  value="Update" styleClass="ButtonBackgroundImage"></html:submit></td>  
</tr>
</table>
</html:form>
</body>
</html>