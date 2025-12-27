<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="java.util.Map"%>    
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>  
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loansOnDeposit.LoanPurposeObject"%>
<html> 
<head> 
<script type="text/javascript">
  
	function validate(target)
	{  
		alert(document.forms[0].txt_LoanActype.value);
		
		if(document.forms[0].txt_LoanActype.value=="select")
		{
			alert("Select the Loan Account type");
			document.forms[0].txt_LoanActype.focus();
			return false;   
		}
		else if(document.forms[0].txt_loanaccno.value==0)   
		{
			alert("Enter the Loan Account Number");
			document.forms[0].txt_loanaccno.focus();
			return false;
		}
		else
		{
			alert(document.forms[0].forward.value=target);
	    	document.forms[0].forward.value=target;
	    	return true;
	    }
    }	
      
    function clear_function()    
    {
    	alert("Inside clear");
    	var elements=document.forms[0].elements;
    	for(var i=0;i<elements.length;i++)
    	{   
    		if(elements[i].type=="text")
    		{ 
    			elements[i].value="";  
    		}
    	}
    	
    }
    
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
    
    
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>
</head>    

<body bgcolor="beige">

 <%!ModuleObject[] DepositType,LoanACType,PayAcNum; %>
 <%DepositType=(ModuleObject[])request.getAttribute("DepositType"); %>
 <%LoanACType=(ModuleObject[])request.getAttribute("LoanACType"); %>
 <%PayAcNum=(ModuleObject[])request.getAttribute("PayAcNum"); %>
 <%System.out.println("Pay Account Num----->"+PayAcNum); %>
 <%! LoanPurposeObject LoanPurpose[]; %>
 <% LoanPurpose=(LoanPurposeObject[])request.getAttribute("LoanPurpose"); %>
 <%String[] str,ver_paymode; %>  
 <%str=(String[])request.getAttribute("PayMode");%>
 <%ver_paymode=(String[])request.getAttribute("ver_paymode");%>
 <%String Description,Datefun,Combo_PayMode,Name_AccountHolder,Loan_ModuleCode,Loan_Type,LTDescription,LoanPurposecode,Aoto_loanrec; %>
 <%Description=(String)request.getAttribute("Description");%>
 <%Datefun=(String)request.getAttribute("Datefun"); %> 
 <%Loan_Type=(String)request.getAttribute("Loan_Type"); %>
 <%Loan_ModuleCode=(String)request.getAttribute("Loan_ModuleCode"); %>
 <%Combo_PayMode=(String)request.getAttribute("Combo_PayMode"); %>
 <%LTDescription=(String)request.getAttribute("LTDescription"); %>
 <%LoanPurposecode=(String)request.getAttribute("LoanPurposecode"); %>
 <%Aoto_loanrec=(String)request.getAttribute("Aoto_loanrec"); %>
 <%Name_AccountHolder=(String)request.getAttribute("Name_AccountHolder"); %>
 <%boolean flag=false,flag2=false; %>
 <%if(Combo_PayMode!=null){%>
 <%if(Combo_PayMode.equalsIgnoreCase("Cash")){   
     flag=true; 
   } else{
    flag=false;   
 }%>
 <%}%>
 <%! double DepositAmmount,
 CurrentIntRate,AmmountEligable,reqamounts,sanctionamt; %>
  <%CurrentIntRate=(Double)request.getAttribute("CurrentIntRate"); %>
  <%AmmountEligable=(Double)request.getAttribute("AmmountEligable"); %>
  <%System.out.println("Amount Eligible-==-=--=-->"+AmmountEligable); %>
  <%reqamounts=(Double)request.getAttribute("reqamounts"); %>
  <%System.out.println("***************reqamounts===>"+reqamounts); %>
  <%! String PageValue,action,Paymode,paymentaccno,button_update,dep_type_abbr,index,paymentacctype; %>
  <%index=(String)request.getAttribute("index"); %>
  <%dep_type_abbr=(String)request.getAttribute("dep_type_abbr"); %>
  <%PageValue=(String)request.getAttribute("PageValue"); %>
  <%Paymode=(String)request.getAttribute("Paymode"); %>
  <%paymentaccno=(String)request.getAttribute("paymentaccno"); %>
  <%System.out.println("Payment Account Number=========>"+paymentaccno); %>
  <%paymentacctype=(String)request.getAttribute("paymentacctype");%>
  
  <%System.out.println("payment acc  type------------------------>"+paymentacctype); %>
  <%button_update=(String)request.getAttribute("button_update"); %>
  <%! int applsrlnum,depaccnum; %>
  <%applsrlnum=(Integer)request.getAttribute("applsrlnum");%>
  <%depaccnum=(Integer)request.getAttribute("depaccnum");%>
<h1 align="center"><font  size="4" style="font-family: serif;color: maroon">Additional Loan Verification</font></h1>
 
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
<html:form action="/AddLoanVerification?pageId=6014">

<table>
<td>
<table class="txtTable">
                <tr> 
                   <td align="right">
                   		<bean:message key="label.combo_loan"></bean:message></td>
                   		<%if(Loan_Type!=null && Loan_ModuleCode!=null){%>
                   		<td>
                   			<html:select property="txt_LoanActype">
                   			<html:option value="<%=Loan_ModuleCode%>"> <%=Loan_Type%></html:option>
                   			</html:select>
                   			<%if(LTDescription!=null)
                   			{%>
                   				<html:text property="txt_loandisplay" size="10" value="<%=LTDescription%>" disabled="<%=flag2%>" styleClass="formTextFieldWithoutTransparent" ></html:text>
                   			<%}%>
                   		</td>
                   		<%}else{%>
                   		<td><html:select property="txt_LoanActype">
                   		    
                   		<%if(LoanACType!=null){ %>
                   		<%for(int i=0;i<LoanACType.length;i++){ %>
                   		<html:option value="<%=""+LoanACType[i].getModuleCode()%>" styleClass="formTextFieldWithoutTransparent"><%=LoanACType[i].getModuleAbbrv()%></html:option> 
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
                        <td><html:text property="txt_loanaccno" size="10" onchange="submit()" onkeypress="return Only_Numbers()" onkeyup="numberlimit(this,'10')" styleClass="formTextFieldWithoutTransparent"></html:text>
                   </td>
                </tr>
                
                <%System.out.println("index========>"+index); %>
                <%System.out.println("dep_type_abbr========>"+dep_type_abbr); %>
                <tr>
                  <%if(index!=null && dep_type_abbr!=null)
                  { %>
                  	<td align="right">
				   		<bean:message key="label.reftype"></bean:message></td>
				       	<td><html:select property="txt_reftype" onchange="submit()" styleClass="formTextFieldblack">
				       	    <html:option value="<%=""+index%>"><%=dep_type_abbr%></html:option>
				       	</html:select></td>
				    
                
                 <%}else { %>
                
				   <td align="right">
				   		<bean:message key="label.reftype"></bean:message></td>
				       	<td><html:select property="txt_reftype" onchange="submit()" styleClass="formTextFieldblack">
				       	    <%if(DepositType!=null)
				       	    { %>
				       	    	 
				       	    	<%for(int i=0;i<DepositType.length;i++){%>
				       	    	<html:option value="<%=""+i%>"><%=DepositType[i].getModuleAbbrv()%></html:option>
				       	    <%}%>
				       	    <%}%>  
				       	</html:select>
				       	<%if(Description!=null ){%>
				      	<html:text property="txt_refdisplay" size="15" value="<%=Description%>" styleClass="formTextFieldblack"></html:text></td>
				       	<%}%>
				       	
				    <%} %>   	
				       	
				</tr>
                
                
                
                
                 <tr>
                    <td align="right">
                    	<bean:message key="label.refno"></bean:message></td>
                    	<%if(depaccnum!=0){ %>
                    	<td><html:text property="txt_refno" size="10" styleId="dep_no" onblur="submit()"  value="<%=""+depaccnum%>" styleClass="formTextFieldblack" onkeypress="return Only_Numbers()"></html:text></td>
                    	<%}else{ %>
                    	<td><html:text property="txt_refno" size="10" onchange="submit()" styleId="dep_no" onblur="submit()"  styleClass="formTextFieldblack" onkeypress="return Only_Numbers()"></html:text></td>
                    	<%} %>
                    
                </tr>
                
                
                
                <tr>
                	<%if(Aoto_loanrec!=null){ %>
                	<td align="right">
                   		<bean:message key="label.AutoLoanRecovery"></bean:message></td>
                   		<td><html:select property="txt_AutoLoanRecovery" styleClass="formTextFieldWithoutTransparent" >
							<html:option value="<%=Aoto_loanrec%>"><%=Aoto_loanrec%></html:option></html:select></td>
                	<%}else{ %>	
                
                   <td align="right">
                   		<bean:message key="label.AutoLoanRecovery"></bean:message></td>
                   		<td><html:select property="txt_AutoLoanRecovery" styleClass="formTextFieldWithoutTransparent">
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
                    		<td><html:select property="txt_purpose" styleClass="formTextFieldWithoutTransparent" >
                    	    	<html:option value="<%=""+LoanPurposecode%>"><%=""+LoanPurposecode%></html:option> 
                    		</html:select></td>
                       <%}else { %>
                        <td align="right">
                    		<bean:message key="label.purpose"></bean:message></td>
                    		<td><html:select property="txt_purpose" styleClass="formTextFieldWithoutTransparent">
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
                   		<td><html:text property="txt_appsrlno" size="10" styleClass="formTextFieldblack" value="<%=""+applsrlnum%>" readonly="true"></html:text></td>
                   		<%}else{ %>
                   		<td><html:text property="txt_appsrlno" size="10" styleClass="formTextFieldblack"></html:text></td>
                   		<%} %>
                </tr>
                <tr>
                   <td align="right">
                   		<bean:message key="label.appndate"></bean:message></td>
                   		<%if(Datefun!=null){ %>
                   		<td><html:text property="txt_appdate" size="10"  value="<%=Datefun%>" styleClass="formTextFieldblack" readonly="true"></html:text></td>
                         <%}else{ %>
                         <td><html:text property="txt_appdate" size="10" styleClass="formTextFieldblack"></html:text></td>
                         <%}%>
                </tr>
                <tr>
                   <td align="right">
                   		<bean:message key="lable.ammeli"></bean:message></td>
                   		<%if(AmmountEligable!=0){%>
                   		<td><html:text property="txt_ammeligable" size="10" value="<%=""+AmmountEligable%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
                   		<%}else{%>
                   		<td><html:text property="txt_ammeligable" size="10" style="border:transparent;background-color:beige;color:blue"></html:text>
                   		<%} %>
                   </td>
                </tr>
                <tr>
                    <td align="right">
                    	<bean:message key="label.reqamount"></bean:message></td>
                    	<%if(reqamounts!=0){%>
                    	<td><html:text property="txt_reqammount" size="10"  value="<%=""+reqamounts%>"  style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
                    	<%}else { %>
                    	<td><html:text property="txt_reqammount" size="10" style="border:transparent;background-color:beige;color:blue"></html:text>
                    	<%}%>
                    </td>
                </tr>
                <tr>
                   <td align="right">
                   		<bean:message key="lable.intrate"></bean:message></td>
                   		<%if(CurrentIntRate!=0){ %>
                   		<td><html:text property="txt_intrate" size="10" value="<%=""+CurrentIntRate%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
                   		<%}else{ %>
                   		<td><html:text property="txt_intrate" size="10" style="border:transparent;background-color:beige;color:blue"></html:text>
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
                	<%if(paymentaccno!=null && paymentacctype!=null && index!=null ){ %>
                	<%System.out.println("**************<====>Testing 1<====>****************"); %>
                	 <td align="right">
                   		<bean:message key="lable.payacno"></bean:message></td>
                   		<td><html:select property="txt_payactype" disabled="<%=flag%>" onchange="submit()" disabled="<%=flag%>">
                   		<html:option value="<%=""+index%>"><%=paymentacctype%></html:option>
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
                   			<%if(paymentaccno!=null) {%>
                  		<html:text property="txt_payacno" size="10" disabled="<%=flag%>"  value="<%=paymentaccno %>" onchange="submit()" disabled="<%=flag%>" styleClass="formTextFieldWithoutTransparent"></html:text>
                  		<%} %>
                   </td>
                   <%} %>
                </tr>
                <tr>
                   <td align="right"><bean:message key="label.custname"></bean:message></td>
                   <%if(Name_AccountHolder!=null){ %>
                   <td><html:text property="txt_acname" size="20" value="<%=""+Name_AccountHolder%>" styleClass="formTextFieldblack" readonly="true"></html:text></td>
                   <%}else{%>
                	   <td><html:text property="txt_acname"  size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
                   <%}%>
               </tr>
                       
                <tr>
                    <html:hidden property="forward" value="error"></html:hidden>
                    <td align="right">
                    	<html:submit  value="Verify" onclick="return validate('verify')" styleClass="ButtonBackgroundImage">
                    		<bean:message key="label.submit"></bean:message>
                    	</html:submit>
                    </td>
  					<td>
  						<html:button property="clear" value="clear" onclick="clear_function()" styleClass="ButtonBackgroundImage"></html:button>
  					</td>
					 
			   </tr>
      			   <html:hidden property="defaultTab" value="AccountInfo"></html:hidden>
      				<html:hidden property="defaultTab" value="Personal"></html:hidden> 
      			    <html:hidden property="defaultTab" value="DepositDetail"></html:hidden>
      					     
                		
</table></td>                
				<td>
				<% String jspPath=(String)request.getAttribute("flag");
    	  			System.out.println("jspPath=="+jspPath);
         		%>
   				<%if(jspPath!=null){ %>
   				<jsp:include page="<%=jspPath%>"></jsp:include>
				<%}%>	
				</td> </table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>




