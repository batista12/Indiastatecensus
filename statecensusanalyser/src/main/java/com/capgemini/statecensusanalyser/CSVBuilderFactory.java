package com.capgemini.statecensusanalyser;

public class CSVBuilderFactory {
	public static ICsvBuilder createBuilder() {
		return (ICsvBuilder) new OpenCsvBuilder();
	}

}
