package com.test.rack;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.jruby.rack.PoolingRackApplicationFactory;
import org.jruby.rack.RackApplicationFactory;
import org.jruby.rack.RackServletContextListener;
import org.jruby.rack.SerialPoolingRackApplicationFactory;
import org.jruby.rack.SharedRackApplicationFactory;
import org.jruby.rack.rails.RailsRackApplicationFactory;
import org.jruby.rack.servlet.ServletRackContext;

public class RackServletContextInitializer 
{
	protected static final Logger log = Logger.getLogger(RackServletContextInitializer.class.getName());
	protected static boolean isRackFactoryInitialized = false;
	
	public void contextInitialized(ServletContext ctx) 
    {
    	if (!isRackFactoryInitialized)
    	{
    		log.log(Level.INFO, "RackFactory needs to be initalized.");
            final RackApplicationFactory fac = newApplicationFactory(ctx);
            ctx.setAttribute(RackServletContextListener.FACTORY_KEY, fac);
            try 
            {
                fac.init(new ServletRackContext(ctx));
            } 
            catch (Exception ex) 
            {
                ctx.log("Error: application initialization failed", ex);
            }
            log.log(Level.INFO, "RackFactory is initalized, so flag is true now.");
            isRackFactoryInitialized = true;
    	}
    	else
		{
			log.log(Level.INFO, "RackFactory is already initalized, so nothing to initalize for JRuby.");
		}

    }
    
    protected RackApplicationFactory newApplicationFactory(ServletContext context) 
    {
        Integer maxRuntimes = null;
        try 
        {
            maxRuntimes = Integer.parseInt(context.getInitParameter("jruby.max.runtimes").toString());
        } 
        catch (Exception e) 
        {
        }
        
        if (maxRuntimes != null && maxRuntimes == 1) 
        {
            return new SharedRackApplicationFactory(new RailsRackApplicationFactory());
        } 
        else 
        {
            boolean serial = false;
            try 
            {
                serial = Boolean.parseBoolean(context.getInitParameter("jruby.init.serial").toString());
            }
            catch (Exception e) 
            {
            }
            
            if(serial) 
            {
                return new SerialPoolingRackApplicationFactory(new RailsRackApplicationFactory());
            }
            else 
            {
                return new PoolingRackApplicationFactory(new RailsRackApplicationFactory());
            }
        }
    }
}
