package com.vishnu.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vishnu.project.model.Mail;


public interface MailRepository extends JpaRepository<Mail,Long>
{
	
	List<Mail> findByToaAndMt(String toa,String type);
	
	
	List<Mail> findByFrmaAndMt(String from, String type);
	
	//List<Mail> findByFrmaOrToaAndMt(String frma, String toa, String mt);
	
	

}
