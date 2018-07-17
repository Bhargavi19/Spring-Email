package com.vishnu.project.testing;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vishnu.project.MailerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MailerApplication.class)
public abstract class MailerAbstractTestClass 
{
	
}