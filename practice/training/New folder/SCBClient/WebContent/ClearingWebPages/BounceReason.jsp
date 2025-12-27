<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>




<%@page import="java.util.StringTokenizer"%>
<html>

<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
		
	
			<%     
						
		        int size = 0;
			    StringTokenizer tok = new StringTokenizer(((String) request.getAttribute("bounce")),",");
				
			    size = tok.countTokens();
				
			%>
	
			var bounce_array = new Array(<%=size%>);
			
			<% 
			    int i = 0;
			    while(  tok.hasMoreTokens() ){%> 
			    	
			    	bounce_array[<%=i%>] = <%=tok.nextToken()%>
			    
			    	<% i++; %>
			   <% }
			  
			%>
			
			
			function checkbounce(){
	
				var frmElement = document.forms[0].elements;
				
				
				for( j =0; j< bounce_array.length; j++ ){
						
						
						for ( i=0;i<frmElement.length;i++   ){
							
							
							
							if ( frmElement[i].name == bounce_array[j]  ){
									
									frmElement[i].checked = true;
									break;
							}
						
					}
						
				}		
				
			}	
		
			function closeWindow() {
			
			
			var frmElement = document.forms[0].elements;
			var count = ",";
				for(i=0;i<frmElement.length;i++)
				{

					if( frmElement[i].type == "checkbox" && frmElement[i].checked )
					{
	 						
	 						count = count+frmElement[i].name +","; 
					}
				}

			alert(count);
				
			window.opener.document.getElementById("reason").value = count;
			window.close();
			 
	}

			
	

</script>


</head>
<body  bgcolor="lightblue" onload="checkbounce()">
			
			
	
	<html:form action="/Clearing/BounceReason"  styleId="form0">
	
		
		<table>
	
	 
	<core:forEach var="reason"  varStatus="count" items="${requestScope.Reason}" >
	
	<tr>
		<td>								
				
			 <core:out value="${reason.int_reason_code}"></core:out>
	
		</td>
		
		<td>
					<core:out value="${reason.string_res_desc}"></core:out>
		</td>	
		
		<td>
		
				<core:out value="${reason.double_fine}"></core:out>
		
		</td>
		
		<td>
					<input type="checkbox" name = "<core:out value="${reason.int_reason_code}"></core:out>" >
				
		</td>
	</tr>
	
	</core:forEach>
	
	
	</table>
		
		
	
	<html:button property="ctrl_no" onclick="closeWindow()"></html:button>
	
	</html:form>	
</body >
</html>