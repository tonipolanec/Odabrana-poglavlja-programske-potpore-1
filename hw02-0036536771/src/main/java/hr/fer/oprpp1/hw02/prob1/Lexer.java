package hr.fer.oprpp1.hw02.prob1;


/** Simple lexicographic analyzer for code.
 * 
 * @author Toni Polanec
 * @see hr.fer.oprpp1.hw02.prob1.Token
 */
public class Lexer {

	/** <p>Entry code for lexer to process </p>*/
	private char[] data;
	
	/** <p>Current token we process </p>*/
	private Token token;
	
	/** <p>Index of first still not processed symbol </p>*/
	private int currentIndex;
	
	/** <p>Flag for escaped characters </p>*/
	private boolean escaped = false;
	
	/** <p>Defines current state of lexer </p>*/
	private LexerState state;

	
	/** Constructor for lexer
	 * 
	 * @param text code we want to parse
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	

	/** Generates and returns next token from data.
	 * 
	 *  @return next token
	 *  @throws LexerException if there's some kind of error
	 */
	public Token nextToken() {
		
		// If last generated token was EOF then throw exception.
		if(token != null && token.getType() == TokenType.EOF) 
			throw new LexerException("No tokens available.");
				
		skipWhitespaces();
		
		// Checks end of file
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		String stringValue = "";
		char currChar;// = nextChar();
		
		
		if(state == LexerState.BASIC) {
		
			// Checks if next token is of type WORD
			if(checkNextChar() == TokenType.WORD) { 
				currChar = nextChar();
				
				if (currChar == '\\') 
					escaped = true;
				else 
					stringValue += currChar;
				
				while(checkNextChar() == TokenType.WORD || escaped) {	
					if (!escaped) {
						currChar = nextChar();
						if(currChar == '\\')
							escaped = true;
						else
							stringValue += currChar;
				
					} else {
						// Adding escaped character (any) to word
						currChar = nextChar();	
						if (Character.isLetter(currChar)) 
							throw new LexerException("Invalid escape");
						
						stringValue += currChar;
						escaped = false;
					}		
				}		
				
				token = new Token(TokenType.WORD, stringValue);	
				return token;
			}
			
			// Checks if next token is of type NUMBER
			else if(checkNextChar() == TokenType.NUMBER) {
				
				int numberStart = currentIndex;
				while(currentIndex < data.length && Character.isDigit(data[currentIndex])) {
					currentIndex++;
				}
				int numberEnd = currentIndex;
				
				stringValue = new String(data, numberStart, numberEnd-numberStart);
				try {
					long value = Long.parseLong(stringValue);
					
					token = new Token(TokenType.NUMBER, value);
					return token;
					
				} catch (NumberFormatException ex) {
					throw new LexerException("Input is wrong!");
				}
			
			// Next token is of type SYMBOL
			} else {
				char value = nextChar();
				token = new Token(TokenType.SYMBOL, value);
			
				if (value == '#') setState(LexerState.EXTENDED);
				return token;
			}
		}
		
		else if (state == LexerState.EXTENDED) {
			
			if(checkLadders()) {
				setState(LexerState.BASIC);
				
				currChar = nextChar();
				token = new Token(TokenType.SYMBOL, currChar);
				return token;
			}
			
			currChar = nextChar();
			
			while(currChar != '#') {
				// Words are spliced by whitespace
				if(currChar == ' ') {
					// Return word token with characters up to whitespace 
					token = new Token(TokenType.WORD, stringValue);
					return token;
				
				}else {		
					stringValue += currChar;
				
					if (!checkLadders()) {
						currChar = nextChar();
					
					}else {	
						setState(LexerState.BASIC);
						token = new Token(TokenType.WORD, stringValue);
						return token;
					}		
				}
			}
		}
		
		// Throw exception because if all works it should return before
		throw new LexerException("ERROR with LexerState");
		
	}
	
	
	/** Returns last generated token, it can be called any amount of times.
	 * It doesn't generate next token
	 * 
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
	}
	
	
	/** Dynamically sets state of lexer.
	 * 
	 * @param state <code>LexerState.BASIC</code> or <code>LexerState.EXTENDED</code>
	 * @throws NullPointerException if given null
	 */
	public void setState(LexerState state) {
		if (state == null) throw new NullPointerException();
		
		this.state = state;
	}
		
	
	/** Skips all whitespaces up to next token.
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
	
	/** Return next character in data */
	private char nextChar() {
		try {
			return data[currentIndex++];	
			
		} catch (IndexOutOfBoundsException ex) {
			throw new LexerException("Error in getting next character");
		}
	}
	
	/** Checks which <code>TokenType</code> is next character in data 
	 * @return token type of next character
	 */
	private TokenType checkNextChar() {
		char c;
		try {
			c = nextChar();
			currentIndex--;
		} catch (LexerException ex) {
			return TokenType.EOF;
		}
		
		if (Character.isLetter(c) || c == '\\') return TokenType.WORD;
		else if (Character.isDigit(c)) return TokenType.NUMBER;
		else return TokenType.SYMBOL;	
	}
	
	/** Checks if next character is <code>#</code> */
	private boolean checkLadders() {	
		
		if(checkNextChar() == TokenType.SYMBOL) {	
			char c = nextChar();
			currentIndex--;
			
			if (c == '#') return true;
			return false;
		}
		return false;
	}

}
