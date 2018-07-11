package com.vishnu.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mail")
public class Mail
{
		@Id
		@GeneratedValue
		private Long id;

		@Column(name = "frma")
		private String frma;

		@Column(name = "toa")
		private String toa;

		@Column(name = "sbjt")
		private String sbjt;
		
		@Column(name="body")
		private String body;
		
		@Column(name="mt")
		private String mt;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFrma() {
			return frma;
		}

		public void setFrma(String frma) {
			this.frma = frma;
		}

		public String getToa() {
			return toa;
		}

		public void setToa(String toa) {
			this.toa = toa;
		}

		public String getSbjt() {
			return sbjt;
		}

		public void setSbjt(String sbjt) {
			this.sbjt = sbjt;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public String getMt() {
			return mt;
		}

		public void setMt(String mt) {
			this.mt = mt;
		}
		public Mail()
		{
			
		}
		public Mail(Long id, String frma, String toa, String sbjt, String body, String mt) {
			super();
			this.id = id;
			this.frma = frma;
			this.toa = toa;
			this.sbjt = sbjt;
			this.body = body;
			this.mt = mt;
		}
		public Mail(String frma, String toa, String sbjt, String body, String mt) {
			super();
			
			this.frma = frma;
			this.toa = toa;
			this.sbjt = sbjt;
			this.body = body;
			this.mt = mt;
		}

	
}