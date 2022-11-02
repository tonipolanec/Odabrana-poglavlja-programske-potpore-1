package hr.fer.oprpp1.hw04.db;

/**
 * Token is the element lexer recognizes.
 * 
 * @author Toni Polanec
 */
public class Token {

	private TokenType type;
	private String value;

	public Token(String value, TokenType type) {
		this.value = value;
		this.type = type;

	}

	public String getValue() {
		return value;
	}

	public TokenType getType() {
		return type;
	}
	
	public String toString() {
		return type.name() + ": " + value;
	}

}
