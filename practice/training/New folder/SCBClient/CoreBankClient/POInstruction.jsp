
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page import="masterObject.general.GLMasterObject" %>
<%@page import="java.util.Map"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>

<%--
  User: Mohsin
 
  To change this template use File | Settings | File Templates.
Modified by Mohsin--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>
<html>

  <head><title>POInstruction</title>
      <font color="blue" ><center>
<h2 class="h2">PO Instruction</h2></center></font>
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
        
        function setval(){
        
        alert("Saving to file");
        document.forms[0].save.value='saveit';
        document.forms[0].submit();
        
        }
    
    
    
    function setval(){
    alert("submitting ");
    document.forms[0].subval.value='submit';
    document.forms[0].submit();
    }
    
     function reset123(){
   
    var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="text")
    {
    ele[i].value="";
    
    }
    else if(ele[i].type=="checkbox"){
    ele[i].checked=false;
    }
    else if(ele[i].type=="hidden"){
     ele[i].value="";
    }
    }
    
   show(false,'div1');
    
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
    System.out.println("inside JSP--------------POInstruction-----------");
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    	sbForm=(SBOpeningActionForm)request.getAttribute("Account");
    	String closingmsg=(String)request.getAttribute("closingmsg");
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
    <core:if test="<%=closingmsg!=null %>">
    <font color="red"><%=closingmsg %></font>
    </core:if>
    </div>
    
    <br><br>
    <table align="left" valign="top" class="txtTable">
     <html:form action="/FrontCounter/PoIns?pageId=3019" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="defaultTab" value="PersonalDetails"/>
     	<html:hidden property="defSIndex" value="1"/>
     	<html:hidden property="subval"/>
     	<html:hidden property="sysdate" />
         <td >
            <table border="1" width="726" height="43">
	<tr>
		<td height="37" width="109">po instruction:</td>
		<td height="37" width="174"><html:select property="poinst">
		<html:option value="1">Stop</html:option>
		<html:option value="2">Cancellation</html:option>
		<html:option value="3">Revalidation</html:option>
		
		</html:select></td>
		<td height="37" width="125">Cheque No.:</td>
		<td height="37" width="290"><html:text property="chno" onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
	</tr>
</table>
<br><br><br><br>
<table border="1" width="685" height="207" class="txtTable">
	<tr>
		<td height="43" width="150">PO Number:</td>
		<td height="43" width="166"><html:text property="pon" readonly="true"></html:text></td>
		<td height="43" width="3"></td>
		<td height="43" width="166">Stop Payment:</td>
		<td height="43" width="166">
<html:select property="stop">
		<html:option value="yes">Yes</html:option>
		<html:option value="no">No</html:option>
		
		</html:select>
</td>
	</tr>
	<tr>
		<td height="46" width="150">PO Date:</td>
		<td height="46" width="166"><html:text property="date" readonly="true"></html:text></td>
		<td height="46" width="3"></td>
		<td height="46" width="166">Amount:</td>
		<td height="46" width="166"><html:text property="amount" readonly="true"></html:text> </td>
	</tr>
	<tr>
		<td height="50" width="150">Payee Name:</td>
		<td height="50" width="166"><html:text property="payee" readonly="true"></html:text> </td>
		<td height="50" width="3"></td>
		<td height="50" width="166">Validity:</td>
		<td height="50" width="166"><html:text property="validity" onblur="return datevalidation(this)" readonly="true"></html:text></td>
	</tr>
	<tr>
		<td height="51" width="150">Cancel PO:</td>
		<td height="51" width="166"><html:select property="cancel">
		<html:option value="yes">Yes</html:option>
		<html:option value="no">No</html:option>
		
		</html:select> </td>
		<td height="51" width="3"></td>
		<td height="51" width="166">&nbsp;</td>
		<td height="51" width="166">&nbsp;</td>
	</tr>
</table>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
<html:button property="rest" value="Submit" onclick="setval()" styleClass="ButtonBackgroundImage"></html:button>
<%}else{ %> 
<html:button property="rest" value="Submit" onclick="setval()" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
<%} %>
<html:button property="rest" value="Clear" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button> 
        </td>
        <td>

        </td>
               	
       </html:form>
      </table>
      
       <table align="left" valign="top">
      		<tr>
      		<td>
      		<%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
            </td>
            </tr>
        </table>
   <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
</html>