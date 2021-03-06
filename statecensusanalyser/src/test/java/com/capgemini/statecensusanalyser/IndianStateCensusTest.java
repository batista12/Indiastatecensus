package com.capgemini.statecensusanalyser;
import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Test;
public class CensusAnalyserTest {
	
	public static final String RIGHT_CENSUS_CSV = "./src/main/resources/StateCensusCsvData(2).csv";
	public static final String WRONG_CENSUS_CSV = "file/IndiaStateCensusDatae.csv";
	public static final String WRONGTYPE_CENSUS_CSV = "file/IndiaStateCensusData-WrongType.pdf";
	public static final String WRONGDELIMITER_CENSUS_CSV = "file/IndiaStateCensusData-WrongDelimiter.csv";
	public static final String WRONGHEADER_CENSUS_CSV = "file/IndiaStateCensusData-WrongHeader.csv";
	public static final String RIGHT_STATE_CODE_CSV = "./src/main/resources/StateCodeCSVData.csv";
	public static final String WRONG_STATE_CODE_CSV =  "./src/main/resources/_StateCodeCSVData.csv";
	public static final String WRONGTYPE_STATE_CODE_CSV ="./src/main/resources/_StateCodeCSVData.csv";
	public static final String WRONGDELIMITER_STATE_CODE_CSV ="./src/main/resources/_StateCodeCSVData.csv";
	public static final String WRONGHEADER_STATE_CODE_CSV = "./src/main/resources/_StateCodeCSVData.csv";
	@Test
	public void givenIndiaCensusDataCsv_ShouldReturnExactCount() {
		try {
			int recordsCount = new StateCensusAnalyser().loadStateCensusData(RIGHT_CENSUS_CSV);
			assertEquals(6, recordsCount);
		}
		catch (CustomStateCensusAnalyserException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void givenWrongCsvFile_ShouldThrowCensusAnalyserExceptionOfTypeCensusFileProblem() {
		try {
			new StateCensusAnalyser().loadStateCensusData(WRONG_CENSUS_CSV );
		}
		catch(CustomStateCensusAnalyserException e) {
			assertEquals(CustomStateCensusAnalyserException.STATE_CENSUS_FILE_PROBLEM, e.exceptionType);
		}
	}
	
	@Test
	public void givenWrongTypeCsvFile_ShouldThrowCensusAnalyserExceptionOfTypeIncorrectType() {
		try {
			new StateCensusAnalyser().loadStateCensusData(WRONGTYPE_CENSUS_CSV );
		}
		catch(CustomStateCensusAnalyserException e) {
			assertEquals(CustomStateCensusAnalyserException.INCORRECT_TYPE, e.exceptionType);
		}
	}
	
	@Test
	public void givenCsvFileIncorrectDelimiter_ShouldThrowCensusAnalyserExceptionOfTypeIncorrectDelimiter() {
		try {

			new StateCensusAnalyser().loadStateCensusData(WRONGDELIMITER_CENSUS_CSV);
		}
		catch(CustomStateCensusAnalyserException e) {
			assertEquals(ExceptionType.SOME_OTHER_ERRORS, e.exceptionType);
		}
	}
	
	@Test
	public void givenCsvFileIncorrectHeader_ShouldThrowCensusAnalyserExceptionOfTypeIncorrectHeader() {
		try {		
			new StateCensusAnalyser().loadStateCensusData(WRONGHEADER_CENSUS_CSV );
		}
		catch(CustomStateCensusAnalyserException e) {
			assertEquals(ExceptionType.SOME_OTHER_ERRORS, e.exceptionType);
		}
	}
	
	@Test
	public void givenIndiaStateCodeCsv_ShouldReturnExactCount() {
		try {
			int recordsCount = new StateCensusAnalyser().loadStateCensusData(RIGHT_STATE_CODE_CSV);
			assertEquals(37, recordsCount);
		}
		catch (CustomStateCensusAnalyserException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void givenWrongIndiaStateCode_ShouldThrowCensusAnalyserExceptionOfTypeCensusFileProblem() {
		try {
			new StateCensusAnalyser().loadStateCensusData(WRONG_STATE_CODE_CSV);
		}
		catch(CustomStateCensusAnalyserException e) {
			assertEquals(ExceptionType.STATE_CENSUS_FILE_PROBLEM, e.exceptionType);
		}
	}
	
	@Test
	public void givenWrongTypeIndiaStateCode_ShouldThrowCensusAnalyserExceptionOfTypeIncorrectType() {
		try {
			new StateCensusAnalyser().loadStateCensusData(WRONGTYPE_STATE_CODE_CSV );
		}
		catch(CustomStateCensusAnalyserException e) {
			assertEquals(ExceptionType.INCORRECT_TYPE, e.exceptionType);
		}
	}
	
	@Test
	public void givenIndiaStateCodeIncorrectDelimiter_ShouldThrowCensusAnalyserExceptionOfTypeIncorrectDelimiter() {
		try {

			new StateCensusAnalyser().loadStateCensusData(WRONGDELIMITER_STATE_CODE_CSV  );
		}
		catch(CustomStateCensusAnalyserException e) {
			assertEquals(ExceptionType.SOME_OTHER_ERRORS, e.exceptionType);
		}
	}
	
	@Test
	public void givenIndiaStateCodeIncorrectHeader_ShouldThrowCensusAnalyserExceptionOfTypeIncorrectHeader() {
		try {		
			new StateCensusAnalyser().loadStateCensusData(WRONGHEADER_STATE_CODE_CSV );
		}
		catch(CustomStateCensusAnalyserException e) {
			assertEquals(ExceptionType.SOME_OTHER_ERRORS, e.exceptionType);
		}
	}
}

