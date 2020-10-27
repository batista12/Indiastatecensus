package com.capgemini.statecensusanalyser;
import com.opencsv.bean.CsvBindByName;

/**
 * @author ASUS
 *
 */
public class IndiaStateCensus {
	@CsvBindByName (column = "State")
	private String stateName;

	@CsvBindByName (column = "Population")
	private String population;

	@CsvBindByName (column = "AreaInSqKm")
	private String area;

	@CsvBindByName (column = "DensityPerSqKm")
	private String density;

	@Override
	public String toString() {
		return "IndiaStateCensus [stateName=" + stateName + ", population=" + population + ", area=" + area
				+ ", density=" + density + "]";
	}
}

