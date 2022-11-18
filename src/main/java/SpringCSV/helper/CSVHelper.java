package SpringCSV.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import SpringCSV.model.DeveloperTutorial;

public class CSVHelper {

	public static String TYPE = "text/csv";
	static String[] HEADERs = {"Id","Title","Description","Published"};

	public static boolean hasCSVFormat(MultipartFile file) {
		if(TYPE.equals(file.getContentType()) || file.getContentType().equals("application/vnd.ms-excel")) {
			return true;
		}
		return false;
	}

	public static List<DeveloperTutorial> csvToTutorials(InputStream is){
		try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){
			List<DeveloperTutorial> developerTutorialsList = new ArrayList<>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for(CSVRecord csvRecord : csvRecords) {
				DeveloperTutorial developerTutorial = new DeveloperTutorial(Long.parseLong(csvRecord.get("Id")),
						csvRecord.get("Title"),
						csvRecord.get("Description"),
						Boolean.parseBoolean(csvRecord.get("Published")));
				developerTutorialsList.add(developerTutorial);
			}

			return developerTutorialsList;
		}catch(IOException e) {
			throw new RuntimeException("fail to parse CSV file: "+e.getMessage());
		}
	}

	public static ByteArrayInputStream tutorialsToCSV(List<DeveloperTutorial> developerTutorialsList) {
		final CSVFormat format= CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

		try(ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format); ) {
			
			for(DeveloperTutorial developerTutorial : developerTutorialsList) {
				List<String> data = Arrays.asList(
						String.valueOf(developerTutorial.getId()),
						developerTutorial.getTitle(),
						developerTutorial.getDescription(),
						String.valueOf(developerTutorial.isPublished()));
				
				csvPrinter.printRecord(data);
			}
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		}catch(IOException e) {
			throw new RuntimeException("fail to import data to CSV file: "+e.getMessage());
		}
	}

}
