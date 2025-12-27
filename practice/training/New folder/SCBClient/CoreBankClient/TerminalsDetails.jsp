

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Amzad
  Date: Feb 26, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<html>
  <head><title>Terminals Details</title>
  
  <style type="text/css">
          a:hover{
          text-decoration:underlined;
          background-color:verdana;
          }
          
    </style>
      
    <script type="text/javascript">
   
   function setFlag(flagValue)
   {
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     alert(""+validate()+"");
          
     }
     
     
     
      function setFlag1(flagValue)
   {
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
    
    function clearFunc()
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
    
    function onlyalpha()
	{
 		var cha =event.keyCode;
		if ( (cha >= 97 && cha <= 122 ) ) 
		{
			return true;
            		
        	} else {
        		
        	return false;
        	}
	};	
	
	function onlyDoublenumbers()
	{
        	var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || cha == 46 ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
    };
    
     function onlyalpha_num()
    {
    var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || (cha >= 97 && cha <= 122 ) ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
    };
    
    
    function validate(){
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         return val;
       } 
       
       else{
         return false;
       }
     };
    
    </script>
    
    <h1 style="font:small-caps;font-style:normal;color: blue" ><center>Terminals Details</center></h1>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
       <link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    
    
  </head>
  <body class="Mainbody" onload="validate()" >
    
    
    <%
    Object str[]=(Object[])request.getAttribute("TmlDetails");
    %>
     <html:form action="/Administrator/TerminalsDetails?pageId=10011">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="pageId" value="3005"/>
     	<table name="main">
     	<tr>
     	<td>
     	<table bgcolor="verdana" align="center">
     	<tr>
     	  
     	  <td align="center" style="font-family:bold;color:blue">
     	  Terminals
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	    <html:select property="tmls" styleClass="formTextFieldWithoutTransparent"  onchange="setFlag1('from_tmls')">
     	       <html:option value="NewTerminal" style="color:blue">NewTerminal</html:option>
     	       
     	       <core:forEach var="itm" items="${requestScope.TmlDetails}">
     	       <html:option value="${itm}" style="color:blue"><core:out value="${itm}"></core:out> </html:option>
     	       </core:forEach>
     	      
     	    </html:select>
     	  </td>
     	</tr>
     	<tr>
     	  
     	  <td align="center" style="font-family:bold;color:blue">
     	  Terminal Name
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	    <html:text property="tmlName"  style="font-family:bold;color:blue" onkeypress="return onlyalpha()" ></html:text>
     	  </td>
     	</tr>
     	<tr>
     	  
     	  <td align="center" style="font-family:bold;color:blue">
     	  Description
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	    <html:text property="tmlDesc"  style="font-family:bold;color:blue" onkeypress="return onlyalpha_num()" ></html:text>
     	  </td>
     	</tr>
     	<tr>
     	  
     	  <td align="center" style="font-family:bold;color:blue">
     	  IP Address
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	    <html:text property="tmlIpAddr"  style="font-family:bold;color:blue" onkeypress="return onlyDoublenumbers()" ></html:text>
     	  </td>
     	</tr>
     	<tr>
     	  
     	  <td align="center" style="font-family:bold;color:blue">
     	  Max Tran Amount
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	    <html:text property="tranAmount"  style="font-family:bold;color:blue" readonly="true"></html:text>
     	  </td>
     	</tr>
     	<tr>
     	  
     	  <td align="center" style="font-family:bold;color:blue">
     	  <html:radio property="tmlType" value="Cashier">Cashier</html:radio>
     	  &nbsp;&nbsp;&nbsp;&nbsp;
     	  <html:radio property="tmlType" value="MainCashier">Main Cashier</html:radio>
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	    <html:radio property="tmlType"  value="NonCashier">Non Cashier</html:radio>
     	    &nbsp;&nbsp;&nbsp;&nbsp;
     	    <%
     	    String showStatus=(String)request.getAttribute("Status");
     	    %>
     	    <%if(showStatus!=null){ %>
     	    <html:checkbox property="status" value="Status">Status</html:checkbox>
     	    <%} %>
     	  </td>
     	</tr>
     	</table>
     	
     	</td>
     	<td>
     	&nbsp;
     	</td>
     	<td>
     	&nbsp;
     	</td>
     	<td valign="top">
     	<table bgcolor="helvetica">
     	<thead style="color:red;font-family:bold">Assign Access To Modules Here</thead>
     	 <tr>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"> <html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Lockers</font></html:link></a>
     	 </td>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">BackOffice</font></html:link></a>
     	 </td>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Customer </font></html:link></a>
     	 </td>
     	 </tr>
     	 <tr>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Cash</font></html:link></a>
     	 </td>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Verification</font></html:link></a>
     	 </td>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Share</font></html:link></a>
     	 </td>
     	 </tr>
     	 <tr>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Front Counter</font></html:link></a>
     	 </td>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Term Deposits</font></html:link></a>
     	 </td>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Loans</font></html:link></a>
     	 </td>
     	 </tr>
     	  <tr>
     	    <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Clearing</font></html:link></a>
     	 </td>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Pygmy Deposit</font></html:link></a>
     	 </td>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Loans On Deposit</font></html:link></a>
     	 </td>
     	 
     	  </tr>
     	  <tr>
     	     <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">Administrator</font></html:link></a>
     	 </td>
     	 <td>
     	   <a style="border-width:1px 1px 1px 1px;color:red;"><html:link action="/Administrator/LockersTmlDetailsMenu?pageId=10010"><font style="color:red">General Ledger</font></html:link></a>
     	 </td>
     	  </tr>
     	</table>
     	
     	</td>
     	</tr>
     	</table>
     	<br>
     	<table name="button_table" align="center">
     	<html:button  property="submS"  onclick="setFlag('submit'); " styleClass="ButtonBackgroundImage" ><bean:message key="label.submit"></bean:message> </html:button>
        <html:button  property="update" onclick="setFlag('update')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.update"></bean:message> </html:button>
        <html:button  property="del" onclick="setFlag('delete')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.delete"></bean:message></html:button>
        <html:button  property="cl" onclick="clearFunc()" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:button>
     	
     	</table>
  </html:form>
  </body>
  </html>          