package com.re.proj1.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{
	
	Logger log = Logger.getLogger(LogoutServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("In logout servlet");
		// Returns current session. Does not create new session if one doesn't exist.
		HttpSession session = req.getSession(false);		
		if (session != null) {
			session.invalidate();
			resp.sendRedirect("index.html");
		}
	
	}

}
