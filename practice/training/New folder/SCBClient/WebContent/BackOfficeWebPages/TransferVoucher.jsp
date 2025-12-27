<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>

<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.backOffice.VoucherDataObject"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="masterObject.backOffice.VoucherDataObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>transfer voucher</title>

<link rel="stylesheet" href="../css/print.css" type="text/css"
	media="print" />
<link rel="stylesheet" href="Images/DMTStyle.css" type="text/css"
	media="print" />
<style type="text/css" media="all">
@import url("../css/alternative.css");

@import url("../css/maven-theme.css");

@import url("../css/site.css");

@import url("../css/screen.css");
</style>
<script type="text/javascript">
    	function glcodes(){
    
    	//var acn[]=document.getElementById('xy');
    	
    	
    	}
    	  	
    	</script>


<script type="text/javascript">
    	
var count=1;
var nrows=2;

function addRow()
{
var gltype=document.forms[0].gltype.value;


var glcode=document.forms[0].glcode.value;

var actype=document.forms[0].acctype.value;

var acno=document.forms[0].accno.value;
var cind=document.forms[0].trntype.value;

var amount=document.forms[0].amount.value;


var tbl = document.getElementById('addTable');
var lastRow = tbl.rows.length;
// if there's no header row in the table, then iteration = lastRow + 1
var iteration = lastRow;
var itr=iteration-1; 
var row = tbl.insertRow(lastRow);


// left cell
var cellLeft = row.insertCell(0);
var textNode = document.createTextNode(iteration);
cellLeft.appendChild(textNode);

// right cell

var cellRight = row.insertCell(1);
var e1 = document.createElement('input');
e1.setAttribute('type', 'checkbox');
e1.setAttribute('name', 'cbox');
e1.setAttribute('value', ' '+ itr);
cellRight.appendChild(e1);

var cellRight = row.insertCell(2);
var e2 = document.createElement('input');
e2.setAttribute('type', 'text');
e2.setAttribute('name', 'gltype');
e2.setAttribute('size', '5');
e2.setAttribute('value',gltype);
cellRight.appendChild(e2);



var cellRight = row.insertCell(3);
var e3 = document.createElement('input');
e3.setAttribute('type', 'text');
e3.setAttribute('name', 'glcode');
e3.setAttribute('size', '5');
e3.setAttribute('value',glcode);
cellRight.appendChild(e3);

var cellRight = row.insertCell(4);
var e4 = document.createElement('input');
e4.setAttribute('type', 'text');
e4.setAttribute('name', 'actype');
e4.setAttribute('size', '5');
e4.setAttribute('value',actype);
cellRight.appendChild(e4);

var cellRight = row.insertCell(5);
var e5 = document.createElement('input');
e5.setAttribute('type', 'text');
e5.setAttribute('name', 'acno');
e5.setAttribute('size', '4');
e5.setAttribute('value',acno);
cellRight.appendChild(e5);

var cellRight = row.insertCell(6);
var e6 = document.createElement('input');
e6.setAttribute('type', 'text');
e6.setAttribute('name', 'cind');
e6.setAttribute('size', '3');
e6.setAttribute('value',cind);
cellRight.appendChild(e6);

var cellRight = row.insertCell(7);
var e7 = document.createElement('input');
e7.setAttribute('type', 'text');
e7.setAttribute('name', 'amount');
e7.setAttribute('size', '4');
e7.setAttribute('value',amount);
cellRight.appendChild(e7);




};

</script>


<script type="text/javascript">

function onlynumbers()
{
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        };
   
    function set(target)
    {
    
    document.forms[0].forward.value=target;
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
    
    
    function callClear(target){
           document.forms[0].forward.value=target;
            document.forms[0].narration.value="";
         	 var ele= document.forms[0].elements;
         	
             for(var i=0;i<ele.length;i++){
               if(ele[i].type=="text"){
                  
               ele[i].value="";
               }
               if(ele[i].type=="hidden"){
                  
               ele[i].value="";
               }
           
           }
            };
            
     function button_delete(target)
        {
      
       document.forms[0].forward.value=target;
     
      var deleteval=confirm("Are you sure you want to delete voucher no ???");
 		if(deleteval==true)
 		{
 		 
 			
 			document.forms[0].submit();
 			return true;
		}
 		else 
 		{
 			return false;
 		}
      
        };
    
       function button_update(target)
        {
      
       document.forms[0].forward.value=target;
       alert("Updated Successfully");
        };
        
      
 	 	
            
            
            function instruction()
              {
         
          	
          	
          
          	};
   function numbersonly_date(eve){
         var cha = event.keyCode

            if (  ( cha  >= 47 && cha < 58  ) ) {
              
                return true ;
                            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
         
        
      };        	
          	
            function addRows()
            {
              if(document.forms[0].acctype.value=="Select" || document.forms[0].accno.value=="0"||document.forms[0].glcode.value=="Select"||document.forms[0].t_type.value=="Select"
              ||document.forms[0].cd_ind.value=="Select" ||document.forms[0].amount.value=="0.00")
            {
              alert("Please Select Details Correctly")
            }
            else{
           
             document.forms[0].check_acctype1.value=document.forms[0].acctype.value
              
             document.forms[0].check_accno1.value=document.forms[0].accno.value
              
             document.forms[0].check_gltype1.value=document.forms[0].gltype.value
               
             document.forms[0].check_glcode1.value=document.forms[0].glcode.value
               
             document.forms[0].trn_tpe1.value=document.forms[0].t_type.value
               
             document.forms[0].cdind1.value=document.forms[0].cd_ind.value
               
             document.forms[0].check_amt1.value=document.forms[0].amount.value
         
          
            
            if(document.forms[0].cdind1.value=="C")
            {
             
               var cred=0.0;
               var cred1=0.0;
               cred=document.forms[0].creditamt.value;
               cred1=document.forms[0].amount.value;
               
                document.forms[0].creditamt.value=parseInt(cred)+parseInt(cred1)
                
            }
                
            if(document.forms[0].cdind1.value=="D")
            {
              
               var deb=0.0;
               var deb1=0.0;
               deb=document.forms[0].debitamt.value;
                deb1=document.forms[0].amount.value;
                
                document.forms[0].debitamt.value= parseInt(deb)+parseInt(deb1)
            }
            }
            
            }
              function addRows1()
            {
                if(document.forms[0].acctype.value=="Select" || document.forms[0].accno.value=="0"||document.forms[0].glcode.value=="Select"||document.forms[0].t_type.value=="Select"
              ||document.forms[0].cd_ind.value=="Select" ||document.forms[0].amount.value=="0.00")
            {
              alert("Please Select Details Correctly")
            }
            else{
             document.forms[0].check_acctype2.value=document.forms[0].acctype.value
              
             document.forms[0].check_accno2.value=document.forms[0].accno.value
              
             document.forms[0].check_gltype2.value=document.forms[0].gltype.value
               
             document.forms[0].check_glcode2.value=document.forms[0].glcode.value
               
             document.forms[0].trn_tpe2.value=document.forms[0].t_type.value
               
             document.forms[0].cdind2.value=document.forms[0].cd_ind.value
               
             document.forms[0].check_amt2.value=document.forms[0].amount.value
             
               if(document.forms[0].cdind2.value=="C")
            {
               
               var cred2=document.forms[0].creditamt.value;
               var cred3=document.forms[0].amount.value;
                
                document.forms[0].creditamt.value=parseInt(cred2)+parseInt(cred3)
                
            }
                
            if(document.forms[0].cdind2.value=="D")
            {
             
               var deb2=document.forms[0].debitamt.value;
               var deb3=document.forms[0].amount.value;
               
                document.forms[0].debitamt.value= parseInt(deb2)+parseInt(deb3)
            }
            
         }
            
            }
              function addRows2()
            {
             if(document.forms[0].acctype.value=="Select" || document.forms[0].accno.value=="0"||document.forms[0].glcode.value=="Select"||document.forms[0].t_type.value=="Select"
              ||document.forms[0].cd_ind.value=="Select" ||document.forms[0].amount.value=="0.00")
            {
              alert("Please Select Details Correctly")
            }
            else{
            
             document.forms[0].check_acctype3.value=document.forms[0].acctype.value
              
             document.forms[0].check_accno3.value=document.forms[0].accno.value
              
             document.forms[0].check_gltype3.value=document.forms[0].gltype.value
               
             document.forms[0].check_glcode3.value=document.forms[0].glcode.value
               
             document.forms[0].trn_tpe3.value=document.forms[0].t_type.value
               
             document.forms[0].cdind3.value=document.forms[0].cd_ind.value
               
             document.forms[0].check_amt3.value=document.forms[0].amount.value
             
                if(document.forms[0].cdind3.value=="C")
            {
               
               var cred4=document.forms[0].creditamt.value;
               var cred5=document.forms[0].amount.value;
              
                document.forms[0].creditamt.value=parseInt(cred4)+parseInt(cred5)
                
            }
                
            if(document.forms[0].cdind3.value=="D")
            {
              
               var deb4=document.forms[0].debitamt.value;
               var deb5=document.forms[0].amount.value;
               
                document.forms[0].debitamt.value= parseInt(deb4)+parseInt(deb5)
            }
            
         }
            
            }
              function addRows3()
            {
             if(document.forms[0].acctype.value=="Select" || document.forms[0].accno.value=="0"||document.forms[0].glcode.value=="Select"||document.forms[0].t_type.value=="Select"
              ||document.forms[0].cd_ind.value=="Select" ||document.forms[0].amount.value=="0.00")
            {
              alert("Please Select Details Correctly")
            }
            else{
             document.forms[0].check_acctype4.value=document.forms[0].acctype.value
              
             document.forms[0].check_accno4.value=document.forms[0].accno.value
              
             document.forms[0].check_gltype4.value=document.forms[0].gltype.value
               
             document.forms[0].check_glcode4.value=document.forms[0].glcode.value
               
             document.forms[0].trn_tpe4.value=document.forms[0].t_type.value
               
             document.forms[0].cdind4.value=document.forms[0].cd_ind.value
               
             document.forms[0].check_amt4.value=document.forms[0].amount.value
             
             
                if(document.forms[0].cdind4.value=="C")
            {
              
               var cred6=document.forms[0].creditamt.value;
               var cred7=document.forms[0].amount.value;
              
                document.forms[0].creditamt.value=parseInt(cred6)+parseInt(cred7)
                
            }
                
            if(document.forms[0].cdind4.value=="D")
            {
              
               var deb6=document.forms[0].debitamt.value;
               var deb7=document.forms[0].amount.value;
                document.forms[0].debitamt.value= parseInt(deb6)+parseInt(deb7)
            }
            
         }
            
            }
              function addRows4()
            {
             if(document.forms[0].acctype.value=="Select" || document.forms[0].accno.value=="0"||document.forms[0].glcode.value=="Select"||document.forms[0].t_type.value=="Select"
              ||document.forms[0].cd_ind.value=="Select" ||document.forms[0].amount.value=="0.00")
            {
              alert("Please Select Details Correctly")
            }
            else{
            
             document.forms[0].check_acctype5.value=document.forms[0].acctype.value
              
             document.forms[0].check_accno5.value=document.forms[0].accno.value
              
             document.forms[0].check_gltype5.value=document.forms[0].gltype.value
               
             document.forms[0].check_glcode5.value=document.forms[0].glcode.value
               
             document.forms[0].trn_tpe5.value=document.forms[0].t_type.value
               
             document.forms[0].cdind5.value=document.forms[0].cd_ind.value
               
             document.forms[0].check_amt5.value=document.forms[0].amount.value
             
              if(document.forms[0].cdind5.value=="C")
            {
               
               var cred8=document.forms[0].creditamt.value;
               var cred9=document.forms[0].amount.value;
                
                document.forms[0].creditamt.value=parseInt(cred8)+parseInt(cred9)
                
            }
                
            if(document.forms[0].cdind5.value=="D")
            {
               
               var deb8=document.forms[0].debitamt.value;
               var deb9=document.forms[0].amount.value;
               
                document.forms[0].debitamt.value= parseInt(deb8)+parseInt(deb9)
            }
            
         }
         
            
            }
            
        
          function button_insert(target)
          {
         
          
          if(document.forms[0].creditamt.value!=document.forms[0].debitamt.value)
          {
            alert("Debit And Credit Amount Don't Match")
            return false;
          }
           if(document.forms[0].narration.value==""||document.forms[0].accno.value=="0")
          {
            alert("Please fill all the details")
          }
           
         else 
            {
          
          	document.forms[0].forward.value=target;
            var entryval=confirm("Confirm Entry????");
            if(entryval==true)
            	{
           		 	document.getElementById("vch").value="true";
           			document.forms[0].submit();
           			 return true;
           			 
           			
         		}
          	else 
          		{
          			document.getElementById("vch").value="false";
          				return false;
          		}
          
             }          
              
         };     
         function button_verify(target)
        {
           
           document.forms[0].forward.value=target;
          
        }; 
       
       
        function setFlag(flagValue)
        {
        document.forms[0].flag.value=flagValue;
        document.forms[0].submit();
        	if(document.getElementById("accexist").value=="invalidacct")
 			{
 	 				alert("Invalid account no");
 	 				return false;
 	 			}
        }        
    </script>


</head>
<%
	Vector vec_glcode = (Vector) request.getAttribute("glcode");
	//System.out.println("GLCODE****"+vec_glcode);
	String errMsg = (String) request.getAttribute("errmsg");
	System.out.println("err ms in jsp====____________" + errMsg);
	String gldesc = (String) request.getAttribute("gldesc");
	//System.out.println("GLDESC****"+gldesc);
	ModuleObject[] gltype = (ModuleObject[]) request.getAttribute("gltype");
	ModuleObject[] acctype = (ModuleObject[]) request.getAttribute("acctype");
	Vector vec_transacctypes = (Vector) request.getAttribute("trntype");
	String[] cdind = (String[]) request.getAttribute("cdind");
	String vchdate = (String) request.getAttribute("vchdate");
	String msg = (String) request.getAttribute("msg");
	String displaymsg=(String)request.getAttribute("displaymsg");
	String error = (String) request.getAttribute("error");
	String vcherr = (String) request.getAttribute("vcherror");
	String[][] glnmcd = (String[][]) request.getAttribute("glnmcd");
	String code[] = (String[]) request.getAttribute("code");
	String trn_typ[] = (String[]) request.getAttribute("trn_typ");
	String cd_ind = (String) request.getAttribute("cd_ind");
	String ac_holder=(String)request.getAttribute("ac_holder");

	VoucherDataObject[] vchdataobject = (VoucherDataObject[]) request
			.getAttribute("vchdataobject");

	System.out.println("Voucher in jsp=====" + vchdataobject);
%>
<%!String date;%>
<%
	date = (String) request.getAttribute("date");
	System.out.println("Date------>");
%>
<body class="Mainbody" style="overflow: scroll;" onload="instruction()">
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
<center>
<h2 class="h2">Transfer Voucher</h2>
</center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/BackOffice/TransferVoucher?pageId=11018">
	<html:hidden property="flag" />
	<html:hidden property="accexist" styleId="accext" />
	<br>
	<br>

	<%
	if (msg != null) {
	%>
	<font color="red"><%=msg%></font>
	<%
	}
	%>
	<br>
	<br>
	<%
	if (error != null) {
	%>
	<font color="red"><%=error%></font>
	<%
	}
	%>
	<br>
	<br>
	<%
	if (vcherr != null) {
	%>
	<font color="red"><%=vcherr%></font>
	<%
	}
	%>
	<br>
	<br>
	<%
	if (displaymsg!= null) {
	%>
	<font style="font-style:normal;font-size:12px" color="red" ><%=displaymsg%></font>
	<%
	}
	%>





	<table align="left">
		<tr>


			<table style="border: thin solid #339999;">
				<thead>
					<center><b>Narration Entry</b></center>
				</thead>
				<tr>
					<td align="right"><font style="font-style:normal; font-size: 12px" color="brown"><bean:message key="label.vchnumber"></bean:message></font></td>
					<td><html:text property="vchnum" size="8" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" onblur="submit()"></html:text></td>
				</tr>

				<tr>
					<td align="right"><font style="font-style: normal; font-size: 12px" color="brown"><bean:message key="label.date"></bean:message></font></td>
					<%
					if (vchdate != null) {
					%>
					<td><html:text property="date" size="9" readonly="true" onblur="return datevalidation(this)" onkeypress="return numbersonly_date(this)" styleClass="formTextFieldWithoutTransparent" value="<%=""+vchdate %>"></html:text></td>
					<%
					} else {
					%>
					<td><html:text property="date" size="9"
						styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly_date(this)" readonly="true" value="<%=""+date %>"></html:text></td>
					<%
					}
					%>
				</tr>

				<tr>
					<td align="right"><font
						style="font-style: normal; font-size: 12px" color="brown"><bean:message
						key="label.narr"></bean:message></font></td>
					<td><html:textarea property="narration" cols="10" rows="3"></html:textarea></td>
				</tr>
			</table>
		</tr>
		
		<tr>
			<table class="txtTable">
				<%
				System.out.println("Sow======>");
				%>

				<tr>
					<table border="1" width="25%">
						<tr>
							<td>Click</td>
							<td width="5">GLType</td>
							<td>GLCode</td>
							<td>AccType</td>
							<td>AccNo</td>
							<td>TrnType</td>
							<td>CDInd</td>
							<td>Amount</td>
						<tr>
							<td><html:checkbox property="cbox" styleClass="formTextFieldWithoutTransparent" value="1"></html:checkbox></td>
							<td><html:text property="check_gltype1" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_glcode1" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_acctype1" readonly="true" size="5"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_accno1" readonly="true" size="5"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="trn_tpe1" readonly="true" size="5"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="cdind1" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_amt1" readonly="true" size="5"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:button property="addbutton1"
								styleClass="ButtonBackgroundImage" styleId="button"
								value="AddRow" onclick="addRows()"></html:button></td>
						</tr>

						<tr>
							<td><html:checkbox property="cbox"
								styleClass="formTextFieldWithoutTransparent" value="2"></html:checkbox></td>
							<td><html:text property="check_gltype2" readonly="true" size="5"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_glcode2" readonly="true" size="5"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_acctype2" readonly="true" size="5"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_accno2" readonly="true"  size="5"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="trn_tpe2" readonly="true" size="5"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="cdind2" readonly="true" size="5"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_amt2" size="5" readonly="true" 
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:button property="addbutton2"
								styleClass="ButtonBackgroundImage" styleId="button"
								value="AddRow" onclick="addRows1()"></html:button></td>
						</tr>

						<tr>
							<td><html:checkbox property="cbox"
								styleClass="formTextFieldWithoutTransparent" value="3"></html:checkbox></td>
							<td><html:text property="check_gltype3" size="5" readonly="true" 
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_glcode3" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_acctype3" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_accno3" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="trn_tpe3" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="cdind3" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_amt3" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:button property="addbutton3"
								styleClass="ButtonBackgroundImage" styleId="button"
								value="AddRow" onclick="addRows2()"></html:button></td>
						</tr>

						<tr>
							<td><html:checkbox property="cbox"
								styleClass="formTextFieldWithoutTransparent" value="4"></html:checkbox></td>
							<td><html:text property="check_gltype4" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_glcode4" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_acctype4" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_accno4" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="trn_tpe4" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="cdind4" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_amt4" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:button property="addbutton4"
								styleClass="ButtonBackgroundImage" styleId="button"
								value="AddRow" onclick="addRows3()"></html:button></td>
						</tr>

						<tr>
							<td><html:checkbox property="cbox"
								styleClass="formTextFieldWithoutTransparent" value="5"></html:checkbox></td>
							<td><html:text property="check_gltype5" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_glcode5" size="5" readonly="true" 
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_acctype5" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_accno5" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="trn_tpe5" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="cdind5" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:text property="check_amt5" size="5" readonly="true"
								styleClass="formTextFieldWithoutTransparent"></html:text></td>
							<td><html:button property="addbutton5"
								styleClass="ButtonBackgroundImage" styleId="button"
								value="AddRow" onclick="addRows4()"></html:button></td>
						</tr>

					</table>
				</tr>
			
				<table>
					<tr>
						<td><font style="font-style: normal; font-size: 12px"
							color="brown"><bean:message key="label.creditamt"></bean:message></font><html:text
							property="creditamt" onkeypress=" return onlynumbers()" disabled="true" size="8"
							styleClass="formTextFieldWithoutTransparent"></html:text></td>
						<td><font style="font-style: normal; font-size: 12px"
							color="brown"><bean:message key="label.debitamt"></bean:message></font><html:text
							property="debitamt" disabled="true" onkeypress=" return onlynumbers()" size="8"
							styleClass="formTextFieldWithoutTransparent"></html:text></td>
					</tr>

				</table>
				<table border="1" id="addTable" width="25%">
					<tr>
					</tr>
				</table>
				<tr>

					<div>
					<%
							if (vchdataobject != null) {

							System.out.println("cashobject====="
							+ vchdataobject[0].getGlType());
							System.out.println("cashobject====="
							+ vchdataobject[0].getGlCode());
							System.out.println("cashobject====="
							+ vchdataobject[0].getTransactionAmount());
					%>
					<table border=1 width="25%">
						<th>Click</th>
						<th>GLType</th>
						<th>GLCode</th>
						<th>AccType</th>
						<th>AccNo</th>
						<th>CdInd</th>
						<th>TrnType</th>
						<th>Amount</th>
					</table>
					<table border=1 width="25%">
						<%
						for (int i = 0; i < vchdataobject.length; i++) {
						%>
						<tr>

							<td><input type="checkbox" name="cbox"></td>
							<td><input type="text" disabled="true" size="5" value="<%=""+vchdataobject[i].getGlType()%>"></td>
							<td><input type="text" disabled="true" size="5" value="<%=""+vchdataobject[i].getGlCode()%>"></td>
							<td><input type="text" disabled="true" size="5" value="<%=""+vchdataobject[i].getModuleAccountType()%>"></td>
							<td><input type="text" disabled="true" size="5" value="<%=""+vchdataobject[i].getModuleAccountNo()%>"></td>
							<td><input type="text" disabled="true" size="5" value="<%=""+vchdataobject[i].getCdIndicator()%>"></td>
							<td><input type="text" disabled="true" size="5" value="<%=""+vchdataobject[i].getTransactionType()%>"></td>
							<td><input type="text" disabled="true" size="5" value="<%=""+vchdataobject[i].getTransactionAmount()%>"></td>
						</tr>
						<%
						}
						%>
					</table>
					<%
					}
					%>
					</div>

					<table style="border: thin solid #339999;" align="left">
						<thead>
							<center><b>Transaction Entry</b></center>
						</thead>


						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr>
							<td>
							<center>
							<table>
								<tr>
									<td align="right"><font
										style="font-style: normal; font-size: 12px" color="brown"><bean:message
										key="label.acType"></bean:message></font></td>


									<td>
									<%acctype = (ModuleObject[]) request.getAttribute("acctype");%>
									 <html:select property="acctype">
									 <html:option value="Select"></html:option>
										<%if (acctype != null) {%>
										<%for (int j = 0; j < acctype.length; j++) {%>
										<html:option value="<%=acctype[j].getModuleCode()%>"><%=acctype[j].getModuleAbbrv()%></html:option>
										<%}}
										%>
									</html:select></td>
									<td align="right"><font
										style="font-style: normal; font-size: 12px" color="brown"><bean:message
										key="label.acNum"></bean:message></font></td>
									<td><html:text property="accno" size="6" onkeypress=" return onlynumbers()" onblur="submit()">
									</html:text> 
									<%if (errMsg != null) {%> <font color="red"><%=errMsg%> </font> <%}%>
									</td>
									
								</tr>
								<tr>
								</tr>
								<tr></tr>
								
							
								<tr>
								<td align="right"><font	style="font-style: normal; font-size: 12px" color="brown"><bean:message
										key="label.ac_holder_name"></bean:message></font></td>
										<%if(ac_holder!=null){ %>
							     <td><html:text property="ac_holder"  value="<%=""+ac_holder %>" size="28" style="font-family:bold;color:red" readonly="true"></html:text></td>
							     <%}else{ %> 
							     <td><html:text property="ac_holder" size="28" ></html:text></td>
							     		<%} %>									
						         </tr>
						         <tr>
						         </tr>
						        <tr>
									<td align="right"><font
										style="font-style: normal; font-size: 12px" color="brown"><bean:message
										key="label.gltype"></bean:message></font></td>
									<td><html:select property="gltype" onblur="submit()">
										<%
										if (gltype != null) {
										%>
										<%
										for (int j = 0; j < gltype.length; j++) {
										%>
										<html:option value="<%=gltype[j].getModuleCode()%>"><%=gltype[j].getModuleAbbrv()%></html:option>
										<%
											}
											}
										%>
									</html:select></td>

									<td align="right"><font style="font-style: normal; font-size: 12px" color="brown"><bean:message key="label.glcode"></bean:message></font></td>
									<td><html:select property="glcode" onblur="submit()">
										<%if (code != null) {%>
										<%
										for (int i = 0; i < code.length; i++) {
										%>

										<html:option value="<%=""+code[i]%>"><%="" + code[i]%></html:option>
										<%
											}
											} else {
										%>
										<html:option value="Select">Select</html:option>
										<%
										}
										%>
									</html:select></td>
								</tr>

								<tr>

									<td align="right"><font
										style="font-style: normal; font-size: 12px" color="brown"><bean:message
										key="label.trntype"></bean:message></font></td>
									<td><html:select property="t_type" onblur="submit()">
										<%
										if (trn_typ != null) {
										%>
										<%
										for (int i = 0; i < trn_typ.length; i++) {
										%>

										<html:option value="<%=""+trn_typ[i]%>"><%="" + trn_typ[i]%></html:option>
										<%
											}
											} else {
										%>
										<html:option value="Select">Select</html:option>
										<%
										}
										%>
									</html:select></td>

									<td align="right"><font
										style="font-style: normal; font-size: 12px" color="brown"><bean:message
										key="label.cdind"></bean:message></font></td>

									<td><html:select property="cd_ind">
										<%
										if (cd_ind != null) {
										%>
										<%
										for (int i = 0; i < code.length; i++) {
										%>

										<html:option value="<%=""+cd_ind%>"><%="" + cd_ind%></html:option>
										<%
											}
											} else {
										%>
										<html:option value="Select">Select</html:option>
										<%
										}
										%>
									</html:select></td>
								</tr>


								<tr>
									<td align="right"><font
										style="font-style: normal; font-size: 12px" color="brown"><bean:message
										key="label.amt"></bean:message></font></td>
									<td><html:text property="amount" size="6" value="0.00" onkeypress="return onlynumbers()"></html:text>
									</td>
								</tr>


							</table>

							</center>
							</td>
						</tr>




						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr>
							<html:hidden property="forward" value="error"></html:hidden>
							<html:hidden property="vouchervalue" styleId="vch" />
							<html:hidden property="voucherno" styleId="vchno" />
							<html:hidden property="lk_amount" styleId="lk_amt" />
							<html:hidden property="accountobject" styleId="accobject" />
							<html:hidden property="accexist" styleId="accext" />
							<html:hidden property="miniamt" styleId="miniamt" />
							<html:hidden property="scrverify" styleId="scrverify" />
							<html:hidden property="newscrverify" styleId="newverify" />
							<html:hidden property="closed" styleId="closed" />
							<html:hidden property="sysdate" />
							

							<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
           						<td><input type="submit" value="Delete" name="method" class="ButtonBackgroundImage" onclick="button_delete('delete')" />
             				<%}else{ %>
            				<td><input type="submit" value="Delete" disabled="disabled" name="method" class="ButtonBackgroundImage" onclick="button_delete('delete')" />
             				<%} %>
							 
							<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            				<html:button onclick="return button_insert('submit')" property="subButton" styleClass="ButtonBackgroundImage"> <bean:message key="label.submit"></bean:message></html:button>
             				<%}else{ %>
             				<html:button onclick="return button_insert('submit')" disabled="true" property="subButton" styleClass="ButtonBackgroundImage"> <bean:message key="label.submit"></bean:message></html:button>
            				 <%} %>
						 	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            				<html:submit onclick="button_verify('verify')" value="verify" styleClass="ButtonBackgroundImage"/>
								<!--<bean:message key="label.verify"></bean:message>
             				--><%}else{ %>
            				<html:submit onclick="button_verify('verify')" value="verify" disabled="true" styleClass="ButtonBackgroundImage"/>
								<!--<bean:message key="label.verify"></bean:message>
            				 --><%} %>
							<html:button property="clear" value="Clear" styleClass="ButtonBackgroundImage" onclick="callClear()"></html:button>
							
							</td>
						</tr>
						<tr>
						

						</tr>
					</table>
				</tr>
				<tr></tr>

			</table>




		</tr>
	</table>
</html:form>

</body>
<form name="myform">
<body>
<table border="1">



</table>
</body>
</form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</html>
