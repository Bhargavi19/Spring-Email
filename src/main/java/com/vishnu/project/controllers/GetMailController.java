package com.vishnu.project.controllers;

import java.net.ConnectException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
public class GetMailController 
{
	
	/* remember IF {name} then name+=".com"
	 * 
	*/
	
	@Autowired
    private MailService mailService;
	
	@Autowired
	private UserService service; 
	
	private static final Logger logger = Logger.getLogger(GetMailController.class);
	
	/* controller for retrieving all mails */
	
	
	@RequestMapping(value="/inbox/{name}", method=RequestMethod.GET)
	public ModelAndView getAllMails(@PathVariable String name, Principal p)
	{
		logger.info("entered into getAllMails: USER --> "+name);
		logger.info("name: "+name);
		name+=".com";
		if(p.getName().equalsIgnoreCase(name))
		{
			List<Mail> mails = null;
			try
			{
				 mails = mailService.getAllMailsByToaAndMt(name,"sent");
			}
			catch(CrudException e)
			{
				throw new CrudException("Sorry "+name+"!. Your mails couldnt be retrieved. Error Message "+e.getMessage());
			//	throw new Exception("hello");
			}
			
			ModelAndView model = new ModelAndView("inbox");
			model.addObject("mails", mails);
			
			return model;
		}
		else
		{
			throw new UserAccessException("Hello "+p.getName()+" You cannot access "+name+" inbox mails");
			//return new ModelAndView("login");  // exception can be thrown here
		}
	}
	
	
	@ExceptionHandler(UserAccessException.class)
	public ModelAndView handleCustomException(UserAccessException ex) 
	{

		ModelAndView model = new ModelAndView("Exception");
		model.addObject("errMsg", ex.getErrMsg());
		return model;
	}
	
	@ExceptionHandler(CrudException.class)
	public ModelAndView handleCustomException(CrudException ex) 
	{

		ModelAndView model = new ModelAndView("Exception");
		model.addObject("errMsg", ex.getErrMsg());
		return model;
	}
	
	/* controller for viewing particular mail */
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public ModelAndView showMail(@RequestParam String name, @RequestParam Long id, Principal p)
	{
		
		//System.out.println(name);
		if(p.getName().equalsIgnoreCase(name))
		{
			Mail mail = null;
			ModelAndView viewModel = new ModelAndView("viewmail");
			try {
				mail = mailService.getById(id);
			}
			catch(CrudException e){
				throw new CrudException("Sorry "+name+"!.There is some problem retrieving your mail");
			}
			
			//System.out.println(mail.toString());
			viewModel.addObject("mail",mail);
			return viewModel;
		}
		else
		{
			throw new UserAccessException("Hello "+p.getName()+" You cannot view "+name+" mails");
		}
		
	}
	
	/* controller to delete a particular mail */
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public ModelAndView deleteMail(@RequestParam String name,@RequestParam Long id,@RequestParam String page,@RequestParam String move, Principal p)
	{
		if(p.getName().equalsIgnoreCase(name))
		{
			Mail mail = null;
			if(move.equals("move"))
			{	
				mail = mailService.getById(id);
				try 
				{
					mail.setMt("deleted");
					mailService.saveMail(mail);
				}
				catch(CrudException e) 
				{
					throw new CrudException("Sorry "+name+"!.There is some problem moving your mail to trash");
				}
				
			}
			else if(move.equals("perm"))
			{
				try {
				mailService.deleteMail(id);
				}
				catch(CrudException e) {
					throw new CrudException("Sorry "+name+"!.There is some problem deleting your mail");
				}
				
			}
			return new ModelAndView("redirect:/"+page+"/"+name);
			//return "redirect:/inbox/vishnu@gmail.com";
			
		}
		else
		{
			throw new UserAccessException("Hello "+p.getName()+" You cannot delete "+name+" mails");
		}
		
		
	}
	
	/* controller to restore a particular mail */
	@RequestMapping(value="/restore", method=RequestMethod.GET)
	public ModelAndView restoreMail(@RequestParam String name,@RequestParam Long id, Principal p)
	{
		if(p.getName().equalsIgnoreCase(name))
		{
			Mail mail = null;
			try {
			mail = mailService.getById(id);
			mail.setMt("sent");
			mailService.saveMail(mail);
			}
			catch(CrudException e) {
				throw new CrudException("Sorry "+name+"!.There is some problem restoring your mail");
			}
			//return "redirect:/inbox/vishnu@gmail.com";
			return new ModelAndView("redirect:/trash/"+name);
		}
		else
		{
			throw new UserAccessException("Hello "+p.getName()+" You cannot restore "+name+"  mails");
		}
	}
	
	/* controller to show trash mails */
	@RequestMapping(value="/trash/{name}", method=RequestMethod.GET)
	public ModelAndView showTrash(@PathVariable String name, Principal p)
	{
		//System.out.println("p --> "+p.getName());
		//System.out.println("name --> "+name);
		name+=".com";
		if(p.getName().equalsIgnoreCase(name))
		{
			List<Mail> to_mails = null ; List<Mail> from_mails = null;
			try 
			{
				 to_mails = mailService.getAllMailsByToaAndMt(name,"deleted");
				 from_mails = mailService.getAllMailsByFromaddAndType(name,"deleted");
			}
			catch(CrudException e) {
				throw new CrudException("Sorry "+name+"!.There is some problem retrieving your mails");
			}
			List<Mail> mails = new ArrayList<Mail>();
			mails.addAll(to_mails);
			mails.addAll(from_mails);
			ModelAndView model = new ModelAndView("trash");
			model.addObject("mails", mails);
			
			return model;
			
			
		}
		else
		{
			throw new UserAccessException("Hello "+p.getName()+" You cannot access "+name+" trash mails");
		}
	}
	
	/* controller to show sent mails */
	@RequestMapping(value="/sent/{name}", method=RequestMethod.GET)
	public ModelAndView showSentMails(@PathVariable String name, Principal p)
	{
		name+=".com";
		if(p.getName().equals(name)) 
		{
			List<Mail> mails;
			try 
			{
				mails = mailService.getAllMailsByFromaddAndType(name,"sent");
			}
			catch(CrudException e)
			{
				throw new CrudException("Sorry "+name+"!.There is some problem retrieving your mail");
			}
			
			ModelAndView model = new ModelAndView("sent");
			model.addObject("mails", mails);
			
			return model;
		}
		else
		{
			throw new UserAccessException("Hello "+p.getName()+" You cannot access "+name+" sent mails");
		}
	}
	@RequestMapping(value="/compose", method=RequestMethod.GET)
	public ModelAndView composeView(Principal p) 
	{
		
		return new ModelAndView("compose","compose",new Compose());
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public ModelAndView editDraft(@RequestParam Long id)
	{
		Compose compose  = new Compose();
		ModelAndView mvc = new ModelAndView("compose");
		
		Mail m = mailService.getById(id);
		compose.setId(m.getId());
		compose.setBody(m.getBody());
		compose.setTo(m.getToa());
		compose.setSubject(m.getSbjt());
		
		mvc.addObject("compose", compose);
		return mvc;
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
	
	@RequestMapping(value="/draft/{name}", method=RequestMethod.GET)
	public ModelAndView showDrafts(@PathVariable String name, Principal p)
	{
		name+=".com";
		if(p.getName().equalsIgnoreCase(name))
		{
			List<Mail> mails;
			try
			{
				mails = mailService.getAllMailsByFromaddAndType(name,"draft");
			}
			catch(CrudException e)
			{
				throw new CrudException("Sorry "+p.getName()+"!.There is some problem reading your draft");
			}
			 
			//System.out.println(mails.size());
			
			ModelAndView model = new ModelAndView("draft");
			model.addObject("mails", mails);
			
			return model;
		}
		else
		{
			throw new UserAccessException("Hello "+p.getName()+" You cannot access "+name+" drafts");
		}
	}
	
	@RequestMapping(value="draft/delete/{name}/{id}", method=RequestMethod.GET)
	public ModelAndView deleteDarft(@PathVariable String name, @PathVariable Long id,Principal p)
	{
		
		if(p.getName().equalsIgnoreCase(name))
		{
			try
			{
				mailService.deleteMail(id);
			}
			catch(CrudException e)
			{
				throw new CrudException("Sorry "+p.getName()+"!.There is some problem deleting your draft");
			}
			
			return new ModelAndView("redirect:/draft/"+name);
		}
		else
		{
			throw new UserAccessException("Hello "+p.getName()+" You cannot delete "+name+" drafts");
		}
	}
	
	
	
	@RequestMapping(value="/savedraft", method=RequestMethod.POST, consumes="application/json")
	public  @ResponseBody String  saveDraft(@RequestBody Compose compose, Principal p) 
	{
		if(p != null)
		{
			long id = compose.getId();
			System.out.println("draft id"+id);
			String to = compose.getTo();
			String body = compose.getBody();
			String subject = compose.getSubject();
			
			Mail m = null;
			if(id == 0)
			{
				System.out.println("creating new draft");
				m = new Mail();
			}
			else
			{
				System.out.println("editing old draft");
				try
				{
					m = mailService.getById(id);
				}
				catch(CrudException e)
				{
					throw new CrudException("Sorry "+p.getName()+"!.There is some problem saving your draft");
				}
			}
			m.setFrma(p.getName());
			m.setMt("draft");
			m.setToa(to);
			m.setBody(body);
			m.setSbjt(subject);
			try
			{
				mailService.saveMail(m);
			}
			catch(CrudException e)
			{
				throw new CrudException("Sorry "+p.getName()+"!.There is some problem saving your draft");
			}
			
			return "success";
		}
		return "failure";
	}
	
}

