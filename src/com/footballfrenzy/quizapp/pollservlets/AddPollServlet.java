package com.footballfrenzy.quizapp.pollservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballfrenzy.quizapp.dao.Datastore;
import com.footballfrenzy.quizapp.dao.DatastoreImpl;
import com.footballfrenzy.quizapp.dataobjects.Poll;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class AddPollServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		String clientOrigin = req.getHeader("origin");
		String pollJson = req.getParameter("poll");
		
		Gson gson = new Gson();
		Poll poll = gson.fromJson(pollJson, Poll.class);
		Datastore datastore = new DatastoreImpl();
		boolean isSuccess=datastore.addPollItem(poll);
		String success=isSuccess?"Success":"Fail";
		
		resp.setHeader("Access-Control-Allow-Origin", clientOrigin);
        resp.setHeader("Access-Control-Allow-Methods", "GET");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Max-Age", "86400");		
		resp.getWriter().println(success);
	}
}
