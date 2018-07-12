package com.vishnu.project.testing;

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
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/*
 * @author: VishnuJammula
 */

public class TestLogin extends ContextLoadingTests
{
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
    private FilterChainProxy springSecurityFilterChain;
	
	private MockMvc mockMvc;

	@Before
	public void setup() {
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
	    		 
	  /*  requestBuilder = post("http://localhost:8080/welcome");
	    mockMvc.perform(requestBuilder).andExpect(redirectedUrl("/login"));*/
	    
	            
	            
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
		mockMvc.perform(get("/forgotpassword")).andExpect(status().isOk()).andExpect(view().name("forgotpassword"));
	}
	
	@Test
	public void forgotPasswordWithValidUsername() throws Exception
	{
		RequestBuilder requestBuilder = post("http://localhost:8080/forgotpassword")
	            .param("name", "vishnu@gmail.com");
	            
	    		 mockMvc.perform(requestBuilder)
	            .andDo(print())
	            .andExpect(model().attributeExists("message")).andExpect(model().attribute("message","Password reset link has been sent"));
	}
	@Test
	public void forgotPasswordWithInValidUsername() throws Exception
	{
		RequestBuilder requestBuilder = post("http://localhost:8080/forgotpassword")
	            .param("name", "xyz@gmail.com");
	            
	    		 mockMvc.perform(requestBuilder)
	            .andDo(print())
	            .andExpect(model().attributeExists("message")).andExpect(model().attribute("message","username couldnt be found"));
	}
	
	/* perform registration testing
	 * 
	 */
	
	/*@Test
	public void registrationTest() throws Exception
	{
		RequestBuilder requestBuilder = post("http://localhost:8080/registration");
		mockMvc.perform(requestBuilder)
		.andDo(print()).andExpect(model().hasErrors());	
	 }*/
	
}
