package shop.buy;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 7334039383678464060L;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String uid = req.getParameter("uid");
		Cookie c = new Cookie("uid", uid);
		res.addCookie(c);
		String htm = "";
		htm += "<body><center><font color=blue><h1><u>welcome to wings sales and services.hi,"
				+ uid + ".</u></h1></font><br/>";
		htm += "<form action=./s2 method=post><input type=hidden name=uid value="
				+ uid + "><input type=submit value=hd&ram></form><br/>";
		htm += "<form action=./s3 method=post><input type=hidden name=uid value="
				+ uid + "><input type=submit value=processor></form><br/>";
		htm += "<form action=./s4 method=post><input type=hidden name=uid value="
				+ uid
				+ "><input type=submit value=payment></form></center></body>";
		PrintWriter pw = res.getWriter();
		pw.println(htm);
		pw.flush();
		pw.close();
	}
}
