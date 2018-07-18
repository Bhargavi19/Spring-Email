package com.mailer.service;

import java.util.List;

import com.mailer.model.Mail;
/**
 * This interface provides the abstract methods that has to be implemented by service implementation classes
 * @author vishnu
 * 
 */
public interface MailService 
{
	List<Mail> getAllMailsByToAndType(String to,String type);
	List<Mail> getAllMailsByFromAndType(String from, String type);
	Mail getById(Long id);
	void saveMail(Mail mail);
	void deleteMail(Long id);
}
