package hr.fer.oprpp1.custom.scripting.lexer;


/** Exception which warns if there's been an error with lexer.
 * 
 * @author Toni Polanec
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = -1096344907212473881L;

	public LexerException() {
		super();
	}

	public LexerException(String message) {
		super(message);
	}


}
