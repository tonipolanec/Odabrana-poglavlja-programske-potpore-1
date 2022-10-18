package hr.fer.oprpp1.hw02.prob1;


/** Exception which warns if there's been an error with lexer.
 * 
 * @author Toni Polanec
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1609971924115613480L;

	public LexerException() {
		super();
	}

	public LexerException(String message) {
		super(message);
	}


}
