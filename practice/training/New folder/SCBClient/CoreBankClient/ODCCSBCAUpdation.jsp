
<%@ page import="java.util.*" %>
<%@page import="java.util.Map"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Feb 20, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<html>
  <head><title>SB Updation</title>
       <font color="blue" ><center>
<h2 class="h2">OD/CC & SB/CA Updations</h2></center></font>
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
    
    function only_alpa()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 97 && cha <=122)||cha==32) 
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
	
	 function only_numbers_date()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57 ||cha==47)) 
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

    
    function beforeSub(){
    document.forms[0].updt.value='update';
    document.forms[0].submit();
    }
    
    
    function Addcoborrower(){
    
    document.forms[0].addco.value='addcoborrower';
    document.forms[0].submit();
    
    }
    
    function ClearAll(){
   
    var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="text")
    {
    ele[i].value="";
    
    }
    
    else if(ele[i].type=="hidden"){
     
    }
    }
   
   show(false,'div1');
    
    }
    
    function AddJoint(){
    document.forms[0].addjoint.value='ADDJOINT';
    document.forms[0].submit();
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
    Map user_role;
    String access;
    %>
    <%
    String msg=(String)request.getAttribute("msg");
    String Appln=(String)request.getAttribute("Appln");
    String coborrower=(String)request.getAttribute("coborrower");
    Vector vect=(Vector)request.getAttribute("cobordetails");
    String addcoborrower=(String)request.getAttribute("addcoborrower");
    
    String jointholder=(String)request.getAttribute("jointholder");
    int[] jointarray=(int[])request.getAttribute("jointarray");
    
    String addjoint=(String)request.getAttribute("addjoint");
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
     <html:form action="/FrontCounter/OdccSbcaUpdation?pageId=3057">
     	
       	<html:hidden property="pageId" value="3057"/>
       	<html:hidden property="updt"/>
       	<html:hidden property="addco"/>
       		<html:hidden property="addjoint"/>
       		<html:hidden property="sysdate" />
       		
       	<core:if test="<%=msg!=null %>">
       	<div id = "div1" class = "myLayersClass">
       	<font color="red"><%=msg %></font>
       	</div>
       	
       	</core:if>
     	<br><br><br>
    <table  class="txtTable" >
    <tr><td>
    Account Type:
    </td>
    <td height="33" width="252"><html:select property="acType" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select></td>
    </tr>
    <tr>
    <td>Account No.</td>
    <td><html:text property="acno" onblur="submit()" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'10')"></html:text> </td>
    </tr>
    <tr>
    <td>Details: </td>
    <td><html:select property="detail" onblur="submit()">
     <html:option value="SELECT">Select</html:option>
    <html:option value="Application">Application</html:option>
    <html:option value="CoBorrower">Co-Borrower</html:option>
    <html:option value="Jointholders">Joint Holders</html:option>
    
    </html:select>       </td>
    
    </tr>
    
    </table>
    
    <core:if test="<%=Appln!=null %>">
    
    <table class="txtTable" >
   <tr><td style="border:thin solid navy;"><font color="navy">Application Details</font></td></tr>
	<tr>
		<td>Purpose:</td>
		<td>
		<html:select property="purpose" styleClass="formTextFieldWithoutTransparent">
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="purpose" varStatus="count" items="${requestScope.purpose}" >
                        	    <html:option value="${purpose.purposeCode}" ><core:out value="${purpose.purposeDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>

</td>
	</tr>
	<tr>
		<td>Appln. Srl No:</td>
		<td><html:text property="srlno" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text></td>
	</tr>
	<tr>
		<td>Appln Date:</td>
		<td><html:text property="appdate" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')"></html:text></td>
	</tr>
	<tr>
		<td>Amount Required</td>
		<td><html:text property="loanamount" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'10')"></html:text></td>
	</tr>
	<tr>
		<td>Payment Mode:</td>
		<td>
		<html:select property="paymode">
		<html:option value="cash">Cash</html:option>
		
		</html:select>
	</td>
	</tr>
	<tr>
		<td>Interest Type:</td>
		<td>
	<html:select property="intType">
	<html:option value="1">Fixed</html:option>
		<html:option value="2">Floating</html:option>
	
	</html:select>
</td>
	</tr>
	<tr>
		<td>Inerest Calc. Type:</td>
		<td>
		<html:select property="intcalc">
		<html:option value="1">Daily basis</html:option>
	
		
		</html:select>
		</td>
	</tr>
	<tr>
		
		<td></td>
	</tr>
	
    
    </table>
    </core:if>
    <br>
    <core:if test="<%=coborrower!=null %>">
    <%if(vect!=null){ %>
   <b> <font color="red" ><%="Co-Borrower Details" %> </font></b>
   <br><br>
    <table border="1" style="border:thin solid navy;">
    	<tr><td><b> Account Type:</b></td>
    	<td><b>Account No.</b></td>
    	</tr>
    	<%for(int i=0;i<vect.size();i++){%>
				<tr>
				<td><b><%=vect.get(i).toString().substring(0,7) %></b></td>
				<td><b><%=vect.get(i).toString().substring(8) %></b></td>
				</tr>
<%}%>
    </table>
    
    
    <%}
    else{%>
    <%="click on Add Button to Add coborrower" %>
    <%} %>
    <br><br>
    <html:button property="b3" value="ADD COBORROWER" onclick="Addcoborrower()" styleClass="ButtonBackgroundImage"></html:button>
    <core:if test="<%=addcoborrower!=null %>">
    <table  class="txtTable" >
    <tr><td>
   <b> Account Type:</b>
    </td>
    <td height="33" width="252"><html:select property="coacType" styleClass="formTextFieldWithoutTransparent">
                       
                        <core:forEach var="acType" varStatus="count" items="${requestScope.sh}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
                        </core:forEach>
                        </html:select></td>
    </tr>
    <tr>
    <td><b>Account No.</b></td>
    <td><html:text property="coacno" onblur="submit()" onkeyup="numberlimit(this,'10')"></html:text> </td>
    
    </tr>
    </table>
    
    
    </core:if>
    </core:if>
    
    <core:if test="<%=jointholder!=null %>">
    <core:if test="<%=jointarray!=null %>">
    <u><b>List Of JointHolder's</b></u><BR>
    <table border="1"><tr><td><b>JointHolder CID</b></td></tr>
    <%for(int d=0;d<jointarray.length;d++){ %>
    <tr><td><b><%=jointarray[d] %></b></td></tr>
    <%} %>
    </table>
    </core:if>
    <br><br>
    <html:button property="b6" value="ADD JOINTHOLDER" onclick="AddJoint()" styleClass="ButtonBackgroundImage"></html:button>
    <br><br>
    <core:if test="<%=addjoint!=null %>">
    <table>
    <tr><td><b>CID: </b></td>
    <td><html:text property="jointcid" onblur="submit()" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'10')"></html:text></td></tr>
    <tr><td><b>Name</b></td><td><html:text property="jointname" onkeypress="return false;"></html:text></td></tr>
    <tr><td><b>Signature Int.</b></td><td><html:text property="signinst" onkeypress="return only_alpa()"></html:text></td>
    </tr>
    </table>
    
    </core:if>
    
    </core:if>
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
    <html:button property="b1" value="Update" onclick="beforeSub()" styleClass="ButtonBackgroundImage"></html:button>
    <%}else{ %>
    <html:button property="b1" value="Update" onclick="beforeSub()" styleClass="ButtonBackgroundImage" disabled="true"></html:button>
    <%} %>
    <html:button property="b1" value="Clear" onclick="ClearAll()" styleClass="ButtonBackgroundImage"></html:button> 
               </html:form>													
 	
     	   
     <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	   
     	   
     	   
     	 	
     	
  </body>
  </html>          