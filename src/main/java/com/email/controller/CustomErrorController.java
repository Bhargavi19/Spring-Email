package com.email.controller;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController  
{
	static final Logger logger =Logger.getLogger(CustomErrorController.class);
	
    @RequestMapping("/error")
    public String handleError(Exception e) 
    {
    	logger.error("returning error page");
    	return "errorPage";
    }
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
}