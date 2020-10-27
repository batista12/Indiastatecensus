package com.capgemini.statecensusanalyser;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;

public class StateCensusAnalyser {
	public int loadStateCensusData(String csvfilePath) throws CustomStateCensusAnalyserException {
		if (!(csvfilePath.matches(".*\\.csv$")))
			throw new CustomStateCensusAnalyserException("Incorrect Type", ExceptionType.INCORRECT_TYPE);
		try (Reader reader = Files.newBufferedReader(Paths.get(csvfilePath));) {

			Iterator<CSVStateCensus> censusCsvIterator = getIteratorFromCsv(reader, CSVStateCensus.class);
			return getCountFromIterator(censusCsvIterator);
		} catch (IOException e) {
			throw new CustomStateCensusAnalyserException("Incorrect CSV File", ExceptionType.STATE_CENSUS_FILE_PROBLEM);
		}
	}

	public int loadStateCodeData(String csvFilePath) throws CustomStateCensusAnalyserException {
		if (!(csvFilePath.matches(".*\\.csv$")))
			throw new CustomStateCensusAnalyserException("Incorrect Type", ExceptionType.INCORRECT_TYPE);
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {

			Iterator<CSVStates> censusCsvIterator = getIteratorFromCsv(reader, CSVStates.class);
			return getCountFromIterator(censusCsvIterator);
		} catch (IOException e) {
			throw new CustomStateCensusAnalyserException("Incorrect CSV File", ExceptionType.STATE_CENSUS_FILE_PROBLEM);
		}
	}

	public <T> Iterator<T> getIteratorFromCsv(Reader reader, Class<T> csvBindedClass)
			throws CustomStateCensusAnalyserException {
		try {

			CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvBindedClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<T> csvToBean = csvToBeanBuilder.build();
			Iterator<T> censusCsvIterator = csvToBean.iterator();
			return censusCsvIterator;
		} catch (RuntimeException e) {

			throw new CustomStateCensusAnalyserException("Wrong Delimiter or Header", ExceptionType.SOME_OTHER_ERRORS);
		}
	}

	public <T> int getCountFromIterator(Iterator<T> csvIterator) {
		Iterable<T> csvIterable = () -> csvIterator;
		int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEntries;
	}
}
