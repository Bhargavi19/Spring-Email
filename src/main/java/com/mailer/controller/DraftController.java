package com.mailer.controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mailer.exception.CrudException;
import com.mailer.exception.UserAccessException;
import com.mailer.model.ComposeInfo;
import com.mailer.model.Mail;
import com.mailer.service.MailService;
/**
 * This class comprises of controllers to handle draft feature.
 * @author vishnu
 *
 */
@Controller
public class DraftController 
{
	@Autowired
    private MailService mailService;
	
	private static final String DRAFT = "draft";
	
	static final Logger logger =Logger.getLogger(DraftController.class);
	
	/**
	 * This method retrieves all drafts belong to particular user
	 * @param name of current signed in user
	 * @param Principal p which has information about currently authenticated user
	 * @return ModelAndView object having view set to  draft and list of drafts appended to view
	 * @exception UserAccessException is thrown if the signed in user wants to retrieve other user drafts
	 * 
	 */
	@RequestMapping(value="/draft/{name}", method=RequestMethod.GET)
	public ModelAndView showDrafts(@PathVariable String name, Principal p)
	{
		final String username = name+".com";
		logger.info("show drafts is called by "+username+" principal is "+p.getName());
		if(p.getName().equalsIgnoreCase(username))
		{
			List<Mail> mails;
			try
			{
				mails = mailService.getAllMailsByFromAndType(username,DRAFT);
			}
			catch(CrudException e)
			{
				logger.error("problem reading drafts for user "+username);
				throw new CrudException("Sorry "+p.getName()+"!.There is some problem reading your draft");
			}
			 
			
			
			ModelAndView model = new ModelAndView(DRAFT);
			model.addObject("mails", mails);
			logger.info("sending drafts of user "+username);
			return model;
		}
		else
		{
			logger.error("illegal access [drafts] by "+p.getName());
			throw new UserAccessException("Hello "+p.getName()+" You cannot access "+name+" drafts");
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param id
	 * @param p
	 * @return
	 */
	@RequestMapping(value="draft/delete/{name}/{id}", method=RequestMethod.GET)
	public ModelAndView deleteDarft(@PathVariable String name, @PathVariable Long id,Principal p)
	{
		logger.info("delete drafts invoked by user "+name+". Draft id is "+id);
		if(p.getName().equalsIgnoreCase(name))
		{
			try
			{
				mailService.deleteMail(id);
			}
			catch(CrudException e)
			{
				logger.error("unable to delete drafts. ID "+id);
				throw new CrudException("Sorry "+p.getName()+"!.There is some problem deleting your draft");
			}
			logger.info("returning drafts of user "+name);
			return new ModelAndView("redirect:/draft/"+name);
		}
		else
		{
			logger.error("ILLEGAL ACCESS BY "+name);
			throw new UserAccessException("Hello "+p.getName()+" You cannot delete "+name+" drafts");
		}
	}
	
	
	/**
	 * 
	 * @param compose
	 * @param p
	 * @return
	 */
	@RequestMapping(value="/savedraft", method=RequestMethod.POST, consumes="application/json")
	public  @ResponseBody String  saveDraft(@RequestBody ComposeInfo compose, Principal p) 
	{
		logger.info("savedraft");
		if(p != null)
		{
			long id = compose.getId();
			
			String to = compose.getTo();
			String body = compose.getBody();
			String subject = compose.getSubject();
			
			Mail m = null;
			if(id == 0)
			{
				logger.info("composing new draft");
				m = new Mail();
			}
			else
			{
				logger.info("editing already existing draft with id"+id);
				try
				{
					m = mailService.getById(id);
				}
				catch(CrudException e)
				{
					logger.error("CRUD EXCEPTION "+p.getName());
					throw new CrudException("Sorry "+p.getName()+"!.There is some problem saving your draft");
				}
			}
			m.setFromAddress(p.getName());
			m.setType(DRAFT);
			m.setToAddress(to);
			m.setBody(body);
			m.setSubject(subject);
			try
			{
				logger.info("saving draft");
				mailService.saveMail(m);
			}
			catch(CrudException e)
			{
				logger.error("CRUD EXCEPTION");
				throw new CrudException("Sorry "+p.getName()+"!.There is some problem saving your draft");
			}
			logger.info("draft saved");
			return "success";
		}
		logger.error("draft not saved");
		return "failure";
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public ModelAndView editDraft(@RequestParam Long id)
	{
		ComposeInfo compose  = new ComposeInfo();
		ModelAndView mvc = new ModelAndView("compose");
		
		Mail m = mailService.getById(id);
		compose.setId(m.getId());
		compose.setBody(m.getBody());
		compose.setTo(m.getToAddress());
		compose.setSubject(m.getSubject());
		
		mvc.addObject("compose", compose);
		return mvc;
	}

	
}


