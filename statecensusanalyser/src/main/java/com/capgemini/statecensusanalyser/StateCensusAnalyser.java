package com.capgemini.statecensusanalyser;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import com.capgemini.opencsvbuilder.CSVBuilderFactory;
import com.capgemini.opencsvbuilder.CustomCSVBuilderException;
import com.capgemini.opencsvbuilder.ICSVBuilder;
import com.google.gson.Gson;
import com.opencsv.bean.MappingStrategy;

public class StateCensusAnalyser {

	List<CSVStateCensus> csvStateCensusList;
	List<CSVStates> csvStateCodeList;

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

	private void sortDescending(Comparator<CSVStateCensus> censusComparator) {
		for (int i = 0; i < csvStateCensusList.size(); i++) {
			for (int j = 0; j < csvStateCensusList.size() - i - 1; j++) {
				CSVStateCensus censusOne = csvStateCensusList.get(j);
				CSVStateCensus censusTwo = csvStateCensusList.get(j + 1);
				if (censusComparator.compare(censusOne, censusTwo) < 0) {
					csvStateCensusList.set(j, censusTwo);
					csvStateCensusList.set(j + 1, censusOne);
				}
			}
		}
	}

	public String getAlpahebeticalStateCodeWiseData() {
		Comparator<CSVStates> codeComparator = Comparator.comparing(codelist -> codelist.code);
		this.sortCodeWise(codeComparator);
		String sortedStateCodeList = new Gson().toJson(csvStateCodeList);
		return sortedStateCodeList;
	}

	private void sortCodeWise(Comparator<CSVStates> codeComparator) {
		for (int i = 0; i < csvStateCodeList.size(); i++) {
			for (int j = 0; j < csvStateCodeList.size() - i - 1; j++) {
				CSVStates codeEntryOne = csvStateCodeList.get(j);
				CSVStates codeEntryTwo = csvStateCodeList.get(j + 1);
				if (codeComparator.compare(codeEntryOne, codeEntryTwo) > 0) {
					csvStateCodeList.set(j, codeEntryTwo);
					csvStateCodeList.set(j + 1, codeEntryOne);
				}
			}
		}

	}

	public String getPopulationWiseCensusDataAndWriteToJsonFile(String jsonFilePath) throws IOException {
		FileWriter fileWriter = new FileWriter(jsonFilePath);
		Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.population);
		this.sortDescending(censusComparator);
		String sortedStateCensus = new Gson().toJson(csvStateCensusList);
		fileWriter.write(sortedStateCensus);
		fileWriter.close();
		return sortedStateCensus;
	}

	public String getPopulationDensityWiseCensusDataAndWriteToJsonFile(String jsonFilePath) throws IOException {
		FileWriter fileWriter = new FileWriter(jsonFilePath);
		Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
		this.sortDescending(censusComparator);
		String sortedStateCensus = new Gson().toJson(csvStateCensusList);
		fileWriter.write(sortedStateCensus);
		fileWriter.close();
		return sortedStateCensus;
	}
	public String getAreaWiseCensusDataAndWriteToJsonFile(String jsonFilePath) throws IOException {
		FileWriter fileWriter = new FileWriter(jsonFilePath);
		Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
		this.sortDescending(censusComparator);
		String sortedStateCensus = new Gson().toJson(csvStateCensusList);
		fileWriter.write(sortedStateCensus);
		fileWriter.close();
		return sortedStateCensus;
	}
}
