package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.*;

/** Lexer class used for tokenizing input stream.
 * 
 * @author Toni Polanec
 */
public class Lexer {

	/**<p>Input data in characters</p>*/
	private char[] data;
	
	/**<p>Index we are currently working with in data array</p>*/
	private int currentIndex;
	
	/**<p>Shows which state we are in (<code>TEXT</code> or <code>TAG</code>)</p>*/
	private LexerState state;
	
	
	//private Element element;
	/**<p>Flag to know if we are processing tag or not</p>*/
	private boolean currentlyInTag;
	
	/**<p>Flag for tag name</p>*/
	private boolean gotTokenName;
	
	/**<p>Index at which current tag ends</p>*/
	private int tagEnd;
	

	
	public Lexer(String data) {
		this.data = data.toCharArray();
		currentIndex = 0;
		currentlyInTag = false;
		gotTokenName = false;
		
		state = determineFirstState();
	}
	 
	/** Gets next element in data.
	 * @return next element
	 * @throws LexerException if any error happens
	 */
	public Element getElement() {
		if(currentIndex >= data.length) 
			throw new LexerException("No elements available.");
		
		// If we just left tag, it automatically put us in TEXT mode
		// maybe there's 2 tags repeatedly
		if (state == LexerState.TEXT) {
			if (checkTagStart(false))
				setState(LexerState.TAG);
		}
		
		
		if(state == LexerState.TEXT) {
			// We need to worry about strings, escaped characters and starting tag symbol
			
			
			String value = "";
			boolean escaped = false;
			
			// Goes as long as it can until tag start symbols
			while(!checkTagStart(escaped) && !checkEOF() /*|| currentIndex < data.length*/) {
				
				// If it starts with escape character
				if (checkNextChar() == '\\' && !escaped) {
					escaped = true;
					currentIndex++;
				}	
				// Normal state
				if(escaped) { 
					char nextChar = nextChar();
					if(!(nextChar == '\\' || nextChar == '{'))
						throw new LexerException("Wrong escape in TEXT mode!");
					
					value += nextChar;
					escaped = false;
				} else {
			
					value += nextChar();
				}
			}

			//System.out.println("--TEXT--\n"+ ElementString.getPlainText(value));
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
			
			while(currentIndex < tagEnd && !checkEOF()) {
				String value = "";
				
				// We need tag name at the beginning of tag
				if (!gotTokenName) {
					//System.out.println("--TAG--");
					if (checkNextChar() == '=') {
						value += nextChar();
						gotTokenName = true;
						//System.out.println("name: " + value);
						
						checkIfLastInTag();
						return new ElementTag(value);
					}
					else if (Character.isLetter(checkNextChar()) && checkNextChar() != '@') {
						value += nextChar();
						while(checkNextChar() != ' ' && checkNextChar() != '$' && checkNextChar() != '-' && checkNextChar() != '"') {
							if(Character.isLetter(checkNextChar()) || Character.isDigit(checkNextChar()) || checkNextChar() == '_')
								value += nextChar();
							else
								throw new LexerException("Invalid tag (variable name)!");
						}
						gotTokenName = true;
						//System.out.println("name: " + value);
						
						checkIfLastInTag();
						return new ElementTag(value);
						
					} else {
						throw new LexerException("Invalid tag!");
					}	
				
				} else {
					
					// ELEMENT VARIABLE
					if (Character.isLetter(checkNextChar()) && checkNextChar() != '@') {
						value += nextChar();
						while(checkNextChar() != ' ' && checkNextChar() != '$' && checkNextChar() != '-' && checkNextChar() != '"') {
							if(Character.isLetter(checkNextChar()) || Character.isDigit(checkNextChar()) || checkNextChar() == '_')
								value += nextChar();
							else
								throw new LexerException("Invalid variable name!");
						}

						//System.out.println("variable: " + value);

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

							//System.out.println("function: "+value);

							checkIfLastInTag();
							return new ElementFunction(value);
						
						} else {
							throw new LexerException("Invalid function name!");						
						}
					
					// ELEMENT OPERATOR +
					} else if (checkNextChar() == '+') {
						checkIfLastInTag();
						//System.out.println("operator: +");
						return new ElementOperator(nextChar()+"");
						
					// ELEMENT OPERATOR *
					} else if (checkNextChar() == '*') {
						checkIfLastInTag();
						//System.out.println("operator: *");
						return new ElementOperator(nextChar()+"");
						
					// ELEMENT OPERATOR /
					} else if (checkNextChar() == '/') {
						checkIfLastInTag();
						//System.out.println("operator: /");
						return new ElementOperator(nextChar()+"");
						
					// ELEMENT OPERATOR ^
					} else if (checkNextChar() == '^') {
						checkIfLastInTag();
						//System.out.println("operator: ^");
						return new ElementOperator(nextChar()+"");
						
					// ELEMENT OPERATOR -
					} else if (checkNextChar() == '-') {
						value += nextChar();
						// Checks if there's digit behind '-'
						if (!Character.isDigit(checkNextChar())) {
							checkIfLastInTag();
							//System.out.println("operator: -");
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

							//System.out.println("minus number: " + value);
							
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

						//System.out.println("number: " + value);

						checkIfLastInTag();
						if (dot)
							return new ElementConstantDouble(Double.parseDouble(value));
						return new ElementConstantInteger(Integer.parseInt(value));
						
					// ELEMENT STRING
					} else if (checkNextChar() == '"' || checkNextChar() == '\\') {
						char prev = ' ';
						
						int stringStart = 0;
						if(checkNextChar() == '"') {
							stringStart = ++currentIndex;
						}else if(checkNextChar() == '\\'){
							stringStart = currentIndex++;
							prev = '\\';
						}
						//prev = checkNextChar();
						
						while(checkNextChar() != '"' || (prev == '\\' && checkNextChar() == '"')) {
							prev = checkNextChar();
							currentIndex++;
						}
						int stringEnd = currentIndex++;
						
						String string = new String(data, stringStart, stringEnd-stringStart);

						//System.out.println("string: " + string);

						checkIfLastInTag();
						return new ElementString(processEscaping(string));
					}
					
				}
			}
			
		}
		
		throw new LexerException("Error at getting new element!");
	}
	 
	
	/**<p> Checks if there's next element to get. <p>*/ 
	public boolean checkNextElement() {
		//skipWhitespaces();
		if(currentIndex >= data.length) 
			return false;
		return true;
	}
	
	
	/**<p> Returns state which lexer is currently in. <p>*/
	public LexerState getState() {
		return state;
	}
	
	/** Determines if it is first text or immediately tag.
	 * @return <code>LexerState.TAG</code> or <code>LexerState.TAG</code>
	 */
	public LexerState determineFirstState() {
		if (checkTagStart(false))
			return LexerState.TAG;
		return LexerState.TEXT;
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
				
				} else if(string.charAt(index) == '"') {
					processed += '"';
				} else if(string.charAt(index) == 'n') {
					processed += '\n';
					
				} else if(string.charAt(index) == 'r') {
					processed += '\r';
					
				} else if(string.charAt(index) == 't') {
					processed += '\t';
				
				} else {
					throw new LexerException("Tag escaping invalid!");
				}
				
				escaped = false;
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
	private void checkIfLastInTag() {
		skipWhitespaces();
		if (currentIndex >= tagEnd) {
			//System.out.println("--ENDTAG--");
			currentlyInTag = false;
			gotTokenName = false; // For next tag if it exists
			state = LexerState.TEXT;
			currentIndex += 2; // We need to skip tag end symbols '$}'
		}
	}
	
	
	/** Return next character in data.
	 * @return next character in data
	 * @exception LexerException if we at the end of file 
	 */
	private char nextChar() {
		try {
			return data[currentIndex++];
		} catch (Exception ex) {
			throw new LexerException("Failed getting next character in data!");
		}
	}
	
	/** Checks which character is next data but we don't increment currentIndex.
	 * @return next character in data
	 * @exception LexerException if we at the end of file 
	 */
	private char checkNextChar() {
		if(currentIndex + 1 > data.length) return ' ';
		try {
			char c = nextChar();
			currentIndex--;
			return c;
			
		} catch (LexerException ex) {
			throw new LexerException("Failed checking next character in data!");
		}
	}
	
	
	/** Checks tag start symbols <code>{$</code>.
	 * @param escaped boolean if we are currently in escape mode
	 * @return <code>true</code> if next are tag symbols, <code>false</code> otherwise
	 * @exception LexerException if we at the end of file
	 */
	private boolean checkTagStart(boolean escaped) {
		// Don't start tag if its escaped
		if (escaped) return false;
		if(currentIndex + 2 > data.length) return false;
		try {
			
			char c1 = nextChar();
			char c2 = nextChar();
			currentIndex -= 2;
		
			if(c1 == '{' && c2 == '$') {
				gotTokenName = false;
				setState(LexerState.TAG);
				return true;
			}
			return false;
			
		} catch (LexerException ex) {
			throw new LexerException("Failed checking tag start symbols!");
		}
	}
	/** Checks tag end symbols <code>{$</code>.
	 * @return <code>true</code> if next are tag symbols, <code>false</code> otherwise
	 * @exception LexerException if we at the end of file
	 */
	private boolean checkTagEnd() {
		try {
			char c1 = nextChar();
			char c2 = nextChar();
			currentIndex -= 2;
		
			if(c1 == '$' && c2 == '}') {
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
	
	/**<p> Skips all whitespaces up to next element. </p>*/
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
	
	
	/**<p> Checks if we are at the end of file. <p>*/
	private boolean checkEOF() {
		if (currentIndex >= data.length)
			return true;
		return false;
	}
	
	
	
	
	
	
}
