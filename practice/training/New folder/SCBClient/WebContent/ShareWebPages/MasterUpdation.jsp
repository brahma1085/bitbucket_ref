<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Hashtable"%>
<%@page import="masterObject.share.ShareMasterObject"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<html>
<head><title> Master Updation</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Share Master Updation</center></h2>
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
           
           
       function check_acnt(){
          if(document.getElementById("ac").value=='T'){
           alert("Account not found")
           document.forms[0].acno.value="";
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
       
       function numbers_only(eve){
         var cha = event.keyCode


            if (  ( cha  >= 47 && cha < 58  ) ) {


              
                return true ;
                
            }
            else{
              alert("Please enter valid date ");
              return false ;
            }
            
        
      };
      
      function fun_validate(){
        var val=document.forms[0].validations.value;
       if(val!=0 || val!=""){
         alert(val); 
      
       } 
       else{
         return false;
       }
      };
     
     function reset123(){
    var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="text")
    {
    ele[i].value="";
    }
   else if(ele[i].type=="hidden")
    {
    ele[i].value="";
    }
    else if(ele[i].type=="checkbox")
    {
    ele[i].checked=false;
    }
    
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
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
         
    </script>


  </head>
<body class="Mainbody"  onload="fun_validate()" style="overflow: scroll;">
<%!
   Hashtable hash_type,hash_div;
   ShareMasterObject shobj;
%>
<% 
   shobj=(ShareMasterObject)request.getAttribute("ShareDetails");
    
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
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
  <html:form action="/Share/MasterUpdation?pageId=4014" focus="<%=""+request.getAttribute("focusto")%>">
    <table class="txtTable">
      <td>
      <table class="txtTable">
      <tr>
      <td>
       <bean:message key="label.actype"></bean:message>
      </td>
      <td>
      <html:select property="actype" styleClass="formTextFieldWithoutTransparent">
       <html:option value="1001001">SH</html:option>
      </html:select>
      
      
      
       <bean:message key="label.acNum"></bean:message>
       <html:text property="acno" styleClass="formTextFieldWithoutTransparent" size="6" onblur="validfn(acno.value,'Share Number')" onchange="submit()" onkeypress="return numbersonly()" onkeyup="numberlimit(this,'11')"></html:text>
       <html:hidden property="ac_not_fnd" styleId="ac"></html:hidden>
      </td>
      </tr>
      
      <tr>
      <td><bean:message key="label.sh_type"></bean:message></td>
      <td><html:select property="shtype" styleClass="formTextFieldWithoutTransparent" disabled="<%=(Boolean)request.getAttribute("disablefield")%>">
      
      
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
        }}else{
        	System.out.println("In the jsp========="+shobj.getShareStatus());
        	System.out.println("++++++++++++++++++++++"+hash_type.get(shobj.getShareStatus().trim()));
      %>
      <html:option value="<%=""+hash_type.get(shobj.getShareStatus())%>"><%=""+hash_type.get(shobj.getShareStatus())%></html:option>
      <%
        }
      %>
     </html:select></td>
      </tr>
     
     <tr>
      <td><bean:message key="label.temp"></bean:message></td>
      <core:choose>
       <core:when test="${requestScope.ShareDetals!=null}">
         <td><html:text property="tempno" styleClass="formTextField" readonly="true" value="${requestScope.ShareDetails.tempShareNumber}"></html:text>
       </core:when>
       <core:otherwise>
       <td><html:text property="tempno" styleClass="formTextField" readonly="true"></html:text>
     </td> 
       </core:otherwise>
      </core:choose>
       
      <td><bean:message key="label.cid"></bean:message></td>
      <core:choose>
       <core:when test="${requestScope.ShareDetails!=null}">
       <td><html:text property="cid" styleClass="formTextField" readonly="true" value="${requestScope.ShareDetails.customerId}"></html:text></td>
       </core:when>
       <core:otherwise>
        <td><html:text property="cid" styleClass="formTextField" readonly="true"></html:text></td>       
       </core:otherwise>
      </core:choose>
       
     </tr>
     
     <tr>
      <td><bean:message key="label.branch"></bean:message></td>
      <td><html:select property="brcode" styleClass="formTextFieldWithoutTransparent" disabled="<%=(Boolean)request.getAttribute("disablefield")%>">
      <core:choose>
      <core:when test="${empty requestScope.ShareDetails}">
      <core:forEach var="branch" items="${requestScope.Branch}">
      <%System.out.println("HERE"); %>
      <html:option value="${branch.branchCode}">
      <core:out value="${branch.branchName}"></core:out>
      </html:option>
      </core:forEach>
      </core:when>
      <core:otherwise>
      <html:option value="${requestScope.Branch[requestScope.ShareDetails.branchCode-1].branchCode}">
      <core:out value="${requestScope.Branch[requestScope.ShareDetails.branchCode-1].branchName}"></core:out>
      </html:option>
      </core:otherwise>
      </core:choose>
      </html:select></td>
     </tr>
     
     <tr>
      <td><bean:message key="label.addtype"></bean:message></td>
      <td><html:select property="addtype" styleClass="formTextFieldWithoutTransparent">
       <html:option value="1">1</html:option>
       <html:option value="2">2</html:option>
       <html:option value="3">3</html:option>
       <html:option value="4">4</html:option>
      </html:select></td>
     </tr>
     
       <tr>
      <td><bean:message key="label.sh_cat"></bean:message></td>
      <td><html:select property="memcat" styleClass="formTextFieldWithoutTransparent"  disabled="<%=(Boolean)request.getAttribute("disablefield")%>">
      <core:choose>
      <core:when test="${empty requestScope.ShareDetails}">
      <core:forEach var="sh_cat" items="${requestScope.ShareCat}">
      <html:option value="${sh_cat.shCat}">
      <core:out value="${sh_cat.catName}"></core:out>
      </html:option>
      </core:forEach>
      </core:when>
      <core:otherwise>
      <core:forEach var="sh_cat" items="${requestScope.ShareCat}">
      <html:option value="${sh_cat.shCat}">
      <core:out value="${sh_cat.catName}"></core:out>
      </html:option>
     
      </core:forEach>
      </core:otherwise>
      </core:choose>
      </html:select></td>
      </tr>
       
      <tr>
       <td><bean:message key="label.memdate"></bean:message></td>
       <core:choose>
       <core:when test="${requestScope.ShareDetails!=null}">
        <td><html:text property="issuedate" styleClass="formTextFieldWithoutTransparent" size="10" value="${requestScope.ShareDetails.issueDate}" readonly="<%=(Boolean)request.getAttribute("disablefield")%>" onkeypress="return numbers_only()"></html:text></td>
       </core:when>
       <core:otherwise>
       <td><html:text property="issuedate" styleClass="formTextFieldWithoutTransparent" size="10" onkeypress="return numbers_only()"></html:text></td>
       </core:otherwise>
       </core:choose>
       
       
       <td><bean:message key="label.sh_bal"></bean:message></td>
       <core:choose>
        <core:when test="${requestScope.ShareDetails!=null}">
          <td><html:text property="sh_bal" styleClass="formTextField" readonly="true" value="${requestScope.ShareDetails.displayShareBalance}"></html:text></td>
        </core:when>
        <core:otherwise>
           <td><html:text property="sh_bal" styleClass="formTextField" readonly="true"></html:text></td>        
        </core:otherwise>
       </core:choose>
      
      </tr> 
       
       <tr>
     <td><bean:message key="label.intrAcType"></bean:message></td>
     <td><html:select property="intrtype" disabled="<%=(Boolean)request.getAttribute("disablefield")%>" styleClass="formTextFieldWithoutTransparent" >
     <core:forEach var="intro_type" items="${requestScope.AcType}">
        <html:option value="${intro_type.moduleCode}">
        <core:out value="${intro_type.moduleAbbrv}"></core:out>
        </html:option>
     </core:forEach> 
     </html:select>
     <core:choose>
     <core:when test="${requestScope.ShareDetails!=null}">
       <html:text property="intracno" styleClass="formTextFieldWithoutTransparent"  value="${requestScope.ShareDetails.introducerAccno}" size="8" readonly="<%=(Boolean)request.getAttribute("disablefield")%>" onkeypress="return numbersonly()"></html:text></td>
     </core:when>
     <core:otherwise>
    <html:text property="intracno"  styleClass="formTextFieldWithoutTransparent" size="8" onkeypress="return numbersonly()"></html:text></td> 
     </core:otherwise>
     </core:choose>
    
    </tr>

    <tr>
     <td><bean:message key="label.nomno"></bean:message></td>
     <core:choose>
      <core:when test="${requestScope.ShareDetails!=null}">
      <td><html:text property="no_no" styleClass="formTextFieldWithoutTransparent" size="8" value="${requestScope.ShareDetails.nomineeno}" readonly="<%=(Boolean)request.getAttribute("disablefield")%>" onkeypress="return numbersonly()"></html:text></td>
      </core:when>
      <core:otherwise>
     <td><html:text property="no_no" styleClass="formTextFieldWithoutTransparent" size="8" onkeypress="return numbersonly()"></html:text></td> 
      </core:otherwise>
     </core:choose>
     
     
     <td><bean:message key="label.certno"></bean:message></td>
      <core:choose>
       <core:when test="${requestScope.ShareDetails!=null}">
        <td><html:text property="certno" styleClass="formTextField" readonly="true" size="8" value="${requestScope.ShareDetails.numberCert}" onkeypress="return numbersonly()"></html:text></td>
       </core:when>
       <core:otherwise>
       <td><html:text property="certno" styleClass="formTextField" readonly="true" size="8" onkeypress="return numbersonly()"></html:text></td>  
       </core:otherwise>
      </core:choose>
     
    </tr>
       
    <tr>
     <td><bean:message key="label.divdate"></bean:message></td>
      <%if(shobj!=null){
    	  System.out.println("The div date in jsp is"+shobj.getDivUptoDate());
    	  %>
         <td><html:text property="div_date" size="10" styleClass="formTextFieldWithoutTransparent" value="<%=""+shobj.getDivUptoDate() %>" readonly="<%=(Boolean)request.getAttribute("disablefield")%>" onkeypress="return numbers_only()"></html:text></td>
         
         <%
            }else{
         %>
        <td><html:text property="div_date" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbers_only()"></html:text></td>
        <% 
            }
        %>  
      
     
    </tr>   
    
    <tr>
     <td><bean:message key="label.closedt"></bean:message></td>
      <%if(shobj!=null&&shobj.getCloseDate()!=null){ %>
       <td><html:text property="closedate" styleClass="formTextFieldWithoutTransparent" size="10" value="<%=""+shobj.getCloseDate() %>" readonly="<%=(Boolean)request.getAttribute("disablefield")%>" onkeypress="return numbers_only()"></html:text></td>
       <%}else{ %>
       <td><html:text property="closedate" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbers_only()" onkeyup="numberlimit(this,'11')"></html:text></td>
       <%
       }
       %> 
    
     
    </tr>
    
    <tr>
     <td><bean:message key="label.tfrdate"></bean:message></td>
        <%if(shobj!=null&&shobj.getTransferDate()!=null){ %>
         <td><html:text property="transfer_date" styleClass="formTextFieldWithoutTransparent" size="10" value="<%=""+shobj.getTransferDate() %>" readonly="<%=(Boolean)request.getAttribute("disablefield")%>" onkeypress="return numbers_only()"></html:text></td>
         
         <%
        }else{
         %>
         <td><html:text property="transfer_date" styleClass="formTextFieldWithoutTransparent" size="10" onkeypress="return numbers_only()" onkeyup="numberlimit(this,'11')"></html:text></td>   
      <%
        }
      %>
    </tr>
    
    <tr>
    <td><bean:message key="label.acstat"></bean:message></td>
      <%if(shobj!=null){ %>
       <td><html:text property="sharestate" styleClass="formTextFieldWithoutTransparent" size="8" value="<%=""+shobj.getShareStatus() %>" readonly="<%=(Boolean)request.getAttribute("disablefield")%>"></html:text></td>
       <%
      }else{
       %>
       <td><html:text property="sharestate" styleClass="formTextFieldWithoutTransparent" size="8"></html:text></td>
       <%
      }
       %>
    
    </tr>
    
    <tr>
     <td><bean:message key="label.pay_type"></bean:message></td>
     <td><html:select property="paymode" styleClass="formTextFieldWithoutTransparent" disabled="<%=(Boolean)request.getAttribute("disablefield")%>">
     <%hash_div=(Hashtable)request.getAttribute("Paymode");%>
     <% System.out.println("Checking here3");
     
     System.out.println("Checking here4");
     %>
     <%if(shobj==null){ %>
     <html:option value="SELECT">SELECT</html:option>
     
     <%
        Object div_key;
        Enumeration en_div=hash_div.keys();
        while(en_div.hasMoreElements()){
        	div_key=en_div.nextElement();
        
     %>
     <html:option value="<%=""+div_key%>"><%=""+hash_div.get(div_key)%></html:option>
     <%
        }}else{
        	System.out.println("Checking 5");
        	System.out.println("Checking 6"+hash_div+shobj+shobj.getRecievedMode());
        	System.out.println("CHECKING 7"+shobj.getRecievedMode());
     %>
     <html:option value="<%=""+hash_div.get(shobj.getRecievedMode())%>"><%=""+hash_div.get(shobj.getRecievedMode())%></html:option>
     <%
        }
     %>
     </html:select></td>
     </tr>
    

     
     <tr>
     <td><bean:message key="label.acType"></bean:message></td>
     <td><html:select property="paytype" styleClass="formTextFieldWithoutTransparent"  disabled="<%=(Boolean)request.getAttribute("disablefield")%>">
     <core:forEach var="acTypes" items="${requestScope.AcTypes}">
     <html:option value="${acTypes.moduleCode}">
     <core:out value="${acTypes.moduleAbbrv}"></core:out>
     </html:option>
     </core:forEach>
     
     </html:select>
     <%
       if(shobj!=null){
     %>
     <html:text property="payacno" styleClass="formTextFieldWithoutTransparent"  value="<%=""+shobj.getPaymentAccno() %>" readonly="<%=(Boolean)request.getAttribute("disablefield")%>" onkeypress="return numbersonly()"></html:text></td>
     <%
       }else{
     %>
     <html:text property="payacno" styleClass="formTextFieldWithoutTransparent"  size="8" onkeypress="return numbersonly()"></html:text></td>
     <%
       }
     %>
     </tr>
    
    <tr>
     <td><bean:message key="label.lnavld"></bean:message></td>
     <%if(shobj!=null){ %>
     <td><html:text property="lnapp" styleClass="formTextFieldWithoutTransparent"  readonly="<%=(Boolean)request.getAttribute("disablefield")%>" value="<%=""+shobj.getLoanAvailed() %>" ></html:text></td>
    <%}else{ %>
    <td><html:text property="lnapp" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
    <%} %>
    
      
     <td><bean:message key="label.ledger"></bean:message></td>
     <%if(shobj!=null&&shobj.getLdgPrntDate()!=null){ %>
     <td><html:text property="ledger" styleClass="formTextField" readonly="true" value="<%=""+shobj.getLdgPrntDate() %>"></html:text></td>
     <%}else{ %>
     <td><html:text property="ledger" styleClass="formTextField" readonly="true"></html:text></td>
     <%} %>
    </tr>
    
    <tr>
     <td><bean:message key="label.trnseq"></bean:message></td>
      <%if(shobj!=null){ %>
     <td><html:text property="trnseq" styleClass="formTextField" readonly="true" value="<%=""+shobj.getTrnseq() %>"></html:text></td>
     <%}else{ %>
     <td><html:text property="trnseq" styleClass="formTextField" readonly="true"></html:text></td>
     <%} %>
    </tr>
    
    <tr>
     <html:hidden property="forward" value="error"></html:hidden>
     <html:hidden property="validations"></html:hidden>
     <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
     <td><html:submit onclick="set('update')"  styleClass="ButtonBackgroundImage"><bean:message key="label.update"></bean:message> </html:submit></td>
      <td><html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:submit></td>
      <%}else{ %>
      <td><html:submit onclick="set('update')"  styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.update"></bean:message> </html:submit></td>
      <td><html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.submit"></bean:message> </html:submit></td>
      <%} %>
      <td><html:submit onclick="reset123()" styleClass="ButtonBackgroundImage"><bean:message key="label.clear"></bean:message> </html:submit></td>
    </tr>
    
     </table>
    </td>        
    </table>
  </html:form>
 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>