import com.capgemini.statecensusanalyser
import java.io.Reader;
import java.util.Iterator;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCsvBuilder {
	public <T> Iterator<T> getIteratorFromCsv(Reader reader, Class<T> csvBindedClass) throws CustomStateCensusAnalyserException {
		try {
			
			CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvBindedClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<T> csvToBean = csvToBeanBuilder.build();
			Iterator<T> censusCsvIterator = csvToBean.iterator();
			return censusCsvIterator;
		} 
		catch (RuntimeException e) {

			throw new CustomStateCensusAnalyserException("Wrong Delimiter or Header", ExceptionType.SOME_OTHER_ERRORS);
		}
	}
}
