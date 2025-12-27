

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Feb 23, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<html>
  <head><title>SB Updation</title>
      <font color="blue" ><center>
<h2 class="h2">CC Employment Detail Updation</h2></center></font>
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
    <script><!--
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
    
    function only_alpa()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 97 && cha <=122)) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	
	function only_numbers_alpa()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57)||(cha >= 97 && cha <=122)) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
    
   function confirmsubmit(){
  
   
   
   }
   
   function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
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
    		if(ele[i].type=="checkbox")
    		{ 
    			ele[i].value="";
    		}
    		if(ele[i].type=="textarea")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    function makeEnable(val){
    var value=val;
    if(value=="yes")
    {
     document.getElementById("amzad").disabled=false;
    }
    if(value=="no")
    {
     document.getElementById("amzad").disabled=true;
    }
    
    }
    
    
    --></script>
  </head>
  <body class="Mainbody" onload="return validate()">
    <%
    String employmentType=(String)request.getAttribute("Employment");
    %>
    
    
     <html:form action="/FrontCounter/EmpDetails?pageId=3047">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="pageId" value="3005"/>
     	<table>
     	<tr>
     	   <td>
     	   <table background="#fffccc" >
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Account Type&No:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue"><html:select property="acType" style="font-family:bold;color:blue">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                        <html:text property="acNo" onblur="setFlag('from_acno')" onkeypress="return only_numbers()" ></html:text>
                    </td>
                
     	</tr>
     	<!--<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 Employment Type:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:select property="empType" style="font-family:bold;color:blue" styleClass="formTextFieldWithoutTransparent" onchange="setFlag('from_type')">
     	  <html:option value="SelfEmployed" style="font-family:bold;color:blue">Self Employed</html:option>
     	  <html:option value="Service" style="font-family:bold;color:blue">Service</html:option>
     	  <html:option value="Pension" style="font-family:bold;color:blue">Pension</html:option>
     	  <html:option value="ByRent" style="font-family:bold;color:blue">By Rent</html:option>
     	  <html:option value="Business" style="font-family:bold;color:blue">Business</html:option>
     	  
     	  </html:select>
     	 </td>
     	</tr>
     	
     	
     	--></table>
     	<%
     	if(employmentType!=null){
     	%>
     	<%
     	if(employmentType.equalsIgnoreCase("SelfEmployed")){
     	%>
     	    <table name="selfEmp" style="border:thin solid verdana;">
     	    <thead style="font-family:bold;color:blue"><font color="red">Self Employed</font></thead>
     	      <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Nature Of Employment:
     	          </td>
     	          <td>
     	          <html:text property="empNature" style="font-family:bold;color:blue" onkeypress="return only_alpa()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Address:
     	          </td>
     	          <td>
     	          <html:textarea property="selfEmpAddress" cols="25" rows="5" style="font-family:bold;color:blue" onkeypress="return only_numbers_alpa()"></html:textarea>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Phone No:
     	          </td>
     	          <td>
     	          <html:text property="selfPhno" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Length Of Service:
     	          </td>
     	          <td>
     	          <html:text property="serviceLen" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Monthly Income:
     	          </td>
     	          <td>
     	          <html:text property="monthlyIncome" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Monthly Expenditure:
     	          </td>
     	          <td>
     	          <html:text property="monthlyExpenditure" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Net Monthly Income:
     	          </td>
     	          <td>
     	          <html:text property="netMonthlyIncome" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	        </tr>
     	      
     	    </table>
     	    <%} %>
     	    <%
     	    if(employmentType.equalsIgnoreCase("Pension")){
     	    %>
     	    <table name="pension" style="border:thin solid verdana;">
     	    <thead style="font-family:bold;color:blue"><font color="red">Pension</font></thead>
     	      <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Employer Name:
     	          </td>
     	          <td>
     	          <html:text property="pensionerName" style="font-family:bold;color:blue" onkeypress="return only_alpa()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Address:
     	          </td>
     	          <td>
     	          <html:textarea property="pensionerAddr" cols="25" rows="5" style="font-family:bold;color:blue"></html:textarea>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Phone No:
     	          </td>
     	          <td>
     	          <html:text property="pensionerPhno" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Pension Amount:
     	          </td>
     	          <td>
     	          <html:text property="pensionAmount" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Bank Name:
     	          </td>
     	          <td>
     	          <html:text property="pensionerBankName" style="font-family:bold;color:blue" onkeypress="return only_alpa()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Account Type:
     	          </td>
     	          <td>
     	          <html:text property="pensionerAcType" style="font-family:bold;color:blue" onkeypress="return only_alpa()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Account No:
     	          </td>
     	          <td>
     	          <html:text property="pensionerAcNo" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Net Monthly Income:
     	          </td>
     	          <td>
     	          <html:text property="pensionerMthIncome" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr>
     	       </table>
     	       <%} %>
     	    <%
     	    if(employmentType.equalsIgnoreCase("Business")){
     	    %>
     	           <table name="business" style="border:thin solid verdana;">
     	    <thead style="font-family:bold;color:blue"><font color="red">Business</font></thead>
     	      <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Name Of Concern:
     	          </td>
     	          <td>
     	          <html:text property="businessName" style="font-family:bold;color:blue" onkeypress="return only_alpa()"></html:text>
     	          </td>
     	       </tr>
     	      <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Address:
     	          </td>
     	          <td>
     	          <html:textarea property="businessAddr" cols="25" rows="5" style="font-family:bold;color:blue" onkeypress="return only_numbers_alpa()" ></html:textarea>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Nature Of Business:
     	          </td>
     	          <td>
     	          <html:text property="businessNature" style="font-family:bold;color:blue" onkeypress="return only_alpa()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Value Of Stock:
     	          </td>
     	          <td>
     	          <html:text property="stockValue" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Type Of Goods:
     	          </td>
     	          <td>
     	          <html:text property="goodsType" style="font-family:bold;color:blue" onkeypress="return only_alpa()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Condition Of Goods:
     	          </td>
     	          <td>
     	          <html:text property="goodsCondition" style="font-family:bold;color:blue" onkeypress="return only_alpa()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Average Turnover Per Month(Rs):
     	          </td>
     	          <td>
     	          <html:text property="turnover" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Phone No:
     	          </td>
     	          <td>
     	          <html:text property="businessPhno" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Monthly Income:
     	          </td>
     	          <td>
     	          <html:text property="busMonthlyIncome" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Monthly Expenditure:
     	          </td>
     	          <td>
     	          <html:text property="busMonthlyExpenditure" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Net Monthly Income:
     	          </td>
     	          <td>
     	          <html:text property="busNetMonthlyIncome" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	        </tr>
     	       </table>
     	       <%} %>
     	       <%
     	       if(employmentType.equalsIgnoreCase("ByRent")){
     	       %>
     	           <table name="byrent" style="border:thin solid verdana;">
     	    <thead style="font-family:bold;color:blue"><font color="red">By Rent</font></thead>
     	      <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Land Address:
     	          </td>
     	          <td>
     	          <html:textarea property="landAddr" cols="25" rows="5" style="font-family:bold;color:blue" onkeypress="return only_numbers_alpa()"></html:textarea>
     	          </td>
     	       </tr> 
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Total Amount Of Rent Per Month:
     	          </td>
     	          <td>
     	          <html:text property="rentAmount" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr> 
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Payment Of Tax per Month:
     	          </td>
     	          <td>
     	          <html:text property="rentTax" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr> 
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Net Income:
     	          </td>
     	          <td>
     	          <html:text property="rentIncome" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr> 
     	       </table>
     	       <%} %>
     	       <%
     	       if(employmentType.equalsIgnoreCase("Service")){
     	       %>
     	          <table name="service" style="border:thin solid verdana;">
     	    <thead style="font-family:bold;color:blue"><font color="red">Service</font></thead>
     	      <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Name Of Employee:
     	          </td>
     	          <td>
     	          <html:text property="empName" style="font-family:bold;color:blue" onkeypress="return only_alpa()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Address:
     	          </td>
     	          <td>
     	          <html:textarea property="empAddress" cols="25" rows="5" style="font-family:bold;color:blue" onkeypress="return only_numbers_alpa()"></html:textarea>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Phone No:
     	          </td>
     	          <td>
     	          <html:text property="servicePhno" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Emp No:
     	          </td>
     	          <td>
     	          <html:text property="empNo" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Designation:
     	          </td>
     	          <td>
     	          <html:text property="empDesig" style="font-family:bold;color:blue" onkeypress="return only_alpa()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Department:
     	          </td>
     	          <td>
     	          <html:text property="empDept" style="font-family:bold;color:blue" onkeypress="return only_alpa()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Whether Confirmed in Present Service?
     	          </td>
     	          <td>
     	          <html:select property="presentService" style="font-family:bold;color:blue" styleClass="formTextFieldWithoutTransparent">
     	          <html:option value="Yes" style="font-family:bold;color:blue">Yes</html:option>
     	          <html:option value="Yes" style="font-family:bold;color:blue">Yes</html:option>
     	          </html:select>
     	          </td>
     	       </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Whether Service is Transferable?
     	          </td>
     	          <td>
     	          <html:select property="serviceTrf" style="font-family:bold;color:blue" styleClass="formTextFieldWithoutTransparent">
     	          <html:option value="Yes" style="font-family:bold;color:blue">Yes</html:option>
     	          <html:option value="Yes" style="font-family:bold;color:blue">Yes</html:option>
     	          </html:select>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Whether Salary Certificate Enclosed?
     	          </td>
     	          <td>
     	          <html:select property="salCertEnc" style="font-family:bold;color:blue" styleClass="formTextFieldWithoutTransparent">
     	          <html:option value="Yes" style="font-family:bold;color:blue">Yes</html:option>
     	          <html:option value="Yes" style="font-family:bold;color:blue">Yes</html:option>
     	          </html:select>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Length of Service:
     	          </td>
     	          <td>
     	          <html:text property="serviceLength" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Monthly Income:
     	          </td>
     	          <td>
     	          <html:text property="srvMonthlyIncome" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Monthly Expenditure:
     	          </td>
     	          <td>
     	          <html:text property="srvMonthlyExpenditure" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Net Monthly Income:
     	          </td>
     	          <td>
     	          <html:text property="srvNetMonthlyIncome" style="font-family:bold;color:blue" onkeypress="return only_numbers()"></html:text>
     	          </td>
     	        </tr>
     	       </table>
     	       <%} %>
     	       
     	  
     	<%} %>
    
     	<table name="button_table" align="center">
     	<tr>
     	<td>
     	<html:button  property="update"  onclick="setFlag('update'); " styleClass="ButtonBackgroundImage" ><bean:message key="gl.label.update"></bean:message> </html:button>
        </td>
        <td>&nbsp;</td>
        <td>
        <html:reset   onclick="clearFunc()" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.cancel"></bean:message> </html:reset>
     	</td>
     	</tr>
     	</table>
    
    
     </td>
 	</tr>
 	</table>	
     
    
    
    
    
    
               </html:form>													
 	
     	   
     	   
     	   
     	   
     	 	
     	
  </body>
  </html>          