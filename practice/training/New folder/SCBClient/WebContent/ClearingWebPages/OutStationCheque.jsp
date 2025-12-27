<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<%@page import="masterObject.clearing.Reason;"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">

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
 
 function submitMICR(flagVal)
 {
	if(document.getElementById("panel_city_code").value.length != 3 )
	{
		document.forms[0].validateFlag.value=" Enter the City Code ";
		return false;
	}
	else if (document.getElementById("panel_bank_code").value.length != 3 )
	{
		document.forms[0].validateFlag.value="Enter the Bank Code";
		return false;
	}
	else if ( document.getElementById("panel_branch_code" ).value.length != 3 )
	{
		document.forms[0].validateFlag.value=" Enter the Branch Code" ;
		return false;
	}
	else
	{
		document.forms[0].flag.value=flagVal;
		document.forms[0].submit();
	}
		
}
 
 
 
 
 	function getnextFeild(input)
 	{
 		var index  = -1;
		var i = 0 ;
		while(i<input.form.length  && index == -1)
		   {
				if ( input.form[i] == input)
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
 
 
  function numbersonly(input)
  {
	 var cha=event.keyCode;
	if(cha >= 48 && cha <= 57)
	 {
		if(input.value.length >= 2)
		  {
			if(input == document.getElementById("panel_branch_code"))
			{
				if (input.value.length == 2 )
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
  
 function clearfeild(ids)
	{
	
		document.getElementById(ids).value= "";
		
	} 
        
function setFlag(flagVal)
{	
	if(flagVal=='frmUpdate')
	{			if ( document.getElementById("ctrl_no").value == 0  )
					{
						document.forms[0].validateFlag.value=" Enter Valid Control Number ";
						return false;
					}
					else if(document.getElementById("credit_acc_no").value==0)
					{
						document.forms[0].validateFlag.value=" Enter Valid Account Number";
						return false;
					}
					else if ( document.getElementById("panel_city_code").value.length != 3 )
					{
						document.forms[0].validateFlag.value=" Enter the City Code ";
						document.getElementById("panel_city_code").focus;
						return false;
					}
				   else if ( document.getElementById("panel_bank_code" ).value.length != 3 )
				   {
						document.forms[0].validateFlag.value=" Enter the Bank Code";
						document.getElementById("panel_bank_code").focus;
						return false;
					}
				 	else if ( document.getElementById("panel_branch_code" ).value.length != 3 )
				 	{
						document.forms[0].validateFlag.value= " Enter the Branch Code" ;
						document.getElementById("panel_branch_code").focus;
						return false;
					}
					else if(document.getElementById("panel_chqddno").value=="")
					{
							document.forms[0].validateFlag.value= " Enter Cheque Number";
							return false;
					}
					else if(document.getElementById("panel_chqdate").value=="")
					{
							document.forms[0].validateFlag.value= " Enter Cheque Date";
							return false;
					}
					else if(document.getElementById("panel_amt").value=="" && document.getElementById("panel_amt").value==0.0)
					{
							document.forms[0].validateFlag.value="Enter Cheque Amount";
							return false;
					}
					else
					{
						document.forms[0].flag.value=flagVal;
						document.forms[0].booleanFlag.value=0;
						document.forms[0].submit();
					}
				
		
	}
	else if(flagVal=='frmSubmit')
	{				
					if ( document.getElementById("ctrl_no").value != 0  )
					{
						document.forms[0].validateFlag.value=" Control Number Should Be Zero ";
						return false;
					}
					else if(document.getElementById("credit_acc_no").value==0)
					{
						document.forms[0].validateFlag.value=" Enter Valid Account Number ";
						return false;
					}
					else if(document.getElementById("panel_city_code").value.length != 3 )
					{
						document.forms[0].validateFlag.value=" Enter the City Code ";
						document.getElementById("panel_city_code").focus;
						return false;
					}
				   else if ( document.getElementById("panel_bank_code" ).value.length != 3 )
				   {
						document.forms[0].validateFlag.value=" Enter the Bank Code";
						document.getElementById("panel_bank_code").focus;
						return false;
					}
				 	else if ( document.getElementById("panel_branch_code" ).value.length != 3 )
				 	{
						document.forms[0].validateFlag.value="Enter the Branch Code";
						document.getElementById("panel_branch_code").focus;
						return false;
					}
					else if(document.getElementById("panel_chqddno").value=="")
					{
							document.forms[0].validateFlag.value="Enter Cheque Number";
							return false;
					}
					else if(document.getElementById("panel_chqdate").value=="")
					{
							document.forms[0].validateFlag.value="Enter Cheque Date";
							return false;
					}
					else if(document.getElementById("panel_amt").value=="")
					{
							document.forms[0].validateFlag.value="Enter Cheque Amount";
							return false;
					}
					else if(document.getElementById("panel_amt").value==0.0)
					{
						document.forms[0].validateFlag.value="Enter Cheque Amount";
						return false;
					}
					else
					{
						document.forms[0].flag.value='frmUpdate';
						document.forms[0].booleanFlag.value=0;
						document.forms[0].submit();
					}
					
	}
	else if(flagVal='frmCtrlNum')
	{
			document.forms[0].flag.value='frmCtrlNum';
			document.forms[0].booleanFlag.value=0;
			document.forms[0].submit();
	}	
				
		
		
}
function callBounce()
       {
       
       		if (document.forms[0].returned.checked )
		 	{
				document.getElementById("bounce_fine").style.display = 'block';  
			} 
			else 
			{
				document.getElementById("bounce_fine").style.display = 'none';
			}
       }

function getAlertMessages()
{
	
	if(document.forms[0].errorFlag.value=="1")
	{
		clearAll();
	}
	callBounce();

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
		document.getElementById("prev_ctrlno").value=0;
		document.forms[0].validateFlag.value=val;
			
		return false;
}

function set(flagVal)
{
	document.forms[0].flag.value=flagVal;
	document.forms[0].submit();
}

 function checkformat(ids) 
 {
            
                      var cha =   event.keyCode;
                      var format = true;
                      var allow=true;

             var ff =  ids.value;
            
            var dd = ff.split( '/' );

			if(dd.length==3)
			{

             	for(var i =0; i< dd.length; i++)
             	{
                      if(i!=2 && dd[i].length!= 2)
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
         if(dd[0].length==2){
          if(dd[0]>days)
            {
               document.forms[0].validateFlag.value=" Day should not be greater than "+days+" ";
               allow=false;
            }
            if(dd[0]==00)
          	{
          		document.forms[0].validateFlag.value=" There is no date with 00";
          		allow=false;
          	}
            if(mth<1 || mth>12)
            {
          		document.forms[0].validateFlag.value=" Month should be greater than 0 and \n lessthan 13  "+mth+" ";
          		allow=false;
          	}
          }
          



         
          

         if(dd[2].length==4){
          if((parseInt(dd[2])<=parseInt(date.getYear()))){
          
                        
                        
                        
          }
          else{
          document.forms[0].validateFlag.value=" Year should be less than "+date.getYear()+" ";
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
		document.forms[0].panelChqDate.focus();
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
<body class="MainBody" onload="getAlertMessages()">

<center><h2 class="h2"><bean:message key="label.OutStation"/></h2></center>
<html:form  action="/Clearing/OutStationChequeLink?pageId=7005">
				
				<html:hidden property="pageId" ></html:hidden>
				<html:hidden property="flag"/>
				<html:hidden property="booleanFlag"></html:hidden>
				<html:hidden property="form_flag"></html:hidden>
				<html:hidden property="errorFlag"></html:hidden>

<table>
	<tr>
	<td>
	<table>
		<tr><html:text property="validateFlag" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
	</table>
	</td>
	</tr>
	<tr>
	<td>	
	<table>		
	<tr>
		<td valign="top">	
		<table align="left"> 
		<tr>
		<td><bean:message key="label.controlno"></bean:message></td>
		<td><html:text property="ctrlNum"  onblur="setFlag('frmCtrlNum')" styleId="ctrl_no" size="16" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		</tr>
		<tr>
		<td><bean:message key="label.microcde"></bean:message></td>
		<td><html:text property="cityCode" readonly="true"  styleId="city_code" size="3" styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="bnkCode" readonly="true"  styleId="bank_code" size="3"  styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="brhCode" readonly="true" size="3" styleId="branch_code"  styleClass="formTextFieldWithoutTransparent"></html:text>
		<html:text property="bnkName" readonly="true" styleClass="formTextField" style="color:blue"></html:text>
		</td>
		</tr>
		<tr>
		<td><bean:message key="label.branch_name"></bean:message></td>
		<td><html:text property="brhName" readonly="true" styleClass="formTextFieldWithoutTransparent" size="16" style="color:blue" ></html:text></td>
		</tr>
		<tr>
		<td> <bean:message key="label.acctype"></bean:message></td>
		<td> <html:select property="crAccTyp"  styleClass="formTextFieldWithoutTransparent" styleId="credit_acc_type"  >
				<html:option value="select">select</html:option>
				<core:forEach var="module"  varStatus="count" items="${requestScope.Main_Module_code}" >
				<html:option value="${module.moduleCode}"><core:out value="${module.moduleAbbrv}"></core:out></html:option>
				</core:forEach>
				</html:select>
		 </td>
		 </tr>						 
		 <tr>
		 <td> <bean:message key="label.creditAcNum"></bean:message></td>
		 <td><html:text property="crAccNum" readonly="true" styleClass="formTextFieldWithoutTransparent" size="10" styleId="credit_acc_no"  ></html:text><html:text property="acName" styleClass="formTextField" readonly="true"></html:text>	</td>
		 </tr>
		 <tr>
		 <td><bean:message key="label.amount"/></td>
		 <td><html:text property="amount" readonly="true" onkeypress="return onlyDoublenumbers()" size="10" styleClass="formTextFieldWithoutTransparent" styleId="amt" ></html:text></td>
		 </tr>
		 
		 <tr>
		 <td><bean:message key="label.chqdate"/></td>
		 <td><html:text property="chqDate" readonly="true"  styleClass="formTextFieldWithoutTransparent"  size="10" styleId="chqdate" /></td>
		 </tr>
		 <tr>
		 <td><bean:message key="label.return"></bean:message></td>
		 <td><html:checkbox  property="returned" styleId="bounce_id" styleClass="formTextFieldWithoutTransparent" onclick="callBounce()"></html:checkbox></td>
		</tr>
		
		
		<tr>
		<td><bean:message key="label.bounceFine"></bean:message></td>
		<td><html:text property="bounceFine" styleClass="formTextField" style="color:red" readonly="true"></html:text></td>
		</tr>
		<tr>
		<td><html:button property="sub" onclick="setFlag('frmSubmit')" styleClass="ButtonBackGroundImage" value="submit"></html:button>
		 <html:button property="update" onclick="setFlag('frmUpdate')" styleClass="ButtonBackGroundImage" value="Update"></html:button></td>
		 <td><html:button property="delete" onclick="setFlag('frmDelete')" styleClass="ButtonBackGroundImage" value="Delete"></html:button>
		 <html:button property="clear" onclick="clearAll()" styleClass="ButtonBackGroundImage" value="Clear"></html:button></td>
		 </tr>
		</table>
		</td>
		<td valign="top">
		<table   style="border:thin solid #000000;">
			<tr>
				<td><bean:message key="label.microcde"></bean:message></td>
				<td><html:text property="panelCityCode" onkeypress="return numbersonly(this)" onfocus="clearfeild(this.id)" styleId="panel_city_code" size="3" styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="panelBnkCode" onkeypress="return numbersonly(this)" onfocus="clearfeild(this.id)" styleId="panel_bank_code" size="3"  styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="panelBrhCode" size="3" styleId="panel_branch_code" onkeypress="return numbersonly(this)" onfocus="clearfeild(this.id)" styleClass="formTextFieldWithoutTransparent" onblur="return submitMICR('frmMicrCode')"></html:text></td>
				<td><html:text property="panelBnkName" size="15" readonly="true" styleClass="formTextField" style="color:blue"></html:text></td>
				
			</tr>
			
			<tr>
				<td><bean:message key="label.branch_name"></bean:message></td>
				<td><html:text property="panelBrhName"  styleClass="formTextFieldWithoutTransparent" size="16" style="color:blue" ></html:text></td>
				
		 		<td><bean:message key="label.chqDdPoNo"/></td>
				<td><html:text property="panelChqDDNum" styleId="panel_chqddno" onkeypress="return onlynumbers()" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		 	</tr>
			
			<tr>
				<td><bean:message key="label.amount"/></td>
				 <td><html:text property="panelAmount" onkeypress="return onlyDoublenumbers()" size="16" styleClass="formTextFieldWithoutTransparent" styleId="panel_amt" ></html:text></td>
		 
		 		<td><bean:message key="label.pocomm"/></td>
				<td><html:text property="commission" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlyDoublenumbers()" styleId="comm"></html:text></td>
		 	</tr>
			
			<tr>
				<td><bean:message key="label.chqdate"/></td>
				<td><html:text property="panelChqDate"  styleClass="formTextFieldWithoutTransparent"  size="16" styleId="panel_chqdate" onblur="checkformat(this)" /></td>
				
				<td><bean:message key="label.earlier"></bean:message></td>
				<td><html:text property="prevCtrlNum" size="10" readonly="true" styleId="prev_ctrlno" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		
			</tr>
				
		</table>
				
		<div id="bounce_fine" style="display:none;overflow:scroll;width:310px;height:100px;">
			<table border="1" style="border:thin solid #000000;">
			<tr><td>ResCode</td><td>Description</td><td>FineAmt</td><td>Select</td></tr>
				
				<%Reason[] reason=(Reason[])request.getAttribute("Reason"); %>
			<% 
			for(int i=0;i<reason.length;i++)
				{ %>
				
				<tr>
				<td><html:text property="txtReasonCode" size="6" value="<%=""+reason[i].getReasonCd()%>" ></html:text></td>
				<td><html:text property="txtDesription"  value="<%=""+reason[i].getReasonDesc()%>" ></html:text></td>	
				<td><html:text property="txtBounceFine" size="5" value="<%=""+reason[i].getBounceFine()%>" ></html:text></td>
				<td><html:checkbox property="chkBox"  value="<%=""+i%>"/></td>
				</tr>
			<%} %>
					
		</table>
 	</div>
 			<% 
 				String jspPath=(String)request.getAttribute("flag");
				System.out.println("'m inside panel"+jspPath);
						        
				if(jspPath!=null)
				{
					System.out.println("wen 'm  null");
            %>
			<table  style="border:thin solid #000000;">
 		 	<tr>
 			<td>
 			<jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
           	<%}  else{	%>
            <jsp:include page="/LockerWebPages/blank.jsp"></jsp:include>
            <%} %>	
 			</td>
 		</tr>
 	</table>		
						
 	</td>
 	</tr>
			
		</table>
			</td>
			</tr>
			</table>
				
	</html:form>
</body>
</html>