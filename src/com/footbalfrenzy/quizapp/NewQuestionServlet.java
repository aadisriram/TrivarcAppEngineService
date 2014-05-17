package com.footbalfrenzy.quizapp;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballfrenzy.quizapp.dao.Datastore;
import com.footballfrenzy.quizapp.dao.DatastoreImpl;
import com.footballfrenzy.quizapp.dataobjects.Question;
import com.google.gson.Gson;

/*
	This is the servlet for handling new question creation request.
	If you see the servlet has a method doPost which means it expects HTTP Post request
	The post request must contain a JSON string for a question Object.
*/
@SuppressWarnings("serial")
public class NewQuestionServlet extends HttpServlet {
	
	// Expects a HTTP Post request
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		Gson gson = new Gson();
		String questionJson = req.getParameter("question");
		Question question = gson.fromJson(questionJson, Question.class);
		
		Datastore datastore = new DatastoreImpl();
		datastore.addQuestion(question);
		
		resp.getWriter().println("Question Added");
	}

}