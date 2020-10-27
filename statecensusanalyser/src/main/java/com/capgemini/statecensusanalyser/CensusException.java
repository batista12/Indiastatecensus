package com.capgemini.statecensusanalyser;
/**
 * @author ASUS
 *
 */
enum ExceptionType {
	STATE_CENSUS_FILE_PROBLEM, INCORRECT_TYPE, SOME_OTHER_ERRORS
}

@SuppressWarnings("serial")
public class CensusException extends Exception {
	public static final Object[] STATE_CENSUS_FILE_PROBLEM = null;
	public static final Object[] INCORRECT_TYPE = null;
	public Object[] exceptionType;

	public CustomStateCensusAnalyserException(String message, ExceptionType exceptionType) {
		super(exceptionType.toString());
	}
}
