package hr.fer.oprpp1.custom.collections;

/** Exception which warns when we want to access elements in empty stack.
 * 
 * @author Toni Polanec
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 8971006009421026750L;

	public EmptyStackException() {
		super();
	}

	public EmptyStackException(String message) {
		super(message);
	}

}
