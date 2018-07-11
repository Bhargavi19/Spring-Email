package com.vishnu.project;


import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MailerApplication extends SpringBootServletInitializer 
{
	private static final Logger logger = Logger.getLogger(MailerApplication.class);
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MailerApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MailerApplication.class, args);
    }
}
