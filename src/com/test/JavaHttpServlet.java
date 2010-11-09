package com.test;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class JavaHttpServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{	
		resp.setContentType("text/html");
		resp.getWriter().println("<html>");
		resp.getWriter().println("<p><strong>Hello, JAVA Servlet World</strong>");
		resp.getWriter().println("<p><img SRC=\"/images/java_servlet.jpg\" HSPACE=4 BORDER=0  align=TOP>");
		resp.getWriter().println("<p><a href=\"/index.html\"><img SRC=\"/images/return.gif\" HSPACE=4 BORDER=0></a><a href=\"/index.html\">Return</a>");
		resp.getWriter().println("</body>");
		resp.getWriter().println("</html>");
	}
}
