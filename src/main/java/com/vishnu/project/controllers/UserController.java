package com.vishnu.project.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vishnu.project.model.User;
import com.vishnu.project.service.SecurityService;
import com.vishnu.project.service.UserService;
import com.vishnu.project.validator.UserValidator;

@Controller
public class UserController 
{
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) 
    {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) 
        {
        	//model.addAttribute("userForm",new User());
            return "registration";
        }

        userService.save(userForm);

        //securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        model.addAttribute("successMessage", "You have been registered successfully");
        model.addAttribute("userForm",new User());
        
        return "registration";
    }
    @RequestMapping(value="/forgotpassword", method=RequestMethod.GET)
    public ModelAndView showForgotPassword()
    {
    	return new ModelAndView("forgotpassword");
    }
    @RequestMapping(value="/forgotpassword", method=RequestMethod.POST)
    public ModelAndView sendMailLink(@RequestParam String name)
    {
    	User u = userService.findByUsername(name);
    	ModelAndView view = new ModelAndView("forgotpassword");
    	if(u == null)
    	{
    		view.addObject("message","username couldnt be found");
    		return view;
    	}
    	else
    	{
    		view.addObject("message","Password reset link has been sent");
    		return view;
    	}
    	
    }
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
    
   /* @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/welcome";
        }
        return "/login";
}*/

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public ModelAndView welcome(Model model, Principal principal) 
    {
    	return new ModelAndView("redirect:/inbox/"+principal.getName());
    	
    }
    
}
