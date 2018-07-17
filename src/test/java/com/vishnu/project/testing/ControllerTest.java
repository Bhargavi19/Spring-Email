package com.vishnu.project.testing;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.vishnu.project.exceptions.UserAccessException;
import com.vishnu.project.service.UserService;

@WithMockUser
@Transactional
public class ControllerTest extends MailerAbstractTestClass
{
	@Autowired
    private UserService userService;
    
	@Autowired
    private WebApplicationContext context;
    
    private MockMvc mockMvc;
    
    @Autowired
    private Filter springSecurityFilterChain;
    
   
    
    @Before
    public void setUp() 
    {
      mockMvc = MockMvcBuilders
              .webAppContextSetup(context)
              .addFilters(springSecurityFilterChain)
             .defaultRequest(get("/").with(testSecurityContext()))
              .build();
    }
    
    
    @Test
    //  @WithMockUser//(username = "user",password="passwd" )
  	public void testgetWelcomeSuccess() throws Exception 
    {
  	  mockMvc.perform(get("/welcome"))
  	  //.andExpect(status().isOk())
  	  .andDo(print())
  	  .andExpect(view().name("redirect:/inbox/user"))
  	  .andExpect(redirectedUrl("/inbox/user"));
    
    }  
    
    @Test
    //  @WithMockUser//(username = "user",password="passwd" )
  	public void testgetDefaultPage() throws Exception 
    {
  	  mockMvc.perform(get("/"))
  	  //.andExpect(status().isOk())
  	  .andDo(print())
  	  .andExpect(view().name("redirect:/inbox/user"))
  	  .andExpect(redirectedUrl("/inbox/user"));
    
    }  
    
    @Test
    //  @WithMockUser//(username = "user",password="passwd" )
  	public void testgetDefaultErrorPage() throws Exception 
    {
  	  mockMvc.perform(get("/error"))
  	  .andExpect(status().isOk())
  	  .andDo(print())
  	  .andExpect(view().name("errorPage"));
    
    }  
    
    @Test
    @WithMockUser(username = "vishnu@gmail.com",password="vishnu123" )
    public void showDraftsTestWithValidUserName() throws Exception
    {
    	mockMvc.perform(get("/draft/vishnu@gmail.com"))
    	.andExpect(status().isOk())
    	.andDo(print())
    	.andExpect(view().name("draft"))
    	.andExpect(model().attributeExists("mails"));
    }
    
    
    
}
