package com.mailer.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mailer.model.Mail;
import com.mailer.repository.MailRepository;
import com.mailer.service.MailService;
/**
 * 
 * @author vishnu
 * 
 * This class is the concrete class of MailService i.e, this class implements the MailService class
 * It overrides the methods of MailService and therefore providing the implementation
 *
 */
@Service
public class MailServiceImpl implements MailService 
{
	
	@Autowired 
	private MailRepository mailRepository;
	
	/**
	 * This method returns the list of Mail objects depending upon to address and mail type
	 * @param String toa is the to address 
	 * @param String type is the mail type
	 * @return List<Mail> This method returns the list of Mail objects filtered by to address and type
	 */
	@Override
	public List<Mail> getAllMailsByToAndType(String toa,String type) 
	{
		
			 
			//return  mailRepository.findByToaAndMt(toa,type); 
			return mailRepository.findByToAddressAndType(toa, type);
		
	}
	
	/**
	 * This method returns the list of Mail objects depending upon from address and mail type
	 * @param String from is the from address
	 * @param String type is the mail type
	 * @return List<Mail> This method returns the list of Mail objects filtered by from address and type 
	 */
	@Override
	public List<Mail> getAllMailsByFromAndType(String from, String type) 
	{
		
		//return mailRepository.findByFrmaAndMt(from,type);
		return mailRepository.findByFromAddressAndType(from, type);
	}
	
	/**
	 * This method returns the Mail object depending upon the mail id
	 * @param  id is the id of the mail
	 * @return Mail returns the Mail object retrieved by id
	 */
	@Override
	public Mail getById(Long id) 
	{
		return mailRepository.findOne(id);
	}
	
	/**
	 * This method saves the mail into mail table
	 * @param  mail is the mail pojo that has to be saved
	 */
	@Override
	public void save(Mail mail) 
	{
		mailRepository.save(mail);
		
	}
	
	/**
	 * This method deletes the mail based on id of mail
	 * @param id is the ID of the mail that has to be deleted
	 */
	@Override
	public void deleteMail(Long id) {
		mailRepository.delete(id);
		
	}

	

}
