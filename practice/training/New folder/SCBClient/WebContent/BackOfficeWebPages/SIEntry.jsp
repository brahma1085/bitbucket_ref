<%--
  Created by IntelliJ IDEA.     
  User: admin
  Date: Nov 11, 2007
  Time: 11:54:38 AM
  To change this template use File | Settings | File Templates.
--%>

<%--<%@ page import="SIEntry.SIEntryForm"%>--%>

<!--<%@ page import="com.scb.bk.forms.SIEntryComboElements" %>
--> 
<%@ page import="masterObject.backOffice.AdminObject"%>
<%@ page import="masterObject.backOffice.*" %>
<%@ page import="masterObject.general.ModuleObject"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>


<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Enumeration"%>
<%@page import="masterObject.backOffice.SIEntryObject"%>
<%@page import="masterObject.general.AccountObject"%>

<%AdminObject[] arraymastertable=(AdminObject[])request.getAttribute("arraymastertable");
String inst_no=(String)request.getAttribute("instr_no");
ModuleObject[] mod_abbr=(ModuleObject[])request.getAttribute("Module_Abbr");

%>
<% ModuleObject[]array_moduleobject_mod=(ModuleObject[])request.getAttribute("array_moduleobject_mod"); %>
<%
String si[][]=(String[][])request.getAttribute("String_adminobject");
String msg = (String)request.getAttribute("msg");
String emsg = (String)request.getAttribute("emsg");
String displaymsg = (String)request.getAttribute("displaymsg");
%>


<html>
  <head><title>SIEntry</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      
  <script language="javascript">
       
        var dtCh= "/";
 var minYear=1900;
 var maxYear=9999;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
};

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
};

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
};
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
};

function onlynumbers()
{
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        };

function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strMonth=dtStr.substring(pos1+1,pos2)
     
	var strDay=dtStr.substring(0,pos1)
     
	var strYear=dtStr.substring(pos2+1)
     

	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	
	if (pos1==-1 || pos2==-1){
		alert("The date format should be : dd/mm/yyyy")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Please enter a valid day as this year is not a leap year")
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Please enter a valid date")
		return false
	}
return true
};

function ValidateForm(){
   
	var dt=document[0].due_date;
	var dt1=document[0].last_exec_date;
	if (isDate(dt.value)==false){
	       	
		return false
	}
	if (isDate(dt1.value)==false){
	       	
		return false
	}
	
    return true
 };
       
       
       function set(target){
        alert(target);
          document.forms[0].forward.value=target;
          };
     
     //validation for null value     
    
           
     //clearing form
        function clears(){
        alert("clearing form");
         	 document.forms[0].priority_num.value="";                  
             document.forms[0].from_ac_ty.value="";
         	 document.forms[0].from_ac_no.value="";
        	 document.forms[0].to_ac_ty.value="";
         	 document.forms[0].to_ac_no.value="";
         	 document.forms[0].due_date.value="";
         	 document.forms[0].period_mon.value="";
         	 document.forms[0].days.value="";
         	 document.forms[0].amount.value="";
         	 document.forms[0].no_of_times_exec.value="";
         	 document.forms[0].last_exec_date.value="";
         	 document.forms[0].expiry_days.value="";
         	 
          }
          
          
           function callClear(){
           
         	 var ele= document.forms[0].elements;
             for(var i=0;i<ele.length;i++){
               if(ele[i].type=="text"){
                  
               ele[i].value="";
               }
           
           }
            };


             function instruction()
             {
         
          	if(document.forms[0].instnum.value!=0)
          	{
          		alert("note the instruction no."+document.forms[0].instnum.value);
          		callClear()
          		return false;
          	}
          	if(document.getElementById("invalid_ins").value=="invalid")
          	{
          	  alert("Instruction Number Not Valid")
          	  callClear()
          	  return false;
          	}
          	
          	if(document.getElementById("invalid_ins").value=="alverified")
          	{
          	  alert("Inst Number Already Verified")
          	  callClear()
          	  return false;
          	}
          	
          	if(document.getElementById("verifyfun").value!=null)
          	{
          	  if(document.getElementById("verifyfun").value=="verified")
          	  {
          	     alert("Standing Instruction Verified Sucessfully");
          	     callClear()
          	     return false;
          	  }
          	   if(document.getElementById("verifyfun").value=="update")
          	   {
          	     alert("Standing Instruction Updated Sucessfully");
          	     callClear()
          	     return false;
          	   }
          	}
    
          	
          	if(document.getElementById("accobject").value!=null)
          	{
          		if(document.getElementById("accobject").value=="freeze")
          		{
          			alert("Account Freezed....");
          		}
          		
          		if(document.getElementById("accobject").value=="verified")
          		{
          			alert("Account not verified....");
          		}
          		
          		if(document.getElementById("accobject").value=="Default")
          		{
          			alert("Default Account....");
          		}
          		
          		if(document.getElementById("accobject").value=="Closed")
          		{
          			alert("Account Closed....");
          		}
          		
          		if(document.getElementById("accobject").value=="Closed")
          		{
          			alert("Account Closed....");
          		}
          		if(document.getElementById("accobject").value=="AccountNotFound")
          		{
          			alert("Account Not Found");
          		}
          	}
            var i=document.forms[0].elements;
            for(var j=0;j<i.length;j++)
            {
            	if(i[j].type=="hidden")
            	{
            		i[j].value="";
            	}
            }    	
      };
          
          
          function button_insert(target)
          {
          
          	
          	document.forms[0].forward.value=target;
            var entryval=confirm("Confirm Entry????");
            if(entryval==true)
            	{
           		 	document.getElementById("instruct").value="true";
           			document.forms[0].submit();
           			
         		}
          	else 
          		{
          			document.getElementById("instruct").value="false";
          				return false;
          		}
          };
              
              
             function validfn(fnm,str){
             var len=fnm.length;
             if(len==0){
               alert("Enter the :"+str )
             }
             else
             {
              document.forms[0].submit();
             }
           };
          
          
          function button_update(target){
            
            document.forms[0].forward.value=target;
         	var updateval=confirm("Confirm Update????");
         	
         	
         	if(updateval==true)
         	{
         		document.getElementById("instruct").value="true";
         		document.forms[0].submit();
         	
         		
         	}
         	else 
         	{ 
         		document.getElementById("instruct").value="false";
         		return false;
         	}
         	};
         	
        
         
         	
         function button_verify(target){
         	    
                document.forms[0].forward.value=target;
         	    var verificationval=confirm("Confirm Verify????");
         	
             	if(verificationval==true)
         	      {
         		document.getElementById("instruct").value="true";
         	     document.forms[0].submit();
         		
         	     }
         	   else
         	     { 
         		 document.getElementById("instruct").value="false";
         		 return false;
         	   }
            };         	
          
          
          
          
        
      </script>
  </head>
  <body class="Mainbody" style="overflow: scroll;" onload="instruction()">
 
 <center><h2 class="h2">Standing Instruction Entry</h2></center>
  <%if(displaymsg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=displaymsg %></font>
<%} %>

 <%if(msg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=msg %></font>
<%} %>
 
 <%!
    
     AccountObject accObj;
     ModuleObject[] modObj,modObj1;
     Hashtable loan_options;
     int i;
 %>
 <%@page import="java.util.Map"%>


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
    <%
        String date=(String)request.getAttribute("date");
        System.out.println("i am here&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"); %>

    <% SIEntryObject siretriveObj=(SIEntryObject)request.getAttribute("SIUpdate");
        System.out.println("i inside si"); %>
        <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
 <html:form action="/BackOffice/SIEntry?pageId=11001" focus="<%=""+request.getAttribute("priority")%>">
  
  <%if(si!=null){
			
		System.out.println("cashobject====="+arraymastertable[0].getPriorityNo());
		System.out.println("cashobject====="+arraymastertable[0].getFromType());
		%>
		<div id="table" style="overflow:scroll;height:100;width:200">
		<table border="1">
		<tr>
		<td>Pri_Num</td><td>FrmAcType</td><td>ToAcType</td>
		</tr>
	<% for(int i= 0; i <si.length;i++){%>
	<tr>

	<td><input type="text" disabled="true" size="3" value="<%=""+si[i][0]%>" style="color:red;"></td>
	<td><input type="text" disabled="true" size="4" value="<%=""+si[i][1]%>" style="color:red;"></td>
	<td><input type="text" disabled="true" size="4" value="<%=""+si[i][2]%>" style="color:red;"></td>
	
	</tr>
	<%} %>
	</table>
	</div>
<%} %>
 
  <table>
 	
 	 <td width="40%">
 	         <table align="left">

    	    <tr>
    	    	<html:hidden property="accnovalid" styleId="accobject"/>
        	    <html:hidden property="instnum" />
        	    <html:hidden property="invalid_ins" styleId="invalid_ins"/>
        	    <html:hidden property="insvalue" styleId="instruct"/>
        	    <html:hidden property="verifyfun" styleId="verifyfun"/>
        	 
        	   <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.instruction_num"  ></bean:message></font></td>
        	     <%if(inst_no!=null){ %>
        	   <td><html:text property="instruction_num"  size="8"  onkeypress="return onlynumbers()" value="<%=""+request.getAttribute("instr_no")%>" onblur="submit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
        	   <% }else{%>
        	   <td><html:text property="instruction_num"  size="8" onkeypress="return onlynumbers()" value="0" onblur="submit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
        	   <%} %>
         	</tr>
         	<tr>
               <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.priority_num" ></bean:message></font></td>
               <core:choose>
               <core:when test="${requestScope.SIUpdate!=null}">
               <td><html:text property="priority_num" size="8" onkeypress="return onlynumbers()" value="<%=""+siretriveObj.getPriorityNo()%>" styleClass="formTextFieldWithoutTransparent"
               ></html:text></td>
               </core:when>
               <core:otherwise>
               <td><html:text property="priority_num" size="8" onkeypress="return onlynumbers()" onblur="submit()" styleClass="formTextFieldWithoutTransparent" 
               ></html:text></td>
               </core:otherwise>
               </core:choose>  
            </tr>
            <tr>
           <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.from_acc_num" ></bean:message></font></td>
           <td><html:select property="from_ac_ty" onblur="validfn(from_ac_ty.value,'From AccountType')" styleClass="formTextFieldWithoutTransparent">	
                <%if(mod_abbr!=null){%>
						<%for(int i=0;i<mod_abbr.length;i++){ %>
						 
						<html:option value="<%=""+mod_abbr[i].getModuleCode() %>"><%=mod_abbr[i].getModuleAbbrv()%></html:option>
					<%} } %></html:select></td>
					  <core:choose>
                 <core:when test="${requestScope.SIUpdate!=null}">
			<td><html:text property="from_ac_no" size="6" onblur="submit()" value="<%=""+siretriveObj.getFromAccNo()%>"  onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
             </core:when>
               <core:otherwise>
               <td><html:text property="from_ac_no" size="6" onblur="submit()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
               </core:otherwise>
               </core:choose>
             </tr>
             
             <tr>
              <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.to_acc_num" ></bean:message></font></td>
                <td><html:select property="to_ac_ty" onblur="validfn(to_ac_ty.value,'To AccountType')" styleClass="formTextFieldWithoutTransparent">	
                <%if(mod_abbr!=null){%>
						<%for(int i=0;i<mod_abbr.length;i++){ %>
						 
						<html:option value="<%=""+mod_abbr[i].getModuleCode() %>"><%=mod_abbr[i].getModuleAbbrv()%></html:option>
					<%} } %></html:select></td>
					  <core:choose>
                 <core:when test="${requestScope.SIUpdate!=null}">
			    <td><html:text size="6" property="to_ac_no"  value="<%=""+siretriveObj.getToAccNo()%>" onblur="submit()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			    </core:when>
               <core:otherwise>
                <td><html:text size="6" property="to_ac_no" onblur="submit()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
               </core:otherwise>
               </core:choose>
             </tr>
             
        	<tr>
               <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.loan_option" ></bean:message></font></td>
               <td><html:select property="loan_option" >
                  <% System.out.println("hhhhhhhhhhhhhhhhhhhhhhaaaaaaaaaaa");
                     loan_options=(Hashtable)request.getAttribute("loanoption");
                     Object key;  
                     Enumeration en=loan_options.keys();
                      while(en.hasMoreElements()){
                      	key=en.nextElement();
                   %> 
                   <html:option value="<%=""+key%>" ><%=loan_options.get(key)%></html:option>
                   <%} %>
                   </html:select></td>
           </tr>
           <tr>
             <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.due_date" ></bean:message></font></td>
             <core:choose>
             <core:when test="${requestScope.SIUpdate!=null}">
               <td><html:text property="due_date" size="9" onkeypress="return onlynumbers()" value="${requestScope.SIUpdate.dueDt}" styleClass="formTextFieldWithoutTransparent"
               ></html:text></td>
             </core:when>
             <core:otherwise> 
               <td><html:text property="due_date" size="9" onkeypress="return onlynumbers()" onblur="validfn(due_date.value,'Due Date')" onchange="ValidateForm()" value="<%=""+date %>" styleClass="formTextFieldWithoutTransparent"
               ></html:text></td>
           <td> <%if(emsg!=null){ %>
                <font style="font-style:normal;font-size:12px" color="red"><%=emsg %></font>
               <%} %></td>
             </core:otherwise>
             </core:choose> 
          </tr>
         
          <tr>
             <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.period(m)" ></bean:message></font></td>
             <core:choose>
             <core:when test="${requestScope.SIUpdate!=null}">
             <td><html:text property="period_mon" size="8" value="<%=""+siretriveObj.getPeriodMonths()%>" styleClass="formTextFieldWithoutTransparent"
             ></html:text></td>
             <td><font size="2"><font size="2" color="brown"><bean:message key="label.days"></bean:message></font>
             <html:text property="days" size="4" onkeypress="return onlynumbers()" value="<%=""+siretriveObj.getPeriodDays()%>" styleClass="formTextFieldWithoutTransparent"
             ></html:text></td>
             </core:when>
             <core:otherwise> 
             <td><html:text property="period_mon" onkeypress="return onlynumbers()"  size="8" onblur="validfn(period_mon.value,'Months')" styleClass="formTextFieldWithoutTransparent"
             ></html:text></td>
             <td><font size="2"><font size="2" color="brown"><bean:message key="label.days"></bean:message></font>
             <html:text property="days" size="4" onkeypress="return onlynumbers()"  onblur="validfn(days.value,'Days')" styleClass="formTextFieldWithoutTransparent"
             ></html:text></td>
             </core:otherwise>
             </core:choose> 
          </tr>
          <tr>
             <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.amount" ></bean:message></font></td>
             <core:choose>
             <core:when test="${requestScope.SIUpdate!=null}">
             <td><html:text property="amount" onkeypress="return onlynumbers()" size="8" value="<%=""+siretriveObj.getAmount()%>" styleClass="formTextFieldWithoutTransparent"
             ></html:text></td>
             </core:when>
             <core:otherwise> 
             <td><html:text property="amount" onkeypress="return onlynumbers()"  size="8" onblur="validfn(amount.value,'Amount')" styleClass="formTextFieldWithoutTransparent"
             ></html:text></td>
             </core:otherwise>
             </core:choose> 
         </tr>
         <tr>
            <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.no_of_times_to_exec" ></bean:message></font></td>
            <core:choose>
            <core:when test="${requestScope.SIUpdate!=null}">
           	<td><html:text property="no_of_times_exec" onkeypress="return onlynumbers()" size="8" value="<%=""+siretriveObj.getNoExec()%>" styleClass="formTextFieldWithoutTransparent"
           	></html:text></td>
           	</core:when>
            <core:otherwise> 
            <td><html:text property="no_of_times_exec" onkeypress="return onlynumbers()" size="8" onblur="validfn(no_of_times_exec.value,'No of times to execute')" styleClass="formTextFieldWithoutTransparent"
            ></html:text></td>
            </core:otherwise>
            </core:choose> 
         </tr>
         <tr>
            <td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.last_exec_date" ></bean:message></font></td>
            <core:choose>
            <core:when test="${requestScope.SIUpdate!=null}">
            <td><html:text property="last_exec_date" onkeypress="return onlynumbers()"  size="9" value="${requestScope.SIUpdate.lastExec}" styleClass="formTextFieldWithoutTransparent"
            ></html:text></td>
            </core:when>
            <core:otherwise> 
            <td><html:text property="last_exec_date" onkeypress="return onlynumbers()"  size="9" onblur="validfn(last_exec_date.value,'Last Execution Date')" onchange="ValidateForm()" styleClass="formTextFieldWithoutTransparent"
            ></html:text></td>
            </core:otherwise>
            </core:choose> 
         </tr>
         <tr>
           	<td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.expiry_days" ></bean:message></font></td>
           	<core:choose>
            <core:when test="${requestScope.SIUpdate!=null}">
           	<td><html:text property="expiry_days" onkeypress="return onlynumbers()" size="8" value="<%=""+siretriveObj.getExpiryDays()%>" styleClass="formTextFieldWithoutTransparent"
           	></html:text></td>
           	</core:when>
            <core:otherwise>
            <td><html:text property="expiry_days" onkeypress="return onlynumbers()" size="8" onblur="validfn(expiry_days.value,'Expiry Days')" styleClass="formTextFieldWithoutTransparent"
            ></html:text></td>
            </core:otherwise>
            </core:choose> 
         </tr>
         
         <%String str=(String)request.getAttribute("updatebutton");%>
         <%System.out.println("Hi i m here......................."); %>
         <tr>
            <html:hidden property="forward" value="error"></html:hidden>
              <%if(str!=null){ %>
              <%if(str.equalsIgnoreCase("UPDATE")){%>
              <%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
            <td><html:submit onclick="button_update('update')" styleClass="ButtonBackgroundImage">UPDATE</html:submit></td>
             <%}else{ %>
           <td><html:submit onclick="button_update('update')" styleClass="ButtonBackgroundImage">UPDATE</html:submit></td>
             <%} %>
              
              <%} %>
              <%}else {%>
              <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <td align="right"><html:button property="submit1"onclick="button_insert('submit');" styleClass="ButtonBackgroundImage">SUBMIT</html:button></td>
             <%}else{ %>
            <td align="right"><html:button disabled="true" property="submit1"onclick="button_insert('submit');" styleClass="ButtonBackgroundImage">SUBMIT</html:button></td>
             <%} %>
              
              <%} %>
              <td><html:button property="clear" onclick="callClear()" styleClass="ButtonBackgroundImage">CLEAR</html:button></td> 
              <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <td><html:submit onclick="button_verify('verify');" styleClass="ButtonBackgroundImage">VERIFY</html:submit></td>
             <%}else{ %>
             <td><html:submit onclick="button_verify('verify');" disabled="true" styleClass="ButtonBackgroundImage">VERIFY</html:submit></td>
             <%} %>
                 
        </tr>  
        </table>
    
    </td>
    <!--<td>	
      <table align="right">
		  <jsp:include page="/CommonWebPages/Personnel.jsp"></jsp:include>
       </table>
     </td>
 -->
 
 	
 </table>
 </html:form> 
 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
 
 </body>
</html>