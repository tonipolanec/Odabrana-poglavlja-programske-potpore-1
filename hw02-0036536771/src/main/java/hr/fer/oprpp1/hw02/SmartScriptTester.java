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
		try {
			docBody = new String(
					Files.readAllBytes(Paths.get("src/main/resources/doc1.txt")),
					StandardCharsets.UTF_8
					);
		} catch (IOException e1) {
			throw new SmartScriptParserException("Error with path!");
		}
		
//	
//		SmartScriptParser parser = null;
//		
//		try {
//			parser = new SmartScriptParser(docBody);
//			
//		} catch(SmartScriptParserException e) {
//			System.out.println("Unable to parse document!");
//			System.out.println(e.getMessage());
//			System.exit(-1);
//			
//		} catch(Exception e) {
//			System.out.println("If this line ever executes, you have failed this class!");
//			System.exit(-1);
//		}
//		
//		DocumentNode document = parser.getDocumentNode();
//		String originalDocumentBody = document.toString();
//		
//		// should write something like original content of docBody
//		System.out.println(originalDocumentBody); 	
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2); // ==> "same" must be true

		
		System.out.println("\n"+ document.toString() + "\n");
		System.out.println(document2.toString() + "\n");
		
		System.out.println(same);
		
		


	}

}
