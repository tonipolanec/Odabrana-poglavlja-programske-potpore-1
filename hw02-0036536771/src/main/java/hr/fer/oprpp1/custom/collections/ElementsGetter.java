package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

public interface ElementsGetter {
	
	/** Checks if collection has more elements to get. 
	 * @return <code>true</code> if more elements available, <code>false</code> otherwise
	 * */
	boolean hasNextElement();
	
	/** Returns next object in collection.
	 * @return element at the next index
	 * @throws NoSuchElementException if no more elements to get
	 */
	Object getNextElement();
	
	
	/** Process all remaining elements in collection
	 * @param proccesor with which we process elements
	 */
	default void processRemaining(Processor p) {
		while(true) {
			try {		
				p.process(getNextElement());
				
			} catch (NoSuchElementException ex) {
				break;
			}
		}
	}

}
