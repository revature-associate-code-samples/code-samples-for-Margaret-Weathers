package com.re.proj1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.re.proj1.pojos.User;
import com.re.proj1.service.UserService;


@WebServlet("/user-servlet")
public class GetUserServlet extends HttpServlet{
	
	private static Logger log = Logger.getLogger(GetUserServlet.class);
	private static UserService us = new UserService();

	// For login
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		log.trace("IN GET USER SERVLET. SESSION: " + session.getId());		
		User user = (User) session.getAttribute("user");
//		if (user == null) {
//			log.debug("user is null. FIX THIS!");
//			session.invalidate();
//			resp.sendRedirect("login");
//		}
//		else {
			log.trace("Got a user: " + user);
			ObjectMapper mapper = new ObjectMapper();
			String userJson = mapper.writeValueAsString(user);
			log.trace(userJson);
			PrintWriter writer = resp.getWriter();
			resp.setContentType("application/json");
			writer.write(userJson);
//		}
		
	}

	/**
	 * Returns a list of all users
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> users = new ArrayList<User>();
		users = us.safeGetAllUsers();
		ObjectMapper mapper = new ObjectMapper();
		String usersJson = mapper.writeValueAsString(users);
		PrintWriter writer = resp.getWriter();
		resp.setContentType("application/json");
		writer.write(usersJson);
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
