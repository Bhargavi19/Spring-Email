/*package com.vishnu.project.testing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.RequestBuilder;
@RunWith(SpringRunner.class)
public class myTest extends Auth 
{
	@Autowired
	MockHttpSession session;
	
     @Test
     public void test() throws Exception
     { 
         setAuthentication("vishnu@gmail.com","vishnu123",session);
         
         RequestBuilder requestBuilder = get("http://localhost:8080/welcome");
 	    mockMvc.perform(requestBuilder).andExpect(redirectedUrl("/login"));

     }

}*/