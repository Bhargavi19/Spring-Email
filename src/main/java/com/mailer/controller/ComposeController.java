package com.mailer.controller;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mailer.exception.CrudException;
import com.mailer.exception.UserAccessException;
import com.mailer.model.ComposeInfo;
import com.mailer.model.Mail;
import com.mailer.model.User;
import com.mailer.service.MailService;
import com.mailer.service.UserService;

import org.apache.log4j.Logger;
/**
 * This class comprises of controllers to handle mail compose feature.
 * 
 * @author vishnu
 *
 */
@Controller
public class ComposeController 
{
	@Autowired
    private MailService mailService;
	
	@Autowired
	private UserService service;
	
	static final Logger logger =Logger.getLogger(ComposeController.class);
	
	
	/**
	 * This method returns the compose page along with ComposeInfo object binded to it.
	 * 
	 * @param Principal p, to get the currently authenticated user;
	 * 
	 * @return Returns ModelAndView object having view name set to compose. 
	 */
	@RequestMapping(value="/compose", method=RequestMethod.GET)
	public ModelAndView composeView(Principal p) 
	{
		logger.info("compose method called");
		return new ModelAndView("compose","compose",new ComposeInfo());
	}
	
	/**
	 * This method is used to send the mail to intended recipient. If recipient address is valid and present in user table, then the mail is sent otherwise mail is discarded.
	 * @param name is the from address
	 * @param compose is object of ComposeInfo class which has the  
	 * @param Principal p to get the currently authenticated user
	 * @return
	 */
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
	
	/**
	 * This method act as helper method for composing mail and sending it
	 * @param name of the person sending the mail
	 * @param compose object consisting of mail content
	 * @return Returns a string saying whether mail is sent or discarded
	 */
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
				mailService.saveMail(m);
			}
			catch(CrudException e) 
			{
				throw new CrudException("Sorry "+name+"!.There is some problem sending your mail");
			}
			
			return "The mail has been sent successfully";
			
		}  // end of else
		
	} // end of method
	

}
