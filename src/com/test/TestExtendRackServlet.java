package com.test;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.filter.RequestModeRackFilter;

@SuppressWarnings("serial")
public class TestExtendRackServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		
		String requestMode = request.getParameter("requestMode");
		
		if (requestMode.equalsIgnoreCase(RequestModeRackFilter.EXTENDRACK_REQUEST_MODE_JRUBY))
		{
			response.setContentType("text/plain");
			response.getWriter().println("This should not happen.");
		}
		else if (requestMode.equalsIgnoreCase(RequestModeRackFilter.EXTENDRACK_REQUEST_MODE_JAVA))
		{
			response.setContentType("text/html");
			response.getWriter().println("<html>");
			response.getWriter().println("<p><strong>Hello, Cached JAVA World!</strong>");
			response.getWriter().println("<p><img SRC=\"/images/Java_HelloWorld.png\" HSPACE=4 BORDER=0  align=TOP>");
			response.getWriter().println("<p><a href=\"/index.html\"><img SRC=\"/images/return.gif\" HSPACE=4 BORDER=0></a><a href=\"/index.html\">Return</a>");
			response.getWriter().println("</body>");
			response.getWriter().println("</html>");
		}
		else
		{
			response.setContentType("text/plain");
			response.getWriter().println("Unknown?");
		}
	}
}
