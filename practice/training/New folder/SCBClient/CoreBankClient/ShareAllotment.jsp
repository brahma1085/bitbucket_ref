<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="java.util.Date"%>
<%@page import="masterObject.share.ShareCategoryObject"%>
<%@page import="masterObject.general.BranchObject"%>
<%@page import="masterObject.share.ShareMasterObject"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Hashtable"%>
<%@page import="masterObject.share.ShareParamObject"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<html>
  <head><title>Share Allotment</title>
  <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Share Allotment</center></h2>
      <hr style="color: green">
      
    
    <script type="text/javascript">
     function set(target){
       document.forms[0].forward.value=target
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
    
     function only_numbers() {
	
	var cha=event.keyCode;
	if(cha>47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  
    
    function only_for_address()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >= 44 && cha <=58)||(cha >= 65 && cha <=90)||cha==32||cha==35) 
 		{
   			return true;
       	} else 
       	{
   			return false;
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
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
    
           function validfn(fnm,str,prop){
             var len=fnm.length;
             if(len==0){
               alert("Enter the "+str )
              document.forms[0].prop.focus;
             }
             else if(len>10){
             alert("Enter valid "+str)
             document.forms[0].prop.focus;
            }
           };
     
       function fun(shnum){
       alert(shnum)
       };
     
       function funverify(){
        alert("Share number is Verified successfully");
       
        
       };
       
       function check_acnt(frm){
      
        var len=fnm.length;
             if(len==0){
               alert("Enter the Customer ID" )
              
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
      
      function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) || (cha >= 65 && cha <=90) ||cha==32) 
 		{
   			return true;
       	} else 

       	{
   			return false;
       	}
	};
      
      function  alphaonly(eve){


              var cha = event.keyCode


            if ( (cha>=65 && cha<=90)||(cha>=97 && cha<=122||cha==32)  ) {
             
                return true ;
              
            }
            else{
             alert("Numbers not allowed");
            return false ;
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
     
     
     function setFlag(flagValue){

     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
    
     }
     function generateAcno(){
     if(document.forms[0].verifyCh.value!=""){
     if(document.forms[0].verifyCh.value=="C"){
         document.getElementById("Cash1").style.display='block';
         document.getElementById("Cash2").style.display='block';
         document.getElementById("Transfer").style.display='none';
          document.getElementById("Transfer1").style.display='none';
      }
      
     else if(document.forms[0].verifyCh.value=="T"){
         document.getElementById("Cash1").style.display='none';
         document.getElementById("Cash2").style.display='none';
         document.getElementById("Transfer").style.display='block';
          document.getElementById("Transfer1").style.display='block';
      }
     }
     
     if(document.forms[0].pay_type.value!=""){
     
      if(document.forms[0].pay_type.value=="C"){
         document.getElementById("Cash1").style.display='block';
         document.getElementById("Cash2").style.display='block';
         document.getElementById("Transfer").style.display='none';
          document.getElementById("Transfer1").style.display='none';
      }
      
     else if(document.forms[0].pay_type.value=="T"){
         document.getElementById("Cash1").style.display='none';
         document.getElementById("Cash2").style.display='none';
         document.getElementById("Transfer").style.display='block';
          document.getElementById("Transfer1").style.display='block';
      }
    
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
     function nomPer(perVal){
     var nomper=parseInt(document.forms[0].percentage.value);
     if(nomper<100){
     alert("Nominee Percentage should be 100%");
     document.forms[0].percentage.requestFocus();
     return false;
     }
     else{
     document.forms[0].sharesubmit.requestFocus();
     return true;
     }
     
     }
    
    
    </script>
 

  </head>
  <body onload="generateAcno()" class="Mainbody">
  
  <%!
     ModuleObject[] array_module;
     String[] sh_type,div_paymode,paymode,details;
     ShareCategoryObject[] sh_cat;
     BranchObject[] br_obj,branchObjects;
     ShareMasterObject shobj;
     String jsppath,totamt,date;
     Boolean flag;
     Hashtable hash_type,hash_div;
     String[] values,sign_det;
     ShareParamObject[] shparam;
     Double total_sh_amt;
     Integer sharenumber=0;
     Boolean vis;
     AccountObject acc_obj;
     ShareMasterObject[] sh_obj;
     CustomerMasterObject cust_obj;
     String msg;
     double shrbal=0.0;
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
  System.out.println("request.getAttribute----->"+request.getAttribute("Bal"));
     
     shobj=(ShareMasterObject)request.getAttribute("ShareDetails");
     vis=(Boolean)request.getAttribute("Disable2");
     acc_obj=(AccountObject)request.getAttribute("cashamt");
     System.out.println("The boooooooooooooooooooooooolean is "+vis);
     String shareAcNo=(String)request.getAttribute("ShareAcNo");
     String msg=(String)request.getAttribute("msg");
     String msg1=(String)request.getAttribute("msg1");
  String panelName=(String)request.getAttribute("panelName");
  String displaysh=(String)request.getAttribute("displaysh");
  branchObjects=(BranchObject[])request.getAttribute("branch");
     %>
  
  <core:if test="<%=msg!=null %>">
  <font color="red"><%=msg %></font>
  <br><br>
  </core:if>
  <core:if test="<%=msg1!=null %>">
  <font color="red"><%=msg1 %></font>
  <br><br>
  </core:if>
  
    <%
   
    if(shareAcNo!=null){
    %>
    <table background="white">
    <tr><td>Your Share Account No is: <%=shareAcNo %></td></tr>
    </table>
    <%} %>
     <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
    <table>
     <td>                                                                                                                                   
    <html:form action="/Share/Allotment?pageId=4001" focus="<%=""+request.getAttribute("focusto")%>" >
     <html:hidden property="flag"></html:hidden>
     
     <html:hidden property="newnumber"></html:hidden>
     <html:hidden property="verifyCh"></html:hidden>
      <table style="border:thin solid #339999;" align="left" valign="top" class="txtTable">
     
         
      <tr>
        
        <td><bean:message key="label.acType"></bean:message></td>
        
        <td><html:select property="actype" styleClass="formTextFieldWithoutTransparent">
          <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}">
          <html:option value="${acType.moduleCode}">
          <core:out value="${acType.moduleAbbrv}"></core:out>
          </html:option>
          </core:forEach>
        </html:select>
        
        <html:text property="acnum"  styleClass="formTextFieldWithoutTransparent" onchange="submit()" onkeypress="return numbersonly()" onkeydown="validfn(acnum.value,'Account Number',acnum)"></html:text></td>
        <html:hidden property="ac_not_fnd" styleId="ac"></html:hidden>

      </tr>
      
      <tr>
        <td><bean:message key="label.cust"></bean:message></td>
       <core:choose>
       <core:when test="${empty requestScope.ShareDetails}">
       <td><html:text property="cid" styleClass="formTextFieldWithoutTransparent" onkeydown="validfn(cid.value,'Customer ID',cid)" onchange="submit()" onkeypress="return numbersonly()"></html:text></td>
       <html:hidden property="cid_not_fnd" styleId="cid_num"></html:hidden>
       </core:when>
       <core:otherwise>
       <td> <html:text property="cid" styleClass="formTextFieldWithoutTransparent"  onchange="submit()" value="<%=""+shobj.getCustomerId()%>" onkeypress="return numbersonly()" readonly="<%=(Boolean)request.getAttribute("disablefield") %>"></html:text></td>
       </core:otherwise>
       </core:choose>
      </tr>
      
      <tr>
      <td><bean:message key="label.appdt"></bean:message></td>
      <core:choose>
      <core:when test="${empty requestScope.ShareDetails}">
      <td><html:text property="appdate" value="${requestScope.date}" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
      </core:when>
      <core:otherwise>
      <td><html:text property="appdate" value="<%=""+shobj.getIssueDate()%>" styleClass="formTextFieldWithoutTransparent" readonly="<%=(Boolean)request.getAttribute("disablefield") %>"></html:text></td>
      </core:otherwise>
      </core:choose>
      </tr>
 	  		    
      <tr>
      <td><bean:message key="label.sh_type"></bean:message></td>
      <td><html:select property="sh_type"  styleClass="formTextFieldWithoutTransparent">
      
      
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
        	System.out.println("++++++++++++++++++++++"+hash_type.get(shobj.getShareStatus()));
      %>
      <html:option value="<%=""+hash_type.get(shobj.getShareStatus())%>" disabled="<%=(Boolean)request.getAttribute("disablefield") %>"><%=""+hash_type.get(shobj.getShareStatus())%></html:option>
      <%
        }
      %>
     </html:select></td>
      </tr>
     
      <tr>
      <td><bean:message key="label.sh_cat"></bean:message></td>
      
     
      <td><html:select property="sh_cat" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
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
      <html:option value="${sh_cat.shCat}" disabled="<%=(Boolean)request.getAttribute("disablefield") %>">
      <core:out value="${sh_cat.catName}"></core:out>
      </html:option>
      </core:forEach>
      </core:otherwise>
      </core:choose>
     
      </html:select></td>
      </tr> 
      
      <tr>
      <td><bean:message key="label.branch"></bean:message></td>
      <td><html:select property="branch" styleClass="formTextFieldWithoutTransparent">
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
      <html:option value="${requestScope.Branch[requestScope.ShareDetails.branchCode-1].branchCode}" disabled="<%=(Boolean)request.getAttribute("disablefield") %>">
      <core:out value="${requestScope.Branch[requestScope.ShareDetails.branchCode-1].branchName}"></core:out>
      </html:option>
      </core:otherwise>
      </core:choose>
      </html:select></td>
      </tr>
          
     <tr>
     <td><bean:message key="label.sh_val"></bean:message></td>
     <td><html:text property="sh_val" value="100.00" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
     
     </tr>
     
     <tr>
     <td><bean:message key="label.no_of_shares"></bean:message></td>
     <core:choose>
     <core:when test="${empty requestScope.ShareDetails}">
     <td><html:text property="num_shares" styleClass="formTextFieldWithoutTransparent" onblur="validfn(num_shares.value,' number of Shares',num_shares)" onchange="submit()" onkeypress="return numbersonly()" onkeyup="numberlimit(this,'5')"></html:text></td>
     </core:when>
     <core:otherwise>
     <td><html:text property="num_shares" styleClass="formTextFieldWithoutTransparent" onchange="submit()" value="${requestScope.ShareDetails.numberofShares}" onkeypress="return numbersonly()" readonly="<%=(Boolean)request.getAttribute("disablefield") %>"></html:text></td>  
     </core:otherwise>
     </core:choose>
     </tr>
     
     <tr>
     <td><bean:message key="label.tot_shares"></bean:message></td>
    
     <%if(shobj==null){ %>
     <%
        totamt=(String)request.getAttribute("Total");
        total_sh_amt=(Double)request.getAttribute("Sh_cal_amt");
  %>
    
     <%
        if(totamt==null){
     %>
     <td><html:text property="sh_amt" value="0" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
     <%
        }else{
        	System.out.println("The amount multiplied"+totamt);
     %>
     <td><html:text property="sh_amt" styleClass="formTextFieldWithoutTransparent" value="<%=""+totamt %>" readonly="<%=(Boolean)request.getAttribute("disablefield") %>" onchange="submit()"></html:text></td>
   
     <%
        }}else{
        	System.out.println("Balance"+shobj.getShareBalance()+"Value"+shobj.getShareVal());
     %>
     <td><html:text property="sh_amt" styleClass="formTextFieldWithoutTransparent" value="<%=""+shobj.getShareVal()* shobj.getNumberofShares()%>" readonly="<%=(Boolean)request.getAttribute("disablefield") %>" onchange="submit()"></html:text></td>
     <%
        }
     %>
     </tr>
    
     <tr>
     <td><bean:message key="label.intrAcType"></bean:message></td>
     <td><html:select property="intro_type" styleClass="formTextFieldWithoutTransparent">
     <core:forEach var="intro_type" items="${requestScope.AcType}">
        <html:option value="${intro_type.moduleCode}">
        <core:out value="${intro_type.moduleAbbrv}"></core:out>
        </html:option>
     </core:forEach> 
     </html:select>
     
     <core:choose>
     <core:when test="${empty requestScope.ShareDetails}">
     <html:text property="intro_ac_num" value="0" onchange="submit()" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly()" onblur="validfn(intro_ac_num.value,'Introducer Account Number',intro_ac_num)" onkeyup="numberlimit(this,'11')"></html:text></td>
     <html:hidden property="intr_not_fnd" styleId="intr"></html:hidden>
     </core:when>
     <core:otherwise>
     <html:text property="intro_ac_num" styleClass="formTextFieldWithoutTransparent" onchange="submit()" value="${requestScope.ShareDetails.introducerAccno}"   onkeypress="return numbersonly()" readonly="<%=(Boolean)request.getAttribute("disablefield") %>" onkeydown="validfn(intro_ac_num.value,'Introducer Account Number',intro_ac_num)"></html:text></td>
     </core:otherwise>
    </core:choose>
     
     
     </tr>
   
     <tr>
     <td><bean:message key="label.details"></bean:message></td>
          
     <td><html:select property="combo_details" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
     
     <core:forEach var="details" items="${requestScope.Details}">
     <html:option value="${details}">
     <core:out value="${details}"></core:out></html:option>
     </core:forEach>
     </html:select></td>
     </tr>
   
   <tr style="visibility:<%=""+request.getAttribute("vistable") %> ">
    <td>
     
     <%cust_obj=(CustomerMasterObject)request.getAttribute("personalInfo"); %>
     <tr>
        <td>Name</td>
        <%if(cust_obj!=null){ %>
       <td><html:text property="sname" styleClass="formTextFieldWithoutTransparent" value="<%=""+cust_obj.getFirstName() %>" onkeypress="return alphaonly()" disabled="true"></html:text></td>
      <%}else{ %>
       <td> <html:text property="sname" styleClass="formTextFieldWithoutTransparent"   onkeypress="return alphaonly()" readonly="<%=(Boolean)request.getAttribute("disablefield") %>"></html:text></td>
      <%} %>
      </tr>
                  
      <tr>
        <td>Signature Operation</td>
       <core:choose>
       <core:when test="${empty requestScope.ShareDetails}">
       <td><html:text property="sign" styleClass="formTextFieldWithoutTransparent"  onkeypress="return alphaonly()"></html:text></td>
      
       </core:when>
       <core:otherwise>
       <td> <html:text property="sign" styleClass="formTextFieldWithoutTransparent"  value="Single" onkeypress="return alphaonly()" readonly="<%=(Boolean)request.getAttribute("disablefield") %>"></html:text></td>
       </core:otherwise>
       </core:choose>
      </tr>
     
      </td>
  </tr>
    
    <!--<tr  style="visibility:<%=""+request.getAttribute("vis") %> ">
        <td>Nominee Name & No</td>
       <core:choose>
       <core:when test="${empty requestScope.ShareDetails}">
       <td><html:text property="nom_name" styleClass="formTextFieldWithoutTransparent"  onkeypress="return alphaonly()"></html:text></td>
      
       </core:when>
       <core:otherwise>
       <td> <html:text property="nom_name" styleClass="formTextFieldWithoutTransparent"  value="<%=""+shobj.getNom_name()%>" onkeypress="return alphaonly()" readonly="<%=(Boolean)request.getAttribute("disablefield") %>"></html:text></td>
       </core:otherwise>
       </core:choose>
      
    </tr>
    
    
     --><tr>
     
     <td>
     <font color="#996699" face="Garmond" size="2">
     <u><bean:message key="label.app_det"></bean:message></u></font> </td>
     </tr>
     
     <tr> 
     <td ><bean:message key="label.div_pay_mode"></bean:message></td>
     <td ><html:select property="div_paymode" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
     <%hash_div=(Hashtable)request.getAttribute("Divpaymode");%>
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
        	System.out.println("Checking 6"+hash_div+shobj+shobj.getPayMode());
     %>
     <html:option value="<%=""+hash_div.get(shobj.getPayMode())%>" disabled="<%=(Boolean)request.getAttribute("disablefield") %>"    ><%=""+hash_div.get(shobj.getPayMode())%></html:option>
     <%
        }
     %>
     </html:select></td>
     </tr>
     
     
     
     
     
     
     
     
     
     <tr>
    
     <td><bean:message key="label.div_ac-type"></bean:message></td>
     <td><html:select property="div_ac_type" disabled="<%=(Boolean)request.getAttribute("Butt_disable")%>"  styleClass="formTextFieldWithoutTransparent">
     <%array_module=(ModuleObject[])request.getAttribute("AcTypes");
     
     %>
     <core:choose>
     <core:when test="${empty requestScope.ShareDetails}">
     <core:forEach var="div_ac_types" items="${requestScope.AcTypes}">
     <html:option value="${div_ac_types.moduleCode}">
     <core:out value="${div_ac_types.moduleAbbrv}"></core:out>
     </html:option>
     </core:forEach>
     </core:when>
     <core:otherwise>
     <html:option value="${requestScope.ShareDetails.paymentAcctype}" disabled="<%=(Boolean)request.getAttribute("disablefield") %>">
     <core:out value="${div_ac_types[requestScope.ShareDetails.paymentAcctype].moduleAbbrv}"></core:out>
     </html:option>
     </core:otherwise>
     </core:choose>
     </html:select>

     <bean:message key="label.div_ac_num"></bean:message>
      <core:choose>
      <core:when test="${empty requestScope.ShareDetails}">
     <html:text property="div_ac_num"  styleClass="formTextFieldWithoutTransparent" size="6" disabled="<%=(Boolean)request.getAttribute("Disable")%>" onkeypress="return numbersonly()" onblur="setFlag('from_div_acno')" onkeyup="numberlimit(this,'11')"></html:text></td>
      </core:when>
      <core:otherwise>
      <html:text property="div_ac_num" styleClass="formTextFieldWithoutTransparent" size="6" value="${requestScope.ShareDetails.paymentAccno}" onkeypress="return numbersonly()"  readonly="<%=(Boolean)request.getAttribute("disablefield") %>" onblur="setFlag('from_div_acno')" disabled="<%=(Boolean)request.getAttribute("Butt_disable")%>"></html:text></td>
      </core:otherwise>
      </core:choose>
     
     </tr>
     
     
      <tr>
     <td><bean:message key="label.pay_type"></bean:message></td>
     <td><html:select property="pay_type" onchange="submit()" onblur="submit()" styleClass="formTextFieldWithoutTransparent">
     <%hash_div=(Hashtable)request.getAttribute("Paymode");%>
     <% System.out.println("Checking here3");
     String[][] scroll_nos=(String[][])request.getAttribute("UnverifiedScrNos");
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
     <html:option value="<%=""+hash_div.get(shobj.getRecievedMode())%>" disabled="<%=(Boolean)request.getAttribute("disablefield") %>"><%=""+hash_div.get(shobj.getRecievedMode())%></html:option>
     <%
        }
     %>
     </html:select></td>
     </tr>
    
    
    
     <tr id="Cash1" style="display:block;">
       <td>
        <bean:message key="label.scrNo"></bean:message></td>
        <%if(shobj!=null){ %>
        <td><html:text property="sr_no" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly()" value="<%=""+shobj.getRecievedAccno() %>" size="6"></html:text></td>
       <%}else{ %> 
       <td><html:text property="sr_no" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly()" onchange="submit()"  size="6" onkeyup="numberlimit(this,'11')"></html:text>
       
       </td>
       <%} %>
       <td>
        <bean:message key="label.date"></bean:message></td>
        <td><html:text property="date" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly()" value="<%=""+(String)request.getAttribute("date") %>" disabled="true"></html:text></td>
        
       
     </tr>
     
     <tr id="Cash2" style="display:none;">
      <td>
        <bean:message key="label.name"></bean:message></td>
        <%if(acc_obj!=null){ %>
        <td><html:text property="name" styleClass="formTextFieldWithoutTransparent" value="<%=""+acc_obj.getAccname()%>" disabled="true"></html:text></td>
         <%}else{ %>
        <td><html:text property="name" styleClass="formTextFieldWithoutTransparent" disabled="true" ></html:text></td>
      <%} %>
      
      <td>
        <bean:message key="label.amount"></bean:message></td>
        <%if(acc_obj!=null){ %>
        <td><html:text property="amt" styleClass="formTextFieldWithoutTransparent" size="6" value="<%=""+acc_obj.getAmount() %>" disabled="true"></html:text></td>
        <%}else{ %>
        <td><html:text property="amt" styleClass="formTextFieldWithoutTransparent" size="6" disabled="true"></html:text></td>
        <%} %>
     </tr>
     
     <tr id="Transfer" style="display:block">
    <% 
    System.out.println("displaysh is null----->>>>");
    %>
    
     <td><bean:message key="label.acType"></bean:message></td>
     <td><html:select property="pay_ac_type" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
     <core:forEach var="acTypes" items="${requestScope.AcTypes}">
     <html:option value="${acTypes.moduleCode}">
     <core:out value="${acTypes.moduleAbbrv}"></core:out>
     </html:option>
     </core:forEach>
     
     </html:select>
     <%
     
       if(shobj!=null){
     %>
     <html:text property="pay_ac_no" size="6" value="<%=""+shobj.getRecievedAccno() %>" styleClass="formTextFieldWithoutTransparent" readonly="<%=(Boolean)request.getAttribute("disablefield") %>"></html:text></td>
     <%
       }else{
     %>
     <html:text property="pay_ac_no" size="6" onchange="submit()" onkeypress="return numbersonly()" styleClass="formTextFieldWithoutTransparent" onkeyup="numberlimit(this,'11')"></html:text></td>
     <%
       }
     %>
     </tr>
    
     <tr id="Transfer1" style="display: block">
     <td><bean:message key="label.balance"></bean:message></td>
     <%
     
     if(shobj!=null){ %>
     <td><html:text property="balance" size="8" value="<%=""+shobj.getAcc_balance()%>" onchange="submit()" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>
     <%
     }else{
     %>
     <td><html:text property="balance" size="8"  value="<%=""+request.getAttribute("Bal") %>" onchange="submit()" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>
      <%
        }
      %>
     <bean:message key="label.amount"></bean:message>
     
     <%if(shobj==null){%>
    	 <% total_sh_amt=(Double)request.getAttribute("Sh_cal_amt"); %>
    	 
    
    <%if(total_sh_amt==null){ %>	
      <html:text property="amount" size="6"  onchange="submit()" onkeypress="return numbersonly()" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>    	 

     <%} else{
    	 
    	 System.out.println("The amount cal by suraj in jsp in else"+total_sh_amt);
     %>
     <html:text property="amount" size="6" value="<%=""+total_sh_amt %>"  onchange="submit()" readonly="true" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly()"></html:text></td>
        <%
     }} if(shobj!=null){
    	 System.out.println("GONG TO SHARE MASETR OBJECT");
        %>  
         <html:text property="amount" size="6" value="<%=""+shobj.getAmount() %>"   onchange="submit()" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly()" readonly="<%=(Boolean)request.getAttribute("disablefield") %>"></html:text></td>
     
     <%} %>
     </tr>
    
    
    
     <tr>
     <td>
     
     <table class="txtTable" >
     
     
     <%
         
        values=(String[])request.getAttribute("PaymentDetails");
        if(values!=null){
           
     %>
     <tr>
     
     <td>
     <font color="#996699" face="Garmond" size="2">
     <u><bean:message key="label.payment"></bean:message></u></font></td>
     </tr>
      
     <tr>
     <td><bean:message key="label.sh_fee"></bean:message></td>
     <td><html:text property="sh_fee" value="<%=""+values[0]%>" readonly="true" styleClass="formTextField"></html:text></td>
     </tr>
     <tr>
     <td><bean:message key="label.ad_fee"></bean:message></td>
     <td><html:text property="ad_fee" value="<%=""+values[1]%>" readonly="true" styleClass="formTextField"></html:text></td>
     </tr>
     <tr>
     <td><bean:message key="label.sale_form"></bean:message></td>
     <td><html:text property="sh_sale" value="<%=""+values[2]%>" readonly="true" styleClass="formTextField"></html:text></td>
     </tr>
     <tr>
     <td><bean:message key="label.misc"></bean:message></td>
     <td><html:text property="misc" value="<%=""+values[3]%>" readonly="true" styleClass="formTextField"></html:text></td>
     </tr>
     
      <tr>
     
     <td><bean:message key="label.tot_sh_val"></bean:message></td>
    
     <td><html:text property="totshval" value="<%=""+values[5]%>" readonly="true" styleClass="formTextField"></html:text></td>
     </tr>
     <tr>
     <td><bean:message key="label.amt_rcvd"></bean:message></td>
     <td><html:text property="amt_rcvd" value="<%=""+values[4]%>" readonly="true" styleClass="formTextField"></html:text></td>
     </tr>
     <tr>
      <td>Click here for Adding the Nominee Details</td>
      <td><html:checkbox property="nomineeChoice" value="yes" onclick="setFlag('AddNominee')"></html:checkbox>
     </tr>
    <%
        } 
    %>
    
    </table>
    </td>
    </tr> 
    <%!
    CustomerMasterObject cusobj;
    %>
    <%
    cusobj=(CustomerMasterObject)request.getAttribute("custdetails");
    String nomi=(String)request.getAttribute("addnomineechoice");
    if(nomi!=null){
    %> 
    <tr>
      <td>
          <table class="txtTable" >
          <tr>
               <td>
                 <font color="#996699" face="Garmond" size="2">
                 <u>Add Nominee</u>
                 </font>
               </td>
               <td>
               &nbsp;
               </td>
          </tr>
          <tr>
    <td>Has Account</td>
    <td><html:checkbox property="has_ac" styleClass="formTextFieldWithoutTransparent" onclick="setFlag('nomineeHasAccount')" value="hasAccount"></html:checkbox> </td>
   </tr>
   <tr style="visibility: <%=""+request.getAttribute("vis") %>">
    <td><bean:message key="label.custid"></bean:message></td>
    <td><html:text property="nomCid" styleClass="formTextFieldWithoutTransparent" onblur="setFlag('nomineeCid')" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"></html:text></td>
   </tr>
   <tr>
    <td><bean:message key="label.name"></bean:message></td>
    <%if(cusobj!=null){ %> 
    <td><html:text property="nomName" value="<%=""+cusobj.getName() %>" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text> </td>
    <%}else{ %>
    <td><html:text property="nomName"  styleClass="formTextFieldWithoutTransparent" onkeypress="return only_alpha()"></html:text> </td>
    <%} %>
   </tr>
   
   <tr>
     <td><bean:message key="label.dob"></bean:message> </td>
     <%if(cusobj!=null){ %>
     <td><html:text property="dob" value="<%=""+cusobj.getDOB() %>" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text> </td>
    <%}else{
    %>
     <td><html:text property="dob" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text> </td>
     <%} %>
   </tr>
   
   <tr>
    <td><bean:message key="label.gender"></bean:message></td>
    <%if(cusobj!=null){ %>
    <td><html:text property="gender" value="<%=""+cusobj.getSex() %>" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
    <%}else{ %>
    <td><html:text property="gender" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_alpha()"></html:text></td>
    <%} %>
   </tr>
   
   <tr>
    <td><bean:message key="label.address"></bean:message></td>
      <%if(cusobj!=null){ %>
    <td><html:textarea property="address" value="<%=""+cusobj.getAddressProof()%>" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:textarea></td>
        <%}else{ %>
    <td><html:textarea property="address" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_for_address()"></html:textarea></td>
     <%} %>
   </tr>
   
   <tr>
     <td><bean:message key="label.rel"></bean:message></td>
     <td><html:text property="rel_ship" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_alpha()"></html:text></td>
   </tr>

   <tr>
    <td><bean:message key="label.percentage"></bean:message></td>
    <td><html:text property="percentage" styleClass="formTextFieldWithoutTransparent" onblur="nomPer(this.value)" onkeypress="return only_numbers()"></html:text></td>
   </tr>
   
          </table>
      </td>
    </tr> 
     <%} %>
     
   

     <tr>
     <html:hidden property="validations"></html:hidden>
     <html:hidden property="forward" value="error"></html:hidden>
     
     
     
     <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
     <%if(msg1!=null){ %>
     <td align="right"><html:button property="sharesubmit"  onclick="setFlag('submit')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.submit"></bean:message> </html:button>
     <%}else{ %>
     <td align="right"><html:button property="sharesubmit"  onclick="setFlag('submit')" styleClass="ButtonBackgroundImage" disabled="<%=(Boolean)request.getAttribute("Disable")%>" ><bean:message key="label.submit"></bean:message> </html:button>
     <html:submit onclick="set('verify')" disabled="<%=(Boolean)request.getAttribute("Disable2")%>" styleClass="ButtonBackgroundImage"><bean:message key="label.verify" ></bean:message></html:submit>
     <%}}else{ %>
     <td align="right"><html:button property="sharesubmit"  onclick="setFlag('submit')" styleClass="ButtonBackgroundImage" disabled="true" ><bean:message key="label.submit"></bean:message> </html:button>
     <html:submit onclick="set('verify')" disabled="true" styleClass="ButtonBackgroundImage"><bean:message key="label.verify" ></bean:message></html:submit>
     <%} %>
     <!--
     <html:submit onclick="set('update')" disabled="<%=(Boolean)request.getAttribute("Disable2")%>" styleClass="ButtonBackgroundImage"><bean:message key="label.update"></bean:message>  </html:submit></td>-->
     
     </td>
     <td>
      <%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>   
     <html:submit onclick="set('delete')" styleClass="ButtonBackgroundImage"><bean:message key="label.delete"></bean:message> </html:submit>
     <%}else{ %>
     <html:submit onclick="set('delete')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.delete"></bean:message> </html:submit>
     <%} %>
     </td><td><html:button property="resetbutton" value="reset" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button></td>
</tr>



      </table>
</html:form>     
<input type="hidden" name="hid" value="<%=request.getParameter("operation")%>">

<td>
 <table>
   <tr>
   <%
    String strn=(String)request.getAttribute("names");
	System.out.println(strn) ;
	if(strn!=null){

%>
<jsp:include page="<%=strn%>"></jsp:include>
<%

}%>

   </tr>
 </table>
 
 <table align="left"  valign="top" width="10%">
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
</td>
</td>
</table>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

 </body>
</html>

