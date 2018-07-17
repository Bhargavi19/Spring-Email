package com.vishnu.project.testing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mailer.MailerApplication;
import com.mailer.controller.ComposeController;
import com.mailer.controller.CustomErrorController;
import com.mailer.controller.DraftController;
import com.mailer.controller.ForgotPasswordController;
import com.mailer.controller.LoginController;
import com.mailer.controller.RegistrationController;
import com.mailer.service.UserService;


public  class ContextLoadingTests extends MailerAbstractTestClass 
{
	@Autowired
	private LoginController mockLogin;
	
	@Autowired
	private RegistrationController mockRegis;
	
	@Autowired
	private ComposeController mockCompose;
	
	@Autowired
	private CustomErrorController mockError;
	
	@Autowired
	private DraftController mockDraft;
	
	@Autowired
	private ForgotPasswordController mockFPC;
	
	
	
	@Test
	public void contextLoads() 
	{
		
	}
	
	 @Test
	 public void contextLoginLoads() throws Exception 
	 {
	     assertThat(mockLogin).isNotNull();
	 }
	
	 @Test
	 public void contextRegistrationLoads() throws Exception 
	 {
	     assertThat(mockRegis).isNotNull();
	 }
	 
	 @Test
	 public void contextComposeLoads() throws Exception 
	 {
	     assertThat(mockCompose).isNotNull();
	 }
	 
	 @Test
	 public void contexErrorLoads() throws Exception 
	 {
	     assertThat(mockError).isNotNull();
	 }
	 
	 @Test
	 public void contexDraftLoads() throws Exception 
	 {
	     assertThat(mockDraft).isNotNull();
	 }
	 
	 @Test
	 public void contextFPCLoads() throws Exception 
	 {
	     assertThat(mockFPC).isNotNull();
	 }
	 
	 @Test
	 public void contextDraftLoads() throws Exception 
	 {
	     assertThat(mockDraft).isNotNull();
	 }
	 
	 
}


/*import org.junit.runner.RunWith;

*/