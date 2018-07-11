package com.vishnu.project.exceptions;

public class UserAccessException extends RuntimeException
{
	
	private String errMsg;

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public UserAccessException(String errMsg) 
	{
		this.errMsg = errMsg;
	}
}
