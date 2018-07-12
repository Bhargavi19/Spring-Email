/*package com.vishnu.project.testing.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vishnu.project.controllers.MailController;
import com.vishnu.project.service.MailService;
import com.vishnu.project.testing.ContextLoadingTests;

public class ControllerTesting extends ContextLoadingTests 
{
		
	
	    @Autowired
	    private WebApplicationContext webApplicationContext;
	    
	    
	    
	    private MockMvc mockMvc;
	    
	    @Before
	    public void setUp()
	    {
	    	   mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	    }
	    
	    @Test
	    public void testList() throws Exception 
	    {
	        //assertThat(this.service).isNotNull();
	        
	        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/inbox/vishnu@gmail.com"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("inbox"))
	                .andDo(print());
	    }

}
*/