package com.email.service;

import java.util.List;

import com.email.model.Mail;

public interface MailService 
{
	List<Mail> getAllMailsByToAndType(String to,String type);
	List<Mail> getAllMailsByFromAndType(String from, String type);
	Mail getById(Long id);
	void save(Mail mail);
	void deleteMail(Long id);
}
