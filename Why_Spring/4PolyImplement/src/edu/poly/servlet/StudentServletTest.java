package edu.poly.servlet;

/*
 * COPYRIGHT NOTICE 
 * Copyright@2010 by Varma. All rights reserved.
 */

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//Servlet / GenericServlet - A
// StudentServlet - B
/*
 *
 * @author Varma 
 *
 */
class StudentServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;

	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		System.out.println(".StudentServlet.service()");
	}
}

// web.xml
// <servlet>
// <servlet-name>StudentServlet</servlet-name>
// <servlet-class>edu.servlet.StudentServlet</servlet-class>
// </servlet>
// <servlet-mapping>
// <servlet-name>StudentServlet</servlet-name>
// <url-pattern>/StudentServlet</url-pattern>
// </servlet-mapping>

public class StudentServletTest {
	static {
		// Read the web.xml
		// Convert web.xml Info to Java Objects
		// Map servletMappingMap = new HashMap();
		// put(url-pattern, servlet-name);
		// servletMappingMap.put(StudentServlet, StudentServlet);
		// Map servletMap = new HashMap();
		// put(servlet-name, servlet-class);
		// servletMap.put(StudentServlet, StudentServlet);

	}

	public static void main(String[] args) {
		// Container
		// If the request is http://localhost://StudentServlet
		// String urlPattern = request.getPathXYZ();
		// String servletName = servletMappingMap.get(urlPattern);
		// String servletClass = servletMap.get(servletName);
		// servlet - points To -> StudentServlet
		// A a = new B();
		// Servlet servlet = (Serlvet)
		// Class.forName(servletClass).newInstance();
		// a.x();
		// servlet.service(req, res);

	}
}
