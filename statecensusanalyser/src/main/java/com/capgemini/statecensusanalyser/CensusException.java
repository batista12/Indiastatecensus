package com.capgemini.statecensusanalyser;
/**
 * @author ASUS
 *
 */
public class CensusException extends Exception {
	public enum ExceptionType{
		INVALID_FILE_PATH,INVALID_CLASS_TYPE, INVALID_DELIMITER, INVALID_HEADER
	}
	public ExceptionType type;
	
	public CensusException(String message,ExceptionType type) {
		super(message);
		this.type = type;
	}
}
