<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.lockers.LockerMasterObject"%>
<%@page import="masterObject.general.AccountObject"%>
<html>
<head>

<script type="text/javascript"> 

 function getTable()
 {
 
  var url = "LKTableMenuLink.do?pageId=9007";   		
  window.open( url,'Popup','WIDTH=500,HEIGHT=380,left=200,top=100,scrollbars=yes');
  
 }
function callClear()
 {
           
          var ele= document.forms[0].elements;
           
           for(var i=0;i<ele.length;i++)
           {
               if(ele[i].type=="text")
               {
                  ele[i].value="";
               }
           
           }
           
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

function onlynumbers()
 {
       var cha = event.keyCode;
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



function setForward(forwardVal)
{
	if(document.forms[0].lkacNum.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
		document.forms[0].lkacNum.focus();
	}
	else
	{	
		document.forms[0].forward.value=forwardVal;
		document.forms[0].submit();
	}
}  
  
function set(forwardVal)
{

document.forms[0].forward.value=forwardVal;

} 
function setNom(fv)
{
	if(document.forms[0].nomineeCid.value!='')
	{
		document.forms[0].forward.value=fv;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Nominee Cid";
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

function getAlertMessages()
{
	setJoint();
	setNominee();
}

function setFlag(flagVal)
{

	if(document.forms[0].lkacNum.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
		document.forms[0].lkacNum.focus();
	}
	else if(document.forms[0].opInstr.value!="Individual")
	{
		if(document.forms[0].cid.value=='')
		{
			document.forms[0].validateFlag.value="Enter Valid Cid";
			document.forms[0].cid.focus();
		}
		else
		{
			document.forms[0].forward.value=flagVal;
			document.forms[0].submit();
		}
	}
	
}  

function setDepVal(flagVal)
{

	if(document.forms[0].lkacNum.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Locker A/c Number";
		document.forms[0].lkacNum.focus();
	}
	else if(document.forms[0].acc_no.value=='')
	{
		document.forms[0].validateFlag.value="Enter Valid Deposit A/c Number";
		
	}
	else if(document.forms[0].acc_no.value==0)
	{
		document.forms[0].validateFlag.value="Enter Valid Deposit A/c Number";
		
	}
	else
	{
		document.forms[0].forward.value=flagVal;
		document.forms[0].submit();
	}
	
	
}  

function setDetails()
{

	if(document.forms[0].details.value=='Personal')
	{
		if(document.forms[0].txt_cid.value=='')
		{
			document.forms[0].validateFlag.value="Enter Valid Cid";
			document.forms[0].txt_cid.focus();
		}
		else
		{
			document.forms[0].submit();
		}
	}
	else if(document.forms[0].details.value=='Introducer')
	{
		if(document.forms[0].intrNum.value=='')
		{
			document.forms[0].validateFlag.value="Enter Valid Introducer Number";
			document.forms[0].intrNum.focus();
		}
		else
		{
			document.forms[0].submit();
		}
	}
	else if(document.forms[0].details.value=='Signature')
	{
		if(document.forms[0].lkacNum.value=='')
		{
			document.forms[0].validateFlag.value="Enter Locker A/c Number";
			document.forms[0].lkacNum.focus();
		}
		else
		{
			document.forms[0].submit();
		}
	}
	else if(document.forms[0].details.value=='JointHolders')
	{
		if(document.forms[0].cid.value=='')
		{
			document.forms[0].validateFlag.value="Enter Joint Holder Cid";
			
		}
		else
		{
			document.forms[0].submit();
		}
	}
	else if(document.forms[0].details.value=='Deposit')
	{
		if(document.forms[0].acc_no.value=='')
		{
			document.forms[0].validateFlag.value="Enter Deposit A/c Number";
			document.forms[0].acc_no.focus();
		}
		else
		{
			document.forms[0].submit();
		}
	}
	else if(document.forms[0].details.value=='Nominee')
	{
		document.forms[0].submit();
	}
	 
}

</script>
 <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
</head>
 <body class="Mainbody" onload="getAlertMessages()">
  <center><h2 class="h2">Locker Master Update</h2></center>
  
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
  <html:form action="/LKMaserUpdateLink?pageId=9020">
  
        <%! ModuleObject array_module;
            ModuleObject[] modobj;
            ModuleObject[] modobjIntro;
            ModuleObject[] deposittypes;
            LockerMasterObject lockermasterobject;
            String[] rentExpirydate;
            String Nominee="";
         %> 
  	<%  rentExpirydate = (String[]) request.getAttribute("rentExpirydate");  %> 
	<%!LockerMasterObject acntDetails; %>
	<% acntDetails=(LockerMasterObject)request.getAttribute("LockerDetails"); %>
	<%deposittypes=(ModuleObject[])request.getAttribute("DepositTypes"); %>
	
 
<table class="txtTable">
<tr><html:text property="validateFlag" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
<tr>
<td>

<table class="txtTable">
      <tr>
      <td><bean:message key="lab.ac_type"></bean:message> </td>
      <td><html:select property="lkacType" styleClass="formTextFieldWithoutTransparent">
      		<c:forEach var="parm" items="${requestScope.param}"> 
      			<html:option value="${parm.moduleCode}"><c:out value="${parm.moduleAbbrv}"></c:out></html:option>
      		</c:forEach>
      	  </html:select>
      </td>
	  <td><html:text property="lkacNum" size="8" styleClass="formTextFieldWithoutTransparent" styleId="lkacNumber" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'10')"  onblur="setForward('frmAcntNum')"></html:text> </td>
      </tr>
	  <tr>
        <td><bean:message key="label.cid"></bean:message> </td>
        <td><html:text property="txt_cid" size="8" readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
      </tr>
 
	   <tr>
	   
	   <td><bean:message key="label.tabView"></bean:message> </td>
	   <td><html:button property="tabView" value="LockerTable" styleClass="ButtonBackgroundImage" ></html:button></td>
	   
	   </tr> 

       <tr><td><bean:message key="lab.acType_num"></bean:message></td>

		<td><html:select property="lkType" styleClass="formTextFieldWithoutTransparent">
			<c:forEach var="lktypes" items="${requestScope.lktypes}">              
             <html:option value="${lktypes}" > <c:out value="${lktypes}"></c:out>  </html:option>
			</c:forEach>
			</html:select>
		</td>
		<td><html:text property="lkNum" size="8" readonly="true" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent" ></html:text> </td>
    	
      </tr>
          
      <tr><td><bean:message key="label.keyNo&Rent"></bean:message> </td>
         <td><html:text property="lkKey" size="8" readonly="true" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
      </tr>
           

      <tr><td>Period:Months - Days</td>
          <td><html:text property="months" readonly="true" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()"  size="8"></html:text> </td>
          <td><html:text property="days" readonly="true" styleClass="formTextFieldWithoutTransparent"  onkeypress="return onlynumbers()"   size="8"></html:text> </td>
          </tr>

    
    
       <tr><td><bean:message key="lab.allotExpiry"></bean:message> </td>
           <td><html:text styleClass="formTextFieldWithoutTransparent" property="allotDate" readonly="true" size="10"></html:text> </td>
  			 <td><html:text styleClass="formTextFieldWithoutTransparent" property="expDate" readonly="true" size="10"></html:text> </td>
        </tr>
        
       <tr>
              <td><bean:message key="label.rentAmount"></bean:message> </td>
              <td><html:text styleClass="formTextFieldWithoutTransparent" property="lkRent" onkeypress="return onlynumbers()" readonly="true" size="8"></html:text> </td>
      </tr>
      <tr>
      	<td>Receipt:</td>
      	<td><html:text property="receiptDetails" styleClass="formTextFieldWithoutTransparent" readonly="true" size="8"></html:text></td>
      </tr>
<%if(acntDetails!=null){

	if(acntDetails.getRentBy().equalsIgnoreCase("C")){
%>            
	<tr><td><bean:message key="label.scroll_no"></bean:message></td>	      
	<td><html:text property="transferAcNum" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=String.valueOf(acntDetails.getTransAcNo())%>" onkeypress="return onlynumbers()" size="8"></html:text></td>
	</tr>
<%}else if(acntDetails.getRentBy().equalsIgnoreCase("T")){ %>	
	<tr>
	<td> TranferA/c From:</td>
	<td>
	 	
			  <html:select property="transferAcType" styleClass="formTextFieldWithoutTransparent"  style="border:thin solid blue">
              <c:forEach var="introtypes" items="${requestScope.transfertypes}">
              <html:option value="${introtypes.moduleCode}" > <c:out value="${introtypes.moduleAbbrv}"> </c:out></html:option>
              </c:forEach>
              </html:select>
	</td>
	<td>
	<html:text property="transferAcNum" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=String.valueOf(acntDetails.getTransAcNo())%>" onkeypress="return onlynumbers()" size="8"></html:text>
	</td>
	</tr>
<%} }else{ %>
	<tr>
	<td> TranferA/c From:</td>
	<td>
	 	
			  <html:select property="transferAcType" styleClass="formTextFieldWithoutTransparent"  style="border:thin solid blue">
              <c:forEach var="introtypes" items="${requestScope.transfertypes}">
              <html:option value="${introtypes.moduleCode}" > <c:out value="${introtypes.moduleAbbrv}"> </c:out></html:option>
              </c:forEach>
              </html:select>
	</td>
	<td>
	<html:text property="transferAcNum" styleClass="formTextFieldWithoutTransparent" readonly="true" onkeypress="return onlynumbers()" size="8"></html:text>
	</td>
	</tr>
<%} %>	
      <tr><td><bean:message key="lab.operatingInstr"></bean:message> </td>
               <td><html:select property="opInstr" styleClass="formTextFieldWithoutTransparent" onchange="setJoint()">
                               <html:option value="Individual"></html:option>
                               <html:option value="E or S"></html:option>
                               <html:option value="Jointly"></html:option>
               </html:select></td>
           </tr>

           <tr><td><bean:message key="label.membNo"></bean:message> </td>
              <td><html:select property="membType" styleClass="formTextFieldWithoutTransparent">
              <html:option value="1001001">SH</html:option>
              </html:select></td>

           	<td><html:text styleClass="formTextFieldWithoutTransparent" property="membAc" readonly="true" onkeypress="return onlynumbers()"  size="8"></html:text> </td>              

          </tr>

           <tr><td><bean:message key="label.Intro"></bean:message>   </td>
           
             <td><html:select property="intrType" styleClass="formTextFieldWithoutTransparent">
            	  <c:forEach var="introtypes" items="${requestScope.introtypes}">
                     <html:option value="${introtypes.moduleCode}" > <c:out value="${introtypes.moduleAbbrv}"> </c:out></html:option>
              	  </c:forEach>
                 </html:select>
             </td> 
               
               
            <td><html:text styleClass="formTextFieldWithoutTransparent" property="intrNum" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'10')"  size="8" onblur="setForward('IntrNum')"></html:text> </td>
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
	    		
	    		<td><html:text property="acc_no" size="8" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'10')" styleClass="formTextFieldWithoutTransparent" onblur="setDepVal('depositnum')" ></html:text></td>
			</tr>
          
			<tr>
				
                <td><div id="jointlabel" style="display:none;"><bean:message key="label.jointCid"></bean:message></div></td>
                <td><div id="jointtxt" style="display:none;"><html:text property="cid" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'10')" size="10" onblur="setFlag('jointcid')"></html:text></div></td>
            </tr>		
				
          <tr><td><bean:message key="label.nomReg"></bean:message> </td>
              <td><html:text property="nomReg" size="8" onkeypress="return onlynumbers()" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
          </tr>

          <tr><td><bean:message key="label.details"></bean:message> </td>
              <td><html:select property="details" styleClass="formTextFieldWithoutTransparent" onblur="setDetails()">
              	 <html:option value="Select"></html:option>
              	  <html:option value="Personal"></html:option>
                  <html:option value="Introducer"></html:option>
                  <html:option value="Signature"></html:option>
                  <html:option value="Nominee"></html:option>
                  <html:option value="JointHolders"></html:option>
                  <html:option value="Deposit"></html:option>
              </html:select> </td>
          </tr>


          <tr><td><bean:message key="label.autoExtn"></bean:message> </td>
              <td><html:select property="autoExtn" styleClass="formTextFieldWithoutTransparent">
                  <html:option value="yes">YES</html:option>
                  <html:option value="no">NO</html:option>
              </html:select>

               </td>
          </tr>
          
        <tr> <td>
       <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.update"></bean:message>  </html:submit>
             <%}else{ %>
             <html:submit onclick="set('submit')" disabled="true" styleClass="ButtonBackgroundImage"><bean:message key="label.update"></bean:message>  </html:submit>
             <%} %>
   		
        <html:button property="buttonClear"  styleClass="ButtonBackgroundImage" onclick="callClear()"><bean:message key="label.clear" ></bean:message></html:button>

       </td>  </tr>
       <html:hidden property="forward"></html:hidden>
       <html:hidden property="flag"></html:hidden>

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
								<td><div id="txtcid" style="display:none;"><html:text property="nomineeCid" onkeypress="return onlynumbers()" size="5" styleClass="formTextFieldWithoutTransparent" onblur="setNom('nomineecid')"></html:text></div></td>
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
								<td><html:text property="nomineeAge" onkeypress="return onlynumbers()" styleId="nomAge" styleClass="formTextFieldWithoutTransparent"></html:text></td>
							    
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
      	
            <%    String jspPath = (String) request.getAttribute("flag");   %>
            
            <c:if  test="${requestScope.flag !=null}">
          		<jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
            </c:if>           
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