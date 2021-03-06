package com.mailer.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.email.controller.LoginController;
import com.email.controller.RegistrationController;

@WebAppConfiguration
public class MailerTestClass extends MailerAbstractTestClass 
{
	protected MockMvc mvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;
    
   
   
    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
