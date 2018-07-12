package com.vishnu.project.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vishnu.project.model.User;
import com.vishnu.project.service.UserService;

@Controller
public class ForgotPasswordController 
{
	@Autowired
    private UserService userService;
	
	@RequestMapping(value="/forgotpassword", method=RequestMethod.GET)
    public ModelAndView showForgotPassword()
    {
    	return new ModelAndView("forgotpassword");
    }
    @RequestMapping(value="/forgotpassword", method=RequestMethod.POST)
    public ModelAndView sendMailLink(@RequestParam String name)
    {
    	User u = userService.findByUsername(name);
    	ModelAndView view = new ModelAndView("forgotpassword");
    	if(u == null)
    	{
    		view.addObject("message","username couldnt be found");
    		return view;
    	}
    	else
    	{
    		view.addObject("message","Password reset link has been sent");
    		return view;
    	}
    	
    }
}
