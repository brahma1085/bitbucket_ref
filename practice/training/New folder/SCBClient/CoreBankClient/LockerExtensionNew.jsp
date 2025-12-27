
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html"  uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean"  uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="c"     uri="/WEB-INF/c-rt.tld"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.lockers.LockerMasterObject"%>
<%@page import="masterObject.general.AccountObject"%>

<html>
<head>

<script type="text/javascript">
function set(target){
document.forms[0].forward.value=target;
};
//fun 1
            function validate(txt,id){
                      if(txt.length==0)
           {  alert("plz enter=="+id)  }             };
           
           
//To validate all fields
           function validate1(){
           if((document.forms[0].lockerDepth.value=="")||(document.forms[0].lockerHeight.value=="")||(document.forms[0].lockerLength.value=="")||(document.forms[0].lockerDepth.value==""))
           { alert("Some fields are null");  }
           }
           
           
// To clear Forms           
           function callClear(){
           
          var fields= document.forms[0].elements;
           for(var i=0;i<fields.length;i++){
               if(fields[i].type=="text"){
                  
               fields[i].value="";
               }
           
           }
           
           }
           
//to display alerts
           function callMe(){
           
          
           
           if((document.forms[0].successFlag.value="")||(document.forms[0].successFlag.value==null)||(document.forms[0].validateFlag.value=="") ||(document.forms[0].validateFlag.value=="0")){
           return false;
           }
           else{
          
           alert(document.forms[0].successFlag.value+""+document.forms[0].validateFlag.value);
           return true;
           }
          
           getReceiptFrame();
           }
           
//changing according to receipt

		function getReceiptFrame(){
		alert(document.forms[0].receiptDetails.value);
		if(document.forms[0].receiptDetails.value=="Select"){
		
		document.getElementById('cash1').style.display='none';
		document.getElementById('cash2').style.display='none';
		document.getElementById('cash3').style.display='none';
		
		document.getElementById('transfer').style.display='none';
		}
		
		if(document.forms[0].receiptDetails.value=="Cash"){
		alert("inside cash");
		document.getElementById('cash1').style.display='block';
		document.getElementById('cash2').style.display='block';
		document.getElementById('cash3').style.display='block';
		
		document.getElementById('transfer').style.display='none';
		}
	
		if(document.forms[0].receiptDetails.value=="Transfer"){
		document.getElementById('cash1').style.display='none';
		document.getElementById('cash2').style.display='none';
		document.getElementById('cash3').style.display='none';
		
		document.getElementById('transfer').style.display='block';
		}
		
		}
		
		//validate transfer account numbers
		function validateAccountNumber(){
		
		document.forms[0].flag.value="1";
		document.forms[0].submit();
		
		}
		
		
		
</script>
 <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
</head>

<body onload="callMe()" >


<table>

<html:form action="/lockers/LockerExtensionVerificationLink?pageId=9034" >
<td>
        <table class="txtTable">
               <%! ModuleObject[] arraymodobj; 
				   LockerMasterObject lockermasterobject;	 
				   String[] rentFutureDate;
				   String[] monthsDays;
				   AccountObject accountObject;
				   
               %>
     
               <% accountObject=(AccountObject)request.getAttribute("trnsferDetails"); %>
               
               
                <tr><td><bean:message key="lab.ac_type"></bean:message></td>
                    <td><html:select property="txt_acType">
                	<%arraymodobj=(ModuleObject[])request.getAttribute("param");
                		for(int i=0;i<arraymodobj.length;i++){               
 					%> 
                	<html:option value="<%=""+arraymodobj[i].getModuleCode()%>"><%=""+arraymodobj[i].getModuleAbbrv()%></html:option>
                	<%} %>
                	</html:select> </td> 
                	<td><html:text property="txt_acNo"  styleClass="formTextFieldWithoutTransparent" onblur="submit()" size="8" ></html:text></td>    
                </tr>
      		
				<%  lockermasterobject=(LockerMasterObject)request.getAttribute("lkparams");
				    if(lockermasterobject!=null) { 
					System.out.println("*****MoonLight******* ");		
				%>	               
          	  <tr><td><bean:message key="lab.acType_num"></bean:message></td>
                <td><html:text property="txt_lockType"  readonly="true" value="<%=lockermasterobject.getLockerType()%>" onchange="submit()" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
                <td><html:text property="txt_lockNo"  readonly="true" value="<%=""+lockermasterobject.getLockerNo()%>" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
              </tr>

            <tr><td><bean:message key="lab.allotExpiry"></bean:message></td>
                <td><html:text property="txt_allotDate"  readonly="true" value="<%=lockermasterobject.getAllotDate() %>" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
              
                <td><html:text property="txt_expiryDate"  readonly="true" value="<%=lockermasterobject.getRentUpto() %>" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>   
               
            </tr>

            <tr><td><bean:message key="lab.rent"></bean:message></td>
                <td><html:text property="txt_rentUpto"  readonly="true" value="<%=lockermasterobject.getRentUpto() %>" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
            </tr>

			

            <tr><td><bean:message key="lab.totRent"></bean:message></td>
                <td><html:text property="txt_totRent"  readonly="true" value="<%=""+lockermasterobject.getRentColl() %>"  size="8" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
            </tr>

          	
          
          
           <tr><td><bean:message key="lab.extnMonthsDays"></bean:message> </td>
                <td><html:text property="txt_extnMonths" styleClass="formTextFieldWithoutTransparent"  size="8"></html:text> </td>
                <td><html:text property="txt_extnDays"   styleClass="formTextFieldWithoutTransparent" onblur="submit()" size="8"></html:text>  </td>
            </tr>  
           
 			
                     
            <tr><td><bean:message key="lab.extndate"></bean:message> </td>
                <td><html:text property="txt_extnDate" value="<%=lockermasterobject.getRentUpto() %>"  size="8" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
            </tr>
           
           <tr><td><html:text property="transferAcntNum" value="<%=""+lockermasterobject.getTransAcNo()%>" styleClass="formTextFieldWithoutTransparent"></html:text>
        	</td></tr> 
		     <tr><td> <%=lockermasterobject.getTransAcType() %> </td></tr>     
		     <tr><td> <%=lockermasterobject.getRentBy() %> </td></tr>
			
           <% } else { System.out.println("we@@@@@@@ null");%>
           
           <tr><td><bean:message key="lab.acType_num" ></bean:message></td>
                <td><html:text property="txt_lockType" value="" onchange="submit()" onblur="validate(this.value,'LockerType')" size="8"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
                <td><html:text property="txt_lockNo" value="" size="8"  styleClass="formTextField"></html:text></td>
            </tr>

            <tr><td><bean:message key="lab.allotExpiry"></bean:message></td>
                <td><html:text property="txt_allotDate" value="" size="8"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
                <td><html:text property="txt_expiryDate" value="" size="8"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
            </tr>

            <tr><td><bean:message key="lab.rent"></bean:message></td>
                <td><html:text property="txt_rentUpto" value="" size="8" styleClass="formTextFieldWithoutTransparent"></html:text></td>
            </tr>

            <tr><td><bean:message key="lab.totRent"></bean:message> </td>
                <td><html:text property="txt_totRent" value=""  size="8" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
            </tr>

	         <tr><td><bean:message key="lab.extnMonthsDays"></bean:message> </td>
                <td><html:text property="txt_extnMonths" value="0" onchange="submit()" size="8" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
                <td><html:text property="txt_extnDays"  value="0" size="8" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
            </tr>  
           

           
            <tr><td><bean:message key="lab.extndate"></bean:message> </td>
                <td><html:text property="txt_extnDate" value="0" size="8" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
            </tr>
           

             
         
            <tr><td><bean:message key="lab.rentAmt" ></bean:message> </td>
                <td><html:text property="txt_rentAmnt" value="0"  size="8" ></html:text> </td>
            </tr>
            
            
            
            <% }%>
            

            
            <tr>
                <td><bean:message key="lab.details"></bean:message> </td>
                <td><html:select property="select_details" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
                    <html:option value="personal">Personal</html:option>
                    <html:option value="signature">signature</html:option>
                    <html:option value="Receipt">Receipt</html:option>
                    <html:option value="Deposit">Deposit</html:option>
                </html:select> </td>
            </tr>
            
            <tr><td>Receipt:</td>
            	<td>
            	<html:text property="receiptDetails" styleClass="formTextFieldWithoutTransparent"></html:text>
            	</td></tr>
            	
  

<html:hidden property="flag" />       
<html:hidden property="forward" value="error"></html:hidden>
<html:hidden property="successFlag"></html:hidden>
<html:hidden property="validateFlag"></html:hidden>
        <tr>
        	
        	<td><html:submit onclick="set('verify');" styleClass="ButtonBackgroundImage"><bean:message key="label.verify"></bean:message>  </html:submit>
            <html:submit onclick="set('delete');" styleClass="ButtonBackgroundImage"><bean:message key="label.delete" ></bean:message>  </html:submit>
            <html:button property="clearButton" onclick="callClear()" styleClass="ButtonBackgroundImage"><bean:message key="label.clear" ></bean:message></html:button> 
            </td>      
	        
            
      </tr>
       </table>     
</td>       
</html:form>

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
</table>     
</body>
</html>