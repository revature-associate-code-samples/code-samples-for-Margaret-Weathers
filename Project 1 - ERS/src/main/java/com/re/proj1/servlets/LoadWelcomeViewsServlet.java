package com.re.proj1.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * Front controller to load login and create account partials.
 *
 */
public class LoadWelcomeViewsServlet extends HttpServlet{
	
	private static Logger log = Logger.getLogger(LoadWelcomeViewsServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("In LoadWelcomeViewsServlet");
		log.debug(req.getRequestURI());
		String resourcePath = "partials/" + process(req, resp) + ".html";
		req.getRequestDispatcher(resourcePath).forward(req, resp);
	}

	static String process(HttpServletRequest req, HttpServletResponse resp) {
		switch(req.getRequestURI()) {
		case "/ERS/login.welcomeView":
			return "login";	
		case "/ERS/create-account.welcomeView":
			return "create-account";
		default:
			return null;
		}
			
	}
}
