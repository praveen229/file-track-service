package com.qcom.search.exception;

@SuppressWarnings("serial")
public class TagsFavsServiceException extends Exception {

	/**
	 * This exception is thrown when the requested url is not found.
	 * 
	 * @author c_pdivam
	 */

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public TagsFavsServiceException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public TagsFavsServiceException() {
		super();
	}

}
