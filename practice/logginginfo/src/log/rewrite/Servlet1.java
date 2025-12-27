package log.rewrite;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet1 extends HttpServlet {

	private static final long serialVersionUID = 5436724181169355753L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		String user = req.getParameter("uid");
		String color = req.getParameter("color");
		String htm = "";
		htm += "<body bgcolor=" + color
				+ "><center><font color=blue><h1><u>welcome to zmail.hi,"
				+ user + "@zmail.in</u></h1></font><br/>";
		htm += "<form action=./s2><input type=hidden name=uid value=" + user
				+ "><input type=hidden name=color value=" + color
				+ "><input type=submit value=inbox/></form><br/>";
		htm += "<form action=./s3><input type=hidden name=uid value=" + user
				+ "><input type=hidden name=color value=" + color
				+ "><input type=submit value=compose/></form><br/>";
		htm += "<form action=./s4><input type=hidden name=uid value=" + user
				+ "><input type=hidden name=color value=" + color
				+ "><input type=submit value=trash/></form></center></body>";
		PrintWriter pw = null;
		try {
			pw = res.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.println(htm);
		pw.flush();
		pw.close();
	}
}
