package com.capgemini.statecensusanalyser;
import com.capgemini.opencsvbuilder.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;
import com.opencsv.bean.MappingStrategy;

public class StateCensusAnalyser {

	List<CSVStateCensus> csvStateCensusList;

	public int loadStateCensusData(String csvFilePath, MappingStrategy<CSVStateCensus> mappingStrategy,
			Class<? extends CSVStateCensus> csvBinderClass, final char separator)
			throws CustomFileIOException, CustomCSVBuilderException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICsvBuilder csvBuilder = new CsvBuilderFactory().createCSVBuilder();
			if (csvBinderClass != null)
				csvStateCensusList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class, mappingStrategy,
						separator);
			else
				csvStateCensusList = csvBuilder.getCSVFileList(reader, null, null, separator);
			return csvStateCensusList.size();
		} catch (IOException e) {
			throw new CustomFileIOException(ExceptionTypeIO.FILE_PROBLEM);
		}
	}

	public int loadStateCodeData(String csvFilePath, MappingStrategy<CSVStates> mappingStrategy,
			Class<? extends CSVStates> csvBinderClass, final char separator)
			throws CustomFileIOException, CustomCSVBuilderException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			List<CSVStates> csvStateCodeList;
			ICsvBuilder csvBuilder = new CsvBuilderFactory().createCSVBuilder();
			if (csvBinderClass != null)
				csvStateCodeList = csvBuilder.getCSVFileList(reader, CSVStates.class, mappingStrategy, separator);
			else
				csvStateCodeList = csvBuilder.getCSVFileList(reader, null, null, separator);
			return csvStateCodeList.size();
		} catch (IOException e) {
			throw new CustomFileIOException(ExceptionTypeIO.FILE_PROBLEM);
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
}
