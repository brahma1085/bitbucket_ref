
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page import="masterObject.frontCounter.AccountTransObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>

<%--
  User: Mohsin
  Date: Jan 2, 2009
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<%@page import="com.scb.fc.forms.AccountCloseActionForm"%>
<html>
  <head><title>AccountOpening</title>
      <font color="blue" ><center>
<h2 class="h2">Pass Sheet</h2></center></font>
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
         		alert("only "+size+"digits allowed")
         		txt.value="";
         		return false;
          	}
         }
    
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
         AccountCloseActionForm acForm;
    %>
    <% 
    System.out.println("---------------------Inside JSP-----------------");
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("acStatus");
    	AccountTransObject[] acctranobj=(AccountTransObject[])request.getAttribute("passvalues");
    %>
   
     <html:form action="/FrontCounter/Passbook?pageId=3014" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     
     	<html:hidden property="pageId" value="3014"/>
     	<html:hidden property="sysdate" />
     	<table border="1" width="686" height="75">
	<tr>
		<td height="34" width="112">Account Type</td>
		<td height="34" width="101">
<html:select property="acType" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
</td>
		<td height="34" width="93">AC No.:</td>
		<td height="34" width="105"><html:text property="acno"  onkeypress="return only_numbers()" onkeyup="numberlimit(this,'10')"></html:text></td>
		<td height="34" width="118">&nbsp;</td>
		<td height="34" width="117">
<core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=closingmsg%></font>
    </core:if>

</td>
	</tr>
	<tr>
		<td height="33" width="112">Sequence No.:</td>
		<td height="33" width="101">
<html:select property="sqno" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <%for(int j=0;j<6;j++){ %>
                        	    <html:option value="<%=""+j%>"><%=j%></html:option>
                        <%} %>
                        </html:select>
</td>
		<td height="33" width="93">From Date</td>
		<td height="33" width="105"><html:text property="fdate" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')"></html:text></td>
		<td height="33" width="118">To Date</td>
		<td height="33" width="117"><html:text property="tdate" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')"></html:text></td>
	</tr>
</table>
<br><br>
<html:button property="but" onclick="window.print()" value="Print"></html:button>
<br><br>

<%if(acctranobj!=null){ %>
	<table border="1">
	<tr bgcolor="#FFFFCC"><td>Date</td>
	<td>Chqeque No.</td>
	<td>Narration/Payee</td>
	<td>Debit</td>
	<td>Credit</td>
	<td>Closing Bal.</td>
	<td>Ent By</td>
	<td>ver By</td>
	</tr>
	<%
	for(int k=0;k<acctranobj.length;k++){%>
		<tr>
		
					<td><%=acctranobj[k].getTransDate()%></td>
					<%if(acctranobj[k].getTransMode().equals("G"))
					{%>
					<td><%=String.valueOf(acctranobj[k].getChqDDNo())%></td>
					<td><%=acctranobj[k].getTransNarr()%></td>
						
				<%	}
					else if (acctranobj[k].getTransMode().equals("C"))
					{%>
					<td><%=""%></td>
					<td><%=String.valueOf("Cash.ScrNo "+(acctranobj[k].getRef_No()==0?" ":""+acctranobj[k].getRef_No()))%></td>
						<%}
					else
					{%>
					<td><%=""%></td>
					<td><%=String.valueOf("Trf frm "+acctranobj[k].getTransNarr())%></td>
						<%	} %>
					
					<%if(acctranobj[k].getCdInd().equals("D")) {%>
					<td><%=acctranobj[k].getTransAmount()%></td>
					<td><%=""%></td>
					<%}
					else if(acctranobj[k].getCdInd().equals("C")) {
					%>
					<td><%=""%></td>
					<td><%=acctranobj[k].getTransAmount()%></td>
					
					<%} 
					else{%>
					<td><%=""%></td>
					<td><%=acctranobj[k].getTransAmount()%></td>
					<%} %>
					<td><%=acctranobj[k].getCloseBal()%></td>
					<td><%=acctranobj[k].uv.getUserId().toString()%></td>
					<td><%=acctranobj[k].uv.getVerId().toString()%></td>
					
		</tr>
		
	<% }%>
	</table><% }%>	
	
	<%if(acctranobj!=null){
	for(int k=0;k<acctranobj.length;k++){
		System.out.println("At 119"+acctranobj[k].toString());
		}}%>
	
	</html:form>
                <%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
  </body>
  </html>          