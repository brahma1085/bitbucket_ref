<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%> 
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="masterObject.clearing.Reason;"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function setDetails(flagVal)
{
	submitMICR();
	if(document.forms[0].ackNumber.value==0)
	{
		document.forms[0].validateFlag.value="Enter Valid Acknowledgement Number";
		document.forms[0].focus();
	}
	else if(document.forms[0].acNumber.value==0)
	{
		document.forms[0].validateFlag.value="Enter Valid Account Number";
		document.forms[0].focus();
	}
	else if(document.forms[0].amount.value==0)
	{
		document.forms[0].validateFlag.value="Enter Amount ";
		document.forms[0].focus();
	}
	else if(document.forms[0].itemSeqNum.value=='')
	{
		document.forms[0].validateFlag.value="Enter ItemSeqNumber";
	}
	else
	{	
	
		if(flagVal=='Update')
		{
			if(document.forms[0].controlNum.value==0)
			{
				document.forms[0].validateFlag.value="Control Number Can't Be Zero";
			}
			else
			{
				document.forms[0].flag.value=flagVal;
				document.forms[0].booleanFlag.value=0;
				document.forms[0].submit();
			}
		}
		else if(flagVal=='submit')
		{
			document.forms[0].flag.value=flagVal;
			document.forms[0].booleanFlag.value=0;
			document.forms[0].submit();
		}	
	}

}

function setAcNum(flagVal)
{
	if(document.forms[0].acNumber.value!="")
	{
		if(document.forms[0].acNumber.value>0)
		{
			document.forms[0].flag.value=flagVal;
			document.forms[0].submit();
		}
		else
		{
			document.forms[0].validateFlag.value="Enter Valid Account Number";
		}
	
	}
	else
	{
		document.forms[0].validateFlag.value="Account Number Field Can't Be Blank";
	}

}

function setFlag(flagValue)
{
	document.forms[0].flag.value=flagValue;
	document.forms[0].submit();
}

function set(flagValue)
{
	if(document.forms[0].ackNumber.value==0)
	{
		document.forms[0].validateFlag.value="Enter Valid Acknowledgement Number";
		document.forms[0].ackNumber.focus();
	
	}
	else
	{	
		document.forms[0].flag.value=flagValue;
		document.forms[0].submit();
	}
	
}


function getAlertMessages()
{
	if(document.forms[0].errorFlag.value=='1')
	{
		clearAll();
	}
}

function callBounce()
       {
       
       			if( document.getElementById("bounce_id").checked )
		 		{
					document.getElementById("bounce_fine").style.display = 'block';  
				} 
				else 
				{
					document.getElementById("bounce_fine").style.display = 'none';  
				}
      }


function submitMICR()
{
		if ( document.getElementById( "citycode" ).value.length != 3 )
		{
		
				document.forms[0].validateFlag.value="Enter the City Code";
				document.forms[0].focus();
				return false;
	
		} 
		else if ( document.getElementById("bank_code" ).value.length != 3 )
		{
		
				document.forms[0].validateFlag.value= "Enter the Bank Code";
				return false;
	
		} 
		else if(document.getElementById("branch_code" ).value.length != 3 )
		{
		
				document.forms[0].validateFlag.value="Enter the Branch Code";
				document.forms[0].focus();
				return false;
	
		} 
		else 
		{
				document.forms[0].booleanFlag.value =0;
				var mirc =  document.getElementById("citycode").value + document.getElementById("bank_code" ).value+document.getElementById("branch_code" ).value;
				document.forms[0].flag.value='frmMICRCode';
				document.forms[0].submit();
				return true;
		}
		
	
	
	}

function setAmount(flagVal)
{
	if(document.getElementById("ackNo").value=="")
	{
		document.forms[0].validateFlag.value="Enter Valid Acknowledgement Number";
		document.getElementById("ackNo").focus();
	}
	else if(document.getElementById("ackNo").value==0)
	{
		document.forms[0].validateFlag.value="Enter Valid Acknowledgement Number";
		document.getElementById("ackNo").focus();
	}
	else if(document.getElementById("amt").value=="")
	{
		document.forms[0].validateFlag.value="Enter Valid Amount";
		document.getElementById("amt").focus();
	}
	else if(document.getElementById("amt").value==0)
	{
		document.forms[0].validateFlag.value="Enter Valid Amount";
		document.getElementById("amt").focus();
	}
	else
	{
		document.forms[0].flag.value=flagVal;
		document.forms[0].submit();
	}

}

function onlynumbers()
{
        	var cha =event.keyCode;
            if(cha>=48 && cha<=57) 
            {
            		return true;
            		
        	} 
        	else
            {
        			return false;
        	}
		}
        
     function numbersonly(input)
     {
           var cha = event.keyCode;
            if(cha>= 48 && cha<=57) 
            {
					 if(input.value.length >= 2)  
					 {
							if( input == document.getElementById("branch_code")   ){
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
        
function clearfeild(ids)
	{
		document.getElementById(ids).value= "";
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

                      if(i!=2 && dd[i].length!=2)
                      {
                          document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
                          format = false ;
                          allow=false;
                      } 
                      else if(i==2 && dd[i].length != 4)
                      {
                           document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
                           format = false ;
                           allow=false;
                      }

            	 }
             } 
             else
             {
                    allow=false;
             		format = false ;
             		document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
             		
             }

        if(format){
        var date=new Date();
         var dayCheck=dd[0];
         
         var monthCheck=dd[1];
         var yrCheck=dd[2];
         var mth=dd[1];
         
         dd[1]=dd[1]-1;
	   var days = 32 - new Date(dd[2],dd[1],32).getDate();
         if(dd[0].length==2)
         {
          if(dd[0]>days)
            {
              document.forms[0].validateFlag.value="Day should not be greater than "+days+" ";
              allow=false;
            }
           if(dd[0]==00)
            {
          	 document.forms[0].validateFlag.value="There is no date with 00";
         	 allow=false;
            }
          if(mth<1 || mth>12)
           {
            document.forms[0].validateFlag.value="Month should be greater than 0 and \n lessthan 13  "+mth+" ";
            allow=false;
           }
         }
         if(dd[2].length==4)
         {
          if((parseInt(dd[2])<=parseInt(1980)))
          {
          		document.forms[0].validateFlag.value="Year should be greater than "+1980+" ";
          		 allow=false;
          }
          else if((parseInt(dd[2])>parseInt(date.getYear())))
          {
          document.forms[0].validateFlag.value="Year should be less than "+date.getYear()+" ";
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
			document.forms[0].clr_date.focus();
		
			 return false;
		}

}	
       
       
function clearAll()
{
	
		var form_ele = document.forms[0].elements;
		
		var val = document.forms[0].validateFlag.value;
		
		for ( var i = 0 ; i< form_ele.length; i++ )
		{
			
			if(form_ele[i].type=='text')
				form_ele[i].value = "";
		
		}
		
		document.getElementById("bounce_id").checked = false; 
		document.getElementById("ctrl_no").value=0;
		document.forms[0].validateFlag.value=val;
			
		return false;
}
		
function onlyDoublenumbers()
{
        	var cha = event.keyCode;
            if((cha >= 48 && cha <= 57 ) || cha == 46)
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
			if ( cha >= 48 && cha <= 57  )
            {
           		return true;
            } 
        	else 
        	{
        		return false;
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

<center><h2 class="h2"><bean:message key="label.ReceivedECS"></bean:message> </h2></center>
<%Reason[] reason=(Reason[])request.getAttribute("Reason"); %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/ReceivedECSLink?pageId=7027">
		<html:hidden property="flag"></html:hidden>
		
		<html:hidden property="booleanFlag"></html:hidden>
		<html:hidden property="errorFlag"></html:hidden>
<table>
	<tr><html:text property="validateFlag" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
	
	<tr>
	<td>
	<table>
			<tr>
			<td> <bean:message key="label.controlno"></bean:message> </td>
			<td> <html:text property="controlNum" styleId="ctrl_no" styleClass="formTextFieldWithoutTransparent" onblur="setFlag('frmCtrlNum')" onkeypress="return onlynumbers()"></html:text>  </td>
			<td><bean:message key="label.ack_amt"></bean:message>
			<html:text property="ack_balance" readonly="true" style="color:red" styleClass="formTextField"></html:text></td>
				
			</tr>

	<tr>
	
 	<td> <bean:message key="label.c_d"></bean:message> </td>
 	
	 	<td> 
			<html:select property="credebit" styleClass="formTextFieldWithoutTransparent">
				<html:option value="C">C</html:option>
				<html:option value="D">D</html:option>
			</html:select></td>  
	</tr>

	<tr>
	<td> <bean:message key="label.ack"></bean:message> </td>
	<td> <html:text property="ackNumber" styleId="ackNo" onblur="set('ackNumber')" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
	<td><html:text property="ack_source" styleClass="formTextField" readonly="true"></html:text></td>
	</tr>


	<tr>
	<td> <bean:message key="label.acType"></bean:message> </td>
	<td> <html:select property="credit_account_type" styleClass="formTextFieldWithoutTransparent" styleId="credit_acc_type">
		 <html:option value="select">select</html:option>
		 <core:forEach var="module"  varStatus="count" items="${requestScope.Main_Module_code}" >
		 <html:option value="${module.moduleCode}"><core:out value="${module.moduleAbbrv}"></core:out></html:option>
		 </core:forEach>
		 </html:select>
	</td>								
	</tr>

	<tr>
	
	<td><bean:message key="label.acNum"></bean:message> </td>
	<td> <html:text property="acNumber" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" onblur="setAcNum('frmAccNum')"></html:text></td>
	
	<td>Balance:<html:text property="balance" styleClass="formTextField" style="color:red"  readonly="true"></html:text> </td>
	</tr>
	
	<tr>
	<td> <bean:message key="label.clr_date"></bean:message> </td>
	<td> <html:text property="clr_date" styleClass="formTextFieldWithoutTransparent" value="<%=""+request.getAttribute("date")%>" onblur="checkformat(this)"></html:text>  </td>
	</tr>

	<tr>
	<td> <bean:message key="label.bankCodeName"></bean:message> </td>
	
	<td><html:text property="cityCode" size="3" styleId="citycode" onkeypress="return numbersonly(this)"  onfocus="clearfeild(this.id)" styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="bankCode" styleId="bank_code" size="3"  styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly(this)" onfocus="clearfeild(this.id)"></html:text>-<html:text property="branchCode" size="3" styleClass="formTextFieldWithoutTransparent"   onkeypress="return numbersonly(this)" onblur="return submitMICR()" onfocus="clearfeild(this.id)" styleId="branch_code"></html:text></td>
	<td><html:text property="bankName" readonly="true" styleClass="formTextField"></html:text></td>
	</tr>

	<tr>
	<td> <bean:message key="label.payee"></bean:message> </td>
	<td> <html:text property="payee" styleClass="formTextFieldWithoutTransparent"></html:text>        </td>
	</tr>

	<tr>
	<td> <bean:message key="label.amount"></bean:message> </td>
	<td> <html:text property="amount" styleId="amt" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlyDoublenumbers()" onblur="setAmount('frmAmt')"></html:text>  </td>
	</tr>

	<tr>
	<td> <bean:message key="label.itemSeqNum"></bean:message> </td>
	<td> <html:text property="itemSeqNum" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()"></html:text>  </td>
	</tr>
	<tr>
	<td><bean:message key="label.bounce"></bean:message></td>
	<td><html:checkbox property="bounce" styleId="bounce_id" onclick="callBounce()" styleClass="formTextField"></html:checkbox></td>
	<td><html:text property="bounceFine" readonly="true" style="color:red" styleClass="formTextField"></html:text></td>
	</tr>
	
	<tr>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <td> <html:button property="button_submit" onclick="setDetails('submit')" styleClass="ButtonBackGroundImage"> <bean:message key="label.submit"></bean:message></html:button>
             <%}else{ %>
             <td> <html:button property="button_submit" disabled="true" onclick="setDetails('submit')" styleClass="ButtonBackGroundImage"> <bean:message key="label.submit"></bean:message></html:button>
             <%} %>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
           <td><html:button property="buttonupdate" onclick="setDetails('Update')" styleClass="ButtonBackGroundImage" value="Update"></html:button></td>
             <%}else{ %>
           <td> <html:button property="buttonupdate" onclick="setDetails('Update')" disabled="true" styleClass="ButtonBackGroundImage" value="Update"></html:button></td>
             <%} %>
	 		
	 		<td><html:button property="clear" value="Clear" onclick="clearAll()" styleClass="ButtonBackGroundImage"></html:button></td>
	 </tr>
	 	
	</table>	
	</td>
		<td valign="top">
		
		<div id="bounce_fine" style="display:none;overflow:scroll;width:310px;height:100px">
		<table border="1" style="border:thin solid #000000;" title="ReasonTable">
				<tr><td>ResCode</td><td>Desptn</td><td>FAmt</td><td>Select</td></tr>
				<% 
						for(int i=0;i<reason.length;i++)
						{ %>
						<tr>
						<td><html:text property="txtReasonCode" size="5" value="<%=""+reason[i].getReasonCd()%>" ></html:text></td>
						<td><html:text property="txtDesription"  value="<%=""+reason[i].getReasonDesc()%>" ></html:text></td>	
						<td><html:text property="txtBounceFine" size="5" value="<%=""+reason[i].getBounceFine()%>" ></html:text></td>
						<td><input type="checkbox" name="chkBoxVal"  value="<%=""+i%>" /></td>
						</tr>
				<%} %>	
		</table>
 		</div>	
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
			
	</tr>
	</table>
	

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>