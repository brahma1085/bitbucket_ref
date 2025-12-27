package org.commons;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.processors.RequestProcessor;

public class CommonServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map strutsMap = new HashMap();
	private String config = "mvcprops.properties";

	@Override
	public void init() throws ServletException {
		Properties strutsProperties = new Properties();
		String initConfig = null;
		try {
			initConfig = getServletContext().getInitParameter("config");
			if (initConfig != null) {
				config = initConfig;
			}
			strutsProperties.load(CommonServlet.class.getClassLoader()
					.getResourceAsStream(config));
			strutsMap.putAll(strutsProperties);
			getServletContext().setAttribute("strutsMap", strutsMap);
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) {
		RequestProcessor processor = getRequestProcessor(req);
		processor.process(req, resp);
	}

	protected synchronized RequestProcessor getRequestProcessor(
			HttpServletRequest req) {
		RequestProcessor processor = (RequestProcessor) getServletContext()
				.getAttribute("RequestProcessor");
		if (processor != null) {
			return processor;
		} else {
			processor = new RequestProcessor();
			processor.init(this);
			getServletContext().setAttribute("RequestProcessor", processor);
			return processor;
		}
	}

}
