package com.footballfrenzy.quizapp.pollservlets;

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
public class GetPollDetailsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/json");
		String clientOrigin = req.getHeader("origin");
		String pollName = req.getParameter("poll");
		Datastore datastore = new DatastoreImpl();
		JSONObject jsonobj=null;
		try {
			jsonobj = datastore.getPollStatus(pollName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.setHeader("Access-Control-Allow-Origin", clientOrigin);
        resp.setHeader("Access-Control-Allow-Methods", "GET");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Max-Age", "86400");
		resp.getWriter().println(jsonobj);
	}
	
}
