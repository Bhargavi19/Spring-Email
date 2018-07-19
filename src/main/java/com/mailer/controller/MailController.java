package com.mailer.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mailer.exception.CrudException;
import com.mailer.exception.UserAccessException;
import com.mailer.model.Mail;
import com.mailer.service.MailService;
/**
 * This class comprises of controllers to handle inbox, sent mails and trash
 * @author vishnu
 *
 */
@Controller
public class MailController 
{
	
	
	
	@Autowired
    private MailService mailService;
	
	
	
	private static final Logger logger = Logger.getLogger(MailController.class);
	private static final String DELETED = "deleted";
	private static final String MAILS = "mails";
	private static final String TYPE = "mail";
	
	/**
	 * This controller handles all the get requests to inbox.
	 * @param name is username of current user who is logged in
	 * @param p gives details about currently authenticated user
	 * @return inbox page is returned with inbox mails in the page
	 * @exception throws UserAccessException, CrudException 
	 */
	@RequestMapping(value="/inbox/{name}", method=RequestMethod.GET)
	public ModelAndView getAllMails(@PathVariable String name, Principal p)
	{
		logger.info("entered into getAllMails: USER --> "+name);
		logger.info("name: "+name);
		
		final String uname=name+".com";
		
		if(p.getName().equalsIgnoreCase(uname))
		{
			List<Mail> mails = null;
			try
			{
				 mails = mailService.getAllMailsByToAndType(uname,TYPE);
				 logger.info("inbox size::"+mails.size());
			}
			catch(CrudException e)
			{
				throw new CrudException("Sorry "+uname+"!. Your mails couldnt be retrieved. Error Message "+e.getMessage());
			
			}
			
			ModelAndView model = new ModelAndView("inbox");
			model.addObject(MAILS, mails);
			
			return model;
		}
		else
		{
			logger.info("UserAccessException raised in inbox. username is "+uname+" principal name is"+p.getName());
			throw new UserAccessException("Hello "+p.getName()+" You cannot access "+name+" inbox mails");
			
		}
	}
	
	/**s
	 * This is the exception handler for UserAccessException 
	 * This method returns a page with error message in it
	 * @param ex containing the trace about the exception
	 * @return error page is returned with error message int
	 */
	@ExceptionHandler(UserAccessException.class)
	public ModelAndView handleCustomException(UserAccessException ex) 
	{

		ModelAndView model = new ModelAndView("Exception");
		model.addObject("errMsg", ex.getErrMsg());
		return model;
	}
	
	 /** This is the exception handler for CrudException 
	 * This method returns a page with error message in it
	 * @param ex containing the trace about the exception
	 * @return Exception  page is returned with error message in it
	 */

	@ExceptionHandler(CrudException.class)
	public ModelAndView handleCustomException(CrudException ex) 
	{

		ModelAndView model = new ModelAndView("Exception");
		model.addObject("errMsg", ex.getErrMsg());
		return model;
	}
	
	/**
	 * This method handles all the get requests to view a particular mail
	 * @param name is username of user currently signed in 
	 * @param id is the ID field of the mail
	 * @param p gives details about currently authenticated user
	 * @return returns a view page with mail content embedded in it
	 * @exception throws UserAccessException,CrudException
	 */
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public ModelAndView showMail(@RequestParam String name, @RequestParam Long id, Principal p)
	{
		if(p.getName().equalsIgnoreCase(name))
		{
			Mail mail = null;
			ModelAndView viewModel = new ModelAndView("viewmail");
			try 
			{
				logger.info("id is::"+id);
				mail = mailService.getById(id);
				logger.info("mail object is "+mail);
			}
			catch(CrudException e)
			{
				throw new CrudException("Sorry "+name+"!.There is some problem retrieving your mail");
			}
			
			
			viewModel.addObject("mail",mail);
			return viewModel;
		}
		else
		{
			logger.info("UserAccessException raised in showMail. username is "+name+" principal name is"+p.getName());
			throw new UserAccessException("Hello "+p.getName()+" You cannot view "+name+" mails");
		}
		
	}
	
	/**
	 * This method handles all the get requests to delete a particular mail
	 * @param name is username of user currently signed in 
	 * @param id is the ID field of the mail
	 * @param p gives details about currently authenticated user
	 * @param move indicates whether has to be deleted permanently or moved to trash box
	 * 
	 * @return return a view page with list of remaining mails
	 * @exception throws UserAccessException,CrudException 
	 */
	
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
					mail.setType(DELETED);
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
			
			
		}
		else
		{
			logger.info("UserAccessException raised in deleteMail. username is "+name+" principal name is"+p.getName());
			throw new UserAccessException("Hello "+p.getName()+" You cannot delete "+name+" mails");
		}
		
		
	}
	
	/**
	 * This method handles all the get requests to restore a particular mail
	 * @param name is username of user currently signed in 
	 * @param id is the ID field of the mail
	 * @param p gives details about currently authenticated user
	 * @return returns a view  with remaining trash mails in it
	 * @exception throws UserAccessException, CrudException
	 */
	@RequestMapping(value="/restore", method=RequestMethod.GET)
	public ModelAndView restoreMail(@RequestParam String name,@RequestParam Long id, Principal p)
	{
		if(p.getName().equalsIgnoreCase(name))
		{
			Mail mail = null;
			try 
			{
				logger.info("principal name "+p.getName()+" trash mail id"+id);
				mail = mailService.getById(id);
				if(mail == null) throw new EntityNotFoundException("Can't find Mail for ID " + id);
				mail.setType(TYPE);
				mailService.saveMail(mail);
				logger.info("mail type after restore" + mail.getType());
			}
			catch(CrudException e) 
			{
				throw new CrudException("Sorry "+name+"!.There is some problem restoring your mail");
			}
			
			return new ModelAndView("redirect:/trash/"+name);
		}
		else
		{
			logger.info("UserAccessException raised in restoreMail. username is "+name+" principal name is"+p.getName());
			throw new UserAccessException("Hello "+p.getName()+" You cannot restore "+name+"  mails");
		}
	}
	
	/**
	 * This method handles all the get requests to view all trash mails
	 * @param name is username of user currently signed in 
	 * @param p gives details about currently authenticated user
	 * @return returns a view page with all trash mails in it
	 * @exception throws UserAccessException, CrudException
	 */
	
	@RequestMapping(value="/trash/{name}", method=RequestMethod.GET)
	public ModelAndView showTrash(@PathVariable String name, Principal p)
	{
		
		final String username=name+".com";
		if(p.getName().equalsIgnoreCase(username))
		{
			List<Mail> toMails = null ; 
			List<Mail> fromMails = null;
			
			try 
			{
				 toMails = mailService.getAllMailsByToAndType(username,DELETED);
				 fromMails = mailService.getAllMailsByFromAndType(username,DELETED);
				 
			}
			catch(CrudException e) {
				throw new CrudException("Sorry "+name+"!.There is some problem retrieving your mails");
			}
			
			Set<Mail> s = new LinkedHashSet<>();
			s.addAll(toMails);
			s.addAll(fromMails);
			
			List<Mail> mails = new ArrayList<>();
			mails.addAll(s);
			
			ModelAndView model = new ModelAndView("trash");
			model.addObject(MAILS, mails);
			
			return model;
			
			
		}
		else
		{
			logger.info("UserAccessException raised in showTrash controller. username is "+username+" principal name is"+p.getName());
			throw new UserAccessException("Hello "+p.getName()+" You cannot access "+name+" trash mails");
		}
	}
	
	/**
	 *  This method handles all the get requests to view all sent mails
	 * @param name is username of user currently signed in 
	 * @param p gives details about currently authenticated user
	 * @return returns a view page with all sent mails  in it
	 * @exception throws UserAccessException,CrudException  
	*/
	
	@RequestMapping(value="/sent/{name}", method=RequestMethod.GET)
	public ModelAndView showSentMails(@PathVariable String name, Principal p)
	{
		final String username = name+".com";
		logger.info(username +" accessing sent mails. Principal is "+p.getName());
		if(p.getName().equals(username)) 
		{
			List<Mail> mails;
			try 
			{
				mails = mailService.getAllMailsByFromAndType(username,TYPE);
				logger.info("size::"+mails.size());
			}
			catch(CrudException e)
			{
				throw new CrudException("Sorry "+username+"!.There is some problem retrieving your mail");
			}
			
			ModelAndView model = new ModelAndView("sent");
			model.addObject(MAILS, mails);
			
			return model;
		}
		else
		{
			logger.info("UserAccessException raised in showMails controller. username is "+username+" principal name is"+p.getName());
			throw new UserAccessException("Hello "+p.getName()+" You cannot access "+username+" sent mails");
		}
	}
	
}


