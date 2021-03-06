package com.email.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.email.model.Mail;
import com.email.repository.MailRepository;
import com.email.service.MailService;

@Service
public class MailServiceImpl implements MailService 
{
	
	@Autowired 
	private MailRepository mailRepository;
	
	
	@Override
	public List<Mail> getAllMailsByToAndType(String toa,String type) 
	{
		
			 
			//return  mailRepository.findByToaAndMt(toa,type); 
			return mailRepository.findByToAddressAndType(toa, type);
		
	}
	
	
	@Override
	public List<Mail> getAllMailsByFromAndType(String from, String type) 
	{
		
		//return mailRepository.findByFrmaAndMt(from,type);
		return mailRepository.findByFromAddressAndType(from, type);
	}
	

	@Override
	public Mail getById(Long id) 
	{
		return mailRepository.findOne(id);
	}
	
	
	@Override
	public void save(Mail mail) 
	{
		mailRepository.save(mail);
		
	}
	

	@Override
	public void deleteMail(Long id) {
		mailRepository.delete(id);
		
	}

	

}
