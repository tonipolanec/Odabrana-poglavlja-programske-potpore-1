package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

public class SmartScriptTester {

	public static void main(String[] args) {
		
		String docBody = "This is sample text.\r\n"
				+ "{$ FOR i 1 10 1 $}\r\n"
				+ "This is {$= i $}-th time this message is generated.\r\n"
				+ "{$END$}"
				+ "closing text";
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
			
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.out.println(e.getMessage());
			System.exit(-1);
			
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		
		// should write something like original content of docBody
		System.out.println("\nOriginal content:\n" + originalDocumentBody); 	
		
	

		
		


	}

}
