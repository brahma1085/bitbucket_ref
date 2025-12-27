
<%@ page import="javax.servlet.http.*,java.sql.*,javax.servlet.*,com.eris.DBConnection" %>

     <%
	 String s=request.getParameter("t1");
              	String s2=request.getParameter("t2");
	String s3=null,s4=null;
           	String s1 = s.substring(0, 2);
		System.out.println("in jsp ste"+s+":"+s2);
	DBConnection  db= new DBConnection();
	System.out.println("in con:jsp");
	ResultSet rs=db.executeQuery("select username,password  from login where username='"+ s +"' and password='" +s2+"'");
      	if(rs.next())
	{
           	s3=rs.getString(1);
            	s4=rs.getString(2);

	   	if(s3.equals(s) && s4.equals(s2))
		{
	                 if(s1.equals("AD"))
                  	     	{
	                       response.sendRedirect("admin.htm");
	                       	}
                  	else if(s1.equals("RE"))
                     		{
		                     response.sendRedirect("recruit.htm");
                      		}
	                  else if(s1.equals("ME") )
			{
                                   		     response.sendRedirect("MARKETING.htm");
		                    }

		}

 	}
	else
    		{
 %>
     <html>
<body>

<pre>
	<p align="center"><b>Enterprise Resource Information System</b></p>

		<center>	 Invaild user or password</center>

		<a href="login.htm">  ok  </a>

</pre>

</body >
</html>
<%
		}

%>