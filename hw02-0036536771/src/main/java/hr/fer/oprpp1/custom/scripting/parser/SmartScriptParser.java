package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.lexer.Lexer;

public class SmartScriptParser {

	//private String document;
	private Lexer lexer;
	
	public SmartScriptParser(String document) {
		//this.document = document;
		lexer = new Lexer(document);
	}
	
	public void getTokens() {
		
	}

}
