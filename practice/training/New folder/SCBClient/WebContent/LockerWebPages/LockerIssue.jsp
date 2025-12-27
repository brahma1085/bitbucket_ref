<%@ page import="masterObject.general.ModuleObject"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld"%>
<%@page import="java.util.Map"%>


<html>


<link class="OnlyNumerals.js">
<head>


<script type="text/javascript">
  
  
          function  getNumbersOnly()
          {
			 var cha = event.keyCode
		
			if(( cha >= 48 && cha < 58)) 
			{
				return true ;
            }
			return false ;
            
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
         		alert("only "+size+" digits allowed")
         		txt.value="";
         		return false;
          	}
         }
    
        
  
  function getTable()
  {
  
  	var url ="LKTableMenuLink.do?pageId=9007";      		
  	window.open( url,'Popup','WIDTH=600,HEIGHT=380,left=200,top=100,scrollbars=yes');
  
  }


	

function getAlertMessages()
{
		setJoint();
		setPassWord();
		getReceiptFrame();
	    setNominee();
   	
}
//changing according to receipt

		function getReceiptFrame()
		{
		
		
			if(document.forms[0].receiptDetails.value=="Select")
			{
				document.getElementById('cash1').style.display='none';
				document.getElementById('cash2').style.display='none';
				document.getElementById('cash3').style.display='none';
				document.getElementById('transfer').style.display='none';
			}
			else if(document.forms[0].receiptDetails.value=="C")
			{
				document.getElementById('cash1').style.display='block';
				document.getElementById('cash2').style.display='block';
				document.getElementById('cash3').style.display='block';
				document.getElementById('transfer').style.display='none';
			}
			else if(document.forms[0].receiptDetails.value=="T")
			{
				document.getElementById('cash1').style.display='none';
				document.getElementById('cash2').style.display='none';
				document.getElementById('cash3').style.display='none';
				document.getElementById('transfer').style.display='block';
			}
		
		}


	function callSubmit(){

	document.forms[0].forward.value="";
	document.forms[0].submit();
	}

 
  
 function callClear()
 {
           
          var ele= document.forms[0].elements;
           for(var i=0;i<ele.length;i++){
               if(ele[i].type=="text"){
                  
               ele[i].value="";
               }
           
           }
           
  }
           
  
  function validateDays(){
  
  var days=document.forms[0].days.value;
  var months=document.forms[0].months.value;
  var tot=days+(months*30);
 
  document.forms[0].validateFlag.value=" "+days+"<--------->"+months+"<---->"+tot+" ";
  
  return true;
  
  }
  
  
  function setForward(forwardVal)
  {
  
  	if(document.forms[0].lkacNum.value=='')
  	{
  		document.forms[0].validateFlag.value="Enter Locker A/c Number";
  		
  	}
  	else
  	{
  		document.forms[0].forward.value=forwardVal;
  		document.forms[0].submit();
  	}	
  }
  
  
  function set(target){
  
   
   document.forms[0].forward.value=target;
   
   var ele=document.forms[0].elements;
        	
        	
        	for(var i=2;i<ele.length;i++)
	      	{ 
				if(ele[i].type=="text")
				{  
					if(ele[i].value.length==0)
					{
						///document.forms[0].getElementById("lkacNumber").focus();
						return false;
					}
				}
			}
					
					var condialogue=confirm("Are  You Sure To Submit");
					
					if(condialogue)
					{
						///alert("after condialogue");
						if(document.forms[0].receiptDetails.value=="C"){
						
							///alert("inside receipt details c");
								if(document.forms[0].scrollNum.value==""||document.forms[0].date.value==""||document.forms[0].amount.value==""){
								document.forms[0].validateFlag.value="Fill Receipt(By CAsh) details";
								return false;
								}
						}
						if(document.forms[0].receiptDetails.value=="T"){
				
						///alert("inside receipt details T");
								if(document.forms[0].transferAcntNum.value==""){
									document.forms[0].validateFlag.value="Fill Receipt(TRansfer) details";
									return false;
								}						
						}
				
						///alert("-"+document.forms[0].forward.value);
						document.forms[0].submit();
					}
					else
					{
					return false;
					
					}
					
					
					
};
           
 
 
  function validateAndSubmit(target)
  {
 		if(document.forms[0].receiptDetails.value=="Select")
 		{
 			document.forms[0].validateFlag.value="Enter Receipt Details";
 			return false;
 		} 
 		else if(document.forms[0].receiptDetails.value=="C")
 		{
							
			if(document.forms[0].scrollNum.value==""||document.forms[0].date.value==""||document.forms[0].amount.value=="")
			{
				document.forms[0].validateFlag.value="Fill Receipt(By CAsh) details";
				return false;
			}
			else
			{
				document.forms[0].forward.value=target;
				document.forms[0].submit();
			}
		}
		else if(document.forms[0].receiptDetails.value=="T")
		{
								
			if(document.forms[0].transferAcntNum.value=="")
			{
				document.forms[0].validateFlag.value="Fill Receipt(TRansfer) details";
				return false;
			}
			else
			{
				document.forms[0].forward.value=target;
				document.forms[0].submit();
			}
								
		}
		else
		{
			  document.forms[0].forward.value=target;
			  document.forms[0].submit(); 
			  return true ;
		}

} 
  
function setFlag(flagVal)
{

	if(document.forms[0].txt_cid.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Cid";
		document.forms[0].txt_cid.focus();
	}
	else
	{
		
		document.forms[0].forward.value=flagVal;
		document.forms[0].submit();
	}
}  
  
function setTrnAcNum(flagVa)
{
	if(document.forms[0].txt_cid.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Cid";
		document.forms[0].txt_cid.focus();
	}
	else if(document.forms[0].lkRent.value=='')
	{
		document.forms[0].validateFlag.value="Enter Locker Rent";
		
	}
	else if(document.forms[0].transferAcntNum.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Transfer A/c Number";
		document.forms[0].transferAcntNum.focus();
	}
	else
	{
		
		document.forms[0].forward.value=flagVa;
		document.forms[0].submit();
	}
}  
  
function setScrolNum(flagVa)
{
	if(document.forms[0].scrollNum.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Scroll Number";
		document.forms[0].scrollNum.focus();
	}
	else
	{
		
		document.forms[0].forward.value=flagVa;
		document.forms[0].submit();
	}
}  
  
function Details()
{

	var name=document.forms[0].details.value;
	
	if(name=="Introducer")
	{
		if(document.forms[0].intrNum.value=='')
		{
			document.forms[0].validateFlag.value="Enter Valid Introducer Number";
		}
		else
		{
			document.forms[0].submit();	
		}
	
	}
	else if(name=="Deposit")
	{
		if(document.forms[0].acc_no.value=="")
		{
			document.forms[0].validateFlag.value="Enter Valid Deposit Number";
		}
		else
		{
			document.forms[0].submit();	
		}
	}
	else if(name=="Signature")
	{
		if(document.forms[0].txt_cid.value=='')
		{
			document.forms[0].validateFlag.value="Enter Valid Cid";
		}
		else
		{
			document.forms[0].submit();
		}
	}
	else if(name=="Personal")
	{
		if(document.forms[0].txt_cid.value=='')
		{
			document.forms[0].validateFlag.value="Enter Valid Cid";
		}
		else
		{
			document.forms[0].submit();
		}
	}
	else
	{
		document.forms[0].submit();	
	}

}  
  
function setNom(target)
{
	document.forms[0].forward.value=target;
	document.forms[0].submit();
}  
  
  
  
function setNominee()
{
	if(document.forms[0].hasAccount.checked)
	{
		document.getElementById("labelcid").style.display = 'block';
		document.getElementById("txtcid").style.display = 'block';
		document.getElementById("nomName").readonly = true ;
		document.getElementById("nomDob").readonly = true ;
		document.getElementById("nomAge").readonly = true ;
		document.getElementById("nomSex").readonly = true ;
		document.getElementById("nomRel").readonly = true ;
		document.getElementById("nomAdd").readonly = true ;
	}
	else 
	{
		document.getElementById("labelcid").style.display = 'none';
		document.getElementById("txtcid").style.display = 'none';
	}

}  
  
  function setJoint()
  {
  	if(document.forms[0].opInstr.value=='Individual')
  	{
  		document.getElementById("jointlabel").style.display='none';
  		document.getElementById("jointtxt").style.display='none';
  
  	}
  	else
  	{
  		document.getElementById("jointlabel").style.display='block';
  		document.getElementById("jointtxt").style.display='block';
  	}
  
  }
  
  function setDays()
  {
  	if(document.forms[0].lkNum.value=='' && document.forms[0].lkNum.value==0)
  	{
  		document.forms[0].validateFlag.value="Enter Valid Locker Number";
  		document.forms[0].lkNum.focus();
  		
  	}
  	else if(document.forms[0].lkKey.value=='' && document.forms[0].lkKey.value==0)
  	{
  		document.forms[0].validateFlag.value="Enter Valid Key Number";
  		document.forms[0].lkKey.focus();
  	}
  	else if(document.forms[0].months.value=='')
  	{
  		document.forms[0].validateFlag.value="Enter Number Of Months";
  		document.forms[0].months.focus();
  	}
  	else if(document.forms[0].days.value=='')
  	{
  		document.forms[0].validateFlag.value="Enter Number Of Days";
  		document.forms[0].days.focus();
  	}
  	else
  	{
  		document.forms[0].submit();
  	}
  
  }
  
 function setPassWord()
 {
 	if(document.forms[0].required.checked)
 	{
 		document.getElementById("passreq").disabled=false;
 	}
 	else
 	{
 		document.getElementById("passreq").disabled=true;
 	}
 
 } 
 
function validateIntr()
{
	if(document.forms[0].intrNum.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Introducer Number";
		document.forms[0].intrNum.focus();
	}
	else
	{
		document.forms[0].forward.value='IntrNum';
		document.forms[0].submit();
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

<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
</head>
<body class="Mainbody" onload="getAlertMessages()">
<center>
<h2 class="h2">Locker Issue</h2>
</center>



<%!
String access;
String  accesspageId;
 Map user_role;

%>

<%

  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");

if(user_role!=null && accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/LKIssueLink?pageId=9001">

	<%!
		ModuleObject[] array_module;
		ModuleObject[] modobjIntro;
		ModuleObject[] deposittypes;
		String jointholder="";
		String Nominee="";
		String[] rentExpirydate;
	%>
			
			<html:hidden property="forward"></html:hidden>
			<html:hidden property="depositFlag"></html:hidden>

	<table class="txtTable">
	
		<tr><html:text property="validateFlag" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
		<tr>
		<td>
		
			<%deposittypes=(ModuleObject[])request.getAttribute("DepositTypes"); %>		
			<%rentExpirydate = (String[]) request.getAttribute("rentExpirydate");%>
					
		<table class="txtTable">
			<tr>
					<td><bean:message key="lab.ac_type"></bean:message></td>
					<td><html:select property="lkacType" styleClass="formTextFieldWithoutTransparent">
					<c:forEach var="parm" items="${requestScope.param}">
						<html:option value="${parm.moduleCode}"><c:out value="${parm.moduleAbbrv}"></c:out></html:option>
					</c:forEach>
					</html:select>
					</td>
					<td><html:text property="lkacNum" styleClass="formTextFieldWithoutTransparent" size="8" onkeypress="return getNumbersOnly()" onblur="setForward('LkNum')"></html:text></td>
				
			</tr>
			<tr>
				<td><bean:message key="label.cid"></bean:message></td>
				<td><html:text property="txt_cid" size="10" onblur="setFlag('Cid')" onkeypress="return getNumbersOnly()" onkeyup="numberlimit(this,'9')"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
			</tr>
			<tr>
				<td><bean:message key="label.tabView"></bean:message></td>
				<td><html:button property="tabView" value="LockerTable"  styleClass="ButtonBackgroundImage" onclick="getTable()"></html:button></td>
			</tr>
			<tr>
				<td><bean:message key="lab.acType_num"></bean:message></td>
				<td><html:select property="lkType"  styleClass="formTextFieldWithoutTransparent">
						<c:forEach var="lktypes" items="${requestScope.lktypes}">
						<html:option value="${lktypes}"><c:out value="${lktypes}"></c:out></html:option>
						</c:forEach>
					</html:select>
				</td>
				<td><html:text property="lkNum" onkeypress="return getNumbersOnly()" onkeyup="numberlimit(this,'8')" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			</tr>

			<tr>
				<td><bean:message key="label.keyNo&Rent"></bean:message></td>
				<td><html:text property="lkKey" onkeypress="return getNumbersOnly()" onkeyup="numberlimit(this,'11')" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<td><html:text property="lkRent" onkeypress="return getNumbersOnly()" onkeyup="numberlimit(this,'11')" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			</tr>


			<tr>
				<td><bean:message key="lab.password"></bean:message></td>
				<td><html:checkbox property="required" onclick="setPassWord()"></html:checkbox>
					<bean:message key="label.require"></bean:message>
				</td>
				<td><html:password property="pass" size="8" styleId="passreq" onkeyup="numberlimit(this,'11')" styleClass="formTextFieldWithoutTransparent"></html:password></td>
			</tr>
			
			<tr>
				<td>Period:Months&&Days</td>
				<td><html:text property="months" onkeypress="return getNumbersOnly()" onkeyup="numberlimit(this,'5')" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				<td><html:text property="days" onkeypress="return getNumbersOnly()" onkeyup="numberlimit(this,'8')" onblur="setDays()" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			</tr>
				
			<tr>
				<td><bean:message key="lab.allotExpiry"></bean:message></td>
				<td><html:text styleClass="formTextFieldWithoutTransparent" property="allotDate" readonly="true" value="<%=(String)request.getAttribute("date")%>" size="10"></html:text></td>

				<td><html:text styleClass="formTextFieldWithoutTransparent" property="expDate" readonly="true" size="8"></html:text></td>
			<tr>
				<td><bean:message key="label.rentAmount"></bean:message></td>
				<td><html:text styleClass="formTextFieldWithoutTransparent" property="lkRent" readonly="true" size="10"></html:text></td>
			</tr>

				<!--<tr>
				<td> TranferAccount From:</td>
				<td>
				<html:select property="transferAcType" styleClass="formTextFieldWithoutTransparent"  style="border:thin solid blue">
              			  <c:forEach var="introtypes" items="${requestScope.introtypes}">
              			  <html:option value="${introtypes.moduleCode}" > <c:out value="${introtypes.moduleDesc}"> </c:out></html:option>
                          </c:forEach>
               </html:select>
			   </td>
			   <td>
			   <html:text property="transferAcNum" styleClass="formTextFieldWithoutTransparent" size="8"></html:text>
				</td>
				</tr>
              -->
				
			<tr>
				<td>Receipt:</td>
				<td><html:select property="receiptDetails" styleClass="formTextFieldWithoutTransparent" onchange="getReceiptFrame()">
					<html:option value="Select">Select</html:option>
					<html:option value="C">Cash</html:option>
					<html:option value="T">Transfer</html:option>
					</html:select>
				</td>
			</tr>


			<tr id="cash1" style="display:none;">
				<td><bean:message key="label.scroll_no"></bean:message></td>
				<td><html:text property="scrollNum" styleClass="formTextFieldWithoutTransparent" size="10" onkeyup="numberlimit(this,'8')" onkeypress="return getNumbersOnly()" onblur="setScrolNum('ScrollNum')"></html:text></td>
			</tr>
			<tr id="cash2" style="display:none;">
				<td><bean:message key="label.date"></bean:message></td>
				<td><html:text property="date" readonly="true" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			</tr>
			<tr id="cash3" style="display:none;">
				<td><bean:message key="label.amount"></bean:message></td>
				<td><html:text property="amount" readonly="true" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			</tr>
			<tr id="transfer" style="display:none;">
				<td><bean:message key="label.trf_actype"></bean:message>--<bean:message key="label.trf_acno"></bean:message></td>
				<td><html:select property="transferAcntType" styleClass="formTextFieldWithoutTransparent">
					<c:forEach var="introtypes" items="${requestScope.transfertypes}">
						<html:option value="${introtypes.moduleCode}">
							<c:out value="${introtypes.moduleAbbrv}"></c:out>
						</html:option>
					</c:forEach>
					</html:select>
				</td>
				<td><html:text property="transferAcntNum" styleClass="formTextFieldWithoutTransparent"  size="8" onkeypress="return getNumbersOnly()" onkeyup="numberlimit(this,'10')" onblur="setTrnAcNum('TrnAcNum')"></html:text></td>
			</tr>

			

			<tr>
				<td><bean:message key="lab.operatingInstr"></bean:message></td>
				<td><html:select property="opInstr" styleClass="formTextFieldWithoutTransparent" onchange="setJoint()">
					<html:option value="Individual"></html:option>
					<html:option value="E or S"></html:option>
					<html:option value="Jointly"></html:option>
				</html:select></td>
			</tr>

			<tr>
				<td><bean:message key="label.membNo"></bean:message></td>
				<td><html:select property="membType" styleClass="formTextFieldWithoutTransparent">
					<html:option value="1001001">SH</html:option>
					</html:select>
				</td>
				<td><html:text property="membAc" readonly="true" styleClass="formTextFieldWithoutTransparent" size="8" ></html:text>  </td>				
				
				
			</tr>

			<tr>
				<td><bean:message key="label.Intro"></bean:message></td>

				<td><html:select property="intrType" styleClass="formTextFieldWithoutTransparent">
					<c:forEach var="introtypes" items="${requestScope.introtypes}">
						<html:option value="${introtypes.moduleCode}">
							<c:out value="${introtypes.moduleAbbrv}"></c:out>
						</html:option>
					</c:forEach>
				</html:select></td>


				<td><html:text styleClass="formTextFieldWithoutTransparent" property="intrNum" onblur="validateIntr()" onkeypress="return getNumbersOnly()" onkeyup="numberlimit(this,'10')" size="8"></html:text></td>
			</tr>

			<tr>
				<td><bean:message key="label.nomReg"></bean:message></td>
				<td><html:text property="nomReg" size="10" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			</tr>
			
			<tr>
		 		
		 		<td><bean:message key="lable.Dep_ac"></bean:message></td>
				<td>
					<html:select  property="combo_details" styleClass="formTextFieldWithoutTransparent">
	     				<%for(int k=0;k<deposittypes.length;k++){ %>
	     				<html:option value="<%=deposittypes[k].getModuleCode()%>"><%=""+deposittypes[k].getModuleAbbrv()%></html:option>
	    				<%} %>
	    			</html:select>
	    			<bean:message key="lable.depacnum"></bean:message>
	    			
	    		</td>
	    		
	    		<td><html:text property="acc_no" size="8" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'10')" onkeypress="return getNumbersOnly()" onblur="setFlag('depositnum')"  ></html:text></td>
			</tr>
			<tr>
				
                <td><div id="jointlabel" style="display:none;"><bean:message key="label.jointCid"></bean:message></div></td>
                <td><div id="jointtxt" style="display:none;"><html:text property="cid" styleClass="formTextFieldWithoutTransparent" onkeypress="return getNumbersOnly()" size="10" onblur="setFlag('jointcid')"></html:text></div></td>
            </tr>		
				
			<tr>
				<td><bean:message key="label.details"></bean:message></td>
				<td><html:select property="details" styleClass="formTextFieldWithoutTransparent" onchange="Details()">
					<html:option value="SELECT"></html:option>
					<html:option value="Personal"></html:option>
					<html:option value="Introducer"></html:option>
					<html:option value="Signature"></html:option>
					<html:option value="JointHolders"></html:option>
					<html:option value="Deposit"></html:option>
					<html:option value="Nominee"></html:option>
					
				</html:select></td>
			</tr>


			<tr>
				<td><bean:message key="label.autoExtn"></bean:message></td>
				<td><html:select property="autoExtn"
					styleClass="formTextFieldWithoutTransparent">
					<html:option value="yes">YES</html:option>
					<html:option value="no">NO</html:option>
				</html:select></td>
			</tr>

			<tr>
				<td>
				
<!--<html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit" ></bean:message>  </html:submit>
		-->
		
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            <html:button property="submitButton" onclick="return validateAndSubmit('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message>
		</html:button>
             <%}else{ %>
            <html:button property="submitButton" disabled="true" onclick="return validateAndSubmit('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message>
		</html:button>
             <%} %>
		
		
		 <html:button property="buttonClear" styleClass="ButtonBackgroundImage" onclick="callClear()"><bean:message key="label.clear"></bean:message>
		</html:button></td>
		
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
            <td><html:button property="button_delete" styleClass="ButtonBackgroundImage" onclick="setForward('delete')"> <bean:message key="label.delete"></bean:message>
		</html:button></td>
             <%}else{ %>
            <td><html:button property="button_delete" disabled="true" styleClass="ButtonBackgroundImage" onclick="setForward('delete')"> <bean:message key="label.delete"></bean:message>
		</html:button></td>
             <%} %>
		
			
		</tr>
			
		</table>
	
		</td>
		
		
		<td align="left" valign="top">
		
			<table class="txtTable">
			<tr>
					
	   			 	<%Nominee=(String)request.getAttribute("nomdetail"); %>
			
					<%if(Nominee!=null){ %>
					<td>
						<table class="txtTable" style="border:solid thin #000000; ">
							<tr><td></td><td>NomineeDetails</td><td></td></tr>
							<tr>
								<td><bean:message key="label.hasAccount"></bean:message></td>
								<td><html:checkbox property="hasAccount" onclick="setNominee()"></html:checkbox></td>
							</tr>
							<tr>
								<td><div id="labelcid" style="display:none;"><bean:message key="label.cid"></bean:message></div></td>
								<td><div id="txtcid" style="display:none;"><html:text property="nomineeCid" size="5" styleClass="formTextFieldWithoutTransparent" onblur="setNom('nomineecid')"></html:text></div></td>
							</tr>
							<tr>
								<td><bean:message key="label.custname"></bean:message></td>
								<td><html:text property="nomineeName" styleId="nomName" styleClass="formTextFieldWithoutTransparent"></html:text></td>
							    	
							</tr>
							<tr>
								<td><bean:message key="label.dob"></bean:message></td>
								<td><html:text property="nomineeDob" styleId="nomDob" styleClass="formTextFieldWithoutTransparent"></html:text></td>
							    
							</tr>
							<tr>
								<td><bean:message key="label.age"></bean:message></td>
								<td><html:text property="nomineeAge" styleId="nomAge" styleClass="formTextFieldWithoutTransparent"></html:text></td>
							    
							</tr>
							<tr>
								<td><bean:message key="label.sex"></bean:message></td>
								<td><html:text property="nomineeSex" styleId="nomSex" styleClass="formTextFieldWithoutTransparent"></html:text></td>
							    
							</tr>
							<tr>
								<td><bean:message key="label.address"></bean:message></td>
								<td><html:textarea property="nomineeAddress" styleId="nomAdd" styleClass="formTextFieldWithoutTransparent"></html:textarea></td>
							    
							</tr>
							<tr>
								<td><bean:message key="label.relationship"></bean:message></td>
								<td><html:text property="nomineeRelationship" styleId="nomRel" styleClass="formTextFieldWithoutTransparent"></html:text></td>
							   
							</tr>
							<tr>	
								<td><bean:message key="label.percentage"></bean:message></td>
								<td><html:text property="nomineePercentage" size="5" readonly="true" styleClass="formTextFieldWithoutTransparent" value="100"></html:text></td>
							</tr>
							
						</table>
												
					<% } %>
				</td>
			</tr>
			<tr>
			<td>
			<%
			String jspPath = (String) request.getAttribute("flag");
			%> 
			<c:if test="${requestScope.flag !=null}">
				<table style="border:solid thin #000000;"><tr>
					<jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
				</tr>
				</table>
			</c:if>
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