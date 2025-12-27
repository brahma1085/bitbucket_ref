
<%@ page import="masterObject.general.ModuleObject"%>
<%@ page import="masterObject.frontCounter.AccountMasterObject"%>
<%@ page import="masterObject.customer.CustomerMasterObject"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="bean"
	uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
 
by Mohsin--%>
<%@page import="masterObject.general.BranchObject"%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Map"%>
<html>
	<head>

		<script type="text/javascript">
		
		function only_for_address()
		{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >= 47 && cha <=57)||cha==32||cha==45||cha==35) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
		
       function detailsubmit(){
        
        document.forms[0].submit();
        
       };
       
             function set(target){
             
         
         	document.forms[0].forward.value=target;
         	
         };
         
           function button_insert(target)
          {
          
         
          var entryval;
               <%System.out.println("Inside onclick of submit button in JSP--1");%>
           
          	
          	    <%System.out.println("Inside onclick of submit button in JSP--2");%>
          	document.forms[0].forward.value=target;
          	if(target=='delete'){
          	entryval=confirm("Are you sure you want to delete this Account?");
          	
          	}
          	else{
            entryval=confirm("Are you sure you want to go ahead with operation ?");
            }
           
            if(entryval)
            	{
            
           		 	var a=beforesubmiting();
           		
           		 	    <%System.out.println("Inside onclick of submit button in JSP--3");%>
           		if(a)
           			{
           			
           			 document.forms[0].submit();
           			
           			 reset123();
           			 return true;
           			 
           			 }
           				
           		else{
           				document.forms[0].forward.value='';
           			 }
           		
         		}
          	else 
          		{
          			document.forms[0].forward.value='';
          				return false;
          		}
       
         };   
         
        function age_calc(da){
	  		var date1=da.value;
	  		var date=new Date(); 
	  		var dd=date1.split('/');
	  		if(dd.length==3)
	  		{
	  		var ccyear=date.getYear();
	  		var uuyear=dd[2]; 	
	  		var ageval=(parseInt(ccyear)-parseInt(uuyear));
	  		document.forms[0].nomage.value=ageval;
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
         	{	txt.value="";
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!");
         		
         		return false;
          	}
         };
         
                  
         function CheckAll(){
           var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="text")
    {
    if(ele[i].value==""){
    
    
    return false;
	}
	else{
	 
	return false;
	}
    }
    }
       return false;  
         }
       
      
		
		//validate transfer account numbers
		function validateAccountNumber(){
		
		document.forms[0].flag.value="1";
		document.forms[0].submit();
		
		}
       
       
       function disabledetails(){
       document.forms[0].nomname.disabled='disabled';
       
       }
       
        function datevalidation(ids){
    	var format = true;
        var allow=true;
    	var ff=ids.value;
    	var dd=ff.split('/');
    	
    	var ss=document.forms[0].sysdate.value;
    	var dds=ss.split('/');
    	
    	if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          alert (  " Date format should be:DD/MM/YYYY" );
                         ids.value="";
                          format = false ;
                          allow=false;
                          

                      } else if (  i==2 && dd[i].length != 4 ){

                           alert (  " Date format should be:DD/MM/YYYY " );
                          ids.value="";
                           format = false ;
                           allow=false;
                      }

            	 }
             } else{
                    allow=false;
             		format = false ;
             		alert (  " Date format should be:DD/MM/YYYY " );
             		document.forms[0].fdate.value="";
             }
            if(allow){
            	
            	var day=dd[0];
            	var month=dd[1];
            	var year=dd[2];
            	var fdays;
            	if(month==2)
            	{
            	if((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0)))
            	{
            		if(day>29)
            		{
            			alert("Days should be less than 29 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	else
            	{
            		if(day>28)
            		{
            			alert("Days should be less than 28 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	}
            	
            	if(month>1 || month<12){
            	if(month == 4 || month==6||month==9||month==11)
            	{
            		if(day>30)
            		{
            			alert("Days should be less than 31 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            	}
            	else
            	{
            		if(day>31)
            		{
            			alert("Days should be less than 32 only");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}
            		
            	}
            	}
            	if(month>12)
            	{
            		alert("Month should  be greater than 0 and lesser than 13");
            		ids.value="";
                           format = false ;
                           allow=false;
            	}
            	
            	if(year<1900 || year>9999)
            	{
            		alert("Year should be in between 1900 to 9999 ");
            		ids.value="";
                           format = false ;
                           allow=false;
            	}
            	if(dd[0].length==2||dd[1].length==2||dd[2].length==4)
            	{
            		if(dd[2]>dds[2])
            		{
            			alert(" year should be less than or equal to"+dds[2]+" !");
            			ids.value="";
                           format = false ;
                           allow=false;
            		}	
            		else{
            			if(dd[2]==dds[2]){
            				if(dd[1]>dds[1]){
	            				alert(" Month should be less than or equal to"+dds[1]+" !");
	            				ids.value="";
	                           	format = false ;
	                          	 allow=false;		
            						
            				}else{
            					if(dd[1]==dds[1]){
            						if(dd[0]>dds[0]){
											alert(" Day should be less than or equal to"+dds[0]+" !");
				            				ids.value="";
				                           	format = false ;
				                          	allow=false;           							
            						}else{
            								format = true ;
				                          	allow=true; 
            						}
            					
            					}
            				}
            			}
            		}
            	}            	          	
             }
        }
        
        //sudhakar 09/09/2011
        function button_second()
        {
        alert("already Account Number is generated for this details ");
        reset123();
        }
        
        
function disval(){
  if(eval("document.forms[0].jointh.checked") == true){

document.forms[0].jointcid.disabled=false;
}
else{document.forms[0].jointcid.disabled=true;}
        
        }
        
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
    
    show(false,'div1');
    document.forms[0].nomaddress.value="";
    document.forms[0].acNum.value="0";
    document.forms[0].cid.value="0";
    }
    
    
    function Beforesubmit(){
    var a=document.forms[0].acNum.value;
    if(parseInt(a)>0)
    {
    document.forms[0].beforesub.value='unverified';
    
    }
    else{
    
    document.forms[0].beforesub.value='';    
    }
    document.forms[0].submit();
    }
    
    var ns4 = (document.layers) ? true : false;
var ie4 = (document.all && !document.getElementById) ? true : false;
var ie5 = (document.all && document.getElementById) ? true : false;
var ns6 = (!document.all && document.getElementById) ? true : false;

function show(sw,obj) {
  // show/hide the divisions
  if (sw && (ie4 || ie5) ) document.all[obj].style.visibility = 'visible';
  if (!sw && (ie4 || ie5) ) document.all[obj].style.visibility = 'hidden';
  if (sw && ns4) document.layers[obj].visibility = 'visible';
  if (!sw && ns4) document.layers[obj].visibility = 'hidden';
}
    
    
    
    
    
    function beforesubmiting(){
    if(document.forms[0].acNum.value==""){
    alert("Please Enter Proper Account Number");
    document.forms[0].acNum.style.background='#FF0000';
    document.forms[0].acNum.focus();
    return false;
    }
    else if(document.forms[0].cid.value==""||document.forms[0].cid.value=='0'){
    alert("Please Enter Proper CID");
    document.forms[0].cid.focus();
     document.forms[0].cid.style.background='#FF0000';
    return false;
    }
    else if(document.forms[0].introAcNum.value==""){
    alert("Please Enter Proper Introducer Account No.");
    document.forms[0].introAcNum.focus();
     document.forms[0].introAcNum.style.background='#FF0000';
    return false;
    }
    else if(document.forms[0].scrollNum.value==""){
    alert("Please Enter Proper Scroll Number");
    document.forms[0].scrollNum.focus();
    document.forms[0].scrollNum.style.background='#FF0000';
    return false;
    }
    else if(document.forms[0].chqbook.value=="SELECT"){
    alert("Please Select  Type Of Operation");
    document.forms[0].chqbook.focus();
    document.forms[0].chqbook.style.background='#FF0000';
    return false;
    }
    else if(document.forms[0].nom.checked==true){
    if(document.forms[0].check.checked==true){
    if(document.forms[0].nomcid.value==""){
    alert("Please Enter Nominee CID");
     document.forms[0].nomcid.focus();
     document.forms[0].nomcid.style.background='#FF0000';
    return false;
    }
    if(document.forms[0].nomcidrelation.value==""){
    alert("Please Enter Nominee Relationship");
    document.forms[0].nomcidrelation.focus();
    document.forms[0].nomcidrelation.style.background='#FF0000';
    return false;
    }
    }
    else{
    if(document.forms[0].nomage.value==""){
    alert("Please Enter Age of Nominee");
    document.forms[0].nomage.focus();
    document.forms[0].nomage.style.background='#FF0000';
    return false;
    }
    if(document.forms[0].nomname.value==""){
    alert("Please Enter Name of Nominee");
    document.forms[0].nomname.focus();
    document.forms[0].nomname.style.background='#FF0000';
    return false;
    }
    
    if(document.forms[0].nomdob.value==""){
    alert("Please Enter date of birth of Nominee");
    document.forms[0].nomdob.focus();
    document.forms[0].nomdob.style.background='#FF0000';
    return false;
    }
    
    if(document.forms[0].nomaddress.value==""){
    alert("Please Enter Address of Nominee");
    document.forms[0].nomaddress.focus();
    document.forms[0].nomaddress.style.background='#FF0000';
    return false;
    }
    if(document.forms[0].nomrelation.value==""){
    alert("Please Enter Relationship with Nominee");
    document.forms[0].nomrelation.focus();
    document.forms[0].nomrelation.style.background='#FF0000';
    return false;
    }
    }
 
    }
    
    
     else if(document.forms[0].jointh.checked==true){
   if(document.forms[0].jointcid.value==""){
    alert("Please Enter Jointholder's CID");
    document.forms[0].jointcid.focus();
    document.forms[0].jointcid.style.background='#FF0000';
    return false;
    }
    }
    else{
    return true;
    }
     return true;
    }
    
    function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||cha==32 ) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
    
  </script>
	</head>

	<title>AccountOpening</title>
	<font color="blue"><center>
			<h2 class="h2">
				SB/CA AccountOpening
			</h2>
		</center>
	</font>
	<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />


	<!--<style type="text/css">
          body{
              font-size:10px;
              font-family:serif;
              font-style:oblique;
              font-weight:bold;
              background:beige;
          }
          table{
               background:transperent; 
          }
          
    </style>
    -->
	<link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />


	<body class="Mainbody">
		<%!ModuleObject[] array_module;
	String details[];
	AccountMasterObject acountMaster;
	String jspPath, pagenew;
	ModuleObject[] mod;
	CustomerMasterObject cmObject;
	SBOpeningActionForm sbForm, sbformde;
	String pageId;
	AccountObject intro;
	Map user_role;
	String access;
	BranchObject[] branchObjects;%>
		<%
			branchObjects=(BranchObject[])request.getAttribute("branch");
			String msg = (String) request.getAttribute("msg");
			pageId = (String) request.getAttribute("path");
			sbformde = new SBOpeningActionForm();
			acountMaster = (AccountMasterObject) request
					.getAttribute("AccountDetails");
			sbForm = (SBOpeningActionForm) request.getAttribute("Account");
			intro = (AccountObject) request.getAttribute("Intr");
			String message = (String) request.getAttribute("msg");
			String getreceipt = (String) request.getAttribute("getreceipt");
			String verify = (String) request.getAttribute("verify");
			String subenable = (String) request.getAttribute("subenable");
			String visible = (String) request.getAttribute("visible");
			String nomdetail = (String) request.getAttribute("nomdetail");
			String setreceipt = (String) request.getAttribute("setreceipt");
			String nomdetailhasaccount = (String) request
					.getAttribute("nomdetailhasaccount");
			String accesspageId = (String) request.getAttribute("accesspageId");
			user_role = (Map) request.getAttribute("user_role");

			if (user_role != null && accesspageId != null) {
				if (user_role.get(accesspageId) != null) {
					access = (String) user_role.get(accesspageId);
					System.out.println("access-----In SBOpening------>"
							+ access);
				}

			}
		%>
		<%
			if (access != null && accesspageId != null
					&& access.toCharArray()[0] == '1') {
		%>

		<div id="div1" class="myLayersClass">
			<core:if test="<%=message!=null %>">
				<font color="red"><%=message%></font>
				
				<br>
				<br>
				<br>
				<br>
			</core:if>
		</div>

		<table align="left" valign="top" class="txtTable">
			<html:form action="/FrontCounter/SBOpening?pageId=3001"
				>
				<html:hidden property="defaultSignIndex" value="0" />
				<html:hidden property="defaultTab" value="PersonalDetails" />
				<html:hidden property="defSIndex" value="1" />
				<html:hidden property="forward" />
				<html:hidden property="beforesub" />
				<html:hidden property="dohere" />
				<html:hidden property="sysdate" />
				<td valign="top">
					<table class="txtTable" style="border: thin solid navy">
	
						
						<tr>
							<td>
								<bean:message key="label.acType"></bean:message>
							</td>
							<td>
								<html:select property="acType"
									styleClass="formTextFieldWithoutTransparent">
									<html:option value="SELECT">SELECT</html:option>
									<core:forEach var="acType" varStatus="count"
										items="${requestScope.AcType}">
										<html:option value="${acType.moduleCode}">
											<core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out>
										</html:option>
									</core:forEach>
								</html:select>
							</td>
						</tr>
						<tr>
							<td>
								<bean:message key="label.acNum"></bean:message>
							</td>
							<core:choose>
								<core:when test="${empty requestScope.AccountDetails}">
									<td>
										<html:text property="acNum"
											styleClass="formTextFieldWithoutTransparent" size="10"
											onblur="Beforesubmit()"
											onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')" ></html:text>
										<%
											Boolean acntNotFound = (Boolean) request
																	.getAttribute("AccountNotFound");
															Boolean newAcnt = (Boolean) request
																	.getAttribute("NewAccount");
										%>
										<core:if test="<%= acntNotFound!=null %>">
											<bean:message key="label.acntNotFound"></bean:message>
											<%
												System.out.println("i am here");
											%>
										</core:if>
										<core:if test="<%=newAcnt!=null %>">
											<bean:message key="label.newAccount"></bean:message>
										</core:if>
									</td>
								</core:when>
								<core:otherwise>
									<td>
										<html:text property="acNum"
											styleClass="formTextFieldWithoutTransparent" size="8"
											onchange="submit()"
											onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text>
									</td>
								</core:otherwise>
							</core:choose>
						</tr>
						<tr>
							<td>
								<bean:message key="label.cust"></bean:message>
							</td>
							<td>
								<html:text styleClass="formTextFieldWithoutTransparent"
									property="cid" size="8"
									onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text>
							</td>
							<td>
								<core:if test="${setreceipt!=null}">
									<html:text styleClass="formTextFieldWithoutTransparent"
										property="custname" size="25" onkeypress="return false;"></html:text>
								</core:if>
							</td>
						</tr>


						<tr>
							<td>
								<bean:message key="label.intrAcType"></bean:message>
							</td>
							<td>
								<html:select property="introAcType"
									styleClass="formTextFieldWithoutTransparent">

									<%
										array_module = (ModuleObject[]) request
															.getAttribute("IntroAcType");
									%>
									<core:choose>
										<core:when test="<%=acountMaster!=null%>">
											<core:if
												test="<%=(acountMaster.getIntrAccType().trim().length()!=0) %>">
												<core:forEach var="introAcType"
													items="${requestScope.IntroAcType}" varStatus="count">
													<core:if
														test="${requestScope.AccountDetails.intrAccType==introAcType.moduleCode}">
														<html:option
															value="${requestScope.IntroAcType[count.index].moduleCode}">${requestScope.IntroAcType[count.index].moduleAbbrv}</html:option>
													</core:if>
												</core:forEach>
											</core:if>
										</core:when>

										<core:otherwise>
											<html:option value="SELECT">SELECT</html:option>
											<core:forEach var="introAcType"
												items="${requestScope.IntroAcType}" varStatus="count">
												<html:option
													value="${requestScope.IntroAcType[count.index].moduleCode}">${requestScope.IntroAcType[count.index].moduleAbbrv}</html:option>
											</core:forEach>
										</core:otherwise>
									</core:choose>
								</html:select>
							</td>




							<td>
								<core:choose>
									<core:when test="<%=intro!=null%>">

										<html:text styleClass="formTextFieldWithoutTransparent"
											property="introAcNum" size="10"
											value="<%=""+intro.getAccno()%>" onblur="submit()" onkeyup="numberlimit(this,'10')"></html:text>
									</core:when>
									<core:when test="<%=acountMaster!=null %>">
										<html:text styleClass="formTextFieldWithoutTransparent"
											property="introAcNum" size="10" onchange="submit()"
											value="<%=""+acountMaster.getIntrAccNo() %>"
											onblur="submit()" onkeyup="numberlimit(this,'10')"></html:text>
									</core:when>
									<core:otherwise>
										<html:text styleClass="formTextFieldWithoutTransparent"
											property="introAcNum" size="10" onblur="submit()"
											onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text>
									</core:otherwise>
								</core:choose>

							</td>

						</tr>
						<tr>
							<td>
								<bean:message key="label.details"></bean:message>
							</td>
							<%!String path;%>
							<%
								//path="/SCBClient/CommonWebPages/Personnel.jsp?personalInfo="+request.getAttribute("personalInfo")+"&panelName";
							%>

							<td>
								<html:select property="detailsCombo" onblur="detailsubmit()"
									styleClass="formTextFieldWithoutTransparent">
									<html:option value="SELECT">SELECT</html:option>
									<%
										details = (String[]) request.getAttribute("Details");
													if (details != null) {
														for (int k = 0; k < details.length; k++) {
									%>
									<html:option value="<%=details[k]%>"><%=details[k]%></html:option>


									<%
										}
													}
									%>

								</html:select>
							</td>
						</tr>
						<core:if test="${getreceipt!=null}">
							<tr>

								<td>
									Joint/Single Operation
								</td>
								<td>
									<html:select property="chqbook">
										<html:option value="SELECT">SELECT</html:option>
										<html:option value="Single">Single</html:option>
										<html:option value="Joint">Joint</html:option>
									</html:select>
								</td>

							</tr>
							<!--<tr><td>Cheque Book Issued:</td>
   	                 <td><html:select property="chqbook">
   	                 <html:option value="F">No</html:option>
   	                 <html:option value="T">Yes</html:option>
   	                 </html:select></td>
            	</tr>
            	-->
							<tr>
								<td bgcolor="#0099CC">
									Nominee Details
								</td>
							</tr>
							<tr>
								<td>
									Add Nominee:
								</td>
								<td>
									<html:checkbox property="nom" value="1" onclick="submit()"></html:checkbox>
								</td>
							</tr>

							<core:if test="<%=visible!=null %>">
								<tr>
									<td>
										<html:checkbox property="check" value="checkin"
											onclick="submit()">Has Account</html:checkbox>
									</td>


								</tr>
								<core:if test="<%=nomdetailhasaccount!=null %>">
									<tr>
										<td>
											Nominee Cid:
										</td>
										<td>
											<html:text property="nomcid" onblur="submit()"
												onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text>
										</td>
									</tr>
									<tr>
										<td>
											Relationship:
										</td>
										<td>
											<html:text property="nomcidrelation" onkeypress="return only_alpha()"></html:text>
										</td>
									</tr>
									<tr>
										<td>
											Percentage:
										</td>
										<td>
											<html:text property="nompercentage" value="100"
												onkeypress="return false;" readonly="true"></html:text>
											<%="%"%></td>
									</tr>
								</core:if>
							</core:if>
							<core:if test="<%=nomdetail!=null %>">
								<tr>
									<td>
										Name:
									</td>
									<td>
										<html:text property="nomname" onkeypress="return only_alpha()"></html:text>
									</td>
								</tr>
								<tr>
									<td>
										DOB:
									</td>
									<td>
										<html:text property="nomdob" onblur="return datevalidation(this)" onkeyup="numberlimit(this,'11')" onkeypress="if (event.keyCode <46 || event.keyCode > 57) event.returnValue = false;"></html:text>
									</td>
								</tr>
								<tr>
									<td>
										Age:
									</td>
									<td>
										<html:text property="nomage"
											onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" ></html:text>
									</td>
								</tr>
								<tr>
									<td>
										Sex:
									</td>
									<td>
										<html:select property="nomsex">
											<html:option value="male">Male</html:option>
											<html:option value="female">Female</html:option>

										</html:select>
									</td>
								</tr>
								<tr>
									<td>
										Address:
									</td>
									<td>
										<html:textarea property="nomaddress" onkeypress="return only_for_address()"></html:textarea>
									</td>
								</tr>
								<tr>
									<td>
										Relationship:
									</td>
									<td>
										<html:text property="nomrelation" onkeypress="return only_alpha()" ></html:text>
									</td>
								</tr>
								<tr>
									<td>
										Percentage:
									</td>
									<td>
										<html:text property="nompercentage" value="100"
											onkeypress="return false;" readonly="true"></html:text>
										<%="%"%></td>
								</tr>

							</core:if>
							<tr>
								<td bgcolor="#0099CC">
									Joint Holder
								</td>
							</tr>
							<tr>
								<td>
									Add JointHolder:
								</td>
								<td>
									<html:checkbox property="jointh" value="1" onclick="disval()"></html:checkbox>
								</td>
							</tr>
							<tr>
								<td>
									JointHolder's Cid:
								</td>
								<td>
									<html:text property="jointcid" onblur="submit()"
										onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text>
								</td>
							</tr>

							<tr>
								<td bgcolor="#0099CC">
									Receipt Details
								</td>
							</tr>


							<tr id="cash1">
								<td>
									<bean:message key="label.scroll_no"></bean:message>
								</td>
								<td>
									<html:text property="scrollNum"
										styleClass="formTextFieldWithoutTransparent" onblur="submit()"
										onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text>
								</td>
							</tr>
							<tr id="cash2">
								<td>
									Name:
								</td>
								<td>
									<html:text property="name"
										styleClass="formTextFieldWithoutTransparent"
										onkeypress="return false;" readonly="true"></html:text>
								</td>
							</tr>
							<tr id="cash2">
								<td>
									<bean:message key="label.date"></bean:message>
								</td>
								<td>
									<html:text property="date"
										styleClass="formTextFieldWithoutTransparent"
										onkeypress="return false;" readonly="true"></html:text>
								</td>
							</tr>
							<tr id="cash3">
								<td>
									<bean:message key="label.amount"></bean:message>
								</td>
								<td>
									<html:text property="amount"
										styleClass="formTextFieldWithoutTransparent"
										onkeypress="return false;" readonly="true"></html:text>
								</td>
							</tr>

						</core:if>



						<tr>

							<core:if test="<%=subenable==null %>">
								<td>
								<!--
								sudhakar 09/09/2011
								-->
								<% if(message!=null) { %>
								
								<html:button property="but" onclick="button_second()"
										styleClass="ButtonBackgroundImage">
										<bean:message key="label.submit"></bean:message>
									</html:button>
									
									<%
									}else if (access != null && accesspageId != null
															&& access.toCharArray()[1] == '1') {
									%>
									<html:button property="but" onclick="button_insert('submit')"
										styleClass="ButtonBackgroundImage">
										<bean:message key="label.submit"></bean:message>
									</html:button>
									<%
										} else {
									%>
									<html:button property="but" onclick="button_insert('submit')"
										styleClass="ButtonBackgroundImage" disabled="true">
										<bean:message key="label.submit"></bean:message>
									</html:button>
									<%
										}
									%>
								</td>
								<td>
									<%
										if (access != null && accesspageId != null
															&& access.toCharArray()[2] == '1') {
									%>
									<html:button property="but3" onclick="button_insert('delete')"
										styleClass="ButtonBackgroundImage" value="Delete">
									</html:button>
									<%
										} else {
									%>
									<html:button property="but3" onclick="button_insert('delete')"
										styleClass="ButtonBackgroundImage" value="Delete"
										disabled="true">
									</html:button>
									<%
										}
									%>
								</td>
							</core:if>
							<td align="left"><br><html:button property="b4" value="Clear" onclick="reset123()"
									styleClass="ButtonBackgroundImage"></html:button>
							<br></td>


						</tr>
					</table>
				</td>

				<td valign="top">

					<table align="left" valign="top">
						<tr>
							<td valign="top">
								<%
									jspPath = (String) request.getAttribute("flag");
											System.out.println("jspPath==" + jspPath);
								%>
								<core:if test="<%=jspPath!=null %>">
									<jsp:include page="<%=jspPath.trim()%>"></jsp:include>
								</core:if>
							</td>
							<td>

								<%
									System.out.println("===After Page Include===");
								%>
							</td>
						</tr>
					</table>
				</td>
			</html:form>
		</table>
		<%
			} else {
		%>
		<font color="red"><bean:message key="label.accessdenied"></bean:message>
		</font>
		<%
			}
		%>
	</body>
</html>