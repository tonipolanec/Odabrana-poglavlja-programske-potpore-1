package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.*;

/** Lexer class used for...
 * 
 * @author Toni Polanec
 */
public class Lexer {

	private char[] data;
	private int currentIndex;
	private LexerState state;
	
	//private Element element;
	private boolean currentlyInTag, tagNameDefined;
	private int tagEnd;
	private TokenType currentTokenType;
	
	public Lexer(String data) {
		this.data = data.toCharArray();
		currentIndex = 0;
		currentlyInTag = false;
		tagNameDefined = false;
		currentTokenType = TokenType.TAG;
		
		state = LexerState.TEXT;
	}
	
	/** Gets next element in data.
	 * @return next element
	 * @throws LexerException if any error happens*/
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

			System.out.println("--TEXT--");
			return new ElementString(value);						
		}
		
		else if (state == LexerState.TAG) {
			
			// If we are here for the first time this tag
			if (!currentlyInTag) {
				// Takes the size of whole tag
				int tagStart = currentIndex;
				while(currentIndex < data.length) {
					if(checkTagEnd()) {
						//currentIndex += 2;
						break;
					}
					currentIndex++;
				}
				tagEnd = currentIndex;	
				// We don't need first 2 symbols "{$"
				currentIndex = tagStart+2;
				currentlyInTag = true;
				
				if(tagEnd >= data.length || tagStart >= data.length)
					throw new LexerException("Error with reading tag!");
			}
			
			skipWhitespaces();
			
			while(currentIndex < tagEnd) {
				String value = "";
				
				// We need tag name at the beginning of tag
				if (currentTokenType == TokenType.TAG) {
					System.out.println("--TAG--");
					if (checkNextChar() == '=') {
						value += nextChar();
						currentTokenType = null;
						System.out.println("name:");
						
						checkIfLastInTag();
						return new ElementTag(value);
					}
					else if (Character.isLetter(checkNextChar())) {
						value += nextChar();
						while(checkNextChar() != ' ' && checkNextChar() != '$') {
							if(Character.isLetter(checkNextChar()) || Character.isDigit(checkNextChar()) || checkNextChar() == '_')
								value += nextChar();
							else
								throw new LexerException("Invalid tag (variable name)!");
						}
						currentTokenType = null;
						System.out.println("name:");
						
						checkIfLastInTag();
						return new ElementTag(value);
						
					} else {
						throw new LexerException("Invalid tag!");
					}	
				
				} else {
					
					// ELEMENT VARIABLE
					if (Character.isLetter(checkNextChar())) {
						value += nextChar();
						while(checkNextChar() != ' ' && checkNextChar() != '$') {
							if(Character.isLetter(checkNextChar()) || Character.isDigit(checkNextChar()) || checkNextChar() == '_')
								value += nextChar();
							else
								throw new LexerException("Invalid variable name!");
						}

						System.out.println("variable:");

						checkIfLastInTag();
						return new ElementVariable(value);
					
					// ELEMENT FUNCTION
					}else if (checkNextChar() == '@') {
						currentIndex++;
						if (Character.isLetter(checkNextChar())) {
							value += nextChar();
							while(checkNextChar() != ' ') {
								if(Character.isLetter(checkNextChar()) || Character.isDigit(checkNextChar()) || checkNextChar() == '_')
									value += nextChar();
								else
									throw new LexerException("Invalid function name!");
							}

							System.out.println("function:");

							checkIfLastInTag();
							return new ElementVariable(value);
						
						} else {
							throw new LexerException("Invalid function name!");						
						}
					
					// ELEMENT OPERATOR +
					} else if (checkNextChar() == '+') {
						checkIfLastInTag();
						System.out.println("operator:");
						return new ElementOperator(nextChar()+"");
						
					// ELEMENT OPERATOR *
					} else if (checkNextChar() == '*') {
						checkIfLastInTag();
						System.out.println("operator:");
						return new ElementOperator(nextChar()+"");
						
					// ELEMENT OPERATOR /
					} else if (checkNextChar() == '/') {
						checkIfLastInTag();
						System.out.println("operator:");
						return new ElementOperator(nextChar()+"");
						
					// ELEMENT OPERATOR ^
					} else if (checkNextChar() == '^') {
						checkIfLastInTag();
						System.out.println("operator:");
						return new ElementOperator(nextChar()+"");
						
					// ELEMENT OPERATOR -
					} else if (checkNextChar() == '-') {
						value += nextChar();
						// Checks if there's digit behind '-'
						if (!Character.isDigit(checkNextChar())) {
							checkIfLastInTag();
							System.out.println("operator:");
							return new ElementOperator(value);
						
						// There's digit behind '-', treat it as digit!
						} else {
							value += nextChar();
							boolean dot = false;
							while(Character.isDigit(checkNextChar()) || checkNextChar() == '.') {
								if (dot && checkNextChar() == '.')
									throw new LexerException("Invalid constant double!");
								
								char c = nextChar();
								value += c;
								if (c == '.') 
									dot = true;
							}

							System.out.println("minus number");
							
							checkIfLastInTag();
							if (dot)
								return new ElementConstantDouble(Double.parseDouble(value));
							return new ElementConstantInteger(Integer.parseInt(value));
						}			
					
					// ELEMENT INTEGER OR DOUBLE
					} else if(Character.isDigit(checkNextChar())) {
						value += nextChar();
						boolean dot = false;
						while(Character.isDigit(checkNextChar()) || checkNextChar() == '.') {
							if (dot && checkNextChar() == '.')
								throw new LexerException("Invalid constant double!");
							
							char c = nextChar();
							value += c;
							if (c == '.') 
								dot = true;
						}

						System.out.println("number:");

						checkIfLastInTag();
						if (dot)
							return new ElementConstantDouble(Double.parseDouble(value));
						return new ElementConstantInteger(Integer.parseInt(value));
						
					// ELEMENT STRING
					} else if (checkNextChar() == '"') {
						int stringStart = ++currentIndex;
						char prev = checkNextChar();
						while(checkNextChar() != '"' || prev == '\\') {
							prev = checkNextChar();
							currentIndex++;
						}
						int stringEnd = currentIndex++;
						
						String string = new String(data, stringStart, stringEnd-stringStart);

						System.out.println("string:");

						checkIfLastInTag();
						return new ElementString(processEscaping(string));
					}
					
				}
			}
			
		}
		
		throw new LexerException("Error at getting new element!");
	}
	 
	
	
	/** Processes the string by escaping rules in tag.
	 * @param string we want to process
	 * @return processed string
	 * @throws LexerException
	 */
	private String processEscaping(String string) {
		String processed = "";
		int index = 0;
		boolean escaped = false;
		while(index < string.length()) {
			
			if(escaped) {
				if(string.charAt(index) == '\\') {
					processed += '\\';
				
				} else if(string.charAt(index) == 'n') {
					processed += '\n';
					
				} else if(string.charAt(index) == 'r') {
					processed += '\r';
					
				} else if(string.charAt(index) == 't') {
					processed += '\t';
				
				} else {
					throw new LexerException("Tag escaping invalid!");
				}
				
				index++;
					
			} else if(string.charAt(index) == '\\') {
				escaped = true;
				index++;
			
			// Normal string
			} else {
				processed += string.charAt(index++);
			}
		}

		return processed;
	}
	
	
	/**<p> Checks if current element is last in tag. 
	 * If it is switch state. </p>
	 */
	public void checkIfLastInTag() {
		skipWhitespaces();
		if (currentIndex >= tagEnd) {
			System.out.println("--ENDTAG--");
			currentlyInTag = false;
			state = LexerState.TEXT;
			currentIndex += 2; // We need to skip tag end symbols '$}'
		}
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
				currentTokenType = TokenType.TAG;
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
				//setState(LexerState.TEXT);
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
	
	/** Skips all whitespaces up to next element.
	 */
	private void skipWhitespaces() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			
			if (c=='\r' || c=='\n' || c=='\t' || c==' ') {
				currentIndex++;
				continue;
			}
			break;
		}
	}
	
//	public Element getLastElement() {
//		return element;
//	}
	
	
	
	
	
	
}
