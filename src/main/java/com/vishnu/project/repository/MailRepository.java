package com.vishnu.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vishnu.project.model.Mail;


public interface MailRepository extends JpaRepository<Mail,Long>
{
	
	List<Mail> findByToaAndMt(String toa,String type);
	
	
	List<Mail> findByFrmaAndMt(String from, String type);
	
	
	
	

}
