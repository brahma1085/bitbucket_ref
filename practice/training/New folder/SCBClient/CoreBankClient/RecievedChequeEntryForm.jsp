<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@page import="java.util.Map"%>


<%@page import="masterObject.clearing.Reason;"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">

function setflag(flag_num)
{
			
				document.forms[0].flag.value = flag_num;
				document.forms[0].booleanFlag.value=0;
				document.forms[0].submit();
			
			
}
function set(flag_num)
{
		if(document.forms[0].ackno.value!=0)
		{
				document.forms[0].flag.value = flag_num;
				document.forms[0].submit();
		}
		else
		{
			document.forms[0].error_message.value="Enter Valid Acknowledgement Number";
			
		}
	
}
function setPayOrder(flag_num)
{
	if(document.forms[0].payorderNum.value!=0)
		{
				document.forms[0].flag.value = flag_num;
				document.forms[0].submit();
		}
		else
		{
			document.forms[0].error_message.value="Enter Valid PO Number";
			document.forms[0].payorderNum.focus();
		}


}

function submitFlag(flagVal)
{
	document.forms[0].flag.value=flagVal;
	document.forms[0].submit();
}



	function onlyDoublenumbers()
	{
        	
        	var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || cha == 46 )
             {
            
            		return true;
            		
        	} 
        	else 
        	{
        		
        			return false;
        	}
			        
    }
        
   function onlynumbers()
   {
        	
        	var cha =   event.keyCode;

            if(cha>=48 && cha<=57) 
            {
            		return true;
          	} 
        	else 
        	{
        			return false;
        	}
			        
        }
        
        
   function numbersonly ( input )
   {

           var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) 
            {
				 if ( input.value.length >= 2 )  
					 {
						if ( input == document.getElementById("branch_code"))
							{
								if (  input.value.length == 2 )
									input.value = input.value + String.fromCharCode(cha);
									
								return false ;
								
							}
							
							else
								{			 	
								
								input.value = input.value + String.fromCharCode(cha);
					 			var j = getnextFeild(input);
                				input.form[j].focus();
                				return false ;
                			}
					 
					}	
                    return true;
                    
              }
               else 
              {

                        return false;

             }

                
            }
            
            
            
            function getTodayDate()
            {
        	var date = new Date();
        	var day = date.getDate();
        	var month = date.getMonth()+1; 
        	var year = date.getYear();
      
         		if(day <= 9)
        		{
        			day = "0"+day;
        		}  
        		if(month <= 9)
        		{
					month = "0"+month;        	
        		}
        		return day+"/"+month+"/"+year;
        	}
        
		function call()
		{
		
		setPanel();
		displaypanel();
		checkError();
		callBounce();
		enabledisablepane();
		 
		if(document.forms[0].ctrl_no.value!=0  && document.forms[0].form_flag.value == 8 ) 
		{ 
			
					alert ( document.forms[0].ctrl_no.value );
					document.getElementById("ctrl_no").value = 0;
					clearAll();
		}
		if(document.getElementById("ctrl_no").value !=0  &&  document.forms[0].form_flag.value == 9 ) 
		{
				document.getElementById("ctrl_no").value = 0;
				clearAll();
		}
		
		if(document.getElementById("flagset").value== 2)
		     document.getElementById("chqddpo").focus(); 
			
		else if(document.getElementById("flagset").value == 3)
				enabledisablepane();	
				
				
				
	}
	
	function setPanel()
	{
		if(document.getElementById("chqddpo").value =='C')
		{
			document.getElementById("div_debit_pan").style.display = 'block';
		}
		else if(document.getElementById("chqddpo").value=='P')
		{
			document.getElementById("payorder_pane").style.display = 'block';
		}
		else if(document.getElementById("chqddpo").value=='D')
		{
			document.getElementById("warrant_pane").style.display = 'block';
			
		}
	
	
	}
	
	function checkError()
	{
		
			if(document.getElementById("err_id").value==1)
			{
					clearAll();
			}
	
	}	
		
	function clearfeild(ids) 
	{
	
		document.getElementById(ids).value= "";
		
	}		
		
	function submitMICR( id ) 
	{
		if(document.getElementById("citycode").value.length != 3)
		{
				document.forms[0].error_message.value="Enter the City Code";
				document.getElementById("citycode").focus();
				return false;
		} 
		else if(document.getElementById("bank_code" ).value.length != 3)
		{
				document.forms[0].error_message.value="Enter the Bank Code";
				document.getElementById("bank_code").focus();
				return false;
		} 
		else if(document.getElementById("branch_code").value.length != 3)
		{
				document.forms[0].error_message.value="Enter the Branch Code";
				document.getElementById("branch_code").focus();
				return false;
		} 
		else 
		{
					 var mirc =  document.getElementById("citycode").value + document.getElementById("bank_code" ).value+document.getElementById("branch_code" ).value;
					 document.getElementById("micr").value = mirc;
		   			 document.forms[0].flag.value="frmMICRCode";
				     document.forms[0].submit();
				   	 return true;
		}
	}
	
		function getnextFeild(input)
		{
			var index  = -1;
			var i = 0 ;
		
			while(i<input.form.length  && index == -1)   
			{
				if(input.form[i] == input) 
				{
					index = i + 1 ;
					return index;
				} 
				else 
				{
					 i++;
				}
			}
			return index;
		}
		
	function displaypanel()
	{
			var val = document.getElementById("chqddpo").value;
		
		if(val == "C")
		{
			document.getElementById("div_debit_pan").style.display = 'block'; 
			document.getElementById("payorder_pane").style.display ='none';
			document.getElementById("warrant_pane").style.display ='none';
			
		}
		else if(val == "P")
		{
			document.getElementById("payorder_pane").style.display = 'block';
			document.getElementById("div_debit_pan").style.display ='none';
			document.getElementById("warrant_pane").style.display ='none';
		}
		else if(val == "D")
		{
			document.getElementById("warrant_pane").style.display = 'block';
			document.getElementById("div_debit_pan").style.display ='none';
			document.getElementById("payorder_pane").style.display ='none';
		}
	
	}	
	
		
		
	function enabledisablepane()  
	{
		var val = document.getElementById("combo_returned").value;
	  	
	   if(val=="N") 
	   {
	 			   document.getElementById("citycode").disabled=false;
	   			   document.getElementById("branchname").disabled=false;
	               document.getElementById("amt").disabled=false;
	   
	               document.getElementById("chqddpo").disabled=false;
	               document.getElementById("paydetail").disabled=false;
	               document.getElementById("bank_code").disabled=false;
	               document.getElementById("branch_code").disabled=false;
	               
	               document.getElementById("prev_ctrl").disabled =true ;   
	               
	   
	   } 
	   else 
	   {
	 			   document.getElementById("citycode").readonly = true;
	   			   document.getElementById("branchname").readonly = true;
	               document.getElementById("amt").readonly = true ;
	   
	               document.getElementById("chqddpo").readonly = true ;
	               document.getElementById("paydetail").readonly = true ;
	               document.getElementById("bank_code").readonly=true;
	               document.getElementById("branch_code").readonly=true;
	   
	   	
	   				document.getElementById("prev_ctrl").disabled = false;
	   } 
	   	
	}	
	
	function checkAmt() 
	{  
			var chq_amt = document.getElementById("amt").value;
			var ack_rem = document.getElementById("ack_rem").value;
			
			if(chq_amt>ack_rem) 
			{  
					document.forms[0].error_message.value="Cheque Amount is greater than remaining Acknowledge Amount " ;
			}		
	}
	
function chequedate(ids)
{
            
                      var cha =   event.keyCode;
                      var format = true;
                      var allow=true;

             var ff =  ids.value;
            
            var dd = ff.split( '/' );

			if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          document.forms[0].error_message.value=" Problem In Date Format-DD/MM/YYYY ";
                          format = false ;
                          allow=false;
                          

                      }else if(i==2 && dd[i].length != 4 ){

                            document.forms[0].error_message.value=" Problem In Date Format-DD/MM/YYYY ";
                        
                           format = false ;
                           allow=false;
                      }

            	 }
             } else{
                    allow=false;
             		format = false ;
             		 document.forms[0].error_message.value=" Problem In Date Format-DD/MM/YYYY ";
                        
             }

        if(format)
        {
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

                              document.forms[0].error_message.value="Day should not be greater than "+days+" ";
                              allow=false;
                              
                        }
                        
          
          if(dd[0]==00){
          document.forms[0].error_message.value="There is no date with 00";
          allow=false;
          }
          

          if(mth<1 || mth>12){
          document.forms[0].error_message.value="Month should be greater than 0 and \n lessthan 13  "+mth+" ";
          allow=false;
          }
          }
         }
		if(allow)
		{
		 return true;
		}
		else
		{
			document.forms[0].debit_chq_date.focus();
		 	return false;
		}
}	

  
		
		function clearAll (){
	
		var form_ele = document.forms[0].elements;
		
		var val = document.forms[0].error_message.value;
		
		for ( var i = 0 ; i< form_ele.length; i++ )
		{
			if(form_ele[i].type=='text')
				form_ele[i].value = "";
		
		}
		
		document.getElementById("bounce_id").checked = false; 
		document.forms[0].error_message.value=val;
		document.getElementById("ctrl_no").value=0;
			
		return false;
	}
	
	 function callBounce()
       {
       
       			if(document.getElementById("bounce_id").checked )
		 		{
					document.getElementById("bounce_fine").style.display = 'block';  
					
				} 
				else 
				{
					document.getElementById("bounce_fine").style.display = 'none';  
					
				}
       		
       }
	
	function setUpdate(flagVal)
	{	
		
		if(document.forms[0].ctrl_no.value<1)
		{
			document.forms[0].error_message.value="Enter Control Number ";
		}
		else
		{
			setAlert(flagVal);
		}
	}
	
	
	function setAlert(flagVal)
	{
		if(document.forms[0].ackno.value<1)
		{
			document.forms[0].error_message.value="Enter Ack Number";
		}
		else if(document.forms[0].send_to.value=="")
		{
			document.forms[0].error_message.value="Enter Source Value";
			document.forms[0].focus();
		}
		else if(document.forms[0].city_code.value=="")
		{
			submitMICR();
		}
		else
		{
			document.forms[0].flag.value=flagVal;
			document.forms[0].booleanFlag.value=0;
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

</head>
<body  bgcolor="beige" onload="call()" class="MainBody">
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

<center><h2 class="h2">Recieved Cheque Entry</h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/RecievedChequeEntryLink?pageId=7007">
		<%Reason[] reason=(Reason[])request.getAttribute("Reason"); %>	      
			      	
<table>	
	
	<tr><html:text property="error_message" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
	<tr>
	<td>
		<table>
			<tr>
					<td><bean:message key="label.controlno"></bean:message></td>
					<td><html:text property="ctrl_no" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()"  onblur="setflag('frmCtrlNum')"></html:text></td>
			</tr>
			<tr>
	
 	<td> <bean:message key="label.c_d"></bean:message> </td>
 	
	 	<td> 
			<html:select property="credebit" styleClass="formTextFieldWithoutTransparent">
				<html:option value="I">I</html:option>
				<html:option value="C">C</html:option>
				<html:option value="D">D</html:option>
				<html:option value="O">O</html:option>
			</html:select></td>  
	</tr>
										
					<tr>
					<td><bean:message key="label.ack"/></td>
					<td><html:text property="ackno"  styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" onblur="set('frmAckNum')"   styleId="ack_no" ></html:text></td>
					</tr>
					
					<tr>
					<td><bean:message key="label.source"/></td>
					<td><html:text property="send_to" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
					</tr>
					
					<tr>
					<td><bean:message key="label.sourcenm"/> </td>
					<td><html:text property="source_name" readonly="true" styleClass="formTextFieldWithoutTransparent" styleId= "sourcename" ></html:text></td>
					</tr>
										
					<tr>
					<td><bean:message key="label.return"/></td>
					<td><html:select property="return_type" styleId="combo_returned"  styleClass="formTextFieldWithoutTransparent" onchange="enabledisablepane()">
						<html:option value="N">No</html:option>
						<html:option value="Y">Yes</html:option>
						</html:select>
					</td>
					</tr>
										
					<tr>
					<td><bean:message key="label.earlier"/></td>
					<td><div><html:text property="prev_ctrl_no" styleClass="formTextFieldWithoutTransparent" styleId="prev_ctrl"  onblur="setflag('frmPrevCtrlNum')" ></html:text></div></td>
					</tr>
										
					<tr>
					<td><bean:message key="label.microcde"/> </td>
					<td><html:text property="city_code" onkeypress="return numbersonly(this)"  onfocus="clearfeild(this.id)" size="4" styleClass="formTextFieldWithoutTransparent" styleId="citycode"></html:text>-<html:text property="bank_code" onkeypress="return numbersonly(this)" onfocus="clearfeild(this.id)" size="4"  styleClass="formTextFieldWithoutTransparent" styleId="bank_code"></html:text>-<html:text property="branch_code" size="4" onkeypress="return numbersonly(this)"  onblur="return submitMICR(this)" onfocus="clearfeild(this.id)" styleClass="formTextFieldWithoutTransparent" styleId="branch_code"></html:text>
						<html:text property="cityname" style="color:blue" styleClass="formTextField" readonly="true" size="5"></html:text>
						<html:hidden property="micrcode" styleId="micr" ></html:hidden>
					</td>
					</tr>
					
					<tr>
					<td><bean:message key="label.bankname"></bean:message></td>
					<td><html:text property="bankname" styleClass="formTextField" style="color:blue" readonly="true"  styleId="bankname"></html:text></td>
					
					</tr>	
						
					<tr>
					<td><bean:message key="label.branch_name"></bean:message></td>
					<td><html:text property="branchname" styleClass="formTextFieldWithoutTransparent"   styleId="branchname"></html:text></td>
					</tr>
										
					<tr>               
					<td><bean:message key="label.chqddpo"></bean:message></td>
					<td><html:select property="chqddpo" styleClass="formTextFieldWithoutTransparent"  styleId="chqddpo" onchange="displaypanel()">
						<html:option value="C">Cheque</html:option>
						<html:option value="D">Warrant</html:option>
						<html:option value="P">PO</html:option>
						</html:select>
					</td>
					</tr>
										
					<tr>
					<td><bean:message key="label.amount"/></td>
					<td><html:text property="amount" styleClass="formTextFieldWithoutTransparent"  onkeypress="return onlyDoublenumbers()" styleId="amt" onblur="checkAmt()"></html:text></td>
					</tr>
										
					<tr>
					<td><bean:message key="label.paydetails"/></td>
					<td><html:text property="paydetail" styleClass="formTextFieldWithoutTransparent"   styleId="paydetail" ></html:text></td>
					</tr>
																	
					<tr>
					<td><bean:message key="label.bounce"/>
						<html:checkbox property="bounce" styleClass="formTextFieldWithoutTransparent" styleId="bounce_id" onclick="callBounce()" ></html:checkbox></td>
						<td><html:text property="bounceFine" style="color:red" styleClass="formTextField" readonly="true"></html:text>
					</td>
					</tr>
														
					
					<tr>
					<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             		<td><html:button property="submitValue" onclick="setAlert('frmSubmit')" value="Submit" styleClass="ButtonBackGroundImage" ></html:button>
             		<%}else{ %>
            		 <html:button property="submitValue" onclick="setAlert('frmSubmit')" value="Submit" disabled="true" styleClass="ButtonBackGroundImage" ></html:button></td>
            		 <%} %>
						<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
            		<td><html:button property="updateVale" onclick="setUpdate('frmSubmit')" value="Update" styleClass="ButtonBackGroundImage"></html:button>
            	 	<%}else{ %>
            		<html:button property="updateVale" onclick="setUpdate('frmSubmit')" disabled="true" value="Update" styleClass="ButtonBackGroundImage"></html:button></td>
            		 <%} %>
						
						<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
           			<td><html:button property="deletevale" onclick="setflag('frmDelete')" value="Delete" styleClass="ButtonBackGroundImage"></html:button>
             		<%}else{ %>
             		<html:button property="deletevale" onclick="setflag('frmDelete')" value="Delete" disabled="true" styleClass="ButtonBackGroundImage"></html:button></td>
             		<%} %>
					
						<td><html:button property="clear"  onclick="clearAll()" value="Clear"  styleClass="ButtonBackGroundImage"></html:button></td>
					</tr>	
			</table>
			</td>
								
			<td valign="top">				
					<bean:message key="label.totalAmount"></bean:message>&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="acknowledge_amt" size="5"   style="color:red" styleClass="formTextField" styleId="ack_amt" ></html:text>
					<bean:message key="label.balanceAmt"/>&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="acknowledge_rem_amt" size="5"  styleClass="formTextField" style="color:red" styleId="ack_rem"  ></html:text>
			
					<div id="div_debit_pan" style="display:none">  
 					<table style="border:thin solid #000000;">
					 	<tr>	
					 		<td><bean:message key="label.debit_ac_ty"></bean:message></td>
					 		<td><html:select property="debit_account_type" styleClass="formTextFieldWithoutTransparent"  styleId="debit_acc_type">
								<html:option value="select">Select</html:option>
								<core:forEach var="debit_module"  varStatus="count1" items="${requestScope.Debit_Module_code}" >
								<html:option value="${debit_module.moduleCode}"><core:out value="${debit_module.moduleAbbrv}"></core:out></html:option>
								</core:forEach>
								</html:select> 
							</td>
							<td><bean:message key="label.debit_ac_no"></bean:message></td>
					 		<td><html:text property="debit_account_no" styleClass="formTextFieldWithoutTransparent"  size="7" onblur="submitFlag('frmAccNum')" onkeypress="return onlynumbers()" ></html:text></td>
					 		</tr>
					 		<tr>
					 		<td><bean:message key="label.balance"></bean:message></td>
					 		<td><html:text property="balance" style="color:red" styleClass="formTextFieldWithoutTransparent" readonly="true" size="9"></html:text>
					 		</td>
					 		<td><bean:message key="label.sha_balance"></bean:message></td>
					 		<td><html:text property="shodowbalance" style="color:red" styleClass="formTextFieldWithoutTransparent" readonly="true" size="7"></html:text>
					 		</td>
					 		</tr>
					 		<tr>
					 		<td><bean:message key="label.debit_chq_date"></bean:message></td>
					 		<td><html:text property="debit_chq_date" styleClass="formTextFieldWithoutTransparent"  styleId="debit_che_date" size="9" onblur="chequedate(this)"></html:text>
					 		</td>
					 		<td><bean:message key="label.debit_chq_no" ></bean:message></td>
					 		<td><html:text property="debit_chequeno" styleClass="formTextFieldWithoutTransparent" size="7"  onkeypress="return onlynumbers()" ></html:text>
					 		</td>
					 		</tr>
					 	</table>
					 </div>
					 <div id="payorder_pane" style="display:none">
					 <table style="border:thin solid #000000;">
					 	<tr>
					 		<td><bean:message key="label.payOrderDate"></bean:message><html:text property="payorderDate" styleClass="formTextFieldWithoutTransparent" onblur="chequedate(this)"></html:text></td>
					 		<td><bean:message key="label.pay_order_no"></bean:message><html:text property="payorderNum" styleClass="formTextFieldWithoutTransparent" size="8" onkeypress="return onlynumbers()" onblur="setPayOrder('frmPayorderNum')" ></html:text></td>
					 	</tr>
					 	<tr>
					 		<td><bean:message key="label.balance"></bean:message><html:text property="payorderBalance" style="color:red" readonly="true" styleClass="formTextField"></html:text></td>
					 	</tr>
					 	<tr>
					 		<td><bean:message key="label.name"></bean:message><html:text property="payorderName" readonly="true" style="color:red" styleClass="formTextField"></html:text></td>
					 	</tr>
					 </table>
					 </div>
					 
					 <div id="warrant_pane" style="display:none">
					 <table style="border:thin solid #000000;">
					 	<tr>
					 		<td><bean:message key="label.warrantDate"></bean:message><html:text property="warrantDate" styleClass="formTextFieldWithoutTransparent" onblur="chequedate(this)" ></html:text></td>
					 		<td><bean:message key="label.warrantNum"></bean:message><html:text property="warrantNum" styleClass="formTextFieldWithoutTransparent" size="8" onkeypress="return onlynumbers()"></html:text></td>
					 	</tr>
					 	<tr>
					 		<td><bean:message key="label.balance"></bean:message><html:text property="warrantBalance" readonly="true" style="color:red" styleClass="formTextField"></html:text></td>
					 	</tr>
					 	<tr>
					 		<td><bean:message key="label.name"></bean:message><html:text property="warrantName" readonly="true" style="color:red" styleClass="formTextField"></html:text></td>
					 	</tr>
					 </table>
					 </div>					
					<div id="bounce_fine" style="display:none;overflow:scroll;width:310px;height:100px">
					<table border="1" style="border:thin solid #000000;">
						<tr><td>ResCode</td><td>Desptn</td><td>FAmt</td><td>Select</td></tr>
						<% 
						for(int i=0;i<reason.length;i++)
						{ %>
						<tr>
						<td><html:text property="txtReasonCode" size="6" value="<%=""+reason[i].getReasonCd()%>" ></html:text></td>
						<td><html:text property="txtDesription"  value="<%=""+reason[i].getReasonDesc()%>" ></html:text></td>	
						<td><html:text property="txtBounceFine" size="5" value="<%=""+reason[i].getBounceFine()%>" ></html:text></td>
						<td><input type="checkbox" name="chkBox"  value="<%=""+i%>"/></td>
						</tr>
						<%} %>	
					</table>
 					</div>	
 								
					<html:hidden property="panelFlag" styleId="panel_flag"></html:hidden>
					<html:hidden property="form_flag" styleId="flagset"></html:hidden>
					<html:hidden property="reason_codes" styleId="reason"></html:hidden> 
					<html:hidden property="error_flag" styleId="err_id"></html:hidden> 
					
					<html:hidden property="today_date" styleId="today_dt"></html:hidden>
					<html:hidden property="pageId"></html:hidden>
					<html:hidden property="booleanFlag"></html:hidden>
					<html:hidden property="flag"></html:hidden>
						
					 <table>
 					 <tr>
 						<td>
 							
 							<% 
 							String jspPath=(String)request.getAttribute("flag");
						     	System.out.println("'m inside panel"+jspPath);
						        
						     	if(jspPath!=null)
						           {
						          		System.out.println("wen 'm  null");
            							%>
            								<jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
            
            						<%}
						     	
						     	else{	%>
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