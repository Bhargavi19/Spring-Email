package com.mailer.testing;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import com.email.model.Mail;
import com.email.repository.MailRepository;
import com.email.service.MailService;
import com.email.service.UserService;

@WithMockUser
@Transactional
public class ControllerTests extends MailerAbstractTestClass
{
	@Autowired
    private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MailRepository mailRepo;
    
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
    @WithMockUser(username = "user@gmail.com",password="password" )
  	public void testGetWelcomeSuccessWithValidUser() throws Exception 
    {
  	  mockMvc.perform(get("/welcome"))
  	  	.andDo(print())
  	  	.andExpect(view().name("redirect:/inbox/user@gmail.com"))
  	  	.andExpect(redirectedUrl("/inbox/user@gmail.com"));
    
    }  
    
    @Test
    @WithMockUser(username = "user@gmail.com",password="password" )
    public void viewMailTestWithValidUser() throws Exception
    {
    	
    	mockMvc.perform(get("/view").param("name", "user@gmail.com").param("id", "1"))
    	.andDo(print())
    	.andExpect(view().name("viewmail"))
    	.andExpect(model().attributeExists("mail"));
    	
    }
    
    @Test
    public void viewMailTestWithInvalidUser() throws Exception
    {
    	mockMvc.perform(get("/view").param("name", "dummyuser@gmail.com").param("id", "1"))
    	.andDo(print())
    	.andExpect(view().name("Exception"))
    	.andExpect(model().attribute("errMsg","Hello user You cannot view dummyuser@gmail.com mails" ));
    }
    
    @Test
    @WithMockUser(username = "user@gmail.com",password="testpassword" )
    public void showInboxTestSuccess() throws Exception
    {
    	
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
    
    //SENT MAILS TESTING
    @Test
    @WithMockUser(username = "user@gmail.com",password="password" )
    public void showSentMailsTestSuccessTwo() throws Exception
    {
    	
    	mockMvc.perform(get("/sent/{name}","user@gmail.com")).andDo(print())
    	.andExpect(status().isOk())
    	.andExpect(view().name("sent")).andExpect(model().attributeExists("mails"));
    }
    
    @Test
    public void showSentMailsTestFail() throws Exception
    {
    	
    	mockMvc.perform(get("/sent/{name}","dummyuser@gmail.com")).andDo(print())
    	.andExpect(status().isOk())
    	.andExpect(view().name("Exception"))
    	.andExpect(model().attribute("errMsg","Hello user You cannot access dummyuser@gmail.com sent mails"));
    }
    
    //TRASH TESTING
    @Test
    @WithMockUser(username = "user@gmail.com",password="password" )
    public void showTrashMailsSuccessTwo() throws Exception
    {
    	mockMvc.perform(get("/trash/{name}","user@gmail.com")).andDo(print())
    	.andExpect(status().isOk())
    	.andExpect(view().name("trash")).andExpect(model().attributeExists("mails"));
    }
    
    @Test
    public void showTrashMailsTestFail() throws Exception
    {
    	
    	mockMvc.perform(get("/trash/{name}","dummyuser@gmail.com")).andDo(print())
    	.andExpect(status().isOk())
    	.andExpect(view().name("Exception"))
    	.andExpect(model().attribute("errMsg","Hello user You cannot access dummyuser@gmail trash mails"));
    }
    
    //restore mail
    @Test
    @WithMockUser(username = "user@gmail.com",password="password" )
    public void restoreTrashMailsSuccess() throws Exception
    {
    	
      	  
    	mockMvc.perform(get("/restore").param("name","user@gmail.com").param("id","2")).andDo(print())
    	.andExpect(status().is3xxRedirection())
    	.andExpect(view().name("redirect:/trash/user@gmail.com"))
  	  	.andExpect(redirectedUrl("/trash/user@gmail.com"));
    	
    	/*Mail m = mailService.getById(2l);
    	assertEquals(m.getType(),"mail");*/
    	
    }
    
    @Test(expected = NestedServletException.class)
    @WithMockUser(username = "user@gmail.com",password="password" )
    public void restoretrashMailFailure() throws Exception
    {
    	mockMvc.perform(get("/restore").param("name","user@gmail.com").param("id","21"));
    }
    
    @Test
    public void restoreTrashMailsWithInvalidUSer() throws Exception
    {
    	mockMvc.perform(get("/restore").param("name","dummyuser@gmail.com").param("id","4"))
    	.andExpect(view().name("Exception"))
    	.andExpect(model().attributeExists("errMsg"));
    	
    }
    
    //delete mail controller
    
    @Test
    @WithMockUser(username = "user@gmail.com",password="password" )
    public void deleteMailSuccessByTypeChange() throws Exception
    {
    	mockMvc.perform(get("/delete").param("name","user@gmail.com").param("id","1").param("page","inbox").param("move","move")).andDo(print())
    	.andExpect(status().is3xxRedirection())
    	.andExpect(view().name("redirect:/inbox/user@gmail.com"))
  	  	.andExpect(redirectedUrl("/inbox/user@gmail.com"));
    	
    	Mail m = mailService.getById(1l);
    	assertEquals(m.getType(),"deleted");
    	
    }
    
    @Test
    @WithMockUser(username = "user@gmail.com",password="password" )
    public void deleteMailSuccessPerm() throws Exception
    {
    	mockMvc.perform(get("/delete").param("name","user@gmail.com").param("id","1").param("page","inbox").param("move","perm")).andDo(print())
    	.andExpect(status().is3xxRedirection())
    	.andExpect(view().name("redirect:/inbox/user@gmail.com"))
  	  	.andExpect(redirectedUrl("/inbox/user@gmail.com"));
    	
    	Mail m = mailService.getById(1l);
    	assertEquals(m,null);
    	
    }
    
    @Test(expected = NestedServletException.class)
    @WithMockUser(username = "user@gmail.com",password="password" )
    public void deleteMailFailure() throws Exception
    {
    	mockMvc.perform(get("/delete").param("name","user@gmail.com").param("id","21").param("page","inbox").param("move","perm"));
    }
    
    @Test
    public void deleteMailWithInvalidUSer() throws Exception
    {
    	
   	  
    	mockMvc.perform(get("/delete").param("name","dummyuser@gmail.com").param("id","1").param("page","inbox").param("move","perm"))
    	.andExpect(view().name("Exception"))
    	.andExpect(model().attribute("errMsg","Hello user You cannot delete dummyuser@gmail.com mails"));
    }
    
    // draft tests
    @Test
    @WithMockUser(username = "user@gmail.com",password="password" )
    public void showDraftsTestWithUserTwo() throws Exception
    {
    	mockMvc.perform(get("/draft/user@gmail.com"))
    	.andExpect(status().isOk())
    	.andDo(print())
    	.andExpect(view().name("draft"))
    	.andExpect(model().attributeExists("mails"));
    }
    
    @Test
    @WithMockUser(username = "user@gmail.com",password="password" )
    public void deleteDraftWithUserOne() throws Exception
    {
    	mockMvc.perform(get("/draft/delete/{name}/{id}","user@gmail.com",3))
    	.andDo(print())
    	.andExpect(status().is3xxRedirection())
    	.andExpect(view().name("redirect:/draft/user@gmail.com"))
  	  	.andExpect(redirectedUrl("/draft/user@gmail.com"));
    	
    	Mail m = mailService.getById(3l);
    	assertEquals(m, null);
    }
    
    @Test
    public void editDraftTestWithValidID() throws Exception
    {
    	mockMvc.perform(post("/edit").param("id","3"))
    	.andExpect(view().name("compose"))
    	.andExpect(model().attributeExists("compose"));
    	
    }
    
    @Test(expected=NestedServletException.class)
    public void editDraftTestWithInValidID() throws Exception
    {
    	mockMvc.perform(post("/edit").param("id","31"));
    	
    }
    
    @Test
    public void mailServiceTestgetAllMailsByToAndTypeSuccess()
    {
    	List<Mail> m = mailService.getAllMailsByToAndType("user@gmail.com", "mail");
    	assertEquals(m.size(), 1);
    	assertEquals(m.get(0).getToAddress(),"user@gmail.com");
    }
    
    public void mailServiceTestgetAllMailsByToAndTypeFailure() throws EntityNotFoundException
    {
    	List<Mail> m = mailService.getAllMailsByToAndType("user@gmail.com", "undefined");
    	assertEquals(m.size(), 0);
    }
}

