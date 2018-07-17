package com.mailer.controller;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * This page is substitution for the default white label error page.
 * @author vishnu
 * 
 *
 */
@Controller
public class CustomErrorController implements ErrorController  
{
	static final Logger logger =Logger.getLogger(CustomErrorController.class);
	/**
	 * This method returns the custom error page overriding the default error message 
	 * @param Exception e 
	 * @return name of the error page
	 */
    @RequestMapping("/error")
    public String handleError(Exception e) 
    {
    	logger.error("returning error page");
    	return "errorPage";
    }
 
    /**
     * Method overridden from ErrorController
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}