package com.vishnu.project.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishnu.project.model.Mail;
import com.vishnu.project.repository.MailRepository;
import com.vishnu.project.service.MailService;

@Service
public class MailServiceImpl implements MailService 
{
	
	@Autowired 
	private MailRepository mailRepository;
	
	
	@Override
	public List<Mail> getAllMailsByToaAndMt(String toa,String type) 
	{
		
			 
			return  mailRepository.findByToaAndMt(toa,type); 
		
	}
	
	@Override
	public List<Mail> getAllMailsByFromaddAndType(String from, String type) 
	{
		
		return mailRepository.findByFrmaAndMt(from,type);
	}
	@Override
	public Mail getById(Long id) 
	{
		return mailRepository.findOne(id);
	}
	@Override
	public void saveMail(Mail mail) 
	{
		mailRepository.save(mail);
		
	}
	@Override
	public void deleteMail(Long id) {
		mailRepository.delete(id);
		
	}

}
