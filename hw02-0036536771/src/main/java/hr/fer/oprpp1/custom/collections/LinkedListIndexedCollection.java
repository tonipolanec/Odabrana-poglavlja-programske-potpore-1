package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * <p>Implementation of linked list collection.</p>
 * <p>Duplicate elements are allowed, storage of null references is not allowed.</p>
 * 
 * @author Toni Polanec
 * 
 * @see hr.fer.oprpp1.custom.collections.Collection
 * @see hr.fer.oprpp1.custom.collections.ArrayIndexedCollection
 */
public class LinkedListIndexedCollection implements Collection {
	
	/** <p>Current numbers of elements in collection.</p>*/
	int size;
	
	/** <p>Reference to the first node of the linked list.</p>*/
	ListNode first;
	
	/** <p>Reference to the last node of the linked list.</p>*/
	ListNode last;
	
	/** <p>Number used to check if collection was modified in mean time.</p>*/
	long modificationCount;
	
	
	/**<p>Local class for individual nodes in collection.</p>*/
	private static class ListNode{
		/**<p>Value of object we want to store.</p>*/
		Object value;
		
		/**<p>Pointer to previous list node.</p>*/
		ListNode prev;
		
		/**<p>Pointer to next list node.</p>*/
		ListNode next;
	}
	
	/**<p>Initializes empty LinkedListIndexedCollection</p>*/
	public LinkedListIndexedCollection() {
		// first=last=null
		first = null;
		last = null;
		size = 0;
		modificationCount = 0;
	}
	
	/**Initializes LinkedListIndexedCollection and copies other collection.
	 * 
	 * @param other collection we want to copy in this new one
	 * @exception NullPointerException if given null collection to copy
	 */
	public LinkedListIndexedCollection(Collection other) {
		if (other == null)
			throw new NullPointerException();
		
		addAll(other);
		modificationCount = 0;
	}
	
	
	/** Returns the number of currently stored objects in this collections. */
	@Override
	public int size() {
		return size;
	}
	
	
	/** Adds the given object into this collection at the end of collection. 
	 * @param value
	 * @exception NullPointerExcpetion if given null as an argument
	 * */
	@Override
	public void add(Object value) {
		if (value == null)
			throw new NullPointerException();
		
		ListNode node = new ListNode();
		node.value = value;
		
		// If first node
		if (size == 0) {
			first = node;
			last = node;
			
		}else {
			last.next = node;
			node.prev = last;
			
			last = node;
		}
		
		size++;
		modificationCount++;
	}

	/** Returns true only if the collection contains given value, as determined by equals method. 
	 * @param value
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null)
			return false;
		
		ListNode node = first;
		
		for(int i=0; i<size; i++) {
			if (node.value.equals(value)) 
				return true;
			node = node.next;
		}
		
		return false;
	}

	/** Returns true only if the list contains given value as determined by 
	 * equals method and removes one occurrence of it. 
	 * @param value we want to remove from linked list
	 * @return <code>true</code> if it was successfully removed, <code>false</code> if it doesn't contain given value
	 */
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
		ListNode node = first;
		Object[] array = new Object[this.size()];
		
		for(int i=0; i<size; i++) {
			array[i] = node.value;
			node = node.next;
		}
				
		return array;
	}

	/** Method calls processor.process(.) for each element of this collection. 
	 * The order in which elements will be sent is undefined in this class. 
	 * @param processor
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode node = first;
		
		for(int i=0; i<size; i++) {
			processor.process(node.value);
			node = node.next;
		}	
	}

	/** Removes all elements from this collection. */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
		modificationCount++;
	}
	

	/** Returns the object that is stored in linked list at position index. 
	 * @param index
	 * @exception IndexOutOfBoundsException if given index is lower than zero or greater or equals than size of collection
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) 
			throw new IndexOutOfBoundsException();
		
		ListNode currNode = new ListNode();
		if (index < size/2) {
			// Start from beginning
			currNode = first;
			for(int i=0; i<index; i++) {
				currNode = currNode.next;
			}
		}else {
			// Start from  end
			currNode = last;
			for(int i=size-1; i>index; i--) {
				currNode = currNode.prev;
			}
		}
		
		return currNode.value;
	}

	/** Inserts (does not overwrite) the given value at the given position in linked-list. 
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
		
		ListNode node = new ListNode();
		node.value = value;
		
		if (position == 0) {
			first.prev = node;
			node.next = first;
			first = node;
			
			size++;
			modificationCount++;
		
		}else if (position == size) {
			// We are adding to the tail of collection so we can use add()
			add(value);
			
		}else {
			ListNode currNode = first;
			ListNode nextNode;
			for(int i=0; i<position-1; i++) {
				currNode = currNode.next;
			}
			nextNode = currNode.next;
			
			currNode.next = node;
			node.prev = currNode;
			node.next = nextNode;
			nextNode.prev = node;
	
			size++;
			modificationCount++;
		}

			
	}
	
	
	/** Searches the collection and returns the index of the first occurrence of the given value
	 * @param value
	 * @return index at which is given value or -1 if it doesn't exists in collection
	 */
	public int indexOf(Object value) {
		if (value == null)
			return -1;
		
		ListNode node = first;
		
		for(int i=0; i<size; i++) {
			if (node.value.equals(value)) 
				return i;
			node = node.next;
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
		
		
		if (index == 0) {
			first = first.next;
			first.prev = null;

		}else if (index == size-1) {
			last = last.prev;
			last.next = null;
		
		}else {	
			ListNode node = first;
			for(int i=0; i<index; i++) {
				node = node.next;
			}
			ListNode prevNode = node.prev;
			ListNode nextNode = node.next;
			
			prevNode.next = nextNode;
			nextNode.prev = prevNode;
		}
				
		size--;
		modificationCount++;
	}
	
	
	
	/** Creates and returns new ElementsGetter for LinkedListIndexedCollection
	 * @return new ElementsGetter for collection*/
	@Override
	public ElementsGetter createElementsGetter() {
		return new ElementsGetterLinkedList(this);
	}
	
	
	/**
	 * Private static class used for getting elements from collection.
	 * 
	 * @author Toni Polanec
	 */
	private static class ElementsGetterLinkedList implements ElementsGetter {
		
		LinkedListIndexedCollection llic;
		ListNode currNode;
		long savedModificationCount;
		
		public ElementsGetterLinkedList(LinkedListIndexedCollection coll) {
			llic = coll;
			currNode = coll.first;
			savedModificationCount = coll.modificationCount;
		}
		
		/** Checks if collection has more elements to get. 
		 * @return <code>true</code> if more elements available, <code>false</code> otherwise
		 * @throws ConcurrentModificationException if collection was modified 
		 * and ElementsGetter refers to old collection
		 */
		public boolean hasNextElement() {
			if (savedModificationCount != llic.modificationCount)
				throw new ConcurrentModificationException();
			
			if (currNode == null) return false;
			
			return true;
		}
		
		/** Returns next object in collection.
		 * @return element at the next index
		 * @throws NoSuchElementException if no more elements to get
		 * @throws ConcurrentModificationException if collection was modified
		 * and ElementsGetter refers to old collection
		 */
		public Object getNextElement() {	
			if (savedModificationCount != llic.modificationCount)
				throw new ConcurrentModificationException();
			
			if(currNode == null) throw new NoSuchElementException();
			
			ListNode current = currNode;
			currNode = currNode.next;
			
			return current.value;			
		}
		
		
	}
	
	
	
	
	

}
