<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld"    prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"    prefix="bean"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display" %>
<%@taglib uri="/WEB-INF/c-rt.tld" prefix="core" %>
<%@page import="masterObject.clearing.ChequeDepositionObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
 <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
<script type="text/javascript">

function setFlag(flagValue)
{
	if(document.forms[0].clr_no.value=='')
	{
		document.forms[0].validateFlag.value="Enter Clearing Number Value";
	}
	else if(document.forms[0].sent_to.value=='')
	{
		document.forms[0].validateFlag.value="Enter Value In The Field Send To";
	}
	else
	{
		document.forms[0].flag.value=flagValue;
		document.forms[0].submit();
	}	
}


function setChkBox()
{
	
	var v=document.forms[0].chkBox;
	
	if(document.forms[0].selectAll.checked)
	{
	  for(var i=0;i<v.length;i++)
	  {
		v[i].checked=true;
	  }
	}
	else
	{
		for(var i=0;i<v.length;i++)
	  	{
			v[i].checked=false;
	  	}
	}
}






function checkSubmit()
{	
			if(document.forms[0].chkBox.checked)
			{
				setFlag('unIdentify');
						
			}
			else
			{
				document.forms[0].validateFlag.value="CheckBox Is Not Selected";
			}
			
}	
    
    
    
    
    
function checkformat(ids) 

{
            
           var cha =event.keyCode;
            var format = true;
            var allow=true;

             var ff =  ids.value;
            
            var dd = ff.split( '/' );

			if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
                          format = false ;
                          allow=false;
                          

                      } else if (  i==2 && dd[i].length != 4 ){

                           document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
                           format = false ;
                           allow=false;
                      }

            	 }
             } 
             else
             {
                    allow=false;
             		format = false ;
             		document.forms[0].validateFlag.value="Problem In Date Format-DD/MM/YYYY";
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
          if(dd[0] > days)
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
          	document.forms[0].validateFlag.value="Month should be greater than 0 and \n lessthan 13  "+mth+" ";
          	allow=false;
          }
          }
      
      if(dd[2].length==4)
      {
          if((parseInt(dd[2])<=parseInt(date.getYear())))
          {
          
          }
          else
          {
          	document.forms[0].validateFlag.value="Year should be less than "+date.getYear()+" ";
          	allow=false;
          }
      }
          


}
		if(allow)
		{
		
		  ///document.forms[0].submit();
		  return true;
		}
		else
		{
		 
		 document.forms[0].clr_date.focus();
		 return false;
		}

        }	
    
function onlynumbers()
{
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  )
            {
            
            		return true;
            		
        	}
        	 else 
        	{
        		
        			return false;
        	}
			        
}
</script>

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
%>

<center><h2 class="h2">UnIdentified Cheque</h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/unIdentifylink?pageId=7018">
<%ChequeDepositionObject[] cd=(ChequeDepositionObject[])request.getAttribute("details"); %>

<table>
	<tr><html:text property="validateFlag" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
	
	<tr>
	<td>
	<table class="txtTable">
	<tr>
		<td><bean:message key="label.clr_date"></bean:message> </td>
		<td><html:text property="clr_date" styleClass="formTextFieldWithoutTransparent" value="<%=""+request.getAttribute("date")%>" onblur="return checkformat(this)"></html:text> </td>
	</tr>

	<tr>
	<td><bean:message key="label.clr_no"></bean:message> </td>
	<td><html:text size="5"  styleClass="formTextFieldWithoutTransparent" property="clr_no" onkeypress="return onlynumbers()" ></html:text></td>
	</tr>

	<tr>
	<td><bean:message key="label.sent_to"></bean:message> </td>
	<td><html:text size="5" styleClass="formTextFieldWithoutTransparent" property="sent_to"  onblur="setFlag('frmSendTo')" onkeypress="return onlynumbers()"></html:text></td>
	<td><bean:message key="label.selectAll"></bean:message></td>
	<td><input type="checkbox"  name="selectAll" onclick="setChkBox()"></td>
	</tr>
	</table>
	</td>
	</tr>
	<tr>
	<td>
	<%if(cd!=null){ %>
	<table>
	<tr>
	<td>SN</td>
	<td>CtrlNo</td><td>InstNo</td><td>InstDate</td><td>TanAmt</td><td>Select</td>
	</tr>
	<%for(int i=0;i<cd.length;i++){ %>
	<tr>
	<td><%=(i+1)%></td>
	<td><html:text property="txtCtrlNum" size="3" styleClass="formTextFieldWithoutTransparent" value="<%=""+cd[i].getControlNo()%>" ></html:text></td>
	<td><html:text property="txtInstNum" size="5" styleClass="formTextFieldWithoutTransparent" value="<%=""+cd[i].getExpClgNo() %>" ></html:text></td>
	<td><html:text property="txtInstDate" styleClass="formTextFieldWithoutTransparent" value="<%=""+cd[i].getExpectedClgDate()%>"></html:text></td>
	<td><html:text property="txtAmount" styleClass="formTextFieldWithoutTransparent" value="<%=""+cd[i].getTranAmt() %>"></html:text></td>
	<td><input type="checkbox"  name="chkBox" value="<%=""+i%>"/></td>
	</tr>
	<%} %>
	</table>
	
	<html:button property="buttonUnIdentify" onclick="setFlag('unIdentify')" styleClass="ButtonBackGroundImage" value="UnIdentify"></html:button>
	<%} %>
	</td>
</tr>
<html:hidden property="flag"></html:hidden>

</table>

</html:form>

<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>