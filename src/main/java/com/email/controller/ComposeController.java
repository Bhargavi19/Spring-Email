package com.email.controller;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.email.exception.CrudException;
import com.email.exception.UserAccessException;
import com.email.model.ComposeInfo;
import com.email.model.Mail;
import com.email.model.User;
import com.email.service.MailService;
import com.email.service.UserService;

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
		return new ModelAndView("compose","compose",new ComposeInfo());
	}
	
	@RequestMapping(value="/compose/{name}", method=RequestMethod.POST)
	@ResponseBody
	public  String composeMail(@PathVariable String name, @RequestBody ComposeInfo compose, Principal p)
	{
		
		final String username = name+".com";
		
		if(p.getName().equalsIgnoreCase(username))
		{
			/*ComposeControllerHelper composeHelper = new ComposeControllerHelper();*/
			
			return composeMail(username, compose, service, mailService);
			
		} // if end
		
		else
		{
			logger.error("Illegal access exception");
			throw new UserAccessException("Hello "+p.getName()+" You cannot compose mails");
		}
		
	}
	
	public String composeMail(String name, ComposeInfo compose, UserService service, MailService mailService) 
	{
		
		
		String to = compose.getTo();
		
		
		User u = service.findByUsername(to);
		if(u == null)
		{
			return "Recipient cannot be found";
		}
		else 
		{
			Mail m = null;
			long ln = compose.getId();
			if(ln == 0)
			{
				m = new Mail();
			}
			else
			{
				
				try 
				{
					m = mailService.getById((long) ln);
				}
				catch(CrudException e) 
				{
					throw new CrudException("Sorry "+name+"!.There is some problem sending your mail");
				}
			}
			m.setFromAddress(name);
			m.setType("mail");
			m.setToAddress(compose.getTo());
			m.setBody(compose.getBody());
			m.setSubject(compose.getSubject());
			try 
			{
				mailService.save(m);
			}
			catch(CrudException e) 
			{
				throw new CrudException("Sorry "+name+"!.There is some problem sending your mail");
			}
			
			return "The mail has been sent successfully";
			
		}  // end of else
		
	} // end of method
	

}
