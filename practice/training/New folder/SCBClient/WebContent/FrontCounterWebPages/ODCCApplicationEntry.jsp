
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.general.SignatureInstructionObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page import="masterObject.loansOnDeposit.LoanPurposeObject" %>
<%@page import="java.util.Map"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Dec 8, 2008
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<%@page import="com.scb.fc.forms.AccountCloseActionForm"%>
<%@page import="masterObject.frontCounter.ODCCMasterObject"%>
<html>
  <head><title>AccountOpening</title>
       <font color="blue" ><center>
<h2 class="h2">ODCC Application Data Entry</h2></center></font>
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
    <script>
    function created_already()
    {
    	alert("!WARNING\nalready account number generated for this details");
    	clearall();
    }
    
    function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only "+size+"digits allowed")
         		txt.value="";
         		return false;
          	}
         }
    
    
    function setme(){
    var s=document.forms[0].srlnonew.value;
    
    
    }
    
    
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
    
    
    
    function show(){
    
    alert(document.forms[0].scrollno.value);
    }
    
    
    function setval(val){
    
    if(confirm("Are you sure you want to go ahead with the operation ")){
    document.forms[0].hidval.value=val;
    
    document.forms[0].submit();
    }
    else{
    }
    }
    function setDEval(val){
    if(confirm("Are you sure you want to delete the account")){
    document.forms[0].hidval.value='create';
    document.forms[0].del.value=val;
    
    document.forms[0].submit();
    }
    else{
    }
    
    
    }
    function clearall(){
    
    var all=document.forms[0].elements;
    for(var k=0;k<all.length;k++){
    all[k],value="";
    
    }
    
    }
    
    function reset123(){
  
    var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="text")
    {
    ele[i].value="";
    show(false,'div1');
    }
    else if(ele[i].type=="checkbox"){
    ele[i].checked=false;
    }
    
    }
    document.forms[0].ac_no.value='0';
   	document.forms[0].businessAddr.value="";
   	    
    }
    
    function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) ) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
	
	function only_for_address()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >= 47 && cha <=57)||cha==32||cha==45||cha==35) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
    
    function firstone(){
    document.forms[0].first.value='First';
    document.forms[0].submit();
    }
    
    function checkit(){
    document.forms[0].cobortext.disabled=false;
    
    }
    function countchecks(){
   var a=0;
   if(document.forms[0].chk1.checked){
      a=a+1;
      }
    if(document.forms[0].chk2.checked){
      a=a+1;
      }
    if(document.forms[0].chk3.checked){
      a=a+1;
		}      
       
       
       document.forms[0].countchk.value=a;
    
    
    }
   
    
    
    var ns4 = (document.layers) ? true : false;
var ie4 = (document.all && !document.getElementById) ? true : false;
var ie5 = (document.all && document.getElementById) ? true : false;
var ns6 = (!document.all && document.getElementById) ? true : false;

function show(sw,obj) {
  // show/hide the divisions
  if (sw && (ie4 || ie5) ) document.all[obj].style.visibility = 'visible';
  if (!sw && (ie4 || ie5) ) document.all[obj].style.visibility = 'hidden';
  if (sw && ns4) document.layers[obj].visibility = 'visible';
  if (!sw && ns4) document.layers[obj].visibility = 'hidden';
}
    
    </script>
    
  </head>
  <body class="Mainbody">
    <%!
        ModuleObject[] array_module;
        String details[];
        AccountMasterObject acountMaster;
        AccountMasterObject[] acMasterObject;
        String jspPath;
        ModuleObject[] mod;
        CustomerMasterObject cmObject;
         AccountCloseActionForm acForm;
         Map user_role;
         String access;
         LoanPurposeObject[] loanpurposeobject;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    acMasterObject=(AccountMasterObject[])request.getAttribute("acMasterObject");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	String verified=(String)request.getAttribute("verified");
    	String newac=(String)request.getAttribute("newac");
    	String jspPath=(String)request.getAttribute("flag");
    	String[] dupchk=(String[])request.getAttribute("dupchk");
    	String coborrower=(String)request.getAttribute("coborrower");
    	loanpurposeobject=(LoanPurposeObject[])request.getAttribute("purpose");
    	request.setAttribute("purpose",loanpurposeobject);
    	AccountObject master=(AccountObject)request.getAttribute("master");
    	session.setAttribute("master",master);
    	if(master!=null){
    	System.out.println("master in odccApplication====1===="+master.getScrollno());
    	System.out.println("master in odccApplication====1===="+master.getAccname());
    	System.out.println("master in odccApplication====1===="+master.getAcOpenDate());
    	System.out.println("master in odccApplication====1===="+master.getAmount());
    	String[] arr={String.valueOf(master.getScrollno()),master.getAcOpenDate(),master.getAccname(),String.valueOf(master.getAmount())};
    	}
    	ModuleObject[] mod=(ModuleObject[])request.getAttribute("mod");
    	session.setAttribute("modObj",mod);
    	ODCCMasterObject odccmaster=(ODCCMasterObject)request.getAttribute("odccmaster");
    	session.setAttribute("odccmaster",odccmaster);
    	
    	CustomerMasterObject cust=(CustomerMasterObject)request.getAttribute("cust");
    	session.setAttribute("cust",cust);
    	SignatureInstructionObject[] signObject=(SignatureInstructionObject[])request.getAttribute("signature");
    	request.setAttribute("sigObject",signObject);
    	String odaccount=(String)request.getAttribute("odaccount");
    	session.setAttribute("data",(String)request.getAttribute("data"));
    	String getreceipt=(String)request.getAttribute("getreceipt");
    	String accesspageId=(String)request.getAttribute("accesspageId");
    	user_role=(Map)request.getAttribute("user_role");
    	if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}
    	//request.setAttribute("master",master);
  // String[] arr={String.valueOf(master.getScrollno()),master.getAcOpenDate(),master.getAccname(),String.valueOf(master.getAmount())};
    	//ODCCMasterObject odccDetails=(ODCCMasterObject)request.getAttribute("odccDetails");
    %><!--
 
    
    -->
     <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
    <div id = "div1" class = "myLayersClass">
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=closingmsg%></font>
    </core:if>
    </div>
   <%if(master!=null){
	   System.out.println("master has value in odccApplication"+master);
	   
   } %>
     <html:form action="/FrontCounter/OdccApplication?pageId=3022" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="hidval" />
     	<html:hidden property="del" />
     	<html:hidden property="first" />
     	<html:hidden property="countchk" />
     	<div id="tabs">
     	</div>
     	<br><br>
          <table><tr><td>
<table border="1" width="386" height="76" style="border:thin solid navy" class="txtTable">
	<tr>
		<td height="33" width="145">Account Type:</td>
			<td height="33" width="252"><html:select property="acType" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select></td>
	</tr>
		<tr>
		<td height="35" width="145">Account No:</td>
			<td height="35" width="252">
			<html:text property="ac_no" size="10" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onblur="firstone()" onkeyup="return numberlimit(this,'10')"></html:text><core:if test="<%=newac!=null %>"><font color="navy"><%=newac%></font></core:if> </td>
	</tr> 
	</table>
	<br><br>
		<table style="border:thin solid navy;" class="txtTable">
	<tr>
	         
		<td width="157"> Share Type : </td>
		<td width="227">
		<html:select property="combo_share_type" styleClass="formTextFieldWithTransparent">
                        
                        <core:forEach var="sh" varStatus="count" items="${requestScope.sh}" >
                        	    <html:option value="${sh.moduleCode}" ><core:out value="${sh.moduleAbbrv}-${sh.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
		</td>
	</tr>
	<tr>
	         
		<td width="157"> Share No: </td>
		<td width="227">
		<html:text property="txt_sh_type" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onblur="submit()" onkeyup="return numberlimit(this,'10')"></html:text>
		</td>
	</tr>
	<tr>
		<td width="157"> Name : </td>
		<td width="227">
		<html:text property="name" size="30" readonly="true"></html:text>
		</td>
	</tr>
	<tr>
		<td  width="157"> Details : </td>
		<td  width="227">
		<html:select property="details" onblur="submit()">
		<html:option value="SELECT">SELECT</html:option>
		<html:option value="PersonalDetails">PersonalDetails</html:option>
		<html:option value="Loan&ShareDetails">Loan&ShareDetails</html:option>
		<html:option value="ReceiptDetails">ReceiptDetails</html:option>
		<html:option value="Application">Application</html:option>
		
		
		<html:option value="SignatureInst">SignatureInst</html:option>
		
		<html:option value="EmploymentDetails">EmploymentDetails</html:option>
		
		<html:option value="DepositDetails">DepositDetails</html:option>
		
		
		</html:select>
		</td>
	</tr>
	<core:if test="${getreceipt!=null}">
   	                 <!--<tr><td>Cheque Book Issued:</td>
   	                 <td><html:select property="chqbook">
   	                 <html:option value="T">Yes</html:option>
   	                 <html:option value="F">No</html:option>
   	                 
   	                 
   	                 </html:select></td>
            	</tr>
            	
   	                  -->
   	                  
   	                  <tr>
   	                
   	                <td>Type of Operation</td>
   	                <td><html:select property="typeofoperation">
   	                <html:option value="Single">Single</html:option>
   	                <html:option value="Joint">Joint</html:option>
   	                
   	                </html:select> </td>
   	                
   	                </tr>
   	                  <tr><td style="border:thin solid navy;">Receipt Details</td>
            	</tr>
            	
       	
 <tr id="cash1">
        	  <td><bean:message key="label.scroll_no"></bean:message></td>
			 <td><html:text property="scrollNum" styleClass="formTextFieldWithoutTransparent" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onblur="submit()" onkeyup="return numberlimit(this,'10')"></html:text>	</td> 
 </tr> 
 <tr id="cash2">
    		<td>  Name:</td>
    		 <td><html:text property="rcptname" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>    </td>
  </tr>
 <tr id="cash2">
    		<td>  <bean:message key="label.date"></bean:message></td>
    		 <td><html:text property="date" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>    </td>
  </tr> <tr id="cash3">  		 
    		<td>  <bean:message key="label.amount"></bean:message></td>
    		 <td><html:text property="amount" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>    </td>
</tr>
 
   	               
   	                
	
	<tr><td>
	
	</td></tr>
	
	<tr><td style="border:thin solid navy;"><font color="navy">Application Details</font></td></tr>
	<tr>
		<td height="25" width="158">Purpose:</td>
		<td height="25" width="175">
		<html:select property="purpose" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="purpose" varStatus="count" items="${requestScope.purpose}" >
                        	    <html:option value="${purpose.purposeCode}" ><core:out value="${purpose.purposeDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>

</td>
	</tr>
	<tr>
		<td height="25" width="158">Appln. Srl No:</td>
		<td height="25" width="175"><html:text property="srlno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="return numberlimit(this,'10')"></html:text></td>
	</tr>
	<tr>
		<td height="29" width="158">Appln Date:</td>
		<td height="29" width="175"><html:text property="appdate" onblur="return checkformat(this)" readonly="true" onkeyup="return numberlimit(this,'11')"></html:text></td>
	</tr>
	<tr>
		<td height="26" width="158">Amount Required</td>
		<td height="26" width="175"><html:text property="loanamount" onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="return numberlimit(this,'10')"></html:text></td>
	</tr>
	<tr>
		<td height="29" width="158">Payment Mode:</td>
		<td height="29" width="175">
		<html:select property="paymode">
		<html:option value="cash">Cash</html:option>
		
		</html:select>
	</td>
	</tr>
	<tr>
		<td height="25" width="158">Interest Type:</td>
		<td height="25" width="175">
	<html:select property="intType">
	<html:option value="1">Fixed</html:option>
		<html:option value="2">Floating</html:option>
	
	</html:select>
</td>
	</tr>
	<tr>
		<td height="29" width="158">Inerest Calc. Type:</td>
		<td height="29" width="175">
		<html:select property="intcalc">
		<html:option value="1">Daily basis</html:option>
	
		
		</html:select>
		</td>
	</tr>
	<tr>
		
		<td height="29" width="175"></td>
	</tr>
	
	<tr>
	<td style="border:thin solid navy">Co-Borrowers</td>
	</tr>
	<tr>
	<td>
	Click to add coborrowers:
	<html:checkbox property="cobor" value="coborchecked" onclick="submit()"></html:checkbox>
	</td>
	
	</tr>
	<%
	if(coborrower!=null){
		%>
	<tr>
	<td>
	<html:checkbox property="chk1" value="chk1" onclick="countchecks()"></html:checkbox>
		<html:select property="coborActype1" styleClass="formTextFieldWithTransparent">
                        
                        <core:forEach var="sh" varStatus="count" items="${requestScope.sh}" >
                        	    <html:option value="${sh.moduleCode}" ><core:out value="${sh.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
		<html:text property="cobor1" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')" onblur="submit()"></html:text>
		
	</td>
	</tr>
	<tr>
	<td>
	<html:checkbox property="chk2" value="chk2" onclick="countchecks()"></html:checkbox>
		<html:select property="coborActype2" styleClass="formTextFieldWithTransparent">
                        
                        <core:forEach var="sh" varStatus="count" items="${requestScope.sh}" >
                        	    <html:option value="${sh.moduleCode}" ><core:out value="${sh.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
		<html:text property="cobor2" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"  onblur="submit()"></html:text>
		
	</td>
	</tr>
	<tr>
	<td>
	<html:checkbox property="chk3" value="chk3" onclick="countchecks()"></html:checkbox>
		<html:select property="coborActype3" styleClass="formTextFieldWithTransparent">
                        
                        <core:forEach var="sh" varStatus="count" items="${requestScope.sh}" >
                        	    <html:option value="${sh.moduleCode}" ><core:out value="${sh.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
		<html:text property="cobor3" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"  onblur="submit()"></html:text>
		
	</td>
	
	</tr>
	<%} %>
	<core:if test="<%=odaccount==null %>">
	<tr>
	<td style="border:thin solid navy">Business Details:</td>
	</tr>
	 <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Name Of Concern:
     	          </td>
     	          <td>
     	          <html:text property="businessName" onkeypress="return only_alpha()" style="font-family:bold;color:blue"></html:text>
     	          </td>
     	       </tr>
     	      <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Address:
     	          </td>
     	          <td>
     	          <html:textarea property="businessAddr" cols="25" rows="5" onkeypress="return only_for_address()" style="font-family:bold;color:blue"></html:textarea>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Nature Of Business:
     	          </td>
     	          <td>
     	          <html:text property="businessNature" onkeypress="return only_alpha()" style="font-family:bold;color:blue"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Value Of Stock:
     	          </td>
     	          <td>
     	          <html:text property="stockValue" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="return numberlimit(this,'10')" style="font-family:bold;color:blue"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Type Of Goods:
     	          </td>
     	          <td>
     	          <html:text property="goodsType" onkeypress="return only_alpha()" style="font-family:bold;color:blue"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Condition Of Goods:
     	          </td>
     	          <td>
     	          <html:text property="goodsCondition" onkeypress="return only_alpha()" style="font-family:bold;color:blue"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Average Turnover Per Month(Rs):
     	          </td>
     	          <td>
     	          <html:text property="turnover" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="return numberlimit(this,'10')" style="font-family:bold;color:blue"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Phone No:
     	          </td>
     	          <td>
     	          <html:text property="businessPhno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="return numberlimit(this,'11')" style="font-family:bold;color:blue"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Monthly Income:
     	          </td>
     	          <td>
     	          <html:text property="busMonthlyIncome" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="return numberlimit(this,'10')" style="font-family:bold;color:blue"></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Monthly Expenditure:
     	          </td>
     	          <td>
     	          <html:text property="busMonthlyExpenditure" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="return numberlimit(this,'10')" style="font-family:bold;color:blue" ></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Net Monthly Income:
     	          </td>
     	          <td>
     	          <html:text property="busNetMonthlyIncome" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="return numberlimit(this,'10')" style="font-family:bold;color:blue"></html:text>
     	          </td>
     	        </tr>
     	        </core:if>
     	        <core:if test="<%=odaccount!=null %>">
     	        <core:if test="<%=acMasterObject!=null %>">
     	        <tr><td style="border:thin solid navy">Deposit Details</td></tr>
     	        <tr>
     	        <td>
     	        <table bgcolor="#CDCEAE">
     	        <tr><td></td><td>Ac Type</td><td>Ac No.</td></tr>
     	        <%for(int k=0;k<acMasterObject.length;k++){ %>
     	        <tr>
     	        <td>
     	        <core:choose>
     	        <core:when test="<%=dupchk!=null %>">
     	        <%
     	        for(int j=0;j<dupchk.length;j++){
     	        	if(dupchk[j]!=null){
     	        	System.out.println("inside JSP at 551"+dupchk[j]);
     	        if(dupchk[j].equals(String.valueOf(k))){%>
     	        <input type="checkbox" name="dpcheck" value="<%=k %>" checked> 
     	        <%
     	        break;} 
     	        else{%>
     	         <input type="checkbox" name="dpcheck" value="<%=k %>"> 
     	       <%} }}%>
     	        </core:when>
     	        <core:otherwise>
     	         <input type="checkbox" name="dpcheck" value="<%=k %>"> 
     	        </core:otherwise>
     	        </core:choose>
     	       </td>
     	        <td><input type="text"  name="dpactyp" value=<%=acMasterObject[k].getAccType() %> size="10" style="color:#FFFFFF;background-color:#CDCEAE" onkeypress="return false;"></td>
     	        <td><input type="text"  name="dpacno" value=<%=acMasterObject[k].getAccNo() %> size="7" style="color:#FFFFFF;background-color:#CDCEAE" onkeypress="return false;"></td>
     	        </tr>
     	        <%} %>
     	        </table>
     	        </td>
     	        </tr>
	
     	       </core:if> 
     	        </core:if>
	</core:if> 
	<tr>
	<core:if test="${verified==null}">
	<td>
	<%if(closingmsg==null){ %>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<html:button property="B3" value="Submit" onclick="setval('create')" styleClass="ButtonBackgroundImage"></html:button>
	<%}else{ %>
            <html:button property="B3" value="Submit" onclick="setval('create')" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
             <%} }else{%>
             <html:button property="B3" value="Submit" onclick="created_already()" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
             <%} %>
	</td>
	<td>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
	<html:button property="B9" value="Delete" onclick="setDEval('delete')" styleClass="ButtonBackgroundImage"></html:button>
	<%}else{ %>
	<html:button property="B9" value="Delete" onclick="setDEval('delete')" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
	<%} %>
	</td>
	
	</core:if>
	<td><html:button  property="clear" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button></td>
	</tr>
</table>
</td>
<td valign="top">
<table>
<tr>
<td>
<core:if test="<%=jspPath!=null %>">
<jsp:include page="<%=jspPath.trim() %>"></jsp:include>

</core:if>
</td>
</tr>
</table>


</td>


</tr>
<tr>
<td></td>

</tr>

</table>
<p>&nbsp;</p>

	


   </html:form>
   <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
                <%
                //jspPath=(String)request.getAttribute("flag");
      		//System.out.println("jspPath=="+jspPath);
            %>
       		</body>
     </html>          