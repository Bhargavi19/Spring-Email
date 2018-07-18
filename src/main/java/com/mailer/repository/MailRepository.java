package com.mailer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mailer.model.Mail;

/**
 * This repository extends JpaRepository in which custom methods are declared depending upon our requirements
 * @author vishnu
 *
 */
public interface MailRepository extends JpaRepository<Mail,Long>
{
	
	List<Mail> findByToAddressAndType(String to,String type);
	
	
	List<Mail> findByFromAddressAndType(String from, String type);
	
	
	
	

}
