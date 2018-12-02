package com.email.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.email.model.Mail;


public interface MailRepository extends JpaRepository<Mail,Long>
{
	
	List<Mail> findByToAddressAndType(String to,String type);
	
	
	List<Mail> findByFromAddressAndType(String from, String type);
	
	
	
	

}
