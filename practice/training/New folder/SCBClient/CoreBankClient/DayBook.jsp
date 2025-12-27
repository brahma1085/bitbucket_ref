<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@taglib prefix="c" uri="/WEB-INF/c-rt.tld" %> 
<%@page import="java.util.Map"%>  
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.generalLedger.DayBookObject"%>
<%@page import="masterObject.generalLedger.DayBookObjectNew"%>
<html>
<head><title>Daily Posting</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      
      
      
    
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
      function checkformat(ids, flagVal) 
	        {
            
                      var cha =   event.keyCode;
                      var format = true;
                      var allow=true;
                      var flagValue=flagVal;

             var ff =  ids.value;

            var dd = ff.split( '/' );

			if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          alert (  " problem in date format " );
                          format = false ;
                          allow=false;
                          

                      } else if (  i==2 && dd[i].length != 4 ){

                           alert (  " problem in date format " );
                           format = false ;
                           allow=false;
                      }

            	 }
             } else{
                    allow=false;
             		format = false ;
             		alert (  " problem in date format " );
             }

        if(format){
        var date=new Date();
         var dayCheck=dd[0];
         
         var monthCheck=dd[1];
         var yrCheck=dd[2];
         var mth=dd[1];
         
         dd[1]=dd[1]-1;
	   var days = 32 - new Date(dd[2],dd[1],32).getDate();
         if(dd[0].length==2){
          if ( dd[0] > days  )
                        {

                              alert("Day should not be greater than "+days);
                              allow=false;
                              
                        }
                        
          
          if(dd[0]==00){
          alert("There is no date with 00");
          allow=false;
          }
          

          if(mth<1 || mth>12){
          alert("Month should be greater than 0 and \n lessthan 13  "+mth);
          allow=false;
          }
          }
          



         
          

         if(dd[2].length==4){
          if((parseInt(dd[2])<=parseInt(date.getYear()))){
          
                        
                        
                        
          }
          else{
          alert("Year should be less than "+date.getYear());
          allow=false;
          }
          }
          


         }
		if(allow){
		
		  setFlag(flagValue);
		  return true;
		}
		else{
		 return false;
		}

        }	
      
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
     function setValue(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()" bgcolor="#edeefe">
<h2 class="h2">
      <center>Day Book</center></h2>
<%!
int count=0;
%>
<%
System.out.println("Hi_______________------------");
String result=(String)request.getAttribute("result");
String trialdaybook=(String)request.getAttribute("trialdaybook");
Object[][] daybookObj=(Object[][])request.getAttribute("daybookDataObj");
Object[][] daybookObj1=(Object[][])request.getAttribute("daybookDataObj1");
String msg=(String)request.getAttribute("msg");
%>

<%if(msg!=null){ %>
  <font color="red"><%=msg %></font>
  <br><br>
<%} %>
<%!
String access;
String  accesspageId;
 Map user_role;
String dwnload=null;
String shwdwn=null;
Boolean showBtn=new Boolean(true);
%>

<%
  dwnload=(String)request.getAttribute("Youcandownload");
shwdwn=(String)request.getAttribute("showdwn");
if(shwdwn!=null){
	showBtn=new Boolean(shwdwn);	
}
  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");

if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}

// Note:access=1111==>read,insert,delete,update
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/GL/GLDayBook?pageId=12008">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
 <input type="hidden" name="fname" />
   <tr>
    <td><b>Enter Date</b>    
      <html:text property="date" onblur="return checkformat(this,'fromdate')" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue" ></html:text>
    </td>
   </tr>
   <tr>
   <td>
   <html:radio property="userChoice" value="DayBook" onclick="setFlag('from_radio')">DayBook</html:radio>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <html:radio property="userChoice" value="TrailBalance" onclick="setFlag('from_radio')">Trial Balance</html:radio>
   
   </td>
   </tr>
   
   <tr>
     <td>
        <html:button  property="view"  onclick="setFlag('view'); " styleClass="ButtonBackgroundImage" ><bean:message key="gl.label.view"></bean:message> </html:button>
        <%if(dwnload!=null){ %>
       <html:button  property="file" onclick="setValue('file')" disabled="<%=showBtn %>" styleClass="ButtonBackgroundImage">DownLoad</html:button>
       <%} %>
        <html:button  property="printFile" onclick="window.print() " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button>
        <html:reset   styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:reset>
     </td>
   </tr>
 </table>
 <%!
 DayBookObject dbook[]=null;
 DayBookObjectNew dbook1[]=null;
 %>
 <%
 dbook=(DayBookObject[])request.getAttribute("DisplayDaybookObject");
 dbook1=(DayBookObjectNew[])request.getAttribute("DayBookNewObject");
 %>
 <%if(dbook1!=null){
	 if(trialdaybook.equalsIgnoreCase("daybook")){%>
 <table class="txtTable" style="background-color:#CDCEAE">
 
 </table>
 <%} 
 }%>
  <%if(trialdaybook!=null){
	 if(trialdaybook.equalsIgnoreCase("daybook")){
	 %>
<div id="daybook" style="display:block;overflow:scroll;width:700px;height:250px">	 
 <table class="txtTable" border="1" style="background-color:#CDCEAE">
 <tr><td colspan="3" align="center" style="background-color:#ECEBD2"><b>General Ledger</b></td><td colspan="4" align="center" style="background-color:#ECEBD2"><b>Debit Amount</b></td><td colspan="4" align="center" style="background-color:#ECEBD2"><b>Credit Amount</b></td><td style="background-color:#ECEBD2"><b>Net</b></td>
 
 </tr>
 <tr>
 <td style="background-color:#ECEBD2"><b>Type</b></td>
 <td style="background-color:#ECEBD2"><b>Code</b></td>
 <td style="background-color:#ECEBD2"><b>Name</b></td>
 <td style="background-color:#ECEBD2"><b>Cash</b></td>
 <td style="background-color:#ECEBD2"><b>Clearing</b></td>
 <td style="background-color:#ECEBD2"><b>Transfer</b></td>
 <td style="background-color:#ECEBD2"><b>Total</b></td>
 <td style="background-color:#ECEBD2"><b>Cash</b></td>
 <td style="background-color:#ECEBD2"><b>Clearing</b></td>
 <td style="background-color:#ECEBD2"><b>Transfer</b></td>
 <td style="background-color:#ECEBD2"><b>Total</b></td> 
 <td style="background-color:#ECEBD2"><b>Transaction</b></td>
 </tr>
  
 <%
 if(daybookObj!=null ){
	 for(int j=0;j<1;j++){
		 %>
		
		 <tr>
		 <%for(int k=0;k<12;k++){
			 
	%> 
	  
	 
	 <td style="background-color:#ECEBD2"><%=daybookObj[j][k]%>
	 </td>

	 <%}%>
	 
	 </tr>
	 
	 <%}
		 
	 %>
	 
	 
	 
	 
	 
	 
	 <%for(int j=1;j<daybookObj.length;j++){
	 if(count!=12){%>
	
	 <tr>
	 <%for(int k=0;k<12;k++){
		 if(daybookObj[j][k]!=null){
%> 
  
 
 <td style="background-color:#ECEBD2"><%=daybookObj[j][k]%>
 </td>

 <%}else
	 count++;
		 }%>
 
 </tr>
 
 <%}
	 }
 %>
 
  
 <%
	 }
	 %>
 <%
 if(daybookObj1!=null ){
	 for(int j=0;j<daybookObj1.length-1;j++){%>
	
	 <tr >
	 <%for(int k=0;k<12;k++){
		 if(daybookObj1[j][k]!=null){
%> 
  
 
 <td style="background-color:#ECEBD2" ><%=daybookObj1[j][k]%>
 </td>

 <%} 
 }%>
 
 
 </tr>
 
 <%}%>
 
  
 <%
	 }
	 %>
 

 
 </table>
  </div>
 <%
 }
 }%>
 <%if(trialdaybook!=null){
	 if(trialdaybook.equalsIgnoreCase("TrailBalance")){
	 %>
<div id="trialbalance" style="display:block;overflow:scroll;width:700px;height:250px"> 
 <table class="txtTable" border="1" style="background-color:#CDCEAE">
 <tr><td colspan="3" align="center" style="background-color:#ECEBD2"><b>General Ledger</b></td><td colspan="4" align="center" style="background-color:#ECEBD2"><b>Debit Amount</b></td><td colspan="4" align="center" style="background-color:#ECEBD2"><b>Credit Amount</b></td><td style="background-color:#ECEBD2"><b>Net</b></td>
 <td colspan="2" align="center" style="background-color:#ECEBD2"><b>Closing Balances</b></td>
 </tr>
 <tr>
 <td style="background-color:#ECEBD2"><b>Type</b></td>
 <td style="background-color:#ECEBD2"><b>Code</b></td>
 <td style="background-color:#ECEBD2"><b>Name</b></td>
 <td style="background-color:#ECEBD2"><b>Cash</b></td>
 <td style="background-color:#ECEBD2"><b>Clearing</b></td>
 <td style="background-color:#ECEBD2"><b>Transfer</b></td>
 <td style="background-color:#ECEBD2"><b>Total</b></td>
 <td style="background-color:#ECEBD2"><b>Cash</b></td>
 <td style="background-color:#ECEBD2"><b>Clearing</b></td>
 <td style="background-color:#ECEBD2"><b>Transfer</b></td>
 <td style="background-color:#ECEBD2"><b>Total</b></td>
 <td style="background-color:#ECEBD2"><b>Transaction</b></td>
 <td style="background-color:#ECEBD2"><b>Debit</b></td>
 <td style="background-color:#ECEBD2"><b>Credit</b></td>

 </tr>
 <%
 if(daybookObj!=null ){
	 for(int j=0;j<daybookObj.length;j++){
	 if(count!=14){%>
	
	 <tr>
	 <%for(int k=0;k<14;k++){
		 if(daybookObj[j][k]!=null){
%>
  
 
 <td><%=daybookObj[j][k]%>
 </td>

 <%}else
	 count++;
		 }%>
 
 
 </tr>
 
 <%}
 }%>
 
  
 <%
	 }
	 %>
 <%
 if(daybookObj1!=null ){
	 for(int j=0;j<daybookObj1.length-1;j++){%>
	
	 <tr>
	 <%for(int k=0;k<14;k++){
		 if(daybookObj1[j][k]!=null){
%>
  
 
 <td><%=daybookObj1[j][k]%>
 </td>

 <%}
		 }%>
 
 
 </tr>
 
 <%}%>
 
  
 <%
	 }
	 %>
 
 

 </table>
 </div>
  <%}
	 }%>
 
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>