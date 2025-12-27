<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.general.NomineeObject"%>
<%@page import="java.util.Map"%>
<html>
<head><title>Share Allotment</title>
  <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Nominee</center></h2>
      <hr  color="#339999">
      
    
    <script type="text/javascript">
    
    
    
    function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57) ) 
		 {
   		 	return true;
          } 
          else 
          {
   			alert("Enter Numbers Only");
   			return false;
          }
	};
    
     function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) ) 
 		{
   			return true;
       	} else 
       	{
   			alert("Enter Alphabets Only-Numbers Not Allowed");
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
    
    
    
    
    
    
    
    
    
     function set(target)
     {
        
    
    
    if(document.forms[0].name.value=="")
    {
    alert("Name Cannot Be Blank");
    }
    else if(document.forms[0].dob.value=="")
    {
     alert("DateOFBirth Cannot Be Blank");
    }
    else if(document.forms[0].gender.value=="")
    {
    alert("Gender Cannot Be Blank");
    }
    else if(document.forms[0].address.value=="")
    {
    alert("Address Cannot Be Blank");
    }
    else if(document.forms[0].rel_ship.value=="")
    {
    alert("RelationShip Cannot Be Blank");
    }
    else if(document.forms[0].percentage.value<100 || document.forms[0].percentage.value>100 )
    {
        alert("Nominee Percentage Should Be 100% Only");
    }   
    else
    {
        
        document.forms[0].forward.value=target;
        document.forms[0].submit();
    
    }
        
       
     };
     
    function DisableSubmit()
    {
    if(document.forms[0].has_ac.value=="No")
     {
      
      document.getElementById("cidlab").style.display='none';
      document.getElementById("cidtxt").style.display='none'; 
      
     }
     else
     {
      document.getElementById("cidlab").style.display='block';
      document.getElementById("cidtxt").style.display='block'; 
     }
    } 
     
     function clearfun()
     {
       alert("Clear");
       document.forms[0].cid.value="";
       document.forms[0].gender.value="";
       document.forms[0].address.value="";
       document.forms[0].rel_ship.value="";
       document.forms[0].percentage.value="";
       document.forms[0].dob.value="";
       document.forms[0].name.value="";
       
     };
     function totalsubmit()
     {
     if(document.forms[0].has_ac.checked)
     {
     	document.getElementById("tom").style.display='block';
     }
    }
     
     
    
     
     function checkpercentage()
     {
     
     if(document.getElementById("totaltesting").value!=0)
	{
		
		alert(document.getElementById("totaltesting").value);	
		
			
	}
     
     
     DisableSubmit();
      var val=document.forms[0].validations.value;
       if(val!=0 || val!=""){
         alert(val);
       
       } 
       else{ 
        
        return false;
       }
       
       
       
       var hidenull=document.forms[0].elements;
       for(var i=0;i<hidenull.length;i++)
       {
         if(hidenull[i].type=="hidden")
         {
         hidenull[i]="";
         }
       }  
       
     };
</script>
</head>
<%!
    CustomerMasterObject cusobj;
    Integer shnum=0;
    String cidcore;
    String NominAddr;
    ModuleObject[] mod_obj=null; 
    NomineeObject[] nomObj=null;
%>
<%!
String access;
String  accesspageId,nomineemsg;
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
<%
	nomineemsg=(String)request.getAttribute("nomineemsg");
  String ac_TYPE=(String)request.getAttribute("ACTyp");
  cusobj=(CustomerMasterObject)request.getAttribute("custdetails");
 nomObj=(NomineeObject[])request.getAttribute("NomDetail");
  shnum=(Integer)request.getAttribute("shnum");
  System.out.println("New Account NUmber======<<<>>>"+shnum);
  mod_obj=(ModuleObject[])request.getAttribute("Dep type");
  cidcore=(String)request.getAttribute("cid_psnt");
  NominAddr=(String)request.getAttribute("NOMADDR");
  System.out.println("The Address in NOM SJP--> "+NominAddr);
  System.out.println("value===============>"+cidcore);
  System.out.println("NomOBJ===============>"+nomObj);
  
%>
<body class="Mainbody" style="overflow: scroll;"  onload="checkpercentage()">
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Term/Nominee?pageId=13026">
  <table class="txtTable">
  <html:hidden property="forward"></html:hidden>
  <html:hidden property="validations"></html:hidden>
  <html:hidden property="testing" styleId="totaltesting"></html:hidden>
   <tr>
<%if(ac_TYPE!=null){%>  
    <td><bean:message key="label.actype"></bean:message></td>
    
     <td><html:text property="actype"  value="<%=""+ac_TYPE %>"  styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
      
      <%}else{ %>
      <td><html:text property="actype" readonly="true"></html:text></td>
      <%} %>
    
    
    <td><bean:message key="label.acno"></bean:message></td>
      <%if(shnum!=null){ %>
    <td><html:text property="acno" value="<%=""+shnum %>" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
    <%}else{ %>
    <td><html:text property="acno" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
    <%} %>
   </tr>
   
   <tr>
    <td>Has Account</td>
    <td><html:select property="has_ac" styleClass="formTextFieldWithoutTransparent" onchange="DisableSubmit()">
    	<html:option value="sel">Select</html:option>
    	<html:option value="Yes">Yes</html:option>
    	<html:option value="No">No</html:option>
    </html:select> </td>
    
   </tr>

    <tr> 
   <td><div id="cidlab" style="display:none;"><bean:message key="label.custid"></bean:message></div></td>   
   <td><div id="cidtxt" style="display:none;"><html:text property="cid" styleClass="formTextFieldWithoutTransparent"  onkeypress="return only_numbers()" onchange="submit()"></html:text></div></td>
   </tr>
   
  
   
          
   
   <tr>
    <td><bean:message key="label.name"></bean:message></td>
    <%if(cusobj!=null){ %> 
    <td><html:text property="name" value="<%=""+cusobj.getName() %>" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
    <%}else if(nomObj!=null){%>
    <td><html:text property="name" value="<%=""+nomObj[0].getNomineeName() %>" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
    <%}else { %>
    <td><html:text property="name"  styleClass="formTextFieldWithoutTransparent" onkeypress="return only_alpha()"></html:text> </td>
    <%} %>
   </tr>
   
   <tr>
     <td><bean:message key="label.dob"></bean:message> </td>
     <%if(cusobj!=null){ %>
     <td><html:text property="dob" value="<%=""+cusobj.getDOB() %>" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
    <%}else if(nomObj!=null){%>
    <td><html:text property="dob" value="<%=""+nomObj[0].getNomineeDOB() %>" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
    <%}else{ %>
     <td><html:text property="dob" styleClass="formTextFieldWithoutTransparent" onblur="return checkformat(this)"></html:text> </td>
     <%} %>
   </tr>
   
   <tr>
    <td><bean:message key="label.gender"></bean:message></td>
    <%if(cusobj!=null){ %>
    <td><html:select property="gender" value="<%=""+cusobj.getSex() %>" styleClass="formTextFieldWithoutTransparent">
    <html:option value="select">Select</html:option>
    <html:option value="M">Male</html:option>
    <html:option value="F">Female</html:option>
    </html:select></td>
    <%}else if(nomObj!=null){ %>
    <td><html:select property="gender" value="<%=""+nomObj[0].getSex() %>" styleClass="formTextFieldWithoutTransparent">
    <html:option value="select">Select</html:option>
    <html:option value="M">Male</html:option>
    <html:option value="F">Female</html:option>
    </html:select></td>
    <%}else{ %>
    <td><html:select property="gender">
    <html:option value="select">Select</html:option>
    <html:option value="M">Male</html:option>
    <html:option value="F">Female</html:option>
    </html:select></td>
    <%} %>
   </tr>
   
   <tr>
    <td><bean:message key="label.address"></bean:message></td>
      <%if(NominAddr!=null){ %>
    <td><html:textarea property="address"  value="<%=""+NominAddr%>" styleClass="formTextFieldWithoutTransparent"></html:textarea></td>
       <%}else if(nomObj!=null){%> 
        <td><html:textarea property="address" value="<%=""+nomObj[0].getNomineeAddress()%>" styleClass="formTextFieldWithoutTransparent"></html:textarea></td>
        <%}else{ %>
    <td><html:text property="address" styleClass="formTextFieldWithoutTransparent"></html:text></td>
     <%} %>
   </tr>
   
   <tr>
   <td><bean:message key="label.rel"></bean:message></td>
   <%if(nomObj!=null){ %>
   <td><html:text property="rel_ship" value="<%=""+nomObj[0].getNomineeRelation()%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
   <%}else{ %>
     
     <td><html:text property="rel_ship" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_alpha()"></html:text></td>
  <%}%> 
   </tr>

   <tr>
    <td><bean:message key="label.percentage"></bean:message></td>
    <%if(nomObj!=null){ %>
    <td><html:text property="percentage" styleClass="formTextFieldWithoutTransparent" value="<%=""+nomObj[0].getPercentage()%>"></html:text></td>
    <%}else{ %>
    <td><html:text property="percentage" styleClass="formTextFieldWithoutTransparent" value="100" onkeypress="return only_numbers()"></html:text></td>
   <%} %>
   </tr>
      
   <tr>
    <td>
    <%if(nomineemsg!=null){ %>
     <html:button property="subm1"  styleClass="ButtonBackgroundImage" ><bean:message key="label.submit"></bean:message> </html:button>
   		<%}else{ %>
   	<html:button property="subm" styleClass="ButtonBackgroundImage" onclick="set('Submit')"><bean:message key="label.submit"></bean:message> </html:button>	
  		<%} %>
  	 </td>
    <td align="left">
    <html:button    property="clbut"  styleClass="ButtonBackgroundImage" value="Clear" onclick="clearfun()"></html:button>
    </td> 
   </tr>
  </table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>