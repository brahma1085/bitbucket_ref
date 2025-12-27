<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>   
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareMasterObject"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Enumeration"%>
<%@page import="masterObject.share.ShareCategoryObject"%>
<%@page import="java.util.Map"%>
<html>
<head><title>Share Withdrawal</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Share Withdrawal</center></h2>
      <hr>
      
    
    <script type="text/javascript">
    
    function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
    
     function set(target){
       document.forms[0].forward.value=target
        };
     																																					
     
           function validfn(fnm,str){
             var len=fnm.length;
             if(len==0){
               alert("Enter the "+str )
             }
           };
     
      function resetfields(str){
      
         if(document.forms[0].with_type.value==str){
         document.forms[0].pay_ac_no.value="";
         document.forms[0].no_of_sh.value="0";
         document.forms[0].tot_amt.value="";
         document.forms[0].balance.value="";
         document.forms[0].amount.value="";
         
         }
         document.forms[0].submit();
      };
      
      function checkloan(){
      
        if(document.forms[0].flag.value=="1"){
         alert("Cannot close the account");
         document.forms[0].with_type.value=="W";
         
        }
        else{
        
          return true;
        }
      };
      
      function verified(){
        alert("Account already verified");
      };
      
      function validfn(fnm,str){
             var len=fnm.length;
             if(len==0){
               alert("Enter the"+str )
             }
             
           };
           
           
      function check(str){
        
        if(str==1){
        alert("No details exists for this account");
        }
        else{
          alert("Details available");
        }
      };     
     
     function numbersonly(eve){
         var cha = event.keyCode


            if (  ( cha  > 47 && cha < 58  ) ) {


              
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
         
        
      };
      
      function generateAcno(){
       var val=document.forms[0].validations.value;
       if(val!=0 || val!=""){
         alert(val);
         clearfun();
       } 
       else{
         return false;
       }
     };
         
         function clearfun(){
       var v=document.forms[0].elements;
       for(var i=0;i<v.length;i++){
         if(v[i].type=="text"){
            v[i].value="";
         }
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
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
         
    </script>


  </head>
<body onload="generateAcno()" class="Mainbody" style="overflow: scroll;">
<%!
   ShareMasterObject shobj,withobj;
   Hashtable hash_type,hash_with,hash_pay;
   String [] min_amt;
   String tot_amt,tot_sh,clo_sh,tot_amt_cl,checkloans;
   String total_sh_amt="0.0";
   String vis="hidden";
   String check="1";
   String jsppath;
   ShareMasterObject[] sh_obj=null;
   String shareWithdrawal;
%>
<%
  shobj=(ShareMasterObject)request.getAttribute("ShareDetails");
  withobj=(ShareMasterObject)request.getAttribute("with_det");
  shareWithdrawal=(String)request.getAttribute("WithdrawalShare");
  String message=(String)request.getAttribute("msg");
%>
<%
if(shareWithdrawal!=null){
%> 
<font color="red" face="+3">No details exists for this account number</font>
<%} %>
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
<table class="txtTable" width="70%">
<td>
<html:form action="/Share/Withdrawal?pageId=4005" focus="<%=""+request.getAttribute("focusto")%>">
<core:if test="<%=message!=null&&message.length()>0%>">
    <font color="red"><%=message %></font>
    </core:if>
    <br><br>
  <table class="txtTable"> 
  <tr>
   <td>
    <bean:message key="label.trn_no"></bean:message>
   </td>
   
   <core:choose>
    <core:when test="${requestScope.ShareDetails!=null}">
    <td><html:text property="trn_no" styleClass="formTextFieldWithoutTransparent" size="6" value="${requestScope.ShareDetails.tranNumber}" onkeypress="return numbersonly()"></html:text></td>
    </core:when>
      
      <core:when test="${requestScope.with_det!=null}">
      <td><html:text property="trn_no" styleClass="formTextFieldWithoutTransparent" size="6" value="${requestScope.with_det.tranNumber}" onkeypress="return numbersonly()"></html:text></td>
      </core:when>  
            
    <core:otherwise>
      <td><html:text property="trn_no" styleClass="formTextFieldWithoutTransparent"  size="6" value="0"  onchange="submit()" onkeypress="return numbersonly()" ></html:text></td>     
    </core:otherwise>
   </core:choose>
   
  </tr>
  
  <tr>
   <td><bean:message key="label.ac_type"></bean:message></td>
   <td><html:select property="ac_type"  styleClass="formTextFieldWithoutTransparent">
      <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}">
        <html:option value="${acType.moduleCode}">
          <core:out value="${acType.moduleAbbrv}"></core:out>
        </html:option>
      </core:forEach>
   </html:select>
    
   <core:choose>
   <core:when test="${requestScope.with_det!=null}">
      <html:text property="sh_no" styleClass="formTextFieldWithoutTransparent" size="6" value="${requestScope.with_det.shareNumber} " onkeypress="return numbersonly()"></html:text></td>
      
     </core:when>
     <core:when test="${empty requestScope.ShareDetails}">
       
  <html:text property="sh_no" style="color:brown" styleClass="formTextFieldWithoutTransparent" size="6" value="0"  onchange="submit()" onblur="validfn(sh_no.value,'Share Number')" onkeypress="return numbersonly()" onkeyup="numberlimit(this,'11')"></html:text></td>
     </core:when>
     
     
     <core:otherwise>
      <html:text property="sh_no"  style="color:brown " styleClass="formTextFieldWithoutTransparent" size="6" value="<%=""+shobj.getShareNumber() %>" onkeypress="return numbersonly()"></html:text></td>  
     </core:otherwise>
  </core:choose>
   
  </tr>
  
  <tr>
   <td><bean:message key="label.no_sh"></bean:message></td>
    <core:choose>
     <core:when test="${requestScope.with_det!=null}">
      <td><html:text property="no_of_sh_al" value="<%=""+withobj.getNumberofShares() %>" styleClass="formTextFieldWithoutTransparent"  readonly="true"></html:text></td>
     </core:when>
    
     <core:when test="${empty requestScope.ShareDetails}">
     <td><html:text property="no_of_sh_al" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
     </core:when>
     <core:otherwise>
      <td><html:text property="no_of_sh_al" value="<%=""+shobj.getNumberofShares() %>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>     
     </core:otherwise>
    </core:choose>
   
  </tr>

 <tr>
  <td><bean:message key="label.sh_type"></bean:message></td>
  <td><html:select property="sh_type" styleClass="formTextFieldWithoutTransparent">
  <%
      hash_type=(Hashtable)request.getAttribute("ShareType");
       
      %>
      <%if(shobj==null){ %>
      
      
      <%
       Object key;  
       
       Enumeration en=hash_type.keys();
        while(en.hasMoreElements()){
        	key=en.nextElement();
      %>
      <html:option value="<%=""+key%>"><%=""+hash_type.get(key)%></html:option>
      <%
        }}else if(shobj!=null){
        	System.out.println("In the jsp=="+shobj.getShareStatus());
        	System.out.println("++++++++++++++++++++++"+hash_type.get(shobj.getShareStatus().trim()));
      %>
      <html:option value="<%=""+hash_type.get(shobj.getShareStatus())%>"><%=""+hash_type.get(shobj.getShareStatus())%></html:option>
      <%
        }else if(withobj!=null){
        	System.out.println("In jsp with the withdrawal details---------------->"+withobj.getShareStatus());
      %>
      <html:option value="<%=""+hash_type.get(withobj.getShareStatus())%>"><%=""+hash_type.get(withobj.getShareStatus().trim())%></html:option>
      <%}%>
     </html:select></td>
      </tr>
  
  <tr>
   <td><bean:message key="label.branch"></bean:message></td>
     <core:choose>
      <core:when test="${requestScope.with_det!=null}">
       <td><html:text property="br_code" styleClass="formTextFieldWithoutTransparent"  value="${requestScope.with_det.branchCode}" readonly="true" ></html:text></td>
      </core:when>
     
      <core:when test="${empty requestScope.ShareDetails}">
      <td><html:text property="br_code"  styleClass="formTextFieldWithoutTransparent" readonly="true" ></html:text></td>
      </core:when>
      
      <core:otherwise>
      <%System.out.println("The branch name is "+shobj.getBranchName()); %>
        <td><html:text property="br_code" styleClass="formTextFieldWithoutTransparent"  readonly="true" value="<%=""+shobj.getBranchCode() %>" ><%=""+shobj.getBranchName()%></html:text></td>       
      </core:otherwise>
     </core:choose> 
   
  </tr>
 
 <tr>
  <td><bean:message key="label.sh_cat"></bean:message></td>
    <core:choose>
     <core:when test="${requestScope.with_det!=null}">
       <td><html:text property="sh_cat" styleClass="formTextFieldWithoutTransparent"  value="${requestScope.with_det.memberCategory}" readonly="true"  ></html:text></td>
     </core:when>
     <core:when test="${empty requestScope.ShareDetails}">
      <td><html:text property="sh_cat" styleClass="formTextFieldWithoutTransparent"  readonly="true"  value="1"></html:text></td>
     </core:when>
     <core:otherwise>
      <td><html:text property="sh_cat" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=""+shobj.getShareType() %>" ></html:text></td>      
     </core:otherwise>
    </core:choose>
  
 </tr>
  
 <tr>
  <td><bean:message key="label.sh_val"></bean:message></td>
   <core:choose>
     <core:when test="${requestScope.with_det!=null}">
       <td><html:text property="sh_val" styleClass="formTextFieldWithoutTransparent" value="${requestScope.with_det.shareVal}" readonly="true" ></html:text></td>
     </core:when>
    <core:when test="${empty requestScope.ShareDetails}">
      <td><html:text property="sh_val" styleClass="formTextFieldWithoutTransparent" readonly="true" ></html:text></td>
    </core:when>
    <core:otherwise>
      <td><html:text property="sh_val" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=""+shobj.getShareVal() %>" ></html:text></td>     
    </core:otherwise>
   </core:choose>
 </tr>
  
 <tr>
    
    <%
      min_amt=(String[])request.getAttribute("min_withdraw");
      if(min_amt!=null){
    	  System.out.println("minamt"+min_amt[0]+"min amt"+min_amt[1]);
    %>    
    
    <%}else{
    %>	
    <td style="visibility:hidden "></td>
    <%
    }
    %>
  
 </tr>
 
 
 <tr>
  <td>Withdrawal Type</td>
  <html:hidden property="flag"></html:hidden>
  <td><html:select property="with_type" styleClass="formTextFieldWithoutTransparent"  onchange="resetfields(this.value)">
   <%
     hash_with=(Hashtable)request.getAttribute("WithdrawalTypes");
        
   %>
   <%
      Object key;
      Enumeration en=hash_with.keys();
      while(en.hasMoreElements()){
      	key=en.nextElement();
    %>
    <html:option value="<%=""+key%>"><%=""+hash_with.get(key)%></html:option>
   <% 
      }if(withobj!=null){

   %>
    <html:option value="<%=""+hash_with.get(withobj.getWithdrawalType())%>"><%=""+hash_with.get(withobj.getWithdrawalType())%></html:option>
     <%
      }
     %>
  </html:select></td>
 </tr>
 
 <tr>
  <td><bean:message key="label.no_sh"></bean:message></td>
    <%
      tot_amt=(String)request.getAttribute("Amount");
      tot_sh=(String)request.getAttribute("totshares");
      clo_sh=(String)request.getAttribute("partshares");
      tot_amt_cl=(String)request.getAttribute("Amountcl");
    %>
    <%
    if(tot_amt!=null){
    %>
  <td><html:text property="no_of_sh" styleClass="formTextFieldWithoutTransparent" size="6" value="<%=""+tot_sh %>" onkeypress="return numbersonly()"></html:text></td>
  <%
      }else if(tot_amt_cl!=null){
  %>
  <td><html:text property="no_of_sh" styleClass="formTextFieldWithoutTransparent" size="6"  value="<%=""+clo_sh %>" onkeypress="return numbersonly()"></html:text></td>
  <%
      }else if(withobj!=null){
  %>
  <td><html:text property="no_of_sh"  styleClass="formTextFieldWithoutTransparent" size="6" value="<%=""+withobj.getWith_shares() %>" onkeypress="return numbersonly()"></html:text></td>
  <%
      }else{
  %>
  <td><html:text property="no_of_sh" styleClass="formTextFieldWithoutTransparent" size="6" onchange="submit()" value="0" onkeypress="return numbersonly()" onkeyup="numberlimit(this,'5')"></html:text></td>
  <%
      }
  %>
 </tr>
 
 <tr>
  <td><bean:message key="label.tot_sh_val"></bean:message></td>
  <%
    if(tot_amt!=null){
  %>
  <td><html:text property="tot_amt" styleClass="formTextFieldWithoutTransparent"   value="<%=""+tot_amt %>"  onkeypress="return numbersonly()" ></html:text></td>
  <%
    }else if(tot_amt_cl!=null){
  %>
  <td><html:text property="tot_amt"  styleClass="formTextFieldWithoutTransparent" value="<%=""+tot_amt_cl %>"  onkeypress="return numbersonly()"></html:text></td>
  <%
    }else if(withobj!=null){
  %>
  <td><html:text property="tot_amt" styleClass="formTextFieldWithoutTransparent"  value="<%=""+withobj.getTrnamt() %>"  onkeypress="return numbersonly()"></html:text></td>
  <%
    }else{
  %>
  
  <td><html:text property="tot_amt" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly()" readonly="true"></html:text></td>
  <%
    }
  %>
 </tr>
 
 <tr>
  <td><bean:message key="label.details"></bean:message></td>
  <td><html:select property="details" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
    <core:forEach var="details" items="${requestScope.WithDetails}">
     <html:option value="${details}">
     <core:out value="${details}"></core:out></html:option>
     </core:forEach>
  </html:select></td>
 </tr>
 
 <tr>
  <td><bean:message key="label.paymode"></bean:message></td>
  <td><html:select property="paymode" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
   <%hash_pay=(Hashtable)request.getAttribute("Paymode"); %>
   <%
      Object key1;
      Enumeration en1=hash_pay.keys();
      while(en1.hasMoreElements()){
      	key1=en1.nextElement();
    %>
    <html:option value="<%=""+key1%>"><%=""+hash_pay.get(key1)%></html:option>
   <% 
      }
   %>
  </html:select></td>
 </tr>

 <%
 String userChoice=(String)request.getAttribute("CashWithdrawal");
 if(userChoice==null){
 %>
  <tr>
          <td><bean:message key="label.actype"></bean:message></td>
         <td>
         <html:select property="pay_ac_type" styleClass="formTextFieldWithoutTransparent" disabled="<%=(Boolean)request.getAttribute("Butt_disable")%>">
            <core:forEach var="acTypes" items="${requestScope.AcTypes}">
             <html:option value="${acTypes.moduleCode}">
              <core:out value="${acTypes.moduleAbbrv}"></core:out>
           </html:option>
          </core:forEach>
        </html:select>
        <core:choose>
         <core:when test="${requestScope.with_det!=null}">
          <html:text property="pay_ac_no" styleClass="formTextFieldWithoutTransparent" value="${requestScope.with_det.recievedAccno}" onkeypress="return numbersonly()" readonly="<%=(Boolean)request.getAttribute("disablefield") %>"></html:text>
         </core:when>
         <core:otherwise>
          <html:text property="pay_ac_no" styleClass="formTextFieldWithoutTransparent" onblur="submit()"  onkeypress="return numbersonly()" onkeyup="numberlimit(this,'11')"></html:text>
         </core:otherwise>
        </core:choose>
        </td>
     <td><bean:message key="label.balance"></bean:message></td>     
	<td><html:text property="balance"  styleClass="formTextFieldWithoutTransparent" value="<%=""+request.getAttribute("Bal") %>" size="6" readonly="true"></html:text>        
        </tr>
    <%}
 %>
      <tr>
   <td>
      
     <bean:message key="label.amount"></bean:message></td>
     <%
       if(withobj!=null){
     %>
     <td><html:text property="amount"  styleClass="formTextFieldWithoutTransparent" value="<%=""+withobj.getTrnamt()%>" onkeypress="return numbersonly()" readonly="true"></html:text></td>
     <%
       }else{
     %>
       <td> <html:text property="amount" styleClass="formTextFieldWithoutTransparent" value="<%=""+request.getAttribute("Amt") %>" onkeypress="return numbersonly()" readonly="true"></html:text></td>     
     <%
       }
     %>
     
     </tr>
   
 <tr>
    <html:hidden property="forward" value="error"></html:hidden>
    <html:hidden property="validations"></html:hidden>
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
    <td><html:submit  onclick="set('submit')" styleClass="ButtonBackgroundImage"  ><bean:message key="label.submit"></bean:message> </html:submit></td>
   <td> <html:submit  onclick="set('verify')" styleClass="ButtonBackgroundImage"><bean:message key="label.verify"></bean:message> </html:submit></td>
   <%}else{ %>
   <td><html:submit  onclick="set('submit')" styleClass="ButtonBackgroundImage"  disabled="true"><bean:message key="label.submit"></bean:message> </html:submit></td>
   <td> <html:submit  onclick="set('verify')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.verify"></bean:message> </html:submit></td>
   <%} %>
   <td> <!--<html:cancel styleClass="ButtonBackgroundImage"></html:cancel>--></td>
   <td> <!--<html:reset styleClass="ButtonBackgroundImage"></html:reset>
    --></td>
        
 </tr>
</table>
</html:form> 


<td>

<table align="right" valign="top" width="40%">
  <tr>
    <%
       sh_obj=(ShareMasterObject[])request.getAttribute("LoanDetails");
       if(sh_obj!=null){
    %>
       <jsp:include page="/LoanDetails.jsp"></jsp:include>
    <%} %>
  </tr>
 </table>



<table align="right"  valign="top">
    <tr> 
    <td>
        
        <%
           jsppath=(String)request.getAttribute("flag");
           System.out.println("the jsppath is "+jsppath);
           if(jsppath!=null){
        %>
           <jsp:include page="<%=jsppath%>"></jsp:include>
                        
        <%
           }
        %>
    </td>
      </tr>
        

 </table>
 
 </td>


</td> 
 </table>
 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>