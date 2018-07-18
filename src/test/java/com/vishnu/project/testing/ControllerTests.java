package com.vishnu.project.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import com.mailer.exception.UserAccessException;
import com.mailer.model.Mail;
import com.mailer.service.MailService;
import com.mailer.service.UserService;

@WithMockUser
@Transactional
public class ControllerTests extends MailerAbstractTestClass
{
	@Autowired
    private UserService userService;
	
	@Autowired
	private MailService mailService;
    
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
    public void testGetWelcomeSuccessWithDefaultUser() throws Exception 
    {
  	  mockMvc.perform(get("/welcome"))
  	  	.andExpect(status().is3xxRedirection())
  	  	.andDo(print())
  	  	.andExpect(view().name("redirect:/inbox/user"))
  	  	.andExpect(redirectedUrl("/inbox/user"));
    
    }
    
    
    @Test
    @WithMockUser(username = "testuser@gmail.com",password="testpassword" )
  	public void testGetWelcomeSuccessWithValidUser() throws Exception 
    {
  	  mockMvc.perform(get("/welcome"))
  	  	.andDo(print())
  	  	.andExpect(view().name("redirect:/inbox/testuser@gmail.com"))
  	  	.andExpect(redirectedUrl("/inbox/testuser@gmail.com"));
    
    }  
    
     @Test
  	public void testGetinboxPageWithDefaultUser() throws Exception 
    {
  	  mockMvc.perform(get("/"))
  	  .andExpect(status().is3xxRedirection())
  	  .andDo(print())
  	  .andExpect(view().name("redirect:/inbox/user"))
  	  .andExpect(redirectedUrl("/inbox/user"));
    
    }
     
    
    
    @Test
  	public void testgetDefaultErrorPage() throws Exception 
    {
  	  mockMvc.perform(get("/error"))
  	  .andExpect(status().isOk())
  	  .andDo(print())
  	  .andExpect(view().name("errorPage"));
    
    }  
    
    @Test
    @WithMockUser(username = "vishnu@gmail.com",password="vishnu123" )
    public void showDraftsTestWithValidUser() throws Exception
    {
    	mockMvc.perform(get("/draft/vishnu@gmail.com"))
    	.andExpect(status().isOk())
    	.andDo(print())
    	.andExpect(view().name("draft"))
    	.andExpect(model().attributeExists("mails"));
    }
    
    @Test(expected = NestedServletException.class)
    public void showDraftsTestWithInvalidUser() throws Exception
    {
    	try
    	{
    		mockMvc.perform(get("/draft/vishnu@gmail.com"));
      	}
    	catch(UserAccessException e) 
    	{
    		e.printStackTrace();
    	}
    	
    }    
    
    @Test
    public void showMailsTestWithInvalidUser() throws Exception
    {
    	mockMvc.perform(get("/view").param("name", "testuser@gmail.com").param("id", "19"))
    	//.andExpect(model().attributeExists("mail"))
    	.andDo(print())
    	.andExpect(view().name("Exception"))
    	.andExpect(model().attribute("errMsg","Hello user You cannot view testuser@gmail.com mails" ));
    	
    }
    
    @Test
    @WithMockUser(username = "testuser@gmail.com",password="testpassword" )
    public void showMailsTestWithValidUser() throws Exception
    {
    	mockMvc.perform(get("/view").param("name", "testuser@gmail.com").param("id", "1"))
    	.andDo(print())
    	.andExpect(view().name("viewmail"))
    	.andExpect(model().attributeExists("mail"));
    	
    }
    
    @Test
    public void showMailsTestWithDefaultUser() throws Exception
    {
    	mockMvc.perform(get("/view").param("name", "user").param("id", "20"))
    	.andDo(print())
    	.andExpect(view().name("viewmail")).andExpect(model().attributeDoesNotExist("mail"));
    }
    
    
    @Test
    @WithMockUser(username = "user@gmail.com",password="password" )
    public void showInboxTestSuccess() throws Exception
    {
    	Mail m = new Mail();
    	
    	m.setToAddress("user@gmail.com");
    	m.setFromAddress("user@gmail.com");
    	m.setType("mail");
    	m.setSubject("test subject");
    	m.setBody("test body");
    	
    	mailService.saveMail(m);
    	
    	mockMvc.perform(get("/inbox/user@gmail.com")).andDo(print())
    	.andExpect(status().isOk())
    	.andExpect(view().name("inbox")).andExpect(model().attributeExists("mails"));
    }
    
    @Test
    public void showInboxTestFail() throws Exception
    {
    	
    	mockMvc.perform(get("/inbox/dummyuser@gmail.com")).andDo(print())
    	.andExpect(status().isOk())
    	.andExpect(view().name("Exception"))
    	.andExpect(model().attribute("errMsg","Hello user You cannot access dummyuser@gmail inbox mails"));
    }
}
