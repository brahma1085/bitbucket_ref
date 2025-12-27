<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>  
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loansOnDeposit.LoanPurposeObject"%>
<%@page import="masterObject.loans.LoanTransactionObject"%>
<html>   
<head> 
<link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
 
<script type="text/javascript">

function validate(target)
	{

	if(document.forms[0].txt_LoanActype.value=="select")
	{
		alert("<bean:message key="label.reftype"></bean:message>"); 
	    return false;
	} 
	if(document.forms[0].txt_refno.value=="0") 
	{ 
	    
	    alert("<bean:message key="label.reftype"></bean:message>");
		alert("Plz enter the reference num"); 
	    return false;
	}
	if(document.forms[0].txt_purpose.value=="SElect")
	{
		alert("Plz select Loan Purpose"); 
	    return false;
	}
	if(document.forms[0].txt_appsrlno.value=="0")
	{
		alert("Plz enter application Serial num"); 
	    return false; 
	}
	
	if(document.forms[0].txt_reqammount.value=="0.0")
	{
		alert("Plz enter the requried ammount"); 
	    return false;
	}
	else
	{  
	    document.forms[0].forward.value=target;
	    return true;
	}
  }	
 
  function LoanAcnum()
  {
  	
  		
  	if(document.getElementById("loan_acc_num").value!=0)
  	{
  		alert("Loan Account Number="+document.getElementById("loan_acc_num").value);
  		fun_clear();
  	}
  	if(document.getElementById("loan_acno_update").value!=0)
  	{
  		alert("Loan Account Number"+document.getElementById("loan_acno_update").value+"Updated Sucessfully");
  		fun_clear();
  	}
  	if( document.getElementById("loanid").value!=null)
  	{
  		 
  		if(document.getElementById("loanid").value=="notverified")
  		{
  	 			var e1=confirm("Loan account number not yet verified");
  	 			if(e1==true)
  	 			{
  					return true;
  				}
  				else
  				{
  					fun_clear();
  					return false;
  				}	 
  		}
  		else if(document.getElementById("loanid").value=="VerifiedAlready")
  		{
  		   alert("Loan Account Allready Verified");
  		   var ele=document.forms[0].elements;
  	 			for(var i=0;i<ele.length;i++)
  				{
  					if(ele[i].type=="text")
  					{
  						ele[i].value="";   
  					}
  				}   
  		
  		}
  		
  		 
  		  
  	}
  	if( document.getElementById("loanacc_closed").value!=null)
  	{
  		 
  		if(document.getElementById("loanacc_closed").value=="closed")
  		{
  	 			alert("Loan Account Closed");
  				
  		}
  	}
  	if( document.getElementById("loanacc_notfound").value!=null)
  	{
  		 
  		if(document.getElementById("loanacc_notfound").value=="NotFound")
  		{
  	 			alert("Loan account number not found");
  				
  		}
  	}   
  	if( document.getElementById("loan_acc_notver").value!=null)
  	{
  		if(document.getElementById("loan_acc_notver").value=="LoanAccNotVerified")
  		{
  	 			alert("Loan account is not yet verified");
  	 			var ele=document.forms[0].elements;
  	 			for(var i=0;i<ele.length;i++)
  				{
  					if(ele[i].type=="text")
  					{
  						ele[i].value="";   
  					}
  				}          
  		}
  	}
  	if( document.getElementById("additional_loan").value != false )
  	{
  		if(document.getElementById("additional_loan").value == "true" )
  		{   
  		    
  	 		var add_val=confirm("Do you want additional loan");
  	 		
  	 		if(add_val==true)
  	 		{
  	 		 document.getElementById("add_val").value =  "yes";
  	 		 document.getElementById("update2").value="Update_LoanDet";
  	 		  
  	 		 document.forms[0].submit();
  	 		}
  	 		else {
  	 		
  	 			document.getElementById("add_val").value =  "no";
  	 			document.getElementById("update2").value="UndoOperation";
  	 			
  	 			return false;
  	 		}
  				
  		}
  	}  
  	if(document.getElementById("Account_NotFound").value !=null)
  		{
  			if(document.getElementById("Account_NotFound").value=="AccountNotFound")
  			{
  				alert("Reference Deposit Type Not Found");
  				return false;
  			}
	  	
  		}
  		if(document.getElementById("matdate_elapsed").value !=null)
  		{
  			if(document.getElementById("matdate_elapsed").value=="MatedateElapsed")
  			{
  				alert("Maturity date is elapsed & you are unable to take loan");
  				var ele=document.forms[0].elements;
  				for(var i=0;i<ele.length;i++)
  				{
  					if(ele[i].type=="text")
  					{
  						ele[i].value="";   
  					}
  				}
  				return false;
  			
  			}
  		  
  		
  		}
  		if(document.getElementById("accountclosed").value!=null)
  		{
  			if(document.getElementById("accountclosed").value=="accountclosed")
  			{
  				alert("This account is already closed");
  				return false;
  			
  			}
  		}
  	
  	
  	
  	
  }
     
  
  function Verification(target)
  {
   
  	document.forms[0].forward.value=target;
  }
  
  function setFlag( falg_num,ids )
  {
  		document.getElementById("update2").value="UndoOperation";
  		document.getElementById("flags").value = falg_num ;
  		document.forms[0].submit();
  		
  }       
      
  function fun_clear()
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
  function Only_Numbers()
  {
  	var cha =   event.keyCode;
	
 	if ( (cha >= 48 && cha <= 57 ) ) 
 	{
		
   		return true;
   	} 
   	else 
   	{
		return false;
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
         		alert("only "+size+" digits allowed")
         		txt.value="";
         		return false;
          	}
         }
 
 function valid_Amount()
 {
 	
 	if(document.forms[0].txt_reqammount.value > document.forms[0].txt_ammeligable.value)
 	{
 		alert("Loanee can apply loan for less than or equal to"+document.forms[0].txt_ammeligable.value);
 		return false;
 	}
 	else
 	{
 		
 	 	document.forms[0].submit();
 	}
 };
 
 function fun_delete(target)
 {
 	
 	document.forms[0].forward.value=target;
 
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
<h2 class="h2"><center>Deposit Loan Application Entry</center></h2> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<title>Insert title here</title>
</head>

<body class="Mainbody" bgcolor="beige" onload="LoanAcnum()" >




 <%!ModuleObject[] DepositType,LoanACType,PayAcNum; %>
 <%DepositType=(ModuleObject[])request.getAttribute("DepositType"); %>
 <%LoanACType=(ModuleObject[])request.getAttribute("LoanACType"); %>
 <%PayAcNum=(ModuleObject[])request.getAttribute("PayAcNum"); %>
 <%System.out.println("DepositType in jsp page========"+DepositType);%>
 
 
 <%System.out.println("****************Hi from LoansApplication DE******************"); %>
 
 <%System.out.println("****************1***********************"); %>
 
 <%! LoanPurposeObject LoanPurpose[]; %>
 <% LoanPurpose=(LoanPurposeObject[])request.getAttribute("LoanPurpose"); %>
 <%System.out.println("LoanPurpose in jsp page========"+LoanPurpose);%>
<%LoanTransactionObject loan_tran_obj=(LoanTransactionObject)request.getAttribute("loantrn"); %> 
 
 <%System.out.println("***dsrfs*************2***********************"); %>
 <%String displaymsg = (String)request.getAttribute("displaymsg");%>
 
 
 <%String[] str,ver_paymode; %>  
 
 <%str=(String[])request.getAttribute("PayMode");%>
 
 <%ver_paymode=(String[])request.getAttribute("ver_paymode");%>
 
 <%String Description,Datefun,Combo_PayMode,Name_AccountHolder,Loan_ModuleCode,Loan_Type,LTDescription,LoanPurposecode,Aoto_loanrec; %>
 
 
 <%Description=(String)request.getAttribute("Description");%>
 <%Datefun=(String)request.getAttribute("Datefun"); %> 
 <%Loan_Type=(String)request.getAttribute("Loan_Type"); %>
 <%Loan_ModuleCode=(String)request.getAttribute("Loan_ModuleCode"); %>
 <%Combo_PayMode=(String)request.getAttribute("Combo_PayMode"); %>
 
 <%System.out.println("<============Combo_PayMode======>"+Combo_PayMode); %>
 
 <%LTDescription=(String)request.getAttribute("LTDescription"); %>
 
 <%LoanPurposecode=(String)request.getAttribute("LoanPurposecode"); %>
 
 <%Aoto_loanrec=(String)request.getAttribute("Aoto_loanrec"); %>
 
 
 <%System.out.println("****************3***********************"); %>
 
 <%Name_AccountHolder=(String)request.getAttribute("Name_AccountHolder"); %>
 
 <%System.out.println("Account holder name==="+Name_AccountHolder); %>
 
 <%boolean flag=false,flag2=false; %>
 <%if(Combo_PayMode!=null){%>
 <%if(Combo_PayMode.equalsIgnoreCase("Cash")){   
     flag=true; 
   } else{
    flag=false;   
 }%>
 <%}%>
 <%System.out.println("Description====="+Description); %>
 <%System.out.println("Datefun=====>"+Datefun); %>

<%System.out.println("****************4***********************"); %>

 <%! double DepositAmmount,
 CurrentIntRate,AmmountEligable,reqamounts,sanctionamt; %>
  
 
  
  <%System.out.println("****************5***********************"); %>
  
  <%CurrentIntRate=(Double)request.getAttribute("CurrentIntRate"); %>
  
  <%System.out.println("****************6***********************"+CurrentIntRate); %>
  
  <%AmmountEligable=(Double)request.getAttribute("AmmountEligable"); %>
  
  <%System.out.println("AmmountEligable****7***********************"+AmmountEligable); %>
  
  <%reqamounts=(Double)request.getAttribute("reqamounts"); %>
  <%System.out.println("***************reqamounts===>"+reqamounts); %>
      
  
  <%System.out.println("****************8***********************"); %>
      
  <%! String PageValue,action,Paymode,paymentaccno,button_update,paymentacctype,PayIndex; %>
 
  <%PayIndex=(String)request.getAttribute("PayIndex"); %>
  
  <%PageValue=(String)request.getAttribute("PageValue"); %>
  
  <%Paymode=(String)request.getAttribute("Paymode"); %>
  
  <%paymentaccno=(String)request.getAttribute("paymentaccno"); %>
  
  <%System.out.println("payment acc  num------------------------>"+paymentaccno); %>
  
  <%paymentacctype=(String)request.getAttribute("paymentacctype");%>
  
  <%System.out.println("payment acc  type------------------------>"+paymentacctype); %>
  
  <%button_update=(String)request.getAttribute("button_update"); %>
  <%System.out.println("button_update value==========>"+button_update); %>
  
  
  <%System.out.println("PageValue==========="+PageValue); %>
  
  <%! int applsrlnum,depaccnum; %>
  
  <%applsrlnum=(Integer)request.getAttribute("applsrlnum");%>
  
  <%System.out.println("***************applsrlnum===>"+applsrlnum); %>
  
  <%depaccnum=(Integer)request.getAttribute("depaccnum");%>
  <%System.out.println("***************depaccnum===>"+depaccnum); %>
  
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
  <%if(PageValue!=null){
  if( Integer.parseInt(PageValue.trim()) == 2 ){ 
 		 action="/LoansOnDeposit/ApplicationDE?pageId=6001&value=2";
  		 flag2=true;
  } 
  else if( Integer.parseInt(PageValue.trim()) == 1 ) {
 		 action="/LoansOnDeposit/ApplicationDE?pageId=6001&value=1";
  }
  }%>
  
  <%if(displaymsg!=null){ %>
  <font color="red" value="<%=""+displaymsg %>"></font>
  <%} %>
  
<html:form action="<%=action%>">
<%System.out.println("action======================"+action); %>
<table class="txtTable">
<td>

	<tr>
	   <td>
	   	  <div id="table_block" style="display: block">		
	
	   	  	


<table class="txtTable">

				 <tr> 
                   <td align="right">
                   		<bean:message key="label.combo_loan"></bean:message></td>
                   		<%if(Loan_Type!=null && Loan_ModuleCode!=null){%>
                   		<td>
                   			<html:select property="txt_LoanActype">
                   			<html:option value="<%=Loan_ModuleCode%>" disabled="<%=flag2%>"> <%=Loan_Type%></html:option>
                   			</html:select>
                   			<%if(LTDescription!=null)
                   			{%>
                   				<html:text property="txt_loandisplay" size="10" value="<%=LTDescription%>" disabled="<%=flag2%>" styleClass="formTextFieldWithoutTransparent" ></html:text>
                   			<%}%>
                   		</td>
                   		
                   		<%}else{%>
                   		<td><html:select property="txt_LoanActype" onchange="submit()">
                   		    
                   		<%if(LoanACType!=null){ %>
                   		<%for(int i=0;i<LoanACType.length;i++){ %>
                   		<html:option value="<%=""+i%>"><%=LoanACType[i].getModuleAbbrv()%></html:option> 
                   		<%}%>
                   		<%} %>
                   		</html:select> 
                   		<html:text property="txt_loandisplay" size="10" styleClass="formTextFieldWithoutTransparent"></html:text>
                   		</td>
                   	<%}%>
                   	
                </tr>
                <tr>
                   <td align="right">
                   		<bean:message key="label.loanaccno"></bean:message></td>
                        <td><html:text property="txt_loanaccno" size="10" onchange="submit()" onkeypress="return Only_Numbers()" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'10')"></html:text>
                   </td>
                </tr>

				<tr>
				   <td align="right">
				   		<bean:message key="label.reftype"></bean:message></td>
				       	<td><html:select property="txt_reftype" onchange="submit()" >
				       	    <%if(DepositType!=null)
				       	    { %>
				       	    	 
				       	    	<%for(int i=0;i<DepositType.length;i++){%>
				       	    	<html:option value="<%=""+i%>"><%=DepositType[i].getModuleAbbrv()%></html:option>
				       	    <%}%>
				       	    <%}%>  
				       	</html:select>
				       	<%if(Description!=null ){%>
				      	<html:text property="txt_refdisplay" size="15" value="<%=Description%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				       	<%}%>
				</tr>  
                <tr>
                    <td align="right">
                    	<bean:message key="label.refno"></bean:message></td>
                    	<%if(depaccnum!=0){ %>
                    	<td><html:text property="txt_refno" size="10" styleId="dep_no" onblur="return setFlag(1,this)"  onkeypress="return Only_Numbers()" disabled="<%=flag2%>" value="<%=""+depaccnum%>" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'10')"></html:text></td>
                    	<%}else{ %>
                    	<td><html:text property="txt_refno" size="10" onblur="return setFlag(1,this)" styleId="dep_no" onkeypress="return Only_Numbers()" disabled="<%=flag2%>" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'10')"></html:text></td>
                    	<%}%>
                    
                </tr>
                
                                   	
                	
               
                <tr>
                	<%if(Aoto_loanrec!=null){ %>
                	<td align="right">
                   		<bean:message key="label.AutoLoanRecovery"></bean:message></td>
                   		<td><html:select property="txt_AutoLoanRecovery" disabled="<%=flag2%>">
							<html:option value="<%=Aoto_loanrec%>"><%=Aoto_loanrec%></html:option></html:select></td>
                	<%}else{ %>	
                
                   <td align="right">
                   		<bean:message key="label.AutoLoanRecovery"></bean:message></td>
                   		<td><html:select property="txt_AutoLoanRecovery" disabled="<%=flag2%>">
							<html:option value="NONE">NONE</html:option>
                            <html:option value="Interest">Interest</html:option>
                            <html:option value="Total Amount">Total Amount</html:option>							                   		
                   		    </html:select>
                        </td>
                     <%} %>    
                </tr>
                <tr>
                     <%if(LoanPurposecode!=null){ %>
                      <td align="right">
                    		<bean:message key="label.purpose"></bean:message></td>
                    		<td><html:select property="txt_purpose" disabled="<%=flag2%>">
                    	    	<html:option value="<%=""+LoanPurposecode%>"><%=""+LoanPurposecode%></html:option> 
                    		</html:select></td>
                       <%}else { %>
                        <td align="right">
                    		<bean:message key="label.purpose"></bean:message></td>
                    		<td><html:select property="txt_purpose" disabled="<%=flag2%>">
                    	    	<html:option value="SElect">select</html:option> 
                    			<%if(LoanPurpose!=null)
                    			{%>
                    				<%for(int i=0;i<LoanPurpose.length;i++)
                    				{%>
                    				<html:option value="<%=LoanPurpose[i].getPurposeCode()+"   "+LoanPurpose[i].getPurposeDesc()%>"><%=LoanPurpose[i].getPurposeCode()+""+LoanPurpose[i].getPurposeDesc()%></html:option>
                    				<%}%>
                    			<%}%>
                    			</html:select> 
                       		</td>
                      <%} %>	
                      
                </tr>
                <tr>
                   <td align="right">
                   		<bean:message key="lable.appsrlno"></bean:message></td>
                   		<%if(applsrlnum!=0){ %>
                   		<td><html:text property="txt_appsrlno" size="10" disabled="<%=flag2%>" value="<%=""+applsrlnum%>" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'9')"></html:text></td>
                   		<%}else{ %>
                   		<td><html:text property="txt_appsrlno" size="10" disabled="<%=flag2%>"  onkeypress="return Only_Numbers()" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'9')"></html:text></td>
                   		<%} %>
                </tr>
                <tr>
                   <td align="right">
                   		<bean:message key="label.appndate"></bean:message></td>
                   		<%if(Datefun!=null){ %>
                   		<td><html:text property="txt_appdate" size="10" value="<%=Datefun%>"  styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
                         <%}else{ %>
                         <td><html:text property="txt_appdate" size="10" disabled="<%=flag2%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                         <%}%>
                </tr>
                <tr>
                   <td align="right">
                   		<bean:message key="lable.ammeli"></bean:message></td>
                   		<%if(AmmountEligable!=0){%>
                   		<td><html:text property="txt_ammeligable" size="10" value="<%=""+AmmountEligable%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
                   		<%}else{%>
                   		<td><html:text property="txt_ammeligable" size="10" disabled="<%=flag2%>"></html:text>
                   		<%} %>
                   </td>
                </tr>
                
                
                
                
                <tr>   
                    <td align="right">
                    	<bean:message key="label.reqamount"></bean:message></td>
                    	<%if(reqamounts!=0){%>
                    	<td><html:text property="txt_reqammount" size="10"  value="<%=""+reqamounts%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                    	<%}else { %>
                    	<td><html:text property="txt_reqammount" size="10" onblur="return valid_Amount()" disabled="<%=flag2%>" styleClass="formTextFieldWithoutTransparent"></html:text>
                    	<%}%>
                    </td>
                </tr> 
                
                
               
                
                                  
                <tr>  
                   <td align="right">
                   		<bean:message key="lable.intrate"></bean:message></td>
                   		<%if(CurrentIntRate!=0){ %>
                   		<td><html:text property="txt_intrate" size="10" value="<%=""+CurrentIntRate%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
                   		<%}else{ %>
                   		<td><html:text property="txt_intrate" size="10" disabled="<%=flag2%>"></html:text>
                   		<%} %>
                   </td>
                </tr>
                <tr>
                   <%if(Paymode!=null){ %>	
                   <td align="right">
                   		<bean:message key="label.paymtmode"></bean:message></td>
                   		<td><html:select property="txt_paymode" disabled="<%=flag2%>">
                   		   	<html:option value="<%=""+Paymode%>"></html:option>
                   			</html:select></td>
                   <%}else { %>
                   <td align="right">
                   		<bean:message key="label.paymtmode"></bean:message></td>
                   		<td><html:select property="txt_paymode" onchange="submit()" disabled="<%=flag2%>">
 							<%if(str!=null){%>
 							<%for(int i=0;i<str.length;i++){%>
 							<html:option value="<%=str[i]%>"><%=str[i]%></html:option>  
 							<%} %>
 							<%} %>                  		
                   		    </html:select>
                   	    </td>
                   	  <%} %>  
                </tr>
                <tr> 
                	<%if(paymentaccno!=null && paymentacctype!=null && PayIndex!=null ){ %>
                	<%System.out.println("**************<====>Testing 1<====>****************"); %>
                	 <td align="right">
                   		<bean:message key="lable.payacno"></bean:message></td>
                   		<td><html:select property="txt_payactype" disabled="<%=flag%>" onchange="submit()" disabled="<%=flag%>">
                   		<%if(PayAcNum!=null){ %>
                   		<%for(int i=0;i<PayAcNum.length;i++){ %>
                   		<%if(PayAcNum[i].getModuleAbbrv().equalsIgnoreCase(paymentacctype)){ %>
                   		<html:option value="<%=""+PayAcNum[i].getModuleCode()%>"><%=""+PayAcNum[i].getModuleAbbrv()%></html:option>
                   		<%} %>
                   		<%} %>
                   		<%} %>
                        </html:select>
                        
                        <html:text property="txt_payacno" size="10" disabled="<%=flag%>" value="<%=""+paymentaccno %>" onchange="submit()" disabled="<%=flag%>"></html:text>
                        </td> 
                	<%}else { %>
                	<%System.out.println("**************<====>Testing 1<====>****************"); %>
                   <td align="right">
                   		<bean:message key="lable.payacno"></bean:message></td>
                   		<%System.out.println("flag value inside td=="+flag);%>
                   		<td><html:select property="txt_payactype" disabled="<%=flag%>" onchange="submit()" disabled="<%=flag%>">
                   		    
                   		     <%if(PayAcNum!=null){%> 
                   		     <%for(int i=0;i<PayAcNum.length;i++){%> 
                   		      <html:option value="<%=""+PayAcNum[i].getModuleCode()%>"><%=PayAcNum[i].getModuleAbbrv()%></html:option>
                   		     <%}%> 
                   		     <%}%>
                   			</html:select>
                  		<html:text property="txt_payacno" size="10" disabled="<%=flag%>" onchange="submit()" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'10')"></html:text>
                  		
                   </td>
                   <%} %>
                </tr>
                 
                <tr>
                   <td align="right"><bean:message key="label.custname"></bean:message></td>
                   <%if(Name_AccountHolder!=null){ %>
                   <td><html:text property="txt_acname" size="20" value="<%=""+Name_AccountHolder%>" styleClass="formTextField" disabled="<%=flag%>" readonly="true"></html:text></td>
                   <%}else{%>
                	   <td><html:text property="txt_acname" disabled="<%=flag%>" value="<%=""+Name_AccountHolder%>" size="10" disabled="<%=flag%>"></html:text></td>
                   <%}%>
               </tr>
     			           
                <tr>
                    <html:hidden property="forward" value="error"></html:hidden>
                    <html:hidden property="button_value" value="error"></html:hidden>
                    
                    <%if(PageValue!=null && button_update!=null )
                    { 
	                	if(Integer.parseInt(PageValue.trim())==2)
	                	{
	               			if(button_update.equalsIgnoreCase("Verify"))
	               			{%>
     							<td align="right"><html:submit  value="Verify" onclick="return Verification('verify')" styleClass="ButtonBackgroundImage"><bean:message key="label.verify" ></bean:message></html:submit></td>
     							<td><html:button property="clear" value="Clear" onclick="fun_clear()" styleClass="ButtonBackgroundImage"></html:button></td>
     							
     						<%}else if(button_update.equalsIgnoreCase("SUBMIT"))
     						{%>
                    			<td align="right"><html:submit  value="submit" onclick="return validate('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message></html:submit></td>
  								<td><html:button property="clear" value="Clear" onclick="fun_clear()" styleClass="ButtonBackgroundImage"></html:button></td>
						    <%}%>
						     <%}else if(Integer.parseInt(PageValue.trim())==1)
								{
  						 		if(button_update.equalsIgnoreCase("UPDATE"))
  						 		{%>
  						 			<td align="right"><html:submit value="UPDATE" onclick="validate('update')" styleClass="ButtonBackgroundImage"></html:submit></td>
  						 			<td><html:button property="clear" value="Clear" onclick="fun_clear()" styleClass="ButtonBackgroundImage"></html:button>
  						 			<html:submit value="Delete" onclick="fun_delete('DELETE')" styleClass="ButtonBackgroundImage"></html:submit></td>
  						 			
  						 		<%}else{%>
  						 			
  									<td align="right"><html:submit  value="submit" onclick="return validate('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit" ></bean:message></html:submit></td>
  									<td><html:button property="clear" value="Clear" onclick="fun_clear()" styleClass="ButtonBackgroundImage"></html:button></td>
  							<%}%>
  						<%}%>
  					    <%}else 
  					    {%>
  					    	<td align="right"><html:submit  value="submit" onclick="return validate('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message></html:submit></td>
  							<td><html:button property="clear" value="Clear" onclick="fun_clear()" styleClass="ButtonBackgroundImage"></html:button></td>
  					    <%}%>          
	     			</tr>
	     			
	     			
	     			
	     			
	     				
	     				<html:hidden property="loan_acno_update" styleId="loan_acno_update"></html:hidden>
      			        <html:hidden property="defaultTab" value="AccountInfo"></html:hidden>
      					<html:hidden property="defaultTab" value="Personal"></html:hidden> 
      			        <html:hidden property="defaultTab" value="DepositDetail"></html:hidden>
      					<html:hidden property="loanacnum" styleId="loanid"></html:hidden>
      					<html:hidden property="loanacc_closed" styleId="loanacc_closed"></html:hidden>
      					<html:hidden property="loanacc_notfound" styleId="loanacc_notfound"></html:hidden>
                		<html:hidden property="loan_acc_notver" styleId="loan_acc_notver"></html:hidden>
                		<html:hidden property="additional_loan" styleId="additional_loan"></html:hidden>
                		<html:hidden property="add_val" styleId="add_val"></html:hidden>
                		<html:hidden property="flag_num" styleId="flags"></html:hidden>
                		<html:hidden property="update_value" styleId="update2"></html:hidden>
                		<html:hidden property="acc_notfound" styleId="Account_NotFound"></html:hidden>
                		<html:hidden property="matdate_elapsed" styleId="matdate_elapsed"></html:hidden>
                		<html:hidden property="accountclosed" styleId="accountclosed"></html:hidden>
                		<html:hidden property="loan_acc_num" styleId="loan_acc_num"></html:hidden>
                		<html:hidden property="notverified" value="notverified" styleId="notverified"></html:hidden>
                		
                		
                		
                		
                		
                		
</table>
</div>
</td>

            
            		 	

			<td>
				<div id="table_block2" style="display: block">
				<% String jspPath=(String)request.getAttribute("flag");
    	  			System.out.println("jspPath=="+jspPath);
         		%>
   				<%if(jspPath!=null){ %>
   				<jsp:include page="<%=jspPath%>"></jsp:include>
				<%}%>
				</div>	
    		</td>
    		
    		</tr>
			
						
		
			
			 	
</table>
				 
				 
				 
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>

</html>