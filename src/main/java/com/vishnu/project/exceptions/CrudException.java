package com.vishnu.project.exceptions;

public class CrudException extends RuntimeException
{
	
	private static final long serialVersionUID = 1L;
		private String type;
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		private String errMsg;
		
		public String getErrMsg() {
			return errMsg;
		}

		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}
		
		public CrudException(String errMsg) 
		{
			
			this.errMsg = errMsg;
		}
	

}
