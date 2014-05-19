package com.footballfrenzy.quizapp.userservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballfrenzy.quizapp.dao.Datastore;
import com.footballfrenzy.quizapp.dao.DatastoreImpl;

@SuppressWarnings("serial")
public class QuestionIsAnsweredServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		String emailId=req.getParameter("emailId");
		String questionId = req.getParameter("questionId");
		Datastore datastore = new DatastoreImpl();
		boolean isSuccess=false;
		if(questionId != null && !questionId.equals(""))
		{ 
		  long qId = Long.parseLong((questionId));
		  isSuccess= datastore.addtoQuestionsAnswered(emailId, qId);
		}  		
		String Success=isSuccess?"Question Added to user History":"Failed to add question to user History";
		resp.getWriter().println(Success);
	}
}
