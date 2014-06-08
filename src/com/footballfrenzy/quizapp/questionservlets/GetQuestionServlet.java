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
		String clientOrigin = req.getHeader("origin");
		Date date = new Date();
		//Date date = new Date(Long.parseLong(dateString));
	
		Datastore datastore = new DatastoreImpl();
		Question question = (datastore.getQuestion(date)).clone();
		resp.setHeader("Access-Control-Allow-Origin", clientOrigin);
        resp.setHeader("Access-Control-Allow-Methods", "GET");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Max-Age", "86400");
		resp.getWriter().println(gson.toJson(question));
	}
}
