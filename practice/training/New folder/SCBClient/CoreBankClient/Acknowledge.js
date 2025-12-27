
function getClearingType()
{
document.forms[0].flag.value="1";
document.forms[0].submit();

}
function clearAll()
{
	document.forms[0].ackNumber.value=0;
	document.forms[0].amount.value="";

}

function onlynumbers()
 {
       var cha =   event.keyCode;
		if ( cha >= 48 && cha <= 57  )
        {
           return true;
        } 
        else 
        {
        	return false;
        }
			   		 
 }   



function getMessages()
{

	if(document.forms[0].validateFlag.value!="")
	{
		alert(document.forms[0].validateFlag.value);
	}
	if(document.forms[0].errorFlag.value=='1')
	{
		clearAll();
	}

}
function set(target)
{
   if(document.forms[0].amount.value=="")
   {
       alert("Enter amount");
       return false;
   }
   else if(document.forms[0].ackNumber.value=="")
   {
   		alert("Enter Valid Ack Num");
   }
   else
   {
      
      if(target=='submit')
      {
      		if(document.forms[0].ackNumber.value==0)
      		{
        		document.forms[0].flag.value=target;
        		document.forms[0].booleanFlag.value=0;
        		document.forms[0].submit();
        	}
        	else
        	{
        		alert("You Can't Submit Values");
        	}	
      }
      else if(target=='update')
      { 
      		if(document.forms[0].ackNumber.value!=0)
      		{
      			document.forms[0].flag.value=target;
        		document.forms[0].booleanFlag.value=0;
        		document.forms[0].submit();
      		}
      		else
      		{
      			alert("You Can't Update Values");
      		}
      	
      }
   }
           
};

function setFlag(flagVal)
{
	
	if(document.forms[0].ackNumber.value!=0)
	{
		document.forms[0].flag.value=flagVal;
		document.forms[0].booleanFlag.value=0;
		document.forms[0].submit();
		clearAll();
	}

}
 function checkformat(ids) 
{
            
                      var cha =   event.keyCode;
                      var format = true;
                      var allow=true;

            	 var ff =  ids.value;
            	 var dd = ff.split( '/' );

				if(dd.length == 3)
				{
					for(var i =0; i< dd.length; i++ )
					{
						if(i != 2 && dd[i].length != 2 )
						{

                            alert(" problem in date format " );
                          	format = false ;
                          	allow=false;
                        } 
                        else if(i==2 && dd[i].length != 4 )
                        {
							alert(" problem in date format " );
                            format = false ;
                            allow=false;
                        }

            	 	}
                 } 
                 else
                 {
                    allow=false;
             		format = false ;
             		alert (  " problem in date format " );
             	 }
			if(format)
			{
        		var date=new Date();
         		var dayCheck=dd[0];
         
         		var monthCheck=dd[1];
         		var yrCheck=dd[2];
         		var mth=dd[1];
         
         		dd[1]=dd[1]-1;
	   			var days = 32 - new Date(dd[2],dd[1],32).getDate();
         		if(dd[0].length==2)
         		{
          			if(dd[0] > days)
                    {
						alert("Day should not be greater than "+days);
                        allow=false;
                              
                    }
                    if(dd[0]==00)
                    {
         				 alert("There is no date with 00");
          				 allow=false;
          			}
          			if(mth<1 || mth>12)
          			{
          				alert("Month should be greater than 0 and \n lessthan 13  "+mth);
          				allow=false;
          			}
          		}
     			
     			if(dd[2].length==4)
     			{
          			if((parseInt(dd[2])<parseInt(date.getYear())))
          			{
          				alert("Year should be "+date.getYear());
          				allow=false;
          			}
         			
          		}
          }
		if(allow)
		{
		
		  document.forms[0].submit();
		  return true;
		}
		else
		{
			document.forms[0].ackDate.focus();
			return false;
		}

}	
 function onlyDoublenumbers()
 {
        	
        	var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || cha == 46 ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        }