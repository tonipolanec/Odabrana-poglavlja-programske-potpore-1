package hr.fer.oprpp1.custom.collections;

public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 8971006009421026750L;

	public EmptyStackException() {
		super();
	}

	public EmptyStackException(String message) {
		super(message);
	}

}
