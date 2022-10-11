package hr.fer.oprpp1.custom.collections;

public class ArrayIndexedCollection extends Collection {
	// Duplicate elements are allowed, storage of null references is not allowed.
	
	private int size;
	private Object[] elements;
	
	// Constructor delegation
	public ArrayIndexedCollection() {
		this(16);
	}
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		size = 0;
		elements = new Object[initialCapacity];
	}	
	
	public ArrayIndexedCollection(Collection other) {
		this(other, 1);
	}
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (other == null){
			throw new NullPointerException();
		}
		if (other.size() > initialCapacity)
			elements = new Object[other.size()];
		else
			elements = new Object[initialCapacity];
		
		this.addAll(other);
		size = other.size();
	}
	
	
	/** Returns the number of currently stored objects in this collections. */
	public int size() {
		return size;
	}
	
	
	/** Adds the given object into this collection. */
	public void add(Object value) {
		// Complexity of O(n)
		
		if (value == null) {
			throw new NullPointerException();
		}
		
		int emptyEl = this.size();
		if (emptyEl < elements.length) {
			elements[emptyEl] = value;	
		
		}else {
			// Doubling the size
			int arrSize = elements.length;
			Object[] newElements = new Object[arrSize*2];
			
			for (int i=0; i<arrSize; i++) {
				newElements[i] = elements[i];
			}
			
			newElements[emptyEl] = value;
			
			elements = newElements;
		}
		
		size++;
	}
	
	
	
	/** Returns true only if the collection contains given value, as determined by equals method. */
	public boolean contains(Object value) {
		for(int i=0; i<size; i++) {
			if (elements[i].equals(value))
				return true;
		}
		return false;
	}
	
	
	
	/** Returns the object that is stored in backing array at position index. */
	public Object get(int index) {
		// Complexity of O(1)
		
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		return elements[index];
	}
	
	
	/** Removes all elements from this collection. */
	public void clear() {
		for (int i=0; i<size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	
	/** Inserts (does not overwrite) the given value at the given position in array. */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		// Inserting on last position
		if (position == size) {
			add(value);
		}
		// Array is full
		else if (size == elements.length) {
			Object lastEl = get(size-1);
			
			// Shifting elements one place right
			for (int i=size-2; i>=position; i--) {
				elements[i+1] = elements[i];
			}
			// Inserting value
			elements[position] = value;
			// Adding last element back
			add(lastEl);
		}
		// Array isn't full, freely shifting and inserting
		else {
			// Shifting elements one place right
			for (int i=size-1; i>=position; i--) {
				elements[i+1] = elements[i];
			}
			// Inserting value
			elements[position] = value;
			size++;
		}

				
	}
	
	
	/** Searches the collection and returns the index of the first occurrence of the given value 
	 * or -1 if the value is not found. */
	public int indexOf(Object value) {
		// Average complexity of O(n/2)
		if (value == null)
			return -1;
		
		for(int i=0; i<size; i++) {
			if (elements[i].equals(value))
				return i;
		}
		return -1;
	}
	

	/** Removes element at specified index from collection. */
	public void remove(int index) {
		if (index < 0 || index > size-1) 
			throw new IndexOutOfBoundsException();
		
		// Removes and shifts elements to fill the hole
		for(int i=index+1; i<size; i++) {
			elements[i-1] = elements[i];
		}
		
		size--;
	}
	
	/** Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array. */
	public Object[] toArray() {
		Object[] array = new Object[this.size()];
		
		for(int i=0; i<size; i++) {
			array[i] = this.get(i);
		}
		return array;
	}
	
	
	/** Method calls processor.process(.) for each element of this collection. 
	 * The order in which elements will be sent is undefined in this class. */
	public void forEach(Processor processor) {
		for(int i=0; i<size; i++) {
			processor.process(get(i));
		}
	}
	
	
	
	
	
	
	
}
