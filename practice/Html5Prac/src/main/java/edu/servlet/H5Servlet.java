package edu.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.bean.Contact;
import edu.bean.Users;
import edu.dao.impl.H5DaoImpl;
import edu.exceptions.ServiceException;
import edu.service.H5Service;
import edu.service.impl.H5ServiceImpl;
import edu.util.H5Utils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/insertUser", "/insertContact", "/updateContact", "/updateUser", "/getAllUsers", "/getUser", "/getContactInfo",
		"/deleteContact", "/deleteUser" })
public class H5Servlet extends HttpServlet {
	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			doService(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			doService(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doService(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServiceException,
			ServletException, IOException {
		String uri = request.getRequestURI();
		String cxtPath = request.getServletContext().getContextPath();
		uri = uri.replaceAll(cxtPath, "");
		Random randomGenerator = new Random();
		List<Users> usersList = new LinkedList<Users>();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null) {
			json = br.readLine();
		}
		ObjectMapper mapper = new ObjectMapper();
		H5Service service = new H5ServiceImpl();
		service.setConnection(H5Utils.getConnection());
		service.setDao(new H5DaoImpl());
		if ("/insertUser".equals(uri)) {
			Users users = mapper.readValue(json, Users.class);
			users.setId(randomGenerator.nextInt(100));
			users.setDescription("description");
			users.setCreatedDate(new Date(new java.util.Date().getTime()));
			users.setCreatedBy("loginuser");
			service.insertUsers(users);
			response.setContentType("application/json");
			mapper.writeValue(response.getOutputStream(), usersList.add(users));
		} else if ("/insertContact".equals(uri)) {
			Contact contact = new Contact();
			contact.setId(randomGenerator.nextInt(100));
			contact.setName(request.getParameter("name"));
			contact.setDesignation(request.getParameter("designation"));
			contact.setInfo(request.getParameter("info"));
			contact.setDescription(request.getParameter("description"));
			contact.setCreatedDate(new Date(new java.util.Date().getTime()));
			contact.setCreatedBy(request.getParameter("loginuser"));
			service.insertContact(contact);
			request.getRequestDispatcher("/views/index1.html").forward(request, response);
		} else if ("/updateContact".equals(uri)) {
			Contact contact = new Contact();
			contact.setId(Integer.parseInt(request.getParameter("id")));
			contact.setName(request.getParameter("name"));
			contact.setDesignation(request.getParameter("designation"));
			contact.setInfo(request.getParameter("info"));
			service.updateContact(contact);
			request.getRequestDispatcher("/views/index1.html").forward(request, response);
		} else if ("/updateUser".equals(uri)) {
			Users users = new Users();
			users.setId(Integer.parseInt(request.getParameter("id")));
			users.setName(request.getParameter("name"));
			users.setPassword(request.getParameter("password"));
			service.updateUsers(users);
			request.getRequestDispatcher("/views/index1.html").forward(request, response);
		} else if ("/getAllUsers".equals(uri)) {
			service.getUsers();
			request.getRequestDispatcher("/views/index1.html").forward(request, response);
		} else if ("/deleteContact".equals(uri)) {
			service.deleteContact(Integer.parseInt(request.getParameter("id")));
			request.getRequestDispatcher("/views/index1.html").forward(request, response);
		} else if ("/deleteUser".equals(uri)) {
			service.deleteUsers(Integer.parseInt(request.getParameter("id")));
			request.getRequestDispatcher("/views/index1.html").forward(request, response);
		} else if ("/getUser".equals(uri)) {
			service.getUser(Integer.parseInt(request.getParameter("id")));
			request.getRequestDispatcher("/views/index1.html").forward(request, response);
		} else if ("/getContactInfo".equals(uri)) {
			service.getContacts();
			request.getRequestDispatcher("/views/index1.html").forward(request, response);
		}
	}

}
