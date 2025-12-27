package shop.buy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet2 extends HttpServlet {

	private static final long serialVersionUID = -2454248377193489800L;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String uid = req.getParameter("uid");
		Cookie a = req.getValue();
		String htm = "<center><h1><u>hi,"
				+ uid
				+ " welcome to wings sales and services</u></h1><br/><form action=./s2 method=post><h2><u>select hard disk</u></h2><br><h3><input type=radio name=hd value=320 />320GB<br/><input type=radio name=hd value=512 />512GB<br/><input type=radio name=hd value=1024 />1024GB<br/></h3><h2><u>select RAM</u></h2><br><h3><input type=radio name=ram value=2 />2GB<br/><input type=radio name=ram value=3 />3GB<br/><input type=radio name=ram value=4 />4GB<br/><input type=submit value=next /><br/></h3></form></center>";
		res.getWriter().println(htm);
	}
}
