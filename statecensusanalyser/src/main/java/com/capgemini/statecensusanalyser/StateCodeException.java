package com.capgemini.statecensusanalyser;

enum ExceptionTypeStateCode {
	STATE_CODE_FILE_PROBLEM, STATE_CODE_PARSE_PROBLEM, STATE_CODE_DELIMITER_PROBLEM
}

@SuppressWarnings("serial")
public class StateCodeException extends Exception {
	public StateCodeException(ExceptionTypeStateCode exceptionType) {
		super(exceptionType.toString());
	}
}
