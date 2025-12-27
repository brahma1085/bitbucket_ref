
<%@ page import="javax.servlet.http.*,java.sql.*,javax.servlet.*,com.eris.DBConnection" %>

     <%
	String s=request.getParameter("empid");
	String fr,r,m,e,cid;
	int empid=0;
	DBConnection  db= new DBConnection();
	System.out.println("in con:jsp");
	ResultSet rs=db.executeQuery("select employeeid,firstname,role,mobileno,email,clientid from employeedetails where employeeid=' "+ s +" ' ");
      	if(rs.next())
	{
	empid=rs.getInt(1);
	System.out.println(empid);
	fr=rs.getString(2);
	r=rs.getString(3);
	m=rs.getString(4);
	e=rs.getString(5);
	cid=rs.getString(6);
	}
	System.out.println(empid);
   response.sendRedirect("./empsearch1.jsp?id="+empid);
//+\&f=\"+fr+\" &ro=\"+r+\"&mo=\"+m+\" &em=\"+e+\"&ci=\"+cid+\" ");
    //response.setHeader("REFRESH","0;URL=./empsearch1.jsp?id="+empid+"&f='"+fr+"'&ro=r&mo=m&em=e&ci=cid");
%>