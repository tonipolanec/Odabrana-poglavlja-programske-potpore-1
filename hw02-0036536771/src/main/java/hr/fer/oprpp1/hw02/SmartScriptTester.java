package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.lexer.LexerException;

public class SmartScriptTester {

	public static void main(String[] args) {
		Lexer lexer = new Lexer("This is sample text.\r\n"
				+ "{$ FOR i 1 10 1 $}\r\n"
				+ " This is {$= i $}-th time this message is generated.\r\n"
				+ "{$END$}\r\n"
				+ "{$FOR i 0 10 2 $}\r\n"
				+ " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n"
				+ "{$END$}");

		while(true) {
			if(lexer.checkNextElement())
				System.out.println(lexer.getElement().asText());
			else
				break;
		}
	}

}
