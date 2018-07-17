package com.mailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
/**
 * 
 * @author vishnu
 * 
 * This class serves as the Main class for Mailer application
 */
@SpringBootApplication
public class MailerApplication extends SpringBootServletInitializer 
{
	
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
    {
    	
        return application.sources(MailerApplication.class);
    }
    
    /**
     * 
     * @param args
     * @return ConfigurableApplicationContext
     */
    public static void main(String[] args)
    { 
    	
        SpringApplication.run(MailerApplication.class, args);
        
    }
}
