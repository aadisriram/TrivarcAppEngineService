package com.footballfrenzy.quizapp.questionservlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballfrenzy.quizapp.dao.Datastore;
import com.footballfrenzy.quizapp.dao.DatastoreImpl;
import com.footballfrenzy.quizapp.dataobjects.Question;
import com.google.gson.Gson;

public class GetQuestionFromIdServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("text/json");
		Gson gson = new Gson();
		Question question=null;
		String questionId = req.getParameter("QID");
		String clientOrigin = req.getHeader("origin");

		Datastore datastore = new DatastoreImpl();
		if (questionId != null && !questionId.equals("")) {
			long qId = Long.parseLong((questionId));
			question = datastore.getQuestion(qId);
		}
		
		resp.setHeader("Access-Control-Allow-Origin", clientOrigin);
        resp.setHeader("Access-Control-Allow-Methods", "GET");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Max-Age", "86400");
		if(question!=null)
			resp.getWriter().println(gson.toJson(question));
		else
			resp.getWriter().println("Fail");
	}
}
