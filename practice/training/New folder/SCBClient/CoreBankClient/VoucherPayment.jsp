<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html"  uri="/WEB-INF/struts-html.tld"%> 
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>   
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
    
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Voucher Payment</title>
<center><h2 class="h2">Voucher Payment</h2></center>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function showalert()
{
 alert("Hello");
}
function setvouch(){

    
	if(document.getElementById("totaltesting").value!=0)
	{
		
		alert(document.getElementById("totaltesting").value);	
		
			
	}
	

}

function addSubmit()
{
   if(document.forms[0].ac_no.value=="" || document.forms[0].ac_no.value==0)
   {
    alert("Please enter the AccountNumber");
   }
   else
   {
       document.forms[0].submit();
   }

}

function Display()
{      
    if(document.getElementById("pcombo").value=="Cash")
	{	
		document.getElementById("combo_pay_actype_lab").style.display='none';
		document.getElementById("combo_pay_actype_select").style.display='none';
		document.getElementById("payac_no").style.display='none';
	
	}else if(document.getElementById("pcombo").value=="Transfer")
	{
	    document.getElementById("combo_pay_actype_lab").style.display='block';
		document.getElementById("combo_pay_actype_select").style.display='block';
		document.getElementById("payac_no").style.display='block';
	}
	else
	{
	    document.getElementById("combo_pay_actype_lab").style.display='none';
		document.getElementById("combo_pay_actype_select").style.display='none';
		document.getElementById("payac_no").style.display='none';
	}
    
}

function numbersonly(eve){
         var cha = event.keyCode


            if (  ( cha>=48 && cha<58  ) ) {


              
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
         
        
      };

function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
   



function setchecked(){

alert("Want To Do Interest Payment??");

var close = confirm("Want To Do Interest Payment??");
if(close)
{


document.getElementById("flagcheck").value="true";
document.forms[0].submit();

}
else	{
alert("else loooooooooppppppp"+close);
document.getElementById("flagcheck").value="false";
return false;
		}
		
		
};	
function beforSubmit(){

alert(document.forms[0].details.value);
document.forms[0].submit();

};


</script>





</head>
<body class="Mainbody()"  onload="setvouch()">
<%!
ModuleObject[] array_module,module_object;
String[] pay_mode,details;
Double amt;
List trf_list;
String pagenew;
%>
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
<%
String nstr=(String)request.getAttribute("flag");
System.out.println("inside 0"+nstr);
System.out.println("R u getting class cast exception?????????????????????????????");
trf_list=(ArrayList)request.getAttribute("voucherpayment");
amt=(Double)request.getAttribute("trf_amt");
System.out.println("The amt in JSP is )))))))))))))))))"+amt);


%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/TermDeposit/VoucherPayment?pageId=13011" focus="<%=""+request.getAttribute("getfocus")%>">

<table  class = "txtTable"  cellspacing="3">
	
	<html:hidden property="flag" styleId="flagcheck" ></html:hidden>
	<html:hidden property="testing" styleId="totaltesting"></html:hidden>
	
 <td>
	<table class = "txtTable" width="20%" cellspacing="3">
		<tr>
		   		<td>
			       <bean:message key="label.td_actype"></bean:message>
			    </td>
			    <td><html:select property = "ac_type" styleClass="formTextFieldWithoutTransparent">
			    	<% array_module=(ModuleObject[])request.getAttribute("Dep type");
			    			for(int i=0;i<array_module.length;i++)
			    			{
			        		 	System.out.println("acctype"+ array_module);
			    	%>	    	
			    				<html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%
			    			}
			    	%>	
			    	 </html:select>
    		   	 </td>
    		   	 
    	 		  	
		<td>
			  <bean:message key="label.td_acno"></bean:message>
			 	
			 <html:text property="ac_no"   size="8"  onblur="setchecked()"  onkeypress="return numbersonly(this)" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:text>
		</td>
			</tr>
   		  	
   		  	
			   
</table>			   
<hr>

<hr>
<table>
<tr>

<td>
<div id = "table1" style="display:block; overflow:scroll; width: 550px;height: 300px">


<%if(trf_list!=null){
	
%>
<font color="red">Can Update Only One Record At A Time</font>
<display:table name="voucherpayment" list="voucherpayment"  id="voucherpayment"  class="its"  pagesize="10">
   			 
<display:column title="Select"><input type="checkbox" name="id" value="${voucherpayment.id}"></display:column> 			 
   
<display:column title="Voucher No">

<input type="text" value="${voucherpayment.Voucher_no}"  name="Voucher no"> 

</display:column>
 

<display:column title="Voucher Type"><input type="text" name="Voucher_Amount" value="${voucherpayment.Voucher_Amount}" readonly="readonly" ></display:column>

<display:column title="Voucher Date"><input type="text" name="Voucher_date" value="${voucherpayment.Voucher_date}" readonly="readonly" ></display:column>
  
</display:table>

<%} %>	
</div>
</td>
</tr>		   

</table>			   
	
<table>	
			
<tr>
				<td>
			    	<bean:message key="label.combo_pay_mode"></bean:message>
			    </td>
			    <td>	
			    	<html:select property="pay_mode" styleClass="formTextFieldWithoutTransparent" styleId="pcombo" onchange="Display()">	
			        	<%pay_mode=(String[])request.getAttribute("pay_mode");
			             	 for(int i=0;i < pay_mode.length;i++)
			             	 {
						    	 System.out.println("pay_mode"+ pay_mode);
				    	
			    	    %>	   			    			    	 
			    				<html:option value="<%=""+pay_mode[i]%>"><%=""+pay_mode[i]%></html:option>
			    		<%	  }		%>
			        	 
			           </html:select>
			           
			        		        	 
			     </td>
			     
			</tr>
			    
			    
			<tr>
				<td>
			    <div id="combo_pay_actype_lab" style="display:none;">	<bean:message key="label.combo_pay_actype"></bean:message></div>
			    </td>
			    <td>
			    	<div id="combo_pay_actype_select" style="display:none;"><html:select property="pay_ac_type" styleClass="formTextFieldWithoutTransparent" >
			        <%module_object=(ModuleObject[])request.getAttribute("pay_actype");
			        	for(int i=0;i < module_object.length;i++)
			            {
						 	 System.out.println("module_object"+ module_object);
				    	
			    	 %>	   			    			    	 
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    	<%	}	%>
			        	 
			         </html:select>
			         </div>
			    </td>  
			    
			    <td>
			       		<div id="payac_no" style="display:none;"><html:text property="pay_mode_ac_no" styleClass="formTextFieldWithoutTransparent"></html:text></div>
			       </td>      			         
			</tr> 
			   
			   <tr>
			   
			   <td>
			            <bean:message key="label.details"></bean:message>
			       </td>
			       <td>
			            <html:select property="details" styleClass="formTextFieldWithoutTransparent" onchange="beforSubmit()">
			          <html:option value="Select">Select</html:option>
			          <html:option value="Personal">Personal</html:option>
			        			         
			         	</html:select>
			       	</td>
			   
			   </tr>
			   
	<table>		   
			 <tr>
			 <td>
			 <html:submit property="add" value="add" styleClass="ButtonBackgroundImage"></html:submit>
			 
			 </td>
			 <td>
			 
			 
			 </td>
		</tr>
		<tr>
		<td>

<table align="right"  valign="top" width="10%">
<%if( nstr!=null){%>
    <tr> 
    <td>
	<% System.out.println("At 1086 ApplDataEntry.jsp===============================================nstr  >"+nstr);
    pagenew=nstr;%>
	   <jsp:include page="<%=pagenew %>"></jsp:include>
	   
			   <%System.out.println("After include------>"+pagenew);%>
    </td>
		</tr>
	</table>		
</table>

 
			   


</td>

    </tr>
      
		 
				 
				 
	<%} %>
        
  
 </table>
</td>
</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>


</body>
</html>