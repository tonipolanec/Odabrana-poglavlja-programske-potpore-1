package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * <p>Implementation of array indexed collection.</p>
 * <p>Duplicate elements are allowed, storage of null references is not allowed.</p>
 * 
 * @author Toni Polanec
 * 
 * @see hr.fer.oprpp1.custom.collections.Collection
 * @see hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection
 */
public class ArrayIndexedCollection implements Collection {
	
	/** <p>Current numbers of elements in collection</p>*/
	private int size;
	
	/** <p>Array of object references</p>*/
	private Object[] elements;
	
	/** <p>Number used to check if collection was modified in mean time.</p>*/
	long modificationCount;
	
	
	/** <p>Initializes ArrayIndexedCollection with initial capacity 16.</p>*/
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/** Initializes ArrayIndexedCollection with given initial capacity.
	 * 
	 * @param initialCapacity
	 * @exception IllegalArgumentException if initialCapacity is lower than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) 
			throw new IllegalArgumentException();
		
		size = 0;
		elements = new Object[initialCapacity];
		modificationCount = 0;
	}	
	
	/** <p>Initializes ArrayIndexedCollection and copies other collection.</p>
	 * <p>Capacity is determined by other collections's size.</p>
	 * 
	 * @param other collection we want to copy in this new one
	 * @exception NullPointerException if given null
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, 1);
	}
	
	/** <p>Initializes ArrayIndexedCollection and copies other collection.</p>
	 * <p>Capacity is determined by initialCapacity or others size (bigger of the two).</p>
	 * 
	 * @param other collection we want to copy in this new one
	 * @param initialCapacity
	 * @exception NullPointerException if given null collection to copy
	 */
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
		modificationCount = 0;
	}
	
	
	/** Returns the number of currently stored objects in this collections. */
	@Override
	public int size() {
		return size;
	}
	
	
	/** Adds the given object into this collection. 
	 * @param value
	 * @exception NullPointerException if given null as an argument
	 */
	@Override
	public void add(Object value) {
		// Complexity of O(n)
		if (value == null) 
			throw new NullPointerException();
		
		
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
		modificationCount++;
	}
		
	
	/** Returns true only if the collection contains given value, as determined by equals method. 
	 * @param value
	 */
	@Override
	public boolean contains(Object value) {
		for(int i=0; i<size; i++) {
			if (elements[i].equals(value))
				return true;
		}
		return false;
	}

	/** Removes element with given value. If it doesn't exists it does nothing. 
	 * @param value of an element we want to remove
	 * @return <code>true</code> if it was successfully removed, <code>false</code> if collection didn't contain given value
	 * */
	@Override
	public boolean remove(Object value) {
		if (!contains(value))
			return false;
		
		remove(indexOf(value));
		return true;
	}

	/** Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array.
	 * @return array of an collection
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[this.size()];
		
		for(int i=0; i<size; i++) {
			array[i] = this.get(i);
		}
		return array;
	}

	/** Method calls processor.process(.) for each element of this collection. 
	 * The order in which elements will be sent is undefined in this class. 
	 * @param processor with which we process elements
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i=0; i<size; i++) {
			processor.process(get(i));
		}
	}

	/** Removes all elements from this collection. */
	@Override
	public void clear() {
		for (int i=0; i<size; i++) {
			elements[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	/** Returns the object that is stored in backing array at position index. 
	 * @param index
	 * @exception IndexOutOfBoundsException if given index is lower than zero or greater or equals than size of collection
	 */
	public Object get(int index) {
		// Complexity of O(1)
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		return elements[index];
	}
	
	
	/** Inserts (does not overwrite) the given value at the given position in array. 
	 * @param value
	 * @param position
	 * @exception NullPointerException if given null as an argument
	 * @exception IndexOutOfBoundsException if given index is lower than zero or greater than size of collection
	 */
	public void insert(Object value, int position) {
		if (value == null)
			throw new NullPointerException();
		if (position < 0 || position > size) 
			throw new IndexOutOfBoundsException();
		
		
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
			modificationCount++;
		}		
	}
	
	
	/** Searches the collection and returns the index of the first occurrence of the given value
	 * @param value
	 * @return index at which is given value or -1 if it doesn't exists in collection
	 */
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
	
	
	/** Removes element at specified index from collection. 
	 * @param index
	 * @exception IndexOutOfBoundsException if given index is lower than zero or greater or equals than size of collection
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) 
			throw new IndexOutOfBoundsException();
		
		// Removes and shifts elements to fill the hole
		for(int i=index+1; i<size; i++) {
			elements[i-1] = elements[i];
		}
		
		size--;
		modificationCount++;
	}
	
	
	
	/** Creates and returns new ElementsGetter for ArrayIndexedCollection
	 * @return new ElementsGetter for collection*/
	@Override
	public ElementsGetter createElementsGetter() {
		return new ElementsGetterArray(this);
	}
	
	
	/**
	 * Private static class used for getting elements from collection.
	 * 
	 * @author Toni Polanec
	 */
	private static class ElementsGetterArray implements ElementsGetter {
		
		ArrayIndexedCollection aic;
		int index = 0;
		long savedModificationCount;
		
		public ElementsGetterArray(ArrayIndexedCollection coll) {
			aic = coll;
			savedModificationCount = coll.modificationCount;
		}
		
		/** Checks if collection has more elements to get. 
		 * @return <code>true</code> if more elements available, <code>false</code> otherwise
		 * @throws ConcurrentModificationException if collection was modified 
		 * and ElementsGetter refers to old collection
		 * */
		public boolean hasNextElement() {
			if (savedModificationCount != aic.modificationCount)
				throw new ConcurrentModificationException();
			
			try {
				Object element = aic.elements[index];
				if (element == null) return false;
				return true;
				
			} catch (IndexOutOfBoundsException ex) {
				return false;
			}
			
		}
		
		/** Returns next object in collection.
		 * @return element at the next index
		 * @throws NoSuchElementException if no more elements to get
		 * @throws ConcurrentModificationException if collection was modified 
		 * and ElementsGetter refers to old collection
		 */
		public Object getNextElement() {
			if (savedModificationCount != aic.modificationCount)
				throw new ConcurrentModificationException();
			
			try {
				Object element = aic.elements[index++];
				if (element == null) throw new NoSuchElementException();
				
				return element;
			} catch (IndexOutOfBoundsException ex) {
				throw new NoSuchElementException();
			}	
		}
		
	}
	
	
	
	
	

	
	
	
}
