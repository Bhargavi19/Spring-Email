package com.vishnu.project.controllers;

import java.security.Principal;
import java.util.List;

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
import com.vishnu.project.service.UserService;

@Controller
public class DraftController 
{
	@Autowired
    private MailService mailService;
	
	@Autowired
	private UserService service;
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


