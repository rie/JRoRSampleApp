package com.test.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.jruby.rack.RackEnvironment;
import org.jruby.rack.servlet.DefaultServletDispatcher;
import org.jruby.rack.servlet.ServletDispatcher;

import com.test.rack.RackServletContextInitializer;


public class ExtendBaseRackFilter implements Filter
{	
	private static Boolean isRackServletInitialized = false;
	protected ServletContext ctx;
	protected Object servletContextRockObject = new Object();

	@Override
	public void init(FilterConfig config) throws ServletException 
	{
		ctx = config.getServletContext();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest    httpRequest  = (HttpServletRequest)request;
        HttpServletResponse   httpResponse = (HttpServletResponse) response;
        ResponseStatusCapture capture      = new ResponseStatusCapture(httpResponse);
        chain.doFilter(httpRequest, capture);
        if (capture.isError()) 
        {
            httpResponse.reset();
            doExtendedRackFilter(httpRequest, httpResponse);
        }
		
	}
	
	private void doRackServletInitialize()
	{
		synchronized (isRackServletInitialized) 
		{
			if (!isRackServletInitialized)
			{
//				TODO we need to synchronize ServletContext ?
				synchronized (servletContextRockObject) 
		    	{
					new RackServletContextInitializer().contextInitialized(ctx);
		    	}
				isRackServletInitialized = true;
			}
		}
	}
	
	protected final void doExtendedRackFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
	{


        request.setAttribute(RackEnvironment.DYNAMIC_REQS_ONLY, Boolean.TRUE);
        
		doRackServletInitialize();
        ServletDispatcher rackDispatcher;
//		TODO we need to synchronize ServletContext ?
        synchronized (servletContextRockObject) 
    	{
			rackDispatcher = new DefaultServletDispatcher(ctx);
    	}
        rackDispatcher.process(request , response);
    
	}
	
    private static class ResponseStatusCapture extends HttpServletResponseWrapper {
        private int status = 200;

        public ResponseStatusCapture(HttpServletResponse response) {
            super(response);
        }

        @Override public void sendError(int status, String message) throws IOException {
            this.status = status;
        }

        @Override public void sendError(int status) throws IOException {
            this.status = status;
        }

        @Override public void sendRedirect(String path) throws IOException {
            this.status = 302;
            super.sendRedirect(path);
        }

        @Override public void setStatus(int status) {
            this.status = status;
            if (!isError()) {
                super.setStatus(status);
            }
        }

        @Override public void setStatus(int status, String message) {
            this.status = status;
            if (!isError()) {
                super.setStatus(status, message);
            }
        }

        @Override public void flushBuffer() throws IOException {
            if (!isError()) {
                super.flushBuffer();
            }
        }

        private boolean isError() {
            return status >= 400;
        }
    }

	@Override
	public void destroy() 
	{
	}



}
