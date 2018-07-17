package com.mailer.exception;
/**
 * 
 * @author vishnu
 *
 */
public class UserAccessException extends RuntimeException
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
