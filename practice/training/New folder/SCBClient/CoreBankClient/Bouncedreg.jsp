<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.clearing.ChequeAckObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />

</head>
<script type="text/javascript">

function callClear()
{
    var ele= document.forms[0].elements;
    for(var i=0;i<ele.length;i++)
    {
        if(ele[i].type=="text")
         {
             ele[i].value="";
         }
     }
    document.forms[0].submit();  
 }

function storeFile(target)
{
	document.forms[0].flag.value=target;
	document.forms[0].submit();
}

function checkformat(ids) 
{
    var cha=event.keyCode;
    var format=true;
    var allow=true;
    var ff=ids.value;
    var dd=ff.split('/');

			if(dd.length==3)
			{
	            for(var i=0;i<dd.length;i++)
	            {
                    if(i!=2 && dd[i].length!=2)
                    {
                          document.forms[0].validateFlag.value="The date format should be : dd/mm/yyyy";
                          format = false ;
                          allow=false;
                     } 
                     else if(i==2 && dd[i].length!=4)
                     {
					       document.forms[0].validateFlag.value="The date format should be : dd/mm/yyyy";
                           format = false ;
                           allow=false;
                     }
            	 }
             } 
             else
             {
                    allow=false;
             		format=false ;
             		document.forms[0].validateFlag.value="The date format should be : dd/mm/yyyy";
             }

        if(format)
        {
        	var date=new Date();
         	var dayCheck=dd[0];
        	var monthCheck=dd[1];
         	var yrCheck=dd[2];
         	var mth=dd[1];
         	dd[1]=dd[1]-1;
	   		var days = 32 - new Date(dd[2],dd[1],32).getDate();
         	if(dd[0].length==2)
         	{
          		if(dd[0]>days)
                {
	               document.forms[0].validateFlag.value="Day should not be greater than "+days+" ";
                   allow=false;
                }
      		    if(dd[0]==00)
      		    {
          			document.forms[0].validateFlag.value="There is no date with 00";
          			allow=false;
          		}
                if(mth<1 || mth>12)
                {
         			 document.forms[0].validateFlag.value="Month should be greater than 0 and \n lessthan 13  "+mth+"";
          			 allow=false;
          		}
            }
          
            if(dd[2].length==4)
         	{
         		 if((parseInt(dd[2])<=parseInt(1980)))
          		{
          
                	document.forms[0].validateFlag.value="Year should be greater than "+1980+" ";        
                    allow=false;     
          		}
            }
         }
		if(allow)
		{
		  return true;
		}
		else
		{
		 document.forms[0].from_date.focus();
		 return false;
		}
}	

</script>
<body>
<center><h2 class="h2">Bounced Register</h2></center>
	<%String clrdate=(String)request.getAttribute("clrdate");%>
	<%ChequeAckObject[] ackObjects=(ChequeAckObject[])request.getAttribute("ChequeDet");%>
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
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>

<html:form action="/Clearing/Bouncedreg?pageId=7024">


<html:hidden property="flag"></html:hidden>
<table>
	<tr><html:text property="validateFlag" styleClass="formTextField" size="100" style="color:red;font-family:bold;"></html:text></tr>
	<tr><td>
	<table>
	
	<tr>
		<td><bean:message key="label.from_date"/></td>
		<td><html:text size="10" property="from_date" styleClass="formTextFieldWithoutTransparent" onblur="return checkformat(this)"></html:text></td>
	
		<td><bean:message key="label.to_date"/></td>
		<%if(clrdate!=null){ %>
		<td><html:text size="10" property="to_date" styleClass="formTextFieldWithoutTransparent" value="<%=clrdate%>" onblur="return checkformat(this)"></html:text></td>
		<%} %>
	</tr>
	</table>
	
	
	
	<table class="txtTable">
		<tr>
			<td>
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
		<html:submit property="view" styleClass="ButtonBackgroundImage"><bean:message key="label.view1"></bean:message> </html:submit>
		<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
				
				<html:button property="print" styleClass="ButtonBackgroundImage" onclick="window.print()">Print</html:button>
				<html:button property="reprint" styleClass="ButtonBackgroundImage" onclick="callClear()"><bean:message key="label.clear"></bean:message></html:button>
			 	<html:button property="stor" styleClass="ButtonBackgroundImage" onclick="storeFile('Store')">StoreToFile</html:button>
				
			</td>
		</tr>
	</table>
	
	<display:table name="ChequeDet"  pagesize="10" class="its" requestURI="/Clearing/Bouncedreg.do">
		<display:column property="controlNum" title="ControlNo" style="width:6%;"  maxLength="50"></display:column>
		<display:column property="tranTyp" title="TranType" style="width:6%;"  maxLength="50"></display:column>
		<display:column property="docBss" title="ChequeDate"></display:column>
		<display:column property="docTotall" title="ChequeAmt"></display:column>
		<display:column property="srcName" title="Source"></display:column>
		<display:column property="destinName" title="Destination"></display:column>
		<display:column property="creditAcNum" title="A/c No"></display:column>
		<display:column property="bnkName" title="BankName"></display:column>
		<display:column property="ackNum" title="AckNo"></display:column>
	</display:table>
	
	
	
	</td>
	</tr>
	</table>


</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>