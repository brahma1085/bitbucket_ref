package com.eris.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eris.exceptions.ServiceException;
import com.eris.factories.ServiceFactory;
import com.eris.model.Client;
import com.eris.services.ClientService;

public class ClientServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		Client client = new Client();
		client.setClientid(Integer.parseInt(request.getParameter("clientid")));
		client.setCompanyname(request.getParameter("companyname"));
		client.setAddress(request.getParameter("address"));
		client.setTitle(request.getParameter("title"));
		client.setCity(request.getParameter("city"));
		client.setName(request.getParameter("name"));
		client.setState(request.getParameter("state"));
		client.setTelephone(request.getParameter("telephone"));
		client.setZip(request.getParameter("zip"));
		client.setExtension(request.getParameter("ext"));
		client.setCountry(request.getParameter("country"));
		client.setFax(request.getParameter("fax"));
		client.setDepartment(request.getParameter("department"));
		client.setEmail(request.getParameter("email"));
		ClientService clientService = ServiceFactory.getClientService();
		boolean flag = false;
		RequestDispatcher dispatcher = null;
		try {
			flag = clientService.insertClientDetails(client);
			if (flag) {
				dispatcher = request
						.getRequestDispatcher("/WEB-INF/administrator/success.jsp");
				dispatcher.forward(request, response);
			} else {
				dispatcher = request
						.getRequestDispatcher("/WEB-INF/administrator/clientfailure.jsp");
				dispatcher.forward(request, response);
			}
		} catch (ServiceException e) {
			System.out.println("ServiceException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			dispatcher = request.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}
	}
}
