package com.capgemini.statecensusanalyser;

import com.opencsv.bean.CsvBindByName;

public class StateCode {
	@CsvBindByName(column = "State", required = true)
	public String state;

	@CsvBindByName(column = "Code", required = true)
	public String code;

	@Override
	public String toString() {
		return "StateCode [state=" + state + ", code=" + code + "]";
	}

}
