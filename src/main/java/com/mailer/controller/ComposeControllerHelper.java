/*package com.mailer.controller;

import com.mailer.exception.CrudException;
import com.mailer.model.Compose;
import com.mailer.model.Mail;
import com.mailer.model.User;
import com.mailer.service.MailService;
import com.mailer.service.UserService;

public class ComposeControllerHelper 
{
	public String composeMail(String name, Compose compose, UserService service, MailService mailService) 
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
			m.setFrma(name);
			m.setMt("sent");
			m.setToa(compose.getTo());
			m.setBody(compose.getBody());
			m.setSbjt(compose.getSubject());
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
	
} // end of class
*/