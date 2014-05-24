package com.footballfrenzy.quizapp.userservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballfrenzy.quizapp.dao.Datastore;
import com.footballfrenzy.quizapp.dao.DatastoreImpl;

@SuppressWarnings("serial")
public class ModifyUserDataServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		String userId = req.getParameter("UID");
		String name = req.getParameter("newName");
	
		Datastore datastore = new DatastoreImpl();
		boolean isSuccess = datastore.modifyUserData(userId, name);
		String success=isSuccess?"Success":"Fail";
		resp.getWriter().println(success);
	}

}
