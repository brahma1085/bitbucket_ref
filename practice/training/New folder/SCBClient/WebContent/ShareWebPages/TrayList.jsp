<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>  
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Pending List</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Pending Tray List</center></h2>
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
            if (  ( cha  > 46 && cha < 58  ) ) {
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
     function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
     
    function btnEnable(){
    var val1=document.forms[0].butnStatus.value;
       alert("val1===="+val1);
    if(val1!=0 || val1!=""){
         if(val1=="Posting")
            document.getElementById('btnSubmit').disabled=false;
         else
            document.getElementById('btnSubmit').disabled=true;
       } 
       
       else{
         return false;
       }
    }
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
		
		  document.forms[0].submit();
		  return true;
		}
		else{
		 return false;
		}

        }	
     
    </script>

 </head>
  
<body class="Mainbody">

<html:form action="/GL/GLTrayList?pageId=12034">
<%
Object[][] pending=(Object[][])request.getAttribute("PendingTrayList");
%>
 <table class="txtTable">
 
 
   <tr>
    <td>Enter Date:   
      <html:text property="date" style="color:blue;font-family:bold" size="10"   onblur="return checkformat(this)" styleClass="formTextFieldWithoutTransparent"></html:text><b><font color="red"></font> </b>
      &nbsp;&nbsp;&nbsp;&nbsp;<html:submit property="sub"  styleClass="ButtonBackgroundImage"><bean:message key="gl.label.submit"></bean:message> </html:submit>
    </td>
   </tr>
   
   
 </table>
 <%
 if(pending!=null){
 %>
 <table border="1" background="gray">
 <tr>
   <td align="center" style="color:red">No.</td>
   <td align="center" style="color:red">TrayList</td>
 </tr>
 <%for(int i=0;i<pending.length;i++){ %>
 <tr>
   <td style="color:red"><%=pending[i][0] %> </td>
   <td style="color:red"><%=pending[i][1] %></td>
 </tr>
 <%} %>
 </table>
 <%} %>
</html:form>
</body>
</html>