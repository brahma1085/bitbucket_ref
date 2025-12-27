
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@page import="java.util.Map"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Dec 24, 2008
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<html>
  <head><title>AccountOpening</title>
      <font color="blue" ><center>
<h2 class="h2">Cheque Instrucrtion </h2></center></font>
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
    --><link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    <script>
    
    function only_numbers_date()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57)||cha==47) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	
	function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57)) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	
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

	function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only "+size+"digits allowed")
         		txt.value="";
         		return false;
          	}
         }
	
	
	function only_alpa()
	{
	var cha=event.keyCode;
	if((cha >= 97 && cha <= 122)){
	return true;
	}else{
	return false;
	}
	
	}
    
    function settrue(){
    document.forms[0].selectedcombo.value='submit';
    alert(document.forms[0].selectedcombo.value);
    }
    function newpg(){
    document.forms[0].action='/FrontCounter/ChqIssueMenu';
    }
    
    function reset123(){
   
    var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="text")
    {
    ele[i].value="";
    show(false,'div1');
    show(false,'div2');
    }
    else if(ele[i].type=="checkbox"){
    ele[i].checked=false;
    }
    
    }
    
   
    
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
    </script>
  </head>
  <body class="Mainbody">
    <%!
        ModuleObject[] array_module;
        String details[];
        AccountMasterObject acountMaster;
        String jspPath;
        ModuleObject[] mod;
        CustomerMasterObject cmObject;
        SBOpeningActionForm sbForm;
        Map user_role;
        String access;
       
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    	sbForm=(SBOpeningActionForm)request.getAttribute("Account");
    	String chqins=(String)request.getAttribute("chqins");
    	String[] chqinsDetails=(String[])request.getAttribute("chqinsDetails");
    	String chqinscombo=(String)request.getAttribute("chqinscombo");
    	String accesspageId=(String)request.getAttribute("accesspageId");
    	user_role=(Map)request.getAttribute("user_role");
    	
    	
    	if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}
    	
    %>
    
     <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
    <div id = "div1" class = "myLayersClass">
     <core:if test="<%=chqins!=null%>">
         <font color="red"><%=chqins%></font>
         </core:if>   
         </div>
         <br><br>
    <html:form action="/FrontCounter/ChequeInstruction?pageId=3013" focus="<%=(String)request.getAttribute("FocusTo") %>" >
  
     	<html:hidden property="pageId" value="3013"/>
     	<html:hidden property="selectedcombo" />
     	<html:hidden property="sysdate" />
     	
     	<br><br>
<table border="1" width="497" height="126" align="center" class="txtTable">
	<tr>
		<td height="39" width="497" align="center" colspan="2">Cheque Instruction</td>
	</tr>
	<tr>
		<td height="37" width="232" align="right">Cheque Instruction</td>
		<td height="37" width="249">
		<html:select property="chqins" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <html:option value="StopPayment" >Stop Payment</html:option>
                        <html:option value="PostDated" >Post Dated</html:option>
                        <html:option value="Cancel" >Cancel</html:option>
                        
                        </html:select>
		
		
		
		</td>
	</tr>
	<tr>
		<td height="40" width="232" align="right">Cheque No.:</td>
		<td height="40" width="249"><html:text property="chqno" onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'7')"></html:text></td>
	</tr>
</table>
<br><br>
 
<core:if test="<%=chqinsDetails!=null%>">
<div id = "div2" class = "myLayersClass">
<table border="1" width="621" height="292" align="center">
	<tr>
		<td height="41" width="137">Account Details:</td>
		<td height="41" width="145"><font color="blue"><%=chqinsDetails[0]+" "+chqinsDetails[1] %></font></td>
		<td height="41" width="143">Cheque book no:</td>
		<td height="41" width="168"><font color="blue"><%=chqinsDetails[2]%></font></td>
	</tr>
	<tr>
		<td height="37" width="288" colspan="2" align="right">Account Type:</td>
		<td height="37" width="317" colspan="2"><font color="blue"><%=chqinsDetails[0]%></font></td>
	</tr>
	<tr>
		<td height="37" width="137">Account No.:</td>
		<td height="37" width="145"><font color="blue"><%=chqinsDetails[1]%></font></td>
		<td height="37" width="143">Stop Payment?:</td>
		<td height="37" width="168">
		<core:if test="<%=chqinscombo.equals("stop")%>">
		<font color="red"><%="YES" %></font>
		</core:if>
		<core:if test="<%=chqinscombo.equals("post")%>">
<core:if test="<%=chqinsDetails[10].equals("F")%>">
<font color="blue"><%="No" %></font>
</core:if>
<core:if test="<%=chqinsDetails[10].equals("T")%>">
<font color="blue"><%="yes" %></font>
</core:if>

</core:if>
<core:if test="<%=chqinscombo.equals("cancel")%>">
<core:if test="<%=chqinsDetails[10].equals("F")%>">
<font color="blue"><%="No" %></font>
</core:if>
<core:if test="<%=chqinsDetails[10].equals("T")%>">
<font color="blue"><%="yes" %></font>
</core:if>

</core:if>
</td>
	</tr>
	<tr>
		<td height="37" width="137">Cheque No:</td>
		<td height="37" width="145"><font color="blue"><%=chqinsDetails[3]%></font></td>
		<td height="37" width="143">Cheque Issue Date:</td>
		<td height="37" width="168">
		<%if(chqinsDetails[5]!=null){%>
	<font color="blue">	<%=chqinsDetails[5]%></font>
		<%}else{ %>
		<html:text property="chqissuedate" onkeypress="return only_numbers_date()" onblur="return datevalidation(this)" onkeyup="numberlimit(this,'11')"></html:text>
		<%} %>
		</td>
	</tr>
	<tr>
		<td height="42" width="137">Post Dated?</td>
		<td height="42" width="145">

<core:if test="<%=chqinscombo.equals("post")%>">
		<font color="red"><%="YES" %></font>
		</core:if>
		<core:if test="<%=chqinscombo.equals("stop")%>">
<core:if test="<%=chqinsDetails[9].equals("F")%>">
<font color="blue"><%="No" %></font>
</core:if>
<core:if test="<%=chqinsDetails[9].equals("T")%>">
<font color="blue"><%="yes" %></font>
</core:if>
</core:if>
<core:if test="<%=chqinscombo.equals("cancel")%>">
<core:if test="<%=chqinsDetails[10].equals("F")%>">
<font color="blue"><%="No" %></font>
</core:if>
<core:if test="<%=chqinsDetails[10].equals("T")%>">
<font color="blue"><%="yes" %></font>
</core:if>

</core:if>

</td>
		<td height="42" width="143">Payee Name:</td>
		<td height="42" width="168"><html:text property="payname" value="<%=chqinsDetails[6] %>" onkeypress="return only_alpa()" ></html:text></td>
	</tr>
	<tr>
		<td height="39" width="137">Expected Date:</td>
		<td height="39" width="145"><html:text property="date" value="<%=chqinsDetails[4] %>" onkeypress="return only_numbers_date()" onblur="return datevalidation(this)" onkeyup="numberlimit(this,'11')"></html:text></td>
		<td height="39" width="143">Cheque Amount:</td>
		<td height="39" width="168">
		<%if(chqinsDetails[7]!=null&&!chqinsDetails[7].equals("0.00")) {%>
		<font color="blue"><%=chqinsDetails[7] %></font>
		<%}else{%>
		<html:text property="chqamount" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" ></html:text>
		<%} %>
		</td>
	</tr>
	
</table>
</div>
<br><br>
<center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
<html:submit onclick="settrue()" styleClass="ButtonBackgroundImage"></html:submit>
<%}else{ %>
<html:submit onclick="settrue()" styleClass="ButtonBackgroundImage" disabled="true"></html:submit>
<%} %>
<html:button  property="B3" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button>
    </center>
     </core:if>	
 
          
          
         
    </html:form>
    <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
</html>