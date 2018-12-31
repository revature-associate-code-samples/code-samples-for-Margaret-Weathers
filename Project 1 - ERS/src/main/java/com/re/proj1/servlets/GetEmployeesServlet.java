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
import com.re.proj1.pojos.Reimbursement;
import com.re.proj1.pojos.User;
import com.re.proj1.service.UserService;

@WebServlet({"/get-approved-employees","/get-pending-employees", "/approve-employee", "/deny-employee"})
public class GetEmployeesServlet extends HttpServlet{

	private static Logger log = Logger.getLogger(GetEmployeesServlet.class);
	private static UserService us = new UserService();

	/**
	 * Gets approved and pending employees for manager views
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			resp.sendRedirect("login");		// Fix if time..
		}
		else {
			List<User> users = new ArrayList<User>();
			String requestType = req.getRequestURI();
			log.trace(requestType);
			switch(requestType) {
			case "/ERS/get-approved-employees":
				users = us.getAllUsersExcludeCurrent(user.getUserId());	// Gets all users excluding current manager. Passwords are set to null;
				break;
			case "/ERS/get-pending-employees":
				users = us.getAllPendingUsers();
				break;
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String usersJson = mapper.writeValueAsString(users);
			PrintWriter writer = resp.getWriter();
			resp.setContentType("application/json");
			writer.write(usersJson);				
		}		
	}
	
	// Approves or denies pending employees
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String requestType = req.getRequestURI();
		log.trace(requestType);
		User onlyId = mapper.readValue(req.getInputStream(), User.class);	// User object with only the id set
		int id = onlyId.getUserId();	// extract UserId
		User u = us.getUserById(id);	// get user from db
		switch(requestType) {
		case "/ERS/approve-employee": 
			u.setApproved(1);			// Approve the user
			us.update(u);
			break;
		case "/ERS/deny-employee":
			us.delete(id);
			break;		
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
