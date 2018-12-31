package com.re.proj1.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * Front controller to manager-view partials.
 *
 */
@WebServlet("/manager")
public class LoadManagerViewsServlet extends HttpServlet{
	
	private static Logger log = Logger.getLogger(LoadManagerViewsServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String resourcePath = "partials/" + process(req, resp) + ".html";
		req.getRequestDispatcher(resourcePath).forward(req, resp);
	}
	
	static String process(HttpServletRequest req, HttpServletResponse resp) {
		switch(req.getRequestURI()) {
		case "/ERS/front.managerView":
			return "manager-front";
		case "/ERS/past.managerView":
			return "manager-past";
		case "/ERS/resolved.managerView":
			return "manager-resolved";
		case "/ERS/employees.managerView":
			return "manager-employees";
		case "/ERS/pendingUsers.managerView":
			return "manager-pending-users";
		}			
		return null;
	}
	

}
