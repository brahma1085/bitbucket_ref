<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.TreeMap"%>
    <%@taglib  prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib  prefix="bean" uri="http://struts.apache.org/tags-bean" %>  
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
 function fun_sub(target)
 {
 
 if(target==1)
 {
 document.forms[0].submit();
 
 }
 else
 {
 	return false;
 }
 }

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
</head>
<%!int helpCall=1; %>
<%
	if(request.getAttribute("Helpcall")!=null){
		helpCall=0;
		System.out.println(helpCall);
		System.out.println("path in help jsp "+request.getParameter("path"));
		System.out.println("path in help jsp "+request.getAttribute("path"));
	}
System.out.println("path in help jsp "+request.getParameter("path"));
System.out.println("path in help jsp "+request.getAttribute("path"));

%>
<body onload="fun_sub(<%=helpCall%>)" class="Mainbody">
	<% 
	System.out.println("req par====>"+request.getAttribute("Helpcall") );
   TreeMap unverified_accounts;
	String path="/Loans/LoanDocsUpdation?help=24";

%>

<%! String date="";
		
		%>
		<% Calendar   c = Calendar.getInstance();
			date="27/02/2008";
			
		%>       


<%
 
  
  path=(String)request.getAttribute("path");
	System.out.println("path in help jsp "+path);
 System.out.println("path in help jsp "+request.getParameter("path"));
 if(request.getParameter("path")!=null)
  	path=request.getParameter("path").substring(0,request.getParameter("path").indexOf('-'))+"?help="+request.getParameter("path").substring(request.getParameter("path").indexOf('-')+1,request.getParameter("path").length())+"&sysdate="+date;
 if(request.getAttribute("path")!=null)
	 path=request.getAttribute("path").toString();
  System.out.println("path in help jsp "+path);  
  unverified_accounts=(TreeMap)request.getAttribute("unverified_accounts");
  System.out.println("unverified_accounts========>"+unverified_accounts);
%>
<%if(unverified_accounts!=null)
{
	System.out.println("value of pending tray list========>"+unverified_accounts.get("24"));
	 Vector data=(Vector)unverified_accounts.get("24");
	 Enumeration eb=data.elements();
	
	 while(eb.hasMoreElements())
	{
		System.out.println("=======value=========>");
		Vector v=(Vector)eb.nextElement();
		Enumeration obj2=v.elements();
		while(obj2.hasMoreElements())
		{
			System.out.println("=======value=========>"+obj2.nextElement());	
		}
		v=(Vector)eb.nextElement();
		obj2=v.elements();
		while(obj2.hasMoreElements())
		{
			Object obj[]=(Object[])obj2.nextElement();
			for(int i=0;i<obj.length;i++)
			{
			System.out.println("=======Help=========>"+obj[i]);
			}
		}
		
		} 
	 
}

%>
<html:form action="<%=path%>" >

	<table class="txtTable">
		<tr>
			<%
			if(unverified_accounts!=null)
			{
				System.out.println("value of pending tray list========>"+unverified_accounts.get("24"));
				 Vector data=(Vector)unverified_accounts.get("24");
				 Enumeration eb=data.elements();
				
				 while(eb.hasMoreElements())
				{
					System.out.println("=======value=========>");
					Vector v=(Vector)eb.nextElement();
					Enumeration obj2=v.elements();
					while(obj2.hasMoreElements())
					{%>
					<th class="PageHeader"><%=obj2.nextElement() %></th>
				<% 	}
					v=(Vector)eb.nextElement();
					obj2=v.elements();
					while(obj2.hasMoreElements())
					{
						Object obj[]=(Object[])obj2.nextElement();
					%>
					<tr>
					<%	for(int i=0;i<obj.length;i++)
						{
							
					%>
					<td><%=obj[i] %></td>
					<%	}
					%>
					</tr>
					<%}
				}
				}%>
		
	</table>

</html:form>
</body>   
</html> 