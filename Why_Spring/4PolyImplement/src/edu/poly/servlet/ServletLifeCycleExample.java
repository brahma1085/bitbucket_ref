package edu.poly.servlet;

interface Servlet {
	public abstract void service(StringBuffer req);
}

abstract class GenericServlet implements Servlet {
	public abstract void service(StringBuffer req);
}

class HttpServlet extends GenericServlet {
	public void service(StringBuffer req) {
		String httpReq = (String) req.toString();
		System.out.println("HttpServlet==>public service()");
		service(httpReq);
	}

	protected void service(String httpReq) {
		System.out.println("HttpServlet==>protected service()");
		doGet(httpReq);
	}

	public void doGet(String httpReq) {
		System.out.println("HttpServlet==>doGet()");
	}
}

class EmployeeServlet extends HttpServlet {
	public void doGet(String httpReq) {
		System.out.println("EmployeeServlet==>doGet()");
	}

	public void service(StringBuffer req) {
		System.out.println("EmployeeServlet==>public service()");
		super.service(req);
	}
}

public class ServletLifeCycleExample {

	public static void main(String[] args) {
		try {
			Servlet servlet = (Servlet) Class.forName(
					"edu.poly.servlet.EmployeeServlet").newInstance();
			servlet.service(new StringBuffer());
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}
}
