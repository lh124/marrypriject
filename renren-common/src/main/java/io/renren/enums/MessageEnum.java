package io.renren.enums;

public enum MessageEnum {

	MESSAGE_ERROR("","","","");
	
	private String message;
	private String code;
	private String errorType;
	private String errorAddress;
	
	
	private MessageEnum(String message, String code, String errorType,
			String errorAddress) {
		this.message = message;
		this.code = code;
		this.errorType = errorType;
		this.errorAddress = errorAddress;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getErrorType() {
		return errorType;
	}


	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}


	public String getErrorAddress() {
		return errorAddress;
	}


	public void setErrorAddress(String errorAddress) {
		this.errorAddress = errorAddress;
	}
	
	
	
}
