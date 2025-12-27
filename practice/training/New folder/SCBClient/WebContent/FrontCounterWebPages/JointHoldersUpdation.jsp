
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page import="masterObject.general.Address" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Amzad
  Date: Feb 20, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<html>
  <head><title>Joint Holders</title>
      <h1 style="font:small-caps;font-style:normal;color: blue" ><center>Joint Holders Updation</center></h1>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
       
      
      <!--<style type="text/css">
          body{
              font-size:10px;
              font-family:serif;
              font-style:oblique;
              font-weight:bold;
              background:beige;
          }
          table{
               background:transperent; 
          }
          
    </style>
    --><link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    <script>
    
    function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only "+size+"digits allowed")
         		txt.value="";
         		return false;
          	}
         }
    
    function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57)) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
    
   function setDels(delval){
   if(document.forms[0].acno.value="")
   {
   	alert("plz enter the account number");
   }
   
  else if(confirm("Are you sure yuo want to delete the joint holder")){
  document.forms[0].delflag.value=delval;
  document.forms[0].flag.value='from_acno';
     document.forms[0].subflag.value='submit';
     document.forms[0].submit();
   }
   else{
   document.forms[0].delflag.value='';
  document.forms[0].flag.value='';
     document.forms[0].subflag.value='';
   }
   
   }
   
   function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
     function setFlags(flagval){
     document.forms[0].flag.value='from_acno';
     document.forms[0].subflag.value=flagval;
     document.forms[0].submit();
     
     }
     function validate(){
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         alert(val);
       } 
       
       else{
         return false;
       }
     }; 
     function clearFunc()
    {
    	
    	var ele=document.forms[0].elements;
    	  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    		if(ele[i].type=="password")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    
    </script>
  </head>
  <body class="Mainbody" onload="return validate()">
   <%!Address addr; %>
    <%
   
    CustomerMasterObject cust=(CustomerMasterObject)request.getAttribute("cust");

    String cid=(String)request.getAttribute("cid");
    String msg=(String)request.getAttribute("msg");
    %>
    
    
     <html:form action="/FrontCounter/JointHoldersUpdation?pageId=3044">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="subflag"/>
     	<html:hidden property="delflag"/>
     	
     	<core:if test="<%=msg!=null%>">
     	<font color="red"><%=msg %></font>
     	
     	</core:if>
     	<br><br>
     	
     	<table align="center">
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Account Type:
     	
     	 <html:select property="acType" style="font-family:bold;color:blue" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                    </td>
                
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Account Number:<html:text property="acno" onblur="setFlag('from_acno')" size="6" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'10')"></html:text>
     	</td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Details:
     	  <html:select property="details" style="font-family:bold;color:blue" styleClass="formTextFieldWithoutTransparent">
     	  <html:option value="JointHolders">JointHolders</html:option>
     	  <html:option value="SignatureIns">Signature Ins</html:option>
     	  </html:select>
     	  
     	</td>
     	</tr>
     	<core:if test="${cust==null}">
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	JointHolder's Cid:
     	</td>
     	<td>
     	<html:text property="jointhcid" size="6" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'10')"></html:text>
     	</td>
     	</tr>
     	</core:if>
     	</table>
     	<br><br>
     	<core:if test="${cust!=null}">
<table border="1" width="342" height="263" align="center">
	<tr>
		<td height="39" width="332" colspan="2" align="center"><b>
		<font size="4">JointHoLder's Details</font></b></td>
	</tr>
	<tr>
		<td height="36" width="148">Customer ID: </td>
		<td height="36" width="178"><%=String.valueOf(cust.getCustomerID())%></td>
	</tr>
	<tr>
		<td height="35" width="148">Name: </td>
		<td height="35" width="178"><%=String.valueOf(cust.getName())%></td>
	</tr>
	<tr>
		<td height="37" width="148">Category:<%=cust.getCategory()%></td>
		<td height="37" width="178">Sub-Category:<%=cust.getSubCategory()%></td>
	</tr>
	<tr>
		<td height="33" width="148">&nbsp;</td>
		<td height="33" width="178">SC/ST:<%=cust.getScSt()%></td>
	</tr>
	<tr>
		<td height="33" width="148">DOB:<%=cust.getDOB()%></td>
		<td height="33" width="178">Sex:<%=cust.getSex()%></td>
	</tr>
	<tr>
		<td height="32" width="148">Age:<%="no"%></td>
		<td height="32" width="178">Occupation:<%=cust.getOccupation()%></td>
	</tr>
	</table>
	
	
	
	
	<table width="341" border="1" align="center">
	
	
	<tr>
<td height="199" width="335" rowspan="2" valign="top" >
<font color="navy" size="3"> Address:</font>
		<br><br>
		<% addr=(Address)cust.getAddress().get(1);%>
		<core:if test="<%=addr!=null %>">
		<core:if test="<%=addr.getAddress()!=null %>">
		<% if(addr.getAddress().length()>20)
					{	%>
						<%=addr.getAddress().subSequence(0,20) %><br>
						<%=addr.getAddress().substring(20)%><br>
					<%}
					else %>
						<%= addr.getAddress()%><br>
							<core:if test="<%=addr.getCity()!=null %>">	
						
					<%=addr.getCity() %><br>					</core:if>
					<core:if test="<%=addr.getState()!=null %>">	
					<%=addr.getState() %><br>
					</core:if>
					<core:if test="<%=addr.getCountry()!=null %>">	
					<%=addr.getCountry() %><br>
					</core:if>
					
					<core:if test="<%=addr.getPin()!=null %>">	
					<%=addr.getPin() %><br>
					</core:if>
					<core:if test="<%=addr.getPhno()!=null %>">	
					<%if(addr.getPhno().length()>0)
						%>
						<%="Ph No: "+addr.getPhStd()+"-"+addr.getPhno() %><br>
					
					</core:if>
					<core:if test="<%=addr.getFax()!=null %>">	
					<%if(addr.getFax().length()>0)
						%>
						<%="Fax No: "+addr.getFaxStd()+"-"+addr.getFax() %><br>
					
					</core:if>
					<core:if test="<%=addr.getMobile()!=null %>">	
					<%if(addr.getMobile().length()>0)
						%>
						<%="Mobile No: "+addr.getMobile() %><br>
					
					</core:if>
						
					<core:if test="<%=addr.getEmail()!=null %>">	
					<%if(addr.getEmail().length()>0)
						%>
						<%="Email: "+addr.getEmail() %><br>
					
					</core:if>

					
		
		</core:if>
		</core:if>
	</td>	

		
	
	
	</tr>
</table>

</core:if>
     	<table>
     	   <tr>
     	      <td>
     	         
                 
                 <html:button  property="sub" onclick="setFlags('submit')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.submit"></bean:message></html:button>
                 <html:button  property="cancel" onclick="clearFunc()" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.cancel"></bean:message></html:button>
     			<html:button property="del" onclick="setDels('delete')" value="Delete" styleClass="ButtonBackgroundImage"></html:button>
     			
     	      </td>
     	    
           </tr>
          
     	</table>
          
     	 
     </html:form>	
     </body>
  </html> 	
     	  
     	   

													
 		
    	
 														
 	
     	   
     	   
     	   
     	   
     	 	
     	
           