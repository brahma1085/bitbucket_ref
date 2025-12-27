<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Consolidated Day Book</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Consolidate Day Book</center></h2>
      <hr>
      
    
    <script type="text/javascript">
     function set(target){
       document.forms[0].forward.value=target
      };
     
      function check_acnt(){
      
        if(document.getElementById("ac").value=='T'){
          alert("Account not found")
           document.forms[0].acno.value="";
        }
      };
        
   function clear_fun()
    {
    	
    	var ele=document.forms[0].elements;
    	document.getElementById("forward").style.display='none';  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    
    function numbersonly(eve){
         var cha = event.keyCode
            if (  ( cha  > 47 && cha < 58  ) ) {
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
      };
     
     
     function validate(){
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         alert(val);
       } 
       
       else{
         return false;
       }
     }; 
     
     function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%String result=(String)request.getAttribute("result");
String brConName=(String)request.getAttribute("brcon");
String daybookName=(String)request.getAttribute("daybook");
Object[][] objArrayData=(Object[][])request.getAttribute("datadummy");
%>
<html:form action="/GL/GLConsolidatedDayBook?pageId=12017">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
   <tr>
    <td><b>Date</b>    
      <html:text property="date"  size="10"  styleClass="formTextFieldWithoutTransparent"></html:text>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           
    </td>
    
   </tr>
   <tr>
   <td>
    <html:radio property="brConsolidation" value="branchConsolidation"></html:radio><b>Branch Consolidation</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <html:radio property="brConsolidation" value="consolidatedDayBook"></html:radio><b>consolidated Day Book</b>  
   </td>
   </tr>
   <tr>
   <td>
    <html:radio property="dayBook" value="DayBook"></html:radio><b>Day Book</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <html:radio property="dayBook" value="TrialBalance"></html:radio><b>Trial Balance</b>  
   </td>
   </tr>
   
   
   <tr>
     <td>
        <html:submit  onclick="setFlag('view'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message> </html:submit>
        <html:submit  onclick="set('file')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.file"></bean:message> </html:submit>
        <html:button  property="printFile" onclick="window.print()" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button>
        <html:submit  onclick="clear_fun(); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:submit>
     </td>
   </tr>
   
 </table>
 <%
 if(brConName!=null && daybookName!=null){
	 if((brConName.equalsIgnoreCase("branchconsolidation") && daybookName.equalsIgnoreCase("daybook")) || (brConName.equalsIgnoreCase("consolidateddaybook") && daybookName.equalsIgnoreCase("daybook"))){
 %>
 <table border=1 bordercolor="blue" >
 <tr>
 <td colspan="3" align="center"><b>General Ledger</b>
 </td>
 <td colspan="4" align="center"><b>Debit Amount</b>
 </td>
 <td colspan="4" align="center"><b>Credit Amount</b>
 </td>
 <td  align="center"><b>Net</b>
 </td>
 </tr>
 <tr>
 <td><b>Type</b>
 </td>
 <td><b>Code</b>
 </td>
 <td><b>Name</b>
 </td>
 <td><b>Cash</b>
 </td>
 <td><b>Clearing</b>
 </td>
 <td><b>Transfer</b>
 </td>
 <td><b>Total</b>
 </td>
 <td><b>Cash</b>
 </td>
 <td><b>Clearing</b>
 </td>
 <td><b>Transfer</b>
 </td>
 <td><b>Total</b>
 </td>
 <td><b>Transaction</b>
 </td>
 </tr>
 </table>
 <%}else if(brConName.equalsIgnoreCase("consolidateddaybook") && daybookName.equalsIgnoreCase("trialbalance")){ %>
 <table border=1 bordercolor="blue">
 <tr>
 <td colspan="3" align="center"><b>General Ledger</b>
 </td>
 <td colspan="4" align="center"><b>Debit Amount</b>
 </td>
 <td colspan="4" align="center"><b>Credit Amount</b>
 </td>
 <td  align="center"><b>Net</b>
 </td>
 <td colspan="2" align="center"><b>Closing Balances</b>
 </td>
 </tr>
 <tr>
 <td><b>Type</b>
 </td>
 <td><b>Code</b>
 </td>
 <td><b>Name</b>
 </td>
 <td><b>Cash</b>
 </td>
 <td><b>Clearing</b>
 </td>
 <td><b>Transfer</b>
 </td>
 <td><b>Total</b>
 </td>
 <td><b>Cash</b>
 </td>
 <td><b>Clearing</b>
 </td>
 <td><b>Transfer</b>
 </td>
 <td><b>Total</b>
 </td>
 <td><b>Transaction</b>
 </td>
 <td><b>Debit</b>
 </td>
 <td><b>Credit</b>
 </td>
 </tr>
 </table>
 <%}else if(brConName.equalsIgnoreCase("branchconsolidation") && daybookName.equalsIgnoreCase("trialbalance")){ %>
 
 <table border=1 bordercolor="blue">
 <tr>
 <td colspan="3" align="center"><b>General Ledger</b>
 </td>
 <td colspan="2" align="center"><b>Branch</b>
 </td>
 <td colspan="4" align="center"><b>Debit Amount</b>
 </td>
 <td colspan="4" align="center"><b>Credit Amount</b>
 </td>
 <td  align="center"><b>Net</b>
 </td>
 <td colspan="2" align="center"><b>Closing Balances</b>
 </td>
 </tr>
 <tr>
 <td><b>Type</b>
 </td>
 <td><b>Code</b>
 </td>
 <td><b>Name</b>
 </td>
 <td><b>Code</b>
 </td>
 <td><b>Name</b>
 </td>
 <td><b>Cash</b>
 </td>
 <td><b>Clearing</b>
 </td>
 <td><b>Transfer</b>
 </td>
 <td><b>Total</b>
 </td>
 <td><b>Cash</b>
 </td>
 <td><b>Clearing</b>
 </td>
 <td><b>Transfer</b>
 </td>
 <td><b>Total</b>
 </td>
 <td><b>Transaction</b>
 </td>
 <td><b>Debit</b>
 </td>
 <td><b>Credit</b>
 </td>
 </tr>
 </table>
 <%}
	 }%>
 <table>
 <%
 if(objArrayData!=null ){
	 for(int j=0;j<objArrayData.length;j++){%><tr>
	 <%for(int k=0;k<12;k++){	 
%>
 
 
 <td><%=objArrayData[j][k]%>
 </td>
 <%} %>
 
 
 
 <%}%>
 </tr><%
	 }
	 %>
 
 
 </table>
 
 
 
 
 
 
</html:form>
</body>
</html>