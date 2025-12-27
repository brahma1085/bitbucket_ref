<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>   
<%@page import="java.util.Map"%>
<%--
  User: Amzad
  Date: April 02, 2009
  
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.generalLedger.GLObject"%>
<html>
<head><title>GL Administration Screen</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>GL Administration Screen</center></h2>
      <hr>
      
    
    <script type="text/javascript">
     function set(target){
       document.forms[0].forward.value=target
      };
     
      function check_acnt(){
      
        if(document.getElementById("ac").value=='T'){
          alert("Account not found")
           document.forms[0].acno.value="";
        }
      };
        
   function clear_fun()
    {
    	
    	var ele=document.forms[0].elements;
    	document.getElementById("forward").style.display='none';  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    
    function numbersonly(eve){
         var cha = event.keyCode
            if (  ( cha  > 47 && cha < 58  ) ) {
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
      };
     
     
     function validate(){
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         alert(val);
       } 
       
       else{
         return false;
       }
     }; 
      function checkformat(ids) 
	        {
            
                      var cha =   event.keyCode;
                      var format = true;
                      var allow=true;

             var ff =  ids.value;
            
            var dd = ff.split( '/' );

			if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          alert (  " problem in date format " );
                          format = false ;
                          allow=false;
                          

                      } else if (  i==2 && dd[i].length != 4 ){

                           alert (  " problem in date format " );
                           format = false ;
                           allow=false;
                      }

            	 }
             } else{
                    allow=false;
             		format = false ;
             		alert (  " problem in date format " );
             }

        if(format){
        var date=new Date();
         var dayCheck=dd[0];
         
         var monthCheck=dd[1];
         var yrCheck=dd[2];
         var mth=dd[1];
         
         dd[1]=dd[1]-1;
	   var days = 32 - new Date(dd[2],dd[1],32).getDate();
         if(dd[0].length==2){
          if ( dd[0] > days  )
                        {

                              alert("Day should not be greater than "+days);
                              allow=false;
                              
                        }
                        
          
          if(dd[0]==00){
          alert("There is no date with 00");
          allow=false;
          }
          

          if(mth<1 || mth>12){
          alert("Month should be greater than 0 and \n lessthan 13  "+mth);
          allow=false;
          }
          }
          



         
          

         if(dd[2].length==4){
          if((parseInt(dd[2])<=parseInt(date.getYear()))){
          
                        
                        
                        
          }
          else{
          alert("Year should be less than "+date.getYear());
          allow=false;
          }
          }
          


         }
		if(allow){
		
		  
		  return true;
		}
		else{
		 return false;
		}

        }	
     
     
     function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%String result=(String)request.getAttribute("result"); 
String showDate=(String)request.getAttribute("ShowDate");
String showRadio=(String)request.getAttribute("ShowRadio");
String[] tableCols=(String[])request.getAttribute("tableinfo");
String[] newCols=(String[])request.getAttribute("newTableCols");

String[] tableAddRow=(String[])request.getAttribute("tablecols");
String nameoftable=(String)request.getAttribute("nameOfTable");
String newTablename=(String)request.getAttribute("newTableName");
String[] datatoset=(String[])request.getAttribute("dataToSet");
Object[][] objArrayData=(Object[][])request.getAttribute("objectArrayData");

String msg=(String)request.getAttribute("msg");
%>

<%if(msg!=null){ %>
  <font color="red"><%=msg %></font>
  <br><br>
<%} %>
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
<html:form action="/GL/Admin?pageId=12033">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
  <html:hidden property="flag"></html:hidden>
   <tr>
    <td>Select Table:&nbsp;&nbsp;&nbsp;&nbsp;<html:select property="table" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue;" onblur="setFlag('from_combo')">
    <!--<html:option value="GLPost">GLPost</html:option>
    <html:option value="GLTransactionType">GLTransactionType</html:option>
    <html:option value="GLMaster">GLMaster</html:option>
    <html:option value="GLKeyParam">GLKeyParam</html:option>
    <html:option value="Modules">Modules</html:option>
    --><html:option value="DailyStatus">DailyStatus</html:option>
    <html:option value="MonthlyStatus">MonthlyStatus</html:option>
    <!--<html:option value="DailyConStatus">DailyConStatus</html:option>
    <html:option value="MonthlyConStatus">MonthlyConStatus</html:option>
    --><html:option value="YearlyStatus">YearlyStatus</html:option>
    </html:select>
    <%
    if(showDate!=null){
    %>
    Enter Date:<html:text property="date" style="font-family:bold;color:blue;" onblur="return checkformat(this)"></html:text>
    <%} %>
    
    <%
    if(showRadio!=null){
    %>
    <html:radio property="userChoice" value="All">ALL</html:radio>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <html:radio property="userChoice" value="Gl">GL</html:radio>
    <%} %>
    </td>
    
   </tr>
   
   <tr>
     <td>
        <html:button  property="view" onclick="setFlag('view'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message></html:button>
        <html:button  property="addRow" onclick="setFlag('addRow'); " styleClass="ButtonBackgroundImage"><bean:message key="label.add.row"></bean:message> </html:button>
        <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
        <html:button  property="update" onclick="setFlag('update'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.update"></bean:message> </html:button>
        <html:button  property="insert" onclick="setFlag('submit'); " styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:button>
        <%}else{ %>
        <html:button  property="update" onclick="setFlag('update'); " styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="gl.label.update"></bean:message> </html:button>
        <html:button  property="insert" onclick="setFlag('submit'); " styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.submit"></bean:message> </html:button>
        <%} %>
        <!--<html:button  property="delete" onclick="set('delete'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.delete"></bean:message> </html:button>
        --><html:button  property="clear" onclick="setFlag('clear')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:button>
     </td>
   </tr>
 </table>
 <%
 if(newCols!=null && datatoset!=null && newTablename!=null){
 %>
 <table bgcolor="cyan" border="1" style="color:red;font-family:bold">
 <tr>
 <%
 for(int l=0;l<newCols.length;l++){
 %>
 <td><%=newCols[l].toString() %></td>
 <%} %>
 </tr>
 <%if(newTablename.equalsIgnoreCase("DailyStatus")){ %>
 <tr>
  <td>
    <html:text property="trnDate"></html:text>
  </td>
  <td>
    <html:select property="postInd">
       <html:option value="T">T</html:option>
       <html:option value="F">F</html:option>
    </html:select>
  </td>
  <td>
    <html:select property="cashClose">
       <html:option value="T">T</html:option>
       <html:option value="F">F</html:option>
    </html:select>
  </td>
  <td>
    <html:select property="dayClose">
       <html:option value="T">T</html:option>
       <html:option value="F">F</html:option>
    </html:select>
  </td>
  <td>
    <html:text property="deTml"></html:text>
  </td>
  <td>
    <html:text property="deUser"></html:text>
  </td>
  <td>
    <html:text property="dedtTime"></html:text>
  </td>
 </tr>
 <%}else if(newTablename.equalsIgnoreCase("YearlyStatus")){  %>
 <tr>
   <td>
    <html:text property="yearMth"></html:text>
  </td>
  <td>
    <html:text property="brCode"></html:text>
  </td>
  <td>
    <html:select property="yrPostInd">
       <html:option value="T">T</html:option>
       <html:option value="F">F</html:option>
    </html:select>
  </td>
  <td>
    <html:select property="yrClose">
       <html:option value="T">T</html:option>
       <html:option value="F">F</html:option>
    </html:select>
  </td>
  <td>
    <html:text property="yrDeTml"></html:text>
  </td>
  <td>
    <html:text property="yrDeUser"></html:text>
  </td>
  <td>
    <html:text property="yrDedtTime"></html:text>
  </td>
 </tr>
 <%}else if(newTablename.equalsIgnoreCase("MonthlyStatus")){  %>
 <tr>
     <td>
    <html:text property="yrMth"></html:text>
  </td>
  <td>
    <html:select property="mthPostInd">
       <html:option value="T">T</html:option>
       <html:option value="F">F</html:option>
    </html:select>
  </td>
  <td>
    <html:select property="mthClose">
       <html:option value="T">T</html:option>
       <html:option value="F">F</html:option>
    </html:select>
  </td>
  <td>
    <html:text property="mthDeTml"></html:text>
  </td>
  <td>
    <html:text property="mthDeUser"></html:text>
  </td>
  <td>
    <html:text property="mthDedtTime"></html:text>
  </td>
 </tr>
 <%} %>
 </table>
 <%
 }
 %>
 <%
 if(tableCols!=null){
 %>
 <div id="gl_admin" style="display:block;overflow:scroll;width:700px;height:250px">
 <table border=1 bordercolor="blue">
 <thead><b><font color="red">
 <%
 if(nameoftable!=null){
 %>
 <%=nameoftable %>
 <%} %></font></b></thead>
 <tr>
 <%
 if(tableCols!=null){%>
 <td><b>Check</b></td>
	 <%
 for(int i=0;i<tableCols.length;i++){
	 System.out.println("====Array items===="+tableCols[i]);
 %>
   
   <td><b>
   <%=tableCols[i].toString() %>
   </b>
   </td>
   <%}
 }
 %>
 </tr>
 
 <%
 
 if(objArrayData!=null ){
	 for(int j=0;j<objArrayData.length;j++){%><tr>
	 <td><input type="radio" name="selectedRadio" value="<%=j %>">
	 <%for(int k=0;k<tableCols.length;k++){	 
%>
 
 
 <td><input type="text" name="col<%=k%>" value="<%=objArrayData[j][k]%>" readonly="readonly">
 
 </td>
 <%} %>
 
 
 
 <%}%>
 </tr><%
	 }
	 %>
 
  </table>
  </div>
  <%} %>
  <%
  if(tableAddRow!=null){
  %>
  <table border=1 bordercolor="blue">
 <thead><b><font color="red">
 <%
 if(nameoftable!=null){
 %>
 <%=nameoftable %>
 <%} %></font></b></thead>
 <tr>
 <%
 
 for(int i=0;i<tableAddRow.length;i++){
	 
 %>
 
   <td><b>
   <%=tableAddRow[i].toString() %>
   </b>
   </td>
   <%}
  
 %>
 </tr>
 <tr>
 <%
 
 for(int i=0;i<tableAddRow.length;i++){
	 
 %>
 
   <td>
   <input type="text" name="txtFld" >
   
   </td>
   <%}
  
 %>
 </tr>
 </table>
 <%} %> 
  
  
  
 
 </html:form>
 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>
