package com.footballfrenzy.quizapp.userservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballfrenzy.quizapp.dao.Datastore;
import com.footballfrenzy.quizapp.dao.DatastoreImpl;

@SuppressWarnings("serial")
public class CheckIsQuestionAnsweredServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		String userId=req.getParameter("UID");
		String questionId = req.getParameter("QID");
		Datastore datastore = new DatastoreImpl();
		boolean isAnswered=false;
		if(questionId != null && !questionId.equals(""))
		{ 
		  long qId = Long.parseLong((questionId));
		  isAnswered= datastore.isQuestionAlreadyAnswered(userId, qId);
		}  		
		String Success=isAnswered?"Success":"Fail";
		resp.getWriter().println(Success);
	}
}
