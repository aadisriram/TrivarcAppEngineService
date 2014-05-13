package com.footbalfrenzy.quizapp;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class FIFAWcQAServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
