package com.footballfrenzy.quizapp.userservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballfrenzy.quizapp.dao.Datastore;
import com.footballfrenzy.quizapp.dao.DatastoreImpl;

@SuppressWarnings("serial")
public class DeleteUserServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		String emailId = req.getParameter("emailId");
	
		Datastore datastore = new DatastoreImpl();
		boolean isSuccess = datastore.deleteUser(emailId);
		String success=isSuccess?"User Data deleted":" Unable to Delete User Data";
		resp.getWriter().println(success);
	}
}
