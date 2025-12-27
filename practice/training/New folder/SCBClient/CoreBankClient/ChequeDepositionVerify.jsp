<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@page import="java.util.Map"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject" %>
<%@page import="masterObject.clearing.Reason" %>


<html>
<head>
<link rel="stylesheet" type="text/css" href="label.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>




<script type="text/javascript">

   function call()
	{
		
		if(document.getElementById("int_clr").checked  || (document.forms[0].form_flag.value == 4 || document.forms[0].form_flag.value == 5) )
		{
				document.getElementById("citycode").disabled=true;
				document.getElementById("bank_code").disabled=true;
				document.getElementById("branch_code").disabled=true;
				document.getElementById("branchname").value=1;
				document.getElementById("branchname").disabled=true;
				document.getElementById("chequeno").disabled=true;
				document.getElementById("chqdate").disabled=true;
				
			    document.getElementById("div_debit_pan").style.display = 'block';
		}
		if(document.getElementById("bounce_id").checked)
		{
			callBounce();
		} 
			
		checkError();	
		check_discount();
		check_laon();
		check_multi();
		
	}




	function setflag(flag_num, ids )
	{
	
		if(ids.value!="")
		{
			
			if( flag_num == 8 ) 
			{
					if(document.getElementById("ctrl_no").value==0)
					 {
					 	document.getElementById("err_mess_id").value="Control Number Can't Be Zero";
						return false;
					 }
			}
			
			
			
			 document.getElementById("flagset").value = flag_num
			 document.forms[0].booleanFlag.value=1;
			 document.forms[0].submit();
	
		}
			
	}
	
	function setcheckflag( flag_num, ids )
	{
			if(ids.checked)
			{
				document.getElementById("div_debit_pan").style.display = 'block';					
				document.forms[0].form_flag.value = flag_num;
			} 
			else 
			{
				document.getElementById("citycode").disabled=false;
				document.getElementById("bank_code").disabled=false;
				document.getElementById("branch_code").disabled=false;
				document.getElementById("bankname").value="";
				document.getElementById("branchname").readonly=true;
				document.getElementById("div_debit_pan").style.display = 'none';
			}
		
	}
	
	
	function checkError()
	{
			
			if(document.getElementById("err_id").value=='1')
			{
			clearAll();
			}
				
	}
	
	function check_laon()
	{
		
		if ( document.getElementById("loan_cre").checked ) {
			
			 
			document.getElementById("loan_div").style.display = 'block';  
			document.getElementById("loan_div1").style.display = 'block';
			
		} else {
			
			document.getElementById("loan_div").style.display = 'none';  
			document.getElementById("loan_div1").style.display = 'none';
			
		}
	
	}
	
	function check_multi()
	{
	
		if ( document.getElementById("chk_comp").checked ) 
		{
		
			document.getElementById("div_comp1").style.display = 'block';  
			document.getElementById("div_comp").style.display = 'block';
			 document.getElementById("credit_acc_type").disabled = true ;
			  document.getElementById("credit_account_no").disabled = true ;
			  
		} 
		else 
		{
			
			document.getElementById("div_comp1").style.display = 'none';  
			document.getElementById("div_comp").style.display = 'none';
			 document.getElementById("credit_acc_type").disabled = false ;
			   document.getElementById("credit_account_no").disabled = false ;
			  
			  
		}
	
	}
	
	function check_discount(){
		
		if ( document.getElementById("chk_discount").checked )
		 {
		
			document.getElementById("div_discount1").style.display = 'block';  
			document.getElementById("div_discount").style.display = 'block';
			
		} else {
			
			document.getElementById("div_discount1").style.display = 'none';  
			document.getElementById("div_discount").style.display = 'none';
			
		}
	
	}
	
	function open_div_pocomm( value ){
	
		if ( value ){
					
					document.getElementById("div_discount1").style.display = 'block';  
					document.getElementById("div_discount").style.display = 'block';
				
		} else {
				
				document.getElementById("div_discount1").style.display = 'none';  
				document.getElementById("div_discount").style.display = 'none';
		}
	
	}
	
        
        function onlynumbers(){
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        }
        
        
        function numbersonly( input ){

           var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
									
					 if ( input.value.length >= 2 )  {
							
								
							
							if ( input == document.getElementById("branch_code")   ){
								if (  input.value.length == 2 )
									input.value = input.value + String.fromCharCode(cha);
									
								return false ;
								
							}
							
							else {			 	
								
								input.value = input.value + String.fromCharCode(cha);
					 			var j = getnextFeild(input);
                				input.form[j].focus();
                				return false ;
                			}
					 
					}	
                    return true;
                    
              } else {

                        return false;

             }

                
            }
        
	
		
	
	      
     
       function callBounce()
       {
       			if(document.getElementById("bounce_id").checked)
		 		{
					document.getElementById("bounce_fine").style.display = 'block';  
				} 
				else 
				{
					document.getElementById("bounce_fine").style.display = 'none';  
				}
        }

	

	function clearAll(){
	
		var form_ele = document.forms[0].elements;
		
		var val = document.forms[0].error_message.value;
		
		for ( var i = 0 ; i< form_ele.length; i++ )
		{
			if(form_ele[i].type=="text")
				form_ele[i].value = "";
		
		}
		
		document.getElementById("bounce_id").checked = false; 
		 
		 document.forms[0].error_message.value=val;
			
		return false;
	}
		
		
	function select_ChqPo(){
	
			if ( document.getElementById("int_clr").checked  ) {
			
				if (     document.getElementById("chddpo").value == "P" ){
				
						document.getElementById( "div_debit_pan" ).style.display = 'none';
						
				
				} else if(    document.getElementById("chddpo").value == "C" ){
			
							document.getElementById( "div_debit_pan" ).style.display = 'block';
							document.getElementById( "div_pocomm" ).style.display = 'none';
							document.getElementById( "div_pocomm1" ).style.display = 'none';
							document.getElementById( "po_div" ).style.display = 'none';
				
				} 
			
			 }
	
			var val = document.getElementById("credit_acc_type").value;
		
		
		
		if ( val.substring(0,4) == "1016" ) 
		{
			
			document.getElementById("credit_acc_no").value = 0;
			document.getElementById("cr_name").value = "Pay order";
			
			document.getElementById( "div_pocomm" ).style.display = 'block';
			document.getElementById( "div_pocomm1" ).style.display = 'block';
		    
		}
			
	}	
		
	function  setDiscount()
	{
	
		var cr_code = document.getElementById("credit_acc_type").value;
		
		
		if(cr_code.substring(0,4)== "1002" || cr_code.substring(0,4) == "1007" || cr_code.substring(0,4) == "1014" ) 
		{
				document.getElementById("chk_discount").disabled = true;
		} 
		else 
		{
				document.getElementById("chk_discount").disabled = false;
		}
	
	}	
		
	</script>
	
	 <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />

	
	
  </head>
  
  
<body  class="MainBody" onload="call()">
	<center><h2 class="h2"><bean:message key="label.ChequeVerify"></bean:message></h2></center>

<%!
	 ModuleObject[] module = null;
	ModuleObject[] module_debit = null;    
	Reason[] reason=null;
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
%>




 <% 
     module = (ModuleObject[])request.getAttribute("Main_Module_code");
 	module_debit = 	(ModuleObject[])request.getAttribute("Debit_Module_code");
 	reason=(Reason[])request.getAttribute("Reason");
 %>
 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
 <html:form action="/Clearing/ChequeDeposition?pageId=7050" styleId="kk">
 <table> 
 			<tr><html:text property="error_message" styleId="err_mess_id" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
 			<tr>
 				<td rowspan="2">
									
				<table>
				<tr> 
				<td><bean:message key="label.controlno"></bean:message></td>
				<td><html:text property="ctrl_no" size="5"  styleClass="formTextFieldWithoutTransparent"  onkeypress="return onlynumbers()"  onblur="return setflag(2,this)" styleId = "ctrl_no"></html:text></td>
				</tr>	
				
				<tr>
					<td><bean:message key="label.InternalClg"></bean:message></td>
					<td><html:checkbox property="internal_clearing" styleId="int_clr" styleClass="formTextFieldWithoutTransparent" value="intlclg" onclick="return setcheckflag(4,this)"></html:checkbox>
						<bean:message key="label.OutstationChq"></bean:message><html:checkbox property="outstation_chq" styleClass="formTextFieldWithoutTransparent"></html:checkbox>
					</td>
				</tr>
				
				<tr>
				<td><bean:message key="label.microcde"></bean:message></td>
				<td><html:text property="city_code" readonly="true" styleClass="formTextFieldWithoutTransparent"  size="4" styleId="citycode"></html:text>-<html:text property="bank_code"  readonly="true" size="4" styleId="bank_code"></html:text>-<html:text property="branch_code" size="4"   readonly="true" styleId="branch_code"></html:text><html:text property="cityname"  styleClass="formTextField"  readonly="true" style="color:blue"></html:text>
					 <html:hidden property="micrcode" styleId="micr"></html:hidden>	
				</td>
				
				</tr>
				<tr>
				<td><bean:message key="label.bankName"></bean:message></td>
				<td><html:text property="bankname" styleClass="formTextField" readonly="true" style="color:blue"></html:text></td>
				</tr>
				<tr>
				<td><bean:message key="label.branch_name"></bean:message></td>
				<td>	
				<html:text property="branchname" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
				</td>
				</tr>
				
				
				<tr>
				<td>
					Multi Credit</td><td><html:checkbox property="multicredit" value="Yes" styleClass="formTextFieldWithoutTransparent" onclick="check_multi()"  styleId="chk_comp"></html:checkbox>
					Loan Credit<html:checkbox property="laon_credit" styleClass="formTextFieldWithoutTransparent" onclick="check_laon()" styleId="loan_cre" ></html:checkbox></td>
				</tr>
					
				<tr>
				<td><bean:message key="label.acctype"></bean:message></td>
				<td><html:select property="credit_account_type" styleClass="formTextFieldWithoutTransparent" styleId="credit_acc_type"  >
					<html:option value="select">select</html:option>
					<core:forEach var="module"  varStatus="count" items="${requestScope.Main_Module_code}" >
					<html:option value="${module.moduleCode}"><core:out value="${module.moduleAbbrv}"></core:out></html:option>
					</core:forEach>
					</html:select>
					<html:text property="credit_account_no" readonly="true" styleClass="formTextFieldWithoutTransparent"  size="6" styleId="credit_acc_no" ></html:text>
					<html:text property="credit_acc_type_name"  styleClass="formTextField"  styleId="cr_name"  style="color:blue" readonly="true"></html:text> 
				</td>
				</tr>
	
				<tr>
				<td><div id="loan_div1" style="display: none;">Loan Credit</div></td>		
		  		<td><div id="loan_div" style="display: none;">
					<html:select property="laon_credit_ac_type" styleClass="formTextFieldWithoutTransparent" >
					<html:option value="select">select</html:option>
					<core:forEach var="module"  varStatus="count" items="${requestScope.Loan_Module_code}" >
					<html:option value="${module.moduleCode}"><core:out value="${module.moduleAbbrv}"></core:out></html:option>
					</core:forEach>
					</html:select> 
					<html:text property="laon_credit_no" size="9"  styleClass="formTextFieldWithoutTransparent"  readonly="true"></html:text> 
					<html:text property="loan_acc_name" styleClass="formTextField" style="color:blue" readonly="true"></html:text>
					</div>
				</td>
				</tr>
	
				<tr>
				<td>
				<div  id="div_comp1" style="display: none;" >
				<bean:message key="lable.company_name"/>
				</div>
				</td>
				<td>
				<div  id="div_comp" style="display: none;" >
				<html:text property="company_name"   styleClass="formTextFieldWithoutTransparent"></html:text><html:text property="cname" readonly="true" style="color:red" styleClass="formTextField"></html:text>
				</div>
				</td>
				</tr>
	
				<tr>
				<td><bean:message key="label.amount"/></td>
				<td><html:text property="amount" styleClass="formTextFieldWithoutTransparent" readonly="true"  styleId="amt"></html:text></td>
				</tr>
	
	
				<tr>
				<td>
				<div id="div_pocomm" style="display:none">
				<bean:message key="label.pocomm"/>
				</div>		
				</td>
					
				<td>
				<div id="div_pocomm1" style="display:none">
				<html:text property="pocommision" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
				</div>		
				</td>
				</tr>
	
	
				<tr>
				<td>
				<bean:message key="label.paydetails"/>
				</td>
				<td>
				<html:text property="paydetail" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
				</td>
				</tr>
				
				<tr>
				<td><bean:message key="label.chqno"/></td>
				<td><html:text property="chequeno" readonly="true" styleClass="formTextFieldWithoutTransparent" styleId="chequeno"></html:text></td>
				</tr>
				
				<tr>
				<td><bean:message key="label.chqdate"/></td>
				<td><html:text property="chequedate" readonly="true" styleClass="formTextFieldWithoutTransparent"  styleId="chqdate"></html:text></td>
				</tr>
				
				
				<tr>
				<td><bean:message key="label.chqddpo"></bean:message></td>
				<td><html:select property="chqddpo" styleClass="formTextFieldWithoutTransparent" onblur="select_ChqPo()"  styleId="chddpo" >
					<html:option value="C">Cheque</html:option>
					<html:option value="D">DD</html:option>
					<html:option value="P">PO</html:option>
					</html:select>
				</td>
				</tr>
	
				<tr>
				<td><bean:message key="label.bounce"/></td>
					<td><html:checkbox property="bounce" styleClass="formTextFieldWithoutTransparent" styleId="bounce_id" onclick="callBounce()" ></html:checkbox>
						<html:text property="bounceFine" readonly="true" style="color:red" styleClass="formTextField"></html:text></td>
				</tr>
			
				<tr>
		
				<td><bean:message key="label.discount"/></td>
				<td><html:checkbox property="discount" styleClass="formTextFieldWithoutTransparent" styleId="chk_discount" onclick="check_discount()"></html:checkbox>
				</td>
				</tr>
				
				<tr>
				<td>
				<div id="div_discount1"  style="display: none;">
				<bean:message key="label.dis_amount"/>
				</div>		
				</td>
		
				<td>
				<div id="div_discount" style="display: none;">
				<html:text property="discountamount" readonly="true" styleClass="formTextFieldWithoutTransparent" size="5" styleId="dis_amt" onblur="return setflag(11,this)"  onkeypress="return onlyDoublenumbers()"></html:text>
				<html:text property="discountcharge" readonly="true" styleClass="formTextFieldWithoutTransparent" styleId="dis_chg"  size="5"></html:text>
				</div>		
				</td>
			</tr>
		<tr>
		<td>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            <html:button property="verify" styleClass="ButtonBackGroundImage" value="Verify" onclick="return setflag(8,this)"></html:button>
             <%}else{ %>
            <html:button property="verify" styleClass="ButtonBackGroundImage" disabled="true" value="Verify" onclick="return setflag(8,this)"></html:button>
             <%} %>

		
		<html:button property="clers" styleClass="ButtonBackGroundImage" value="Clear" onclick="clearAll()"></html:button>
		</td>
		</tr>
		<html:hidden property="form_flag" styleId="flagset"></html:hidden>
		<html:hidden property="reason_codes" styleId="reason"></html:hidden>
		<html:hidden property="error_flag" styleId="err_id"></html:hidden>
		
		<html:hidden property="today_date" styleId="today_dt"></html:hidden>
		<html:hidden property="booleanFlag"></html:hidden>
		
	</table>
	</td>
	
	<td>
		<table>
			<tr>
			<td>
			<div id="div_debit_pan" style="display: none;" >  
 			<table style="border:thin solid #000000;">
 			<tr>
 				<td><bean:message key="label.debit_ac_ty"></bean:message></td>
 				<td><html:select property="debit_account_type" styleClass="formTextFieldWithoutTransparent" styleId="debit_acc_type">
				<html:option value="select">select</html:option>
				<core:forEach var="debit_module"  varStatus="count1" items="${requestScope.Debit_Module_code}" >
				<html:option value="${debit_module.moduleCode}"><core:out value="${debit_module.moduleAbbrv}"></core:out></html:option>
				</core:forEach>
				</html:select> 
				</td>
				<td><bean:message key="label.debit_ac_no"></bean:message></td>
 				<td><html:text property="debit_account_no" styleClass="formTextFieldWithoutTransparent" size="7" styleId="debit_acc_num" readonly="true" ></html:text>
 				</td>
 			</tr>
 			<tr>
 				<td><bean:message key="label.balance"></bean:message></td>
 				<td><html:text property="balance" readonly="true" styleClass="formTextFieldWithoutTransparent" size="7"></html:text>
 				</td>
 				<td><bean:message key="label.sha_balance"></bean:message></td>
 				<td><html:text property="shodowbalance" readonly="true" styleClass="formTextFieldWithoutTransparent" size="7"></html:text>
 				</td>
 			</tr>
 			<tr>
 				<td><bean:message key="label.debit_chq_date"></bean:message></td>
 				<td><html:text property="debit_chq_date" readonly="true" styleClass="formTextFieldWithoutTransparent"  styleId="debit_che_date" size="7"></html:text>
 				</td>
 				<td><bean:message key="label.debit_chq_no" ></bean:message></td>
 				<td><html:text property="debit_chequeno" readonly="true" size="7" styleClass="formTextFieldWithoutTransparent" styleId="debit_chq_no" ></html:text>
 				</td>
 			</tr>
 			
 		</table>
 	</div>
	<div id = "po_div" style="display: none;">
		<table style="border:thin solid #000000;">
			<tr>
			<td><bean:message key="label.pay_order_no"/></td>   
				<td><html:text property="pay_order_no" styleClass="formTextFieldWithoutTransparent" size="5" onblur="return setflag(5,this)" ></html:text> 
			</td>
			 <td><bean:message key="label.pay_order_date" /></td>
				<td><html:text property="po_date" styleClass="formTextFieldWithoutTransparent" size="5"></html:text>
			</td> 
			</tr>
			<tr>	 
	       <td><bean:message key="label.amount"/></td>
			   <td><html:text property="pay_order_amt" styleClass="formTextFieldWithoutTransparent" size="5"></html:text>
		  </td> 	
			<td><bean:message key="label.favour"/></td>
			<td><html:text property="po_favour" styleClass="formTextFieldWithoutTransparent" size="5"></html:text>
			</td> 	
			</tr>
		</table>
		</div>
	<div id="bounce_fine" style="display:none;overflow:scroll;height:100px">
		<table border="1" style="border:thin solid #000000;">
		
				<tr><td>ResCode</td><td>Desptn</td><td>FAmt</td><td>Select</td></tr>
				
			<% 
			for(int i=0;i<reason.length;i++)
				{ %>
				
				<tr>
				<td><html:text property="txtReasonCode" size="5" value="<%=""+reason[i].getReasonCd()%>" ></html:text></td>
				<td><html:text property="txtDesription"  value="<%=""+reason[i].getReasonDesc()%>" ></html:text></td>	
				<td><html:text property="txtBounceFine" size="5" value="<%=""+reason[i].getBounceFine()%>" ></html:text></td>
				<td><input type="checkbox" name="chkBox"  value="<%=""+i%>"/></td>
				</tr>
				
			<%} %>	
		</table>
 	</div>
 	</td>
 	</tr>
 	</table>
 	</td>
				 			
 			
 
 </tr>
 			<tr>
 			<td>
 			<table>
 			<tr>
 			<td>
 			<%  String jspPath=(String)request.getAttribute("flag");
				 System.out.println("'m inside panel"+jspPath);
				 if(jspPath!=null)
				{
					System.out.println("wen 'm  null");
            %>
            <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
            
            <%}else{	%>
            <jsp:include page="/LockerWebPages/blank.jsp"></jsp:include>
          	<%} %>
 			</td>
 			</tr>
 		</table>
 		</td>
 	</tr>
 	
 </table> 
 
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
</body>
</html>