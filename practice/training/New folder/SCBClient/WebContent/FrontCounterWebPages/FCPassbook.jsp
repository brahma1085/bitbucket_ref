
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
<%@ page import="masterObject.general.Address" %>
<%@page import="com.scb.fc.forms.AccountCloseActionForm"%>
<html>
  <head><title>AccountOpening</title>
      <font color="blue" ><center>
<h2 class="h2">PassBook</h2></center></font>
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
    
     function getTable()
  {
  
  	var url = "PassbookMenu.do?pageId=3014";   		
  	window.open( url,'Popup','WIDTH=500,HEIGHT=380,left=200,top=100,scrollbars=yes');
  
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
    
    
    document.forms[0].save.value='saveit';
    document.forms[0].submit();
    
    }
    
    function Seevalue(){
    
    document.forms[0].submit();
    
    
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
    <%!Address addr; %>
    <% 
    System.out.println("---------------------Inside JSP-----------------");
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String msg=(String)request.getAttribute("msg");
    	String verified=(String)request.getAttribute("Verified");
    	String closingmsg=(String)request.getAttribute("acStatus");
    	AccountTransObject[] acctranobj=(AccountTransObject[])request.getAttribute("passvalues");
    	CustomerMasterObject cust=(CustomerMasterObject)request.getAttribute("cust");
    %>
   
    
    
    
     <html:form action="/FrontCounter/Passbook?pageId=3014" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     <core:if test="<%=msg!=null %>">
     <font color="red"><%=msg %></font>
     
     </core:if>
     	<html:hidden property="pageId" value="3014"/>
     	<html:hidden property="save"/>
		<html:hidden property="sysdate" />
     	<table border="1" width="686" height="75" style="border:thin solid navy" class="txtTable">
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
		<td height="34" width="105"><html:text property="acno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
		<td height="34" width="118">A/C Status:</td>
		<td height="34" width="117">
<core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=closingmsg%></font>
    </core:if>

</td>
	</tr>
	<tr>
		
		<td height="33" width="93">From Date</td>
		<td height="33" width="105"><html:text property="fdate" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')"></html:text></td>
		<td height="33" width="118">To Date</td>
		<td height="33" width="117"><html:text property="tdate" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')"></html:text></td>
	</tr>
	<tr>
	<td><html:button property="but2" onclick="Seevalue()" value="View" styleClass="ButtonBackgroundImage"></html:button></td>
	<td><html:button property="but" onclick="window.print()" value="Print" styleClass="ButtonBackgroundImage"></html:button></td>
	<td><html:button property="but2" onclick="setval()" value="Download" styleClass="ButtonBackgroundImage"></html:button></td>
	
	</tr>
</table>
<br><br>
<br>
<core:if test="<%=verified==null %>">
<core:if test="<%=cust!=null %>">
<table>
<tr>
<td valign="top"><font color="navy" size="3"> Name:</font></td>
<td><font color="red"> <%=cust.getName() %></font></td>
</tr>
<tr>
<td valign="top"><font color="navy" size="3"> Address:</font></td>
<td>
		<font color="red">
		<% addr=(Address)cust.getAddress().get(1);%>
		<core:if test="<%=addr!=null %>">
		<core:if test="<%=addr.getAddress()!=null %>">
		<% if(addr.getAddress().length()>20)
					{	%>
						<%=addr.getAddress().subSequence(0,20) %>
						<%=addr.getAddress().substring(20)%><br>
					<%}
					else {%>
						<%= addr.getAddress()%><br>
						<%} %>
							<core:if test="<%=addr.getCity()!=null %>">	
						
					<%=addr.getCity() %><br>					</core:if>
					<core:if test="<%=addr.getState()!=null %>">	
					<%=addr.getState() %><br>
					</core:if>
					<core:if test="<%=addr.getCountry()!=null %>">	
					<%=addr.getCountry() %><br>
					</core:if>
					
					<core:if test="<%=addr.getPin()!=null %>">	
					<%=addr.getPin() %><br>
					</core:if>
					<core:if test="<%=addr.getPhno()!=null %>">	
					<%if(addr.getPhno().length()>0)
						%>
						<%="Ph No: "+addr.getPhStd()+"-"+addr.getPhno() %><br>
					
					</core:if>
					<core:if test="<%=addr.getFax()!=null %>">	
					<%if(addr.getFax().length()>0)
						%>
						<%="Fax No: "+addr.getFaxStd()+"-"+addr.getFax() %><br>
					
					</core:if>
					<core:if test="<%=addr.getMobile()!=null %>">	
					<%if(addr.getMobile().length()>0)
						%>
						<%="Mobile No: "+addr.getMobile() %><br>
					
					</core:if>
						
					<core:if test="<%=addr.getEmail()!=null && !addr.getEmail().trim().equals("null")%>">	
					<%if(addr.getEmail().length()>0)
						if(addr.getEmail()!=null)
						%>
						<%="Email: "+addr.getEmail() %><br>
					
					</core:if>

					
		
		</core:if>
		</core:if>

</font>
</td>
</tr>
</table>
</core:if>
<br><br>
<div id="pocon" style="display:block;overflow:scroll;width:750px;height:300px">
<%if(acctranobj!=null){ %>
	<table border="1">
	<tr style="background-color:#CDCEAE"><td>Date</td>
	<td>Cheque No.</td>
	<td>Narration/Payee</td>
	<td>Debit</td>
	<td>Credit</td>
	<td>Closing Bal.</td>
	<td>Ent By</td>
	<td>ver By</td>
	</tr>
	<%
	for(int k=0;k<acctranobj.length;k++){%>
		<tr style="background-color:#ECEBD2">
					<%if(acctranobj[k].getTransDate()!=null&&acctranobj[k].getCdInd()!=null){%>
					<td><%=acctranobj[k].getTransDate()%></td>
					
					<%if(acctranobj[k].getCdInd().equals("C")){%>
					<%if(acctranobj[k].getTransMode().equals("G"))
					{%>
					<td><%=String.valueOf(acctranobj[k].getChqDDNo())%></td>
					<td>
					<core:if test="<%=acctranobj[k].getTransNarr()!=null %>">
					<%=acctranobj[k].getTransNarr()%>
					</core:if>
					</td>
						
				<%	}
					else if (acctranobj[k].getTransMode().equals("C"))
					{%>
					<td><%=""%></td>
					<td><%=String.valueOf("Cash.ScrNo "+(acctranobj[k].getRef_No()==0?" ":""+acctranobj[k].getRef_No()))%></td>
						<%}
					else
					{%>
					<td><%=""%></td>
					<td>
					<core:if test="<%=acctranobj[k].getTransNarr()!=null %>">
					<%=String.valueOf("Trf frm "+acctranobj[k].getTransNarr())%>
					</core:if>
					</td>
						<%	} %>
					<%} 
					else{%>
					<%if(acctranobj[k].getTransMode().equals("G"))
					{%>
					<td><%=String.valueOf(acctranobj[k].getChqDDNo())%></td>
					<td>
					<core:if test="<%=acctranobj[k].getTransNarr()!=null&&acctranobj[k].getPayeeName()!=null%>">
					<%=acctranobj[k].getTransNarr()+" / "+acctranobj[k].getPayeeName()%>
					</core:if>
					</td>
						
					<%}
					else if (acctranobj[k].getTransMode().equals("C"))
					{%>
					<td><%=String.valueOf(acctranobj[k].getChqDDNo())%></td>
					<td>
					<core:if test="<%=acctranobj[k].getTransNarr()!=null&&acctranobj[k].getPayeeName()!=null%>">
					<%=acctranobj[k].getTransNarr()+" / "+acctranobj[k].getPayeeName()%>
					</core:if>
					</td>
					<%}
					else
					{%>
					<td><%=""%></td>
					<td><%=String.valueOf("Trf frm "+acctranobj[k].getTransNarr())%></td>
						<%	} %>
					<%}%>
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
					<td>
					<core:if test="<%=acctranobj[k].uv.getUserId()!=null%>">
					<%=acctranobj[k].uv.getUserId().toString()%>
					</core:if>
					</td>
					<td>
					<core:if test="<%=acctranobj[k].uv.getVerId()!=null%>">
					<%=acctranobj[k].uv.getVerId().toString()%>
					</core:if>
					</td>
					
					<%}
					else{%>
					<td></td>
					<td></td>
					<td><%="B/F"%></td>
					<td></td>
					<td></td>
					<td><%=acctranobj[k].getPreCloseBal()%></td>
					<td></td>
					<td></td>
			
			
					<%} %>
			
		
		
		
		
		</tr>
		
		
	<% }%>
	</table><% }%>	
	
	<%if(acctranobj!=null){
	for(int k=0;k<acctranobj.length;k++){
		System.out.println("At 119"+acctranobj[k].toString());
		}}%>
	</div>
	</core:if>
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