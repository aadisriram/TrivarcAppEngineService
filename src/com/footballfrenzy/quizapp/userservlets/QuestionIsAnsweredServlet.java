package com.footballfrenzy.quizapp.userservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballfrenzy.quizapp.dao.Datastore;
import com.footballfrenzy.quizapp.dao.DatastoreImpl;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class QuestionIsAnsweredServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/json");
		String userId=req.getParameter("UID");
		String questionId = req.getParameter("QID");
		String answer = req.getParameter("AID");
		String timeTaken = req.getParameter("TimeTaken");
		String clientOrigin = req.getHeader("origin");

		Datastore datastore = new DatastoreImpl();
		JSONObject obj=null;
		if(questionId != null && !questionId.equals("") && timeTaken != null && !timeTaken.equals("")  )
		{ 
		  long qId = Long.parseLong((questionId));
		  long time = Long.parseLong((questionId));
		  try {
			obj= datastore.addUserActivity(userId, qId, answer, time);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}  		
		
		resp.setHeader("Access-Control-Allow-Origin", clientOrigin);
        resp.setHeader("Access-Control-Allow-Methods", "GET");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Max-Age", "86400");
		resp.getWriter().println(obj);
	}
}
