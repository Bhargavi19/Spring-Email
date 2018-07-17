package com.mailer.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 
 * @author vishnu
 *
 */
@Controller
public class LoginController 
{
	/**
	 * 
	 * @param model
	 * @param error
	 * @param logout
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout,Principal p) 
    {
    	if(p != null)
    	{
    		
    		return "redirect:/welcome";
    	}
        if (error != null)
            model.addAttribute("error", "Invalid username password combination.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

}
