package com.vishnu.project.controllers;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomErrorController implements ErrorController  
{
 
    @RequestMapping("/error")
    public String handleError(Exception e) 
    {
    	System.out.println(e.getMessage());
    	return "errorPage";
    }
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
}