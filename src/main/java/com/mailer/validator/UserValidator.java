package com.mailer.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mailer.controller.RegistrationController;
import com.mailer.model.User;
import com.mailer.service.UserService;
/**
 * This class has methods to validate the data entered by the user in registration form
 * @author vishnu
 *
 */
@Component
public class UserValidator implements Validator 
{
    @Autowired
    private UserService userService;
    private static final String USERNAME = "username";
    
    static final Logger logger =Logger.getLogger(RegistrationController.class);
    
    
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }
    
    /**
     * @param  o contains the user data type casted to User class
     * @param  errors holds the errors that has to be displayed to the user
     *
     * This method validates the info entered by the user. 
     * If the user is already registered or information entered doesn't meet the specified criteria, then the information entered by user is discarded
     * 
     */
    @Override
    public void validate(Object o, Errors errors) 
    {
    	
    	logger.info("IN VALIDATION CLASS");
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, USERNAME, "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue(USERNAME, "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue(USERNAME, "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
