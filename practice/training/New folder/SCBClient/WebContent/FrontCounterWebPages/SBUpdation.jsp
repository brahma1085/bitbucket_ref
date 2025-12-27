

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="java.util.Map"%>
<%--
  User: Amzad
  Date: Feb 19, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<html>
  <head><title>SB Updation</title>
      <font color="blue" ><center>
<h2 class="h2">AccountMaster Updation</h2></center></font>
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
    
    function only_alpa()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 97 && cha <=122)|| cha==32) 
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
		 if ( (cha >= 48 && cha <=57)||cha==47) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	
   function confirmsubmit(){
  
   
   
   }
    function setFlag1(flagValue){
    if(document.forms[0].acType.value=='SELECT')
    {
    	alert("fill all the details");
    	return false;
    }
    else if(document.forms[0].acNo.value=="")
    {
    	alert("fill all the details");
    	return false;
    }
    else
    {
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     }
     
     }
   
   function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
     
     function validate(){
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         alert(val);
       } 
       
       else{
         return false;
       }
     }; 
     function clearFunc()
    {
    	
    	var ele=document.forms[0].elements;
    	  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    		if(ele[i].type=="password")
    		{ 
    			ele[i].value="";
    		}
    		if(ele[i].type=="checkbox")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    
    </script>
  </head>
  <body class="Mainbody" onload="return validate()">
    <%!
    Map user_role;
    String access;
    %>
    <%String msg=(String)request.getAttribute("msg"); 
    
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
     <html:form action="/FrontCounter/SBUpdation?pageId=3041">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="pageId" value="3005"/>
     	<html:hidden property="sysdate" />
     	<core:if test="<%=msg!=null%>">
    <font color="red"><%=msg%></font>
    </core:if>
    <br><br>  
     	<table>
     	<tr>
     	   <td>
     	   <table background="#fffccc" style="border:thin solid #000000;">
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Account Type:
     	</td>
     	 <td style="background: threedlightshadow;"><html:select property="acType" >
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                    </td>
                
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Account Number:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue" >
     	  <html:text property="acNo" onblur="setFlag('from_acno')" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Customer Id:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="cid" readonly="true"></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Introducer AcType/No:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:select property="introducerAcType" disabled="true" >
                        <html:option value="SELECT" >SELECT</html:option>
                        <core:forEach var="acType" varStatus="count" items="${requestScope.AcType}" >
                        	    <html:option value="${acType.moduleCode}" ><core:out value="${acType.moduleAbbrv}-${acType.moduleDesc}"></core:out></html:option>
                        </core:forEach>
                        </html:select>
                    
     	  
     	  <html:text property="introducerAcNo" readonly="true" ></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  <html:checkbox property="ckBook" value="ChequeBook"><b>Cheque Book Issue</b></html:checkbox>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 Pass Book Sequence:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue" >
     	  <html:text property="pbSeq" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;"onkeyup="numberlimit(this,'10')" ></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 Interest Paid Date:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="intPayDate" readonly="true"></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 <html:checkbox property="freezed" value="Freezed"><b>Freezed</b></html:checkbox>
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:checkbox property="inOperative" value="InOperative"><b>InOperative</b></html:checkbox>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 Freezed Reason:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:textarea property="freezeReason" cols="15" rows="5" onkeypress="return only_alpa()"></html:textarea>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 Account Open Date:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="acOpenDate" readonly="true" ></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 Account Close Date:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="acCloseDate" onblur="return datevalidation(this)" onkeypress="return only_numbers_date()" onkeyup="numberlimit(this,'11')"></html:text>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	 Details:
     	</td>
     	 <!--<td align="center" style="font-family:bold;color:blue">
     	  <html:select property="details">
     	  <html:option value="Personal" style="font-family:bold;color:blue">Personal</html:option>
     	  <html:option value="Nominee" style="font-family:bold;color:blue">Nominee</html:option>
     	  <html:option value="JointHolders" style="font-family:bold;color:blue">Joint Holders</html:option>
     	  <html:option value="JointHolders" style="font-family:bold;color:blue">Signature Ins</html:option>
     	  </html:select>
     	 </td>
     	--></tr>
     	
     	
     	</table>
    
     	   </td>
     	   <td>
     	   <table>
<tr>
<td>
<%  String jspPath=(String)request.getAttribute("flag");
	System.out.println("'m inside panel"+jspPath);
	if(jspPath!=null)
	{
	System.out.println("wen 'm  null");
    %>
    <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
    
    <%}else{	%>
    <jsp:include page="/LockerWebPages/blank.jsp"></jsp:include>
    <%} %>
 	</td>
 	</tr>
 	</table>													
 	</td>
    </tr>
    </table>	
    <br>	
 	<table name="button_table" align="center">
     	<tr>
     	<td>
     	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
     	<html:button  property="update"  onclick="setFlag1('update'); " styleClass="ButtonBackgroundImage" ><bean:message key="gl.label.update"></bean:message> </html:button>
     	<%}else{ %>
     	<html:button  property="update"  onclick="setFlag1('update'); " styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="gl.label.update"></bean:message> </html:button>
     	<%} %>
        </td>
        <td>&nbsp;</td>
        <td>
        <html:reset   onclick="clearFunc()" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.cancel"></bean:message> </html:reset>
     	</td>
     	</table>
    
               </html:form><!--													
 	
     	   
     	    style="border:thin solid #000000;"
     	   
     	-->   
     	 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
     	
 </body>
  </html>          