package com.vishnu.project.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vishnu.project.model.User;
import com.vishnu.project.service.UserService;
import com.vishnu.project.validator.UserValidator;

@Controller
public class RegistrationController 
{
    @Autowired
    private UserService userService;


    @Autowired
    private UserValidator userValidator;
    
    static final String REGISTRATION = "registration";

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) 
    {
        model.addAttribute("userForm", new User());

        return REGISTRATION;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) 
        {
        	
        	return REGISTRATION;
        }

        userService.save(userForm);

       
        model.addAttribute("successMessage", "You have been registered successfully");
        model.addAttribute("userForm",new User());
        
        return REGISTRATION;
    }
    
    
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public ModelAndView welcome(Model model, Principal principal) 
    {
    	return new ModelAndView("redirect:/inbox/"+principal.getName());
    	
    }
    
}
