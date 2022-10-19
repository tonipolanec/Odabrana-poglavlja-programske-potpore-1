package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.lexer.LexerException;

public class SmartScriptTester {

	public static void main(String[] args) {
		Lexer lexer = new Lexer("Example \\{$=1$}. Now actually write one {$=1$}");

		while(true) {
			if(lexer.checkNextElement()) {
				System.out.println(lexer.getElement().asText());
			
			
			
			
			} else
				break;
		}
		

	}

}
