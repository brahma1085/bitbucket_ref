<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>   
<%@page import="java.util.Map"%>
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head><title>Transfer Scroll Printing</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Transfer Scroll Printing</center></h2>
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
    function checkformat(ids) 
	        {
            
                      var cha =   event.keyCode;
                      var format = true;
                      var allow=true;

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
		
		  return true;
		}
		else{
		 return false;
		}

        }	
     
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
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%!
double total=0.0;
int count=0;
%>
<%String result=(String)request.getAttribute("result"); 
ModuleObject[] modobj=(ModuleObject[])request.getAttribute("trfModuleObj1");
String msgTrf=(String)request.getAttribute("newstringcode");
Object[][] trfScrollData=(Object[][])request.getAttribute("TrfScrollPrintingObj");
// total=Double.parseDouble(request.getAttribute("grandtotal").toString());
 String tot=(String)request.getAttribute("grandtotal");
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

%>

<%

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
<html:form action="/GL/GLTrfScrollPrinting?pageId=12010">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
   <tr>
    <td><b>From Date</b>    
      <html:text property="fromDate" size="10" onblur="return checkformat(this)" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue"></html:text>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <b>To Date</b>    
      <html:text property="toDate"  size="10" onblur="return checkformat(this)" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue"></html:text>
      
    </td>
    
   </tr>
   <tr>
   <td>
   <b>Acc Type:</b><html:select property="types" onblur="setFlag('from_acctype')" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <html:option value="alltypes">All Types</html:option>
     <html:option value="Loans">Loans</html:option>
     <html:option value="Loans_on_deposit">Loans On Deposit</html:option>
   </html:select>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <html:hidden property="flag" />
   <html:select property="codes" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <html:option value="allcodes">All Codes</html:option>
      <c:if test="${requestScope.trfModuleObj!=null}">
     <c:forEach var="modules" varStatus="count" items="${requestScope.trfModuleObj}" >
                        	    <html:option value="${modules.moduleAbbrv}" ><c:out value="${modules.moduleAbbrv}"></c:out>--<c:out value="${modules.moduleDesc}"></c:out></html:option>
                        </c:forEach>
                        </c:if>
                     
      <%
      if(msgTrf!=null){
      
      %>    
      <font color="red"> <%=msgTrf %> </font>  
      <%} %>         
      
                        
  
     
     
     
     
     
   </html:select>
   </td>
   </tr>
   
   <tr>
     <td>
        <html:button  property="view"  onclick="setFlag('view')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message> </html:button>
        <!--<html:button  property="file"  onclick="set('file')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.file"></bean:message></html:button>
        --><html:button  property="printFile" onclick="window.print() " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button>
        <html:button  property="clearForm" onclick="setFlag('clear')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:button>
         <html:button  property="saveForm" onclick="setFlag('save')" styleClass="ButtonBackgroundImage">DownLoad</html:button>
     </td>
   </tr>
   
 </table>
 <div id="trialbalance" style="display:block;overflow:scroll;width:700px;height:250px">
 <table border="1" bordercolor="blue">
 <tr><td align="center"><b>Transfer</b></td><td colspan="3" align="center"><b>Transfer From</b></td><td colspan="3" align="center"><b>Transfer To</b></td><td align="center"><b>Amount</b></td><td colspan="2" align="center"><b>Data Entry</b></td><td colspan="2" align="center"><b>Verification</b></td></tr>
 <tr>
 <td><b>Scroll No</b></td>
 <td><b>Dr AcType</b></td>
 <td><b>Dr AcNo</b></td>
 <td><b>Name</b></td>
 <td><b>Cr AcType</b></td>
 <td><b>Cr AcNo</b></td>
 <td><b>Name</b></td>
 <td><b>Amount </b></td>
 <td><b>Tml</b></td>
 <td><b>User</b></td>
 <td><b>Tml</b></td>
 <td><b>User</b></td>
 </tr>
 <%
 if(trfScrollData!=null ){
	 for(int j=0;j<trfScrollData.length;j++){
		 if(count!=12){
	 %><tr>
	 <%for(int k=0;k<12;k++){
		 if(trfScrollData[j][k]!=null){	 
	 
%>
 
 
 <td><%=trfScrollData[j][k]%>
 </td>
 <%}else
	 count++;
	 }%>
 
 
 
 <%}
 }%>
 </tr><%
	 }
 
	 %>
 
 
 
 </table>
 </div>
 
 <%
 if(tot!=null){
 %>
 <font color="red">
 <b>Grand Total</b>
 <b><%=tot %> </b>
 </font>
 <%} %>
 
 
 
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>