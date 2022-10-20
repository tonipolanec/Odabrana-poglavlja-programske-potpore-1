package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

public class SmartScriptTester {

	public static void main(String[] args) {
		
//		String docBody = "Example \\{$=1$}. Now actually write one {$=1$}";
//		SmartScriptParser parser = null;
//		
//		try {
//			parser = new SmartScriptParser(docBody);
//			
//		} catch(SmartScriptParserException e) {
//			System.out.println("Unable to parse document!");
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
//		
//		
//		Lexer lexer = new Lexer("{$ FOR sco_re \"-1\"10 \"1\" $}");

		
		
		String docBody = "Example {$ for i i \"text2\"$} text3 {$ end   $} text4";
		
		Lexer lexer = new Lexer(docBody);
		Element element;
		while(lexer.checkNextElement()) {
			element = lexer.getElement();
			
			System.out.println(element.asText());
		}

	}

}
