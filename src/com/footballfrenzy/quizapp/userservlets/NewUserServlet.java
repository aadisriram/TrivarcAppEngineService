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
 * This is the servlet for handling new question creation request.
 */
@SuppressWarnings("serial")
public class NewUserServlet extends HttpServlet{

	// Expects a HTTP Post request
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		Gson gson = new Gson();
		String userJson = req.getParameter("user");
		User user = gson.fromJson(userJson, User.class);
		
		Datastore datastore = new DatastoreImpl();
		boolean isSuccess=datastore.addUser(user);
		String success=isSuccess?"Success":"Fail";
		resp.getWriter().println(success);
	}
}
