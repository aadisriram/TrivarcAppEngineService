package com.footballfrenzy.quizapp.userservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballfrenzy.quizapp.dao.Datastore;
import com.footballfrenzy.quizapp.dao.DatastoreImpl;
import com.footballfrenzy.quizapp.dataobjects.QuestionAttempt;

@SuppressWarnings("serial")
public class QuestionIsAnsweredServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		String userId=req.getParameter("UID");
		String questionId = req.getParameter("QID");
		String answer = req.getParameter("AID");
		String timeTaken = req.getParameter("TimeTaken");
		Datastore datastore = new DatastoreImpl();
		boolean isSuccess=false;
		if(questionId != null && !questionId.equals("") && timeTaken != null && !timeTaken.equals("")  )
		{ 
		  long qId = Long.parseLong((questionId));
		  long time = Long.parseLong((questionId));
		  QuestionAttempt attempt= new QuestionAttempt(qId, answer, time);
		  isSuccess= datastore.addUserActivity(userId,attempt);
		}  		
		String Success=isSuccess?"Success":"Fail";
		resp.getWriter().println(Success);
	}
}
