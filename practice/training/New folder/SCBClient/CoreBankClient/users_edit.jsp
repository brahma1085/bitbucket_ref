<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Random"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <script type="text/javascript">
    	function radio(ids){
    		
    	};
    	function checkAll(ids){
    		alert(ids);
    		alert(document.forms[0].allbox.value);
    		alert(document.forms[0].allbox.checked);
    		if(document.forms[0].allbox.checked==true){
    			alert(document.forms[0].allbox.checked);
    			document.forms[0].first.checked=checked;
    			alert(document.forms[0].first.checked);
    			document.forms[0].last.checked=true;
    		}
    	};
    		
    	
    </script>
</head>
<body>
<%
	String method=request.getParameter("method");
	if(method==null){
		List testData=new ArrayList();
		Map map1=new TreeMap();
		map1.put("id","1");
		map1.put("firstName","Mahesh");
		map1.put("lastName","Kanchipal");
		testData.add(map1);
		
		Map map2=new TreeMap();
		map2.put("id","2");
		map2.put("firstName","Shriram");
		map2.put("lastName","Kanchipal");
		testData.add(map2);
		
		Map map3=new TreeMap();
		map3.put("id","3");
		map3.put("firstName","Sowmya");
		map3.put("lastName","Bhat");
		testData.add(map3);
	
		
		session.setAttribute("test",testData);
		
		request.setAttribute("test",testData);
		
	}else{
		List testData=(List)session.getAttribute("test");
		String useMe=request.getParameter("id");
		System.out.println("method =="+method );
		if(useMe!=null&&method.equalsIgnoreCase("Delete")){
			for(int i=0;i<testData.size();i++){
				Map m=(TreeMap)testData.get(i);
				String id=m.get("id").toString();
				if(useMe.equals(id)){
					testData.remove(m); 
				%><div class="message">Deleted!</div><% 
					break;
				}
			}
		}else if(method.equalsIgnoreCase("Save")){
			testData=(List)session.getAttribute("test");
			System.out.println(testData);
			useMe=request.getParameter("id");
			System.out.println(useMe);
			for(int i=0;i<testData.size();i++){
				
				Map m=(TreeMap)testData.get(i);
				System.out.println(m);
				String id=m.get("id").toString();
				if(useMe.equals(id)){
					m.put("firstName",request.getParameter("firstName"));
					m.put("lastName",request.getParameter("lastName"));
					testData.set(i,m);
					%><div class="message"><b><c:out value=""/></b>Updated 
					</div>
					<%
					break;
				}
			}
		}else if(method.equalsIgnoreCase("Add")){
			Map map4=new TreeMap();
			Random generator=new Random();
			String id=String.valueOf(generator.nextInt());
			map4.put("id",id);
			map4.put("firstName","");
			map4.put("lastName","");
			testData.add(map4);
			%><core:redirect url="users_edit.jsp">
				<core:param name="id" value=""></core:param>
				<core:param name="method" value="Edit"></core:param>
			</core:redirect>	
		<%
		}
		session.setAttribute("test",testData);
		request.setAttribute("test",testData);
	}
%>

<core:set var="checkAll">
	<input type="checkbox" id="allbox" name="allbox" onclick="checkAll(this.form)" style="margin:0 0 0 4px"/>
</core:set>
<form name="editForm" action="users_edit.jsp">
	<core:if test="true">
		<input type="button" onclick="location.href='users_edit.jsp'" class="button" value="Reset"/>
	</core:if>
	<core:if test="true">
		<input type="submit" onclick="location.href='?method=Save'" class="button" value="Save" name="method"/>
	</core:if>
	<input type="submit" value="Edit" name="method" class="button" onclick="location.href='?method=Edit'" />
	<input type="submit" value="Add" name="method" class="button" onclick="location.href='?method=Add'"/>
	<input type="submit" value="Delete" name="method" class="button" onclick="location.href='?method=Delete'"/>
	<br/><br/>
	<display:table name="test" id="test" class="list">
		<display:column title="${checkAll}" style="width:1%"><input type="checkbox" name="id" value="${test.id}" style="margin:0 0 0 4px" onclick="radio(this)" <core:if test="${param.id==test.id and param.method != 'Save'}">checked="checked"</core:if> /></display:column>
		<display:column style="width:3%" paramId="first" title="First Name">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==test.id }">
					<input type="text" name="firstName" style="padding:0" value="${test.firstName}"   
					
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${test.firstName}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%" paramId="last" title="Last Name">
			<core:choose>
				<core:when test="${param.method=='Edit'and param.id==test.id }">
					<input type="text" name="lastName" style="padding:0" value="${test.lastName}"/>
				</core:when>
				<core:otherwise>
					<core:out value="${test.lastName}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
	</display:table>
</form>
</body>
</html>