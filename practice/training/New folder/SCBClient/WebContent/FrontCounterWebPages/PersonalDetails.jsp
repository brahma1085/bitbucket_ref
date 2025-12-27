<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="masterObject.general.ModuleObject" %>
     <%@page import="masterObject.frontCounter.ODCCMasterObject"%>
     <%@ page import="masterObject.general.AccountObject" %>
     <%@ page import="masterObject.general.AccSubCategoryObject" %>
     <%@ page import="masterObject.general.AccCategoryObject" %>
     <%@page import="java.text.DateFormat"%>
      <%@page import="java.util.*" %>
     <%@ page import="masterObject.general.Address" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page language="java" session="true" 
import= "
java.awt.image.BufferedImage,java.io.ByteArrayOutputStream,java.io.InputStream,java.sql.Blob,java.sql.Connection,
java.sql.ResultSet,javax.swing.ImageIcon,java.sql.*,java.io.*" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Details</title>
</head>

<body>
<%!Address addr;
Date now,now1;
DateFormat fmt,fmt1;
String strDate,strTime;
AccSubCategoryObject[] accsub;
AccCategoryObject[] acccat;
%>
<%
accsub=(AccSubCategoryObject[])request.getAttribute("accsubcat");
acccat=(AccCategoryObject[])request.getAttribute("acccat");

AccountObject master=(AccountObject)request.getAttribute("master");
ODCCMasterObject odccmaster=(ODCCMasterObject)request.getAttribute("odccmaster");
CustomerMasterObject cust=(CustomerMasterObject)request.getAttribute("cust");
String joint=(String)request.getAttribute("jointholder");
FileOutputStream fos;
String Nominee=(String)request.getAttribute("Nominee");
String Introducer=(String)request.getAttribute("Introducer");
System.out.println("master is in JSP-------PersonalDetails.jsp----"+cust);
now=new Date();
now1=new Date();
fmt=DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.UK);
fmt1=DateFormat.getTimeInstance(DateFormat.MEDIUM,Locale.UK);
strDate=fmt.format(now);
strTime=fmt1.format(now1);
System.out.println("====date==="+now);
System.out.println("====date==="+now.getDate());
//System.out.println("====date==="+months[now.getMonth()]);
System.out.println("====date==="+now.getYear());
Calendar cal=Calendar.getInstance();
String msg=(String)request.getAttribute("msgs");

%>
<html:form action="/FrontCounter/PersonalDetails?pageId=3032">
<core:if test="<%=joint==null %>">
<core:if test="<%=Nominee==null%>">
<core:if test="<%=Introducer==null %>">
<font color="blue" ><center>
<h2 class="h2">Personal Details</h2>
</core:if>
</core:if>
</core:if>

<core:if test="<%=joint!=null %>">
<core:if test="${cust==null}">
<center><font color="red">No JointHolder's</font></center>
</core:if>
</core:if>
<core:if test="<%=Introducer!=null %>">
<core:if test="${cust==null}">
<center><font color="red">No Introducer</font></center>
</core:if>
</core:if>
<core:if test="<%=Nominee!=null %>">
<core:if test="${cust==null}">
<center><font color="red">No Nominee Details</font></center>
</core:if>
</core:if>

<core:if test="${cust!=null}">
<table border="1"  width="412" height="263" style="border:thin solid #000000;" class="txtTable" bordercolor="black">
	<tr>
		<td colspan="3" align="center">
		<core:if test="<%=joint==null %>">
		<core:if test="<%=Nominee==null %>">
		<core:if test="<%=Introducer==null %>">
		<b>
		<font>Personal Details</font></b>
		</core:if>
		</core:if>
		</core:if>
		<core:if test="<%=joint!=null %>">
		<b>
		<font>JointHolder's Details</font></b>
		</core:if>
		<core:if test="<%=Nominee!=null %>">
		<b>
		<font>Nominee Details</font></b>
		</core:if>
		<core:if test="<%=Introducer!=null %>">
		<b>
		<font>Introducer Details</font></b>
		</core:if>
		</td>
	</tr>
	<tr>
		<td width="84">Customer ID: </td>
		<td width="142"><%=String.valueOf(cust.getCustomerID())%></td>
		<td width="162" rowspan="2" valign="top">
<%
try
		{
	String s1=String.valueOf(cust.getCustomerID());
	String n1="C:/jboss-4.0.0/Photos/p"+s1+".jpg" ;
			System.out.println("to print photo");
			if(cust.getBinaryImage()!=null){
				byte[] b=cust.getBinaryImage();
				System.out.println("to print photo----------128--------");
				fos=new FileOutputStream("../Photos/p"+s1+".jpg");
					fos.write(b);
					fos.close();
				%>
				
				<img src="<%=n1%>"  height="80" width="90">
			<%}
		}
		catch (Exception exc)
		{ System.out.println(exc);
		}
%>
</td>
	</tr>
	<tr>
		<td width="84">Name: </td>
		<td width="142"><%=String.valueOf(cust.getName())%></td>
	</tr>
	<tr>
		<td width="84">Category:
	<%	if(acccat!=null) {
	for(int i=0;i<acccat.length;i++){
		if(acccat[i].getCategoryCode()==cust.getCategory()){
	%>
	<%= acccat[i].getCategoryDesc()%>
	<%}
		else{
			break;
		}
	} }%>
		</td>
		<td width="142">Sub-Category:<%=cust.getSubCategory() %>
		<%	if(accsub!=null) {
	for(int j=0;j<accsub.length;j++){
		System.out.println("accsub----->>>"+accsub[j].getSubCategoryCode());
		if(accsub[j].getSubCategoryCode()==cust.getSubCategory()){
	%>
	<%= accsub[j].getSubCategoryDesc()%>
	<%}
		else{
			break;
		}
	} }%>
		
		
		
		</td>
	</tr>
	<tr>
		<td width="84">&nbsp;</td>
		<td width="142">SC/ST:
		<%if(cust.getScSt()!=null){%>
		<%=cust.getScSt()%>
		<%}else{ %>
		<%="Other"%>
		<%} %>
		</td>
		<td width="162" rowspan="3" valign="top">
		  <div id="pocon" style="display:block;overflow:scroll;width:150px;height:120px">
<font color="navy" size="3"> Address:</font>
		<br>
		<% 
		if((Address)cust.getAddress().get(1)!=null){
		addr=(Address)cust.getAddress().get(1);
		}
		else if((Address)cust.getAddress().get(2)!=null){
		addr=(Address)cust.getAddress().get(2);
		}
		else if((Address)cust.getAddress().get(3)!=null){
			addr=(Address)cust.getAddress().get(3);
			}
		
		%>
		<core:if test="<%=addr!=null %>">
		<core:if test="<%=addr.getAddress()!=null %>">
		<% if(addr.getAddress().length()>20)
					{	%>
						<%=addr.getAddress().subSequence(0,20) %><br>
						<%=addr.getAddress().substring(20)%><br>
					<%}
					else {%>
						<%= addr.getAddress()%><br>
							
						<%} %>
						<core:if test="<%=addr.getCity()!=null %>">	
					<%=addr.getCity() %>				
						</core:if>
						<br>
					<core:if test="<%=addr.getState()!=null %>">	
					<%=addr.getState() %><br>
					</core:if>
					<core:if test="<%=addr.getCountry()!=null %>">	
					<%=addr.getCountry() %><br>
					</core:if>
					
					<core:if test="<%=addr.getPin()!=null %>">	
					<%=addr.getPin() %><br>
					</core:if>
					<core:if test="<%=addr.getPhno()!=null %>">	
					<%if(addr.getPhno().length()>0)
						%>
						<%="Ph No: "+addr.getPhStd()+"-"+addr.getPhno() %><br>
					
					</core:if>
					<core:if test="<%=addr.getFax()!=null %>">	
					<%if(addr.getFax().length()>0)
						%>
						<%="Fax No: "+addr.getFaxStd()+"-"+addr.getFax() %><br>
					
					</core:if>
					<core:if test="<%=addr.getMobile()!=null %>">	
					<%if(addr.getMobile().length()>0)
						%>
						<%="Mobile No: "+addr.getMobile() %><br>
					
					</core:if>
						
					<core:if test="<%=addr.getEmail()!=null %>">	
					<%if(addr.getEmail().length()>0)
						%>
						<%="Email: "+addr.getEmail() %><br>
					
					</core:if>

					
		
		</core:if>
		</core:if>
		</div>

</td>
	</tr>
	<tr>
		<td width="84">DOB:
		<core:if test="<%=cust.getDOB()!=null %>">
		<%=cust.getDOB()%>
		</core:if>
		</td>
		<td width="142">Sex:
		<core:if test="<%=cust.getSex()!=null %>">
		<%=cust.getSex()%>
		</core:if>
		</td>
	</tr>
	<tr>
		<td width="84">Age:
		<core:if test="<%=cust.getDOB()!=null %>">
		<%
		
		StringTokenizer d=new StringTokenizer(cust.getDOB(),"/");
		d.nextToken(); d.nextToken();
		int yy=Integer.parseInt(d.nextToken()); 
		int age= cal.get(Calendar.YEAR)-yy;
		System.out.println("Age is---------"+age);
		%>
		<%=age+"  years"%>
		</core:if>
		</td>
		<td width="142">Occupation:
		<core:if test="<%=cust.getOccupation()!=null %>">
		<%=cust.getOccupation()%>
		</core:if>
		</td>
	</tr>
	</table>
	
	
	
	

</core:if>
</html:form>
</body>
</html>