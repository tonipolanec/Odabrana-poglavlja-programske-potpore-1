package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

/**
 * Interface for various collections.
 * 
 * @author Toni Polanec
 */
public interface Collection {

	
	/** Returns the number of currently stored objects in this collections. 
	 * 
	 * @return number of elements in collection*/
	int size();


	/** Returns true if collection contains no objects and false otherwise. 
	 * 
	 * @return <code>true</code> if collection has no elements, <code>false</code> otherwise.
	 */
	default boolean isEmpty() {
		if (size() == 0) return true;
		return false;
	}

	
	/** Adds the given object into this collection. 
	 * 
	 * @param value of element we want to add
	 */
	void add(Object value);
	
	
	/** Returns true only if the collection contains given value, as determined by equals method. 
	 * 
	 * @param value element we want to check if it is in collection
	 * @return <code>true</code> if it is in collection,
	 * 		<code>false</code> otherwise 
	 */
	boolean contains(Object value);
	
	
	/** Returns true only if the collection contains given value as determined by 
	 * equals method and removes one occurrence of it. 
	 * 
	 * @param value we want to remove from collection
	 * @return <code>true</code> if it was successfully removed, 
	 * 		<code>false</code> if it doesn't contain given value
	 */
	boolean remove(Object value);
	
	
	/** Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array. 
	 * 
	 * @return array of type Object[]
	 */
	Object[] toArray();
	
	
	/** Method calls processor.process(.) for each element of this collection. 
	 * The order in which elements will be sent is undefined in this class. 
	 * 
	 * @param processor with which we process each element in collection
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = createElementsGetter();
		
		while(true) {
			try {
				processor.process(getter.getNextElement());
			} catch (NoSuchElementException ex) {
				break;
			}
		}
	}
	

	/** Method adds into the current collection all elements from the given collection. 
	 * 
	 * @param other collection we want to add to this collection
	 */
	default void addAll(Collection other) {
		Object[] arr = other.toArray();
		for (int i=0; i<other.size(); i++) {
			add(arr[i]);
		}
	}
	
	
	/** Removes all elements from this collection. */
	void clear();


	/** Creates and returns new ElementsGetter
	 * 
	 * @return new ElementsGetter for collection
	 */
	ElementsGetter createElementsGetter();

	
	/** Gets all elements of collection with ElementsGetter and adds at the end all elements
	 *  which tester finds acceptable.
	 *  
	 *  @param col other collection we want to add elements from
	 *  @param tester for testing elements
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		
		while(true) {
			try {		
				Object element = getter.getNextElement();
				if (tester.test(element))
					add(element);
									
			} catch (NoSuchElementException ex) {
				break;
			}
		}
	}

	
	
	
	
	
	
	
	
	
}

