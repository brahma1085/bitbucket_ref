<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<%@page import="masterObject.share.ShareCategoryObject"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.backOffice.ClosingBalObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ClosingBalanceReport</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
	      
     <script language="javascript">
      
      //submitting
       function set(target){
           
       document.forms[0].forward.value=target;
           
          };
          
       
       //clearing form
        function clears(){
        alert("clearing form");
         	    } ;  
         	    
 function datevalidation(ids){
    	var format = true;
        var allow=true;
    	var ff=ids.value;
    	var dd=ff.split('/');
    	
    	var ss=document.forms[0].sysdate.value;
    	var dds=ss.split('/');
    	
    	if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          alert (  " Date format should be:DD/MM/YYYY" );
                         ids.value="";
                          format = false ;
                          allow=false;
                          

                      } else if (  i==2 && dd[i].length != 4 ){

                           alert (  " Date format should be:DD/MM/YYYY " );
                          ids.value="";
                           format = false ;
                           allow=false;
                      }

            	 }
             } else{
                    allow=false;
             		format = false ;
             		alert (  " Date format should be:DD/MM/YYYY " );
             		document.forms[0].fdate.value="";
             }
            if(allow){
            	
            	var day=dd[0];
            	var month=dd[1];
            	var year=dd[2];
            	var fdays;
            	if(month==2)
            	{
            	if((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0)))
            	{
            		if(day>29)
            		{
            			alert("Days should be less than 29 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	else
            	{
            		if(day>28)
            		{
            			alert("Days should be less than 28 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	}
            	
            	if(month>1 || month<12){
            	if(month == 4 || month==6||month==9||month==11)
            	{
            		if(day>30)
            		{
            			alert("Days should be less than 31 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	else
            	{
            		if(day>31)
            		{
            			alert("Days should be less than 32 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            		
            	}
            	}
            	if(month>12)
            	{
            		alert("Month should  be greater than 0 and lesser than 13");
            		ids.value="";
                           format = false ;
                           allow=false;
            	}
            	
            	if(year<1900 || year>9999)
            	{
            		alert("Year should be in between 1900 to 9999 ");
            		ids.value="";
                           format = false ;
                           allow=false;
            	}
            	if(dd[0].length==2||dd[1].length==2||dd[2].length==4)
            	{
            		if(dd[2]>dds[2])
            		{
            			alert(" year should be less than or equal to"+dds[2]+" !");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}	
            		else{
            			if(dd[2]==dds[2]){
            				if(dd[1]>dds[1]){
	            				alert(" Month should be less than or equal to"+dds[1]+" !");
	            				ids.value="";
	                           	format = false ;
	                          	 allow=false;		
            						
            				}else{
            					if(dd[1]==dds[1]){
            						if(dd[0]>dds[0]){
											alert(" Day should be less than or equal to"+dds[0]+" !");
				            				ids.value="";
				                           	format = false ;
				                          	allow=false;           							
            						}else{
            								format = true ;
				                          	allow=true; 
            						}
            					
            					}
            				}
            			}
            		}
            	}            	          	
             }
        }
    
   function numbersonly_date(eve){
         var cha = event.keyCode

            if (  ( cha  >= 47 && cha < 58  ) ) {
              
                return true ;
                            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
         
        
      };       	    
         function callMe1(){
         	    
         	
         	    
         	    if(document.forms[0].acctype.value=="1001001"){
         	    document.forms[0].shcategory.disabled=false;
         	    
         	     }
         	    
         	    if(document.forms[0].acctype.value!="1001001"){
         	    document.forms[0].shcategory.disabled=true;
         	   
         	    }  	    
         	    }
       //records validation    
       function CallMe(){
      
       if(document.getElementById("valid").value!=null)
       {
       	if(document.getElementById("valid").value=="Recordsnotfound")
       	{
         	alert("Records Not Found");
        } 
       }
    
       }; 
           	    
      </script> 
     

</head>
<body class="Mainbody" style="overflow: scroll;" onload="CallMe()">
<%! String date; %>
<% date=(String)request.getAttribute("date");
   System.out.println("Date------>"+date);
  String[] select=(String[])request.getAttribute("Acccategory");
  System.out.println("select----->"+select);
  ShareCategoryObject[] sharecat=(ShareCategoryObject[])request.getAttribute("sharecate");
  System.out.println("sharecat------>"+sharecat);
  ModuleObject[] mod=(ModuleObject[])request.getAttribute("acctype");
  System.out.println("mod------>"+mod);
  ClosingBalObject[] array_closingbalobject_receive_sb=(ClosingBalObject[])request.getAttribute("CloseBalSB");
  System.out.println("array_closingbalobject_receiveSB------>"+array_closingbalobject_receive_sb);
  ClosingBalObject[] array_closingbalobject_receive_sh=(ClosingBalObject[])request.getAttribute("CloseBalSH");
  System.out.println("array_closingbalobject_receiveSH------>"+array_closingbalobject_receive_sh);
  ClosingBalObject[] array_closingbalobject_receive_dep=(ClosingBalObject[])request.getAttribute("CloseBalDEP");
  System.out.println("array_closingbalobject_receiveDEP------>"+array_closingbalobject_receive_dep);
  ClosingBalObject[] array_closingbalobject_receive_ln=(ClosingBalObject[])request.getAttribute("CloseBalLN");
  System.out.println("array_closingbalobject_receiveLN------>"+array_closingbalobject_receive_ln);
  ClosingBalObject[] array_closingbalobject_receive_lnd=(ClosingBalObject[])request.getAttribute("CloseBalLND");
  System.out.println("array_closingbalobject_receiveLND------>"+array_closingbalobject_receive_lnd);
  ClosingBalObject[] array_closingbalobject_receive_PdAccwise=(ClosingBalObject[])request.getAttribute("CloseBalPDACCWISE");
  System.out.println("array_closingbalobject_PdAccwise------>"+array_closingbalobject_receive_PdAccwise);
 // ClosingBalObject[] array_closingbalobject_receive_PdAgntwise=(ClosingBalObject[])request.getAttribute("CloseBalPDAGENTWISE");
  //System.out.println("array_closingbalobject_PdAgentwise------>"+array_closingbalobject_receive_PdAgntwise);
%>

<center><h2 class="h2">Transaction With Closing Balance Report</h2></center>
<%@page import="java.util.Map"%>


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
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/BackOffice/ClosingBalanceReport?pageId=11012">

<center>
 <% String displaymsg=(String)request.getAttribute("displaymsg");%>
<%if(displaymsg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=displaymsg %></font>
<%} %>
<table class="txtTable">
 	<tr>
		<td><bean:message key="label.acType" ></bean:message></td>
			<td><html:select property="acctype" size="1" onchange="callMe1()">
			<html:option value="Select">Select</html:option>
                 	<% for(int i=0;i<mod.length;i++){%>	
				<html:option value="<%=mod[i].getModuleCode()%>" ><%=mod[i].getModuleAbbrv()%></html:option>
 		          <%} %>
			</html:select>
		</td>
		<td><bean:message key="label.frm_dt"></bean:message><html:text property="fromdate" size="10"  onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)" ></html:text></td>
		<td><bean:message key="label.to_dt"></bean:message><html:text property="todate" size="10"  onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)" ></html:text></td>
        
</tr>
</table>
<br>
<table class="txtTable">
<tr>
<td></td><td></td>
<td><bean:message key="label.acccategory"></bean:message>
			<html:select property="acccategory" >
			
			 <% for(int i=0;i<select.length;i++){%>	
				<html:option value="<%=select[i]%>" ><%=select[i]%></html:option>
 		<%} %>
			</html:select>
</td>
<td><bean:message key="label.sh_category"></bean:message>
			<html:select property="shcategory" disabled="true">
			   <% if(sharecat!=null){ 
			  for(int i=0;i<sharecat.length;i++){%>
				<html:option value="<%=sharecat[i].getCatName() %>" ><%=sharecat[i].getCatName() %></html:option>
 		<%}} %>
			</html:select>
</td>
<td>
</tr>
</table>
<br><br>

		
<table class="txtTable">	
	<tr>
	    
	     <html:hidden property="forward" value="error"></html:hidden>
	     <html:hidden property="valid" styleId="valid"></html:hidden>
	     <html:hidden property="sysdate" />
	     
	   <td>
	   <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
		  <html:submit  onclick="set('view')" styleClass="ButtonBackgroundImage"><bean:message key="label.view1"></bean:message> </html:submit>
		<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
		
          <!--<html:button  onclick="window.print()" property="printFile" styleClass="ButtonBackgroundImage"><bean:message key="label.print" ></bean:message> </html:button>-->
		  <html:submit onclick="set('clear')" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message></html:submit>
		<html:submit onclick="set('save')" styleClass="ButtonBackgroundImage">DownLoad</html:submit>
		</td>  
	</tr>
</table>
<table>
<tr>
<td>
    
</tr>
</table>
<br>

<div style="overflow:scroll;width:550px;height:150px">
<%if(array_closingbalobject_receive_sb!=null){ %>
<display:table name="CloseBalSB" style="background-color:#E4D5BE">
   
   <display:column title="A/C No"   property="acNo"       	style="background-color:#f5dfc9"></display:column>
   <display:column title="Name"     property="name"      	style="background-color:#f5dfc9"></display:column>
   <display:column title="O/B"      property="openBal"    	style="background-color:#f5dfc9"></display:column>
   <display:column title="Receipts" property="creditAmt"    style="background-color:#f5dfc9"></display:column>
   <display:column title="Pavments" property="debitAmt" 	style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B"      property="clBal" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="Interest" property="interest" 	style="background-color:#f5dfc9"></display:column>  

</display:table>
<%} %>
<%if(array_closingbalobject_receive_sh!=null){ %>
<table>
<tr>
<th>A/C No</th>
<th>Name</th>
<th>Share Type</th>
<th>O/B</th>
<th>Allots</th>
<th>Withdrawal</th>
<th>Permanent (C/B)</th>
<th>Permanent(Int)</th>
</tr>
<%for(int i=0;i<array_closingbalobject_receive_sh.length;i++){ %>
<%if(array_closingbalobject_receive_sh[i]!=null){ %>
<tr>
<td><%=""+array_closingbalobject_receive_sh[i].getAcNo() %></td>
<td><%=""+array_closingbalobject_receive_sh[i].getName() %></td>
<td><%=""+array_closingbalobject_receive_sh[i].getShareInd() %></td>
<td><%=""+array_closingbalobject_receive_sh[i].getOpenBal() %></td>
<td><%=""+array_closingbalobject_receive_sh[i].getCreditAmt() %></td>
<td><%=""+array_closingbalobject_receive_sh[i].getDebitAmt() %></td>
<td><%=""+array_closingbalobject_receive_sh[i].getClBal() %></td>
<td><%=""+array_closingbalobject_receive_sh[i].getInterest() %></td>
</tr>
<%}%>
<%} %>
</table>
<%} %>

<%if(array_closingbalobject_receive_dep!=null){ %>
 
<display:table name="CloseBalDEP" requestURI="/BackOffice/ClosingBalanceReport.do" style="background-color:#E4D5BE">
  
   <display:column title="A/C No"          property="acNo"       	style="background-color:#f5dfc9"></display:column>
   <display:column title="Name"    		   property="name"      	style="background-color:#f5dfc9"></display:column>
   <display:column title="O/B " 		   property="openBal"    	style="background-color:#f5dfc9"></display:column>
   <display:column title="Received"        property="creditAmt"    	style="background-color:#f5dfc9"></display:column>
   <display:column title="Repayments " 	   property="debitAmt" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B"      	   property="clBal" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="O/B (Int)" 	   property="intOpenBal" 	style="background-color:#f5dfc9"></display:column>
   <display:column title="Accrued" 		   property="intAcd" 		style="background-color:#f5dfc9"></display:column>  
   <display:column title="Paid" 		   property="intPaid" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B (Int)"       property="intClBal" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="Net C/B"         property="netClBal" 		style="background-color:#f5dfc9"></display:column>

</display:table>
<%} %>



 <%if(array_closingbalobject_receive_ln!=null){ %>
<display:table name="CloseBalLN"  requestURI="/BackOffice/ClosingBalanceReport.do" style="background-color:#E4D5BE">
  
   <display:column title="A/C No"          property="acNo"       	style="background-color:#f5dfc9"></display:column>
   <display:column title="Name"    		   property="name"      	style="background-color:#f5dfc9"></display:column>
   <display:column title="O/B " 		   property="openBal"    	style="background-color:#f5dfc9"></display:column>
   <display:column title="Disp Amt"        property="creditAmt"    	style="background-color:#f5dfc9"></display:column>
   <display:column title="Recovery Amt "   property="debitAmt" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B"      	   property="clBal" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="Int Recd" 	   property="intRcd" 	style="background-color:#f5dfc9"></display:column>
   <display:column title="Int Date" 	   property="intDate" 		style="background-color:#f5dfc9"></display:column>  
   <display:column title="P-Int Recd" 	   property="intRcd" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="Dr Amt"          property="otherAmt" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="Recy"            property="otherRecoveryAmt" 		style="background-color:#f5dfc9"></display:column>


</display:table>
<%} %>


<%if(array_closingbalobject_receive_lnd!=null){ %>
<display:table name="CloseBalLND" requestURI="/BackOffice/ClosingBalanceReport.do" style="background-color:#E4D5BE">
  
   <display:column title="A/C No"          property="acNo"       	style="background-color:#f5dfc9"></display:column>
   <display:column title="Name"    		   property="name"      	style="background-color:#f5dfc9"></display:column>
   <display:column title="O/B " 		   property="openBal"    	style="background-color:#f5dfc9"></display:column>
   <display:column title="Disp Amt"        property="creditAmt"    	style="background-color:#f5dfc9"></display:column>
   <display:column title="Recy Pr Amt  "   property="debitAmt" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="Recy Int Amt"    property="intRcd" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B" 	   		   property="clBal" 	style="background-color:#f5dfc9"></display:column>
   
</display:table>
<%} %>



 <%if(array_closingbalobject_receive_PdAccwise!=null){ %>
<display:table name="CloseBalPDACCWISE" requestURI="/BackOffice/ClosingBalanceReport.do" style="background-color:#E4D5BE">
  
   <display:column title="A/C No"          property="pgAcNo"       	style="background-color:#f5dfc9"></display:column>
   <display:column title="Name"    		   property="name"      	style="background-color:#f5dfc9"></display:column>
   <display:column title="O/B " 		   property="openBal"    	style="background-color:#f5dfc9"></display:column>
   <display:column title="Receipts"        property="creditAmt"    	style="background-color:#f5dfc9"></display:column>
   <display:column title="Pavments"   	   property="debitAmt" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B "    		   property="clBal" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="O/B (Int)" 	   property="intOpenBal" 	style="background-color:#f5dfc9"></display:column>
   <display:column title="Accrued  "       property="intAcd" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="Paid "           property="intPaid" 		style="background-color:#f5dfc9"></display:column>
   <display:column title="C/B(Int) "       property="intClBal" 		style="background-color:#f5dfc9"></display:column>
   
</display:table>
<%} %>
</div>

</center>
	
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>