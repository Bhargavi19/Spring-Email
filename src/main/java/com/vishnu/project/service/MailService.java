package com.vishnu.project.service;

import java.util.List;

import com.vishnu.project.model.Mail;

public interface MailService 
{
	List<Mail> getAllMailsByToaAndMt(String to,String type);
	List<Mail> getAllMailsByFromaddAndType(String from, String type);
	Mail getById(Long id);
	void saveMail(Mail mail);
	void deleteMail(Long id);
}
