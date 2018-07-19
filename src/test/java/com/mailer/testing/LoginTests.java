package com.mailer.testing;
/**
 * @author vishnu
 */
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.mailer.controller.ComposeController;
import com.mailer.controller.ForgotPasswordController;
import com.mailer.model.ComposeInfo;
import com.mailer.model.User;
import com.mailer.service.MailService;
import com.mailer.service.UserService;

//@WithMockUser
@Transactional
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
	
	private MockMvc mockMvc;
	
	final String USERNAME = "testuser@gmail.com";
	final String PASSWORD = "testpassword";
	
	private User u;

	@Before
	public void setup() 
	{
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.defaultRequest(get("/").with(testSecurityContext()))
					.addFilter(springSecurityFilterChain)
                .build();
		
		
		u = new User();
		u.setUsername("testuser@gmail.com");
		u.setPassword("testpassword");
		userService.save(u);
		
	 }

	@Test
	public void testLoginPageLoading() throws Exception 
	{
		mockMvc.perform(get("/login")).andExpect(view().name("login"));
	}
	@Test
	public void testRegistrationPageLoading() throws Exception 
	{
		mockMvc.perform(get("/registration")).andExpect(status().isOk()).andExpect(view().name("registration"));
	}
	
	@Test
	public void testUserLoginSuccess() throws Exception 
	{
		
			
	   RequestBuilder requestBuilder = post("http://localhost:8080/login")
	            .param("username", USERNAME)
	            .param("password", PASSWORD);
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
	            .param("name", USERNAME);
	            
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
		assertEquals("Password reset link has been sent",helper.getMessage(userService,"testuser@gmail.com"));
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
	  .perform(formLogin().user(u.getUsername()).password("testpassword"))
	  .andExpect(authenticated());
	}
	
	@Test
	public void testLoginFailure() throws Exception
	{
		mockMvc
	    .perform(formLogin().user(u.getUsername()).password("dummy12"))
		.andExpect(unauthenticated());
	}
	
	 @Test
	 public void Logout() throws Exception 
	 {
	    	  mockMvc.perform(logout()).andExpect(unauthenticated());
	 }
	 
}