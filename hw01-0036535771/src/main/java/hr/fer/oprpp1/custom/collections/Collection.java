package hr.fer.oprpp1.custom.collections;

public class Collection {

	protected Collection() {	
		
	}
	
	/** Returns true if collection contains no objects and false otherwise. */
	public boolean isEmpty() {
		// Utilizing method size()
		if (this.size() == 0) 
			return true;
		else
			return false;
	}

	
	/** Returns the number of currently stored objects in this collections. */
	public int size() {
		// Implement it here to always return 0.
		return 0;
	}
	
	
	/** Adds the given object into this collection. */
	public void add(Object value) {
		// Implement it here to do nothing
	}
	
	
	/** Returns true only if the collection contains given value, as determined by equals method. */
	public boolean contains(Object value) {
		// Value CAN be null
		// Implement it here to always return false. 
		return false;
	}
	
	
	/** Returns true only if the collection contains given value as determined by 
	 * equals method and removes one occurrence of it 
	 * (in this class it is not specified which one). */
	public boolean remove(Object value) {
		// Implement it here to always return false.
		return false;
	}
	
	
	/** Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array. */
	public Object[] toArray() {
		// Object[] array = new Object[this.size()];
		// Fills the array with collection content
		// return array;
		
		// Implement it here to throw UnsupportedOperationException.
		throw new UnsupportedOperationException();
	}
	
	
	/** Method calls processor.process(.) for each element of this collection. 
	 * The order in which elements will be sent is undefined in this class. */
	public void forEach(Processor processor) {
		// Implement it here as an empty method.
	}
	

	/** Method adds into the current collection all elements from the given collection. */
	public void addAll(Collection other) {
		
		class AddingProcessor extends Processor {
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new AddingProcessor());
	}
	
	
	/** Removes all elements from this collection. */
	public void clear() {
		// Implement it here as an empty method.
	}









}



