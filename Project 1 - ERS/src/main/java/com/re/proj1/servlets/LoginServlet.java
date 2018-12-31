package com.re.proj1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.re.proj1.pojos.User;
import com.re.proj1.service.UserService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	static UserService us = new UserService();
	private static Logger log = Logger.getLogger(LoginServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Functionality to go back to welcome page
		req.getRequestDispatcher("index.html").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Login functionality
		String email = req.getParameter("inputEmail");
		String password = req.getParameter("inputPassword");
		log.trace(email);
		log.trace(password);
		
		// Validate user
		User user = us.getUserByEmailPassword(email, password);
		log.trace(user);
		// Go back to login page if wrong email/pw combo OR if user is not approved
		if (user == null || user.getApproved() == 0) {			
			req.getRequestDispatcher("index.html").forward(req, resp);
		}
		else {
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			log.trace("ADDING USER TO SESSION: " + session.getId());		
			// Employee login
			if (user.getRoll() == 1) {
				resp.sendRedirect("load-employee-home");
			}
			// Manager login
			else if (user.getRoll() == 2) {
				resp.sendRedirect("load-manager-home");
			}
		}
	}

}
