<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<html>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<head>  
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
    <h3><center>Log File Of Customer</center></h3>
<script type="text/javascript">
   function Only_Numbers()
  {
  	var cha =   event.keyCode;
	
 	if ( (cha >= 48 && cha <= 57 ) ) 
 	{
		
   		return true;
   	} 
   	else 
   	{
		return false;
   	}
  };



</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title> 
</head>                
<body bgcolor="beige"> 
<html:form action="/Customer/CustomerViewLog?pageId=1006">  


      <%! String[] custcoloum; %>
      <% custcoloum=(String[])request.getAttribute("viewlogcoloum");%>
      <% System.out.println("coloum value in jsp page================="+custcoloum); %>
      
      <%! Object[][] custrow; %>
      <% custrow=(Object[][])request.getAttribute("Viewlogrow");%>
      <% System.out.println("row value in jsp page================="+custrow); %>
      
      <%! String[] addrcoloum; %>
      <% addrcoloum=(String[])request.getAttribute("addresslogcoloum");%>
      <% System.out.println("addrcoloum value in jsp page================="+addrcoloum);%>
      
      <%! Object[][] addrrow; %>
      <% addrrow=(Object[][])request.getAttribute("addresslogrow");%>
      <% System.out.println("row value in jsp page================="+addrrow); %>
      
      <%! Integer cid; %> 
      <% cid=(Integer)request.getAttribute("cid");%>
      <% System.out.println("cid======================"+cid); %>
      <% int cid=Integer.parseInt(request.getAttribute("cid").toString());%>
      <table> 
                     
     </table>
      
     <table>
      <tr>
         <td><bean:message key="label.cust"></bean:message>
         <%if(cid==0){%>
         <html:text property="custid" onchange="submit()" size="8" value="<%=""+cid%>" onkeypress="return Only_Numbers()">
         </html:text>
         <%}else{ %>
         <html:text property="custid" onchange="submit()" size="8"  onkeypress="return Only_Numbers()">
         </html:text>
         <%} %>
         </td>
      </tr> 
      
      <tr>    
      <tr></tr>
      <tr></tr>
      </table>
      <table> 
      <tr>
          <td><b><FONT style="color: black;font-family:serif"><bean:message key="label.CustomerMaster"></bean:message></FONT></b></td>      
      </tr>
      </table>
      
      
      <table>
      		<tr>
      		   <td>
      		      <div style="overflow: scroll;width: 750px;height: 150px">
      
      <table  border="1"  >
      			<tr><%if((custcoloum!=null)&& (custrow!=null)){%> 
            	<%for(int i=0;i<custcoloum.length;i++){ %>
           		<td ><%=custcoloum[i].toUpperCase()%></td>
           		<%} %>
     			</tr>      
          		<%for(int j=0;j<custrow.length;j++){ %>
          		<tr>
          		<% for(int k=0;k<custcoloum.length;k++){ %>
          		<td width="100">
          		<%if(custrow[j][k]!=null){ %>
          		<%=custrow[j][k]%>
          		<%} %>
          		</td>
           		<%} %>
           		</tr>
          		<%}%>
          		<%}%>
          		
          
  
            
</table>

</div></td></tr></table>
      
        
 <table> 
      <tr>
           <td><b><FONT style="color: black;font-family:serif"><bean:message key="label.CustomerAddress"></bean:message></FONT></b></td>
                
      </tr>
      </table>
       <table>
      		<tr>
      		   <td>
      		      <div style="overflow: scroll;width: 750px;height: 150px">
      
      
       <table  border="1"    border="1"  >
      <tr><%if((addrcoloum!=null)&& (addrrow!=null)){%>
            <%for(int i=0;i<addrcoloum.length;i++){ %>
           <td ><%=addrcoloum[i].toUpperCase()%></td>
           <%} %>
     </tr>      
          <%for(int j=0;j<addrrow.length;j++){ %>
          <tr>
          <% for(int k=0;k<addrcoloum.length;k++){ %>
          <%System.out.println("addrrow====> "+addrrow.length); %>
          <%System.out.println("addrrow-----< "+addrrow[j]); %>
          <td>
          <% if(addrrow[j][k]!=null){%>
          <%=addrrow[j][k]%>
          <%} %>
          </td>
           <%} %>
           </tr>
          <%}%>
          <%}%>  
    </table>
</div></td></tr></table>


</html:form>
</body>
</html>