
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page import="masterObject.general.AccCategoryObject" %>
<%@ page import="masterObject.general.AccSubCategoryObject" %>
<%@ page import="masterObject.frontCounter.PayOrderObject" %>
<%@ page import="com.scb.designPatterns.FrontCounterDelegate" %>
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
<html>
  <head><title>AccountOpening</title>
      <font color="blue" ><center>
<h2 class="h2">PayOrder Admin</h2></center></font>
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
         		alert("You can't enter more than "+((parseInt(size)))+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
         
         function only_numbers_date() {
	
	var cha=event.keyCode;
	if(cha>=47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  
         
         function only_numbers_doublevalue() {
	
	var cha=event.keyCode;
	if((cha>=48 && cha<=57) || cha==46)
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;
         
    
    function setval(){
   
    document.forms[0].viewval.value='view';
    document.forms[0].submit();
    
    }
    function setAdd(){
 
    document.forms[0].addval.value='add';
     document.forms[0].submit();
    }
    
    function setsub(val){
    var ele=document.forms[0].elements;
    
    if(document.forms[0].addval.value==""){
    alert("Please click on add row to add new parameters");
    return false;
    }
    for(var i=0;i<ele.length;i++){
    if(ele[i].type=="text"){
    if(ele[i].value==""){
    alert("Please fill all the values ");
    return false;
    }
    }
    }
    document.forms[0].submiting.value=val;
    document.forms[0].submit();
    
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
         Map user_role;
         String access;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
    	String one=(String)request.getAttribute("error1");
    	String closingmsg=(String)request.getAttribute("closingmsg");
    	 AccCategoryObject[] cat=(AccCategoryObject[])request.getAttribute("cat");
    	 AccSubCategoryObject[] subcat=(AccSubCategoryObject[])request.getAttribute("subcat");
    	 PayOrderObject[] podetails=(PayOrderObject[])request.getAttribute("podetails");
    	 String showtab=(String)request.getAttribute("showtab");
    	 FrontCounterDelegate fdelegate=new FrontCounterDelegate();
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
    <font color="red"><%=closingmsg%></font>
    </core:if>
    </div>
    
     <html:form action="/FrontCounter/PayOrdAdmin?pageId=3027"  focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	
     	<html:hidden property="pageId" />
     	<html:hidden property="viewval" />
     	<html:hidden property="addval" />
     	<html:hidden property="submiting" />
     	<html:hidden property="sysdate" />
     	<br><br><br>
            <table border="1" height="44" class="txtTable">
                <tr >
                    <td ><bean:message key="label.acType"></bean:message></td>
                    <td ><html:select property="acType" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                    </td>
                  <td height="38" width="151">Category:</td>
                 <td ><html:select property="cat" styleClass="formTextFieldWithoutTransparent" onblur="submit()">
                        <html:option value="SELECT" >SELECT</html:option>
                        
                        <core:forEach var="cat" varStatus="count" items="${requestScope.cat}" >
                        	    <html:option value="${cat.categoryCode}" ><core:out value="${cat.categoryDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                    </td>
		
                
                  <core:if test="<%=subcat!=null %>">
                    <td height="38" width="134">Sub-Category</td>
		<td ><html:select property="subcat" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="subcat" varStatus="count" items="${requestScope.subcat}" >
                        <html:option value="${subcat.subCategoryCode}" ><core:out value="${subcat.subCategoryDesc}"></core:out></html:option>
                        	    
                        </core:forEach>
                        </html:select>
                    </td>
                  </core:if>
                </tr>
               
                </table>
                <br><br>
                <p>&nbsp;<!--<input type="button" value="Update" name="B3" onclick="setsub('2')">&nbsp;&nbsp;
                 <input type="button" value="   View " name="B1" onclick="setval()" >&nbsp;&nbsp;
	<input type="button" value="  AddRow  " name="B2" onclick="setAdd()">&nbsp;&nbsp;
	
	<input type="reset" value="  Clear  " name="B5">
	<input type="button" value="Submit" name="B4" onclick="setsub('1')">&nbsp;&nbsp;
	-->
	 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:button property="B1" value="VIEW" onclick="setval()" styleClass="ButtonBackgroundImage"></html:button>
	<%}else{ %>
	<html:button property="B1" value="VIEW" onclick="setval()" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
	<%} %>
	<html:button property="B2" value="ADD ROW" onclick="setAdd()" styleClass="ButtonBackgroundImage"></html:button>
	 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
	<html:button property="B3" value="SUBMIT" onclick="setsub('1')" styleClass="ButtonBackgroundImage"></html:button>
	<%}else{ %>
	<html:button property="B3" value="SUBMIT" onclick="setsub('1')" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
	<%} %>
	<html:button property="B3" value="CLEAR" onclick="reset123()" styleClass="ButtonBackgroundImage"></html:button>
	</p>
                
                <br><br>
                <div id = "div1" class = "myLayersClass">
                <core:if test="<%=podetails!=null %>">
                <table style="background-color:#CDCEAE" border="1">
                <tr>
                <td>From Date</td>
                <td>To Date</td>
                <td>From Amount</td>
                <td>To Amount</td>
                <td>Comm Rate</td>
                <td>Comm Type</td>
                <td>Min Comm Amt</td>
                <td>Extra Comm Rat</td>
                <td>DE Tml</td>
                <td>DE User</td>
                <td>DE Date</td>
                 </tr>
                <tr></tr>
                <%for(int k=0;k<podetails.length;k++){ %>
                <tr style="background-color:#ECEBD2">
                <td><%=podetails[k].getPOFromDate() %></td>
                <td><%=podetails[k].getPOToDate() %></td>
                <td><%=podetails[k].getPOFromAmt() %></td>
                <td><%=podetails[k].getPOToAmt() %></td>
                <td><%=podetails[k].getPOCommRate() %></td>
                <td><%=podetails[k].getPOCommType() %></td>
                <td><%=podetails[k].getPOMinCommAmt() %></td>
                
                <td><%=podetails[k].getPOExtraCommRate() %></td>
                <td><%=podetails[k].uv.getUserTml() %></td>
                <td><%=podetails[k].uv.getUserId() %></td>
                <td><%=podetails[k].uv.getUserDate() %></td>
                
                
                
                
                </tr>
                <%} %>
                </table>
                </core:if>
                <br><br><br>
                <core:if test="${showtab!=null}">
                <core:if test="<%= showtab.equals("showtable")%>">
                <table border="1" style="background-color:#CDCEAE">
                <tr>
                <td>From Date</td>
                <td>To Date</td>
                <td>From Amount</td>
                <td>To Amount</td>
                <td>Comm Rate</td>
                <td>Comm Type</td>
                <td>Min Comm Amt</td>
                <td>Extra Comm Rat</td>
                
                <td>DE Date</td>
                 </tr>
                 
                 <tr>
                <td><html:text property="fdate" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_date()" onblur="return datevalidation(this)"></html:text></td>
                <td><html:text property="tdate" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_date()" onblur="return datevalidation(this)"></html:text></td>
                <td><html:text property="famount" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_doublevalue()"></html:text></td>
                <td><html:text property="tamount" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_doublevalue()"></html:text></td>
                <td><html:text property="comrate" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_doublevalue()"></html:text></td>
                <td><html:select property="comtype">
                <html:option value="S">S</html:option><!--//S==Slabs,P==>Percentage
                --><html:option value="P">P</html:option>
                </html:select> </td>
                <td><html:text property="mincomamt" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_doublevalue()"></html:text></td>
                <td><html:text property="extcomrate" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers_doublevalue()"></html:text></td>
                <td><html:text property="dedate" value="<%=fdelegate.getSysDate() %>" disabled="disabled" ></html:text></td>
                 </tr>
                 
                 </table>
                
                </core:if>
                </core:if>
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