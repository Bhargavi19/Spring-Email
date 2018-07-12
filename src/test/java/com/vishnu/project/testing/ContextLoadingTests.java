package com.vishnu.project.testing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vishnu.project.MailerApplication;
import com.vishnu.project.controllers.ComposeController;
import com.vishnu.project.controllers.CustomErrorController;
import com.vishnu.project.controllers.DraftController;
import com.vishnu.project.controllers.ForgotPasswordController;
import com.vishnu.project.controllers.LoginController;
import com.vishnu.project.controllers.MailController;
import com.vishnu.project.controllers.RegistrationController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MailerApplication.class)
public class ContextLoadingTests 
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
	
	@Autowired 
	private MailController mockMail;
	
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

