package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class SmartScriptTester {

	public static void main(String[] args) {
		
		String docBody;
		
		SmartScriptParser parser;
		
		DocumentNode document;
		String originalDocumentBody;
		
		SmartScriptParser parser2;
		DocumentNode document2;
		
		
		for(int i=1; i<=5; i++) {
			
			try {
				docBody = new String(
						Files.readAllBytes(Paths.get("src/test/resources/sourcecode"+ i +".txt")),
						StandardCharsets.UTF_8
						);
			} catch (IOException e1) {
				throw new SmartScriptParserException("Error with path!");
			}
			
			
				
			parser = new SmartScriptParser(docBody);
			
			
			document = parser.getDocumentNode();
			originalDocumentBody = document.toString();
			

			parser2 = new SmartScriptParser(originalDocumentBody);
			document2 = parser2.getDocumentNode();
			
			System.out.println(document.equals(document2));
				
			
			
		}
	

	}

}
