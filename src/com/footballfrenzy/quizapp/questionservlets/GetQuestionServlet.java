package com.footballfrenzy.quizapp.questionservlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballfrenzy.quizapp.dao.Datastore;
import com.footballfrenzy.quizapp.dao.DatastoreImpl;
import com.footballfrenzy.quizapp.dataobjects.Question;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class GetQuestionServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		resp.setContentType("text/json");
		Gson gson = new Gson();
		String dateString = req.getParameter("date");
		Date date = new Date(dateString);
	
		Datastore datastore = new DatastoreImpl();
		Question question = datastore.getQuestion(date);
		
		resp.getWriter().println(gson.toJson(question));
	}

}
