package com.re.proj1.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * Front controller to employee-view partials.
 *
 */
public class LoadEmployeeViewsServlet extends HttpServlet{
private static Logger log = Logger.getLogger(LoadEmployeeViewsServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String resourcePath = "partials/" + process(req, resp) + ".html";
		log.debug(resourcePath);
		req.getRequestDispatcher(resourcePath).forward(req, resp);
	}
	
	static String process(HttpServletRequest req, HttpServletResponse resp) {
		log.trace("In LoadEmployeeViews Servlet process()");
		switch(req.getRequestURI()) {
		case "/ERS/front.employeeView":
			return "employee-front";
		case "/ERS/all.employeeView":
			return "employee-all";
		case "/ERS/past.employeeView":
			return "employee-past";
		case "/ERS/submit.employeeView":
			return "employee-submit";
		case "/ERS/request-submitted.employeeView":
			return "employee-request-submitted";
		}
			
		return null;
	}
	

}

