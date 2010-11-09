package com.test.rack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RackHttpRequestWrapper extends HttpServletRequestWrapper 
{
	private static final String REDIRECT_JRUBY_PATH_DEFAULT = "/jrubyprocess/index";
	private String jruby_path;
	
	public RackHttpRequestWrapper(HttpServletRequest request) 
	{
		super(request);
		this.jruby_path = REDIRECT_JRUBY_PATH_DEFAULT;
	}
	
	public RackHttpRequestWrapper(HttpServletRequest request, String jruby_path) 
	{
		super(request);
		if (jruby_path!=null)
		{
			this.jruby_path = jruby_path;
		}
		else
		{
			this.jruby_path = REDIRECT_JRUBY_PATH_DEFAULT;
		}
	}
	
    /**
     * Rewrite meaning of request URI to include query string.
     * @return
     */
    @Override public String getRequestURI() 
    {
    	return jruby_path;
    }
	
    /**
     * Rewrite meaning of path info to be servlet path + path info.
     * @return full path info
     */
	@Override
     public String getPathInfo() 
	{
		return jruby_path;
    }
}
