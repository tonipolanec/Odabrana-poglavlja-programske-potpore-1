package hr.fer.oprpp1.custom.collections;

public interface Processor<T> {
	
	/** Method in which we process element of a collection in a certain way 
	 * @param value of element we want to process
	 */
	void process(T value);
}
