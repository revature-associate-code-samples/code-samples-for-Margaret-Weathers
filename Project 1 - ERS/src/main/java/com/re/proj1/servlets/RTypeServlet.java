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

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.re.proj1.pojos.RType;
import com.re.proj1.service.RTypeService;

/**
 * 
 * Gets reimbursement types to load into submit reimbursement dropdown.
 *
 */
@WebServlet("/r-type")
public class RTypeServlet extends HttpServlet{
	
	private static Logger log = Logger.getLogger(RTypeServlet.class);
	private static RTypeService typeService = new RTypeService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<RType> types = new ArrayList<RType>();
		types = typeService.getAll();
		
		ObjectMapper mapper = new ObjectMapper();
		String typesJson = mapper.writeValueAsString(types);
		log.debug(typesJson);
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("application/json");
		writer.write(typesJson);
	}
	

}
