package com.httpeg;

import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpTest111 extends HttpServlet{
	public void init(ServletConfig cfg) {
		System.out.println("init executed");
		try {
			super.init(cfg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void service(ServletRequest req, ServletResponse res) {
		System.out.println("overriding service executed");
		try {
			super.service(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void service(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("overloading service executed");
		try {
			super.service(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("do get executed");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("do post executed");
	}

	public void destroy() {
		System.out.println("destroy executed");
	}
}
