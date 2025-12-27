
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="c"    uri="/WEB-INF/c-rt.tld" %>

<html>
<head>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
</style>


    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
   
<script type="text/javascript">

function setExtend(target)
{
	if(document.forms[0].receiptDetails.value=="C")
	{
							
		if(document.forms[0].scrollNum.value=="")
		{
			document.forms[0].validateFlag.value="Enter Valid Scroll Number";
			document.forms[0].scrollNum.focus(); 
		}
		else if(document.forms[0].scrollNum.value==0)
		{
			document.forms[0].validateFlag.value="Enter Valid Scroll Number";
			document.forms[0].scrollNum.focus(); 
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
			
			document.forms[0].validateFlag.value="Enter Valid A/c Number";
			document.forms[0].transferAcntNum.focus();
		}
		else if(document.forms[0].transferAcntNum.value==0)
		{
			
			document.forms[0].validateFlag.value="Enter Valid A/c Number";
			document.forms[0].transferAcntNum.focus();
		}
		else
		{
			document.forms[0].forward.value=target;
			document.forms[0].submit();
		}
								
	}
			
} 

function set(target)
{
	 if(document.forms[0].txt_acNo.value=='')
	 {
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";	
		document.forms[0].txt_acNo.focus(); 
	 }
	 else if(document.forms[0].txt_acNo.value==0)
	 {
	 	document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
	 	document.forms[0].txt_acNo.focus();
	 }
	 else if(document.forms[0].txt_extnMonths.value=='') 
	 {
	 	document.forms[0].validateFlag.value="Enter Valid Number Of Months";
	 }
	 else if(document.forms[0].txt_extnDays.value=='') 
	 {
	 	document.forms[0].validateFlag.value="Enter Valid Number Of Days";
	 }
	 else if(document.forms[0].txt_extnDays.value==0) 
	 {
	 	document.forms[0].validateFlag.value="Enter Valid Number Of Days";
	 }
	 else if(document.forms[0].receiptDetails.value=='Select')
	 {
	 	document.forms[0].validateFlag.value="Enter Receipt Details";
	 }
	 else if(document.forms[0].receiptDetails.value=='C')
	 {
	 	if(document.forms[0].scrollNum.value=='')
	 	{
	 		document.forms[0].validateFlag.value="Enter Valid Scroll Number";
	 		document.forms[0].scrollNum.focus();
	 	}
	 	else if(document.forms[0].scrollNum.value==0)
	 	{
	 		document.forms[0].validateFlag.value="Enter Valid Scroll Number";
	 		document.forms[0].scrollNum.focus();
	 	}
	 	else
	 	{
	 	    document.forms[0].forward.value=target;
			document.forms[0].submit();
	 	}
	 }
	 else if(document.forms[0].receiptDetails.value=='T')
	 {
	 	if(document.forms[0].transferAcntNum.value=='')
	 	{
	 		document.forms[0].validateFlag.value="Enter Valid A/c Number";
	 		document.forms[0].transferAcntNum.focus();
	 	}
	 	else if(document.forms[0].transferAcntNum.value==0)
	 	{
	 		document.forms[0].validateFlag.value="Enter Valid A/c Number";
	 		document.forms[0].transferAcntNum.focus();
	 	}
	 	else
	 	{
	 	    document.forms[0].forward.value=target;
			document.forms[0].submit();
	 	}
	 }
	 
	 
};


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
 


//fun 1
            function validate(txt,id)
            {
                if(txt.length==0)
           		{  
           		  document.forms[0].validateFlag.value="Please Enter=="+id+" ";  
           		}             
           	};
           
           
//To validate all fields
           function validate1()
           {
           		if((document.forms[0].lockerDepth.value=="")||(document.forms[0].lockerHeight.value=="")||(document.forms[0].lockerLength.value=="")||(document.forms[0].lockerDepth.value==""))
           		{ 
           		 document.forms[0].validateFlag.value="Some Fields Are Null";  
           		 }
           }
           
 
 function setFlag(flagVal)
{
	if(document.forms[0].txt_acNo.value=='')
	 {
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";	
		document.forms[0].txt_acNo.focus(); 
	 }
	 else if(document.forms[0].txt_acNo.value==0)
	 {
	 	document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
	 	document.forms[0].txt_acNo.focus();
	 }
	else if(document.forms[0].acc_no.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Number";
	}
	else
	{
		document.forms[0].forward.value=flagVal;
		document.forms[0].submit();
	}
}           
// To clear Forms           
           function callClear()
           {
           		 var fields= document.forms[0].elements;
           		 for(var i=0;i<fields.length;i++)
           		 {
               		if(fields[i].type=="text")
               		{
                  		fields[i].value="";
               		}
           		}
           
           }
           
//to display alerts
    function callMe()
    {
          getReceiptFrame();
          depShow();
           
    	///   if((document.forms[0].successFlag.value="")||(document.forms[0].successFlag.value==null)||(document.forms[0].validateFlag.value=="") ||(document.forms[0].validateFlag.value=="0"))
   		 ///   {
    	///return false;
   		///    }
   		///    else
   		///    {
   		///       alert(document.forms[0].successFlag.value+""+document.forms[0].validateFlag.value);
   		///       return true;
   		///    }
          
    }
     
    function setDate(flagVal)
    {
    	if(document.forms[0].txt_acNo.value=='')
    	{
    		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
    		document.forms[0].txt_acNo.focus();
    	}
    	else if(document.forms[0].txt_rentUpto.value=='')
    	{
    		document.forms[0].validateFlag.value="Enter Value Rent Collected UpTo Field";
    		document.forms[0].txt_rentUpto.focus();
    	}
    	else if(document.forms[0].txt_extnMonths.value=='')
    	{
    		document.forms[0].validateFlag.value="Enter Number Of Months";
    		document.forms[0].txt_extnMonths.focus();
    	}
    	else if(document.forms[0].txt_extnDays.value=='')
    	{
    		document.forms[0].validateFlag.value="Enter Number Of Days";
    		document.forms[0].txt_extnDays.focus();
    	}
    	else
    	{
    		document.forms[0].forward.value=flagVal;
    		document.forms[0].submit();
    	}
    
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
				///alert("inside cash");
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
		
		//validate transfer account numbers
function lknum(flagVal)
{
	 if(document.forms[0].txt_acNo.value=='')
	 {
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";	
		document.forms[0].txt_acNo.focus(); 
	 }
	 else if(document.forms[0].txt_acNo.value==0)
	 {
	 	document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
	 	document.forms[0].txt_acNo.focus();
	 }
	 else
	 {
	 	document.forms[0].forward.value=flagVal;
	 	document.forms[0].submit();
	 }
			
}
		
function depShow()
{
	if(document.forms[0].chk.checked)
	{
		document.getElementById("labeldeptyp").style.display = 'block';
		document.getElementById("txtdeptyp").style.display = 'block';
		document.getElementById("txtdepacc").style.display = 'block';
		
	}
	else
	{
		document.getElementById("labeldeptyp").style.display = 'none';
		document.getElementById("txtdeptyp").style.display = 'none';
		document.getElementById("txtdepacc").style.display = 'none';
		
	}



}		
		
</script>

</head>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />

<body onload="callMe()" class="Mainbody">
<center><h2 class="h2">Locker Extension</h2></center> 
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
<html:form action="/LKExtensionLink?pageId=9002" >
<table  class="txtTable">
		<tr><html:text property="validateFlag" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
		<tr>
		<td valign="top">
        <table class="txtTable">
        <html:hidden property="flag" />       
		<html:hidden property="forward"></html:hidden>
		<html:hidden property="successFlag"></html:hidden>
             <tr>
               		<td><bean:message key="lab.ac_type"></bean:message></td>
                    <td><html:select property="txt_acType" styleClass="formTextFieldWithoutTransparent">
               			<c:forEach var="lktypes" items="${requestScope.param}">
                      		<html:option value="${lktypes.moduleCode}"><c:out value="${lktypes.moduleAbbrv}"></c:out></html:option>
                		</c:forEach>
                		</html:select> 
                	</td>
               		<td><html:text property="txt_acNo"  styleClass="formTextFieldWithoutTransparent" onblur="lknum('AcNum')" size="8" onkeyup="numberlimit(this,'10')" onkeypress="return onlynumbers()"></html:text></td>
                    
                </tr>
          		
          		 <tr><td><bean:message key="lab.acType_num" ></bean:message></td>
                	<td><html:text property="txt_lockType"   size="10" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
                	<td><html:text property="txt_lockNo"  size="10" readonly="true" styleClass="formTextField"></html:text></td>
            	</tr>

            	<tr><td><bean:message key="lab.allotExpiry"></bean:message></td>
                	<td><html:text property="txt_allotDate"  size="10" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                	<td><html:text property="txt_expiryDate"  size="10" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
            	</tr>

            	<tr><td><bean:message key="lab.rent"></bean:message></td>
                	<td><html:text property="txt_rentUpto" size="10" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
            	</tr>

            	<tr><td><bean:message key="lab.totRent"></bean:message> </td>
                	<td><html:text property="txt_totRent" readonly="true"  size="10" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
            	</tr>

	         	<tr><td><bean:message key="lab.extnMonthsDays"></bean:message> </td>
                	<td><html:text property="txt_extnMonths" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'5')" size="10" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
                	<td><html:text property="txt_extnDays"  onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'8')" size="10" onblur="setDate('futureDate')" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
            	</tr>  
           
           		 <tr><td><bean:message key="lab.extndate"></bean:message> </td>
                	<td><html:text property="txt_extnDate" readonly="true" size="10" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
            	</tr>
           
           		 <tr><td><bean:message key="lab.rentAmt" ></bean:message> </td>
                	<td><html:text property="txt_rentAmnt" readonly="true" size="10" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
            	</tr>
            
         		<tr>
                	<td><bean:message key="lab.details"></bean:message> </td>
                	<td><html:select property="select_details" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
                    	<html:option value="Select">Select</html:option>
                    	<html:option value="personal">Personal</html:option>
                    	<html:option value="signature">Signature</html:option>
                    	<html:option value="Deposit">Deposit</html:option>
                		</html:select> 
                	</td>
            	</tr>
            
            	<tr><td><bean:message key="label.receipt"></bean:message></td>
            		<td>
            		<html:select property="receiptDetails"  styleClass="formTextFieldWithoutTransparent" onchange="getReceiptFrame()">
            		<html:option value="Select"></html:option>
            		<html:option value="C"><bean:message key="label.cash"></bean:message></html:option>
            		<html:option value="T"> <bean:message key="label.Transfer"></bean:message> </html:option>
            		</html:select>
            		</td>
            	</tr>
            	
       	
 				<tr id="cash1">
        	  	<td><bean:message key="label.scroll_no"></bean:message></td>
			 	<td><html:text property="scrollNum" onblur="setExtend('ScrollNum')" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent" size="10"></html:text>	</td> 
 				</tr> 
 				<tr id="cash2">
    			<td><bean:message key="label.date"></bean:message></td>
    		 	<td><html:text property="date" readonly="true" styleClass="formTextFieldWithoutTransparent" size="10"></html:text>    </td>
  				</tr> 
  				<tr id="cash3">  		 
    			<td><bean:message key="label.amount"></bean:message></td>
    		 	<td><html:text property="amount" styleClass="formTextFieldWithoutTransparent" readonly="true" size="10"></html:text>    </td>
				</tr>  
 				<tr id="transfer">
        	  	<td><bean:message key="label.trf_actype"></bean:message>-<bean:message key="label.trf_acno"></bean:message></td>
			 	
			 	<td><html:select property="transferAcntType" styleClass="formTextFieldWithoutTransparent">
			  	
			  	<c:forEach var="introtypes" items="${requestScope.transferTypes}">
              		<html:option value="${introtypes.moduleCode}" > <c:out value="${introtypes.moduleAbbrv}"> </c:out></html:option>
              	</c:forEach>
              	
			  	</html:select></td>
			  	
    		 	<td><html:text property="transferAcntNum" onblur="setExtend('TrnAcNum')" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" size="10"></html:text> </td> 	
 				</tr>

				<tr>
				<td><bean:message key="label.ques"></bean:message></td>
				<td><html:checkbox property="chk" styleClass="formTextFieldWithoutTransparent" onclick="depShow()"></html:checkbox>Yes/No</td>
				</tr>
			
				<tr>
					
				
				
				<td><div id="labeldeptyp" style="display:none;"><bean:message key="label.depacctyp"></bean:message></div></td>
				<td><div id="txtdeptyp" style="display:none;">
				
				<html:select  property="combo_details" styleClass="formTextFieldWithoutTransparent">
	     		<c:forEach var="depotypes" items="${requestScope.DepositTypes}">
	     			<html:option value="${depotypes.moduleCode}"><c:out value="${depotypes.moduleAbbrv}"> </c:out></html:option>
	    		</c:forEach>
	    		</html:select>
	    		
	    		</div>	
	    		
	    		</td>
	    		<td><div id="txtdepacc" style="display:none;"><html:text property="acc_no" size="8" styleClass="formTextFieldWithoutTransparent" onblur="setFlag('depositnum')" onkeypress="return onlynumbers()" ></html:text></div></td>
				
				</tr>
        
        		<tr>
        		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <td><html:button property="sub" onclick="set('extend')" styleClass="ButtonBackgroundImage"><bean:message key="label.extend"></bean:message>  </html:button>
             <%}else{ %>
             <td><html:button property="sub" disabled="true" onclick="set('extend')" styleClass="ButtonBackgroundImage"><bean:message key="label.extend"></bean:message>  </html:button>
             <%} %>
        		
        		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
            <html:button property="del" onclick="set('delete')" styleClass="ButtonBackgroundImage"><bean:message key="label.delete" ></bean:message>  </html:button>
             <%}else{ %>
            <html:button property="del" onclick="set('delete')" disabled="true" styleClass="ButtonBackgroundImage"><bean:message key="label.delete" ></bean:message>  </html:button>
             <%} %>
            	
            	<html:button property="clearButton" onclick="callClear()" styleClass="ButtonBackgroundImage"><bean:message key="label.clear" ></bean:message></html:button> 
            	</td>      
	        	</tr>
          
       </table>     
</td>
<td align="left">
      	
            <%  
           System.out.println("@@@@ From Tab PAn@@@"); 
           String jspPath=(String)request.getAttribute("flag");
           
            
            if(jspPath!=null){
            	System.out.println("wen 'm  null");
            %>
            <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
            
            <%}else{	%>
            
            <jsp:include page="/LockerWebPages/blank.jsp"></jsp:include>
            <%} %>
                     
</td>  
</tr>
 </table>      
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %> 
</body>   
</html>