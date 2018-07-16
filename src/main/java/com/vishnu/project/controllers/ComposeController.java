package com.vishnu.project.controllers;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vishnu.project.exceptions.CrudException;
import com.vishnu.project.exceptions.UserAccessException;
import com.vishnu.project.model.Compose;
import com.vishnu.project.model.Mail;
import com.vishnu.project.model.User;
import com.vishnu.project.service.MailService;
import com.vishnu.project.service.UserService;
import org.apache.log4j.Logger;

@Controller
public class ComposeController 
{
	@Autowired
    private MailService mailService;
	
	@Autowired
	private UserService service;
	
	static final Logger logger =Logger.getLogger(ComposeController.class);

	@RequestMapping(value="/compose", method=RequestMethod.GET)
	public ModelAndView composeView(Principal p) 
	{
		logger.info("compose method called");
		return new ModelAndView("compose","compose",new Compose());
	}
	
	
	@RequestMapping(value="/compose/{name}", method=RequestMethod.POST)
	@ResponseBody
	public  String composeMail(@PathVariable String name, @RequestBody Compose compose, Principal p)
	{
		
		final String username = name+".com";
		
		if(p.getName().equalsIgnoreCase(username))
		{
			ComposeControllerHelper composeHelper = new ComposeControllerHelper();
			return composeHelper.composeMail(username, compose, service, mailService);
			
		} // if end
		
		else
		{
			logger.error("Illegal access exception");
			throw new UserAccessException("Hello "+p.getName()+" You cannot compose mails");
		}
		
	}

}
