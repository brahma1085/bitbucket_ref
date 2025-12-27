<center>
	<h3>
		Getting request parameter values using EL implicit objects
	</h3>
	<br />
	<h4>
		n1=${param.n1}
		<br />
		n2=${param.n2 }
	</h4>
	<h3>
		Getting request parameter values using JSP implicit objects
	</h3>
	<br />
	<h3>
		n1=<%=request.getParameter("n1")%><br>
		n2=<%=request.getParameter("n2")%>
	</h3>
	<br />
	<h3>
		Getting request attributes using EL implicit objects
	</h3>
	<h4>
		<br />
		r1=${requestScope.r1}
		<br />
		r2=${requestScope.r2}
		<br />
		r3=${requestScope.r3}
	</h4>
	<br />
	<h3>
		Getting request attributes using JSP implicit objects
	</h3>
	<br />
	<h4>
		r1=<%=request.getAttribute("r1")%><br />
		r2=<%=request.getAttribute("r2")%><br />
		r3=<%=request.getAttribute("r3")%>
	</h4>
</center>