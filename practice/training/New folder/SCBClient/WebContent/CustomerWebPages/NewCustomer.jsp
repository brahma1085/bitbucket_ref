
<%@ page import="org.apache.struts.action.DynaActionForm"%>
<%@ page import="com.scb.cm.actions.CustomerAction"%>
<%@ page import="masterObject.customer.CustomerMasterObject"%>
<%@ page import="com.scb.cm.forms.Customerform"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt" %>
<%--    
  User: swetha
  Date: Oct 30, 2007
  Time: 11:54:55 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<script type="text/javascript">
	
	function Only_Numbers()
    {
  	var cha =   event.keyCode;
	
 	if ( (cha >= 48 && cha <= 57 ) ) 
 	{
		
   		return true;
   	} 
   	else 
   	{
		return false;
   	}
  };
  
  function fun_custver()
  {
   var a=document.forms[0].cid_first.value;
   if(a=="firsttime"){
   clearme();
   document.forms[0].cid_first.value="";
  				return false; 
   }
   
   
  	if(document.getElementById("cid_verified").value!=null)
  	{
  		if(document.getElementById("cid_verified").value=="CustIdVerified")
  		{
  			
  			document.getElementById("custId").value="0";
  			if(document.getElementById("clear_value").value=="1")
  			{
 				clear();
  				return false; 
  			}
  			return false; 				
  		}
  	}
  	 if(document.getElementById("cid_notver").value!=null)
  	{
  		if(document.getElementById("cid_notver").value=="CIDNOTVER")
  		{
  			
  			document.getElementById("custId").value="0";
  			if(document.getElementById("clear_value").value=="1")
  			{
  				
  				clear();
  				return false; 
  			}
  			return false; 
  		
  		}
  	
  	}
  	
  	
  	 if(document.getElementById("clear_mypage").value!="")
  	{
  	  
       clearme();
    
    }
    
    if(document.getElementById("totaltesting").value!=0)
	{
		
		alert(document.getElementById("totaltesting").value);	
		clearme();
			
	}
    
    
    
    
    
    
  };
  
  function clearme()
	{
		
		
			
	
		var ele1=document.forms[0].elements;
  			for(var i=0;i<ele1.length;i++)
  			{
  				if(ele1[i].type=="text" || ele1[i].type=="textarea")
  				{
  			  		ele1[i].value="";
  				}
  		
  			if(ele1[i].type=="hidden")
  				{
  			  		ele1[i].value="";
  				}
	}
	var ele=document.forms[1].elements;
  			for(var i=0;i<ele.length;i++)
  			{
  				if(ele[i].type=="text" || ele[i].type=="textarea")
  				{
  			  		ele[i].value="";
  				}
  		
  			if(ele[i].type=="hidden")
  				{
  			  		ele[i].value="";
  				}
	}
  
  }
  
  
  function clear()
  {
	document.getElementById("custId").value="0";
  };


function setfirst()
{

 document.forms[0].totalclear.value='hhhhhhhh';


document.forms[0].submit();
}


</script>
</head>
<body bgcolor="beige" onload="fun_custver()" >
   <%! String pagevalue;
   	String action;
   	Map user_role;
    String access;
   %>
   
   <%! CustomerMasterObject custobj;%>
   <%custobj=(CustomerMasterObject)request.getAttribute("Detail");
   	 System.out.println("customer master object=======>"+custobj);
   	String accesspageId=(String)request.getAttribute("accesspageId");
	user_role=(Map)request.getAttribute("user_role");
	
	
	if(user_role!=null&&accesspageId!=null){
		if(user_role.get(accesspageId)!=null){
			access=(String)user_role.get(accesspageId);
			request.setAttribute("newcuAccess",access);
			System.out.println("access-----In SBOpening------>"+access);
		}

		
		}
   	 
   %>
   
   
   <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
    
   <% pagevalue=(String)request.getAttribute("submit"); %>
   
   
    <%if(Integer.parseInt(pagevalue.trim())==2){%>
   			<%action="/custlink?pageId=1001&value=2";%>
   		<%}else if(Integer.parseInt(pagevalue.trim())==1){%>
   			<%action="/custlink?pageId=1001&value=1";%>
   		 <%}else{%>
   			<% action="/custlink?pageId=1001&value=3";%>       
         <%}%>
  
   <%System.out.println("page value in new customer jsp============"+pagevalue); %>
   
   
   <%!boolean flag; %>      
   <%if((pagevalue.trim().equalsIgnoreCase("2"))||(pagevalue.trim().equalsIgnoreCase("3"))){ %>
   <%flag=true; 
     System.out.println("inside if condition in institute page");%>
   <%}else{%> 
   <%flag=false; %>      
   <%}%>      
   
   <%! String[] combotype;%>
   <% combotype=(String[])request.getAttribute("Customertype");%>
   <%System.out.println("combo values"+combotype);%>
   
   
    
         
   <html:form action="<%=action %>"  method="post" enctype="multipart/form-data">
   <html:errors/>
   <table border="2">
     <html:hidden property="totalclear" ></html:hidden>
     <html:hidden property="testing" styleId="totaltesting"></html:hidden>
   <tr><td>  
  <table class="txtTable">
         <tr>
             <td><bean:message key="label.cust"></bean:message>&nbsp;
             <html:text property="custid" styleId="custId" onblur="setfirst()"  size="10" onkeypress="return Only_Numbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
             
             <%if(custobj!=null){ %>
             <%System.out.println("I am in If of Category"); %>
             <%System.out.println("category=========>"+custobj.getCategory()); %>
              <td><bean:message key="label.custtype"></bean:message>
             <html:select property="custtype" styleClass="formTextField">
             <%if(custobj.getCategory()==0){ %>
             <html:option value="<%=""+custobj.getCategory()%>">Individual</html:option>
             <%}else { %>
              <html:option value="<%=""+custobj.getCategory()%>">Institute</html:option>
             <%}%>
             </html:select></td>
             <%}else{%>
             <% System.out.println("I am in Else of Category"); %>
             <td><bean:message key="label.custtype"></bean:message>
             <html:select property="custtype" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
              <% for(int i=0; i<combotype.length; i++){%>
              <!--
               for identifing customer individual or institute 
              --><html:option value="<%=""+i%>" disabled="<%=flag%>"><%=combotype[i]%></html:option>
               <%}%>
	               <%System.out.println("flag value======="+flag);%>
              </html:select></td>   
              <%}%>   
         </tr>
      </table>
 
     </td>
    </tr>
</table>

	<html:hidden property="cid_verified" styleId="cid_verified"/>
    <html:hidden property="cid_first" styleId="clear_mypage"/>
  
    <html:hidden property="cid_notver" styleId="cid_notver"/>
    <html:hidden property="clear_value" styleId="clear_value"/>

 </html:form>
    
   <table class="txtTable">
   
         <tr>
            <td>
            <%!String path; %> 
             <%
                 path=(String)request.getAttribute("pageName");
             	 System.out.println("path in new Customer12345=====-----=====--->"+path);	
                 if(path!=null){
             %>
                    <jsp:include page="<%=path%>"></jsp:include>
             <% } %>
             </td>
         </tr>
    
    </table>  
 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
  </html>