package hr.fer.oprpp1.hw04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
			
			if(parser.isDirectQuery()) {
				StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
				System.out.println("Using index for record retrieval.");
				
			} else {
				for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
					
				}
			}
			
		}
		
	}
		

	
	private static List<String> getLines(String path) throws IOException {
		Path filePath = Paths.get("src/main/resources/" + path);
		return Files.readAllLines(filePath);
	}


	
	
	
	
	
	
	
	
	
	
	
	

}
