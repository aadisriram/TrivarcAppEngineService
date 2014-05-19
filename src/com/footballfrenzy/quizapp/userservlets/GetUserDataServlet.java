package com.footballfrenzy.quizapp.userservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballfrenzy.quizapp.dao.Datastore;
import com.footballfrenzy.quizapp.dao.DatastoreImpl;
import com.footballfrenzy.quizapp.dataobjects.User;
import com.google.gson.Gson;

/**
 * This is the servlet for getting user details
 */
@SuppressWarnings("serial")
public class GetUserDataServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/json");
		Gson gson = new Gson();
		String emailId = req.getParameter("emailId");
	
		Datastore datastore = new DatastoreImpl();
		User user = datastore.getUserData(emailId);
		
		resp.getWriter().println(gson.toJson(user));
	}

}
