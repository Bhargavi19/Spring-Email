package com.vishnu.project.testing;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mailer.controller.ComposeController;

import com.mailer.controller.ForgotPasswordController;
import com.mailer.model.ComposeInfo;
import com.mailer.service.MailService;
import com.mailer.service.UserService;

public class LoginTests extends MailerAbstractTestClass 
{
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
    private FilterChainProxy springSecurityFilterChain;
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	MailService mailService;
	
	
	MockHttpSession session;
	private MockMvc mockMvc;

	@Before
	public void setup() 
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain)
                .build();
		
	 }

	@Test
	public void testLoginPageLoading() throws Exception 
	{
		mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("login"));
	}
	@Test
	public void testRegistrationPageLoading() throws Exception 
	{
		mockMvc.perform(get("/registration")).andExpect(status().isOk()).andExpect(view().name("registration"));
	}
	
	@Test
	public void testUserLoginSuccess() throws Exception {
	   RequestBuilder requestBuilder = post("http://localhost:8080/login")
	            .param("username", "vishnu@gmail.com")
	            .param("password", "vishnu123");
	    		 mockMvc.perform(requestBuilder)
	            .andDo(print())
	            .andExpect(redirectedUrl("/welcome"));	
	}
	
	@Test
	public void testUserLoginFailure() throws Exception {
	    RequestBuilder requestBuilder = post("http://localhost:8080/login")
	            .param("username", "vishnu@gmail.com")
	            .param("password", "vishnu");
	    		 mockMvc.perform(requestBuilder)
	            .andDo(print())
	            .andExpect(redirectedUrl("/login?error"));
	            
	}
	
	/* perfom forgotpassword testing */
	@Test
	public void testForgotPasswordLoading() throws Exception 
	{
		mockMvc.perform(get("/forgotpassword"))
			.andExpect(status().isOk())
			.andExpect(view().name("forgotpassword"));
	}
	
	@Test
	public void forgotPasswordWithValidUsername() throws Exception
	{
		RequestBuilder requestBuilder = post("http://localhost:8080/forgotpassword")
	            .param("name", "vishnu@gmail.com");
	            
	    		 mockMvc.perform(requestBuilder)
	            .andDo(print())
	            .andExpect(model().attributeExists("message"))
	            .andExpect(model().attribute("message","Password reset link has been sent"));
	}
	
	@Test
	public void forgotPasswordWithInValidUsername() throws Exception
	{
		RequestBuilder requestBuilder = post("http://localhost:8080/forgotpassword")
	            .param("name", "xyz@gmail.com");
	            
	    		 mockMvc.perform(requestBuilder)
	            .andDo(print())
	            .andExpect(model().attributeExists("message"))
	            .andExpect(model().attribute("message","username couldnt be found"));
	}
	 
	
	@Test
	public void forgotPasswordTesterWithValidUserName() 
	{
		ForgotPasswordController helper = new ForgotPasswordController();
		assertEquals("Password reset link has been sent",helper.getMessage(userService,"vishnu@gmail.com"));
	}
	
	@Test
	public void forgotPasswordTesterWithInvalidUserName() 
	{
		ForgotPasswordController helper = new ForgotPasswordController();
		assertEquals("username couldnt be found",helper.getMessage(userService,"vhnu@gmail.com"));
	}
	
	
	@Test
	public void ComposeControllerTestWithInvalidUsername()
	{
		ComposeController compose = new ComposeController();
		ComposeInfo composeInfo = new ComposeInfo();
		composeInfo.setTo("xyz@gmail.com");
		assertEquals("Recipient cannot be found",compose.composeMail("xyz@gmail.com", composeInfo, userService, mailService));
	}
	 
	@Test
	public void testLoginSuccess() throws Exception 
	{
	  mockMvc
	  .perform(formLogin().user("vishnu@gmail.com").password("vishnu123"))
	  .andExpect(authenticated());
	}
	
	@Test
	public void testLoginFailure() throws Exception
	{
		mockMvc
		  .perform(formLogin().user("vishnu@gmail.com").password("vishnu12"))
		  .andExpect(unauthenticated());
	}
	 @Test
	 public void Logout() throws Exception 
	 {
	    	  mockMvc.perform(logout()).andExpect(unauthenticated());
	 }
	 
}