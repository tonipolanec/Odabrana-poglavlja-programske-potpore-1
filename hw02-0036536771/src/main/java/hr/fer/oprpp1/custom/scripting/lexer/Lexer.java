package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.hw02.prob1.TokenType;

/** Lexer class used for...
 * 
 * @author Toni Polanec
 */
public class Lexer {

	private char[] data;
	private int currentIndex;
	private LexerState state;
	
	private Element element;
	
	
	public Lexer(String data) {
		this.data = data.toCharArray();
		currentIndex = 0;
		
		state = LexerState.TEXT;
	}
	
	
	public Element getElement() {
		if(currentIndex >= data.length) 
			throw new LexerException("No elements available.");
		
		
		if(state == LexerState.TEXT) {
			// We need to worry about strings, escaped characters and starting tag symbol
			
			String value = "";
			boolean escaped = false;
			
			// Goes as long as it can until tag start symbols
			while(!checkTagStart(escaped) || currentIndex > data.length) {
				
				// If it starts with escape character
				if (checkNextChar() == '\\' && !escaped) {
					escaped = true;
					currentIndex++;
				}	
				// Normal state
				if(escaped) { 
					char nextChar = nextChar();
					if(nextChar != '\\' || nextChar != '{')
						throw new LexerException("Wrong escape in TEXT mode!");
					
					value += nextChar;
					escaped = false;
				} else {
					value += nextChar();
				}
			}
			
			return new ElementString(value);						
		}
		
		else if (state == LexerState.TAG) {
			// Takes whole tag, removes whitespaces and puts it in char[] tag
			int tagStart = currentIndex;
			while(currentIndex < data.length) {
				if(checkTagEnd()) {
					currentIndex += 2;
					break;
				}
				currentIndex++;
			}
			int tagEnd = currentIndex;	
			
			char[] tag = (new String(data, tagStart, tagEnd-tagStart)).replaceAll("\s+\t+", "").toCharArray();
			
			return new ElementString(String.valueOf(tag));
			
//			for(int i=2; i<tag.length-2; i++) { // [2, length-2] because of start and end symbols '{$', '$}'
//				
//			
//			}
//			
//			
//			
//			setState(LexerState.TEXT);
			
		}
		
		throw new LexerException("Error at getting new element!");
	}
	
	
	/**<p> Checks if there's next element to get. <p>*/ 
	public boolean checkNextElement() {
		if(currentIndex >= data.length) 
			return false;
		return true;
	}
	
	
	/**<p> Return next character in data </p>*/
	private char nextChar() {
		try {
			return data[currentIndex++];
		} catch (Exception ex) {
			throw new LexerException("Failed getting next character in data!");
		}
	}
	
	/**<p> Checks which character is next data </p>*/
	private char checkNextChar() {
		try {
			char c = nextChar();
			currentIndex--;
			return c;
			
		} catch (LexerException ex) {
			throw new LexerException("Failed checking next character in data!");
		}
	}
	
	/**<p> Checks tag start symbols <code>{$</code> </p>*/
	private boolean checkTagStart(boolean escaped) {
		// Don't start tag if its escaped
		if (escaped) return false;
		
		try {
			char c1 = nextChar();
			char c2 = nextChar();
			currentIndex -= 2;
		
			if(c1 == '{' && c2 == '$') {
				setState(LexerState.TAG);
				return true;
			}
			return false;
			
		} catch (LexerException ex) {
			throw new LexerException("Failed checking tag start symbols!");
		}
	}
	/**<p> Checks tag end symbols <code>$}</code> </p>*/
	private boolean checkTagEnd() {
		try {
			char c1 = nextChar();
			char c2 = nextChar();
			currentIndex -= 2;
		
			if(c1 == '$' && c2 == '}') {
				setState(LexerState.TEXT);
				return true;
			}
			return false;
			
		} catch (LexerException ex) {
			throw new LexerException("Failed checking tag end symbols!");
		}
	}
	
	/**<p> Sets lexer state of this lexer. <p>*/
	private void setState(LexerState state) {
		this.state = state; 
	}
	
	
	
//	public Element getLastElement() {
//		return element;
//	}
	
	
	
	
	
	
}
