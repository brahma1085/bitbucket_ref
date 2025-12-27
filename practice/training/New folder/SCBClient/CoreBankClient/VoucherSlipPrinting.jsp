<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>   
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Daily Posting</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Voucher Slip Printing</center></h2>
      <hr>
      
    
    <script type="text/javascript">
     function set(target){
       document.forms[0].forward.value=target
      };
      
      function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
     
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
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%String result=(String)request.getAttribute("result"); 
String[][] comboCodeArrayVoucher=(String[][])request.getAttribute("codetypevoucher");
String showVoucher=(String)request.getAttribute("ShowVoucher");
String noVoucher=(String)request.getAttribute("NoVoucher");

String[][] VouchingSlipData=(String[][])request.getAttribute("VouchingSlipPrinting");
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
<html:form action="/GL/GLVoucherSlipPrinting?pageId=12011">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
 
   <tr>
    <td><b>Slip Date</b>    
      <html:text property="slipDate" onblur="return checkformat(this)" size="10"  styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue"></html:text>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           
    </td>
    
   </tr>
   <tr>
   <td>
   <b>GL Type:</b><html:select property="glTypes" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <html:option value="alltypes">All Types</html:option>
     <c:if test="${requestScope.moduleobjvoucher!=null}">
     <c:forEach var="modules" varStatus="count" items="${requestScope.moduleobjvoucher}" >
                        	    <html:option value="${modules.moduleAbbrv}" ><c:out value="${modules.moduleAbbrv}"></c:out></html:option>
                        </c:forEach>
                        </c:if>
     
    
     
   </html:select>
   
   </td>
   <td>
   <b>Credit</b>  <html:radio property="radioType" value="credit"></html:radio>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <b>Debit</b>   <html:radio property="radioType" value="debit"></html:radio>
   </td>
   </tr>
   <tr>
   <td>
   <b>From GL No:</b>   <html:select property="fromGlNo" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <html:option value="FromGl">select</html:option>
     <c:if test="${requestScope.codetypevoucher==null}">
       <c:out value="No Transactions"></c:out>
     </c:if>
     
     <%      for(int i=0;i<comboCodeArrayVoucher.length;i++)
                            {
                                if(!comboCodeArrayVoucher[i][1].endsWith("000"))
                                {%>
                                <html:option value="<%=comboCodeArrayVoucher[i][1] %>" ><%=comboCodeArrayVoucher[i][1] %></html:option>
                                   <%
                                }
                            }
     
     %>            
     
     
     
      </html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <b>To GL No:</b>   <html:select property="toGlNo" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <html:option value="toGl">select</html:option>
     <c:if test="${requestScope.codetypevoucher==null}">
       <c:out value="No Transactions"></c:out>
     </c:if>
     
     <%      
     
     for(int i=0;i<comboCodeArrayVoucher.length;i++)
                            {
                                if(!comboCodeArrayVoucher[i][1].endsWith("000"))
                                {%>
                                <html:option value="<%=comboCodeArrayVoucher[i][1] %>" ><%=comboCodeArrayVoucher[i][1] %></html:option>
                                   <%
                                }
                            }
     
     %>            
     
     
     
     
     </html:select>
   </td>
   <!--<td>
   <b>Pre Printed Stationary</b>&nbsp;&nbsp;&nbsp;<html:checkbox property="printedStationary"></html:checkbox>
   </td>
   --></tr>
  
   <tr>
     <td>
        <html:button  property="view"  onclick="setFlag('view') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message></html:button>
        <!--<html:button  property="file"  onclick="set('file')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.file"></bean:message> </html:button>
        --><html:button  property="printFile" onclick="window.print() " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button>
        <html:button  property="clearForm"  onclick="setFlag('clear') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message></html:button>
     </td>
   </tr>
   
 </table>
 <%if(showVoucher!=null && VouchingSlipData!=null){
	  %>
 <div id="voucherslip" style="display:block;overflow:scroll;width:700px;height:250px">
 <table name="voucherslip" align="center" bgcolor="white" style="border:thin solid #000000;">
 <%for(int i=0;i<VouchingSlipData.length;i++){ %>
   <tr>
     <td colspan="4" align="center">
     <font style="font-family:Book Antiqua;"><%=VouchingSlipData[i][0] %></font>
     </td>
   </tr>
   <tr>
     <td colspan="4" align="center">
     <font style="font-family:Book Antiqua;color:red"><%=VouchingSlipData[i][1] %></font>
     </td>
    
   </tr>
   <tr>
     <td colspan="4" align="center">
     <font style="font-family:Book Antiqua;color:red"><%=VouchingSlipData[i][2] %></font>
     </td>
    
   </tr>
   <tr>
     <td style="font-family:Book Antiqua;">GLCode:&nbsp;&nbsp;&nbsp;<%=VouchingSlipData[i][3] %></td>
     <td>&nbsp;</td><td>&nbsp;</td>
     <td style="font-family:Book Antiqua;">
     Slip Date:&nbsp;&nbsp;&nbsp;<%=VouchingSlipData[i][4] %>
     </td>
     
   </tr>
   <tr>
      <td colspan="4" align="left" style="font-family:Book Antiqua;">
       Credit : <%=VouchingSlipData[i][5] %>     
      </td>
      
   </tr>
   <tr>
     <td colspan="3" align="left" style="font-family:Book Antiqua;">
     By:&nbsp;&nbsp; <%=VouchingSlipData[i][6] %>
     </td>
     
     
     <td style="font-family:Book Antiqua;color:red">
     Rs.: <%=VouchingSlipData[i][7] %>
     </td>
     
   </tr>
   <tr>
     <td colspan="4" align="left" style="font-family:Book Antiqua;">
     Rupees(In Words):<%=VouchingSlipData[i][8] %>
     </td>
   </tr>
   <tr>
   <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
   </tr>
   <tr>
     <td colspan="4" style="font-family:Book Antiqua;" align="center">
     <b>CLERK</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <b>ACCOUNTANT</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <b>MANAGER</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     </td>
   </tr>
   <tr>
     <td colspan="4" align="center" style="font-family:Book Antiqua;color:red">
     --------------------------------------------------------------------------------------------------
     </td>
   </tr>
   <%} %>
 </table>
 </div>
 <% 
 }
 %>
 <%if(noVoucher!=null){ %>
 <table name="voucherslip" align="center" bgcolor="white">
 <tr>
   <td colspan="4" style="font-family:Book Antiqua;color:red">
   No Records Found to Display
   </td>
 </tr>
 </table>
 <%} %>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>