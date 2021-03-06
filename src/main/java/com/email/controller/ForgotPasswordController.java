package com.email.controller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.email.model.User;
import com.email.service.UserService;

@Controller
public class ForgotPasswordController 
{
	@Autowired
    private UserService userService;
	
	static final Logger logger =Logger.getLogger(ForgotPasswordController.class);
	
	@RequestMapping(value="/forgotpassword", method=RequestMethod.GET)
    public ModelAndView showForgotPassword()
    {
		logger.info("get request for forgot password served");
    	return new ModelAndView("forgotpassword");
    }
	
	
    @RequestMapping(value="/forgotpassword", method=RequestMethod.POST)
    public ModelAndView sendMailLink(@RequestParam String name)
    {
    	ModelAndView view = new ModelAndView("forgotpassword");
    	view.addObject("message",getMessage(userService,name));
    	return view;
    }
    
    public String getMessage(UserService userService, String name) 
	{
		logger.info("name is "+name);
		if(userService == null) System.out.println("null us");
		User u = userService.findByUsername(name);
		if(u == null) 
		{
			logger.info("user is null");
			return "username couldnt be found";
		}
		else 
		{
			logger.info("user is not null");
			return "Password reset link has been sent";
		}
	}
    	
}

