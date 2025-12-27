	function submitMICR(id){
	
		alert ( id ) ;
		
		alert () 
	
		if ( document.getElementById( "citycode" ).value.length != 3 ){
		
				alert( "Enter the City Code" );
				return false;
	
		} else if ( document.getElementById("bank_code" ).value.length != 3 ){
		
				alert( "Enter the Bank Code" );
				return false;
	
		} else if ( document.getElementById("branch_code" ).value.length != 3 ){
		
				alert( "Enter the Branch Code" );
				return false;
	
		} else {
		
			
				document.forms[0].form_flag.value = 1;
				
				var mirc =  document.getElementById("citycode").value + document.getElementById("bank_code" ).value+document.getElementById("branch_code" ).value;
				
				
				 document.getElementById("micr").value = mirc;
				
				document.forms[0].submit();				
				return true;
		}
		
	
	
	}
	
	function calls(input){
	
	
			
			var index = -1, i =0, found = false ;
			
			alert ("M................."+input.form.length);
			while ( i< input.form.length && index== -1  ){
					
					if ( input.form[i] == input )
						  index = i;
					 else
						 i++; 
			} 
		
		alert( input.form[4].value);
	}
	
	

	function setformtype( form_type ){
	
		alert ( form_type );
		
		
		if ( form_type=='submit'){
		
			document.forms[0].form_flag.value=2;
			alert ( " hello 2...............");
		
			return true;
		}
		
		else if ( form_type=='update'){
		
			document.forms[0].form_flag.value=3;
			alert ( " hello 2...............");
		
			return false;
		}
		
		else if ( form_type=='delete'){
		
			document.forms[0].form_flag.value=4;
			alert ( " hello 2...............");
		
			return false;
		}
		return false;
	
	} 
	
	
	

function call(){
		
		
		checkError();	
		check_discount();
		check_laon();
		 check_multi();
			
		if ( document.forms[0].ctrl_no.value!=0 ) 
			alert ( document.forms[0].ctrl_no.value );
			
		if ( document.getElementById("int_clr" ).checked  || ( document.forms[0].form_flag.value == 4 || document.forms[0].form_flag.value == 5) ){
		
				document.getElementById("citycode").disabled=true;
				document.getElementById("bank_code").disabled=true;
				document.getElementById("branch_code").disabled=true;
				
			document.getElementById("div_debit_pan").style.display = 'block';
		}
		
		
		
	}
	
	function setflag( flag_num, ids ){
	
		if ( ids.value != "" ){
			
			if ( flag_num == 5  ) {
				
				if ( (document.getElementById("credit_acc_no") .value == ids.value) && ( document.getElementById("credit_acc_type").value == document.getElementById("debit_acc_type").value) ){
					
					
					alert ( " Credit Account and Debit Account are Same plz verify " ) ;
					return false;
				}
			
			} 
			
			if (ids.id == "dis_amt" ){
			
					if ( document.getElementById("dis_amt").value.length  > 0 && document.getElementById("amt").value .length > 0){
					
						if ( document.getElementById("dis_amt").value > document.getElementById("amt").value ){
								
							alert ( " Discount Amount is greater than the Cheque Amount "  );
							return false;
					}
				} else {
						
						alert ("please enter the Feilds ");
						return false;
				}
			}
			
			document.getElementById( "today_dt" ).value = getTodayDate();
			
			alert ( document.getElementById( "today_dt" ).value + " present date " )
			
			document.getElementById("flagset").value = flag_num
			 
			document.forms[0].submit();
	
		}
		
		
	}
	
	function setcheckflag( flag_num, ids ){
	
	
			
			alert( ids.value +"----------" + flag_num  ) 
			
			if ( ids.checked ){
									
				   					
				
				
				
				document.getElementById("div_debit_pan").style.display = 'block';					
				document.forms[0].form_flag.value = flag_num;
				document.forms[0].submit();
				
			
			} else {
				
				document.getElementById("citycode").disabled=false;
				document.getElementById("bank_code").disabled=false;
				document.getElementById("branch_code").disabled=false;
				
				document.getElementById("div_debit_pan").style.display = 'none';
			
			}
		
	}
	
	
	
	function checkError(){
			
			
			
			
			if ( document.getElementById("err_id").value != 0 ){
				
				
					alert ( document.getElementById("err_mess_id").value  );	
					
					if ( document.getElementById("err_id").value > 1 )
						document.getElementById( "bounce_id" ).checked='checked'; 			
					
			            				
			}
			return false;	
		
	
	}
	
	function check_laon(){
		
		if ( document.getElementById("loan_cre").checked ) {
			
			 
			document.getElementById("loan_div").style.display = 'block';  
			document.getElementById("loan_div1").style.display = 'block';
			
		} else {
			
			document.getElementById("loan_div").style.display = 'none';  
			document.getElementById("loan_div1").style.display = 'none';
			
		}
	
	}
	
	function check_multi(){
	
		if ( document.getElementById("chk_comp").checked ) {
		
			document.getElementById("div_comp1").style.display = 'block';  
			document.getElementById("div_comp").style.display = 'block';
			
		} else {
			
			document.getElementById("div_comp1").style.display = 'none';  
			document.getElementById("div_comp").style.display = 'none';
			
		}
	
	}
	
	function check_discount(){
		
		if ( document.getElementById("chk_discount").checked ) {
		
			document.getElementById("div_discount1").style.display = 'block';  
			document.getElementById("div_discount").style.display = 'block';
			
		} else {
			
			document.getElementById("div_discount1").style.display = 'none';  
			document.getElementById("div_discount").style.display = 'none';
			
		}
	
	}
	
	function open_div_pocomm( value ){
	
		if ( value ){
					
					document.getElementById("div_discount1").style.display = 'block';  
					document.getElementById("div_discount").style.display = 'block';
				
		} else {
				
				document.getElementById("div_discount1").style.display = 'none';  
				document.getElementById("div_discount").style.display = 'none';
		}
	
	}
	
	
	
        function validateMICRCode( input ){
		
					
			alert ( input.value  + "inside the true after clarify " );	
            if ( input.value.length >= 3 )  {

                input.value = input.value.slice(0,3);
                
                 var j = getnextFeild(input);
                
                  
                	input.form[j].focus();
	
            }
        }
        
        function onlynumbers(){
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        }
        
        function onlyDoublenumbers(){
        	
        	var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || cha == 46 ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        }
        
        
        function numbersonly( input ){

           var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
									
					 if ( input.value.length >= 2 )  {
							
								
							
							if ( input == document.getElementById("branch_code")   ){
								if (  input.value.length == 2 )
									input.value = input.value + String.fromCharCode(cha);
									
								return false ;
								
							}
							
							else {			 	
								
								input.value = input.value + String.fromCharCode(cha);
					 			var j = getnextFeild(input);
                				input.form[j].focus();
                				return false ;
                			}
					 
					}	
                    return true;
                    
              } else {

                        return false;

             }

                
            }
        
	
	function getnextFeild( input ){
		
			var index  = -1;
			var i = 0 ;
			
			
			while ( i < input.form.length  && index == -1  )   {
				
			
					if ( input.form[i] == input  ) {
							index = i + 1 ;
							return index;
							
							
							
					} else {
					 		 i++;
					  }
				
			}
			
			return index;
	}	
	
	        function checkformat( ids ,eve  ) {

                      var cha =   event.keyCode;
                      var format = true;


             var ff =  ids.value;

            var dd = ff.split( '/' );

			if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          alert (  " problem in date format " );
                          format = false ;
                          break;

                      } else if (  i==2 && dd[i].length != 4 ){

                           alert (  " problem in date format " );
                           format = false ;
                           break;
                      }

            	 }
             } else{

             		format = false ;
             		alert (  " problem in date format " );
             }


			if ( format ){

						var dt = new Date();

						dt.setYear(dd[2]);
						dt.setMonth(dd[1]);

						dd[1] = dd[1] -1;
						var days = 32 - new Date(dd[2],dd[1],32).getDate();

                        alert(days + "........................... ");

                        if ( dd[0] > days  ){

                                    alert ( "given days is more than the given month ") ;
                        }

                        var current = new Date();
                        if ( current.getYear() <= dt.getYear() ){

                            alert ( current.getYear()   +"     current  year -- given date     "+ dt.getYear()  );

                            if ( current.getMonth() <= ( dt.getMonth() )  ){


                                alert ( current.getDate() + "     current month ---- given month   "+ dt.getDate()  );
                                if ( current.getDate() <= dt.getDate() ) {

                                              alert ( " no problem ") ;

                                }  else {
                                    alert( "Posted Cheque " );
                                }

                            } else {

                                                 alert( "Posted Cheque " );
                            }

                        } else {

                            alert( "Posted Cheque " );
                        }
            }

        }	
        
        function chequeDate( ids ,eve  ) {

                      var cha =   event.keyCode;
                      var format = true;


             var ff =  ids.value;

            var dd = ff.split( '/' );

			if ( dd.length == 3){

             for ( var i =0; i< dd.length; i++ ){

                      if ( i != 2 && dd[i].length != 2 ){

                          alert (  " problem in date format " );
                          format = false ;
                          break;

                      } else if (  i==2 && dd[i].length != 4 ){

                           alert (  " problem in date format " );
                           format = false ;
                           break;
                      }

            	 }
             } else{

             		format = false ;
             		alert (  " problem in date format " );
             }


			if ( format ){

				setflag(6,ids)
				
        }
        }
        
        
        
        function getTodayDate(){
        
        
        	var date = new Date();
        	
        	var day = date.getDate();
        	var month = date.getMonth()+1; 
        	var year = date.getYear();
        	
		
		alert (" hello ") ;			

        	if ( day <= 9  ){
        		
        		day = "0"+day;
        	
        	}  
        	
        	if (  month <= 9 ){
				
				month = "0"+month;        	
        	}
        
        	
        	return day+"/"+month+"/"+year;
        }
        
        
        
       function submitform2(){
       
       		return true;
       
       }
       
       
       function callBounce(){
       		
       	    if ( document.getElementById("bounce_id").checked ) {	
       	   
       	   		 var url = 	"BounceReason.do?bounce="+document.getElementById("reason").value;   		
       			 wind=window.open( url,'Popup','WIDTH=500,HEIGHT=380,left=200,top=100,scrollbars=yes');
       		
       		}
       }

	function clearfeild(ids){
	
		document.getElementById(ids).value= "";
		
	}