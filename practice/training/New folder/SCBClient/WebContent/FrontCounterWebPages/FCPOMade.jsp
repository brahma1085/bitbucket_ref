<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page import="masterObject.frontCounter.PayOrderObject" %>
<%--
  User: Mohsin
  Date: Dec 8, 2008
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<%@page import="com.scb.fc.forms.AccountCloseActionForm"%>
<html>
  <head><title>AccountOpening</title>
       <font color="blue" ><center>
<h2 class="h2">List Of PayOrder's Created</h2></center></font>
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
    function settingval(){
    document.forms[0].save.value='submit';
    document.forms[0].submit();
   
    
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
         PayOrderObject[] poObj;
    %>
    <% String disabling=(String)request.getAttribute("disabling");
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	poObj=(PayOrderObject[])request.getAttribute("poObj");
    %>
    <core:if test="${one!=null}">
    <core:if test="<%= one.equals("true")%>">
       <font color="red"><bean:message key="label.empty"></bean:message></font>
       <%System.out.println("i am here"); %>
    </core:if>
    </core:if>
    
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=request.getAttribute("closingmsg")%></font>
    </core:if>
    
     <html:form action="/FrontCounter/ListPOMake?pageId=3051" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="save" />
     	<html:hidden property="sysdate" />
     	<div id="tabs">
     	</div>
     	<br><br><br>
     	
            <table>
            <tr>
            <td>From Date:</td>
            <td><html:text property="fdate" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')"></html:text> </td>
            <td></td>
            <td>To Date:</td>
            <td><html:text property="tdate" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')"></html:text> </td>
            <td></td>
            <td><html:submit value="View" styleClass="ButtonBackgroundImage"></html:submit> </td>
            <td><html:submit value="Print" onclick="window.print()" styleClass="ButtonBackgroundImage"></html:submit> </td>
            <td><html:button value="Download" property="b2" onclick="settingval()" styleClass="ButtonBackgroundImage"></html:button> </td>
            </tr>
            </table>
            <br><br><br>
            <core:if test="<%= poObj!=null%>">
            <table style="background-color:#CDCEAE"><tr>
            <td>Sr No.</td>
            <td>Voucher No.</td>
            <td>Debit A/C No.</td>
            <td>Name</td>
            <td>GL Type</td>
            <td>GL Code</td>
            <td>Amount</td>
            <td>Commission</td>
            
            </tr>
            <core:if test="<%= poObj!=null%>">
            <%for(int i=0;i<poObj.length;i++){
            	int k=i+1;
            	%>
            <tr style="background-color:#ECEBD2">
            <td><%=k %></td>
            <td><%=poObj[i].getPOType()+"   "+String.valueOf(poObj[i].getPOSerialNo())%></td>
             <td><%=poObj[i].getPOAccType()+" "+poObj[i].getPOAccNo() %></td>
             <td><%=poObj[i].getPOPayee() %></td>
             <td><%=poObj[i].getGLRefCode() %></td>
             <td><%=poObj[i].getPOGlCode()%></td>
             <td><%=poObj[i].getPOAmount() %></td>
             <td><%=poObj[i].getCommissionAmount()%></td>
            
            </tr>
            <%} %>
            </core:if>
            </table>
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