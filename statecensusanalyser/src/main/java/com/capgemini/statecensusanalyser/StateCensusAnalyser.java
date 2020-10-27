package com.capgemini.statecensusanalyser;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * @author ASUS
 *
 */
public class StateCensusAnalyser {
	public int loadCensusData(String csvfilePath) throws CustomStateCensusAnalyserException {
		try {	
			Reader reader;
			reader = Files.newBufferedReader(Paths.get(csvfilePath));
			CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(CSVStateCensus.class);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<CSVStateCensus> csvToBean = csvToBeanBuilder.build();
			Iterator<CSVStateCensus> csvStateCensusIterator = csvToBean.iterator();
			Iterable<CSVStateCensus> csvStateCensusIterable = () -> csvStateCensusIterator;
			int numOfEntries = (int) StreamSupport.stream(csvStateCensusIterable.spliterator(), false).count();
			return numOfEntries;
		} catch (IOException e) {
			throw new CustomStateCensusAnalyserException(ExceptionType.STATE_CENSUS_FILE_PROBLEM);
		}
		catch(IllegalStateException e) {
			throw new CustomStateCensusAnalyserException(ExceptionType.STATE_CENSUS_PARSE_PROBLEM);
		}
	}
}
