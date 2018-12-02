package com.email.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mail")
public class Mail
{
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		
		private Long id;

		//@Column(name = "from")
		private String fromAddress;

		//@Column(name = "to")
		private String toAddress;

		//@Column(name = "subject")
		private String subject;
		
		//@Column(name="body")
		private String body;
		
		//@Column(name="type")
		private String type;
		
		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getFromAddress() {
			return fromAddress;
		}


		public void setFromAddress(String fromAddress) {
			this.fromAddress = fromAddress;
		}


		public String getToAddress() {
			return toAddress;
		}


		public void setToAddress(String toAddress) {
			this.toAddress = toAddress;
		}


		public String getSubject() {
			return subject;
		}


		public void setSubject(String subject) {
			this.subject = subject;
		}


		public String getBody() {
			return body;
		}


		public void setBody(String body) {
			this.body = body;
		}


		public String getType() {
			return type;
		}


		public void setType(String type) {
			this.type = type;
		}

		public Mail(Long id, String fromAddress, String toAddress, String subject, String body, String type) {
			super();
			this.id = id;
			this.fromAddress = fromAddress;
			this.toAddress = toAddress;
			this.subject = subject;
			this.body = body;
			this.type = type;
		}


		public Mail(String fromAddress, String toAddress, String subject, String body, String type) {
			super();
			this.fromAddress = fromAddress;
			this.toAddress = toAddress;
			this.subject = subject;
			this.body = body;
			this.type = type;
		}


		public Mail() {
			// TODO Auto-generated constructor stub
		}

	
}