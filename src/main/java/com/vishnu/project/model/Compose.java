package com.vishnu.project.model;

public class Compose {
	private long id;
	private String to;
	private String subject;
	private String body;
	public String getTo() 
	{
		return to;
	}
	public Compose()
	{
		
	}
	public Compose(long id,String to, String subject, String body) {
		super();
		this.id = id;
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setTo(String to) {
		this.to = to;
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
}
