<jsp:useBean id="l" scope="session" class="edu.Login"></jsp:useBean>
<jsp:setProperty property="luid" name="l" value="anand" param="" />
your username=<jsp:getProperty property="luid" name="l" /><br />
your password=<jsp:getProperty property="pwd" name="l" />
<jsp:forward page=""></jsp:forward>
<jsp:include page="add.jsp" flush="false"></jsp:include>
<jsp:param value="" name="" />