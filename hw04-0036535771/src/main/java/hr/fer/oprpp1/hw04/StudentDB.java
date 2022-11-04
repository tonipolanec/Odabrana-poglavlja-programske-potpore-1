package hr.fer.oprpp1.hw04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw04.db.QueryFilter;
import hr.fer.oprpp1.hw04.db.QueryParser;
import hr.fer.oprpp1.hw04.db.StudentDatabase;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class StudentDB {

	public static void main(String[] args) throws IOException{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
//		List<String> lines = getLines("database.txt");
		StudentDatabase db = new StudentDatabase(getLines("database.txt"));
		QueryParser parser;
		
		while(true) {
			String command = reader.readLine();			
			
			if (command.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			parser = new QueryParser(command);
			List<StudentRecord> acceptableRecords = new ArrayList<>();
			List<String> output;
			
			if(parser.isDirectQuery()) {
				
				acceptableRecords = resultFromQuery(db, parser);
				output = formatRecords(acceptableRecords);
				
				System.out.println("Using index for record retrieval.");
				
				if (output.size() > 0)
					for(String s : output) 
						System.out.println(s);
				
				System.out.println("Records selected: " + acceptableRecords.size() + "\n");
		
			} else {
				//acceptableRecords = db.filter(new QueryFilter(parser.getQuery()));
				
				acceptableRecords = resultFromQuery(db, parser);
				output = formatRecords(acceptableRecords);

				if (output.size() > 0)
					for(String s : output) 
						System.out.println(s);
				
				System.out.println("Records selected: " + acceptableRecords.size() + "\n");
			}
			
		}
		
	}
		

	private static List<StudentRecord> resultFromQuery(StudentDatabase db, QueryParser parser) {
		List<StudentRecord> results = new ArrayList<>();
		
		if (parser.isDirectQuery())
			results.add(db.forJMBAG(parser.getQueriedJMBAG()));	
		else {
			results = db.filter(new QueryFilter(parser.getQuery()));
		}
		
		return results;
	}


	/**
	 * Processes all acceptable records and gives out list of strings representing output.
	 * 
	 * @param acceptableRecords
	 * @return output
	 */
	private static List<String> formatRecords(List<StudentRecord> acceptableRecords) {
		
		List<String> outputLines = new ArrayList<>();
		
		if (acceptableRecords.size() == 0)
			return outputLines;
		
		int maxSizeJmbag = 0, maxSizeLastName = 0, maxSizeFirstName = 0;
		for (StudentRecord r : acceptableRecords) {
			if (r.getJmbag().length() > maxSizeJmbag)
				maxSizeJmbag = r.getJmbag().length();
			
			if (r.getLastName().length() > maxSizeLastName)
				maxSizeLastName = r.getLastName().length();
			
			if (r.getFirstName().length() > maxSizeFirstName)
				maxSizeFirstName = r.getFirstName().length();
		}
		
		// Formating horizontal edge
		// added for jmbag initially
		String horizontalLine = "+============+";
		// adding for last name
		for (int i=0; i<maxSizeLastName+2; i++)
			horizontalLine += "=";
		horizontalLine += "+";
		// adding for first name
		for (int i=0; i<maxSizeFirstName+2; i++)
			horizontalLine += "=";
		// adding for final grade and ending
		horizontalLine += "+===+";
		
		
		// First edge
		outputLines.add(horizontalLine);
		
		for (StudentRecord r : acceptableRecords) {
			
			String jmbagPart = r.getJmbag();
			String lastNamePart = r.getLastName();
			String firstNamePart = r.getFirstName();
			
			// adding spaces for jmbag
			if (r.getJmbag().length() < maxSizeJmbag) {
				int diff = maxSizeJmbag - r.getJmbag().length();
				for (int i=0; i<diff; i++)
					jmbagPart += " ";
			}
			// adding spaces for last name
			if (r.getLastName().length() < maxSizeLastName) {
				int diff = maxSizeLastName - r.getLastName().length();
				for (int i=0; i<diff; i++)
					lastNamePart += " ";
			}
			// adding spaces for first name
			if (r.getFirstName().length() < maxSizeFirstName) {
				int diff = maxSizeFirstName - r.getFirstName().length();
				for (int i=0; i<diff; i++)
					firstNamePart += " ";
			}
			
			// adding separators and all parts together
			String recordLine = "| " + jmbagPart + " | " + lastNamePart + " | " + firstNamePart + " | " +
								r.getFinalGrade() + " |";
			
			outputLines.add(recordLine);		
		}
		
		
		// Last edge
		outputLines.add(horizontalLine);
		
		return outputLines;
	}

	

	/**
	 * Gets all lines from given document from path.
	 */
	private static List<String> getLines(String path) throws IOException {
		Path filePath = Paths.get("src/main/resources/" + path);
		return Files.readAllLines(filePath);
	}


	
	
	
	
	
	
	
	
	
	
	
	

}
