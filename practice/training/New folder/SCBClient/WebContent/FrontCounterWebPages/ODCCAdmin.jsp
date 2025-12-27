
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@page import="java.util.Map"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Dec 8, 2008
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<%@page import="com.scb.fc.forms.AccountCloseActionForm"%>
<%@page import="masterObject.frontCounter.AdminObject"%>
<%@page import="com.scb.designPatterns.FrontCounterDelegate"%>
<html>
  <head><title>AccountOpening</title>
       <font color="blue" ><center>
<h2 class="h2">OD/CC Admin</h2></center></font>
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
   
   
function getDetails(val){

document.forms[0].hidval.value=val;
document.forms[0].submit();
}
function setDetails(val){

document.forms[0].hidval.value=val;
document.forms[0].submit();
}


function setIDetails(val){

if(document.forms[0].hidval.value==""){
alert("Please click on add row to add parameters");
}
if(document.forms[0].odcc_table.value=="DirectorRelation"){
if(document.forms[0].rel_code.value==""){
alert("Please enter relation code");
document.forms[0].rel_code.focus();
return false;
}
else if(document.forms[0].rel_type.value==""){
alert("Please enter relation Type");
document.forms[0].rel_type.focus();
return false;
}
else{
document.forms[0].hidval.value='toupdate';
document.forms[0].insertval.value=val;
document.forms[0].submit();
}
}

else if(document.forms[0].odcc_table.value=="SecurityDetails"){
if(document.forms[0].perc_ln_availed.value==""){
alert("Please enter perc_ln_availed");
document.forms[0].perc_ln_availed.focus();
return false;
}
else if(document.forms[0].tdate.value==""){
alert("Please enter To date");
document.forms[0].tdate.focus();
return false;
}
else{
document.forms[0].hidval.value='toupdate';
document.forms[0].insertval.value=val;
document.forms[0].submit();
}
}

else if(document.forms[0].odcc_table.value=="Relations"){
if(document.forms[0].type_rel.value==""){
alert("Please enter relation ");
document.forms[0].type_rel.focus();
return false;
}
else{
document.forms[0].hidval.value='toupdate';
document.forms[0].insertval.value=val;
document.forms[0].submit();
}

}

else if(document.forms[0].odcc_table.value=="DirectorMaster"){
if(document.forms[0].dircode.value==""){
alert("Please enter Director code ");
document.forms[0].dircode.focus();
return false;
}
if(document.forms[0].dircid.value==""){
alert("Please enter Director Customer ID");
document.forms[0].dircid.focus();
return false;
}
else if(document.forms[0].tdate.value==""){
alert("Please enter To Date ");
document.forms[0].tdate.focus();
return false;
}
else{
document.forms[0].hidval.value='toupdate';
document.forms[0].insertval.value=val;
document.forms[0].submit();
}
}


}

function resetting(){
document.forms[0].action='/FrontCounter/SbCaAdminMenu?pageId=3025';
document.forms[0].submit();
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
      function only_numbers_date() {
	
	var cha=event.keyCode;
	if(cha>47 && cha<58 || cha==47)
	{
		return true;
	}
	else
	{
		return false;
	}
}  

function only_alpa() {
	
	var cha=event.keyCode;
	if((cha>=65 && cha<=90)||(cha>=97 && cha<=122) ||cha==32)
	{
		return true;
	}
	else
	{
		return false;
	}
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
     show(false,'div2');
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
         String[] strArry;
         Map user_role;
         String access;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    AdminObject[] adminobj=(AdminObject[])request.getAttribute("sbcaAdminvalues");
	String closingmsg=(String)request.getAttribute("closingmsg");
	FrontCounterDelegate fcdel=new FrontCounterDelegate();
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
    <div id = "div2" class = "myLayersClass">
    <core:if test="<%= closingmsg!=null%>">
    <font color="red"><%=request.getAttribute("closingmsg")%></font>
    </core:if>
    </div>
    
     <html:form action="/FrontCounter/OdCcAdmin?pageId=3026" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="hidval" />
     	<html:hidden property="insertval" />
     	<html:hidden property="sysdate" />
     	<br><br><br>
          
<table  border="1" width="565" height="66" id="table1" class="txtTable">
	<tr>
		<td height="60" width="220">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp; Select Table :</td>
		<td height="60" width="329">&nbsp;&nbsp;&nbsp;
		<html:select property="odcc_table">
		<html:option value="SELECT">SELECT</html:option>
		<html:option value="DirectorRelation">DirectorRelation</html:option>
		<html:option value="SecurityDetails">SecurityDetails</html:option>
		<html:option value="Relations">Relations</html:option>
		<html:option value="DirectorMaster">DirectorMaster</html:option>
		
		</html:select>
		</td>
	</tr>
</table>

	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	
	
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:button property="B1" value="VIEW" styleClass="ButtonBackgroundImage" onclick="submit()"></html:button>
	<%}else{ %>
	<html:button property="B1" value="VIEW" styleClass="ButtonBackgroundImage" onclick="submit()" disabled="true"></html:button>
	<%} %>
	<html:button property="B2" value="ADD ROW" onclick="getDetails('toadd')" styleClass="ButtonBackgroundImage"></html:button>
	
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<html:button property="B3" styleClass="ButtonBackgroundImage" value="INSERT" onclick="setIDetails('toinsert')"></html:button>
	<%}else{ %>
	<html:button property="B3" styleClass="ButtonBackgroundImage" value="INSERT" onclick="setIDetails('toinsert')" disabled="true"></html:button>
	<%} %>
	<html:button property="B3" value="CLEAR" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button>
	</p>
 <div id = "div1" class = "myLayersClass">
<table border="1" style="background-color:#CDCEAE">
<%
if(adminobj!=null){
for(int i=0;i<adminobj.length;i++){
	if(adminobj[i]!=null){
		System.out.println("adminobject is not null");
	 strArry=adminobj[i].getCol_names();%>
	<tr style="background-color:#CDCEAE">
	<%
	if(strArry!=null){
	for(int p=1;p<strArry.length;p++){%>
		
		<%if(strArry[p]!=null){ 
			System.out.println("Name of columns are "+strArry[p]);
		%>
		<td ><%= strArry[p]%></td>
		<%} %>
		
		<%
		
	}}
	%></tr>
	<%
	System.out.println(" i am in Object");
	Object[][] admobj2=adminobj[i].getData();
	System.out.println(" i am in Object"+admobj2.length);
	for(int k=0;k<admobj2.length;k++){%>
		<tr>
		<%for(int j=1;j<strArry.length;j++){ %>
		<td style="background-color:#ECEBD2"><%=admobj2[k][j].toString()%></td>
		
		
		
		
	<%}%>
	</tr>
		<%}
	}
}
}

%>
</table>

<br><br>
<%
if(request.getAttribute("settrue")!=null){
	%>
		<table border="1">
<%
if(adminobj!=null){
for(int i=0;i<adminobj.length;i++){
	String[] strArry=adminobj[i].getCol_names();%>
	<tr>
	<%
	if(strArry!=null){
	for(int p=1;p<strArry.length;p++){
	if(strArry[p]!=null){
	%>
		<td bgcolor=#FF9933><%= strArry[p]%></td>
		<%
		
	}}}
	%></tr>		
	<%}}
	if(request.getAttribute("settrue").equals("dirRel")){%>
	<tr><td><html:text property="rel_code" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>  
	<td><html:text property="rel_type" onkeypress="return only_alpa()" ></html:text></td>     
             <!--<td><html:text property="de_tml" onkeypress="return false;"></html:text>    </td>
             <td><html:text property="de_user" onkeypress="return false;"></html:text>    </td>           
              <td><html:text property="de_date" value="" disabled="true"></html:text>    
              </td>          
                        
                        --></tr>
	
	<%}
	else if(request.getAttribute("settrue").equals("securitydet")){
	%>
		<tr>
                        <td><html:select property="sectype" styleClass="formTextFieldWithoutTransparent">
                       
                        <html:option value="Property" >Property</html:option>
                        <html:option value="Rent" >Rent</html:option>
                        <html:option value="Pension" >Pension</html:option>
                        <html:option value="Gold" >Gold</html:option>
                        <html:option value="Service" >Service</html:option>
                        <html:option value="SelfEmployed" >SelfEmployed</html:option>
                        <html:option value="Vehicle" >Vehicle</html:option>
                      
                        </html:select></td>
                        <td>
            <html:select property="secActype" styleClass="formTextFieldWithoutTransparent">
                       
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>         
                        
                        </td>
                        <td><html:text property="perc_ln_availed" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
     <td><html:text property="fdate" value="<%= fcdel.getSysDate()%>" disabled="true"></html:text></td>                   
                   <td><html:text property="tdate" onkeypress="return only_numbers_date()" onblur="return datevalidation(this)" onkeyup="numberlimit(this,'11')"></html:text></td>
                <td><html:text property="de_tml" readonly="true"></html:text>    </td>
             <td><html:text property="de_user" readonly="true"></html:text>    </td>           
              <td><html:text property="de_date" value="<%= fcdel.getSysDate()+""+fcdel.getSysTime()%>" disabled="true"></html:text>    
              </td>        
                        
                        </tr>
	
	<%}
	else if(request.getAttribute("settrue").equals("Relation")){%>
	
		<tr><td><html:text property="type_rel" onkeypress="return only_alpa()" ></html:text></td>
          
            <!--<td><html:text property="de_tml" onkeypress="return false;"></html:text>    </td>
             <td><html:text property="de_user" onkeypress="return false;"></html:text>    </td>           
              <td><html:text property="de_date" value="<%= fcdel.getSysDate()+""+fcdel.getSysTime()%>" disabled="true"></html:text>    
              </td>-->            
                        
                        
                        </tr>
	
	<%}
	else if(request.getAttribute("settrue").equals("dirmaster")){%>
	
		<tr><td><html:text property="dircode" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
		<td><html:text property="dircid" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onblur="submit()"></html:text></td>
		<td><html:text property="dirname" readonly="true"></html:text></td>
		<td><html:text property="fdate" value="<%=fcdel.getSysDate() %>" disabled="true"></html:text></td>
		<td><html:text property="tdate" onkeypress="return only_numbers_date()"  onkeyup="numberlimit(this,'11')"></html:text></td>
          
                      
                        
                        
                        </tr>
	
	<%}%>
	</table>
<%}


%>
</div>
   </html:form>
                <%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
            <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
     </body>
     </html>          
