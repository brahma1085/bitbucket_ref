<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %> 
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Enumeration"%>
<%@page import="masterObject.share.ShareMasterObject"%>
<%@page import="masterObject.general.AccountObject"%>
<html>
<head><title>Share Allotment</title>
  <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Additional Allotment</center></h2>
      <hr  color="#339999">
      
    
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
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
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
             else if(len>10){
               alert("Enter valid "+str)
             }
             
           };
     
       function fun(shnum){
       alert(shnum)
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
      
       function generateTrno(){
         
        if(document.forms[0].pay_mode.value=="C"){
         document.getElementById("Cash1").style.display='block';
         document.getElementById("Cash2").style.display='block';
         document.getElementById("Transfer").style.display='none';
          document.getElementById("Transfer1").style.display='none';
      }
      
     else if(document.forms[0].pay_mode.value=="T"){
         document.getElementById("Cash1").style.display='none';
         document.getElementById("Cash2").style.display='none';
         document.getElementById("Transfer").style.display='block';
          document.getElementById("Transfer1").style.display='block';
      }
        
       
       
       
       
       
       var val=document.forms[0].validations.value;
       var val1=document.forms[0].newnumber.value;
       if(val!=0 || val!=""){
         alert(val);
       } 
       else if(val1!=0 || val1!=""){
        alert(val1);
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
     
    </script>


  </head>
<body class="Mainbody" onload="return generateTrno()" style="overflow: scroll;">
 <%!
    Hashtable hash_type,pay_mode;
    ShareMasterObject shobj,sh_trn;
    String amt;
    //Double amt;
    Double total_sh_amt=0.0;
    String[] values;
    String vis="visible";
    String jsppath;
    AccountObject acc_obj;
 %>
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
 <%
 String message=(String)request.getAttribute("msg");
    shobj=(ShareMasterObject)request.getAttribute("ShareDetails");
    sh_trn=(ShareMasterObject)request.getAttribute("ShareDetails_trn"); 
    acc_obj=(AccountObject)request.getAttribute("cashamt");
    String panelName=(String)request.getAttribute("panelName");
 %>
 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<html:form action="/Share/AdditionalAllotment?pageId=4002" focus="<%=""+request.getAttribute("focusto")%>">  
   
 
    <core:if test="<%=message!=null&&message.length()>0%>">
    <font color="red"><%=message %></font>
    </core:if>
    <br><br>
     <table class="txtTable" align="left" valign="top">
       
       <html:hidden property="newnumber"></html:hidden>
         <tr>
          <td><bean:message key="label.trn_no"></bean:message></td>
            <core:choose>
              <core:when test="${requestScope.ShareDetails_trn!=null}">
              <td><html:text property="transaction_num" size="5" styleClass="formTextFieldWithoutTransparent"  value="<%=""+sh_trn.getTranNumber() %>" onkeypress="return numbersonly()" ></html:text></td>
              </core:when>
            
              <core:when test="${empty requestScope.ShareDetails}">
          		<td><html:text property="transaction_num" size="5" styleClass="formTextFieldWithoutTransparent" onchange="submit()" value="0" onkeypress="return numbersonly()" onkeyup="numberlimit(this,'11')"></html:text></td>
              </core:when>
              
          <core:otherwise>
          <td><html:text property="transaction_num" styleClass="formTextFieldWithoutTransparent" size="5" onkeypress="return numbersonly()" onkeyup="numberlimit(this,'11')"></html:text></td>
          </core:otherwise>
          </core:choose>

         </tr>
         
        <tr>
          <td><bean:message key="label.ac_type"></bean:message></td>
          <td><html:select property="ac_type" styleClass="formTextFieldWithoutTransparent">
          <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}">
          <html:option value="${acType.moduleCode}">
          <core:out value="${acType.moduleAbbrv}"></core:out>
          </html:option>
          </core:forEach>
          </html:select>
          <core:choose>
           <core:when test="${requestScope.ShareDetails_trn!=null}">
            <html:text property="ac_no" styleClass="formTextFieldWithoutTransparent" size="8" value="<%=""+sh_trn.getShareNumber()%>" onkeypress="return numbersonly()"></html:text>
           </core:when>
          
          <core:when test="${empty requestScope.ShareDetails}">
          <html:text property="ac_no" size="8" styleClass="formTextFieldWithoutTransparent" onblur="validfn(ac_no.value,'Share Number')" onkeypress="return numbersonly()" onchange="submit()" onkeyup="numberlimit(this,'11')"></html:text>
          </core:when> 
          <core:otherwise>
          <html:text property="ac_no" styleClass="formTextFieldWithoutTransparent" size="8" value="<%=""+shobj.getShareNumber()%>" onkeypress="return numbersonly()"></html:text>
          </core:otherwise>
          </core:choose>
          
          </td>
        </tr>  
        
        <tr>
         <td><bean:message key="label.br_code"></bean:message></td>
         <core:choose>
          <core:when test="${requestScope.ShareDetails_trn!=null}">
          <td><html:text property="branch_code" styleClass="formTextFieldWithoutTransparent" value="<%=""+sh_trn.getBranchCode() %>" readonly="true" ></html:text></td>
          </core:when>
         <core:when test="${empty requestScope.ShareDetails}">
         <td><html:text property="branch_code" styleClass="formTextFieldWithoutTransparent" readonly="true" onkeyup="numberlimit(this,'11')"></html:text></td>
         </core:when>
         <core:otherwise>
         <td><html:text property="branch_code" styleClass="formTextFieldWithoutTransparent" value="<%=""+shobj.getBranchCode() %>" readonly="true" ></html:text></td>
         </core:otherwise>
        
         </core:choose>
        </tr>
        
        <tr>
         <td><bean:message key="label.sh_bal"></bean:message></td>
         <core:choose>
          <core:when test="${requestScope.ShareDetails_trn!=null}">
          <td><html:text property="sh_bal" styleClass="formTextFieldWithoutTransparent" value="<%=""+sh_trn.getAmount() %>" readonly="true" ></html:text></td>
          </core:when>
         
         <core:when test="${empty requestScope.ShareDetails}">
         <td><html:text property="sh_bal" styleClass="formTextFieldWithoutTransparent" readonly="true"  ></html:text></td>
         </core:when>
         <core:otherwise>
         <td><html:text property="sh_bal" styleClass="formTextFieldWithoutTransparent" value="<%=""+shobj.getDisplayShareBalance() %>" readonly="true" ></html:text></td>
         </core:otherwise>
         </core:choose>
        </tr>
            
        <tr>
         <td><bean:message key="label.no_sh"></bean:message></td>
         <core:choose>
         <core:when test="${requestScope.ShareDetails_trn!=null}">
         <td><html:text property="no_of_shares_all" styleClass="formTextFieldWithoutTransparent" value="<%=""+sh_trn.getNumberofShares() %>" readonly="true" ></html:text></td>
         </core:when>
         <core:when test="${empty requestScope.ShareDetails}">
         <td><html:text property="no_of_shares_all" styleClass="formTextFieldWithoutTransparent"  readonly="true"  ></html:text></td>
         </core:when>
         
         <core:otherwise>
         <td><html:text property="no_of_shares_all" styleClass="formTextFieldWithoutTransparent"  value="<%=""+(shobj.getDisplayShareBalance()/100.00) %>" readonly="true" ></html:text></td>
         </core:otherwise>
         </core:choose>
        </tr>
        
        <tr>
         <td><bean:message key="label.sh_cat"></bean:message></td>
         <core:choose>
         <core:when test="${requestScope.ShareDetails_trn!=null}">
         <td><html:text property="sh_cat" styleClass="formTextFieldWithoutTransparent" value="<%=""+sh_trn.getMemberCategory() %>" readonly="true" ></html:text></td>
         </core:when>
         <core:when test="${empty requestScope.ShareDetails}">
         <td><html:text property="sh_cat" styleClass="formTextFieldWithoutTransparent"  readonly="true" ></html:text></td>
         </core:when>
         
         <core:otherwise>
         <td><html:text property="sh_cat" styleClass="formTextFieldWithoutTransparent" value="<%=""+shobj.getMemberCategory() %>" readonly="true" ></html:text></td>
         </core:otherwise>
         </core:choose>
        </tr>
            
       <tr>
        <td><bean:message key="label.sh_type"></bean:message></td>
        <td><html:select property="sh_type" styleClass="formTextFieldWithoutTransparent">
         <%hash_type=(Hashtable)request.getAttribute("ShareType"); %>
         <% 
            if(shobj==null){
         %>
         <%
           Object key;
           Enumeration en=hash_type.keys();
           while(en.hasMoreElements()){
        	   key=en.nextElement();
           
         %> 
         <html:option value="<%=""+key%>"><%=""+hash_type.get(key)%></html:option>
         <%
           }}else if(sh_trn!=null){
        	   System.out.println("SHARE STATUS IN JSP IS "+shobj.getShareStatus());
         %>
         <html:option value="<%=""+shobj.getShareStatus()%>"><%=""+hash_type.get(shobj.getShareStatus())%></html:option>
         <%
           }else{
               %>
               <html:option value="<%=""+shobj.getShareStatus()%>"><%=""+hash_type.get(shobj.getShareStatus())%></html:option>
               <%
                 }
               %>
         %>
        </html:select></td>
       </tr>
       
       <tr>
        <td><bean:message key="label.no_of_shares"></bean:message></td>
        <core:choose>
         <core:when test="${requestScope.ShareDetails_trn!=null}">
         <td><html:text property="no_of_sh" styleClass="formTextFieldWithoutTransparent" size="8"  value="<%=""+sh_trn.getLnk_shares()%>" onkeypress="return numbersonly()"></html:text></td>
         </core:when>
                  
         <core:otherwise>
         <td><html:text property="no_of_sh" styleClass="formTextFieldWithoutTransparent"  onchange="submit()" onkeyup="numberlimit(this,'5')" onkeypress="return numbersonly()"></html:text></td>
         </core:otherwise>
                 
        </core:choose>
        
       </tr>
       
       <tr>
        <td><bean:message key="label.tot_shares"></bean:message></td>
        
        <%
          
          amt=(String)request.getAttribute("Total");
          total_sh_amt=(Double)request.getAttribute("Sh_cal_amt");
          System.out.println("The amt in jsp is"+amt);
        %>
       
       <%
         if(amt!=null){
        	 
       %>
       <td> <html:text property="sh_amt" styleClass="formTextFieldWithoutTransparent" value="<%=""+amt%>" readonly="true" ></html:text></td>
       <%
         }else if(sh_trn!=null){
       %>
        <td><html:text property="sh_amt"  styleClass="formTextFieldWithoutTransparent"  readonly="true" value="<%=""+sh_trn.getTotalAmount() %>" ></html:text></td>
        
        <%
         }else{
        %>
        <td><html:text property="sh_amt"  styleClass="formTextFieldWithoutTransparent"  readonly="true" ></html:text></td>
        <%} %>
       </tr>
       
       <tr>
         <td><bean:message key="label.details"></bean:message></td>
         <td><html:select property="combo_details" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
         <html:option value="Personal Details">Personal Details</html:option>
         <html:option value="Payment Details">Payment Details</html:option>
         </html:select></td>
       </tr>
       
       <tr>
        <td><bean:message key="label.paymode"></bean:message></td>
        <td><html:select property="pay_mode" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
        <%pay_mode=(Hashtable)request.getAttribute("Paymode"); %>
        <%
          Object mode;
          Enumeration en1=pay_mode.keys();
          while(en1.hasMoreElements()){
        	  mode=en1.nextElement();
          
        %>
        <html:option value="<%=""+mode%>"><%=""+pay_mode.get(mode)%></html:option>
        <%
          }
        %>
        </html:select></td>  
       </tr>
       
         <tr id="Cash1" style="display:none;">
       <td>
        <bean:message key="label.scrNo"></bean:message></td>
        <%
        if(acc_obj!=null){
        	 vis=(String)request.getAttribute("vis");	
        	%>
        <td><html:text property="sr_no" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly()" value="<%=""+acc_obj.getScrollno() %>" size="6"></html:text></td>
       <%}else{ %> 
       <td><html:text property="sr_no" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly()" onchange="submit()" value="0" size="6"></html:text></td>
       <%} %>
       <td>
        <bean:message key="label.date"></bean:message></td>
        <td><html:text property="date" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly()" value="<%=""+(String)request.getAttribute("date") %>"></html:text></td>
        
       
     </tr>
     
     <tr id="Cash2" style="display:none;">
      <td>
        <bean:message key="label.name"></bean:message></td>
        <%if(acc_obj!=null){ %>
        <td><html:text property="name" styleClass="formTextFieldWithoutTransparent" value="<%=""+acc_obj.getAccname()%>"></html:text></td>
         <%}else{ %>
        <td><html:text property="name" styleClass="formTextFieldWithoutTransparent"></html:text></td>
      <%} %>
      
      <td>
        <bean:message key="label.amount"></bean:message></td>
        <%if(acc_obj!=null){ %>
        <td><html:text property="amt" styleClass="formTextFieldWithoutTransparent" size="6" value="<%=""+acc_obj.getAmount() %>" readonly="true"></html:text></td>
        <%}else{ %>
        <td><html:text property="amt" styleClass="formTextFieldWithoutTransparent" size="6" value="0.00"></html:text></td>
        <%} %>
     </tr>
     
          
       
      
          <tr id="Transfer" style="display: block">
          <td><bean:message key="label.actype"></bean:message></td>
         <td>
         <html:select property="pay_ac_type" styleClass="formTextFieldWithoutTransparent">
            <core:forEach var="acTypes" items="${requestScope.AcTypes}">
             <html:option value="${acTypes.moduleCode}">
              <core:out value="${acTypes.moduleAbbrv}"></core:out>
           </html:option>
          </core:forEach>
                   
        </html:select>
        <%if(sh_trn!=null){ %>
        <html:text property="pay_ac_no" styleClass="formTextFieldWithoutTransparent"  size="6" onkeypress="return numbersonly()" value="<%=""+sh_trn.getRecievedAccno() %>"></html:text></td>
        <%}else{ %>
        <html:text property="pay_ac_no" styleClass="formTextFieldWithoutTransparent"  size="6" onkeypress="return numbersonly()"  onchange="submit()" onkeyup="numberlimit(this,'10')"></html:text></td>
        <%} %>
        </tr>
     
     <tr id="Transfer1" style="display: block">
     <td><bean:message key="label.balance"></bean:message></td>
     <td><html:text property="balance" styleClass="formTextFieldWithoutTransparent" value="<%=""+request.getAttribute("Bal") %>" size="6" readonly="true"></html:text> 
     <bean:message key="label.amount"></bean:message>
     
     <%
       if(total_sh_amt!=null){
    	   System.out.println("The total amount is"+total_sh_amt);
     %>
     <html:text property="amount" styleClass="formTextFieldWithoutTransparent" value="<%=""+total_sh_amt %>" size="6" onkeypress="return numbersonly()" readonly="true"></html:text></td>
     <%
     //vis=(String)request.getAttribute("vis");
       }else if(sh_trn!=null){
     %>
      <html:text property="amount" styleClass="formTextFieldWithoutTransparent" value="<%=""+sh_trn.getTrnamt() %>" size="6" onkeypress="return numbersonly()" readonly="true"  ></html:text></td>
     <%}else{ %>
     <html:text property="amount" styleClass="formTextFieldWithoutTransparent" value="0.0" size="6" onkeypress="return numbersonly()" onchange="submit()" ></html:text></td> 
     <%
       }
     %>
     </tr>
     
    <tr>  
     <td>
      <table  class="txtTable" align="left" style="visibility:<%=""+(String)request.getAttribute("vis")%>">
     
     
     <%
         
        values=(String[])request.getAttribute("PaymentDetails");
        if(values!=null){
           
     %>
     <tr>
     
     <td>
     <font style="color: #663399" face="Garmond" size="2">
     <u><bean:message key="label.payment"></bean:message></u></font></td>
     </tr>
      
     <tr>
     <td><bean:message key="label.sh_fee"></bean:message></td>
     <td><html:text property="sh_fee" styleClass="formTextField" value="<%=""+values[0]%>" readonly="true" ></html:text></td>
     </tr>
     <tr>
     <td><bean:message key="label.add_sh"></bean:message></td>
     <td><html:text property="addshfee" styleClass="formTextField" value="<%=""+values[1]%>" readonly="true" ></html:text></td>
     </tr>
     <tr>
     <td><bean:message key="label.misc"></bean:message></td>
     <td><html:text property="misc" styleClass="formTextField" value="<%=""+values[2]%>" readonly="true" ></html:text></td>
     </tr>
     <tr>
     <td><bean:message key="label.tot_sh_val"></bean:message></td>
     <td><html:text property="totshval" styleClass="formTextField" value="<%=""+values[3]%>" readonly="true" ></html:text></td>
     </tr>
     <tr>
     <td><bean:message key="label.amt_rcvd"></bean:message></td>
     <td><html:text property="amtrcvd" styleClass="formTextField" value="<%=""+values[4]%>" readonly="true" ></html:text></td>
     </tr>
      
    <%
        }
    %>
   </table>
     </td>
     </tr>             
     
       <tr>
        <html:hidden property="forward" value="error"></html:hidden>
        <html:hidden property="validations"></html:hidden>
        <td align="right">
        <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
         <html:submit  onclick="set('submit'); " styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:submit>
         <html:submit onclick="set('verify')" disabled="<%=(Boolean)request.getAttribute("Disable2")%>" styleClass="ButtonBackgroundImage"><bean:message key="label.verify" ></bean:message></html:submit>
         <%}else{ %>
         <html:submit  onclick="set('submit'); " disabled="true" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:submit>
         <html:submit onclick="set('verify')" disabled="true" styleClass="ButtonBackgroundImage"><bean:message key="label.verify" ></bean:message></html:submit>
         <%} %>
         </td>
         <td align="left"><html:reset styleClass="ButtonBackgroundImage"></html:reset>
        </td> 
       </tr>
       
       
       
    </table>
  </html:form>
    
  
    <table align="left"  valign="top" height="10%" width="30%">
    <tr> 
    <td>
        
        <%
        if(panelName!=null){
           jsppath=(String)request.getAttribute("flag");
           System.out.println("the jsppath is "+jsppath);
           if(jsppath!=null){
        %>
           <jsp:include page="<%=jsppath%>"></jsp:include>
                        
        <%
           }}
        %>
    </td>
      </tr>
        
  
 </table>
 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
 
  
 
</body>
</html>
