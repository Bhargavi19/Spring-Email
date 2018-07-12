package com.vishnu.project.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vishnu.project.exceptions.CrudException;
import com.vishnu.project.exceptions.UserAccessException;
import com.vishnu.project.model.Compose;
import com.vishnu.project.model.Mail;
import com.vishnu.project.model.User;
import com.vishnu.project.service.MailService;
import com.vishnu.project.service.UserService;

@Controller
public class ComposeController 
{
	@Autowired
    private MailService mailService;
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value="/compose", method=RequestMethod.GET)
	public ModelAndView composeView(Principal p) 
	{
		
		return new ModelAndView("compose","compose",new Compose());
	}
	
	
	@RequestMapping(value="/compose/{name}", method=RequestMethod.POST)
	@ResponseBody
	public  String composeMail(@PathVariable String name, @RequestBody Compose compose, Principal p)
	{
		
		name+=".com";
		
		if(p.getName().equalsIgnoreCase(name))
		{
			String to = compose.getTo();
			long ln = compose.getId();
			User u = service.findByUsername(to);
			if(u == null)
			{
				
				return "Recipient cannot be found";
			}
			else 
			{
				Mail m = null;
				if(ln == 0)
				{
					System.out.println("creating new mail");
					m = new Mail();
					
				}
				else
				{
					System.out.println("edited draft");
					try {
					m = mailService.getById((long) ln);
					}
					catch(CrudException e) {
						throw new CrudException("Sorry "+p.getName()+"!.There is some problem sending your mail");
					}
				}
				m.setFrma(name);
				m.setMt("sent");
				m.setToa(compose.getTo());
				m.setBody(compose.getBody());
				m.setSbjt(compose.getSubject());
				try {
					mailService.saveMail(m);
				}
				catch(CrudException e) {
					throw new CrudException("Sorry "+p.getName()+"!.There is some problem sending your mail");
				}
				
				
				
				return "The mail has been sent successfully";
				
			}
			
		}
		else
		{
			throw new UserAccessException("Hello "+p.getName()+" You cannot compose mails");
		}
		
	}

}
