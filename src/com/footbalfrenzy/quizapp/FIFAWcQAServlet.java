package com.footbalfrenzy.quizapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.*;

import com.footballfrenzy.quizapp.dataobjects.Question;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class FIFAWcQAServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/json");
		List<String> tmp = new ArrayList<String>();
		tmp.add("a");
		tmp.add("b");
		Question q = new Question("Temp Question", tmp, "a", new Date(), "Temp Fact");
		Gson gs = new Gson();
		resp.getWriter().println(gs.toJson(q));
	}
}
