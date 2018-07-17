package com.vishnu.project.controllers;

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

import com.vishnu.project.exceptions.CrudException;
import com.vishnu.project.exceptions.UserAccessException;
import com.vishnu.project.model.Compose;
import com.vishnu.project.model.Mail;
import com.vishnu.project.service.MailService;

@Controller
public class DraftController 
{
	@Autowired
    private MailService mailService;
	
	private static final String DRAFT = "draft";
	
	static final Logger logger =Logger.getLogger(DraftController.class);
	
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
				mails = mailService.getAllMailsByFromaddAndType(username,DRAFT);
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
			logger.error("illegal access [drafts] by "+username);
			throw new UserAccessException("Hello "+p.getName()+" You cannot access "+name+" drafts");
		}
	}
	
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
	
	
	
	@RequestMapping(value="/savedraft", method=RequestMethod.POST, consumes="application/json")
	public  @ResponseBody String  saveDraft(@RequestBody Compose compose, Principal p) 
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
			m.setFrma(p.getName());
			m.setMt(DRAFT);
			m.setToa(to);
			m.setBody(body);
			m.setSbjt(subject);
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

	
}


