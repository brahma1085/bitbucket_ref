<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="c"    uri="/WEB-INF/c-rt.tld" %>


<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.lockers.LockerMasterObject"%>
<%@page import="masterObject.general.AccountObject"%>




<html>
<head>



<script type="text/javascript">

function set(target)
{
	if(document.forms[0].txt_acNo.value!='')
	{
		document.forms[0].forward.value=target;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
		document.forms[0].txt_acNo.focus();
	}
};

function onlynumbers()
 {
       var cha=event.keyCode;
		if(cha>=48 && cha<=57)
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
           		
           		
    ///      		if((document.forms[0].successFlag.value="")||(document.forms[0].successFlag.value==null)||(document.forms[0].validateFlag.value=="") ||(document.forms[0].validateFlag.value=="0"))
    ///      		{
    ///       			return false;
    ///       		}
    ///       		else
    ///       		{
    ///      			alert(document.forms[0].successFlag.value+""+document.forms[0].validateFlag.value);
    ///       			return true;
    ///       		}
          
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
function validateAccountNumber()
{
			document.forms[0].flag.value="1";
			document.forms[0].submit();
}
		
function setDetails(target)
{
	if(document.forms[0].txt_acNo.value!='')
	{
		document.forms[0].forward.value=target;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
		document.forms[0].txt_acNo.focus();
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
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />



</head>

<body onload="callMe()" class="Mainbody">
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
<html:form action="/lockers/LockerExtensionVerificationLink?pageId=9032">

<center><h2 class="h2">Locker Extension Verification</h2></center> 

   <%! 			   
   		ModuleObject[] arraymodobj; 
		LockerMasterObject lockermasterobject;	 
		String[] rentFutureDate;
		String[] monthsDays;
		AccountObject accountObject;
   %>
     
<table>

<tr><html:text property="validateFlag" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
<tr>
<td>
        <table class="txtTable">
                <tr><td><bean:message key="lab.ac_type"></bean:message></td>
                    <td><html:select property="txt_acType" styleClass="formTextFieldWithoutTransparent">
                	<%arraymodobj=(ModuleObject[])request.getAttribute("param");
                		for(int i=0;i<arraymodobj.length;i++){               
 					%> 
                	<html:option value="<%=""+arraymodobj[i].getModuleCode()%>"><%=""+arraymodobj[i].getModuleAbbrv()%></html:option>
                	<%} %>
                	</html:select> 
                	</td>
               
                	<td><html:text property="txt_acNo"  styleClass="formTextFieldWithoutTransparent" onblur="set('LockerNo')" size="10" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'10')"></html:text></td>
                    
                </tr>
        
        	    <tr><td><bean:message key="lab.acType_num" ></bean:message></td>
            	    <td><html:text property="txt_lockType" readonly="true"  size="10"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
               		<td><html:text property="txt_lockNo" readonly="true" size="10"  styleClass="formTextField"></html:text></td>
            	</tr>

            	<tr><td><bean:message key="lab.allotExpiry"></bean:message></td>
                	<td><html:text property="txt_allotDate" readonly="true" size="10"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
                	<td><html:text property="txt_expiryDate" readonly="true" size="10"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
            	</tr>

            	<tr><td><bean:message key="lab.rent"></bean:message></td>
                	<td><html:text property="txt_rentUpto" readonly="true" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
            	</tr>

            	<tr><td><bean:message key="lab.totRent"></bean:message> </td>
                	<td><html:text property="txt_totRent" readonly="true"  size="10" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
            	</tr>

	         	<tr><td><bean:message key="lab.extnMonthsDays"></bean:message> </td>
                	<td><html:text property="txt_extnMonths" readonly="true" size="10" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
                	<td><html:text property="txt_extnDays" readonly="true"  size="10" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
           	   </tr>  
           

           		<tr><td><bean:message key="lab.extndate"></bean:message> </td>
                	<td><html:text property="txt_extnDate" readonly="true" size="10" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
            	</tr>
           
				<tr><td><bean:message key="lab.rentAmt" ></bean:message> </td>
                	<td><html:text property="txt_rentAmnt" readonly="true"  size="10" styleClass="formTextFieldWithoutTransparent" ></html:text> </td>
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
			 	<td><html:text property="scrollNum" readonly="true"  styleClass="formTextFieldWithoutTransparent" size="10"></html:text>	</td> 
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
        	  	<td><bean:message key="label.trf_actype"></bean:message>--<bean:message key="label.trf_acno"></bean:message></td>
			 	
			 	<td><html:select property="transferAcntType" styleClass="formTextFieldWithoutTransparent">
			  	
			  	<c:forEach var="introtypes" items="${requestScope.transferTypes}">
              		<html:option value="${introtypes.moduleCode}" > <c:out value="${introtypes.moduleAbbrv}"> </c:out></html:option>
              	</c:forEach>
              	
			  	</html:select></td>
			  	
    		 	<td><html:text property="transferAcntNum" readonly="true" styleClass="formTextFieldWithoutTransparent"  size="10"></html:text> </td> 	
 				</tr>
 				
 				
           		 <tr>
                	<td><bean:message key="lab.details"></bean:message> </td>
               		 <td><html:select property="select_details" styleClass="formTextFieldWithoutTransparent" onchange="setDetails('LockerNo')">
                    <html:option value="personal">Personal</html:option>
                    <html:option value="signature">signature</html:option>
                    <html:option value="Deposit">Deposit</html:option>
                	</html:select> </td>
            	</tr>
           	
  

			<html:hidden property="flag"></html:hidden>       
			<html:hidden property="forward" value="error"></html:hidden>
			<html:hidden property="successFlag"></html:hidden>
			
       
        <tr>
        <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <td><html:button property="ver" onclick="set('verify');" styleClass="ButtonBackgroundImage"><bean:message key="label.verify"></bean:message>  </html:button>
             <%}else{ %>
            <td><html:button property="ver" disabled="true" onclick="set('verify');" styleClass="ButtonBackgroundImage"><bean:message key="label.verify"></bean:message>  </html:button>
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
           System.out.println("'m inside panel"+jspPath);
            
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