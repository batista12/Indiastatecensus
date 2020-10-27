import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.CsvBadConverterException;

import org.junit.Assert;
import org.junit.Test;

public class StateCodeTest {
	private final String STATE_CODE_CSV_FILE = "./src/main/resources/StateCodeCSVData.csv";
	private final String INCORRECT_STATE_CODE_CSV_FILE = "./src/main/resources/_StateCodeCSVData.csv";
	private final String INCORRECT_HEADER_STATE_CODE_CSV_FILE = "./src/main/resources/StateCodeCSVDataIncorrectHeader.csv";

	@Test
	public void givenTheStatesCodeCSVFile_WhenRead_NoOfRecordsShouldMatch()
			throws CustomStateCodeAnalyserException, CustomFileIOException {
		StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
		MappingStrategy<CSVStates> mappingStrategy = new HeaderColumnNameMappingStrategy<CSVStates>();
		mappingStrategy.setType(CSVStates.class);
		int numOfRecords = stateCensusAnalyser.loadStateCodeData(STATE_CODE_CSV_FILE, mappingStrategy, CSVStates.class,
				',');
		Assert.assertEquals(5, numOfRecords);
	}

	@Test
	public void givenIncorrectCSVFile_ShouldReturnCustomException() {
		String exceptionMessage = null;
		try {
			StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
			MappingStrategy<CSVStates> mappingStrategy = new HeaderColumnNameMappingStrategy<CSVStates>();
			mappingStrategy.setType(CSVStates.class);
			stateCensusAnalyser.loadStateCodeData(INCORRECT_STATE_CODE_CSV_FILE, mappingStrategy, CSVStates.class, ',');
		} catch (CustomFileIOException e) {
			exceptionMessage = e.getMessage();
		}
		Assert.assertEquals(ExceptionTypeStateCode.STATE_CODE_FILE_PROBLEM.toString(), exceptionMessage);
	}

	@Test
	public void givenIncorrectCSVType_ShouldReturnCustomException() {
		String exceptionMessage = null;
		try {
			StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
			stateCensusAnalyser.loadStateCodeData(STATE_CODE_CSV_FILE, null, null, ',');
		} catch (CustomFileIOException e) {
			exceptionMessage = e.getMessage();
		}
		Assert.assertEquals(ExceptionTypeStateCode.STATE_CODE_PARSE_PROBLEM.toString(), exceptionMessage);
	}

	@Test
	public void givenCorrectCSVFileIncorrectDelimiter_ShouldReturnCustomException() {
		String exceptionMessage = null;
		try {
			StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
			MappingStrategy<CSVStates> mappingStrategy = new HeaderColumnNameMappingStrategy<CSVStates>();
			mappingStrategy.setType(CSVStates.class);
			stateCensusAnalyser.loadStateCodeData(STATE_CODE_CSV_FILE, mappingStrategy, CSVStates.class, '|');
		} catch (CustomFileIOException e) {
			exceptionMessage = e.getMessage();
		}
		Assert.assertEquals(ExceptionTypeStateCode.STATE_CODE_HEADER_OR_DELIMITER_PROBLEM.toString(), exceptionMessage);
	}

	@Test
	public void givenCorrectCSVFileIncorrectHeader_ShouldReturnCustomException() {
		String exceptionMessage = null;
		try {
			StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
			MappingStrategy<CSVStates> mappingStrategy = new HeaderColumnNameMappingStrategy<CSVStates>();
			mappingStrategy.setType(CSVStates.class);
			stateCensusAnalyser.loadStateCodeData(INCORRECT_HEADER_STATE_CODE_CSV_FILE, mappingStrategy,
					CSVStates.class, ',');
		} catch (CustomFileIOException e) {
			exceptionMessage = e.getMessage();
		}
		Assert.assertEquals(ExceptionTypeStateCode.STATE_CODE_HEADER_OR_DELIMITER_PROBLEM.toString(), exceptionMessage);
	}
}
