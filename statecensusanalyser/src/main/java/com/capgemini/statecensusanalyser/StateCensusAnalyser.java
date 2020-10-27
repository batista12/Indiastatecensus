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
	public int loadStateCodeData(String csvFilePath, MappingStrategy<CSVStates> mappingStrategy,
			Class<? extends CSVStates> csvBinderClass, final char separator) throws CustomStateCodeAnalyserException {
		try {
			Reader reader;
			reader = Files.newBufferedReader(Paths.get(csvFilePath));
			CsvToBeanBuilder<CSVStates> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withMappingStrategy(mappingStrategy);
			csvToBeanBuilder.withType(csvBinderClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			csvToBeanBuilder.withSeparator(separator);
			CsvToBean<CSVStates> csvToBean = csvToBeanBuilder.build();
			Iterator<CSVStates> csvStateCodeIterator = csvToBean.iterator();
			Iterable<CSVStates> csvStateCodeIterable = () -> csvStateCodeIterator;
			int numOfEntries = (int) StreamSupport.stream(csvStateCodeIterable.spliterator(), false).count();
			return numOfEntries;
		} catch (IOException e) {
			throw new CustomStateCodeAnalyserException(ExceptionTypeStateCode.STATE_CODE_FILE_PROBLEM);
		} catch (IllegalStateException e) {
			throw new CustomStateCodeAnalyserException(ExceptionTypeStateCode.STATE_CODE_PARSE_PROBLEM);
		} catch (RuntimeException e) {
			throw new CustomStateCodeAnalyserException(ExceptionTypeStateCode.STATE_CODE_HEADER_OR_DELIMITER_PROBLEM);
		}
}
	public String getAlpahebeticalStateWiseCensusData() {
		Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.state);
		this.sort(censusComparator);
		String sortedStateCensus = new Gson().toJson(csvStateCensusList);
		return sortedStateCensus;
	}

	private void sort(Comparator<CSVStateCensus> censusComparator) {
		for (int i = 0; i < csvStateCensusList.size(); i++) {
			for (int j = 0; j < csvStateCensusList.size() - i - 1; j++) {
				CSVStateCensus censusOne = csvStateCensusList.get(j);
				CSVStateCensus censusTwo = csvStateCensusList.get(j + 1);
				if (censusComparator.compare(censusOne, censusTwo) > 0) {
					csvStateCensusList.set(j, censusTwo);
					csvStateCensusList.set(j + 1, censusOne);
				}
			}
		}
	}	
