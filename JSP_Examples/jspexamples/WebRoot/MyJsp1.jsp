<jsp:useBean id="b" class="edu.Book" scope="session"></jsp:useBean>
<jsp:setProperty property="ISOno" name="b" value="123456789"/>
<jsp:getProperty property="ISOno" name="b"/>