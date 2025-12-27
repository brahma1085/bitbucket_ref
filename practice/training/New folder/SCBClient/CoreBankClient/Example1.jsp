<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>  
<%@ page language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<script type="text/javascript">
var name="";
function hello()
{
name=prompt('Enter Your Name:','Name');
alert('Greetings'+name+',Welcome to my page!');



document.URL='http://localhost:8080/SCBClient/TdNominee.jsp';


}

 function getTable()
  {
  
  	var url = "http://localhost:8080/SCBClient/TdNominee.jsp";   
  	 		
  	window.open( url,'popup','WIDTH=500,HEIGHT=380,left=200,top=100,scrollbars=yes');
  
  }


function goodbye()
{
alert('Goodbye'+name+',Sorry to see you go!');

}

</script>
<body>

<html:button property="but"  onclick="getTable()" ></html:button>

</body>

</html>