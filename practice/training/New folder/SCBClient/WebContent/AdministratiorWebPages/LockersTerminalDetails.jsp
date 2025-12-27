

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Amzad
  Date: Feb 25, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<html>
  <head><title>Lockers</title>
      <h1 style="font:small-caps;font-style:normal;color: blue" ><center>Lockers</center></h1>
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
   function confirmsubmit(){
  
   
   
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
     function checkAll(){
     
     if(document.forms[0].lkReadAll.checked){
                          document.forms[0].mainMenuR.checked=true;
                          document.forms[0].menuR.checked=true;
		    			  document.forms[0].newLkR.checked=true;
		    			  document.forms[0].extensionR.checked=true;
		    			  document.forms[0].lkOperR.checked=true;
		    			  document.forms[0].autoR.checked=true;
		    			  document.forms[0].adminR.checked=true;
		    			  document.forms[0].cabDER.checked=true;
		    			  document.forms[0].cabinetR.checked=true;
		    			  document.forms[0].issueSurR.checked=true;
		    			  document.forms[0].lkOwnR.checked=true;
		    			  document.forms[0].lkRemainderR.checked=true;
		    			  document.forms[0].lksOperR.checked=true;
		    			  document.forms[0].lkrentR.checked=true;
		    			  document.forms[0].lkTypesR.checked=true;
		    			  document.forms[0].masterR.checked=true;
		    			  document.forms[0].newLkR.checked=true;
		    			  document.forms[0].notSurrR.checked=true;
		    			  document.forms[0].pbR.checked=true;
		    			  document.forms[0].rentCR.checked=true;
		    			  document.forms[0].rentDueR.checked=true;
		    			  document.forms[0].reportsR.checked=true;
		    			  document.forms[0].surrenderR.checked=true;
     }
     else
     {
                          document.forms[0].mainMenuR.checked=false;
                          document.forms[0].menuR.checked=false;
		    			  document.forms[0].newLkR.checked=false;
		    			  document.forms[0].extensionR.checked=false;
		    			  document.forms[0].lkOperR.checked=false;
		    			  document.forms[0].autoR.checked=false;
		    			  document.forms[0].adminR.checked=false;
		    			  document.forms[0].cabDER.checked=false;
		    			  document.forms[0].cabinetR.checked=false;
		    			  document.forms[0].issueSurR.checked=false;
		    			  document.forms[0].lkOwnR.checked=false;
		    			  document.forms[0].lkRemainderR.checked=false;
		    			  document.forms[0].lksOperR.checked=false;
		    			  document.forms[0].lkrentR.checked=false;
		    			  document.forms[0].lkTypesR.checked=false;
		    			  document.forms[0].masterR.checked=false;
		    			  document.forms[0].newLkR.checked=false;
		    			  document.forms[0].notSurrR.checked=false;
		    			  document.forms[0].pbR.checked=false;
		    			  document.forms[0].rentCR.checked=false;
		    			  document.forms[0].rentDueR.checked=false;
		    			  document.forms[0].reportsR.checked=false;
		    			  document.forms[0].surrenderR.checked=false;
     }
     
     if(document.forms[0].lkInsertAll.checked){
                          document.forms[0].mainMenuI.checked=true;
                          document.forms[0].menuI.checked=true;
		    			  document.forms[0].newLkI.checked=true;
		    			  document.forms[0].extensionI.checked=true;
		    			  document.forms[0].lkOperI.checked=true;
		    			  document.forms[0].autoI.checked=true;
		    			  document.forms[0].adminI.checked=true;
		    			  document.forms[0].cabDEI.checked=true;
		    			  document.forms[0].cabinetI.checked=true;
		    			  document.forms[0].issueSurI.checked=true;
		    			  document.forms[0].lkOwnI.checked=true;
		    			  document.forms[0].lkRemainderI.checked=true;
		    			  document.forms[0].lksOperI.checked=true;
		    			  document.forms[0].lkrentI.checked=true;
		    			  document.forms[0].lkTypesI.checked=true;
		    			  document.forms[0].masterI.checked=true;
		    			  document.forms[0].newLkI.checked=true;
		    			  document.forms[0].notSurrI.checked=true;
		    			  document.forms[0].pbI.checked=true;
		    			  document.forms[0].rentCI.checked=true;
		    			  document.forms[0].rentDueI.checked=true;
		    			  document.forms[0].reportsI.checked=true;
		    			  document.forms[0].surrenderI.checked=true;
     }
     else
     {
                          document.forms[0].mainMenuI.checked=false;
                          document.forms[0].menuI.checked=false;
		    			  document.forms[0].newLkI.checked=false;
		    			  document.forms[0].extensionI.checked=false;
		    			  document.forms[0].lkOperI.checked=false;
		    			  document.forms[0].autoI.checked=false;
		    			  document.forms[0].adminI.checked=false;
		    			  document.forms[0].cabDEI.checked=false;
		    			  document.forms[0].cabinetI.checked=false;
		    			  document.forms[0].issueSurI.checked=false;
		    			  document.forms[0].lkOwnI.checked=false;
		    			  document.forms[0].lkRemainderI.checked=false;
		    			  document.forms[0].lksOperI.checked=false;
		    			  document.forms[0].lkrentI.checked=false;
		    			  document.forms[0].lkTypesI.checked=false;
		    			  document.forms[0].masterI.checked=false;
		    			  document.forms[0].newLkI.checked=false;
		    			  document.forms[0].notSurrI.checked=false;
		    			  document.forms[0].pbI.checked=false;
		    			  document.forms[0].rentCI.checked=false;
		    			  document.forms[0].rentDueI.checked=false;
		    			  document.forms[0].reportsI.checked=false;
		    			  document.forms[0].surrenderI.checked=false;
     }
     if(document.forms[0].lkUpdateAll.checked){
                          document.forms[0].mainMenuU.checked=true;
                          document.forms[0].menuU.checked=true;
		    			  document.forms[0].newLkU.checked=true;
		    			  document.forms[0].extensionU.checked=true;
		    			  document.forms[0].lkOperU.checked=true;
		    			  document.forms[0].autoU.checked=true;
		    			  document.forms[0].adminU.checked=true;
		    			  document.forms[0].cabDEU.checked=true;
		    			  document.forms[0].cabinetU.checked=true;
		    			  document.forms[0].issueSurU.checked=true;
		    			  document.forms[0].lkOwnU.checked=true;
		    			  document.forms[0].lkRemainderU.checked=true;
		    			  document.forms[0].lksOperU.checked=true;
		    			  document.forms[0].lkrentU.checked=true;
		    			  document.forms[0].lkTypesU.checked=true;
		    			  document.forms[0].masterU.checked=true;
		    			  document.forms[0].newLkU.checked=true;
		    			  document.forms[0].notSurrU.checked=true;
		    			  document.forms[0].pbU.checked=true;
		    			  document.forms[0].rentCU.checked=true;
		    			  document.forms[0].rentDueU.checked=true;
		    			  document.forms[0].reportsU.checked=true;
		    			  document.forms[0].surrenderU.checked=true;
     }
     else
     {
                          document.forms[0].mainMenuU.checked=false;
                          document.forms[0].menuU.checked=false;
		    			  document.forms[0].newLkU.checked=false;
		    			  document.forms[0].extensionU.checked=false;
		    			  document.forms[0].lkOperU.checked=false;
		    			  document.forms[0].autoU.checked=false;
		    			  document.forms[0].adminU.checked=false;
		    			  document.forms[0].cabDEU.checked=false;
		    			  document.forms[0].cabinetU.checked=false;
		    			  document.forms[0].issueSurU.checked=false;
		    			  document.forms[0].lkOwnU.checked=false;
		    			  document.forms[0].lkRemainderU.checked=false;
		    			  document.forms[0].lksOperU.checked=false;
		    			  document.forms[0].lkrentU.checked=false;
		    			  document.forms[0].lkTypesU.checked=false;
		    			  document.forms[0].masterU.checked=false;
		    			  document.forms[0].newLkU.checked=false;
		    			  document.forms[0].notSurrU.checked=false;
		    			  document.forms[0].pbU.checked=false;
		    			  document.forms[0].rentCU.checked=false;
		    			  document.forms[0].rentDueU.checked=false;
		    			  document.forms[0].reportsU.checked=false;
		    			  document.forms[0].surrenderU.checked=false;
     }
     
     if(document.forms[0].lkDeleteAll.checked){
                          document.forms[0].mainMenuD.checked=true;
                          document.forms[0].menuD.checked=true;
		    			  document.forms[0].newLkD.checked=true;
		    			  document.forms[0].extensionD.checked=true;
		    			  document.forms[0].lkOperD.checked=true;
		    			  document.forms[0].autoD.checked=true;
		    			  document.forms[0].adminD.checked=true;
		    			  document.forms[0].cabDED.checked=true;
		    			  document.forms[0].cabinetD.checked=true;
		    			  document.forms[0].issueSurD.checked=true;
		    			  document.forms[0].lkOwnD.checked=true;
		    			  document.forms[0].lkRemainderD.checked=true;
		    			  document.forms[0].lksOperD.checked=true;
		    			  document.forms[0].lkrentD.checked=true;
		    			  document.forms[0].lkTypesD.checked=true;
		    			  document.forms[0].masterD.checked=true;
		    			  document.forms[0].newLkD.checked=true;
		    			  document.forms[0].notSurrD.checked=true;
		    			  document.forms[0].pbD.checked=true;
		    			  document.forms[0].rentCD.checked=true;
		    			  document.forms[0].rentDueD.checked=true;
		    			  document.forms[0].reportsD.checked=true;
		    			  document.forms[0].surrenderD.checked=true;
     }
     else
     {
                          document.forms[0].mainMenuD.checked=false;
                          document.forms[0].menuD.checked=false;
		    			  document.forms[0].newLkD.checked=false;
		    			  document.forms[0].extensionD.checked=false;
		    			  document.forms[0].lkOperD.checked=false;
		    			  document.forms[0].autoD.checked=false;
		    			  document.forms[0].adminD.checked=false;
		    			  document.forms[0].cabDED.checked=false;
		    			  document.forms[0].cabinetD.checked=false;
		    			  document.forms[0].issueSurD.checked=false;
		    			  document.forms[0].lkOwnD.checked=false;
		    			  document.forms[0].lkRemainderD.checked=false;
		    			  document.forms[0].lksOperD.checked=false;
		    			  document.forms[0].lkrentD.checked=false;
		    			  document.forms[0].lkTypesD.checked=false;
		    			  document.forms[0].masterD.checked=false;
		    			  document.forms[0].newLkD.checked=false;
		    			  document.forms[0].notSurrD.checked=false;
		    			  document.forms[0].pbD.checked=false;
		    			  document.forms[0].rentCD.checked=false;
		    			  document.forms[0].rentDueD.checked=false;
		    			  document.forms[0].reportsD.checked=false;
		    			  document.forms[0].surrenderD.checked=false;
     }
     }
     
    
    </script>
  </head>
  <body class="Mainbody" onload="return validate()">
    
    
    
     <html:form action="/Administrator/LockersTmlDetails?pageId=10010">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="pageId" value="3005"/>
     	<center>
     	<div id="lockers" style="display:block;overflow:scroll;width:400px;height:250px">
     	<table border="1" bgcolor="#fffccc">
     	<thead><font color:"red"><b>Lockers</b></font></thead>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Form Name
     	  </td>
     	  <td style="color:blue;font-family:bold">
     	     Read
     	  </td>
     	  <td style="color:blue;font-family:bold">
     	     Insert
     	  </td>
     	  <td style="color:blue;font-family:bold">
     	     Update
     	  </td>
     	  <td style="color:blue;font-family:bold">
     	     Delete
     	  </td>
     	</tr>
     	<tr>
     	
     	  <td style="color:blue;font-family:bold">
     	     &nbsp;
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkReadAll" value="ReadAll" onclick="checkAll()">ALL</html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkInsertAll" value="InsertAll" onclick="checkAll()">ALL</html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkUpdateAll"  value="UpdateAll" onclick="checkAll()">ALL</html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkDeleteAll" value="DeleteAll" onclick="checkAll()">ALL</html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:red;font-family:bold">
     	     Lockers Main Menu
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="mainMenuR" value="mainMenuR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="mainMenuI" value="mainMenuI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="mainMenuU" value="mainMenuU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="mainMenuD" value="mainMenuD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:red;font-family:bold">
     	     Lockers Menu
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="menuR" value="menuR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="menuI" value="menuI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="menuU" value="menuU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="menuD" value="menuD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Issue New Locker
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="newLkR" value="newLkR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="newLkI" value="newLkI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="newLkU" value="newLkU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="newLkD" value="newLkD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Extension
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="extensionR" value="extensionR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="extensionI" value="extensionI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="extensionU" value="extensionU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="extensionD" value="extensionD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Auto Extension
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="autoR" value="autoR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="autoI" value="autoI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="autoU" value="autoU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="autoD" value="autoD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Surrender
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="surrenderR" value="surrenderR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="surrenderI" value="surrenderI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="surrenderU" value="surrenderU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="surrenderD" value="surrenderD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:red;font-family:bold">
     	     Locker Operation Menu
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkOperR" value="lkOperR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkOperI" value="lkOperI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkOperU" value="lkOperU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkOperD" value="lkOperD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:red;font-family:bold">
     	     Cabinet View Menu
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="cabinetR" value="cabinetR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="cabinetI" value="cabinetI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="cabinetU" value="cabinetU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="cabinetD" value="cabinetD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:red;font-family:bold">
     	     Passbook Menu
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="pbR" value="pbR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="pbI" value="pbI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="pbU" value="pbU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="pbD" value="pbD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:red;font-family:bold">
     	     Reports Menu
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="reportsR" value="reportsR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="reportsI" value="reportsI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="reportsU" value="reportsU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="reportsD" value="reportsD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Locker Owners
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkOwnR" value="lkOwnR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkOwnI" value="lkOwnI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkOwnU" value="lkOwnU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkOwnD" value="lkOwnD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Locker Operation
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lksOperR" value="lksOperR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lksOperI" value="lksOperI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lksOperU" value="lksOperU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lksOperD" value="lksOperD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Locker Issued/Surrendered
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="issueSurR" value="issueSurR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="issueSurI" value="issueSurI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="issueSurU" value="issueSurU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="issueSurD" value="issueSurD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Locker Rent Due
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="rentDueR" value="rentDueR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="rentDueI" value="rentDueI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="rentDueU" value="rentDueU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="rentDueD" value="rentDueD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Locker Rent Collected
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="rentCR" value="rentCR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="rentCI" value="rentCI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="rentCU" value="rentCU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="rentCD" value="rentCD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Locker Reminder
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkRemainderR" value="lkRemainderR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkRemainderI" value="lkRemainderI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkRemainderU" value="lkRemainderU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkRemainderD" value="lkRemainderD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	     Locker Not Surrendered
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="notSurrR" value="notSurrR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="notSurrI" value="notSurrI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="notSurrU" value="notSurrU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="notSurrD" value="notSurrD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:red;font-family:bold">
     	     Admin Menu
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="adminR" value="adminR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="adminI" value="adminI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="adminU" value="adminU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="adminD" value="adminD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	    Locker Types
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkTypesR" value="lkTypesR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkTypesI" value="lkTypesI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkTypesU" value="lkTypesU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkTypesD" value="lkTypesD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	    Locker Rent
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkrentR" value="lkrentR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkrentI" value="lkrentI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkrentU" value="lkrentU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="lkrentD" value="lkrentD"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	    Locker Cabinet DE
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="cabDER" value="cabDER"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="cabDEI" value="cabDEI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="cabDEU" value="cabDEU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="cabDED" value="cabDED"></html:checkbox>
     	  </td>
     	</tr>
     	<tr>
     	  <td style="color:blue;font-family:bold">
     	    Modify Master
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="masterR" value="masterR"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="masterI" value="masterI"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="masterU" value="masterU"></html:checkbox>
     	  </td>
     	  <td style="color:red;font-family:bold">
     	     <html:checkbox property="masterD" value="masterD"></html:checkbox>
     	  </td>
     	</tr>
     	</table>
     	</div>
     	 
     </html:form>
  </body>
  </html>          