package com.test.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.rack.RackHttpRequestWrapper;

public class RequestModeRackFilter extends ExtendBaseRackFilter 
{

	public static final String EXTENDRACK_REQUEST_MODE_JAVA = "java";
	public static final String EXTENDRACK_REQUEST_MODE_JRUBY = "jruby";
	private String pathForJRuby;
	
    public void init(FilterConfig config) throws ServletException 
    {
    	super.init(config);
        pathForJRuby = config.getInitParameter("redirect_jruby_path");
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest httpRequest = (HttpServletRequest)request;

		String requestMode = httpRequest.getParameter("requestMode");		
		if (requestMode.equalsIgnoreCase(EXTENDRACK_REQUEST_MODE_JAVA))
		{
			chain.doFilter(request, response);
		}
		else if (requestMode.equalsIgnoreCase(EXTENDRACK_REQUEST_MODE_JRUBY))
		{
	        doExtendedRackFilter(new RackHttpRequestWrapper(httpRequest, pathForJRuby), (HttpServletResponse)response);
		}
		else
		{
			chain.doFilter(request, response);
		}
		
	}



}
