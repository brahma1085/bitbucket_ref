<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="html"  uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>
<%@page import="masterObject.loansOnDeposit.LoanReportObject"%>
<html>
<head>

<script type="text/javascript"><!--
   function but_value(target)
	{
		
		document.forms[0].button_value.value=target;
		document.forms[0].submit();
	
	var from_date=document.forms[0].txt_frm_dt.value;
	var to_date=document.forms[0].txt_to_dt.value;	

	if(from_date==to_date)
	{
	
	document.forms[0].txt_frm_dt.focus();
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
	
	function clear(target)
	{
	
    document.forms[0].button_value.value=target;
    document.forms[0].submit();
    
    };
	

function properdate(txt_frm_dt,txt_to_dt){
  
  
  var dtCh="/";
   
  var pos1=txt_frm_dt.indexOf(dtCh)
  var pos2=txt_frm_dt.indexOf(dtCh,pos1+1)
  var frmMonth=txt_frm_dt.substring(pos1+1,pos2)
  var frmDay=txt_frm_dt.substring(0,pos1)
  var frmYear=txt_frm_dt.substring(pos2+1)
  
  
  var pos3=txt_to_dt.indexOf(dtCh)
  
  var pos4=txt_to_dt.indexOf(dtCh,pos3+1)
  
  var ToMonth=txt_to_dt.substring(pos3+1,pos4)
  
  var ToDay=txt_to_dt.substring(0,pos3)
  
  var ToYear=txt_to_dt.substring(pos4+1)
  
  
  
  if(frmYear > ToYear){
    alert("From Year is greater than To Year!!!! Enter valid date")
  }
  //else if(frmMonth > ToMonth && frmYear<=ToYear ){
    //alert("From Month is greater than To Month !!!! Enter valid date")
  //}
 //else if(frmDay>ToDay && frmMonth<=ToMonth && frmYear<=ToYear){
  //alert("From day is greater than To day !!!! Enter valid date")
  //}
 }; 
 
 function numbersonly_date(eve){
         var cha = event.keyCode

            if (  ( cha  >= 47 && cha < 58  ) ) {
              
                return true ;
                            }
            else{
             
              return false ;
            }
         
        
      }; 

--></script>	

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>LDOpenClosed Accounts</center></h2> 	
<%! ModuleObject[] LoanACType; ArrayList list;%>
<%LoanACType=(ModuleObject[])request.getAttribute("LoanACType");
list=(ArrayList)request.getAttribute("OpenClosed");
%>

<%! String FromDate; %>
<%FromDate=(String)request.getAttribute("FromDate"); %>



</head>

<body class="Mainbody">
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
	<html:form action="/LDOpenClosedReports?pageId=6009" styleId="form1">
	<html:hidden property="testing" styleId="totaltesting"></html:hidden>
	
<table class="txtTable">	
	<tr>
	   <td align="right"> 
	      <bean:message key="lable.openclose"/></td>
	      <td><html:select property="combo_account" styleClass="formTextFieldWithoutTransparent">
	      <html:option value="open">Open Accounts</html:option>
	      <html:option value="close">Closed Accounts</html:option>
	      </html:select>
	   </td>  
	   
	   <td align="right">
	      <bean:message key="lable.acctye"/></td>
	      <td><html:select property="combo_acctype" styleClass="formTextFieldWithoutTransparent"> 
	      <html:option value="alltype">All Type</html:option>
	      <%if(LoanACType!=null){ %>
	      <%for(int i=0;i<LoanACType.length;i++){%>
	      <html:option value="<%=""+i%>"><%=LoanACType[i].getModuleAbbrv()%></html:option>
	      <%}%>
	      <%}%>
	      
	     </html:select> 
	   </td>
	   </tr>
	   <tr> 
	   <td align="right">
	      <bean:message key="label.frm_dt"/></td>
	      <td><html:text property="txt_frm_dt" size="10" onblur="return datevalidation(this)" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly_date(this)"></html:text>
	   </td>   
	   <td align="right">
	      <bean:message key="label.to_dt"/></td>
	      <td><html:text styleClass="formTextFieldWithoutTransparent" property="txt_to_dt" size="10" onchange="properdate(txt_frm_dt.value,txt_to_dt.value)" onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)"></html:text>
	   </td>    
	</tr>
	<tr>
	   <table class="txtTable">
	   <html:hidden property="button_value" value="error"></html:hidden>
	   <html:hidden property="sysdate" />
	   <tr align="center">
	   		<td><html:submit value="VIEW" onclick="but_value('View')" styleClass="ButtonBackgroundImage"></html:submit></td>
	   		<td></td>
	   		<td><html:button property="but_print" onclick="but_value('download')" styleClass="ButtonBackgroundImage">DownLoad</html:button></td>
	   		<td></td>
	   		<td><html:submit property="clear"  value="CLEAR"  styleClass="ButtonBackgroundImage"  onclick="but_value('Clear')"></html:submit></td>
       </tr>
       </table>		 
   </tr>
   
   <tr>
      <td>
      <%if(list!=null){%>
      <table class="txtTable">
       <tr>
          <td>
            <div id = "table1" style="display: block;overflow: scroll;width: 600px;height: 200px">
			<display:table name="OpenClosed" id="OpenClosed" class="its" >
			<display:column title="SrlNo"  class="formTextFieldWithoutTransparent" ><input type="text" readonly="readonly" class="dispTabStringTextField" value="${OpenClosed.SrlNo}" size="3"></display:column>
			<display:column title="LoanAcNo"  class="formTextFieldWithoutTransparent" ><input type="text" readonly="readonly" class="dispTabStringTextField" value="${OpenClosed.LoanAcNo}" size="30"></display:column>
			<!--<display:column title="Name"  class="formTextFieldWithoutTransparent" ><input type="text" align="left" readonly="readonly" class="dispTabStringTextField" value="${OpenClosed.Name}" size="50"></display:column>
			--><display:column title="DepositAcNo"  class="formTextFieldWithoutTransparent" ><input type="text" readonly="readonly" class="dispTabStringTextField" value="${OpenClosed.DepositAcNo}" size="30"></display:column>
			<display:column title="DepDate"  class="formTextFieldWithoutTransparent"><input type="text" readonly="readonly" class="dispTabStringTextField" value="${OpenClosed.DepDate}"></display:column>
			<display:column title="MatDate"  class="formTextFieldWithoutTransparent"><input type="text" readonly="readonly" class="dispTabStringTextField" value="${OpenClosed.MatDate}"/></display:column>
			<display:column title="Perioddays"  class="formTextFieldWithoutTransparent"><input type="text" readonly="readonly" class="dispTabStringTextField"  value="${OpenClosed.Perioddays}"/></display:column>
			<display:column title="Intrate" class="formTextFieldWithoutTransparent"><input type="text" readonly="readonly" class="dispTabStringTextField" value="${OpenClosed.Intrate}"/></display:column>
			<display:column title="Amount"  class="formTextFieldWithoutTransparent"><input type="text" readonly="readonly" class="dispTabStringTextField" value="${OpenClosed.Amount}"/></display:column>
			<display:column title="AmtAdv"  class="formTextFieldWithoutTransparent"><input type="text" readonly="readonly" class="dispTabStringTextField" value="${OpenClosed.AmtAdv}"/></display:column>
			<display:column title="LoanRate"  class="formTextFieldWithoutTransparent"><input type="text" readonly="readonly" class="dispTabStringTextField"  value="${OpenClosed.LoanRate}"/></display:column>
			<display:column title="SanDate"  class="formTextFieldWithoutTransparent"><input type="text" readonly="readonly"  class="dispTabStringTextField" value="${OpenClosed.SanDate}"/></display:column>
			
			
    </display:table>
    </div>
             
            </td>
   	    </tr>	
   		</table>
   		<%} %>
   		</td>
   </tr>
</table>	
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>