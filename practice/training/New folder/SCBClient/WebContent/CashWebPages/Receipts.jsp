<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="masterObject.general.ModuleObject"%>

<%@page import="masterObject.general.AccSubCategoryObject"%>

<%@page import="masterObject.share.ShareCategoryObject"%>

<%@page import="masterObject.cashier.CashObject"%>
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"><!--
function onlyDoublenumbers()
	{
        	var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || cha == 46 ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
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
 function onlyalpha()
	{
 		var cha =event.keyCode;
		if ( (cha >= 97 && cha <= 122 ) ) 
		{
			return true;
            		
        	} else {
        		
        	return false;
        	}
	};	
	
	function details()
    {
      alert("Details");
    };
	function clearfun1()
	{
	  document.forms[0].amount.value=="";
	}
	
    function HideShow(AttributetoHide){
    		if(document.forms[0].clearatstart.value=="clear"){
    		clearfun();
    		document.forms[0].clearatstart.value="";
    		}
    		document.getElementById('Name'+AttributetoHide).style.display='block'; 
			document.getElementById('Cust'+AttributetoHide).style.display='none';
        	document.getElementById('Cactype'+AttributetoHide).style.display='none';
        	document.getElementById('Cacc'+AttributetoHide).style.display='none';
        	document.getElementById('Desc'+AttributetoHide).style.display='none';
        	document.getElementById('Purc'+AttributetoHide).style.display='none';
        	document.getElementById('Fav'+AttributetoHide).style.display='none';
        	document.getElementById('Poamt'+AttributetoHide).style.display='none';
        	document.getElementById('Comm'+AttributetoHide).style.display='none';
        	document.getElementById('Tot'+AttributetoHide).style.display='none';
        	document.getElementById('Shcat'+AttributetoHide).style.display='block';
        	document.getElementById('Lock'+AttributetoHide).style.display='none';
        	document.getElementById('Amt'+AttributetoHide).style.display='block'; 
        		
       if((document.getElementById("combo").value == 'PL')||(document.getElementById("combo").value == 'SL')||(document.getElementById("combo").value == 'PAG')||(document.getElementById("combo").value == 'CC')
       ||(document.getElementById("combo").value == 'OD')||(document.getElementById("combo").value == 'CA Bk')||(document.getElementById("combo").value == 'CA Br')||(document.getElementById("combo").value == 'BD')||(document.getElementById("combo").value == 'CD')||(document.getElementById("combo").value == 'VD')||(document.getElementById("combo").value == 'CA')
      	||(document.getElementById("combo").value == 'LL')||(document.getElementById("combo").value == 'DL')||(document.getElementById("combo").value == 'ML')||(document.getElementById("combo").value == 'TL')||(document.getElementById("combo").value == 'AL'))
      	{
        	document.getElementById('Name'+AttributetoHide).style.display='block'; 
			document.getElementById('Cust'+AttributetoHide).style.display='none';
        	document.getElementById('Cactype'+AttributetoHide).style.display='none';
        	document.getElementById('Cacc'+AttributetoHide).style.display='none';
        	document.getElementById('Desc'+AttributetoHide).style.display='none';
        	document.getElementById('Purc'+AttributetoHide).style.display='none';
        	document.getElementById('Fav'+AttributetoHide).style.display='none';
        	document.getElementById('Poamt'+AttributetoHide).style.display='none';
        	document.getElementById('Comm'+AttributetoHide).style.display='none';
        	document.getElementById('Tot'+AttributetoHide).style.display='none';
        	document.getElementById('Shcat'+AttributetoHide).style.display='none';
        	document.getElementById('Lock'+AttributetoHide).style.display='none';
        	document.getElementById('Amt'+AttributetoHide).style.display='block';
        }
        
        else if(document.getElementById("combo").value == 'PO') 
        { 
          
            document.getElementById('Name'+AttributetoHide).style.display='none'; 
            document.getElementById('Amt'+AttributetoHide).style.display='none';
            document.getElementById('Cust'+AttributetoHide).style.display='block';
        	document.getElementById('Cactype'+AttributetoHide).style.display='block';
        	document.getElementById('Cacc'+AttributetoHide).style.display='block';
        	document.getElementById('Desc'+AttributetoHide).style.display='block';
        	document.getElementById('Purc'+AttributetoHide).style.display='block';
        	document.getElementById('Fav'+AttributetoHide).style.display='block';
        	document.getElementById('Poamt'+AttributetoHide).style.display='block';
        	document.getElementById('Comm'+AttributetoHide).style.display='block';
        	document.getElementById('Tot'+AttributetoHide).style.display='block';
        	document.getElementById('Shcat'+AttributetoHide).style.display='none';
        	document.getElementById('Lock'+AttributetoHide).style.display='none';
           
           
        } 
      	
      	
      	else if((document.getElementById("combo").value == 'EL')||(document.getElementById("combo").value == 'GL')||(document.getElementById("combo").value == 'KL')||(document.getElementById("combo").value == 'JL')
			||(document.getElementById("combo").value == 'NL')||(document.getElementById("combo").value == 'HL')||(document.getElementById("combo").value == 'IL')||(document.getElementById("combo").value == 'OL'))

       	{ 
 			document.getElementById('Name'+AttributetoHide).style.display='block'; 
			document.getElementById('Cust'+AttributetoHide).style.display='none';
        	document.getElementById('Cactype'+AttributetoHide).style.display='none';
        	document.getElementById('Cacc'+AttributetoHide).style.display='none';
        	document.getElementById('Desc'+AttributetoHide).style.display='none';
        	document.getElementById('Purc'+AttributetoHide).style.display='none';
        	document.getElementById('Fav'+AttributetoHide).style.display='none';
        	document.getElementById('Poamt'+AttributetoHide).style.display='none';
        	document.getElementById('Comm'+AttributetoHide).style.display='none';
        	document.getElementById('Tot'+AttributetoHide).style.display='none';
        	document.getElementById('Shcat'+AttributetoHide).style.display='none';
        	document.getElementById('Lock'+AttributetoHide).style.display='none';
        	document.getElementById('Amt'+AttributetoHide).style.display='block';
         }
         	
         else if(document.getElementById("combo").value == 'LK') 
         { 
            document.getElementById('Name'+AttributetoHide).style.display='block'; 
			document.getElementById('Cust'+AttributetoHide).style.display='none';
        	document.getElementById('Cactype'+AttributetoHide).style.display='none';
        	document.getElementById('Cacc'+AttributetoHide).style.display='none';
        	document.getElementById('Desc'+AttributetoHide).style.display='none';
        	document.getElementById('Purc'+AttributetoHide).style.display='none';
        	document.getElementById('Fav'+AttributetoHide).style.display='none';
        	document.getElementById('Poamt'+AttributetoHide).style.display='none';
        	document.getElementById('Comm'+AttributetoHide).style.display='none';
        	document.getElementById('Tot'+AttributetoHide).style.display='none';
        	document.getElementById('Shcat'+AttributetoHide).style.display='none';
        	document.getElementById('Lock'+AttributetoHide).style.display='block';
        	document.getElementById('Amt'+AttributetoHide).style.display='block';
        }  
        
        else if(document.getElementById("combo").value == 'SH') 
        { 
           	document.getElementById('Name'+AttributetoHide).style.display='block'; 
			document.getElementById('Cust'+AttributetoHide).style.display='none';
        	document.getElementById('Cactype'+AttributetoHide).style.display='none';
        	document.getElementById('Cacc'+AttributetoHide).style.display='none';
        	document.getElementById('Desc'+AttributetoHide).style.display='none';
        	document.getElementById('Purc'+AttributetoHide).style.display='none';
        	document.getElementById('Fav'+AttributetoHide).style.display='none';
        	document.getElementById('Poamt'+AttributetoHide).style.display='none';
        	document.getElementById('Comm'+AttributetoHide).style.display='none';
        	document.getElementById('Tot'+AttributetoHide).style.display='none';
        	document.getElementById('Shcat'+AttributetoHide).style.display='block';
        	document.getElementById('Lock'+AttributetoHide).style.display='none';
        	document.getElementById('Amt'+AttributetoHide).style.display='block';
        }           
      
      
         
        if(document.getElementById("closed").value!=null)
		{
			if(document.getElementById("closed").value=="dayclose")
 				{
 					alert("Day closed\n You can't do any Transactions");
 					
 					return false;
 				
 				}
 			if(document.getElementById("closed").value=="cashclose")
 				{
 				    var closed = document.getElementById("closed").value;
 				    alert(closed);
 	 				alert("Cash closed You can't do any Transactions");
 	 				<%System.out.println("-----------cash is closed----");
 	 				
 	 				
 	 				%>
 	 				
 	 				
 	 				return false;
 	 				
 	 			}
 	 		if(document.getElementById("closed").value=="noentry")
 	 			{
 	  				alert("Error : no entry in Daily status for today");
 	  				return false;
 	  			
 	  			}
 	 		if(document.getElementById("closed").value=="cashopen")
 	 			{
 	 				alert("Cash Transactions");
 	 				return false;
 	 			
 	 			}
 	 		if(document.getElementById("closed").value=="payorderamt")
 	 			{
 	 				alert("Cash Transactions");
 	 				return false;
 	 			
 	 			}
 	 		if(document.getElementById("closed").value=="errordb")
 	 			{
 	 				alert("Error : Check DB Entries");
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="invalid")
 	 			{
 	 				alert("Invalid Scroll No");
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="error")
 	 			{
 	 				alert("Error :");
 	 				return false;
 	 			}
 	 		if(document.getElementById("scrverify").value=="alverify")
 	 			{
 	 				alert("Scroll No Already Verified");
 	 				clearfun();
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="cannotverify")
 	 			{
 	 				alert("You cannot verify this Scroll No");
 	 				return false;
 	 			}
 	 		
 	 		if(document.getElementById("closed").value=="poerror")
 	 			{
 	 				alert("PO Error: Verification failed");
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="errorverify")
 	 			{
 	 				alert("Error: Verification failed");
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="txt_name")
 	 			{
 	 				alert("txt_name is not editable");
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="txt_nameedit")
 	 			{
 	 				alert("txt_name is editable");
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="installment")
 	 			{
 	 				alert("Your installment amount is more\n you are paying less than that");
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="paidamt")
 	 			{
 	 				alert("Paid amt is more than amount to pay");
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="paidamt")
 	 			{
 	 				alert("Paid amt is more than amount to pay");
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="multipleof")
 	 			{
 	 				alert("Amount is not multiple of installment amount");
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="entervalid")
 	 			{
 	 				alert("Enter Valid Amount");
 	 				return false;
 	 			}
 	 		if(document.getElementById("closed").value=="invalidacct")
 	 			{
 	 				alert("Invalid account no");
 	 				return false;
 	 			}
 	 	
		}        
        if(document.getElementById("accext").value!=null)
       	{
       		if(document.getElementById("accext").value=="accexist")
		 	{
 				alert("given Account Number Not Found");
 				return false;
 			}
 	  	}
 	  	
 	  	if(document.getElementById("accobject").value!=null)
 	  	{
 	  		if(document.getElementById("accobject").value=="accountobject")
 	  		{
 	  			alert("Given account is Closed");
 	  			return false;
 	  		}
 	  		if(document.getElementById("accobject").value=="accountobject1")
 	  		{
 	  			alert("InOperative Account");
 	  			return false;
 	  		}
 	  		if(document.getElementById("accobject").value=="accountobject2")
 	  		{
 	  			alert("Given account is not yet Verified");
 	  			return false;
 	  		}
 	  		if(document.getElementById("accobject").value=="accountobject3")
 	  		{
 	  			alert("Default account");
 	  			return false;
 	  		}
 	  		if(document.getElementById("accobject").value=="accountobject4")
 	  		{
 	  			alert("Freezed account");
 	  			return false;
 	  		}
 	  	}
      if(document.getElementById("scrverify").value!=null)
       	{
       		if(document.getElementById("scrverify").value=="scrverify")
		 	{
 				alert("Share A/c Scroll No .. U Cant Verify");
 				return false;
 			}
 			if(document.getElementById("scrverify").value=="lkscrverify")
		 	{
 				alert("Locker A/c Scroll No .. U Cant Verify");
 				return false;
 			}
 			if(document.getElementById("scrverify").value=="pyscrverify")
		 	{
 				alert("Pygmy Agent Scroll No .. U Cant Verify");
 				return false;
 			}
 			if(document.getElementById("scrverify").value=="msscrverify")
		 	{
 				alert("This is Misc Rec Scroll No");
 				return false;
 			}
 			if(document.getElementById("scrverify").value=="Inscrverify")
		 	{
 				alert("Invalid Scroll No");
 				return false;
 			}
 			if(document.getElementById("scrverify").value=="scupdated")
		 	{
 				alert("scroll No. <"+document.forms[0].scrollno.value+"> is being Updated");
 				
 				document.forms[0].scrollno.value="0";
 				document.forms[0].accno.value="0";
 				document.forms[0].accname.value="";
 				document.forms[0].favour.value="";
 				document.forms[0].poamount.value="0.0";
 				document.forms[0].commAcc.value="0.0";
 				document.forms[0].total.value="0.0";
 				document.forms[0].amount.value="0.0";
 				document.forms[0].p_thousand.value="0";
				document.forms[0].r_thousand.value="0";
				document.forms[0].p_fivered.value="0";
				document.forms[0].r_fivered.value="0";
				document.forms[0].p_hundred.value="0";
				document.forms[0].r_hundred.value="0";
				document.forms[0].p_fifty.vlue="0";
				document.forms[0].r_fifty.value="0";
				document.forms[0].p_twenty.value="0";
				document.forms[0].r_twenty.value="0";
				document.forms[0].p_ten.value="0";
				document.forms[0].r_ten.value="0";
				document.forms[0].p_five.value="0";
				document.forms[0].r_five.value="0";
				document.forms[0].p_two.value="0";
				document.forms[0].r_two.value="0";
				document.forms[0].p_one.value="0";
				document.forms[0].r_one.value="0";
				document.forms[0].p_coins.value="0.0";
				document.forms[0].r_coins.value="0.0";
 				
 				return false;
 			}
 			
 		}
 		if(document.getElementById("newverify").value!=null)
 		{
 			if(document.getElementById("newverify").value=="shnewscrverify")
		 		{
 					alert("New Share A/c Scroll No .. U Cant Verify");
 					 hidden();
 				
 				}
 			if(document.getElementById("newverify").value=="sbnewscrverify")
		 		{
 					alert("New SB A/c Scroll No .. U Cant Verify");
 					
 					return false;
 				}
 			if(document.getElementById("newverify").value=="fdnewscrverify")
		 		{
 					alert("New FD A/c Scroll No .. U Cant Verify");
 					
 					return false;
 				}
 			if(document.getElementById("newverify").value=="rdnewscrverify")
		 		{
 					alert("New RD A/c Scroll No .. U Cant Verify");
 				
 					return false;
 				}
 			if(document.getElementById("newverify").value=="Renewscrverify")
		 		{
 					alert("New ReInvD A/c Scroll No .. U Cant Verify");
 					
 					return false;
 				}
 			if(document.getElementById("newverify").value=="canewscrverify")
		 		{
 					alert("New CA A/c Scroll No .. U Cant Verify");
 					
 					return false;
 				}
 			if(document.getElementById("newverify").value=="lknewscrverify")
		 		{
 					alert("New Locker A/c Scroll No .. U Cant Verify");
 					
 					return false;
 				}
 			if(document.getElementById("newverify").value=="pynewscrverify")
		 		{
 					alert("New A/c Scroll No .. U Cant Verify");
 					
 					return false;
 				}
 				if(document.getElementById("newverify").value=="newscrverify")
		 		{
 					alert("New A/c Scroll No .. U Cant Verify");
 					
 					return false;
 				}
 				if(document.getElementById("newverify").value=="errnewscrverify")
		 		{
 					alert("Error .... A/c No is null");
 					
 					return false;
 				}
 				if(document.getElementById("newverify").value=="updateverify")
		 		{
 					alert("Scroll No in Use .. Cant Update or Verify");
 					
 					return false;
 				}
 				
 				if(document.getElementById("newverify").value=="alverify")
		 		{
 					alert("Scroll No < "+document.forms[0].gen_scroll.value+" > Already Verified");
 					
 					return false;
 				}
 				if(document.getElementById("newverify").value=="already")
		 		{
 					alert("This Scroll No is already verified");
 					
 					return false;
 				}
 			
 				
 				
 		}
 	
 	
 
 		if(document.getElementById("amtverify").value=="shareamt")
 		{
 			alert("Min Share Amount for Category <Regular> should be Rs.100");
 			clearfun();
 			document.getElementById("amtverify").value="";
 	     	return false;
 		   
 		}
 		else if(document.getElementById("amtverify").value=="POmaxamt")
 		{
 	      alert("PayOrder Amount cannot be more than");
 	    clearfun();
 	 
 		}
 			else if(document.getElementById("amtverify").value=="POminamt")
 			{
 			
 	          alert("PayOrder Amount cannot be less than");
 	         return false;
 	 
 		
 			}
 	
 			else if(document.getElementById("amtverify").value=="lkamt")
 			{
 			
 	          alert("U r Paying less rent...rent to be paid is Rs.");
 	          return false;
 			}
 			else if(document.getElementById("amtverify").value=="nor_minamt")
 			{
 			
 	          alert("Min Amount should be Rs. 100");
 	          return false;
 			}
    
    
     	if(document.forms[0].gen_scroll.value!=0)
 			{
 				alert(" Note Scroll Number="+document.forms[0].gen_scroll.value);
 				clearfun();
 				
 				document.forms[0].accno.value="0";
 				document.forms[0].accname.value="";
 				document.forms[0].favour.value="";
 				document.forms[0].poamount.value="0.0";
 				document.forms[0].commAcc.value="0.0";
 				document.forms[0].total.value="0.0";
				
			
  			
 				return false;
 			}           
 		 if(document.getElementById("closed").value=="scrollvreify")
 	 		{
 		alert("Scroll no verify successfully....");
 			
			clearfun();
			document.getElementById("closed").value="";
 				return false;
 			}	       
 		 if(document.getElementById("delete").value=="deleted")
 	    {
 	          alert("Deleted Successfully!!!!!!")
 	           clearfun();
 	           return false;
 	     }	
 	
  
    }; 
  	function hidden()
  	{
    var ele=document.forms[0].elements;
 	for(var j=0;j<ele.length;j++){
 	if(ele[j].type=="hidden"){
 	ele[j].value="";
 	}
 	}
  	};
  	
  	
  	function share_amt()
  	{
  		if(document.getElementById("combo").value == 'SH')
 	{
 	 if(document.forms[0].sharecat.value=="0" && document.forms[0].amount.value<350.0 )
 	 {
 	 	alert("Min Share Amount for Category <Regular> should be Rs.350");
 	 	clearfun1();
 	 	return false;
 	 }
 	 
 	 if(document.forms[0].sharecat.value=="1" && document.forms[0].amount.value<350.0 )
 	 {
 	 	alert("Min Share Amount for Category <Associate> should be Rs.350");
 	 	clearfun1();
 	 	return false;
 	 }			
 	 if(document.forms[0].sharecat.value=="2" && document.forms[0].amount.value<450.0)
 	 {
 	 	alert("Min Share Amount for Category <Nominal> should be Rs.450");
 	 	clearfun1();
 	 	return false;
 	 }	
 	  if(document.forms[0].sharecat.value=="3" && document.forms[0].amount.value<350.0 )
 	 {
 	 	alert("Min Share Amount for Category <Socity> should be Rs.350");
 	 	clearfun1();
 	 	return false;
 	 }	
 	}
 	 else if(document.getElementById("combo").value == 'LK')
    {
   
 	 if(document.forms[0].lockertype.value=="SMALL" && document.forms[0].amount.value<100.0 )
 	 {
 	 	alert("U r Paying less rent...rent to be paid 100");
 	 	return false;
 	 }		
     if(document.forms[0].lockertype.value=="MEDIUM" && document.forms[0].amount.value<150.0 )
 	 {
 	 	alert("U r Paying less rent...rent to be paid 150");
 	 	return false;
 	 }		
     if(document.forms[0].lockertype.value=="LARGE" && document.forms[0].amount.value<200.0 )
 	 {
 	 	alert("U r Paying less rent...rent to be paid 200");
 	 	return false;
 	 }
 	} 
 	 
  	};
  	  	
  	function module()
  		{
 		 	document.forms[0].but_value.value="false";	
 		 	document.forms[0].submit();
 		};
 	
 	function but_verify(target)
 	{
 		document.forms[0].but_value.value =target;
 		
 		var value=confirm("Are you sure you want to "+target+" the receipt?");
   		if(value==true)
   		{
 			document.getElementById("verify").value="true";
 			document.forms[0].submit();
		}
 	else 
 		{
 			document.getElementById("verify").value="false";
 			return false;
 		}
 	};	
 function but_delete(target)
 	{
 		document.forms[0].but_value.value=target;
 		var deleteval=confirm("R U Sure to Delete the Scroll No ?");
 		if(deleteval==true)
 		{
 			document.getElementById("delvalue").value="true";
 		
 			document.forms[0].submit();	
 			
		}
 		else 
 		{
 			document.getElementById("delvalue").value="false";
 			return false;
 		}
 	};
 	
 function but_receive(target)
 	{
 		if(document.forms[0].required.value=="Required")
 		{
 		 var refund= document.forms[0].refund.value
 	  
 	     var total=document.forms[0].rec_amt.value
 	  
 		 if(parseInt(document.forms[0].amount.value)==parseInt(refund))
 	        {
 	            alert("Invalid Data Entry");
 	        }
 	
         else
           {
            /* if(refund<total)
                 {
                 	alert("Please Enter Correct Denomination");
                   }
              else
              if(refund==total)
                {
                  alert("Please Enter Correct Denomination");
                } 
 	          else{*/ 
 	     
 	             	document.forms[0].but_value.value=target;
 		            var updateval=confirm("Are you sure you want to confirm the receipt ?");
 		          	 if(updateval==true)
 		                {
 			               document.getElementById("updateval").value="true";
 			               document.forms[0].submit();
		                }
 		            	else  
 		                 {
 			               document.getElementById("updateval").value="false";
 			               return false;
 		                 }
 		
 		           
 		        }
 	}
 	else
 	{ 
 	document.forms[0].but_value.value=target;
    var updateval=confirm("Are you sure you want to confirm the receipt ?");
    if(updateval==true)
 	 {
 	    document.getElementById("updateval").value="true";
 		document.forms[0].submit();
	 }
	 else  
 	  {
        document.forms[0].amount.value="";
        alert("Details to be entered");
        //getElementById("updateval").value="false";
        return false;
 	  }
 		
     }
 	
 	};
 	
 	function numLimit()
 	{
 	if(document.forms[0].scrollno.value.length<=5)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].scrollno.value="";
 	document.forms[0].scrollno.focus;
 	alert("Digit Limit is 5");
 	return false;
 	}
 	}
 	
 	function name_Limit()
 	{
 	if(document.forms[0].accname.value.length<=20)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].accname.value="";
 	document.forms[0].accname.focus;
 	alert("Warning:\nEnter not more than 20 characters");
 	return false;
 	}
 	}
 	
 	function pname_Limit()
 	{
 	if(document.forms[0].purchase.value.length<=20)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].purchase.value="";
 	document.forms[0].purchase.focus;
 	alert("Warning:\nEnter not more than 20 characters");
 	return false;
 	}
 	}
 	
 
 	function favLimit()
 	{
 	if(document.forms[0].favour.value.length<=20)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].favour.value="";
 	document.forms[0].favour.focus;
 	alert("Warning:\nEnter not more than 20 characters");
 	return false;
 	}
 	}
 	
 	
 	function poamtLimit()
 	{
 	if(document.forms[0].poamount.value.length<=12)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].poamount.value="";
 	document.forms[0].poamount.focus;
 	alert("Warning:\nDigit Limit is 12!!!");
 	return false;
 	}
 	}
 	
 	function numLimit1()
 	{
 	if(document.forms[0].accno.value.length<=12)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].accno.value="";
 	document.forms[0].accno.focus;
 	alert("Digit Limit is 12");
 	return false;
 	}
 	}
 	
 	function comm_amtLimit()
 	{
 	if(document.forms[0].commAcc.value.length<=12)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].commAcc.value="";
 	document.forms[0].commAcc.focus;
 	alert("Warning:\nDigit Limit is 12!!!");
 	return false;
 	}
 	}
 	
 	function numLimit1()
 	{
 	if(document.forms[0].accno.value.length<=12)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].accno.value="";
 	document.forms[0].accno.focus;
 	alert("Digit Limit is 12");
 	return false;
 	}
 	}
 	
 	
 	
 	function amtLimit()
 	{
 	 if(document.forms[0].amount.value.length<=12)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].amount.value="";
 	document.forms[0].amount.focus;
 	alert("Warning:\nEnter not more than 12 Digits!!!");
 	return false;
 	}
 	}
 	
 	function custacnoLimit()
 	{
 	if(document.forms[0].custacno.value.length<=12)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].custacno.value="";
 	document.forms[0].custacno.focus;
 	alert("Digit Limit is 12");
 	return false;
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
         		alert("Warning:\nOnly 7 digits allowed!!!")
         		txt.value="";
         		return false;
          	}
         }

 	function but_confirm(target)
 	{
 		document.forms[0].but_value.value=target;
 		var updateval=confirm("Are you sure you want to confirm the receipt updation?");
 		if(updateval==true)
 		{
 			document.getElementById("updateval").value="true";
 			document.forms[0].submit();
 			
		}
 		else 
 		{
 			document.getElementById("updateval").value="false";
 			return false;
 		}
 	};
 	
 	function but_update(target)
 	{
 		document.forms[0].but_value.value=target;
 	
 	};
 	
 	function clearfun()
  {
  		
  		var ele=document.forms[0].elements;
  		for(var i=0;i<ele.length;i++)
  		{
  			if(ele[i].type=="text")
  			{
  				ele[i].value="";   
  			}
  			if(ele[i].type=="hidden")
  			{
  				ele[i].value="";   
  			}
  		}
  };
 
 function rmultiplvalue()
{
		var multiple=new Array(9);
		var i=0;
		var sum=0;
		
		multiple[0]=document.forms[0].lbl_thousand.value * document.forms[0].r_thousand.value;
		document.forms[0].rval_thousand.value=multiple[0];
		
		multiple[1]= document.forms[0].lbl_fivered.value * document.forms[0].r_fivered.value;
		document.forms[0].rval_fivered.value=multiple[1];
		
		multiple[2]= document.forms[0].lbl_hundred.value * document.forms[0].r_hundred.value;
		document.forms[0].rval_hundred.value=multiple[2];
		
		multiple[3]= document.forms[0].lbl_fifty.value * document.forms[0].r_fifty.value;
		document.forms[0].rval_fifty.value=multiple[3];
		
		multiple[4]= document.forms[0].lbl_twenty.value * document.forms[0].r_twenty.value;
		document.forms[0].rval_twenty.value=multiple[4];
		
		multiple[5]= document.forms[0].lbl_ten.value * document.forms[0].r_ten.value;
		document.forms[0].rval_ten.value=multiple[5];
		
		multiple[6] = document.forms[0].lbl_five.value * document.forms[0].r_five.value;
		document.forms[0].rval_five.value=multiple[6];
		
		multiple[7] = document.forms[0].lbl_two.value * document.forms[0].r_two.value;
		document.forms[0].rval_two.value=multiple[7];
		
		multiple[8] = document.forms[0].lbl_one.value * document.forms[0].r_one.value;
		document.forms[0].rval_one.value=multiple[8];
		
		multiple[9]= document.forms[0].r_coins.value;		
		document.forms[0].rval_coins.value=multiple[9];
		for(i=0;i<9;i++)
		{
			sum=sum+multiple[i];
			document.forms[0].rec_amt.value=sum;
			document.forms[0].cur_total.value=sum;
				
		}
		
		var total=document.forms[0].cur_total.value;
		var refund=document.forms[0].refund.value;	
		document.forms[0].rec_amt.value=total-refund;
	};	
	function disable()
{
 alert(document.forms[0].updatefun.value);
 if(document.forms[0].updatefun.value=="update")
 {
  document.forms[0].accno.disabled=false;
  document.forms[0].amount.disabled=false;
  document.forms[0].accname.disabled=false;
 
  }
  
  };
	
	
function pmultiplvalue()
	{ 
		var multiple=new Array(9);
		var i=0;
		var sum1=0;
			
		multiple[0]=document.forms[0].lbl_thousand.value * document.forms[0].p_thousand.value;
		document.forms[0].pval_thousand.value=multiple[0];
		
		multiple[1]= document.forms[0].lbl_fivered.value * document.forms[0].p_fivered.value;
		document.forms[0].pval_fivered.value=multiple[1];
		
		multiple[2]= document.forms[0].lbl_hundred.value * document.forms[0].p_hundred.value;
		document.forms[0].pval_hundred.value=multiple[2];
		
		multiple[3]= document.forms[0].lbl_fifty.value * document.forms[0].p_fifty.value;
		document.forms[0].pval_fifty.value=multiple[3];
		
		multiple[4]= document.forms[0].lbl_twenty.value * document.forms[0].p_twenty.value;
		document.forms[0].pval_twenty.value=multiple[4];
		
		multiple[5]= document.forms[0].lbl_ten.value * document.forms[0].p_ten.value;
		document.forms[0].pval_ten.value=multiple[5];
		
		multiple[6] = document.forms[0].lbl_five.value * document.forms[0].p_five.value;
		document.forms[0].pval_five.value=multiple[6];
		
		multiple[7] = document.forms[0].lbl_two.value * document.forms[0].p_two.value;
		document.forms[0].pval_two.value=multiple[7];
		
		multiple[8] = document.forms[0].lbl_one.value * document.forms[0].p_one.value;
		document.forms[0].pval_one.value=multiple[8];
		
		multiple[9]= document.forms[0].p_coins.value;		
		document.forms[0].pval_coins.value=multiple[9];
		
		for(i=0;i<9;i++)
		{
			sum1=sum1+multiple[i];
			document.forms[0].refund.value=sum1;
		}
		
		var total=document.forms[0].cur_total.value;
		var refund=document.forms[0].refund.value;	
		document.forms[0].rec_amt.value=total-refund;
	};    
		
function fun_exc()
	{
		
	};
	function fun_denom()
	{
		if(document.forms[0].required.value=="Required")
		alert("Enter Currency Denmoinations");
	};	
		
	
		
--></script>

</head>

<body onload="HideShow(11)" class="Mainbody">
<%! String date; %>
<% date=(String)request.getAttribute("date");
   System.out.println("Date------>"+date);
%>
<%!ModuleObject[] module_obj_array,module_obj_array1;%>

<%module_obj_array =(ModuleObject[])request.getAttribute("MainModules");%>

<%module_obj_array1 =(ModuleObject[])request.getAttribute("MainModules1");%>

<%String module1 = (String)request.getAttribute("module1");%>

<%AccSubCategoryObject[] accsubcategoryobject = (AccSubCategoryObject[])request.getAttribute("podescription");
AccSubCategoryObject[] account1 = (AccSubCategoryObject[])request.getAttribute("podescription1");
AccSubCategoryObject[] account2 = (AccSubCategoryObject[])request.getAttribute("podescription2");
%>

<%String custtype = (String)request.getAttribute("custtype");%>

<%String module = (String)request.getAttribute("module");%>

<%String[] locktype = (String[])request.getAttribute("locktype"); 
	System.out.println("Locker Type....-------------->"+locktype);%>

<%ShareCategoryObject[] share = (ShareCategoryObject[])request.getAttribute("sharecategory");
	System.out.println("share master object-------------->"+share);

%>
	
<%String getAbbrv = (String)request.getAttribute("getAbbrv"); %>

<%String newaccount =(String)request.getAttribute("newacc");
  System.out.println("Is it new account====="+newaccount);
%>
<%String flag1 =(String)request.getAttribute("flag");%>
<% String sharecategory= (String)request.getAttribute("sharecategoryname");%>

<%String shareindex = (String)request.getAttribute("shareindex");%>

<%String lockerdecrpn= (String)request.getAttribute("lockerdescpn");%>

<%String lockerindex=(String)request.getAttribute("lockerindex");%>

<%String button=(String)request.getAttribute("button_value"); %>

<%String val_button=(String)request.getAttribute("button"); %>

<%String button_val=(String)request.getAttribute("submit");%>

<%String semodule=(String)request.getAttribute("setmodule");%>

<%String setindex=(String)request.getAttribute("setindex");%>

<%String sepomodule=(String)request.getAttribute("setcustmodule");%>

<%String setpoindex=(String)request.getAttribute("setcustindex");%>

    
<%String p1=(String)request.getAttribute("p1");%>

<%String p10=(String)request.getAttribute("p10");%>

<%String p100=(String)request.getAttribute("p100");%>

<%String p1000=(String)request.getAttribute("p1000");%>

<%String p2=(String)request.getAttribute("p2");%>

<%String p20=(String)request.getAttribute("p20");%>

<%String p5=(String)request.getAttribute("p5");%>

<%String p500=(String)request.getAttribute("p500");%>

<%String pcoins=(String)request.getAttribute("pcoins");%>

<%String r1=(String)request.getAttribute("r1");%>

<%String r10=(String)request.getAttribute("r10");%>

<%String r100=(String)request.getAttribute("r100");%>

<%String r1000=(String)request.getAttribute("r1000");%>

<%String r2=(String)request.getAttribute("r2");%>

<%String r20=(String)request.getAttribute("r20");%>

<%String r5=(String)request.getAttribute("r5");%>

<%String r50=(String)request.getAttribute("r50");%>

<%String rcoins=(String)request.getAttribute("rcoins");%>

<%String setpodesc=(String)request.getAttribute("pomoduledesc");%>

<%String setmod_desc=(String)request.getAttribute("moduledesc");%>

<%String setshare=(String)request.getAttribute("setshare");%>

<%String setshindex=(String)request.getAttribute("setsharindex");%>

<%String custsubcat=(String)request.getAttribute("custsubcat");%>

<%String custsubcatdesc=(String)request.getAttribute("cust_cat");%>

<%double tml_runable=(Double)request.getAttribute("tml_runable");%>

<%int scroll_no=(Integer)request.getAttribute("scrollno"); %>

<%double tot_runable=(Double)request.getAttribute("Allterminal"); %>

<%String lockerstore=(String)request.getAttribute("lokertype");%>

<%Double amt=(Double)request.getAttribute("amount");%>

<%String accountno=(String)request.getAttribute("accno");%>
<%String displaymsg=(String)request.getAttribute("displaymsg");%>

<%String accname1=(String)request.getAttribute("accname"); %>
<%String verified= (String)request.getAttribute("Verified"); %>


<%String name=(String)request.getAttribute("scroll_nm");%>
<%System.out.println("The account holder name in jsp===="+name); %>

<%String runnable=(String)request.getAttribute("tmlrunnable");%>

<%String customertype=(String)request.getAttribute("custtype");%>

<%String custacno=(String)request.getAttribute("custacno");%>

<%String custacname=(String)request.getAttribute("custacname");%>

<%String favour=(String)request.getAttribute("favour");%>

<%String commamt=(String)request.getAttribute("commamt");%>

<%String totalamt=(String)request.getAttribute("totalamt");%>

<%String poamt=(String)request.getAttribute("poamt");%>

<%String acc_nm=(String)request.getAttribute("acc_nm"); %>

<%CashObject cash = (CashObject)request.getAttribute("cashamt"); %>
<%String msg=(String)request.getAttribute("msg");%>

<html:form action="/Receipt?pageId=2001">

<%String value = (String)request.getAttribute("buttvalue");%>
<%String required=(String)request.getAttribute("required"); %>


<%!boolean flag = true;%>
<%if(value!=null){ %>
	<%if(value.equalsIgnoreCase("update")){
		flag = false;
		System.out.print("value in JSP=====>"+value);
	}
	else
	{
		flag=false;
	}
	%>

<%}%>	

<%if(Integer.parseInt(button_val.trim())==1){ %>
<h2 class="h2">
<center>General Receipt</center></h2>


<%if(displaymsg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=displaymsg %></font>
<%} %>
<%if(msg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=msg %></font>
<%} %>

<%} if(Integer.parseInt(button_val.trim())==2){ %>

<h2 class="h2">

<center>Receipt Verification</center></h2>
<h3 align="left" style="font-size:x-small;color: red;font-family:Comic Sans MS "><bean:message key="label.terminalrs"/><%= tot_runable%></h3>
<%} %>



	<html:hidden property="accountobject" styleId="accobject" />
	<html:hidden property="accexist" styleId="accext" />
	<html:hidden property="abbrv" value="<%=getAbbrv %>" styleId="combo" />
	<html:hidden property="miniamt" styleId="miniamt" />
	<html:hidden property="amtverify" styleId="amtverify"/>
	<html:hidden property="scrverify" styleId="scrverify"/>
	<html:hidden property="newscrverify" styleId="newverify"/>
   <html:hidden property="clearatstart"/>
	

<table class="txtTable">
	<tr>
		<td>
		<table cellspacing="5" class="txtTable">
				
			<tr>
				<td align="right"><bean:message key="label.scroll_no"></bean:message></td>
				<TD><html:text property= "scrollno" onblur="submit()" size="10" onkeypress="return onlynumbers()" onkeydown="return numLimit()" styleClass="formTextFieldWithoutTransparent"></html:text></TD>
			</tr>
			
			<tr>
				<td align="right"><bean:message key="label.acctype"></bean:message></td>
				<%if(semodule!=null && setindex!=null){%>
				<td><html:select size="1" property="accounttype"  styleClass="formTextFieldWithoutTransparent">
				       
						<html:option value="<%=setindex%>"><%=semodule%></html:option>
					</html:select></td>
				<td><html:text property="Moduledesc" value="<%=setmod_desc%>" size="12" readonly="true"></html:text></td>
				<%} else{%>
					
				<td><html:select size="1" property="accounttype" onchange="module()" styleClass="formTextFieldWithoutTransparent">
				  
					<%if(module_obj_array!=null){%>
						<%for(int i=0;i<module_obj_array.length;i++){ %>
						 
						<html:option value="<%=""+i %>"><%=module_obj_array[i].getModuleAbbrv()%></html:option>
					<%} } %>
					</html:select></td>

					<%} %>
			</tr>

			<tr>
				<td align="right"><bean:message key="label.acc_no2"></bean:message></td>
				
				<%if(accountno!=null){ %>
				
				<td><html:text property="accno"  onblur="submit()" size="10" onkeypress="return onlynumbers()" value="<%=""+accountno%>" onkeydown="return numLimit1()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				<% }
				
				else if(accountno!=null && scroll_no!=0){ %>
				
				<td><html:text property="accno" onblur="submit()" size="10" onkeypress="return onlynumbers()" value="<%=""+accountno%>" onkeydown="return numLimit1()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
				
				<%}else{ %>
				<td><html:text property="accno" onblur="submit()" size="10" onkeypress="return onlynumbers()" onkeydown="return numLimit1()" styleClass="formTextFieldWithoutTransparent"  ></html:text></td>
				<%} %>
				
				<%if(newaccount!=null){ %>
				<td><html:text property="newlabel" readonly="true" value="<%=newaccount%>" size="10" styleClass="formTextField" style="font-family:bold;color:red" ></html:text></td>
				<%} %>
			</tr>
			
			<tr id="Name11">
				<td align="right"><bean:message key="label_name1"></bean:message></td>
				  
				  
				   <% if(name!=null){ %>
				
				         <td><html:text property="accname" size="20"   onkeypress="return false;"  style="font-family:bold;color:red" value="<%=""+name%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				    <%}
				   else if(accname1!=null){ %>
					
			         <td><html:text property="accname" size="20" onkeypress="return onlyalpha()" onkeydown="return name_Limit()"  style="font-family:bold;color:red" value="<%=""+accname1%>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			    <%}
				   else if(acc_nm!=null){ %>
					
			         <td><html:text property="accname" size="20" onkeypress="return onlyalpha()"  disabled="true" style="font-family:bold;color:red" value="<%=""+acc_nm%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			    <%}	    			  
			    else{ %>
				<td><html:text property="accname" size="20" onkeypress="return onlyalpha()" onkeydown="return name_Limit()"  styleClass="formTextFieldWithoutTransparent"   ></html:text></td>
				<%} %>
				

			<tr id="Cust11">
				<td align="right"><bean:message key="label.custtype" /></td>
				<%if(customertype!=null){ %>
				<td><html:select property="custtype" styleClass="formTextFieldWithoutTransparent">
						<html:option value="<%=""+customertype%>"></html:option>
					</html:select></td>
				<%}else { %>
				<td><html:select property="custtype" onchange="module()" styleClass="formTextFieldWithoutTransparent">
						<html:option value="Customer"></html:option>
						<html:option value="Non Customer"></html:option>
						
					</html:select></td>
				<%} %>
			</tr>

			<tr id="Cactype11">
				<td align="right"><bean:message key="label.custacctype" /></td>
				<%if(sepomodule!=null && setpoindex!=null){%>
				<td><html:select size="2" property="accounttype" onchange="module()" styleClass="formTextFieldWithoutTransparent">
					<html:option value="<%=setpoindex%>"><%=sepomodule%></html:option>
					</html:select></td>
					<%if(setpodesc!=null){ %>
				<td><html:text property="Moduledesc" value="<%=setpodesc%>" readonly="true" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
				<%} }else{%>
				
				<td><html:select property="custacctype" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
				<%if(module_obj_array1!=null){%>
				<%for(int i=0;i<module_obj_array1.length;i++){ %>
					<html:option value="<%=""+i%>"><%=module_obj_array1[i].getModuleAbbrv()%></html:option>
				<%} }%>
					</html:select></td>
	
				<%if(module1!=null ){ %>
				<td><html:text property="Moduledesc1" value="<%=module1%>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				<%} }%>
			</tr>
			
			<tr id="Cacc11">
				<td align="right"><bean:message key="label.custaccno" /></td>
				<%if(custacno!=null){ %>
				<td><html:text property="custacno" onblur="submit()" size="10" onkeypress="return onlynumbers()" value="<%=""+custacno%>" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
				<%}else { %>
				<td><html:text property="custacno" onblur="submit()" size="10" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent" onkeydown="return custacnoLimit()"></html:text></td>
				<%} %>
			</tr>

			<tr id="Desc11">
				<td align="right"><bean:message key="label.Desc" /></td>
				<td><html:select property="description" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
				<%if(custsubcat!=null && custsubcatdesc!=null ) {%>
					<html:option value="<%=""+custsubcat%>" ><%=""+custsubcatdesc%></html:option>
					<%}else{ %>
					<%if(custtype!=null){ %>
					<%if(custtype.equalsIgnoreCase("Customer")){ %>
					<%for(int i=0;i<accsubcategoryobject.length;i++){%>
					<html:option value="<%=""+accsubcategoryobject[i].getSubCategoryCode()%>" ><%=accsubcategoryobject[i].getSubCategoryDesc()%></html:option>
					<%}} }%>
						
					<%if(custtype!=null){ %>
					<% if(custtype.equalsIgnoreCase("Non Customer")){
					if(account2!=null){
					for(int i=0;i<account2.length;i++){%>
					<html:option  value="<%=""+account2[i].getSubCategoryCode()%>" ><%=account2[i].getSubCategoryDesc()%></html:option>
					<% 	}}}	} %>
					<%} %>
					</html:select></td>
			</tr>

			<tr id="Purc11">
				<td align="right"><bean:message key="label.purchas" /></td>
				<%if(custacname!=null){ %>
				<td><html:text property="purchase" size="20" onkeypress="return onlyalpha()" onkeydown="return pname_Limit()" value="<%=""+custacname%>" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
				<%}else { %>
				<td><html:text property="purchase" size="20" onkeypress="return onlyalpha()" onkeydown="return pname_Limit()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
				<%} %>
			</tr>

			<tr id="Fav11">
				<td align="right"><bean:message key="label.favour" /></td>
				<%if(favour!=null){ %>
				<td><html:text property="favour" size="10" onkeypress="return onlyalpha()" onkeydown="return favLimit()" value="<%=""+favour%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				<%}else { %>
				<td><html:text property="favour" size="10" onkeypress="return onlyalpha()" onkeydown="return favLimit()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
				<%} %>
			</tr>
				
			<tr id="Poamt11">
				<td align="right"><bean:message key="label.Amt"></bean:message></td>
				<%if(poamt!=null){ %>
				<td><html:text property="poamount" onblur="submit()" size="10" value="<%=""+poamt%>" onkeypress="return onlyDoublenumbers()" onkeydown="return poamtLimit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				<%}else { %>
				<td><html:text property="poamount" onblur="submit()" size="10" onkeypress="return onlyDoublenumbers()" onkeydown="return poamtLimit()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
				<%} %>
			</tr>
			
			<tr id="Comm11">
				<td align="right"><bean:message key="label.comm" /></td>
				<%if(commamt!=null){ %>
				<td><html:text property="commAcc" size="10"  value="<%=commamt%>" readonly="true" onkeypress="return onlyDoublenumbers()" onkeydown="return comm_amtLimit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				<%}else { %>
				<td><html:text property="commAcc" size="10" readonly="true" onkeypress="return onlyDoublenumbers()" onkeydown="return comm_amtLimit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				<%} %>
			</tr>

			<tr id="Tot11">
				<td align="right"><bean:message key="label.tot" /></td>
				<%if(totalamt!=null){ %>
				<td><html:text property="total" size="10" onkeypress="return onlyDoublenumbers()" value="<%=totalamt %>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
				<%} 
				else { %>
				<td><html:text property="total" size="10" onkeypress="return onlyDoublenumbers()" readonly="true" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
				<%} %>
			</tr>
			
			<tr id="Shcat11">
				<%if(share!=null) {%>
				<td align="right"><bean:message key="label.sh_cat"></bean:message></td>
				<td><html:select property="sharecat" styleId="sharecat" styleClass="formTextFieldWithoutTransparent">
				<%for(int i=0;i<share.length;i++){ %>
					<html:option value="<%=""+i%>"><%=share[i].getCatName()%></html:option>
				<%}  %>
					</html:select></td>
				<%}else if(sharecategory!=null && shareindex!=null){ %>
				<td align="right"><bean:message key="label.sh_cat"></bean:message></td>
				<td><html:select property="sharecat" styleClass="formTextFieldWithoutTransparent">
					<html:option value="<%=""+shareindex%>"><%=sharecategory%></html:option>
					</html:select></td>
				<%}
					
					else if(setshare!=null && setshindex!=null){ %>
				<td align="right"><bean:message key="label.sh_cat"></bean:message></td>
				<td><html:select property="sharecat" styleClass="formTextFieldWithoutTransparent">
					<html:option value="<%=""+setshindex%>"><%=setshare%></html:option>
					</html:select></td>

				<%} %>
					
			</tr>

			<tr id="Lock11">
				<%if(locktype!=null){ %>
				<td><bean:message key="lab.lock_types"></bean:message></td>
				<td><html:select property="lockertype" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
						
				<%for(int i=0;i<locktype.length;i++){ %>
					<html:option value="<%=locktype[i]%>"><%=locktype[i]%></html:option>
				<%} %>
					</html:select></td>
				<% }else if(lockerdecrpn!=null && lockerindex!=null){ %>
				<td align="right"><bean:message key="lab.lock_types"></bean:message></td>
				<td><html:select property="lockertype" styleClass="formTextFieldWithoutTransparent">
					<html:option value="<%=lockerdecrpn%>"><%=lockerdecrpn%></html:option>
					</html:select></td>
				<%} 

					else if(lockerstore!=null){%>
				<td align="right"><bean:message key="lab.lock_types"></bean:message></td>
				<td><html:select property="lockertype" styleClass="formTextFieldWithoutTransparent">
					<html:option value="<%=lockerstore%>"><%=lockerstore%></html:option>
					</html:select></td>
				<%} %>
			</tr>


       		<tr id="Amt11">
					<td align="right"><bean:message key="label.Amt"></bean:message></td>
					
					<%if(cash!=null && scroll_no!=0){ %>
					<td><html:text property="amount"  styleId="comm_amt" size="10" onkeypress="return onlyDoublenumbers()" onkeydown="return amtLimit()" style="font-family:bold;color:red" value="<%=""+cash.getAmount() %>" disabled="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
					<%}
					else { %>
					<td><html:text property="amount"  size="10" onkeypress="return onlyDoublenumbers()" onkeydown="return amtLimit()" styleClass="formTextFieldWithoutTransparent" onblur="share_amt()"></html:text></td>
					<%} %>
			</tr>
		
			<tr>
			<td align ="right"><bean:message key="lab.details"/></td>
			<td><html:select property ="details" onchange="submit()">
	             <html:option value="Select"></html:option>	
			    <html:option value="Personal Details"></html:option>
			     <html:option value="Loan Status"></html:option>
			     </html:select>
			     </td>
			</tr>
		
				
			<tr>
					<TD align="right"><bean:message key="label.currdenom" /></TD>
					<td><html:select property="required" onchange="submit()" onblur="fun_denom()">
						<html:option value="Select"></html:option>
						<html:option value="Required"></html:option>
						<html:option value="Not Required"></html:option>
						</html:select>
					</td>
			</tr>
			
				<html:hidden property="but_value" styleId="subvalue" value="error" />
				
				<html:hidden property="closed" styleId="closed" />
				<html:hidden property="gen_scroll" styleId="scroll_num" />
				<html:hidden property="verify_scroll" styleId="scroll_verify" />
				<html:hidden property="lk_amount" styleId="lk_amt" />
				<html:hidden property="ver_button" styleId="verify"></html:hidden>
				<html:hidden property="delete" styleId="delvalue"/>
			
				<html:hidden property="update" styleId="updateval"/>
				<html:hidden property="payorderamt"/>
				<html:hidden property="cur_stock" styleId="currency"/>
								<tr>
					<%if(Integer.parseInt(button_val.trim())==1){ %>
					<%if(scroll_no==0){ %>
					<td align="right"><font size="50"><html:button property="receive" onclick="but_receive('receive')" value="Receive" styleClass="ButtonBackgroundImage">Receive</html:button></font></td>
					<%} else if(scroll_no!=0){ %>
					<td><font size="50"><html:button property="deletefun" onclick="but_delete('Delete')" value="Delete" styleClass="ButtonBackgroundImage">Delete</html:button></font></td>
					<td><font size="50"><html:button property="verifyfun" onclick="but_verify('Verify')" value="Verify" styleClass="ButtonBackgroundImage">Verify</html:button></font></td>
					<td><html:button onclick="disable('Confirm')" property="updatefun" value="update" styleClass="ButtonBackgroundImage">Update</html:button></td>
						<td><font size="50"><html:button property="confirm" onclick="but_confirm('Confirm')" value="Confirm" styleClass="ButtonBackgroundImage">Confirm</html:button></font></td>
					<%} %>
					<td><html:button property="clear" onclick="return clearfun()" value="Clear" styleClass="ButtonBackgroundImage" >Clear</html:button></td>
					<%} else if(Integer.parseInt(button_val.trim())==2){ %>
					<td><font size="50"><html:submit onclick="but_verify('Verify')" value="Verify" styleClass="ButtonBackgroundImage">Verify</html:submit></font></td>
					<td><html:button property="clear" onclick="return clearfun()" value="Clear" styleClass="ButtonBackgroundImage" ></html:button></td>
					<%} %>
				</tr>

			</table>

			</td>
			
			<td>
			
			<%if(required!=null && required.equalsIgnoreCase("Required")){ %>
			
			
			<table cellspacing="10" style="border:thin solid #339999;" class="txtTable">
		
		<tr>
			<td><bean:message key="label.amount"></bean:message></td>
			<td align="center"><bean:message key="label.no"></bean:message></td>
			<td></td>
			<td align="center"><bean:message key="label.no"></bean:message></td>
			<td><bean:message key="label.amount"></bean:message></td>
		</tr>
		
		
		<tr>

			<td align="right"><html:text size="8" property="pval_thousand" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"  ></html:text></td>
			<td align="right"><html:text size="8" property="p_thousand"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<TD><html:text size="2" property="lbl_thousand" value="1000" disabled="true" styleClass="formTextField"></html:text></TD>
			<td align="center"><html:text size="8" property="r_thousand" onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<td align="left"><html:text size="8" property="rval_thousand" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" ></html:text></td>
		</tr>
		
		<tr>
			<td align="right"><html:text size="8" property="pval_fivered" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
			<td align="right"><html:text size="8" property="p_fivered"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<td><html:text size="2" property="lbl_fivered" value="500" disabled="true"  styleClass="formTextField"></html:text></td>
			<td align="center"><html:text size="8" property="r_fivered"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<td align="left"><html:text size="8" property="rval_fivered" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" ></html:text></td>
		</tr>
		
		<tr>
			<td align="right"><html:text size="8" property="pval_hundred" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
			<td align="right"><html:text size="8" property="p_hundred"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<td><html:text size="2" property="lbl_hundred" value="100" disabled="true"  styleClass="formTextField"></html:text></td>
			<td align="center"><html:text size="8" property="r_hundred"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<td align="left"><html:text size="8" property="rval_hundred" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
		</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_fifty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_fifty"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_fifty" value="50" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_fifty"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_fifty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_twenty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_twenty"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_twenty" value="20" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_twenty" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_twenty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_ten" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="center"><html:text size="8" property="p_ten"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_ten" value="10" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_ten" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_ten" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_five" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_five"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_five" value="5" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_five"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_five" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_two" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_two"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_two" value="2" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_two" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_two" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_one" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_one"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_one" value="1" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_one"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_one" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_coins" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="center"><html:text size="8" property="p_coins"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><bean:message key="label.coins"></bean:message></td>
		<td align="center"><html:text size="8" property="r_coins"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_coins" readonly="true" styleClass="formTextFieldcolor" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		
		<td></td>
		<td></td>
		<td></td>
		<td><bean:message key="label.total"></bean:message></td>
		
		<td align="left"><html:text size="8" property="cur_total" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
	</tr>
	
	<tr>
		
		<td align="right"><html:text size="8" property="refund"  styleClass="formTextFieldcolor"></html:text>
		</td>
		<td><bean:message key="label.refund"/></td>
		
		<td></td>
		
		<td align="left"><html:text size="8" property="rec_amt" styleClass="formTextFieldcolor"></html:text></td>
	<td><bean:message key="label.receive"/></td>
	</tr>
			</table>		
			
			</td>
		<%}else if(required!=null && required.equalsIgnoreCase("Personal")){ %>
			
			<td>
			<% String jspPath=(String)request.getAttribute("flag1");
    	  			System.out.println("jspPath=="+jspPath);
         	%> <%if(jspPath!=null){ %> <jsp:include page="<%=jspPath%>"></jsp:include>
			<%}%>
			
		</td>	
		<%}else{ %>
		<td>
			<% String jspPath=(String)request.getAttribute("flag2");
    	  			System.out.println("jspPath=="+jspPath);
         	%> <%if(jspPath!=null){ %> <jsp:include page="<%=jspPath%>"></jsp:include>
			<%}%>
			
		</td>	
               
			<% }%>

		

</table>
	
	

</html:form>

</body>

</html>