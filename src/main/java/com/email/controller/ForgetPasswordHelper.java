package com.email.controller;
/*package com.mailer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mailer.model.User;
import com.mailer.service.UserService;


public class ForgetPasswordHelper
{
	
	
	public String getMessage(UserService userService, String name) 
	{
		System.out.println("name "+name);
		if(userService == null) System.out.println("null us");
		User u = userService.findByUsername(name);
		if(u == null) 
		{
			System.out.println("null");
			return "username couldnt be found";
		}
		else {
			System.out.println("not null");
			return "Password reset link has been sent";
		}
	}
	
	
}*/