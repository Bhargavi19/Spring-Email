package com.mailer.controller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mailer.model.User;
import com.mailer.service.UserService;
/**
 * This class comprises methods to handle forgot password feature 
 * @author vishnu
 *
 */
@Controller
public class ForgotPasswordController 
{
	@Autowired
    private UserService userService;
	
	static final Logger logger =Logger.getLogger(ForgotPasswordController.class);
	/**
	 * This method returns the forgot password page
	 * @return forgot password page is returned
	 */
	@RequestMapping(value="/forgotpassword", method=RequestMethod.GET)
    public ModelAndView showForgotPassword()
    {
		logger.info("get request for forgot password served");
    	return new ModelAndView("forgotpassword");
    }
	
	/**
	 * 
	 * @param name is the username of user who forgot their password
	 * @return view with a message saying whether the username is found or not is returned
	 */
    @RequestMapping(value="/forgotpassword", method=RequestMethod.POST)
    public ModelAndView sendMailLink(@RequestParam String name)
    {
    	ModelAndView view = new ModelAndView("forgotpassword");
    	view.addObject("message",getMessage(userService,name));
    	return view;
    }
    /**
     * 
     * @param userService bean is required to find out whether the user is actually present or not
     * @param name is the username of the user
     * @return message whether the forgot password flow is successful or user is invalid
     */
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

