package org.processors;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.actions.Action;
import org.commons.CommonServlet;
import org.forms.ActionForm;
import org.utils.RequestUtil;

import com.sun.org.apache.commons.beanutils.BeanUtils;

public class RequestProcessor {
	protected CommonServlet servlet;
	protected HashMap actions = new HashMap();

	public void process(HttpServletRequest req, HttpServletResponse resp) {
		String path = processPath(req);
		String servPathName = path.substring(1, path.lastIndexOf(".do"));
		Map strutsMap = (Map) servlet.getServletContext().getAttribute(
				"strutsMap");
		String formName = (String) strutsMap.get(servPathName + ".form");
		String actionName = (String) strutsMap.get(servPathName + ".action");
		String inputPage = (String) strutsMap.get(servPathName + ".input");
		String scope = (String) strutsMap.get(servPathName + ".scope");
		String attribute = (String) strutsMap.get(servPathName + ".attribute");
		try {
			ActionForm actionForm = processActionForm(formName);
			processPopulate(req, actionForm);
			if (actionForm.validate()) {
				RequestDispatcher dispatcher = req
						.getRequestDispatcher(inputPage);
				dispatcher.forward(req, resp);
			} else {
				if ("request".equals(scope)) {
					req.setAttribute(attribute, scope);
				} else if ("session".equals(scope)) {
					req.getSession().setAttribute(attribute, actionForm);
				}
				Action action = processActionCreate(actionName);

				String forward = processActionPerform(action, actionForm);
				String forwardPage = (String) strutsMap.get(servPathName + "."
						+ forward);
				doForward(req, resp, forwardPage);
			}
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}

	private void doForward(HttpServletRequest req, HttpServletResponse resp,
			String forwardPage) throws Exception {
		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPage);
		dispatcher.forward(req, resp);
	}

	private String processActionPerform(Action action, ActionForm actionForm) {
		String forwardName = action.execute(actionForm);
		return forwardName;
	}

	private Action processActionCreate(String actionName) {
		Action actionInstance = null;
		synchronized (actions) {
			actionInstance = (Action) actions.get(actionName);
			if (actionInstance != null) {
				return (actionInstance);
			}
			try {
				actionInstance = (Action) RequestUtil.createObject(actionName);
			} catch (Exception e) {
				System.out.println("Exception==>" + e.getClass().getName()
						+ "==>" + e.getMessage());
			}
			actions.put(actionName, actionInstance);
		}
		return actionInstance;
	}

	private void processPopulate(HttpServletRequest req, ActionForm form)
			throws IllegalAccessException, InvocationTargetException {
		BeanUtils.populate(form, req.getParameterMap());
	}

	private ActionForm processActionForm(String formName) throws Exception {
		ActionForm actionForm = (ActionForm) RequestUtil.createObject(formName);
		return actionForm;
	}

	private String processPath(HttpServletRequest req) {
		return req.getServletPath();
	}

	public void init(CommonServlet commonServlet) {
		servlet = commonServlet;
	}

}
