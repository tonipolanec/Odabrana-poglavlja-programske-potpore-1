package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

/**
 * Interface for various collections.
 * 
 * @author Toni Polanec
 */
public interface Collection<E> {

	
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
	 * @param arr of element we want to add
	 */
	void add(E arr);
	
	
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
	boolean remove(E value);
	
	
	/** Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array. 
	 * 
	 * @return array of type Object[]
	 */
	Object[] toArray();
	
	/** Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array. 
	 * 
	 * @return array of type Object[]
	 */
	E[] toArray(E[] a);
	
	
	/** Method calls processor.process(.) for each element of this collection. 
	 * The order in which elements will be sent is undefined in this class. 
	 * 
	 * @param processor with which we process each element in collection
	 */
	default void forEach(Processor<? super E> processor) {
		ElementsGetter<E> getter = createElementsGetter();
		
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
	@SuppressWarnings("unchecked")
	default void addAll(Collection<? extends E> other) {
		Object[] arr = other.toArray();
		for (int i=0; i<other.size(); i++) {
			add((E)arr[i]);
		}
	}
	
	
	/** Removes all elements from this collection. */
	void clear();


	/** Creates and returns new ElementsGetter
	 * 
	 * @return new ElementsGetter for collection
	 */
	ElementsGetter<E> createElementsGetter();

	
	/** Gets all elements of collection with ElementsGetter and adds at the end all elements
	 *  which tester finds acceptable.
	 *  
	 *  @param col other collection we want to add elements from
	 *  @param tester for testing elements
	 */
	default void addAllSatisfying(Collection<? extends E> col, Tester<? super E> tester) {
		ElementsGetter<? extends E> getter =  col.createElementsGetter();
		
		while(getter.hasNextElement()) {
			E element = getter.getNextElement();
			if (tester.test(element))
				add(element);	

		}
	}



	
	
	
	
	
	
	
	
	
}

