package com.email.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.email.model.User;
import com.email.service.UserService;
import com.email.validator.UserValidator;

@Controller
public class RegistrationController 
{
    @Autowired
    private UserService userService;


    @Autowired
    private UserValidator userValidator;
    
    static final String REGISTRATION = "registration";
    
    static final Logger logger =Logger.getLogger(RegistrationController.class);
   
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) 
    {
    	logger.info("get request to registration page");
        model.addAttribute("userForm", new User());

        return REGISTRATION;
    }
    
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) 
    {
    	logger.info("post request to registration page");
        userValidator.validate(userForm, bindingResult);
        final String username = userForm.getUsername();
        if (bindingResult.hasErrors()) 
        {
        	logger.error("registration unsuccessful for"+username);
        	return REGISTRATION;
        }
        
        
        userService.save(userForm);

       
        model.addAttribute("successMessage", "You have been registered successfully");
        model.addAttribute("userForm",new User());
        
        logger.info("registration succesful for "+username);
        return REGISTRATION;
    }
   
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public ModelAndView welcome(Model model, Principal principal) 
    {
    	final String userName = principal.getName();
    	logger.info("user"+userName+" logged in");
    	return new ModelAndView("redirect:/inbox/"+principal.getName());
    	
    }
    
    
}
