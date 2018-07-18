package com.mailer.controller;

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

import com.mailer.model.User;
import com.mailer.service.UserService;
import com.mailer.validator.UserValidator;
/**
 * This class comprises of controllers to handle user registration
 * @author vishnu
 *
 */
@Controller
public class RegistrationController 
{
    @Autowired
    private UserService userService;


    @Autowired
    private UserValidator userValidator;
    
    static final String REGISTRATION = "registration";
    
    static final Logger logger =Logger.getLogger(RegistrationController.class);
    /**
     * 
     * @param model holds the model object
     * @return this controller returns the registration page
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) 
    {
    	logger.info("get request to registration page");
        model.addAttribute("userForm", new User());

        return REGISTRATION;
    }
    
    /**
     * This method is responsible for handling the user registration
     * @param userForm consists of data entered by the user
     * @param bindingResult has the errors resulted due to information submitted by the user
     * @param model holds the model object
     * @return returns registration view with message whether user is registered successfully or not
     */
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
    
    /**
     * This is where user lands after signing in and is redirected to inbox
     * @param model to hold the model object
     * @param principal to get the details of currently authenticated user
     * @return This method redirects to inbox page after user sign in
     */
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public ModelAndView welcome(Model model, Principal principal) 
    {
    	final String userName = principal.getName();
    	logger.info("user"+userName+" logged in");
    	return new ModelAndView("redirect:/inbox/"+principal.getName());
    	
    }
    
    
}
