<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  Created by IntelliJ IDEA.
  User: shwetha    
  Date: Nov 22, 2007
  Time: 12:38:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.StringTokenizer"%>
<%@page import="masterObject.general.AddressTypesObject"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.HashMap"%> 
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<html>

  <head>
  	<script type="text/javascript">
  		function set(target)
  		{   
  		   document.forms[0].forward.value=target;
           document.forms[0].submit();
           
        };
           
           function clear_textfield()
  			{
  				
  		
  				var ele=document.forms[0].elements;
  				for(var i=0;i<ele.length;i++)
  				{
  					if(ele[i].type=="text")
  					{
  			  			ele[i].value="";
  					}
  		
  				}
  			};	
  			
  			function show()
  			{
  			if(document.getElementById("refreshing").value!=0)
	         {
		
		     document.getElementById("refreshing").value="";
		     	document.forms[0].submit();
		           }
  			}
           
  	</script>
  	<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
	<h2 class="h2"><center>Parameters for Customer</center></h2>  
  </head>
  <body onload="show()"> 
  <table cellspacing="5" align="center" style="border: thin solid black;">
  	   <tr>
  	   	  <td>
  <%!String check_ava; %>
  <%!boolean flag=false; %>
  <%check_ava =(String)request.getAttribute("check_ava");%>
  <%if(check_ava!=null)
  { 
  	if(check_ava.equalsIgnoreCase("val_true"))
  	{
  		flag=true;
  	}
  	else if(check_ava.equalsIgnoreCase("val_false"))
  	{
  		flag=false;
  	}
  } %>
  
  <%! String[] str;
  Map user_role;
  String access;
  
  %>
  <% str=(String[])request.getAttribute("Customization");%>
  
  <%AccountSubCategory=(String[])request.getAttribute("AccountSubCategory");%>
      		
   <%! String[] StateCountry;%>      		 
   <% StateCountry=(String[])request.getAttribute("StateCountry"); %>  
   <%System.out.println("State values in jsp page after selecting country========="+StateCountry); %>
   
    <%if(StateCountry!=null){ %>
    <%System.out.println("inside state country not null");%>
    <%for(int i=0;i<StateCountry.length;i++){ %>
    <%System.out.println("StateCountry[i] in jsp page===="+StateCountry[i]);%>      
     <%}}%>
     
   <% Country=(String[])request.getAttribute("Country"); %>
      
    
   <%!String CountryValue[]; %>  		
   <% CountryValue=(String[])request.getAttribute("CountryValue"); %>
      		
  <%! String StateValue; %>
  <% StateValue =(String)request.getAttribute("StateValue");%>
  <%System.out.println("StateValue====="+StateValue); 
  String accesspageId=(String)request.getAttribute("accesspageId");
	user_role=(Map)request.getAttribute("user_role");
	
	
	if(user_role!=null&&accesspageId!=null){
		if(user_role.get(accesspageId)!=null){
			access=(String)user_role.get(accesspageId);
			System.out.println("access-----In SBOpening------>"+access);
		}

		
		}
  
  %>
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
  
  <html:form action="/Customer/Customization?pageId=1005"> 
  
  
  <table  class="txtTable">
  		
  		<tr>
          <td><font size="3" style="font-family:serif;color: black;"><bean:message key="label.parameters"></bean:message></font>&nbsp;&nbsp;&nbsp;
              <html:select property="param" onchange="submit()" >
              <html:option value="select">Select</html:option>
              <% for(int i=0;i<str.length;i++)
              {%>
          		<%StringTokenizer strtoken=new StringTokenizer(str[i],"$$$");%>
       			<%String cust=strtoken.nextToken(); %>
       			
                <html:option value="<%=""+i+cust%>"><%=cust%></html:option>
              <%}%>
          		</html:select></td>
        
        
         <%if(AccountSubCategory!=null){ %>
         <td>
        <html:select property="accountsubcatdesc" onchange="submit()">
        <html:option value="select">Select</html:option>
        <%for(int i=0;i<AccountSubCategory.length;i++){ %>
        <html:option value="<%=AccountSubCategory[i]%>"></html:option>
        <%}%>
        </html:select></td>
         <%}else if(StateValue!=null) {%>
         	<%if(StateValue.equalsIgnoreCase("staenotnull"))
         	{%>
         		<%if(CountryValue!=null)
         		{ %>
					<td><html:select property="countryvalues" onchange="submit()">
       				<%for(int i=0;i<CountryValue.length;i++){%>
       				<%StringTokenizer strcountry=new StringTokenizer(CountryValue[i],"$$$");%>
       				<%String country_values=strcountry.nextToken(); %>
       				<html:option value="<%=country_values%>"></html:option>
       			<%}%>
       				</html:select></td>
       		<%}%>
       	<%}%> 
       <%} %>
      </tr>    
  <%!String AccountDescription; %>
  <%if(AccountDescription!=null)%>
  <%AccountDescription=(String)request.getAttribute("AccountDescription");%>
  <%System.out.println("AccountDescription in jsp page======="+AccountDescription); %>
   <html:hidden property="forward" value="error/"></html:hidden>
   <html:hidden property="refreshpage" styleId="refreshing"></html:hidden>
  <%if(AccountDescription!=null){%>
  <tr> 
      <td>Code:<html:text property="code" size="3"></html:text></td>
      <td>Description:<html:text property="desc" size="3"></html:text></td>
     
  </tr>
   
  <%}else{%>
  <tr> 
      <td> <font size="3" style="font-family:serif;color:black;"> <bean:message key="label.addpa"></bean:message></font>
      <html:text property="addp" size="10" ></html:text>
       
      </td>
     
  </tr>
  <%}%> 
  <tr><td class="loginTableFooter"></td></tr>
    <tr>
      <td>
          <!--<hr style="color: blue">   
           --><%System.out.println("flag======>"+flag); %>
           <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
          <html:button property="but_sub" styleClass="ButtonBackgroundImage" onclick="set('submit')" >Submit</html:button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <%}else{ %>
          <html:button property="but_sub" styleClass="ButtonBackgroundImage" onclick="set('submit')" disabled="true">Submit</html:button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <%} %>
          <%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
          <html:button property="but_del" styleClass="ButtonBackgroundImage" onclick="set('Delete')"  >Delete</html:button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <%}else{ %>
          <html:button property="but_del" styleClass="ButtonBackgroundImage" onclick="set('Delete')"  disabled="true">Delete</html:button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <%} %>
          <html:button styleClass="ButtonBackgroundImage" property="clear" value="CLEAR" onclick="clear_textfield()"></html:button>
          <!--<hr style="color: blue"> 
      --></td>
     <tr><td class="loginTableFooter"></td></tr> 
  

  <tr>
  	
      <%! HashMap country_code=null; %>
      <% String AddressType[]; %> 
      <% AddressType=(String[])request.getAttribute("AddressType"); %> 
  
  
      <%!String[] Country,CustomerType; %>
      <%! String[] Salutation,NameProof,AddrTypes,AddressProof,MaritalStat,Occupation,Castes,AccountSubCategory,Salaried; %>
      
      
      <% Salaried=(String[])request.getAttribute("Salaried");%>
        
      <% Castes=(String[])request.getAttribute("Castes");%>

      <% Occupation=(String[])request.getAttribute("Occupation");%>

      
      <% MaritalStat=(String[])request.getAttribute("MaritalStat");%>

      
      <% AddressProof=(String[])request.getAttribute("AddressProof");%>
<% CustomerType=(String[])request.getAttribute("CustType");%>      
      <% AddrTypes=(String[])request.getAttribute("AddrTypes");%>

            
      <% Salutation=(String[])request.getAttribute("Salutation");%>

      <% NameProof=(String[])request.getAttribute("NameProof");%> 
    
         
      
       
        
       <%if(Country!=null){ %>  
       <td width="260" height="180"> 
       <html:select property="country" size="10" >
       <%for(int i=0;i<Country.length;i++){%>
       <%StringTokenizer strcountry=new StringTokenizer(Country[i],"$$$");%>
       <%String country_values=strcountry.nextToken(); %>
       <html:option value="<%=country_values%>"></html:option>
       <%}%>
       </html:select></td>
      <%}else if(StateCountry!=null){%>
        <td width="260" height="180">
        <html:select property="statecountry" size="10" >
        <%for(int i=0;i<StateCountry.length;i++){ %>
        <html:option value="<%=StateCountry[i]%>"></html:option>
        <%} %>
        </html:select></td>
        <%}else if(Salutation!=null){%> 
        <td width="260" height="180">
        <html:select property="salutation" size="10" >
        <%for(int i=0;i<Salutation.length;i++){ %>
        <html:option value="<%=Salutation[i]%>"></html:option>
        <%}%>
        </html:select></td>
        <%}else if(NameProof!=null){ %>
        <td width="260" height="180">                         
        <html:select property="nameproof" size="10" >
        <%for(int i=0;i<NameProof.length;i++){ %>
        <html:option value="<%=NameProof[i]%>"></html:option>
        <%}%>
        </html:select></td>                                                                                        
        <%}else if(AddressType!=null){ %>
        <%Vector vect_add=new Vector(); %>
        <td width="260" height="180">
        <html:select property="addresstype" size="10" >
       <%for(int i=0;i<AddressType.length;i++){%>
        <html:option value="<%=AddressType[i]%>"></html:option>
        <%}%>
        </html:select></td>   
        <%}else if(AddressProof!=null){%>  
        <td width="260" height="180"> 
        <html:select property="addrproof" size="10" > 
        <%for(int i=0;i<AddressProof.length;i++){ %>
        <html:option value="<%=AddressProof[i]%>"></html:option>
        <%}%>
        </html:select></td>
	     <%}else if(MaritalStat!=null){ %>
        <td width="260" height="180"> 
        <html:select property="maritialstatus" size="10" >
        <%for(int i=0;i<MaritalStat.length;i++){ %>
        <html:option value="<%=MaritalStat[i]%>"></html:option> 
        <%}%>
        </html:select></td>
        <%}else if(Occupation!=null){ %>
       <td width="260" height="180">
        <html:select property="occupation" size="10" >
        <%if(Occupation!=null){ %>
        <%for(int i=0;i<Occupation.length;i++){ %>
        <html:option value="<%=Occupation[i]%>"></html:option>
        <%} %>
        <%}%>
        </html:select></td>
         <%}else if(CustomerType!=null){ %>
        <td width="260" height="180">
        <html:select property="customertype" size="10" > 
        <%for(int i=0;i<CustomerType.length;i++){ %>
        <html:option value="<%=CustomerType[i]%>"></html:option>
        <%}%>
        </html:select></td>
        <%}else if(Castes!=null){ %>
        <td width="260" height="180">
        <html:select property="caste" size="10" > 
        <%for(int i=0;i<Castes.length;i++){ %>
        <html:option value="<%=Castes[i]%>"></html:option>
        <%}%>
        </html:select></td>
        <%}else if(AccountSubCategory!=null){%>
        <td width="260" height="180">  
        <html:select property="accountsubcatdesc" size="10" >
        <%for(int i=0;i<AccountSubCategory.length;i++){%> 
       <%System.out.println("inside for loop in account sub cat"+AccountSubCategory.length); %>
        <html:option value="<%=AccountSubCategory[i]%>"></html:option>
        <%}%>
        </html:select></td>
        <%}else if(Salaried!=null){ %>
        <td width="260" height="180">
        <html:select property="salaried1" size="10" > 
        <%for(int i=0;i<Salaried.length;i++){ %>
        <html:option value="<%=Salaried[i]%>"></html:option>
        <%}%>
        </html:select></td> 
        <%}else{%>
       <td width="260" height="180">
       <html:select property="showparam" size="10">   
       </html:select></td>                                     
       <%}%> 
  </tr>
  </table>
  </html:form>
  
  </td></tr>
  </table>    
  <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
  </html>