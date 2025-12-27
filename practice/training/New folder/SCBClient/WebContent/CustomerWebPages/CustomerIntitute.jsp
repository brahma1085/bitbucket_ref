<%@ page import="com.scb.cm.actions.CustomerAction" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt" %>
<%-- 
  @author swetha
  Date: Nov 15,2007
  Time: 3:50:10 AM
  To change this template use File | Settings | File Templates. 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="java.util.HashMap"%>         
<%@page import="java.util.Iterator"%>
<%@page import="masterObject.general.Address"%>
<%@page import="masterObject.general.AddressTypesObject"%>
<%@page import="java.util.Vector"%>
<%@page import="com.scb.cm.forms.Customerform"%>
<html>
<head>

<script type="text/javascript">
  		function setButtton(target)
  		{
  		 if(document.forms[1].instname.value=="")
  		 {
  		 	alert("ENTER THE INSTITUTE NAME");
  		 	return false;
  		 }
  		 else
  		 {
			document.forms[1].button.value=target;
			return true;
		 } 
			
  		};
  		
  		function clear_textfield()
  	{
  		    var ele=document.forms[1].elements;
  			for(var i=0;i<ele.length;i++)
  			{
  			if((ele[i].type=="text" || ele[i].type=="textarea"))
  				
  				{
  			  		ele[i].value="";
  				}
  				if(ele[i].type=="hidden")
  				{
  			  		ele[i].value="";
  				}
  		
  			}
  			var ele1=document.forms[0].elements;
  			for(var j=0;i<ele1.length;j++)
  			{
  			alert("Clearing all");
  				if(ele1[j].type=="text")
  				{
  			  		ele1[j].value="";
  				}
  				if(ele1[j].type=="hidden")
  				{
  			  		ele1[j].value="";
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
  		/*function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) ) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};*/
  		function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) || (cha >= 65 && cha <=90) ||cha==32||cha==46) 
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
			alert("plz enter the valid number");
			return false;
		}
		else
		{
			return true;
		}
	}
	  
	function number_valid()
	{
		if(document.forms[1].mobile.value.length<11)
		{
			alert("plz enter the valid number");
			return false;
		}
		else
		{
			return true;
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

function Show()
  		{
  		   if(document.getElementById("totaltesting").value!=0)
	       {
		    alert(document.getElementById("totaltesting").value);	
		    clear_textfield();
		   }
  		}
  		
  		
  		
  		
</script>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
	<h2 class="h2"><center>New Institute Form</center></h2>
</head> 
<body bgcolor="beige" onload="Show()"> 

  <html:form action="/custinst?pageId=1">           
   
   
   
   
  <%! String value; %> 
  <%value=(String)request.getAttribute("submit");%>   
 
 <%! boolean flag;%>
  
  <%if((Integer.parseInt(value)==3)||(Integer.parseInt(value)==2) ){%>
   <%flag=true; %>
   <%}else{ %>  
   <% flag=false; %>
   <%} %> 
  
   <%!String  flag2;%>
   <%flag2=(String)request.getAttribute("update_flag_value");%>
   <%System.out.println("update flag value in customer institute===="+flag2); %>
  
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
   
   
   
   
   
   
 
  <%! CustomerMasterObject obj; %>
  <% obj=(CustomerMasterObject)request.getAttribute("Detail");%> 
  <%System.out.println("object value in Institute page==============="+obj); %>
  
  <%! HashMap address; %>
  <% address=(HashMap)request.getAttribute("Address"); %>
  <%! Iterator addr_ittr;%>      
  
<%! Customerform custform; %>
<% custform=(Customerform)request.getAttribute("customerform"); %>  
<%System.out.println("customer form value in institute page==="+custform); %>


<%!String update_value;%>
    <% update_value=(String)request.getAttribute("button_values");%>
    
 <%! String Cust_Name; %>
 <%Cust_Name=(String)request.getAttribute("Cust_Name");%>
 <%System.out.println("Cust_Name after fetching introducerId========"+Cust_Name); %>   
    

<% if(custform!=null) {%>
<%System.out.println("in hidden variables");%>
<html:hidden property="customer.custid" value="<%=""+custform.getCustid()%>"></html:hidden>
<%System.out.println("in institute page========="+custform.getCustid()); %>
<html:hidden property="customer.custtype" value="<%=""+custform.getCusttype()%>"></html:hidden>
<%System.out.println("in institute page========="+custform.getCusttype()); %>
<html:hidden property="customer.value" value="<%=""+custform.getValue()%>"></html:hidden>
<html:hidden property="customer.pageId" value="<%=""+custform.getPageId()%>"></html:hidden>
<%}%>


<table cellspacing="5" style="border: thin solid black;">
     <tr></tr>
     <tr></tr>
     <tr></tr>
    <td>
    
   
    
<table class="txtTable">
    <tr>
       <core:choose>
       <core:when test="${empty requestScope.Detail}">
       <td><bean:message key="label.instname"></bean:message><html:text property="instname" size="30" disabled="<%=flag%>"></html:text></td>
       </core:when>
       <core:otherwise>
       <td><bean:message key="label.instname"></bean:message><html:text property="instname" size="35" value="<%= obj.getFirstName() %>" disabled="<%=flag%>"></html:text></td>
       <%System.out.println("after first td===="+flag); %>          
       </core:otherwise>
       </core:choose> 
      </tr>
</table>
  
 
<table align="left" class="txtTable">
     <tr>
        <core:choose>
          <core:when test="${empty requestScope.Detail}">
          <td align="right"><bean:message key="label.contact"></bean:message><html:text property="contactpersonname" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" ></html:text></td>         
          </core:when>
          <core:otherwise>
          <td align="right"><bean:message key="label.contact"></bean:message><html:text property="contactpersonname" size="10" value="<%= obj.getMiddleName() %>" disabled="<%=flag%>" onkeypress="return only_alpha()"></html:text></td>
          </core:otherwise>
          </core:choose>  
     
         <core:choose>
          <core:when test="${empty requestScope.Detail}">
          <td ><html:select property="salutation" disabled="<%=flag%>">
          <core:forEach var="combosalutationvalues" items="${requestScope.salutation}">
           <html:option value="${combosalutationvalues}"><core:out value="${combosalutationvalues}"></core:out></html:option>
          </core:forEach>
          </html:select>
          </td>
          </core:when>
          <core:otherwise>
           <td><html:select property="salutation" disabled="<%=flag%>">
               <html:option value="<%=obj.getSalute()%>"></html:option>
              </html:select></td>
          </core:otherwise>
          </core:choose>
         
         
         <core:choose>
          <core:when test="${empty requestScope.Detail}">
          <td align="left"><bean:message key="label.designation"></bean:message><html:text property="designation" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" ></html:text></td>          
          </core:when>
          <core:otherwise>
          <td align="left"><bean:message key="label.designation"></bean:message><html:text property="designation" size="10" value="<%= obj.getOccupation() %>" disabled="<%=flag%>" onkeypress="return only_alpha()"></html:text></td>
          </core:otherwise>
          </core:choose>
       </tr>

        <tr>
           
          <core:choose>
          <core:when test="${empty requestScope.Detail}">
          <td align="right"><bean:message key="label.introid"></bean:message><html:text property="introid" size="10" disabled="<%=flag%>" onchange="submit()" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" ></html:text></td>          
          </core:when>
          <core:otherwise>
          <td align="right"><bean:message key="label.introid"></bean:message>
          <%if(obj.getIntroducerId()!=null){ %>
          <html:text property="introid" size="10" value="<%= obj.getIntroducerId() %>" disabled="<%=flag%>" onkeypress="return only_numbers()" onblur="submit()" onkeyup="numberlimit(this,'11')" ></html:text>
          <%}else{ %>
          <html:text property="introid" size="10"  disabled="<%=flag%>"  onblur="submit()"></html:text>
          <%} %>
          </td>          
          </core:otherwise>
          </core:choose>
          
          
          
          <%if(Cust_Name!=null){ %>
             <td><html:text property="introid2"  disabled="<%=flag%>" value="<%=""+Cust_Name%>" size="20" readonly="true"></html:text></td>
           <%} else{%>
        
          <core:choose>
          <core:when test="${empty requestScope.Detail}">
          <td><html:text property="introid2" size="10" disabled="<%=flag%>" readonly="true" ></html:text></td>          
          </core:when>
          <core:otherwise>
          <td><html:text property="introid2" size="20" value="<%= obj.getBranchCode() %>" disabled="<%=flag%>" readonly="true" ></html:text></td>
          </core:otherwise>
          </core:choose>
          <%} %>
          
      </tr>

<tr></tr>

<tr>
     <core:choose>
          <core:when test="${empty requestScope.Detail}">
           <td align="right"><bean:message key="label.pan"></bean:message><html:text property="pan" disabled="<%=flag%>" size="10" onblur="panvalidation(this)"></html:text></td>      
          </core:when>
          <core:otherwise>
           <td align="right"><bean:message key="label.pan"></bean:message><html:text property="pan" value="<%= obj.getPanno() %>" disabled="<%=flag%>" size="10" onblur="panvalidation(this)"></html:text></td>          
          </core:otherwise>
          </core:choose>
       
    
</tr>
 

<tr></tr>

            
<tr>   
   <%! AddressTypesObject AddressType[];%>
   		<%! Vector vect_add=new Vector();%>
   		<% AddressType=(AddressTypesObject[])request.getAttribute("AddressType"); %>
     <%System.out.println("AddressType*****1**Sumanth*********"+AddressType); %>
   
   		<% if((AddressType!=null)&& (obj!=null))
   		{ %> 
   			<td align="right"><bean:message key="label.address"></bean:message>
   			<html:select property="addrestype"  disabled="true" onchange="submit()" value="<%=""+AddressType[2].getAddressCode() %>">
   			<html:option value="Select">Select</html:option>
   			<html:option value="3">Office</html:option>
   			</html:select></td>
   		<%}else
   			{ %> 
   				
   				<td align="right"><bean:message key="label.address"></bean:message>
   				<html:select property="addrestype" >
   				<html:option value="Select">Select</html:option>
   					<html:option value="3">Office</html:option>
   				
   				</html:select></td>
   			
   		<%}%> 
   
      
     <%System.out.println("before text area"); %> 
     <%address=(HashMap)request.getAttribute("Address");%>
     <%!Integer address_flag; %>
     <% address_flag=(Integer)request.getAttribute("AddressFlag"); %>
     
     <%if((address!=null)&&(obj!=null)){%>
     <td>
     <% Address addr=(Address)address.get(3);%>
     <%if(addr!=null){ %>
     <html:textarea property="addressarea" value="<%=addr.getAddress()%>" disabled="<%=flag%>"></html:textarea></td>
     <%} %>
     <%}else{ %> 
     <td><html:textarea property="addressarea" disabled="<%=flag%>"></html:textarea></td>
     <%}%> 
    </tr>
    <tr></tr>
    
     
         
<tr>  



     <%
     try{
     if((address!=null)&&(obj!=null) ){%>
     <% Address addr=(Address)address.get(1);
     if(addr!=null && addr.getCountry()!=null){
     %>
         <td align="right"><bean:message key="label.country"></bean:message>   
         <html:select property="country" disabled="<%=flag%>" >
         <html:option value="<%=addr.getCountry()%>"></html:option>   	
         </html:select></td>
     <%}}else{%>
         <td align="right"><bean:message key="label.country"></bean:message>  
         <html:select property="country" disabled="<%=flag%>">
         <core:forEach var="Combo_country_values" items="${requestScope.Country}">
         <html:option value="${Combo_country_values}"><core:out value="${Combo_country_values}"></core:out></html:option>
         </core:forEach>
         </html:select></td>
      <%}}
      catch(Exception e){
    	  e.printStackTrace();
      }
      %>
    
     
     
     <%address=(HashMap)request.getAttribute("Address");%>
     <%if((address!=null)&&(obj!=null)){%>
      <%addr_ittr=address.values().iterator();%>
     <td align="right"><bean:message key="label.State"></bean:message>
     <html:select property="state" disabled="<%=flag%>">
     <%while(addr_ittr.hasNext()){ %>
     <% Address addr=(Address)addr_ittr.next();%>
     <% String country= addr.getState();%>
     <html:option value="<%=country%>" disabled="<%=flag%>"></html:option>
     <%}%>
     </html:select></td>
     <%}else{%>
        <td align="right"><bean:message key="label.State"></bean:message>
        <html:select property="state" disabled="<%=flag%>">
       <core:forEach var="state_values" items="${requestScope.statevalues}">
       <html:option value="${state_values}"><core:out value="${state_values}"></core:out></html:option>
        </core:forEach>
        </html:select> </td>
     <%}%>
    </tr>
 
    <tr>
      <%address=(HashMap)request.getAttribute("Address");%>
      <%
      try{
      if((address!=null)&&(obj!=null)){%>
      <% Address addr=(Address)address.get(3);%>
      <td align="right"><bean:message key="label.city"></bean:message>
      <%if(addr!=null && addr.getCity()!=null){ %>
      <html:text property="city" size="10" value="<%=addr.getCity()%>" disabled="<%=flag%>"></html:text></td>
      <%}}else{ %>  
      <td align="right"><bean:message key="label.city"></bean:message>
      <html:text property="city" size="10" disabled="<%=flag%>" onkeypress="return only_alpha()" ></html:text></td>
      <%}}
      catch(Exception e){
    	  e.printStackTrace();
      }
      %>
      
      
     <%address=(HashMap)request.getAttribute("Address");%>
     <%if((address!=null)&&(obj!=null)){%>
     <% Address addr=(Address)address.get(3);%>
     <td align="right"><bean:message key="label.pin"></bean:message>
     <%if(addr!=null && addr.getPin()!=null){ %>
     <html:text property="pin" size="10" value="<%=addr.getPin()%>" disabled="<%=flag%>" onblur="pinvalidation(this)" ></html:text></td>
     <%}}else{ %>    
     <td align="right"> <bean:message key="label.pin"></bean:message><html:text property="pin" size="10" disabled="<%=flag%>" onkeypress="return only_numbers()" onblur="pinvalidation(this)" ></html:text></td>
     <%}%>
    <%if(obj!=null && obj.getAddressProof()!=null){ %>
    <td align="right"><bean:message key="label.addrproof"></bean:message>
    <html:select property="addrproof" disabled="<%=flag%>">
    <html:option value="<%= obj.getAddressProof() %>"></html:option>
    </html:select></td>
    <%}else{ %>
    <td align="right"><bean:message key="label.addrproof"></bean:message>
    <html:select property="addrproof" disabled="<%=flag%>">
    <core:forEach var="combo_addrproof_values" items="${requestScope.AddressProof}">
    <html:option value="${combo_addrproof_values}"><core:out value="${combo_addrproof_values}"></core:out></html:option>
    </core:forEach>
    </html:select>
    </td>  
     <%}%>
    </tr>

         
 <tr>
    <%if((address!=null)&&(obj!=null)){%>
    <% Address addr=(Address)address.get(3);%>
    <td align="right"><bean:message key="label.mobile"></bean:message>
    <%if(addr!=null && addr.getMobile()!=null){ %>
    <html:text property="mobile" size="10" value="<%=addr.getMobile() %>" disabled="<%=flag%>" onkeypress="return only_numbers()" 
onkeyup="numberlimit(this,'11')"></html:text></td>    
    <%}}else{ %>
      <td align="right"><bean:message key="label.mobile"></bean:message><html:text property="mobile" size="10" disabled="<%=flag%>" onkeypress="return only_numbers()" 
onkeyup="numberlimit(this,'11')"></html:text></td>
    <%} %>
  
  
  
    <%address=(HashMap)request.getAttribute("Address");%>
    <%if((address!=null)&&(obj!=null)){%>
    <% Address addr=(Address)address.get(3);%>
    <td align="right"><bean:message key="label.landline"></bean:message>
    <%if(addr!=null && addr.getPhno()!=null){ %>
     <html:text property="phnum1" size="10" value="<%=addr.getPhStd()%>" disabled="<%=flag%>" onkeypress="return only_numbers()" 
onkeyup="numberlimit(this,'11')"></html:text></td>
    <%}}else{ %>
    <td align="right" ><bean:message key="label.landline"></bean:message>
    <html:text property="phnum1" size="10" disabled="<%=flag%>" onkeypress="return only_numbers()" 
onkeyup="numberlimit(this,'11')"></html:text></td>
    <%} %>
    
    <%if(obj!=null){ %>
    <td align="right"><bean:message key="label.nameproof"></bean:message>
        <html:select property="nameproof" disabled="<%=flag%>">
        <html:option value="<%= obj.getNameProof() %>"></html:option>
        </html:select></td>
    <%}else{ %>
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
    <%if((address!=null)&&(obj!=null)){%>
    <% Address addr=(Address)address.get(3);%>
    <td align="right"><bean:message key="label.mailid"></bean:message>
    <%if(addr!=null && addr.getEmail()!=null){ %>
       <html:text property="mailid" size="10" value="<%= addr.getEmail() %>" disabled="<%=flag%>"onkeypress="return alphanumericonly_id(this)" ></html:text></td> 
    <%}}else{%>
    <td align="right"><bean:message key="label.mailid"></bean:message><html:text property="mailid" size="10" disabled="<%=flag%>" onkeypress="return alphanumericonly_id(this)" onchange="return email_check()" ></html:text></td>
    <%}%>
    
    <%if((address!=null)&&(obj!=null)){%>
    <% Address addr=(Address)address.get(3);%>
    <td align="right"><bean:message key="label.faxno"></bean:message>
    <%if(addr!=null && addr.getFax()!=null) {%>
    <html:text property="faxno" size="10" value="<%= addr.getFax() %>" disabled="<%=flag%>" onkeypress="return only_numbers()" 
onkeyup="numberlimit(this,'11')"></html:text></td>
    <%}}else{%> 
    <td align="right"> <bean:message key="label.faxno"></bean:message><html:text property="faxno" size="10" disabled="<%=flag%>" onkeypress="return only_numbers()" 
onkeyup="numberlimit(this,'11')"></html:text></td>
    <%}%>   
  </tr>
  
  
  
   <tr>  
     
     
     <html:hidden property="button" value="error"/>
          
     <%if(Integer.parseInt(value.trim())==1){ %>  
     
      <td align="right"><font size="50"> <html:submit onclick="return setButtton('submit');" styleClass="ButtonBackgroundImage">SUBMIT</html:submit></font></td>
        <%}else if(Integer.parseInt(value.trim())==2){%>
        <td align="right"><font size="50"><html:submit onclick="return setButtton('verify');" styleClass="ButtonBackgroundImage">VERIFY</html:submit></font></td>
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